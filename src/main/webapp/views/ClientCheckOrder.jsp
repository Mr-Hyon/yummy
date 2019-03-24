<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/10 0010
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户查看订单</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var client_info = null;
        var client_name = null;
        var client_email = null;
        var client_credit = null;
        var client_contact = null;
        var client_id = null;
        var OrderList = null;
        var OrderObjArray = null;
        $(function(){
            $.ajax({
                    url:"../client/getCurrentClient",
                    type:'POST',
                    async: false,
                    success:function(data){
                        if(data!==null){
                            client_info = data;
                        }
                        else{
                            window.location.href = "/yummy/home/index";
                        }
                    },
                    error:function(){
                        alert("请求错误");
                    }
                }
            );
            var clientJsonObj = JSON.parse(client_info);
            client_name =  clientJsonObj.uname;
            client_id = clientJsonObj.id;
            client_contact = clientJsonObj.contact;
            client_credit = clientJsonObj.credit;
            client_email = clientJsonObj.email;
            document.getElementById("id").innerHTML="用户编号："+client_id;
            document.getElementById("email").innerHTML="用户邮箱："+client_email;
            document.getElementById("name").innerHTML="用户名称："+ client_name;
            document.getElementById("contact").innerHTML="用户电话："+client_contact;
            document.getElementById("credit").innerHTML="用户积分："+client_credit;

            getUserOrder();
            OrderObjArray = JSON.parse(OrderList);
            reload_table();
        });

        function getUserOrder(){
            $.ajax({
                url:'../client/getUserOrder',
                type:'POST',
                async: false,
                data:{
                    "uid":client_id
                },
                success:function(data){
                    OrderList = data;
                },
                error:function(){
                    alert("request fail");
                }
            });
        }

        function reload_table(){
            var order_table = document.getElementById("order_info");
            while( order_table.rows.length > 1){
                order_table.deleteRow(order_table.rows.length-1);
            }
            for(var i = 1;i<=OrderObjArray[0].num;i++){
                add_order_info(i);
            }
        }

        function add_order_info(index){
            var order_table = document.getElementById("order_info");
            var oneRow = order_table.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            cell1.innerHTML = OrderObjArray[index].id;
            var detail = "商家编号: " + OrderObjArray[index].rid + "<br>" +
                                "商家名称: " + OrderObjArray[index].rname + "<br>" +
                                "订单建立时间: " + OrderObjArray[index].startTime + "<br>" +
                                "配送地址：" + OrderObjArray[index].tgtAddress + "<br>" +
                                "商品内容: " ;
            var menus_id = OrderObjArray[index].menus_id.split("#");
            var menus_name = OrderObjArray[index].menus_name.split("#");
            var menus_num = OrderObjArray[index].menus_num.split("#");

            for(var i = 0 ; i<menus_id.length ; i++){
                detail = detail+menus_name[i]+" * "+menus_num[i];
                detail = detail + "<br>";
                if(i>0 && i< menus_id.length-1){
                    detail += "          ";
                }
            }

            cell2.innerHTML = detail;
            cell3.innerHTML = OrderObjArray[index].price;
            switch(OrderObjArray[index].state){
                case "CREATED":
                    cell4.innerHTML = "待支付";
                    cell5.innerHTML = "<button id='PayButton' onclick='Go2Pay(this)'>进行支付</button>";
                    document.getElementById("PayButton").id = OrderObjArray[index].id;
                    break;
                case "CANCELED":
                    cell4.innerHTML = "已取消";
                    break;
                case "PAID":
                    cell4.innerHTML = "已支付";
                    cell5.innerHTML = "<button id='RefundButton' onclick='Go2Refund(this)'>前往退订</button>";
                    document.getElementById("RefundButton").id = OrderObjArray[index].id;
                    break;
                case "DELIVERED":
                    cell4.innerHTML = "已配送";
                    cell5.innerHTML = "<button id='ConfirmButton' onclick='Go2Confirm(this)'>确认收货</button>";
                    document.getElementById("ConfirmButton").id = OrderObjArray[index].id;
                    break;
                case "REFUND":
                    cell4.innerHTML = "已退订";
                    break;
                case "DONE":
                    cell4.innerHTML = "已完成";
                    break;
                default:
                    alert("STATE ERROR");
            }
        }

        function Go2Pay(o){
            var order_id = o.id;
            $.ajax({
                url:'../client/Payment.Action',
                type:'POST',
                async: false,
                data:{
                    "oid":order_id
                },
                success:function(data){
                    switch(data){
                        case "success":
                            alert("支付成功");
                            window.location.href = "/yummy/client/checkOrderPage";
                            break;
                        case "404":
                            alert("订单不存在");
                            break;
                        case "no_enough_balance":
                            alert("余额不足");
                            break;
                        case "no_bank_account":
                            alert("您尚未绑定银行卡，请先绑定网银以完成支付");
                            break;
                        case "overtime":
                            alert("订单已失效");
                            break;
                        default:
                            alert("some thing went wrong in the payment part");
                    }
                },
                error:function(){
                    alert("request fail");
                }
            });

        }

        function Go2Refund(o){
            var order_id = o.id;
            $.ajax({
                url:'../client/Refund.Action',
                type:'POST',
                async: false,
                data:{
                    "oid":order_id
                },
                success:function(data){
                    switch(data){
                        case "full":
                            alert("于支付后一分钟内退订，全额退款");
                            window.location.href = "/yummy/client/checkOrderPage";
                            break;
                        case "half":
                            alert("于支付后十分钟内退订，半额退款");
                            window.location.href = "/yummy/client/checkOrderPage";
                            break;
                        case "zero":
                            alert("于支付后十分钟后退订，不退款");
                            window.location.href = "/yummy/client/checkOrderPage";
                            break;
                        case "404":
                            alert("订单不存在");
                            break;
                        case "refund_not_allowed":
                            alert("商家已发货，不可退订");
                            break;
                        default:
                            alert("some thing went wrong in the payment part");
                    }
                },
                error:function(){
                    alert("request fail");
                }
            });
        }

        function Go2Confirm(o){
            var order_id = o.id;
            $.ajax({
                url:'../client/Confirm.Action',
                type:'POST',
                async: false,
                data:{
                    "oid":order_id
                },
                success:function(data){
                    switch(data){
                        case "success":
                            alert("确认成功");
                            window.location.href = "/yummy/client/checkOrderPage";
                            break;
                        case "404":
                            alert("订单不存在");
                            break;
                        case "already_confirm":
                            alert("已确认收货");
                            window.location.href = "/yummy/client/checkOrderPage";
                            break;
                        default:
                            alert("some thing went wrong in the payment part");
                    }
                },
                error:function(){
                    alert("request fail");
                }
            });
        }
    </script>
</head>
<body>
<div class="page-container" style="height:100%;width:100%">
    <h1>Good Day, our dear customer.</h1>
    <nav>
        <a href="/yummy/client/main" class="selected">个人首页</a> |
        <a href="/yummy/client/searchSellerPage">前往订餐</a> |
        <a href="/yummy/client/modifyInfoPage">修改信息</a> |
        <a href="/yummy/client/bindBankAccountPage">绑定网银</a> |
        <a href="/yummy/client/checkOrderPage">查看订单</a> |
        <a href="/yummy/client/client.logout">退出登录</a>
    </nav>

    <div class="col-sm-3 text" style="background-color: #996699;height:150%;width:25%">
        <p id="id" style="text-align:center;"></p>
        <p id="email" style="text-align:center;"></p>
        <p id="name" style="text-align:center;"></p>
        <p id="contact" style="text-align:center;"></p>
        <p id="credit" style="text-align:center;"></p>
    </div>

    <div class="col-sm-9 text" style="height:100%;width:75%">
        <h2>订单</h2>
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="order_info">
                <tr>
                    <td>订单编号</td><td>详情</td><td>总价</td><td>订单状态</td><td>操作</td>
                </tr>
            </table>
        </div>
    </div>

</div>
</body>
</html>
