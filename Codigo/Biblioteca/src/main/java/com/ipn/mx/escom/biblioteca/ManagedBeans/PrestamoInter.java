package com.ipn.mx.escom.biblioteca.ManagedBeans;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;


import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ipn.mx.escom.biblioteca.Anexos.Input;
import com.ipn.mx.escom.biblioteca.Anexos.PrestamoB;
import com.ipn.mx.escom.biblioteca.Pojos.Docente;
import com.ipn.mx.escom.biblioteca.Pojos.Estudiante;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;


@ManagedBean 
@SessionScoped
public class PrestamoInter {

	private String nombreLect;
	private  int idLect;
	private List<SelectItem> type;
	private String selectedTipo;
	private  HashMap<PrestamoB.Material,Input> type2;
	private String idVal;
	private int ejem;
	private int tt;
	private int audio;
	private List<ItemMaterial> material;
	private String observ;
	private Lector l;
	private String matricula;
	

	@PostConstruct
	public void init(){
		//initDatos();
		initTipo();		
		
	}
	
	public void initDatos(){
		System.out.println("En initDatos  idLector:" + idLect );
		
		//int idLector=22;
		if((""+idLect).matches("[0-9]*")){
			Session session = HibernateUtil.getSessionFactory().openSession();
			Estudiante  e= (Estudiante) session.createCriteria(Estudiante.class).add(Restrictions.eq("lector.idLector", idLect)).uniqueResult();
			
			if(e!=null){
				System.out.println("Es Estudiante!");
				idLect=e.getLector().getIdLector();
				nombreLect=e.getPrimerApellido() + " "+e.getSegundoApellido()+ " "+e.getNombre();
				l=e.getLector();
				matricula=e.getBoleta();
				
			}else{
				Docente d = (Docente) session.createCriteria(Docente.class).add(Restrictions.eq("lector.idLector", idLect)).uniqueResult();
				if(d!=null){
					System.out.println("Es Docente");
					idLect=d.getLector().getIdLector();
					nombreLect=d.getPrimerAp() + " "+d.getSegundoAp() + " " + d.getNombre();
					l=d.getLector();
					matricula=d.getNumEmpleado();
					
				}else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Lector Inexistente.") );
					
				}
			}
			
			ejem=0;
			tt=0;
			audio=0;
			material=new ArrayList<>();
			session.flush();
			session.close();
		}else{
			 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Formato no Valido!") );
		}
		
		
		
		
	}
	
	public void clear(){
		ejem=0;
		tt=0;
		audio=0;
		material=new ArrayList<>();
		l=null;
		nombreLect="";
		idLect=0;
		selectedTipo="";
		idVal="";
		observ="";
		matricula="";
		
	}
	
	private void initTipo(){
		type2=PrestamoB.getTipoIn();
		System.out.println("SIze :"+type2.size());
		type=new ArrayList<>();
		for (Map.Entry<PrestamoB.Material,Input> entry : type2.entrySet())   
		    type.add(new SelectItem(entry.getValue().getName(),entry.getKey().toString()));
		
	
		
	}
	
	public void registrar(){
	
		
		
		if(!selectedTipo.equals("") && idVal.matches("[0-9]*") ){
			PrestamoB.Material selected= PrestamoB.Material.valueOf(selectedTipo);
			Input in = type2.get(selected);
			System.out.println(in.getName());
			if(in.validar(idVal)){
				System.out.println("Validado!");
				int r=-1;
				switch(selected){
						case Libro:
								r= PrestamoB.validarPrestamoLibro(idLect,Integer.parseInt(idVal),ejem+1);
								 if(r==2){
									 if(ejem<PrestamoB.MAXLIBROPPS ){
										if(!PrestamoB.hasMaterial(Integer.parseInt(idVal),PrestamoB.Material.Libro,material)){
											 System.out.println("Agreaga Libro");
											 material.add(PrestamoB.addLibro(Integer.parseInt(idVal),observ));
											 ejem++;
										}else{
											 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Material ya Registrado!") );
										}
									 }else 
										 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Maximo de Libro por Prestamo Alcanzado!") );
								 }else if(r==1){
									 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Libro No Disponible para su Prestamo!") );

								 }else if(r==0){
									 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Numero maximo de Libros Alcanzado!") );

								 }
								
								
							break;
							
						case TT:
							r=PrestamoB.validarPrestamoTT(idLect,Integer.parseInt(idVal),tt+1);
							if(r==2){
								
								 if(tt+audio<PrestamoB.MAXMaterialPPS){
									 if(!PrestamoB.hasMaterial(Integer.parseInt(idVal),PrestamoB.Material.TT,material)){
										 material.add(PrestamoB.addTT(Integer.parseInt(idVal)));
										 tt++;
									  }else{
										  FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Material ya Registrado!") );
									  }
								 }else 
									 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Maximo de Material por Prestamo Alcanzado!") );
								
							}else if(r==1){
								 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "TT no Disponible para su prestamo!") );

							}else if(r==0){
								FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Numero maximo de TT Alcanzado!") );
							}
							
							
								
							break;
						
						case AudioVisual:
							r=PrestamoB.validarPrestamoAudioV(idLect,Integer.parseInt(idVal), audio+1);
							if(r==2){
								
								if(tt+audio<PrestamoB.MAXMaterialPPS){
									 if(!PrestamoB.hasMaterial(Integer.parseInt(idVal),PrestamoB.Material.AudioVisual,material)){
										 material.add(PrestamoB.addAudioV(Integer.parseInt(idVal)));
										 tt++;
									  }else{
										  FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Material ya Registrado!") );
									  }
								 }else 
									 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Maximo de Material por Prestamo Alcanzado!") );
								
								
							}else if(r==1){
								 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "AudioVisual no Disponible para su Prestamo!") );
							}else if(r==0){
								 FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Número Maximo de Material AudioVisual Prestado!") );
							}
							
							break;
						
				
				}
				
				
				//PrestamoB.getNumPrestamos(idLect);
				
				
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "Formato de Identificador Incorrecto!") );

			}
			
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error", "1.Ingrese Tipo De Busqueda.\n 2.Ingrese formato Adecuado!") );
		}
		
		
	}
	
	
	public void borrarIDTipo(int id,PrestamoB.Material m){
		int size = material.size();
		for(int i=0;i<size;i++){
			if(material.get(i).getId()==id && material.get(i).getTipo()==m){
				material.remove(i);
				break;
			}
		}
		switch(m){
		case Libro:
			 ejem--;
			break;
		case TT:
			tt--;
			break;
		case AudioVisual:
				audio--;
			break;
		}
		
				
		
	}
	
	
	public void registrarPrestamos(){
		if(material.size()>0){
			List<ItemMaterial> ejem=new ArrayList<>();
			List<ItemMaterial> elem= new ArrayList<>();
			for (int it=0;it<material.size();it++){
				if(material.get(it).getTipo()==PrestamoB.Material.Libro)
					ejem.add(material.get(it));
				else 
					elem.add(material.get(it));
				//material.remove(it);
			}
			if(ejem.size()>0)
				PrestamoB.registrarPrestamo2(ejem.get(0).getFechaPrestamo(),ejem.get(0).getFechaEntrega(),l ,ejem );
			if(elem.size()>0)
				PrestamoB.registrarPrestamoOtros(elem.get(0).getFechaPrestamo(),elem.get(0).getFechaEntrega(), l, elem);
			material=new ArrayList<>();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Mensaje",  "Prestamo Existoso!") );
			
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Error",  "No Hay Material para Registrar!") );
			
		}
	}
	
	

	public String getNombreLect() {
		return nombreLect;
	}

	public void setNombreLect(String nombreLect) {
		this.nombreLect = nombreLect;
	}

	public int getIdLect() {
		return idLect;
	}

	public void setIdLect(int idLect) {
		this.idLect = idLect;
	}

	public List<SelectItem> getType() {
		return type;
	}

	public void setType(List<SelectItem> type) {
		this.type = type;
	}

	public String getSelectedTipo() {
		return selectedTipo;
	}

	public void setSelectedTipo(String selectedTipo) {
		this.selectedTipo = selectedTipo;
	}

	public String getIdVal() {
		return idVal;
	}

	public void setIdVal(String idVal) {
		this.idVal = idVal;
	}

	public List<ItemMaterial> getMaterial() {
		return material;
	}

	public void setMaterial(List<ItemMaterial> material) {
		this.material = material;
	}

	public String getObserv() {
		return observ;
	}

	public void setObserv(String observ) {
		this.observ = observ;
	}
	
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	
	public Lector getL() {
		return l;
	}

	public void setL(Lector l) {
		this.l = l;
	}

	/***************************************UTIL*************************************************/
	public String setformatoFecha(Date fecha , PrestamoB.Material m){
		if(m== PrestamoB.Material.TT | m == PrestamoB.Material.AudioVisual)
			return  new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(fecha);
		return new SimpleDateFormat("dd-M-yyyy").format(fecha);
	}
	
	
	
	
	
	
	
	
	
}
