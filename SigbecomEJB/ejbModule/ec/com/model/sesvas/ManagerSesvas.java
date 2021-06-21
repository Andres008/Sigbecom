package ec.com.model.sesvas;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.w3c.dom.ls.LSInput;

import ec.com.model.dao.entity.GesPariente;
import ec.com.model.dao.entity.GesPersona;
import ec.com.model.dao.entity.SesvasAdjunto;
import ec.com.model.dao.entity.SesvasBeneficiario;
import ec.com.model.dao.entity.SesvasBeneficio;
import ec.com.model.dao.entity.SesvasRequisito;
import ec.com.model.dao.entity.SesvasSolicitud;
import ec.com.model.dao.entity.SesvasTipoRequisito;
import ec.com.model.dao.entity.UsrConsanguinidad;
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
    	List<SesvasBeneficio> lstBeneficios = managerDAOSegbecom.findAll(SesvasBeneficio.class);
    	if(lstBeneficios!=null && lstBeneficios.size()>0) {
	    	for (SesvasBeneficio sesvasBeneficio : lstBeneficios) {
	    		for (SesvasBeneficiario sesvasBeneficiario : sesvasBeneficio.getSesvasBeneficiarios()) {
					sesvasBeneficiario.getIdBeneficiario();
				}
	    		for (SesvasRequisito sesvasRequisito : sesvasBeneficio.getSesvasRequisitos()) {
					sesvasRequisito.getIdSesvasRequisitos();
				}
				for (SesvasSolicitud sesvasSolicitud : sesvasBeneficio.getSesvasSolicituds()) {
					sesvasSolicitud.getIdSesvasSolicitud();
				}
				
			}
    	}
    	return lstBeneficios;
    }
    public void actualizarObjeto(Object object) throws Exception {
		try {
			managerDAOSegbecom.actualizar(object);
		} catch (Exception e) {
			throw new Exception("Error al actualizar "+object.getClass());
		}
		
	}
    @SuppressWarnings("unchecked")
	public List<SesvasTipoRequisito> findAllTipoDocumentos() throws Exception {
    	return managerDAOSegbecom.findAll(SesvasTipoRequisito.class);
    }
    
    public void registrarSesvasTipoDocumento(SesvasTipoRequisito tiposDocumento) throws Exception {
    	managerDAOSegbecom.insertar(tiposDocumento);
    }
    
    public void registrarSesvasRequisito(SesvasRequisito sesRequisito) throws Exception {
    	managerDAOSegbecom.insertar(sesRequisito);
    }
    @SuppressWarnings("unchecked")
	public List<SesvasRequisito> findByIdBeneficioListSesvasRequisitos(Long idSesvasBeneficio) throws Exception {
    	List<SesvasRequisito> listSesvasRequisito= managerDAOSegbecom.findWhere(SesvasRequisito.class, "o.sesvasBeneficio.idSesvasBeneficios='"+idSesvasBeneficio+"'", null); 
    	return listSesvasRequisito;
    }
    public void registrarSolicitud(SesvasSolicitud sesvasSolicitud) throws Exception {
    	managerDAOSegbecom.insertar(sesvasSolicitud);
    }
    public void registrarAdjuntos(SesvasAdjunto sesvasAdjunto) throws Exception {
    	managerDAOSegbecom.insertar(sesvasAdjunto);
    }
    
