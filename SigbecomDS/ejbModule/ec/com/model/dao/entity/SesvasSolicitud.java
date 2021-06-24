package ec.com.model.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the sesvas_solicitud database table.
 * 
 */
@Entity
@Table(name="sesvas_solicitud")
@NamedQuery(name="SesvasSolicitud.findAll", query="SELECT s FROM SesvasSolicitud s")
public class SesvasSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_SOLICITUD_IDSESVASSOLICITUD_GENERATOR", sequenceName="SEQ_SESVAS_SOLICITUD", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_SOLICITUD_IDSESVASSOLICITUD_GENERATOR")
	@Column(name="id_sesvas_solicitud")
	private long idSesvasSolicitud;

	private Timestamp fecha;

	//bi-directional many-to-one association to SesvasAdjunto
	@OneToMany(mappedBy="sesvasSolicitud")
	private List<SesvasAdjunto> sesvasAdjuntos;

	//bi-directional many-to-one association to SesvasBeneficio
	@ManyToOne
	@JoinColumn(name="id_sesvas_beneficios")
	private SesvasBeneficio sesvasBeneficio;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;
	
	//bi-directional many-to-one association to SesvasBeneficio
	@ManyToOne
	@JoinColumn(name="id_pariente")
	private GesPariente gesPariente;
	
	@Column(name="estado")
	private String estado;
	
	@Column(name="valor")
	private BigDecimal valor;
	
	@Column(name="fecha_asignacion")
	private Timestamp fechaAsignacion;
	
	@Column(name="resolucion")
	private String resolucion;
	
	@Column(name="periodo")
	private String periodo;
	
	@Column(name="fecha_revision")
	private Timestamp fechaRevision;
	
	@Column(name="observacion")
	private String observacion;
	
	public SesvasSolicitud() {
	}

	public long getIdSesvasSolicitud() {
		return this.idSesvasSolicitud;
	}

	public void setIdSesvasSolicitud(long idSesvasSolicitud) {
		this.idSesvasSolicitud = idSesvasSolicitud;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public List<SesvasAdjunto> getSesvasAdjuntos() {
		return this.sesvasAdjuntos;
	}

	public void setSesvasAdjuntos(List<SesvasAdjunto> sesvasAdjuntos) {
		this.sesvasAdjuntos = sesvasAdjuntos;
	}

	public SesvasAdjunto addSesvasAdjunto(SesvasAdjunto sesvasAdjunto) {
		getSesvasAdjuntos().add(sesvasAdjunto);
		sesvasAdjunto.setSesvasSolicitud(this);

		return sesvasAdjunto;
	}

	public SesvasAdjunto removeSesvasAdjunto(SesvasAdjunto sesvasAdjunto) {
		getSesvasAdjuntos().remove(sesvasAdjunto);
		sesvasAdjunto.setSesvasSolicitud(null);

		return sesvasAdjunto;
	}

	public SesvasBeneficio getSesvasBeneficio() {
		return this.sesvasBeneficio;
	}

	public void setSesvasBeneficio(SesvasBeneficio sesvasBeneficio) {
		this.sesvasBeneficio = sesvasBeneficio;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Timestamp getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Timestamp fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public GesPariente getGesPariente() {
		return gesPariente;
	}

	public void setGesPariente(GesPariente gesPariente) {
		this.gesPariente = gesPariente;
	}

	public Timestamp getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Timestamp fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
}