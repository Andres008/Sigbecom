package ec.com.controlador.aporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.aporte.ManagerAporte;
import ec.com.model.dao.entity.AporteCliente;
import ec.com.model.dao.entity.AporteDescuento;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.modulos.util.JSFUtil;

@Named("frmGenerarAportes")
@SessionScoped
public class FrmGenerarAportes implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerAporte managerAporte;
	@Inject
	private BeanLogin beanLogin;
	
	private int mes;
	private int anio;
	private List<AporteDescuento> lstAporteDescuento;
	
	@PostConstruct
	public void init() {
		mes=0;
		anio=0;
		lstAporteDescuento = new ArrayList<AporteDescuento>();
		cargarListaDescuentos();
	}
	public void cargarListaDescuentos() {
		try {
			lstAporteDescuento = managerAporte.findAllAporteDescuentos();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado de aportes correctamente");
			e.printStackTrace();
		}
	}
	public void generarDescuento() {
		if(anio>0 && mes>0) {
			try {
				DescEstadoDescuento descEstadoDescuento = managerAporte.findWhereEstadoDescEstadoDescuento("INGRESADA");
				List<AporteCliente> lstAporteClientes = managerAporte.findAllAporteClienteEstadoActivo();
				for (AporteCliente aporteCliente : lstAporteClientes) {
					AporteDescuento aporteDescuento = new AporteDescuento();
					aporteDescuento.setDescEstadoDescuento(descEstadoDescuento);
					aporteDescuento.setAporteCliente(aporteCliente);
					aporteDescuento.setAnio(anio);
					aporteDescuento.setMes(mes);
					aporteDescuento.setFechaAporte(new Date());
					aporteDescuento.setValor(aporteCliente.getAporteCuenta().getValor());
					managerAporte.insertarAportDescuento(aporteDescuento);
					
				}
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
				JSFUtil.crearMensajeINFO("Descuento Generado Correctamente");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se genero los aportes a descuentos correctamente");
				e.printStackTrace();
			}
		}
	}
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}
	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public List<AporteDescuento> getLstAporteDescuento() {
		return lstAporteDescuento;
	}
	public void setLstAporteDescuento(List<AporteDescuento> lstAporteDescuento) {
		this.lstAporteDescuento = lstAporteDescuento;
	}
	
}
