package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_lugar_trabajo database table.
 * 
 */
@Entity
@Table(name="usr_lugar_trabajo")
@NamedQuery(name="UsrLugarTrabajo.findAll", query="SELECT u FROM UsrLugarTrabajo u")
public class UsrLugarTrabajo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_LUGAR_TRABAJO_IDLUGARTRABAJO_GENERATOR", sequenceName="SEQ_USR_LUGAR_TRABAJO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_LUGAR_TRABAJO_IDLUGARTRABAJO_GENERATOR")
	@Column(name="id_lugar_trabajo")
	private long idLugarTrabajo;

	private String direccion;

	@Column(name="lugar_trabajo")
	private String lugarTrabajo;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrLugarTrabajo")
	private List<UsrSocio> usrSocios;

	public UsrLugarTrabajo() {
	}

	public long getIdLugarTrabajo() {
		return this.idLugarTrabajo;
	}

	public void setIdLugarTrabajo(long idLugarTrabajo) {
		this.idLugarTrabajo = idLugarTrabajo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLugarTrabajo() {
		return this.lugarTrabajo;
	}

	public void setLugarTrabajo(String lugarTrabajo) {
		this.lugarTrabajo = lugarTrabajo;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrLugarTrabajo(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrLugarTrabajo(null);

		return usrSocio;
	}

}