<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/2/24 0024
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单编辑</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var seller_info = null
        var seller_name = null;
        var seller_rid = null;
        var JsonList = null;
        var MenuJsonObjArray = null;

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
            reload_allmenu(seller_rid);
        });

        function releaseMenu(){
            var menu_name = document.getElementById("menu_name").value;
            var menu_type = document.getElementById("menu_type").value;
            var menu_desc = document.getElementById("menu_desc").value;
            var menu_startDate = document.getElementById("menu_startDate").value;
            var menu_endDate = document.getElementById("menu_endDate").value;
            var menu_num = document.getElementById("menu_num").value;
            var menu_price = document.getElementById("menu_price").value;
            var currentDate = getCurrentDate();
            var reg1=/^[0-9]*[1-9][0-9]*$/;
            var reg2=/^\d+(\.\d{1,2})?$/;
            if(menu_name === null || menu_type === null || menu_desc === null|| menu_startDate === null|| menu_endDate === null|| menu_type === null || menu_num === null || menu_price === null){
                alert("信息不完整");
            }
            else if(menu_name === "" || menu_type === "" || menu_desc === ""|| menu_startDate === ""|| menu_endDate === ""|| menu_type === "" || menu_num === "" || menu_price === ""){
                alert("信息不完整");
            }
            else if(compareDate(menu_startDate,menu_endDate) === false){
                alert("开售日期不可晚于停售日期");
            }
            else if(compareDate(currentDate,menu_startDate) === false){
                alert("开售日期不可早于当前日期");
            }
            else if(!reg1.test(menu_num)){
                alert("供应量参数非法");
            }
            else if(!reg2.test(menu_price)){
                alert("价格参数非法");
            }
            else{
                $.ajax({
                    url:"../seller/addNewMenu",
                    type:'POST',
                    async: false,
                    data:{
                        "menu_name":menu_name,
                        "menu_type":menu_type,
                        "menu_desc":menu_desc,
                        "menu_startDate":menu_startDate,
                        "menu_endDate":menu_endDate,
                        "menu_num":menu_num,
                        "menu_price":menu_price,
                        "rid":seller_rid
                    },
                    success:function(data){
                        if(data!=="success"){
                            alert("添加失败");
                        }
                        else{
                            alert("添加成功");
                            reload_allmenu(seller_rid);
                        }
                    },
                    error:function(){
                        alert("请求错误");
                    }
                });
            }
        }

        function reload_allmenu(rid){
            var seller_info = document.getElementById("allmenu");
            while( seller_info.rows.length > 1){
                seller_info.deleteRow(seller_info.rows.length-1);
            }
            $.ajax({
               url:"../seller/getSellerMenu",
                type:'POST',
                async: false,
                data:{
                   "rid":rid
                },
                success:function(data){
                    JsonList = data;
                },
                error:function(){
                    alert("请求错误");
                }
            });
            MenuJsonObjArray = JSON.parse(JsonList);
            for(var i=1;i<=MenuJsonObjArray[0].num;i++){
                add_menu_info(MenuJsonObjArray[i].mid,MenuJsonObjArray[i].name,MenuJsonObjArray[i].type,MenuJsonObjArray[i].startDate,MenuJsonObjArray[i].endDate
                ,MenuJsonObjArray[i].num,MenuJsonObjArray[i].price);
            }
        }

        function add_menu_info(id,name,type,startDate,endDate,num,price){
            var currentDate = getCurrentDate();
            var seller_info = document.getElementById("allmenu");
            var oneRow = seller_info.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            var cell6 = oneRow.insertCell();
            var cell7 = oneRow.insertCell();
            var cell8 = oneRow.insertCell();
            var cell9 = oneRow.insertCell();
            cell1.innerHTML = id;
            cell2.innerHTML = name;
            cell3.innerHTML = type;
            cell4.innerHTML = startDate;
            cell5.innerHTML = endDate;
            cell6.innerHTML = num;
            cell7.innerHTML = price;
            if(compareDate(currentDate,startDate)){
                cell8.innerHTML = "即将到来";
            }
            else if(compareDate(endDate,currentDate)){
                cell8.innerHTML = "已过期";
            }
            else{
                cell8.innerHTML = "正在进行";
            }
            cell9.innerHTML = "<button id='DeleteButton' onclick='DeleteMenu(this)'>删除</button>";
            document.getElementById('DeleteButton').id = id;
        }

        function DeleteMenu(o){
            var mid = o.id;

            $.ajax({
                url:'../seller/DeleteMenu',
                type:'post',
                async:false,
                data:{
                    mid: mid
                },
                success: function(data){
                    alert(data);
                    o.parentNode.innerHTML = "已删除";
                },
                error: function(){
                    alert('request error');
                }
            })
        }

        function getCurrentDate(){
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() +1;
            var strDate = date.getDate();
            if(month >=1 && month<=9){
                month = "0" + month;
            }
            if(strDate >=0 && strDate<=9){
                strDate ="0"+strDate;
            }
            var currentDate = year+"-"+month+"-"+strDate;
            return currentDate;
        }

        function compareDate(date1,date2){
            var dt1 = new Date(date1);
            var dt2 = new Date(date2);
            if(dt1.getTime()<dt2.getTime()){
                return true;
            }
            else{
                return false;
            }
        }

    </script>
