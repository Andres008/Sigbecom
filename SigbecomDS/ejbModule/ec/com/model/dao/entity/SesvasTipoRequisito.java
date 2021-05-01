package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sesvas_tipo_requisito database table.
 * 
 */
@Entity
@Table(name="sesvas_tipo_requisito")
@NamedQuery(name="SesvasTipoRequisito.findAll", query="SELECT s FROM SesvasTipoRequisito s")
public class SesvasTipoRequisito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_TIPO_REQUISITO_IDSESVASTIPOREQUISITO_GENERATOR", sequenceName="SEQ_SESVAS_TIPO_REQUISITO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_TIPO_REQUISITO_IDSESVASTIPOREQUISITO_GENERATOR")
	@Column(name="id_sesvas_tipo_requisito")
	private long idSesvasTipoRequisito;

	@Column(name="tipo_requisito")
	private String tipoRequisito;

	//bi-directional many-to-one association to SesvasRequisito
	@OneToMany(mappedBy="sesvasTipoRequisito")
	private List<SesvasRequisito> sesvasRequisitos;

	public SesvasTipoRequisito() {
	}

	public long getIdSesvasTipoRequisito() {
		return this.idSesvasTipoRequisito;
	}

	public void setIdSesvasTipoRequisito(long idSesvasTipoRequisito) {
		this.idSesvasTipoRequisito = idSesvasTipoRequisito;
	}

	public String getTipoRequisito() {
		return this.tipoRequisito;
	}

	public void setTipoRequisito(String tipoRequisito) {
		this.tipoRequisito = tipoRequisito;
	}

	public List<SesvasRequisito> getSesvasRequisitos() {
		return this.sesvasRequisitos;
	}

	public void setSesvasRequisitos(List<SesvasRequisito> sesvasRequisitos) {
		this.sesvasRequisitos = sesvasRequisitos;
	}

	public SesvasRequisito addSesvasRequisito(SesvasRequisito sesvasRequisito) {
		getSesvasRequisitos().add(sesvasRequisito);
		sesvasRequisito.setSesvasTipoRequisito(this);

		return sesvasRequisito;
	}

	public SesvasRequisito removeSesvasRequisito(SesvasRequisito sesvasRequisito) {
		getSesvasRequisitos().remove(sesvasRequisito);
		sesvasRequisito.setSesvasTipoRequisito(null);

		return sesvasRequisito;
	}

}