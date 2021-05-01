package ec.com.controlador.sesvas;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;

@Named("frmMisSolicitudes")
@SessionScoped
public class FrmMisSolicitudes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BeanLogin beanLogin;

	public FrmMisSolicitudes() {
		super();
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	
	
	
}
