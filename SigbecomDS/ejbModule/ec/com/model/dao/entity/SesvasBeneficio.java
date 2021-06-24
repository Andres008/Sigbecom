package ec.com.model.dao.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sesvas_beneficios database table.
 * 
 */
@Entity
@Table(name="sesvas_beneficios")
@NamedQuery(name="SesvasBeneficio.findAll", query="SELECT s FROM SesvasBeneficio s")
public class SesvasBeneficio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_BENEFICIOS_IDSESVASBENEFICIOS_GENERATOR", sequenceName="SEQ_SESVAS_BENEFICIOS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_BENEFICIOS_IDSESVASBENEFICIOS_GENERATOR")
	@Column(name="id_sesvas_beneficios")
	private long idSesvasBeneficios;
	
	private String beneficios;

	private String detalle;
	
	private String estado;
	
	private String resolucion;
	
	@Column(name="tipo_cobertura")
	private String tipoCobertura;
	
	@Column(name="monto_maximo")
	private BigDecimal montoMaximo; 

	//bi-directional many-to-one association to SesvasRequisito
	@OneToMany(mappedBy="sesvasBeneficio")
	private List<SesvasRequisito> sesvasRequisitos;

	//bi-directional many-to-one association to SesvasSolicitud
	@OneToMany(mappedBy="sesvasBeneficio")
	private List<SesvasSolicitud> sesvasSolicituds;
	
	//bi-directional many-to-one association to SesvasBeneficiario
	@OneToMany(mappedBy="sesvasBeneficio")
	private List<SesvasBeneficiario> sesvasBeneficiarios;
	
	@Column(name="detalle_finalizacion")
	private String detalleFinalizacion; 

	public SesvasBeneficio() {
	}

	public long getIdSesvasBeneficios() {
		return this.idSesvasBeneficios;
	}

	public void setIdSesvasBeneficios(long idSesvasBeneficios) {
		this.idSesvasBeneficios = idSesvasBeneficios;
	}

	public String getBeneficios() {
		return this.beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public List<SesvasRequisito> getSesvasRequisitos() {
		return this.sesvasRequisitos;
	}

	public void setSesvasRequisitos(List<SesvasRequisito> sesvasRequisitos) {
		this.sesvasRequisitos = sesvasRequisitos;
	}

	public SesvasRequisito addSesvasRequisito(SesvasRequisito sesvasRequisito) {
		getSesvasRequisitos().add(sesvasRequisito);
		sesvasRequisito.setSesvasBeneficio(this);

		return sesvasRequisito;
	}

	public SesvasRequisito removeSesvasRequisito(SesvasRequisito sesvasRequisito) {
		getSesvasRequisitos().remove(sesvasRequisito);
		sesvasRequisito.setSesvasBeneficio(null);

		return sesvasRequisito;
	}

	public List<SesvasSolicitud> getSesvasSolicituds() {
		return this.sesvasSolicituds;
	}

	public void setSesvasSolicituds(List<SesvasSolicitud> sesvasSolicituds) {
		this.sesvasSolicituds = sesvasSolicituds;
	}

	public SesvasSolicitud addSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		getSesvasSolicituds().add(sesvasSolicitud);
		sesvasSolicitud.setSesvasBeneficio(this);

		return sesvasSolicitud;
	}

	public SesvasSolicitud removeSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		getSesvasSolicituds().remove(sesvasSolicitud);
		sesvasSolicitud.setSesvasBeneficio(null);

		return sesvasSolicitud;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public BigDecimal getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(BigDecimal montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public String getTipoCobertura() {
		return tipoCobertura;
	}

	public void setTipoCobertura(String tipoCobertura) {
		this.tipoCobertura = tipoCobertura;
	}
	
	public List<SesvasBeneficiario> getSesvasBeneficiarios() {
		return this.sesvasBeneficiarios;
	}

	public void setSesvasBeneficiarios(List<SesvasBeneficiario> sesvasBeneficiarios) {
		this.sesvasBeneficiarios = sesvasBeneficiarios;
	}

	public SesvasBeneficiario addSesvasBeneficiario(SesvasBeneficiario sesvasBeneficiario) {
		getSesvasBeneficiarios().add(sesvasBeneficiario);
		sesvasBeneficiario.setSesvasBeneficio(this);

		return sesvasBeneficiario;
	}

	public SesvasBeneficiario removeSesvasBeneficiario(SesvasBeneficiario sesvasBeneficiario) {
		getSesvasBeneficiarios().remove(sesvasBeneficiario);
		sesvasBeneficiario.setSesvasBeneficio(null);

		return sesvasBeneficiario;
	}

	public String getDetalleFinalizacion() {
		return detalleFinalizacion;
	}

	public void setDetalleFinalizacion(String detalleFinalizacion) {
		this.detalleFinalizacion = detalleFinalizacion;
	}
	
}