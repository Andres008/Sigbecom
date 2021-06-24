package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sesvas_beneficiario database table.
 * 
 */
@Entity
@Table(name="sesvas_beneficiario")
@NamedQuery(name="SesvasBeneficiario.findAll", query="SELECT s FROM SesvasBeneficiario s")
public class SesvasBeneficiario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SESVAS_BENEFICIARIO_IDBENEFICIARIO_GENERATOR", sequenceName="SEQ_SESVAS_BENEFICIARIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SESVAS_BENEFICIARIO_IDBENEFICIARIO_GENERATOR")
	@Column(name="id_beneficiario")
	private long idBeneficiario;

	//bi-directional many-to-one association to SesvasBeneficio
	@ManyToOne
	@JoinColumn(name="id_beneficios")
	private SesvasBeneficio sesvasBeneficio;

	//bi-directional many-to-one association to UsrConsanguinidad
	@ManyToOne
	@JoinColumn(name="id_consanguinidad")
	private UsrConsanguinidad usrConsanguinidad;
	
	@Column(name="incluir_socio")
	private String incluirSocio;
	
	public SesvasBeneficiario() {
	}

	public long getIdBeneficiario() {
		return this.idBeneficiario;
	}

	public void setIdBeneficiario(long idBeneficiario) {
		this.idBeneficiario = idBeneficiario;
	}

	public SesvasBeneficio getSesvasBeneficio() {
		return this.sesvasBeneficio;
	}

	public void setSesvasBeneficio(SesvasBeneficio sesvasBeneficio) {
		this.sesvasBeneficio = sesvasBeneficio;
	}

	public UsrConsanguinidad getUsrConsanguinidad() {
		return this.usrConsanguinidad;
	}

	public void setUsrConsanguinidad(UsrConsanguinidad usrConsanguinidad) {
		this.usrConsanguinidad = usrConsanguinidad;
	}

	public String getIncluirSocio() {
		return incluirSocio;
	}

	public void setIncluirSocio(String incluirSocio) {
		this.incluirSocio = incluirSocio;
	}
	
}