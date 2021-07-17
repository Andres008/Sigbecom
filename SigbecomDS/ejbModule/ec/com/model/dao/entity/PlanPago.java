package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


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

	private BigDecimal ano;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pago")
	private Date fechaPago;

	private BigDecimal mes;

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

	public PlanPago() {
	}

	public long getIdPlanPagos() {
		return this.idPlanPagos;
	}

	public void setIdPlanPagos(long idPlanPagos) {
		this.idPlanPagos = idPlanPagos;
	}

	public BigDecimal getAno() {
		return this.ano;
	}

	public void setAno(BigDecimal ano) {
		this.ano = ano;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public BigDecimal getMes() {
		return this.mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
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

}