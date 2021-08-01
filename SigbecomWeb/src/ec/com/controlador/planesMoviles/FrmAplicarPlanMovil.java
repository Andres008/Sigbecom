package ec.com.controlador.planesMoviles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanEquipo;
import ec.com.model.dao.entity.PlanPlanMovil;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmAplicarPlanMovil")
@SessionScoped
public class FrmAplicarPlanMovil implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<UsrSocio> lstUsrSocio;
	private List<PlanPlanMovil> lstPlanMovils;
	private List<PlanEquipo> lstEquipos;
	private List<PlanContratoComite> lstPlanContratoComites;
	
	private String cedulaSocio;
	
	private PlanContratoComite planContratoComite;
	
	private Long idPlanMovil;
	private Long idEquipo;
	
	private boolean panelCliente;
	private boolean panelEquipo;
	private boolean panelPlan;
	
	@PostConstruct
	public void init() {
		lstUsrSocio = new ArrayList<UsrSocio>();
		lstPlanMovils = new ArrayList<PlanPlanMovil>();
		lstEquipos = new ArrayList<PlanEquipo>();
		lstPlanContratoComites = new ArrayList<PlanContratoComite>();
		//panelgrid
		panelCliente = false;
		panelEquipo = false;
		panelPlan = false;
		cedulaSocio = "";
		//usrSocio = new UsrSocio();
		idPlanMovil = new Long(0);
		idEquipo = new Long(0);
		planContratoComite = new PlanContratoComite();
		cargarListaUsuarios();
		cargarListaEquiposMoviles();
		cargarListaPlanesMoviles();
		cargarUsuariosConPlanes();
	}
	
	public void cargarListaUsuarios() {
		try {
			lstUsrSocio = managerPlanesMoviles.findAllUsuarios();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void cargarUsuariosConPlanes() {
		try {
			lstPlanContratoComites = managerPlanesMoviles.findAllUsuariosConPlanes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void cargarListaPlanesMoviles() {
		try {
			lstPlanMovils = managerPlanesMoviles.findAllPlanesMoviles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void cargarListaEquiposMoviles() {
		try {
			lstEquipos = managerPlanesMoviles.findAllEquipos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarContrato() {
		System.out.println("PASO1: "+cedulaSocio);
		if(!cedulaSocio.isEmpty()) {
			
			try {
				UsrSocio usrSocio = managerPlanesMoviles.findUsrSocioByCedulaSocio(cedulaSocio);
				planContratoComite.setUsrSocio(usrSocio);
				panelCliente = true;
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se ingreso el usuario requerido");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Seleccione Cliente requerido");
		}
	}
	
	public void cargarEquipoMovil() {
		System.out.println("PASO2:" + idEquipo);
		if(idEquipo!=0) {
			
			try {
				PlanEquipo planEquipo = managerPlanesMoviles.findEquipoMovilByIdEquipo(idEquipo);
				planContratoComite.setPlanEquipo(planEquipo);
				planContratoComite.setValorEquipo(planEquipo.getPrecioRef());
				planContratoComite.setInteresEquipo(planEquipo.getInteres());
				panelEquipo = true;
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo el equipo movil requerido");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Seleccione Equipo Móvil requerido");
		}
		
	}
	public void cargarPlanMovil() {
		System.out.println("PASO3: "+ idPlanMovil);
		if(idPlanMovil!=0) {
			try {
				PlanPlanMovil planMovil = managerPlanesMoviles.findPlanMovilByid(idPlanMovil);
				planContratoComite.setValorPlanMovil(planMovil.getPrecio());
				planContratoComite.setInteresPlanMovil(planMovil.getInteres());
				planContratoComite.setPlanPlanMovil(planMovil);
				panelPlan = true;
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo el Plan Móvil requerido");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Seleccione Plan Móvil requerido");
		}
	}
	public void registrarPlanMovil() {
		if(planContratoComite.getUsrSocio()!=null && planContratoComite.getPlanPlanMovil()!=null && 
		   planContratoComite.getLineaTelefono()!=null && planContratoComite.getFechaContrato()!=null) {
			try {
				managerPlanesMoviles.insertarPlanContratoComite(planContratoComite);
				JSFUtil.crearMensajeINFO("Plan Movil Registrado correctamente");
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("Seleccione Plan Móvil requerido");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Datos requerido, Seleccione Plan Móvil");
		}
	} 
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<UsrSocio> getLstUsrSocio() {
		return lstUsrSocio;
	}

	public void setLstUsrSocio(List<UsrSocio> lstUsrSocio) {
		this.lstUsrSocio = lstUsrSocio;
	}

	public String getCedulaSocio() {
		return cedulaSocio;
	}

	public void setCedulaSocio(String cedulaSocio) {
		this.cedulaSocio = cedulaSocio;
	}

	public PlanContratoComite getPlanContratoComite() {
		return planContratoComite;
	}

	public void setPlanContratoComite(PlanContratoComite planContratoComite) {
		this.planContratoComite = planContratoComite;
	}

	public Long getIdPlanMovil() {
		return idPlanMovil;
	}

	public void setIdPlanMovil(Long idPlanMovil) {
		this.idPlanMovil = idPlanMovil;
	}

	public Long getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public List<PlanPlanMovil> getLstPlanMovils() {
		return lstPlanMovils;
	}

	public void setLstPlanMovils(List<PlanPlanMovil> lstPlanMovils) {
		this.lstPlanMovils = lstPlanMovils;
	}

	public List<PlanEquipo> getLstEquipos() {
		return lstEquipos;
	}

	public void setLstEquipos(List<PlanEquipo> lstEquipos) {
		this.lstEquipos = lstEquipos;
	}

	public boolean isPanelCliente() {
		return panelCliente;
	}

	public void setPanelCliente(boolean panelCliente) {
		this.panelCliente = panelCliente;
	}

	public boolean isPanelEquipo() {
		return panelEquipo;
	}

	public void setPanelEquipo(boolean panelEquipo) {
		this.panelEquipo = panelEquipo;
	}

	public boolean isPanelPlan() {
		return panelPlan;
	}

	public void setPanelPlan(boolean panelPlan) {
		this.panelPlan = panelPlan;
	}

	public List<PlanContratoComite> getLstPlanContratoComites() {
		return lstPlanContratoComites;
	}

	public void setLstPlanContratoComites(List<PlanContratoComite> lstPlanContratoComites) {
		this.lstPlanContratoComites = lstPlanContratoComites;
	}
	
}
