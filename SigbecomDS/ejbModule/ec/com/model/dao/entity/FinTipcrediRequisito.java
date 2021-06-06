package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fin_tipcredi_requisito database table.
 * 
 */
@Entity
@Table(name="fin_tipcredi_requisito")
@NamedQuery(name="FinTipcrediRequisito.findAll", query="SELECT f FROM FinTipcrediRequisito f")
public class FinTipcrediRequisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_TIPCREDI_REQUISITO_ID_GENERATOR", sequenceName="SEQ_FIN_TIPCREDI_REQUISITO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_TIPCREDI_REQUISITO_ID_GENERATOR")
	private long id;

	private String estado;

	//bi-directional many-to-one association to FinRequisito
	@ManyToOne
	@JoinColumn(name="id_requisito")
	private FinRequisito finRequisito;

	//bi-directional many-to-one association to FinTipoCredito
	@ManyToOne
	@JoinColumn(name="id_tipo_credito")
	private FinTipoCredito finTipoCredito;

	public FinTipcrediRequisito() {
	}
	

	public FinTipcrediRequisito(String estado, FinRequisito finRequisito, FinTipoCredito finTipoCredito) {
		super();
		this.estado = estado;
		this.finRequisito = finRequisito;
		this.finTipoCredito = finTipoCredito;
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

	public FinRequisito getFinRequisito() {
		return this.finRequisito;
	}

	public void setFinRequisito(FinRequisito finRequisito) {
		this.finRequisito = finRequisito;
	}

	public FinTipoCredito getFinTipoCredito() {
		return this.finTipoCredito;
	}

	public void setFinTipoCredito(FinTipoCredito finTipoCredito) {
		this.finTipoCredito = finTipoCredito;
	}

}