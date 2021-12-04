package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the plan_pagos database table.
 * 
 */
@Entity
@Table(name="plan_pagos")
@NamedQuery(name="PlanPago.findAll", query="SELECT p FROM PlanPago p")
public class PlanPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_PAGOS_IDPLANPAGOS_GENERATOR", sequenceName="SEQ_PLAN_PAGOS",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_PAGOS_IDPLANPAGOS_GENERATOR")
	@Column(name="id_plan_pagos")
	private long idPlanPagos;

	private Integer ano;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pago")
	private Date fechaPago;

	private Integer mes;

	@Column(name="valor_total")
	private BigDecimal valorTotal;

	//bi-directional many-to-one association to DescEstadoDescuento
	@ManyToOne
	@JoinColumn(name="id_estado_descuento")
	private DescEstadoDescuento descEstadoDescuento;

	//bi-directional many-to-one association to PlanContratoComite
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private PlanContratoComite planContratoComite;
	
	//bi-directional many-to-one association to PlanAmortEquipmov
	@OneToMany(mappedBy="planContratoComite")
	private List<PlanAmortEquipmov> planAmortEquipmovs;

	//bi-directional many-to-one association to PlanRegistroPago
	@OneToMany(mappedBy="planContratoComite")
	private List<PlanRegistroPago> planRegistroPagos;

	public PlanPago() {
	}

	public long getIdPlanPagos() {
		return this.idPlanPagos;
	}

	public void setIdPlanPagos(long idPlanPagos) {
		this.idPlanPagos = idPlanPagos;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public DescEstadoDescuento getDescEstadoDescuento() {
		return this.descEstadoDescuento;
	}

	public void setDescEstadoDescuento(DescEstadoDescuento descEstadoDescuento) {
		this.descEstadoDescuento = descEstadoDescuento;
	}

	public PlanContratoComite getPlanContratoComite() {
		return this.planContratoComite;
	}

	public void setPlanContratoComite(PlanContratoComite planContratoComite) {
		this.planContratoComite = planContratoComite;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public List<PlanAmortEquipmov> getPlanAmortEquipmovs() {
		return this.planAmortEquipmovs;
	}

	public void setPlanAmortEquipmovs(List<PlanAmortEquipmov> planAmortEquipmovs) {
		this.planAmortEquipmovs = planAmortEquipmovs;
	}

	public PlanAmortEquipmov addPlanAmortEquipmov(PlanAmortEquipmov planAmortEquipmov) {
		getPlanAmortEquipmovs().add(planAmortEquipmov);
		planAmortEquipmov.setPlanPago(this);

		return planAmortEquipmov;
	}

	public PlanAmortEquipmov removePlanAmortEquipmov(PlanAmortEquipmov planAmortEquipmov) {
		getPlanAmortEquipmovs().remove(planAmortEquipmov);
		planAmortEquipmov.setPlanPago(null);

		return planAmortEquipmov;
	}

	public List<PlanRegistroPago> getPlanRegistroPagos() {
		return this.planRegistroPagos;
	}

	public void setPlanRegistroPagos(List<PlanRegistroPago> planRegistroPagos) {
		this.planRegistroPagos = planRegistroPagos;
	}

	public PlanRegistroPago addPlanRegistroPago(PlanRegistroPago planRegistroPago) {
		getPlanRegistroPagos().add(planRegistroPago);
		planRegistroPago.setPlanPago(this);

		return planRegistroPago;
	}

	public PlanRegistroPago removePlanRegistroPago(PlanRegistroPago planRegistroPago) {
		getPlanRegistroPagos().remove(planRegistroPago);
		planRegistroPago.setPlanPago(null);

		return planRegistroPago;
	}
}