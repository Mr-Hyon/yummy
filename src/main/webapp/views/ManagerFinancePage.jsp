<%--
  Created by IntelliJ IDEA.
  User: Hyon
  Date: 2019/3/16 0016
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>网站财务统计</title>
    <link rel="stylesheet" href="../statics/css/form.css">
    <link rel="stylesheet" href="../statics/css/Style.css">
    <link rel="stylesheet" href="../statics/css/bootstrap.css">
    <script type="text/javascript" src="../statics/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        var yummy_finance_info = null;
        $(function(){
            getFinanceData();
            var info2show = "客户数量：" + yummy_finance_info.user_num + "<br>" +
                "商家数量：" + yummy_finance_info.seller_num + "<br>" +
                "网站成功交易数：" + yummy_finance_info.complete_order_num + "<br>" +
                "网站退订交易数：" + yummy_finance_info.refund_order_num + "<br>" +
                "网站总收入(RMB)：" + yummy_finance_info.total_income + "<br>" +
                "最受欢迎店家：" + yummy_finance_info.most_welcome_seller + "<br>" +
                "最活跃客户：" + yummy_finance_info.most_common_user ;
            document.getElementById("some_info").innerHTML = info2show;
        });

        function getFinanceData(){
            $.ajax({
                    url:"../manager/getFinancialData",
                    type:'POST',
                    async: false,
                    success:function(data){
                        if(data!==null){
                            yummy_finance_info = JSON.parse(data);
                        }
                        else{
                            alert("财务信息获取失败");
                        }
                    },
                    error:function(){
                        alert("请求错误");
                    }
                }
            );
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
    <h2>网站信息统计</h2>
    <p id="some_info"></p>
</div>
</body>
</html>
