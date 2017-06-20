package com.ipn.mx.escom.biblioteca.ManagedBeans.GestionCredenciales;



import com.ipn.mx.escom.biblioteca.Pojos.Contacto;
import com.ipn.mx.escom.biblioteca.Pojos.Credencial;
import com.ipn.mx.escom.biblioteca.Pojos.Docente;
import com.ipn.mx.escom.biblioteca.Pojos.Estudiante;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;
import com.ipn.mx.escom.biblioteca.Pojos.Usuario;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
@ManagedBean
@SessionScoped
public class Credencials implements Serializable {
    public static final String LogoIPN="http://localhost:8080/Biblioteca/resources/LogoIPN.JPG";
    public static final String LogoESCOM="http://localhost:8080/Biblioteca/resources/LogoESCOM.jpg";
  
    private StreamedContent streamedContent;
    private int boleta;

    public int getBoleta() {
        return boleta;
    }

    public void setBoleta(int boleta) {
        this.boleta = boleta;
    }
    
    
    public void creaPDF(int matricula)
    {
        String nombre = getName(matricula);
        String idLector=Integer.toString(matricula);
        String telefono=getTelefono(matricula);
        Document document = new Document();

          try {
        //----------------------------------

            OutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            
            document.addTitle("Credencial:"+idLector+" Alumno:"+nombre);
              /******************FRENTE DE LA CREDENCIAL***************/
            //creamos 1 fila
            PdfPTable tabla=new PdfPTable(8); //creamos una tabla de 8 columnas
            PdfPCell celdaIPN=createImageCell(LogoIPN);
            celdaIPN.setBorder(0); //quita todo los bordes
            celdaIPN.setBorder(Rectangle.TOP | Rectangle.LEFT); //agrega los bordes TOP y LEFT; no quiero que se vea el borde de la derecha
            celdaIPN.setRowspan(2); //el logo tomará 2 renglones
            tabla.addCell(celdaIPN); //agregamos el objeto de tipo celda al documento
            
            PdfPCell celdaEscuela=new PdfPCell(new Paragraph("Instituto Politécnico Nacional"));
            celdaEscuela.setHorizontalAlignment(Element.ALIGN_CENTER); //centramos el texto
            celdaEscuela.setBorder(0); //quitamos todos los bordes *para que no haya una línea divisora entre IPN y Biblioteca de ...
            celdaEscuela.setBorder(Rectangle.TOP); //agregamos el borde de arriba
            celdaEscuela.setColspan(5); //tomará 5 columnas este texto
            tabla.addCell(celdaEscuela);
            
            PdfPCell celdaESCOM=createImageCell(LogoESCOM);
            celdaESCOM.setRowspan(2);
            celdaESCOM.setBorder(0);
            celdaESCOM.setBorder(Rectangle.TOP);
            celdaESCOM.setVerticalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celdaESCOM);
            tabla.addCell("AGO-DIC");
            
            //segunda Fila
            PdfPCell celdaBiblioteca=new PdfPCell(new Paragraph("Biblioteca de ESCOM\n"+"Credencial de Lector"));
            celdaBiblioteca.setBorder(0);
            celdaBiblioteca.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaBiblioteca.setColspan(5);
            tabla.addCell(celdaBiblioteca);
            tabla.addCell("ENE-JUN");
            
            //tercera fila
            tabla.addCell("Teléfono");
            PdfPCell celdaTelefono=new PdfPCell(new Paragraph(telefono));
            celdaTelefono.setColspan(6);
            tabla.addCell(celdaTelefono);
            tabla.addCell("AGO-DIC");
            
            //cuarta fila
            PdfPCell celdatituloNombre=new PdfPCell(new Paragraph("Nombre Completo"));
            celdatituloNombre.setBackgroundColor(BaseColor.LIGHT_GRAY); //le ponemos un color de fondo
            celdatituloNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdatituloNombre.setBorder(0);
            celdatituloNombre.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
            celdatituloNombre.setColspan(5);
            tabla.addCell(celdatituloNombre);
            PdfPCell celdaFoto=new PdfPCell(new Paragraph(""));
            celdaFoto.setBorder(0);
            celdaFoto.setColspan(2);
            tabla.addCell(celdaFoto);
            tabla.addCell("ENE-JUN");
            
            //quinta fila
            PdfPCell celdaNombre=new PdfPCell(new Paragraph(nombre));
            celdaNombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaNombre.setBorder(0);
            celdaNombre.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            celdaNombre.setColspan(5);
            tabla.addCell(celdaNombre);
            tabla.addCell(celdaFoto);
            tabla.addCell("AGO-DIC");
            
            //sexta Fila
            PdfPCell celdaBoleta=new PdfPCell(new Paragraph("Boleta de lector:\n"+idLector));
            celdaBoleta.setColspan(5);
            celdaBoleta.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celdaBoleta);
            celdaFoto.setBorder(Rectangle.BOTTOM);
            tabla.addCell(celdaFoto);
            tabla.addCell("ENE-JUN");
            document.add(tabla);            
            
