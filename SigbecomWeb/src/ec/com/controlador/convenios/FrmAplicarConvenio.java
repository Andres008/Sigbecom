package ec.com.controlador.convenios;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.controlador.sesvas.DtoSesvasAdjunto;
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

@Named("frmAplicarConvenio")
@SessionScoped
public class FrmAplicarConvenio implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerConvenios managerConvenios;
	
	private List<ConvServicio> lstConvServicio;
	
	private Long idServicio;
	private ConvServicio convServicio;
	private ConvAdquirido convAdquirido;
	private List<ConvAdquirido> lstConvAdquirido;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		lstConvServicio = new ArrayList<ConvServicio>();
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		idServicio = new Long(0);
		convServicio = new ConvServicio();
		convAdquirido = new ConvAdquirido(); 
		cargarListaConvServicio();
		cargarListaConvAdquiridos();
	}
	public void cargarListaConvServicio() {
		try {
			lstConvServicio = managerConvenios.findAllConvServicioActivos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaConvAdquiridos() {
		try {
			String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
			lstConvAdquirido = managerConvenios.findByCedConvAdquirido(cedulaSocio);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarConvServicio() {
		if(idServicio!=0) {
			try {
				convServicio=managerConvenios.findByIdConvServicio(idServicio);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo Servicio correctamente");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("No ha seleccionado ningun servicio");
		}
	}
	
	private UploadedFile file;
	byte[] archivo;
	
	public void handleFileUpload(FileUploadEvent event) {
        System.out.println("Archivo subido: "+ event.getFile().getFileName());
        this.file = event.getFile();
        JSFUtil.crearMensajeINFO("Documento cargado correctamente");
        convAdquirido.setAdjunto(event.getFile().getFileName());
    }
	
	public void precargarArchivo() {
		if(file!=null) {
			System.out.println("Tipo AAAAA archivo:"+file.getContentType());
			archivo = file.getContent();
			//file=null;
		}
	}
	
	public void aplicarConvenio() {
		if(convServicio!= null && convAdquirido.getAdjunto() != null) {
			try {
				String path=managerConvenios.buscarValorParametroNombre("PATH_REPORTES");
				String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
				String url = path+"\\convenios\\"+cedulaSocio+"\\";
				String str = convAdquirido.getAdjunto();
				String ext = str.substring(str.lastIndexOf('.'), str.length());
				Date fechaActual = new Date();
				SimpleDateFormat formato = new SimpleDateFormat("dd");
				int diaActual = Integer.parseInt(formato.format(fechaActual));
				String nombreArchivo = ModelUtil.getAnioActual()+"-"+
						   ModelUtil.getMesActual()+"-"+
						   diaActual+"-proforma";
				convAdquirido.setAdjunto(nombreArchivo+ext);
				convAdquirido.setConvServicio(convServicio);
				convAdquirido.setCedulaSocio(cedulaSocio);
				convAdquirido.setEstado("SOLICITADO");
				convAdquirido.setFechaSol(new Date());
				managerConvenios.insertarConvAdquirido(convAdquirido);
				InputStream fis = new ByteArrayInputStream(archivo);
				ModelUtil.guardarArchivo(fis, nombreArchivo, url, ext);
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form2");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se ha registrado correctamente");
				e.printStackTrace();
			}
		}
	}
	public boolean activarTablaAmortizacion(String estado) {
		if(!estado.equalsIgnoreCase("SOLICITADO") && !estado.equalsIgnoreCase("NO APROBADO")) {
			return true;
		}
		return false;
	}
	
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public List<ConvServicio> getLstConvServicio() {
		return lstConvServicio;
	}
	public void setLstConvServicio(List<ConvServicio> lstConvServicio) {
		this.lstConvServicio = lstConvServicio;
	}
	public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public ConvServicio getConvServicio() {
		return convServicio;
	}
	public void setConvServicio(ConvServicio convServicio) {
		this.convServicio = convServicio;
	}
	public ConvAdquirido getConvAdquirido() {
		return convAdquirido;
	}
	public void setConvAdquirido(ConvAdquirido convAdquirido) {
		this.convAdquirido = convAdquirido;
	}
	public List<ConvAdquirido> getLstConvAdquirido() {
		return lstConvAdquirido;
	}
	public void setLstConvAdquirido(List<ConvAdquirido> lstConvAdquirido) {
		this.lstConvAdquirido = lstConvAdquirido;
	}
}
