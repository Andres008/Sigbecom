package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_agencia database table.
 * 
 */
@Entity
@Table(name="usr_agencia")
@NamedQuery(name="UsrAgencia.findAll", query="SELECT u FROM UsrAgencia u")
public class UsrAgencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_AGENCIA_IDAGENCIA_GENERATOR", sequenceName="SEQ_USR_AGENCIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_AGENCIA_IDAGENCIA_GENERATOR")
	@Column(name="id_agencia")
	private long idAgencia;

	private String agencia;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrAgencia")
	private List<UsrSocio> usrSocios;

	public UsrAgencia() {
	}

	public long getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(long idAgencia) {
		this.idAgencia = idAgencia;
	}

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrAgencia(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrAgencia(null);

		return usrSocio;
	}

}