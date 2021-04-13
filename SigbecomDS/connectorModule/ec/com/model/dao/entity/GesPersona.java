package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ges_persona database table.
 * 
 */
@Entity
@Table(name="ges_persona")
@NamedQuery(name="GesPersona.findAll", query="SELECT g FROM GesPersona g")
public class GesPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_PERSONA_CEDULA_GENERATOR", sequenceName="SEQ_GES_PERSONA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_PERSONA_CEDULA_GENERATOR")
	private String cedula;

	//bi-directional one-to-one association to AutUsuario
	@OneToOne(mappedBy="gesPersona")
	private AutUsuario autUsuario;

	public GesPersona() {
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public AutUsuario getAutUsuario() {
		return this.autUsuario;
	}

	public void setAutUsuario(AutUsuario autUsuario) {
		this.autUsuario = autUsuario;
	}

}