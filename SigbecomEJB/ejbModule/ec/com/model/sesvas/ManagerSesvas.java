package ec.com.model.sesvas;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.SesvasBeneficio;
import ec.com.model.dao.manager.ManagerDAOSegbecom;

/**
 * Session Bean implementation class ManagerSesvas
 */
@Stateless
@LocalBean
public class ManagerSesvas {
	
	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;
    /**
     * Default constructor. 
     */
    public ManagerSesvas() {
        // TODO Auto-generated constructor stub
    }

    public void insertarSesvasBeneficio(SesvasBeneficio sesvasBeneficio) throws Exception {
    	managerDAOSegbecom.insertar(sesvasBeneficio);
    }
    @SuppressWarnings("unchecked")
	public List<SesvasBeneficio> findAllSesvasBeneficio() throws Exception{
    	return managerDAOSegbecom.findAll(SesvasBeneficio.class, "o.idSesvasBeneficios ASC");
    }
    
}
