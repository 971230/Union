<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

AdminUser adminUser=ManagerUtils.getAdminUser();

String theme_source_from = "";

if(null != adminUser){
	theme_source_from =  adminUser.getTheme_source_from();
}
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/ztp-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/SelectTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
var theme_source_from = '<%=theme_source_from%>';
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;
</script>

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
	<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/shop/admin/ordAuto!getPersonnelOrderQuantityTableUrl.do?' style="display: none;">
		<div class="searchformDiv">
			<input type="hidden" class="ipttxt" id="username" name="username"  value="${username}"/>
		</div>
	</form>
	
	<div class="grid" >
		<form method="POST"  id="infoForm" >
			<grid:grid from="webpage"  formId="serchform" ajax="yes">
				<grid:header>
					<grid:cell width="9%">订单号</grid:cell>
					<grid:cell width="5%">商品名称</grid:cell>
					<grid:cell width="4%">客户姓名</grid:cell> 
					<grid:cell width="6%">客户联系号码</grid:cell> 
					<grid:cell width="4%">客户地址</grid:cell>
					<grid:cell width="5%">订单创建时间</grid:cell>
					<grid:cell width="4%">锁单人工号/姓名</grid:cell>
					<grid:cell width="5%">开户号码</grid:cell>
					<grid:cell width="4%">订单状态</grid:cell>
					<grid:cell width="5%">订单结束时间</grid:cell>
					<grid:cell width="10%">工单编号</grid:cell>
					<grid:cell width="4%">派给特投时间</grid:cell>
					<grid:cell width="4%">特投人员姓名/电话</grid:cell>
					<grid:cell width="4%">工单状态</grid:cell>
					<grid:cell width="4%">处理时间</grid:cell>
				</grid:header>
				
				<grid:body item="order">
					<grid:cell  style="width:9%"><a onclick="showIntentInfo('${order.order_id}');" name="intentDetails" ">${order.order_id}</a></grid:cell>
					<grid:cell  style="width:5%">${order.goods_name}</grid:cell>
					<grid:cell  style="width:4%">${order.ship_name}</grid:cell>
					<grid:cell  style="width:6%">${order.ship_tel}</grid:cell>
					<grid:cell  style="width:4%">${order.ship_addr}</grid:cell>	
					<grid:cell  style="width:5%">${order.create_time}</grid:cell>
					<grid:cell  style="width:4%">${order.LOCK_ID}/${order.lock_name}</grid:cell>
					<grid:cell  style="width:6%">${order.acc_nbr}</grid:cell>
					<grid:cell  style="width:4%">${order.order_status}</grid:cell>
					<grid:cell  style="width:4%">${order.intent_finish_time}</grid:cell>
					<grid:cell  style="width:10%">${order.WORK_ORDER_ID}</grid:cell>
					<grid:cell  style="width:4%">${order.cast_time}</grid:cell>
					<grid:cell  style="width:4%">${order.operator_name}/${order.operator_num}</grid:cell>
					<grid:cell  style="width:4%">${order.work_status}</grid:cell>
					<grid:cell  style="width:4%">${order.update_time}</grid:cell>		
				</grid:body>  
			</grid:grid>
		</form>
	</div>
	
	<div id="showIntentDetil"></div>
	</div>
</body>

<script type="text/javascript">
$(function() {
	Eop.Dialog.init({id:"showIntentDetil",modal:false,title:"意向单详情",width:"95%",window:"vcenter"});
});
function showIntentInfo(order_id){
	$("#showIntentDetil").empty();
	Eop.Dialog.open("showIntentDetil");
	var url =ctx+"/shop/admin/orderIntentAction!intentDetails.do?ajax=yes&order_id="+order_id;
	$("#showIntentDetil").load(url); 
}
</script>


