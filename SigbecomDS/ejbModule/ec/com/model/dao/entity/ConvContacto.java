package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the conv_contactos database table.
 * 
 */
@Entity
@Table(name="conv_contactos")
@NamedQuery(name="ConvContacto.findAll", query="SELECT c FROM ConvContacto c")
public class ConvContacto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONV_CONTACTOS_IDCONVCONTACTOS_GENERATOR", sequenceName="SEQ_CONV_CONTACTOS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONV_CONTACTOS_IDCONVCONTACTOS_GENERATOR")
	@Column(name="id_conv_contactos")
	private long idConvContactos;

	private String apellidos;

	private String cargo;

	private String celular;

	private String email;

	private String nombres;

	private String telefono;

	//bi-directional many-to-one association to ConvEmpresa
	@ManyToOne
	@JoinColumn(name="id_conv_empresa")
	private ConvEmpresa convEmpresa;

	public ConvContacto() {
	}

	public long getIdConvContactos() {
		return this.idConvContactos;
	}

	public void setIdConvContactos(long idConvContactos) {
		this.idConvContactos = idConvContactos;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public ConvEmpresa getConvEmpresa() {
		return this.convEmpresa;
	}

	public void setConvEmpresa(ConvEmpresa convEmpresa) {
		this.convEmpresa = convEmpresa;
	}

}