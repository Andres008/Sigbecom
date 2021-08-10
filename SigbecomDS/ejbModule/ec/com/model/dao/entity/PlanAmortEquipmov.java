package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the plan_amort_equipmov database table.
 * 
 */
@Entity
@Table(name="plan_amort_equipmov")
@NamedQuery(name="PlanAmortEquipmov.findAll", query="SELECT p FROM PlanAmortEquipmov p")
public class PlanAmortEquipmov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_AMORT_EQUIPMOV_IDAMORTEQUIPMOV_GENERATOR", sequenceName="SEQ_PLAN_AMORT_EQUIPMOV", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_AMORT_EQUIPMOV_IDAMORTEQUIPMOV_GENERATOR")
	@Column(name="id_amort_equipmov")
	private long idAmortEquipmov;

	private BigDecimal comision;

	private String estado;

	@Column(name="id_equipo")
	private BigDecimal idEquipo;

	@Column(name="meses_plazo")
	private BigDecimal mesesPlazo;

	@Column(name="num_cuota")
	private BigDecimal numCuota;

	private BigDecimal saldo;

	private BigDecimal total;

	@Column(name="valor_capital")
	private String valorCapital;

	@Column(name="valor_cuota")
	private BigDecimal valorCuota;

	//bi-directional many-to-one association to PlanContratoComite
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private PlanContratoComite planContratoComite;

	public PlanAmortEquipmov() {
	}

	public long getIdAmortEquipmov() {
		return this.idAmortEquipmov;
	}

	public void setIdAmortEquipmov(long idAmortEquipmov) {
		this.idAmortEquipmov = idAmortEquipmov;
	}

	public BigDecimal getComision() {
		return this.comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getIdEquipo() {
		return this.idEquipo;
	}

	public void setIdEquipo(BigDecimal idEquipo) {
		this.idEquipo = idEquipo;
	}

	public BigDecimal getMesesPlazo() {
		return this.mesesPlazo;
	}

	public void setMesesPlazo(BigDecimal mesesPlazo) {
		this.mesesPlazo = mesesPlazo;
	}

	public BigDecimal getNumCuota() {
		return this.numCuota;
	}

	public void setNumCuota(BigDecimal numCuota) {
		this.numCuota = numCuota;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getValorCapital() {
		return this.valorCapital;
	}

	public void setValorCapital(String valorCapital) {
		this.valorCapital = valorCapital;
	}

	public BigDecimal getValorCuota() {
		return this.valorCuota;
	}

	public void setValorCuota(BigDecimal valorCuota) {
		this.valorCuota = valorCuota;
	}

	public PlanContratoComite getPlanContratoComite() {
		return this.planContratoComite;
	}

	public void setPlanContratoComite(PlanContratoComite planContratoComite) {
		this.planContratoComite = planContratoComite;
	}

}