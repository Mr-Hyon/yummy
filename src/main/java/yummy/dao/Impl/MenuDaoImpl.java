package yummy.dao.Impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import yummy.dao.MenuDao;
import yummy.model.Menu;

import java.util.List;

@Repository
public class MenuDaoImpl implements MenuDao {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public void addMenu(Menu menu){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.save(menu);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void modifyMenu(Menu menu){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.update(menu);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public void deleteMenu(Menu menu){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        session.delete(menu);
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    public List<Menu> getMenusByRid(String rid){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Menu> list = session.createCriteria(Menu.class).add(Restrictions.eq("rid",rid)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        return list;
    }

    public Menu getMenuById(int id){
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<Menu> list = session.createCriteria(Menu.class).add(Restrictions.eq("id",id)).list();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if(list!=null){
            return list.get(0);
        }
        else{
            return null;
        }
    }

}
