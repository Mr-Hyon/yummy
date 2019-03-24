<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/2/16 0016
  Time: 22:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>STAFF ONLY</title>
</head>
<body>
    <h2>Staff Login Interface</h2>
    <br>
    <form action=${pageContext.request.contextPath}/manager/login.action method="post" >
        <table>
            <tr>
                <td><label>STAFF ID：</label></td>
                <td><input type="text" id="staffid" name="staffid"></td>
            </tr>
            <tr>
                <td><label>PASSWORD：</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" name="loginStaff" value="登录"/></td>
            </tr>
        </table>
    </form>
</body>
</html>
