package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the conv_amortizacion database table.
 * 
 */
@Entity
@Table(name="conv_amortizacion")
@NamedQuery(name="ConvAmortizacion.findAll", query="SELECT c FROM ConvAmortizacion c")
public class ConvAmortizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONV_AMORTIZACION_IDCONVAMORTIZACION_GENERATOR", sequenceName="SEQ_CONV_AMORTIZACION", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONV_AMORTIZACION_IDCONVAMORTIZACION_GENERATOR")
	@Column(name="id_conv_amortizacion")
	private long idConvAmortizacion;

	private BigDecimal capital;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pago")
	private Date fechaPago;

	private BigDecimal interes;

	@Column(name="numero_cuota")
	private Integer numeroCuota;

	private BigDecimal saldo;

	@Column(name="valor_cuota")
	private BigDecimal valorCuota;

	//bi-directional many-to-one association to ConvAdquirido
	@ManyToOne
	@JoinColumn(name="id_conv_adquiridos")
	private ConvAdquirido convAdquirido;
	
	@Column(name="estado_pago")
	private String estadoPago;

	public ConvAmortizacion() {
	}

	public long getIdConvAmortizacion() {
		return this.idConvAmortizacion;
	}

	public void setIdConvAmortizacion(long idConvAmortizacion) {
		this.idConvAmortizacion = idConvAmortizacion;
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

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getValorCuota() {
		return this.valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public ConvAdquirido getConvAdquirido() {
		return this.convAdquirido;
	}

	public void setConvAdquirido(ConvAdquirido convAdquirido) {
		this.convAdquirido = convAdquirido;
	}

	public Integer getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(Integer numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public String getEstadoPago() {
		return estadoPago;
	}

	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}
	
}