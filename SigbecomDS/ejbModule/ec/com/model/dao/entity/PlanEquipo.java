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

	@Column(name="cuota_ref")
	private BigDecimal cuotaRef;

	private String detalle;

	private String equipo;

	private BigDecimal interes;

	@Column(name="precio_ref")
	private BigDecimal precioRef;

	//bi-directional many-to-one association to PlanContratoComite
	@OneToMany(mappedBy="planEquipo")
	private List<PlanContratoComite> planContratoComites;

	public PlanEquipo() {
	}

	public long getIdEquipo() {
		return this.idEquipo;
	}

	public void setIdEquipo(long idEquipo) {
		this.idEquipo = idEquipo;
	}

	public BigDecimal getCuotaRef() {
		return this.cuotaRef;
	}

	public void setCuotaRef(BigDecimal cuotaRef) {
		this.cuotaRef = cuotaRef;
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

	public BigDecimal getInteres() {
		return this.interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getPrecioRef() {
		return this.precioRef;
	}

	public void setPrecioRef(BigDecimal precioRef) {
		this.precioRef = precioRef;
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

}