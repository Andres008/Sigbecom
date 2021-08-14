package ec.com.controlador.planesMoviles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanRegistroPago;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmPlanillaPagoPlan")
@SessionScoped
public class FrmPlanillaPagoPlan implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	
	@Inject
	private BeanLogin beanLogin;
	
	private int anio;
	private String mes;
	private String nombreArch;
	
	private UploadedFile file;
	private byte[] archivo;
	private List<PlanRegistroPago> lstPlanRegistroPago;
	
	@PostConstruct
	public void init() {
		anio = ModelUtil.getAnio(new Date());
		mes = "";
		file = null;
		cargarRegistroPagos();
	}
	
	public void cargarRegistroPagos() {
		try {
			lstPlanRegistroPago = managerPlanesMoviles.findAllPlanRegistroPago();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo correctamente el listado");
			e.printStackTrace();
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
        System.out.println("Archivo subido: "+ event.getFile().getFileName());
        this.file = event.getFile();
        archivo = file.getContent();
        nombreArch = event.getFile().getFileName();
        JSFUtil.crearMensajeINFO("Documento cargado correctamente");
    }

	public void registrarPlanillas(){
		
		System.out.println("Archivo: "+file.getFileName());
		Date fechaIngreso = new Date();
		InputStream fis = new ByteArrayInputStream(archivo);
		try {
			OPCPackage pkg = OPCPackage.open(fis);
			//InputStream pkg = event.getFile().getInputStream();
			//InputStream pkg = TikaInputStream.get(event.getFile().getInputStream()); //event.getFile().getInputStream();
			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getLastRowNum();
			//AtecSectorizacion atecSectorizacion = new AtecSectorizacion();
			PlanRegistroPago planRegistroPago = new PlanRegistroPago();
	        for (int i = 2; i <= rows; ++i) {
	            
	        	XSSFRow row = sheet.getRow(i);
	            
	            XSSFCell lineaTelefono = 	row.getCell(0);//
	            XSSFCell nombreRef = 	row.getCell(1);//
	            XSSFCell valorPlan = row.getCell(2);//
	            System.out.println("Telefono: "+lineaTelefono.getRawValue());
	            System.out.println("nombre Ref: "+nombreRef.getStringCellValue());
	            System.out.println("Valor Plan: "+valorPlan.getRawValue());
	            if((lineaTelefono!=null && lineaTelefono.getRawValue()!=null && !lineaTelefono.getRawValue().isEmpty() && 
	            	!lineaTelefono.getRawValue().toString().equalsIgnoreCase("null")) &&
	               (nombreRef!=null && nombreRef.getRawValue()!=null && !nombreRef.getRawValue().isEmpty() && 
	            	!nombreRef.getRawValue().toString().equalsIgnoreCase("null")) &&
	               (valorPlan!=null && valorPlan.getRawValue()!=null && !valorPlan.getRawValue().isEmpty() && 
           			!valorPlan.getRawValue().toString().equalsIgnoreCase("null"))) {
	            	 System.out.println("Dentro");
	            	PlanContratoComite planContratoComite = managerPlanesMoviles.findContratoComite("0"+lineaTelefono.getRawValue());
	            	if(planContratoComite!=null) {
		            	planRegistroPago.setPlanContratoComite(planContratoComite);
		            	planRegistroPago.setLineaTelefono("0"+lineaTelefono.getRawValue());
		            	planRegistroPago.setNombreRef(nombreRef.getStringCellValue());
		            	planRegistroPago.setValorPlan(new BigDecimal(valorPlan.getRawValue().toString()));
		            	planRegistroPago.setAnio(anio);
		            	planRegistroPago.setMes(Integer.parseInt(mes));
		            	planRegistroPago.setCostoAdm(planContratoComite.getCostoAdministrativo());
		            	//BigDecimal total = planRegistroPago.getValorPlan().add(planContratoComite.getCostoAdministrativo());
		            	planRegistroPago.setTotal(planRegistroPago.getValorPlan().add(planRegistroPago.getCostoAdm()));
		            	planRegistroPago.setEstado("GENERADO");
		            	planRegistroPago.setFechaIngreso(fechaIngreso);
		            	
		            	managerPlanesMoviles.insertarPlanRegistroPagos(planRegistroPago);
	            	}
	            	
	            }
	            
	            planRegistroPago = new PlanRegistroPago();
	        }
	        wb.close();
	        JSFUtil.crearMensajeINFO("Registro de Plantilla Finalizado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se registró correctamente");
			e.printStackTrace();
		}
		init();
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("form1");
		prime.ajax().update("form2");
	}
	
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
	
	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getNombreArch() {
		return nombreArch;
	}

	public void setNombreArch(String nombreArch) {
		this.nombreArch = nombreArch;
	}

	public List<PlanRegistroPago> getLstPlanRegistroPago() {
		return lstPlanRegistroPago;
	}

	public void setLstPlanRegistroPago(List<PlanRegistroPago> lstPlanRegistroPago) {
		this.lstPlanRegistroPago = lstPlanRegistroPago;
	}
	
}
