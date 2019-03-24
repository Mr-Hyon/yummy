package yummy.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import yummy.dao.UserDao;
import yummy.model.User;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    private UserDaoImpl(){}

    public void addUser(User user){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void modifyUser(User user){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteUser(User user){
        user.setIsValid(false);
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public boolean login(String email,String password){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<User> list = session.createCriteria(User.class).add(Restrictions.eq("email",email)).list();
        if(list.size()>0){
            if(list.get(0).getPassword().equals(password) && list.get(0).getIsValid()){
                transaction.commit();
                session.close();
                sessionFactory.close();
                return true;
            }
            else{
                transaction.commit();
                session.close();
                sessionFactory.close();
                return false;
            }
        }
        else{
            transaction.commit();
            session.close();
            sessionFactory.close();
            return false;
        }
    }

    public User getUserById(int id){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<User> list = session.createCriteria(User.class).add(Restrictions.eq("id",id)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list.size()>0) return list.get(0);
        else return null;
    }

    public User getUserByEmail(String email){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<User> list = session.createCriteria(User.class).add(Restrictions.eq("email",email)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list.size()>0) return list.get(0);
        else return null;
    }
}
