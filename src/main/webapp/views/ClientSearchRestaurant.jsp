<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/5 0005
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>搜寻商家</title>
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
        var JsonList = null;
        var JsonObjArray = null;
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

            showResult("");

        });

        function showResult(keyword){
            $.ajax({
                url:'../client/SearchSeller',
                type:'post',
                async:false,
                data:{
                    "keyword": keyword
                },
                success: function(data){
                    JsonList = data;
                },
                error: function(){
                    alert('request error');
                }
            });
            JsonObjArray = JSON.parse(JsonList);
            for(var i=1;i<=JsonObjArray[0].num;i++){
                add_seller_info(JsonObjArray[i].rid,JsonObjArray[i].rname,
                    JsonObjArray[i].type,JsonObjArray[i].location);
            }
        }

        function add_seller_info(rid,rname,type,location){
            var table = document.getElementById("seller_info");
            var oneRow = table.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            cell1.innerHTML = rid;
            cell2.innerHTML = rname;
            cell3.innerHTML = "店铺类型:" + type + "<br>" +
                                "配送区域:" + location.split("#")[0] + "<br>" +
                                "店铺地址:" + location.split("#")[1] + "<br>";
            cell4.innerHTML = "<button id='EnterButton' onclick='EnterRestaurant(this)'>进入</button>";
            document.getElementById("EnterButton").id = rid;
        }

        function EnterRestaurant(o){
            var rid = o.id;
            $.ajax({
                url:"../client/SaveEnteredSeller",
                type:'post',
                async:false,
                data:{
                    "rid": rid
                },
                success: function(){
                },
                error: function(){
                    alert('request error');
                }
            });
            window.location.href = "/yummy/client/observeMenuPage";
        }
    </script>
</head>
<body>
<div class="page-container" style="height:100%;width:100%">
    <h1>Let's Get Started.</h1>
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

    <h2>商家</h2>
    <div class="col-sm-9 text" style="height:100%;width:75%">
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="seller_info">
                <tr>
                    <td>商家编号</td><td>商家名称</td><td>商家信息</td><td>操作</td>
                </tr>
            </table>
        </div>
    </div>
</div>

</body>
</html>
