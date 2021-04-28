package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the log_general database table.
 * 
 */
@Entity
@Table(name="log_general")
@NamedQuery(name="LogGeneral.findAll", query="SELECT l FROM LogGeneral l")
public class LogGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOG_GENERAL_ID_GENERATOR", sequenceName="SEQ_LOG_GENERAL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_GENERAL_ID_GENERATOR")
	private long id;

	@Column(name="direccion_ip")
	private String direccionIp;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_evento")
	private Date fechaEvento;

	private String mensaje;

	private String metodo;

	//bi-directional many-to-one association to LogCategoriaEvento
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private LogCategoriaEvento logCategoriaEvento;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	public LogGeneral() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDireccionIp() {
		return this.direccionIp;
	}

	public void setDireccionIp(String direccionIp) {
		this.direccionIp = direccionIp;
	}

	public Date getFechaEvento() {
		return this.fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMetodo() {
		return this.metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public LogCategoriaEvento getLogCategoriaEvento() {
		return this.logCategoriaEvento;
	}

	public void setLogCategoriaEvento(LogCategoriaEvento logCategoriaEvento) {
		this.logCategoriaEvento = logCategoriaEvento;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

}