package com.ipn.mx.escom.biblioteca.ManagedBeans.GestionUsuarios;


import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.primefaces.model.chart.PieChartModel;


@ManagedBean
@SessionScoped
public class Login implements Serializable {
    
    private Usuario usuario ;
    private int matricula;
    private String password;
    
    public Login() {

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    


    
    public String log()
    {

        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Usuario where password = :pass and matricula= :usn");
        consulta.setParameter("pass",password);
        consulta.setParameter("usn",matricula );
        consulta.setMaxResults(1);
        usuario =(Usuario)consulta.uniqueResult();
        
        if (usuario != null)
        {
        
         switch (usuario.getTipo()) {
                 case 1:
        return "Jefe"; //Pagina de jefe
                 case 2:
        return "Inventario"; //Pagina de inventario
                 case 3:
        return "Bibliotecario"; //Pagina de biblio
         case 4:
        return "Lector"; //Pagina de lector
                 default:
        return "login"; 
             }
        }

        else
        {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Usuario invalido!"));

        return "login";
        }
        
    }

  public String logOut()
  {
    FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    return "login";         
  }
  

}