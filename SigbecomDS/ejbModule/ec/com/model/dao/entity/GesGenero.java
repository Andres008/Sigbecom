package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ges_genero database table.
 * 
 */
@Entity
@Table(name="ges_genero")
@NamedQuery(name="GesGenero.findAll", query="SELECT g FROM GesGenero g")
public class GesGenero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_GENERO_IDGENERO_GENERATOR", sequenceName="SEQ_GES_GENERO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_GENERO_IDGENERO_GENERATOR")
	@Column(name="id_genero")
	private long idGenero;

	private String genero;

	//bi-directional many-to-one association to GesPersona
	@OneToMany(mappedBy="gesGenero")
	private List<GesPersona> gesPersonas;

	public GesGenero() {
	}

	public long getIdGenero() {
		return this.idGenero;
	}

	public void setIdGenero(long idGenero) {
		this.idGenero = idGenero;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public List<GesPersona> getGesPersonas() {
		return this.gesPersonas;
	}

	public void setGesPersonas(List<GesPersona> gesPersonas) {
		this.gesPersonas = gesPersonas;
	}

	public GesPersona addGesPersona(GesPersona gesPersona) {
		getGesPersonas().add(gesPersona);
		gesPersona.setGesGenero(this);

		return gesPersona;
	}

	public GesPersona removeGesPersona(GesPersona gesPersona) {
		getGesPersonas().remove(gesPersona);
		gesPersona.setGesGenero(null);

		return gesPersona;
	}

}