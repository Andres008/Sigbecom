package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the conv_empresa database table.
 * 
 */
@Entity
@Table(name="conv_empresa")
@NamedQuery(name="ConvEmpresa.findAll", query="SELECT c FROM ConvEmpresa c")
public class ConvEmpresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONV_EMPRESA_IDCONVEMPRESA_GENERATOR", sequenceName="SEQ_CONV_EMPRESA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONV_EMPRESA_IDCONVEMPRESA_GENERATOR")
	@Column(name="id_conv_empresa")
	private long idConvEmpresa;

	private String direccion;

	private String empresa;

	//bi-directional many-to-one association to ConvContacto
	@OneToMany(mappedBy="convEmpresa")
	private List<ConvContacto> convContactos;

	//bi-directional many-to-one association to ConvServicio
	@OneToMany(mappedBy="convEmpresa")
	private List<ConvServicio> convServicios;

	public ConvEmpresa() {
	}

	public long getIdConvEmpresa() {
		return this.idConvEmpresa;
	}

	public void setIdConvEmpresa(long idConvEmpresa) {
		this.idConvEmpresa = idConvEmpresa;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public List<ConvContacto> getConvContactos() {
		return this.convContactos;
	}

	public void setConvContactos(List<ConvContacto> convContactos) {
		this.convContactos = convContactos;
	}

	public ConvContacto addConvContacto(ConvContacto convContacto) {
		getConvContactos().add(convContacto);
		convContacto.setConvEmpresa(this);

		return convContacto;
	}

	public ConvContacto removeConvContacto(ConvContacto convContacto) {
		getConvContactos().remove(convContacto);
		convContacto.setConvEmpresa(null);

		return convContacto;
	}

	public List<ConvServicio> getConvServicios() {
		return this.convServicios;
	}

	public void setConvServicios(List<ConvServicio> convServicios) {
		this.convServicios = convServicios;
	}

	public ConvServicio addConvServicio(ConvServicio convServicio) {
		getConvServicios().add(convServicio);
		convServicio.setConvEmpresa(this);

		return convServicio;
	}

	public ConvServicio removeConvServicio(ConvServicio convServicio) {
		getConvServicios().remove(convServicio);
		convServicio.setConvEmpresa(null);

		return convServicio;
	}

}