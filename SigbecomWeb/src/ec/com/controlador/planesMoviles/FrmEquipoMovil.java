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
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanEquipo;
import ec.com.model.modulos.util.JSFUtil;
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
	
	@PostConstruct
	public void init() {
		lstPlanContratoComite = new ArrayList<PlanContratoComite>();
		lstPlanEquipo = new ArrayList<PlanEquipo>();
		idContratoComite = new Long(0);
		idEquipoMovil = new Long(0);
		planAmortEquipmov = new PlanAmortEquipmov();
		//cargarEquiposMoviles();
		cargarContratosComite();
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
				Long idPlanEmpresa = planContratoComite.getPlanPlanMovil().getPlanOperadora().getIdPlanEmpresa();
				System.out.println("id :"+ idPlanEmpresa);
				planAmortEquipmov.setPlanContratoComite(planContratoComite);
				lstPlanEquipo = managerPlanesMoviles.findPlanEquipoByIdOperadora(idPlanEmpresa);
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
	
}
