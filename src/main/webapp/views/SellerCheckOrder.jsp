<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/12 0012
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商家查看订单</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var seller_info = null;
        var seller_name = null;
        var seller_rid = null;
        var OrderList = null;
        var OrderObjArray = null;
        $(function(){
            $.ajax({
                    url:"../seller/getCurrentSeller",
                    type:'POST',
                    async: false,
                    success:function(data){
                        if(data!==null){
                            seller_info = data;
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
            var sellerJsonObj = JSON.parse(seller_info);
            seller_name =  sellerJsonObj.rname;
            seller_rid = sellerJsonObj.rid;
            document.getElementById("rid").innerHTML="店铺编号："+seller_rid;
            document.getElementById("rname").innerHTML="店铺名称："+seller_name;
            document.getElementById("type").innerHTML="店铺类型："+sellerJsonObj.type;
            document.getElementById("location").innerHTML="店铺地址："+sellerJsonObj.location;

            fetchSellerOrder();
            OrderObjArray = JSON.parse(OrderList);
            reload_table();

        });

        function fetchSellerOrder(){
            $.ajax({
                url:'../seller/getSellerOrder',
                type:'POST',
                async: false,
                data:{
                    "rid":seller_rid
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
            var detail = "用户编号: " + OrderObjArray[index].uid + "<br>" +
                "用户名称: " + OrderObjArray[index].uname + "<br>" +
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
                    break;
                case "CANCELED":
                    cell4.innerHTML = "已取消";
                    break;
                case "PAID":
                    cell4.innerHTML = "已支付";
                    cell5.innerHTML = "<button id='DeliverButton' onclick='Go2Deliver(this)'>进行配送</button>";
                    document.getElementById("DeliverButton").id = OrderObjArray[index].id;
                    break;
                case "DELIVERED":
                    cell4.innerHTML = "已配送";
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

        function Go2Deliver(o){
            var oid = o.id;
            $.ajax({
                url:'../seller/Deliver.action',
                type:'POST',
                async: false,
                data:{
                    "oid":oid
                },
                success:function(data){
                    switch(data){
                        case "success":
                            alert("配送成功");
                            window.location.href = "/yummy/seller/checkOrderPage";
                            break;
                        case "404":
                            alert("订单不存在");
                            break;
                        case "cannot_deliver":
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
    </script>
</head>
<body>
<div class="page-container" style="height:100%;width:100%">
    <h1>A happy Business is a sweet business.</h1>
    <nav>
        <a href="/yummy/seller/main" class="selected">店家首页</a> |
        <a href="/yummy/seller/modifyInfoPage">修改信息</a> |
        <a href="/yummy/seller/customizePage">自定义菜单</a> |
        <a href="/yummy/seller/checkOrderPage">订单处理</a> |
        <a href="/yummy/seller/financePage">财务记录</a> |
        <a href="/yummy/seller/seller.logout">退出登录</a>
    </nav>

    <div class="col-sm-3 text" style="background-color: #996699;height:150%;width:25%">
        <p id="rid" style="text-align:center;"></p>
        <p id="rname" style="text-align:center;"></p>
        <p id="type" style="text-align:center;"></p>
        <p id="location" style="text-align:center;"></p>
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
