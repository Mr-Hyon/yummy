<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/2/7 0007
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to Yummy</title>
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
            var order_table = document.getElementById("order_record");
            var refund_table = document.getElementById("refund_record");
            var cost_table = document.getElementById("cost_record");
            while( order_table.rows.length > 1){
                order_table.deleteRow(order_table.rows.length-1);
            }
            while( refund_table.rows.length > 1){
                order_table.deleteRow(order_table.rows.length-1);
            }
            while( cost_table.rows.length > 1){
                order_table.deleteRow(order_table.rows.length-1);
            }
            for(var i = 1;i<=OrderObjArray[0].num;i++){
                if(OrderObjArray[i].state === "DONE"){
                    add_order_info(i);
                }
                else if(OrderObjArray[i].state === "REFUND"){
                    add_refund_info(i);
                }
            }
        }

        function add_order_info(index){
            var order_table = document.getElementById("order_record");
            var oneRow = order_table.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            var cell6 = oneRow.insertCell();
            var cell7 = oneRow.insertCell();
            cell1.innerHTML = OrderObjArray[index].startTime;
            cell2.innerHTML = OrderObjArray[index].payTime;
            cell3.innerHTML = OrderObjArray[index].deliverTime;
            cell4.innerHTML = OrderObjArray[index].endTime;
            cell5.innerHTML = OrderObjArray[index].rname;
            var detail = "" ;
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

            cell6.innerHTML = detail;
            cell7.innerHTML = OrderObjArray[index].price;
            add_cost_info(OrderObjArray[index].payTime,OrderObjArray[index].rname,"支付",OrderObjArray[index].price);
        }

        function add_refund_info(index){
            var refund_table = document.getElementById("refund_record");
            var oneRow = refund_table.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            var cell6 = oneRow.insertCell();
            cell1.innerHTML = OrderObjArray[index].startTime;
            cell2.innerHTML = OrderObjArray[index].payTime;
            cell3.innerHTML = OrderObjArray[index].endTime;
            cell4.innerHTML = OrderObjArray[index].rname;
            var detail = "" ;
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

            cell5.innerHTML = detail;
            cell6.innerHTML = OrderObjArray[index].refund_price;

            add_cost_info(OrderObjArray[index].endTime,OrderObjArray[index].rname,"退款",OrderObjArray[index].refund_price);
            add_cost_info(OrderObjArray[index].payTime,OrderObjArray[index].rname,"支付",OrderObjArray[index].price);
        }

        function add_cost_info(time,name,desp,cost){
            var cost_table = document.getElementById("cost_record");
            var oneRow = cost_table.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            cell1.innerHTML = time;
            cell2.innerHTML = name;
            cell3.innerHTML = desp;
            cell4.innerHTML = cost;
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
        <h2>点餐记录(不包含进行中的订单)</h2>
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="order_record">
                <tr>
                    <td>下单时间</td><td>支付时间</td><td>配送时间</td><td>结束时间</td><td>餐厅</td><td>内容</td><td>金额（RMB）</td>
                </tr>
            </table>
        </div>

        <h2>退订记录</h2>
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="refund_record">
                <tr>
                    <td>下单时间</td><td>支付时间</td><td>退订时间</td><td>餐厅</td><td>内容</td><td>退订金额（RMB）</td>
                </tr>
            </table>
        </div>

        <h2>消费记录(不包含进行中的订单)</h2>
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="cost_record">
                <tr>
                    <td>消费时间</td><td>餐厅</td><td>描述</td><td>金额（RMB）</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
