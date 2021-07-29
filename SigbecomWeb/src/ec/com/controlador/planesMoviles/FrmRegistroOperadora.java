package ec.com.controlador.planesMoviles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.ConvEmpresa;
import ec.com.model.dao.entity.PlanContacto;
import ec.com.model.dao.entity.PlanOperadora;
import ec.com.model.dao.entity.PlanPlanMovil;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmRegistroOperadora")
@SessionScoped
public class FrmRegistroOperadora implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	
	private PlanOperadora planOperadora;
	private List<PlanOperadora> lstPlanOperadora;
	
	private PlanContacto planContacto;
	private PlanPlanMovil planPlanMovil;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		planOperadora = new PlanOperadora();
		lstPlanOperadora = new ArrayList<PlanOperadora>();
		planContacto = new PlanContacto();
		planPlanMovil = new PlanPlanMovil();
		cargarListaEmpresas();
	}
	

	public void registrarEmpresa() {
		System.out.println("Regsitro:"+planOperadora.getEmpresa());
		if(!planOperadora.getEmpresa().isEmpty()&&!planOperadora.getDireccion().isEmpty()){
			try {
				managerPlanesMoviles.insertarPlanOperadora(planOperadora);
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
	
	public void cargarListaEmpresas() {
		try {
			lstPlanOperadora = managerPlanesMoviles.findAllEmpresas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
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
	
	public void cargarEmpresa(PlanOperadora planOperadora) {
		planContacto.setPlanOperadora(planOperadora);
	}
	
	public void registrarContacto() {
		System.out.println("Regsitro Contacto:"+planContacto.getApellidos());
		if(!planContacto.getNombres().isEmpty()&&!planContacto.getApellidos().isEmpty()&&!planContacto.getCargo().isEmpty()&&
		   !planContacto.getCelular().isEmpty()&&!planContacto.getEmail().isEmpty()){
			try {
				managerPlanesMoviles.insertarPlanContacto(planContacto);
				JSFUtil.crearMensajeINFO("Contacto Registrado correctamente");
				init();
				cargarListaEmpresas();
				PrimeFaces prime=PrimeFaces.current();
				prime.executeScript("PF('dlgReg').hide();");
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
		}
	}
	public void cargarPlanPlanMovil(PlanOperadora planOperadora) {
		planPlanMovil.setPlanOperadora(planOperadora);
	}
	public void registrarPlanMovil() {
		System.out.println("RegsitroServicio:"+planPlanMovil.getPlan());
		if(!planPlanMovil.getDetalle().isEmpty()&&!planPlanMovil.getPlan().isEmpty()){
			try {
				//planPlanMovil.se("ACTIVO");
				BigDecimal precio= planPlanMovil.getPrecio().setScale(2, RoundingMode.HALF_EVEN);
				BigDecimal interes= planPlanMovil.getInteres().setScale(2, RoundingMode.HALF_EVEN);
				planPlanMovil.setPrecio(precio);
				planPlanMovil.setInteres(interes);
				managerPlanesMoviles.insertarPlanPlanMovil(planPlanMovil);
				JSFUtil.crearMensajeINFO("Plan Móvil Registrado correctamente");
				init();
				cargarListaEmpresas();
				PrimeFaces prime=PrimeFaces.current();
				prime.executeScript("PF('dlgSer').hide();");
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
		}
	}
	
	public PlanOperadora getPlanOperadora() {
		return planOperadora;
	}

	public void setPlanOperadora(PlanOperadora planOperadora) {
		this.planOperadora = planOperadora;
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}


	public List<PlanOperadora> getLstPlanOperadora() {
		return lstPlanOperadora;
	}


	public void setLstPlanOperadora(List<PlanOperadora> lstPlanOperadora) {
		this.lstPlanOperadora = lstPlanOperadora;
	}


	public PlanContacto getPlanContacto() {
		return planContacto;
	}


	public void setPlanContacto(PlanContacto planContacto) {
		this.planContacto = planContacto;
	}


	public PlanPlanMovil getPlanPlanMovil() {
		return planPlanMovil;
	}


	public void setPlanPlanMovil(PlanPlanMovil planPlanMovil) {
		this.planPlanMovil = planPlanMovil;
	}
	
}
