package ec.com.model.gestionCreditos;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.FinCuotasDescontada;
import ec.com.model.dao.entity.FinPrestamoSocio;
import ec.com.model.dao.entity.FinRequisito;
import ec.com.model.dao.entity.FinResolucionPrestamo;
import ec.com.model.dao.entity.FinTablaAmortizacion;
import ec.com.model.dao.entity.FinTipoCredito;
import ec.com.model.dao.entity.FinTipoSolicitud;
import ec.com.model.dao.entity.GesDiscapacidadPersona;
import ec.com.model.dao.entity.GesPariente;
import ec.com.model.dao.entity.GesTipoSangre;
import ec.com.model.dao.entity.UsrAgencia;
import ec.com.model.dao.entity.UsrArea;
import ec.com.model.dao.entity.UsrCanton;
import ec.com.model.dao.entity.UsrCargo;
import ec.com.model.dao.entity.UsrConsanguinidad;
import ec.com.model.dao.entity.UsrInstitucionBancaria;
import ec.com.model.dao.entity.UsrInstruccion;
import ec.com.model.dao.entity.UsrLicenciaSocio;
import ec.com.model.dao.entity.UsrParroquia;
import ec.com.model.dao.entity.UsrProvincia;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.dao.entity.UsrTipoCuenta;
import ec.com.model.dao.entity.UsrTipoInstruccion;
import ec.com.model.dao.entity.UsrTipoLicencia;
import ec.com.model.dao.entity.UsrTipoVivienda;
import ec.com.model.dao.manager.ManagerDAOSegbecom;
import ec.com.model.modulos.util.JSFUtil;

/**
 * Session Bean implementation class ManagerGestionSocios
 */
@Stateless(mappedName = "managerGestionCredito")
@LocalBean
public class ManagerGestionCredito {

	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;

	/**
	 * Default constructor.
	 */
	public ManagerGestionCredito() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<FinTipoCredito> buscarTodosTipoPrestamo() throws Exception {
		try {
			List<FinTipoCredito> resultado = managerDAOSegbecom.findAll(FinTipoCredito.class,
					"o.estado ASC, o.nombre ASC");
			resultado.forEach(tipoCredito -> {
				tipoCredito.getFinTipcrediRequisitos().forEach(tipoRequisito -> {
					tipoRequisito.getFinRequisito().getNombre();
				});
			});
			return resultado;
		} catch (Exception e) {
			throw new Exception("Error al buscar listado de tipo creditos");
		}
	}

	@SuppressWarnings("unchecked")
	public List<FinRequisito> buscarTodosRequisitos() throws Exception {
		try {
			return managerDAOSegbecom.findAll(FinRequisito.class, "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar requisitos");
		}
	}

	public void ingresarRequisito(FinRequisito objFinRequisito) throws Exception {
		try {
			managerDAOSegbecom.insertar(objFinRequisito);
		} catch (Exception e) {
			throw new Exception("Error al ingresar requisito." + e.getMessage());
		}

	}

	public FinRequisito buscarRequisito(FinRequisito objFinRequisito) throws Exception {
		FinRequisito resultado = (FinRequisito) managerDAOSegbecom.findById(FinRequisito.class,
				objFinRequisito.getIdRequisito());
		if (resultado == null)
			throw new Exception("Requisito no encontrado, comuniquise con el administrador");
		return resultado;
	}

	public void ingresarTipoCredito(FinTipoCredito objFinTipoCredito) throws Exception {
		try {
			managerDAOSegbecom.insertar(objFinTipoCredito);
		} catch (Exception e) {
			throw new Exception("Error al ingresar tipo credito. " + e.getMessage());
		}

	}

