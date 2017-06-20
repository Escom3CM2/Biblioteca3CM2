/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.escom.biblioteca.daos;

import com.ipn.mx.escom.biblioteca.Pojos.Estudiante;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author bardo
 */
public class LectorDAO {
    public Lector getLector(int id){
        SessionFactory sessionFactory;
        sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            Lector lector = (Lector) session.get(Lector.class, id);
            return lector;
        }catch(Exception e){
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error",  "No se encontró ningún campo") );
        }finally{
            session.flush();
            session.close();
        }
        return null;
    }
}
