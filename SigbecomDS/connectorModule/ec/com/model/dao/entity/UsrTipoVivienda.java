package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_tipo_vivienda database table.
 * 
 */
@Entity
@Table(name="usr_tipo_vivienda")
@NamedQuery(name="UsrTipoVivienda.findAll", query="SELECT u FROM UsrTipoVivienda u")
public class UsrTipoVivienda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_TIPO_VIVIENDA_IDTIPOVIVIENDA_GENERATOR", sequenceName="SEQ_USR_TIPO_VIVIENDA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_TIPO_VIVIENDA_IDTIPOVIVIENDA_GENERATOR")
	@Column(name="id_tipo_vivienda")
	private long idTipoVivienda;

	@Column(name="tipo_vivienda")
	private String tipoVivienda;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrTipoVivienda")
	private List<UsrSocio> usrSocios;

	public UsrTipoVivienda() {
	}

	public long getIdTipoVivienda() {
		return this.idTipoVivienda;
	}

	public void setIdTipoVivienda(long idTipoVivienda) {
		this.idTipoVivienda = idTipoVivienda;
	}

	public String getTipoVivienda() {
		return this.tipoVivienda;
	}

	public void setTipoVivienda(String tipoVivienda) {
		this.tipoVivienda = tipoVivienda;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrTipoVivienda(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrTipoVivienda(null);

		return usrSocio;
	}

}