package br.com.sijoga.dao;

import br.com.sijoga.bean.Pessoa;
import br.com.sijoga.exception.DaoException;
import br.com.sijoga.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


public class PessoaDao {
    public Pessoa buscarPessoaCpf(String cpf) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Pessoa WHERE cpf_pessoa = :cpf");
                select.setParameter("cpf", cpf);
                Pessoa pessoa = (Pessoa) select.uniqueResult();
                return pessoa;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar pessoa por CPF [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar pessoa por CPF [DAO]****", e);
        }
    }     
    
    public Pessoa buscarPessoaEmail(String email) throws DaoException {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Query select = session.createQuery("FROM Pessoa WHERE email_pessoa = :email");
                select.setParameter("email", email);
                Pessoa pessoa = (Pessoa) select.uniqueResult();
                return pessoa;                
            } finally {
                session.getTransaction().commit();
                session.close();
            }
        } catch (HibernateException e) {
            throw new DaoException("****Problema ao buscar pessoa por email [Hibernate]****", e);
        } catch (Exception e) {
            throw new DaoException("****Problema ao buscar pessoa por email [DAO]****", e);
        }
    }     
}
