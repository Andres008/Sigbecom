package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the usr_socio database table.
 * 
 */
@Entity
@Table(name = "usr_socio")
@NamedQuery(name = "UsrSocio.findAll", query = "SELECT u FROM UsrSocio u")
public class UsrSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cedula_socio")
	private String cedulaSocio;

	private String clave;

	private String direccion;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_alta")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "id_socio")
	private BigDecimal idSocio;

	@Column(name = "ingresos_mensuales")
	private BigDecimal ingresosMensuales;

	@Column(name = "otros_ingresos")
	private BigDecimal otrosIngresos;

	@Column(name = "primer_inicio")
	private String primerInicio;

	private String vehiculo;
	
	@Column(name="url_foto")
	private String urlFoto;

	// bi-directional many-to-one association to GesPariente
	@OneToMany(mappedBy = "usrSocio", cascade = CascadeType.ALL)
	private List<GesPariente> gesParientes;

	// bi-directional many-to-one association to LogGeneral
	@OneToMany(mappedBy = "usrSocio")
	private List<LogGeneral> logGenerals;

	// bi-directional many-to-one association to UsrCuentaSocio
	@OneToMany(mappedBy = "usrSocio")
	private List<UsrCuentaSocio> usrCuentaSocios;

	// bi-directional many-to-one association to UsrInstruccion
	@OneToMany(mappedBy = "usrSocio")
	private List<UsrInstruccion> usrInstruccions;

	// bi-directional many-to-one association to UsrLicenciaSocio
	@OneToMany(mappedBy = "usrSocio")
	private List<UsrLicenciaSocio> usrLicenciaSocios;

	// bi-directional many-to-one association to AutRol
	@ManyToOne
	@JoinColumn(name = "id_rol")
	private AutRol autRol;

	// bi-directional many-to-one association to GesPersona
	@ManyToOne
	@JoinColumn(name = "cedula_socio", insertable = false, updatable = false)
	private GesPersona gesPersona;

	// bi-directional many-to-one association to UsrAgencia
	@ManyToOne
	@JoinColumn(name = "id_agencia")
	private UsrAgencia usrAgencia;

	// bi-directional many-to-one association to UsrEstadoSocio
	@ManyToOne
	@JoinColumn(name = "id_estado")
	private UsrEstadoSocio usrEstadoSocio;

	// bi-directional many-to-one association to UsrArea
	@ManyToOne
	@JoinColumn(name = "id_area")
	private UsrArea usrArea;

	// bi-directional many-to-one association to UsrCargo
	@ManyToOne
	@JoinColumn(name = "id_cargo")
	private UsrCargo usrCargo;

	// bi-directional many-to-one association to UsrLugarTrabajo
	@ManyToOne
	@JoinColumn(name = "id_lugar_trabajo")
	private UsrLugarTrabajo usrLugarTrabajo;

	// bi-directional many-to-one association to UsrParroquia
	@ManyToOne
	@JoinColumn(name = "id_parroquia")
	private UsrParroquia usrParroquia;

	// bi-directional many-to-one association to UsrTipoVivienda
	@ManyToOne
	@JoinColumn(name = "id_tipo_vivienda")
	private UsrTipoVivienda usrTipoVivienda;

	// bi-directional many-to-one association to SesvasSolicitud
	@OneToMany(mappedBy = "usrSocio")
	private List<SesvasSolicitud> sesvasSolicituds;

	public UsrSocio() {
	}

	public String getCedulaSocio() {
		return this.cedulaSocio;
	}

	public void setCedulaSocio(String cedulaSocio) {
		this.cedulaSocio = cedulaSocio;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public BigDecimal getIdSocio() {
		return this.idSocio;
	}

	public void setIdSocio(BigDecimal idSocio) {
		this.idSocio = idSocio;
	}

	public BigDecimal getIngresosMensuales() {
		return this.ingresosMensuales;
	}

	public void setIngresosMensuales(BigDecimal ingresosMensuales) {
		this.ingresosMensuales = ingresosMensuales;
	}

	public BigDecimal getOtrosIngresos() {
		return this.otrosIngresos;
	}

	public void setOtrosIngresos(BigDecimal otrosIngresos) {
		this.otrosIngresos = otrosIngresos;
	}

	public String getPrimerInicio() {
		return this.primerInicio;
	}

	public void setPrimerInicio(String primerInicio) {
		this.primerInicio = primerInicio;
	}

	public String getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<GesPariente> getGesParientes() {
		return this.gesParientes;
	}

	public void setGesParientes(List<GesPariente> gesParientes) {
		this.gesParientes = gesParientes;
	}

	public GesPariente addGesPariente(GesPariente gesPariente) {
		getGesParientes().add(gesPariente);
		gesPariente.setUsrSocio(this);

		return gesPariente;
	}

	public GesPariente removeGesPariente(GesPariente gesPariente) {
		getGesParientes().remove(gesPariente);
		gesPariente.setUsrSocio(null);

		return gesPariente;
	}

	public List<LogGeneral> getLogGenerals() {
		return this.logGenerals;
	}

	public void setLogGenerals(List<LogGeneral> logGenerals) {
		this.logGenerals = logGenerals;
	}

	public LogGeneral addLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().add(logGeneral);
		logGeneral.setUsrSocio(this);

		return logGeneral;
	}

	public LogGeneral removeLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().remove(logGeneral);
		logGeneral.setUsrSocio(null);

		return logGeneral;
	}

	public List<UsrCuentaSocio> getUsrCuentaSocios() {
		return this.usrCuentaSocios;
	}

	public void setUsrCuentaSocios(List<UsrCuentaSocio> usrCuentaSocios) {
		this.usrCuentaSocios = usrCuentaSocios;
	}

	public UsrCuentaSocio addUsrCuentaSocio(UsrCuentaSocio usrCuentaSocio) {
		getUsrCuentaSocios().add(usrCuentaSocio);
		usrCuentaSocio.setUsrSocio(this);

		return usrCuentaSocio;
	}

	public UsrCuentaSocio removeUsrCuentaSocio(UsrCuentaSocio usrCuentaSocio) {
		getUsrCuentaSocios().remove(usrCuentaSocio);
		usrCuentaSocio.setUsrSocio(null);

		return usrCuentaSocio;
	}

	public List<UsrInstruccion> getUsrInstruccions() {
		return this.usrInstruccions;
	}

	public void setUsrInstruccions(List<UsrInstruccion> usrInstruccions) {
		this.usrInstruccions = usrInstruccions;
	}

	public UsrInstruccion addUsrInstruccion(UsrInstruccion usrInstruccion) {
		getUsrInstruccions().add(usrInstruccion);
		usrInstruccion.setUsrSocio(this);

		return usrInstruccion;
	}

	public UsrInstruccion removeUsrInstruccion(UsrInstruccion usrInstruccion) {
		getUsrInstruccions().remove(usrInstruccion);
		usrInstruccion.setUsrSocio(null);

		return usrInstruccion;
	}

	public List<UsrLicenciaSocio> getUsrLicenciaSocios() {
		return this.usrLicenciaSocios;
	}

	public void setUsrLicenciaSocios(List<UsrLicenciaSocio> usrLicenciaSocios) {
		this.usrLicenciaSocios = usrLicenciaSocios;
	}

	public UsrLicenciaSocio addUsrLicenciaSocio(UsrLicenciaSocio usrLicenciaSocio) {
		getUsrLicenciaSocios().add(usrLicenciaSocio);
		usrLicenciaSocio.setUsrSocio(this);

		return usrLicenciaSocio;
	}

	public UsrLicenciaSocio removeUsrLicenciaSocio(UsrLicenciaSocio usrLicenciaSocio) {
		getUsrLicenciaSocios().remove(usrLicenciaSocio);
		usrLicenciaSocio.setUsrSocio(null);

		return usrLicenciaSocio;
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

	public UsrAgencia getUsrAgencia() {
		return this.usrAgencia;
	}

	public void setUsrAgencia(UsrAgencia usrAgencia) {
		this.usrAgencia = usrAgencia;
	}

	public UsrArea getUsrArea() {
		return this.usrArea;
	}

	public void setUsrArea(UsrArea usrArea) {
		this.usrArea = usrArea;
	}

	public UsrCargo getUsrCargo() {
		return this.usrCargo;
	}

	public void setUsrCargo(UsrCargo usrCargo) {
		this.usrCargo = usrCargo;
	}

	public UsrLugarTrabajo getUsrLugarTrabajo() {
		return this.usrLugarTrabajo;
	}

	public void setUsrLugarTrabajo(UsrLugarTrabajo usrLugarTrabajo) {
		this.usrLugarTrabajo = usrLugarTrabajo;
	}

	public UsrParroquia getUsrParroquia() {
		return this.usrParroquia;
	}

	public void setUsrParroquia(UsrParroquia usrParroquia) {
		this.usrParroquia = usrParroquia;
	}

	public UsrTipoVivienda getUsrTipoVivienda() {
		return this.usrTipoVivienda;
	}

	public void setUsrTipoVivienda(UsrTipoVivienda usrTipoVivienda) {
		this.usrTipoVivienda = usrTipoVivienda;
	}

	public UsrEstadoSocio getUsrEstadoSocio() {
		return usrEstadoSocio;
	}

	public void setUsrEstadoSocio(UsrEstadoSocio usrEstadoSocio) {
		this.usrEstadoSocio = usrEstadoSocio;
	}

	public List<SesvasSolicitud> getSesvasSolicituds() {
		return sesvasSolicituds;
	}

	public void setSesvasSolicituds(List<SesvasSolicitud> sesvasSolicituds) {
		this.sesvasSolicituds = sesvasSolicituds;
	}

	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

}