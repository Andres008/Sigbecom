package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the sesvas_solicitud database table.
 * 
 */
@Entity
@Table(name="sesvas_solicitud")
@NamedQuery(name="SesvasSolicitud.findAll", query="SELECT s FROM SesvasSolicitud s")
public class SesvasSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_SOLICITUD_IDSESVASSOLICITUD_GENERATOR", sequenceName="SEQ_SESVAS_SOLICITUD", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_SOLICITUD_IDSESVASSOLICITUD_GENERATOR")
	@Column(name="id_sesvas_solicitud")
	private long idSesvasSolicitud;

	private Timestamp fecha;

	//bi-directional many-to-one association to SesvasAdjunto
	@OneToMany(mappedBy="sesvasSolicitud")
	private List<SesvasAdjunto> sesvasAdjuntos;

	//bi-directional many-to-one association to SesvasBeneficio
	@ManyToOne
	@JoinColumn(name="id_sesvas_beneficios")
	private SesvasBeneficio sesvasBeneficio;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	public SesvasSolicitud() {
	}

	public long getIdSesvasSolicitud() {
		return this.idSesvasSolicitud;
	}

	public void setIdSesvasSolicitud(long idSesvasSolicitud) {
		this.idSesvasSolicitud = idSesvasSolicitud;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public List<SesvasAdjunto> getSesvasAdjuntos() {
		return this.sesvasAdjuntos;
	}

	public void setSesvasAdjuntos(List<SesvasAdjunto> sesvasAdjuntos) {
		this.sesvasAdjuntos = sesvasAdjuntos;
	}

	public SesvasAdjunto addSesvasAdjunto(SesvasAdjunto sesvasAdjunto) {
		getSesvasAdjuntos().add(sesvasAdjunto);
		sesvasAdjunto.setSesvasSolicitud(this);

		return sesvasAdjunto;
	}

	public SesvasAdjunto removeSesvasAdjunto(SesvasAdjunto sesvasAdjunto) {
		getSesvasAdjuntos().remove(sesvasAdjunto);
		sesvasAdjunto.setSesvasSolicitud(null);

		return sesvasAdjunto;
	}

	public SesvasBeneficio getSesvasBeneficio() {
		return this.sesvasBeneficio;
	}

	public void setSesvasBeneficio(SesvasBeneficio sesvasBeneficio) {
		this.sesvasBeneficio = sesvasBeneficio;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

}