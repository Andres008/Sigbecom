package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the aut_perfiles database table.
 * 
 */
@Entity
@Table(name="aut_perfiles")
@NamedQuery(name="AutPerfile.findAll", query="SELECT a FROM AutPerfile a")
public class AutPerfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUT_PERFILES_ID_GENERATOR", sequenceName="SEQ_AUT_PERFILES", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUT_PERFILES_ID_GENERATOR")
	private long id;

	private String estado;

	private String icon;

	private String nombre;

	private BigDecimal orden;

	private String url;

	//bi-directional many-to-one association to AutMenu
	@ManyToOne
	@JoinColumn(name="id_menu")
	private AutMenu autMenu;

	//bi-directional many-to-one association to AutRolPerfil
	@OneToMany(mappedBy="autPerfile")
	private List<AutRolPerfil> autRolPerfils;

	public AutPerfile() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getOrden() {
		return this.orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AutMenu getAutMenu() {
		return this.autMenu;
	}

	public void setAutMenu(AutMenu autMenu) {
		this.autMenu = autMenu;
	}

	public List<AutRolPerfil> getAutRolPerfils() {
		return this.autRolPerfils;
	}

	public void setAutRolPerfils(List<AutRolPerfil> autRolPerfils) {
		this.autRolPerfils = autRolPerfils;
	}

	public AutRolPerfil addAutRolPerfil(AutRolPerfil autRolPerfil) {
		getAutRolPerfils().add(autRolPerfil);
		autRolPerfil.setAutPerfile(this);

		return autRolPerfil;
	}

	public AutRolPerfil removeAutRolPerfil(AutRolPerfil autRolPerfil) {
		getAutRolPerfils().remove(autRolPerfil);
		autRolPerfil.setAutPerfile(null);

		return autRolPerfil;
	}

}