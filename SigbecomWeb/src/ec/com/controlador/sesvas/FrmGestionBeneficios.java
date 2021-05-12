package ec.com.controlador.sesvas;

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
import ec.com.model.dao.entity.SesvasBeneficio;
import ec.com.model.dao.entity.SesvasRequisito;
import ec.com.model.dao.entity.SesvasTipoRequisito;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.sesvas.ManagerSesvas;

@Named("frmGestionBeneficios")
@SessionScoped
public class FrmGestionBeneficios implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerSesvas managerSesvas;
	
	private SesvasBeneficio sesvasBeneficio;
	//private SesvasTipoRequisito sesvasTipoRequisito;
	
	private SesvasTipoRequisito tiposDocumento;
	private Long idSesvasTipoRequisito;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<SesvasBeneficio> lstSesvasBeneficio;
	private List<SesvasTipoRequisito> lstTiposdocumentos;
	
	
	
	@PostConstruct
	public void init() {
		sesvasBeneficio=new SesvasBeneficio();
		lstSesvasBeneficio = new ArrayList<SesvasBeneficio>();
		lstTiposdocumentos = new ArrayList<SesvasTipoRequisito>();
		//sesvasTipoRequisito = new SesvasTipoRequisito();
		
		
		tiposDocumento = new SesvasTipoRequisito();
		idSesvasTipoRequisito = new Long(0);
		
		cargarListaBeneficios();
		cargarTipoDocumentos();
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
				JSFUtil.crearMensajeERROR("No se realizo el registro correctamente");
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Llene los campos requeridos");
		}
	}
	
	public void cargarListaBeneficios() {
		try {
			System.out.println("Aqui");
			lstSesvasBeneficio = managerSesvas.findAllSesvasBeneficio();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada lista beneficios Sesvas");
			e.printStackTrace();
		}
	}
	
	public void onRowEdit(RowEditEvent<Object> event) {
		 try {
				managerSesvas.actualizarObjeto(event.getObject());
				JSFUtil.crearMensajeINFO("Se actualizó correctamente.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
	}
	public void onRowCancel(RowEditEvent<Object> event) {
        JSFUtil.crearMensajeINFO("Se canceló actualización.");
    }
	
	public void cargarTipoDocumentos() {
		try {
			lstTiposdocumentos = managerSesvas.findAllTipoDocumentos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada lista tipos docmuentos Sesvas");
			e.printStackTrace();
		}
	}
	
	public void registrarTipoDocumento() {
		//System.out.println("Documento:"+tiposDocumento.getTipoRequisito());
		if(tiposDocumento !=null && !tiposDocumento.getTipoRequisito().isEmpty()) {
			try {
				tiposDocumento.setTipoRequisito(tiposDocumento.getTipoRequisito().toUpperCase());
				managerSesvas.registrarSesvasTipoDocumento(tiposDocumento);
				JSFUtil.crearMensajeINFO("Se realizó el registro correctamente.");
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("frmRe");
				prime.executeScript("PF('dlgDoc').hide();");
				init();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada registro nuevo tipo documento");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Nombre tipo Documento Requeirdo");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmReq");
		}
	}

	public void agregarRequisitos() {
		//System.out.println("Beneficio: "+ sesvasBeneficio.getBeneficios());
		//System.out.println("idSesvasTipoRequisito: "+ idSesvasTipoRequisito);
		if(idSesvasTipoRequisito!=0) {
			SesvasTipoRequisito sesvasTipoRequisito;
			SesvasRequisito sesRequisito = new SesvasRequisito();
			sesRequisito.setSesvasBeneficio(sesvasBeneficio);
			sesvasTipoRequisito = lstTiposdocumentos.stream().filter(p ->p.getIdSesvasTipoRequisito()==idSesvasTipoRequisito).findAny().orElse(null);
			sesRequisito.setSesvasTipoRequisito(sesvasTipoRequisito);
			try {
				managerSesvas.registrarSesvasRequisito(sesRequisito);
				JSFUtil.crearMensajeINFO("Registro realizado correctamente");
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form2");
				prime.executeScript("PF('dlgReq').hide();");
				init();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada agregar Nuevo Requisito");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("nuloooo");
			JSFUtil.crearMensajeERROR("Seleccione tipo Documento");
		}
	}
	
	public void cargarBeneficio(long idBeneficio) {
		sesvasBeneficio = lstSesvasBeneficio.stream().filter(p->p.getIdSesvasBeneficios()==idBeneficio).findAny().orElse(null);
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

	public SesvasTipoRequisito getTiposDocumento() {
		return tiposDocumento;
	}

	public void setTiposDocumento(SesvasTipoRequisito tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public List<SesvasTipoRequisito> getLstTiposdocumentos() {
		return lstTiposdocumentos;
	}

	public void setLstTiposdocumentos(List<SesvasTipoRequisito> lstTiposdocumentos) {
		this.lstTiposdocumentos = lstTiposdocumentos;
	}

	public Long getIdSesvasTipoRequisito() {
		return idSesvasTipoRequisito;
	}

	public void setIdSesvasTipoRequisito(Long idSesvasTipoRequisito) {
		this.idSesvasTipoRequisito = idSesvasTipoRequisito;
	}

}