            /******************REVERSO DE LA CREDENCIAL***************/
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            PdfPTable reverso=new PdfPTable(1);
            PdfPCell celdaReverso=new PdfPCell(new Paragraph(" "));
            celdaReverso.setBorder(0);
            celdaReverso.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
            reverso.addCell(celdaReverso);
            celdaReverso.setBorder(0);
            celdaReverso.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            reverso.addCell(celdaReverso);
            celdaReverso.setBorder(0);
            celdaReverso.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            reverso.addCell(celdaReverso);
            
            PdfPCell celdaFirmaLector=new PdfPCell(new Paragraph("Firma del interesado"));
            celdaFirmaLector.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celdaFirmaLector.setHorizontalAlignment(Element.ALIGN_CENTER);
            reverso.addCell(celdaFirmaLector);
            
            PdfPCell celdaCompromiso=new PdfPCell(new Paragraph("ME COMPROMETO A RESPETAR EL REGLAMENTO \n Y POLÍTICAS DE LA BIBLIOTECA"));
            celdaCompromiso.setHorizontalAlignment(Element.ALIGN_CENTER);
            reverso.addCell(celdaCompromiso);
            celdaReverso.setBorder(0);
            celdaReverso.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
            reverso.addCell(celdaReverso);
            celdaReverso.setBorder(0);
            celdaReverso.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            reverso.addCell(celdaReverso);
            celdaReverso.setBorder(0);
            celdaReverso.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            reverso.addCell(celdaReverso);
            PdfPCell celdaDirector=new PdfPCell(new Paragraph("Firma del director del campus\n"));
            celdaDirector.setHorizontalAlignment(Element.ALIGN_CENTER);
            reverso.addCell(celdaDirector);
            document.add(reverso);
            document.close();            
            out.close();
            
            InputStream in =new ByteArrayInputStream(((ByteArrayOutputStream)out).toByteArray());
         
            streamedContent = new DefaultStreamedContent(in, "application/pdf");
        //-------
     
        } catch (Exception e) {
        }
        
    }
    
    public String GenerarCredencial(int matricula)
    {
        creaPDF(matricula);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Credencial generada con exito.", "Credencial generada con exito."));

        if (getCredencial(matricula) == null)
        {
        Calendar calendario = new GregorianCalendar();
        Credencial credencial = new Credencial();
        credencial.setIdCredencial(matricula);
        credencial.setLector( getLector(matricula) );
        credencial.setFechaEmision( calendario.getTime() );
        
          Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
          Transaction t0=hibernateSession.beginTransaction();
            
          hibernateSession.save(credencial);
          t0.commit();

          //Se cierra sesion
          hibernateSession.close();    
        }
            return "VisualizarCredencial";   
            
    }
    
        public String actualizarCredencial()
    {
        int matricula = boleta;
        creaPDF(matricula);
        Credencial c = getCredencial(matricula);
        if (c != null)
        {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Credencial generada con exito.", "Credencial generada con exito."));

        Calendar calendario = new GregorianCalendar();
        c.setFechaEmision( calendario.getTime() );
        
          Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
          Transaction t0=hibernateSession.beginTransaction();
            
          hibernateSession.merge(c);
          t0.commit();

          //Se cierra sesion
          hibernateSession.close();
                      return "VisualizarCredencial";        

        }
        else
        {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error el lector no existe.", "Error el lector no existe."));
            return "Renovar";
        }
    }
   
    //==================================================================
    public StreamedContent getStreamedContent() {
            return streamedContent;
        
     }
    //==================================================================
    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
    //=====================================================================
    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img,true);
        return cell;
    }
    
    public String getName(int matricula)
    {
        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Estudiante where boleta = :mat");
        consulta.setParameter("mat",Integer.toString(matricula));
        consulta.setMaxResults(1);
        Estudiante e =(Estudiante)consulta.uniqueResult();
        if(e==null)
        {
            consulta=hibernateSession.createQuery("from Docente where numEmpleado = :mat");
            consulta.setParameter("mat",Integer.toString(matricula));
            consulta.setMaxResults(1);
            Docente d =(Docente)consulta.uniqueResult();
            if(d==null)
                return "";
            else
                return  d.getNombre() + " " + d.getPrimerAp() + " " + d.getSegundoAp();

        }
        else
            return e.getNombre() +" " + e.getPrimerApellido() + " " + e.getSegundoApellido();
    }
    
    
    public Lector getLector(int matricula)
    {
        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Lector where idLector = :mat");
        consulta.setParameter("mat",matricula);
        consulta.setMaxResults(1);
        Lector e =(Lector)consulta.uniqueResult();
        return e;
    }
    
    
    
    public String getTelefono(int matricula)
    {
        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Contacto where idContacto = :mat");
        consulta.setParameter("mat",matricula);
        consulta.setMaxResults(1);
        Contacto c =(Contacto)consulta.uniqueResult();
        if (c!=null)
         return c.getTelCasa();
        else
            return "";
    }
    public Credencial getCredencial(int matricula)
    {
        Session hibernateSession;
        hibernateSession=HibernateUtil.getSessionFactory().openSession(); 
        Query consulta=hibernateSession.createQuery("from Credencial where idCredencial = :mat");
        consulta.setParameter("mat",matricula);
        consulta.setMaxResults(1);
        return (Credencial)consulta.uniqueResult();
    }
}