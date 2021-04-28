package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_tipo_licencia database table.
 * 
 */
@Entity
@Table(name="usr_tipo_licencia")
@NamedQuery(name="UsrTipoLicencia.findAll", query="SELECT u FROM UsrTipoLicencia u")
public class UsrTipoLicencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_TIPO_LICENCIA_IDTIPOLICENCIA_GENERATOR", sequenceName="SEQ_USR_TIPO_LICENCIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_TIPO_LICENCIA_IDTIPOLICENCIA_GENERATOR")
	@Column(name="id_tipo_licencia")
	private long idTipoLicencia;

	private String descripcion;

	@Column(name="tipo_licencia")
	private String tipoLicencia;

	//bi-directional many-to-one association to UsrLicenciaSocio
	@OneToMany(mappedBy="usrTipoLicencia")
	private List<UsrLicenciaSocio> usrLicenciaSocios;

	public UsrTipoLicencia() {
	}

	public long getIdTipoLicencia() {
		return this.idTipoLicencia;
	}

	public void setIdTipoLicencia(long idTipoLicencia) {
		this.idTipoLicencia = idTipoLicencia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoLicencia() {
		return this.tipoLicencia;
	}

	public void setTipoLicencia(String tipoLicencia) {
		this.tipoLicencia = tipoLicencia;
	}

	public List<UsrLicenciaSocio> getUsrLicenciaSocios() {
		return this.usrLicenciaSocios;
	}

	public void setUsrLicenciaSocios(List<UsrLicenciaSocio> usrLicenciaSocios) {
		this.usrLicenciaSocios = usrLicenciaSocios;
	}

	public UsrLicenciaSocio addUsrLicenciaSocio(UsrLicenciaSocio usrLicenciaSocio) {
		getUsrLicenciaSocios().add(usrLicenciaSocio);
		usrLicenciaSocio.setUsrTipoLicencia(this);

		return usrLicenciaSocio;
	}

	public UsrLicenciaSocio removeUsrLicenciaSocio(UsrLicenciaSocio usrLicenciaSocio) {
		getUsrLicenciaSocios().remove(usrLicenciaSocio);
		usrLicenciaSocio.setUsrTipoLicencia(null);

		return usrLicenciaSocio;
	}

}