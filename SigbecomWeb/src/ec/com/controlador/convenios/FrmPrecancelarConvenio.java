package ec.com.controlador.convenios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.ConvAmortizacion;
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
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		lstUsrSocio = new ArrayList<UsrSocio>();
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
	}
	public void cargarListaConvenios() {
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		try {
			List<ConvAdquirido> lstConvAdquiridoTmp = managerConvenios.findConvAdquiridoByCedula(cedula);
			for (ConvAdquirido convAdquirido : lstConvAdquiridoTmp) {
				boolean hayDeuda = false;
				for (ConvAmortizacion convAmortizacion : convAdquirido.getConvAmortizacions()) {
					if(convAmortizacion.getDescEstadoDescuento().getEstado().equalsIgnoreCase("DESCONTADA")) {
						hayDeuda = true;
					}
				}
				if(hayDeuda=true) {
					lstConvAdquirido.add(convAdquirido);
				}
			}
			
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente los convenios");
			e.printStackTrace();
		}
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
	
}
