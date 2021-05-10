package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_area database table.
 * 
 */
@Entity
@Table(name="usr_area")
@NamedQuery(name="UsrArea.findAll", query="SELECT u FROM UsrArea u")
public class UsrArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_area")
	private long idArea;

	private String area;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrArea")
	private List<UsrSocio> usrSocios;

	public UsrArea() {
	}

	public long getIdArea() {
		return this.idArea;
	}

	public void setIdArea(long idArea) {
		this.idArea = idArea;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrArea(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrArea(null);

		return usrSocio;
	}

}