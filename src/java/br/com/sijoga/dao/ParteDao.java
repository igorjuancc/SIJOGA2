package br.com.sijoga.dao;

import br.com.sijoga.bean.Parte;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class ParteDao {
    public void cadastrarParte(Parte parte) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(parte);                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo cliente [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo cliente [DAO]****", e);
        }
    }     
}
