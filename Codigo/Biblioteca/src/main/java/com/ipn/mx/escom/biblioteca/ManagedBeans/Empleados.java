/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.escom.biblioteca.ManagedBeans;

import com.ipn.mx.escom.biblioteca.Pojos.Colonia;
import com.ipn.mx.escom.biblioteca.Pojos.Contacto;
import com.ipn.mx.escom.biblioteca.Pojos.Cp;
import com.ipn.mx.escom.biblioteca.Pojos.Delegacion;
import com.ipn.mx.escom.biblioteca.Pojos.Empleado;
import com.ipn.mx.escom.biblioteca.Pojos.Estadoempleado;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Rolempleado;
import com.ipn.mx.escom.biblioteca.Pojos.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author oz_co
 */
 @ManagedBean
 @SessionScoped

public class Empleados implements Serializable {


     private int idEmpleado;
     
    private Contacto contacto;
    
    private Estadoempleado estadoempleado;
    private Rolempleado rolempleado;
     
     private Usuario usuario;
     
     private String nombre;
     private String primerAp;
     private String segundoAp;
     private Date fechaAdmision;
     private Integer estadoBorrado;
     
     
        private int idEdo;
     private int idRol;
    
     private int matricula;
     private String password;
     private int tipo;
     
     
    public Empleados() {
    }


    
    
    public List<Empleado> showAll()
    {        
         

        Session hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Empleado");
        List lista=new ArrayList();
        
        lista=consulta.list();
        hibernateSession.close();
        return lista;
    }
    
   
    
    public Empleado showById(int id)
    {
        
    Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
        Transaction t0=hibernateSession.beginTransaction();
        
        Empleado empleado=(Empleado)hibernateSession.load(Empleado.class, id);

        
        hibernateSession.update(empleado);
        t0.commit();
        hibernateSession.close();        
        
        return empleado;
    }
    

    
    
    public String add()
    {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(matricula);
        empleado.setNombre(nombre);
        empleado.setPrimerAp(primerAp);
        empleado.setSegundoAp(segundoAp);
        empleado.setFechaAdmision(fechaAdmision);
        empleado.setEstadoBorrado(0);
        Usuario usuario = new Usuario(matricula,password,1);
        empleado.setRolempleado(getRol(idRol));
        empleado.setEstadoempleado(getEdo(idEdo));
        
        empleado.setUsuario(usuario );
        
        
        empleado.setContacto(getCont(1));
        
        Session hibernateSession =HibernateUtil.getSessionFactory().openSession();

         Transaction t0=hibernateSession.beginTransaction();
        hibernateSession.save(usuario);
        hibernateSession.save(empleado);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();
        return "Empleados";
    }
    
    public void update(int id){
       
        
        Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
        Transaction t0=hibernateSession.beginTransaction();
        
        Empleado empleado=(Empleado)hibernateSession.load(Empleado.class, id);

        empleado.setRolempleado(getRol(idRol));
        empleado.setEstadoempleado(getEdo(idEdo));
        
           

        hibernateSession.update(getRol(idRol));
        hibernateSession.update(getEdo(idEdo));

        hibernateSession.update(empleado);
        hibernateSession.getTransaction().commit();
        hibernateSession.close();

         
    }

    public Estadoempleado getEdo(int idEdo)
    {
        
        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        hibernateSession.beginTransaction(); 
        
        Estadoempleado edo =(Estadoempleado)hibernateSession.load(Estadoempleado.class,idEdo);
        
        hibernateSession.getTransaction().commit();
        hibernateSession.close();

        return edo;
    
    }
    
    public Rolempleado getRol(int idRol)
    {
        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Transaction t=hibernateSession.beginTransaction(); 
        Rolempleado edo =(Rolempleado)hibernateSession.load(Rolempleado.class,idRol);
        t.commit(); 
            hibernateSession.close();

        return edo;
    }
    
    
    public Contacto getCont(int idCon)
    {
        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Transaction t=hibernateSession.beginTransaction(); 
        Contacto edo =(Contacto)hibernateSession.load(Contacto.class,idCon);
        t.commit(); 
        hibernateSession.close();
        return edo;
    }
    
    
    
    
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public Estadoempleado getEstadoempleado() {
        return estadoempleado;
    }

    public void setEstadoempleado(Estadoempleado estadoempleado) {
        this.estadoempleado = estadoempleado;
    }

    public Rolempleado getRolempleado() {
        return rolempleado;
    }

    public void setRolempleado(Rolempleado rolempleado) {
        this.rolempleado = rolempleado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerAp() {
        return primerAp;
    }

    public void setPrimerAp(String primerAp) {
        this.primerAp = primerAp;
    }

    public String getSegundoAp() {
        return segundoAp;
    }

    public void setSegundoAp(String segundoAp) {
        this.segundoAp = segundoAp;
    }

    public Date getFechaAdmision() {
        return fechaAdmision;
    }

    public void setFechaAdmision(Date fechaAdmision) {
        this.fechaAdmision = fechaAdmision;
    }

    public Integer getEstadoBorrado() {
        return estadoBorrado;
    }

    public void setEstadoBorrado(Integer estadoBorrado) {
        this.estadoBorrado = estadoBorrado;
    }

    public int getIdEdo() {
        return idEdo;
    }

    public void setIdEdo(int idEdo) {
        this.idEdo = idEdo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

 
    
}
