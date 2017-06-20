package com.ipn.mx.escom.biblioteca.daos;

import com.ipn.mx.escom.biblioteca.Pojos.Ejemplar;
import com.ipn.mx.escom.biblioteca.Pojos.Estudiante;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;
import com.ipn.mx.escom.biblioteca.Pojos.Libro;
import com.ipn.mx.escom.biblioteca.Pojos.Prestamo;
import com.ipn.mx.escom.biblioteca.Pojos.Prestamointer;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.ipn.mx.escom.biblioteca.Anexos.NewHibernateUtil;

/**
 *
 * @author bardo
 */
public class DevolucionDAO {
    
    public Estudiante getEstudiante(String id){
        SessionFactory sessionFactory;
        sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
                        
            session.beginTransaction();
            String query_string = "select l.estudiantes from Lector as l "
                    + "inner join l.prestamos as p "
                    + "inner join p.DPrestamos as d "
                    + "inner join d.ejemplar as e "
                    + "where e.idEjemplar =:id";
            Query query = session.createQuery(query_string);
            query.setString("id", id);
            query.setMaxResults(1);
            Estudiante estudiante = (Estudiante) query.uniqueResult();
            return estudiante;
            
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
    
    public void modificarEjemplar(Ejemplar ejemplar){
        System.out.println(ejemplar);
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            try{
                session.beginTransaction();
                session.update(ejemplar);
                session.getTransaction().commit();
            }catch(HibernateException e){
                e.printStackTrace();
                session.getTransaction().rollback();
            }finally{
                if(session != null){
                    session.close();
                }
            }
            
    }
    public Libro getLibro(String id){
        SessionFactory sessionFactory;
        sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
                        
            session.beginTransaction();
            String query_string = "select lb from Lector as l "
                    + "inner join l.prestamos as p "
                    + "inner join p.DPrestamos as d "
                    + "inner join d.ejemplar as e "
                    + "inner join e.libro as lb "
                    + "where e.idEjemplar =:id";
            Query query = session.createQuery(query_string);
            query.setString("id", id);
            query.setMaxResults(1);
            Libro libro = (Libro) query.uniqueResult();
            return libro;
            
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
    public Ejemplar getEjemplar(String id){
        SessionFactory sessionFactory;
        sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
                        
            session.beginTransaction();
            String query_string = "select e from Lector as l "
                    + "inner join l.prestamos as p "
                    + "inner join p.DPrestamos as d "
                    + "inner join d.ejemplar as e "
                    + "where e.idEjemplar =:id";
            Query query = session.createQuery(query_string);
            query.setString("id", id);
            query.setMaxResults(1);
            Ejemplar ejemplar = (Ejemplar) query.uniqueResult();
            return ejemplar;
            
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
    public Prestamo getPrestamo(String id){
        SessionFactory sessionFactory;
        sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
                        
            session.beginTransaction();
            String query_string = "select l.prestamos from Lector as l "
                    + "inner join l.prestamos as p "
                    + "inner join p.DPrestamos as d "
                    + "inner join d.ejemplar as e "
                    + "where e.idEjemplar =:id";
            Query query = session.createQuery(query_string);
            query.setString("id", id);
            query.setMaxResults(1);
            Prestamo prestamo = (Prestamo) query.uniqueResult();
            return prestamo;
            
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
