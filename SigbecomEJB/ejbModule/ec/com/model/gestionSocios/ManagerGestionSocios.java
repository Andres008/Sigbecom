package ec.com.model.gestionSocios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

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

/**
 * Session Bean implementation class ManagerGestionSocios
 */
@Stateless(mappedName = "managerGestionSocios")
@LocalBean
public class ManagerGestionSocios {
	
	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;
	
	

    /**
     * Default constructor. 
     */
    public ManagerGestionSocios() {
        // TODO Auto-generated constructor stub
    }



	public void insertarSocio(UsrSocio objUsrSocio) throws Exception {
		try {
			managerDAOSegbecom.insertar(objUsrSocio);
		} catch (Exception e) {
			throw new Exception("Error al ingresar socio. "+e.getMessage());
		}
		
	}



	public void actualizarUsrSocio(UsrSocio objUsrSocio) throws Exception {
		try {
			managerDAOSegbecom.actualizar(objUsrSocio);
		} catch (Exception e) {
			throw new Exception("Error al actualizar Socio."+e.getMessage());
		}
		
	}



	public UsrSocio buscarSocioById(String cedulaSocio) throws Exception {
		UsrSocio objSocio = (UsrSocio) managerDAOSegbecom.findById(UsrSocio.class, cedulaSocio);
		if ( objSocio== null )
			throw new Exception("Socio no existe");
		objSocio.getGesParientes().forEach(familiar->{
			familiar.getGesPersona().getApellidos();
		});;
		objSocio.getGesPersona().getGesDiscapacidadPersonas().forEach(discapacidad->{
			discapacidad.getGesDiscapacidad().getIdDiscapacidad();
		});
		objSocio.getUsrLicenciaSocios().forEach(licencia->{
			licencia.getUsrTipoLicencia().getDescripcion();
		});
		objSocio.getUsrInstruccions().forEach(instruccion->{
			instruccion.getUsrTipoInstruccion().getTipoInstruccion();
		});
		objSocio.getUsrCuentaSocios().forEach(cuenta->{
			cuenta.getUsrInstitucionBancaria().getInstitucionBancaria();
		});
		return objSocio;
	}
	
	public Boolean buscarSocioExisteById(String cedulaSocio) throws Exception {
		UsrSocio objSocio = (UsrSocio) managerDAOSegbecom.findById(UsrSocio.class, cedulaSocio);
		if ( objSocio== null )
			return false;
		return true;
	}



