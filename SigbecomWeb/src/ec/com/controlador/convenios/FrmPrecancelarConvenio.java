package ec.com.controlador.convenios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.ConvAmortizacion;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.dao.entity.PlanAmortEquipmov;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.planesMoviles.ManagerPlanesMoviles;

@Named("frmPrecancelarConvenio")
@SessionScoped
public class FrmPrecancelarConvenio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerPlanesMoviles managerPlanesMoviles;
	@EJB
	private ManagerConvenios managerConvenios;
	private String cedula;
	private List<UsrSocio> lstUsrSocio;
	private List<ConvAdquirido> lstConvAdquirido;
	private List<ConvAmortizacion> lstConvAmortizacion;
	private Integer periodo;
	private Integer mes;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		lstUsrSocio = new ArrayList<UsrSocio>();
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		cedula="";
		cargarListaUsuarios();
		
		
	}
	public FrmPrecancelarConvenio() {
		super();
	}
	public void cargarListaUsuarios() {
		try {
			lstUsrSocio = managerPlanesMoviles.findAllUsuarios();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}//1003688452
	public void cargarListaConvenios() {
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		try {
			List<ConvAdquirido> lstConvAdquiridoTmp = managerConvenios.findConvAdquiridoByCedula(cedula);
			for (ConvAdquirido convAdquirido : lstConvAdquiridoTmp) {
				boolean hayDeuda = false;
				for (ConvAmortizacion convAmortizacion : convAdquirido.getConvAmortizacions()) {
					if(!convAmortizacion.getDescEstadoDescuento().getEstado().equalsIgnoreCase("DESCONTADA")) {
						hayDeuda = true;
					}
				}
				if(hayDeuda=true) {
					lstConvAdquirido.add(convAdquirido);
				}
			}
			
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente los convenios, debe seleccionar un cliente para consultar");
			e.printStackTrace();
		}
	}
	public void precancelarConvAmortizacionPendientes(List<ConvAmortizacion> lstConvAmortizacion) {
		DescEstadoDescuento descEstadoDescuento = new DescEstadoDescuento();
		try {
			descEstadoDescuento = managerConvenios.findWhereEstadoDescEstadoDescuento("PRECANCELADO");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo estado descuento correctamente");
			e.printStackTrace();
		}
		try {
			for (ConvAmortizacion convAmortizacion : lstConvAmortizacion) {
				if(!convAmortizacion.getDescEstadoDescuento().getEstado().equalsIgnoreCase("DESCONTADA")) {
					convAmortizacion.setDescEstadoDescuento(descEstadoDescuento);
					convAmortizacion.setFechaPrecancelacion(new Date());
					managerConvenios.actualizarObjeto(convAmortizacion);
				}
			}
			JSFUtil.crearMensajeINFO("Precancelación de convenios realizado correctamente");
			init();
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");
			prime.ajax().update("form2");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se actualizó el descuento correctamente");
			e.printStackTrace();
		}
	}
	
	public String estadoPago(String estado) {
		if(estado.equalsIgnoreCase("INGRESADA") || estado.equalsIgnoreCase("VIGENTE")) {
			return "PAGO PENDIENTE";
		}
		else
			return estado;
	}
	
	public void cargarConsulta() {
		lstConvAmortizacion = new ArrayList<ConvAmortizacion>();
		if(periodo!=null && mes !=null) {
			try {
				lstConvAmortizacion= managerConvenios.findAllConvAmortizacionByMesAnio(periodo, mes);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo correctamente el listado solicitado");
				e.printStackTrace();
			}
		}
		else if(periodo!=null && mes==null) {
			try {
				lstConvAmortizacion = managerConvenios.findAllConvAmortizacionByPeriodo(periodo);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo correctamente el listado solicitado");
				e.printStackTrace();
			}
		}
		
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("form3");
		prime.ajax().update("form4");
	}
//GETTERS AND SETTERS
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
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
	public List<ConvAdquirido> getLstConvAdquirido() {
		return lstConvAdquirido;
	}
	public void setLstConvAdquirido(List<ConvAdquirido> lstConvAdquirido) {
		this.lstConvAdquirido = lstConvAdquirido;
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
	public List<ConvAmortizacion> getLstConvAmortizacion() {
		return lstConvAmortizacion;
	}
	public void setLstConvAmortizacion(List<ConvAmortizacion> lstConvAmortizacion) {
		this.lstConvAmortizacion = lstConvAmortizacion;
	}
	
}
