<%--
  Created by IntelliJ IDEA.
  User: jiangdongdong.tp
  Date: 2017/8/31
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>排队取号</title>
    <!-- csslib -->
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/lib/css/init.css">
    <!-- JSlib -->
    <script src="${ctx}/static/jquery/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/keyboard.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery.spinner.js"></script>
</head>
<style>
    .main{width: 400px;margin: 10px auto;}
    /*清除浮动*/
    .clearFloat{zoom:1;}
    .clearFloat{content: "" ;display: block;overflow: hidden;clear: both;}
    /*设置外边距*/
    .marginSet{margin: 0 10px;}
    /*title*/
    .title {margin-bottom: 10px;border-bottom: 1px solid #ccc}
    .title .left img{width: 10px;height: 25px}
    .title ul>li{float: left;}
    .title ul .time{float: right;font-size: 20px;line-height: 30px;}
    .title ul span{display: inline-block;font-size: 20px;line-height: 30px;padding-left: 140px;}
    .dinnerNumber ul li{float: left;}
    .dinnerNumber ul li.font{float: right;}
    .dinnerNumber ul li input{text-align: center;font-size: 20px;}
    .toAddBorder{line-height: 50px;font-size: 20px;}
    .dinnerSeat ul li {float: left;}
    .dinnerSeat ul li.selectSeat{margin-left: 38px;}
    .dinnerSeat ul li.selectSeat input{height: 40px;line-height: 30px;margin-left: 30px;background: #ccc;outline: none;font-size: 18px}
    .operationStep{border-bottom: 1px solid #ccc;margin-bottom: 15px;}
    .tableInfo ul li.left_side{float: left;}
    .tableInfo ul li.center{float: left;margin-left: 70px;}
    .tableInfo ul li.right_side{float: right;}
    .tableInfo ul li.right_side{float: right;}
    .Tinfo tr:nth-child(1){font-size: 15px;}
    .Tinfo tr:nth-child(2){font-size: 20px;font-weight: bold;}
    .telInfo ul li{float: left;font-size: 20px;}
    .telInfo ul li input{float: left;margin-left: 30px}
    .suggest {font-size: 15px;margin-top: -15px;}
    .keyborard{margin-top: 70px;}
    .keyborardContent{width: 400px;}
    .keyborardLeft{width:300px;float: left;}
    .keyborardRight{float: right;background: #fff;width: 90px;margin-right: 8px;margin-top: 4px;}
    .keyborardLeft div{float:left;background-color: #ccc; border-radius: 2px; height:60px; width:94px; margin-left:4px;margin-top: 4px;}
    .keyborardLeft div input[type="button"]{width: 94px;height: 60px;font-size: 20px;}
    .keyborardRight div{width: 80px;height:252px;}
    .keyborardRight div input[type="button"]{width: 80px;height:252px;}
</style>
<script>
    var ctx = "<%=basePath%>";
    var id = ${queueId};
</script>
<body>
<div class="main">
    <div class="title clearFloat">
        <div class="marginSet">
            <ul>
                <li class="left"><a href="#"><img src="${ctx}/static/images/icon/arrowl.png" alt="left"></a></li>
                <li><span>排队取号</span></li>
                <li class="time">Time</li>
            </ul>
        </div>
    </div>
    <div class=" ">
        <!-- 选择用餐人数 -->
        <div class="dinnerNumber clearFloat operationStep">
            <div class="toAddBorder marginSet">
                <ul>
                    <li><span>用餐人数:</span></li>
                    <li><input type="text"  class="spinner"/></li>
                    <li class="font"><span>人</span></li>
                    <script type="text/javascript">
                        $('.spinner').spinner({
                            initValue:2,
                            minValue:2,
                            step:2,
                            maxValue:8,
                        });
                    </script>
                </ul>
            </div>
        </div>
        <!-- 选取座位 -->
        <div class="dinnerSeat clearFloat operationStep">
            <div class="toAddBorder marginSet">
                <ul>
                    <li><span>座位:</span></li>
                    <li class="selectSeat">
                        <input  type="button"  id = "seatauto" value="系统自动匹配">
                        <input  type="button"  id = "seatself" value="自选用餐位置">
                    </li>
                </ul>
            </div>
        </div>
        <!-- 显示当前餐桌信息 -->
        <div class="tableInfo clearFloat operationStep">
            <div class="marginSet">
                <ul>
                    <li class="left_side">
                        <table border="0" class="Tinfo">
                            <tr >
                                <th>餐桌类型</th>
                            </tr>
                            <tr>
                                <td>小桌(1-4人)</td>
                            </tr>
                        </table>
                    </li>
                    <li class="center">
                        <table border="0" class="Tinfo">
                            <tr >
                                <th>等待桌数</th>
                            </tr>
                            <tr class="tableInfoTitle">
                                <td>28桌</td>
                            </tr>
                        </table>
                    </li>
                    <li class="right_side">
                        <table border="0" class="Tinfo">
                            <tr>
                                <th>预估时间</th>
                            </tr>
                            <tr>
                                <td>>20分钟</td>
                            </tr>
                        </table>
                    </li>
                </ul>
            </div>

        </div>
        <!-- 填写手机号 -->
        <div class="telInfo clearFloat operationStep">
            <div class="marginSet ">
                <ul class="clearFloat">
                    <li class=""><span>手机号：</span></li>
                    <li><input type="text" style="border:0 ;outline: none;" name="telNumber" id="telNumber"></li>
                </ul>

            </div>
        </div>
        <p class="suggest marginSet">建议您输入手机号码，方便我们短信通知</p>
    </div>
    <div class="keyborard clearFloat">
        <div class="marginSet">
            <div class="keyborardContent clearFloat">
                <div class="keyborardLeft clearFloat" id="keyborardNumber">
                    <div><input type="button" value="1"></div>
                    <div><input type="button" value="2"></div>
                    <div><input type="button" value="3"></div>
                    <div><input type="button" value="4"></div>
                    <div><input type="button" value="5"></div>
                    <div><input type="button" value="6"></div>
                    <div><input type="button" value="7"></div>
                    <div><input type="button" value="8"></div>
                    <div><input type="button" value="9"></div>
                    <div><input type="button" value="清空"></div>
                    <div><input type="button" value="0"></div>
                    <div><input type="button" value="X"></div>
                </div>
                <div class="keyborardRight" >
                    <div>
                        <input type="button" value="立即取号">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    $(function(){
        $("#telNumber").on("focus",focusAction)
        var that ;
        var arr = [];
        function focusAction(){
            that = this;
            // function test(that){
            // 	return that;
            // }
            // return test
            // console.log(this);
            // this.value = "123"
        }
        $("#keyborardNumber input").on("click",clickAction);
        function clickAction(){
            arr.push(parseInt(this.value))
            that.value = arr.join("")
            console.log(arr.join(""));
        }
    })

</script>
<script src = "${ctx}/lib/js/queueUp.js"></script>
</html>
