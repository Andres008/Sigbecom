package ec.com.model.planesMoviles;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvEmpresa;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.entity.PlanContacto;
import ec.com.model.dao.entity.PlanEquipo;
import ec.com.model.dao.entity.PlanOperadora;
import ec.com.model.dao.entity.PlanPlanMovil;
import ec.com.model.dao.manager.ManagerDAOSegbecom;

/**
 * Session Bean implementation class ManagerConvenios
 */
@Stateless
@LocalBean
public class ManagerPlanesMoviles {
	
	
	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;
	/**
     * Default constructor. 
     */
    public ManagerPlanesMoviles() {
        // TODO Auto-generated constructor stub
    }
    
    public void insertarPlanOperadora(PlanOperadora planOperadora) throws Exception {
    	managerDAOSegbecom.insertar(planOperadora);
    }
    
    @SuppressWarnings("unchecked")
	public List<PlanOperadora> findAllEmpresas() throws Exception{
    	List<PlanOperadora> lstEmpresas = managerDAOSegbecom.findAll(PlanOperadora.class);
    	for (PlanOperadora planOperadora : lstEmpresas) {
			for (PlanContacto planContacto : planOperadora.getPlanContactos()) {
				planContacto.getIdPlanContactos();
			}
			for (PlanPlanMovil planPlanMovil : planOperadora.getPlanPlanMovils()) {
				planPlanMovil.getIdPlanMovil();
			}
			for (PlanEquipo planEquipo : planOperadora.getPlanEquipos()) {
				planEquipo.getIdEquipo();
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
    
    public void insertarPlanContacto(PlanContacto planContacto) throws Exception {
    	managerDAOSegbecom.insertar(planContacto);
    }
    
    public void insertarPlanPlanMovil(PlanPlanMovil planPlanServicio) throws Exception {
    	managerDAOSegbecom.insertar(planPlanServicio);
    }
    
    public void insertarPlanEquipo(PlanEquipo planEquipo) throws Exception {
    	managerDAOSegbecom.insertar(planEquipo);
    }
   
}
