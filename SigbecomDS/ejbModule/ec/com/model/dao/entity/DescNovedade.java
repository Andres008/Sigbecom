package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the desc_novedades database table.
 * 
 */
@Entity
@Table(name = "desc_novedades")
@NamedQuery(name = "DescNovedade.findAll", query = "SELECT d FROM DescNovedade d")
public class DescNovedade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DESC_NOVEDADES_IDNOVEDAD_GENERATOR", sequenceName = "SEQ_DESC_NOVEDADES", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESC_NOVEDADES_IDNOVEDAD_GENERATOR")
	@Column(name = "id_novedad")
	private long idNovedad;

	private String anio;

	private String descripcion;

	@Column(name = "fecha_registro")
	private Timestamp fechaRegistro;

	private String mes;

	private BigDecimal valor;

	// bi-directional many-to-one association to DescEstadoDescuento
	@ManyToOne
	@JoinColumn(name = "id_estado_descuento")
	private DescEstadoDescuento descEstadoDescuento;

	// bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name = "cedula_socio_ejecuta")
	private UsrSocio usrSocio1;

	// bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name = "cedula_socio_novedad")
	private UsrSocio usrSocio2;

	// bi-directional many-to-one association to DesTipoNovedad
	@ManyToOne
	@JoinColumn(name = "id_tipo_descuento")
	private DesTipoNovedad desTipoNovedad;

	public DescNovedade() {
	}

	public long getIdNovedad() {
		return this.idNovedad;
	}

	public void setIdNovedad(long idNovedad) {
		this.idNovedad = idNovedad;
	}

	public String getAnio() {
		return this.anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Timestamp fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getMes() {
		return this.mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public DescEstadoDescuento getDescEstadoDescuento() {
		return this.descEstadoDescuento;
	}

	public void setDescEstadoDescuento(DescEstadoDescuento descEstadoDescuento) {
		this.descEstadoDescuento = descEstadoDescuento;
	}

	public UsrSocio getUsrSocio1() {
		return this.usrSocio1;
	}

	public void setUsrSocio1(UsrSocio usrSocio1) {
		this.usrSocio1 = usrSocio1;
	}

	public UsrSocio getUsrSocio2() {
		return this.usrSocio2;
	}

	public void setUsrSocio2(UsrSocio usrSocio2) {
		this.usrSocio2 = usrSocio2;
	}

	public DesTipoNovedad getDesTipoNovedad() {
		return desTipoNovedad;
	}

	public void setDesTipoNovedad(DesTipoNovedad desTipoNovedad) {
		this.desTipoNovedad = desTipoNovedad;
	}

}