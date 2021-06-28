package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the fin_accion_prestamo database table.
 * 
 */
@Entity
@Table(name="fin_accion_prestamo")
@NamedQuery(name="FinAccionPrestamo.findAll", query="SELECT f FROM FinAccionPrestamo f")
public class FinAccionPrestamo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_ACCION_PRESTAMO_ID_GENERATOR", sequenceName="SEQ_FIN_ACCION_PRESTAMO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_ACCION_PRESTAMO_ID_GENERATOR")
	private long id;

	private Timestamp fecha;

	private String observacion;

	//bi-directional many-to-one association to FinAccionesCredito
	@ManyToOne
	@JoinColumn(name="id_accion")
	private FinAccionesCredito finAccionesCredito;

	//bi-directional many-to-one association to FinPrestamoSocio
	@ManyToOne
	@JoinColumn(name="id_prestamo_socio")
	private FinPrestamoSocio finPrestamoSocio;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	public FinAccionPrestamo() {
	}
	
	

	public FinAccionPrestamo(Timestamp fecha, FinAccionesCredito finAccionesCredito,
			FinPrestamoSocio finPrestamoSocio, UsrSocio usrSocio, String observacion) {
		super();
		this.fecha = fecha;
		this.observacion = observacion;
		this.finAccionesCredito = finAccionesCredito;
		this.finPrestamoSocio = finPrestamoSocio;
		this.usrSocio = usrSocio;
	}



	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public FinAccionesCredito getFinAccionesCredito() {
		return this.finAccionesCredito;
	}

	public void setFinAccionesCredito(FinAccionesCredito finAccionesCredito) {
		this.finAccionesCredito = finAccionesCredito;
	}

	public FinPrestamoSocio getFinPrestamoSocio() {
		return this.finPrestamoSocio;
	}

	public void setFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		this.finPrestamoSocio = finPrestamoSocio;
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

}