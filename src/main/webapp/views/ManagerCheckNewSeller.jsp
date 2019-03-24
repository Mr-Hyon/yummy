<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/2/14 0014
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>审批新商家</title>
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
                add_new_info(SellerJsonObjArray[i].rid,SellerJsonObjArray[i].rname,SellerJsonObjArray[i].type,SellerJsonObjArray[i].location);
            }
        });

        function fetchData(){
            $.ajax({
                    url:"../manager/getUncheckedSellerInfo",
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

        function add_new_info(rid,rname,type,location){
            var seller_info = document.getElementById("seller_info");
            var oneRow = seller_info.insertRow();
            var cell1 = oneRow.insertCell();
            var cell2 = oneRow.insertCell();
            var cell3 = oneRow.insertCell();
            var cell4 = oneRow.insertCell();
            var cell5 = oneRow.insertCell();
            cell1.innerHTML = rid;
            cell2.innerHTML = rname;
            cell3.innerHTML = type;
            cell4.innerHTML = location;
            cell5.innerHTML = "<button id='AcceptButton' onclick='AcceptSellerInfo(this)'>同意</button>" +
                "<button id='DenyButton' onclick='DenySellerInfo(this)'>拒绝</button>";
            document.getElementById('AcceptButton').id = "a" + rid;
            document.getElementById('DenyButton').id = "d" + rid;
        }

        function AcceptSellerInfo(o){
            var rid = o.id.substring(1);

            $.ajax({
                url:'../manager/AcceptNewSeller',
                type:'post',
                async:false,
                data:{
                    rid: rid
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

        function DenySellerInfo(o){
            var rid = o.id.substring(1);

            $.ajax({
                url:'../manager/DenyNewSeller',
                type:'post',
                async:false,
                data:{
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
        <a href="/yummy/manager/checkNewSeller" class="selected">审批新商家</a> |
        <a href="/yummy/manager/checkSellerInfo">审批信息修改请求</a> |
        <a href="/yummy/manager/financePage">网站信息统计</a>
    </nav>
    <h2>待审批新商家清单</h2>
    <div class="col-sm-9 text" style="height:100%;width:75%">
        <div class="table-responsive" style="height:75%;width:100%">
        <table class="table table-striped table-bordered table-hover" width="100%" border="1px" id="seller_info">
            <tr>
                <td>商家编号</td><td>商家名称</td><td>商家类型</td><td>地址</td><td>操作</td>
            </tr>
        </table>
        </div>
    </div>
</div>
</body>
</html>
