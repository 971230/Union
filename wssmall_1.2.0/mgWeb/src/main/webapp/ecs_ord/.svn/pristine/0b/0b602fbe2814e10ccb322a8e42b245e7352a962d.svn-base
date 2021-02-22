<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String username = request.getParameter("username");
String phone_num = request.getParameter("phone_num");
String create_end = request.getParameter("create_end");
String order_from = request.getParameter("order_from");
String order_region_code = request.getParameter("order_city_code");
String order_county_code = request.getParameter("order_county_code");
String order_role = request.getParameter("order_role");
String create_start = request.getParameter("create_start");

%>
<head>

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
	<div style='height:100%;'>
		<iframe id="query_order_frame" style='width:100%;height:100%;'  >	
		</iframe>
	</div>
</body>

<script type="text/javascript">

var username = "<%=username%>";
var phone_num = "<%=phone_num%>";
var order_region_code = "<%=order_region_code%>";
var order_county_code = "<%=order_county_code%>";
var order_role = "<%=order_role%>";
var create_start = "<%=create_start%>";
var create_end = "<%=create_end%>";
var order_from = "<%=order_from%>";

$(function() {
/* 	create_start = create_start.replace("--", " ");
	create_end = create_end.replace("--", " "); */
	
	var url = ctx+"/shop/admin/ordAuto!getPersonnelOrderQuantityTableUrl.do?ajax=yes";
	
	url += "&username="+username;
	url += "&phone_num="+phone_num;
	url += "&order_role="+order_role;
	url += "&order_city_code="+order_region_code;
	url += "&order_county_code="+order_county_code;
	url += "&create_start="+create_start;
	url += "&create_end="+create_end;
	url += "&order_from="+order_from;
	
	$("#query_order_frame").attr("src",url);
});

</script>


