package com.ipn.mx.escom.biblioteca.Pojos;
// Generated 24/05/2017 04:48:03 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Estadoestudiante generated by hbm2java
 */
public class Estadoestudiante  implements java.io.Serializable {


     private int idEdoEst;
     private String edoEst;
     private Set estudiantes = new HashSet(0);

    public Estadoestudiante() {
    }

	
    public Estadoestudiante(int idEdoEst, String edoEst) {
        this.idEdoEst = idEdoEst;
        this.edoEst = edoEst;
    }
    public Estadoestudiante(int idEdoEst, String edoEst, Set estudiantes) {
       this.idEdoEst = idEdoEst;
       this.edoEst = edoEst;
       this.estudiantes = estudiantes;
    }
   
    public int getIdEdoEst() {
        return this.idEdoEst;
    }
    
    public void setIdEdoEst(int idEdoEst) {
        this.idEdoEst = idEdoEst;
    }
    public String getEdoEst() {
        return this.edoEst;
    }
    
    public void setEdoEst(String edoEst) {
        this.edoEst = edoEst;
    }
    public Set getEstudiantes() {
        return this.estudiantes;
    }
    
    public void setEstudiantes(Set estudiantes) {
        this.estudiantes = estudiantes;
    }




}


