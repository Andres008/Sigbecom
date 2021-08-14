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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.dao.entity.DescNovedade;
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
import ec.com.model.dao.entity.UsrTipoDescuento;
import ec.com.model.gestionCreditos.ManagerGestionCredito;
import ec.com.model.gestionDescuentos.ManagerGestionDescuentos;
import ec.com.model.gestionSistema.ManagerGestionSistema;
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
	CorreoUtil correoUtil;

	@EJB
	ManagerGestionDescuentos managerGestionDescuentos;

	private UsrTipoDescuento objUsrTipoDescuento;

	private List<UsrTipoDescuento> lstUsrTipoDescuento;

	private DescNovedade objDescNovedade;

	private List<DescNovedade> lstDescNovedade;

	public ControladorGestionDescuentos() {

	}

	public void inicializarNovedadesEconomicas() {
		objDescNovedade= new DescNovedade();
		objDescNovedade.setUsrSocio1(beanLogin.getCredencial().getObjUsrSocio());
		objDescNovedade.setUsrSocio2(new UsrSocio());
		objDescNovedade.setDescEstadoDescuento(new DescEstadoDescuento(1));
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
	
	public void ingresarNovedadEconomica() {
		try {
			objDescNovedade.setFechaRegistro(new Timestamp(new Date().getTime()));
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

}