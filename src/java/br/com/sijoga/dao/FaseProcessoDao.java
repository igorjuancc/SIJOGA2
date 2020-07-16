package br.com.sijoga.dao;

import br.com.sijoga.bean.FaseProcesso;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class FaseProcessoDao {
    public void cadastrarFaseProcesso(FaseProcesso faseProcesso) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.save(faseProcesso);                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao cadastrar nova fase do processo [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao cadastrar nova fase do processo [DAO]****", e);
        }
    }
    
    public FaseProcesso buscaFaseProcessoId(int id) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("SELECT f FROM FaseProcesso f WHERE f.id = :idFaseProcesso");
                select.setParameter("idFaseProcesso", id);
                FaseProcesso fase = (FaseProcesso) select.uniqueResult();
                return fase;
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar fase de processo por id [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar fase de processo por id [DAO]****", e);
        }
    }
    
    public void atualizarFaseProcesso(FaseProcesso fase) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                session.update(fase);
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao atualizar fase de processo [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao atualizar fase de processo [DAO]****", e);
        }
    }
}
