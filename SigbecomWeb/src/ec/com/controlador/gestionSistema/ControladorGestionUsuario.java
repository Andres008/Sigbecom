package ec.com.controlador.gestionSistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.GesEstadoCivil;
import ec.com.model.dao.entity.GesEtnia;
import ec.com.model.dao.entity.GesGenero;
import ec.com.model.dao.entity.GesTipoSangre;
import ec.com.model.dao.entity.UsrConsanguinidad;
import ec.com.model.gestionPersonas.ManagerGestionPersonas;
import ec.com.model.modulos.util.JSFUtil;

@Named("controladorGestionUsuario")
@SessionScoped
public class ControladorGestionUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private BeanLogin beanLogin;
	
	@EJB
	private ManagerGestionPersonas managerGestionPersonas;
	
	public ControladorGestionUsuario() {
		// TODO Auto-generated constructor stub
	}
	
	public List<SelectItem> lstSiEstadoCivil(){
		List<SelectItem> lstSiEstadoCivil= new ArrayList<SelectItem>();
		try {
			for (GesEstadoCivil autEstadoCivil : managerGestionPersonas.buscarEstadosCiviles()) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(autEstadoCivil.getEstadoCivil());
				siCivil.setValue(autEstadoCivil.getIdEstadoCivil());
				lstSiEstadoCivil.add(siCivil);
			}
			return lstSiEstadoCivil;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<SelectItem> lstSiGenero(){
		List<SelectItem> lstSiGenero= new ArrayList<SelectItem>();
		try {
			for (GesGenero autGenero : managerGestionPersonas.buscarGenero()) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(autGenero.getGenero());
				siCivil.setValue(autGenero.getIdGenero());
				lstSiGenero.add(siCivil);
			}
			return lstSiGenero;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<SelectItem> lstSiTipoSangre(){
		List<SelectItem> lstSiEtnia= new ArrayList<SelectItem>();
		try {
			for (GesTipoSangre autEtnia : managerGestionPersonas.buscarTipoSangre()) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(autEtnia.getTipoSangre());
				siCivil.setValue(autEtnia.getIdTipoSangre());
				lstSiEtnia.add(siCivil);
			}
			return lstSiEtnia;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<SelectItem> lstSiConsanguinidad(){
		List<SelectItem> lstSiConsanguinidad= new ArrayList<SelectItem>();
		try {
			for (UsrConsanguinidad usrConsanguinidad : managerGestionPersonas.buscarUsrConsanguinidad()) {
				SelectItem siConsanguinidad = new SelectItem();
				siConsanguinidad.setLabel(usrConsanguinidad.getConsanguinidad());
				siConsanguinidad.setValue(usrConsanguinidad.getIdConsanguinidad());
				lstSiConsanguinidad.add(siConsanguinidad);
			}
			return lstSiConsanguinidad;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public List<SelectItem> lstSiEtnia(){
		List<SelectItem> lstSiEtnia= new ArrayList<SelectItem>();
		try {
			for (GesEtnia autEtnia : managerGestionPersonas.buscarEtnia()) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(autEtnia.getEtnia());
				siCivil.setValue(autEtnia.getIdEtnia());
				lstSiEtnia.add(siCivil);
			}
			return lstSiEtnia;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Getter y Setters
	 * @return
	 */
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	
	

}
