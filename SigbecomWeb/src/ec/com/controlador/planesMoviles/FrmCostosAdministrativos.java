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
import ec.com.model.dao.entity.PlanCostosAdm;
import ec.com.model.dao.entity.UsrTipoSocio;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmCostosAdministrativos")
@SessionScoped
public class FrmCostosAdministrativos implements Serializable{
private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	
	@Inject
	private BeanLogin beanLogin;
	
	private PlanCostosAdm planCostosAdm;
	
	private List<UsrTipoSocio> lstTipoSocio;
	private List<PlanCostosAdm> lstPlanCostosAdm;
	private Long idTipoSocio;
	
	@PostConstruct
	public void init() {
		lstTipoSocio = new ArrayList<UsrTipoSocio>();
		lstPlanCostosAdm = new ArrayList<PlanCostosAdm>();
		idTipoSocio= new Long(0);
		planCostosAdm = new PlanCostosAdm();
		//planCostosAdm.setCargo(new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN));
		//planCostosAdm.setAdministracion(new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN));
		//planCostosAdm.setCostoLinea(new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN));
		
		cargarListaTipoSocio();
		cargarListaPlanCostosAdm();
	}
	
	public void cargarListaTipoSocio() {
		try {
			lstTipoSocio = managerPlanesMoviles.findAllUsrTipoSocioEstadoActivo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void cargarListaPlanCostosAdm() {
		try {
			lstPlanCostosAdm = managerPlanesMoviles.findAllPlanCostosAdm();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void registrarValoresAdm() {
		System.out.println("Regsitro:"+planCostosAdm.getCargo());
		if(planCostosAdm.getCargo()!=null && planCostosAdm.getAdministracion()!= null && 
		   planCostosAdm.getCostoLinea()!=null && idTipoSocio!=0){
			try {
				UsrTipoSocio usrTipoSocio = managerPlanesMoviles.findByIdTipoSocio(idTipoSocio);
				planCostosAdm.setUsrTipoSocio(usrTipoSocio);
				planCostosAdm.setEstado("ACTIVO");
				managerPlanesMoviles.insertarPlanCostosAdm(planCostosAdm);
				JSFUtil.crearMensajeINFO("Operadora Móvil Registrado correctamente");
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
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
	/**
	 * GETTERS AN SETTERS
	 * 
	 */

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public PlanCostosAdm getPlanCostosAdm() {
		return planCostosAdm;
	}

	public void setPlanCostosAdm(PlanCostosAdm planCostosAdm) {
		this.planCostosAdm = planCostosAdm;
	}

	public List<UsrTipoSocio> getLstTipoSocio() {
		return lstTipoSocio;
	}

	public void setLstTipoSocio(List<UsrTipoSocio> lstTipoSocio) {
		this.lstTipoSocio = lstTipoSocio;
	}

	public Long getIdTipoSocio() {
		return idTipoSocio;
	}

	public void setIdTipoSocio(Long idTipoSocio) {
		this.idTipoSocio = idTipoSocio;
	}

	public List<PlanCostosAdm> getLstPlanCostosAdm() {
		return lstPlanCostosAdm;
	}

	public void setLstPlanCostosAdm(List<PlanCostosAdm> lstPlanCostosAdm) {
		this.lstPlanCostosAdm = lstPlanCostosAdm;
	}
	
}
