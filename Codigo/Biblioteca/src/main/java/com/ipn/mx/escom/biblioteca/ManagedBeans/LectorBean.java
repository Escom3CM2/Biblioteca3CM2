/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.escom.biblioteca.ManagedBeans;

import com.ipn.mx.escom.biblioteca.daos.PrestamointerDAO;
import com.ipn.mx.escom.biblioteca.daos.LectorDAO;
import com.ipn.mx.escom.biblioteca.Pojos.Ejemplar;
import com.ipn.mx.escom.biblioteca.Pojos.Estudiante;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;
import com.ipn.mx.escom.biblioteca.Pojos.Libro;
import com.ipn.mx.escom.biblioteca.Pojos.Prestamo;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author bardo
 */

@ManagedBean
@ViewScoped
public class LectorBean {
    @ManagedProperty(value = "#{lector}")
    private Lector lector;
    private Boolean lector_apto;

    public Lector getLector() {
        return lector;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }
    
    /**
     * Creates a new instance of LectorBean
     */
    public LectorBean() { }
    
    public Boolean getPrestamoApto(){
        return true;
    }
    
    public void verifyAll(){
        LectorDAO lectordao = new LectorDAO();
        lector = lectordao.getLector(lector.getIdLector());
    }
}
