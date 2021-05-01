package ec.com.model.gestionPersonas;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.GesDiscapacidad;
import ec.com.model.dao.entity.GesEstadoCivil;
import ec.com.model.dao.entity.GesEtnia;
import ec.com.model.dao.entity.GesGenero;
import ec.com.model.dao.entity.GesPersona;
import ec.com.model.dao.entity.GesTipoSangre;
import ec.com.model.dao.entity.UsrConsanguinidad;
import ec.com.model.dao.manager.ManagerDAOSegbecom;

/**
 * Session Bean implementation class GestionPersonas
 */
@Stateless(mappedName = "managerGestionPersonas")
@LocalBean
public class ManagerGestionPersonas {
	
	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;

    /**
     * Default constructor. 
     */
    public ManagerGestionPersonas() {
        // TODO Auto-generated constructor stub
    }

	public void insertarPersona(GesPersona gesPersona) throws Exception {
		try {
			managerDAOSegbecom.insertar(gesPersona);
		} catch (Exception e) {
			throw new Exception("Error al ingresar persona. "+e.getMessage());
		}
		
	}

	public GesPersona buscarPersonaByCedula(String cedula) throws Exception {
		try {
			return (GesPersona) managerDAOSegbecom.findById(GesPersona.class, cedula);
		} catch (Exception e) {
			throw new Exception("Error al buscar persona "+cedula+" \n"+e.getMessage());
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<GesEstadoCivil> buscarEstadosCiviles() throws Exception  {
		try {
			return managerDAOSegbecom.findAll(GesEstadoCivil.class, "o.estadoCivil ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado de estados civiles.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesGenero> buscarGenero() throws Exception {
		try {
			return managerDAOSegbecom.findAll(GesGenero.class, "o.genero ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado de genero. "+e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesTipoSangre> buscarTipoSangre() throws Exception {
		try {
			return managerDAOSegbecom.findAll(GesTipoSangre.class, "o.tipoSangre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado tipo sangre. "+e.getMessage()); 
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesEtnia> buscarEtnia() throws Exception {
		try {
			return managerDAOSegbecom.findAll(GesEtnia.class, "o.etnia ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar listado etnia. "+e.getMessage()); 
		}
	}

	public void actualizarGesPersona(GesPersona gesPersona) throws Exception {
		try {
			managerDAOSegbecom.actualizar(gesPersona);
		} catch (Exception e) {
			throw new Exception("Error al actualizar persona. "+e.getMessage());
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<UsrConsanguinidad> buscarUsrConsanguinidad() throws Exception {
		try {
			return managerDAOSegbecom.findAll(UsrConsanguinidad.class,"o.idConsanguinidad");
		} catch (Exception e) {
			throw new Exception("Error al listar Consanguinidad. "+e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<GesDiscapacidad> buscarDiscapacidad() throws Exception {
		try {
			return managerDAOSegbecom.findAll(GesDiscapacidad.class,"o.discapacidad");
		} catch (Exception e) {
			throw new Exception("Error al listar Discapacidades. "+e.getMessage());
		}
	}

	public GesDiscapacidad buscarDiscapacidadById(long idDiscapacidad) throws Exception {
		try {
			return (GesDiscapacidad) managerDAOSegbecom.findById(GesDiscapacidad.class, idDiscapacidad);
		} catch (Exception e) {
			throw new Exception("Error al buscar Discapacidad.");
		}
	}

	

}
