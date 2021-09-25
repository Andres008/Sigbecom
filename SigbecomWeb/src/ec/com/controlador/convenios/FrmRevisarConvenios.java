package ec.com.controlador.convenios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.GesPersona;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

@Named("frmRevisarConvenios")
@SessionScoped
public class FrmRevisarConvenios implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerConvenios managerConvenios;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<ConvAdquirido> lstConvAdquirido;
	private List<ConvAdquirido> lstConvAdquiridoTrm;
	private ConvAdquirido convAdquirido;
	private String nombreArchivoTmp;
	private InputStream fis;
	private String extension;
	private String nombres;
	private String estado;
	
	
	private UploadedFile file;
	byte[] archivo;
	
	@PostConstruct
	public void init() {
		extension="";
		nombres = "";
		estado = "";
		archivo = null;
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		lstConvAdquiridoTrm = new ArrayList<ConvAdquirido>();
		convAdquirido = new ConvAdquirido();
		cargarListaConvAdquiridos();
		cargarListaConvAdquiridosTramitados();
	}

	public FrmRevisarConvenios() {
		super();
	}
	public void cargarListaConvAdquiridos() {
		
		try {
			lstConvAdquirido = managerConvenios.findConvAdquiridoByEstado("SOLICITADO");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaConvAdquiridosTramitados() {
			
			try {
				lstConvAdquiridoTrm = managerConvenios.findConvAdquiridoTramitados();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
				e.printStackTrace();
			}
		}
	public String nombres(String cedula) {
		try {
			GesPersona gesPersona = new GesPersona(); 
			gesPersona = managerConvenios.findNombresByCedula(cedula);
			if(gesPersona!=null) {
				return gesPersona.getApellidos()+" "+gesPersona.getNombres();
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo nombres correctamente");
			e.printStackTrace();
		}
		return "";
	}
	public void cargarDocumento(String adjunto, String cedula) {
		
		String path;
		try {
			path = managerConvenios.buscarValorParametroNombre("PATH_REPORTES");
			String url = path+"convenios/"+cedula+"/"+adjunto;
			System.out.println(url);
			File file = new File(url);
			nombreArchivoTmp = adjunto;
			String ext = nombreArchivoTmp.substring(nombreArchivoTmp.lastIndexOf('.'), nombreArchivoTmp.length());
			
			if (ext.equalsIgnoreCase(".pdf")) {
				try {
					fis = new FileInputStream(file);
					//System.out.println("Url 1 : "+url);
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
		} catch (Exception e1) {
			JSFUtil.crearMensajeERROR("No se cargo el archivo correctamente");
			e1.printStackTrace();
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
	
	public void cargarSolicitud(long idConvAdquiridos) {
		convAdquirido = lstConvAdquirido.stream().filter(p->p.getIdConvAdquiridos()==idConvAdquiridos).findAny().orElse(null);
		GesPersona gespersona;
		try {
			gespersona = managerConvenios.findNombresByCedula(convAdquirido.getConvValorMax().getUsrSocio().getCedulaSocio());
			nombres = gespersona.getApellidos()+" "+gespersona.getNombres();
			estado = "";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el archivo correctamente");
			e.printStackTrace();
		}
	}
	
	public void tramitar() {
		System.out.println("estado:" + estado);
		if(!estado.isEmpty()) {
			try {
				String path=managerConvenios.buscarValorParametroNombre("PATH_REPORTES");
				//String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
				String url = path+"/convenios/"+convAdquirido.getConvValorMax().getUsrSocio().getCedulaSocio()+"/";
				String str = convAdquirido.getAdjunto();
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				
				convAdquirido.setEstado(estado);
				convAdquirido.setFechaRevision(new Date());
				InputStream fis = new ByteArrayInputStream(archivo);
				ModelUtil.guardarArchivo(fis, convAdquirido.getIdConvAdquiridos()+"_hoja_de_ruta", url, ext);
				convAdquirido.setHojaRuta(convAdquirido.getIdConvAdquiridos()+"_hoja_de_ruta"+ext);
				managerConvenios.actualizarObjeto(convAdquirido);
				cargarListaConvAdquiridos();
				cargarListaConvAdquiridosTramitados();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.executeScript("PF('dlgTramite').hide();");
				JSFUtil.crearMensajeINFO("Se actualizó el tramite correctamente");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se actualizo el tramite correctamente");
				e.printStackTrace();
			}
			
		}
		else {
			JSFUtil.crearMensajeERROR("No se ha seleccionado una opción");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");
		}
	}
	public boolean activarTablaAmortizacion(String estado) {
		if(!estado.equalsIgnoreCase("SOLICITADO") && !estado.equalsIgnoreCase("NO CALIFICA")) {
			return true;
		}
		return false;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
        System.out.println("Archivo subido: "+ event.getFile().getFileName());
        this.file = event.getFile();
        JSFUtil.crearMensajeINFO("Documento cargado correctamente");
        convAdquirido.setHojaRuta(event.getFile().getFileName());
    }
	
	public void precargarArchivo() {
		if(file!=null) {
			System.out.println("Tipo AAAAA archivo:"+file.getContentType());
			archivo = file.getContent();
			//file=null;
		}
	}
	

	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<ConvAdquirido> getLstConvAdquirido() {
		return lstConvAdquirido;
	}

	public void setLstConvAdquirido(List<ConvAdquirido> lstConvAdquirido) {
		this.lstConvAdquirido = lstConvAdquirido;
	}

	public ConvAdquirido getConvAdquirido() {
		return convAdquirido;
	}

	public void setConvAdquirido(ConvAdquirido convAdquirido) {
		this.convAdquirido = convAdquirido;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ConvAdquirido> getLstConvAdquiridoTrm() {
		return lstConvAdquiridoTrm;
	}

	public void setLstConvAdquiridoTrm(List<ConvAdquirido> lstConvAdquiridoTrm) {
		this.lstConvAdquiridoTrm = lstConvAdquiridoTrm;
	}
	
}
