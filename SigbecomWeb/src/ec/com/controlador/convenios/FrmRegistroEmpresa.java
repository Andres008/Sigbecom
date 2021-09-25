package ec.com.controlador.convenios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvEmpresa;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.modulos.util.JSFUtil;

@Named("frmRegistroEmpresa")
@SessionScoped
public class FrmRegistroEmpresa implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerConvenios managerConvenios;
	
	private ConvEmpresa convEmpresa;
	private List<ConvEmpresa> lstConvEmpresa;
	private List<UsrSocio> lstUsrSocio;
	private ConvContacto convContacto;
	private ConvServicio convServicio;
	private String cedulaSocio;
	
	@Inject
	private BeanLogin beanLogin;

	public FrmRegistroEmpresa() {
		super();
	}
	@PostConstruct
	public void init() {
		convEmpresa = new ConvEmpresa();
		lstConvEmpresa = new ArrayList<ConvEmpresa>();
		lstUsrSocio = new ArrayList<UsrSocio>();
		convContacto = new ConvContacto();
		convServicio = new ConvServicio();
		cedulaSocio = "0";
		cargarListaEmpresasConvenio();
	}
	public void registrarEmpresa() {
		System.out.println("Regsitro:"+convEmpresa.getEmpresa());
		if(!convEmpresa.getEmpresa().isEmpty()&&!convEmpresa.getDireccion().isEmpty()){
			try {
				managerConvenios.insertarConvEmpresa(convEmpresa);
				JSFUtil.crearMensajeINFO("Empresa Registrado correctamente");
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
		}
	}
	public void cargarListaEmpresasConvenio() {
		try {
			lstConvEmpresa = managerConvenios.findAllEmpresas();
			lstUsrSocio = managerConvenios.findAllUsrSocios();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void onRowEdit(RowEditEvent<Object> event) {
		 try {
			 	managerConvenios.actualizarObjeto(event.getObject());
				JSFUtil.crearMensajeINFO("Se actualizó correctamente.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
	}
	public void onRowCancel(RowEditEvent<Object> event) {
       JSFUtil.crearMensajeINFO("Se canceló actualización.");
   }
	public void cargarEmpresa(ConvEmpresa convEmpresa) {
		convContacto.setConvEmpresa(convEmpresa);
	}
	public void registrarContacto() {
		System.out.println("Regsitro Contacto:"+convContacto.getCargo());
		if(!convContacto.getCargo().isEmpty() && !cedulaSocio.equalsIgnoreCase("0")){
			try {
				Long idConvEmpresa = convContacto.getConvEmpresa().getIdConvEmpresa();
				
				List<ConvContacto> lstConvContactoTmp = managerConvenios.findAllContactosByCedulaAndIdConvEmpresa(cedulaSocio, idConvEmpresa);
				if(lstConvContactoTmp==null) {
					UsrSocio usrSocio = lstUsrSocio.stream().filter(p -> p.getCedulaSocio().equalsIgnoreCase(cedulaSocio)).findAny().orElse(null);
					if(usrSocio!=null) {
						convContacto.setUsrSocio(usrSocio);
					}
					managerConvenios.insertarConvContacto(convContacto);
					JSFUtil.crearMensajeINFO("Contacto Registrado correctamente");
					init();
					cargarListaEmpresasConvenio();
					PrimeFaces prime=PrimeFaces.current();
					prime.executeScript("PF('dlgReg').hide();");
					prime.ajax().update("form1");
					prime.ajax().update("form2");
				}
				else {
					JSFUtil.crearMensajeWARN("El contacto con cedula "+cedulaSocio+" ya se encuentra registrado");
					init();
					cargarListaEmpresasConvenio();
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("form1");
				}
				
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
		}
		
	}
	public void cargarEmpresaServ(ConvEmpresa convEmpresa) {
		convServicio.setConvEmpresa(convEmpresa);
	}
	public void registrarServicio() {
		System.out.println("RegsitroServicio:"+convServicio.getServicioProducto());
		if(!convServicio.getDetalle().isEmpty()&&!convServicio.getServicioProducto().isEmpty()){
			try {
				convServicio.setEstado("ACTIVO");
				//BigDecimal montoMax= convServicio.getMontoMax().setScale(2, RoundingMode.HALF_EVEN);
				//BigDecimal interes= convServicio.getInteres().setScale(2, RoundingMode.HALF_EVEN);
				//convServicio.setMontoMax(montoMax);
				//convServicio.setInteres(interes);
				managerConvenios.insertarConvServicio(convServicio);
				JSFUtil.crearMensajeINFO("Servicio Registrado correctamente");
				init();
				cargarListaEmpresasConvenio();
				PrimeFaces prime=PrimeFaces.current();
				prime.executeScript("PF('dlgSer').hide();");
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro correctamente");
				e.printStackTrace();
			}
		}
	}
	//GETTERS AND SETTERS
	public ConvEmpresa getConvEmpresa() {
		return convEmpresa;
	}
	public void setConvEmpresa(ConvEmpresa convEmpresa) {
		this.convEmpresa = convEmpresa;
	}
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}
	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public List<ConvEmpresa> getLstConvEmpresa() {
		return lstConvEmpresa;
	}
	public void setLstConvEmpresa(List<ConvEmpresa> lstConvEmpresa) {
		this.lstConvEmpresa = lstConvEmpresa;
	}
	public ConvContacto getConvContacto() {
		return convContacto;
	}
	public void setConvContacto(ConvContacto convContacto) {
		this.convContacto = convContacto;
	}
	public ConvServicio getConvServicio() {
		return convServicio;
	}
	public void setConvServicio(ConvServicio convServicio) {
		this.convServicio = convServicio;
	}
	public List<UsrSocio> getLstUsrSocio() {
		return lstUsrSocio;
	}
	public void setLstUsrSocio(List<UsrSocio> lstUsrSocio) {
		this.lstUsrSocio = lstUsrSocio;
	}
	public String getCedulaSocio() {
		return cedulaSocio;
	}
	public void setCedulaSocio(String cedulaSocio) {
		this.cedulaSocio = cedulaSocio;
	}
	
}
