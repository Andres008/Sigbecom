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
import javax.servlet.http.HttpServletResponse;

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
	private Long idContrato;
	private PlanRegistroPago planRegistroPago;
	
	private UploadedFile file;
	private byte[] archivo;
	private List<PlanRegistroPago> lstPlanRegistroPago;
	private List<PlanContratoComite> lstPlanContratoComite;
	
	private int filaTmp;
	
	@PostConstruct
	public void init() {
		anio = ModelUtil.getAnio(new Date());
		mes = new Integer(0);
		file = null;
		cargarRegistroPagos();
		filaTmp = 0;
		lstPlanContratoComite = new ArrayList<PlanContratoComite>();
		cargarContratosComiteActivos();
	}
	public void cargarContratosComiteActivos() {
		try {
			lstPlanContratoComite = managerPlanesMoviles.findAllPlanContratoComiteActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado de numeros correctamente");
			e.printStackTrace();
		}
	}
	public void cargarRegistroPagos() {
		try {
			lstPlanRegistroPago = managerPlanesMoviles.findAllPlanRegistroPagoByEstado("GENERADO");
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
	public void eliminarRegistro(PlanRegistroPago planRegistroPago) {
		try {
			managerPlanesMoviles.removePlanRegistro(planRegistroPago.getIdRegistroPagos());
			JSFUtil.crearMensajeINFO("Registro eliminado correctamente");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form2");
			//HttpServletResponse response ;
			//response.setHeader("Refresh", "0; URL=http://your-current-page");
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("El Registro no se elimino correctamente");
			e.printStackTrace();
		}
		
	}
	public void cargarFormulario() {
		anio = ModelUtil.getAnio(new Date());
		planRegistroPago = new PlanRegistroPago();
		planRegistroPago.setAnio(anio);
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("frmReg");
		prime.executeScript("PF('dlgReg').show();");
	}
	public void registroIndividual() {
		PlanContratoComite planContratoComite = new PlanContratoComite();
		try {
			planContratoComite = lstPlanContratoComite.stream().filter(p->p.getIdContrato()==idContrato).findAny().orElse(null);
			BigDecimal costoAdm = planContratoComite.getPlanCostosAdm().getCargo().add(planContratoComite.getPlanCostosAdm().getCostoLinea()).add(planContratoComite.getPlanCostosAdm().getAdministracion());
			Date fechaIngreso = new Date();
			planRegistroPago.setCostoAdm(costoAdm);
			planRegistroPago.setEstado("GENERADO");
			planRegistroPago.setFechaIngreso(fechaIngreso);
			planRegistroPago.setLineaTelefono(planContratoComite.getLineaTelefono());
			planRegistroPago.setNombreRef(planContratoComite.getUsrSocio().getGesPersona().getApellidos()+" "+
										  planContratoComite.getUsrSocio().getGesPersona().getNombres());
			planRegistroPago.setPlanContratoComite(planContratoComite);
			planRegistroPago.setTotal(planRegistroPago.getValorPlan().add(planRegistroPago.getCostoAdm()));
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("El usuario no tiene relacionado los costos administrativos: "+planContratoComite.getLineaTelefono()+" con cedula: "+planContratoComite.getUsrSocio().getCedulaSocio());
			e.printStackTrace();
		}
		try {
			managerPlanesMoviles.insertarPlanRegistroPagos(planRegistroPago);
			init();
			idContrato = null;
			planRegistroPago = new PlanRegistroPago();
			JSFUtil.crearMensajeINFO("Registro realizado correctamente");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");
			prime.ajax().update("form2");
			prime.executeScript("PF('dlgReg').hide();");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se regsitro correctamente");
			e.printStackTrace();
		}
	}
	public void registrarPlanillas(){
		System.out.println("Archivo: "+file.getFileName());
		Date fechaIngreso = new Date();
		InputStream fis = new ByteArrayInputStream(archivo);
		int fila=0;
		try {
			List<PlanRegistroPago> lstPlanRegistroPagos = new ArrayList<PlanRegistroPago>();
			OPCPackage pkg = OPCPackage.open(fis);
			//InputStream pkg = event.getFile().getInputStream();
			//InputStream pkg = TikaInputStream.get(event.getFile().getInputStream()); //event.getFile().getInputStream();
			XSSFWorkbook wb = new XSSFWorkbook(pkg);
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getLastRowNum();
			//int rows = sheet.get
			//AtecSectorizacion atecSectorizacion = new AtecSectorizacion();
			PlanRegistroPago planRegistroPago = new PlanRegistroPago();
	        for (int i = 2; i <= rows-1; ++i) {
	            
	        	
	        	XSSFRow row = sheet.getRow(i);
	            fila=i;//
	            System.out.println("FILA: "+fila);
	            XSSFCell lineaTelefono = 	row.getCell(0);//
	            XSSFCell nombreRef = 	row.getCell(1);//
	            XSSFCell valorPlan = row.getCell(2);//
	            
	            System.out.println("Telefono: "+lineaTelefono.getRawValue());
	            System.out.println("nombre Ref: "+nombreRef.getStringCellValue());
	            System.out.println("Valor Plan: "+valorPlan.getRawValue());
	            System.out.println("--------------------------------------------------");
	            
	            if((lineaTelefono!=null && lineaTelefono.getRawValue()!=null && !lineaTelefono.getRawValue().isEmpty() && 
	            	!lineaTelefono.getRawValue().toString().equalsIgnoreCase("null")) &&
	               (nombreRef!=null && nombreRef.getRawValue()!=null && !nombreRef.getRawValue().isEmpty() && 
	            	!nombreRef.getRawValue().toString().equalsIgnoreCase("null")) &&
	               (valorPlan!=null && valorPlan.getRawValue()!=null && !valorPlan.getRawValue().isEmpty() && 
           			!valorPlan.getRawValue().toString().equalsIgnoreCase("null"))) {
	            	 //System.out.println("Dentro");
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
		            	
		            	lstPlanRegistroPagos.add(planRegistroPago);
		            	planRegistroPago = new PlanRegistroPago();
	            	}
	            	else if(planContratoComite==null) {
	            		filaTmp = i+1;
	            		JSFUtil.crearMensajeERROR("No se encuentra registrado el numero: "+lineaTelefono.getRawValue()+" en la fila: "+filaTmp);
	            		lstPlanRegistroPagos=null;
	            		init();
	            		PrimeFaces prime=PrimeFaces.current();
	     	           	prime.ajax().update("form1");
	     	           	prime.ajax().update("form2");
	     	           	break;
	            	}
	           }
	           planRegistroPago = new PlanRegistroPago();
	        }
	        wb.close();
	        if(lstPlanRegistroPagos!=null) {
		        System.out.println("lista: "+lstPlanRegistroPagos.size());
		        for (PlanRegistroPago planRegistroPago2 : lstPlanRegistroPagos) {
		        	managerPlanesMoviles.insertarPlanRegistroPagos(planRegistroPago2);
				}
		        JSFUtil.crearMensajeINFO("Planilla cargada correctamente");
		        init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
	        }
		} catch (Exception e) {
			fila++;
			JSFUtil.crearMensajeERROR("Se produjo un error en la carga del archivo, revisa en la fila:"+fila+" valores, espacios");
			System.out.println(e.getCause());
			e.printStackTrace();
		}
		//return "frmPlanillaPagoPlan.xhtml?faces-redirect=true";
	}
	
	public void onRowEdit(RowEditEvent<Object> event) {
		 try {
			 PlanRegistroPago planRegistroPago = (PlanRegistroPago) event.getObject();
			 BigDecimal costoAdic = new BigDecimal(0);
			 if(planRegistroPago.getValorAdicional()!=null) {
				 costoAdic=planRegistroPago.getValorAdicional();
			 }
			 BigDecimal total = new BigDecimal(0);
			 BigDecimal valorPlan=planRegistroPago.getValorPlan();
			 BigDecimal costoAdmi=planRegistroPago.getCostoAdm();
			  
			 total=total.add(valorPlan).add(costoAdmi).add(costoAdic);
			 //System.out.println("total:"+planRegistroPago.getValorAdicional());
			 planRegistroPago.setTotal(total);
			 
			 //System.out.println("total:"+planRegistroPago.getTotal());
			 managerPlanesMoviles.actualizarObjeto(planRegistroPago);
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
		if(estado.equalsIgnoreCase("GENERADO")) {
			return true;
		}
		else
			return false;
	}
	public void generarPlanillaMes() {
		
		try {
			List<String> lstCedulasContratoPlanActivos = managerPlanesMoviles.findAllCedulasContratoPlanActivosRenovados();
			System.out.println("Tamaño ContratoComite: "+lstCedulasContratoPlanActivos.size());
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
			init();
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");
			prime.ajax().update("form2");
			
		} catch (Exception e) {
			 JSFUtil.crearMensajeERROR("No se cargo el descuento a roll correctamente.");
			e.printStackTrace();
		}
		
		
	}
	public void borrarGenerados() {
		try {
			List<PlanRegistroPago> lstPlanRegistroPago=managerPlanesMoviles.findAllPlanRegistroPagoByEstado("GENERADO");
			for (PlanRegistroPago planRegistroPago : lstPlanRegistroPago) {
				managerPlanesMoviles.removePlanRegistro(planRegistroPago.getIdRegistroPagos());
			}
			init();
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");
			prime.ajax().update("form2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public List<PlanContratoComite> getLstPlanContratoComite() {
		return lstPlanContratoComite;
	}
	public void setLstPlanContratoComite(List<PlanContratoComite> lstPlanContratoComite) {
		this.lstPlanContratoComite = lstPlanContratoComite;
	}
	public PlanRegistroPago getPlanRegistroPago() {
		return planRegistroPago;
	}
	public void setPlanRegistroPago(PlanRegistroPago planRegistroPago) {
		this.planRegistroPago = planRegistroPago;
	}
	
}
