package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the aut_rol database table.
 * 
 */
@Entity
@Table(name = "aut_rol")
@NamedQuery(name = "AutRol.findAll", query = "SELECT a FROM AutRol a")
public class AutRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "AUT_ROL_IDROL_GENERATOR", sequenceName = "SEQ_AUT_ROL", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUT_ROL_IDROL_GENERATOR")
	@Column(name = "id_rol")
	private long idRol;

	private String descripcion;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicial")
	private Date fechaInicial;

	private String nombre;

	// bi-directional many-to-one association to AutRolPerfil
	@OneToMany(mappedBy = "autRol")
	private List<AutRolPerfil> autRolPerfils;

	// bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy = "autRol")
	private List<UsrSocio> usrSocios;

	// bi-directional many-to-one association to UsrTipoSocio
	@ManyToOne
	@JoinColumn(name = "id_tipo_socio")
	private UsrTipoSocio usrTipoSocio;

	public AutRol() {
	}

	public long getIdRol() {
		return this.idRol;
	}

	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AutRolPerfil> getAutRolPerfils() {
		return this.autRolPerfils;
	}

	public void setAutRolPerfils(List<AutRolPerfil> autRolPerfils) {
		this.autRolPerfils = autRolPerfils;
	}

	public AutRolPerfil addAutRolPerfil(AutRolPerfil autRolPerfil) {
		getAutRolPerfils().add(autRolPerfil);
		autRolPerfil.setAutRol(this);

		return autRolPerfil;
	}

	public AutRolPerfil removeAutRolPerfil(AutRolPerfil autRolPerfil) {
		getAutRolPerfils().remove(autRolPerfil);
		autRolPerfil.setAutRol(null);

		return autRolPerfil;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setAutRol(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setAutRol(null);

		return usrSocio;
	}

	public UsrTipoSocio getUsrTipoSocio() {
		return usrTipoSocio;
	}

	public void setUsrTipoSocio(UsrTipoSocio usrTipoSocio) {
		this.usrTipoSocio = usrTipoSocio;
	}

}