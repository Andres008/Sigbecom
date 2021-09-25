package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the conv_valor_max database table.
 * 
 */
@Entity
@Table(name="conv_valor_max")
@NamedQuery(name="ConvValorMax.findAll", query="SELECT c FROM ConvValorMax c")
public class ConvValorMax implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONV_VALOR_MAX_IDVALORMAX_GENERATOR", sequenceName="SEQ_CONV_VALOR_MAX", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONV_VALOR_MAX_IDVALORMAX_GENERATOR")
	@Column(name="id_valor_max")
	private long idValorMax;
	
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	private String estado;

	@Column(name="valor_max")
	private BigDecimal valorMax;
	
	// bi-directional many-to-one association to LogGeneral
	@OneToMany(mappedBy = "convValorMax")
	private List<ConvAdquirido> convAdquiridos;

	public ConvValorMax() {
	}

	public long getIdValorMax() {
		return this.idValorMax;
	}

	public void setIdValorMax(long idValorMax) {
		this.idValorMax = idValorMax;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getValorMax() {
		return this.valorMax;
	}

	public void setValorMax(BigDecimal valorMax) {
		this.valorMax = valorMax;
	}

	public UsrSocio getUsrSocio() {
		return usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public List<ConvAdquirido> getConvAdquiridos() {
		return convAdquiridos;
	}

	public void setConvAdquiridos(List<ConvAdquirido> convAdquiridos) {
		this.convAdquiridos = convAdquiridos;
	}
	public ConvAdquirido addConvAdquirido(ConvAdquirido convAdquirido) {
		getConvAdquiridos().add(convAdquirido);
		convAdquirido.setConvValorMax(this);
		return convAdquirido;
	}
	public ConvAdquirido removeConvAdquirido(ConvAdquirido convAdquirido) {
		getConvAdquiridos().remove(convAdquirido);
		convAdquirido.setConvValorMax(null);
		return convAdquirido;
	}
}