package ec.com.controlador.convenios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.ConvAmortizacion;
import ec.com.model.dao.entity.GesPersona;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

@Named("frmGestionarConvenios")
@SessionScoped
public class FrmGestionarConvenios implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerConvenios managerConvenios;
	
	@Inject
	private BeanLogin beanLogin;
	
	private List<ConvAdquirido> lstConvAdquirido;
	private List<ConvAdquirido> lstConvAdquiridoTrm;
	private ConvAdquirido convAdquirido;
	private String nombreArchivoTmp;
	private InputStream fis;
	private String extension;
	private String nombres;
	private String estado;
	private boolean inputVal;
	private BigDecimal valor;
	private int meses;
	private List<ConvAmortizacion> lstConvAmortizacion;
	
	@PostConstruct
	public void init() {
		extension="";
		nombres = "";
		estado = "";
		meses=0;
		valor = new BigDecimal("0");
		inputVal=true;
		lstConvAmortizacion = new ArrayList<ConvAmortizacion>();
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		lstConvAdquiridoTrm = new ArrayList<ConvAdquirido>();
		convAdquirido = new ConvAdquirido();
		cargarListaConvAdquiridos();
		cargarListaConvAdquiridosTramitados();
	}

	
	public FrmGestionarConvenios() {
		super();
	}


	public void cargarListaConvAdquiridos() {
		
		try {
			lstConvAdquirido = managerConvenios.findConvAdquiridoByEstado("REVISADO");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaConvAdquiridosTramitados() {
			
			try {
				lstConvAdquiridoTrm = managerConvenios.findConvAdquiridoRevisados();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
				e.printStackTrace();
			}
		}
	public String nombres(String cedula) {
		try {
			GesPersona gesPersona = new GesPersona(); 
			gesPersona = managerConvenios.findNombresByCedula(cedula);
			if(gesPersona!=null) {
				return gesPersona.getApellidos()+" "+gesPersona.getNombres();
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo nombres correctamente");
			e.printStackTrace();
		}
		return "";
	}
	public void cargarDocumento(String adjunto, String cedula) {
		
		String path;
		try {
			path = managerConvenios.buscarValorParametroNombre("PATH_REPORTES");
			String url = path+"convenios\\"+cedula+"\\"+adjunto;
			System.out.println(url);
			File file = new File(url);
			nombreArchivoTmp = adjunto;
			String ext = nombreArchivoTmp.substring(nombreArchivoTmp.lastIndexOf('.'), nombreArchivoTmp.length());
			
			if (ext.equalsIgnoreCase(".pdf")) {
				try {
					fis = new FileInputStream(file);
					//System.out.println("Url 1 : "+url);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("frmPDFT");
				prime.executeScript("PF('dlgPDFT').show();");
				
			}
			else {
				if(ext.equalsIgnoreCase(".png")) {
					extension = "png";
				}
				else if(ext.equalsIgnoreCase(".jpg")) {
					extension = "jpg";
				}
				try {
					fis = new FileInputStream(file);
					System.out.println("Url 2: "+url);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("frmIMAGET");
				prime.executeScript("PF('dlgImageT').show();");
			}
		} catch (Exception e1) {
			JSFUtil.crearMensajeERROR("No se cargo el archivo correctamente");
			e1.printStackTrace();
		}
		

	}
	@SuppressWarnings("static-access")
	public StreamedContent getPdf() {
		if (fis != null) {
			return new DefaultStreamedContent().builder().contentType("application/pdf; charset=UTF-8").name(nombreArchivoTmp).stream(()-> fis).build();
			//return new DefaultStreamedContent(fis, "application/pdf; charset=UTF-8", "Sol_Factibilidad.pdf");
		}
		else {
			return null;
		}
	}
	@SuppressWarnings("static-access")
	public StreamedContent getDownload() {
		if (fis != null) {
			//System.out.println("extensionDownload: "+extension);
			return new DefaultStreamedContent().builder().contentType("image/"+extension).name(nombreArchivoTmp).stream(()-> fis).build();
		}
		else {
			return null;
		}
	}
	
	public void cargarSolicitud(long idConvAdquiridos) {
		convAdquirido = lstConvAdquirido.stream().filter(p->p.getIdConvAdquiridos()==idConvAdquiridos).findAny().orElse(null);
		GesPersona gespersona;
		try {
			gespersona = managerConvenios.findNombresByCedula(convAdquirido.getCedulaSocio());
			nombres = gespersona.getApellidos()+" "+gespersona.getNombres();
			estado = "";
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el archivo correctamente");
			e.printStackTrace();
		}
	}
	
	public void tramitar() {
		System.out.println("estado:" + estado);
		if(!estado.isEmpty() && valor.compareTo(BigDecimal.ZERO) == 1 && meses>0) {
			BigDecimal interes=convAdquirido.getConvServicio().getInteres().setScale(2, BigDecimal.ROUND_HALF_EVEN);
			convAdquirido.setEstado(estado);
			convAdquirido.setFechaRevision(new Date());
			convAdquirido.setValorTotal(valor);
			convAdquirido.setInteres(interes);
			try {
				managerConvenios.actualizarObjeto(convAdquirido);
				lstConvAmortizacion = new ArrayList<ConvAmortizacion>();
				generarTablaAmortizacion(valor, interes, meses);
				cargarListaConvAdquiridos();
				cargarListaConvAdquiridosTramitados();
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.executeScript("PF('dlgTramite').hide();");
				JSFUtil.crearMensajeINFO("Se registró el tramite correctamente");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se actualizo el tramite correctamente");
				e.printStackTrace();
			}
			
		}
	}
	public void generarTablaAmortizacion(BigDecimal valor,BigDecimal interes, int plazo) {
		BigDecimal interesCuota = valor.multiply(interes);
		ConvAmortizacion convAmortizacion;
		int anio=ModelUtil.getAnio(new Date());
		int mes =ModelUtil.getMes(new Date());
		String mesTmp;
		SimpleDateFormat formato= new SimpleDateFormat("yyyy-MM-dd");
		Date fecha;
		if(plazo>1) {
			for (int i = 1 ; i<=plazo;i++) {
				convAmortizacion = new ConvAmortizacion();
				
			}
		}
		else{
			try {
				mes++;
				mesTmp=mes+"";
				if(mes<10) {
					mesTmp="0"+mes;
				}
				fecha=formato.parse(anio+"-"+mesTmp+"-01");
				
				convAmortizacion = new ConvAmortizacion();
				convAmortizacion.setFechaPago(fecha);
				convAmortizacion.setCapital(valor);
				convAmortizacion.setConvAdquirido(convAdquirido);
				convAmortizacion.setInteres(interesCuota);
				convAmortizacion.setNumeroCuota(plazo);
				convAmortizacion.setValorCuota(valor.add(interesCuota));
				convAmortizacion.setSaldo(new BigDecimal(0));
				convAmortizacion.setEstadoPago("PENDIENTE");
				managerConvenios.insertarConvAmortizacion(convAmortizacion);
				
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro la cuota correctamente");
				e.printStackTrace();
			}
		}
	}
	public boolean activarTablaAmortizacion(String estado) {
		if(!estado.equalsIgnoreCase("SOLICITADO") && !estado.equalsIgnoreCase("NO CALIFICA")) {
			return true;
		}
		return false;
	}
	public void renderValor() {
		System.out.println("Dato:");
		if(estado.equalsIgnoreCase("APROBADO")) {
			inputVal = false;
		}
		else {
			inputVal = true;
		}
	}
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<ConvAdquirido> getLstConvAdquirido() {
		return lstConvAdquirido;
	}

	public void setLstConvAdquirido(List<ConvAdquirido> lstConvAdquirido) {
		this.lstConvAdquirido = lstConvAdquirido;
	}

	public ConvAdquirido getConvAdquirido() {
		return convAdquirido;
	}

	public void setConvAdquirido(ConvAdquirido convAdquirido) {
		this.convAdquirido = convAdquirido;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ConvAdquirido> getLstConvAdquiridoTrm() {
		return lstConvAdquiridoTrm;
	}

	public void setLstConvAdquiridoTrm(List<ConvAdquirido> lstConvAdquiridoTrm) {
		this.lstConvAdquiridoTrm = lstConvAdquiridoTrm;
	}


	public boolean isInputVal() {
		return inputVal;
	}


	public void setInputVal(boolean inputVal) {
		this.inputVal = inputVal;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public int getMeses() {
		return meses;
	}


	public void setMeses(int meses) {
		this.meses = meses;
	}
	
}
