package ec.com.controlador.sesvas;

import ec.com.model.dao.entity.GesPariente;

public class DtoPariente {
	private String cedula;
	private String nombres;
	private GesPariente gesPariente;
	
	public DtoPariente() {
		super();
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public GesPariente getGesPariente() {
		return gesPariente;
	}
	public void setGesPariente(GesPariente gesPariente) {
		this.gesPariente = gesPariente;
	}
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	
	
}
