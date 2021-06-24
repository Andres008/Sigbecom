package ec.com.model.convenios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvEmpresa;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.manager.ManagerDAOSegbecom;

/**
 * Session Bean implementation class ManagerConvenios
 */
@Stateless
@LocalBean
public class ManagerConvenios {

	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;
	/**
     * Default constructor. 
     */
    public ManagerConvenios() {
        // TODO Auto-generated constructor stub
    }
    
    public void insertarConvEmpresa(ConvEmpresa convEmpresa) throws Exception {
    	managerDAOSegbecom.insertar(convEmpresa);
    }
    public void insertarConvContacto(ConvContacto convContacto) throws Exception {
    	managerDAOSegbecom.insertar(convContacto);
    }
    public void insertarConvServicio(ConvServicio convServicio) throws Exception {
    	managerDAOSegbecom.insertar(convServicio);
    }
    @SuppressWarnings("unchecked")
	public List<ConvEmpresa> findAllEmpresas() throws Exception{
    	List<ConvEmpresa> lstEmpresas = managerDAOSegbecom.findAll(ConvEmpresa.class);
    	for (ConvEmpresa convEmpresa : lstEmpresas) {
			for (ConvContacto convContacto : convEmpresa.getConvContactos()) {
				convContacto.getIdConvContactos();
			}
			for (ConvServicio convServicio : convEmpresa.getConvServicios()) {
				convServicio.getIdConvServicio();
			}
		}
    	return lstEmpresas;
    }
    public void actualizarObjeto(Object object) throws Exception {
		try {
			managerDAOSegbecom.actualizar(object);
		} catch (Exception e) {
			throw new Exception("Error al actualizar "+object.getClass());
		}
		
	}
}
