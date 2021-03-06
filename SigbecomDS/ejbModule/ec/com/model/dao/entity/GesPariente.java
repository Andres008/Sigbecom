package ec.com.model.dao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the ges_pariente database table.
 * 
 */
@Entity
@Table(name="ges_pariente")
@NamedQuery(name="GesPariente.findAll", query="SELECT g FROM GesPariente g")
public class GesPariente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_PARIENTE_IDPARIENTE_GENERATOR", sequenceName="SEQ_GES_PARIENTE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_PARIENTE_IDPARIENTE_GENERATOR")
	@Column(name="id_pariente")
	private long idPariente;

	@Column(name="contacto_emergencia")
	private String contactoEmergencia;

	private String pariente;

	//bi-directional many-to-one association to GesPersona
	@ManyToOne
	@JoinColumn(name="cedula")
	private GesPersona gesPersona;

	//bi-directional many-to-one association to UsrConsanguinidad
	@ManyToOne
	@JoinColumn(name="id_consanguinidad")
	private UsrConsanguinidad usrConsanguinidad;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;
	
	//bi-directional many-to-one association to gesPariente
	@OneToMany(mappedBy="gesPariente")
	private List<SesvasSolicitud> sesvasSolicituds;

	public GesPariente() {
	}

	public long getIdPariente() {
		return this.idPariente;
	}

	public void setIdPariente(long idPariente) {
		this.idPariente = idPariente;
	}

	public String getContactoEmergencia() {
		return this.contactoEmergencia;
	}

	public void setContactoEmergencia(String contactoEmergencia) {
		this.contactoEmergencia = contactoEmergencia;
	}

	public String getPariente() {
		return this.pariente;
	}

	public void setPariente(String pariente) {
		this.pariente = pariente;
	}

	public GesPersona getGesPersona() {
		return this.gesPersona;
	}

	public void setGesPersona(GesPersona gesPersona) {
		this.gesPersona = gesPersona;
	}

	public UsrConsanguinidad getUsrConsanguinidad() {
		return this.usrConsanguinidad;
	}

	public void setUsrConsanguinidad(UsrConsanguinidad usrConsanguinidad) {
		this.usrConsanguinidad = usrConsanguinidad;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public List<SesvasSolicitud> getSesvasSolicituds() {
		return sesvasSolicituds;
	}

	public void setSesvasSolicituds(List<SesvasSolicitud> sesvasSolicituds) {
		this.sesvasSolicituds = sesvasSolicituds;
	}
	public SesvasSolicitud addSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		getSesvasSolicituds().add(sesvasSolicitud);
		sesvasSolicitud.setGesPariente(this);

		return sesvasSolicitud;
	}

	public SesvasSolicitud removeSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		getSesvasSolicituds().remove(sesvasSolicitud);
		sesvasSolicitud.setGesPariente(null);

		return sesvasSolicitud;
	}
}