/*    @SuppressWarnings("unused")
	public void subirArchivo(String url,String nombreAdjunto, byte[] file) throws Exception  {
		File fRuta = new File(url);
		if (!fRuta.exists()) {
			File directorio = new File(url);
			directorio.mkdirs();
			url = directorio.getAbsolutePath() + "/";
		} else {
			url = url + "\\";
		}
		InputStream in;
		in=new ByteArrayInputStream(file);
		if(in!=null){
			// Escribir con inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(new File(url+nombreAdjunto));//nombre del adjunto debe incluir extencion
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
			//System.out.println("Nuevo archivo creado!!");
		}
		else {
			throw new Exception("No exite el archivo: "+in.getClass().getName());
		}
	}*/
	
    @SuppressWarnings("unchecked")
	public List<SesvasSolicitud> listadoDeSolicitudesByCedula(String cedulaSocio) throws Exception {
    	List<SesvasSolicitud> listado= managerDAOSegbecom.findWhere(SesvasSolicitud.class, "o.usrSocio.cedulaSocio='"+cedulaSocio+"'", "o.fecha DESC");
    	if (listado!=null && listado.size()>0) {
			for (SesvasSolicitud sesvasSolicitud : listado) {
				for (SesvasAdjunto sesvasAdjunto : sesvasSolicitud.getSesvasAdjuntos()) {
					sesvasAdjunto.getIdAdjuntos();
				}
			}
    		return listado;
		}
    	else {
			return null;
		}
    }
    @SuppressWarnings("unchecked")
   	public List<SesvasSolicitud> listadoDeSolicitudesByEstado(String estado) throws Exception {
       	List<SesvasSolicitud> listado= managerDAOSegbecom.findWhere(SesvasSolicitud.class, "o.estado='"+estado+"'", "o.fecha DESC");
       	if (listado!=null && listado.size()>0) {
   			for (SesvasSolicitud sesvasSolicitud : listado) {
   				for (SesvasAdjunto sesvasAdjunto : sesvasSolicitud.getSesvasAdjuntos()) {
   					sesvasAdjunto.getIdAdjuntos();
   				}
   			}
       		return listado;
   		}
       	else {
   			return null;
   		}
    }
    @SuppressWarnings("unchecked")
   	public List<SesvasSolicitud> findAllSolicitudes() throws Exception {
       	List<SesvasSolicitud> listado= managerDAOSegbecom.findAll(SesvasSolicitud.class, "o.fecha DESC");
       	if (listado!=null && listado.size()>0) {
   			for (SesvasSolicitud sesvasSolicitud : listado) {
   				for (SesvasAdjunto sesvasAdjunto : sesvasSolicitud.getSesvasAdjuntos()) {
   					sesvasAdjunto.getIdAdjuntos();
   				}
   			}
       		return listado;
   		}
       	else {
   			return null;
   		}
    }
    
    public void eliminarBeneficio(long idSesvasBeneficios) throws Exception {
    	managerDAOSegbecom.eliminar(SesvasBeneficio.class, idSesvasBeneficios);
    }
    public SesvasBeneficio findByIdBeneficio(long idbeneficio) throws Exception {
    	SesvasBeneficio sesvasBeneficio = (SesvasBeneficio) managerDAOSegbecom.findById(SesvasBeneficio.class, idbeneficio);
    	for (SesvasBeneficiario sesBeneficiario : sesvasBeneficio.getSesvasBeneficiarios()) {
			sesBeneficiario.getIdBeneficiario();
		}
    	return sesvasBeneficio;
    }
    @SuppressWarnings("unchecked")
	public List<UsrConsanguinidad> findAllUsrConsanguinidad() throws Exception{
    	return managerDAOSegbecom.findAll(UsrConsanguinidad.class);
    }
    
    public void insertarSesvasBeneficiario(SesvasBeneficiario sesvasBeneficiario) throws Exception {
    	managerDAOSegbecom.insertar(sesvasBeneficiario);
    }
    @SuppressWarnings("unchecked")
	public List<SesvasBeneficiario> beneficiariosByIdBeneficios(long idSesvasBeneficio) throws Exception {
    	return managerDAOSegbecom.findWhere(SesvasBeneficiario.class, "o.sesvasBeneficio.idSesvasBeneficios = '"+idSesvasBeneficio+"'", null);
    }
    @SuppressWarnings("unchecked")
	public List<SesvasBeneficio> findSesvasBeneficioByEstado(String estado) throws Exception{ 
    	 
    	 List<SesvasBeneficio> lstBeneficios = managerDAOSegbecom.findWhere(SesvasBeneficio.class, "o.estado = '"+estado+"'", null);
     	if(lstBeneficios!=null && lstBeneficios.size()>0) {
 	    	for (SesvasBeneficio sesvasBeneficio : lstBeneficios) {
 	    		for (SesvasBeneficiario sesvasBeneficiario : sesvasBeneficio.getSesvasBeneficiarios()) {
 					sesvasBeneficiario.getIdBeneficiario();
 				}
 	    		for (SesvasRequisito sesvasRequisito : sesvasBeneficio.getSesvasRequisitos()) {
 					sesvasRequisito.getIdSesvasRequisitos();
 				}
 				for (SesvasSolicitud sesvasSolicitud : sesvasBeneficio.getSesvasSolicituds()) {
 					sesvasSolicitud.getIdSesvasSolicitud();
 				}
 				
 			}
 	    	return lstBeneficios;
     	}
     	return null;
    }
    @SuppressWarnings("unchecked")
	public List<SesvasSolicitud> findSolicitudesByIdBeneficios(Long idBeneficios,String estado, String cedulaSocio) throws Exception{
    	return managerDAOSegbecom.findWhere(SesvasSolicitud.class, "o.sesvasBeneficio.idSesvasBeneficios = '"+idBeneficios+"' AND o.estado = '"+estado+"' AND o.usrSocio.cedulaSocio='"+cedulaSocio+"'", null);
    	
    }
    @SuppressWarnings("unchecked")
   	public List<SesvasSolicitud> findSolicitudesByIdBenEstadoCedPeriodo(Long idBeneficios,String estado, String cedulaSocio,String periodo) throws Exception{
       	return managerDAOSegbecom.findWhere(SesvasSolicitud.class, "o.sesvasBeneficio.idSesvasBeneficios = '"+idBeneficios+"' AND o.estado = '"+estado+"' AND o.usrSocio.cedulaSocio='"+cedulaSocio+"' AND o.periodo='"+periodo+"'", null);
    }
    @SuppressWarnings("unchecked")
	public List<GesPariente> findParientesByCed(String cedulaSocio) throws Exception {
    	return managerDAOSegbecom.findWhere(GesPariente.class, "o.usrSocio.cedulaSocio='"+cedulaSocio+"'", null);
    }
    
    public GesPariente findParienteByCedFamSocio(String cedulaFamiliar) throws Exception {
    	return (GesPariente) managerDAOSegbecom.findWhere(GesPariente.class, "o.gesPersona.cedula='"+cedulaFamiliar+"'", null).get(0);
    }
    
    
}
