<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>

<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/ztp-min-bak.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>


<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workFlow.js"></script>

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单量查询</title>

</head>
<style type="text/css">
.detailDiv 
{  
    display: none;   
} 
#orderQuantity {
	tr td,th{border:1px solid #fff;}
	border-collapse: collapse;
}
.tdOne{
border:1.5px solid #6B6B6B;
border:1;
cellpadding:0;
cellspacing:0;
}
.tdTwo{
border:1px solid #6B6B6B;
border:1;
cellpadding:0;
cellspacing:0;
}
.grid table td {
    border-bottom: 1px dashed #e3e3e3;
    height: 30px;
    vertical-align: middle;
    font-size: x-small;
    color: #666;
    text-align: center;
}
</style>

<body style="background: white;">
	
<div class="grid" id="orderslist">
<%-- <form method="post" id="serchform" type="hidden" action='<%=request.getContextPath()%>/shop/admin/ordAuto!personnelOrderQuantityQueryByName.do'>

</form> --%>
<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/shop/admin/ordAuto!personnelOrderQuantityQueryByName.do'>	
		<table class="table table-bordered" id="orderQuantity"  style="border:1px solid #fff"  border="1" cellpadding="0" cellspacing="0" >
				<tbody id="table_B" >
					<tr  style="border:1px solid #6B6B6B"  border="1" cellpadding="0" cellspacing="0">
						<td class="tdTwo" style="width:9%">订单号</td>
						<td class="tdTwo" style="width:5%">商品名称</td>
						<td class="tdTwo" style="width:4%">客户姓名</td>
						<td class="tdTwo" style="width:6%">客户联系号码</td>
						<td class="tdTwo" style="width:4%">客户地址</td>
						<td class="tdTwo" style="width:4%">订单创建时间</td>
						<td class="tdTwo" style="width:4%">锁单人工号/姓名</td>
						<td class="tdTwo" style="width:4%">开户号码</td>
						<td class="tdTwo" style="width:4%">订单状态</td>
						<td class="tdTwo" style="width:4%">订单结束时间</td>
						<td class="tdTwo" style="width:10%">工单编号</td>
						<td class="tdTwo" style="width:4%">派给特投时间</td>
						<td class="tdTwo" style="width:4%">特投人员姓名/电话</td>
						<td class="tdTwo" style="width:4%">工单状态</td>
						<td class="tdTwo" style="width:4%">处理时间</td>
					</tr>
			 <grid:grid from="webpage" formId="serchform">
					<grid:body item="order">
						<grid:cell  style="width:9%"><a onclick="showIntentInfo('${order.order_id}');" name="intentDetails" ">${order.order_id}</a></grid:cell>
						<%-- <grid:cell  style="width:9%"><a href="javascript:void(0)" id="showDetail"  onclick="showIntentInfo()">${order.order_id}</a>   	</grid:cell>
						 --%>
						<grid:cell  style="width:5%">${order.goods_name}</grid:cell>
						<grid:cell  style="width:4%">${order.ship_name}</grid:cell>
						<grid:cell  style="width:6%">${order.ship_tel}</grid:cell>
						<grid:cell  style="width:4%">${order.ship_addr}</grid:cell>	
						<grid:cell  style="width:4%">${order.create_time}</grid:cell>
						<grid:cell  style="width:4%">${order.LOCK_ID}/${order.lock_name}</grid:cell>
						<grid:cell  style="width:4%">${order.acc_nbr}</grid:cell>
						<grid:cell  style="width:4%">${order.status}</grid:cell>
						<grid:cell  style="width:4%">${order.intent_finish_time}</grid:cell>
						<grid:cell  style="width:10%">${order.WORK_ORDER_ID}</grid:cell>
						<grid:cell  style="width:4%">${order.cast_time}</grid:cell>
						<grid:cell  style="width:4%">${order.operator_name}/${order.operator_num}</grid:cell>
						<grid:cell  style="width:4%">${order.status}</grid:cell>
						<grid:cell  style="width:4%">${order.update_time}</grid:cell>						
				</grid:body> 
			</grid:grid>		 
				</tbody>
			</table>
		<c:if test="${empty webpage }">
			<!-- 当页面还没读取数据的时候用来填充，以防查询条件下拉显示不全 -->
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
			<br></br>
	</c:if>
	</form>
	<div id="showIntentDetil"></div>
	</div>
	
<script type="text/javascript">

$(function() {
	
	Eop.Dialog.init({id:"showIntentDetil",modal:false,title:"意向单详情",width:"1080px"});
	
});

function showIntentInfo(order_id){
	$("#showIntentDetil").empty();
	Eop.Dialog.open("showIntentDetil");
	var url =ctx+"/shop/admin/orderIntentAction!intentDetails.do?ajax=yes&order_id="+order_id;
	$("#showIntentDetil").load(url); 
}
</script>
</body>

