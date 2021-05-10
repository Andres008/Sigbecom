package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sesvas_adjuntos database table.
 * 
 */
@Entity
@Table(name="sesvas_adjuntos")
@NamedQuery(name="SesvasAdjunto.findAll", query="SELECT s FROM SesvasAdjunto s")
public class SesvasAdjunto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_ADJUNTOS_IDADJUNTOS_GENERATOR", sequenceName="SEQ_SESVAS_ADJUNTOS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_ADJUNTOS_IDADJUNTOS_GENERATOR")
	@Column(name="id_adjuntos")
	private long idAdjuntos;

	@Column(name="nombre_archivo")
	private String nombreArchivo;

	//bi-directional many-to-one association to SesvasSolicitud
	@ManyToOne
	@JoinColumn(name="id_sesvas_solicitud")
	private SesvasSolicitud sesvasSolicitud;
	
	@ManyToOne
	@JoinColumn(name="id_sesvas_requisitos")
	private SesvasRequisito sesvasRequisito;
	

	public SesvasAdjunto() {
	}

	public long getIdAdjuntos() {
		return this.idAdjuntos;
	}

	public void setIdAdjuntos(long idAdjuntos) {
		this.idAdjuntos = idAdjuntos;
	}

	public String getNombreArchivo() {
		return this.nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public SesvasSolicitud getSesvasSolicitud() {
		return this.sesvasSolicitud;
	}

	public void setSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		this.sesvasSolicitud = sesvasSolicitud;
	}

	public SesvasRequisito getSesvasRequisito() {
		return sesvasRequisito;
	}

	public void setSesvasRequisito(SesvasRequisito sesvasRequisito) {
		this.sesvasRequisito = sesvasRequisito;
	}

	
}