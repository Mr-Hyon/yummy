package yummy.dao;

import yummy.model.MyOrder;

import java.util.List;

public interface OrderDao {

    public void addOrder(MyOrder myOrder);

    public void modifyOrder(MyOrder myOrder);

    public void deleteOrder(MyOrder myOrder);

    public List<MyOrder> getAllOrder();

    public MyOrder getOrderById(int id);
}
