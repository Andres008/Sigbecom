package ec.com.controlador.gestionCreditos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import ec.com.model.gestionCreditos.ManagerGestionCredito;
import ec.com.model.gestionSistema.ManagerGestionSistema;
import ec.com.model.modulos.util.CorreoUtil;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named("controladorGestionCreditos")
@SessionScoped
public class ControladorGestionCreditos implements Serializable {

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
	ManagerGestionCredito managerGestionCredito;

	@EJB
	ManagerGestionSistema managerGestionSistema;

	private FinTipoCredito objFinTipoCredito;

	private FinRequisito objFinRequisito;

	private int banderaSolicitud;

	private List<FinTipoCredito> lstFinTipoCredito;

	private FinPrestamoSocio objFinPrestamoSocio;

	private List<FinPrestamoSocio> lstFinPrestamoSocio;

	private boolean skip;

	private UploadedFile file;

	private byte[] archivo, reportPdf;

	private String nombreArc, resolucion;

	private boolean valorChecked;

	private UsrSocio objSocio;

	public ControladorGestionCreditos() {

	}

	public void inicializarRevisionSolicitudes() {
		try {
			// Estado de creditos 1 solicitud ingresada
			long idEstadoCredito = new Long(1);
			lstFinPrestamoSocio = managerGestionCredito.buscarSolicitudesPrestamoByEstado(idEstadoCredito);
			objFinPrestamoSocio = new FinPrestamoSocio();
			objFinPrestamoSocio.setFinResolucionPrestamo(new FinResolucionPrestamo());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public void inicializarAprobacionSolicitudes() {
		try {
			// Estado de creditos 3 solicitud REVISADA
			long idEstadoCredito = new Long(3);
			lstFinPrestamoSocio = managerGestionCredito.buscarSolicitudesPrestamoByEstado(idEstadoCredito);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public void agregarEliminarPrestamo(FinPrestamoSocio prestamo) {
		if (valorChecked) {
			objFinPrestamoSocio.getFinPrestamoNovacions2().add(new FinPrestamoNovacion(objFinPrestamoSocio, prestamo));
		} else {
			for (FinPrestamoNovacion prestamoNovacion : objFinPrestamoSocio.getFinPrestamoNovacions2()) {
				if (prestamo.getIdPrestamoSocio() == prestamo.getIdPrestamoSocio()) {
					objFinPrestamoSocio.getFinPrestamoNovacions2().remove(prestamoNovacion);
					break;
				}
			}
		}
	}

	public void inicializarDescuentoPrestamo() {
		try {
			objFinPrestamoSocio = new FinPrestamoSocio();
			objFinPrestamoSocio.setFinTablaAmortizacions(new ArrayList<FinTablaAmortizacion>());
			lstFinPrestamoSocio = managerGestionCredito
					.buscarSolicitudesBySocio(beanLogin.getCredencial().getObjUsrSocio());
			lstFinPrestamoSocio = lstFinPrestamoSocio.stream()
					.filter(fecha -> fecha.getFechaPrimeraCouta().before(new Date())).collect(Collectors.toList());
			lstFinPrestamoSocio = lstFinPrestamoSocio.stream()
					.filter(fecha -> fecha.getFechaUltimaCuota().after(new Date())).collect(Collectors.toList());
			lstFinPrestamoSocio = lstFinPrestamoSocio.stream()
					.filter(prestamo -> cuotaVigenteByPrestamo(prestamo) != null).collect(Collectors.toList());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public void inicializarMisSolicitudes() {
		try {
			lstFinPrestamoSocio = managerGestionCredito
					.buscarSolicitudesBySocio(beanLogin.getCredencial().getObjUsrSocio());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public void inicializarHistorialCreditos() {
		try {
			lstFinPrestamoSocio = managerGestionCredito.buscarTodosSolicitudes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public String mesAnioLetras() {
		return ModelUtil.getMesAlfanumerico(new Date()) + " " + ModelUtil.getAnioActual();
	}

	public void inicializarAcreditacionSolicitudes() {
		try {
			// Estado de creditos 4 solicitud APROVADA
			long idEstadoCredito = new Long(4);
			lstFinPrestamoSocio = managerGestionCredito.buscarSolicitudesPrestamoByEstado(idEstadoCredito);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public void inicializarTipoPrestamo() {
		try {
			inicializarRequisito();
			objFinTipoCredito = new FinTipoCredito();
			objFinTipoCredito.setFinTipcrediRequisitos(new ArrayList<FinTipcrediRequisito>());
			lstFinTipoCredito = managerGestionCredito.buscarTodosTipoPrestamo();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inicializarSolicitudCredito() {
		objFinPrestamoSocio = new FinPrestamoSocio();
		objFinPrestamoSocio.setFinTipoCredito(new FinTipoCredito());
		objFinPrestamoSocio.setFinTipoSolicitud(new FinTipoSolicitud(1));
		objFinPrestamoSocio.setFinPrestamoRequisitos(new ArrayList<FinPrestamoRequisito>());
		objFinPrestamoSocio.setFinTablaAmortizacions(new ArrayList<FinTablaAmortizacion>());
	}

	public String inicializarNovacionCredito(FinPrestamoSocio prestamoSocio) {
		objFinPrestamoSocio = new FinPrestamoSocio();
		objFinPrestamoSocio.setFinTipoCredito(new FinTipoCredito());
		objFinPrestamoSocio.setFinTipoSolicitud(new FinTipoSolicitud(2));
		objFinPrestamoSocio.setFinPrestamoRequisitos(new ArrayList<FinPrestamoRequisito>());
		objFinPrestamoSocio.setFinTablaAmortizacions(new ArrayList<FinTablaAmortizacion>());
		objFinPrestamoSocio.setFinPrestamoNovacions2(new ArrayList<FinPrestamoNovacion>());
		objFinPrestamoSocio.getFinPrestamoNovacions2().add(new FinPrestamoNovacion(objFinPrestamoSocio, prestamoSocio));
		return "/modulos/prestamosFina/novacionCredito?faces-redirect=true";
	}

	public String unificacionPrestamos() {
		return "/modulos/prestamosFina/novacionCredito?faces-redirect=true";
	}

	public void inicializarUnificacionPrestamos() {
		objSocio = new UsrSocio();
		valorChecked = false;
		lstFinPrestamoSocio = new ArrayList<FinPrestamoSocio>();
		objFinPrestamoSocio = new FinPrestamoSocio();
		objFinPrestamoSocio.setFinTipoCredito(new FinTipoCredito());
		objFinPrestamoSocio.setFinTipoSolicitud(new FinTipoSolicitud(2));
		objFinPrestamoSocio.setFinPrestamoRequisitos(new ArrayList<FinPrestamoRequisito>());
		objFinPrestamoSocio.setFinTablaAmortizacions(new ArrayList<FinTablaAmortizacion>());
		objFinPrestamoSocio.setFinPrestamoNovacions2(new ArrayList<FinPrestamoNovacion>());
	}

	public void cargarRequisitoTipoCredito() {
		try {
			objFinTipoCredito.getFinTipcrediRequisitos().add(new FinTipcrediRequisito("A",
					managerGestionCredito.buscarRequisito(objFinRequisito), objFinTipoCredito));
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void buscarSolicitudesActivasSocio() {
		try {
			lstFinPrestamoSocio = managerGestionCredito.buscarSolicitudesBySocio(objSocio);
			lstFinPrestamoSocio = lstFinPrestamoSocio.stream()
					.filter(prestamo -> prestamo.getFinEstadoCredito().getIdEstadoCredito() == 5)
					.collect(Collectors.toList());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public void inicializarRequisito() {
		objFinRequisito = new FinRequisito();
	}

	public void ingresarTipoCredito() {
		try {
			objFinTipoCredito.setFechaInicial(new Date());
			objFinTipoCredito.setEstado("A");
			managerGestionCredito.ingresarTipoCredito(objFinTipoCredito);
			inicializarTipoPrestamo();
			JSFUtil.crearMensajeINFO("TIPO DE CREDITO CREADO CORRECTAMENTE.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void imprimirSolicitudCredito(FinPrestamoSocio prestamoSocio) {
		try {
			SimpleDateFormat formatodia = new SimpleDateFormat("dd");
			SimpleDateFormat formatoanio = new SimpleDateFormat("yyyy");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("apelativoSG",
					managerGestionSistema.buscarValorParametroNombre("APELATIVO SECRETARIO GENERAL"));
			parametros.put("nombreSocio", prestamoSocio.getUsrSocio().getGesPersona().getApellidos() + " "
					+ prestamoSocio.getUsrSocio().getGesPersona().getNombres());
			parametros.put("cedulaSocio", prestamoSocio.getUsrSocio().getCedulaSocio());
			parametros.put("telefono", prestamoSocio.getUsrSocio().getGesPersona().getTelefono());
			parametros.put("nombreSG", managerGestionSistema.buscarValorParametroNombre("NOMBRE SECRETARIO GENERAL"));
			parametros.put("primerTexto", "Yo, " + prestamoSocio.getUsrSocio().getGesPersona().getApellidos() + " "
					+ prestamoSocio.getUsrSocio().getGesPersona().getNombres()
					+ ", solicito de la manera más comedida, la autorización a quien corresponda para hacerme acreedor del credito de tipo "
					+ prestamoSocio.getFinTipoCredito().getNombre() + ".");
			parametros.put("segundoTexto",
					"Por un valor de $" + prestamoSocio.getValorPrestamo() + ", a " + prestamoSocio.getPlazoMeses()
							+ " meses plazo, a una tasa de interés del "
							+ prestamoSocio.getFinTipoCredito().getTasaInteres() + "%.");
			parametros.put("nroSolicitud", String.valueOf(prestamoSocio.getIdPrestamoSocio()));
			parametros.put("codEmpelado", String.valueOf(prestamoSocio.getUsrSocio().getIdSocio()));
			parametros.put("lugarFecha",
					"Ibarra, " + formatodia.format(prestamoSocio.getFechaSolicitud()) + " de "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaSolicitud()) + " del "
							+ formatoanio.format(prestamoSocio.getFechaSolicitud()));
			List<FinPrestamoSocio> lstPrestamo = new ArrayList<FinPrestamoSocio>();
			lstPrestamo.add(prestamoSocio);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lstPrestamo);
			File jasper = new File(beanLogin.getPathReporte() + "solicitudPrestamo.jasper");
			JasperPrint jasperPrint;
			try {
				jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, beanCollectionDataSource);
				archivo = JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				JSFUtil.crearMensajeERROR("Error al generar el reporte de solicitud. " + e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error al generar el reporte de solicitud. " + e.getMessage());
			e.printStackTrace();
		}

	}

	public void imprimirSolicitudSecretario(FinPrestamoSocio prestamoSocio) {
		try {
			SimpleDateFormat formatodia = new SimpleDateFormat("dd");
			SimpleDateFormat formatoanio = new SimpleDateFormat("yyyy");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("apelativoSG",
					managerGestionSistema.buscarValorParametroNombre("APELATIVO SECRETARIO GENERAL"));
			parametros.put("nombreSocio", prestamoSocio.getUsrSocio().getGesPersona().getApellidos() + " "
					+ prestamoSocio.getUsrSocio().getGesPersona().getNombres());
			parametros.put("cedulaSocio", prestamoSocio.getUsrSocio().getCedulaSocio());
			parametros.put("telefono", prestamoSocio.getUsrSocio().getGesPersona().getTelefono());
			parametros.put("nombreSG", managerGestionSistema.buscarValorParametroNombre("NOMBRE SECRETARIO GENERAL"));
			parametros.put("primerTexto",
					"Como es de su conocimiento conforme solicitud Nº " + prestamoSocio.getIdPrestamoSocio()
							+ ", con fecha " + formatodia.format(prestamoSocio.getFechaSolicitud()) + " de "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaSolicitud()) + " de "
							+ formatoanio.format(prestamoSocio.getFechaSolicitud())
							+ ", solicité se me otorgue un prestamo de $" + prestamoSocio.getValorPrestamo()
							+ ", mismo que será cancelado en " + prestamoSocio.getPlazoMeses()
							+ " cuotas mensuales de $" + prestamoSocio.getCuotaMensual() + ", apartir de "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaPrimeraCouta()) + " de "
							+ formatoanio.format(prestamoSocio.getFechaPrimeraCouta()) + " a "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaUltimaCuota()) + " de "
							+ formatoanio.format(prestamoSocio.getFechaUltimaCuota())+".");
			parametros.put("segundoTexto",
					"De ser aprobada mi solicitud de PRÉSTAMO, Autorizo en forma libre y voluntaria con carácter de irrevocable "
							+ "ante sus autoridades competentes se descuente los valores correspondientes conforme a las especificaciones "
							+ "del inciso anterior.");
			parametros.put("nroSolicitud", String.valueOf(prestamoSocio.getIdPrestamoSocio()));
			parametros.put("codEmpelado", String.valueOf(prestamoSocio.getUsrSocio().getIdSocio()));
			parametros.put("lugarFecha",
					"Ibarra, " + formatodia.format(prestamoSocio.getFechaSolicitud()) + " de "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaSolicitud()) + " del "
							+ formatoanio.format(prestamoSocio.getFechaSolicitud()));
			List<FinPrestamoSocio> lstPrestamo = new ArrayList<FinPrestamoSocio>();
			lstPrestamo.add(prestamoSocio);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lstPrestamo);
			File jasper = new File(beanLogin.getPathReporte() + "solicitudSecretario.jasper");
			JasperPrint jasperPrint;
			try {
				jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, beanCollectionDataSource);
				archivo = JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				JSFUtil.crearMensajeERROR("Error al generar el reporte de solicitud. " + e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error al generar el reporte de solicitud. " + e.getMessage());
			e.printStackTrace();
		}

	}

	public void imprimirSolicitudPresidente(FinPrestamoSocio prestamoSocio) {
		try {
			SimpleDateFormat formatodia = new SimpleDateFormat("dd");
			SimpleDateFormat formatoanio = new SimpleDateFormat("yyyy");
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("apelativoSG",
					managerGestionSistema.buscarValorParametroNombre("APELATIVO PRESIDENTE EJECUTIVO"));
			parametros.put("nombreSocio", prestamoSocio.getUsrSocio().getGesPersona().getApellidos() + " "
					+ prestamoSocio.getUsrSocio().getGesPersona().getNombres());
			parametros.put("cedulaSocio", prestamoSocio.getUsrSocio().getCedulaSocio());
			parametros.put("telefono", prestamoSocio.getUsrSocio().getGesPersona().getTelefono());
			parametros.put("nombreSG", managerGestionSistema.buscarValorParametroNombre("PRESIDENTE EJECUTIVO"));
			parametros.put("primerTexto",
					"Pongo en su conocimiiento que con fecha " + formatodia.format(prestamoSocio.getFechaSolicitud())
							+ " de " + ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaSolicitud()) + " de "
							+ formatoanio.format(prestamoSocio.getFechaSolicitud())
							+ ", solicité se me otorgue un préstamo de $" + prestamoSocio.getValorPrestamo()
							+ ", mismo que será cancelado en " + prestamoSocio.getPlazoMeses()
							+ " cuotas mensuales de $" + prestamoSocio.getCuotaMensual() + ", a partir de "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaPrimeraCouta()) + " de "
							+ formatoanio.format(prestamoSocio.getFechaPrimeraCouta()) + " a "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaUltimaCuota()) + " de "
							+ formatoanio.format(prestamoSocio.getFechaUltimaCuota()) + ".");
			parametros.put("segundoTexto", "Descuentos que serán realizados con mi autorización, "
					+ "en forma libre y voluntaria con carácter de irrevocable ante su Autoridad los valores correspondientes conforme a "
					+ "las especificaciones del inciso anterior, "
					+ "se entregarán al Comité de Empresa conforme a las normas vigentes y proceso interno de Emelnorte.");
			parametros.put("nroSolicitud", String.valueOf(prestamoSocio.getIdPrestamoSocio()));
			parametros.put("codEmpelado", String.valueOf(prestamoSocio.getUsrSocio().getIdSocio()));
			parametros.put("lugarFecha",
					"Ibarra, " + formatodia.format(prestamoSocio.getFechaSolicitud()) + " de "
							+ ModelUtil.getMesAlfanumerico(prestamoSocio.getFechaSolicitud()) + " del "
							+ formatoanio.format(prestamoSocio.getFechaSolicitud()));
			List<FinPrestamoSocio> lstPrestamo = new ArrayList<FinPrestamoSocio>();
			lstPrestamo.add(prestamoSocio);
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lstPrestamo);
			File jasper = new File(beanLogin.getPathReporte() + "solicitudPresiEjec.jasper");
			JasperPrint jasperPrint;
			try {
				jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, beanCollectionDataSource);
				archivo = JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (JRException e) {
				JSFUtil.crearMensajeERROR("Error al generar el reporte de solicitud. " + e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error al generar el reporte de solicitud. " + e.getMessage());
			e.printStackTrace();
		}

	}

	public String ingresarCreditoSocio() {
		try {
			if (objFinPrestamoSocio.getValorPrestamo().doubleValue() < objFinPrestamoSocio.getFinTipoCredito()
					.getValorMinimo().doubleValue())
				throw new Exception("Atención, el monto $" + objFinPrestamoSocio.getValorPrestamo()
						+ " es menor al autorizado para este tipo de credito $"
						+ objFinPrestamoSocio.getFinTipoCredito().getValorMinimo() + ".");
			for (FinPrestamoRequisito requisito : objFinPrestamoSocio.getFinPrestamoRequisitos()) {
				if (ModelUtil.isEmpty(requisito.getUrl()))
					throw new Exception(
							"Atención, requisitos no ingresados, favor complete los requisitos para continuar.");
			}
			calcularCuotaMensualPrestamo();
			if (objSocio == null)
				objFinPrestamoSocio.setUsrSocio(beanLogin.getCredencial().getObjUsrSocio());
			else
				objFinPrestamoSocio.setUsrSocio(objSocio);
			objFinPrestamoSocio.setFechaSolicitud(new Date());
			objFinPrestamoSocio
					.setFechaPrimeraCouta(objFinPrestamoSocio.getFinTablaAmortizacions().get(0).getFechaPago());
			objFinPrestamoSocio.setFechaUltimaCuota(objFinPrestamoSocio.getFinTablaAmortizacions()
					.get(objFinPrestamoSocio.getFinTablaAmortizacions().size() - 1).getFechaPago());
			objFinPrestamoSocio.setSaldoCapital(objFinPrestamoSocio.getValorPrestamo());
			objFinPrestamoSocio.setFinEstadoCredito(new FinEstadoCredito(1));
			objFinPrestamoSocio.setValorRecibido(calcularValorRecibirNovacion(objFinPrestamoSocio));
			objFinPrestamoSocio.setCuotasPagadas(new BigDecimal(0));
			objFinPrestamoSocio.setFinTablaAmortizacions(new ArrayList<FinTablaAmortizacion>());
			managerGestionCredito.ingresarCreditoSocio(objFinPrestamoSocio);
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarCreditoSocio",
					"Se ingreso correctamente credito Nº" + objFinPrestamoSocio.getIdPrestamoSocio());
			JSFUtil.crearMensajeINFO("Se creo exitosamente la solicitud.");
			return "/modulos/prestamosFina/misCreditos?faces-redirect=true";
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarCreditoSocio",
					e.getMessage());
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			return "";
		}
	}

	public FinTablaAmortizacion cuotaVigenteByPrestamo(FinPrestamoSocio objPrestamo) {
		List<FinTablaAmortizacion> lstAmortiza = objPrestamo.getFinTablaAmortizacions().stream()
				.filter(amortiza -> amortiza.getFinEstadoCuota().getIdEstadoCuota() == 1).collect(Collectors.toList());
		lstAmortiza = lstAmortiza.stream().filter(anio -> anio.getFechaPago().before(new Date()))
				.collect(Collectors.toList());
		if (lstAmortiza.size() == 1)
			return lstAmortiza.get(0);
		return null;
	}

	private BigDecimal calcularValorRecibirNovacion(FinPrestamoSocio objFinPrestamoSocio2) {
		if (objFinPrestamoSocio2.getFinTipoSolicitud().getIdTipoSolicitud() == 1)
			return objFinPrestamoSocio.getValorPrestamo();
		else {
			BigDecimal valorCapitalPendiente = new BigDecimal(0);
			for (FinPrestamoNovacion novacion : objFinPrestamoSocio2.getFinPrestamoNovacions2()) {
				valorCapitalPendiente = valorCapitalPendiente.add(novacion.getFinPrestamoSocio1().getSaldoCapital());
			}
			return objFinPrestamoSocio2.getValorPrestamo().subtract(valorCapitalPendiente);
		}

	}

	public void inactivarTipoCredito(FinTipoCredito objTipoCreditoAux) {
		try {
			objTipoCreditoAux.setFechaFinal(new Date());
			objTipoCreditoAux.setEstado("I");
			managerGestionCredito.actualizarTipoCredito(objTipoCreditoAux);
			inicializarTipoPrestamo();
			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "incativarTipoCredito",
					"Se incativo tipo credito. " + objTipoCreditoAux.getIdTipoCredito());
			JSFUtil.crearMensajeINFO("Se inactivo correctamente.");
		} catch (Exception e) {
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "incativarTipoCredito",
					e.getMessage());
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void ingresarRequisito() {
		try {
			objFinRequisito.setIdRequisito(0);
			managerGestionCredito.ingresarRequisito(objFinRequisito);
			JSFUtil.crearMensajeINFO("Requisito creado correctamente");
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "ingresarRequisito",
					"Se creo nuevo requisito");
			inicializarRequisito();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarRequisito",
					e.getMessage());
			e.printStackTrace();
		}
	}

	public void cargarAdjuntoRequisito(FileUploadEvent event) {
		this.file = event.getFile();
	}

	public boolean verificarNovacion(FinPrestamoSocio objPrestamo) {

		if (objPrestamo.getCuotasPagadas().intValue() >= (objPrestamo.getPlazoMeses().intValue() / 2)
				&& objPrestamo.getFinEstadoCredito().getIdEstadoCredito() == 5)
			return true;
		return false;
	}

	public void cargarAdjuntoRequisitoPrestamo(FinPrestamoRequisito requisito) {
		if (this.file != null) {
			SimpleDateFormat formato = new SimpleDateFormat("ddMMyyymmss");
			objFinPrestamoSocio.getFinPrestamoRequisitos().forEach(req -> {
				if (req.getFinRequisito().getIdRequisito() == requisito.getFinRequisito().getIdRequisito()) {
					try {
						req.setUrl(ModelUtil.guardarArchivo(this.file.getInputStream(),
								requisito.getFinRequisito().getNombre() + formato.format(new Date()),
								managerGestionSistema.buscarValorParametroNombre("PATH_REQUISITOS_PRESTAMO")
										+ beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio() + "\\",
								ModelUtil.obtenerExtensionArchivo(this.file.getFileName())));
						JSFUtil.crearMensajeINFO("Archivo Cargado Correctamente");
					} catch (Exception e) {
						JSFUtil.crearMensajeERROR(e.getMessage());
						e.printStackTrace();
					}

				}
			});
		}
		file = null;
	}

	public void cargarTipoCredito() {
		try {
			objFinPrestamoSocio.setFinTipoCredito(
					managerGestionCredito.buscarTipoCreditoId(objFinPrestamoSocio.getFinTipoCredito()));
			objFinPrestamoSocio.setFinPrestamoRequisitos(new ArrayList<FinPrestamoRequisito>());
			objFinPrestamoSocio.getFinTipoCredito().getFinTipcrediRequisitos().forEach(requisito -> {
				FinPrestamoRequisito requisitos = new FinPrestamoRequisito();
				requisitos.setFinPrestamoSocio(objFinPrestamoSocio);
				requisitos.setFinRequisito(requisito.getFinRequisito());
				requisitos.setEstado("A");
				objFinPrestamoSocio.getFinPrestamoRequisitos().add(requisitos);
			});
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void cargarFinPrestamoSocio(FinPrestamoSocio prestamoSocio) {
		try {
			objFinPrestamoSocio = managerGestionCredito.buscarSolicitudPrestamoById(prestamoSocio.getIdPrestamoSocio());
			if (objFinPrestamoSocio.getFinResolucionPrestamo() == null)
				objFinPrestamoSocio.setFinResolucionPrestamo(new FinResolucionPrestamo());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error al buscar Prestamo.");
		}

	}

	public void cargarFinPrestamoSocioAmortizacion(FinPrestamoSocio prestamoSocio) {
		try {
			objFinPrestamoSocio = managerGestionCredito.buscarSolicitudPrestamoById(prestamoSocio.getIdPrestamoSocio());
			if (objFinPrestamoSocio.getFinTablaAmortizacions().size() == 0)
				objFinPrestamoSocio.setFinTablaAmortizacions(ModelUtil.calcularTablaAmortizacion(objFinPrestamoSocio));
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("Error al cargar solicitud credito.");
		}
	}

	public void aceptarRevisionSolicitudPrestamo() {
		objFinPrestamoSocio.getFinResolucionPrestamo().setFechaRevision(new Date());
		objFinPrestamoSocio.getFinResolucionPrestamo()
				.setIdUsuarioRevisor(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		try {
			managerGestionCredito.ingresarResolucionCredito(objFinPrestamoSocio.getFinResolucionPrestamo());
			objFinPrestamoSocio.getFinEstadoCredito().setIdEstadoCredito(new Long(3));
			managerGestionCredito.actualizarSolicitudCredito(objFinPrestamoSocio);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo",
					"Se acepto solicitud prestamo: " + objFinPrestamoSocio.getIdPrestamoSocio());
			JSFUtil.crearMensajeINFO("Se acepto la solicitud de prestamo.");
			correoUtil.enviarCorreoElectronico(objFinPrestamoSocio.getUsrSocio().getGesPersona().getEmail(),
					"Revisión Aprobada", "Atención su solicitud de prestamo fue revisada satisfactoriamente");
			inicializarRevisionSolicitudes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo", e.getMessage());
		}
	}

	public void aprobarSolicitudPrestamo() {
		objFinPrestamoSocio.getFinResolucionPrestamo().setFechaAprobacion(new Date());
		objFinPrestamoSocio.getFinResolucionPrestamo()
				.setIdUsuarioAprobador(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		try {
			managerGestionCredito.actualizarResolucionCredito(objFinPrestamoSocio.getFinResolucionPrestamo());
			objFinPrestamoSocio.getFinEstadoCredito().setIdEstadoCredito(new Long(4));
			managerGestionCredito.actualizarSolicitudCredito(objFinPrestamoSocio);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo",
					"Se acepto solicitud prestamo: " + objFinPrestamoSocio.getIdPrestamoSocio());
			JSFUtil.crearMensajeINFO("Se aprobo la solicitud de prestamo.");
			correoUtil.enviarCorreoElectronico(objFinPrestamoSocio.getUsrSocio().getGesPersona().getEmail(),
					"Aprobación de Prestamo", "Atención su solicitud de prestamo fue aprobada.");
			inicializarAprobacionSolicitudes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo", e.getMessage());
		}
	}

	public void ejecutarDescuento() {
		lstFinPrestamoSocio.forEach(prestamo -> {
			FinTablaAmortizacion amortizacionActual = cuotaVigenteByPrestamo(prestamo);
			prestamo.setSaldoCapital(amortizacionActual.getSaldoCapital());
			prestamo.setCuotasPagadas(prestamo.getCuotasPagadas().add(new BigDecimal(1)));
			prestamo.getFinTablaAmortizacions().forEach(amortizacion -> {
				if (amortizacion.getNumeroCuota().intValue() == prestamo.getCuotasPagadas().intValue()) {
					amortizacion.setFinEstadoCuota(new FinEstadoCuota(3));
					FinCuotasDescontada cuotaDescontada = new FinCuotasDescontada();
					cuotaDescontada.setFechaEjecucionDescuento(new Date());
					cuotaDescontada.setFinTablaAmortizacion(amortizacion);
					if (prestamo.getCuotasPagadas().intValue() == prestamo.getPlazoMeses().intValue())
						prestamo.setFinEstadoCredito(new FinEstadoCredito(6));
					try {
						managerGestionCredito.ingresarCuotaDescontada(cuotaDescontada);
					} catch (Exception e) {
						JSFUtil.crearMensajeERROR(e.getMessage());
					}
				}
				if (amortizacion.getNumeroCuota().intValue() == prestamo.getCuotasPagadas().add(new BigDecimal(1))
						.intValue())
					amortizacion.setFinEstadoCuota(new FinEstadoCuota(1));

			});
			try {
				managerGestionCredito.actualizarSolicitudCredito(prestamo);
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("Error al descontar credito: " + prestamo.getIdPrestamoSocio());
			}
		});
		JSFUtil.crearMensajeINFO("Descuento generado Correctamente.");
		inicializarDescuentoPrestamo();
	}

	public void registrarAcreditacionPrestamo() {
		try {
			managerGestionCredito.actualizarResolucionCredito(objFinPrestamoSocio.getFinResolucionPrestamo());
			objFinPrestamoSocio.getFinEstadoCredito().setIdEstadoCredito(new Long(5));
			objFinPrestamoSocio.setFinTablaAmortizacions(ModelUtil.calcularTablaAmortizacion(objFinPrestamoSocio));
			objFinPrestamoSocio
					.setFechaPrimeraCouta(objFinPrestamoSocio.getFinTablaAmortizacions().get(0).getFechaPago());
			objFinPrestamoSocio.setFechaUltimaCuota(objFinPrestamoSocio.getFinTablaAmortizacions()
					.get(objFinPrestamoSocio.getFinTablaAmortizacions().size() - 1).getFechaPago());
			objFinPrestamoSocio.setValorRecibido(calcularValorRecibirNovacion(objFinPrestamoSocio));
			if (objFinPrestamoSocio.getFinTipoSolicitud().getIdTipoSolicitud() == 2)

				objFinPrestamoSocio.getFinPrestamoNovacions2().forEach(prestamo -> {
					prestamo.getFinPrestamoSocio1().setFinEstadoCredito(new FinEstadoCredito(8));
					try {
						managerGestionCredito.actualizarSolicitudCredito(prestamo.getFinPrestamoSocio1());
					} catch (Exception e) {
						JSFUtil.crearMensajeERROR("Error al actualizar estado credito.");
					}
				});
			managerGestionCredito.actualizarSolicitudCredito(objFinPrestamoSocio);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "registrarAcreditacionPrestamo",
					"Se acredito solicitud prestamo: " + objFinPrestamoSocio.getIdPrestamoSocio());
			JSFUtil.crearMensajeINFO("Se aprobo la solicitud de prestamo.");
			correoUtil.enviarCorreoElectronico(objFinPrestamoSocio.getUsrSocio().getGesPersona().getEmail(),
					"Acreditación de Prestamo", "Atención su solicitud de prestamo fue acreditada. "
							+ objFinPrestamoSocio.getFinResolucionPrestamo().getResolucion());
			inicializarAcreditacionSolicitudes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"registrarAcreditacionPrestamo", e.getMessage());
		}
	}

	public void negarSolicitudPrestamo() {
		objFinPrestamoSocio.getFinResolucionPrestamo().setFechaAprobacion(new Date());
		objFinPrestamoSocio.getFinResolucionPrestamo()
				.setIdUsuarioAprobador(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		try {
			managerGestionCredito.actualizarResolucionCredito(objFinPrestamoSocio.getFinResolucionPrestamo());
			objFinPrestamoSocio.getFinEstadoCredito().setIdEstadoCredito(new Long(2));
			managerGestionCredito.actualizarSolicitudCredito(objFinPrestamoSocio);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo",
					"Se nego solicitud prestamo: " + objFinPrestamoSocio.getIdPrestamoSocio());
			JSFUtil.crearMensajeINFO("Se acepto la solicitud de prestamo.");
			correoUtil.enviarCorreoElectronico(objFinPrestamoSocio.getUsrSocio().getGesPersona().getEmail(),
					"Solicitud Negada", "Atención su solicitud de prestamo fue negada con la siguente observación: "
							+ objFinPrestamoSocio.getFinResolucionPrestamo().getResolucion());
			inicializarAprobacionSolicitudes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo", e.getMessage());
		}
	}

	public void negarRevisionSolicitudPrestamo() {
		objFinPrestamoSocio.getFinResolucionPrestamo().setFechaRevision(new Date());
		objFinPrestamoSocio.getFinResolucionPrestamo()
				.setIdUsuarioRevisor(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		try {
			managerGestionCredito.ingresarResolucionCredito(objFinPrestamoSocio.getFinResolucionPrestamo());
			objFinPrestamoSocio.getFinEstadoCredito().setIdEstadoCredito(new Long(2));
			managerGestionCredito.actualizarSolicitudCredito(objFinPrestamoSocio);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo",
					"Se acepto solicitud prestamo: " + objFinPrestamoSocio.getIdPrestamoSocio());
			JSFUtil.crearMensajeINFO("Se nego la solicitud de prestamo.");
			correoUtil.enviarCorreoElectronico(objFinPrestamoSocio.getUsrSocio().getGesPersona().getEmail(),
					"Solicitud Negada", "Atención su solicitud de prestamo fue negada con la siguente observación: "
							+ objFinPrestamoSocio.getFinResolucionPrestamo().getResolucion());
			inicializarRevisionSolicitudes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(),
					"aceptarRevisionSolicitudPrestamo", e.getMessage());
		}
	}

	public void calcularCuotaMensualPrestamo() {
		if (objFinPrestamoSocio.getValorPrestamo() != null && objFinPrestamoSocio.getPlazoMeses() != null)
			if (objFinPrestamoSocio.getValorPrestamo().doubleValue() > 0
					&& objFinPrestamoSocio.getPlazoMeses().doubleValue() > 0) {
				objFinPrestamoSocio.setCuotaMensual(
						ModelUtil.calcularCuotaMensual(objFinPrestamoSocio.getValorPrestamo().doubleValue(),
								objFinPrestamoSocio.getFinTipoCredito().getTasaInteres().doubleValue(),
								objFinPrestamoSocio.getPlazoMeses().doubleValue()));
				objFinPrestamoSocio.setFinTablaAmortizacions(ModelUtil.calcularTablaAmortizacion(objFinPrestamoSocio));
			}
	}

	/*
	 * MÉTODO DEL PANEL WIZARD
	 */
	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // resetear en caso de que regrese
			return "solicitudCredito";
		} else {
			return event.getNewStep();
		}
	}

	/***
	 * Select Items para Selec One Menu
	 * 
	 * @return
	 */
	public List<SelectItem> lstSiRequisitos() {
		List<SelectItem> lstSiGenero = new ArrayList<SelectItem>();
		try {
			for (FinRequisito finRequisito : managerGestionCredito.buscarTodosRequisitos()) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(finRequisito.getNombre());
				siCivil.setValue(finRequisito.getIdRequisito());
				lstSiGenero.add(siCivil);
			}
			return lstSiGenero;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * Select Items para Selec One Menu
	 * 
	 * @return
	 */
	public List<SelectItem> lstSiTipoSolicitud() {
		List<SelectItem> lstSiGenero = new ArrayList<SelectItem>();
		try {
			for (FinTipoSolicitud finRequisito : managerGestionCredito.buscarTodosTiposSolicitud()) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(finRequisito.getTipoSolicitud());
				siCivil.setValue(finRequisito.getIdTipoSolicitud());
				lstSiGenero.add(siCivil);
			}
			return lstSiGenero;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unused")
	public void visualizacionArchivo(String pathArchivo) {
		String arc[] = pathArchivo.split("/");
		nombreArc = arc[arc.length - 1];
		if (pathArchivo == null)
			JSFUtil.crearMensajeERROR("No se ha cargado la ruta.");
		else {
			try {
				archivo = Files.readAllBytes(Paths.get(pathArchivo));
			} catch (IOException e) {
				e.printStackTrace();
				JSFUtil.crearMensajeERROR(e.getMessage());
			}
		}
	}

	@SuppressWarnings("deprecation")
	public StreamedContent reportePDF() {
		if (archivo != null) {
			InputStream fis = new ByteArrayInputStream(archivo);
			return new DefaultStreamedContent(fis, "application/pdf; charset=UTF-8", nombreArc);
		} else {
			return null;
		}
	}

	/***
	 * Select Items para Selec One Menu
	 * 
	 * @return
	 */
	public List<SelectItem> lstSiTipoCredito() {
		List<SelectItem> lstSiGenero = new ArrayList<SelectItem>();
		try {
			for (FinTipoCredito finTipoCredito : managerGestionCredito.buscarTodosTipoCredito().stream()
					.filter(tipCredito -> tipCredito.getEstado().equals("A")).collect(Collectors.toList())) {
				SelectItem siCivil = new SelectItem();
				siCivil.setLabel(finTipoCredito.getNombre());
				siCivil.setValue(finTipoCredito.getIdTipoCredito());
				lstSiGenero.add(siCivil);
			}
			return lstSiGenero;
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public FinTipoCredito getObjFinTipoCredito() {
		return objFinTipoCredito;
	}

	public void setObjFinTipoCredito(FinTipoCredito objFinTipoCredito) {
		this.objFinTipoCredito = objFinTipoCredito;
	}

	public List<FinTipoCredito> getLstFinTipoCredito() {
		return lstFinTipoCredito;
	}

	public void setLstFinTipoCredito(List<FinTipoCredito> lstFinTipoCredito) {
		this.lstFinTipoCredito = lstFinTipoCredito;
	}

	public FinRequisito getObjFinRequisito() {
		return objFinRequisito;
	}

	public void setObjFinRequisito(FinRequisito objFinRequisito) {
		this.objFinRequisito = objFinRequisito;
	}

	public FinPrestamoSocio getObjFinPrestamoSocio() {
		return objFinPrestamoSocio;
	}

	public void setObjFinPrestamoSocio(FinPrestamoSocio objFinPrestamoSocio) {
		this.objFinPrestamoSocio = objFinPrestamoSocio;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public List<FinPrestamoSocio> getLstFinPrestamoSocio() {
		return lstFinPrestamoSocio;
	}

	public void setLstFinPrestamoSocio(List<FinPrestamoSocio> lstFinPrestamoSocio) {
		this.lstFinPrestamoSocio = lstFinPrestamoSocio;
	}

	public int getBanderaSolicitud() {
		return banderaSolicitud;
	}

	public void setBanderaSolicitud(int banderaSolicitud) {
		this.banderaSolicitud = banderaSolicitud;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public UsrSocio getObjSocio() {
		return objSocio;
	}

	public void setObjSocio(UsrSocio objSocio) {
		this.objSocio = objSocio;
	}

	public boolean getValorChecked() {
		return valorChecked;
	}

	public void setValorChecked(boolean valorChecked) {
		this.valorChecked = valorChecked;
	}

	public byte[] getReportPdf() {
		return reportPdf;
	}

	public void setReportPdf(byte[] reportPdf) {
		this.reportPdf = reportPdf;
	}

}