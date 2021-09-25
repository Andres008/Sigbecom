package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the aporte_cliente database table.
 * 
 */
@Entity
@Table(name="aporte_cliente")
@NamedQuery(name="AporteCliente.findAll", query="SELECT a FROM AporteCliente a")
public class AporteCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APORTE_CLIENTE_IDCLIENTE_GENERATOR", sequenceName="SEQ_APORTE_CLIENTE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APORTE_CLIENTE_IDCLIENTE_GENERATOR")
	@Column(name="id_cliente")
	private long idCliente;

	private String detalle;

	private String estado;

	@Column(name="saldo_inicial")
	private BigDecimal saldoInicial;

	//bi-directional many-to-one association to AporteCuenta
	@ManyToOne
	@JoinColumn(name="id_cuenta")
	private AporteCuenta aporteCuenta;
	
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	//bi-directional many-to-one association to AporteDescuento
	@OneToMany(mappedBy="aporteCliente")
	private List<AporteDescuento> aporteDescuentos;

	public AporteCliente() {
	}

	public long getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}
	
	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getSaldoInicial() {
		return this.saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public AporteCuenta getAporteCuenta() {
		return this.aporteCuenta;
	}

	public void setAporteCuenta(AporteCuenta aporteCuenta) {
		this.aporteCuenta = aporteCuenta;
	}

	public List<AporteDescuento> getAporteDescuentos() {
		return this.aporteDescuentos;
	}

	public void setAporteDescuentos(List<AporteDescuento> aporteDescuentos) {
		this.aporteDescuentos = aporteDescuentos;
	}

	public AporteDescuento addAporteDescuento(AporteDescuento aporteDescuento) {
		getAporteDescuentos().add(aporteDescuento);
		aporteDescuento.setAporteCliente(this);

		return aporteDescuento;
	}

	public AporteDescuento removeAporteDescuento(AporteDescuento aporteDescuento) {
		getAporteDescuentos().remove(aporteDescuento);
		aporteDescuento.setAporteCliente(null);

		return aporteDescuento;
	}

	public UsrSocio getUsrSocio() {
		return usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}
	
}