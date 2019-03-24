<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/7 0007
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Client Observe Menu</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var client_info = null;
        var seller_info = null;
        var client_name = null;
        var client_email = null;
        var client_credit = null;
        var client_contact = null;
        var client_id = null;
        var seller_rid = null;
        var MenuList = null;
        var MenuObjArray = null;
        var clientJsonObj = null;
        var sellerJsonObj = null;
        var CartIndexArray = new Array();
        var CartNumArray = new Array();

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
            clientJsonObj = JSON.parse(client_info);
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
            $.ajax({
                    url:"../client/GetEnteredSeller",
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
            sellerJsonObj = JSON.parse(seller_info);
            seller_rid = sellerJsonObj.rid;
            document.getElementById("seller_name").innerHTML="商家名称："+ sellerJsonObj.rname;
            document.getElementById("seller_type").innerHTML="商家类型："+ sellerJsonObj.type;
            document.getElementById("seller_location").innerHTML="商家地址："+ sellerJsonObj.location;

            fetchSellerMenu(seller_rid);
            MenuObjArray = JSON.parse(MenuList);
            addAddress();
            reload_menu();
            reload_cart();
        });

        function addAddress(){
            var addresses = clientJsonObj.address;
            var select_address = document.getElementById("tgtAddress");
            var op1 = new Option(addresses.split(";")[0],addresses.split(";")[0]);
            select_address.appendChild(op1);
            if(addresses.split(";")[1] !== null && addresses.split(";")[1]!== "" && addresses.split(";")[1]!== undefined){
                var op2 = new Option(addresses.split(";")[1],addresses.split(";")[1]);
                select_address.appendChild(op2);
            }
            if(addresses.split(";")[2] !== null && addresses.split(";")[2]!== "" && addresses.split(";")[2]!== undefined){
                var op3 = new Option(addresses.split(";")[2],addresses.split(";")[2]);
                select_address.appendChild(op3);
            }
        }

        function fetchSellerMenu(rid){
            $.ajax({
                url:"../seller/getSellerMenu",
                type:'POST',
                async: false,
                data:{
                    "rid":rid
                },
                success:function(data){
                    MenuList = data;
                },
                error:function(){
                    alert("请求错误");
                }
            });
        }

        function reload_menu(){
            var menu_table = document.getElementById("menu_info");
            while( menu_table.rows.length > 1){
                menu_table.deleteRow(menu_table.rows.length-1);
            }
            for(var i = 1;i<=MenuObjArray[0].num;i++){
                add_menu_info(i);
            }
        }

        function reload_cart(){
            var cart_table = document.getElementById("cart_info");
            while( cart_table.rows.length > 1){
                cart_table.deleteRow(cart_table.rows.length-1);
            }
            for(var i = 0; i<CartIndexArray.length;i++){
                add_cart_info(i,CartIndexArray[i]);
            }
        }

        function add_menu_info(index){
            var menu_table = document.getElementById("menu_info");
            var oneRow = menu_table.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            var cell6 = oneRow.insertCell();
            cell1.innerHTML = MenuObjArray[index].mid;
            cell2.innerHTML = MenuObjArray[index].name;
            cell3.innerHTML = MenuObjArray[index].desp;
            cell4.innerHTML = MenuObjArray[index].num;
            cell5.innerHTML = MenuObjArray[index].price;
            cell6.innerHTML = "<button id='AddCartButton' onclick='add2cart(this)'>添加至购物车</button>";
            document.getElementById("AddCartButton").id = "a" + index;
        }

        function add_cart_info(i,index){
            var cart_table = document.getElementById("cart_info");
            var oneRow = cart_table.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            cell1.innerHTML = MenuObjArray[index].mid;
            cell2.innerHTML = MenuObjArray[index].name;
            cell3.innerHTML = CartNumArray[i];
            cell4.innerHTML = MenuObjArray[index].price;
            cell5.innerHTML = "<button id='DeleteCartButton' onclick='deleteFromCart(this)'>从购物车移除</button>";
            document.getElementById("DeleteCartButton").id = "d"+index;
        }

        function add2cart(o){
            var index = o.id.substring(1);
            var isFound = false;
            for(var i=0;i<CartIndexArray.length;i++){
                if(CartIndexArray[i] === index){
                    isFound = true;
                    if(parseInt(MenuObjArray[index].num)< 1+CartNumArray[i]){
                        alert("不可超买");
                    }
                    else{
                        CartNumArray[i] += 1;
                        reload_cart();
                    }
                    break;
                }
            }
            if(!isFound){
                if(parseInt(MenuObjArray[index].num)< 1){
                    alert("不可超买");
                }
                else{
                    CartIndexArray.push(index);
                    CartNumArray.push(1);
                    reload_cart();
                }
            }
        }

        function deleteFromCart(o){
            var index = o.id.substring(1);
            for(var i=0;i<CartIndexArray.length;i++){
                if(CartIndexArray[i] === index){
                    CartNumArray[i] -= 1;
                    if(parseInt(CartNumArray[i]) <= 0){
                        CartNumArray.splice(i,1);
                        CartIndexArray.splice(i,1);
                    }
                    break;
                }
            }
            reload_cart();
        }

        function createOrder(){
            if(CartIndexArray.length <=0 ){
                alert("请先选购餐点");
            }
            else if(document.getElementById("tgtAddress").value.split("#")[0] !== sellerJsonObj.location.split("#")[0]){
                alert("超出商家配送区域");
            }
            else{
                var mid_list = "";
                var num_list = "";
                var price = 0.0;
                for(var i = 0;i<CartIndexArray.length;i++){
                    mid_list += MenuObjArray[CartIndexArray[i]].mid;
                    num_list += CartNumArray[i];
                    price += parseFloat(MenuObjArray[CartIndexArray[i]].price) * parseInt(CartNumArray[i]);
                    if(i !== CartIndexArray.length - 1){
                        mid_list += "#";
                        num_list += "#";
                    }
                }
                $.ajax({
                    url:"../client/EstablishNewOrder",
                    type:'POST',
                    async: false,
                    data:{
                      "rid":seller_rid,
                      "uid":client_id,
                      "menus_id":mid_list,
                      "menus_num":num_list,
                      "tgt_address":document.getElementById("tgtAddress").value,
                        "price": price
                    },
                    success:function(data){
                        if(data === "success"){
                            alert("订单建立成功，请于两分钟内完成支付，否则订单将自动取消");
                            window.location.href = "/yummy/client/checkOrderPage";
                        }
                        else{
                            alert("不可超买");
                        }
                    },
                    error:function(){
                        alert("请求错误");
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="page-container" style="height:100%;width:100%">
    <h1>Check Restaurant Menu.</h1>
    <nav>
        <a href="/yummy/client/main">个人首页</a> |
        <a href="/yummy/client/searchSellerPage" class="selected">前往订餐</a> |
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

    <p id="seller_name" style="text-align:center;"></p>
    <p id="seller_type" style="text-align:center;"></p>
    <p id="seller_location" style="text-align:center;"></p>
    <div class="col-sm-9 text" style="height:100%;width:75%">
        <h2>菜单</h2>
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="menu_info">
                <tr>
                    <td>餐点编号</td><td>餐点名称</td><td>描述</td><td>剩余数量</td><td>单价</td><td>操作</td>
                </tr>
            </table>
        </div>
        <h2>购物车</h2>
        <br>
        <div class="form-row">
            <div class="field-label"><label for="tgtAddress">配送地址：</label></div>
            <div class="field-widget">
                <select id="tgtAddress" name="field6" class="validate-selection" title="choose menu type">
                </select>
            </div>
        </div>
        <br>
        <div style="text-align:center;">
            <button id="SubmitOrder" onclick="createOrder()">建 立 订 单</button>
        </div>
        <br>
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="cart_info">
                <tr>
                    <td>餐点编号</td><td>餐点名称</td><td>数量</td><td>单价</td><td>操作</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
