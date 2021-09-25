package ec.com.controlador.convenios;

import java.io.Serializable;
import java.math.BigDecimal;
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
import ec.com.model.dao.entity.ConvValorMax;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.modulos.util.JSFUtil;

@Named("frmRegistroValoresMax")
@SessionScoped
public class FrmRegistroValoresMax implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerConvenios managerConvenios;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<UsrSocio> lstUsrSocio;
	private String cedulaSocio;
	private BigDecimal valorMax;
	private List<ConvValorMax> lstConvValorMax;
	
	@PostConstruct
	public void init() {
		lstUsrSocio = new ArrayList<UsrSocio>();
		lstConvValorMax = new ArrayList<ConvValorMax>();
		cedulaSocio = "";
		valorMax = new BigDecimal(0);
		cargarListaUsuarios();
		cargarListaRegistroValoresMax();
	}
	public void cargarListaUsuarios() {
		try {
			lstUsrSocio = managerConvenios.findAllUsrSocios();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaRegistroValoresMax() {
		try {
			lstConvValorMax = managerConvenios.findAllConvValorMax();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void registrar() {
		//System.out.println("Registro");
		//System.out.println("cedula: "+cedulaSocio + " Condicion"+ !cedulaSocio.isEmpty());
		//System.out.println("valor Max: "+valorMax +" codicion :"+valorMax.compareTo(new BigDecimal(0)));
		if(!cedulaSocio.isEmpty() && valorMax.compareTo(new BigDecimal(0))==1) {
			//System.out.println("Ingresado:");
			
			try {
				List<ConvValorMax> lstConvValorMaxsTmp = new ArrayList<ConvValorMax>();
				lstConvValorMaxsTmp = managerConvenios.findConvValorMaxByCedula(cedulaSocio);
				if(lstConvValorMaxsTmp==null) {
					UsrSocio usrSocio = managerConvenios.findUsrSociosByCedulaSocio(cedulaSocio);
					ConvValorMax convValorMax = new ConvValorMax();
					convValorMax.setUsrSocio(usrSocio);
					convValorMax.setEstado("ACTIVO");
					convValorMax.setValorMax(valorMax);
					managerConvenios.insertarConvValorMax(convValorMax);
					JSFUtil.crearMensajeINFO("Registro realizado correctamente");
					
				}
				else if (lstConvValorMaxsTmp!=null && lstConvValorMaxsTmp.size()==1) {
					JSFUtil.crearMensajeWARN("Usuario ya se encuentra registrado");
				} 
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form2");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSFUtil.crearMensajeERROR("No se encuetra registrado correctamente el usuario con cedula nro: "+cedulaSocio);
			}
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
	//GETTERS SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}
	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
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
	public List<ConvValorMax> getLstConvValorMax() {
		return lstConvValorMax;
	}
	public void setLstConvValorMax(List<ConvValorMax> lstConvValorMax) {
		this.lstConvValorMax = lstConvValorMax;
	}
	public BigDecimal getValorMax() {
		return valorMax;
	}
	public void setValorMax(BigDecimal valorMax) {
		this.valorMax = valorMax;
	}
	
}
