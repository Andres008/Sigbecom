package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fin_garante_credito database table.
 * 
 */
@Entity
@Table(name="fin_garante_credito")
@NamedQuery(name="FinGaranteCredito.findAll", query="SELECT f FROM FinGaranteCredito f")
public class FinGaranteCredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_GARANTE_CREDITO_IDGARANTECREDITO_GENERATOR", sequenceName="SEQ_FIN_GARANTE_CREDITO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_GARANTE_CREDITO_IDGARANTECREDITO_GENERATOR")
	@Column(name="id_garante_credito")
	private long idGaranteCredito;

	//bi-directional many-to-one association to FinPrestamoSocio
	@ManyToOne
	@JoinColumn(name="id_prestamo_socio")
	private FinPrestamoSocio finPrestamoSocio;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	public FinGaranteCredito() {
	}

	public long getIdGaranteCredito() {
		return this.idGaranteCredito;
	}

	public void setIdGaranteCredito(long idGaranteCredito) {
		this.idGaranteCredito = idGaranteCredito;
	}

	public FinPrestamoSocio getFinPrestamoSocio() {
		return this.finPrestamoSocio;
	}

	public void setFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		this.finPrestamoSocio = finPrestamoSocio;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

}