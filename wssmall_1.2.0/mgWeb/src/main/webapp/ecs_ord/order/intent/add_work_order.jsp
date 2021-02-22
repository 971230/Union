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

String order_id = request.getParameter("order_id");
String workflow_id = request.getParameter("workflow_id");
String instance_id = request.getParameter("instance_id");
String node_index = request.getParameter("node_index");
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>
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

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基础分支选择页面</title>

<style>
.clickClass{
   background:#f7f7f7
}

.table_td_width{
	width: 12.5%;
}

.table_select{
	width: 162px;
	height: 24px;
}

.red_mark {
    color: red;
}

.thstyle {
	width: 150px;
	text-align: right;
	-web-kit-appearance:none;
 		-moz-appearance: none;
 		font-size:1.0em;
	height:1.5em;
	outline:0;
}

.accept{
	width: 400px;
	-web-kit-appearance:none;
 		-moz-appearance: none;
 		font-size:1.2em;
	height:1.5em;
	border-radius:4px;
	border:1px solid #c8cccf;
	color:#6a6f77;
	outline:0;
}

a{
	cursor:pointer;
	text-decoration:none;
}

textarea{
	border:1px solid #c8cccf;
	border-radius:4px;
}

.sl{
	width: 200px;
	appearance:none;
	-web-kit-appearance:none;
 		-moz-appearance: none;
	border:1px solid #c8cccf;
	border-radius:4px;
	padding-right: 14px;
}
</style>

</head>

<body style="background: white;">	
	<div id="error_msg_div" class="pop_btn" align="center">
		<span id="error_msg_span"></span>
	</div>
		
	<div class="pop_btn" align="center">
		<a name="saveIntent" onclick="runNodeManual();" class="blueBtns"><span>提交</span></a>
	</div>
</body>

<script type="text/javascript">
var order_id = "<%=order_id%>";
var workflow_id = "<%=workflow_id%>";
var instance_id = "<%=instance_id%>";
var node_index = "<%=node_index%>";
var select_index = 1;

$(function(){
	
});

function runNodeManual(){
	var condition = $("#web_condition_select option:selected").html();
	var remark = $("#web_condition_remark").val();
	
	if(window.parent.canvas){
		window.parent.canvas.runNodeManual(instance_id,condition,remark);
	}
}
</script>

