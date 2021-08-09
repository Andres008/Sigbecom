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
import ec.com.model.dao.entity.PlanPago;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmMiPlanMovil")
@SessionScoped
public class frmMiPlanMovil implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<PlanPago> lstPlanPago;

	@PostConstruct
	public void init() {
		lstPlanPago = new ArrayList<PlanPago>();
		cargarMisPlanesMoviles();
	}
	
	public void cargarMisPlanesMoviles() {
		try {
			lstPlanPago = managerPlanesMoviles.findAllPlanPago();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
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
	
}
