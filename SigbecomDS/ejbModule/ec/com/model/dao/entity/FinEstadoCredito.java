package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fin_estado_credito database table.
 * 
 */
@Entity
@Table(name="fin_estado_credito")
@NamedQuery(name="FinEstadoCredito.findAll", query="SELECT f FROM FinEstadoCredito f")
public class FinEstadoCredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_ESTADO_CREDITO_IDESTADOCREDITO_GENERATOR", sequenceName="SEQ_FIN_ESTADO_CREDITO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_ESTADO_CREDITO_IDESTADOCREDITO_GENERATOR")
	@Column(name="id_estado_credito")
	private long idEstadoCredito;

	private String descripcion;

	//bi-directional many-to-one association to FinPrestamoSocio
	@OneToMany(mappedBy="finEstadoCredito")
	private List<FinPrestamoSocio> finPrestamoSocios;

	public FinEstadoCredito() {
	}
	
	

	public FinEstadoCredito(long idEstadoCredito) {
		super();
		this.idEstadoCredito = idEstadoCredito;
	}



	public long getIdEstadoCredito() {
		return this.idEstadoCredito;
	}

	public void setIdEstadoCredito(long idEstadoCredito) {
		this.idEstadoCredito = idEstadoCredito;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<FinPrestamoSocio> getFinPrestamoSocios() {
		return this.finPrestamoSocios;
	}

	public void setFinPrestamoSocios(List<FinPrestamoSocio> finPrestamoSocios) {
		this.finPrestamoSocios = finPrestamoSocios;
	}

	public FinPrestamoSocio addFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().add(finPrestamoSocio);
		finPrestamoSocio.setFinEstadoCredito(this);

		return finPrestamoSocio;
	}

	public FinPrestamoSocio removeFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().remove(finPrestamoSocio);
		finPrestamoSocio.setFinEstadoCredito(null);

		return finPrestamoSocio;
	}

}