	public UsrConsanguinidad buscarConsanguinidadById(long idConsanguinidad) throws Exception {
		try {
			return (UsrConsanguinidad) managerDAOSegbecom.findById(UsrConsanguinidad.class, idConsanguinidad);
		} catch (Exception e) {
			throw new Exception("Error al buscar Consanguinidad. "+e.getMessage());
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrTipoLicencia> buscarTipoLicencia() throws Exception {
		try {
			return managerDAOSegbecom.findAll(UsrTipoLicencia.class, "o.tipoLicencia ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado tipo licencia. "+e.getMessage()); 
		}
	}

	public UsrTipoLicencia buscarTipoLicenciaById(long idTipoLicencia) throws Exception {
		try {
			return (UsrTipoLicencia) managerDAOSegbecom.findById(UsrTipoLicencia.class, idTipoLicencia);
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo de licencia.");
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrTipoInstruccion> buscarTipoInstruccion() throws Exception {
		try {
			return managerDAOSegbecom.findAll(UsrTipoInstruccion.class, "o.idTipoInstruccion ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado tipo instrucción. "+e.getMessage()); 
		}
	}



	public UsrTipoInstruccion buscarTipoInstruccionById(long idTipoInstruccion) throws Exception {
		try {
			return (UsrTipoInstruccion) managerDAOSegbecom.findById(UsrTipoInstruccion.class, idTipoInstruccion);
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo de instrucción.");
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrTipoCuenta> buscarTipoCuenta() throws Exception {
		try {
			return managerDAOSegbecom.findAll(UsrTipoCuenta.class, "o.tipoCuenta ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado tipo cuenta. "+e.getMessage()); 
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrInstitucionBancaria> buscarInsitucionBancaria() throws Exception {
		try {
			return managerDAOSegbecom.findAll(UsrInstitucionBancaria.class, "o.institucionBancaria ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado tipo cuenta. "+e.getMessage()); 
		}
	}



	public UsrTipoCuenta buscarTipoCuentaById(long idTipoCuenta) throws Exception {
		try {
			return (UsrTipoCuenta) managerDAOSegbecom.findById(UsrTipoCuenta.class, idTipoCuenta);
		} catch (Exception e) {
			throw new Exception("Error al buscar tipo de cuenta.");
		}
	}



	public UsrInstitucionBancaria buscarInsitucionBancariaById(long idInstitucionBancaria) throws Exception {
		try {
			return (UsrInstitucionBancaria) managerDAOSegbecom.findById(UsrInstitucionBancaria.class, idInstitucionBancaria);
		} catch (Exception e) {
			throw new Exception("Error al buscar institución bancaria.");
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrTipoVivienda> buscarTipoVivienda() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrTipoVivienda.class, "o.idTipoVivienda<>0",  "o.idTipoVivienda ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado tipo vivienda. "+e.getMessage()); 
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrProvincia> buscarProvincias() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrProvincia.class, "o.idProvincia <> '0'", "o.provincia ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado provincias. "+e.getMessage()); 
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrCanton> buscarCantoByProvincia(String idProvincia) throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrCanton.class, "o.idCanton <> '0' and o.usrProvincia.idProvincia='"+idProvincia+"'", "o.canton ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado cantones. "+e.getMessage()); 
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrParroquia> buscarParroquiaByCanton(String idCanton) throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrParroquia.class, "o.idParroquia <> '0' and o.usrCanton.idCanton='"+idCanton+"'", "o.parroquia ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado parroquias. "+e.getMessage()); 
		}
	}



	public UsrProvincia buscarProvinciaById(String idProvincia) throws Exception {
		try {
			return (UsrProvincia) managerDAOSegbecom.findById(UsrProvincia.class, idProvincia);
		} catch (Exception e) {
			throw new Exception("Error al buscar provincia. "+e.getMessage());
		}
	}



	public UsrCanton buscarCantonById(String idCanton) throws Exception {
		try {
			return (UsrCanton) managerDAOSegbecom.findById(UsrCanton.class, idCanton);
		} catch (Exception e) {
			throw new Exception("Error al buscar canton. "+e.getMessage());
		}
	}



	public UsrParroquia buscarParroquiaById(String idParroquia) throws Exception {
		try {
			return (UsrParroquia) managerDAOSegbecom.findById(UsrParroquia.class, idParroquia);
		} catch (Exception e) {
			throw new Exception("Error al buscar parroquia. "+e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<UsrAgencia> buscarAgencia() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrAgencia.class,"o.idAgencia<>0", "o.agencia ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado agencia. "+e.getMessage()); 
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrArea> buscarAreas() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrArea.class, "o.idArea<>0","o.area ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado areas. "+e.getMessage()); 
		}
	}



	@SuppressWarnings("unchecked")
	public List<UsrCargo> buscarCargos() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(UsrCargo.class,"o.idCargo<>0", "o.cargo ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado cargos. "+e.getMessage()); 
		}
	}



	public void actualizarDiscapacidad(GesDiscapacidadPersona discapacidad) throws Exception {
		managerDAOSegbecom.actualizar(discapacidad);
		
	}



	public void eliminarGesPariente(GesPariente pariente) throws Exception {
		try {
			managerDAOSegbecom.eliminar(GesPariente.class, pariente.getIdPariente());
		} catch (Exception e) {
			throw new Exception("Error al eliminar GesPariente.");
		}
	}



		public void eliminarUsrLicenciaSocio(UsrLicenciaSocio licencia) throws Exception {
		try {
			managerDAOSegbecom.eliminar(UsrLicenciaSocio.class, licencia.getId());
		} catch (Exception e) {
			throw new Exception("Error al eliminar UsrLicenciaSocio.");
		}
	}



		public void eliminarUsrInstruccion(UsrInstruccion instruccion) throws Exception {
			try {
				managerDAOSegbecom.eliminar(UsrInstruccion.class, instruccion.getIdInstruccion());
			} catch (Exception e) {
				throw new Exception("Error al eliminar UsrInstruccion.");
			}
			
		}



		@SuppressWarnings("unchecked")
		public List<UsrSocio> buscarTodosSocios() throws Exception {
			try {
				return managerDAOSegbecom.findAll(UsrSocio.class,"o.gesPersona.apellidos ASC");
			} catch (Exception e) {
				throw new Exception("Error al buscar listado de socios");
			}
		}

}
