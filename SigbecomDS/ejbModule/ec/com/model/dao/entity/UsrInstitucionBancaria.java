package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_institucion_bancaria database table.
 * 
 */
@Entity
@Table(name="usr_institucion_bancaria")
@NamedQuery(name="UsrInstitucionBancaria.findAll", query="SELECT u FROM UsrInstitucionBancaria u")
public class UsrInstitucionBancaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_INSTITUCION_BANCARIA_IDINSTITUCIONBANCARIA_GENERATOR", sequenceName="SEQ_USR_INSTITUCION_BANCARIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_INSTITUCION_BANCARIA_IDINSTITUCIONBANCARIA_GENERATOR")
	@Column(name="id_institucion_bancaria")
	private long idInstitucionBancaria;

	@Column(name="institucion_bancaria")
	private String institucionBancaria;

	//bi-directional many-to-one association to UsrCuentaSocio
	@OneToMany(mappedBy="usrInstitucionBancaria")
	private List<UsrCuentaSocio> usrCuentaSocios;

	public UsrInstitucionBancaria() {
	}

	public long getIdInstitucionBancaria() {
		return this.idInstitucionBancaria;
	}

	public void setIdInstitucionBancaria(long idInstitucionBancaria) {
		this.idInstitucionBancaria = idInstitucionBancaria;
	}

	public String getInstitucionBancaria() {
		return this.institucionBancaria;
	}

	public void setInstitucionBancaria(String institucionBancaria) {
		this.institucionBancaria = institucionBancaria;
	}

	public List<UsrCuentaSocio> getUsrCuentaSocios() {
		return this.usrCuentaSocios;
	}

	public void setUsrCuentaSocios(List<UsrCuentaSocio> usrCuentaSocios) {
		this.usrCuentaSocios = usrCuentaSocios;
	}

	public UsrCuentaSocio addUsrCuentaSocio(UsrCuentaSocio usrCuentaSocio) {
		getUsrCuentaSocios().add(usrCuentaSocio);
		usrCuentaSocio.setUsrInstitucionBancaria(this);

		return usrCuentaSocio;
	}

	public UsrCuentaSocio removeUsrCuentaSocio(UsrCuentaSocio usrCuentaSocio) {
		getUsrCuentaSocios().remove(usrCuentaSocio);
		usrCuentaSocio.setUsrInstitucionBancaria(null);

		return usrCuentaSocio;
	}

}