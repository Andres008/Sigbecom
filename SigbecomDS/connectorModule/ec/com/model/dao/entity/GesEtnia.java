package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ges_etnia database table.
 * 
 */
@Entity
@Table(name="ges_etnia")
@NamedQuery(name="GesEtnia.findAll", query="SELECT g FROM GesEtnia g")
public class GesEtnia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_ETNIA_IDETNIA_GENERATOR", sequenceName="SEQ_GES_ETNIA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_ETNIA_IDETNIA_GENERATOR")
	@Column(name="id_etnia")
	private long idEtnia;

	private String etnia;

	//bi-directional many-to-one association to GesPersona
	@OneToMany(mappedBy="gesEtnia")
	private List<GesPersona> gesPersonas;

	public GesEtnia() {
	}

	public long getIdEtnia() {
		return this.idEtnia;
	}

	public void setIdEtnia(long idEtnia) {
		this.idEtnia = idEtnia;
	}

	public String getEtnia() {
		return this.etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	public List<GesPersona> getGesPersonas() {
		return this.gesPersonas;
	}

	public void setGesPersonas(List<GesPersona> gesPersonas) {
		this.gesPersonas = gesPersonas;
	}

	public GesPersona addGesPersona(GesPersona gesPersona) {
		getGesPersonas().add(gesPersona);
		gesPersona.setGesEtnia(this);

		return gesPersona;
	}

	public GesPersona removeGesPersona(GesPersona gesPersona) {
		getGesPersonas().remove(gesPersona);
		gesPersona.setGesEtnia(null);

		return gesPersona;
	}

}