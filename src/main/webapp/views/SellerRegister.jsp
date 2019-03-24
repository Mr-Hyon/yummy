<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/1/26 0026
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商家注册</title>
    <link rel="stylesheet" type="text/css" href="../statics/css/index.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type = "text/javascript">

    </script>
</head>
<body>
<h2>Welcome to Yummy!</h2>
<br>
<form action="${pageContext.request.contextPath}/seller/regist.action" method="post" >
    <table>
        <tr>
            <td><label>店铺名称：</label></td>
            <td><input type="text" id="rname" name="rname"></td>
        </tr>
        <tr>
            <td><label>密码：</label></td>
            <td><input type="password" id="password" name="password"></td>
        </tr>
        <tr>
            <td><label>店铺类型：</label></td>
            <td><input type="text" id="type" name="type"></td>
        </tr>
        <tr>
            <td><label>店铺地址：</label></td>
            <td><input type="text" id="location" name="location"></td>
        </tr>
        <tr>
            <td><label>配送区域：</label></td>
            <td><select name="area" id="area">
                <option value="南京">南京</option>
                <option value="上海">上海</option>
                <option value="北京">北京</option>
                <option value="广东">广东</option>
                <option value="重庆">重庆</option>
            </select></td>
        </tr>
        <tr>
            <td><input type="submit" value="注册"></td>
            <td><input type="reset" value="重置"></td>
        </tr>
    </table>
</form>
</body>
</html>
