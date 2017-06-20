/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.escom.biblioteca.ManagedBeans;

import com.ipn.mx.escom.biblioteca.Anexos.Mensajes;
import com.ipn.mx.escom.biblioteca.Anexos.RN2;
import com.ipn.mx.escom.biblioteca.Pojos.Elemento;
import com.ipn.mx.escom.biblioteca.Pojos.Estadoelemnto;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Tt;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean
@SessionScoped

public class TTs {

    // campos de TT
    private String noTt;
    private Elemento elemento;
    private String titulo;
    private Date fechaPresent;
    private String autor1;
    private String autor2;
    private String autor3;
    private String autor4;
    private String autor5;
    private String director1;
    private String director2;
    private Integer estadoBorrado;

    // objeto tt
    private Tt tt;

    // auxiliar para estadoBorrado
    boolean borrar;

    public TTs() {
    }

    // date to String
    private String dateToString(Date fecha) {
        if (fecha != null) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            return df.format(fecha);
        }
        return "";
    }

    // Consultar
    public boolean obtenerInstanciaTt() {
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = hibernateSession.beginTransaction();
        tt = (Tt) hibernateSession.createQuery("from Tt where noTt='" + noTt + "' ").uniqueResult();
        tx.commit();
        hibernateSession.close();
        if (tt == null) {
            return false;
        } else {
            noTt = tt.getNoTt();
            titulo = tt.getTitulo();
            fechaPresent = tt.getFechaPresent();
            autor1 = tt.getAutor1();
            autor2 = tt.getAutor2();
            autor3 = tt.getAutor3();
            autor4 = tt.getAutor4();
            autor5 = tt.getAutor5();
            director1 = tt.getDirector1();
            director2 = tt.getDirector2();
            estadoBorrado = tt.getEstadoBorrado();
            return true;
        }
    }

    // Registrar
    public String registrarInstanciaTt() {

        // se registra el elemento para que este disponible
        Estadoelemnto estadoElemento = new Estadoelemnto(1, "Disponible");

        Tt tt1 = new Tt();
        tt1.setNoTt(noTt);
        tt1.setTitulo(titulo);
        tt1.setFechaPresent(fechaPresent);
        tt1.setAutor1(autor1);
        tt1.setAutor2(autor2);
        tt1.setAutor3(autor3);
        tt1.setAutor4(autor4);
        tt1.setAutor5(autor5);
        tt1.setDirector1(director1);
        tt1.setDirector2(director2);
        // borrado S/N = 1/0
        estadoBorrado = 0;
        tt1.setEstadoBorrado(estadoBorrado);

        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = hibernateSession.beginTransaction();
        try {

            int idElemento = ((Number) hibernateSession.createQuery("select MAX(idElemento) FROM Elemento").uniqueResult()).intValue();
            idElemento++;
            elemento = new Elemento(idElemento, estadoElemento);
            tt1.setElemento(elemento);

            hibernateSession.save(elemento);

            hibernateSession.save(tt1);
            hibernateSession.getTransaction().commit();
            hibernateSession.close();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "¡Éxito!", "El TT fue agregado"));
            clearValues();
            return "ConsultarTTReg";
        } catch (HibernateException e) {
            tx.commit();
            hibernateSession.close();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "!Error", Mensajes.MSJ2_1b3));
            clearValues();
            return "RegistrarTt";
        }
    }

    // modificar 
    private String actualizarInstanciaTt() {
        Tt tt1 = new Tt();
        tt1.setNoTt(noTt);
        /*
        tt1.setTitulo(titulo);
        tt1.setFechaPresent(fechaPresent);
        tt1.setAutor1(autor1);
        tt1.setAutor2(autor2);
        tt1.setAutor3(autor3);
        tt1.setAutor4(autor4);
        tt1.setAutor5(autor5);
        tt1.setDirector1(director1);
        tt1.setDirector2(director2);
        // borrado S/N = 1/0
        estadoBorrado = 0;
        tt1.setEstadoBorrado(estadoBorrado);
         */
        Session hibernateSession = HibernateUtil.getSessionFactory().openSession();
        Transaction t0 = hibernateSession.beginTransaction();

        tt = (Tt) hibernateSession.load(Tt.class, tt1.getNoTt());

        tt.setTitulo(titulo);
        tt.setFechaPresent(fechaPresent);
        tt.setAutor1(autor1);
        tt.setAutor2(autor2);
        tt.setAutor3(autor3);
        tt.setAutor4(autor4);
        tt.setAutor5(autor5);
        tt.setDirector1(director1);
        tt.setDirector2(director2);
        if (borrar) {
            tt.setEstadoBorrado(1);
        } else {
            tt.setEstadoBorrado(0);
        }
        try {
            hibernateSession.update(tt);
            hibernateSession.getTransaction().commit();
            hibernateSession.close();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "OK", "Se modificó exitosamente el TT"));
            clearValues();
            return "ConsultarTTMod";
        } catch (HibernateException e) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Error!", "No se pudo modificar el TT"));
            return "ModificarEliminarTt";
        }
    }

    public String verificarTtRegistrar() throws IOException {

        clearValues();

        // non empty field
        if (RN2.RN2_1b1(noTt)) {
            // noTT accepted
            if (RN2.RN2_1b2(noTt)) {
                // verify if noTT alredy exist
                if (obtenerInstanciaTt()) {
                    // if exists
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "¡Cuidado!", Mensajes.MSJ2_1b5));
                } else {
                    // U can register new TT element
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "¡Ok!", "Puedes Registrar"));
                    return "RegistrarTt";
                }

            } else {
                // noTT denied
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "¡Error!", Mensajes.MSJ2_1b2));
            }
        } else { // empty field
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "¡Error!", Mensajes.MSJ2_1b1));
        }

        return "ConsultarTTReg";
    }

    public String verificarTtMod() throws IOException {

        clearValues();

        // non empty field
        if (RN2.RN2_1b1(noTt)) {
            // noTT accepted
            if (RN2.RN2_1b2(noTt)) {
                // verify if noTT alredy exist
                if (obtenerInstanciaTt()) {
                    // if exists: forward
                    return "ModificarEliminarTt";
                } else {
                    // TT not found
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "¡Advertencia!", "No Existe Registro"));
                }

            } else {
                // noTT denied
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "¡Error!", Mensajes.MSJ2_1b2));
            }
        } else { // empty field
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "¡Error!", Mensajes.MSJ2_1b1));
        }

        return "ConsultarTTMod";
    }

    public String add() {
        // convertimos la fecha a String
        String fechaTT = dateToString(fechaPresent);

        // verificamos que los campos estén llenos
        // titulo
        if (RN2.RN2_1b1(titulo)) {
            // al menos un autor
            if (RN2.RN2_1b1(autor1)) {
                // fecha de publicacion
                if (RN2.RN2_1b1(fechaTT)) {
                    // directores
                    if (RN2.RN2_1b1(director1)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "Procesando Solicitud", "Agregar TT"));
                        return registrarInstanciaTt();
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "¡Error!", Mensajes.MSJ2_1b1 + "Agrega "
                                        + "al director de TT"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "¡Error!", Mensajes.MSJ2_1b1 + "Agrega "
                                    + "la fecha de publicacion"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "¡Error!", Mensajes.MSJ2_1b1 + "Agrega "
                                + "al menos un autor."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "¡Error!", Mensajes.MSJ2_1b1 + "Agrega un titulo."));
        }
        return "RegistrarTt";
    }

    public String updateTT() {
        // convertimos la fecha a String
        String fechaTT = dateToString(fechaPresent);

        // verificamos que los campos estén llenos
        // titulo
        if (RN2.RN2_1b1(titulo)) {
            // al menos un autor
            if (RN2.RN2_1b1(autor1)) {
                // fecha de publicacion
                if (RN2.RN2_1b1(fechaTT)) {
                    // directores
                    if (RN2.RN2_1b1(director1)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                        "Procesando Solicitud", "Agregar TT"));
                        return actualizarInstanciaTt();
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "¡Error!", Mensajes.MSJ2_1b1 + "Agrega "
                                        + "al director de TT"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "¡Error!", Mensajes.MSJ2_1b1 + "Agrega "
                                    + "la fecha de publicacion"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "¡Error!", Mensajes.MSJ2_1b1 + "Agrega "
                                + "al menos un autor."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "¡Error!", Mensajes.MSJ2_1b1 + "Agrega un titulo."));
        }
        return "RegistrarTt";
    }

    private void clearValues() {
        String cleanField = "";

        setTitulo(cleanField);
        setAutor1(cleanField);
        setAutor2(cleanField);
        setAutor3(cleanField);
        setAutor4(cleanField);
        setAutor5(cleanField);
        setDirector1(cleanField);
        setDirector2(cleanField);
        setFechaPresent(null);
        setEstadoBorrado(1);
        tt = null;
    }

    public String getNoTt() {
        return noTt;
    }

    public void setNoTt(String noTt) {
        this.noTt = noTt;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaPresent() {
        return fechaPresent;
    }

    public void setFechaPresent(Date fechaPresent) {
        this.fechaPresent = fechaPresent;
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

    public String getAutor5() {
        return autor5;
    }

    public void setAutor5(String autor5) {
        this.autor5 = autor5;
    }

    public String getDirector1() {
        return director1;
    }

    public void setDirector1(String director1) {
        this.director1 = director1;
    }

    public String getDirector2() {
        return director2;
    }

    public void setDirector2(String director2) {
        this.director2 = director2;
    }

    public Integer getEstadoBorrado() {
        return estadoBorrado;
    }

    public void setEstadoBorrado(Integer estadoBorrado) {
        this.estadoBorrado = estadoBorrado;
    }

    public Tt getTt() {
        return tt;
    }

    public void setTt(Tt tt) {
        this.tt = tt;
    }

    public boolean isBorrar() {
        return borrar;
    }

    public void setBorrar(boolean borrar) {
        this.borrar = borrar;
    }
}


/*
+---------------------+-------------+------+-----+---------+-------+
| Field               | Type        | Null | Key | Default | Extra |
+---------------------+-------------+------+-----+---------+-------+
| noTT                | varchar(45) | NO   | PRI | NULL    |       |
| Elemento_idElemento | int(11)     | NO   | MUL | NULL    |       |
| Titulo              | varchar(45) | NO   |     | NULL    |       |
| FechaPresent        | date        | NO   |     | NULL    |       |
| Autor1              | varchar(95) | NO   |     | NULL    |       |
| Autor2              | varchar(45) | YES  |     | NULL    |       |
| Autor3              | varchar(45) | YES  |     | NULL    |       |
| Autor4              | varchar(45) | YES  |     | NULL    |       |
| Autor5              | varchar(45) | YES  |     | NULL    |       |
| Director1           | varchar(45) | NO   |     | NULL    |       |
| Director 2          | varchar(45) | YES  |     | NULL    |       |
| EstadoBorrado       | int(11)     | YES  |     | NULL    |       |
+---------------------+-------------+------+-----+---------+-------+
12 rows in set (1.02 sec)

 */