	public void actualizarTipoCredito(FinTipoCredito objTipoCreditoAux) throws Exception {
		try {
			managerDAOSegbecom.actualizar(objTipoCreditoAux);
		} catch (Exception e) {
			throw new Exception("Error al actualizar Tipo Credito.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<FinTipoSolicitud> buscarTodosTiposSolicitud() throws Exception {
		try {
			return managerDAOSegbecom.findAll(FinTipoSolicitud.class, "o.idTipoSolicitud ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo solicitud");
		}
	}

	public List<FinTipoCredito> buscarTodosTipoCredito() throws Exception {
		try {
			return managerDAOSegbecom.findAll(FinTipoCredito.class, "o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo credito.");
		}
	}

	public FinTipoCredito buscarTipoCreditoId(FinTipoCredito finTipoCredito) throws Exception {
		try {
			FinTipoCredito tipoCredito = (FinTipoCredito) managerDAOSegbecom.findById(finTipoCredito.getClass(),
					finTipoCredito.getIdTipoCredito());
			tipoCredito.getFinTipcrediRequisitos().forEach(tipoCredi -> {
				tipoCredi.getFinRequisito().getNombre();
			});

			return tipoCredito;
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo de credito");
		}
	}

	public void ingresarCreditoSocio(FinPrestamoSocio objFinPrestamoSocio) throws Exception {
		try {
			managerDAOSegbecom.insertar(objFinPrestamoSocio);
		} catch (Exception e) {
			throw new Exception("Error al ingresar prestamo.");
		}
	}

	public List<FinPrestamoSocio> buscarSolicitudesPrestamoByEstado(long idEstadoCredito) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<FinPrestamoSocio> lstFinPrestamoSocio = managerDAOSegbecom.findWhere(FinPrestamoSocio.class,
					"o.finEstadoCredito.idEstadoCredito=" + idEstadoCredito, "o.idPrestamoSocio ASC");
			lstFinPrestamoSocio.forEach(prestamoSocio -> {
				prestamoSocio.getFinPrestamoRequisitos().forEach(requisitos -> {
					requisitos.getId();
				});
				prestamoSocio.getFinAccionPrestamos().forEach(accion -> {
					accion.getObservacion();
				});
				prestamoSocio.getFinTablaAmortizacions().forEach(table -> {
					table.getIdTablaAmortizacion();
				});
			});
			return lstFinPrestamoSocio;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al listar prestamos socios, comuniquese con el administrador.");
		}

	}

	public void ingresarResolucionCredito(FinResolucionPrestamo finResolucionPrestamo) throws Exception {
		try {
			managerDAOSegbecom.insertar(finResolucionPrestamo);
		} catch (Exception e) {
			throw new Exception("Error al ingresar resolución.");
		}

	}

	public void actualizarSolicitudCredito(FinPrestamoSocio objFinPrestamoSocio) throws Exception {
		try {
			managerDAOSegbecom.actualizar(objFinPrestamoSocio);
		} catch (Exception e) {
			throw new Exception("Error al actualizar la solicitud credito.");
		}

	}

	public void actualizarResolucionCredito(FinResolucionPrestamo finResolucionPrestamo) throws Exception {
		try {
			managerDAOSegbecom.actualizar(finResolucionPrestamo);
		} catch (Exception e) {
			throw new Exception("Error al ingresar resolución.");
		}

	}

	public FinPrestamoSocio buscarSolicitudPrestamoById(long idPrestamoSocio) throws Exception {
		FinPrestamoSocio objPrestamo = (FinPrestamoSocio) managerDAOSegbecom.findById(FinPrestamoSocio.class,
				idPrestamoSocio);
		objPrestamo.getFinTablaAmortizacions().forEach(table -> {
			table.getFinEstadoCuota().getIdEstadoCuota();
		});
		objPrestamo.getFinPrestamoNovacions1().forEach(creditos -> {
			creditos.getFinPrestamoSocio2().getIdPrestamoSocio();
			creditos.getFinPrestamoSocio1().getIdPrestamoSocio();
		});
		objPrestamo.getFinPrestamoNovacions2().forEach(creditos -> {
			creditos.getFinPrestamoSocio2().getIdPrestamoSocio();
			creditos.getFinPrestamoSocio1().getIdPrestamoSocio();
		});
		objPrestamo.getFinAccionPrestamos().forEach(accion -> accion.getObservacion());
		return objPrestamo;
	}

