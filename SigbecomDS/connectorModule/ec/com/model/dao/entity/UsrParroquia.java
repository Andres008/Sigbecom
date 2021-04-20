package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_parroquia database table.
 * 
 */
@Entity
@Table(name="usr_parroquia")
@NamedQuery(name="UsrParroquia.findAll", query="SELECT u FROM UsrParroquia u")
public class UsrParroquia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_PARROQUIA_IDPARROQUIA_GENERATOR", sequenceName="SEQ_USR_PARROQUIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_PARROQUIA_IDPARROQUIA_GENERATOR")
	@Column(name="id_parroquia")
	private String idParroquia;

	private String parroquia;

	//bi-directional many-to-one association to UsrCanton
	@ManyToOne
	@JoinColumn(name="id_canton")
	private UsrCanton usrCanton;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrParroquia")
	private List<UsrSocio> usrSocios;

	public UsrParroquia() {
	}

	public String getIdParroquia() {
		return this.idParroquia;
	}

	public void setIdParroquia(String idParroquia) {
		this.idParroquia = idParroquia;
	}

	public String getParroquia() {
		return this.parroquia;
	}

	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}

	public UsrCanton getUsrCanton() {
		return this.usrCanton;
	}

	public void setUsrCanton(UsrCanton usrCanton) {
		this.usrCanton = usrCanton;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrParroquia(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrParroquia(null);

		return usrSocio;
	}

}