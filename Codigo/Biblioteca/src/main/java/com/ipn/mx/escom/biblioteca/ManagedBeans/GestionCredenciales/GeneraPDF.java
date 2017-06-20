package com.ipn.mx.escom.biblioteca.ManagedBeans.GestionCredenciales;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneraPDF {
    public static final String LogoIPN="LogoIPN.jpg";
    public static final String LogoESCOM="LogoESCOM.jpg";
    
    public static void main(String[] args) {
        GeneraPDF pdf=new GeneraPDF();
        pdf.creaPDF("HERNÁNDEZ FLORES LUIS Rojas","1234567890","55223344");
    }
    
    public static void creaPDF(String nombre,String idLector,String telefono)
    {
        Document document=new Document();
        try{
            PdfWriter.getInstance(document,new FileOutputStream(idLector+".pdf")); //el nombre con que se guardará el pdf
            document.open();           
            
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
        } catch (FileNotFoundException ex) {
            System.out.println("Error al crear archivo");
            System.exit(-1);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(GeneraPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static PdfPCell createImageCell(String path) throws DocumentException, IOException {
        Image img = Image.getInstance(path);
        PdfPCell cell = new PdfPCell(img,true);
        return cell;
    }
}
