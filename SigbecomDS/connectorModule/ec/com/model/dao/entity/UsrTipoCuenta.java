package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_tipo_cuenta database table.
 * 
 */
@Entity
@Table(name="usr_tipo_cuenta")
@NamedQuery(name="UsrTipoCuenta.findAll", query="SELECT u FROM UsrTipoCuenta u")
public class UsrTipoCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_TIPO_CUENTA_IDTIPOCUENTA_GENERATOR", sequenceName="SEQ_USR_TIPO_CUENTA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_TIPO_CUENTA_IDTIPOCUENTA_GENERATOR")
	@Column(name="id_tipo_cuenta")
	private long idTipoCuenta;

	@Column(name="tipo_cuenta")
	private String tipoCuenta;

	//bi-directional many-to-one association to UsrCuentaSocio
	@OneToMany(mappedBy="usrTipoCuenta")
	private List<UsrCuentaSocio> usrCuentaSocios;

	public UsrTipoCuenta() {
	}

	public long getIdTipoCuenta() {
		return this.idTipoCuenta;
	}

	public void setIdTipoCuenta(long idTipoCuenta) {
		this.idTipoCuenta = idTipoCuenta;
	}

	public String getTipoCuenta() {
		return this.tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public List<UsrCuentaSocio> getUsrCuentaSocios() {
		return this.usrCuentaSocios;
	}

	public void setUsrCuentaSocios(List<UsrCuentaSocio> usrCuentaSocios) {
		this.usrCuentaSocios = usrCuentaSocios;
	}

	public UsrCuentaSocio addUsrCuentaSocio(UsrCuentaSocio usrCuentaSocio) {
		getUsrCuentaSocios().add(usrCuentaSocio);
		usrCuentaSocio.setUsrTipoCuenta(this);

		return usrCuentaSocio;
	}

	public UsrCuentaSocio removeUsrCuentaSocio(UsrCuentaSocio usrCuentaSocio) {
		getUsrCuentaSocios().remove(usrCuentaSocio);
		usrCuentaSocio.setUsrTipoCuenta(null);

		return usrCuentaSocio;
	}

}