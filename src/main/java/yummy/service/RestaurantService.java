package yummy.service;

import yummy.model.Restaurant;
import yummy.model.SellerInfo;

import java.util.ArrayList;
import java.util.List;

public interface RestaurantService {

    public void regist(Restaurant restaurant);

    public boolean login(String rid,String password);

    public List<Restaurant> getUncheckedRestaurant();

    public void AcceptNewSeller(String rid);

    public void DenyNewSeller(String rid);

    public Restaurant getSellerByRid(String rid);

    public void addSellerInfo(SellerInfo sellerInfo);

    public void AcceptInfoChange(String sid,String rid);

    public void DenyInfoChange(String sid,String rid);

    public List<SellerInfo> getUncheckedSellerInfo();

    public List<Restaurant> searchByKeyword(String keyword);

    public int getSellerNum();

}
