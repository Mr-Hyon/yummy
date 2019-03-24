package yummy.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yummy.model.Restaurant;
import yummy.model.SellerInfo;
import yummy.service.RestaurantService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    RestaurantService restaurantService;

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
}
