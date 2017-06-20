package com.ipn.mx.escom.biblioteca.Pojos;
// Generated 24/05/2017 04:48:03 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Rolempleado generated by hbm2java
 */
public class Rolempleado  implements java.io.Serializable {


     private int idRolEmpleado;
     private String rolEmp;
     private Set empleados = new HashSet(0);

    public Rolempleado() {
    }

	
    public Rolempleado(int idRolEmpleado, String rolEmp) {
        this.idRolEmpleado = idRolEmpleado;
        this.rolEmp = rolEmp;
    }
    public Rolempleado(int idRolEmpleado, String rolEmp, Set empleados) {
       this.idRolEmpleado = idRolEmpleado;
       this.rolEmp = rolEmp;
       this.empleados = empleados;
    }
   
    public int getIdRolEmpleado() {
        return this.idRolEmpleado;
    }
    
    public void setIdRolEmpleado(int idRolEmpleado) {
        this.idRolEmpleado = idRolEmpleado;
    }
    public String getRolEmp() {
        return this.rolEmp;
    }
    
    public void setRolEmp(String rolEmp) {
        this.rolEmp = rolEmp;
    }
    public Set getEmpleados() {
        return this.empleados;
    }
    
    public void setEmpleados(Set empleados) {
        this.empleados = empleados;
    }




}

