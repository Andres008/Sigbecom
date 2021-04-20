package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usr_licencia_socio database table.
 * 
 */
@Entity
@Table(name="usr_licencia_socio")
@NamedQuery(name="UsrLicenciaSocio.findAll", query="SELECT u FROM UsrLicenciaSocio u")
public class UsrLicenciaSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_LICENCIA_SOCIO_ID_GENERATOR", sequenceName="SEQ_USR_LICENCIA_SOCIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_LICENCIA_SOCIO_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="id_socio")
	private UsrSocio usrSocio;

	//bi-directional many-to-one association to UsrTipoLicencia
	@ManyToOne
	@JoinColumn(name="id_tipo_licencia")
	private UsrTipoLicencia usrTipoLicencia;

	public UsrLicenciaSocio() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public UsrTipoLicencia getUsrTipoLicencia() {
		return this.usrTipoLicencia;
	}

	public void setUsrTipoLicencia(UsrTipoLicencia usrTipoLicencia) {
		this.usrTipoLicencia = usrTipoLicencia;
	}

}