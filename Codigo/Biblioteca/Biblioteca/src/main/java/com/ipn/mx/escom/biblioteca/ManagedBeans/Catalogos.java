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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author oz_co
 */
 @ManagedBean
@Named(value = "catalogos")
@Dependent
public class Catalogos {

   
        private int idEdo;
     private int idRol;
    
  
     
    public Catalogos() {
    }


    
    
    public List<Empleado> showAll()
    {        
        Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Empleado");
        List lista=new ArrayList();
        
        lista=consulta.list();


        return lista;
    }
    
    public List<Contacto> showContacto()
    {        
        Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Contacto");
        List lista=new ArrayList();
        lista=consulta.list();
                hibernateSession.close();

        return lista;
    }
    
    public List<Estadoempleado> showEstadoempleado()
    {        
        Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Estadoempleado");
        List lista=new ArrayList();
        lista=consulta.list();
                hibernateSession.close();

        return lista;
    }
    
    public List<Rolempleado> showrolempleado()
    {        
        Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Rolempleado");
        List lista=new ArrayList();
        lista=consulta.list();
                hibernateSession.close();

        return lista;
    }
    
    public String deletById(int idEmpleado)
    {
        Session hibernateSession;
  
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 

        Transaction t=hibernateSession.beginTransaction(); 
        Empleado Empleado=(Empleado)hibernateSession.load(Empleado.class,idEmpleado);
        hibernateSession.delete(Empleado);
        t.commit(); 
        hibernateSession.close();

        return "Empleados";
    }
    
   
    
}
