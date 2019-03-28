package yummy.service;

import yummy.model.MyOrder;

import java.util.List;

public interface OrderService {

    public void createNewOrder(MyOrder myOrder);

    public void modifyOrder(MyOrder myOrder);

    public void deleteOrder(MyOrder myOrder);

    public List<MyOrder> getUnpaidOrder();

    public List<MyOrder> getDeliveredOrder();

    public List<MyOrder> getOrderByUser(int uid);

    public List<MyOrder> getOrderBySeller(String rid);

    public List<MyOrder> getAllOrder();

    public void cancelOrder(MyOrder myOrder);

    public MyOrder getOrderById(int id);

    public void deliverOrder(MyOrder order);

    public void confirmOrder(MyOrder order);
}
