package yummy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.MenuDao;
import yummy.dao.OrderDao;
import yummy.dao.UserDao;
import yummy.model.Menu;
import yummy.model.MyOrder;
import yummy.model.User;
import yummy.service.OrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    MenuDao menuDao;

    @Autowired
    UserDao userDao;

    public void createNewOrder(MyOrder myOrder){
        String ids[] = myOrder.getMenus_id().split("#");
        String nums[] = myOrder.getMenus_num().split("#");
        for(int i=0;i<ids.length;i++){
            Menu sample = menuDao.getMenuById(Integer.parseInt(ids[i]));
            int minus_num = sample.getNum() - Integer.parseInt(nums[i]);
            sample.setNum(minus_num);
            menuDao.modifyMenu(sample);
        }
        orderDao.addOrder(myOrder);
    }

    public void modifyOrder(MyOrder myOrder){
        orderDao.modifyOrder(myOrder);
    }

    public void deleteOrder(MyOrder myOrder){
        orderDao.deleteOrder(myOrder);
    }

    public List<MyOrder> getUnpaidOrder(){
        List<MyOrder> all_My_order = orderDao.getAllOrder();
        List<MyOrder> result = new ArrayList<>();
        if(all_My_order != null){
            for(int i = 0; i< all_My_order.size(); i++){
                if(all_My_order.get(i).getState().equals("CREATED")){
                    result.add(all_My_order.get(i));
                }
            }
            return result;
        }
        else{
            return result;
        }
    }

    public List<MyOrder> getOrderByUser(int uid){
        List<MyOrder> all_My_order = orderDao.getAllOrder();
        List<MyOrder> result = new ArrayList<>();
        if(all_My_order != null){
            for(int i = 0; i< all_My_order.size(); i++){
                if(all_My_order.get(i).getUid() == uid){
                    result.add(all_My_order.get(i));
                }
            }
            return result;
        }
        else{
            return result;
        }
    }

    public List<MyOrder> getOrderBySeller(String rid){
        List<MyOrder> all_My_order = orderDao.getAllOrder();
        List<MyOrder> result = new ArrayList<>();
        if(all_My_order != null){
            for(int i = 0; i< all_My_order.size(); i++){
                if(all_My_order.get(i).getRid().equals(rid)){
                    result.add(all_My_order.get(i));
                }
            }
            return result;
        }
        else{
            return result;
        }
    }

    public void cancelOrder(MyOrder myOrder){
        String ids[] = myOrder.getMenus_id().split("#");
        String nums[] = myOrder.getMenus_num().split("#");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        myOrder.setEndTime(sdf.format(date));
        for(int i=0;i<ids.length;i++){
            Menu sample = menuDao.getMenuById(Integer.parseInt(ids[i]));
            int plus_num = sample.getNum() + Integer.parseInt(nums[i]);
            sample.setNum(plus_num);
            menuDao.modifyMenu(sample);
        }
        myOrder.setState("CANCELED");
        orderDao.modifyOrder(myOrder);
    }

    public MyOrder getOrderById(int id){
        return orderDao.getOrderById(id);
    }

    public void deliverOrder(MyOrder order){
        order.setState("DELIVERED");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setDeliverTime(sdf.format(date));
        orderDao.modifyOrder(order);
    }

    public void confirmOrder(MyOrder order){
        order.setState("DONE");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setEndTime(sdf.format(date));
        orderDao.modifyOrder(order);
        User user = userDao.getUserById(order.getUid());
        int alter_credit = user.getCredit() + (int)order.getPrice();
        user.setCredit(alter_credit);
        userDao.modifyUser(user);
    }

    public List<MyOrder> getDeliveredOrder(){
        List<MyOrder> all_My_order = orderDao.getAllOrder();
        List<MyOrder> result = new ArrayList<>();
        if(all_My_order != null){
            for(int i = 0; i< all_My_order.size(); i++){
                if(all_My_order.get(i).getState().equals("DELIVERED")){
                    result.add(all_My_order.get(i));
                }
            }
            return result;
        }
        else{
            return result;
        }
    }

    public List<MyOrder> getAllOrder(){
        List<MyOrder> all_My_order = orderDao.getAllOrder();
        return all_My_order;
    }
}
