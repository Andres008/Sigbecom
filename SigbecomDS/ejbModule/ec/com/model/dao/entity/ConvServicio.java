package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the conv_servicio database table.
 * 
 */
@Entity
@Table(name="conv_servicio")
@NamedQuery(name="ConvServicio.findAll", query="SELECT c FROM ConvServicio c")
public class ConvServicio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONV_SERVICIO_IDCONVSERVICIO_GENERATOR", sequenceName="SEQ_CONV_SERVICIO",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONV_SERVICIO_IDCONVSERVICIO_GENERATOR")
	@Column(name="id_conv_servicio")
	private long idConvServicio;

	private String detalle;

	private BigDecimal interes;

	@Column(name="monto_max")
	private BigDecimal montoMax;

	@Column(name="servicio_producto")
	private String servicioProducto;

	//bi-directional many-to-one association to ConvAdquirido
	@OneToMany(mappedBy="convServicio")
	private List<ConvAdquirido> convAdquiridos;

	//bi-directional many-to-one association to ConvEmpresa
	@ManyToOne
	@JoinColumn(name="id_conv_empresa")
	private ConvEmpresa convEmpresa;
	
	@Column(name="estado")
	private String estado;

	public ConvServicio() {
	}

	public long getIdConvServicio() {
		return this.idConvServicio;
	}

	public void setIdConvServicio(long idConvServicio) {
		this.idConvServicio = idConvServicio;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getInteres() {
		return this.interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMontoMax() {
		return this.montoMax;
	}

	public void setMontoMax(BigDecimal montoMax) {
		this.montoMax = montoMax;
	}

	public String getServicioProducto() {
		return this.servicioProducto;
	}

	public void setServicioProducto(String servicioProducto) {
		this.servicioProducto = servicioProducto;
	}

	public List<ConvAdquirido> getConvAdquiridos() {
		return this.convAdquiridos;
	}

	public void setConvAdquiridos(List<ConvAdquirido> convAdquiridos) {
		this.convAdquiridos = convAdquiridos;
	}

	public ConvAdquirido addConvAdquirido(ConvAdquirido convAdquirido) {
		getConvAdquiridos().add(convAdquirido);
		convAdquirido.setConvServicio(this);

		return convAdquirido;
	}

	public ConvAdquirido removeConvAdquirido(ConvAdquirido convAdquirido) {
		getConvAdquiridos().remove(convAdquirido);
		convAdquirido.setConvServicio(null);

		return convAdquirido;
	}

	public ConvEmpresa getConvEmpresa() {
		return this.convEmpresa;
	}

	public void setConvEmpresa(ConvEmpresa convEmpresa) {
		this.convEmpresa = convEmpresa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}