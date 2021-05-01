package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ges_discapacidad_persona database table.
 * 
 */
@Entity
@Table(name="ges_discapacidad_persona")
@NamedQuery(name="GesDiscapacidadPersona.findAll", query="SELECT g FROM GesDiscapacidadPersona g")
public class GesDiscapacidadPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GES_DISCAPACIDAD_PERSONA_ID_GENERATOR", sequenceName="SEQ_GES_DISCAPACIDAD_PERSONA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GES_DISCAPACIDAD_PERSONA_ID_GENERATOR")
	private long id;

	private String estado;

	@Column(name="fecha_valor")
	private Date fechaValor;

	@Column(name="nro_registro")
	private String nroRegistro;

	private BigDecimal porcentaje;

	//bi-directional many-to-one association to GesDiscapacidad
	@ManyToOne
	@JoinColumn(name="id_discapacidad")
	private GesDiscapacidad gesDiscapacidad;

	//bi-directional many-to-one association to GesPersona
	@ManyToOne
	@JoinColumn(name="cedula")
	private GesPersona gesPersona;

	public GesDiscapacidadPersona() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaValor() {
		return this.fechaValor;
	}

	public void setFechaValor(Date fechaValor) {
		this.fechaValor = fechaValor;
	}

	public String getNroRegistro() {
		return this.nroRegistro;
	}

	public void setNroRegistro(String nroRegistro) {
		this.nroRegistro = nroRegistro;
	}

	public BigDecimal getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public GesDiscapacidad getGesDiscapacidad() {
		return this.gesDiscapacidad;
	}

	public void setGesDiscapacidad(GesDiscapacidad gesDiscapacidad) {
		this.gesDiscapacidad = gesDiscapacidad;
	}

	public GesPersona getGesPersona() {
		return this.gesPersona;
	}

	public void setGesPersona(GesPersona gesPersona) {
		this.gesPersona = gesPersona;
	}

}