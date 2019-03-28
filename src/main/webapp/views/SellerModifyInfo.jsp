<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/1 0001
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户修改个人信息</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var seller_info = null
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

        function requestModifyInfo(){
            var alter_name = document.getElementById("seller_name").value;
            var alter_type = document.getElementById("seller_type").value;
            var alter_area = document.getElementById("seller_area").value;
            var alter_location = document.getElementById("seller_location").value;

            if(alter_name === null || alter_type === null || alter_area === null || alter_location === null ){
                alert("信息不完整");
            }
            else if(alter_name === "" || alter_type === "" || alter_area === "" || alter_location === "" ){
                alert("信息不完整");
            }
            else{
                var location = alter_area+"#"+alter_location;
                $.ajax({
                    url:"../seller/ChangeInfo",
                    type:'POST',
                    async: false,
                    data:{
                        "rid":seller_rid,
                        "rname":alter_name,
                        "type":alter_type,
                        "location":location,
                    },
                    success:function(data){
                        alert(data);
                        window.location.href = "/yummy/home/index";
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
    <h1>Modify Your Seller Info</h1>
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

    <h2>修改店家信息</h2>

    <div class="col-sm-9 text" style="height:100%;width:75%">
    <div class="form-row">
        <div class="field-label"><label for="seller_name">商家名称：</label></div>
        <div class="field-widget"><input id="seller_name" class="required" placeholder="" /></div>
    </div>

    <div class="form-row">
        <div class="field-label"><label for="seller_type">商家类型：</label></div>
        <div class="field-widget">
            <select id="seller_type" name="field6" class="validate-selection" title="choose menu type">
                <option value="咖啡店">咖啡店</option>
                <option value="奶茶店">奶茶店</option>
                <option value="中餐店">中餐店</option>
                <option value="西餐店">西餐店</option>
                <option value="日料店">日料店</option>
            </select>
        </div>
    </div>

    <div class="form-row">
        <div class="field-label"><label for="seller_area">商家地区：</label></div>
        <div class="field-widget">
            <select id="seller_area" name="field6" class="validate-selection" title="choose menu type">
                <option value="南京">南京</option>
                <option value="上海">上海</option>
                <option value="北京">北京</option>
                <option value="广东">广东</option>
                <option value="重庆">重庆</option>
            </select>
        </div>
    </div>

    <div class="form-row">
        <div class="field-label"><label for="seller_location">详细地址：</label></div>
        <div class="field-widget"><input id="seller_location" class="required" placeholder="" /></div>
    </div>

    <div style="text-align:center;">
        <button id="release" onclick="requestModifyInfo()">提交修改信息</button>
    </div>
        </div>
</div>
</body>
</html>
