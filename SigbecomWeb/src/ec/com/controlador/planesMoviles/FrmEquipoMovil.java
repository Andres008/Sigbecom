package ec.com.controlador.planesMoviles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.PlanAmortEquipmov;
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanEquipo;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmEquipoMovil")
@SessionScoped
public class FrmEquipoMovil implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<PlanContratoComite> lstPlanContratoComite;
	private List<PlanEquipo> lstPlanEquipo;
	
	private Long idContratoComite;
	private Long idEquipoMovil;
	
	private PlanAmortEquipmov planAmortEquipmov;
	
	private int anio;
	private int mes;
	
	@PostConstruct
	public void init() {
		lstPlanContratoComite = new ArrayList<PlanContratoComite>();
		lstPlanEquipo = new ArrayList<PlanEquipo>();
		idContratoComite = new Long(0);
		idEquipoMovil = new Long(0);
		planAmortEquipmov = new PlanAmortEquipmov();
		anio = ModelUtil.getAnio(new Date());
		mes = ModelUtil.getMes(new Date());
		//cargarEquiposMoviles();
		cargarContratosComite();
	}
	public void cargarListSinEquiposContratosComite() {
		try {
			lstPlanContratoComite = managerPlanesMoviles.findAllPlanContratoComite();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarContratosComite() {
		try {
			lstPlanContratoComite = managerPlanesMoviles.findAllPlanContratoComite();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarEquiposMoviles() {
		if(idContratoComite!=null) {
			try {
				
				PlanContratoComite planContratoComite = managerPlanesMoviles.findByIdContratoComite(idContratoComite);
				//Verificar si ya tiene un equipo pendiente de pago
				List<PlanAmortEquipmov> lstPlanAmortEquipmovs = managerPlanesMoviles.findPlanAmortEquipmovByLineaTelef(planContratoComite.getLineaTelefono());
				if(lstPlanAmortEquipmovs==null) {
					Long idPlanEmpresa = planContratoComite.getPlanPlanMovil().getPlanOperadora().getIdPlanEmpresa();
					System.out.println("id :"+ idPlanEmpresa);
					planAmortEquipmov.setPlanContratoComite(planContratoComite);
					planAmortEquipmov.setValorCapital(null);
					lstPlanEquipo = managerPlanesMoviles.findPlanEquipoByIdOperadora(idPlanEmpresa);
				}
				else {
					JSFUtil.crearMensajeERROR("La cuenta tiene valores pedientes de pago de un Equipo Movil");
				}
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
				e.printStackTrace();
			}
		}
	}
	
	public void cargarPlanEquipo() {
		if(idEquipoMovil!=null) {
			try {
				PlanEquipo planEquipo = managerPlanesMoviles.findEquipoMovilByIdEquipo(idEquipoMovil);
				planAmortEquipmov.setValorCapital(planEquipo.getPrecio());
				planAmortEquipmov.setPlanEquipo(planEquipo);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
				e.printStackTrace();
			} 
		}
	}
	
	public void calcularTotalEquipoMovil() {
		if(idContratoComite!= 0 && idEquipoMovil !=0){
			//System.out.println("Capital: "+ planAmortEquipmov.getValorCapital());
			//System.out.println("Comision: "+ planAmortEquipmov.getComision());
			BigDecimal total = planAmortEquipmov.getValorCapital().add(planAmortEquipmov.getComision());
			planAmortEquipmov.setTotal(total);
		}
		else {
			planAmortEquipmov.setTotal(null);
		}
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("form1");
	}
	
	public void generarTablaAmortizacion() {
		//int anio = ModelUtil.getAnio(new Date());
		//int mes = ModelUtil.getMes(new Date());
		if(mes==12) {
			anio++;
			mes=1;
		}
		else {
			mes++;
		}
		
		//System.out.println("Fecha Suma: "+ModelUtil.getSumarMeses(fecha, 1));
		
		if(planAmortEquipmov.getComision()!=null && planAmortEquipmov.getMesesPlazo()!=null && 
		   idContratoComite!= 0 && idEquipoMovil !=0) {
			
			BigDecimal sumValorCuota = new BigDecimal(0);
			PlanAmortEquipmov amortEquipmov;
			BigDecimal saldo = new BigDecimal(0);
			for(int i=1; i<=planAmortEquipmov.getMesesPlazo().intValue();i++) {
				
				amortEquipmov = new PlanAmortEquipmov();
				amortEquipmov.setPlanEquipo(planAmortEquipmov.getPlanEquipo());
				amortEquipmov.setValorCapital(new BigDecimal(0));
				amortEquipmov.setComision(new BigDecimal(0));
				amortEquipmov.setTotal(new BigDecimal(0));
				amortEquipmov.setMesesPlazo(planAmortEquipmov.getMesesPlazo());
				amortEquipmov.setNumCuota(i);
				amortEquipmov.setEstado("GENERADO");//cuando pasa a cobros
				amortEquipmov.setPlanContratoComite(planAmortEquipmov.getPlanContratoComite());
				amortEquipmov.setMes(mes);
				amortEquipmov.setAnio(anio);
				
				
				if(i==1) {
					saldo = planAmortEquipmov.getTotal();
					amortEquipmov.setValorCapital(planAmortEquipmov.getValorCapital());
					amortEquipmov.setComision(planAmortEquipmov.getComision());
					amortEquipmov.setTotal(planAmortEquipmov.getTotal());
				}
				
				BigDecimal valorCuota = planAmortEquipmov.getTotal().divide(planAmortEquipmov.getMesesPlazo(),2, RoundingMode.HALF_EVEN);
				
				
				amortEquipmov.setValorCuota(valorCuota);
				
				
				sumValorCuota = sumValorCuota.add(valorCuota);
				System.out.println("sumValorCuota: "+sumValorCuota);
				System.out.println("total: "+sumValorCuota);
				if(i==planAmortEquipmov.getMesesPlazo().intValue()) {
					if(sumValorCuota.compareTo(planAmortEquipmov.getTotal())==1) {
						BigDecimal tmp = sumValorCuota.subtract(planAmortEquipmov.getTotal());
						amortEquipmov.setValorCuota(valorCuota.subtract(tmp));
						valorCuota = valorCuota.subtract(tmp);
						System.out.println("DATO 1");
					}
					else if(sumValorCuota.compareTo(planAmortEquipmov.getTotal())==-1) {
						BigDecimal tmp = planAmortEquipmov.getTotal().subtract(sumValorCuota);
						amortEquipmov.setValorCuota(valorCuota.add(tmp));
						valorCuota = valorCuota.add(tmp);
						System.out.println("DATO 2");
					}
				}
				saldo = saldo.subtract(valorCuota);
				amortEquipmov.setSaldo(saldo);
				try {
					managerPlanesMoviles.insertarPlanAmortEquipmov(amortEquipmov);
					
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
					e.printStackTrace();
				}
				if(mes==12) {
					anio++;
					mes=1;
				}
				else {
					mes++;
				}
			}
			init();
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");
			prime.ajax().update("form2");
			JSFUtil.crearMensajeINFO("Información generada correctamente");
		}
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
	public boolean activarEditEstado(String estado) {
		if(estado.equalsIgnoreCase("GENERADO")) {
			return true;
		}
		else
			return false;
	}
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public List<PlanContratoComite> getLstPlanContratoComite() {
		return lstPlanContratoComite;
	}
	public void setLstPlanContratoComite(List<PlanContratoComite> lstPlanContratoComite) {
		this.lstPlanContratoComite = lstPlanContratoComite;
	}
	public List<PlanEquipo> getLstPlanEquipo() {
		return lstPlanEquipo;
	}
	public void setLstPlanEquipo(List<PlanEquipo> lstPlanEquipo) {
		this.lstPlanEquipo = lstPlanEquipo;
	}
	public Long getIdContratoComite() {
		return idContratoComite;
	}
	public void setIdContratoComite(Long idContratoComite) {
		this.idContratoComite = idContratoComite;
	}
	public Long getIdEquipoMovil() {
		return idEquipoMovil;
	}
	public void setIdEquipoMovil(Long idEquipoMovil) {
		this.idEquipoMovil = idEquipoMovil;
	}
	public PlanAmortEquipmov getPlanAmortEquipmov() {
		return planAmortEquipmov;
	}
	public void setPlanAmortEquipmov(PlanAmortEquipmov planAmortEquipmov) {
		this.planAmortEquipmov = planAmortEquipmov;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	
}
