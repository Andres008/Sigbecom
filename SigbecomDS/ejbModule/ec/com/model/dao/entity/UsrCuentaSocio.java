package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the usr_cuenta_socio database table.
 * 
 */
@Entity
@Table(name="usr_cuenta_socio")
@NamedQuery(name="UsrCuentaSocio.findAll", query="SELECT u FROM UsrCuentaSocio u")
public class UsrCuentaSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_CUENTA_SOCIO_IDCUENTA_GENERATOR", sequenceName="SEQ_USR_CUENTA_SOCIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_CUENTA_SOCIO_IDCUENTA_GENERATOR")
	@Column(name="id_cuenta")
	private long idCuenta;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_baja")
	private Date fechaBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_registro")
	private Date fechaRegistro;

	@Column(name="nro_cuenta")
	private String nroCuenta;

	//bi-directional many-to-one association to UsrInstitucionBancaria
	@ManyToOne
	@JoinColumn(name="id_institucion_bancaria")
	private UsrInstitucionBancaria usrInstitucionBancaria;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	//bi-directional many-to-one association to UsrTipoCuenta
	@ManyToOne
	@JoinColumn(name="id_tipo_cuenta")
	private UsrTipoCuenta usrTipoCuenta;

	public UsrCuentaSocio() {
	}

	public long getIdCuenta() {
		return this.idCuenta;
	}

	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getNroCuenta() {
		return this.nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}

	public UsrInstitucionBancaria getUsrInstitucionBancaria() {
		return this.usrInstitucionBancaria;
	}

	public void setUsrInstitucionBancaria(UsrInstitucionBancaria usrInstitucionBancaria) {
		this.usrInstitucionBancaria = usrInstitucionBancaria;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public UsrTipoCuenta getUsrTipoCuenta() {
		return this.usrTipoCuenta;
	}

	public void setUsrTipoCuenta(UsrTipoCuenta usrTipoCuenta) {
		this.usrTipoCuenta = usrTipoCuenta;
	}

}