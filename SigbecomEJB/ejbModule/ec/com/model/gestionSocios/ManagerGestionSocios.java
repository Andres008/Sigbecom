package ec.com.model.gestionSocios;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.UsrConsanguinidad;
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
		return objSocio;
	}



	public UsrConsanguinidad buscarConsanguinidadById(long idConsanguinidad) throws Exception {
		try {
			return (UsrConsanguinidad) managerDAOSegbecom.findById(UsrConsanguinidad.class, idConsanguinidad);
		} catch (Exception e) {
			throw new Exception("Error al buscar Consanguinidad. "+e.getMessage());
		}
	}

}
