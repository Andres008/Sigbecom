package ec.com.controlador.aporte;

import java.io.Serializable;
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
import ec.com.model.aporte.ManagerAporte;
import ec.com.model.dao.entity.AporteCuenta;
import ec.com.model.modulos.util.JSFUtil;

@Named("frmCuenta")
@SessionScoped
public class FrmCuenta implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerAporte managerAporte;
	
	private AporteCuenta aporteCuenta;
	private List<AporteCuenta> lstAporteCuentas;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		aporteCuenta = new AporteCuenta();
		lstAporteCuentas = new ArrayList<AporteCuenta>();
		cargarListaAporteCuentas();
	}
	public void registrarCuenta() {
		if(aporteCuenta.getCuenta()!=null && aporteCuenta.getDetalle()!=null && aporteCuenta.getValor()!=null) {
			try {
				managerAporte.insertarAportCuenta(aporteCuenta);
				JSFUtil.crearMensajeINFO("Cuenta Registrada correctamente");
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
	public void cargarListaAporteCuentas() {
		try {
			lstAporteCuentas = managerAporte.findAllAporteCuenta();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void onRowEdit(RowEditEvent<Object> event) {
		 try {
			 	managerAporte.actualizarObjeto(event.getObject());
				JSFUtil.crearMensajeINFO("Se actualizó correctamente.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
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

	public AporteCuenta getAporteCuenta() {
		return aporteCuenta;
	}

	public void setAporteCuenta(AporteCuenta aporteCuenta) {
		this.aporteCuenta = aporteCuenta;
	}
	public List<AporteCuenta> getLstAporteCuentas() {
		return lstAporteCuentas;
	}
	public void setLstAporteCuentas(List<AporteCuenta> lstAporteCuentas) {
		this.lstAporteCuentas = lstAporteCuentas;
	}
	
}
