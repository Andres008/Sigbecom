package ec.com.controlador.convenios;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.controlador.sesvas.DtoSesvasAdjunto;
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.entity.ConvValorMax;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

@Named("frmAplicarConvenio")
@SessionScoped
public class FrmAplicarConvenio implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerConvenios managerConvenios;
	
	private List<ConvServicio> lstConvServicio;
	
	private Long idServicio;
	private ConvServicio convServicio;
	private ConvAdquirido convAdquirido;
	private List<ConvAdquirido> lstConvAdquirido;
	private List<ConvValorMax> lstConvValorMax;
	private Long idValorMax;
	private ConvValorMax convValorMax;
	private String valorMaximo;
	private BigDecimal valorProformado;
	private boolean pnlRender;
	private UploadedFile file;
	byte[] archivo;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		lstConvServicio = new ArrayList<ConvServicio>();
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		lstConvValorMax = new ArrayList<ConvValorMax>();
		idServicio = new Long(0);
		convServicio = new ConvServicio();
		convAdquirido = new ConvAdquirido();
		idValorMax = new Long(0);
		convValorMax = new ConvValorMax();
		valorMaximo = "N/A";
		valorProformado = new BigDecimal(0);
		pnlRender = false;
		archivo = null;
		cargarListaConvServicio();
		cargarListaConvAdquiridos();
		cargarListaRegistroValoresMax();
	}
	public void cargarListaConvServicio() {
		try {
			lstConvServicio = managerConvenios.findAllServicioActivosByCedula(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaConvAdquiridos() {
		try {
			String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
			List<ConvContacto> lstConvContactos = managerConvenios.findAllContactosByCedula(cedulaSocio);
			List<ConvAdquirido> lstConvAdquiridosTmp = new ArrayList<ConvAdquirido>();
			for (ConvContacto convContacto : lstConvContactos) {
				lstConvAdquiridosTmp = new ArrayList<ConvAdquirido>();
				lstConvAdquiridosTmp = managerConvenios.findConvAdquiridoByIdEmpresa(convContacto.getConvEmpresa().getIdConvEmpresa());
				lstConvAdquirido.addAll(lstConvAdquiridosTmp);
			}
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaRegistroValoresMax() {
		try {
			lstConvValorMax = managerConvenios.findAllConvValorMaxEstadoActivo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarConvServicio() {
		if(idServicio!=0) {
			try {
				pnlRender=true;
				convServicio=managerConvenios.findByIdConvServicio(idServicio);
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1:datEmp");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo Servicio correctamente");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("No ha seleccionado ningun servicio");
		}
	}
	public void cargarConvValorMax() {
		if(idValorMax>0) {
			convValorMax = lstConvValorMax.stream().filter(p->p.getIdValorMax()==idValorMax).findAny().orElse(null);
			if(convServicio.getMontoMax()!=null && convServicio.getMontoMax().equalsIgnoreCase("SI")) {
				valorMaximo = convValorMax.getValorMax()+"";
			}
			else {
				JSFUtil.crearMensajeWARN("No ha seleccionado un servicio");
			}
		}
	}
	
	
	public void handleFileUpload(FileUploadEvent event) {
        System.out.println("Archivo subido: "+ event.getFile().getFileName());
        this.file = event.getFile();
        JSFUtil.crearMensajeINFO("Documento cargado correctamente");
        convAdquirido.setAdjunto(event.getFile().getFileName());
    }
	
	public void precargarArchivo() {
		if(file!=null) {
			System.out.println("Tipo AAAAA archivo:"+file.getContentType());
			archivo = file.getContent();
			//file=null;
		}
	}
	
	public void aplicarConvenio() {
		//if(archivo!=null && convServicio!= null && convAdquirido.getAdjunto() != null && idValorMax>0 && valorProformado.compareTo(new BigDecimal(0))==1) {
		if(convServicio!= null && convAdquirido.getAdjunto() != null && idValorMax>0 && valorProformado.compareTo(new BigDecimal(0))==1) {	
			if(convServicio.getMontoMax().equalsIgnoreCase("SI")) {
				if(valorProformado.compareTo(convValorMax.getValorMax())==-1 || valorProformado.compareTo(convValorMax.getValorMax())==0) {
					try {
						String path=managerConvenios.buscarValorParametroNombre("PATH_REPORTES");
						//String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
						String url = path+"/convenios/"+convValorMax.getUsrSocio().getCedulaSocio()+"/";
						String str = convAdquirido.getAdjunto();
						String ext = str.substring(str.lastIndexOf('.'), str.length());
						Date fechaActual = new Date();
						SimpleDateFormat formato = new SimpleDateFormat("dd");
						int diaActual = Integer.parseInt(formato.format(fechaActual));
						String nombreArchivo = ModelUtil.getAnioActual()+"-"+
								   ModelUtil.getMesActual()+"-"+
								   diaActual+"-proforma";
						convAdquirido.setAdjunto(nombreArchivo+ext);
						convAdquirido.setConvServicio(convServicio);
						convAdquirido.setConvValorMax(convValorMax);
						convAdquirido.setEstado("SOLICITADO");
						convAdquirido.setFechaSol(new Date());
						convAdquirido.setAplicaValorMax("SI");
						convAdquirido.setValorMax(convValorMax.getValorMax());
						convAdquirido.setInteres(convServicio.getInteres());//interes de Comision
						convAdquirido.setDeudaTotal(valorProformado);//valor total proformado incluye la comisión
						BigDecimal interes = (convServicio.getInteres().divide(new BigDecimal(100))).add(new BigDecimal(1));
						System.out.println("interes:"+ interes);
						BigDecimal valorTotal = valorProformado.divide(interes,2, RoundingMode.HALF_EVEN);
						convAdquirido.setValorTotal(valorTotal);
						BigDecimal interesTotal = convServicio.getInteres().multiply(convAdquirido.getValorTotal()).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN);
						convAdquirido.setInteresTotal(interesTotal);
						managerConvenios.insertarConvAdquirido(convAdquirido);
						if(archivo!=null) {
							InputStream fis = new ByteArrayInputStream(archivo);
							ModelUtil.guardarArchivo(fis, nombreArchivo, url, ext);
						}
						
						JSFUtil.crearMensajeINFO("Solicitud Realizada Correctamente");
						init();
						PrimeFaces prime=PrimeFaces.current();
						prime.ajax().update("form2");
					} catch (Exception e) {
						JSFUtil.crearMensajeERROR("No se ha registrado correctamente");
						e.printStackTrace();
					}
				}
				else {
					JSFUtil.crearMensajeWARN("Valor Proformado no debe ser mayor que el valor maximo permitido");
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("form2");
				}
			}
			else {
				try {
					String path=managerConvenios.buscarValorParametroNombre("PATH_REPORTES");
					//String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
					String url = path+"convenios/"+convValorMax.getUsrSocio().getCedulaSocio()+"/";
					String str = convAdquirido.getAdjunto();
					String ext = str.substring(str.lastIndexOf('.'), str.length());
					Date fechaActual = new Date();
					SimpleDateFormat formato = new SimpleDateFormat("dd");
					int diaActual = Integer.parseInt(formato.format(fechaActual));
					String nombreArchivo = ModelUtil.getAnioActual()+"-"+
							   ModelUtil.getMesActual()+"-"+
							   diaActual+"-proforma";
					convAdquirido.setAdjunto(nombreArchivo+ext);
					convAdquirido.setConvServicio(convServicio);
					convAdquirido.setConvValorMax(convValorMax);
					convAdquirido.setEstado("SOLICITADO");
					convAdquirido.setFechaSol(new Date());
					convAdquirido.setAplicaValorMax("NO");
					convAdquirido.setValorMax(new BigDecimal(0));
					convAdquirido.setInteres(convServicio.getInteres());//interes de Comision
					convAdquirido.setDeudaTotal(valorProformado);//valor total proformado incluye la comisión
					BigDecimal interes = (convServicio.getInteres().divide(new BigDecimal(100))).add(new BigDecimal(1));
					BigDecimal valorTotal = valorProformado.divide(interes).setScale(2, RoundingMode.HALF_EVEN);
					convAdquirido.setValorTotal(valorTotal);
					BigDecimal interesTotal = convServicio.getInteres().multiply(convAdquirido.getValorTotal()).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN);
					convAdquirido.setInteresTotal(interesTotal);
					managerConvenios.insertarConvAdquirido(convAdquirido);
					InputStream fis = new ByteArrayInputStream(archivo);
					ModelUtil.guardarArchivo(fis, nombreArchivo, url, ext);
					JSFUtil.crearMensajeINFO("* Solicitud Realizada Correctamente");
					init();
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("form2");
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR("* No se ha registrado correctamente");
					e.printStackTrace();
				}
			}
			
		}
		else if(valorProformado.compareTo(new BigDecimal(0))==0) {
			JSFUtil.crearMensajeWARN("Ingrese el valor proformado distinto de 0.00");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form2");
		}
//		else if(archivo==null) {
//			JSFUtil.crearMensajeWARN("No ha seleccionado un archivo proforma");
//			PrimeFaces prime=PrimeFaces.current();
//			prime.ajax().update("form2");
//		}
	}
	public boolean activarTablaAmortizacion(String estado) {
		if(!estado.equalsIgnoreCase("SOLICITADO") && !estado.equalsIgnoreCase("NO APROBADO")) {
			return true;
		}
		return false;
	}
	
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public List<ConvServicio> getLstConvServicio() {
		return lstConvServicio;
	}
	public void setLstConvServicio(List<ConvServicio> lstConvServicio) {
		this.lstConvServicio = lstConvServicio;
	}
	public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public ConvServicio getConvServicio() {
		return convServicio;
	}
	public void setConvServicio(ConvServicio convServicio) {
		this.convServicio = convServicio;
	}
	public ConvAdquirido getConvAdquirido() {
		return convAdquirido;
	}
	public void setConvAdquirido(ConvAdquirido convAdquirido) {
		this.convAdquirido = convAdquirido;
	}
	public List<ConvAdquirido> getLstConvAdquirido() {
		return lstConvAdquirido;
	}
	public void setLstConvAdquirido(List<ConvAdquirido> lstConvAdquirido) {
		this.lstConvAdquirido = lstConvAdquirido;
	}
	public List<ConvValorMax> getLstConvValorMax() {
		return lstConvValorMax;
	}
	public void setLstConvValorMax(List<ConvValorMax> lstConvValorMax) {
		this.lstConvValorMax = lstConvValorMax;
	}
	public Long getIdValorMax() {
		return idValorMax;
	}
	public void setIdValorMax(Long idValorMax) {
		this.idValorMax = idValorMax;
	}
	public String getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(String valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public BigDecimal getValorProformado() {
		return valorProformado;
	}
	public void setValorProformado(BigDecimal valorProformado) {
		this.valorProformado = valorProformado;
	}
	public boolean isPnlRender() {
		return pnlRender;
	}
	public void setPnlRender(boolean pnlRender) {
		this.pnlRender = pnlRender;
	}
	
}
