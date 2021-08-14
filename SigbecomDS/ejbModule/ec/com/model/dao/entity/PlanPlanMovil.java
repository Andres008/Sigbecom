package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the plan_plan_movil database table.
 * 
 */
@Entity
@Table(name="plan_plan_movil")
@NamedQuery(name="PlanPlanMovil.findAll", query="SELECT p FROM PlanPlanMovil p")
public class PlanPlanMovil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_PLAN_MOVIL_IDPLANMOVIL_GENERATOR", sequenceName="SEQ_PLAN_PLAN_MOVIL",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_PLAN_MOVIL_IDPLANMOVIL_GENERATOR")
	@Column(name="id_plan_movil")
	private long idPlanMovil;

	private String detalle;

	private String plan;

	private BigDecimal precio;

	//bi-directional many-to-one association to PlanContratoComite
	@OneToMany(mappedBy="planPlanMovil")
	private List<PlanContratoComite> planContratoComites;

	//bi-directional many-to-one association to PlanOperadora
	@ManyToOne
	@JoinColumn(name="id_plan_empresa")
	private PlanOperadora planOperadora;

	public PlanPlanMovil() {
	}

	public long getIdPlanMovil() {
		return this.idPlanMovil;
	}

	public void setIdPlanMovil(long idPlanMovil) {
		this.idPlanMovil = idPlanMovil;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public List<PlanContratoComite> getPlanContratoComites() {
		return this.planContratoComites;
	}

	public void setPlanContratoComites(List<PlanContratoComite> planContratoComites) {
		this.planContratoComites = planContratoComites;
	}

	public PlanContratoComite addPlanContratoComite(PlanContratoComite planContratoComite) {
		getPlanContratoComites().add(planContratoComite);
		planContratoComite.setPlanPlanMovil(this);

		return planContratoComite;
	}

	public PlanContratoComite removePlanContratoComite(PlanContratoComite planContratoComite) {
		getPlanContratoComites().remove(planContratoComite);
		planContratoComite.setPlanPlanMovil(null);

		return planContratoComite;
	}

	public PlanOperadora getPlanOperadora() {
		return this.planOperadora;
	}

	public void setPlanOperadora(PlanOperadora planOperadora) {
		this.planOperadora = planOperadora;
	}

}