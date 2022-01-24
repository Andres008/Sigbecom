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
import ec.com.model.dao.entity.PlanAmortEquipmov;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmPrecancelar")
@SessionScoped
public class FrmPrecancelar implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;

	public FrmPrecancelar() {
		super();
	}
	private List<PlanAmortEquipmov> lstCuotasPendientes;
	private List<PlanAmortEquipmov> lstAllAmortEquipmov;
	private String cedula;
	private List<UsrSocio> lstUsrSocio;
	private Integer periodo;
	private Integer mes;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		lstUsrSocio = new ArrayList<UsrSocio>();
		cargarListaUsuarios();
	}
	public void inicializar() {
		lstCuotasPendientes = new ArrayList<PlanAmortEquipmov>();
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
	public void cargarAllAmortEquipmov() {
		try {
			lstAllAmortEquipmov = managerPlanesMoviles.findAllAmortEquipmovByPeriodoMes(periodo, mes);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo correctamente la lista");
			e.printStackTrace();
		}
	}
	public void cargarListaCuotasPendientes() {
		if(cedula!=null && !cedula.isEmpty()) {
			lstCuotasPendientes = new ArrayList<PlanAmortEquipmov>();
			try {
				lstCuotasPendientes = managerPlanesMoviles.findWhereCuotasPendientes(cedula);
				JSFUtil.crearMensajeINFO("Listado cargado correctamente");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo correctamente el listado.");
				e.printStackTrace();
			}
		}
		
	}
	public void precancelar() {
		if(lstCuotasPendientes!=null && lstCuotasPendientes.size()>0) {
			try {
				for (PlanAmortEquipmov planAmortEquipmov : lstCuotasPendientes) {
					planAmortEquipmov.setEstado("PRECANCELADO");
					
						managerPlanesMoviles.actualizarObjeto(planAmortEquipmov);
					
				}
				init();
				inicializar();
				JSFUtil.crearMensajeINFO("Cuotas precanceladas correctamente");
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("No ha seleecionado un usuario con cuotas pendientes");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");
			prime.ajax().update("form2");
		}
	}
	public void cargarConsulta() {
		lstAllAmortEquipmov = new ArrayList<PlanAmortEquipmov>();
		if(periodo!=null && mes !=null) {
			try {
				lstAllAmortEquipmov= managerPlanesMoviles.findAllAmortEquipmovByPeriodoMes(periodo, mes);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo correctamente el listado solicitado");
				e.printStackTrace();
			}
		}
		else if(periodo!=null && mes==null) {
			try {
				lstAllAmortEquipmov= managerPlanesMoviles.findAllAmortEquipmovByPeriodo(periodo);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo correctamente el listado solicitado");
				e.printStackTrace();
			}
		}
		
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("form2");
		prime.ajax().update("form3");
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public List<PlanAmortEquipmov> getLstCuotasPendientes() {
		return lstCuotasPendientes;
	}
	public void setLstCuotasPendientes(List<PlanAmortEquipmov> lstCuotasPendientes) {
		this.lstCuotasPendientes = lstCuotasPendientes;
	}
	public List<UsrSocio> getLstUsrSocio() {
		return lstUsrSocio;
	}
	public void setLstUsrSocio(List<UsrSocio> lstUsrSocio) {
		this.lstUsrSocio = lstUsrSocio;
	}
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}
	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public List<PlanAmortEquipmov> getLstAllAmortEquipmov() {
		return lstAllAmortEquipmov;
	}
	public void setLstAllAmortEquipmov(List<PlanAmortEquipmov> lstAllAmortEquipmov) {
		this.lstAllAmortEquipmov = lstAllAmortEquipmov;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	
}
