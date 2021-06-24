package ec.com.controlador.sesvas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.FilterRegistration.Dynamic;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.ibm.wsdl.util.IOUtils;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.GesPariente;
import ec.com.model.dao.entity.SesvasAdjunto;
import ec.com.model.dao.entity.SesvasBeneficiario;
import ec.com.model.dao.entity.SesvasBeneficio;
import ec.com.model.dao.entity.SesvasRequisito;
import ec.com.model.dao.entity.SesvasSolicitud;
import ec.com.model.modulos.util.CorreoUtil;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;
import ec.com.model.sesvas.ManagerSesvas;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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
	private List<DtoPariente> lstDtoparientes;
	
	
	private String nombreArchivoTmp;
	private InputStream fis;
	private String extension="";
	private String cedulaBeneficiario;
	
	private static byte[] reportPdf;
	
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
		lstDtoparientes = new ArrayList<DtoPariente>();
		file = null;
		documento = null;
		mostrarReq = false;
		cedulaBeneficiario="";
		lstSesvasSolictud = new ArrayList<SesvasSolicitud>();
		cargarBeneficios();
		cargarMisSolicitudes();
	} 
	
	public void cargarBeneficios() {
		try {
			listBeneficios = managerSesvas.findSesvasBeneficioByEstado("ACTIVO");
			} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada");
			e.printStackTrace();
		}
	}
	public void cargarListaRequisitos() {
			
		System.out.println("PASO1");
		if(idBeneficioSelected>0) {
			//OBTENER BENEFICIO
			SesvasBeneficio sesvasBeneficio = new SesvasBeneficio();
			String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
			
			try {
				List<GesPariente> lstGesParientes = new ArrayList<GesPariente>();
				lstGesParientes = managerSesvas.findParientesByCed(cedulaSocio);
				sesvasBeneficio = managerSesvas.findByIdBeneficio(idBeneficioSelected);
				System.out.println("Cobertura:"+sesvasBeneficio.getTipoCobertura());
				//Verificar beneficio ***UNICO***
				if(sesvasBeneficio.getTipoCobertura().equalsIgnoreCase("UNICO")) {
					List<SesvasSolicitud> lstSolicitudes = new ArrayList<SesvasSolicitud>();
					System.out.println("Cedula:"+cedulaSocio);
					lstSolicitudes = managerSesvas.findSolicitudesByIdBeneficios(idBeneficioSelected,"APROBADO",cedulaSocio);
					System.out.println("Tamaño Lista:"+lstSolicitudes.size());
					if(lstSolicitudes!=null && lstSolicitudes.size()>0) {
						JSFUtil.crearMensajeERROR("Ya registra una solicitud aprobada a este beneficio Unico");
						PrimeFaces prime=PrimeFaces.current();
						prime.ajax().update("form1");
					}
					else {
						List<SesvasBeneficiario> lstSesvasBeneficiarios;
						lstSesvasBeneficiarios = new ArrayList<SesvasBeneficiario>();
						lstSesvasBeneficiarios = sesvasBeneficio.getSesvasBeneficiarios();
						
						lstDtoparientes= new ArrayList<DtoPariente>();
						//List<GesPariente> lstGesParientes = new ArrayList<GesPariente>();
						//lstGesParientes = beanLogin.getCredencial().getObjUsrSocio().getGesParientes();
						cargarListadoParientes(lstSesvasBeneficiarios, lstGesParientes);
						cargarRequisitos(idBeneficioSelected);
					}
				}//Verificar beneficio ***ANUAL***
				else if(sesvasBeneficio.getTipoCobertura().equalsIgnoreCase("ANUAL")) {
					BigDecimal valorMaximo = new BigDecimal(0);
					BigDecimal valorUtilizado = new BigDecimal(0);
					Date date = new Date();
					SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
					String periodo = getYearFormat.format(date);
					
					valorMaximo = sesvasBeneficio.getMontoMaximo();
					valorUtilizado = new BigDecimal(0);
					List<SesvasSolicitud> lstSolicitudes = new ArrayList<SesvasSolicitud>();
					System.out.println("Cedula:"+cedulaSocio);
					
					lstSolicitudes = managerSesvas.findSolicitudesByIdBenEstadoCedPeriodo(idBeneficioSelected,"APROBADO",cedulaSocio,periodo);
					for (SesvasSolicitud sesvasSolicitud : lstSolicitudes) {
						valorUtilizado=valorUtilizado.add(sesvasSolicitud.getValor()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
					}
					System.out.println("Valor Utilizado:"+valorUtilizado);
					if(valorMaximo.compareTo(valorUtilizado)==1) {
						List<SesvasBeneficiario> lstSesvasBeneficiarios;
						lstSesvasBeneficiarios = new ArrayList<SesvasBeneficiario>();
						lstSesvasBeneficiarios = sesvasBeneficio.getSesvasBeneficiarios();
						
						lstDtoparientes= new ArrayList<DtoPariente>();
						//List<GesPariente> lstGesParientes = new ArrayList<GesPariente>();
						//lstGesParientes = beanLogin.getCredencial().getObjUsrSocio().getGesParientes();
						cargarListadoParientes(lstSesvasBeneficiarios, lstGesParientes);
						cargarRequisitos(idBeneficioSelected);
					}
					else {
						JSFUtil.crearMensajeERROR("Ya registra solicitudes que cubren el monto anual asignado");
					}
				}//Verificar beneficio ***MORTUORIO***
				else if(sesvasBeneficio.getTipoCobertura().equalsIgnoreCase("MORTUORIO")) {
					List<SesvasBeneficiario> lstSesvasBeneficiarios;
					lstSesvasBeneficiarios = new ArrayList<SesvasBeneficiario>();
					lstSesvasBeneficiarios = sesvasBeneficio.getSesvasBeneficiarios();
					
					lstDtoparientes= new ArrayList<DtoPariente>();
					//List<GesPariente> lstGesParientes = new ArrayList<GesPariente>();
					//lstGesParientes = beanLogin.getCredencial().getObjUsrSocio().getGesParientes();
					cargarListadoParientes(lstSesvasBeneficiarios, lstGesParientes);
					cargarRequisitos(idBeneficioSelected);
				}
				
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo correctamente los requisitos");
				System.out.println("Error carga:"+e.getMessage());
			}
				
		}
	}
	//Carga de Requisitos
	public void cargarRequisitos(Long idBeneficio) throws Exception {
		lstAdjuntos=new ArrayList<DtoSesvasAdjunto>();
		lstSesvasRequisitos = managerSesvas.findByIdBeneficioListSesvasRequisitos(idBeneficioSelected);
		for (SesvasRequisito sesvasRequisito : lstSesvasRequisitos) {
			DtoSesvasAdjunto dtoSesvasAdjunto = new DtoSesvasAdjunto();
			dtoSesvasAdjunto.setSesvasRequisito(sesvasRequisito);
			lstAdjuntos.add(dtoSesvasAdjunto);
		}
		mostrarReq = true;
	}
	//CARGAR LISTA DE PARIENTES
	public void cargarListadoParientes(List<SesvasBeneficiario> lstSesvasBeneficiarios, List<GesPariente> lstGesParientes) {
		DtoPariente dtoPariente = new DtoPariente();
		for (SesvasBeneficiario sesvasBeneficiario : lstSesvasBeneficiarios) {
			if(sesvasBeneficiario.getUsrConsanguinidad()!=null) {
				for(GesPariente gesPariente:lstGesParientes) {
					if(sesvasBeneficiario.getUsrConsanguinidad().getIdConsanguinidad()==
						gesPariente.getUsrConsanguinidad().getIdConsanguinidad()) {
						dtoPariente.setCedula(gesPariente.getGesPersona().getCedula());
						dtoPariente.setGesPariente(gesPariente);
						dtoPariente.setNombres(gesPariente.getGesPersona().getApellidos()+" "+
												gesPariente.getGesPersona().getNombres());
						lstDtoparientes.add(dtoPariente);
						dtoPariente = new DtoPariente();
					}
				}
			}
			else {
				dtoPariente.setCedula(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
				dtoPariente.setNombres(beanLogin.getCredencial().getObjUsrSocio().getGesPersona().getApellidos()+" "+
									   beanLogin.getCredencial().getObjUsrSocio().getGesPersona().getNombres());
				dtoPariente.setGesPariente(null);
				lstDtoparientes.add(dtoPariente);
			}
			dtoPariente = new DtoPariente();
		}
	}
	public void generarPdf() {
		//String homeUsuario = System.getProperty("user.home");
		//String pathImages = homeUsuario + "/sigbecom/images/logo-comite.png";
		//String pathReportes = homeUsuario + "/sigbecom/reportes/sesvas/rpteSesvasSolicitud.jasper";
		String pathImages =  beanLogin.getPathImagesReportes()+ "logo-comite.png";
		String pathReportes = beanLogin.getPathReporte()+ "sesvas/rpteSesvasSolicitud.jasper";
		String contenido = "";
		String pariente = "N/A";
		String parienteNombres="N/A";
		String parienteCedula="N/A";
		Date fecha = new Date();
		String fechaDocumento= "Ibarra, "+ModelUtil.getDia(fecha)+" de "+
								ModelUtil.getMesAlfanumerico(fecha)+" de "+
								ModelUtil.getAnio(fecha);
		
		String nombresSocio=beanLogin.getCredencial().getObjUsrSocio().getGesPersona().getApellidos()+" "+
							beanLogin.getCredencial().getObjUsrSocio().getGesPersona().getNombres();
		String cedulaSocio= beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
		String telefonoSocio=beanLogin.getCredencial().getObjUsrSocio().getGesPersona().getTelefono();
		String idSocio = beanLogin.getCredencial().getObjUsrSocio().getIdSocio().toString();
		
		SesvasBeneficio sesvasBeneficio = new SesvasBeneficio();
		try {
			sesvasBeneficio = managerSesvas.findByIdBeneficio(idBeneficioSelected);
		
		if(cedulaBeneficiario.equalsIgnoreCase(cedulaSocio)) {
			contenido = "Yo "+nombresSocio+" con cédula Nro. "+
						cedulaSocio+" ; me permito dirigirme a usted"
						+ " para solicitarle muy comedidamente la ayuda económica que corresponde al Seguro de "
						+ "Salud, Vida y Asistencia Social SESVAS, para acceder al beneficio de "+sesvasBeneficio.getBeneficios()+" "
						+ sesvasBeneficio.getDetalle()+", en razón que actualmente me encuentro atravesando una "
						+ "difícil situación.";
		}
		else {
			contenido = "Yo "+beanLogin.getCredencial().getObjUsrSocio().getGesPersona().getApellidos()+" "+
					beanLogin.getCredencial().getObjUsrSocio().getGesPersona().getNombres()+" con cédula Nro. "+
					beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio()+" ; me permito dirigirme a usted"
							+ " para solicitarle muy comedidamente la ayuda económica que corresponde al Seguro de "
							+ "Salud, Vida y Asistencia Social SESVAS, para acceder al beneficio de "+sesvasBeneficio.getBeneficios()+" "
							+ sesvasBeneficio.getDetalle()+", en razón que actualmente me encuentro atravesando una "
							+ "difícil situación en mi familia.";
			System.out.println("cedulaBeneficiario:"+cedulaBeneficiario);
			GesPariente gesPariente = managerSesvas.findParienteByCedFamSocio(cedulaBeneficiario);
			
			//lstDtoparientes.stream().filter(p->p.getCedula().equalsIgnoreCase(cedulaBeneficiario)).findAny().orElse(null);
			
			System.out.println("Pariente:"+ gesPariente.getUsrConsanguinidad().getConsanguinidad());
			pariente = gesPariente.getUsrConsanguinidad().getConsanguinidad();
			//pariente = 
			parienteCedula = cedulaBeneficiario;
			parienteNombres = gesPariente.getGesPersona().getApellidos()+" "+gesPariente.getGesPersona().getNombres();
			
		}
			
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("logo", pathImages);// Parametrizar la ubicacion del logo
		parametros.put("contenido", contenido);
		parametros.put("pariente", pariente);
		parametros.put("parienteNombres",parienteNombres);
		parametros.put("parienteCedula",parienteCedula);
		parametros.put("nombresSocio",nombresSocio);
		parametros.put("cedulaSocio",cedulaSocio);
		parametros.put("telefonoSocio",telefonoSocio);
		parametros.put("idSocio",idSocio);
		parametros.put("fechaDocumento",fechaDocumento);
		JasperPrint jasperPrint = new JasperPrint();
		jasperPrint = JasperFillManager.fillReport(pathReportes, parametros, new JREmptyDataSource());
		//jasperPrint = JasperFillManager.fillReport(pathReportes, parametros);
		reportPdf = null;
		reportPdf = JasperExportManager.exportReportToPdf(jasperPrint);
		
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("frmSol");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@SuppressWarnings("static-access")
	public StreamedContent getReport() {
		if (reportPdf != null) {
			InputStream fis = new ByteArrayInputStream(reportPdf);
			//return new DefaultStreamedContent(fis, "application/pdf; charset=UTF-8", "FichaTecnica.pdf");
			return new DefaultStreamedContent().builder().contentType("application/pdf; charset=UTF-8").name("Solicitud_Sesvas.pdf").stream(()-> fis).build();
		}
		return null;
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
				String homeUsuario = System.getProperty("user.home");
				String cedula=beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
				String url = homeUsuario+"/reportes_sesvas/"+cedula+"/";
				//String url = "C:\\Archivos\\"+"1001962156"+"\\";
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
	
	public void cargarDocumento(String nombreArchivo,String cedula) {
		//String cedula=beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
		//String url = "C:/Archivos/"+cedula+"/"+nombreArchivo;
		String homeUsuario = System.getProperty("user.home");
		String url = homeUsuario+"/reportes_sesvas/"+cedula+"/"+nombreArchivo;
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
			 try {
				String tipo="image/"+extension;
				System.out.println("contentType: "+tipo);
				StreamedContent imagen = new DefaultStreamedContent().builder().contentType(tipo).name(nombreArchivoTmp).stream(()-> fis).build();
				if(imagen!=null) {
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("frmIMAGE");
					return imagen;
				}
			 }catch (Exception e) {
                    e.printStackTrace();
                    return null;
             }
		}
		else {
			return null;
		}
		return null;
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

	public List<DtoPariente> getLstDtoparientes() {
		return lstDtoparientes;
	}

	public void setLstDtoparientes(List<DtoPariente> lstDtoparientes) {
		this.lstDtoparientes = lstDtoparientes;
	}

	public String getCedulaBeneficiario() {
		return cedulaBeneficiario;
	}

	public void setCedulaBeneficiario(String cedulaBeneficiario) {
		this.cedulaBeneficiario = cedulaBeneficiario;
	}
}
