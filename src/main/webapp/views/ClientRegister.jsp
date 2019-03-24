<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/1/26 0026
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>客户注册</title>
    <link rel="stylesheet" type="text/css" href="../statics/css/index.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type = "text/javascript">
        function sendVcode(){
            var reg = /^([a-zA-Z0-9]+[_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if(!reg.test(document.getElementById("email").value)){
                alert("邮箱格式错误!");
            }
            else{
                $.ajax({
                    url:"sendvcode.action",
                    type:"POST",
                    data:{"email":$("#email").val()},
                    success:function(data){
                        if(data === "success"){
                            alert("验证码发送成功");
                        }
                        else{
                            alert("邮箱已注册");
                        }
                    },
                    error:function(){
                        alert("验证码发送失败");
                    }
                });
            }
        }
    </script>
</head>
<body>
    <h2>Welcome to Yummy!</h2>
    <br>
    <form action="${pageContext.request.contextPath}/client/regist.action" method="post" >
        <table>
            <tr>
                <td><label>邮箱：</label></td>
                <td><input type="text" id="email" name="email"></td>
            </tr>
            <tr>
                <td><label>用户名称：</label></td>
                <td><input type="text" id="uname" name="uname"></td>
            </tr>
            <tr>
                <td><label>电话号码：</label></td>
                <td><input type="text" id="contact" name="contact"></td>
            </tr>
            <tr>
                <td><label>密码：</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr>
                <td><label>配送区域1（必填）：</label></td>
                <td><select id="area1" name="area1">
                    <option value="">无</option>
                    <option value="南京">南京</option>
                    <option value="上海">上海</option>
                    <option value="北京">北京</option>
                    <option value="广东">广东</option>
                    <option value="重庆">重庆</option>
                </select></td>
                <td><label>具体地址1（必填）：</label></td>
                <td><input type="text" id="address1" name="address1"></td>
            </tr>
            <tr>
                <td><label>配送区域2（可选）：</label></td>
                <td><select id="area2" name="area2">
                    <option value="">无</option>
                    <option value="南京">南京</option>
                    <option value="上海">上海</option>
                    <option value="北京">北京</option>
                    <option value="广东">广东</option>
                    <option value="重庆">重庆</option>
                </select></td>
                <td><label>配送地址2（可选）：</label></td>
                <td><input type="text" id="address2" name="address2"></td>
            </tr>
            <tr>
                <td><label>配送区域3（可选）：</label></td>
                <td><select id="area3" name="area3">
                    <option value="">无</option>
                    <option value="南京">南京</option>
                    <option value="上海">上海</option>
                    <option value="北京">北京</option>
                    <option value="广东">广东</option>
                    <option value="重庆">重庆</option>
                </select></td>
                <td><label>配送地址3（可选）：</label></td>
                <td><input type="text" id="address3" name="address3"></td>
            </tr>
            <tr>
                <td>验证码</td>
                <td><input type = "text" id="vcode" name="vcode"></td>
                <td><input type="button" id="sendvcode" value="发送验证码" onclick="sendVcode()"></td>
            </tr>
            <tr>
                <td><input type="submit" value="注册"></td>
                <td><input type="reset" value="重置"></td>
            </tr>
        </table>
    </form>
</body>
</html>
