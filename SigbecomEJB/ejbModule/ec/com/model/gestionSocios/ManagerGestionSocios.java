package ec.com.model.gestionSocios;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.UsrSocio;
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

}
