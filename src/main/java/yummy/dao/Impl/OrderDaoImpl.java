package yummy.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import yummy.dao.OrderDao;
import yummy.model.MyOrder;

import java.util.List;


@Repository
public class OrderDaoImpl implements OrderDao{
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    private OrderDaoImpl(){}

    public void addOrder(MyOrder myOrder){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(myOrder);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void modifyOrder(MyOrder myOrder){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(myOrder);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteOrder(MyOrder myOrder){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(myOrder);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public List<MyOrder> getAllOrder(){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<MyOrder> list = session.createCriteria(MyOrder.class).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        return list;
    }

    public MyOrder getOrderById(int id){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<MyOrder> list = session.createCriteria(MyOrder.class).add(Restrictions.eq("id",id)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list != null && list.size()>0){
            return list.get(0);
        }
        else{
            return null;
        }
    }
}
