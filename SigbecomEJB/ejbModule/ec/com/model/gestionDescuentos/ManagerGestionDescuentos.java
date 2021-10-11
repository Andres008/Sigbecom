package ec.com.model.gestionDescuentos;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.DesDescuentoMensuale;
import ec.com.model.dao.entity.DescNovedade;
import ec.com.model.dao.entity.DescValoresFijo;
import ec.com.model.dao.entity.FinCuotasDescontada;
import ec.com.model.dao.entity.FinPrestamoSocio;
import ec.com.model.dao.entity.FinRequisito;
import ec.com.model.dao.entity.FinResolucionPrestamo;
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
import ec.com.model.dao.entity.UsrSocioDescuentoFijo;
import ec.com.model.dao.entity.UsrTipoCuenta;
import ec.com.model.dao.entity.UsrTipoDescuento;
import ec.com.model.dao.entity.UsrTipoInstruccion;
import ec.com.model.dao.entity.UsrTipoLicencia;
import ec.com.model.dao.entity.UsrTipoVivienda;
import ec.com.model.dao.entity.VDescuentoMensualSocio;
import ec.com.model.dao.manager.ManagerDAOSegbecom;
import ec.com.model.modulos.util.JSFUtil;

/**
 * Session Bean implementation class ManagerGestionSocios
 */
@Stateless(mappedName = "managerGestionDescuentos")
@LocalBean
public class ManagerGestionDescuentos {

	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;

	/**
	 * Default constructor.
	 */
	public ManagerGestionDescuentos() {
		// TODO Auto-generated constructor stub
	}

	public void ingresarTipoDescuento(UsrTipoDescuento objUsrTipoDescuento) throws Exception {
		try {
			managerDAOSegbecom.insertar(objUsrTipoDescuento);
		} catch (Exception e) {
			throw new Exception("Error al ingresar registro Tipo descuento: " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<UsrTipoDescuento> buscarTodoDescuentos() throws Exception {
		try {
			return managerDAOSegbecom.findAll(UsrTipoDescuento.class, "o.estado ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar tipos descuentos.");
		}
	}

	public UsrTipoDescuento buscarTipoDescuentoById(long idDescuento) throws Exception {
		try {
			return (UsrTipoDescuento) managerDAOSegbecom.findById(UsrTipoDescuento.class, idDescuento);
		} catch (Exception e) {
			throw new Exception("Error al buscar Tipo descuento.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<DescNovedade> buscarNovedades() throws Exception {
		List<DescNovedade> lstDescuentos;
		try {
			lstDescuentos = managerDAOSegbecom.findAll(DescNovedade.class,
					"o.descEstadoDescuento.idEstadoDescuento DESC, o.fechaRegistro ASC");
			return lstDescuentos;
		} catch (Exception e) {
			throw new Exception("Error al buscar Novedades.");
		}

	}

	public void ingresarNovedadEconomica(DescNovedade objDescNovedade) throws Exception {
		try {
			managerDAOSegbecom.insertar(objDescNovedade);
		} catch (Exception e) {
			throw new Exception("Error al ingresar novedad económica. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<UsrSocioDescuentoFijo> buscarDescuentosFijos() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrSocioDescuentoFijo.class, "o.estado='A'",
					"o.usrSocio.gesPersona.apellidos ASC,o.usrSocio.gesPersona.nombres ASC, o.usrTipoDescuento.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar descuentos fijos. " + e.getMessage());
		}

	}

	public void ingresarDescuentoFijoSocio(DescValoresFijo valorFijo) throws Exception {
		try {
			managerDAOSegbecom.insertar(valorFijo);
		} catch (Exception e) {
			throw new Exception("Error al ingresar descuento fijo. " + valorFijo.getUsrSocio().getCedulaSocio());
		}

	}

	@SuppressWarnings("unchecked")
	public List<DescValoresFijo> buscarDescuentosFijosPendientes() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(DescValoresFijo.class, "o.descEstadoDescuento.idEstadoDescuento=2",
					null);
		} catch (Exception e) {
			throw new Exception("Error al buscar descuentos pendientes.");
		}
	}

	public void eliminarDescuentoFijosPendientes(DescValoresFijo valoresFijos) throws Exception {
		try {
			managerDAOSegbecom.eliminar(DescValoresFijo.class, valoresFijos.getIdVaorFijo());
		} catch (Exception e) {
			throw new Exception("Error al eleiminar");
		}
	}

	@SuppressWarnings("unchecked")
	public List<VDescuentoMensualSocio> findDescuentoMensual() throws Exception {
		try {
			return managerDAOSegbecom.findAll(VDescuentoMensualSocio.class, "o.nombreSocio");
		} catch (Exception e) {
			throw new Exception("Error al consular datos de descuento mensual.");
		}
	}

	public void ejecutarDescuentoMensual() throws Exception {
		try {
			managerDAOSegbecom.ejecutarProcedimiento("pro_ejecutardescuentomens()");
		} catch (Exception e) {
			throw new Exception("Error al ejecutar procedimiento. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<DesDescuentoMensuale> buscarMisDescuentos(String cedulaSocio) throws Exception {
		return managerDAOSegbecom.findWhere(DesDescuentoMensuale.class, "o.usrSocio.cedulaSocio='"+cedulaSocio+"'","o.anio DESC, o.mes DESC");
	}

	@SuppressWarnings("unchecked")
	public List<DesDescuentoMensuale> buscarDescuentosMensuales(Long anio, Long mes) throws Exception {
		try {
			return managerDAOSegbecom.findWhere(DesDescuentoMensuale.class,"o.anio="+anio+" and o.mes="+mes, "o.nombreSocio ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar descuentos.");
		}
	}

	public void ingresarUsrSocioDescuentoFijo(UsrSocioDescuentoFijo descuentoNuevo) throws Exception {
		managerDAOSegbecom.insertar(descuentoNuevo);
		
	}

}