</head>
<body>
<div class="page-container" style="height:100%;width:100%">
    <h1>Customize Your Exclusive Menu</h1>
    <nav>
        <a href="/yummy/seller/main" class="selected">店家首页</a> |
        <a href="/yummy/seller/modifyInfoPage">修改信息</a> |
        <a href="/yummy/seller/customizePage" class="selected">自定义菜单</a> |
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

        <div class="col-sm-9 text" style="height:100%;width:75%">
            <br>
            <p id="task"  style="text-align:center;"></p>
            <HR   width="80%" color=#000000 SIZE=3>
            <div class="table-responsive" style="height:75%;width:100%">
                <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="allmenu">
                    <tr>
                        <td>餐点编号</td><td>餐点名称</td><td>餐点类型</td><td>开售日期</td><td>停售日期</td><td>供应量（份）</td><td>价格（元）</td><td>状态</td><td>操作</td>
                    </tr>

                </table>
            </div>

    <h2>添加菜单</h2>

    <div class="form-row">
        <div class="field-label"><label for="menu_name">商品名称：</label></div>
        <div class="field-widget"><input id="menu_name" class="required" placeholder="" /></div>
    </div>

     <div class="form-row">
         <div class="field-label"><label for="menu_type">商品类型：</label></div>
         <div class="field-widget">
             <select id="menu_type" name="field6" class="validate-selection" title="choose menu type">
                 <option value="套餐">套餐</option>
                 <option value="单品">单品</option>
             </select>
         </div>
     </div>

    <div class="form-row">
        <div class="field-label"><label for="menu_desc">商品描述：</label></div>
        <div class="field-widget"><input id="menu_desc" class="required" placeholder="" /></div>
    </div>

     <div class="form-row">
         <div class="field-label"><label for="menu_num">商品数量(份)：</label></div>
         <div class="field-widget"><input id="menu_num" class="required" placeholder="" /></div>
     </div>

     <div class="form-row">
         <div class="field-label"><label for="menu_price">商品价格(RMB)：</label></div>
         <div class="field-widget"><input id="menu_price" class="required" placeholder="" /></div>
     </div>

    <div class="form-row">
        <div class="field-label"><label for="menu_startDate">上架日期：</label></div>
        <div class="field-widget"><input type="date" id="menu_startDate" class="required" placeholder="请输入商品上架日期" /></div>
    </div>

    <div class="form-row">
        <div class="field-label"><label for="menu_endDate">下架日期：</label></div>
        <div class="field-widget"><input type="date" id="menu_endDate" class="required" placeholder="请输入商品下架日期" /></div>
    </div>

     <div style="text-align:center;">
        <button id="release" onclick="releaseMenu()">添加餐点</button>
     </div>
        </div>

</div>
</body>
</html>
