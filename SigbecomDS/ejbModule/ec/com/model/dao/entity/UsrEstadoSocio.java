package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_estado_socio database table.
 * 
 */
@Entity
@Table(name="usr_estado_socio")
@NamedQuery(name="UsrEstadoSocio.findAll", query="SELECT u FROM UsrEstadoSocio u")
public class UsrEstadoSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_ESTADO_SOCIO_IDESTADO_GENERATOR", sequenceName="SEQ_USR_ESTADO_SOCIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_ESTADO_SOCIO_IDESTADO_GENERATOR")
	@Column(name="id_estado")
	private long idEstado;

	private String estado;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrEstadoSocio")
	private List<UsrSocio> usrSocios;

	public UsrEstadoSocio() {
	}

	public long getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(long idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrEstadoSocio(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrEstadoSocio(null);

		return usrSocio;
	}

}