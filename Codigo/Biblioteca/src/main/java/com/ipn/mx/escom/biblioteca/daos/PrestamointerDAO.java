/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.escom.biblioteca.daos;

import com.ipn.mx.escom.biblioteca.Pojos.Escuela;
import com.ipn.mx.escom.biblioteca.Pojos.Prestamointer;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.ipn.mx.escom.biblioteca.Anexos.NewHibernateUtil;
/**
 *
 * @author boa
 */
public class PrestamointerDAO {
    
    public PrestamointerDAO(){
       
    }
    
    
    public List<Prestamointer> getPrestamoById(String id){
        SessionFactory sessionFactory;
        sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
                        
            session.beginTransaction();
            String query_string = "from Prestamointer where idPrestamoInter=:id";
            Query query = session.createQuery(query_string);
               query.setString("id", id);
            List<Prestamointer> list = query.list();
            if(list.size() > 0){
                for(Prestamointer p : list){
                    System.out.println(p.getEscuela());
                }
                return list;
            }
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
    
    public List<Prestamointer> getPrestamoByEscuela(String escuela){
        SessionFactory sessionFactory;
        sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            String query_string = "from Prestamointer as p where p.escuela.NombreEsc=:escuela";
            Query query = session.createQuery(query_string);
            query.setString("escuela", escuela);
            List<Prestamointer> list = query.list();
            if(list.size() > 0){
                return list;
            }
        }catch(Exception e){
            
        }finally{
            session.flush();
            session.close();
        }
        return null;
    }
    
    public List<Prestamointer> prestamosList(){
           SessionFactory sessionFactory;
           sessionFactory = NewHibernateUtil.getSessionFactory();
           Session session = sessionFactory.openSession();
           try{
            session.beginTransaction();
            String query_string = "from Prestamointer";
            Query query = session.createQuery(query_string);
            List<Prestamointer> list = query.list();
            if(list.size() > 0){
                for(Prestamointer p : list){
                    System.out.println(p.getEscuela());
                }
                return list;
            }
        }catch(Exception e){
            
        }finally{
            session.flush();
            session.close();
        }
        return null;
    }
     public List<Prestamointer> getPrestamoByTitulo(String titulo){
           SessionFactory sessionFactory;
           sessionFactory = NewHibernateUtil.getSessionFactory();
           Session session = sessionFactory.openSession();
           try{
            session.beginTransaction();
            String query_string = "from Prestamointer where titulo=:title";
            Query query = session.createQuery(query_string);
            query.setString("title", titulo);
            List<Prestamointer> list = query.list();
            if(list.size() > 0){
                 for(Prestamointer p : list){
                    System.out.println(p.getEscuela());
                }
                return list;
            }
        }catch(Exception e){
            
        }finally{
            session.flush();
            session.close();
        }
        return null;
    }
}
