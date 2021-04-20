package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ges_estado_civil database table.
 * 
 */
@Entity
@Table(name="ges_estado_civil")
@NamedQuery(name="GesEstadoCivil.findAll", query="SELECT g FROM GesEstadoCivil g")
public class GesEstadoCivil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_ESTADO_CIVIL_IDESTADOCIVIL_GENERATOR", sequenceName="SEQ_GES_ESTADO_CIVIL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_ESTADO_CIVIL_IDESTADOCIVIL_GENERATOR")
	@Column(name="id_estado_civil")
	private long idEstadoCivil;

	@Column(name="estado_civil")
	private String estadoCivil;

	//bi-directional many-to-one association to GesPersona
	@OneToMany(mappedBy="gesEstadoCivil")
	private List<GesPersona> gesPersonas;

	public GesEstadoCivil() {
	}

	public long getIdEstadoCivil() {
		return this.idEstadoCivil;
	}

	public void setIdEstadoCivil(long idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	public String getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public List<GesPersona> getGesPersonas() {
		return this.gesPersonas;
	}

	public void setGesPersonas(List<GesPersona> gesPersonas) {
		this.gesPersonas = gesPersonas;
	}

	public GesPersona addGesPersona(GesPersona gesPersona) {
		getGesPersonas().add(gesPersona);
		gesPersona.setGesEstadoCivil(this);

		return gesPersona;
	}

	public GesPersona removeGesPersona(GesPersona gesPersona) {
		getGesPersonas().remove(gesPersona);
		gesPersona.setGesEstadoCivil(null);

		return gesPersona;
	}

}