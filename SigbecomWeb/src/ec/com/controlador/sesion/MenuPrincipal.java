package ec.com.controlador.sesion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named("menuPrincipal")
@SessionScoped
public class MenuPrincipal implements Serializable{
	private static final long serialVersionUID = 1L;
	private InputStream fis;
	@Inject
	private BeanLogin beanLogin;
	
	public StreamedContent getPdfInformeAnual() {
        return DefaultStreamedContent.builder()
				.name("REPORTE_INGRESOS_GASTOS_2021.pdf")
                .contentType("application/pdf")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/archivos/REPORTE_INGRESOS_GASTOS_2021.pdf"))
                .build();
    }
	
	public void cargarDocumento() {
		
		//String homeUsuario = System.getProperty("user.home");
		//String url = homeUsuario+"/manuales/MANUALES SIGBECOM[4356].pdf";
		String url = "/home/app/manuales/MANUALES SIGBECOM[4356].pdf";
		System.out.println("URL:"+url);
		File file = new File(url);
		try {
			fis = new FileInputStream(file);
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmPDFT");
			prime.executeScript("PF('dlgPDFT').show();");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
public void cargarPresentacion() {
		
		//String homeUsuario = System.getProperty("user.home");
		//String url = homeUsuario+"/manuales/MANUALES SIGBECOM[4356].pdf";
		String url = "/home/app/manuales/PRESENTACION SIGBECOM FINAL,v1.pdf";
		System.out.println("URL:"+url);
		File file = new File(url);
		try {
			fis = new FileInputStream(file);
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("frmPDFT");
			prime.executeScript("PF('dlgPDFT').show();");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("static-access")
	public StreamedContent getPdf() {
		if (fis != null) {
			return new DefaultStreamedContent().builder().contentType("application/pdf; charset=UTF-8").name("Manual Comite").stream(()-> fis).build();
			//return new DefaultStreamedContent(fis, "application/pdf; charset=UTF-8", "Sol_Factibilidad.pdf");
		}
		else {
			return null;
		}
	}
}
