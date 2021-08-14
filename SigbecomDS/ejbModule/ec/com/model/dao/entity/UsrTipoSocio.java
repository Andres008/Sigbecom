package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_tipo_socio database table.
 * 
 */
@Entity
@Table(name="usr_tipo_socio")
@NamedQuery(name="UsrTipoSocio.findAll", query="SELECT u FROM UsrTipoSocio u")
public class UsrTipoSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_TIPO_SOCIO_IDTIPOSOCIO_GENERATOR", sequenceName="SEQ_USR_TIPO_SOCIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_TIPO_SOCIO_IDTIPOSOCIO_GENERATOR")
	@Column(name="id_tipo_socio")
	private long idTipoSocio;

	private String descripcion;

	private String estado;

	private String nombre;

	//bi-directional many-to-one association to AutRol
	@OneToMany(mappedBy="usrTipoSocio")
	private List<AutRol> autRols;

	//bi-directional many-to-one association to UsrSocio
	@OneToMany(mappedBy="usrTipoSocio")
	private List<UsrSocio> usrSocios;

	//bi-directional many-to-one association to UsrTipoDescuentoSocio
	@OneToMany(mappedBy="usrTipoSocio", cascade = CascadeType.ALL)
	private List<UsrTipoDescuentoSocio> usrTipoDescuentoSocios;
	
	//bi-directional many-to-one association to AutRol
	@OneToMany(mappedBy="usrTipoSocio")
	private List<PlanCostosAdm> planCostosAdms;
	
	public UsrTipoSocio() {
	}

	public long getIdTipoSocio() {
		return this.idTipoSocio;
	}

	public void setIdTipoSocio(long idTipoSocio) {
		this.idTipoSocio = idTipoSocio;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AutRol> getAutRols() {
		return this.autRols;
	}

	public void setAutRols(List<AutRol> autRols) {
		this.autRols = autRols;
	}

	public AutRol addAutRol(AutRol autRol) {
		getAutRols().add(autRol);
		autRol.setUsrTipoSocio(this);

		return autRol;
	}

	public AutRol removeAutRol(AutRol autRol) {
		getAutRols().remove(autRol);
		autRol.setUsrTipoSocio(null);

		return autRol;
	}

	public List<UsrSocio> getUsrSocios() {
		return this.usrSocios;
	}

	public void setUsrSocios(List<UsrSocio> usrSocios) {
		this.usrSocios = usrSocios;
	}

	public UsrSocio addUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().add(usrSocio);
		usrSocio.setUsrTipoSocio(this);

		return usrSocio;
	}

	public UsrSocio removeUsrSocio(UsrSocio usrSocio) {
		getUsrSocios().remove(usrSocio);
		usrSocio.setUsrTipoSocio(null);

		return usrSocio;
	}

	public List<UsrTipoDescuentoSocio> getUsrTipoDescuentoSocios() {
		return this.usrTipoDescuentoSocios;
	}

	public void setUsrTipoDescuentoSocios(List<UsrTipoDescuentoSocio> usrTipoDescuentoSocios) {
		this.usrTipoDescuentoSocios = usrTipoDescuentoSocios;
	}

	public UsrTipoDescuentoSocio addUsrTipoDescuentoSocio(UsrTipoDescuentoSocio usrTipoDescuentoSocio) {
		getUsrTipoDescuentoSocios().add(usrTipoDescuentoSocio);
		usrTipoDescuentoSocio.setUsrTipoSocio(this);

		return usrTipoDescuentoSocio;
	}

	public UsrTipoDescuentoSocio removeUsrTipoDescuentoSocio(UsrTipoDescuentoSocio usrTipoDescuentoSocio) {
		getUsrTipoDescuentoSocios().remove(usrTipoDescuentoSocio);
		usrTipoDescuentoSocio.setUsrTipoSocio(null);

		return usrTipoDescuentoSocio;
	}

	public List<PlanCostosAdm> getPlanCostosAdms() {
		return planCostosAdms;
	}

	public void setPlanCostosAdms(List<PlanCostosAdm> planCostosAdms) {
		this.planCostosAdms = planCostosAdms;
	}
	
	public PlanCostosAdm addPlanCostosAdm(PlanCostosAdm planCostosAdm) {
		getPlanCostosAdms().add(planCostosAdm);
		planCostosAdm.setUsrTipoSocio(this);

		return planCostosAdm;
	}

	public PlanCostosAdm removePlanCostosAdm(PlanCostosAdm planCostosAdm) {
		getPlanCostosAdms().remove(planCostosAdm);
		planCostosAdm.setUsrTipoSocio(null);

		return planCostosAdm;
	}
}