package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aut_usuario database table.
 * 
 */
@Entity
@Table(name="aut_usuario")
@NamedQuery(name="AutUsuario.findAll", query="SELECT a FROM AutUsuario a")
public class AutUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUT_USUARIO_USUARIO_GENERATOR", sequenceName="SEQ_AUT_USUARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUT_USUARIO_USUARIO_GENERATOR")
	private String usuario;

	private String clave;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_baja")
	private Date fechaBaja;

	@Column(name="primer_inicio")
	private String primerInicio;

	//bi-directional many-to-one association to AutRol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private AutRol autRol;

	//bi-directional one-to-one association to GesPersona
	@OneToOne
	@JoinColumn(name="usuario")
	private GesPersona gesPersona;

	//bi-directional many-to-one association to LogGeneral
	@OneToMany(mappedBy="autUsuario")
	private List<LogGeneral> logGenerals;

	public AutUsuario() {
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getPrimerInicio() {
		return this.primerInicio;
	}

	public void setPrimerInicio(String primerInicio) {
		this.primerInicio = primerInicio;
	}

	public AutRol getAutRol() {
		return this.autRol;
	}

	public void setAutRol(AutRol autRol) {
		this.autRol = autRol;
	}

	public GesPersona getGesPersona() {
		return this.gesPersona;
	}

	public void setGesPersona(GesPersona gesPersona) {
		this.gesPersona = gesPersona;
	}

	public List<LogGeneral> getLogGenerals() {
		return this.logGenerals;
	}

	public void setLogGenerals(List<LogGeneral> logGenerals) {
		this.logGenerals = logGenerals;
	}

	public LogGeneral addLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().add(logGeneral);
		logGeneral.setAutUsuario(this);

		return logGeneral;
	}

	public LogGeneral removeLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().remove(logGeneral);
		logGeneral.setAutUsuario(null);

		return logGeneral;
	}

}