package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usr_instruccion database table.
 * 
 */
@Entity
@Table(name="usr_instruccion")
@NamedQuery(name="UsrInstruccion.findAll", query="SELECT u FROM UsrInstruccion u")
public class UsrInstruccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USR_INSTRUCCION_IDINSTRUCCION_GENERATOR", sequenceName="SEQ_USR_INSTRUCCION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USR_INSTRUCCION_IDINSTRUCCION_GENERATOR")
	@Column(name="id_instruccion")
	private long idInstruccion;

	private String titulo;

	@Column(name="id_institucion")
	private String id_institucion;

	//bi-directional many-to-one association to UsrSocio
	@ManyToOne
	@JoinColumn(name="cedula_socio")
	private UsrSocio usrSocio;

	//bi-directional many-to-one association to UsrTipoInstruccion
	@ManyToOne
	@JoinColumn(name="id_tipo_instruccion")
	private UsrTipoInstruccion usrTipoInstruccion;

	public UsrInstruccion() {
	}

	public long getIdInstruccion() {
		return this.idInstruccion;
	}

	public void setIdInstruccion(long idInstruccion) {
		this.idInstruccion = idInstruccion;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo.toUpperCase();
	}

	public UsrSocio getUsrSocio() {
		return this.usrSocio;
	}

	public void setUsrSocio(UsrSocio usrSocio) {
		this.usrSocio = usrSocio;
	}

	public UsrTipoInstruccion getUsrTipoInstruccion() {
		return this.usrTipoInstruccion;
	}

	public void setUsrTipoInstruccion(UsrTipoInstruccion usrTipoInstruccion) {
		this.usrTipoInstruccion = usrTipoInstruccion;
	}

	public String getId_institucion() {
		return id_institucion;
	}

	public void setId_institucion(String id_institucion) {
		this.id_institucion = id_institucion.toUpperCase();
	}

}