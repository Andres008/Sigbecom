package ec.com.controlador.sesvas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.SesvasBeneficio;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.sesvas.ManagerSesvas;

@Named("frmGestionBeneficios")
@SessionScoped
public class FrmGestionBeneficios implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerSesvas managerSesvas;
	private SesvasBeneficio sesvasBeneficio;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<SesvasBeneficio> lstSesvasBeneficio;
	
	@PostConstruct
	public void init() {
		sesvasBeneficio=new SesvasBeneficio();
		lstSesvasBeneficio = new ArrayList<SesvasBeneficio>();
		cargarListaBeneficios();
	}

	public FrmGestionBeneficios() {
		super();
	}
	public void registrarBeneficio() {
		//System.out.println("Ingreso: "+sesvasBeneficio);
		if(sesvasBeneficio.getBeneficios().isEmpty()==false && sesvasBeneficio.getDetalle().isEmpty()==false) {
			try {
				managerSesvas.insertarSesvasBeneficio(sesvasBeneficio);
				JSFUtil.crearMensajeINFO("Registro guardado correctamente");
				init();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSFUtil.crearMensajeERROR("No se realiz� el registro correctamente");
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Llene los campos requeridos");
		}
	}
	
	public void cargarListaBeneficios() {
		try {
			lstSesvasBeneficio = managerSesvas.findAllSesvasBeneficio();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada lista beneficios Sesvas");
			e.printStackTrace();
		}
	}
	//Getter and Setters
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public SesvasBeneficio getSesvasBeneficio() {
		return sesvasBeneficio;
	}

	public void setSesvasBeneficio(SesvasBeneficio sesvasBeneficio) {
		this.sesvasBeneficio = sesvasBeneficio;
	}

	public List<SesvasBeneficio> getLstSesvasBeneficio() {
		return lstSesvasBeneficio;
	}

	public void setLstSesvasBeneficio(List<SesvasBeneficio> lstSesvasBeneficio) {
		this.lstSesvasBeneficio = lstSesvasBeneficio;
	}
	
}
