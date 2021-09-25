package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the aporte_cuenta database table.
 * 
 */
@Entity
@Table(name="aporte_cuenta")
@NamedQuery(name="AporteCuenta.findAll", query="SELECT a FROM AporteCuenta a")
public class AporteCuenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APORTE_CUENTA_IDCUENTA_GENERATOR", sequenceName="SEQ_APORTE_CUENTA",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APORTE_CUENTA_IDCUENTA_GENERATOR")
	@Column(name="id_cuenta")
	private long idCuenta;

	private String cuenta;

	private String detalle;

	private BigDecimal valor;

	//bi-directional many-to-one association to AporteCliente
	@OneToMany(mappedBy="aporteCuenta")
	private List<AporteCliente> aporteClientes;

	public AporteCuenta() {
	}

	public long getIdCuenta() {
		return this.idCuenta;
	}

	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getCuenta() {
		return this.cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<AporteCliente> getAporteClientes() {
		return this.aporteClientes;
	}

	public void setAporteClientes(List<AporteCliente> aporteClientes) {
		this.aporteClientes = aporteClientes;
	}

	public AporteCliente addAporteCliente(AporteCliente aporteCliente) {
		getAporteClientes().add(aporteCliente);
		aporteCliente.setAporteCuenta(this);

		return aporteCliente;
	}

	public AporteCliente removeAporteCliente(AporteCliente aporteCliente) {
		getAporteClientes().remove(aporteCliente);
		aporteCliente.setAporteCuenta(null);

		return aporteCliente;
	}

}