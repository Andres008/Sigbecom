package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the fin_tipo_credito database table.
 * 
 */
@Entity
@Table(name="fin_tipo_credito")
@NamedQuery(name="FinTipoCredito.findAll", query="SELECT f FROM FinTipoCredito f")
public class FinTipoCredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FIN_TIPO_CREDITO_IDTIPOCREDITO_GENERATOR", sequenceName="SEQ_FIN_TIPO_CREDITO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FIN_TIPO_CREDITO_IDTIPOCREDITO_GENERATOR")
	@Column(name="id_tipo_credito")
	private long idTipoCredito;

	@Column(name="dia_pago_maximo")
	private BigDecimal diaPagoMaximo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	private String nombre;
	
	private String estado;

	@Column(name="plazo_maximo")
	private BigDecimal plazoMaximo;

	@Column(name="tasa_interes")
	private BigDecimal tasaInteres;

	@Column(name="valor_maximo")
	private BigDecimal valorMaximo;
	
	@Column(name="valor_minimo")
	private BigDecimal valorMinimo;

	//bi-directional many-to-one association to FinPrestamoSocio
	@OneToMany(mappedBy="finTipoCredito")
	private List<FinPrestamoSocio> finPrestamoSocios;

	//bi-directional many-to-one association to FinTipcrediRequisito
	@OneToMany(mappedBy="finTipoCredito", cascade = CascadeType.ALL)
	private List<FinTipcrediRequisito> finTipcrediRequisitos;

	public FinTipoCredito() {
	}

	public long getIdTipoCredito() {
		return this.idTipoCredito;
	}

	public void setIdTipoCredito(long idTipoCredito) {
		this.idTipoCredito = idTipoCredito;
	}

	public BigDecimal getDiaPagoMaximo() {
		return this.diaPagoMaximo;
	}

	public void setDiaPagoMaximo(BigDecimal diaPagoMaximo) {
		this.diaPagoMaximo = diaPagoMaximo;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.toUpperCase();
	}

	public BigDecimal getPlazoMaximo() {
		return this.plazoMaximo;
	}

	public void setPlazoMaximo(BigDecimal plazoMaximo) {
		this.plazoMaximo = plazoMaximo;
	}

	public BigDecimal getTasaInteres() {
		return this.tasaInteres;
	}

	public void setTasaInteres(BigDecimal tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public BigDecimal getValorMaximo() {
		return this.valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public List<FinPrestamoSocio> getFinPrestamoSocios() {
		return this.finPrestamoSocios;
	}

	public void setFinPrestamoSocios(List<FinPrestamoSocio> finPrestamoSocios) {
		this.finPrestamoSocios = finPrestamoSocios;
	}

	public FinPrestamoSocio addFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().add(finPrestamoSocio);
		finPrestamoSocio.setFinTipoCredito(this);

		return finPrestamoSocio;
	}

	public FinPrestamoSocio removeFinPrestamoSocio(FinPrestamoSocio finPrestamoSocio) {
		getFinPrestamoSocios().remove(finPrestamoSocio);
		finPrestamoSocio.setFinTipoCredito(null);

		return finPrestamoSocio;
	}

	public List<FinTipcrediRequisito> getFinTipcrediRequisitos() {
		return this.finTipcrediRequisitos;
	}

	public void setFinTipcrediRequisitos(List<FinTipcrediRequisito> finTipcrediRequisitos) {
		this.finTipcrediRequisitos = finTipcrediRequisitos;
	}

	public FinTipcrediRequisito addFinTipcrediRequisito(FinTipcrediRequisito finTipcrediRequisito) {
		getFinTipcrediRequisitos().add(finTipcrediRequisito);
		finTipcrediRequisito.setFinTipoCredito(this);

		return finTipcrediRequisito;
	}

	public FinTipcrediRequisito removeFinTipcrediRequisito(FinTipcrediRequisito finTipcrediRequisito) {
		getFinTipcrediRequisitos().remove(finTipcrediRequisito);
		finTipcrediRequisito.setFinTipoCredito(null);

		return finTipcrediRequisito;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

}