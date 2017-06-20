package com.ipn.mx.escom.biblioteca.Anexos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ipn.mx.escom.biblioteca.ManagedBeans.ItemMaterial;
import com.ipn.mx.escom.biblioteca.Pojos.Audiovisual;
import com.ipn.mx.escom.biblioteca.Pojos.DPresot;
import com.ipn.mx.escom.biblioteca.Pojos.DPrestamo;
import com.ipn.mx.escom.biblioteca.Pojos.Ejemplar;
import com.ipn.mx.escom.biblioteca.Pojos.Elemento;
import com.ipn.mx.escom.biblioteca.Pojos.Estadoelemnto;
import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;

import com.ipn.mx.escom.biblioteca.Pojos.Prestamo;
import com.ipn.mx.escom.biblioteca.Pojos.Prestamootros;
import com.ipn.mx.escom.biblioteca.Pojos.Tt;


public class PrestamoB {
	
	private static final int LENDING =1;
	private static  final int MAXNUMEJEM=5;
	//private static final int MAXNUMOTRO=3;
	private static final int MAXNAuV=2;
	private static final int MAXNTT=1;
	private static final byte EJEMAVAL=1;
	private static final byte EJEMNOAVAL=0;
	private static final String ELEMDIS="Disponible";
	private static final int DAYPRESTAMO=7;
	private static final int HOURPRESTAMOTT=1;
	public static final int MAXLIBROPPS=3;
	public static final int MAXMaterialPPS=3;
	public static final  int OTROSNOAVAL  =2;
	
	
	public enum Material{
		Libro,
		TT,
		AudioVisual
	}
	/***********************************Disponibilidad de Material**************************************************/
	
	public static  int[] getNumPrestamos(int idLector) throws RuntimeException{
		List<Prestamo> prestamos = new ArrayList<>();
		int acc=0;
		int auV=0;
		int tt=0;
		Session session = HibernateUtil.getSessionFactory().openSession();
            prestamos  = session.createCriteria(Prestamo.class).add(Restrictions.and(Restrictions.eq("lector.idLector",idLector),Restrictions.eq("estadoPr", LENDING))).list();
            for(Prestamo  p:prestamos){
            	acc+= session.createCriteria(DPrestamo.class).add(Restrictions.eq("prestamo.idPrestamo",p.getIdPrestamo())).list().size();	
            }  
            List<Prestamootros> presOt=session.createCriteria(Prestamootros.class).add(Restrictions.and(Restrictions.eq("lector.idLector",idLector),Restrictions.eq("edoPr", LENDING))).list();;
            for(Prestamootros po : presOt){
            	Iterator it= po.getDPresots().iterator();
            	while(it.hasNext()){
            		DPresot dp=(DPresot)it.next();
            		auV+=dp.getElemento().getAudiovisuals().size();
            		tt+=dp.getElemento().getTts().size();
            				
            				
            	}
            }
            
            System.out.println("Numero de Ejempalres :"+ acc + " #Audio:"+auV+ " tt:"+tt );
            
            
        return new int[]{acc,auV,tt}; //Ejemplares,AudioVisuales,tt    	
	}
	
	public static boolean disponibilidadLibro(int idEjem){  // Comprueba la disponiblidad del Material 
		     	Session session = HibernateUtil.getSessionFactory().openSession();
				Ejemplar e = (Ejemplar) session.createCriteria(Ejemplar.class).add(Restrictions.eq("idEjemplar",idEjem)).uniqueResult();
				if (e==null)
					return false;
				
				session.flush();
				session.close();
				return e.getDisponibilidad()==EJEMAVAL;
	}
	
	public static boolean disponibilidadElementoAudio(int idElem){
				Session session = HibernateUtil.getSessionFactory().openSession();
				Audiovisual e = (Audiovisual) session.createCriteria(Audiovisual.class).add(Restrictions.eq("elemento.idElemento",idElem)).uniqueResult();
				if(e==null)
					return false;
				String d= e.getElemento().getEstadoelemnto().getEdoElem();
				session.flush();
				session.close();
				System.out.println(d);
				return d.equalsIgnoreCase(ELEMDIS);
				
	}


