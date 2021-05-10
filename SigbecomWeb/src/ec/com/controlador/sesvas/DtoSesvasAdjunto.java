package ec.com.controlador.sesvas;

import org.primefaces.model.file.UploadedFile;

import ec.com.model.dao.entity.SesvasRequisito;
import ec.com.model.dao.entity.SesvasSolicitud;

public class DtoSesvasAdjunto {
	
	private String nombreArchivo;
	private SesvasSolicitud sesvasSolicitud;
	private SesvasRequisito sesvasRequisito;
	private byte[] file;
	
	public DtoSesvasAdjunto() {
		super();
	}
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public SesvasSolicitud getSesvasSolicitud() {
		return sesvasSolicitud;
	}

	public void setSesvasSolicitud(SesvasSolicitud sesvasSolicitud) {
		this.sesvasSolicitud = sesvasSolicitud;
	}

	public SesvasRequisito getSesvasRequisito() {
		return sesvasRequisito;
	}

	public void setSesvasRequisito(SesvasRequisito sesvasRequisito) {
		this.sesvasRequisito = sesvasRequisito;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
	
}
