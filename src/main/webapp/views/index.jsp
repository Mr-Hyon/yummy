<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/1/24 0024
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>登录页面</title>
        <link rel="stylesheet" type="text/css" href="../statics/css/index.css">
        <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
        <script type="text/javascript">
            function clickLogin(){
                if($("#username").val()!== null && $("#password").val()!== null){
                    if($("input[name='radio']:checked").val()==="client"){
                        $.ajax({
                            url:"../client/client.login",
                            type:"POST",
                            dataType:"text",
                            data:{"email":$("#username").val(),
                                    "password":$("#password").val()},
                            success:function(data){
                                if(data ==="success"){
                                    alert("登录成功");
                                    window.location.href = "/yummy/client/main";
                                }
                                else{
                                    alert("用户不存在或密码错误");
                                }
                            },
                            error:function(){
                                alert("ERROR");
                            }
                        });
                    }
                    else{
                        $.ajax({
                            url:"../seller/seller.login",
                            type:"POST",
                            data:{"rid":$("#username").val(),
                                "password":$("#password").val()},
                            dataType:"text",
                            success:function(data){
                                if(data === "success"){
                                    alert("登录成功");
                                    window.location.href = "/yummy/seller/main";
                                }
                                else{
                                    alert("用户不存在或商家待审核中");
                                }
                            },
                            error:function(){
                                alert("ERROR");
                            }
                        });
                    }
                }
                else{
                    alert("信息不完整");
                }
            }
        </script>
    </head>
    <body>
        <h2>Welcome to Yummy!</h2>
        <br>
            <table>
                <tr>
                    <td><label>登录名：</label></td>
                    <td><input type="text" id="username" name="username"></td>
                </tr>
                <tr>
                    <td><label>密码：</label></td>
                    <td><input type="password" id="password" name="password"></td>
                </tr>
                <tr>
                    <td><label>用户类型：</label></td>
                    <td>
                        <input name="radio" id="radio1" type="radio" checked="checked" value="client"/>会员
                        <input name="radio" id="radio2" type="radio" value="seller"/>商家
                    </td>
                </tr>
                <tr>
                    <td><input type="button" name="login" value="登录" onclick="clickLogin()"/></td>
                </tr>
            </table>

        <h2><a href="/yummy/client/register">Wanna myOrder some menu? Click here to be our valuable customer!</a></h2>
        <h2><a href="/yummy/seller/register">Wanna be the Top tier Restaurant? Click here to join us!</a></h2>
        <h2><a href="/yummy/manager/login">STAFF ONLY</a></h2>
    </body>
</html>
