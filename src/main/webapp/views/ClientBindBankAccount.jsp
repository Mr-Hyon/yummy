<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/11 0011
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户绑定银行账户</title>
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
        var bankAccountInfo = null;
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

            fetchUserBankAccount();
            if(bankAccountInfo === null){
                document.getElementById("some_info").innerHTML = "您尚未绑定支付银行卡";
            }
            else{
                var info = "您已绑定银行卡，卡内余额：" + bankAccountInfo.balance +" RMB";
                document.getElementById("some_info").innerHTML = info;
                document.getElementById("account_id").value = bankAccountInfo.accountid;
                document.getElementById("account_id").disabled = "disabled";
                document.getElementById("password").value = bankAccountInfo.password;
                document.getElementById("password").disabled = "disabled";
            }
        });

        function fetchUserBankAccount(){
            $.ajax({
               url:"../client/getUserBankAccount",
               type:'POST',
               async: false,
               data:{
                   "uid":client_id
                },
               success:function(data){
                    if(data !== "fail"){
                        bankAccountInfo = JSON.parse(data);
                    }
               },
               error:function(){
                   alert("请求错误");
               }
            });
        }

        function requestBindCard(){
            if(bankAccountInfo !== null){
                alert("您已经绑定银行卡，不可进行更改操作");
            }
            else{
                var account_id = document.getElementById("account_id").value;
                var password = document.getElementById("password").value;
                if(account_id.length !== 19 ){
                    alert("卡号必须是19位数字组合");
                }
                else if(password.length !== 6){
                     alert("卡密必须是6位数字组合");
                }
                else {
                    $.ajax({
                        url: "../client/BindBankAccount",
                        type: 'POST',
                        async: false,
                        data: {
                            "uid": client_id,
                            "accountid": account_id,
                            "password": password
                        },
                        success: function (data) {
                            if (data === "success") {
                                alert("绑定成功");
                                window.location.href = "/yummy/client/bindBankAccountPage";
                            }
                            else {
                                alert("该银行账户已被绑定");
                            }
                        },
                        error: function () {
                            alert("request error");
                        }
                    });
                }
            }
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

    <h2>绑定网银信息</h2>

    <p id="some_info"></p>

    <div class="col-sm-9 text" style="height:100%;width:75%">
        <div class="form-row">
            <div class="field-label"><label for="account_id">银行卡号：</label></div>
            <div class="field-widget"><input id="account_id" type="text" class="required" placeholder="Card.No" /></div>
        </div>

        <div class="form-row">
            <div class="field-label"><label for="password">卡密：</label></div>
            <div class="field-widget"><input id="password" type="password" class="required" placeholder="Password" /></div>
        </div>

        <div style="text-align:center;">
            <button id="bind" onclick="requestBindCard()">绑定银行卡</button>
        </div>
    </div>

</div>
</body>
</html>
