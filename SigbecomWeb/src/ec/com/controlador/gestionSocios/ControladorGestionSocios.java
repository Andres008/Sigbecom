package ec.com.controlador.gestionSocios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.dao.entity.GesDiscapacidadPersona;
import ec.com.model.dao.entity.GesEstadoCivil;
import ec.com.model.dao.entity.GesEtnia;
import ec.com.model.dao.entity.GesGenero;
import ec.com.model.dao.entity.GesPariente;
import ec.com.model.dao.entity.GesPersona;
import ec.com.model.dao.entity.GesTipoSangre;
import ec.com.model.dao.entity.UsrConsanguinidad;
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

	private boolean skip;

	private UploadedFile file;

	private GesPariente objGesPariente;

	public ControladorGestionSocios() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarIngresoSocio() {
		objUsrSocio = new UsrSocio();
		objUsrSocio.setGesPersona(new GesPersona());
		objUsrSocio.setAutRol(new AutRol());
		objUsrSocio.setUsrEstadoSocio(new UsrEstadoSocio());
	}

	/***
	 * Metodo inicial para Actualizacion de datos
	 */
	public void inicializarActualizacionSocio() {

		try {
			inicializarGesPariente();
			objUsrSocio = managerGestionSocios
					.buscarSocioById(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
			if (objUsrSocio.getPrimerInicio().equals("S"))
				objUsrSocio.setClave("");
			if (objUsrSocio.getGesPersona().getGesDiscapacidadPersonas() == null)
				objUsrSocio.getGesPersona().setGesDiscapacidadPersonas(new ArrayList<GesDiscapacidadPersona>());
			if (objUsrSocio.getGesPersona().getGesEstadoCivil() == null)
				objUsrSocio.getGesPersona().setGesEstadoCivil(new GesEstadoCivil());
			if (objUsrSocio.getGesPersona().getGesEtnia() == null)
				objUsrSocio.getGesPersona().setGesEtnia(new GesEtnia());
			if (objUsrSocio.getGesPersona().getGesGenero() == null)
				objUsrSocio.getGesPersona().setGesGenero(new GesGenero());
			if (objUsrSocio.getGesPersona().getGesParientes() == null)
				objUsrSocio.getGesPersona().setGesParientes(new ArrayList<GesPariente>());
			if (objUsrSocio.getGesPersona().getGesTipoSangre() == null)
				objUsrSocio.getGesPersona().setGesTipoSangre(new GesTipoSangre());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"inicializarActualizacionSocio", e.getMessage());
			e.printStackTrace();
		}

	}

	/****
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/***
	 * 
	 */
	public void upload() {
		System.out.println("Llega a carga la fotografia");
		if (file != null) {
			FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	/***
	 * 
	 */
	public void actualizarContraseniaActualizacionDatos() {
		try {
			objUsrSocio.setClave(ModelUtil.md5(objUsrSocio.getClave()));
			objUsrSocio.setPrimerInicio("N");
			managerGestionSocios.actualizarUsrSocio(objUsrSocio);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(),
					"actualizarContraseniaActualizacionDatos",
					"Actualizo contraseña usuario " + objUsrSocio.getGesPersona().getCedula());
			JSFUtil.crearMensajeINFO("Contraseña cambiada correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * 
	 */
	public void actualizarPersonaSocio() {
		try {
			objUsrSocio.getGesPersona().setNombres(objUsrSocio.getGesPersona().getNombres().toUpperCase());
			objUsrSocio.getGesPersona().setApellidos(objUsrSocio.getGesPersona().getApellidos().toUpperCase());
			objUsrSocio.getGesPersona().setEmail(objUsrSocio.getGesPersona().getEmail().toLowerCase());
			managerGestionPersonas.actualizarGesPersona(objUsrSocio.getGesPersona());
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "actualizarPersonaSocio",
					"Actualización a persona: " + objUsrSocio.getGesPersona().getCedula());
			JSFUtil.crearMensajeINFO("Datos actualizados Correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "actualizarPersonaSocio",
					e.getMessage());
		}
	}

	public void actualizarSocio() {
		try {
			for (GesPariente pariente : objUsrSocio.getGesParientes()) {
				if (managerGestionPersonas.buscarPersonaByCedula(pariente.getGesPersona().getCedula()) == null)
					managerGestionPersonas.insertarPersona(pariente.getGesPersona());
			}
			managerGestionSocios.actualizarUsrSocio(objUsrSocio);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "actualizarSocio",
					"Actualización a socio: " + objUsrSocio.getGesPersona().getCedula());
			JSFUtil.crearMensajeINFO("Datos actualizados Correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "actualizarSocio",
					e.getMessage());
		}
	}

	/*
	 * MÉTODO DEL PANEL WIZARD
	 */
	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // resetear en caso de que regrese
			return "solicitudPermisos";
		} else {
			return event.getNewStep();
		}
	}

	/***
	 * 
	 * @throws Exception
	 */
	public void ingresarFamiliar() throws Exception {
		try {
			ModelUtil.verificarCedulaEcuador(objGesPariente.getGesPersona().getCedula());
			objGesPariente.setUsrConsanguinidad(managerGestionSocios
					.buscarConsanguinidadById(objGesPariente.getUsrConsanguinidad().getIdConsanguinidad()));
			objGesPariente.setUsrSocio(objUsrSocio);
			objUsrSocio.getGesParientes().add(objGesPariente);
			JSFUtil.crearMensajeINFO("Agregado correctamente.");
			inicializarGesPariente();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Atención", e.getMessage());
			PrimeFaces.current().dialog().showMessageDynamic(message);
			throw new Exception("Error...");
		}

	}

	/***
	 * 
	 */
	public void inscribirSocio() {
		try {
			String clave = ModelUtil.randomAlphaNumeric();
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
			JSFUtil.crearMensajeINFO("Usuario creado correctamente.");
			inicializarIngresoSocio();
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "inscribirUsuario",
					"Error al crear usuario " + objUsrSocio.getGesPersona().getCedula());
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}

	}

	public void inicializarGesPariente() {
		objGesPariente = new GesPariente();
		objGesPariente.setUsrConsanguinidad(new UsrConsanguinidad());
		objGesPariente.setGesPersona(new GesPersona());
		objGesPariente.getGesPersona().setGesDiscapacidadPersonas(new ArrayList<GesDiscapacidadPersona>());
		objGesPariente.getGesPersona().setGesEstadoCivil(new GesEstadoCivil());
		objGesPariente.getGesPersona().setGesEtnia(new GesEtnia());
		objGesPariente.getGesPersona().setGesGenero(new GesGenero());
		objGesPariente.getGesPersona().setGesParientes(new ArrayList<GesPariente>());
		objGesPariente.getGesPersona().setGesTipoSangre(new GesTipoSangre());
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

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public GesPariente getObjGesPariente() {
		return objGesPariente;
	}

	public void setObjGesPariente(GesPariente objGesPariente) {
		this.objGesPariente = objGesPariente;
	}

}