<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/2/14 0014
  Time: 23:00
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
        var client_info = null;
        var client_name = null;
        var client_email = null;
        var client_credit = null;
        var client_contact = null;
        var client_id = null;
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
        });

        function requestModifyInfo(){
            var alter_name = document.getElementById("client_name").value;
            var alter_contact = document.getElementById("client_contact").value;
            var alter_area1 = document.getElementById("area1").value;
            var alter_location1 = document.getElementById("location1").value;
            var alter_area2 = document.getElementById("area2").value;
            var alter_location2 = document.getElementById("location2").value;
            var alter_area3 = document.getElementById("area3").value;
            var alter_location3 = document.getElementById("location3").value;

            if(alter_name === null || alter_contact === null || alter_area1 === null || alter_location1 === null ){
                alert("信息不完整");
            }
            else if(alter_name === "" || alter_contact === "" || alter_area1 === "" || alter_location1 === "" ){
                alert("信息不完整");
            }
            else{
                var location = alter_area1+"#"+alter_location1;
                if(alter_area2 !== "" && alter_location2 !== "") location += ";"+alter_area2+"#"+alter_location2;
                if(alter_area3 !== "" && alter_location3 !== "") location += ";"+alter_area3+"#"+alter_location3;
                $.ajax({
                    url:"../client/ChangeInfo",
                    type:'POST',
                    async: false,
                    data:{
                        "id":client_id,
                        "name":alter_name,
                        "contact":alter_contact,
                        "location":location,
                    },
                    success:function(data){
                        alert(data);
                        window.location.href = "/yummy/client/modifyInfoPage";
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
    <h1>Modify Your Client Info</h1>
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

    <h2>修改用户信息</h2>

    <div class="col-sm-9 text" style="height:100%;width:75%">
    <div class="form-row">
        <div class="field-label"><label for="client_name">用户名称（必填）：</label></div>
        <div class="field-widget"><input id="client_name" class="required" placeholder="" /></div>
    </div>

    <div class="form-row">
        <div class="field-label"><label for="client_contact">用户电话（必填）：</label></div>
        <div class="field-widget"><input id="client_contact" class="required" placeholder="" /></div>
    </div>

        <div class="form-row">
            <div class="field-label"><label for="area1">区域1（必填）：</label></div>
            <div class="field-widget">
                <select id="area1" name="field6" class="validate-selection" title="choose menu type">
                    <option value="">无</option>
                    <option value="南京">南京</option>
                    <option value="上海">上海</option>
                    <option value="北京">北京</option>
                    <option value="广东">广东</option>
                    <option value="重庆">重庆</option>
                </select>
            </div>
            <div class="field-label"><label for="location1">具体地址1（必填）：</label></div>
            <div class="field-widget"><input id="location1" class="required" placeholder="" /></div>
        </div>

        <div class="form-row">
            <div class="field-label"><label for="area2">区域2（选填）：</label></div>
            <div class="field-widget">
                <select id="area2" name="field6" class="validate-selection" title="choose menu type">
                    <option value="">无</option>
                    <option value="南京">南京</option>
                    <option value="上海">上海</option>
                    <option value="北京">北京</option>
                    <option value="广东">广东</option>
                    <option value="重庆">重庆</option>
                </select>
            </div>
            <div class="field-label"><label for="location2">具体地址2（选填）：</label></div>
            <div class="field-widget"><input id="location2" class="required" placeholder="" /></div>
        </div>

        <div class="form-row">
            <div class="field-label"><label for="area3">区域3（选填）：</label></div>
            <div class="field-widget">
                <select id="area3" name="field6" class="validate-selection" title="choose menu type">
                    <option value="">无</option>
                    <option value="南京">南京</option>
                    <option value="上海">上海</option>
                    <option value="北京">北京</option>
                    <option value="广东">广东</option>
                    <option value="重庆">重庆</option>
                </select>
            </div>
            <div class="field-label"><label for="location3">具体地址3（选填）：</label></div>
            <div class="field-widget"><input id="location3" class="required" placeholder="" /></div>
        </div>

        <div style="text-align:center;">
            <button id="release" onclick="requestModifyInfo()">提交修改信息</button>
        </div>
    </div>
</div>
</body>
</html>
