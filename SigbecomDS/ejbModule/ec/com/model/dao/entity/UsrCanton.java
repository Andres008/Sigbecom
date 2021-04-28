package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_canton database table.
 * 
 */
@Entity
@Table(name="usr_canton")
@NamedQuery(name="UsrCanton.findAll", query="SELECT u FROM UsrCanton u")
public class UsrCanton implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_CANTON_IDCANTON_GENERATOR", sequenceName="SEQ_USR_CANTON")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_CANTON_IDCANTON_GENERATOR")
	@Column(name="id_canton")
	private String idCanton;

	private String canton;

	//bi-directional many-to-one association to UsrProvincia
	@ManyToOne
	@JoinColumn(name="id_provincia")
	private UsrProvincia usrProvincia;

	//bi-directional many-to-one association to UsrParroquia
	@OneToMany(mappedBy="usrCanton")
	private List<UsrParroquia> usrParroquias;

	public UsrCanton() {
	}

	public String getIdCanton() {
		return this.idCanton;
	}

	public void setIdCanton(String idCanton) {
		this.idCanton = idCanton;
	}

	public String getCanton() {
		return this.canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public UsrProvincia getUsrProvincia() {
		return this.usrProvincia;
	}

	public void setUsrProvincia(UsrProvincia usrProvincia) {
		this.usrProvincia = usrProvincia;
	}

	public List<UsrParroquia> getUsrParroquias() {
		return this.usrParroquias;
	}

	public void setUsrParroquias(List<UsrParroquia> usrParroquias) {
		this.usrParroquias = usrParroquias;
	}

	public UsrParroquia addUsrParroquia(UsrParroquia usrParroquia) {
		getUsrParroquias().add(usrParroquia);
		usrParroquia.setUsrCanton(this);

		return usrParroquia;
	}

	public UsrParroquia removeUsrParroquia(UsrParroquia usrParroquia) {
		getUsrParroquias().remove(usrParroquia);
		usrParroquia.setUsrCanton(null);

		return usrParroquia;
	}

}