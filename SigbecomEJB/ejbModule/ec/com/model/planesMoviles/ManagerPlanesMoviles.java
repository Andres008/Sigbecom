package ec.com.model.planesMoviles;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.taglibs.standard.tag.common.core.UrlSupport;

import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvEmpresa;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.entity.PlanContacto;
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanEquipo;
import ec.com.model.dao.entity.PlanOperadora;
import ec.com.model.dao.entity.PlanPlanMovil;
import ec.com.model.dao.entity.UsrSocio;
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
    
    @SuppressWarnings("unchecked")
   	public List<UsrSocio> findAllUsuarios() throws Exception{
       	List<UsrSocio> lstUsrSocio = managerDAOSegbecom.findAll(UsrSocio.class);
       	return lstUsrSocio;
    }
    public UsrSocio findUsrSocioByCedulaSocio(String cedulaSocio) throws Exception {
    	UsrSocio usrSocio = (UsrSocio) managerDAOSegbecom.findById(UsrSocio.class, cedulaSocio);
    	return usrSocio;
    }
    public PlanEquipo findEquipoMovilByIdEquipo(Long idEquipo) throws Exception {
    	PlanEquipo planEquipo = (PlanEquipo) managerDAOSegbecom.findById(PlanEquipo.class, idEquipo);
    	return planEquipo;
    }
    public PlanPlanMovil findPlanMovilByid(Long idPlanMovil) throws Exception {
    	PlanPlanMovil planMovil = (PlanPlanMovil) managerDAOSegbecom.findById(PlanPlanMovil.class, idPlanMovil);
    	return planMovil;
    }
    @SuppressWarnings("unchecked")
   	public List<PlanPlanMovil> findAllPlanesMoviles() throws Exception{
       	List<PlanPlanMovil> lstPlanMovil = managerDAOSegbecom.findAll(PlanPlanMovil.class);
       	return lstPlanMovil;
    }
    @SuppressWarnings("unchecked")
   	public List<PlanEquipo> findAllEquipos() throws Exception{
       	List<PlanEquipo> lstPlanEquipo = managerDAOSegbecom.findAll(PlanEquipo.class);
       	return lstPlanEquipo;
    }
    
    @SuppressWarnings("unchecked")
   	public List<PlanContratoComite> findAllUsuariosConPlanes() throws Exception{
       	List<PlanContratoComite> lstPlanContratoComite = managerDAOSegbecom.findAll(PlanContratoComite.class);
       	return lstPlanContratoComite;
    }
    public void insertarPlanContratoComite(PlanContratoComite planContratoComite) throws Exception {
    	managerDAOSegbecom.insertar(planContratoComite);
    }
}
