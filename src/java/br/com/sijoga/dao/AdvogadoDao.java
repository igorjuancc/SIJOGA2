package br.com.sijoga.dao;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class AdvogadoDao {
    public void cadastrarAdvogado(Advogado advogado) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(advogado);                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar novo advogado [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar novo advogado [DAO]****", e);
        }
    } 
    
    public Advogado buscarAdvogadoOab(int regOab) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Advogado a WHERE a.registroOab = :regOab");
                select.setParameter("regOab", regOab);
                Advogado advogado = (Advogado) select.uniqueResult();
                return advogado;
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar advogado por OAB [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar advogado por OAB [DAO]****", e);
        }
    } 
    
    public List<Advogado> listaAdvogados() throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Advogado a ORDER BY a.nome");
                List<Advogado> advogados = select.list();
                return advogados;
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar lista de advogados [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar lista de advogados [DAO]****", e);
        }        
    }    
}