	public static boolean disponibilidadElementoTT(int idElem){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Tt e = (Tt) session.createCriteria(Tt.class).add(Restrictions.eq("elemento.idElemento",idElem)).uniqueResult();
		if(e==null)
			return false;
		String d= e.getElemento().getEstadoelemnto().getEdoElem();
		session.flush();
		session.close();
		System.out.println(d);
		return d.equalsIgnoreCase(ELEMDIS);		
}
	
	
	public static  HashMap<PrestamoB.Material , Input> getTipoIn(){
		HashMap<PrestamoB.Material,Input> tp = new HashMap<>();
		tp.put(PrestamoB.Material.Libro,new Input("Libro","[0-9]*"));
		//tp.put(PrestamoB.Material.TT,new Input("TT","[0-9]*"));
		//tp.put(PrestamoB.Material.AudioVisual,new Input("AudioVisual","[0-9]*"));
		
		
		return tp;
		
	}
	
	
	/**************************************Reglas de Negocio*****************************************************************************/
	public static int validarPrestamoLibro(int idLect,int idEjem, int num){
		int prestamosA[]=getNumPrestamos(idLect);
		if(prestamosA[0]+num>MAXNUMEJEM)
			return 0; //Excede el Numero maximo de Prestamos de Libros
		
		if(!disponibilidadLibro(idEjem))
			return 1;  // No esta disponible el Libro
		
		return 2;	  //El libro Puede ser Prestado 
		
	}
	
	
	public static int validarPrestamoTT(int idLect, int idElem ,int num){
		int prestamosA[]=getNumPrestamos(idLect);
		System.out.println("Numero de TT:" +prestamosA[2] );
		if(prestamosA[2]+num>MAXNTT)
			return 0;
		if (!disponibilidadElementoTT(idElem))
			return 1;
		
		return 2;

	}
	
	public static int validarPrestamoAudioV(int idLect,int idElem , int mum){
		int prestamosA[]=getNumPrestamos(idLect);
		if(prestamosA[1]+1>MAXNAuV)
			return 0;
		if(!disponibilidadElementoAudio(idElem))
			return 1;
		return 2;
	}
/*****************************************Agregar libro a La lista para Prestamo***************************************************/	
    public static ItemMaterial addLibro(int idEjem, String observaciones){
    	Session session = HibernateUtil.getSessionFactory().openSession();
		Ejemplar e = (Ejemplar) session.createCriteria(Ejemplar.class).add(Restrictions.eq("idEjemplar",idEjem)).uniqueResult();
		Date now = new Date();
		session.flush();
    	session.close();
		return new ItemMaterial(idEjem,e.getLibro().getTitulo(),addDate(now,DAYPRESTAMO,Calendar.DAY_OF_MONTH),now,PrestamoB.Material.Libro,observaciones,e);
    }
    
    public static ItemMaterial  addTT(int idElem ){
    	//Guarda Elemento
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Tt tt =(Tt) session.createCriteria(Tt.class).add(Restrictions.eq("elemento.idElemento",idElem)).uniqueResult();
       	Date now = new Date();
    	session.flush();
    	session.close();
		return new ItemMaterial(idElem,tt.getTitulo(),addDate(now,HOURPRESTAMOTT,Calendar.HOUR_OF_DAY),now,PrestamoB.Material.TT,"",tt.getElemento());
    }
    
    public static ItemMaterial  addAudioV(int idElem ){
    	//Guarda Elemento
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Audiovisual a =(Audiovisual) session.createCriteria(Audiovisual.class).add(Restrictions.eq("elemento.idElemento",idElem)).uniqueResult();
    	Date now = new Date();
    	session.flush();
    	session.close();
		return new ItemMaterial(idElem,a.getTitulo(),addDate(now,HOURPRESTAMOTT,Calendar.HOUR_OF_DAY),now,PrestamoB.Material.AudioVisual,"",a.getElemento());
    }
    
    public static boolean hasMaterial(int id , Material tipo, final List<ItemMaterial> m){
    	for(ItemMaterial i : m){
    		if (i.getId()==id & i.getTipo()==tipo){
    			return true;
    		}
    	}
    	return false;
   
    }
    /*************************************Registro BD****************************************/
   
    
    
    public static  DPrestamo registrarLibro(ItemMaterial i ,final Prestamo p ,int id){
       	Ejemplar e = (Ejemplar) i.getObj();
    	DPrestamo dp = new DPrestamo(id,e,p);
    	//System.out.println("Antes de Registrar!!!");
    	updateLibroEstado(e.getIdEjemplar(),EJEMNOAVAL ,i.getObservaciones());
      	System.out.println("Actualizacion de estado!");
    	register(dp);
    	//registerUp(dp,e); // Asociar Ejemplar a Prestamo 
    	//e.setDisponibilidad(EJEMNOAVAL);
    	//update(e);// actualizacion Estado de Ejemplar	
    	//update(e);
    	
  
    	return dp;
    }
    
