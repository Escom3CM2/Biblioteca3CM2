package com.ipn.mx.escom.biblioteca.Pojos;
// Generated 24/05/2017 04:48:03 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Contacto generated by hbm2java
 */
public class Contacto  implements java.io.Serializable {


     private int idContacto;
     private Colonia colonia;
     private String calleNum;
     private String telCasa;
     private String telCelular;
     private String correo;
     private Set lectors = new HashSet(0);
     private Set empleados = new HashSet(0);

    public Contacto() {
    }

	
    public Contacto(int idContacto, Colonia colonia) {
        this.idContacto = idContacto;
        this.colonia = colonia;
    }
    public Contacto(int idContacto, Colonia colonia, String calleNum, String telCasa, String telCelular, String correo, Set lectors, Set empleados) {
       this.idContacto = idContacto;
       this.colonia = colonia;
       this.calleNum = calleNum;
       this.telCasa = telCasa;
       this.telCelular = telCelular;
       this.correo = correo;
       this.lectors = lectors;
       this.empleados = empleados;
    }
   
    public int getIdContacto() {
        return this.idContacto;
    }
    
    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }
    public Colonia getColonia() {
        return this.colonia;
    }
    
    public void setColonia(Colonia colonia) {
        this.colonia = colonia;
    }
    public String getCalleNum() {
        return this.calleNum;
    }
    
    public void setCalleNum(String calleNum) {
        this.calleNum = calleNum;
    }
    public String getTelCasa() {
        return this.telCasa;
    }
    
    public void setTelCasa(String telCasa) {
        this.telCasa = telCasa;
    }
    public String getTelCelular() {
        return this.telCelular;
    }
    
    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }
    public String getCorreo() {
        return this.correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Set getLectors() {
        return this.lectors;
    }
    
    public void setLectors(Set lectors) {
        this.lectors = lectors;
    }
    public Set getEmpleados() {
        return this.empleados;
    }
    
    public void setEmpleados(Set empleados) {
        this.empleados = empleados;
    }




}


