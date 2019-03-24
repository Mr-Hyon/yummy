<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/16 0016
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商家财务统计</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var seller_info = null;
        var seller_name = null;
        var seller_rid = null;
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
        });
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

</div>
</body>
</html>