    public static void registrarPrestamoOtros(Date inicio , Date fin ,Lector l ,List<ItemMaterial> m){
    	Prestamootros po = new Prestamootros(getUltimoDPretamoOtros()+1,l,inicio,fin,LENDING,inicio,fin,null);
    	register(po);
    	int lastDp=getUltimoDPretamoOtros();
    	for(ItemMaterial i :m){
    		registrarElemPrestamo(i,po,++lastDp);
    	}
    	System.out.println("Elemento Rgistrado");

    }
    
    public static void registrarElemPrestamo (ItemMaterial m,Prestamootros p,int id){
    	Elemento e = (Elemento)m.getObj();
    	DPresot dp = new DPresot(id ,e,p);
    	updateElemMaterial(e.getIdElemento(),OTROSNOAVAL); // Cambiar estado Elemento
    	register(dp);
    }
    
    public static void registrarPrestamo2(Date inicio , Date fin ,Lector l ,List<ItemMaterial> m){
    	Prestamo p = new Prestamo(getUltimoPrestamo()+1,l,inicio,fin,LENDING);
    	register(p);
    	int lastL=getUltimoDPrestamo()+1;
    	for (ItemMaterial i : m){
    		registrarLibro(i,p,lastL++);
    	}
    	System.out.println("Pass ot register  last:"+lastL);
    }
    
    
	
    
    /********************************Busqueda Ultimo ID***********************************/
    private  static int getUltimoDPrestamo(){
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	int i;
    	try{
    	Criteria c = session.createCriteria(DPrestamo.class);
    	c.addOrder(Order.desc("idDPrestamo"));
    	c.setMaxResults(1);
    	i =((DPrestamo)c.uniqueResult()).getIdDPrestamo();
    	}finally {
            session.flush();
            session.close();
        }
    	return i;
    }
    
    private  static int getUltimoPrestamo(){
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Criteria c ;
    	int i;
    	try{
    	c= session.createCriteria(Prestamo.class);
    	c.addOrder(Order.desc("idPrestamo"));
    	c.setMaxResults(1);
    	i=((Prestamo)c.uniqueResult()).getIdPrestamo();
    	}finally {
            session.flush();
            session.close();
        }
    	
    	return i;
    	
    }
    
    private static int  getUltimoDPretamoOtros(){
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Criteria c;
    	int id;
    	try{
	    	c = session.createCriteria(DPresot.class);
	    	c.addOrder(Order.desc("idDPresOt"));
	    	c.setMaxResults(1);
	    	id=((DPresot)c.uniqueResult()).getIdDPresOt();
	    	
	   } finally {
           session.flush();
           session.close();
       }
    	return id;
    }
    
  /*  private static int  getUltimoPretamoOtros(){

    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Criteria c;
    	int id;
    	try{
	    	c = session.createCriteria(Prestamootros.class);
	    	c.addOrder(Order.desc("idPrestamoOtros"));
	    	c.setMaxResults(1);
	    	id=((DPresot)c.uniqueResult()).getIdDPresOt();
	    	
	   } finally {
           session.flush();
           session.close();
       }
    	return id;
    }*/
    
    

    
    /************************************************************************************************/
	private static Date addDate(Date fecha , int valor ,int tipo ){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(tipo, valor);
		return calendar.getTime();
	}
	

	/**********************************Coneccion BD*****************************************/
	private static void register(Object o) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(o);
            //session.getTransaction().commit();
            trns.commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
        	
           // session.flush();
            session.close();
        }
    }
	
	
	private static void updateLibroEstado(int id,byte edo,String obs){
		Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Ejemplar ejem = 
	                    (Ejemplar)session.get(Ejemplar.class,id);
	         System.out.println("idInUpdate:"+ejem.getIdEjemplar());
	         tx.commit();
	         tx = session.beginTransaction();
	         ejem.setDisponibilidad(edo);
	         if(!obs.equals(""))
	        	 ejem.setObservaciones(obs);
			 session.update(ejem); 
	         tx.commit();
	         System.out.println("doCommit Update");
	         
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	}

	private static void updateElemMaterial(int idElem,int idedo){
		Session session = HibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         Elemento elem = 
	                    (Elemento)session.get(Elemento.class,idElem);
	         System.out.println("idInUpdateElem:"+elem.getIdElemento());
	         Estadoelemnto edo=(Estadoelemnto)session.get(Estadoelemnto.class, idedo);
	         System.out.println("Actualizacion de eStado a :" + edo.getEdoElem());
	         tx.commit();
	         tx = session.beginTransaction();
	         elem.setEstadoelemnto(edo);	
	         session.update(elem); 
	         tx.commit();
	         System.out.println("doCommit UpdateElem");
	         
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
		
		
	}
}
