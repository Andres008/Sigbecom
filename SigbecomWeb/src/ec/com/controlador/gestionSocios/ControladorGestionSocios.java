package ec.com.controlador.gestionSocios;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.dao.entity.GesPersona;
import ec.com.model.dao.entity.UsrEstadoSocio;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.gestionPersonas.ManagerGestionPersonas;
import ec.com.model.gestionSocios.ManagerGestionSocios;
import ec.com.model.modulos.util.CorreoUtil;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;


@Named("controladorGestionSocios")
@SessionScoped
public class ControladorGestionSocios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private BeanLogin beanLogin;

	@EJB
	ManagerLog managerLog;

	@EJB
	ManagerGestionSocios managerGestionSocios;

	@EJB
	ManagerGestionPersonas managerGestionPersonas;

	@EJB
	CorreoUtil correoUtil;

	private UsrSocio objUsrSocio;

	public ControladorGestionSocios() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarIngresoSocio() {
		System.out.println("Inicia...");
		objUsrSocio = new UsrSocio();
		objUsrSocio.setGesPersona(new GesPersona());
		objUsrSocio.setAutRol(new AutRol());
		objUsrSocio.setUsrEstadoSocio(new UsrEstadoSocio());
	}

	public void inscribirSocio() {
		try {
			String clave= ModelUtil.randomAlphaNumeric();
			ModelUtil.verificarCedulaEcuador(objUsrSocio.getGesPersona().getCedula());
			objUsrSocio.getGesPersona().setApellidos(objUsrSocio.getGesPersona().getApellidos().toUpperCase());
			objUsrSocio.getGesPersona().setNombres(objUsrSocio.getGesPersona().getNombres().toUpperCase());
			objUsrSocio.getGesPersona().setEmail(objUsrSocio.getGesPersona().getEmail().toLowerCase());
			objUsrSocio.setFechaAlta(new Date());
			objUsrSocio.setPrimerInicio("S");
			objUsrSocio.setClave(ModelUtil.md5(clave));
			objUsrSocio.getUsrEstadoSocio().setIdEstado(1);
			if (managerGestionPersonas.buscarPersonaByCedula(objUsrSocio.getGesPersona().getCedula()) == null)
				managerGestionPersonas.insertarPersona(objUsrSocio.getGesPersona());
			objUsrSocio.setCedulaSocio(objUsrSocio.getGesPersona().getCedula());
			managerGestionSocios.insertarSocio(objUsrSocio);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "inscribirUsuario",
					"Crea usuario " + objUsrSocio.getGesPersona().getCedula());
			correoUtil.enviarCorreoElectronico(objUsrSocio.getGesPersona().getEmail(),
					"Creación Usuarios Socio Comite de Empresa",
					"Se ha creado su usuario para utilizar SIGBECOM (), facor acceder al sistema con las siguentes credenciales, Usuario:"
							+ objUsrSocio.getCedulaSocio() + " , Contraseña:" + clave);
			inicializarIngresoSocio();
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inscribirUsuario",
					"Error al crear usuario " + objUsrSocio.getGesPersona().getCedula());
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}

	}

	/******
	 * GETTERS Y SETTERS
	 * 
	 * 
	 */

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public UsrSocio getObjUsrSocio() {
		return objUsrSocio;
	}

	public void setObjUsrSocio(UsrSocio objUsrSocio) {
		this.objUsrSocio = objUsrSocio;
	}

}
