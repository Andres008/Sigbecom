package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the plan_equipo database table.
 * 
 */
@Entity
@Table(name="plan_equipo")
@NamedQuery(name="PlanEquipo.findAll", query="SELECT p FROM PlanEquipo p")
public class PlanEquipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_EQUIPO_IDEQUIPO_GENERATOR", sequenceName="SEQ_PLAN_EQUIPO",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_EQUIPO_IDEQUIPO_GENERATOR")
	@Column(name="id_equipo")
	private long idEquipo;

	private String detalle;

	private String equipo;

	@Column(name="precio")
	private BigDecimal precio;

	//bi-directional many-to-one association to PlanContratoComite
	@OneToMany(mappedBy="planEquipo")
	private List<PlanContratoComite> planContratoComites;
	
	//bi-directional many-to-one association to PlanContratoComite
	@OneToMany(mappedBy="planEquipo")
	private List<PlanAmortEquipmov> planAmortEquipmovs;
	
	@ManyToOne
	@JoinColumn(name="id_plan_empresa")
	private PlanOperadora planOperadora;

	public PlanEquipo() {
	}

	public long getIdEquipo() {
		return this.idEquipo;
	}

	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getEquipo() {
		return this.equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public BigDecimal getPrecio() {
		return precio;
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
		planContratoComite.setPlanEquipo(this);

		return planContratoComite;
	}

	public PlanContratoComite removePlanContratoComite(PlanContratoComite planContratoComite) {
		getPlanContratoComites().remove(planContratoComite);
		planContratoComite.setPlanEquipo(null);

		return planContratoComite;
	}
	
	public List<PlanAmortEquipmov> getPlanAmortEquipmovs() {
		return planAmortEquipmovs;
	}

	public void setPlanAmortEquipmovs(List<PlanAmortEquipmov> planAmortEquipmovs) {
		this.planAmortEquipmovs = planAmortEquipmovs;
	}
	public PlanAmortEquipmov addPlanAmortEquipmov(PlanAmortEquipmov planAmortEquipmov) {
		getPlanAmortEquipmovs().add(planAmortEquipmov);
		planAmortEquipmov.setPlanEquipo(this);

		return planAmortEquipmov;
	}

	public PlanAmortEquipmov removePlanAmortEquipmov(PlanAmortEquipmov planAmortEquipmov) {
		getPlanAmortEquipmovs().remove(planAmortEquipmov);
		planAmortEquipmov.setPlanEquipo(null);

		return planAmortEquipmov;
	}
	public PlanOperadora getPlanOperadora() {
		return planOperadora;
	}

	public void setPlanOperadora(PlanOperadora planOperadora) {
		this.planOperadora = planOperadora;
	}
	
}