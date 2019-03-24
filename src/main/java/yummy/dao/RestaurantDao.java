package yummy.dao;

import yummy.model.Restaurant;
import yummy.model.SellerInfo;

import java.util.List;

public interface RestaurantDao {

    public void addRestaurant(Restaurant restaurant);

    public void modifyRestaurant(Restaurant restaurant);

    public void deleteRestaurant(Restaurant restaurant);

    public boolean login(String rid,String password);

    public Restaurant getRestaurantById(int id);

    public Restaurant getRestaurantByRid(String rid);

    public List<Restaurant> getUncheckedRestaurant();

    public void addSellerInfo(SellerInfo sellerInfo);

    public void modifySellerInfo(SellerInfo sellerInfo);

    public void deleteSellerInfo(SellerInfo sellerInfo);

    public List<SellerInfo> getUncheckedSellerInfo();

    public SellerInfo getSellerInfoById(int id);

    public List<Restaurant> searchByKeyword(String keyword);

    public List<Restaurant> getAllSeller();
}
