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

	private String cargo;

	//bi-directional many-to-one association to ConvEmpresa
	@ManyToOne
	@JoinColumn(name="id_conv_empresa")
	private ConvEmpresa convEmpresa;
	
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	public ConvContacto() {
	}

	public long getIdConvContactos() {
		return this.idConvContactos;
	}

	public void setIdConvContactos(long idConvContactos) {
		this.idConvContactos = idConvContactos;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public ConvEmpresa getConvEmpresa() {
		return this.convEmpresa;
	}

	public void setConvEmpresa(ConvEmpresa convEmpresa) {
		this.convEmpresa = convEmpresa;
	}

	public UsrSocio getUsrSocio() {
		return usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}
	
}