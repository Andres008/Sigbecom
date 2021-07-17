package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the desc_estado_descuento database table.
 * 
 */
@Entity
@Table(name="desc_estado_descuento")
@NamedQuery(name="DescEstadoDescuento.findAll", query="SELECT d FROM DescEstadoDescuento d")
public class DescEstadoDescuento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DESC_ESTADO_DESCUENTO_IDESTADODESCUENTO_GENERATOR", sequenceName="SEQ_DESC_ESTADO_DESCUENTO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DESC_ESTADO_DESCUENTO_IDESTADODESCUENTO_GENERATOR")
	@Column(name="id_estado_descuento")
	private long idEstadoDescuento;

	private String estado;

	//bi-directional many-to-one association to DescNovedade
	@OneToMany(mappedBy="descEstadoDescuento")
	private List<DescNovedade> descNovedades;

	//bi-directional many-to-one association to DescValoresFijo
	@OneToMany(mappedBy="descEstadoDescuento")
	private List<DescValoresFijo> descValoresFijos;

	//bi-directional many-to-one association to FinCuotasDescontada
	@OneToMany(mappedBy="descEstadoDescuento")
	private List<FinCuotasDescontada> finCuotasDescontadas;

	public DescEstadoDescuento() {
	}
	
	

	public DescEstadoDescuento(long idEstadoDescuento) {
		super();
		this.idEstadoDescuento = idEstadoDescuento;
	}



	public long getIdEstadoDescuento() {
		return this.idEstadoDescuento;
	}

	public void setIdEstadoDescuento(long idEstadoDescuento) {
		this.idEstadoDescuento = idEstadoDescuento;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<DescNovedade> getDescNovedades() {
		return this.descNovedades;
	}

	public void setDescNovedades(List<DescNovedade> descNovedades) {
		this.descNovedades = descNovedades;
	}

	public DescNovedade addDescNovedade(DescNovedade descNovedade) {
		getDescNovedades().add(descNovedade);
		descNovedade.setDescEstadoDescuento(this);

		return descNovedade;
	}

	public DescNovedade removeDescNovedade(DescNovedade descNovedade) {
		getDescNovedades().remove(descNovedade);
		descNovedade.setDescEstadoDescuento(null);

		return descNovedade;
	}

	public List<DescValoresFijo> getDescValoresFijos() {
		return this.descValoresFijos;
	}

	public void setDescValoresFijos(List<DescValoresFijo> descValoresFijos) {
		this.descValoresFijos = descValoresFijos;
	}

	public DescValoresFijo addDescValoresFijo(DescValoresFijo descValoresFijo) {
		getDescValoresFijos().add(descValoresFijo);
		descValoresFijo.setDescEstadoDescuento(this);

		return descValoresFijo;
	}

	public DescValoresFijo removeDescValoresFijo(DescValoresFijo descValoresFijo) {
		getDescValoresFijos().remove(descValoresFijo);
		descValoresFijo.setDescEstadoDescuento(null);

		return descValoresFijo;
	}

	public List<FinCuotasDescontada> getFinCuotasDescontadas() {
		return this.finCuotasDescontadas;
	}

	public void setFinCuotasDescontadas(List<FinCuotasDescontada> finCuotasDescontadas) {
		this.finCuotasDescontadas = finCuotasDescontadas;
	}

	public FinCuotasDescontada addFinCuotasDescontada(FinCuotasDescontada finCuotasDescontada) {
		getFinCuotasDescontadas().add(finCuotasDescontada);
		finCuotasDescontada.setDescEstadoDescuento(this);

		return finCuotasDescontada;
	}

	public FinCuotasDescontada removeFinCuotasDescontada(FinCuotasDescontada finCuotasDescontada) {
		getFinCuotasDescontadas().remove(finCuotasDescontada);
		finCuotasDescontada.setDescEstadoDescuento(null);

		return finCuotasDescontada;
	}

}