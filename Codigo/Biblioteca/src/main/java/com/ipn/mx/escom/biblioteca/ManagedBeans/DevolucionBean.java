/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.escom.biblioteca.ManagedBeans;


import com.ipn.mx.escom.biblioteca.daos.DevolucionDAO;
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
public class DevolucionBean {
    @ManagedProperty(value = "#{estudiante}")
    private Estudiante estudiante;
    @ManagedProperty(value = "#{lector}")
    private Lector lector;
    @ManagedProperty(value = "#{ejemplar}")
    private Ejemplar ejemplar;
    @ManagedProperty(value = "#{libro}")
    private Libro libro;
    @ManagedProperty(value = "#{prestamo}")
    private Prestamo prestamo;
    private String id_material;
    private int total_multa;
    private List<String> otras_multas;

    public List<String> getOtras_multas() {
        return otras_multas;
    }

    public void setOtras_multas(List<String> otras_multas) {
        this.otras_multas = otras_multas;
    }
    private static final int MULTADIA = 5;
    
    public DevolucionBean() {
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }


    public String getId_material() {
        return id_material;
    }

    public int getTotal_multa() {
        return total_multa;
    }

    public void setTotal_multa(int total_multa) {
        this.total_multa = total_multa;
    }

    public void setId_material(String id_material) {
        this.id_material = id_material;
    }

    public Lector getLector() {
        return lector;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }
    
    public void devolver(){
        int days;
        DevolucionDAO dao = new DevolucionDAO();
        ejemplar = dao.getEjemplar(id_material);
        //Verifica si el ejemplar existe
        if(ejemplar == null){
            id_material = null;
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "No se encontró ese material"));
            return;
        }
        //Verifica si el ejemplar está prestado
        if(ejemplar.getDisponibilidad() == 0){
            id_material = null;
            FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "El material no se encuentra prestado"));
        }
        estudiante = dao.getEstudiante(id_material);
        libro = dao.getLibro(id_material);
        prestamo = dao.getPrestamo(id_material);
        days =(int) getDifferenceDays(prestamo.getFechaFin(), Calendar.getInstance().getTime());
        //Calcula multa
        if(days <= 0){           
            total_multa = 0;
        }else{
            total_multa = days * MULTADIA;
        }       
    }
    
    
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
    
    public void modificarObs(){
        System.out.println(ejemplar.getIdEjemplar());
        System.out.println(ejemplar.getObservaciones());
        DevolucionDAO dao = new DevolucionDAO();
        dao.modificarEjemplar(ejemplar);
    }
    
    
}
