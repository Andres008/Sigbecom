package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the fin_resolucion_prestamo database table.
 * 
 */
@Entity
@Table(name="fin_resolucion_prestamo")
@NamedQuery(name="FinResolucionPrestamo.findAll", query="SELECT f FROM FinResolucionPrestamo f")
public class FinResolucionPrestamo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_RESOLUCION_PRESTAMO_IDRESOLUCIONPRESTAMO_GENERATOR", sequenceName="SEQ_FIN_RESOLUCION_PRESTAMO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_RESOLUCION_PRESTAMO_IDRESOLUCIONPRESTAMO_GENERATOR")
	@Column(name="id_resolucion_prestamo")
	private long idResolucionPrestamo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_aprobacion")
	private Date fechaAprobacion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_revision")
	private Date fechaRevision;

	@Column(name="id_usuario_aprobador")
	private String idUsuarioAprobador;

	@Column(name="id_usuario_revisor")
	private String idUsuarioRevisor;

	private String resolucion;

	//bi-directional many-to-one association to FinPrestamoSocio
	@OneToMany(mappedBy="finResolucionPrestamo")
	private List<FinPrestamoSocio> finPrestamoSocios;

	public FinResolucionPrestamo() {
	}

	public long getIdResolucionPrestamo() {
		return this.idResolucionPrestamo;
	}

	public void setIdResolucionPrestamo(long idResolucionPrestamo) {
		this.idResolucionPrestamo = idResolucionPrestamo;
	}

	public Date getFechaAprobacion() {
		return this.fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Date getFechaRevision() {
		return this.fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getIdUsuarioAprobador() {
		return this.idUsuarioAprobador;
	}

	public void setIdUsuarioAprobador(String idUsuarioAprobador) {
		this.idUsuarioAprobador = idUsuarioAprobador;
	}

	public String getIdUsuarioRevisor() {
		return this.idUsuarioRevisor;
	}

	public void setIdUsuarioRevisor(String idUsuarioRevisor) {
		this.idUsuarioRevisor = idUsuarioRevisor;
	}

	public String getResolucion() {
		return this.resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public List<FinPrestamoSocio> getFinPrestamoSocios() {
		return this.finPrestamoSocios;
	}

	public void setFinPrestamoSocios(List<FinPrestamoSocio> finPrestamoSocios) {
		this.finPrestamoSocios = finPrestamoSocios;
	}

	public FinPrestamoSocio addFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().add(finPrestamoSocio);
		finPrestamoSocio.setFinResolucionPrestamo(this);

		return finPrestamoSocio;
	}

	public FinPrestamoSocio removeFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().remove(finPrestamoSocio);
		finPrestamoSocio.setFinResolucionPrestamo(null);

		return finPrestamoSocio;
	}

}