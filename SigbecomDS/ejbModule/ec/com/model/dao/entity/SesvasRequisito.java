package ec.com.model.dao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the sesvas_requisitos database table.
 * 
 */
@Entity
@Table(name="sesvas_requisitos")
@NamedQuery(name="SesvasRequisito.findAll", query="SELECT s FROM SesvasRequisito s")
public class SesvasRequisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_REQUISITOS_IDSESVASREQUISITOS_GENERATOR", sequenceName="SEQ_SESVAS_REQUISITOS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_REQUISITOS_IDSESVASREQUISITOS_GENERATOR")
	@Column(name="id_sesvas_requisitos")
	private long idSesvasRequisitos;

	//bi-directional many-to-one association to SesvasBeneficio
	@ManyToOne
	@JoinColumn(name="id_sesvas_beneficios")
	private SesvasBeneficio sesvasBeneficio;

	//bi-directional many-to-one association to SesvasTipoRequisito
	@ManyToOne
	@JoinColumn(name="id_sesvas_tipo_requisito")
	private SesvasTipoRequisito sesvasTipoRequisito;
	
	//bi-directional many-to-one association to SesvasAdjunto
	@OneToMany(mappedBy="sesvasRequisito")
	private List<SesvasAdjunto> sesvasAdjuntos;

	public SesvasRequisito() {
	}

	public long getIdSesvasRequisitos() {
		return this.idSesvasRequisitos;
	}

	public void setIdSesvasRequisitos(long idSesvasRequisitos) {
		this.idSesvasRequisitos = idSesvasRequisitos;
	}

	public SesvasBeneficio getSesvasBeneficio() {
		return this.sesvasBeneficio;
	}

	public void setSesvasBeneficio(SesvasBeneficio sesvasBeneficio) {
		this.sesvasBeneficio = sesvasBeneficio;
	}

	public SesvasTipoRequisito getSesvasTipoRequisito() {
		return this.sesvasTipoRequisito;
	}

	public void setSesvasTipoRequisito(SesvasTipoRequisito sesvasTipoRequisito) {
		this.sesvasTipoRequisito = sesvasTipoRequisito;
	}
	public List<SesvasAdjunto> getSesvasAdjuntos() {
		return this.sesvasAdjuntos;
	}

	public void setSesvasAdjuntos(List<SesvasAdjunto> sesvasAdjuntos) {
		this.sesvasAdjuntos = sesvasAdjuntos;
	}

	public SesvasAdjunto addSesvasAdjunto(SesvasAdjunto sesvasAdjunto) {
		getSesvasAdjuntos().add(sesvasAdjunto);
		sesvasAdjunto.setSesvasRequisito(this);

		return sesvasAdjunto;
	}

	public SesvasAdjunto removeSesvasAdjunto(SesvasAdjunto sesvasAdjunto) {
		getSesvasAdjuntos().remove(sesvasAdjunto);
		sesvasAdjunto.setSesvasRequisito(null);

		return sesvasAdjunto;
	}
}