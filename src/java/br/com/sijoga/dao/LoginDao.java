package br.com.sijoga.dao;

import br.com.sijoga.bean.Advogado;
import br.com.sijoga.bean.Juiz;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class LoginDao {
    public Advogado loginAdvogado(Advogado advogado) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Advogado a WHERE a.email = :email AND a.senha = :senha");
                select.setParameter("email", advogado.getEmail());
                select.setParameter("senha", advogado.getSenha());
                advogado = (Advogado) select.uniqueResult();
                return advogado;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar login de usuário advogado [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar login de usuário advogado [DAO]****", e);
        }        
    }
    
    public Juiz loginJuiz(Juiz juiz) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Juiz j WHERE j.email = :email AND j.senha = :senha");
                select.setParameter("email", juiz.getEmail());
                select.setParameter("senha", juiz.getSenha());
                juiz = (Juiz) select.uniqueResult();
                return juiz;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar login de usuário juiz [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar login de usuário juiz [DAO]****", e);
        }        
    }
}
