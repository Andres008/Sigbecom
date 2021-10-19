package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the des_tipo_novedad database table.
 * 
 */
@Entity
@Table(name="des_tipo_novedad")
@NamedQuery(name="DesTipoNovedad.findAll", query="SELECT d FROM DesTipoNovedad d")
public class DesTipoNovedad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DES_TIPO_NOVEDAD_ID_GENERATOR", sequenceName="SEQ_DES_TIPO_NOVEDAD")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DES_TIPO_NOVEDAD_ID_GENERATOR")
	private long id;

	private String descripcion;

	private String estado;

	//bi-directional many-to-one association to DescNovedade
	@OneToMany(mappedBy="desTipoNovedad")
	private List<DescNovedade> descNovedades;

	public DesTipoNovedad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
		descNovedade.setDesTipoNovedad(this);

		return descNovedade;
	}

	public DescNovedade removeDescNovedade(DescNovedade descNovedade) {
		getDescNovedades().remove(descNovedade);
		descNovedade.setDesTipoNovedad(null);

		return descNovedade;
	}

}