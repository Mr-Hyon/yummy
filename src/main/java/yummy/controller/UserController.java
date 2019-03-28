package yummy.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import yummy.model.*;
import yummy.service.*;
import yummy.util.MailUtil;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/client")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    @Autowired
    BankAccountService bankAccountService;

    @RequestMapping("/register")
    public String register(){
        return "ClientRegister";
    }

    @RequestMapping("/main")
    public String mainPage() { return "ClientMainMenu"; }

    @RequestMapping("/modifyInfoPage")
    public String modifyInfoPage() { return "ClientModifyInfo"; }

    @RequestMapping("/searchSellerPage")
    public String searchSellerPage() { return "ClientSearchRestaurant"; }

    @RequestMapping("/observeMenuPage")
    public String observeMenuPage() { return "ClientObserveMenu"; }

    @RequestMapping("/checkOrderPage")
    public String checkOrderPage() { return "ClientCheckOrder"; }

    @RequestMapping("/bindBankAccountPage")
    public String bindBankAccountPage() { return "ClientBindBankAccount"; }

    @RequestMapping(value="/sendvcode.action",produces="application/text; charset=utf-8")
    @ResponseBody
    public String SendIdCode(String email, HttpServletResponse response, HttpServletRequest request) throws IOException,AddressException,MessagingException{
        response.setCharacterEncoding("utf-8");
        if(userService.isExist(email)){
            return "fail";
        }
        else{
            int vcode = (int)(Math.random()*100000);
            String text = Integer.toString(vcode);
            request.getSession().setAttribute("vcode",text);
            MailUtil mailUtil = new MailUtil();
            mailUtil.sendMail(email,text);
            return "success";
        }
    }

    @RequestMapping("/regist.action")
    public void regist(@RequestParam("email") String email,
                       @RequestParam("uname") String uname,
                       @RequestParam("contact") String contact,
                       @RequestParam("password") String password,
                       @RequestParam("vcode") String vcode,
                       @RequestParam("area1") String area1,
                       @RequestParam("address1") String address1,
                       @RequestParam("area2") String area2,
                       @RequestParam("address2") String address2,
                       @RequestParam("area3") String area3,
                       @RequestParam("address3") String address3,
                       HttpServletResponse response,
                       HttpServletRequest request) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(email!=null && uname!=null && contact!=null && password!=null && vcode!=null && area1!=null && address1!=null && request.getSession().getAttribute("vcode")!=null
        && !email.equals("") && !uname.equals("") && !contact.equals("") && !password.equals("") && !vcode.equals("") && !area1.equals("") && !address1.equals("")){
            String address = area1+"#"+address1;
            if(!area2.equals("") && !address2.equals("")) address += ";"+area2+"#"+address2;
            if(!area3.equals("") && !address3.equals("")) address += ";"+area3+"#"+address3;
            if(!request.getSession().getAttribute("vcode").equals(vcode)){
                out.print("<script language=\"javascript\">alert('验证码错误!');window.location.href='/yummy/client/register';</script>");
            }
            else{
                request.getSession().removeAttribute("vcode");
                User user = new User();
                user.setIsValid(true);
                user.setCredit(0);
                user.setUname(uname);
                user.setContact(contact);
                user.setEmail(email);
                user.setPassword(password);
                user.setAddress(address);
                boolean result = userService.regist(user);
                if(result)
                    out.print("<script language=\"javascript\">alert('注册成功');window.location.href='/yummy/home/index';</script>");
                else
                    out.print("<script language=\"javascript\">alert('邮箱已被注册或不可用');window.location.href='/yummy/client/register';</script>");
            }
        }
        else{
            out.print("<script language=\"javascript\">alert('信息不完整');window.location.href='/yummy/client/register';</script>");
        }
    }

    @RequestMapping(value = "/client.login", produces="application/text; charset=utf-8")
    @ResponseBody
    public String loginAction(String email,String password,HttpServletResponse response, HttpServletRequest request){
        boolean result = userService.login(email,password);
        if(result){
            request.getSession().setAttribute("ClientLoginInfo",email);
            return "success";
        }
        else{
            return "fail";
        }
    }

    @RequestMapping(value="/client.logout",produces="application/text; charset=utf-8")
    public void ClientLogOut(HttpServletResponse response, HttpServletRequest request) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        request.getSession().removeAttribute("ClientLoginInfo");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">window.location.href='/yummy/home/index';</script>");
    }

    @RequestMapping(value="/getCurrentClient",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getCurrentClient(HttpServletResponse response,
                                   HttpServletRequest request){
        if(request.getSession().getAttribute("ClientLoginInfo") == null){
            return null;
        }
        else{
            String email = (String)request.getSession().getAttribute("ClientLoginInfo");
            User user = userService.getUserByEmail(email);
            JsonObject obj = new JsonObject();
            obj.addProperty("id",String.valueOf(user.getId()));
            obj.addProperty("email",user.getEmail());
            obj.addProperty("address",user.getAddress());
            obj.addProperty("uname",user.getUname());
            obj.addProperty("contact",user.getContact());
            obj.addProperty("credit",String.valueOf(user.getCredit()));
            return obj.toString();
        }
    }

    @RequestMapping(value="/ChangeInfo",produces="application/text; charset=utf-8")
    @ResponseBody
    public String changeInfo(@RequestParam("id") String id,
                             @RequestParam("name") String name,
                             @RequestParam("contact") String contact,
                             @RequestParam("location") String location,
                             HttpServletResponse response,
                             HttpServletRequest request){
        User user = userService.getUserById(Integer.parseInt(id));
        user.setUname(name);
        user.setContact(contact);
        user.setAddress(location);
        userService.updateUser(user);
        return "success";
    }

    @RequestMapping(value="/SearchSeller",produces="application/text; charset=utf-8")
    @ResponseBody
    public String searchSeller(@RequestParam("keyword") String keyword,
                             HttpServletResponse response,
                             HttpServletRequest request){
        List<Restaurant> list = new ArrayList<>();
        list = restaurantService.searchByKeyword(keyword);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        if(list!=null){
            obj.addProperty("num",list.size());
            array.add(obj);
            for(int i=0;i<list.size();i++){
                Restaurant sample = list.get(i);
                JsonObject temp = new JsonObject();
                temp.addProperty("id",String.valueOf(sample.getId()));
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

    @RequestMapping(value="/SaveEnteredSeller",produces="application/text; charset=utf-8")
    @ResponseBody
    public void SaveEnteredSeller(@RequestParam("rid") String rid,
                                  HttpServletResponse response,
                                  HttpServletRequest request){
        request.getSession().setAttribute("EnteredSellerInfo",rid);
    }

    @RequestMapping(value="/GetEnteredSeller",produces="application/text; charset=utf-8")
    @ResponseBody
    public String GetEnteredSeller(HttpServletResponse response,
                                  HttpServletRequest request){
        if(request.getSession().getAttribute("EnteredSellerInfo") == null){
            return null;
        }
        else{
            String rid = (String)request.getSession().getAttribute("EnteredSellerInfo");
            Restaurant restaurant = restaurantService.getSellerByRid(rid);
            JsonObject obj = new JsonObject();
            obj.addProperty("rid",restaurant.getRid());
            obj.addProperty("rname",restaurant.getRname());
            obj.addProperty("type",restaurant.getType());
            obj.addProperty("location",restaurant.getLocation());
            return obj.toString();
        }
    }

    @RequestMapping(value="/EstablishNewOrder",produces="application/text; charset=utf-8")
    @ResponseBody
    public String EstablishNewOrder(@RequestParam("rid") String rid,
                                    @RequestParam("uid") String uid,
                                    @RequestParam("menus_id") String menus_id,
                                    @RequestParam("menus_num") String menus_num,
                                    @RequestParam("tgt_address") String tgt_address,
                                    @RequestParam("price") double price,
                                    HttpServletResponse response,
                                    HttpServletRequest request){
        String ids[] = menus_id.split("#");
        String nums[] = menus_num.split("#");
        boolean isOverFlow = false;
        for(int i=0;i<ids.length;i++){
            Menu sample = menuService.getMenuById(Integer.parseInt(ids[i]));
            if(Integer.parseInt(nums[i]) > sample.getNum()){
                isOverFlow = true;
            }
        }
        if(isOverFlow){
            return "fail";
        }
        else{
            MyOrder myOrder = new MyOrder();
            myOrder.setRid(rid);
            myOrder.setUid(Integer.parseInt(uid));
            myOrder.setMenus_id(menus_id);
            myOrder.setMenus_num(menus_num);
            myOrder.setTgtAddress(tgt_address);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            myOrder.setStartTime(sdf.format(date));
            myOrder.setState("CREATED");
            myOrder.setPrice(price);
            orderService.createNewOrder(myOrder);
            return "success";
        }
    }

    @RequestMapping(value="/getUserOrder",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getUserOrder(@RequestParam("uid") String uid,
                               HttpServletResponse response,
                               HttpServletRequest request){
        List<MyOrder> list = orderService.getOrderByUser(Integer.parseInt(uid));
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
                Restaurant restaurant = restaurantService.getSellerByRid(list.get(i).getRid());
                temp.addProperty("rname",restaurant.getRname());

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

    @RequestMapping(value="/getUserBankAccount",produces="application/text; charset=utf-8")
    @ResponseBody
    public String getUserBankAccount(@RequestParam("uid") String uid,
                                     HttpServletResponse response,
                                     HttpServletRequest request){
        BankAccount bankAccount = bankAccountService.getUserBankAccount(Integer.parseInt(uid));
        if(bankAccount == null){
            return "fail";
        }
        else{
            JsonObject obj = new JsonObject();
            obj.addProperty("id",String.valueOf(bankAccount.getId()));
            obj.addProperty("uid",String.valueOf(bankAccount.getUid()));
            obj.addProperty("accountid",bankAccount.getAccountid());
            obj.addProperty("password",bankAccount.getPassword());
            obj.addProperty("balance",String.valueOf(bankAccount.getBalance()));
            return obj.toString();
        }
    }

    @RequestMapping(value="/BindBankAccount",produces="application/text; charset=utf-8")
    @ResponseBody
    public String bindBankAccountAction(@RequestParam("uid") String uid,
                                        @RequestParam("accountid") String accountid,
                                        @RequestParam("password") String password,
                                        HttpServletResponse response,
                                        HttpServletRequest request){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountid(accountid);
        bankAccount.setPassword(password);
        bankAccount.setUid(Integer.parseInt(uid));
        bankAccount.setBalance(10000.0);
        boolean isSuccess = bankAccountService.BindBankAccount(bankAccount);
        if(isSuccess)
            return "success";
        else
            return "fail";
    }

    @RequestMapping(value="/Payment.Action",produces="application/text; charset=utf-8")
    @ResponseBody
    public String payOrder(@RequestParam("oid") String oid,
                           HttpServletResponse response,
                           HttpServletRequest request){
        MyOrder order = orderService.getOrderById(Integer.parseInt(oid));
        if(order != null){
            if(order.getState().equals("CREATED")){
                BankAccount bankAccount = bankAccountService.getUserBankAccount(order.getUid());
                if(bankAccount != null){
                    if(bankAccount.getBalance() >= order.getPrice()){
                        //正常支付
                        bankAccountService.payOrder(bankAccount,order);
                        return "success";
                    }
                    else{
                        return "no_enough_balance";
                    }
                }
                else{
                    return "no_bank_account";
                }
            }
            else{
                return "overtime";
            }
        }
        else{
            return "404";
        }
    }

    @RequestMapping(value="/Refund.Action",produces="application/text; charset=utf-8")
    @ResponseBody
    public String refundOrder(@RequestParam("oid") String oid,
                           HttpServletResponse response,
                           HttpServletRequest request){
        MyOrder order = orderService.getOrderById(Integer.parseInt(oid));
        if(order != null){
            if(order.getState().equals("PAID")){
                BankAccount bankAccount = bankAccountService.getUserBankAccount(order.getUid());
                String result = bankAccountService.refundOrder(bankAccount,order);
                return result;
            }
            else{
                return "refund_not_allowed";
            }
        }
        else{
            return "404";
        }
    }

    @RequestMapping(value="/Confirm.Action",produces="application/text; charset=utf-8")
    @ResponseBody
    public String confirmOrder(@RequestParam("oid") String oid,
                              HttpServletResponse response,
                              HttpServletRequest request){
        MyOrder order = orderService.getOrderById(Integer.parseInt(oid));
        if(order != null){
            if(order.getState().equals("DELIVERED")){
                orderService.confirmOrder(order);
                return "success";
            }
            else{
                return "already_confirm";
            }
        }
        else{
            return "404";
        }
    }

    @RequestMapping(value="/TerminateUser",produces="application/text; charset=utf-8")
    @ResponseBody
    public String terminateUser(@RequestParam("id") String id,
                           HttpServletResponse response,
                           HttpServletRequest request){
        User user = userService.getUserById(Integer.parseInt(id));
        user.setIsValid(false);
        userService.updateUser(user);
        return "success";
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
