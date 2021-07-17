package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usr_tipo_descuento_socio database table.
 * 
 */
@Entity
@Table(name="usr_tipo_descuento_socio")
@NamedQuery(name="UsrTipoDescuentoSocio.findAll", query="SELECT u FROM UsrTipoDescuentoSocio u")
public class UsrTipoDescuentoSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_TIPO_DESCUENTO_SOCIO_ID_GENERATOR", sequenceName="SEQ_USR_TIPO_DESCUENTO_SOCIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_TIPO_DESCUENTO_SOCIO_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to UsrTipoDescuento
	@ManyToOne
	@JoinColumn(name="id_descuento")
	private UsrTipoDescuento usrTipoDescuento;

	//bi-directional many-to-one association to UsrTipoSocio
	@ManyToOne
	@JoinColumn(name="id_tipo_socio")
	private UsrTipoSocio usrTipoSocio;

	public UsrTipoDescuentoSocio() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UsrTipoDescuento getUsrTipoDescuento() {
		return this.usrTipoDescuento;
	}

	public void setUsrTipoDescuento(UsrTipoDescuento usrTipoDescuento) {
		this.usrTipoDescuento = usrTipoDescuento;
	}

	public UsrTipoSocio getUsrTipoSocio() {
		return this.usrTipoSocio;
	}

	public void setUsrTipoSocio(UsrTipoSocio usrTipoSocio) {
		this.usrTipoSocio = usrTipoSocio;
	}

}