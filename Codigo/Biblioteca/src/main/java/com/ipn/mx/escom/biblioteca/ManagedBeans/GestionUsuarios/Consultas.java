package com.ipn.mx.escom.biblioteca.ManagedBeans.GestionUsuarios;


import com.ipn.mx.escom.biblioteca.Pojos.DPrestamo;
import com.ipn.mx.escom.biblioteca.Pojos.Dmultaeje;
import com.ipn.mx.escom.biblioteca.Pojos.Ejemplar;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Libro;
import com.ipn.mx.escom.biblioteca.Pojos.Prestamo;
import com.ipn.mx.escom.biblioteca.Pojos.Tipomulta;
import com.ipn.mx.escom.biblioteca.Pojos.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;


@ManagedBean
@SessionScoped
public class Consultas implements Serializable {
    
    private List<Prestamo> prestamos;
    private List<Prestamo> pendientes;
    
    public String ejemplo(String a)
    {
        return a;
    }
    public List<Prestamo> Prestamos(int matricula)
    {
        Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Prestamo where lector.idLector=:matricula");
        consulta.setParameter("matricula", matricula);
        List lista= consulta.list();
        hibernateSession.close();
        this.prestamos = lista;
        return lista;
    }
    
    public List<Prestamo> Pendientes(int matricula)
    {
        Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Prestamo where lector.idLector=:matricula and estadoPr=:estado");
        consulta.setParameter("matricula", matricula);
        consulta.setParameter("estado", 1);
        List lista= consulta.list();
        hibernateSession.close();
        this.pendientes = lista;
        return lista;
    }
    
    
    public Libro libro(Prestamo prestamo)
    {
         Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from DPrestamo where prestamo=:pre");
        consulta.setParameter("pre", prestamo);
        List lista= consulta.list();
        DPrestamo dprestamo = (DPrestamo)lista.get(0);
        
        Ejemplar ejemplar = dprestamo.getEjemplar();
        
        consulta=hibernateSession.createQuery("from Ejemplar where idEjemplar=:idE");
        consulta.setParameter("idE", ejemplar.getIdEjemplar());
        lista= consulta.list();
        ejemplar = (Ejemplar)lista.get(0);
        
        
         consulta=hibernateSession.createQuery("from Libro where isbnId=:idL");
        consulta.setParameter("idL", ejemplar.getLibro().getIsbnId() );
        lista= consulta.list();
        Libro libro  = (Libro)lista.get(0);
        
       
        
         hibernateSession.close();

        return libro;
        
    }
    public int numeroPrestamos(int matricula)
    {
        Prestamos(matricula);
        return prestamos.size();
    }
    
    public int numeroPendientes(int matricula)
    {
        Pendientes(matricula);
        return pendientes.size();
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public List<Prestamo> getPendientes() {
        return pendientes;
    }

    public void setPendientes(List<Prestamo> pendientes) {
        this.pendientes = pendientes;
    }
    
    
    
}

