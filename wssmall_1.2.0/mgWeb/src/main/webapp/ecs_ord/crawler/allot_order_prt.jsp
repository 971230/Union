<%@page import="com.ztesoft.net.eop.sdk.user.IUserService"%>
<%@page import="com.ztesoft.net.eop.sdk.user.UserServiceFactory"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<html style="height: 160px; ">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调拨单打印</title>
<style>
html, body, div, span, applet, object, iframe, table, caption,
tbody, tfoot, thead, tr, th, td, del, dfn, em, font, img, ins,
kbd, q, s, samp, small, strike, strong, sub, sup, tt, var,
h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr,
acronym, address, big, cite, code, dl, dt, dd, ol, ul, li,
fieldset, form, label, legend {
	padding: 0;
	margin: 0;
	border: 0;
	list-style:none;
}
.db-item{width:940px; margin:0 auto;}
.db-item h2{ text-align:center; font-size:18px; color:#295e88; padding:8px 0;}
.db-item .db-date{ padding-right:40px; padding-bottom:10px; text-align:right; color:#295e88; font-size:15px;}
.table-bg{ font-size:15px; border:1px solid #295e88; border-collapse:collapse;}
.table-bg td{ border:1px solid #295e88; padding:6px; color:#295e88;}
.table-ex{ color:#295e88; font-size:15px; margin-top:6px;}
.table-ex td{ padding:6px; width:33%;}
</style>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
<script type="text/javascript">
	var g_num = 0;
	$(function(){
		$("td[goods_num='goods_num']").each(function(i,e){
			g_num += Number($(this).attr("num"));
		});
		$("td[total_num='total_num']").html(g_num);
	});
</script>

</head>

<body>
<div class="db-item">
	<h2>中国联通广州分公司调拨单</h2>
    <div class="db-date">调拨日期：${ship_time }</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-bg">
      <tr>
        <td colspan="2">收货部门</td>
        <td rowspan="2">类别</td>
        <td rowspan="2">物科名称</td>
        <td rowspan="2">数量</td>
        <td rowspan="2">单位</td>
        <td rowspan="2">备注</td>
      </tr>
      <tr>
        <td>区域</td>
        <td>自营厅</td>
      </tr>
     <!--  <tr>
        <td>天河区</td>
        <td>天河区新时空自营厅</td>
        <td>调拨</td>
        <td>4G通信终端-手机_iPhone6S 16G玫瑰金</td>
        <td>2</td>
        <td>台</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>天河区</td>
        <td>天河区新时空自营厅</td>
        <td>调拨</td>
        <td>4G通信终端-手机_iPhone6S Plus 64G金</td>
        <td>1</td>
        <td>台</td>
        <td>&nbsp;</td>
      </tr> -->
      <c:forEach items="${voList }"  var="vo">
      	<tr>
        <td>${vo.ship_city }</td>
        <td>${vo.channel_name }</td>
        <td>调拨</td>
        <td>${vo.goods_name }</td>
        <td goods_num="goods_num"  num="${vo.goods_num }">${vo.goods_num }</td>
        <td>台</td>
        <td>&nbsp;</td>
      </tr>
      </c:forEach>
      <!-- <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr> -->
      <tr>
        <td colspan="3">合计</td>
        <td>&nbsp;</td>
        <td total_num="total_num">&nbsp;</td>
        <td>&nbsp;台</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table-ex">
      <tr>
        <td>发货人：</td>
        <td>审核人：</td>
        <td>制单人：</td>
      </tr>
      <tr>
        <td>承运人：</td>
        <td>收货人：</td>
        <td>&nbsp;</td>
      </tr>
    </table>
</div>
</body>
</html>
