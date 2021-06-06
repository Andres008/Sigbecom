package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the fin_cuotas_descontadas database table.
 * 
 */
@Entity
@Table(name="fin_cuotas_descontadas")
@NamedQuery(name="FinCuotasDescontada.findAll", query="SELECT f FROM FinCuotasDescontada f")
public class FinCuotasDescontada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_CUOTAS_DESCONTADAS_ID_GENERATOR", sequenceName="SEQ_FIN_CUOTAS_DESCONTADAS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_CUOTAS_DESCONTADAS_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ejecucion_descuento")
	private Date fechaEjecucionDescuento;

	//bi-directional many-to-one association to FinTablaAmortizacion
	@ManyToOne
	@JoinColumn(name="id_tabla_amortizacion")
	private FinTablaAmortizacion finTablaAmortizacion;

	public FinCuotasDescontada() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFechaEjecucionDescuento() {
		return this.fechaEjecucionDescuento;
	}

	public void setFechaEjecucionDescuento(Date fechaEjecucionDescuento) {
		this.fechaEjecucionDescuento = fechaEjecucionDescuento;
	}

	public FinTablaAmortizacion getFinTablaAmortizacion() {
		return this.finTablaAmortizacion;
	}

	public void setFinTablaAmortizacion(FinTablaAmortizacion finTablaAmortizacion) {
		this.finTablaAmortizacion = finTablaAmortizacion;
	}

}