package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the usr_tipo_descuento database table.
 * 
 */
@Entity
@Table(name="usr_tipo_descuento")
@NamedQuery(name="UsrTipoDescuento.findAll", query="SELECT u FROM UsrTipoDescuento u")
public class UsrTipoDescuento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_TIPO_DESCUENTO_IDDESCUENTO_GENERATOR", sequenceName="SEQ_USR_TIPO_DESCUENTO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_TIPO_DESCUENTO_IDDESCUENTO_GENERATOR")
	@Column(name="id_descuento")
	private long idDescuento;

	private String estado;

	private String nombre;

	@Column(name="valor_defecto")
	private BigDecimal valorDefecto;

	//bi-directional many-to-one association to UsrSocioDescuentoFijo
	@OneToMany(mappedBy="usrTipoDescuento")
	private List<UsrSocioDescuentoFijo> usrSocioDescuentoFijos;

	//bi-directional many-to-one association to UsrTipoDescuentoSocio
	@OneToMany(mappedBy="usrTipoDescuento")
	private List<UsrTipoDescuentoSocio> usrTipoDescuentoSocios;

	public UsrTipoDescuento() {
	}

	public long getIdDescuento() {
		return this.idDescuento;
	}

	public void setIdDescuento(long idDescuento) {
		this.idDescuento = idDescuento;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public BigDecimal getValorDefecto() {
		return this.valorDefecto;
	}

	public void setValorDefecto(BigDecimal valorDefecto) {
		this.valorDefecto = valorDefecto;
	}

	public List<UsrSocioDescuentoFijo> getUsrSocioDescuentoFijos() {
		return this.usrSocioDescuentoFijos;
	}

	public void setUsrSocioDescuentoFijos(List<UsrSocioDescuentoFijo> usrSocioDescuentoFijos) {
		this.usrSocioDescuentoFijos = usrSocioDescuentoFijos;
	}

	public UsrSocioDescuentoFijo addUsrSocioDescuentoFijo(UsrSocioDescuentoFijo usrSocioDescuentoFijo) {
		getUsrSocioDescuentoFijos().add(usrSocioDescuentoFijo);
		usrSocioDescuentoFijo.setUsrTipoDescuento(this);

		return usrSocioDescuentoFijo;
	}

	public UsrSocioDescuentoFijo removeUsrSocioDescuentoFijo(UsrSocioDescuentoFijo usrSocioDescuentoFijo) {
		getUsrSocioDescuentoFijos().remove(usrSocioDescuentoFijo);
		usrSocioDescuentoFijo.setUsrTipoDescuento(null);

		return usrSocioDescuentoFijo;
	}

	public List<UsrTipoDescuentoSocio> getUsrTipoDescuentoSocios() {
		return this.usrTipoDescuentoSocios;
	}

	public void setUsrTipoDescuentoSocios(List<UsrTipoDescuentoSocio> usrTipoDescuentoSocios) {
		this.usrTipoDescuentoSocios = usrTipoDescuentoSocios;
	}

	public UsrTipoDescuentoSocio addUsrTipoDescuentoSocio(UsrTipoDescuentoSocio usrTipoDescuentoSocio) {
		getUsrTipoDescuentoSocios().add(usrTipoDescuentoSocio);
		usrTipoDescuentoSocio.setUsrTipoDescuento(this);

		return usrTipoDescuentoSocio;
	}

	public UsrTipoDescuentoSocio removeUsrTipoDescuentoSocio(UsrTipoDescuentoSocio usrTipoDescuentoSocio) {
		getUsrTipoDescuentoSocios().remove(usrTipoDescuentoSocio);
		usrTipoDescuentoSocio.setUsrTipoDescuento(null);

		return usrTipoDescuentoSocio;
	}

}