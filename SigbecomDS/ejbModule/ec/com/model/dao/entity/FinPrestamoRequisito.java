package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fin_prestamo_requisito database table.
 * 
 */
@Entity
@Table(name="fin_prestamo_requisito")
@NamedQuery(name="FinPrestamoRequisito.findAll", query="SELECT f FROM FinPrestamoRequisito f")
public class FinPrestamoRequisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_PRESTAMO_REQUISITO_ID_GENERATOR", sequenceName="SEQ_FIN_PRESTAMO_REQUISITO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_PRESTAMO_REQUISITO_ID_GENERATOR")
	private long id;

	private String estado;

	private String url;

	//bi-directional many-to-one association to FinPrestamoSocio
	@ManyToOne
	@JoinColumn(name="id_prestamo_socio")
	private FinPrestamoSocio finPrestamoSocio;

	//bi-directional many-to-one association to FinRequisito
	@ManyToOne
	@JoinColumn(name="id_requisito")
	private FinRequisito finRequisito;

	public FinPrestamoRequisito() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public FinPrestamoSocio getFinPrestamoSocio() {
		return this.finPrestamoSocio;
	}

	public void setFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		this.finPrestamoSocio = finPrestamoSocio;
	}

	public FinRequisito getFinRequisito() {
		return this.finRequisito;
	}

	public void setFinRequisito(FinRequisito finRequisito) {
		this.finRequisito = finRequisito;
	}

}