	public List<FinPrestamoSocio> buscarSolicitudesBySocio(UsrSocio objUsrSocio) throws Exception {
		@SuppressWarnings("unchecked")
		List<FinPrestamoSocio> lstFinPrestamoSocio = managerDAOSegbecom.findWhere(FinPrestamoSocio.class,
				"o.usrSocio.cedulaSocio='" + objUsrSocio.getCedulaSocio() + "'", "o.idPrestamoSocio DESC");
		lstFinPrestamoSocio.forEach(prestamoSocio -> {
			prestamoSocio.getFinPrestamoRequisitos().forEach(requisitos -> {
				requisitos.getId();
			});
			prestamoSocio.getFinTablaAmortizacions().forEach(requisitos -> {
				requisitos.getIdTablaAmortizacion();
			});
		});
		return lstFinPrestamoSocio;
	}

	@SuppressWarnings("unchecked")
	public List<FinPrestamoSocio> buscarPrestamosVigentes() throws Exception {
		List<FinPrestamoSocio> lstPrestamos = managerDAOSegbecom.findWhere(FinPrestamoSocio.class,
				"o.finEstadoCredito.idEstadoCredito=5", "o.idPrestamoSocio");
		lstPrestamos.forEach(prestamo -> {
			prestamo.getFinTablaAmortizacions().forEach(amortiza -> {
				amortiza.getIdTablaAmortizacion();
			});
		});
		return lstPrestamos;
	}

	public void ingresarCuotaDescontada(FinCuotasDescontada cuotaDescontada) throws Exception {
		try {
			managerDAOSegbecom.insertar(cuotaDescontada);
		} catch (Exception e) {
			throw new Exception("Error al ingresar cuota a descontar, comuniquese con el administrador.");
		}

	}

	@SuppressWarnings("unchecked")
	public List<FinPrestamoSocio> buscarTodosSolicitudes() throws Exception {
		return managerDAOSegbecom.findAll(FinPrestamoSocio.class, "o.idPrestamoSocio DESC");
	}

	@SuppressWarnings("unchecked")
	public List<FinPrestamoSocio> buscarSolicitudesVigentes() throws Exception {
		return managerDAOSegbecom.findWhere(FinPrestamoSocio.class, "o.finEstadoCredito.idEstadoCredito=5",
				"o.idPrestamoSocio DESC");
	}

	@SuppressWarnings("unchecked")
	public List<FinTablaAmortizacion> buscarTablaAmortizacionByIdCredito(long idPrestamoSocio) throws Exception {
		try {
			return managerDAOSegbecom.findWhere(FinTablaAmortizacion.class,
					"o.finPrestamoSocio.idPrestamoSocio=" + idPrestamoSocio, "o.numeroCuota ASC");
		} catch (Exception e) {
			throw new Exception("Error al consultar tabla de amortización. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<FinTablaAmortizacion> buscarCuotasPendientesByTipoCredito(FinTipoCredito objTipoCredito)
			throws Exception {
		return managerDAOSegbecom.findWhere(FinTablaAmortizacion.class,
				"o.finEstadoCuota.idEstadoCuota in (1,2) and o.finPrestamoSocio.finTipoCredito.idTipoCredito="
						+ objTipoCredito.getIdTipoCredito(),
				null);
	}

	public void actualizarTablaAmortizacion(FinTablaAmortizacion finTablaAmortizacion) throws Exception {
		managerDAOSegbecom.actualizar(finTablaAmortizacion);

	}

	@SuppressWarnings("unchecked")
	public List<FinTablaAmortizacion> busarCuotaPagadaMesAnio(int anio, int mes) throws Exception {
		if (mes < 10)
			return managerDAOSegbecom.findWhere(FinTablaAmortizacion.class,
					"o.finEstadoCuota.idEstadoCuota=3 and to_char(o.fechaPago,'MMyyyy')='"
							.concat("0".concat(String.valueOf(mes)).concat(String.valueOf(anio).concat("'"))),
					"o.finPrestamoSocio.saldoCapital ASC");
		return managerDAOSegbecom.findWhere(FinTablaAmortizacion.class,
				"o.finEstadoCuota.idEstadoCuota=3 and to_char(o.fechaPago,'MMyyyy')='"
						.concat(String.valueOf(mes).concat(String.valueOf(anio).concat("'"))),
				"o.finPrestamoSocio.saldoCapital ASC");
	}

	public void eliminarAmortizacion(FinTablaAmortizacion amortiza) throws Exception {
		managerDAOSegbecom.eliminar(FinTablaAmortizacion.class, amortiza.getIdTablaAmortizacion());
	}

}
