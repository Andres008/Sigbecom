package ec.com.controlador.convenios;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.convenios.ManagerConvenios;
import ec.com.model.dao.entity.ConvAdquirido;
import ec.com.model.dao.entity.ConvAmortizacion;
import ec.com.model.dao.entity.ConvContacto;
import ec.com.model.dao.entity.ConvServicio;
import ec.com.model.dao.entity.ConvValorMax;
import ec.com.model.dao.entity.DescEstadoDescuento;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

@Named("frmRegistroConvenio")
@SessionScoped
public class FrmRegistroConvenio implements Serializable{
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerConvenios managerConvenios;
	
	private List<ConvServicio> lstConvServicio;
	
	private Long idServicio;
	private ConvServicio convServicio;
	private ConvAdquirido convAdquirido;
	private List<ConvAdquirido> lstConvAdquirido;
	private List<ConvValorMax> lstConvValorMax;
	private Long idValorMax;
	private ConvValorMax convValorMax;
	private String valorMaximo;
	private BigDecimal valorProformado;
	private int plazo;
	private boolean pnlRender;
	private Integer mesCon;
	private Integer anioCon;
	
	private List<ConvAdquirido> lstConvAdquiridoTrm;
	private List<ConvAmortizacion> lstConvAmortizacion;
	
	@Inject
	private BeanLogin beanLogin;
	
