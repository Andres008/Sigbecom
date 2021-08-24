package ec.com.model.gestionDescuentos;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.DescNovedade;
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
import ec.com.model.dao.entity.UsrTipoCuenta;
import ec.com.model.dao.entity.UsrTipoDescuento;
import ec.com.model.dao.entity.UsrTipoInstruccion;
import ec.com.model.dao.entity.UsrTipoLicencia;
import ec.com.model.dao.entity.UsrTipoVivienda;
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
			throw new Exception("Error al ingresar novedad económica. "+e.getMessage() );
		}		
	}

}