package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fin_estado_cuota database table.
 * 
 */
@Entity
@Table(name="fin_estado_cuota")
@NamedQuery(name="FinEstadoCuota.findAll", query="SELECT f FROM FinEstadoCuota f")
public class FinEstadoCuota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_ESTADO_CUOTA_IDESTADOCUOTA_GENERATOR", sequenceName="SEQ_FIN_ESTADO_CUOTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_ESTADO_CUOTA_IDESTADOCUOTA_GENERATOR")
	@Column(name="id_estado_cuota")
	private long idEstadoCuota;

	private String descripcion;

	//bi-directional many-to-one association to FinTablaAmortizacion
	@OneToMany(mappedBy="finEstadoCuota")
	private List<FinTablaAmortizacion> finTablaAmortizacions;

	public FinEstadoCuota() {
	}
	
	

	public FinEstadoCuota(long idEstadoCuota) {
		super();
		this.idEstadoCuota = idEstadoCuota;
	}

	public long getIdEstadoCuota() {
		return this.idEstadoCuota;
	}

	public void setIdEstadoCuota(long idEstadoCuota) {
		this.idEstadoCuota = idEstadoCuota;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<FinTablaAmortizacion> getFinTablaAmortizacions() {
		return this.finTablaAmortizacions;
	}

	public void setFinTablaAmortizacions(List<FinTablaAmortizacion> finTablaAmortizacions) {
		this.finTablaAmortizacions = finTablaAmortizacions;
	}

	public FinTablaAmortizacion addFinTablaAmortizacion(FinTablaAmortizacion finTablaAmortizacion) {
		getFinTablaAmortizacions().add(finTablaAmortizacion);
		finTablaAmortizacion.setFinEstadoCuota(this);

		return finTablaAmortizacion;
	}

	public FinTablaAmortizacion removeFinTablaAmortizacion(FinTablaAmortizacion finTablaAmortizacion) {
		getFinTablaAmortizacions().remove(finTablaAmortizacion);
		finTablaAmortizacion.setFinEstadoCuota(null);

		return finTablaAmortizacion;
	}

}