	@PostConstruct
	public void init() {
		lstConvServicio = new ArrayList<ConvServicio>();
		lstConvAdquirido = new ArrayList<ConvAdquirido>();
		lstConvValorMax = new ArrayList<ConvValorMax>();
		//lstConvAmortizacion = new ArrayList<ConvAmortizacion>();
		idServicio = new Long(0);
		convServicio = new ConvServicio();
		convAdquirido = new ConvAdquirido();
		idValorMax = new Long(0);
		convValorMax = new ConvValorMax();
		valorMaximo = "N/A";
		valorProformado = new BigDecimal(0);
		lstConvAdquiridoTrm = new ArrayList<ConvAdquirido>();
		plazo = 0;
		pnlRender = false;
		//anioCon=ModelUtil.getAnio(new Date());
		//mesCon =ModelUtil.getMes(new Date());
		
		cargarListaConvServicio();
		cargarListaConvAdquiridos();
		cargarListaRegistroValoresMax();
		//cargarListaConvAmortizacion();
	}
	public String cancelar() {
		System.out.println("cancelado");
		init();
		PrimeFaces prime=PrimeFaces.current();
		prime.ajax().update("form1");
		return "/modulos/convenios/registroConvenios.xhtml?faces-redirect=true";
	}
	public void cargarListaConvAmortizacion() {
		try {
			System.out.println("MES: "+mesCon);
			System.out.println("ANIO: "+anioCon);
			lstConvAmortizacion = new ArrayList<ConvAmortizacion>();
			lstConvAmortizacion = managerConvenios.findAllConvAmortizacionByMesAnio(anioCon, mesCon);
			System.out.println("SIZE: "+lstConvAmortizacion.size());
			
			PrimeFaces prime=PrimeFaces.current();
			//prime.ajax().update("form1");
			prime.ajax().update("form3");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void cargarListaConvServicio() {
		try {
			lstConvServicio = managerConvenios.findAllConvServicioActivos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaConvAdquiridos() {
		try {
			String cedulaSocio = beanLogin.getCredencial().getObjUsrSocio().getCedulaSocio();
			List<ConvContacto> lstConvContactos = managerConvenios.findAllContactosByCedula(cedulaSocio);
			List<ConvAdquirido> lstConvAdquiridosTmp = new ArrayList<ConvAdquirido>();
			for (ConvContacto convContacto : lstConvContactos) {
				lstConvAdquiridosTmp = new ArrayList<ConvAdquirido>();
				lstConvAdquiridosTmp = managerConvenios.findConvAdquiridoByIdEmpresa(convContacto.getConvEmpresa().getIdConvEmpresa());
				lstConvAdquirido.addAll(lstConvAdquiridosTmp);
			}
			
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarListaRegistroValoresMax() {
		try {
			lstConvValorMax = managerConvenios.findAllConvValorMaxEstadoActivo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	public void cargarConvServicio() {
		if(idServicio!=0) {
			try {
				//pnlRender=true;
				convServicio=managerConvenios.findByIdConvServicio(idServicio);
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1:datEmp");
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se cargo Servicio correctamente");
				e.printStackTrace();
			}
		}
		else {
			JSFUtil.crearMensajeERROR("No ha seleccionado ningun servicio");
		}
	}
	public void cargarConvValorMax() {
		if(idValorMax!=0) {
			convValorMax = lstConvValorMax.stream().filter(p->p.getIdValorMax()==idValorMax).findAny().orElse(null);
			System.out.println("VALAMAX"+convValorMax.getValorMax());
			if(convServicio.getMontoMax()!=null && convServicio.getMontoMax().equalsIgnoreCase("SI")) {
				valorMaximo = convValorMax.getValorMax()+"";
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form1");
				prime.ajax().update("form2");
			}
			else {
				JSFUtil.crearMensajeWARN("No ha seleccionado un servicio");
			}
		}
	}
	public void cargarListaConvAdquiridosTramitados() {
		
		try {
			lstConvAdquiridoTrm = managerConvenios.findConvAdquiridoRevisados();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se cargo el listado correctamente");
			e.printStackTrace();
		}
	}
	
	public void aplicarConvenio() {
		//if(archivo!=null && convServicio!= null && convAdquirido.getAdjunto() != null && idValorMax>0 && valorProformado.compareTo(new BigDecimal(0))==1) {
		if(convServicio!= null && idValorMax>0 && valorProformado.compareTo(new BigDecimal(0))==1 && plazo>0) {	
			if(convServicio.getMontoMax().equalsIgnoreCase("NO")) {
				//if(valorProformado.compareTo(convValorMax.getValorMax())==-1 || valorProformado.compareTo(convValorMax.getValorMax())==0) {
				calcularRegitrarConvenio();
				try {
					DescEstadoDescuento descEstadoDescuento = managerConvenios.findWhereEstadoDescEstadoDescuento("VIGENTE");
					//managerConvenios.actualizarObjeto(convAdquirido);
					//lstConvAmortizacion = new ArrayList<ConvAmortizacion>();
					//generarTablaAmortizacion(valor, interesTotal,deudaTotal, meses,descEstadoDescuento);
					generarTablaAmortizacion(convAdquirido.getInteres(),convAdquirido.getValorTotal(), convAdquirido.getInteresTotal(),convAdquirido.getDeudaTotal(), plazo,descEstadoDescuento);
					cargarListaConvAdquiridos();
					cargarListaConvAdquiridosTramitados();
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("form1");
					prime.ajax().update("form2");
					prime.executeScript("PF('dlgTramite').hide();");
					JSFUtil.crearMensajeINFO("Se genero la tabla correctamente");
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR("No se actualizo las cuotas correctamente");
					e.printStackTrace();
				}	
			}
			else if(convServicio.getMontoMax().equalsIgnoreCase("SI") && (valorProformado.compareTo(convValorMax.getValorMax())==-1 || valorProformado.compareTo(convValorMax.getValorMax())==0)){
				calcularRegitrarConvenio();
				try {
					DescEstadoDescuento descEstadoDescuento = managerConvenios.findWhereEstadoDescEstadoDescuento("VIGENTE");
					//managerConvenios.actualizarObjeto(convAdquirido);
					//lstConvAmortizacion = new ArrayList<ConvAmortizacion>();
					//generarTablaAmortizacion(valor, interesTotal,deudaTotal, meses,descEstadoDescuento);
					generarTablaAmortizacion(convAdquirido.getInteres(),convAdquirido.getValorTotal(), convAdquirido.getInteresTotal(),convAdquirido.getDeudaTotal(), plazo,descEstadoDescuento);
					cargarListaConvAdquiridos();
					cargarListaConvAdquiridosTramitados();
					PrimeFaces prime=PrimeFaces.current();
					prime.ajax().update("form1");
					prime.ajax().update("form2");
					prime.executeScript("PF('dlgTramite').hide();");
					JSFUtil.crearMensajeINFO("Se genero la tabla correctamente");
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR("No se actualizo las cuotas correctamente");
					e.printStackTrace();
				}
			}
			else {
				JSFUtil.crearMensajeWARN("Valor Proformado no debe ser mayor que el valor maximo permitido");
				PrimeFaces prime=PrimeFaces.current();
				prime.ajax().update("form2");
			}
		}
		else if(valorProformado.compareTo(new BigDecimal(0))==0) {
			JSFUtil.crearMensajeWARN("Ingrese el valor proformado distinto de 0.00");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form2");
		}
		else if(plazo==0){
			JSFUtil.crearMensajeWARN("Ingrese plazo mayor que 0");
			PrimeFaces prime=PrimeFaces.current();
			prime.ajax().update("form2");
		}
	}
	
	private void calcularRegitrarConvenio() {
		try {
			
			convAdquirido.setResolucion("Registro administrativo Historicos");
			convAdquirido.setAdjunto("N/A");
			convAdquirido.setHojaRuta("N/A");
			convAdquirido.setPlazo(plazo);
			convAdquirido.setObservacion("Registro inicial de deudas pendientes");
			convAdquirido.setConvServicio(convServicio);
			convAdquirido.setConvValorMax(convValorMax);
			convAdquirido.setEstado("APROBADO");
			convAdquirido.setFechaSol(new Date());
			convAdquirido.setFechaRevision(new Date());
			convAdquirido.setFechaAprob(new Date());
			convAdquirido.setAplicaValorMax("NO");
			convAdquirido.setValorMax(convValorMax.getValorMax());
			convAdquirido.setInteres(convServicio.getInteres());//interes de Comision
			convAdquirido.setDeudaTotal(valorProformado);//valor total proformado incluye la comisión
			BigDecimal interes = (convServicio.getInteres().divide(new BigDecimal(100))).add(new BigDecimal(1));
			//System.out.println("interes:"+ interes);
			BigDecimal valorTotal = valorProformado.divide(interes,2, RoundingMode.HALF_EVEN);
			convAdquirido.setValorTotal(valorTotal);
			BigDecimal interesTotal = convServicio.getInteres().multiply(convAdquirido.getValorTotal()).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN);
			convAdquirido.setInteresTotal(interesTotal);
			managerConvenios.insertarConvAdquirido(convAdquirido);
			
			
//			JSFUtil.crearMensajeINFO("Solicitud Realizada Correctamente");
//			init();
//			PrimeFaces prime=PrimeFaces.current();
//			prime.ajax().update("form2");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR("No se ha registrado correctamente");
			e.printStackTrace();
		}
	}
	
private void generarTablaAmortizacion(BigDecimal interes, BigDecimal valorTotal,BigDecimal interesTotal, BigDecimal deudaTotal, int plazo, DescEstadoDescuento descEstadoDescuento) {
		
		ConvAmortizacion convAmortizacion;
		int anio = ModelUtil.getAnio(new Date());
		int mes = ModelUtil.getMes(new Date());
		Date fecha = new Date();
		
		//System.out.println("Fecha Suma: "+ModelUtil.getSumarMeses(fecha, 1));
//		if(mes==12) {
//			anio++;
//			mes=1;
//		}
//		else {
//			mes++;
//		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		Calendar calendar = Calendar.getInstance(); 
		//System.out.println("Fecha Actual:" + sdf.format(calendar.getTime()));
		calendar.set(Calendar.DAY_OF_MONTH,1);
		//System.out.println("Primer día del mes actual:" + sdf.format(calendar.getTime()));
		
		
		if(plazo>1) {
			
			BigDecimal interesTmp = (interes.divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN)).add(new BigDecimal(1));
			
			BigDecimal cuotaTotalCapital = new BigDecimal(0);
			BigDecimal cuotaTotalInteres = new BigDecimal(0);
			
			BigDecimal cuotaCapital = valorTotal.divide(new BigDecimal(plazo),2,RoundingMode.HALF_EVEN);
			System.out.println("Cuota Capital:"+cuotaCapital);
			BigDecimal cuotaInteres = interesTotal.divide(new BigDecimal(plazo),2,RoundingMode.HALF_EVEN);
			System.out.println("Cuota Capital:"+cuotaInteres);
			BigDecimal saldo = deudaTotal;
			for (int i = 1 ; i<=plazo;i++) {
				try {
					
					fecha =  sdf.parse(sdf.format(calendar.getTime()));
					convAmortizacion = new ConvAmortizacion();
					convAmortizacion.setConvAdquirido(convAdquirido);
					convAmortizacion.setDescEstadoDescuento(descEstadoDescuento);
					convAmortizacion.setNumeroCuota(i);
					convAmortizacion.setFechaPago(fecha);
					
					cuotaTotalCapital = cuotaTotalCapital.add(cuotaCapital);
					cuotaTotalInteres = cuotaTotalInteres.add(cuotaInteres);
					
					if(i==plazo) {
						/*
						 * if(valorTotal.compareTo(cuotaTotalCapital)==1) { BigDecimal tmp =
						 * valorTotal.subtract(cuotaTotalCapital);
						 * convAmortizacion.setCapital(cuotaCapital.add(tmp)); } else
						 * if(cuotaTotalCapital.compareTo(valorTotal)==1) { BigDecimal tmp =
						 * cuotaTotalCapital.subtract(valorTotal);
						 * convAmortizacion.setCapital(cuotaCapital.subtract(tmp)); } else {
						 * convAmortizacion.setCapital(cuotaCapital); }
						 * 
						 * if(interesTotal.compareTo(cuotaTotalInteres)==1) { BigDecimal tmp =
						 * interesTotal.subtract(cuotaTotalInteres);
						 * convAmortizacion.setInteres(cuotaInteres.add(tmp)); } else
						 * if(cuotaTotalInteres.compareTo(interesTotal)==1){ BigDecimal tmp =
						 * cuotaTotalInteres.subtract(interesTotal);
						 * convAmortizacion.setInteres(cuotaInteres.subtract(tmp)); } else {
						 * convAmortizacion.setInteres(cuotaInteres); }
						 */
						convAmortizacion.setCapital(saldo.divide(interesTmp,2, RoundingMode.HALF_EVEN));
						convAmortizacion.setInteres(saldo.subtract(convAmortizacion.getCapital()));
						convAmortizacion.setValorCuota(saldo);
						
						BigDecimal tmp =convAmortizacion.getCapital().add(convAmortizacion.getInteres());
						convAmortizacion.setValorCuota(tmp);
						saldo = saldo.subtract(convAmortizacion.getValorCuota());
						convAmortizacion.setSaldo(saldo.setScale(2, RoundingMode.HALF_EVEN));
					}
					else {
						convAmortizacion.setCapital(cuotaCapital);
						convAmortizacion.setInteres(cuotaInteres);
						convAmortizacion.setValorCuota(cuotaCapital.add(cuotaInteres).setScale(2, RoundingMode.HALF_EVEN));
						saldo =  saldo.subtract(convAmortizacion.getValorCuota());
						convAmortizacion.setSaldo(saldo.setScale(2, RoundingMode.HALF_EVEN));
					}
					convAmortizacion.setAnio(anio);
					convAmortizacion.setMes(mes);
					
					
					
					managerConvenios.insertarConvAmortizacion(convAmortizacion);
				} catch (ParseException e) {
					//JSFUtil.crearMensajeERROR("No se genero correctamente");
					e.printStackTrace();
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR("No se registro la cuota correctamente");
					e.printStackTrace();
				}
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
				if(mes==12) {
					anio++;
					mes=1;
				}
				else {
					mes++;
				}
			}
		}
		else{
			try {
				//calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1);
				//System.out.println("Fecha del del siguiente mes:" + sdf.format(calendar.getTime()));
				fecha =  sdf.parse(sdf.format(calendar.getTime()));
				//System.out.println("fecha:"+fecha);
				convAmortizacion = new ConvAmortizacion();
				convAmortizacion.setConvAdquirido(convAdquirido);
				convAmortizacion.setDescEstadoDescuento(descEstadoDescuento);
				convAmortizacion.setNumeroCuota(plazo);
				convAmortizacion.setFechaPago(fecha);
				convAmortizacion.setCapital(convAdquirido.getValorTotal());
				convAmortizacion.setInteres(interesTotal);
				convAmortizacion.setValorCuota(convAdquirido.getDeudaTotal());
				convAmortizacion.setSaldo(new BigDecimal(0));
				convAmortizacion.setAnio(anio);
				convAmortizacion.setMes(mes);
				managerConvenios.insertarConvAmortizacion(convAmortizacion);
				
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR("No se registro la cuota correctamente");
				e.printStackTrace();
			}
		}
	}
	
	public boolean activarTablaAmortizacion(String estado) {
		if(!estado.equalsIgnoreCase("SOLICITADO") && !estado.equalsIgnoreCase("NO APROBADO")) {
			return true;
		}
		return false;
	}
	
	//GETTERS AND SETTERS
	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}
	public List<ConvServicio> getLstConvServicio() {
		return lstConvServicio;
	}
	public void setLstConvServicio(List<ConvServicio> lstConvServicio) {
		this.lstConvServicio = lstConvServicio;
	}
	public Long getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(Long idServicio) {
		this.idServicio = idServicio;
	}
	public ConvServicio getConvServicio() {
		return convServicio;
	}
	public void setConvServicio(ConvServicio convServicio) {
		this.convServicio = convServicio;
	}
	public ConvAdquirido getConvAdquirido() {
		return convAdquirido;
	}
	public void setConvAdquirido(ConvAdquirido convAdquirido) {
		this.convAdquirido = convAdquirido;
	}
	public List<ConvAdquirido> getLstConvAdquirido() {
		return lstConvAdquirido;
	}
	public void setLstConvAdquirido(List<ConvAdquirido> lstConvAdquirido) {
		this.lstConvAdquirido = lstConvAdquirido;
	}
	public List<ConvValorMax> getLstConvValorMax() {
		return lstConvValorMax;
	}
	public void setLstConvValorMax(List<ConvValorMax> lstConvValorMax) {
		this.lstConvValorMax = lstConvValorMax;
	}
	public Long getIdValorMax() {
		return idValorMax;
	}
	public void setIdValorMax(Long idValorMax) {
		this.idValorMax = idValorMax;
	}
	public String getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(String valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public BigDecimal getValorProformado() {
		return valorProformado;
	}
	public void setValorProformado(BigDecimal valorProformado) {
		this.valorProformado = valorProformado;
	}
	public boolean isPnlRender() {
		return pnlRender;
	}
	public void setPnlRender(boolean pnlRender) {
		this.pnlRender = pnlRender;
	}
	public int getPlazo() {
		return plazo;
	}
	public void setPlazo(int plazo) {
		this.plazo = plazo;
	}
	public List<ConvAmortizacion> getLstConvAmortizacion() {
		return lstConvAmortizacion;
	}
	public void setLstConvAmortizacion(List<ConvAmortizacion> lstConvAmortizacion) {
		this.lstConvAmortizacion = lstConvAmortizacion;
	}
	public Integer getMesCon() {
		return mesCon;
	}
	public void setMesCon(Integer mesCon) {
		this.mesCon = mesCon;
	}
	public Integer getAnioCon() {
		return anioCon;
	}
	public void setAnioCon(Integer anioCon) {
		this.anioCon = anioCon;
	}
	
}
