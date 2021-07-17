package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the usr_socio_descuento_fijo database table.
 * 
 */
@Entity
@Table(name = "usr_socio_descuento_fijo")
@NamedQuery(name = "UsrSocioDescuentoFijo.findAll", query = "SELECT u FROM UsrSocioDescuentoFijo u")
public class UsrSocioDescuentoFijo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "USR_SOCIO_DESCUENTO_FIJO_ID_GENERATOR", sequenceName = "SEQ_USR_SOCIO_DESCUENTO_FIJO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USR_SOCIO_DESCUENTO_FIJO_ID_GENERATOR")
	private long id;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicial")
	private Date fechaInicial;

	private BigDecimal valor;

	// bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name = "cedula_socio")
	private UsrSocio usrSocio;

	// bi-directional many-to-one association to UsrTipoDescuento
	@ManyToOne
	@JoinColumn(name = "id_descuento")
	private UsrTipoDescuento usrTipoDescuento;

	public UsrSocioDescuentoFijo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public UsrTipoDescuento getUsrTipoDescuento() {
		return this.usrTipoDescuento;
	}

	public void setUsrTipoDescuento(UsrTipoDescuento usrTipoDescuento) {
		this.usrTipoDescuento = usrTipoDescuento;
	}

}