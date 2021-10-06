package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the aporte_descuento database table.
 * 
 */
@Entity
@Table(name="aporte_descuento")
@NamedQuery(name="AporteDescuento.findAll", query="SELECT a FROM AporteDescuento a")
public class AporteDescuento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APORTE_DESCUENTO_IDDESCUENTO_GENERATOR", sequenceName="SEQ_APORTE_DESCUENTO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APORTE_DESCUENTO_IDDESCUENTO_GENERATOR")
	@Column(name="id_descuento")
	private long idDescuento;

	private Integer anio;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_aporte")
	private Date fechaAporte;

	private Integer mes;

	private BigDecimal valor;
	
	private BigDecimal comision;
	
	@Column(name="valor_total")
	private BigDecimal valorTotal;

	//bi-directional many-to-one association to AporteCliente
	@ManyToOne
	@JoinColumn(name="id_cliente")
	private AporteCliente aporteCliente;
	
	@ManyToOne
	@JoinColumn(name="id_estado_descuento")
	private DescEstadoDescuento descEstadoDescuento;

	public AporteDescuento() {
	}

	public long getIdDescuento() {
		return this.idDescuento;
	}

	public void setIdDescuento(long idDescuento) {
		this.idDescuento = idDescuento;
	}

	public Date getFechaAporte() {
		return this.fechaAporte;
	}

	public void setFechaAporte(Date fechaAporte) {
		this.fechaAporte = fechaAporte;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public AporteCliente getAporteCliente() {
		return this.aporteCliente;
	}

	public void setAporteCliente(AporteCliente aporteCliente) {
		this.aporteCliente = aporteCliente;
	}

	public DescEstadoDescuento getDescEstadoDescuento() {
		return descEstadoDescuento;
	}

	public void setDescEstadoDescuento(DescEstadoDescuento descEstadoDescuento) {
		this.descEstadoDescuento = descEstadoDescuento;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public BigDecimal getComision() {
		return comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}