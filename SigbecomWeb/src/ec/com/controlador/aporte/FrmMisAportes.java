package ec.com.controlador.aporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.aporte.ManagerAporte;
import ec.com.model.dao.entity.AporteCliente;
import ec.com.model.dao.entity.AporteCuenta;
import ec.com.model.dao.entity.AporteDescuento;

@Named("frmMisAportes")
@SessionScoped
public class FrmMisAportes implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerAporte managerAporte;
	
	private List<AporteDescuento> lstAporteDescuento;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		
		lstAporteDescuento = new ArrayList<AporteDescuento>();
		cargarListaAporteCuentas();
	}
	public void cargarListaAporteCuentas() {
		try {
			lstAporteDescuento = managerAporte.findAllAporteDescuentosByCedulaSocio(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}
	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public List<AporteDescuento> getLstAporteDescuento() {
		return lstAporteDescuento;
	}
	public void setLstAporteDescuento(List<AporteDescuento> lstAporteDescuento) {
		this.lstAporteDescuento = lstAporteDescuento;
	}
	
}
