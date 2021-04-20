package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_provincia database table.
 * 
 */
@Entity
@Table(name="usr_provincia")
@NamedQuery(name="UsrProvincia.findAll", query="SELECT u FROM UsrProvincia u")
public class UsrProvincia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_PROVINCIA_IDPROVINCIA_GENERATOR", sequenceName="SEQ_USR_PROVINCIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_PROVINCIA_IDPROVINCIA_GENERATOR")
	@Column(name="id_provincia")
	private String idProvincia;

	private String provincia;

	//bi-directional many-to-one association to UsrCanton
	@OneToMany(mappedBy="usrProvincia")
	private List<UsrCanton> usrCantons;

	public UsrProvincia() {
	}

	public String getIdProvincia() {
		return this.idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public List<UsrCanton> getUsrCantons() {
		return this.usrCantons;
	}

	public void setUsrCantons(List<UsrCanton> usrCantons) {
		this.usrCantons = usrCantons;
	}

	public UsrCanton addUsrCanton(UsrCanton usrCanton) {
		getUsrCantons().add(usrCanton);
		usrCanton.setUsrProvincia(this);

		return usrCanton;
	}

	public UsrCanton removeUsrCanton(UsrCanton usrCanton) {
		getUsrCantons().remove(usrCanton);
		usrCanton.setUsrProvincia(null);

		return usrCanton;
	}

}