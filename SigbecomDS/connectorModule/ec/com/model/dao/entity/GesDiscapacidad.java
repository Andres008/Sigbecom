package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ges_discapacidad database table.
 * 
 */
@Entity
@Table(name="ges_discapacidad")
@NamedQuery(name="GesDiscapacidad.findAll", query="SELECT g FROM GesDiscapacidad g")
public class GesDiscapacidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_DISCAPACIDAD_IDDISCAPACIDAD_GENERATOR", sequenceName="SEQ_GES_DISCAPACIDAD")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_DISCAPACIDAD_IDDISCAPACIDAD_GENERATOR")
	@Column(name="id_discapacidad")
	private long idDiscapacidad;

	private String discapacidad;

	//bi-directional many-to-one association to GesDiscapacidadPersona
	@OneToMany(mappedBy="gesDiscapacidad")
	private List<GesDiscapacidadPersona> gesDiscapacidadPersonas;

	public GesDiscapacidad() {
	}

	public long getIdDiscapacidad() {
		return this.idDiscapacidad;
	}

	public void setIdDiscapacidad(long idDiscapacidad) {
		this.idDiscapacidad = idDiscapacidad;
	}

	public String getDiscapacidad() {
		return this.discapacidad;
	}

	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}

	public List<GesDiscapacidadPersona> getGesDiscapacidadPersonas() {
		return this.gesDiscapacidadPersonas;
	}

	public void setGesDiscapacidadPersonas(List<GesDiscapacidadPersona> gesDiscapacidadPersonas) {
		this.gesDiscapacidadPersonas = gesDiscapacidadPersonas;
	}

	public GesDiscapacidadPersona addGesDiscapacidadPersona(GesDiscapacidadPersona gesDiscapacidadPersona) {
		getGesDiscapacidadPersonas().add(gesDiscapacidadPersona);
		gesDiscapacidadPersona.setGesDiscapacidad(this);

		return gesDiscapacidadPersona;
	}

	public GesDiscapacidadPersona removeGesDiscapacidadPersona(GesDiscapacidadPersona gesDiscapacidadPersona) {
		getGesDiscapacidadPersonas().remove(gesDiscapacidadPersona);
		gesDiscapacidadPersona.setGesDiscapacidad(null);

		return gesDiscapacidadPersona;
	}

}