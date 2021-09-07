package ec.com.controlador.planesMoviles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.dao.entity.PlanAmortEquipmov;
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanPago;
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
	private Integer mes;
	private String nombreArch;
	
	private UploadedFile file;
	private byte[] archivo;
	private List<PlanRegistroPago> lstPlanRegistroPago;
	
	@PostConstruct
	public void init() {
		anio = ModelUtil.getAnio(new Date());
		mes = new Integer(0);
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
	
	public String convertirMes(int mes) {
		String mesAlfanumerico="";
		switch (mes) {
		case 1:
			mesAlfanumerico = "ENERO";
			break;
		case 2:
			mesAlfanumerico = "FEBRERO";
			break;
		case 3:
			mesAlfanumerico = "MARZO";
			break;
		case 4:
			mesAlfanumerico = "ABRIL";
			break;
		case 5:
			mesAlfanumerico = "MAYO";
			break;
		case 6:
			mesAlfanumerico = "JUNIO";
			break;
		case 7:
			mesAlfanumerico = "JULIO";
			break;
		case 8:
			mesAlfanumerico = "AGOSTO";
			break;
		case 9:
			mesAlfanumerico = "SEPTIEMBRE";
			break;
		case 10:
			mesAlfanumerico = "OCTUBRE";
			break;
		case 11:
			mesAlfanumerico = "NOVIEMBRE";
			break;
		case 12:
			mesAlfanumerico = "DICIEMBRE";
			break;
		default:
			break;
		}
		return mesAlfanumerico;
	}
	
	public void cargarMes() {
		System.out.println("Mes: "+mes);
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
	            	//Obtener datos del contrato
	            	PlanContratoComite planContratoComite = managerPlanesMoviles.findContratoComite("0"+lineaTelefono.getRawValue());
	            	if(planContratoComite!=null) {
		            	planRegistroPago.setPlanContratoComite(planContratoComite);
		            	planRegistroPago.setLineaTelefono("0"+lineaTelefono.getRawValue());
		            	planRegistroPago.setNombreRef(nombreRef.getStringCellValue());
		            	planRegistroPago.setValorPlan(new BigDecimal(valorPlan.getRawValue().toString()));
		            	planRegistroPago.setAnio(anio);
		            	planRegistroPago.setMes(mes);
		            	BigDecimal costoAdm = planContratoComite.getPlanCostosAdm().getCargo().add(planContratoComite.getPlanCostosAdm().getCostoLinea()).add(planContratoComite.getPlanCostosAdm().getAdministracion());
		            	planRegistroPago.setCostoAdm(costoAdm);
		            	//BigDecimal total = planRegistroPago.getValorPlan().add(planContratoComite.getCostoAdministrativo());
		            	planRegistroPago.setTotal(planRegistroPago.getValorPlan().add(planRegistroPago.getCostoAdm()));
		            	planRegistroPago.setEstado("GENERADO");
		            	planRegistroPago.setFechaIngreso(fechaIngreso);
		            	
		            	managerPlanesMoviles.insertarPlanRegistroPagos(planRegistroPago);
	            	}
	            	else {
	            		//
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
	public void onRowEdit(RowEditEvent<Object> event) {
		 try {
			 managerPlanesMoviles.actualizarObjeto(event.getObject());
				JSFUtil.crearMensajeINFO("Se actualizó correctamente.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
	}
	public void onRowCancel(RowEditEvent<Object> event) {
      JSFUtil.crearMensajeINFO("Se canceló actualización.");
  }
	public boolean activarEditEstado(String estado) {
		if(estado.equalsIgnoreCase("DESCUENTO A ROLL")) {
			return true;
		}
		else
			return false;
	}
	public void generarPlanillaMes() {
		
		try {
			List<String> lstCedulasContratoPlanActivos = managerPlanesMoviles.findAllCedulasContratoPlanActivosRenovados();
			System.out.println("Tamaño: "+lstCedulasContratoPlanActivos.size());
			PlanPago planPago = new PlanPago();
			
			if(lstCedulasContratoPlanActivos!=null && lstCedulasContratoPlanActivos.size()>0) {
				
				
				DescEstadoDescuento descEstadoDescuento = managerPlanesMoviles.findWhereEstadoDescEstadoDescuento("VIGENTE");
				for (String cedula : lstCedulasContratoPlanActivos) {
					// System.out.println("Cedula: "+cedula);
					BigDecimal totalDescuento= new BigDecimal(0);
					planPago = new PlanPago();
					List<PlanRegistroPago> lstPlanRegistroPagos = new ArrayList<PlanRegistroPago>();
					lstPlanRegistroPagos = managerPlanesMoviles.findRegistroPagoGeneradosByCedula(cedula);
					
					if(lstPlanRegistroPagos!=null) {
						System.out.println("listaPagos: "+lstPlanRegistroPagos.size());
						Integer mesPlanilla=0;
						//List<PlanAmortEquipmov> lstAmortEquipmovs = new ArrayList<PlanAmortEquipmov>(); 
						for (PlanRegistroPago planRegistroPago : lstPlanRegistroPagos) {
							mesPlanilla = planRegistroPago.getMes();
							totalDescuento = totalDescuento.add(planRegistroPago.getTotal());
							planPago.setAno(planRegistroPago.getAnio());
							planPago.setMes(planRegistroPago.getMes());
							planPago.setPlanContratoComite(planRegistroPago.getPlanContratoComite());
//							lstPlanAmortEquipmovs = managerPlanesMoviles.findAmortEquipmovByPlanContratoMes(planRegistroPago.getLineaTelefono(), mes);
//							if(lstPlanAmortEquipmovs!=null && lstPlanAmortEquipmovs.size()>0) {
//								System.out.println("Equipo Movil 1");
//								PlanAmortEquipmov planAmortEquipmov = lstPlanAmortEquipmovs.get(0); 
//								totalDescuento = totalDescuento.add(planAmortEquipmov.getValorCuota());
//								lstAmortEquipmovs.add(planAmortEquipmov);
//							}
						}
						List<PlanAmortEquipmov> lstPlanAmortEquipmovs = new ArrayList<PlanAmortEquipmov>();
						lstPlanAmortEquipmovs = managerPlanesMoviles.findAllAmortEquipmovByCedulaMes(cedula,mesPlanilla);
						if(lstPlanAmortEquipmovs!=null && lstPlanAmortEquipmovs.size()>0) {
							System.out.println("Equipo Movil 1");
							for (PlanAmortEquipmov planAmortEquipmov : lstPlanAmortEquipmovs) {
								totalDescuento = totalDescuento.add(planAmortEquipmov.getValorCuota());
							}
						}
						
						planPago.setValorTotal(totalDescuento);
						planPago.setDescEstadoDescuento(descEstadoDescuento);
						managerPlanesMoviles.insertarPlanPago(planPago);
						
						if(lstPlanAmortEquipmovs!=null && lstPlanAmortEquipmovs.size()>0) {
							for(PlanAmortEquipmov planAmortEquipmov: lstPlanAmortEquipmovs) {
								System.out.println("Equipo Movil 2");							
								planAmortEquipmov.setEstado("DESCUENTO A ROLL");
								planAmortEquipmov.setPlanPago(planPago);
								managerPlanesMoviles.actualizarObjeto(planAmortEquipmov);
							}
						}
						
						for (PlanRegistroPago planRegistroPago : lstPlanRegistroPagos) {
							planRegistroPago.setEstado("DESCUENTO A ROLL");
							planRegistroPago.setPlanPago(planPago);
							managerPlanesMoviles.actualizarObjeto(planRegistroPago);
						}
						
					}
				}
			}
			JSFUtil.crearMensajeINFO("Descuento a roll cargado correctamente ");
		} catch (Exception e) {
			 JSFUtil.crearMensajeERROR("No se cargo el descuento a roll correctamente.");
			e.printStackTrace();
		}
		
		
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

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
}
