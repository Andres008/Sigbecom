package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_institucion_educativa database table.
 * 
 */
@Entity
@Table(name="usr_institucion_educativa")
@NamedQuery(name="UsrInstitucionEducativa.findAll", query="SELECT u FROM UsrInstitucionEducativa u")
public class UsrInstitucionEducativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_INSTITUCION_EDUCATIVA_IDINSTITUCION_GENERATOR", sequenceName="SEQ_USR_INSTITUCION_EDUCATIVA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_INSTITUCION_EDUCATIVA_IDINSTITUCION_GENERATOR")
	@Column(name="id_institucion")
	private long idInstitucion;

	@Column(name="institucion_educativa")
	private String institucionEducativa;

	//bi-directional many-to-one association to UsrInstruccion
	@OneToMany(mappedBy="usrInstitucionEducativa")
	private List<UsrInstruccion> usrInstruccions;

	public UsrInstitucionEducativa() {
	}

	public long getIdInstitucion() {
		return this.idInstitucion;
	}

	public void setIdInstitucion(long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getInstitucionEducativa() {
		return this.institucionEducativa;
	}

	public void setInstitucionEducativa(String institucionEducativa) {
		this.institucionEducativa = institucionEducativa;
	}

	public List<UsrInstruccion> getUsrInstruccions() {
		return this.usrInstruccions;
	}

	public void setUsrInstruccions(List<UsrInstruccion> usrInstruccions) {
		this.usrInstruccions = usrInstruccions;
	}

	public UsrInstruccion addUsrInstruccion(UsrInstruccion usrInstruccion) {
		getUsrInstruccions().add(usrInstruccion);
		usrInstruccion.setUsrInstitucionEducativa(this);

		return usrInstruccion;
	}

	public UsrInstruccion removeUsrInstruccion(UsrInstruccion usrInstruccion) {
		getUsrInstruccions().remove(usrInstruccion);
		usrInstruccion.setUsrInstitucionEducativa(null);

		return usrInstruccion;
	}

}