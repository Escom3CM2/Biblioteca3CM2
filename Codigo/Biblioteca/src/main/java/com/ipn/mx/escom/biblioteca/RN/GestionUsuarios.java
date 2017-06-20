package com.ipn.mx.escom.biblioteca.RN;

import com.ipn.mx.escom.biblioteca.Pojos.HibernateUtil;
import com.ipn.mx.escom.biblioteca.Pojos.Lector;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class GestionUsuarios {
    static public boolean  RN414 (int id)
    {
        Session hibernateSession =HibernateUtil.getSessionFactory().openSession();
        Transaction t0=hibernateSession.beginTransaction();

        Query query = hibernateSession.createQuery("from Lector where idLector=:id");
        query.setParameter("id", id);
        t0.commit();
        Lector lector =(Lector)query.uniqueResult();
        return lector == null;
    }
}
