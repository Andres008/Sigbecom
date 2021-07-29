package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the plan_operadora database table.
 * 
 */
@Entity
@Table(name="plan_operadora")
@NamedQuery(name="PlanOperadora.findAll", query="SELECT p FROM PlanOperadora p")
public class PlanOperadora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_OPERADORA_IDPLANEMPRESA_GENERATOR", sequenceName="SEQ_PLAN_OPERADORA",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_OPERADORA_IDPLANEMPRESA_GENERATOR")
	@Column(name="id_plan_empresa")
	private long idPlanEmpresa;

	private String direccion;

	private String empresa;

	//bi-directional many-to-one association to PlanContacto
	@OneToMany(mappedBy="planOperadora")
	private List<PlanContacto> planContactos;

	//bi-directional many-to-one association to PlanPlanMovil
	@OneToMany(mappedBy="planOperadora")
	private List<PlanPlanMovil> planPlanMovils;
	
	//bi-directional many-to-one association to PlanPlanMovil
	@OneToMany(mappedBy="planOperadora")
	private List<PlanEquipo> planEquipos;

	public PlanOperadora() {
	}

	public long getIdPlanEmpresa() {
		return this.idPlanEmpresa;
	}

	public void setIdPlanEmpresa(long idPlanEmpresa) {
		this.idPlanEmpresa = idPlanEmpresa;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public List<PlanContacto> getPlanContactos() {
		return this.planContactos;
	}

	public void setPlanContactos(List<PlanContacto> planContactos) {
		this.planContactos = planContactos;
	}

	public PlanContacto addPlanContacto(PlanContacto planContacto) {
		getPlanContactos().add(planContacto);
		planContacto.setPlanOperadora(this);

		return planContacto;
	}

	public PlanContacto removePlanContacto(PlanContacto planContacto) {
		getPlanContactos().remove(planContacto);
		planContacto.setPlanOperadora(null);

		return planContacto;
	}

	public List<PlanPlanMovil> getPlanPlanMovils() {
		return this.planPlanMovils;
	}

	public void setPlanPlanMovils(List<PlanPlanMovil> planPlanMovils) {
		this.planPlanMovils = planPlanMovils;
	}

	public PlanPlanMovil addPlanPlanMovil(PlanPlanMovil planPlanMovil) {
		getPlanPlanMovils().add(planPlanMovil);
		planPlanMovil.setPlanOperadora(this);

		return planPlanMovil;
	}

	public PlanPlanMovil removePlanPlanMovil(PlanPlanMovil planPlanMovil) {
		getPlanPlanMovils().remove(planPlanMovil);
		planPlanMovil.setPlanOperadora(null);

		return planPlanMovil;
	}

	public List<PlanEquipo> getPlanEquipos() {
		return planEquipos;
	}

	public void setPlanEquipos(List<PlanEquipo> planEquipos) {
		this.planEquipos = planEquipos;
	}

	public PlanEquipo addPlanEquipo(PlanEquipo planEquipo) {
		getPlanEquipos().add(planEquipo);
		planEquipo.setPlanOperadora(this);

		return planEquipo;
	}

	public PlanEquipo removePlanEquipo(PlanEquipo planEquipo) {
		getPlanEquipos().remove(planEquipo);
		planEquipo.setPlanOperadora(null);

		return planEquipo;
	}
	
}