package ec.com.controlador.gestionSistema;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;

@Named("controladorGestionUsuario")
@SessionScoped
public class ControladorGestionUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private BeanLogin beanLogin;
	
	public ControladorGestionUsuario() {
		// TODO Auto-generated constructor stub
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	
	

}
