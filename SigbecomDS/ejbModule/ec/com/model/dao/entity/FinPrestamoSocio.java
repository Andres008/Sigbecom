package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the fin_prestamo_socio database table.
 * 
 */
@Entity
@Table(name = "fin_prestamo_socio")
@NamedQuery(name = "FinPrestamoSocio.findAll", query = "SELECT f FROM FinPrestamoSocio f")
public class FinPrestamoSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FIN_PRESTAMO_SOCIO_IDPRESTAMOSOCIO_GENERATOR", sequenceName = "SEQ_FIN_PRESTAMO_SOCIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIN_PRESTAMO_SOCIO_IDPRESTAMOSOCIO_GENERATOR")
	@Column(name = "id_prestamo_socio")
	private long idPrestamoSocio;

	@Column(name = "cuota_mensual")
	private BigDecimal cuotaMensual;

	@Column(name = "cuotas_pagadas")
	private BigDecimal cuotasPagadas;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_primera_couta")
	private Date fechaPrimeraCouta;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_solicitud")
	private Date fechaSolicitud;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_ultima_cuota")
	private Date fechaUltimaCuota;

	@Column(name = "plazo_meses")
	private BigDecimal plazoMeses;

	@Column(name = "saldo_capital")
	private BigDecimal saldoCapital;

	@Column(name = "valor_prestamo")
	private BigDecimal valorPrestamo;

	@Column(name = "valor_recibido")
	private BigDecimal valorRecibido;

	// bi-directional many-to-one association to FinGaranteCredito
	@OneToMany(mappedBy = "finPrestamoSocio")
	private List<FinGaranteCredito> finGaranteCreditos;

	// bi-directional many-to-one association to FinPrestamoRequisito
	@OneToMany(mappedBy = "finPrestamoSocio", cascade = CascadeType.ALL)
	private List<FinPrestamoRequisito> finPrestamoRequisitos;

	// bi-directional many-to-one association to FinEstadoCredito
	@ManyToOne
	@JoinColumn(name = "id_estado_credito")
	private FinEstadoCredito finEstadoCredito;

	// bi-directional many-to-one association to FinTipoCredito
	@ManyToOne
	@JoinColumn(name = "id_tipo_credito")
	private FinTipoCredito finTipoCredito;

	// bi-directional many-to-one association to FinTipoSolicitud
	@ManyToOne
	@JoinColumn(name = "id_tipo_solicitud")
	private FinTipoSolicitud finTipoSolicitud;

	// bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name = "cedula_socio")
	private UsrSocio usrSocio;

	// bi-directional many-to-one association to FinTablaAmortizacion
	@OneToMany(mappedBy = "finPrestamoSocio", cascade = CascadeType.ALL)
	private List<FinTablaAmortizacion> finTablaAmortizacions;

	// bi-directional many-to-one association to FinPrestamoNovacion
	@OneToMany(mappedBy = "finPrestamoSocio1", cascade = CascadeType.ALL)
	private List<FinPrestamoNovacion> finPrestamoNovacions1;

	// bi-directional many-to-one association to FinPrestamoNovacion
	@OneToMany(mappedBy = "finPrestamoSocio2", cascade = CascadeType.ALL)
	private List<FinPrestamoNovacion> finPrestamoNovacions2;

	// bi-directional many-to-one association to FinResolucionPrestamo
	@ManyToOne
	@JoinColumn(name = "id_resolucion_prestamo")
	private FinResolucionPrestamo finResolucionPrestamo;

	public FinPrestamoSocio() {
	}

	public long getIdPrestamoSocio() {
		return this.idPrestamoSocio;
	}

	public void setIdPrestamoSocio(long idPrestamoSocio) {
		this.idPrestamoSocio = idPrestamoSocio;
	}

	public BigDecimal getCuotaMensual() {
		return this.cuotaMensual;
	}

	public void setCuotaMensual(BigDecimal cuotaMensual) {
		this.cuotaMensual = cuotaMensual;
	}

	public BigDecimal getCuotasPagadas() {
		return this.cuotasPagadas;
	}

	public void setCuotasPagadas(BigDecimal cuotasPagadas) {
		this.cuotasPagadas = cuotasPagadas;
	}

	public Date getFechaPrimeraCouta() {
		return this.fechaPrimeraCouta;
	}

	public void setFechaPrimeraCouta(Date fechaPrimeraCouta) {
		this.fechaPrimeraCouta = fechaPrimeraCouta;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaUltimaCuota() {
		return this.fechaUltimaCuota;
	}

	public void setFechaUltimaCuota(Date fechaUltimaCuota) {
		this.fechaUltimaCuota = fechaUltimaCuota;
	}

	public BigDecimal getPlazoMeses() {
		return this.plazoMeses;
	}

	public void setPlazoMeses(BigDecimal plazoMeses) {
		this.plazoMeses = plazoMeses;
	}

	public BigDecimal getSaldoCapital() {
		return this.saldoCapital;
	}

	public void setSaldoCapital(BigDecimal saldoCapital) {
		this.saldoCapital = saldoCapital;
	}

	public BigDecimal getValorPrestamo() {
		return this.valorPrestamo;
	}

	public void setValorPrestamo(BigDecimal valorPrestamo) {
		this.valorPrestamo = valorPrestamo;
	}

	public BigDecimal getValorRecibido() {
		return this.valorRecibido;
	}

	public void setValorRecibido(BigDecimal valorRecibido) {
		this.valorRecibido = valorRecibido;
	}

	public List<FinGaranteCredito> getFinGaranteCreditos() {
		return this.finGaranteCreditos;
	}

	public void setFinGaranteCreditos(List<FinGaranteCredito> finGaranteCreditos) {
		this.finGaranteCreditos = finGaranteCreditos;
	}

	public FinGaranteCredito addFinGaranteCredito(FinGaranteCredito finGaranteCredito) {
		getFinGaranteCreditos().add(finGaranteCredito);
		finGaranteCredito.setFinPrestamoSocio(this);

		return finGaranteCredito;
	}

	public FinGaranteCredito removeFinGaranteCredito(FinGaranteCredito finGaranteCredito) {
		getFinGaranteCreditos().remove(finGaranteCredito);
		finGaranteCredito.setFinPrestamoSocio(null);

		return finGaranteCredito;
	}

	public List<FinPrestamoRequisito> getFinPrestamoRequisitos() {
		return this.finPrestamoRequisitos;
	}

	public void setFinPrestamoRequisitos(List<FinPrestamoRequisito> finPrestamoRequisitos) {
		this.finPrestamoRequisitos = finPrestamoRequisitos;
	}

	public FinPrestamoRequisito addFinPrestamoRequisito(FinPrestamoRequisito finPrestamoRequisito) {
		getFinPrestamoRequisitos().add(finPrestamoRequisito);
		finPrestamoRequisito.setFinPrestamoSocio(this);

		return finPrestamoRequisito;
	}

	public FinPrestamoRequisito removeFinPrestamoRequisito(FinPrestamoRequisito finPrestamoRequisito) {
		getFinPrestamoRequisitos().remove(finPrestamoRequisito);
		finPrestamoRequisito.setFinPrestamoSocio(null);

		return finPrestamoRequisito;
	}

	public FinEstadoCredito getFinEstadoCredito() {
		return this.finEstadoCredito;
	}

	public void setFinEstadoCredito(FinEstadoCredito finEstadoCredito) {
		this.finEstadoCredito = finEstadoCredito;
	}

	public FinTipoCredito getFinTipoCredito() {
		return this.finTipoCredito;
	}

	public void setFinTipoCredito(FinTipoCredito finTipoCredito) {
		this.finTipoCredito = finTipoCredito;
	}

	public FinTipoSolicitud getFinTipoSolicitud() {
		return this.finTipoSolicitud;
	}

	public void setFinTipoSolicitud(FinTipoSolicitud finTipoSolicitud) {
		this.finTipoSolicitud = finTipoSolicitud;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public List<FinTablaAmortizacion> getFinTablaAmortizacions() {
		return this.finTablaAmortizacions;
	}

	public void setFinTablaAmortizacions(List<FinTablaAmortizacion> finTablaAmortizacions) {
		this.finTablaAmortizacions = finTablaAmortizacions;
	}

	public FinTablaAmortizacion addFinTablaAmortizacion(FinTablaAmortizacion finTablaAmortizacion) {
		getFinTablaAmortizacions().add(finTablaAmortizacion);
		finTablaAmortizacion.setFinPrestamoSocio(this);

		return finTablaAmortizacion;
	}

	public FinTablaAmortizacion removeFinTablaAmortizacion(FinTablaAmortizacion finTablaAmortizacion) {
		getFinTablaAmortizacions().remove(finTablaAmortizacion);
		finTablaAmortizacion.setFinPrestamoSocio(null);

		return finTablaAmortizacion;
	}

	public List<FinPrestamoNovacion> getFinPrestamoNovacions1() {
		return this.finPrestamoNovacions1;
	}

	public void setFinPrestamoNovacions1(List<FinPrestamoNovacion> finPrestamoNovacions1) {
		this.finPrestamoNovacions1 = finPrestamoNovacions1;
	}

	public FinPrestamoNovacion addFinPrestamoNovacions1(FinPrestamoNovacion finPrestamoNovacions1) {
		getFinPrestamoNovacions1().add(finPrestamoNovacions1);
		finPrestamoNovacions1.setFinPrestamoSocio1(this);

		return finPrestamoNovacions1;
	}

	public FinPrestamoNovacion removeFinPrestamoNovacions1(FinPrestamoNovacion finPrestamoNovacions1) {
		getFinPrestamoNovacions1().remove(finPrestamoNovacions1);
		finPrestamoNovacions1.setFinPrestamoSocio1(null);

		return finPrestamoNovacions1;
	}

	public List<FinPrestamoNovacion> getFinPrestamoNovacions2() {
		return this.finPrestamoNovacions2;
	}

	public void setFinPrestamoNovacions2(List<FinPrestamoNovacion> finPrestamoNovacions2) {
		this.finPrestamoNovacions2 = finPrestamoNovacions2;
	}

	public FinPrestamoNovacion addFinPrestamoNovacions2(FinPrestamoNovacion finPrestamoNovacions2) {
		getFinPrestamoNovacions2().add(finPrestamoNovacions2);
		finPrestamoNovacions2.setFinPrestamoSocio2(this);

		return finPrestamoNovacions2;
	}

	public FinPrestamoNovacion removeFinPrestamoNovacions2(FinPrestamoNovacion finPrestamoNovacions2) {
		getFinPrestamoNovacions2().remove(finPrestamoNovacions2);
		finPrestamoNovacions2.setFinPrestamoSocio2(null);

		return finPrestamoNovacions2;
	}

	public FinResolucionPrestamo getFinResolucionPrestamo() {
		return this.finResolucionPrestamo;
	}

	public void setFinResolucionPrestamo(FinResolucionPrestamo finResolucionPrestamo) {
		this.finResolucionPrestamo = finResolucionPrestamo;
	}

}