package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the v_descuento_mensual_socio database table.
 * 
 */
@Entity
@Table(name="v_descuento_mensual_socio")
@NamedQuery(name="VDescuentoMensualSocio.findAll", query="SELECT v FROM VDescuentoMensualSocio v")
public class VDescuentoMensualSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="aportes_externos")
	private BigDecimal aportesExternos;

	@Column(name="cedula_socio")
	private String cedulaSocio;

	@Id
	private Long id;

	@Column(name="id_socio")
	private BigDecimal idSocio;

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

	public VDescuentoMensualSocio() {
	}

	public BigDecimal getAportesExternos() {
		return this.aportesExternos;
	}

	public void setAportesExternos(BigDecimal aportesExternos) {
		this.aportesExternos = aportesExternos;
	}

	public String getCedulaSocio() {
		return this.cedulaSocio;
	}

	public void setCedulaSocio(String cedulaSocio) {
		this.cedulaSocio = cedulaSocio;
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

}