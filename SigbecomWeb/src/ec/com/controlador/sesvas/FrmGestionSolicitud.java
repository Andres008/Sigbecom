package ec.com.controlador.sesvas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.SesvasSolicitud;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.sesvas.ManagerSesvas;

@Named("frmGestionSolicitud")
@SessionScoped
public class FrmGestionSolicitud implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerSesvas managerSesvas;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<SesvasSolicitud> lstSesvasSolicituds;
	private SesvasSolicitud sesvasSolicitud;
	private boolean inputVal;
	private String estado;
	private String resolucion;
	private BigDecimal valor;
	
	private String nombreArchivoTmp;
	private InputStream fis;
	private String extension="";
	
	@PostConstruct
	public void ini() {
		lstSesvasSolicituds = new ArrayList<SesvasSolicitud>();
		sesvasSolicitud = new SesvasSolicitud();
		inputVal = true;
		estado="";
		resolucion = "";
		valor = new BigDecimal(0);
		cargarListadoSolicitudes();
		
	}
	public void inicializar() {
		estado="";
		resolucion = "";
		inputVal = true;
		valor = new BigDecimal(0);
	}
	public void cargarListadoSolicitudes() {
		try {
			//lstSesvasSolicituds = managerSesvas.listadoDeSolicitudesByEstado("SOLICITADO");
			lstSesvasSolicituds = managerSesvas.findAllSolicitudes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo la información requerida correctamente");
			e.printStackTrace();
		}
	}
	public void cargarSolicitud(long idSesvasSolicitud) {
		sesvasSolicitud = lstSesvasSolicituds.stream().filter(p->p.getIdSesvasSolicitud()==idSesvasSolicitud).findAny().orElse(null);
	}
	public boolean habilitarTramite(String estado) {
		if(estado.equalsIgnoreCase("APROBADO") || estado.equalsIgnoreCase("NO APROBADO")) {
			return true;
		}
		else {
			return false;
		}
	}
	public void renderValor() {
		System.out.println("Dato:"+sesvasSolicitud.getEstado());
		if(estado.equalsIgnoreCase("APROBADO")) {
			inputVal = false;
		}
		else {
			inputVal = true;
		}
	}
	public String tramitarSolicitud() {
		PrimeFaces prime=PrimeFaces.current();
		if(resolucion.isEmpty()) {
			JSFUtil.crearMensajeERROR("Se necesita que ingrese la Resolución");
		}
		else if(estado==null || estado.isEmpty()) {
			JSFUtil.crearMensajeERROR("Se necesita que seleccione una Opcion");
		}
		else if(valor.compareTo(BigDecimal.ZERO) == 0 && estado.equalsIgnoreCase("APROBADO")) {
			JSFUtil.crearMensajeERROR("Se necesita que ingrese un valor diferente de 0.00");
		}
		else if(!resolucion.isEmpty() && !estado.equalsIgnoreCase("SOLICITADO")) {
			System.out.println("Pricesando");
			Date date = new Date();  
            Timestamp fechaResol = new Timestamp(date.getTime());
			
			sesvasSolicitud.setEstado(estado);
			sesvasSolicitud.setResolucion(resolucion);
			sesvasSolicitud.setFechaAsignacion(fechaResol);
			sesvasSolicitud.setValor(valor.setScale(2, BigDecimal.ROUND_HALF_EVEN));
			try {
				managerSesvas.actualizarObjeto(sesvasSolicitud);
				inicializar();
				cargarListadoSolicitudes();
				prime.executeScript("PF('dlgTramite').hide();");
				prime.ajax().update("@form");
				JSFUtil.crearMensajeINFO("Tramite realizado correctamente");
				return "gestionSolicitudes.xhtml";
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se realizó la actualización correctamente");
				e.printStackTrace();
			}   
		}
		prime.ajax().update("frmTramite");
		return "";
	}
	
	public void cargarDocumento(String nombreArchivo) {
		String cedula=beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
		String url = "C:/Archivos/"+cedula+"/"+nombreArchivo;
		File file = new File(url);
		nombreArchivoTmp = nombreArchivo;
		String ext = nombreArchivoTmp.substring(nombreArchivoTmp.lastIndexOf('.'), nombreArchivoTmp.length());
		
		if (ext.equalsIgnoreCase(".pdf")) {
			try {
				fis = new FileInputStream(file);
				System.out.println("Url 1 : "+url);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmPDFT");
			prime.executeScript("PF('dlgPDFT').show();");
			
		}
		else {
			if(ext.equalsIgnoreCase(".png")) {
				extension = "png";
			}
			else if(ext.equalsIgnoreCase(".jpg")) {
				extension = "jpg";
			}
			try {
				fis = new FileInputStream(file);
				System.out.println("Url 2: "+url);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmIMAGET");
			prime.executeScript("PF('dlgImageT').show();");
		}
	}
	
	@SuppressWarnings("static-access")
	public StreamedContent getPdf() {
		if (fis != null) {
			return new DefaultStreamedContent().builder().contentType("application/pdf; charset=UTF-8").name(nombreArchivoTmp).stream(()-> fis).build();
			//return new DefaultStreamedContent(fis, "application/pdf; charset=UTF-8", "Sol_Factibilidad.pdf");
		}
		else {
			return null;
		}
	}
	@SuppressWarnings("static-access")
	public StreamedContent getDownload() {
		if (fis != null) {
			//System.out.println("extensionDownload: "+extension);
			return new DefaultStreamedContent().builder().contentType("image/"+extension).name(nombreArchivoTmp).stream(()-> fis).build();
		}
		else {
			return null;
		}
	}
	@SuppressWarnings("static-access")
	public StreamedContent getImage() {
		if (fis != null) {
			String tipo="image/"+extension;
			System.out.println("contentType: "+tipo);
			StreamedContent imagen = new DefaultStreamedContent().builder().contentType(tipo).name(nombreArchivoTmp).stream(()-> fis).build();
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmIMAGE");
			return imagen;
		}
		else {
			return null;
		}
	}
	
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<SesvasSolicitud> getLstSesvasSolicituds() {
		return lstSesvasSolicituds;
	}

	public void setLstSesvasSolicituds(List<SesvasSolicitud> lstSesvasSolicituds) {
		this.lstSesvasSolicituds = lstSesvasSolicituds;
	}

	public SesvasSolicitud getSesvasSolicitud() {
		return sesvasSolicitud;
	}

	public void setSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		this.sesvasSolicitud = sesvasSolicitud;
	}

	public boolean isInputVal() {
		return inputVal;
	}

	public void setInputVal(boolean inputVal) {
		this.inputVal = inputVal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
}
