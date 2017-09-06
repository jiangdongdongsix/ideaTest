<%--
  Created by IntelliJ IDEA.
  User: jiangdongdong.tp
  Date: 2017/8/31
  Time: 13:13
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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <script src="${ctx}/static/jquery/jquery-1.11.1.min.js"></script>
    <script src="${ctx}/static/jquery/unslider.min.js"></script>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/lib/css/init.css">
    <link rel="stylesheet" href="${ctx}/lib/css/queue.css">
    <script>
        var ctx = "<%=basePath%>";
    </script>
</head>
<body>
<div class="main">
    <div class="toolbar">
        <ul>
            <li class="title">自主预约排队机</li>
            <li class="time">Time</li>
        </ul>
    </div>
    <div class="banner" id="b04">
        <ul>
            <li><img src="${ctx}/static/images/carousel/pic1.png" alt=""  ></li>
            <li><img src="${ctx}/static/images/carousel/pic2.png" alt=""  ></li>
            <li><img src="${ctx}/static/images/carousel/pic3.png" alt=""  ></li>
        </ul>
        <a href="javascript:void(0);" class="unslider-arrow04 prev"><img class="arrow" id="al" src="${ctx}/static/images/icon/arrowl.png" alt="prev" width="20" height="35"></a>
        <a href="javascript:void(0);" class="unslider-arrow04 next"><img class="arrow" id="ar" src="${ctx}/static/images/icon/arrowr.png" alt="next" width="20" height="37"></a>
    </div>
    <div class="centerInfo">
        <div class="queueInfo">
            <input  type="button"  class="btn btn-success" value="排队抽号">
        </div>
        <div class="checkInfo" >
            <input type="button" class="btn btn-success " value="查看排队" >
        </div>
        <div class="remindInfo">
            <p style="display: inline-block;float: left;width: 10%"><img src="${ctx}/static/images/icon/plogo.png" alt="plogo"></p>
            <p>查看个人排队状态及入场验证，请将您收到的二维码放置感应区进行扫描，若无法验证，请联系店员</p>
        </div>
    </div>
    <div class="footer">
        <p><img src="${ctx}/static/images/qrcode.png" alt="qrcode"></p>
    </div>
</div>
</body>
<!-- 最后用js控制 -->
<script>
    $(document).ready(function(e) {
        var unslider04 = $('#b04').unslider({
                dots: true
            }),
            data04 = unslider04.data('unslider');

        $('.unslider-arrow04').click(function() {
            var fn = this.className.split(' ')[1];
            data04[fn]();
        });
    });

</script>
<script src = "${ctx}/lib/js/queueHome.js"></script>
</html>