package yummy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yummy.dao.RestaurantDao;
import yummy.model.Restaurant;
import yummy.model.SellerInfo;
import yummy.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService{

    @Autowired
    RestaurantDao restaurantDao;

    public void regist(Restaurant restaurant){
        restaurantDao.addRestaurant(restaurant);
    }

    public boolean login(String rid,String password){
        return restaurantDao.login(rid,password);
    }

    public List<Restaurant> getUncheckedRestaurant() {
        return restaurantDao.getUncheckedRestaurant();
    }

    public void AcceptNewSeller(String rid){
        Restaurant restaurant = restaurantDao.getRestaurantByRid(rid);
        restaurant.setisPassed(true);
        restaurantDao.modifyRestaurant(restaurant);
    }

    public void DenyNewSeller(String rid){
        Restaurant restaurant = restaurantDao.getRestaurantByRid(rid);
        restaurantDao.deleteRestaurant(restaurant);
    }

    public Restaurant getSellerByRid(String rid){
        return restaurantDao.getRestaurantByRid(rid);
    }

    public void addSellerInfo(SellerInfo sellerInfo){
        restaurantDao.addSellerInfo(sellerInfo);
    }

    public void AcceptInfoChange(String sid,String rid){
        Restaurant restaurant = restaurantDao.getRestaurantByRid(rid);
        SellerInfo sellerInfo = restaurantDao.getSellerInfoById(Integer.parseInt(sid));
        restaurant.setRname(sellerInfo.getRname());
        restaurant.setType(sellerInfo.getType());
        restaurant.setLocation(sellerInfo.getLocation());
        sellerInfo.setIsChecked(true);
        restaurantDao.modifyRestaurant(restaurant);
        restaurantDao.modifySellerInfo(sellerInfo);
    }

    public void DenyInfoChange(String sid,String rid){
        SellerInfo sellerInfo = restaurantDao.getSellerInfoById(Integer.parseInt(sid));
        restaurantDao.deleteSellerInfo(sellerInfo);
    }

    public List<SellerInfo> getUncheckedSellerInfo(){
        return restaurantDao.getUncheckedSellerInfo();
    }

    public List<Restaurant> searchByKeyword(String keyword) {
        if(keyword.equals(""))
            return restaurantDao.getAllSeller();
        else
            return restaurantDao.searchByKeyword(keyword);
    }
}
