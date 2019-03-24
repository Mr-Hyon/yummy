package yummy.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import yummy.dao.RestaurantDao;
import yummy.model.Restaurant;

import org.hibernate.Transaction;
import yummy.model.SellerInfo;
import yummy.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    private RestaurantDaoImpl(){}

    public void addRestaurant(Restaurant restaurant){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(restaurant);
        int id = restaurant.getId();
        String rid = String.valueOf(id);
        while(rid.length()<7){
            rid = "0" + rid;
        }
        restaurant.setRid(rid);
        session.update(restaurant);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void modifyRestaurant(Restaurant restaurant){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(restaurant);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteRestaurant(Restaurant restaurant){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(restaurant);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public boolean login(String rid,String password){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Restaurant> list = session.createCriteria(Restaurant.class).add(Restrictions.eq("rid",rid)).list();
        if(list.size()>0){
            if(list.get(0).getPassword().equals(password) && list.get(0).getisPassed()){
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

    public Restaurant getRestaurantById(int id){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Restaurant> list = session.createCriteria(Restaurant.class).add(Restrictions.eq("id",id)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list.size()>0) return list.get(0);
        else return null;
    }

    public Restaurant getRestaurantByRid(String rid){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Restaurant> list = session.createCriteria(Restaurant.class).add(Restrictions.eq("rid",rid)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list.size()>0) return list.get(0);
        else return null;
    }

    public List<Restaurant> getUncheckedRestaurant(){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Restaurant> list = session.createCriteria(Restaurant.class).add(Restrictions.eq("isPassed",false)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        return list;
    }

    public void addSellerInfo(SellerInfo sellerInfo){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(sellerInfo);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void modifySellerInfo(SellerInfo sellerInfo){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(sellerInfo);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteSellerInfo(SellerInfo sellerInfo){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(sellerInfo);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public List<SellerInfo> getUncheckedSellerInfo(){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<SellerInfo> list = session.createCriteria(SellerInfo.class).add(Restrictions.eq("isChecked",false)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        return list;
    }

    public SellerInfo getSellerInfoById(int id){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<SellerInfo> list = session.createCriteria(SellerInfo.class).add(Restrictions.eq("id",id)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list.size()>0) return list.get(0);
        else return null;
    }

    public List<Restaurant> searchByKeyword(String keyword){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Restaurant> result=session.createQuery("from Restaurant as a where a.rname like :name").setParameter("name",keyword).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        return result;
    }

    public List<Restaurant> getAllSeller(){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Restaurant> result = session.createCriteria(Restaurant.class).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        return result;
    }
}
