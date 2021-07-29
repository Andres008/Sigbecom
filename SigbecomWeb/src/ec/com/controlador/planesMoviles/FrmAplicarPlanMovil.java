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
import ec.com.model.dao.entity.PlanContratoComite;
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
	private String cedulaSocio;
	//private UsrSocio usrSocio;
	private PlanContratoComite planContratoComite;
	
	@PostConstruct
	public void init() {
		lstUsrSocio = new ArrayList<UsrSocio>();
		cedulaSocio = "";
		//usrSocio = new UsrSocio();
		planContratoComite = new PlanContratoComite();
		cargarListaUsuarios();
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
	public void cargarContrato() {
		planContratoComite = new PlanContratoComite();
		System.out.println("PASO1");
		if(!cedulaSocio.isEmpty()) {
			try {
				UsrSocio usrSocio = managerPlanesMoviles.findUsrSocioByCedulaSocio(cedulaSocio);
				planContratoComite.setUsrSocio(usrSocio);
				
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se entro el usuario requerido");
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
	
}
