package ec.com.controlador.sesvas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.dao.entity.SesvasBeneficiario;
import ec.com.model.dao.entity.SesvasBeneficio;
import ec.com.model.dao.entity.SesvasRequisito;
import ec.com.model.dao.entity.SesvasTipoRequisito;
import ec.com.model.dao.entity.UsrConsanguinidad;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.sesvas.ManagerSesvas;

@Named("frmGestionBeneficios")
@SessionScoped
public class FrmGestionBeneficios implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerSesvas managerSesvas;
	
	private SesvasBeneficio sesvasBeneficio;
	private SesvasBeneficio sesvasBeneficioTmp;
	//private SesvasTipoRequisito sesvasTipoRequisito;
	
	private SesvasTipoRequisito tiposDocumento;
	private Long idSesvasTipoRequisito;
	private Long idConsanguinidad;
	private List<UsrConsanguinidad> lstUsrConsanguinidad;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<SesvasBeneficio> lstSesvasBeneficio;
	private List<SesvasTipoRequisito> lstTiposdocumentos;
	
	
	
	@PostConstruct
	public void init() {
		sesvasBeneficio=new SesvasBeneficio();
		lstSesvasBeneficio = new ArrayList<SesvasBeneficio>();
		lstTiposdocumentos = new ArrayList<SesvasTipoRequisito>();
		lstTiposdocumentos = new ArrayList<SesvasTipoRequisito>();
		//sesvasTipoRequisito = new SesvasTipoRequisito();
		sesvasBeneficioTmp=new SesvasBeneficio();
		
		tiposDocumento = new SesvasTipoRequisito();
		idSesvasTipoRequisito = new Long(0);
		idConsanguinidad = new Long(0);
		
		cargarListaBeneficios();
		cargarTipoDocumentos();
		cargarGradoConsanguinidad();
	}

	public FrmGestionBeneficios() {
		super();
	}
	public void registrarBeneficio() {
		//System.out.println("Ingreso: "+sesvasBeneficio);
		if(sesvasBeneficio.getBeneficios().isEmpty()==false && sesvasBeneficio.getDetalle().isEmpty()==false) {
			try {
				sesvasBeneficio.setEstado("ACTIVO");
				managerSesvas.insertarSesvasBeneficio(sesvasBeneficio);
				JSFUtil.crearMensajeINFO("Registro guardado correctamente");
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form2");
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
	
	public void cargarGradoConsanguinidad() {
		try {
			lstUsrConsanguinidad = managerSesvas.findAllUsrConsanguinidad();
			UsrConsanguinidad usrConsanguinidadTmp = new UsrConsanguinidad();
			usrConsanguinidadTmp.setIdConsanguinidad(0);
			usrConsanguinidadTmp.setConsanguinidad("SOCIO");
			lstUsrConsanguinidad.add(usrConsanguinidadTmp);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se realizo la peticion solicitada correctamente");
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
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("frmRe");
				prime.ajax().update("form2");
				prime.executeScript("PF('dlgDoc').hide();");
				prime.executeScript("PF('dlgReq').hide();");
				
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
	
	public boolean activarBotones(int tamanio) {
		if(tamanio==0) {
			return false;
		}
		else
			return true;
	}
	
	public boolean activarBotonesEditar(int tamanio) {
		if(tamanio==0) {
			return true;
		}
		else
			return false;
	}
	
	public boolean activarBotonesBlock(int tamanio, String estado) {
		if(tamanio>0 && estado.equalsIgnoreCase("ACTIVO")) {
			return false;
		}
		else
			return true;
		
	}
	
	public boolean activarBotonesRequisito(int tamanio, String estado) {
		if(tamanio>0 && estado.equalsIgnoreCase("INACTIVO")) {
			return true;
		}
		else
			return false;
		
	}
	
	public void eliminarBeneficio(long idSesvasBeneficios) {
		try {
			managerSesvas.eliminarBeneficio(idSesvasBeneficios);
			cargarListaBeneficios();
			JSFUtil.crearMensajeINFO("Registro eliminado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se eliminó el registro seleccionado");
			e.printStackTrace();
		}
	}
	
	public void cargarObjBeneficio(SesvasBeneficio sesvasBeneficio) {
		this.sesvasBeneficioTmp = new SesvasBeneficio();
		this.sesvasBeneficioTmp = sesvasBeneficio;
		System.out.println("Beneficio:"+sesvasBeneficioTmp.getBeneficios());
		this.sesvasBeneficioTmp.setDetalleFinalizacion("");
}
	

	public void actualizarEstado() {
		System.out.println("Beneficio:"+ sesvasBeneficioTmp.getDetalleFinalizacion());
		if(sesvasBeneficioTmp!=null && !sesvasBeneficioTmp.getDetalleFinalizacion().isEmpty()){
			try {
				sesvasBeneficioTmp.setEstado("INACTIVO");
				managerSesvas.actualizarObjeto(sesvasBeneficioTmp);
				sesvasBeneficioTmp= new SesvasBeneficio();
				cargarListaBeneficios();
				JSFUtil.crearMensajeINFO("Beneficio Finalizado correctamente");
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form2");
				prime.executeScript("PF('dlgFin').hide();");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se actualizó correctamente");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("Por favor ingrese el motivos de finalización");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmDetBen:msgDet");
		}
	}
	
	public void cargarObjBeneficioFilter(SesvasBeneficio sesvasBeneficio) {
		this.sesvasBeneficioTmp = new SesvasBeneficio();
		this.sesvasBeneficioTmp = sesvasBeneficio;
		//System.out.println("dato sesvasBeneficio:"+ sesvasBeneficio.getSesvasBeneficiarios().size());
		//lstUsrConsanguinidad = lstUsrConsanguinidad.stream().filter(p-> sesvasBeneficioTmp.getSesvasBeneficiarios().stream().anyMatch(q->q.getUsrConsanguinidad().getIdConsanguinidad()==p.getIdConsanguinidad())).collect(Collectors.toList());
		//System.out.println("tamañoA:"+ lstUsrConsanguinidad.size());
		
		List<SesvasBeneficiario> lstBeneficiarios = new ArrayList<SesvasBeneficiario>();
		try {
			
			lstBeneficiarios = managerSesvas.beneficiariosByIdBeneficios(sesvasBeneficioTmp.getIdSesvasBeneficios());
			if(lstBeneficiarios != null && lstBeneficiarios.size()>0) {
				for (int i=0;i<lstUsrConsanguinidad.size();i++) {
					for (SesvasBeneficiario sesvasBeneficiario : lstBeneficiarios) {
						//System.out.println("Lista:"+lstUsrConsanguinidad.get(i).getConsanguinidad());
						if(sesvasBeneficiario.getIncluirSocio()!=null) {
							
						}
						else if(lstUsrConsanguinidad.get(0).getIdConsanguinidad() == sesvasBeneficiario.getUsrConsanguinidad().getIdConsanguinidad()) {
							System.out.println("pos:"+i);
							lstUsrConsanguinidad.remove(i);
						}
					}
				}
			}
			
			//System.out.println("tamañoB:"+ lstUsrConsanguinidad.size());
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmBen");
			prime.executeScript("PF('dlgBen').show();");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	
	public void agregarBeneficiarios() {
		System.out.println("Beneficio: "+sesvasBeneficioTmp.getBeneficios());
		//no guardar si ya esta agregado
		
		if(idConsanguinidad==0) {
			SesvasBeneficiario sesvasBeneficiario = new SesvasBeneficiario();
			sesvasBeneficiario.setIncluirSocio("SI");
			sesvasBeneficiario.setSesvasBeneficio(sesvasBeneficioTmp);
			try {
				
				managerSesvas.insertarSesvasBeneficiario(sesvasBeneficiario);
				JSFUtil.crearMensajeINFO("Registro cargado correctamente");
				PrimeFaces prime=PrimeFaces.current();
				prime.executeScript("PF('dlgBen').hide();");
				prime.ajax().update("form2");
				
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
		}
		else {
			UsrConsanguinidad usrConsanguinidad = new UsrConsanguinidad();
			usrConsanguinidad = lstUsrConsanguinidad.stream().filter(p->p.getIdConsanguinidad()==idConsanguinidad).findAny().orElse(null);
			SesvasBeneficiario sesvasBeneficiario = new SesvasBeneficiario();
			sesvasBeneficiario.setSesvasBeneficio(sesvasBeneficioTmp);
			sesvasBeneficiario.setUsrConsanguinidad(usrConsanguinidad);
			PrimeFaces prime=PrimeFaces.current();
			prime.executeScript("PF('dlgBen').hide();");
			prime.ajax().update("form2");
			try {
				managerSesvas.insertarSesvasBeneficiario(sesvasBeneficiario);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
		}
		init();
		cargarListaBeneficios();
		cargarTipoDocumentos();
		cargarGradoConsanguinidad();
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("form2:tblBen");
	}
	public String retornarBeneficiario(String consanguinidad) {
		if(consanguinidad !=null) {
			return consanguinidad;
		}
		else
			return "SOCIO";
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

	public SesvasBeneficio getSesvasBeneficioTmp() {
		return sesvasBeneficioTmp;
	}

	public void setSesvasBeneficioTmp(SesvasBeneficio sesvasBeneficioTmp) {
		this.sesvasBeneficioTmp = sesvasBeneficioTmp;
	}

	public List<UsrConsanguinidad> getLstUsrConsanguinidad() {
		return lstUsrConsanguinidad;
	}

	public void setLstUsrConsanguinidad(List<UsrConsanguinidad> lstUsrConsanguinidad) {
		this.lstUsrConsanguinidad = lstUsrConsanguinidad;
	}

	public Long getIdConsanguinidad() {
		return idConsanguinidad;
	}

	public void setIdConsanguinidad(Long idConsanguinidad) {
		this.idConsanguinidad = idConsanguinidad;
	}
	
}
