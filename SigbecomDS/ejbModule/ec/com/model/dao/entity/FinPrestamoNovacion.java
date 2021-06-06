package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the fin_prestamo_novacion database table.
 * 
 */
@Entity
@Table(name="fin_prestamo_novacion")
@NamedQuery(name="FinPrestamoNovacion.findAll", query="SELECT f FROM FinPrestamoNovacion f")
public class FinPrestamoNovacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_PRESTAMO_NOVACION_ID_GENERATOR", sequenceName="SEQ_FIN_PRESTAMO_NOVACION", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_PRESTAMO_NOVACION_ID_GENERATOR")
	private long id;

	//bi-directional many-to-one association to FinPrestamoSocio
	@ManyToOne
	@JoinColumn(name="id_prestamo_socio_novado")
	private FinPrestamoSocio finPrestamoSocio1;

	//bi-directional many-to-one association to FinPrestamoSocio
	@ManyToOne
	@JoinColumn(name="id_prestamo_socio")
	private FinPrestamoSocio finPrestamoSocio2;

	public FinPrestamoNovacion() {
	}

	public FinPrestamoNovacion(FinPrestamoSocio objFinPrestamoSocio, FinPrestamoSocio prestamoSocio) {
		this.finPrestamoSocio2=objFinPrestamoSocio;
		this.finPrestamoSocio1=prestamoSocio;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FinPrestamoSocio getFinPrestamoSocio1() {
		return this.finPrestamoSocio1;
	}

	public void setFinPrestamoSocio1(FinPrestamoSocio finPrestamoSocio1) {
		this.finPrestamoSocio1 = finPrestamoSocio1;
	}

	public FinPrestamoSocio getFinPrestamoSocio2() {
		return this.finPrestamoSocio2;
	}

	public void setFinPrestamoSocio2(FinPrestamoSocio finPrestamoSocio2) {
		this.finPrestamoSocio2 = finPrestamoSocio2;
	}

}