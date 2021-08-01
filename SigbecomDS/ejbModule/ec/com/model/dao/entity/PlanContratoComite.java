package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the plan_contrato_comite database table.
 * 
 */
@Entity
@Table(name="plan_contrato_comite")
@NamedQuery(name="PlanContratoComite.findAll", query="SELECT p FROM PlanContratoComite p")
public class PlanContratoComite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_CONTRATO_COMITE_IDCONTRATO_GENERATOR", sequenceName="SEQ_PLAN_CONTRATO_COMITE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_CONTRATO_COMITE_IDCONTRATO_GENERATOR")
	@Column(name="id_contrato")
	private long idContrato;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_contrato")
	private Date fechaContrato;
	
	@Column(name="interes_plan_movil")
	private BigDecimal interesPlanMovil;

	@Column(name="estado_plan_movil")
	private String estadoPlanMovil;

	@Column(name="linea_telefono")
	private String lineaTelefono;

	@Column(name="valor_plan_movil")
	private BigDecimal valorPlanMovil;
	
	@Column(name="valor_equipo")
	private BigDecimal valorEquipo;

	@Column(name="interes_equipo")
	private BigDecimal interesEquipo;
	
	@Column(name="estado_equipo")
	private String estadoEquipo;
	//bi-directional many-to-one association to PlanEquipo
	@ManyToOne
	@JoinColumn(name="id_equipo")
	private PlanEquipo planEquipo;

	//bi-directional many-to-one association to PlanPlanMovil
	@ManyToOne
	@JoinColumn(name="id_plan_movil")
	private PlanPlanMovil planPlanMovil;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	//bi-directional many-to-one association to PlanPago
	@OneToMany(mappedBy="planContratoComite")
	private List<PlanPago> planPagos;

	public PlanContratoComite() {
	}

	public long getIdContrato() {
		return this.idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public Date getFechaContrato() {
		return this.fechaContrato;
	}

	public void setFechaContrato(Date fechaContrato) {
		this.fechaContrato = fechaContrato;
	}

	public String getLineaTelefono() {
		return this.lineaTelefono;
	}

	public void setLineaTelefono(String lineaTelefono) {
		this.lineaTelefono = lineaTelefono;
	}
	public PlanEquipo getPlanEquipo() {
		return this.planEquipo;
	}

	public void setPlanEquipo(PlanEquipo planEquipo) {
		this.planEquipo = planEquipo;
	}

	public PlanPlanMovil getPlanPlanMovil() {
		return this.planPlanMovil;
	}

	public void setPlanPlanMovil(PlanPlanMovil planPlanMovil) {
		this.planPlanMovil = planPlanMovil;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public List<PlanPago> getPlanPagos() {
		return this.planPagos;
	}

	public void setPlanPagos(List<PlanPago> planPagos) {
		this.planPagos = planPagos;
	}

	public PlanPago addPlanPago(PlanPago planPago) {
		getPlanPagos().add(planPago);
		planPago.setPlanContratoComite(this);

		return planPago;
	}

	public PlanPago removePlanPago(PlanPago planPago) {
		getPlanPagos().remove(planPago);
		planPago.setPlanContratoComite(null);

		return planPago;
	}

	public String getEstadoPlanMovil() {
		return estadoPlanMovil;
	}

	public void setEstadoPlanMovil(String estadoPlanMovil) {
		this.estadoPlanMovil = estadoPlanMovil;
	}

	public BigDecimal getValorPlanMovil() {
		return valorPlanMovil;
	}

	public void setValorPlanMovil(BigDecimal valorPlanMovil) {
		this.valorPlanMovil = valorPlanMovil;
	}

	public BigDecimal getValorEquipo() {
		return valorEquipo;
	}

	public void setValorEquipo(BigDecimal valorEquipo) {
		this.valorEquipo = valorEquipo;
	}

	public BigDecimal getInteresEquipo() {
		return interesEquipo;
	}

	public void setInteresEquipo(BigDecimal interesEquipo) {
		this.interesEquipo = interesEquipo;
	}

	public String getEstadoEquipo() {
		return estadoEquipo;
	}

	public void setEstadoEquipo(String estadoEquipo) {
		this.estadoEquipo = estadoEquipo;
	}

	public BigDecimal getInteresPlanMovil() {
		return interesPlanMovil;
	}

	public void setInteresPlanMovil(BigDecimal interesPlanMovil) {
		this.interesPlanMovil = interesPlanMovil;
	}
	
}