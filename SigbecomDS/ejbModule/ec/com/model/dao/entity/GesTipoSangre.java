package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ges_tipo_sangre database table.
 * 
 */
@Entity
@Table(name="ges_tipo_sangre")
@NamedQuery(name="GesTipoSangre.findAll", query="SELECT g FROM GesTipoSangre g")
public class GesTipoSangre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_TIPO_SANGRE_IDTIPOSANGRE_GENERATOR", sequenceName="SEQ_GES_TIPO_SANGRE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_TIPO_SANGRE_IDTIPOSANGRE_GENERATOR")
	@Column(name="id_tipo_sangre")
	private long idTipoSangre;

	@Column(name="tipo_sangre")
	private String tipoSangre;

	//bi-directional many-to-one association to GesPersona
	@OneToMany(mappedBy="gesTipoSangre")
	private List<GesPersona> gesPersonas;

	public GesTipoSangre() {
	}

	public long getIdTipoSangre() {
		return this.idTipoSangre;
	}

	public void setIdTipoSangre(long idTipoSangre) {
		this.idTipoSangre = idTipoSangre;
	}

	public String getTipoSangre() {
		return this.tipoSangre;
	}

	public void setTipoSangre(String tipoSangre) {
		this.tipoSangre = tipoSangre;
	}

	public List<GesPersona> getGesPersonas() {
		return this.gesPersonas;
	}

	public void setGesPersonas(List<GesPersona> gesPersonas) {
		this.gesPersonas = gesPersonas;
	}

	public GesPersona addGesPersona(GesPersona gesPersona) {
		getGesPersonas().add(gesPersona);
		gesPersona.setGesTipoSangre(this);

		return gesPersona;
	}

	public GesPersona removeGesPersona(GesPersona gesPersona) {
		getGesPersonas().remove(gesPersona);
		gesPersona.setGesTipoSangre(null);

		return gesPersona;
	}

}