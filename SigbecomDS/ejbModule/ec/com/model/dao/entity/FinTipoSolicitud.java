package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fin_tipo_solicitud database table.
 * 
 */
@Entity
@Table(name="fin_tipo_solicitud")
@NamedQuery(name="FinTipoSolicitud.findAll", query="SELECT f FROM FinTipoSolicitud f")
public class FinTipoSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_TIPO_SOLICITUD_IDTIPOSOLICITUD_GENERATOR", sequenceName="SEQ_FIN_TIPO_SOLICITUD")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_TIPO_SOLICITUD_IDTIPOSOLICITUD_GENERATOR")
	@Column(name="id_tipo_solicitud")
	private long idTipoSolicitud;

	@Column(name="tipo_solicitud")
	private String tipoSolicitud;

	//bi-directional many-to-one association to FinPrestamoSocio
	@OneToMany(mappedBy="finTipoSolicitud")
	private List<FinPrestamoSocio> finPrestamoSocios;

	public FinTipoSolicitud() {
	}
	
	

	public FinTipoSolicitud(long idTipoSolicitud) {
		super();
		this.idTipoSolicitud = idTipoSolicitud;
	}



	public long getIdTipoSolicitud() {
		return this.idTipoSolicitud;
	}

	public void setIdTipoSolicitud(long idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}

	public String getTipoSolicitud() {
		return this.tipoSolicitud;
	}

	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}

	public List<FinPrestamoSocio> getFinPrestamoSocios() {
		return this.finPrestamoSocios;
	}

	public void setFinPrestamoSocios(List<FinPrestamoSocio> finPrestamoSocios) {
		this.finPrestamoSocios = finPrestamoSocios;
	}

	public FinPrestamoSocio addFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().add(finPrestamoSocio);
		finPrestamoSocio.setFinTipoSolicitud(this);

		return finPrestamoSocio;
	}

	public FinPrestamoSocio removeFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().remove(finPrestamoSocio);
		finPrestamoSocio.setFinTipoSolicitud(null);

		return finPrestamoSocio;
	}

}