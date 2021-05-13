package ec.com.controlador.gestionSistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.AutMenu;
import ec.com.model.dao.entity.AutPerfile;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.dao.entity.AutRolPerfil;
import ec.com.model.dao.entity.UsrEstadoSocio;
import ec.com.model.gestionSistema.ManagerGestionSistema;
import ec.com.model.modulos.util.JSFUtil;

@Named("controladorMenuPerfiles")
@SessionScoped
public class ControladorMenuPerfiles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private BeanLogin beanLogin;

	@EJB
	ManagerGestionSistema managerGestionSistema;

	@EJB
	ManagerLog managerLog;

	private AutMenu objAutMenu;

	private List<AutMenu> lstAutMenu;

	private AutPerfile objPerfil;

	private AutRolPerfil objAutRolPerfil;

	public ControladorMenuPerfiles() {
		// TODO Auto-generated constructor stub
	}

	/***
	 * iNICIALIZAR TODAS LAS VARIABLES Y OBJETOS A SER UTILIZADOS EN MENU ROL
	 */
	public void inicializarMenuPerfil() {
		objAutMenu = new AutMenu();
		objAutMenu.setAutPerfiles(new ArrayList<AutPerfile>());
		objPerfil = new AutPerfile();
		objAutRolPerfil = new AutRolPerfil();
		objAutRolPerfil.setAutPerfile(new AutPerfile());
		objAutRolPerfil.setAutRol(new AutRol());
		try {
			lstAutMenu = managerGestionSistema.buscarTodosAutMenu();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * iNGRESA UN NUEVO Menu perfil
	 */
	public void ingresarMenuPerfil() {
		objAutMenu.setNombre(objAutMenu.getNombre().toUpperCase());
		objAutMenu.setObservacion(objAutMenu.getObservacion().toUpperCase());
		objAutMenu.setEstado("A");
		try {
			managerGestionSistema.insertarAutMenu(objAutMenu);
			JSFUtil.crearMensajeINFO("Menu ingresado Correctamente.");
			inicializarMenuPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * Cargar un objeto Menu para ser utilizado en un nuevo perfil
	 * 
	 * @param auxAutMenu
	 */
	public void cargarMenuPerfil(AutMenu auxAutMenu) {
		objPerfil = new AutPerfile();
		objPerfil.setAutMenu(auxAutMenu);
	}

	/**
	 * Metodo que cagar el perfil
	 * 
	 * @param auxAutPerfile
	 */
	public void cargarPerfil(AutPerfile auxAutPerfile) {
		objAutRolPerfil = new AutRolPerfil();
		objAutRolPerfil.setAutRol(new AutRol());
		objAutRolPerfil.setAutPerfile(auxAutPerfile);
	}
	
	public void eliminarRolPerfil(AutRolPerfil autRolPerfil) {
		try {
			managerGestionSistema.eliminarAutRolPerfil(autRolPerfil);
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "eliminarRolPerfil", "Se elimino Rol perfil.");
			inicializarMenuPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/****
	 * Metodo para ingresar nuevo AutPerfil
	 */
	public void ingresarAutPerfil() {
		objPerfil.setNombre(objPerfil.getNombre().toUpperCase());
		objPerfil.setEstado("A");
		try {
			managerGestionSistema.ingresarAutPerfil(objPerfil);
			JSFUtil.crearMensajeINFO("Se ingreso correctamente.");
			inicializarMenuPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/****
	 * Metodo para ingresar nuevo AutPerfil
	 */
	public void ingresarAutRolPerfil() {
		objAutRolPerfil.setEstado("A");
		objAutRolPerfil.setFechaInicial(new Date());
		
		try {
			managerGestionSistema.ingresarAutRolPerfil(objAutRolPerfil);
			JSFUtil.crearMensajeINFO("Se ingreso correctamente.");
			inicializarMenuPerfil();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * Metodo que retorna una lista SI de AutRol
	 * 
	 * @return
	 */
	public List<SelectItem> siAutRol() {
		List<SelectItem> lstSiAutRol = new ArrayList<SelectItem>();
		try {

			for (AutRol autRol : managerGestionSistema.buscarTodosAutRol()) {
				SelectItem siRol = new SelectItem();
				siRol.setLabel(autRol.getNombre());
				siRol.setValue(autRol.getIdRol());
				lstSiAutRol.add(siRol);
			}
			return lstSiAutRol;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}
	
	/***
	 * Metodo que retorna una lista SI de AutRol
	 * 
	 * @return
	 */
	public List<SelectItem> siAutEstado() {
		List<SelectItem> lstSiAutRol = new ArrayList<SelectItem>();
		try {

			for (UsrEstadoSocio autRol : managerGestionSistema.buscarTodosEstadoSocio()) {
				SelectItem siRol = new SelectItem();
				siRol.setLabel(autRol.getEstado());
				siRol.setValue(autRol.getIdEstado());
				lstSiAutRol.add(siRol);
			}
			return lstSiAutRol;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/****
	 * Getters y Setters
	 */

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public AutMenu getObjAutMenu() {
		return objAutMenu;
	}

	public void setObjAutMenu(AutMenu objAutMenu) {
		this.objAutMenu = objAutMenu;
	}

	public List<AutMenu> getLstAutMenu() {
		return lstAutMenu;
	}

	public void setLstAutMenu(List<AutMenu> lstAutMenu) {
		this.lstAutMenu = lstAutMenu;
	}

	public AutPerfile getObjPerfil() {
		return objPerfil;
	}

	public void setObjPerfil(AutPerfile objPerfil) {
		this.objPerfil = objPerfil;
	}

	public AutRolPerfil getObjAutRolPerfil() {
		return objAutRolPerfil;
	}

	public void setObjAutRolPerfil(AutRolPerfil objAutRolPerfil) {
		this.objAutRolPerfil = objAutRolPerfil;
	}

}
