package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usr_tipo_instruccion database table.
 * 
 */
@Entity
@Table(name="usr_tipo_instruccion")
@NamedQuery(name="UsrTipoInstruccion.findAll", query="SELECT u FROM UsrTipoInstruccion u")
public class UsrTipoInstruccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_TIPO_INSTRUCCION_IDTIPOINSTRUCCION_GENERATOR", sequenceName="SEQ_USR_TIPO_INSTRUCCION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_TIPO_INSTRUCCION_IDTIPOINSTRUCCION_GENERATOR")
	@Column(name="id_tipo_instruccion")
	private long idTipoInstruccion;

	@Column(name="tipo_instruccion")
	private String tipoInstruccion;

	//bi-directional many-to-one association to UsrInstruccion
	@OneToMany(mappedBy="usrTipoInstruccion")
	private List<UsrInstruccion> usrInstruccions;

	public UsrTipoInstruccion() {
	}

	public long getIdTipoInstruccion() {
		return this.idTipoInstruccion;
	}

	public void setIdTipoInstruccion(long idTipoInstruccion) {
		this.idTipoInstruccion = idTipoInstruccion;
	}

	public String getTipoInstruccion() {
		return this.tipoInstruccion;
	}

	public void setTipoInstruccion(String tipoInstruccion) {
		this.tipoInstruccion = tipoInstruccion;
	}

	public List<UsrInstruccion> getUsrInstruccions() {
		return this.usrInstruccions;
	}

	public void setUsrInstruccions(List<UsrInstruccion> usrInstruccions) {
		this.usrInstruccions = usrInstruccions;
	}

	public UsrInstruccion addUsrInstruccion(UsrInstruccion usrInstruccion) {
		getUsrInstruccions().add(usrInstruccion);
		usrInstruccion.setUsrTipoInstruccion(this);

		return usrInstruccion;
	}

	public UsrInstruccion removeUsrInstruccion(UsrInstruccion usrInstruccion) {
		getUsrInstruccions().remove(usrInstruccion);
		usrInstruccion.setUsrTipoInstruccion(null);

		return usrInstruccion;
	}

}