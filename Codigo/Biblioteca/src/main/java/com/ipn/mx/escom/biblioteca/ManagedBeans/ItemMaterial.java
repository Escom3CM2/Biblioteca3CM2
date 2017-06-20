package com.ipn.mx.escom.biblioteca.ManagedBeans;

import java.util.Date;

import com.ipn.mx.escom.biblioteca.Anexos.PrestamoB;
import com.ipn.mx.escom.biblioteca.Anexos.PrestamoB.Material;



public class ItemMaterial {
	private int id;
	private String titulo;
	private Date fechaEntrega;
	private Date fechaPrestamo;
	private PrestamoB.Material tipo;
	private String observaciones;
	private Object Obj;
	
	
	
	
	
	
	
	public ItemMaterial(int id, String titulo, Date fechaEntrega, Date fechaPrestamo, Material tipo,
			String observaciones, Object obj) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.fechaEntrega = fechaEntrega;
		this.fechaPrestamo = fechaPrestamo;
		this.tipo = tipo;
		this.observaciones = observaciones;
		Obj = obj;
	}
	public Object getObj() {
		return Obj;
	}
	public void setObj(Object obj) {
		Obj = obj;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}
	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}
	public PrestamoB.Material getTipo() {
		return tipo;
	}
	public void setTipo(PrestamoB.Material tipo) {
		this.tipo = tipo;
	} 
	
		
}
