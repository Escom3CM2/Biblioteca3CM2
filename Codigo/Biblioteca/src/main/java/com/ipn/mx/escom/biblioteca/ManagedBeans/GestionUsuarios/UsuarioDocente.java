package com.ipn.mx.escom.biblioteca.ManagedBeans.GestionUsuarios;

import com.ipn.mx.escom.biblioteca.Pojos.Colonia;
import com.ipn.mx.escom.biblioteca.Pojos.Contacto;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;
import com.ipn.mx.escom.biblioteca.Pojos.Usuario;
import com.ipn.mx.escom.biblioteca.Pojos.Docente;
import com.ipn.mx.escom.biblioteca.Anexos.GestionUsuarios;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean
@SessionScoped

public class UsuarioDocente implements Serializable{
     private String numEmpleado;
     private String nombre;
     private String primerApellido;
     private String segundoApellido;
     private String curp;
     private Date fechaNac;


     private String calleNum;
     private String telCasa;
     private String telCelular;
     
     private String correo;
     private String password;
    private int semestre;
    private Docente docente;

    private String colonia, delegacion,cp;
    public UsuarioDocente() {
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
       numEmpleado = docente.getNumEmpleado();
       nombre= docente.getNombre();
       primerApellido  = docente.getPrimerApellido();
       segundoApellido = docente.getSegundoApellido();
       curp = docente.getCurp();
       
      Date fechaNac;
        
        this.docente = docente;
    }

    
    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCalleNum() {
        return calleNum;
    }

    public void setCalleNum(String calleNum) {
        this.calleNum = calleNum;
    }

    public String getTelCasa() {
        return telCasa;
    }

    public void setTelCasa(String telCasa) {
        this.telCasa = telCasa;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(String delegacion) {
        this.delegacion = delegacion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void add()
    {
        if ( GestionUsuarios.RN414( Integer.parseInt(numEmpleado) ) )
        {
          //Inicia conexion DB
            Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
            Transaction t0=hibernateSession.beginTransaction();
            //Obtiene estado,tipo y colonia(la colonia se obtiene por que no tiene catalogo)
           //  Estadoestudiante estadoestudiante =(Estadoestudiante)hibernateSession.load(Estadoestudiante.class,1);
            // Tipoestudiante tipoestudiante =(Tipoestudiante)hibernateSession.load(Tipoestudiante.class,1);
             Colonia colonia =(Colonia)hibernateSession.load(Colonia.class,1);

            //Crea los objetos Usuario, contacto, lector y luego se guarda al estudiante
            Usuario usuario = new Usuario(Integer.parseInt(numEmpleado),password,2);
            Contacto contacto = new Contacto(Integer.parseInt(numEmpleado), colonia,  calleNum,  telCasa,  telCelular,  correo) ;
            Lector lector= new Lector(Integer.parseInt(numEmpleado), contacto, usuario,0);


            Docente docente = new Docente(numEmpleado,  lector,  nombre,  primerApellido,  segundoApellido,  fechaNac,curp );

            //Datosacademicos datosacademicos = new Datosacademicos(Integer.parseInt(boleta), estudiante, semestre, "matutino" );

            //Se guardan en la base de datos
            hibernateSession.save(usuario);
            hibernateSession.save(contacto);
            hibernateSession.save(lector);
            hibernateSession.save(docente);
       
            t0.commit();

            //Se cierra sesion
            hibernateSession.close();      
            //Se agrega mensaje
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito", "Se registro exitosamente."));

        
        
        }
        else
        {
            //Lector ya registrado
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Reistro de lector ya existente en el catalogo."));

        }
        
         

                
    }
    public void Eliminar(Docente docente)
    {
        Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
        Transaction t0=hibernateSession.beginTransaction();

        Query query = hibernateSession.createQuery("from Lector where idLector=:b ");
        query.setParameter("b", Integer.parseInt(docente.getNumEmpleado()));
        t0.commit();
        
        Lector lector =(Lector)query.uniqueResult();
        if (lector != null)
        {
            lector.setEstadoBorrado(1);
        Transaction t2=hibernateSession.beginTransaction();
            hibernateSession.merge(lector);
            
            t2.commit();
        }
        hibernateSession.close();      
        
    }
   /* public String Modificar(Estudiante e)
    {
        System.out.println("sdas" + e.getNombre() + "asda" + nombre);

        Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
        Transaction t0=hibernateSession.beginTransaction();
        Query query = hibernateSession.createQuery("from Estudiante where boleta=:b ");
        query.setParameter("b", this.boleta);
        t0.commit();
        Estudiante estudiante =(Estudiante)query.uniqueResult();
        if (estudiante != null)
        {
            estudiante.setNombre(nombre);
        Transaction t2=hibernateSession.beginTransaction();
            hibernateSession.update(estudiante);
            
            t2.commit();
        }
        hibernateSession.close();    
        return "ListaAlumnos";

    }*/
    public List<Docente> Docentes()
    {
        Session hibernateSession;

        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        hibernateSession.beginTransaction();
        Query consulta=hibernateSession.createQuery("from Docente ");
        //consulta.setParameter("b", 0);
        hibernateSession.getTransaction().commit();
        List lista= consulta.list();
        hibernateSession.close();
        return lista;
    }
 
    public String Telefono(Docente docente)
    {
       int idLector = docente.getLector().getIdLector();
       
       Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
        Transaction t0=hibernateSession.beginTransaction();

        Query query = hibernateSession.createQuery("from Lector where IdLector=:e ");
        query.setParameter("e", idLector);
        t0.commit();
        Lector lector =(Lector)query.uniqueResult();
        
        if (lector != null)
            return lector.getContacto().getTelCasa();
        else
        return "No encontrado";

    }
    public String Apellidos(String p , String s)
    {
        return p + " " + s;
    }
    public void Set(Docente d)
    {
        this.docente = d;
    }
    
}
