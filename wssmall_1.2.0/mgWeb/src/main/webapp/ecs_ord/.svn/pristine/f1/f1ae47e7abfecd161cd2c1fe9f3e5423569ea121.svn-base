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
<title>政企订单审核</title>

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
	width: 180px;
	text-align: right;
	-web-kit-appearance:none;
 		-moz-appearance: none;
 		font-size:1.0em;
	height:1.5em;
	outline:0;
}

.accept{
	width: 180px;
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
	width: 180px;
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
	<div class="searchformDiv" style="background: white;border-bottom:0px;border-right:0px;border-top:0px;" >
		<table>
			<tr>
				<td class="thstyle"><span class="red_mark">流转信息：</span></td>
				<td colspan="5">
					<span id="span_flow_info" class="red_mark"></span>
				</td>
			</tr>
			
			<tr height="20px">
			</tr>
			
			<tr>
				<td class="thstyle">用户姓名：</td>
				<td>
					<input type="text" id="customer_name" class="accept" disabled="disabled"/>
				</td>
				
				<td class="thstyle">联系人姓名：</td>
				<td>
					<input type="text" id="ship_name" class="accept" disabled="disabled"/>
				</td>
				
				<td class="thstyle">联系电话：</td>
				<td>
					<input type="text" id="ship_tel" class="accept" disabled="disabled"/>
				</td>
			</tr>
			
			<tr>
				<td class="thstyle">联系电话2：</td>
				<td>
					<input type="text" id="ship_tel2" class="accept" disabled="disabled"/>
				</td>
				
				<td class="thstyle">联系地址：</td>
				<td colspan="3">
					<input type="text" id="ship_addr" class="accept" disabled="disabled" style="width: 540px;"/>
				</td>
			</tr>
			
			<tr>
				<td class="thstyle">商品编号：</td>
				<td>
					<input type="text" id="goods_id" class="accept" disabled="disabled"/>
				</td>
				
				<td class="thstyle">商品名称：</td>
				<td colspan="3">
					<input type="text" id="goods_name" class="accept" disabled="disabled" style="width: 540px;"/>
				</td>
			</tr>
			
			<tr>
				<td class="thstyle">商品小类编号：</td>
				<td>
					<input type="text" id="cat_id" class="accept" disabled="disabled"/>
				</td>
				
				<td class="thstyle">商品小类：</td>
				<td colspan="3">
					<input type="text" id="cat_name" class="accept" disabled="disabled" style="width: 540px;"/>
				</td>
			</tr>
			
			<tr>
				<td class="thstyle">创建时间：</td>
				<td>
					<input type="text" id="create_time" class="accept" disabled="disabled"/>
				</td>
				
				<td class="thstyle">订单备注：</td>
				<td colspan="3">
					<input type="text" id="remark" class="accept" disabled="disabled" style="width: 540px;"/>
				</td>
			</tr>
			
			<tr height="20px">
			</tr>
			
			<tr>
				<td class="thstyle"><span class="red_mark" style="display: inline;">*</span>审核结果：</td>
				<td colspan="5">
					<select id="web_condition_select" class="sl">
	            	</select>
				</td>
			</tr>
			
			<tr>
				<td class="thstyle">审核备注：</td>
				<td colspan="5">
					<textarea rows="5" cols="138" id="web_condition_remark" ></textarea>
				</td>
			</tr>
		</table>
	</div>
		
	<div class="pop_btn" align="center">
		<a onclick="runNodeManual();" class="blueBtns"><span>提交</span></a>
	</div>
</body>

<script type="text/javascript">
var order_id = "<%=order_id%>";
var workflow_id = "<%=workflow_id%>";
var instance_id = "<%=instance_id%>";
var node_index = "<%=node_index%>";
var select_index = 1;

$(function(){
	if(window.parent.canvas){
		var setSelect = false;
		var hasSource = false;
		
		for(var i=0;i<window.parent.canvas.connects.length;i++){
			var connect = window.parent.canvas.connects[i];
			
			if(node_index == connect.source_node_index){
				var html = "";
				
				if(!setSelect && connect.connect_label.indexOf("取消")<0){
					html = "<option value='"+select_index+"' selected='selected'>"+connect.connect_label+"</option>";
					setSelect = true;
				}else{
					html = "<option value='"+select_index+"'>"+connect.connect_label+"</option>";
				}
				
				$("#web_condition_select").append(html);
				
				select_index++;
			}
		}
		
		var node = window.parent.canvas.nodes[node_index];
		$("#span_flow_info").html(node.remark);
	}
	
	var param = {
		"order_id":order_id
	};
	
	var intent;
	var can_update;
	
	$.ajax({
     	url:ctx+"/shop/admin/orderIntentAction!getOrderIntentInfoByOrderId.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				intent = reply["intent"];
     				
     				$("#customer_name").val(intent.customer_name);
     				$("#ship_name").val(intent.ship_name);
     				$("#ship_tel").val(intent.ship_tel);
     				$("#ship_tel2").val(intent.ship_tel2);
     				$("#ship_addr").val(intent.ship_addr);
     				
     				$("#goods_id").val(intent.goods_id);
     				$("#goods_name").val(intent.goods_name);
     				$("#cat_id").val(intent.cat_id);
     				$("#cat_name").val(intent.cat_name);
     				$("#create_time").val(intent.create_time);
     				
     				$("#remark").val(intent.remark);
     			}else{
     				var msg = reply["msg"];
     				alert("查询意向单信息失败："+msg);
     			}
     		}else{
     			alert("查询意向单信息失败");
     		}
     	},
     	error:function(msg){
     		alert("查询意向单信息失败："+msg);
     	}
	});
});

function runNodeManual(){
	var condition = $("#web_condition_select option:selected").html();
	var remark = $("#web_condition_remark").val();
	
	if(typeof(remark)=="undefined" || remark==null || ""==remark.trim()){
		remark = "";
	}
	
	if(window.parent.canvas){
		window.parent.canvas.runNodeManual(instance_id,condition,remark);
	}
}

</script>

