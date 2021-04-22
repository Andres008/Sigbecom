package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ges_persona database table.
 * 
 */
@Entity
@Table(name="ges_persona")
@NamedQuery(name="GesPersona.findAll", query="SELECT g FROM GesPersona g")
public class GesPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_PERSONA_CEDULA_GENERATOR", sequenceName="SEQ_GES_PERSONA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_PERSONA_CEDULA_GENERATOR")
	private String cedula;

	private String apellidos;

	private String email;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_def")
	private Date fechaDef;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nac")
	private Date fechaNac;

	private String nombres;

	private String telefono;

	@Column(name="url_foto")
	private String urlFoto;

	//bi-directional many-to-one association to GesDiscapacidadPersona
	@OneToMany(mappedBy="gesPersona")
	private List<GesDiscapacidadPersona> gesDiscapacidadPersonas;

	//bi-directional many-to-one association to GesPariente
	@OneToMany(mappedBy="gesPersona")
	private List<GesPariente> gesParientes;

	//bi-directional many-to-one association to GesEstadoCivil
	@ManyToOne
	@JoinColumn(name="id_estado_civil")
	private GesEstadoCivil gesEstadoCivil;

	//bi-directional many-to-one association to GesEtnia
	@ManyToOne
	@JoinColumn(name="id_etnia")
	private GesEtnia gesEtnia;

	//bi-directional many-to-one association to GesGenero
	@ManyToOne
	@JoinColumn(name="id_genero")
	private GesGenero gesGenero;

	//bi-directional many-to-one association to GesTipoSangre
	@ManyToOne
	@JoinColumn(name="id_tipo_sangre")
	private GesTipoSangre gesTipoSangre;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="gesPersona")
	private List<UsrSocio> usrSocios;

	public GesPersona() {
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaDef() {
		return this.fechaDef;
	}

	public void setFechaDef(Date fechaDef) {
		this.fechaDef = fechaDef;
	}

	public Date getFechaNac() {
		return this.fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUrlFoto() {
		return this.urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public List<GesDiscapacidadPersona> getGesDiscapacidadPersonas() {
		return this.gesDiscapacidadPersonas;
	}

	public void setGesDiscapacidadPersonas(List<GesDiscapacidadPersona> gesDiscapacidadPersonas) {
		this.gesDiscapacidadPersonas = gesDiscapacidadPersonas;
	}

	public GesDiscapacidadPersona addGesDiscapacidadPersona(GesDiscapacidadPersona gesDiscapacidadPersona) {
		getGesDiscapacidadPersonas().add(gesDiscapacidadPersona);
		gesDiscapacidadPersona.setGesPersona(this);

		return gesDiscapacidadPersona;
	}

	public GesDiscapacidadPersona removeGesDiscapacidadPersona(GesDiscapacidadPersona gesDiscapacidadPersona) {
		getGesDiscapacidadPersonas().remove(gesDiscapacidadPersona);
		gesDiscapacidadPersona.setGesPersona(null);

		return gesDiscapacidadPersona;
	}

	public List<GesPariente> getGesParientes() {
		return this.gesParientes;
	}

	public void setGesParientes(List<GesPariente> gesParientes) {
		this.gesParientes = gesParientes;
	}

	public GesPariente addGesPariente(GesPariente gesPariente) {
		getGesParientes().add(gesPariente);
		gesPariente.setGesPersona(this);

		return gesPariente;
	}

	public GesPariente removeGesPariente(GesPariente gesPariente) {
		getGesParientes().remove(gesPariente);
		gesPariente.setGesPersona(null);

		return gesPariente;
	}

	public GesEstadoCivil getGesEstadoCivil() {
		return this.gesEstadoCivil;
	}

	public void setGesEstadoCivil(GesEstadoCivil gesEstadoCivil) {
		this.gesEstadoCivil = gesEstadoCivil;
	}

	public GesEtnia getGesEtnia() {
		return this.gesEtnia;
	}

	public void setGesEtnia(GesEtnia gesEtnia) {
		this.gesEtnia = gesEtnia;
	}

	public GesGenero getGesGenero() {
		return this.gesGenero;
	}

	public void setGesGenero(GesGenero gesGenero) {
		this.gesGenero = gesGenero;
	}

	public GesTipoSangre getGesTipoSangre() {
		return this.gesTipoSangre;
	}

	public void setGesTipoSangre(GesTipoSangre gesTipoSangre) {
		this.gesTipoSangre = gesTipoSangre;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setGesPersona(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setGesPersona(null);

		return usrSocio;
	}

}