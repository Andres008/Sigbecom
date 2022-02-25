package ec.com.controlador.convenios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import ec.com.model.modulos.util.JSFUtil;

@Named("frmMisConvenios")
@SessionScoped
public class FrmMisConvenios implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@EJB
	private ManagerConvenios managerConvenios;
	
	@Inject
	private BeanLogin beanLogin;
	
    private List<ConvAdquirido> lstConvAdquirido;
    private String nombreArchivoTmp;
	private InputStream fis;
	private String extension;
	private String nombres;
	private String estado;
	private boolean inputVal;
	
	@PostConstruct
	public void init() {
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		cargarConvAdquiridos();
	}
	public void cargarConvAdquiridos() {
		try {
			lstConvAdquirido = managerConvenios.findConvAdquiridoByCedula(beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
public void cargarDocumento(String adjunto, String cedula) {
		
		if(!adjunto.isEmpty()) {
		String path;
		try {
			path = managerConvenios.buscarValorParametroNombre("PATH_REPORTES");
			String url = path+"convenios/"+cedula+"/"+adjunto;
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
		else
			JSFUtil.crearMensajeINFO("Archivo no disponible");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form1");

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
	
}
