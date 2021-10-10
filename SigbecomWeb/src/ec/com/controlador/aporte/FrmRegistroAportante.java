package ec.com.controlador.aporte;

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
import ec.com.model.aporte.ManagerAporte;
import ec.com.model.dao.entity.AporteCliente;
import ec.com.model.dao.entity.AporteCuenta;
import ec.com.model.dao.entity.AporteDescuento;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.modulos.util.JSFUtil;

@Named("frmRegistroAportante")
@SessionScoped
public class FrmRegistroAportante implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerAporte managerAporte;
	@Inject
	private BeanLogin beanLogin;
	
	private List<UsrSocio> lstUsrSocios;
	private List<AporteCuenta> lstAporteCuentas;
	private List<AporteCliente> lstAporteClientes;
	private BigDecimal saldoInicial;
	private String cedulaSocio;
	private Long idCuenta;
	private String detalle;

	@PostConstruct
	public void init() {
		lstUsrSocios = new ArrayList<UsrSocio>();
		lstAporteCuentas = new ArrayList<AporteCuenta>();
		lstAporteClientes = new ArrayList<AporteCliente>();
		saldoInicial = new BigDecimal(0);
		cedulaSocio= "";
		detalle = "";
		idCuenta = new Long(0);
		cargarListaUsrSocios();
		cargarListaAporteCuentas();
		cargarListaAporteClientes();
	}
	
	public void cargarListaUsrSocios() {
		try {
			lstUsrSocios = managerAporte.findAllUsrSocios();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado de Socios correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaAporteCuentas() {
		try {
			lstAporteCuentas = managerAporte.findAllAporteCuenta();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("NO se cargo el listado de Cuentas correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaAporteClientes() {
		try {
			lstAporteClientes = managerAporte.findAllAporteCliente();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("NO se cargo el listado de Cuentas correctamente");
			e.printStackTrace();
		}
	}
	public void registrarAportanteAunaCuenta() {
		if(!cedulaSocio.isEmpty() && idCuenta.intValue()>0 && (saldoInicial.compareTo(BigDecimal.ZERO)==0 || saldoInicial.compareTo(BigDecimal.ZERO)==1) && !detalle.isEmpty()) {
			try {
				List<AporteCliente> lstAporteCliTmp = managerAporte.findAllClientesByCedulaIdCuenta(cedulaSocio,idCuenta);
				
				UsrSocio usrSocio = lstUsrSocios.stream().filter(p->p.getCedulaSocio().equalsIgnoreCase(cedulaSocio)).findAny().orElse(null);
				AporteCuenta aporteCuenta = lstAporteCuentas.stream().filter(p->p.getIdCuenta()==idCuenta).findAny().orElse(null);
				if(lstAporteCliTmp==null) {	
					AporteCliente aporteCliente = new AporteCliente();
					aporteCliente.setAporteCuenta(aporteCuenta);
					aporteCliente.setUsrSocio(usrSocio);
					aporteCliente.setDetalle(detalle);
					aporteCliente.setEstado("ACTIVO");
					aporteCliente.setSaldoInicial(saldoInicial);
					managerAporte.insertarAportCliente(aporteCliente);
					JSFUtil.crearMensajeINFO("Cliente Registrado Correctamente");
					init();
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("form1");
					prime.ajax().update("form2");
				}
				else if(lstAporteCliTmp.size()==1) {
					JSFUtil.crearMensajeWARN("Cliente ya se encuentra registrado con la cedula: "+cedulaSocio);
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("form1");
					prime.ajax().update("form2");
				}
				else if(lstAporteCliTmp.size()>1) {
					JSFUtil.crearMensajeWARN("El cliente con cedula "+cedulaSocio+" se encuetra repetido en la cuenta "+aporteCuenta.getCuenta());
				}
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se encontro ningun registro con ese numero de cedula");
				e.printStackTrace();
			}
			
		}
	}
	public void onRowEdit(RowEditEvent<Object> event) {
		 try {
			 	managerAporte.actualizarObjeto(event.getObject());
				JSFUtil.crearMensajeINFO("Se actualizó correctamente.");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
	}
	public void onRowCancel(RowEditEvent<Object> event) {
     JSFUtil.crearMensajeINFO("Se canceló actualización.");
	}
	
	public void eliminarRegistro(AporteCliente aporteCliente) {
		try {
			List<AporteDescuento> lstAporteCliente=managerAporte.findAllAporteDescuentosByIdCliente(aporteCliente.getIdCliente());
			if(lstAporteCliente==null) {
				managerAporte.eliminarAporteClienteByIdCliente(aporteCliente.getIdCliente());
				JSFUtil.crearMensajeINFO("Registro eliminado correctamente.");
				init();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			}
			else {
				JSFUtil.crearMensajeWARN("No se puede eliminar: Exiten uno o varios registro de pagos historicos relacionados a esta cuenta");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//GETTERS AND SETTERS

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<UsrSocio> getLstUsrSocios() {
		return lstUsrSocios;
	}

	public void setLstUsrSocios(List<UsrSocio> lstUsrSocios) {
		this.lstUsrSocios = lstUsrSocios;
	}

	public List<AporteCuenta> getLstAporteCuentas() {
		return lstAporteCuentas;
	}

	public void setLstAporteCuentas(List<AporteCuenta> lstAporteCuentas) {
		this.lstAporteCuentas = lstAporteCuentas;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public String getCedulaSocio() {
		return cedulaSocio;
	}

	public void setCedulaSocio(String cedulaSocio) {
		this.cedulaSocio = cedulaSocio;
	}

	public Long getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public List<AporteCliente> getLstAporteClientes() {
		return lstAporteClientes;
	}

	public void setLstAporteClientes(List<AporteCliente> lstAporteClientes) {
		this.lstAporteClientes = lstAporteClientes;
	}
	
}
