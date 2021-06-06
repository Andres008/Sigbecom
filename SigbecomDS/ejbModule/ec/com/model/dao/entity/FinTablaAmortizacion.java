package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the fin_tabla_amortizacion database table.
 * 
 */
@Entity
@Table(name = "fin_tabla_amortizacion")
@NamedQuery(name = "FinTablaAmortizacion.findAll", query = "SELECT f FROM FinTablaAmortizacion f")
public class FinTablaAmortizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FIN_TABLA_AMORTIZACION_IDTABLAAMORTIZACION_GENERATOR", sequenceName = "SEQ_FIN_TABLA_AMORTIZACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIN_TABLA_AMORTIZACION_IDTABLAAMORTIZACION_GENERATOR")
	@Column(name = "id_tabla_amortizacion")
	private long idTablaAmortizacion;

	private BigDecimal capital;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_pago")
	private Date fechaPago;

	private BigDecimal interes;

	@Column(name = "numero_cuota")
	private BigDecimal numeroCuota;

	@Column(name = "valor_cuota")
	private BigDecimal valorCuota;

	@Column(name = "saldo_capital")
	private BigDecimal saldoCapital;

	// bi-directional many-to-one association to FinEstadoCuota
	@ManyToOne
	@JoinColumn(name = "id_estado_cuota")
	private FinEstadoCuota finEstadoCuota;

	// bi-directional many-to-one association to FinPrestamoSocio
	@ManyToOne
	@JoinColumn(name = "id_prestamo_socio")
	private FinPrestamoSocio finPrestamoSocio;

	// bi-directional many-to-one association to FinCuotasDescontada
	@OneToMany(mappedBy = "finTablaAmortizacion")
	private List<FinCuotasDescontada> finCuotasDescontadas;

	public FinTablaAmortizacion() {
	}

	public long getIdTablaAmortizacion() {
		return this.idTablaAmortizacion;
	}

	public void setIdTablaAmortizacion(long idTablaAmortizacion) {
		this.idTablaAmortizacion = idTablaAmortizacion;
	}

	public BigDecimal getCapital() {
		return this.capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getInteres() {
		return this.interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getNumeroCuota() {
		return this.numeroCuota;
	}

	public void setNumeroCuota(BigDecimal numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public FinEstadoCuota getFinEstadoCuota() {
		return this.finEstadoCuota;
	}

	public void setFinEstadoCuota(FinEstadoCuota finEstadoCuota) {
		this.finEstadoCuota = finEstadoCuota;
	}

	public FinPrestamoSocio getFinPrestamoSocio() {
		return this.finPrestamoSocio;
	}

	public void setFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		this.finPrestamoSocio = finPrestamoSocio;
	}

	public BigDecimal getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public BigDecimal getSaldoCapital() {
		return saldoCapital;
	}

	public void setSaldoCapital(BigDecimal saldoCapital) {
		this.saldoCapital = saldoCapital;
	}

	public List<FinCuotasDescontada> getFinCuotasDescontadas() {
		return finCuotasDescontadas;
	}

	public void setFinCuotasDescontadas(List<FinCuotasDescontada> finCuotasDescontadas) {
		this.finCuotasDescontadas = finCuotasDescontadas;
	}

}