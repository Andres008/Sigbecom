package ec.com.controlador.planesMoviles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanCostosAdm;
import ec.com.model.dao.entity.PlanEquipo;
import ec.com.model.dao.entity.PlanOperadora;
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
	private List<UsrSocio> lstUsersSocios;
	private List<UsrSocio> lstUsrNoSocios;
	private List<PlanOperadora> lstPlanOperadoras;
	
	private String cedulaSocio;
	
	private PlanContratoComite planContratoComite;
	
	private Long idOperadora;
	private Long idPlanMovil;
	private Long idEquipo;
	
	private boolean pnlEquipo;
	
	private boolean panelCliente;
	private boolean panelEquipo;
	
	@PostConstruct
	public void init() {
		lstUsrSocio = new ArrayList<UsrSocio>();
		lstPlanMovils = new ArrayList<PlanPlanMovil>();
		lstEquipos = new ArrayList<PlanEquipo>();
		lstUsersSocios = new ArrayList<UsrSocio>();
		lstUsrNoSocios = new ArrayList<UsrSocio>();
		lstPlanOperadoras = new ArrayList<PlanOperadora>();
		//panelgrid
		panelCliente = false;
		panelEquipo = false;

		cedulaSocio = "";
		
		//usrSocio = new UsrSocio();
		idOperadora = new Long(0);
		idPlanMovil = new Long(0);
		idEquipo = new Long(0);
		planContratoComite = new PlanContratoComite();
		cargarListaOperadoras();
		cargarListaUsuarios();
		cargarUsuariosSocios();
		cargarUsuariosNoSocios();
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
	
	public void cargarUsuariosSocios() {
		try {
			lstUsersSocios = managerPlanesMoviles.findAllUsuariosSocios();
			lstUsersSocios = lstUsersSocios.stream().filter(p->p.getPlanContratoComites().size()>0).collect(Collectors.toList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void cargarUsuariosNoSocios() {
		try {
			lstUsrNoSocios = managerPlanesMoviles.findAllUsuariosSocios();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaOperadoras() {
		try {
			lstPlanOperadoras = managerPlanesMoviles.findAllOperadoras();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarPlanesAndEquiposMoviles() {
		if(idOperadora!=0) {
			planContratoComite.setPlanPlanMovil(null);
			cargarListaPlanesMoviles();
		}
		else {
			init();
		}
				
	}
	private void cargarListaPlanesMoviles() {
		try {
			lstPlanMovils = managerPlanesMoviles.findAllPlanesMovilesByIdOperadora(idOperadora);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	

	public void cargarContrato() {
		System.out.println("PASO1: "+cedulaSocio);
		if(cedulaSocio!=null && !cedulaSocio.isEmpty()) {
			String tipoUsr= "";
			UsrSocio usrSocio = new UsrSocio();
			try {
				
				usrSocio = managerPlanesMoviles.findUsrSocioByCedulaSocio(cedulaSocio);
				tipoUsr = usrSocio.getUsrTipoSocio().getNombre();
				try {
					PlanCostosAdm planCostosAdm = managerPlanesMoviles.findPlanCostosAdmBytipoUsr(tipoUsr);
					planContratoComite.setPlanCostosAdm(planCostosAdm);
					planContratoComite.setUsrSocio(usrSocio);
					panelCliente = true;
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR("No tiene configurado Valores Administrativos Planes Moviles para el tipo Usuario: "+tipoUsr);
					e.printStackTrace();
					// TODO: handle exception
				}
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No tiene configurado el Tipo Socio");
				e.printStackTrace();
			}
			
				
		}
		else {
			JSFUtil.crearMensajeERROR("Seleccione Cliente requerido");
		}
	}

	public void cargarPlanMovil() {
		System.out.println("PASO3: "+ idPlanMovil);
		if(idPlanMovil!=0) {
			try {
				PlanPlanMovil planMovil = managerPlanesMoviles.findPlanMovilByid(idPlanMovil);
				planContratoComite.setValorPlan(planMovil.getPrecio());
				planContratoComite.setPlanPlanMovil(planMovil);
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
				planContratoComite.setEstado("ACTIVO");
				managerPlanesMoviles.insertarPlanContratoComite(planContratoComite);
				JSFUtil.crearMensajeINFO("Plan cargado correctamente");
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
	public void onRowEdit(RowEditEvent<Object> event) {
		 try {
			 	managerPlanesMoviles.actualizarObjeto(event.getObject());
				JSFUtil.crearMensajeINFO("Se actualizó correctamente.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
	}
	
	public BigDecimal sumaCostosAdm(PlanCostosAdm planCostosAdm) {
		BigDecimal total = new BigDecimal(0);
		if(planCostosAdm!=null) {
			total = planCostosAdm.getAdministracion().add(planCostosAdm.getCargo()).add(planCostosAdm.getCostoLinea());
		}
		return total;
	}
	
	
	public void onRowCancel(RowEditEvent<Object> event) {
	       JSFUtil.crearMensajeINFO("Se canceló actualización.");
	}
	//GETTERS AND SETTERS
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
	
	public List<UsrSocio> getLstUsersSocios() {
		return lstUsersSocios;
	}

	public void setLstUsersSocios(List<UsrSocio> lstUsersSocios) {
		this.lstUsersSocios = lstUsersSocios;
	}

	public List<UsrSocio> getLstUsrNoSocios() {
		return lstUsrNoSocios;
	}

	public void setLstUsrNoSocios(List<UsrSocio> lstUsrNoSocios) {
		this.lstUsrNoSocios = lstUsrNoSocios;
	}

	public boolean isPnlEquipo() {
		return pnlEquipo;
	}

	public void setPnlEquipo(boolean pnlEquipo) {
		this.pnlEquipo = pnlEquipo;
	}

	public List<PlanOperadora> getLstPlanOperadoras() {
		return lstPlanOperadoras;
	}

	public void setLstPlanOperadoras(List<PlanOperadora> lstPlanOperadoras) {
		this.lstPlanOperadoras = lstPlanOperadoras;
	}

	public Long getIdOperadora() {
		return idOperadora;
	}

	public void setIdOperadora(Long idOperadora) {
		this.idOperadora = idOperadora;
	}

}
