package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the des_descuento_mensuales database table.
 * 
 */
@Entity
@Table(name="des_descuento_mensuales")
@NamedQuery(name="DesDescuentoMensuale.findAll", query="SELECT d FROM DesDescuentoMensuale d")
public class DesDescuentoMensuale implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal anio;

	@Id
	private Long id;
	
	@Column(name="aportes_externos")
	private BigDecimal aportesExternos;

	@Column(name="id_socio")
	private BigDecimal idSocio;

	private BigDecimal mes;

	@Column(name="nombre_socio")
	private String nombreSocio;

	private BigDecimal novedades;

	@Column(name="total_descuento")
	private BigDecimal totalDescuento;

	@Column(name="valor_ahorro")
	private BigDecimal valorAhorro;

	@Column(name="valor_cesantia")
	private BigDecimal valorCesantia;

	@Column(name="valor_convenio")
	private BigDecimal valorConvenio;

	@Column(name="valor_plan_movil")
	private BigDecimal valorPlanMovil;

	@Column(name="valor_prestamo")
	private BigDecimal valorPrestamo;

	@Column(name="valor_salud")
	private BigDecimal valorSalud;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	public DesDescuentoMensuale() {
	}

	public BigDecimal getAnio() {
		return this.anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getIdSocio() {
		return this.idSocio;
	}

	public void setIdSocio(BigDecimal idSocio) {
		this.idSocio = idSocio;
	}

	public BigDecimal getMes() {
		return this.mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public String getNombreSocio() {
		return this.nombreSocio;
	}

	public void setNombreSocio(String nombreSocio) {
		this.nombreSocio = nombreSocio;
	}

	public BigDecimal getNovedades() {
		return this.novedades;
	}

	public void setNovedades(BigDecimal novedades) {
		this.novedades = novedades;
	}

	public BigDecimal getTotalDescuento() {
		return this.totalDescuento;
	}

	public void setTotalDescuento(BigDecimal totalDescuento) {
		this.totalDescuento = totalDescuento;
	}

	public BigDecimal getValorAhorro() {
		return this.valorAhorro;
	}

	public void setValorAhorro(BigDecimal valorAhorro) {
		this.valorAhorro = valorAhorro;
	}

	public BigDecimal getValorCesantia() {
		return this.valorCesantia;
	}

	public void setValorCesantia(BigDecimal valorCesantia) {
		this.valorCesantia = valorCesantia;
	}

	public BigDecimal getValorConvenio() {
		return this.valorConvenio;
	}

	public void setValorConvenio(BigDecimal valorConvenio) {
		this.valorConvenio = valorConvenio;
	}

	public BigDecimal getValorPlanMovil() {
		return this.valorPlanMovil;
	}

	public void setValorPlanMovil(BigDecimal valorPlanMovil) {
		this.valorPlanMovil = valorPlanMovil;
	}

	public BigDecimal getValorPrestamo() {
		return this.valorPrestamo;
	}

	public void setValorPrestamo(BigDecimal valorPrestamo) {
		this.valorPrestamo = valorPrestamo;
	}

	public BigDecimal getValorSalud() {
		return this.valorSalud;
	}

	public void setValorSalud(BigDecimal valorSalud) {
		this.valorSalud = valorSalud;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public BigDecimal getAportesExternos() {
		return aportesExternos;
	}

	public void setAportesExternos(BigDecimal aportesExternos) {
		this.aportesExternos = aportesExternos;
	}

}