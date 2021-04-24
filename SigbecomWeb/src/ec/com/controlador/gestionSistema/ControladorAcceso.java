package ec.com.controlador.gestionSistema;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.dao.entity.AutRolPerfil;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.dao.entity.VAutMenuRol;
import ec.com.model.gestionSistema.Credencial;
import ec.com.model.gestionSistema.ManagerGestionSistema;
import ec.com.model.modulos.util.JSFUtil;

@Named("controladorAcceso")
@SessionScoped
public class ControladorAcceso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ControladorAcceso() {
		// TODO Auto-generated constructor stub
	}

	private UsrSocio objUsrSocio;
	private String idUsuario;
	private String clave; 
	private MenuModel model;
	private Boolean panelCambioContr;
	private StreamedContent file;

	@EJB
	ManagerGestionSistema managerGestionSistema;

	@EJB
	ManagerLog managerLog;

	@Inject
	private BeanLogin beanLogin;

	
	public void inicializarAcceso() {
	}
	
	public void mensageCambioContrasenia() {
		 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Si requiere el cambios de su contraseña, favor comuniquese con el Departamente de Seguimiento y Evaluación. Mode 35095.");

	        PrimeFaces.current().dialog().showMessageDynamic(message);
	}

	/*public void cambiarContraseniaPrimerAcceso() {
		try {
			if (!objAutUsuario.getCedula().equals(objAutUsuario.getClave()))
			{
				objAutUsuario.setClave(ModelUtil.md5(objAutUsuario.getClave()));
				objAutUsuario.setPrimerInicio("NO");
				managerUsuario.actualizarUsuario(objAutUsuario);
				managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "cambiarContrase�aPrimerAcceso",
						"Se cambio la contrasenia por primer uso de usuario: " + objAutUsuario.getCedula());
				JSFUtil.crearMensajeINFO("Atenci�n", "Contrase�a Actualizada.");
				inicializarCredenciales();
			}else
			{
				JSFUtil.crearMensajeWARN("Advertencia", "La clave no puede ser igual al usuario (N�mero de c�dula).");
			}
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error", e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"cambiarContrase�aPrimerAcceso",
					"Error al cambiar contrase�a primer uso: " + objAutUsuario.getCedula());
			e.printStackTrace();
		}
	}*/

	public void inicializarCredenciales() {
		panelCambioContr = false;
		objUsrSocio = new UsrSocio();
	}

	
	/***
	 * Metodo para crear el Model menu.
	 * @param objAutRol
	 * @throws Exception
	 */
	public void menuByRol(AutRol objAutRol) throws Exception {
		List<VAutMenuRol> lstVAutMenuRol = managerGestionSistema.findVAutMenuRol(objAutRol);
		//List<AutRolPerfil> lstAutRolMenu = managerGestionSistema.findRolMenuByRol(objAutRol);
		model = new DefaultMenuModel();
		model.getElements().add( DefaultMenuItem.builder().value("Menu Principal").icon("ui-icon-home")
				.command("#{controladorAcceso.acceso('" +"/modulos/menuPrincipal.xhtml" + "')}").update("messages").build());
		for (VAutMenuRol vAutMenuRol : lstVAutMenuRol) {
			// First submenu
			DefaultSubMenu submenu = DefaultSubMenu.builder().label(vAutMenuRol.getNombre()).build();
			
			for (AutRolPerfil autRolPerfil : managerGestionSistema.findRolPerfilbyRol(objAutRol, vAutMenuRol)) {
				DefaultMenuItem item = DefaultMenuItem.builder().value(autRolPerfil.getAutPerfile().getNombre()).icon(autRolPerfil.getAutPerfile().getIcon())
						.command("#{controladorAcceso.acceso('" + autRolPerfil.getAutPerfile().getUrl() + "')}").update("messages").build();
				submenu.getElements().add(item);
			}
			model.getElements().add(submenu);
		}
	}

	public String acceso(String ruta) {
		return ruta + "?faces-redirect=true";
	}

	/*
	 * public String getBrowserName() { ExternalContext externalContext =
	 * FacesContext.getCurrentInstance().getExternalContext(); String userAgent =
	 * externalContext.getRequestHeaderMap().get("User-Agent");
	 * 
	 * if(userAgent.contains("MSIE")){ return "Internet Explorer"; }
	 * if(userAgent.contains("Firefox")){ return "Firefox"; }
	 * if(userAgent.contains("Chrome")){ return "Chrome"; }
	 * if(userAgent.contains("Opera")){ return "Opera"; }
	 * if(userAgent.contains("Safari")){ return "Safari"; } return "Unknown"; }
	 */
	public String actionObtenerAcceso() {
		try {
			Credencial credencial = managerGestionSistema.obtenerAcceso(idUsuario, /*ModelUtil.md5(clave)*/clave);
			objUsrSocio = managerGestionSistema.findByIdAutUsuario(idUsuario);
			// se configura la direccion IP del cliente:
			menuByRol(objUsrSocio.getAutRol());
			HttpServletRequest request;
			request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String remoteAddr = request.getRemoteAddr() + " " + request.getHeader("user-agent");
			if (remoteAddr.length() > 200)
				credencial.setDireccionIP(remoteAddr.substring(0, 199));
			else
				credencial.setDireccionIP(remoteAddr);
			// una vez que se obtiene la credencial, se llenan datos en el
			// BeanLogin para la sesion:
			beanLogin.setCredencial(credencial);
			// cargamos el menu de opciones
			// beanLogin.setMenuRaiz(managerGestionSistema.crearAutMenu());
			// para el resto de generacion de bitacoras unicamente almacenamos la direccion
			// IP:
			credencial.setDireccionIP(request.getRemoteAddr());
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("credencial", credencial);
			if (credencial.getPrimerInicio().equals("SI")) {
				objUsrSocio.setClave("");
				panelCambioContr = true;
				return "";
			}
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "actionObtenerAcceso",
					"Se ingresa al sistema");
			return "/modulos/menuPrincipal?faces-redirect=true";

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return "";
		}

	}
	
    public StreamedContent getFile() {
    	return DefaultStreamedContent.builder()
                .name("GuiaUsuario.pdf")
                .contentType("application/pdf")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/archivos/GuiaUsuario.pdf"))
                .build();
    }

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public UsrSocio getObjAutUsuario() {
		return objUsrSocio;
	}

	public void setObjAutUsuario(UsrSocio objAutUsuario) {
		this.objUsrSocio = objAutUsuario;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public MenuModel getMenuModel() {
		return model;
	}

	public void setMenuModel(MenuModel menuModel) {
		this.model = menuModel;
	}

	public Boolean getPanelCambioContr() {
		return panelCambioContr;
	}

	public void setPanelCambioContr(Boolean panelCambioContr) {
		this.panelCambioContr = panelCambioContr;
	}

}

