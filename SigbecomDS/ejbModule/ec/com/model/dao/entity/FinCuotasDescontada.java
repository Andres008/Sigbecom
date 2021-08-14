package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the fin_cuotas_descontadas database table.
 * 
 */
@Entity
@Table(name = "fin_cuotas_descontadas")
@NamedQuery(name = "FinCuotasDescontada.findAll", query = "SELECT f FROM FinCuotasDescontada f")
public class FinCuotasDescontada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FIN_CUOTAS_DESCONTADAS_ID_GENERATOR", sequenceName = "SEQ_FIN_CUOTAS_DESCONTADAS", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIN_CUOTAS_DESCONTADAS_ID_GENERATOR")
	private long id;

	private BigDecimal anio;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_ejecucion_descuento")
	private Date fechaEjecucionDescuento;

	private BigDecimal mes;

	// bi-directional many-to-one association to FinTablaAmortizacion
	@ManyToOne
	@JoinColumn(name = "id_tabla_amortizacion")
	private FinTablaAmortizacion finTablaAmortizacion;

	// bi-directional many-to-one association to DescEstadoDescuento
	@ManyToOne
	@JoinColumn(name = "id_estado_descuento")
	private DescEstadoDescuento descEstadoDescuento;

	public FinCuotasDescontada() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAnio() {
		return this.anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public Date getFechaEjecucionDescuento() {
		return this.fechaEjecucionDescuento;
	}

	public void setFechaEjecucionDescuento(Date fechaEjecucionDescuento) {
		this.fechaEjecucionDescuento = fechaEjecucionDescuento;
	}

	public BigDecimal getMes() {
		return this.mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public FinTablaAmortizacion getFinTablaAmortizacion() {
		return this.finTablaAmortizacion;
	}

	public void setFinTablaAmortizacion(FinTablaAmortizacion finTablaAmortizacion) {
		this.finTablaAmortizacion = finTablaAmortizacion;
	}

	public DescEstadoDescuento getDescEstadoDescuento() {
		return this.descEstadoDescuento;
	}

	public void setDescEstadoDescuento(DescEstadoDescuento descEstadoDescuento) {
		this.descEstadoDescuento = descEstadoDescuento;
	}

}