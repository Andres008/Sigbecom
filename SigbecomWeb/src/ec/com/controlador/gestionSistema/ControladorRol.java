package ec.com.controlador.gestionSistema;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.gestionSistema.ManagerGestionSistema;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

@Named("controladorRol")
@SessionScoped
public class ControladorRol implements Serializable {

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

	private AutRol objAutRol;

	private List<AutRol> lstAutRols;

	public ControladorRol() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo que inicializa las variables a utilizar en la pantalla de
	 * administración de roles
	 */
	public void inicializarAutRol() {
		try {
			objAutRol = new AutRol();
			lstAutRols = managerGestionSistema.buscarTodosAutRol();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que ingresa y/0 autualiza un rol con los datos de pantalla
	 */
	public void ingresarAutRol() {
		if (objAutRol.getIdRol() != 0) {// Verificación si es nuevo rol
			// Aqui me hace falta inactivar el anterior
			objAutRol.setIdRol(new Long(0));
		}
		objAutRol.setEstado("A");
		objAutRol.setFechaInicial(new Date());
		try {
			objAutRol.setNombre(ModelUtil.cambiarMayusculas(objAutRol.getNombre()));
			objAutRol.setDescripcion(ModelUtil.cambiarMayusculas(objAutRol.getDescripcion()));
			managerGestionSistema.ingresarAutRol(objAutRol);
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarRolPeril",
					"Ingreso coreccto Rol Menu. " + objAutRol.getIdRol());
			inicializarAutRol();
			JSFUtil.crearMensajeINFO("Rol ingresado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarAutRol",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public String nombreEstado(String estado) {
		return ModelUtil.nombreEstadoByInicial(estado);
	}

	/**
	 * Getters y Setter
	 */

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public AutRol getObjAutRol() {
		return objAutRol;
	}

	public void setObjAutRol(AutRol objAutRol) {
		this.objAutRol = objAutRol;
	}

	public List<AutRol> getLstAutRols() {
		return lstAutRols;
	}

	public void setLstAutRols(List<AutRol> lstAutRols) {
		this.lstAutRols = lstAutRols;
	}

}
