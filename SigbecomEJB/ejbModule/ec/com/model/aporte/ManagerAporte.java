package ec.com.model.aporte;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.AporteCliente;
import ec.com.model.dao.entity.AporteCuenta;
import ec.com.model.dao.entity.AporteDescuento;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.dao.entity.UsrSocio;
import ec.com.model.dao.manager.ManagerDAOSegbecom;

/**
 * Session Bean implementation class ManagerAporte
 */
@Stateless
@LocalBean
public class ManagerAporte {
	
	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;
    /**
     * Default constructor. 
     */
    public ManagerAporte() {
        // TODO Auto-generated constructor stub
    }
    public void insertarAportCuenta(AporteCuenta aporteCuenta) throws Exception {
    	managerDAOSegbecom.insertar(aporteCuenta);
    }
    public void insertarAportCliente(AporteCliente aporteCliente) throws Exception {
    	managerDAOSegbecom.insertar(aporteCliente);
    }
    public void insertarAportDescuento(AporteDescuento aporteDescuento) throws Exception {
    	managerDAOSegbecom.insertar(aporteDescuento);
    }
    @SuppressWarnings("unchecked")
	public List<AporteCuenta> findAllAporteCuenta() throws Exception{
    	List<AporteCuenta> lstAporteCuentas = managerDAOSegbecom.findAll(AporteCuenta.class);
    	if(lstAporteCuentas !=null && lstAporteCuentas.size()>0) {
    		for (AporteCuenta aporteCuenta : lstAporteCuentas) {
    			aporteCuenta.getIdCuenta();
				for (AporteCliente aporteCliente : aporteCuenta.getAporteClientes()) {
					aporteCliente.getIdCliente();
					for (AporteDescuento aporteDescuento : aporteCliente.getAporteDescuentos()) {
						aporteDescuento.getIdDescuento();
					}
				}
			}
    		return lstAporteCuentas;
    	}
    	else {
    		return null;
    	}
    }
    public void actualizarObjeto(Object object) throws Exception {
		try {
			managerDAOSegbecom.actualizar(object);
		} catch (Exception e) {
			throw new Exception("Error al actualizar "+object.getClass());
		}
		
	}
    @SuppressWarnings("unchecked")
	public List<UsrSocio> findAllUsrSocios() throws Exception{
    	List<UsrSocio> lstUsrSocio = managerDAOSegbecom.findAll(UsrSocio.class);
    	if(lstUsrSocio!=null && lstUsrSocio.size()>0) {
    		return lstUsrSocio;
    	}
    	else {
			return null;
		}
    }
    @SuppressWarnings("unchecked")
	public List<AporteCliente> findAllClientesByCedulaIdCuenta(String cedulaSocio, Long idCuenta) throws Exception{
    	List<AporteCliente> lstAporteCliente = managerDAOSegbecom.findWhere(AporteCliente.class, "o.usrSocio.cedulaSocio='"+cedulaSocio+"' AND o.aporteCuenta.idCuenta="+idCuenta, null);
    	if(lstAporteCliente!=null && lstAporteCliente.size()>0) {
    		return lstAporteCliente;
    	}
    	else {
    		return null;
    	}
    }
    @SuppressWarnings("unchecked")
   	public List<AporteCliente> findAllAporteCliente() throws Exception{
       	List<AporteCliente> lstAporteCliente = managerDAOSegbecom.findAll(AporteCliente.class, "o.usrSocio.cedulaSocio ASC");
       	if(lstAporteCliente !=null && lstAporteCliente.size()>0) {
       		for (AporteCliente aporteCliente : lstAporteCliente) {
   				aporteCliente.getIdCliente();
   				for (AporteDescuento aporteDescuento : aporteCliente.getAporteDescuentos()) {
   					aporteDescuento.getIdDescuento();
   				}
   			}
   			return lstAporteCliente;
       	}
       	else {
       		return null;
       	}
    }
    @SuppressWarnings("unchecked")
   	public List<AporteCliente> findAllAporteClienteEstadoActivo() throws Exception{
       	List<AporteCliente> lstAporteCliente = managerDAOSegbecom.findWhere(AporteCliente.class, "o.estado='ACTIVO'", "o.usrSocio.cedulaSocio ASC");
       	if(lstAporteCliente !=null && lstAporteCliente.size()>0) {
       		return lstAporteCliente;
       	}
       	else {
       		return null;
       	}
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
	public List<AporteDescuento> findAllAporteDescuentos() throws Exception{
    	return managerDAOSegbecom.findAll(AporteDescuento.class, "o.idDescuento DESC");
    }
}
