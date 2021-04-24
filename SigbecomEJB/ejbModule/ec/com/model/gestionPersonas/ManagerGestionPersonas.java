package ec.com.model.gestionPersonas;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.GesPersona;
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

}
