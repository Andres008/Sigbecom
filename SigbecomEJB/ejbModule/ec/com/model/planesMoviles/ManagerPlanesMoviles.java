package ec.com.model.planesMoviles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.LastExecution;

import org.apache.taglibs.standard.tag.common.core.UrlSupport;

import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvEmpresa;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.dao.entity.PlanAmortEquipmov;
import ec.com.model.dao.entity.PlanContacto;
import ec.com.model.dao.entity.PlanContratoComite;
import ec.com.model.dao.entity.PlanCostosAdm;
import ec.com.model.dao.entity.PlanEquipo;
import ec.com.model.dao.entity.PlanOperadora;
import ec.com.model.dao.entity.PlanPago;
import ec.com.model.dao.entity.PlanPlanMovil;
import ec.com.model.dao.entity.PlanRegistroPago;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.dao.entity.UsrTipoSocio;
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
   	public List<PlanPlanMovil> findAllPlanesMovilesByIdOperadora(Long idOperadora) throws Exception{
       	List<PlanPlanMovil> lstPlanMovil = managerDAOSegbecom.findWhere(PlanPlanMovil.class, "o.planOperadora.idPlanEmpresa='"+idOperadora+"'", null);
       	return lstPlanMovil;
    }
    @SuppressWarnings("unchecked")
   	public List<PlanEquipo> findAllEquiposByIdOperadora(Long idOperadora) throws Exception{
       	List<PlanEquipo> lstPlanEquipo = managerDAOSegbecom.findWhere(PlanEquipo.class, "o.planOperadora.idPlanEmpresa='"+idOperadora+"'", null);
       	return lstPlanEquipo;
    }
    @SuppressWarnings("unchecked")
   	public List<PlanOperadora> findAllOperadoras() throws Exception{
       	List<PlanOperadora> lstPlanOperadora = managerDAOSegbecom.findAll(PlanOperadora.class);
       	return lstPlanOperadora;
    }
   	
	public List<UsrSocio> findAllUsuariosSocios() throws Exception{
   		List<UsrSocio> lstUserSocio = findAllUsuarios();
   		//List<UsrSocio> lstUserSocio = managerDAOSegbecom.findWhere(UsrSocio.class, "o.usrTipoSocio.idTipoSocio <> '4'", null);
       	for (UsrSocio usrSocio : lstUserSocio) {
			for (PlanContratoComite planContratoComite : usrSocio.getPlanContratoComites()) {
				planContratoComite.getIdContrato();
			}
		}
       	return lstUserSocio;
    }
  
   	@SuppressWarnings("unchecked")
	public List<UsrSocio> findAllUsuariosNoSocios() throws Exception{
       	List<UsrSocio> lstUsrSocio = managerDAOSegbecom.findWhere(UsrSocio.class, "o.usrTipoSocio.idTipoSocio = '4'", null);
       	for (UsrSocio usrSocio : lstUsrSocio) {
			for (PlanContratoComite planContratoComite : usrSocio.getPlanContratoComites()) {
				planContratoComite.getIdContrato();
			}
		}
       	return lstUsrSocio;
    }
    public void insertarPlanContratoComite(PlanContratoComite planContratoComite) throws Exception {
    	managerDAOSegbecom.insertar(planContratoComite);
    }
    
    @SuppressWarnings("unchecked")
   	public List<PlanPago> findAllPlanPago() throws Exception{
       	List<PlanPago> lstPlanPago = managerDAOSegbecom.findAll(PlanPago.class);
       	return lstPlanPago;
    }
    public void insertarPlanPago(PlanPago planPago) throws Exception {
    	managerDAOSegbecom.insertar(planPago);
    }
    @SuppressWarnings("unchecked")
	public DescEstadoDescuento findWhereEstadoDescEstadoDescuento(String estado) throws Exception {
		List<DescEstadoDescuento> lstEstadoDes = new ArrayList<DescEstadoDescuento>();
		lstEstadoDes = managerDAOSegbecom.findWhere(DescEstadoDescuento.class, "o.estado='"+estado+"'", null);
		if (lstEstadoDes.isEmpty())
			throw new Exception("Parametro no encontrado.");
		if (lstEstadoDes.size() > 1)
			throw new Exception("Más de un parametro encontrado.");
		return lstEstadoDes.get(0);
	}
    @SuppressWarnings("unchecked")
   	public List<UsrTipoSocio> findAllUsrTipoSocioEstadoActivo() throws Exception{
       	List<UsrTipoSocio> lstUsrTipoSocio = managerDAOSegbecom.findWhere(UsrTipoSocio.class, "o.estado='A'", null);
       	return lstUsrTipoSocio;
    }
    
   	public UsrTipoSocio findByIdTipoSocio(long idTipoSocio) throws Exception{
       	UsrTipoSocio usrTipoSocio = (UsrTipoSocio) managerDAOSegbecom.findById(UsrTipoSocio.class, idTipoSocio);
       	return usrTipoSocio;
    }
   	
    public void insertarPlanCostosAdm(PlanCostosAdm planCostosAdm) throws Exception {
    	managerDAOSegbecom.insertar(planCostosAdm);
    }
    
    @SuppressWarnings("unchecked")
	public List<PlanCostosAdm> findAllPlanCostosAdm() throws Exception {
    	List<PlanCostosAdm> lstPlanCostosAdm = managerDAOSegbecom.findAll(PlanCostosAdm.class);
    	return lstPlanCostosAdm;
    }
    @SuppressWarnings("unchecked")
	public PlanCostosAdm findPlanCostosAdmBytipoUsr(String tipoUsr) throws Exception {
    	List<PlanCostosAdm> lstPlanCostosAdm = managerDAOSegbecom.findWhere(PlanCostosAdm.class, "o.usrTipoSocio.nombre ='"+tipoUsr+"' AND o.estado = 'ACTIVO'", null);
    	if(lstPlanCostosAdm!=null && lstPlanCostosAdm.size()==1) {
    		return lstPlanCostosAdm.get(0);
    	}
    	else
    		return null;
    }
    @SuppressWarnings("unchecked")
	public PlanContratoComite findContratoComite(String lineaTelefono) throws Exception {
    	List<PlanContratoComite> lstPlanContratoComite = managerDAOSegbecom.findWhere(PlanContratoComite.class, "o.lineaTelefono ='"+lineaTelefono+"'", null);
    	if(lstPlanContratoComite!=null && lstPlanContratoComite.size()==1) {
    		return lstPlanContratoComite.get(0);
    	}
    	else
    		return null;
    }
    public void insertarPlanRegistroPagos(PlanRegistroPago planRegistroPago) throws Exception {
    	managerDAOSegbecom.insertar(planRegistroPago);
    }
    @SuppressWarnings("unchecked")
	public List<PlanRegistroPago> findAllPlanRegistroPago() throws Exception {
    	List<PlanRegistroPago> lstPlanRegistroPago = managerDAOSegbecom.findAll(PlanRegistroPago.class, "o.idRegistroPagos DESC");
    	return lstPlanRegistroPago;
    }
    
    @SuppressWarnings("unchecked")
	public List<PlanContratoComite> findAllPlanContratoComite() throws Exception {
    	List<PlanContratoComite> lstPlanContratoComite = managerDAOSegbecom.findAll(PlanContratoComite.class);
    	for (PlanContratoComite planContratoComite : lstPlanContratoComite) {
			for (PlanAmortEquipmov planAmortEquipmov : planContratoComite.getPlanAmortEquipmovs()) {
				planAmortEquipmov.getIdAmortEquipmov();
			}
		}
    	return lstPlanContratoComite;
    }
    @SuppressWarnings("unchecked")
	public List<PlanEquipo> findPlanEquipoByIdOperadora(long idPlanEmpresa) throws Exception {
    	List<PlanEquipo> lstPlanEquipo = managerDAOSegbecom.findWhere(PlanEquipo.class, "o.planOperadora.idPlanEmpresa = '"+idPlanEmpresa+"'", null);
    	return lstPlanEquipo;
    }
   
	public PlanContratoComite findByIdContratoComite(long idContratoComite) throws Exception {
    	PlanContratoComite planContratoComite = (PlanContratoComite) managerDAOSegbecom.findById(PlanContratoComite.class, idContratoComite);
    	return planContratoComite;
    }
    public void insertarPlanAmortEquipmov(PlanAmortEquipmov planAmortEquipmov) throws Exception {
    	managerDAOSegbecom.insertar(planAmortEquipmov);
    }
    @SuppressWarnings("unchecked")
	public List<PlanAmortEquipmov> findPlanAmortEquipmovByLineaTelef(String lineaTelefono) throws Exception{
    	List<PlanAmortEquipmov> lstPlanAmortEquipmov = managerDAOSegbecom.findWhere(PlanAmortEquipmov.class, "o.planContratoComite.lineaTelefono = '"+lineaTelefono+"' AND o.estado = 'GENERADO' OR o.estado = 'PENDIENTE'", null); 
    	if(lstPlanAmortEquipmov!= null && lstPlanAmortEquipmov.size()>0) {
    		return lstPlanAmortEquipmov;
    	}
    	else {
    		return null;
    	}
    }
    
    
    @SuppressWarnings("unchecked")
	public List<String> findAllCedulasContratoPlanActivosRenovados() throws Exception {
    	List<String> lstCedulasContratosPlan = managerDAOSegbecom.findDistinct(PlanContratoComite.class,"o.usrSocio.cedulaSocio" ,"o.estado = 'ACTIVO' OR o.estado = 'RENOVADO'", null);
    	if(lstCedulasContratosPlan !=null && lstCedulasContratosPlan.size()>0) {
    		return lstCedulasContratosPlan;
    	}
    	else {
			return null;
		}	
    }
    @SuppressWarnings("unchecked")
	public List<PlanRegistroPago> findRegistroPagoGeneradosByCedula(String cedula) throws Exception {
    	List<PlanRegistroPago> lstPlanRegistroPago = managerDAOSegbecom.findWhere(PlanRegistroPago.class, "o.planContratoComite.usrSocio.cedulaSocio = '"+cedula+"' AND o.estado = 'GENERADO'",null);
    	if(lstPlanRegistroPago!=null && lstPlanRegistroPago.size()>0) {
    		return lstPlanRegistroPago;
    	}
    	else {
			return null;
		}	
    }
    @SuppressWarnings("unchecked")
	public List<PlanAmortEquipmov> findAmortEquipmovByPlanContratoMes(String lineaTelefono, Integer mes) throws Exception{
    	List<PlanAmortEquipmov> lstPlanAmortEquipmov = managerDAOSegbecom.findWhere(PlanAmortEquipmov.class, "o.planContratoComite.lineaTelefono = '"+lineaTelefono+"' AND o.estado = 'GENERADO' AND o.mes = '"+mes+"'", null); 
    	if(lstPlanAmortEquipmov!= null && lstPlanAmortEquipmov.size()>0) {
    		return lstPlanAmortEquipmov;
    	}
    	else {
    		return null;
    	}
    }
    @SuppressWarnings("unchecked")
   	public List<PlanPago> findAllPlanPagoByCedula(String cedula) throws Exception{
       	List<PlanPago> lstPlanPago = managerDAOSegbecom.findWhere(PlanPago.class, "o.planContratoComite.usrSocio.cedulaSocio = '"+cedula+"'", "o.idPlanPagos DESC");
       	for (PlanPago planPago : lstPlanPago) {
			for (PlanAmortEquipmov planAmortEquipmov : planPago.getPlanAmortEquipmovs()) {
				planAmortEquipmov.getIdAmortEquipmov();
			}
			for (PlanRegistroPago planRegistroPago : planPago.getPlanRegistroPagos()) {
				planRegistroPago.getIdRegistroPagos();
			}
		}
       	return lstPlanPago;
    }
    
    
	public PlanAmortEquipmov findPlanAmortEquipmovById(long idAmortEquipmov) throws Exception{
    	PlanAmortEquipmov planAmortEquipmov = (PlanAmortEquipmov) managerDAOSegbecom.findById(PlanAmortEquipmov.class, idAmortEquipmov); 
    	return planAmortEquipmov;
    }
	
    @SuppressWarnings("unchecked")
	public List<PlanAmortEquipmov> findAllPlanAmortEquipmov() throws Exception{
    	List<PlanAmortEquipmov> lstPlanAmortEquipmov = managerDAOSegbecom.findAll(PlanAmortEquipmov.class, "o.idAmortEquipmov");
    	if(lstPlanAmortEquipmov!= null && lstPlanAmortEquipmov.size()>0) {
    		return lstPlanAmortEquipmov;
    	}
    	else {
    		return null;
    	}
    }
	
}
