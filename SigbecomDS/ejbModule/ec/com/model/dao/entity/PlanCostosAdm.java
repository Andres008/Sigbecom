package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the plan_costos_adm database table.
 * 
 */
@Entity
@Table(name="plan_costos_adm")
@NamedQuery(name="PlanCostosAdm.findAll", query="SELECT p FROM PlanCostosAdm p")
public class PlanCostosAdm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PLAN_COSTOS_ADM_IDCOSTOSADM_GENERATOR", sequenceName="SEQ_PLAN_COSTOS_ADM", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLAN_COSTOS_ADM_IDCOSTOSADM_GENERATOR")
	@Column(name="id_costos_adm")
	private Integer idCostosAdm;

	private BigDecimal administracion;

	private BigDecimal cargo;

	@Column(name="costo_linea")
	private BigDecimal costoLinea;

	private String estado;

	@ManyToOne
	@JoinColumn(name="id_tipo_socio")
	private UsrTipoSocio usrTipoSocio;

	public PlanCostosAdm() {
	}

	public Integer getIdCostosAdm() {
		return this.idCostosAdm;
	}

	public void setIdCostosAdm(Integer idCostosAdm) {
		this.idCostosAdm = idCostosAdm;
	}

	public BigDecimal getAdministracion() {
		return this.administracion;
	}

	public void setAdministracion(BigDecimal administracion) {
		this.administracion = administracion;
	}

	public BigDecimal getCargo() {
		return this.cargo;
	}

	public void setCargo(BigDecimal cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getCostoLinea() {
		return this.costoLinea;
	}

	public void setCostoLinea(BigDecimal costoLinea) {
		this.costoLinea = costoLinea;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public UsrTipoSocio getUsrTipoSocio() {
		return usrTipoSocio;
	}

	public void setUsrTipoSocio(UsrTipoSocio usrTipoSocio) {
		this.usrTipoSocio = usrTipoSocio;
	}
	
}