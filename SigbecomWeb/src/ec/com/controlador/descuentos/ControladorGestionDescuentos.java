package ec.com.controlador.descuentos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.DesDescuentoMensuale;
import ec.com.model.dao.entity.DesTipoNovedad;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.dao.entity.DescNovedade;
import ec.com.model.dao.entity.DescValoresFijo;
import ec.com.model.dao.entity.FinAccionPrestamo;
import ec.com.model.dao.entity.FinAccionesCredito;
import ec.com.model.dao.entity.FinCuotasDescontada;
import ec.com.model.dao.entity.FinEstadoCredito;
import ec.com.model.dao.entity.FinEstadoCuota;
import ec.com.model.dao.entity.FinPrestamoNovacion;
import ec.com.model.dao.entity.FinPrestamoRequisito;
import ec.com.model.dao.entity.FinPrestamoSocio;
import ec.com.model.dao.entity.FinRequisito;
import ec.com.model.dao.entity.FinResolucionPrestamo;
import ec.com.model.dao.entity.FinTablaAmortizacion;
import ec.com.model.dao.entity.FinTipcrediRequisito;
import ec.com.model.dao.entity.FinTipoCredito;
import ec.com.model.dao.entity.FinTipoSolicitud;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.dao.entity.UsrSocioDescuentoFijo;
import ec.com.model.dao.entity.UsrTipoDescuento;
import ec.com.model.dao.entity.VDescuentoMensualSocio;
import ec.com.model.gestionCreditos.ManagerGestionCredito;
import ec.com.model.gestionDescuentos.ManagerGestionDescuentos;
import ec.com.model.gestionSistema.ManagerGestionSistema;
import ec.com.model.gestionSocios.ManagerGestionSocios;
import ec.com.model.modulos.util.CorreoUtil;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named("controladorGestionDescuentos")
@SessionScoped
public class ControladorGestionDescuentos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private BeanLogin beanLogin;

	@EJB
	ManagerLog managerLog;

	@EJB
	ManagerGestionSocios managerGestionSocios;

	@EJB
	CorreoUtil correoUtil;

	@EJB
	ManagerGestionDescuentos managerGestionDescuentos;

	private UsrTipoDescuento objUsrTipoDescuento;

	private List<UsrTipoDescuento> lstUsrTipoDescuento;

	private DescNovedade objDescNovedade;

	private List<DescNovedade> lstDescNovedade;

	private Long tipoNovedad;

	private List<UsrSocioDescuentoFijo> lstUsrSocioDescuentoFijo;

	private List<VDescuentoMensualSocio> lstVDescuentoMensualSocio;

	private List<DesDescuentoMensuale> lstDesDescuentoMensuale;

	private Long mes, anio;

	public ControladorGestionDescuentos() {

	}

	public void inicializarNovedadesEconomicas() {
		tipoNovedad = new Long(0);
		objDescNovedade = new DescNovedade();
		objDescNovedade.setUsrSocio1(beanLogin.getCredencial().getObjUsrSocio());
		objDescNovedade.setUsrSocio2(new UsrSocio());
		objDescNovedade.setDescEstadoDescuento(new DescEstadoDescuento(2));
		objDescNovedade.setDesTipoNovedad(new DesTipoNovedad());
		try {
			lstDescNovedade = managerGestionDescuentos.buscarNovedades();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializartipoDescuentos() {
		objUsrTipoDescuento = new UsrTipoDescuento();
		try {
			lstUsrTipoDescuento = managerGestionDescuentos.buscarTodoDescuentos();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarMisDescuento() {
		try {
			lstDesDescuentoMensuale = managerGestionDescuentos
					.buscarMisDescuentos(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarDescuentoMensuales() {
		lstDesDescuentoMensuale = new ArrayList<DesDescuentoMensuale>();
	}

	public void inicializarResumenDescuento() {
		try {
			lstVDescuentoMensualSocio = managerGestionDescuentos.findDescuentoMensual();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public BigDecimal valorTotalPrestamo() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getValorPrestamo());
		}
		return total;
	}

	public BigDecimal valorTotalPlanes() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getValorPlanMovil());
		}
		return total;
	}

	public BigDecimal valorTotalConvenio() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getValorConvenio());
		}
		return total;
	}

	public BigDecimal valorTotalAhorro() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getValorAhorro());
		}
		return total;
	}

	public BigDecimal valorTotalCesantia() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getValorCesantia());
		}
		return total;
	}

	public BigDecimal valorTotalSalud() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getValorSalud());
		}
		return total;
	}

	public BigDecimal valorTotalExternos() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getAportesExternos());
		}
		return total;
	}

	public BigDecimal valorTotalNovedades() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getNovedades());
		}
		return total;
	}

	public BigDecimal valorTotalDescuento() {
		BigDecimal total = new BigDecimal(0);
		for (VDescuentoMensualSocio desDescuentoMensuale : lstVDescuentoMensualSocio) {
			total = total.add(desDescuentoMensuale.getTotalDescuento());
		}
		return total;
	}

	public void ingresarTipoDescuento() {
		objUsrTipoDescuento.setEstado("A");
		try {
			managerGestionDescuentos.ingresarTipoDescuento(objUsrTipoDescuento);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarTipoDescuento",
					"Se ingreso tipo descuento id: " + objUsrTipoDescuento.getIdDescuento());
			JSFUtil.crearMensajeINFO("Ingreso correcto");
			inicializartipoDescuentos();
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarTipoDescuento",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarDescuentoFijos() {
		try {
			lstUsrSocioDescuentoFijo = managerGestionDescuentos.buscarDescuentosFijos();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void buscarDescuentosMensuales() {
		try {
			lstDesDescuentoMensuale = managerGestionDescuentos.buscarDescuentosMensuales(anio, mes);
		} catch (Exception e) {
			JSFUtil.crearMensajeINFO(e.getMessage());
			e.printStackTrace();
		}
	}

	public void ejecutarDescuentoFijos() {
		inicializarDescuentoFijos();
		try {

			List<DescValoresFijo> lstValoresFijos = managerGestionDescuentos.buscarDescuentosFijosPendientes();
			for (DescValoresFijo descValoresFijo : lstValoresFijos) {
				managerGestionDescuentos.eliminarDescuentoFijosPendientes(descValoresFijo);
			}
			for (UsrSocioDescuentoFijo descuentoSocio : lstUsrSocioDescuentoFijo) {
				DescValoresFijo valorFijo = new DescValoresFijo();
				valorFijo.setDescEstadoDescuento(new DescEstadoDescuento(2));
				valorFijo.setFechaDescuento(new Timestamp(new Date().getTime()));
				valorFijo.setUsrSocio(descuentoSocio.getUsrSocio());
				valorFijo.setUsrSocioDescuentoFijo(descuentoSocio);
				managerGestionDescuentos.ingresarDescuentoFijoSocio(valorFijo);
			}
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ejecutarDescuentoFijos",
					"Se ingreso descuentos fijos");
			JSFUtil.crearMensajeINFO("Se ingreso correctamente los valores a descontar.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarNovedadEconomica",
					e.getMessage());
			e.printStackTrace();
		}

	}

	public void anularNovedadEconomica(DescNovedade objNovedad) {
		System.out.println("Llega");
		try {
			objNovedad.getDescEstadoDescuento().setIdEstadoDescuento(4);
			managerGestionDescuentos.actualizarNovedad(objNovedad);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Anulación valor novedad",
					"Se actualizó novedad " + objNovedad.getIdNovedad());
			inicializarNovedadesEconomicas();
			JSFUtil.crearMensajeINFO("Actualización Correcta.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/***
	 * Metodo para modificar desde pantalla.
	 * 
	 * @param event
	 */
	public void onRowEdit(RowEditEvent<Object> event) {
		try {
			DescNovedade objNovedad = (DescNovedade) event.getObject();
			managerGestionDescuentos.actualizarObjeto(event.getObject());
			JSFUtil.crearMensajeINFO("Se actualizó correctamente.");
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Edición valor novedad",
					"Se actualizó novedad " + objNovedad.getIdNovedad());
			inicializarNovedadesEconomicas();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/***
	 * 
	 * @param event
	 */
	public void onRowCancel(RowEditEvent<Object> event) {
		JSFUtil.crearMensajeINFO("Se canceló actualización.");
	}
	
	
	public List<SelectItem> lstSiTipoNovedad() {
		List<SelectItem> lstSiTipoNovedad = new ArrayList<SelectItem>();
		try {
			for (DesTipoNovedad usrTipoNovedad : managerGestionDescuentos.buscarTipoNovedad()) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(usrTipoNovedad.getDescripcion());
				siCivil.setValue(usrTipoNovedad.getId());
				lstSiTipoNovedad.add(siCivil);
			}
			return lstSiTipoNovedad;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public void ejecutarDescuentoMensual() {
		try {
			lstVDescuentoMensualSocio = lstVDescuentoMensualSocio.stream()
					.filter(c -> c.getValorAhorro().intValue() > 0).collect(Collectors.toList());
			lstVDescuentoMensualSocio = lstVDescuentoMensualSocio.stream()
					.filter(c -> c.getValorCesantia().intValue() > 0).collect(Collectors.toList());
			for (VDescuentoMensualSocio descMensual : lstVDescuentoMensualSocio) {
				UsrSocio socio = managerGestionSocios.buscarSocioById(descMensual.getCedulaSocio());
				if (socio.getCajaAhorro() == null)
					socio.setCajaAhorro(descMensual.getValorAhorro());
				else
					socio.setCajaAhorro(socio.getCajaAhorro().add(descMensual.getValorAhorro()));
				if (socio.getFondoCesantia() == null)
					socio.setFondoCesantia(descMensual.getValorCesantia());
				else
					socio.setFondoCesantia(socio.getFondoCesantia().add(descMensual.getValorCesantia()));
				try {
					managerGestionSocios.actualizarUsrSocio(socio);
				} catch (Exception e) {
					managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(),
							"ejecutarDescuentoMensual",
							"Se produjo un error al actualizar los valores de caja de ahorro y cesantia de: "
									+ socio.getCedulaSocio());
					JSFUtil.crearMensajeERROR(
							"Se produjo un error al actualizar los valores de caja de ahorro y cesantia de: "
									+ socio.getCedulaSocio());
				}
			}
			managerGestionDescuentos.ejecutarDescuentoMensual();
			JSFUtil.crearMensajeINFO("Proceso Ejecutado correctamente.");
			inicializarResumenDescuento();
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "ejecutarDescuentoMensual",
					"Ejecución de descuentos mensuales.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogGeneral(beanLogin.getCredencial(), this.getClass(), "ejecutarDescuentoMensual",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarNovedadEconomica() {
		try {
			objDescNovedade.setFechaRegistro(new Timestamp(new Date().getTime()));
			if (tipoNovedad == 1)
				objDescNovedade.setValor(objDescNovedade.getValor().multiply(new BigDecimal(-1)));
			managerGestionDescuentos.ingresarNovedadEconomica(objDescNovedade);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarNovedadEconomica",
					"Se ingreso tipo descuento id: " + objDescNovedade.getIdNovedad());
			JSFUtil.crearMensajeINFO("Ingreso correcto");
			inicializarNovedadesEconomicas();
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarNovedadEconomica",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public UsrTipoDescuento getObjUsrTipoDescuento() {
		return objUsrTipoDescuento;
	}

	public void setObjUsrTipoDescuento(UsrTipoDescuento objUsrTipoDescuento) {
		this.objUsrTipoDescuento = objUsrTipoDescuento;
	}

	public List<UsrTipoDescuento> getLstUsrTipoDescuento() {
		return lstUsrTipoDescuento;
	}

	public void setLstUsrTipoDescuento(List<UsrTipoDescuento> lstUsrTipoDescuento) {
		this.lstUsrTipoDescuento = lstUsrTipoDescuento;
	}

	public DescNovedade getObjDescNovedade() {
		return objDescNovedade;
	}

	public void setObjDescNovedade(DescNovedade objDescNovedade) {
		this.objDescNovedade = objDescNovedade;
	}

	public List<DescNovedade> getLstDescNovedade() {
		return lstDescNovedade;
	}

	public void setLstDescNovedade(List<DescNovedade> lstDescNovedade) {
		this.lstDescNovedade = lstDescNovedade;
	}

	public Long getTipoNovedad() {
		return tipoNovedad;
	}

	public void setTipoNovedad(Long tipoNovedad) {
		this.tipoNovedad = tipoNovedad;
	}

	public List<UsrSocioDescuentoFijo> getLstUsrSocioDescuentoFijo() {
		return lstUsrSocioDescuentoFijo;
	}

	public void setLstUsrSocioDescuentoFijo(List<UsrSocioDescuentoFijo> lstUsrSocioDescuentoFijo) {
		this.lstUsrSocioDescuentoFijo = lstUsrSocioDescuentoFijo;
	}

	public List<VDescuentoMensualSocio> getLstVDescuentoMensualSocio() {
		return lstVDescuentoMensualSocio;
	}

	public void setLstVDescuentoMensualSocio(List<VDescuentoMensualSocio> lstVDescuentoMensualSocio) {
		this.lstVDescuentoMensualSocio = lstVDescuentoMensualSocio;
	}

	public List<DesDescuentoMensuale> getLstDesDescuentoMensuale() {
		return lstDesDescuentoMensuale;
	}

	public void setLstDesDescuentoMensuale(List<DesDescuentoMensuale> lstDesDescuentoMensuale) {
		this.lstDesDescuentoMensuale = lstDesDescuentoMensuale;
	}

	public Long getMes() {
		return mes;
	}

	public void setMes(Long mes) {
		this.mes = mes;
	}

	public Long getAnio() {
		return anio;
	}

	public void setAnio(Long anio) {
		this.anio = anio;
	}

}