<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/2 0002
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审批商家信息修改请求</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var JsonList = null;
        var SellerJsonObjArray = null;

        $(function(){
            fetchData();
            SellerJsonObjArray = JSON.parse(JsonList);
            for(var i=1;i<=SellerJsonObjArray[0].num;i++){
                add_new_info(SellerJsonObjArray[i].sid,SellerJsonObjArray[i].rid,
                    SellerJsonObjArray[i].origin_name,SellerJsonObjArray[i].origin_type,SellerJsonObjArray[i].origin_location,
                    SellerJsonObjArray[i].alter_name,SellerJsonObjArray[i].alter_type,SellerJsonObjArray[i].alter_location);
            }
        });

        function fetchData(){
            $.ajax({
                    url:"../manager/getUncheckedModifyRequest",
                    type:'POST',
                    async: false,
                    success:function(data){
                        JsonList = data;
                    },
                    error:function(){
                        alert("请求错误");
                    }
                }
            );
        }

        function add_new_info(sid,rid,origin_name,origin_type,origin_location,alter_name,alter_type,alter_location){
            var seller_info = document.getElementById("seller_info");
            var oneRow = seller_info.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            cell1.innerHTML = sid;
            cell2.innerHTML = rid;
            cell3.innerHTML = "店铺名称:"+origin_name+"<br>"+"店铺类型:"+origin_type+"<br>"+"店铺地址:"+origin_location;
            cell4.innerHTML = "店铺名称:"+alter_name+"<br>"+"店铺类型:"+alter_type+"<br>"+"店铺地址:"+alter_location;
            cell5.innerHTML = "<button id='AcceptButton' onclick='AcceptSeller(this)'>同意</button>" +
                "<button id='DenyButton' onclick='DenySeller(this)'>拒绝</button>";
            document.getElementById('AcceptButton').id = "a"+sid+"#"+rid;
            document.getElementById('DenyButton').id = "d"+sid+"#"+rid;
        }

        function AcceptSeller(o){
            var info = o.id.substring(1);
            var sid = info.split("#")[0];
            var rid = info.split("#")[1];

            $.ajax({
                url:'../manager/AcceptInfoChange',
                type:'post',
                async:false,
                data:{
                    "id":sid,
                    "rid": rid
                },
                success: function(data){
                    alert(data);
                    o.parentNode.innerHTML = "已同意";
                },
                error: function(){
                    alert('request error');
                }
            })
        }

        function DenySeller(o){
            var info = o.id.substring(1);
            var sid = info.split("#")[0];
            var rid = info.split("#")[1];

            $.ajax({
                url:'../manager/DenyInfoChange',
                type:'post',
                async:false,
                data:{
                    id: sid,
                    rid: rid
                },
                success: function(data){
                    alert(data);
                    o.parentNode.innerHTML = "已拒绝";
                },
                error: function(){
                    alert('request error');
                }
            })
        }

    </script>
</head>
<body>
<div class="page-container" style="height:100%;width:100%">
    <nav>
        <a href="/yummy/manager/checkNewSeller">审批新商家</a> |
        <a href="/yummy/manager/checkSellerInfo" class="selected">审批信息修改请求</a> |
        <a href="/yummy/manager/financePage">网站信息统计</a> |
        <a href="/yummy/home/index">返回主页</a>
    </nav>
    <h2>待审批修改信息清单</h2>
    <div class="col-sm-9 text" style="height:100%;width:75%">
        <div class="table-responsive" style="height:75%;width:100%">
            <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="seller_info">
                <tr>
                    <td>请求编号</td><td>商家编号</td><td>初始信息</td><td>修改信息</td><td>操作</td>
                </tr>
            </table>
        </div>
    </div>
</div>
</body>
</html>
