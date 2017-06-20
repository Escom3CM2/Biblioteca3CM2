package com.ipn.mx.escom.biblioteca.Anexos;

import java.util.regex.Pattern;

import com.ipn.mx.escom.biblioteca.Anexos.PrestamoB.Material;

public class Input {

	private String name;
	private String exp;
	
	
	
	public Input(String name, String exp) {
		super();
		this.name = name;
		this.exp = exp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	
	public boolean validar(String in){
		return Pattern.matches(exp,in);
	}
	
	
	
}
