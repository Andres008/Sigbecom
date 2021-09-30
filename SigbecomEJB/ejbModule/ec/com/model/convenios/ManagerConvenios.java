package ec.com.model.convenios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.AutParametrosGenerale;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.ConvAmortizacion;
import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvEmpresa;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.entity.ConvValorMax;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.dao.entity.GesPersona;
import ec.com.model.dao.entity.UsrSocio;
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
    @SuppressWarnings("unchecked")
	public List<ConvContacto> findAllContactosByCedulaAndIdConvEmpresa(String cedulaSocio, Long idConvEmpresa) throws Exception{
    	List<ConvContacto> lstConvContacto = managerDAOSegbecom.findWhere(ConvContacto.class, "o.usrSocio.cedulaSocio ='"+cedulaSocio+"' AND o.convEmpresa.idConvEmpresa ="+idConvEmpresa, null);
    	if(lstConvContacto!= null && lstConvContacto.size()>0) {
    		return lstConvContacto;
    	}
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<ConvContacto> findAllContactosByCedula(String cedulaSocio) throws Exception{
    	List<ConvContacto> lstConvContacto = managerDAOSegbecom.findWhere(ConvContacto.class, "o.usrSocio.cedulaSocio = '"+cedulaSocio+"'", null);
    	if(lstConvContacto!= null && lstConvContacto.size()>0) {
    		return lstConvContacto;
    	}
    	return null;
    }
    
    
    @SuppressWarnings("unchecked")
	public List<ConvServicio> findAllServicioActivosByCedula(String cedulaSocio) throws Exception{
    	
    	List<ConvContacto> lstConvContacto = findAllContactosByCedula(cedulaSocio);
    	List<ConvServicio> lstConvServicioTmp = new ArrayList<ConvServicio>();
    	if(lstConvContacto!=null) {
    		for (ConvContacto convContacto : lstConvContacto) {
				Long idEmpresa = convContacto.getConvEmpresa().getIdConvEmpresa();
				List<ConvServicio> lstConvServicio = managerDAOSegbecom.findWhere(ConvServicio.class, "o.estado = 'ACTIVO' AND o.convEmpresa.idConvEmpresa="+idEmpresa, "o.idConvServicio ASC");
				if(lstConvServicio!=null && lstConvServicio.size()>0) {
					lstConvServicioTmp.addAll(lstConvServicio);
				}
			}
    	}
    	
    	return lstConvServicioTmp;
    }
    public ConvServicio findByIdConvServicio(Long idServicio) throws Exception {
    	return (ConvServicio) managerDAOSegbecom.findById(ConvServicio.class, idServicio);
    } 
    
    public void insertarConvAdquirido(ConvAdquirido convAdquirido) throws Exception {
    	managerDAOSegbecom.insertar(convAdquirido);
    }
    public void insertarConvAmortizacion(ConvAmortizacion convAmortizacion) throws Exception {
    	managerDAOSegbecom.insertar(convAmortizacion);
    }
	@SuppressWarnings("unchecked")
	public List<ConvAdquirido> findConvAdquiridoByIdEmpresa(Long idConvEmpresa) throws Exception{
    	List<ConvAdquirido> lstConvAdquiridos = managerDAOSegbecom.findWhere(ConvAdquirido.class, "o.convServicio.convEmpresa.idConvEmpresa = '"+idConvEmpresa+"'", "o.idConvAdquiridos DESC");
    	for (ConvAdquirido convAdquirido : lstConvAdquiridos) {
			for (ConvAmortizacion convAmortizacion : convAdquirido.getConvAmortizacions()) {
				convAmortizacion.getIdConvAmortizacion();
			}
		}
    	return lstConvAdquiridos;
    }
	public String buscarValorParametroNombre(String nombre) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<AutParametrosGenerale> lstParametro = managerDAOSegbecom.findWhere(AutParametrosGenerale.class,
					"o.nombre='" + nombre.toUpperCase() + "' and o.estado='A'", null);
			if (lstParametro.isEmpty())
				throw new Exception("Parametro no encontrado.");
			if (lstParametro.size() > 1)
				throw new Exception("Más de un parametro encontrado.");
			return lstParametro.get(0).getValor();
		} catch (Exception e) {
			throw new Exception("Error al buscar parametro. " + e.getMessage());

		}
	}
	@SuppressWarnings("unchecked")
	public List<ConvAdquirido> findConvAdquiridoByEstado(String estado) throws Exception{
    	List<ConvAdquirido> lstConvAdquiridos = managerDAOSegbecom.findWhere(ConvAdquirido.class, "o.estado = '"+estado+"'", "o.idConvAdquiridos DESC");
    	for (ConvAdquirido convAdquirido : lstConvAdquiridos) {
			for (ConvAmortizacion convAmortizacion : convAdquirido.getConvAmortizacions()) {
				convAmortizacion.getIdConvAmortizacion();
			}
		}
    	return lstConvAdquiridos;
    }
	@SuppressWarnings("unchecked")
	public List<ConvAdquirido> findConvAdquiridoByCedula(String cedulaSocio) throws Exception{
    	List<ConvAdquirido> lstConvAdquiridos = managerDAOSegbecom.findWhere(ConvAdquirido.class, "o.convValorMax.usrSocio.cedulaSocio = '"+cedulaSocio+"'", "o.idConvAdquiridos DESC");
    	for (ConvAdquirido convAdquirido : lstConvAdquiridos) {
			for (ConvAmortizacion convAmortizacion : convAdquirido.getConvAmortizacions()) {
				convAmortizacion.getIdConvAmortizacion();
			}
		}
    	return lstConvAdquiridos;
    }
	@SuppressWarnings("unchecked")
	public List<ConvAdquirido> findConvAdquiridoTramitados() throws Exception{
    	List<ConvAdquirido> lstConvAdquiridos = managerDAOSegbecom.findWhere(ConvAdquirido.class, "o.estado != 'SOLICITADO'", "o.idConvAdquiridos DESC");
    	for (ConvAdquirido convAdquirido : lstConvAdquiridos) {
			for (ConvAmortizacion convAmortizacion : convAdquirido.getConvAmortizacions()) {
				convAmortizacion.getIdConvAmortizacion();
			}
		}
    	return lstConvAdquiridos;
    }
	@SuppressWarnings("unchecked")
	public List<ConvAdquirido> findConvAdquiridoRevisados() throws Exception{
    	List<ConvAdquirido> lstConvAdquiridos = managerDAOSegbecom.findWhere(ConvAdquirido.class, "o.estado != 'REVISADO' AND o.estado != 'SOLICITADO' AND o.estado != 'NO CALIFICA'", "o.idConvAdquiridos DESC");
    	for (ConvAdquirido convAdquirido : lstConvAdquiridos) {
			for (ConvAmortizacion convAmortizacion : convAdquirido.getConvAmortizacions()) {
				convAmortizacion.getIdConvAmortizacion();
			}
		}
    	return lstConvAdquiridos;
    }
	@SuppressWarnings("unchecked")
	public GesPersona findNombresByCedula(String cedula) throws Exception{
		List<GesPersona> lstGespersona = managerDAOSegbecom.findWhere(GesPersona.class, "o.cedula = '"+cedula+"'", null);
		if (lstGespersona.isEmpty())
			throw new Exception("Parametro no encontrado.");
		if (lstGespersona.size() > 1)
			throw new Exception("Más de un parametro encontrado.");
		return lstGespersona.get(0);
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
	public List<UsrSocio> findAllUsrSocios() throws Exception{
		List<UsrSocio> lstUsrSocio = managerDAOSegbecom.findAll(UsrSocio.class);
		return lstUsrSocio;
	}
	
	public UsrSocio findUsrSociosByCedulaSocio(String cedulaSocio) throws Exception {
		UsrSocio usrSocio = (UsrSocio) managerDAOSegbecom.findById(UsrSocio.class, cedulaSocio);
		return usrSocio;
	}
	public void insertarConvValorMax(ConvValorMax convValorMax) throws Exception {
    	managerDAOSegbecom.insertar(convValorMax);
    }
	@SuppressWarnings("unchecked")
	public List<ConvValorMax> findAllConvValorMax() throws Exception{
		List<ConvValorMax> lstConvValorMax = managerDAOSegbecom.findAll(ConvValorMax.class);
		return lstConvValorMax;
	}
	@SuppressWarnings("unchecked")
	public List<ConvValorMax> findConvValorMaxByCedula(String cedulaSocio) throws Exception{
		List<ConvValorMax> lstConvValorMax = managerDAOSegbecom.findWhere(ConvValorMax.class, "o.usrSocio.cedulaSocio = '"+cedulaSocio+"'", null);
		if(lstConvValorMax!=null && lstConvValorMax.size()==1) {
			return lstConvValorMax;
		}
		else if (lstConvValorMax.size()>1) {
			throw new Exception("Exite mas de un registro en la tabla");
		}
		else {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<ConvValorMax> findAllConvValorMaxEstadoActivo() throws Exception{
		List<ConvValorMax> lstConvValorMax = managerDAOSegbecom.findWhere(ConvValorMax.class, "o.estado = 'ACTIVO'", null);
		return lstConvValorMax;
	}
}
