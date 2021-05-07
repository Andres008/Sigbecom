package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_cargo database table.
 * 
 */
@Entity
@Table(name="usr_cargo")
@NamedQuery(name="UsrCargo.findAll", query="SELECT u FROM UsrCargo u")
public class UsrCargo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cargo")
	private long idCargo;

	private String cargo;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrCargo")
	private List<UsrSocio> usrSocios;

	public UsrCargo() {
	}

	public long getIdCargo() {
		return this.idCargo;
	}

	public void setIdCargo(long idCargo) {
		this.idCargo = idCargo;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrCargo(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrCargo(null);

		return usrSocio;
	}

}