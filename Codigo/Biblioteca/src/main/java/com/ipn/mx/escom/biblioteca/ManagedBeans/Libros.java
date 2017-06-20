package com.ipn.mx.escom.biblioteca.ManagedBeans;

import com.ipn.mx.escom.biblioteca.Anexos.Mensajes;
import com.ipn.mx.escom.biblioteca.Anexos.RN2;
import com.ipn.mx.escom.biblioteca.Pojos.Ejemplar;
import com.ipn.mx.escom.biblioteca.Pojos.Estadolibro;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Libro;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author oz_co
 */
@ManagedBean
@SessionScoped

public class Libros implements Serializable {

    // campos de libro
    private String isbnId;
    private String titulo;
    private String autor1;
    private String autor2;
    private String autor3;
    private String autor4;
    private String editorial;
    private String fechaPub;
    private int noPags;
    private int edicion;
    private double precio;
    private int estadoBorrado;
    
    private Libro libro;
    private String readonly;
    private int cantidadregistro;

    public int getCantidadregistro() {
        return cantidadregistro;
    }

    public void setCantidadregistro(int cantidadregistro) {
        this.cantidadregistro = cantidadregistro;
    }

    public Libros() {
    }

    public boolean obtenerInstanciaLibro() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = hibernateSession.beginTransaction();
        isbnId = isbnId.replaceAll("\\s+", "");
        libro = (Libro) hibernateSession.createQuery("from Libro where isbnId='" + isbnId + "' ").uniqueResult();
        tx.commit();
        hibernateSession.close();
        if (libro == null) {///Add message book couldn't found
            readonly = "false";
            return false;
        } else {//call getters methods to fill the fields
            isbnId = libro.getIsbnId();
            titulo = libro.getTitulo();
            autor1 = libro.getAutor1();
            autor2 = libro.getAutor2();
            autor3 = libro.getAutor3();
            autor4 = libro.getAutor4();
            editorial = libro.getEditorial();
            fechaPub = libro.getFechaPub();
            noPags = libro.getNoPags();
            edicion = libro.getEdicion();
            precio = libro.getPrecio();
            estadoBorrado = libro.getEstadoBorrado();
            readonly = "true";
            return true;
        }
    }

    private String registrarCantidad() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = hibernateSession.beginTransaction();
        int idEjemplar = ((Number) hibernateSession.createQuery("select MAX(idEjemplar) FROM Ejemplar").uniqueResult()).intValue();//new id for the copy
        Estadolibro edoLibro = (Estadolibro) hibernateSession.createQuery("from Estadolibro where idEdoLibro=1 ").uniqueResult();
        byte n = (byte) 1;///disponibilidad byte
        ///prepare all to insert a new "EJEMPLAR", the instance already exists
        if (cantidadregistro > 0) {
            for (int i = 0; i < cantidadregistro; i++) {
                hibernateSession.save(new Ejemplar(idEjemplar += 1, edoLibro, libro, n));
            }
            tx.commit();
            hibernateSession.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Libro(s) Registrado(s) Exitosamente!"));
            return "RegistrarLibro";
        } else {
            tx.commit();
            hibernateSession.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Error", "Introduzca un numero mayor a 0"));
            return "RegistrarLibro";
        }
    }

    private String registrarInstancia() {
        Libro libro1 = new Libro();
        libro1.setIsbnId(isbnId);
        libro1.setAutor1(autor1);
        libro1.setTitulo(titulo);
        libro1.setAutor2(autor2);
        libro1.setAutor3(autor3);
        libro1.setAutor4(autor4);
        libro1.setEditorial(editorial);
        libro1.setFechaPub(fechaPub);
        libro1.setNoPags(noPags);
        libro1.setEdicion(edicion);
        libro1.setPrecio(precio);
        libro1.setEstadoBorrado(estadoBorrado);
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = hibernateSession.beginTransaction();
        int idEjemplar = ((Number) hibernateSession.createQuery("select MAX(idEjemplar) FROM Ejemplar").uniqueResult()).intValue();//new id for the copy
        Estadolibro edoLibro = (Estadolibro) hibernateSession.createQuery("from Estadolibro where idEdoLibro=1 ").uniqueResult();
        byte n = (byte) 1;
        Set setEjemplares = new HashSet();
        if (cantidadregistro > 0) {
            for (int i = 0; i < cantidadregistro; i++) {
                setEjemplares.add(new Ejemplar(idEjemplar += 1, edoLibro, libro1, n));
            }
            libro1.setEjemplars(setEjemplares);
            hibernateSession.save(libro1);
            tx.commit();
            hibernateSession.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Instancia de Libro Registrada Exitosamente!"));
        } else {
            tx.commit();
            hibernateSession.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Error", "Introduzca un numero mayor a 0"));
        }

        return "RegistrarLibro";
    }

    //Funcion que verifica si un libro existe
    public String verificarLibro() throws IOException {
        clearValues();//restart the values
        if (RN2.RN2_1b1(isbnId)) {///Verify if field are not empty
            if (RN2.RN2_1b2(isbnId)) {///Verify if the ISBN is well conformed
                obtenerInstanciaLibro();//get instance o book if exist
                return "RegistrarLibro"; //Redirect Registrar Libro
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "!Error",
                        Mensajes.MSJ2_1b2));
                return "ConsultarLibro";
            }
        } /////////////Agrega Mensaje de Error
        else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "!Error",
                    Mensajes.MSJ2_1b1));
            return "ConsultarLibro  ";
        }
    }
    //777///////////////////////////////////////////////////////////

    //its used to add a new Book, first verify if there are an instance
    public void add() {
        if (libro != null) {
            System.out.println("qqq");
            registrarCantidad(); //if there are instances, we only records books
        } else {
            System.out.println("pppp");
            if (noPags > 0 && edicion > 0 && precio > 0) {
                registrarInstancia();
            } else {
                sendError();
            }
        }
    }

    private String sendError() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "!Error", "Introduzca un numero correcto"));
        return "RegistrarLibro";
    }

    public void clearValues() {

        //Se llena la clase con todos sus parametros necesarios para realizar un insert
        setTitulo("");
        setAutor1("");
        setAutor2("");
        setAutor3("");
        setAutor4("");
        setEditorial("");
        setFechaPub("");
        setNoPags(0);
        setEdicion(0);
        setPrecio(0);
        setEstadoBorrado(0);

    }

    public String getIsbnId() {
        return isbnId;
    }

    public void setIsbnId(String isbnId) {
        this.isbnId = isbnId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor1() {
        return autor1;
    }

    public void setAutor1(String autor1) {
        this.autor1 = autor1;
    }

    public String getAutor2() {
        return autor2;
    }

    public void setAutor2(String autor2) {
        this.autor2 = autor2;
    }

    public String getAutor3() {
        return autor3;
    }

    public void setAutor3(String autor3) {
        this.autor3 = autor3;
    }

    public String getAutor4() {
        return autor4;
    }

    public void setAutor4(String autor4) {
        this.autor4 = autor4;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getFechaPub() {
        return fechaPub;
    }

    public void setFechaPub(String fechaPub) {
        this.fechaPub = fechaPub;
    }

    public int getNoPags() {
        return noPags;
    }

    public void setNoPags(int noPags) {
        this.noPags = noPags;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getEstadoBorrado() {
        return estadoBorrado;
    }

    public void setEstadoBorrado(int estadoBorrado) {
        this.estadoBorrado = estadoBorrado;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

}
