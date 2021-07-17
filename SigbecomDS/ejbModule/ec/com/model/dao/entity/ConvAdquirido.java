package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the conv_adquirido database table.
 * 
 */
@Entity
@Table(name="conv_adquirido")
@NamedQuery(name="ConvAdquirido.findAll", query="SELECT c FROM ConvAdquirido c")
public class ConvAdquirido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONV_ADQUIRIDO_IDCONVADQUIRIDOS_GENERATOR", sequenceName="SEQ_CONV_ADQUIRIDO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONV_ADQUIRIDO_IDCONVADQUIRIDOS_GENERATOR")
	@Column(name="id_conv_adquiridos")
	private long idConvAdquiridos;

	@Column(name="cedula_socio")
	private String cedulaSocio;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_sol")
	private Date fechaSol;

	@Column(name="valor_total")
	private BigDecimal valorTotal;

	//bi-directional many-to-one association to ConvServicio
	@ManyToOne
	@JoinColumn(name="id_conv_servicio")
	private ConvServicio convServicio;

	//bi-directional many-to-one association to ConvAmortizacion
	@OneToMany(mappedBy="convAdquirido")
	private List<ConvAmortizacion> convAmortizacions;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="adjunto")
	private String adjunto;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_aprob")
	private Date fechaAprob;
	
	@Column(name="resolucion")
	private String resolucion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_revision")
	private Date fechaRevision;
	
	private BigDecimal interes;
	
	public ConvAdquirido() {
	}

	public long getIdConvAdquiridos() {
		return this.idConvAdquiridos;
	}

	public void setIdConvAdquiridos(long idConvAdquiridos) {
		this.idConvAdquiridos = idConvAdquiridos;
	}

	public String getCedulaSocio() {
		return this.cedulaSocio;
	}

	public void setCedulaSocio(String cedulaSocio) {
		this.cedulaSocio = cedulaSocio;
	}

	public BigDecimal getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public ConvServicio getConvServicio() {
		return this.convServicio;
	}

	public void setConvServicio(ConvServicio convServicio) {
		this.convServicio = convServicio;
	}

	public List<ConvAmortizacion> getConvAmortizacions() {
		return this.convAmortizacions;
	}

	public void setConvAmortizacions(List<ConvAmortizacion> convAmortizacions) {
		this.convAmortizacions = convAmortizacions;
	}

	public ConvAmortizacion addConvAmortizacion(ConvAmortizacion convAmortizacion) {
		getConvAmortizacions().add(convAmortizacion);
		convAmortizacion.setConvAdquirido(this);

		return convAmortizacion;
	}

	public ConvAmortizacion removeConvAmortizacion(ConvAmortizacion convAmortizacion) {
		getConvAmortizacions().remove(convAmortizacion);
		convAmortizacion.setConvAdquirido(null);

		return convAmortizacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public Date getFechaSol() {
		return fechaSol;
	}

	public void setFechaSol(Date fechaSol) {
		this.fechaSol = fechaSol;
	}

	public Date getFechaAprob() {
		return fechaAprob;
	}

	public void setFechaAprob(Date fechaAprob) {
		this.fechaAprob = fechaAprob;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}
	
}