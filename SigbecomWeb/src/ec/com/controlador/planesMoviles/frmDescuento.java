package ec.com.controlador.planesMoviles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.PlanAmortEquipmov;
import ec.com.model.dao.entity.PlanPago;
import ec.com.model.dao.entity.PlanRegistroPago;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmDescuento")
@SessionScoped
public class frmDescuento implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<PlanPago> lstPlanPago;
	private List<PlanRegistroPago> lstRegistroPago;
	private List<PlanAmortEquipmov> lstAmortizacion;

	@PostConstruct
	public void init() {
		lstPlanPago = new ArrayList<PlanPago>();
		lstRegistroPago = new ArrayList<PlanRegistroPago>();
		lstAmortizacion = new ArrayList<PlanAmortEquipmov>();
		cargarPlanesMovilesDescuentos();
	}
	
	public void cargarPlanesMovilesDescuentos() {
		try {
			lstPlanPago = managerPlanesMoviles.findAllPlanPago();
			lstRegistroPago = managerPlanesMoviles.findAllPlanRegistroPago();
			lstAmortizacion = managerPlanesMoviles.findAllPlanAmortEquipmov();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
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
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<PlanPago> getLstPlanPago() {
		return lstPlanPago;
	}

	public void setLstPlanPago(List<PlanPago> lstPlanPago) {
		this.lstPlanPago = lstPlanPago;
	}

	public List<PlanRegistroPago> getLstRegistroPago() {
		return lstRegistroPago;
	}

	public void setLstRegistroPago(List<PlanRegistroPago> lstRegistroPago) {
		this.lstRegistroPago = lstRegistroPago;
	}

	public List<PlanAmortEquipmov> getLstAmortizacion() {
		return lstAmortizacion;
	}

	public void setLstAmortizacion(List<PlanAmortEquipmov> lstAmortizacion) {
		this.lstAmortizacion = lstAmortizacion;
	}
	
}
