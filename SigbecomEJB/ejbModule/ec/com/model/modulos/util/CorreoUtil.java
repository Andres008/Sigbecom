package ec.com.model.modulos.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ec.com.model.gestionSistema.ManagerGestionSistema;

@Stateless(mappedName = "CorreoUtil")
@LocalBean
public class CorreoUtil {

	private String host;
	private String user;
	private String clave;
	private String port;
	@EJB
	private ManagerGestionSistema managerGestionSistema;

	public void enviarCorreoElectronico(String destinatario, String asunto, String mensaje) throws Exception {
		if (!ModelUtil.isEmpty(destinatario))
			enviarConGMail(destinatario.trim(), asunto.trim(), crearMensaje(asunto, mensaje, ""));
	}

	private String crearMensaje(String asunto, String contenido, String nota) {
		String contenidoInicial;
		String contenidoAsunto;
		String contenidoPrincipal;
		String contenidoNota;
		String contenidoFinal;
		SimpleDateFormat anio = new SimpleDateFormat("yyyy");
		String link = "";
		try {
			link = managerGestionSistema.buscarValorParametroNombre("LINK_SISTEMA");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contenidoInicial = "" + "<body style=\"font-family:Verdana, Arial, Helvetica, sans-serif;\">"
				+ "   <table style=\"border-collapse:collapse; border:10px solid #008da9; height:auto; margin:0 auto; width:790px;\">"
				+ "      <tbody style=\"background:#fff; color:#333; font-size:12px; height:50px; line-height:1.6; padding:10px 20px 10px;\">";
		contenidoAsunto = "" + "         <tr>"
				+ "            <td style=\"background:#fff; color:#333; font-size:12px; height:50px; line-height:1.6; padding:10px 20px 10px;\">"
				+ "               <h4 style=\"border-bottom:1px solid #ccc; color:#000; font-size:15px; margin:10px 0 10px; padding:0 0 5px;\">"
				+ "Notificaci&oacute;n del sistema SIGBECOM: " + asunto + "               </h4>" + "            </td>"
				+ "         </tr>";
		contenidoPrincipal = "" + "         <tr>"
				+ "            <td style=\"background:#fff; color:#333; font-size:12px; height:50px; line-height:1.6; padding:10px 20px 10px;\">"
				+ contenido + "            </td>" + "         </tr>" + "         <tr>"
				+ "            <td style=\"background:#fff; color:#333; font-size:12px; height:50px; line-height:1.6; padding:10px 20px 10px;\">"
				+ "<a href=\"" + link + "\">Link del sistema: " + link + "</a>" + "            </td>"
				+ "         </tr>";
		contenidoNota = "" + "         <tr>"
				+ "            <td style=\"background: none repeat scroll 0 0 #FFFFFF; color: #333333; font-size: 12px; mheight: 50px; line-height: 1.6; padding: 10px 20px;\">"
				+ "              <p>===================================<br>" + nota
				+ "<br/>===================================</p>" + "            </td>" + "         </tr>";
		contenidoFinal = "" + "       </tbody>" + "       <tfoot>" + "          <tr>"
				+ "             <td style=\"background:#ddd; color:#333; font-size:12px; line-height:1.3; padding:15px 0; text-align:center;\">"
				+ "               <br>Copyright&copy;" + anio.format(new Date())
				+ " COMITE DE EMPRESA IBARRA. Reservados todos los derechos." + "             </td>" + "          </tr>"
				+ "       </tfoot>" + "    </table>" + "</body>";
		return contenidoInicial + contenidoAsunto + contenidoPrincipal + contenidoNota + contenidoFinal;
	}

	private void cargarParametrosCorreo() throws Exception {
		host = managerGestionSistema.buscarValorParametroNombre("HOST_MAIL");
		user = managerGestionSistema.buscarValorParametroNombre("USER_MAIL");
		clave = managerGestionSistema.buscarValorParametroNombre("PASS_EMAIL");
		port = managerGestionSistema.buscarValorParametroNombre("PORT_MAIL");
	}

	private void enviarConGMail(String destinatario, String asunto, String cuerpo) throws Exception {
		cargarParametrosCorreo();
		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente también.
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host); // El servidor SMTP de Google
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.clave", clave); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", port); // El puerto SMTP seguro de Google
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(user));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario)); // Se podrían añadir
																							// varios de la misma
																							// manera
		message.setSubject(asunto);
		// message.setText(cuerpo);
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.gmail.com", user, clave);
		message.setContent(cuerpo, "text/html;charset=UTF-8");
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	public ManagerGestionSistema getManagerGestionSistema() {
		return managerGestionSistema;
	}

	public void setManagerGestionSistema(ManagerGestionSistema managerGestionSistema) {
		this.managerGestionSistema = managerGestionSistema;
	}

}