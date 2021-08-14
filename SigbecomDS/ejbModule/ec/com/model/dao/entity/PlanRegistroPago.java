package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the plan_registro_pagos database table.
 * 
 */
@Entity
@Table(name="plan_registro_pagos")
@NamedQuery(name="PlanRegistroPago.findAll", query="SELECT p FROM PlanRegistroPago p")
public class PlanRegistroPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_REGISTRO_PAGOS_IDREGISTROPAGOS_GENERATOR", sequenceName="SEQ_PLAN_REGISTRO_PAGOS", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_REGISTRO_PAGOS_IDREGISTROPAGOS_GENERATOR")
	@Column(name="id_registro_pagos")
	private long idRegistroPagos;

	private Integer anio;

	@Column(name="costo_adm")
	private BigDecimal costoAdm;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_ingreso")
	private Date fechaIngreso;

	@Column(name="linea_telefono")
	private String lineaTelefono;

	private Integer mes;

	@Column(name="nombre_ref")
	private String nombreRef;

	private BigDecimal total;

	@Column(name="valor_plan")
	private BigDecimal valorPlan;

	//bi-directional many-to-one association to PlanContratoComite
	@ManyToOne
	@JoinColumn(name="id_contrato")
	private PlanContratoComite planContratoComite;

	public PlanRegistroPago() {
	}

	public long getIdRegistroPagos() {
		return this.idRegistroPagos;
	}

	public void setIdRegistroPagos(long idRegistroPagos) {
		this.idRegistroPagos = idRegistroPagos;
	}
	
	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public BigDecimal getCostoAdm() {
		return this.costoAdm;
	}

	public void setCostoAdm(BigDecimal costoAdm) {
		this.costoAdm = costoAdm;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaIngreso() {
		return this.fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getLineaTelefono() {
		return this.lineaTelefono;
	}

	public void setLineaTelefono(String lineaTelefono) {
		this.lineaTelefono = lineaTelefono;
	}
	
	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getNombreRef() {
		return this.nombreRef;
	}

	public void setNombreRef(String nombreRef) {
		this.nombreRef = nombreRef;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getValorPlan() {
		return this.valorPlan;
	}

	public void setValorPlan(BigDecimal valorPlan) {
		this.valorPlan = valorPlan;
	}

	public PlanContratoComite getPlanContratoComite() {
		return this.planContratoComite;
	}

	public void setPlanContratoComite(PlanContratoComite planContratoComite) {
		this.planContratoComite = planContratoComite;
	}

}