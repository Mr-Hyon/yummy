package yummy.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yummy.model.*;
import yummy.service.MenuService;
import yummy.service.OrderService;
import yummy.service.RestaurantService;
import yummy.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @RequestMapping("/register")
    public String register(){
        return "SellerRegister";
    }

    @RequestMapping("/main")
    public String mainPage() { return "SellerMainMenu"; }

    @RequestMapping("/customizePage")
    public String customizePage(){ return "SellerCustomizeMenu"; }

    @RequestMapping("/modifyInfoPage")
    public String modifyInfoPage(){ return "SellerModifyInfo";}

    @RequestMapping("/checkOrderPage")
    public String checkOrderPage() { return "SellerCheckOrder"; }

    @RequestMapping("/financePage")
    public String financePage() { return "SellerFinancePage"; }

    @RequestMapping("/regist.action")
    public void regist(@RequestParam("rname") String rname,
                       @RequestParam("password") String password,
                       @RequestParam("type") String type,
                       @RequestParam("location") String location,
                       @RequestParam("area") String area,
                       HttpServletResponse response,
                       HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(rname!=null && password!=null && type!=null &&location!=null && area!=null){
            String address = area+"#"+location;
            Restaurant res = new Restaurant();
            res.setisPassed(false);
            res.setLocation(address);
            res.setMenu("");
            res.setPassword(password);
            res.setType(type);
            res.setRname(rname);
            res.setRid("");
            restaurantService.regist(res);
            out.print("<script language=\"javascript\">alert('注册信息已提交，待审核中，您的唯一编号为"+res.getRid()+"');window.location.href='/yummy/home/index';</script>");
        }
        else{
            out.print("<script language=\"javascript\">alert('信息不完整');window.location.href='/yummy/seller/register';</script>");
        }
    }

    @RequestMapping(value="/seller.login",produces="application/text; charset=utf-8")
    @ResponseBody
    public String loginAction(String rid,String password,HttpServletResponse response, HttpServletRequest request){
        boolean result = restaurantService.login(rid,password);
        if(result){
            request.getSession().setAttribute("SellerLoginInfo",rid);
            return "success";
        }
        else{
            return "fail";
        }
    }

    @RequestMapping(value="/seller.logout",produces="application/text; charset=utf-8")
    public void SellerLogout(HttpServletResponse response,
                               HttpServletRequest request) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("SellerLoginInfo");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">window.location.href='/yummy/home/index';</script>");
    }

    @RequestMapping(value="/getCurrentSeller",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getCurrentSeller(HttpServletResponse response,
                                   HttpServletRequest request){
        if(request.getSession().getAttribute("SellerLoginInfo") == null){
            return null;
        }
        else{
            String rid = (String)request.getSession().getAttribute("SellerLoginInfo");
            Restaurant restaurant = restaurantService.getSellerByRid(rid);
            JsonObject obj = new JsonObject();
            obj.addProperty("rid",rid);
            obj.addProperty("rname",restaurant.getRname());
            obj.addProperty("type",restaurant.getType());
            obj.addProperty("location",restaurant.getLocation());
            obj.addProperty("menu",restaurant.getMenu());
            return obj.toString();
        }
    }

    @RequestMapping(value="/addNewMenu",produces="application/text; charset=utf-8")
    @ResponseBody
    public String addNewMenu(@RequestParam("menu_name")String menu_name,
                             @RequestParam("menu_type")String menu_type,
                             @RequestParam("menu_desc")String menu_desc,
                             @RequestParam("menu_startDate")String menu_startDate,
                             @RequestParam("menu_endDate")String menu_endDate,
                             @RequestParam("menu_num")String menu_num,
                             @RequestParam("menu_price")String menu_price,
                             @RequestParam("rid")String seller_rid,
                             HttpServletResponse response,
                             HttpServletRequest request){
        Menu menu= new Menu();
        menu.setDesp(menu_desc);
        menu.setName(menu_name);
        menu.setType(menu_type);
        menu.setStartDate(menu_startDate);
        menu.setEndDate(menu_endDate);
        menu.setNum(Integer.parseInt(menu_num));
        menu.setPrice(Double.parseDouble(menu_price));
        menu.setRid(seller_rid);
        menu.setImage("");
        menuService.addMenu(menu);
        System.out.println("hello");
        return "success";
    }

    @RequestMapping(value="/getSellerMenu",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getSellerMenu(@RequestParam("rid") String rid,
                                HttpServletResponse response,
                                HttpServletRequest request){
        List<Menu> list = new ArrayList<>();
        list = menuService.getMenusByRid(rid);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        if(list!=null){
            obj.addProperty("num",list.size());
            array.add(obj);
            for(int i=0;i<list.size();i++){
                Menu sample = list.get(i);
                JsonObject temp = new JsonObject();
                temp.addProperty("mid",String.valueOf(sample.getId()));
                temp.addProperty("name",sample.getName());
                temp.addProperty("desp",sample.getDesp());
                temp.addProperty("type",sample.getType());
                temp.addProperty("num",sample.getNum());
                temp.addProperty("price",sample.getPrice());
                temp.addProperty("startDate",sample.getStartDate());
                temp.addProperty("endDate",sample.getEndDate());
                array.add(temp);
            }
        }
        else{
            obj.addProperty("num",0);
            array.add(obj);
        }
        return array.toString();
    }

    @RequestMapping(value="/DeleteMenu",produces="application/text; charset=utf-8")
    @ResponseBody
    public String DeleteSellerMenu(@RequestParam("mid") String mid,
                                HttpServletResponse response,
                                HttpServletRequest request){
        Menu menu = menuService.getMenuById(Integer.parseInt(mid));
        if(menu == null){
            return "fail";
        }
        else{
            menuService.DeleteMenu(menu);
            return "success";
        }
    }

    @RequestMapping(value="/ChangeInfo",produces="application/text; charset=utf-8")
    @ResponseBody
    public String ChangeInfo(@RequestParam("rid") String rid,
                             @RequestParam("rname") String rname,
                             @RequestParam("type") String type,
                             @RequestParam("location") String location,
                             HttpServletResponse response,
                             HttpServletRequest request){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setIsChecked(false);
        sellerInfo.setLocation(location);
        sellerInfo.setRid(rid);
        sellerInfo.setRname(rname);
        sellerInfo.setType(type);
        restaurantService.addSellerInfo(sellerInfo);
        return "success";
    }

    @RequestMapping(value="/getSellerOrder",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getSellerOrder(@RequestParam("rid") String rid,
                               HttpServletResponse response,
                               HttpServletRequest request){
        List<MyOrder> list = orderService.getOrderBySeller(rid);
        Collections.reverse(list);
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();
        if(list.size() > 0){
            obj.addProperty("num",list.size());
            arr.add(obj);
            for(int i=0;i<list.size();i++){
                JsonObject temp = new JsonObject();
                temp.addProperty("id",String.valueOf(list.get(i).getId()));
                temp.addProperty("uid",String.valueOf(list.get(i).getUid()));
                temp.addProperty("rid",list.get(i).getRid());
                temp.addProperty("startTime",list.get(i).getStartTime());
                temp.addProperty("endTime",list.get(i).getEndTime());
                temp.addProperty("payTime",list.get(i).getPayTime());
                temp.addProperty("deliverTime",list.get(i).getDeliverTime());
                temp.addProperty("tgtAddress",list.get(i).getTgtAddress());
                temp.addProperty("menus_id",list.get(i).getMenus_id());
                temp.addProperty("menus_num",list.get(i).getMenus_num());
                temp.addProperty("state",list.get(i).getState());
                temp.addProperty("price",String.valueOf(list.get(i).getPrice()));
                if(list.get(i).getState().equals("REFUND")){
                    if(calcTimeDiff(list.get(i).getPayTime(),list.get(i).getEndTime()) <= 60 * 1000){
                        temp.addProperty("refund_price",list.get(i).getPrice());
                    }
                    else if(calcTimeDiff(list.get(i).getPayTime(),list.get(i).getEndTime()) <= 10 * 60 * 1000){
                        temp.addProperty("refund_price",list.get(i).getPrice()/2);
                    }
                    else{
                        temp.addProperty("refund_price",0);
                    }
                }

                User user = userService.getUserById(list.get(i).getUid());
                temp.addProperty("uname",user.getUname());

                String[] ids = list.get(i).getMenus_id().split("#");
                String names = "";
                for(int j=0;j<ids.length;j++){
                    String name = menuService.getMenuById(Integer.parseInt(ids[j])).getName();
                    names += name;
                    if(j!=ids.length-1){
                        names += "#";
                    }
                }
                temp.addProperty("menus_name",names);

                arr.add(temp);
            }
        }
        else{
            obj.addProperty("num",0);
            arr.add(obj);
        }
        return arr.toString();
    }

    @RequestMapping(value="/Deliver.action",produces="application/text; charset=utf-8")
    @ResponseBody
    public String DeliverAction(@RequestParam("oid") String oid,
                              HttpServletResponse response,
                              HttpServletRequest request){
        MyOrder order = orderService.getOrderById(Integer.parseInt(oid));
        if(order != null){
            if(order.getState().equals("PAID")){
                orderService.deliverOrder(order);
                return "success";
            }
            else{
                return "cannot_deliver";
            }
        }
        else{
            return "404";
        }
    }

    @RequestMapping(value="/getSellerFinanceInfo",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getSellerFinanceInfo(@RequestParam("rid") String rid,
                                   HttpServletResponse response,
                                   HttpServletRequest request){
        List<MyOrder> list = orderService.getOrderBySeller(rid);
        int complete_order_num = 0;
        int refund_order_num = 0;
        HashMap<String,Integer> hm = new HashMap<>();
        double origin_income = 0.0;
        double alter_income = 0.0;
        if(list.size()>0){
            //有订单记录
            for(int i=0;i<list.size();i++){
                if(list.get(i).getState().equals("DONE")){
                    complete_order_num ++;
                    origin_income += list.get(i).getPrice();
                    alter_income += list.get(i).getPrice()/2;
                    if(!hm.containsKey(String.valueOf(list.get(i).getUid()))){
                        hm.put(String.valueOf(list.get(i).getUid()),1);
                    }
                    else{
                        hm.put(String.valueOf(list.get(i).getUid()),hm.get(String.valueOf(list.get(i).getUid())) + 1);
                    }
                }
                else if(list.get(i).getState().equals("REFUND")){
                    refund_order_num++;
                    Long diff = calcTimeDiff(list.get(i).getPayTime(),list.get(i).getEndTime());
                    if(diff <= 60 * 1000){

                    }
                    else if(diff <= 10 * 60 * 1000){

                    }
                    else{

                    }
                }
            }
        }
        else{
            //无订单记录

        }
        return "";
    }

    public Long calcTimeDiff(String formal,String later) {
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
}
