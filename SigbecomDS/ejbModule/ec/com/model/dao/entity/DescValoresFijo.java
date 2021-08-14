package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the desc_valores_fijos database table.
 * 
 */
@Entity
@Table(name="desc_valores_fijos")
@NamedQuery(name="DescValoresFijo.findAll", query="SELECT d FROM DescValoresFijo d")
public class DescValoresFijo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DESC_VALORES_FIJOS_IDVAORFIJO_GENERATOR", sequenceName="SEQ_DESC_VALORES_FIJOS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DESC_VALORES_FIJOS_IDVAORFIJO_GENERATOR")
	@Column(name="id_vaor_fijo")
	private long idVaorFijo;

	private BigDecimal anio;

	@Column(name="fecha_descuento")
	private Timestamp fechaDescuento;

	private BigDecimal mes;

	//bi-directional many-to-one association to DescEstadoDescuento
	@ManyToOne
	@JoinColumn(name="id_estado_descuento")
	private DescEstadoDescuento descEstadoDescuento;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	//bi-directional many-to-one association to UsrSocioDescuentoFijo
	@ManyToOne
	@JoinColumn(name="id_tipo_descuento")
	private UsrSocioDescuentoFijo usrSocioDescuentoFijo;

	public DescValoresFijo() {
	}

	public long getIdVaorFijo() {
		return this.idVaorFijo;
	}

	public void setIdVaorFijo(long idVaorFijo) {
		this.idVaorFijo = idVaorFijo;
	}

	public BigDecimal getAnio() {
		return this.anio;
	}

	public void setAnio(BigDecimal anio) {
		this.anio = anio;
	}

	public Timestamp getFechaDescuento() {
		return this.fechaDescuento;
	}

	public void setFechaDescuento(Timestamp fechaDescuento) {
		this.fechaDescuento = fechaDescuento;
	}

	public BigDecimal getMes() {
		return this.mes;
	}

	public void setMes(BigDecimal mes) {
		this.mes = mes;
	}

	public DescEstadoDescuento getDescEstadoDescuento() {
		return this.descEstadoDescuento;
	}

	public void setDescEstadoDescuento(DescEstadoDescuento descEstadoDescuento) {
		this.descEstadoDescuento = descEstadoDescuento;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public UsrSocioDescuentoFijo getUsrSocioDescuentoFijo() {
		return this.usrSocioDescuentoFijo;
	}

	public void setUsrSocioDescuentoFijo(UsrSocioDescuentoFijo usrSocioDescuentoFijo) {
		this.usrSocioDescuentoFijo = usrSocioDescuentoFijo;
	}

}