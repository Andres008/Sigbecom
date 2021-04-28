package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_consanguinidad database table.
 * 
 */
@Entity
@Table(name="usr_consanguinidad")
@NamedQuery(name="UsrConsanguinidad.findAll", query="SELECT u FROM UsrConsanguinidad u")
public class UsrConsanguinidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_CONSANGUINIDAD_IDCONSANGUINIDAD_GENERATOR", sequenceName="SEQ_USR_CONSANGUINIDAD")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_CONSANGUINIDAD_IDCONSANGUINIDAD_GENERATOR")
	@Column(name="id_consanguinidad")
	private long idConsanguinidad;

	private String consanguinidad;

	//bi-directional many-to-one association to GesPariente
	@OneToMany(mappedBy="usrConsanguinidad")
	private List<GesPariente> gesParientes;

	public UsrConsanguinidad() {
	}

	public long getIdConsanguinidad() {
		return this.idConsanguinidad;
	}

	public void setIdConsanguinidad(long idConsanguinidad) {
		this.idConsanguinidad = idConsanguinidad;
	}

	public String getConsanguinidad() {
		return this.consanguinidad;
	}

	public void setConsanguinidad(String consanguinidad) {
		this.consanguinidad = consanguinidad;
	}

	public List<GesPariente> getGesParientes() {
		return this.gesParientes;
	}

	public void setGesParientes(List<GesPariente> gesParientes) {
		this.gesParientes = gesParientes;
	}

	public GesPariente addGesPariente(GesPariente gesPariente) {
		getGesParientes().add(gesPariente);
		gesPariente.setUsrConsanguinidad(this);

		return gesPariente;
	}

	public GesPariente removeGesPariente(GesPariente gesPariente) {
		getGesParientes().remove(gesPariente);
		gesPariente.setUsrConsanguinidad(null);

		return gesPariente;
	}

}