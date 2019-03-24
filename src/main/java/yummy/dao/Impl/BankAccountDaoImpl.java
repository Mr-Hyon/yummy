package yummy.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import yummy.dao.BankAccountDao;
import yummy.model.BankAccount;

import java.util.List;

@Repository
public class BankAccountDaoImpl implements BankAccountDao {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    private BankAccountDaoImpl(){}

    public void addBankAccount(BankAccount bankAccount){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(bankAccount);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void modifyBankAccount(BankAccount bankAccount){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(bankAccount);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteBankAccount(BankAccount bankAccount){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(bankAccount);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public BankAccount getBankAccountById(int id){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<BankAccount> list = session.createCriteria(BankAccount.class).add(Restrictions.eq("id",id)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list != null && list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public BankAccount getBankAccountByUser(int uid){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<BankAccount> list = session.createCriteria(BankAccount.class).add(Restrictions.eq("uid",uid)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list != null && list.size()>0)
            return list.get(0);
        else
            return null;
    }

    public BankAccount getBankAccountByAccountId(String accountId){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<BankAccount> list = session.createCriteria(BankAccount.class).add(Restrictions.eq("accountid",accountId)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list != null && list.size()>0)
            return list.get(0);
        else
            return null;
    }
}
