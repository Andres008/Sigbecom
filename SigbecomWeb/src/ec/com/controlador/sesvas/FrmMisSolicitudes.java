package ec.com.controlador.sesvas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

import com.ibm.wsdl.util.IOUtils;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.SesvasAdjunto;
import ec.com.model.dao.entity.SesvasBeneficio;
import ec.com.model.dao.entity.SesvasRequisito;
import ec.com.model.dao.entity.SesvasSolicitud;
import ec.com.model.modulos.util.CorreoUtil;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;
import ec.com.model.sesvas.ManagerSesvas;

@Named("frmMisSolicitudes")
@SessionScoped
public class FrmMisSolicitudes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerSesvas managerSesvas;
	
	private Long idBeneficioSelected;
	private boolean mostrarReq;
	private UploadedFile file;
	private StreamedContent documento;
	
	private List<SesvasRequisito> lstSesvasRequisitos;
	private List<DtoSesvasAdjunto> lstAdjuntos;
	private List<SesvasBeneficio> listBeneficios;
	private List<SesvasSolicitud> lstSesvasSolictud;
	
	private String nombreArchivoTmp;
	private InputStream fis;
	private String extension="";
	
	@Inject
	private BeanLogin beanLogin;

	public FrmMisSolicitudes() {
		super();
	}

	@PostConstruct
	public void init() {
		listBeneficios = new ArrayList<SesvasBeneficio>();
		idBeneficioSelected= new Long(0);
		lstSesvasRequisitos = new ArrayList<SesvasRequisito>();
		lstAdjuntos = new ArrayList<DtoSesvasAdjunto>();
		file = null;
		documento = null;
		mostrarReq = false;
		lstSesvasSolictud = new ArrayList<SesvasSolicitud>();
		cargarBeneficios();
		cargarMisSolicitudes();
	} 
	
	public void cargarBeneficios() {
		try {
			listBeneficios = managerSesvas.findAllSesvasBeneficio();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada");
			e.printStackTrace();
		}
	}
	public void cargarListaRequisitos() {
		
		System.out.println("PASO1");
		if(idBeneficioSelected>0) {
			mostrarReq = true;
			try {
				lstAdjuntos=new ArrayList<DtoSesvasAdjunto>();
				lstSesvasRequisitos = managerSesvas.findByIdBeneficioListSesvasRequisitos(idBeneficioSelected);
				for (SesvasRequisito sesvasRequisito : lstSesvasRequisitos) {
					DtoSesvasAdjunto dtoSesvasAdjunto = new DtoSesvasAdjunto();
					dtoSesvasAdjunto.setSesvasRequisito(sesvasRequisito);
					lstAdjuntos.add(dtoSesvasAdjunto);
				}
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada");
				e.printStackTrace();
			}
			
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
        System.out.println("Archivo subido: "+ event.getFile().getFileName());
        this.file = event.getFile();
        JSFUtil.crearMensajeINFO("Documento cargado correctamente");
       
    }
	
	public void precargarArchivo(DtoSesvasAdjunto dato) {
		if(file!=null) {
			String documento = dato.getSesvasRequisito().getSesvasTipoRequisito().getTipoRequisito();
			System.out.println("Tipo AAAAA archivo:"+file.getContentType());
			byte[] archivo = file.getContent();
			lstAdjuntos.stream().filter(p->p.getSesvasRequisito().getSesvasTipoRequisito().getTipoRequisito().equalsIgnoreCase(documento)).forEach(p->{
				p.setNombreArchivo(file.getFileName());
				p.setFile(archivo);
			});
			file=null;
		}
	}
	
	public void enviarDocumentos() {
		System.out.println("Upload: ");
        if (lstAdjuntos.stream().filter(p->p.getFile()==null).collect(Collectors.toList()).size()==0) {
        	Date date = new Date();  
            Timestamp fecha = new Timestamp(date.getTime());
            System.out.println("Fecha: "+fecha);
        	SesvasBeneficio sesvasBeneficio = listBeneficios.stream().filter(p->p.getIdSesvasBeneficios()==idBeneficioSelected).findAny().orElse(null);
            SesvasSolicitud sesvasSolicitud = new SesvasSolicitud();
        	sesvasSolicitud.setFecha(fecha);
        	sesvasSolicitud.setSesvasBeneficio(sesvasBeneficio);
        	sesvasSolicitud.setUsrSocio(beanLogin.getCredencial().getObjUsrSocio());
        	sesvasSolicitud.setEstado("SOLICITADO");
        	try {
				managerSesvas.registrarSolicitud(sesvasSolicitud);
				//String url = "C:\\Users\\SUPERTRONICA\\Documents\\"+beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio()+"\\";
				String url = "C:\\Archivos\\"+"1001962156"+"\\";
				for (DtoSesvasAdjunto dtoSesvasAdjunto : lstAdjuntos) {
					SesvasAdjunto sesvasAdjunto = new SesvasAdjunto();
					sesvasAdjunto.setNombreArchivo(dtoSesvasAdjunto.getNombreArchivo());
					sesvasAdjunto.setSesvasRequisito(dtoSesvasAdjunto.getSesvasRequisito());
					sesvasAdjunto.setSesvasSolicitud(sesvasSolicitud);
					
					String str = sesvasAdjunto.getNombreArchivo();
					String ext = str.substring(str.lastIndexOf('.'), str.length());
					Date fechaActual = new Date();
					SimpleDateFormat formato = new SimpleDateFormat("dd");
					int diaActual = Integer.parseInt(formato.format(fechaActual));
					String nombreArchivo = ModelUtil.getAnioActual()+"-"+
										   ModelUtil.getMesActual()+"-"+
										   diaActual+"-"+sesvasAdjunto.getSesvasRequisito().getSesvasTipoRequisito().getTipoRequisito()+"-"+sesvasSolicitud.getIdSesvasSolicitud();
					sesvasAdjunto.setNombreArchivo(nombreArchivo+ext);
					managerSesvas.registrarAdjuntos(sesvasAdjunto);
					InputStream fis = new ByteArrayInputStream(dtoSesvasAdjunto.getFile());
					ModelUtil.guardarArchivo(fis, nombreArchivo, url, ext);
					
				}
				CorreoUtil correoUtil= new CorreoUtil();
				//correoUtil.enviarCorreoElectronico(destinatario, asunto, mensaje);
				init();
				JSFUtil.crearMensajeINFO("Solcitud enviada correctamente");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registró correctamente");
				e.printStackTrace();
			}
        }
        else {
        	JSFUtil.crearMensajeWARN("Campos necesarios","Se necesita que seleccione los archivos correspondientes a la columna Requisitos");
        }
    }
	
	public void cargarMisSolicitudes() {
		String cedulaSocio=beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
		try {
			lstSesvasSolictud = managerSesvas.listadoDeSolicitudesByCedula(cedulaSocio);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			prime.ajax().update("frmPDF");
			prime.executeScript("PF('dlgPDF').show();");
			
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
			prime.ajax().update("frmIMAGE");
			prime.executeScript("PF('dlgImage').show();");
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
	
	public List<SesvasBeneficio> getListBeneficios() {
		return listBeneficios;
	}

	public void setListBeneficios(List<SesvasBeneficio> listBeneficios) {
		this.listBeneficios = listBeneficios;
	}
	
	public List<SesvasRequisito> getLstSesvasRequisitos() {
		return lstSesvasRequisitos;
	}

	public void setLstSesvasRequisitos(List<SesvasRequisito> lstSesvasRequisitos) {
		this.lstSesvasRequisitos = lstSesvasRequisitos;
	}

	public Long getIdBeneficioSelected() {
		return idBeneficioSelected;
	}

	public void setIdBeneficioSelected(Long idBeneficioSelected) {
		this.idBeneficioSelected = idBeneficioSelected;
	}

	public boolean isMostrarReq() {
		return mostrarReq;
	}

	public void setMostrarReq(boolean mostrarReq) {
		this.mostrarReq = mostrarReq;
	}

	public List<DtoSesvasAdjunto> getLstAdjuntos() {
		return lstAdjuntos;
	}

	public void setLstAdjuntos(List<DtoSesvasAdjunto> lstAdjuntos) {
		this.lstAdjuntos = lstAdjuntos;
	}

	public List<SesvasSolicitud> getLstSesvasSolictud() {
		return lstSesvasSolictud;
	}

	public void setLstSesvasSolictud(List<SesvasSolicitud> lstSesvasSolictud) {
		this.lstSesvasSolictud = lstSesvasSolictud;
	}
	
}
