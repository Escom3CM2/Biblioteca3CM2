/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.escom.biblioteca.ManagedBeans;

import com.ipn.mx.escom.biblioteca.daos.PrestamointerDAO;
import com.ipn.mx.escom.biblioteca.Pojos.Prestamointer;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author boa
 */
@ManagedBean
@RequestScoped
public class PrestamointerService {
    
    private String filter_type;
    private String filter_val;
    private List<Prestamointer> prestamointer_list;
    
      /**
     * Creates a new instance of PrestamointerService
     */
    public PrestamointerService() {
    }
    

    public String getFilter_type() {
        return filter_type;
    }

    public void setFilter_type(String filter_type) {
        this.filter_type = filter_type;
    }

    public String getFilter_val() {
        return filter_val;
    }

    public void setFilter_val(String filter_val) {
        this.filter_val = filter_val;
    }

    public List<Prestamointer> getPrestamointer_list() {
        return prestamointer_list;
    }

    public void setPrestamointer_list(List<Prestamointer> prestamoInter_list) {
        this.prestamointer_list = prestamoInter_list;
    }

  
    public void prestamosby(){
        PrestamointerDAO prestamodao = new PrestamointerDAO();
        if(filter_val.equals("")){
             prestamointer_list = prestamodao.prestamosList();
             return;
        }
        switch (filter_type) {
            case "usuario":
                break;
            case "folio":
                {
                    prestamointer_list = prestamodao.getPrestamoById(filter_val);
                    break;
                }
            case "titulo":
                {
                    prestamointer_list = prestamodao.getPrestamoByTitulo(filter_val);
                    break;
                }
            case "escuela":
                prestamointer_list = prestamodao.getPrestamoByEscuela(filter_val);
                break;
            default:
                break;
        }
        if(prestamointer_list == null){
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error",  "No se encontró ningún campo") );
        }       
    }
    
}
