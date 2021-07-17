package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fin_acciones_creditos database table.
 * 
 */
@Entity
@Table(name="fin_acciones_creditos")
@NamedQuery(name="FinAccionesCredito.findAll", query="SELECT f FROM FinAccionesCredito f")
public class FinAccionesCredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_ACCIONES_CREDITOS_IDACCION_GENERATOR", sequenceName="SEQ_FIN_ACCIONES_CREDITOS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_ACCIONES_CREDITOS_IDACCION_GENERATOR")
	@Column(name="id_accion")
	private long idAccion;

	private String descripcion;

	//bi-directional many-to-one association to FinAccionPrestamo
	@OneToMany(mappedBy="finAccionesCredito")
	private List<FinAccionPrestamo> finAccionPrestamos;

	public FinAccionesCredito() {
	}
	
	

	public FinAccionesCredito(long idAccion) {
		super();
		this.idAccion = idAccion;
	}



	public long getIdAccion() {
		return this.idAccion;
	}

	public void setIdAccion(long idAccion) {
		this.idAccion = idAccion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<FinAccionPrestamo> getFinAccionPrestamos() {
		return this.finAccionPrestamos;
	}

	public void setFinAccionPrestamos(List<FinAccionPrestamo> finAccionPrestamos) {
		this.finAccionPrestamos = finAccionPrestamos;
	}

	public FinAccionPrestamo addFinAccionPrestamo(FinAccionPrestamo finAccionPrestamo) {
		getFinAccionPrestamos().add(finAccionPrestamo);
		finAccionPrestamo.setFinAccionesCredito(this);

		return finAccionPrestamo;
	}

	public FinAccionPrestamo removeFinAccionPrestamo(FinAccionPrestamo finAccionPrestamo) {
		getFinAccionPrestamos().remove(finAccionPrestamo);
		finAccionPrestamo.setFinAccionesCredito(null);

		return finAccionPrestamo;
	}

}