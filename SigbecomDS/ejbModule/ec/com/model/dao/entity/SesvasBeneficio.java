package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sesvas_beneficios database table.
 * 
 */
@Entity
@Table(name="sesvas_beneficios")
@NamedQuery(name="SesvasBeneficio.findAll", query="SELECT s FROM SesvasBeneficio s")
public class SesvasBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_BENEFICIOS_IDSESVASBENEFICIOS_GENERATOR", sequenceName="SEQ_SESVAS_BENEFICIOS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_BENEFICIOS_IDSESVASBENEFICIOS_GENERATOR")
	@Column(name="id_sesvas_beneficios")
	private long idSesvasBeneficios;

	private String beneficios;

	private String detalle;

	//bi-directional many-to-one association to SesvasRequisito
	@OneToMany(mappedBy="sesvasBeneficio")
	private List<SesvasRequisito> sesvasRequisitos;

	//bi-directional many-to-one association to SesvasSolicitud
	@OneToMany(mappedBy="sesvasBeneficio")
	private List<SesvasSolicitud> sesvasSolicituds;

	public SesvasBeneficio() {
	}

	public long getIdSesvasBeneficios() {
		return this.idSesvasBeneficios;
	}

	public void setIdSesvasBeneficios(long idSesvasBeneficios) {
		this.idSesvasBeneficios = idSesvasBeneficios;
	}

	public String getBeneficios() {
		return this.beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public List<SesvasRequisito> getSesvasRequisitos() {
		return this.sesvasRequisitos;
	}

	public void setSesvasRequisitos(List<SesvasRequisito> sesvasRequisitos) {
		this.sesvasRequisitos = sesvasRequisitos;
	}

	public SesvasRequisito addSesvasRequisito(SesvasRequisito sesvasRequisito) {
		getSesvasRequisitos().add(sesvasRequisito);
		sesvasRequisito.setSesvasBeneficio(this);

		return sesvasRequisito;
	}

	public SesvasRequisito removeSesvasRequisito(SesvasRequisito sesvasRequisito) {
		getSesvasRequisitos().remove(sesvasRequisito);
		sesvasRequisito.setSesvasBeneficio(null);

		return sesvasRequisito;
	}

	public List<SesvasSolicitud> getSesvasSolicituds() {
		return this.sesvasSolicituds;
	}

	public void setSesvasSolicituds(List<SesvasSolicitud> sesvasSolicituds) {
		this.sesvasSolicituds = sesvasSolicituds;
	}

	public SesvasSolicitud addSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		getSesvasSolicituds().add(sesvasSolicitud);
		sesvasSolicitud.setSesvasBeneficio(this);

		return sesvasSolicitud;
	}

	public SesvasSolicitud removeSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		getSesvasSolicituds().remove(sesvasSolicitud);
		sesvasSolicitud.setSesvasBeneficio(null);

		return sesvasSolicitud;
	}

}