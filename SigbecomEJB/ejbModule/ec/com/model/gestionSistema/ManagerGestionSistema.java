package ec.com.model.gestionSistema;


import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.manager.ManagerDAOSegbecom;

/**
 * Session Bean implementation class ManagerAutorizacion
 */
@Stateless(mappedName = "managerGestionSistema")
@LocalBean
public class ManagerGestionSistema {

	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;
	
	public ManagerGestionSistema() {
		// TODO Auto-generated constructor stub
	}
	
	
}
