package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fin_requisitos database table.
 * 
 */
@Entity
@Table(name="fin_requisitos")
@NamedQuery(name="FinRequisito.findAll", query="SELECT f FROM FinRequisito f")
public class FinRequisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_REQUISITOS_IDREQUISITO_GENERATOR", sequenceName="SEQ_FIN_REQUISITOS", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_REQUISITOS_IDREQUISITO_GENERATOR")
	@Column(name="id_requisito")
	private long idRequisito;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to FinPrestamoRequisito
	@OneToMany(mappedBy="finRequisito")
	private List<FinPrestamoRequisito> finPrestamoRequisitos;

	//bi-directional many-to-one association to FinTipcrediRequisito
	@OneToMany(mappedBy="finRequisito")
	private List<FinTipcrediRequisito> finTipcrediRequisitos;

	public FinRequisito() {
	}

	public long getIdRequisito() {
		return this.idRequisito;
	}

	public void setIdRequisito(long idRequisito) {
		this.idRequisito = idRequisito;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion.toUpperCase();
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public List<FinPrestamoRequisito> getFinPrestamoRequisitos() {
		return this.finPrestamoRequisitos;
	}

	public void setFinPrestamoRequisitos(List<FinPrestamoRequisito> finPrestamoRequisitos) {
		this.finPrestamoRequisitos = finPrestamoRequisitos;
	}

	public FinPrestamoRequisito addFinPrestamoRequisito(FinPrestamoRequisito finPrestamoRequisito) {
		getFinPrestamoRequisitos().add(finPrestamoRequisito);
		finPrestamoRequisito.setFinRequisito(this);

		return finPrestamoRequisito;
	}

	public FinPrestamoRequisito removeFinPrestamoRequisito(FinPrestamoRequisito finPrestamoRequisito) {
		getFinPrestamoRequisitos().remove(finPrestamoRequisito);
		finPrestamoRequisito.setFinRequisito(null);

		return finPrestamoRequisito;
	}

	public List<FinTipcrediRequisito> getFinTipcrediRequisitos() {
		return this.finTipcrediRequisitos;
	}

	public void setFinTipcrediRequisitos(List<FinTipcrediRequisito> finTipcrediRequisitos) {
		this.finTipcrediRequisitos = finTipcrediRequisitos;
	}

	public FinTipcrediRequisito addFinTipcrediRequisito(FinTipcrediRequisito finTipcrediRequisito) {
		getFinTipcrediRequisitos().add(finTipcrediRequisito);
		finTipcrediRequisito.setFinRequisito(this);

		return finTipcrediRequisito;
	}

	public FinTipcrediRequisito removeFinTipcrediRequisito(FinTipcrediRequisito finTipcrediRequisito) {
		getFinTipcrediRequisitos().remove(finTipcrediRequisito);
		finTipcrediRequisito.setFinRequisito(null);

		return finTipcrediRequisito;
	}

}