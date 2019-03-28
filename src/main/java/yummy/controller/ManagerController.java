package yummy.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yummy.model.MyOrder;
import yummy.model.Restaurant;
import yummy.model.SellerInfo;
import yummy.model.User;
import yummy.service.OrderService;
import yummy.service.RestaurantService;
import yummy.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/login")
    public String login(){
        return "ManagerLogin";
    }

    @RequestMapping("/checkNewSeller")
    public String checkNewSeller() {return "ManagerCheckNewSeller";}

    @RequestMapping("/checkSellerInfo")
    public String checkSellerInfo() {return "ManagerCheckSellerInfo";}

    @RequestMapping("/financePage")
    public String financePage() { return "ManagerFinancePage"; }

    @RequestMapping("/login.action")
    public void loginAction(@RequestParam("staffid") String id,
                            @RequestParam("password") String password,
                            HttpServletResponse response,
                            HttpServletRequest request)throws IOException{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(id!=null && password!=null){
            if(id.equals("admin") && password.equals("admin")){
                out.print("<script language=\"javascript\">alert('ACCESS CONFIRMED');window.location.href='/yummy/manager/checkNewSeller';</script>");
            }
            else{
                out.print("<script language=\"javascript\">alert('ERROR INPUT');window.location.href='/yummy/manager/login';</script>");
            }
        }
        else{
            out.print("<script language=\"javascript\">alert('ERROR INPUT');window.location.href='/yummy/manager/login';</script>");
        }
    }

    @RequestMapping(value="/getUncheckedSellerInfo",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getUncheckedSellerInfo( HttpServletResponse response,
                                        HttpServletRequest request){
        response.setContentType("text/html;charset=utf-8");
        List<Restaurant> list = new ArrayList<>();
        list = restaurantService.getUncheckedRestaurant();
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        if(list!=null){
            obj.addProperty("num",list.size());
            array.add(obj);

            for(int i=0;i<list.size();i++){
                Restaurant sample = list.get(i);
                JsonObject temp = new JsonObject();
                temp.addProperty("rid",sample.getRid());
                temp.addProperty("rname",sample.getRname());
                temp.addProperty("type",sample.getType());
                temp.addProperty("location",sample.getLocation());
                array.add(temp);
            }
        }
        else{
            obj.addProperty("num",0);
            array.add(obj);
        }
        return array.toString();
    }

    @RequestMapping(value="/AcceptNewSeller",produces="application/text; charset=utf-8")
    @ResponseBody
    public String AcceptNewSeller(@RequestParam("rid") String rid,HttpServletResponse response,
                                  HttpServletRequest request){
        restaurantService.AcceptNewSeller(rid);
        return "success";
    }

    @RequestMapping(value="/DenyNewSeller",produces="application/text; charset=utf-8")
    @ResponseBody
    public String DenyNewSeller(@RequestParam("rid") String rid,HttpServletResponse response,
                                  HttpServletRequest request){
        restaurantService.DenyNewSeller(rid);
        return "success";
    }

    @RequestMapping(value="/getUncheckedModifyRequest",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getModifyRequest(HttpServletResponse response,
                                   HttpServletRequest request){
        List<SellerInfo> list = new ArrayList<>();
        list = restaurantService.getUncheckedSellerInfo();
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        if(list!=null){
            obj.addProperty("num",list.size());
            array.add(obj);

            for(int i=0;i<list.size();i++){
                SellerInfo sample = list.get(i);
                Restaurant restaurant = restaurantService.getSellerByRid(sample.getRid());
                JsonObject temp = new JsonObject();
                temp.addProperty("sid",String.valueOf(sample.getId()));
                temp.addProperty("rid",sample.getRid());
                temp.addProperty("origin_name",restaurant.getRname());
                temp.addProperty("origin_type",restaurant.getType());
                temp.addProperty("origin_location",restaurant.getLocation());
                temp.addProperty("alter_name",sample.getRname());
                temp.addProperty("alter_type",sample.getType());
                temp.addProperty("alter_location",sample.getLocation());
                array.add(temp);
            }
        }
        else{
            obj.addProperty("num",0);
            array.add(obj);
        }
        return array.toString();
    }

    @RequestMapping(value="/AcceptInfoChange",produces="application/text; charset=utf-8")
    @ResponseBody
    public String AcceptInfoChange(@RequestParam("id") String id,
                                   @RequestParam("rid") String rid,HttpServletResponse response,
                                  HttpServletRequest request){
        restaurantService.AcceptInfoChange(id,rid);
        return "success";
    }

    @RequestMapping(value="/DenyInfoChange",produces="application/text; charset=utf-8")
    @ResponseBody
    public String DenyInfoChange(@RequestParam("id") String id,
                                 @RequestParam("rid") String rid,HttpServletResponse response,
                                HttpServletRequest request){
        restaurantService.DenyInfoChange(id,rid);
        return "success";
    }

    @RequestMapping(value="/getFinancialData",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getFinancialData(HttpServletResponse response,
                                   HttpServletRequest request){
        List<MyOrder> list = orderService.getAllOrder();
        int complete_order_num = 0;
        int refund_order_num = 0;
        int user_num = userService.getUserNum();
        int seller_num = restaurantService.getSellerNum();
        HashMap<String,Integer> user_hm = new HashMap<>();
        HashMap<String,Integer> seller_hm = new HashMap<>();
        double yummy_income = 0.0;
        JsonObject obj = new JsonObject();
        if(list.size()>0){
            //有订单记录
            for(int i=0;i<list.size();i++){
                if(list.get(i).getState().equals("DONE")){
                    complete_order_num ++;
                    yummy_income += list.get(i).getPrice();
                    if(!user_hm.containsKey(String.valueOf(list.get(i).getUid()))){
                        user_hm.put(String.valueOf(list.get(i).getUid()),1);
                    }
                    else{
                        user_hm.put(String.valueOf(list.get(i).getUid()),user_hm.get(String.valueOf(list.get(i).getUid())) + 1);
                    }

                    if(!seller_hm.containsKey(list.get(i).getRid())){
                        seller_hm.put(list.get(i).getRid(),1);
                    }
                    else{
                        seller_hm.put(list.get(i).getRid(),seller_hm.get(list.get(i).getRid()) + 1);
                    }
                }
                else if(list.get(i).getState().equals("REFUND")){
                    refund_order_num++;
                    Long diff = calcTimeDiff(list.get(i).getPayTime(),list.get(i).getEndTime());
                    if(diff <= 60 * 1000){
                        //do nothing
                    }
                    else if(diff <= 10 * 60 * 1000){
                        yummy_income += list.get(i).getPrice()/4;
                    }
                    else{
                        yummy_income += list.get(i).getPrice()/2;
                    }
                }
            }
            obj.addProperty("user_num",user_num);
            obj.addProperty("seller_num",seller_num);
            obj.addProperty("complete_order_num",String.valueOf(complete_order_num));
            obj.addProperty("refund_order_num",String.valueOf(refund_order_num));
            obj.addProperty("total_income",String.valueOf(yummy_income));
            String max_uid = getMaxKey(user_hm);
            String max_rid = getMaxKey(seller_hm);
            User user = userService.getUserById(Integer.parseInt(max_uid));
            Restaurant restaurant = restaurantService.getSellerByRid(max_rid);
            obj.addProperty("most_common_user",user.getUname());
            obj.addProperty("most_welcome_seller",restaurant.getRname());
        }
        else{
            //无订单记录
            obj.addProperty("user_num",user_num);
            obj.addProperty("seller_num",seller_num);
            obj.addProperty("complete_order_num","0");
            obj.addProperty("refund_order_num","0");
            obj.addProperty("total_income","0");
            obj.addProperty("most_common_user","null");
            obj.addProperty("most_welcome_seller","null");
        }
        return obj.toString();
    }

    private Long calcTimeDiff(String formal,String later) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long diff = null;
        try {
            diff = sdf.parse(later).getTime() - sdf.parse(formal).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 返回值为毫秒
        return diff;
    }

    private String getMaxKey(HashMap<String , Integer> hashMap) {
        String key   = "0";
        int value = 0;
        String flagKey   = "0";
        int flagValue = 0;
        Set<Map.Entry<String,Integer>> entrySet = hashMap.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            //key value 代表每轮遍历出来的值
            key   = entry.getKey();
            value = entry.getValue();
            if(flagValue < value ) {
                //flagKey flagValue 当判断出最大值是将最大值赋予该变量
                flagKey   = key;
                flagValue = value;
            }
        }
        return flagKey;
    }
}
