package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the plan_contactos database table.
 * 
 */
@Entity
@Table(name="plan_contactos")
@NamedQuery(name="PlanContacto.findAll", query="SELECT p FROM PlanContacto p")
public class PlanContacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_CONTACTOS_IDPLANCONTACTOS_GENERATOR", sequenceName="SEQ_PLAN_CONTACTOS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_CONTACTOS_IDPLANCONTACTOS_GENERATOR")
	@Column(name="id_plan_contactos")
	private long idPlanContactos;

	private String apellidos;

	private String cargo;

	private String celular;

	private String email;

	private String nombres;

	//bi-directional many-to-one association to PlanOperadora
	@ManyToOne
	@JoinColumn(name="id_plan_empresa")
	private PlanOperadora planOperadora;

	public PlanContacto() {
	}

	public long getIdPlanContactos() {
		return this.idPlanContactos;
	}

	public void setIdPlanContactos(long idPlanContactos) {
		this.idPlanContactos = idPlanContactos;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public PlanOperadora getPlanOperadora() {
		return this.planOperadora;
	}

	public void setPlanOperadora(PlanOperadora planOperadora) {
		this.planOperadora = planOperadora;
	}

}