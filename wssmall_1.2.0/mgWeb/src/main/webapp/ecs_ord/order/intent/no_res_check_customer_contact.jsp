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
<title>客户联系页面</title>

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
	width: 580px;
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
	<div class="searchformDiv" style="background: white;border-bottom:0px;border-right:0px;border-top:0px;" >
		<table>
			<tr>
				<th class="thstyle"><span class="red_mark">流转信息：</span></th>
				<td>
					<span id="span_flow_info" class="red_mark"></span>
				</td>
			</tr>
		
			<tr>
				<th class="thstyle"><span class="red_mark" style="display: inline;">*</span>联系结果：</th>
				<td>
					<select id="web_condition_select" class="sl">
	            	</select>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle" disabled="disabled">用户县分：</th>
				<td>
					<select id="order_county_code1" name="order_county_code1" class="sl">
					</select>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle">预受理姓名：</th>
				<td>
					<input type="text" id="customer_contract_ship_name" class="accept" disabled="disabled"/>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle">预受理身份证：</th>
				<td>
					<input type="text" id="customer_contract_cert_num" class="accept" disabled="disabled"/>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle">预受理地址：</th>
				<td>
					<input type="text" id="customer_contract_ship_addr" class="accept" disabled="disabled"/>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle">备注：</th>
				<td>
					<textarea rows="5" cols="88" id="web_condition_remark" ></textarea>
				</td>
			</tr>
		</table>
	</div>
		
	<div class="pop_btn" align="center">
		<a id="update_customer_btn" name="saveIntent" onclick="updateCustomerInfo();" class="blueBtns" style="display: none;"><span>修改受理人信息</span></a>
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
     	url:ctx+"/shop/admin/orderIntentAction!getOrderIntentByOrderId.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				intent = reply["intent"];
     				countyList = reply["countyList"];
     				can_update = reply["can_update"];
     				
     				for(var i = 0; i < countyList.length; i++) {
     					var city = countyList[i];
     					$("#order_county_code1").append('<option value="' + city.field_value + '">'+city.field_value_desc+'</option>');
     				}
     				
     				$("#order_county_code1").val(intent.order_county_code);
     				
     				$("#customer_contract_ship_name").val(intent.ship_name);
     				$("#customer_contract_cert_num").val(intent.cert_num);
     				$("#customer_contract_ship_addr").val(intent.ship_addr);
     				
     				if("yes" == can_update){
     					$("#order_county_code1").removeAttr("disabled");
     					$("#customer_contract_ship_name").removeAttr("disabled");
     					$("#customer_contract_cert_num").removeAttr("disabled");
     					$("#customer_contract_ship_addr").removeAttr("disabled");
     					$("#update_customer_btn").show();
     				}
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

function updateCustomerInfo(){
	var ship_name = window.parent.WorkFlowTool.getInputValue($("#customer_contract_ship_name").val());
	var cert_num = window.parent.WorkFlowTool.getInputValue($("#customer_contract_cert_num").val());
	var ship_addr = window.parent.WorkFlowTool.getInputValue($("#customer_contract_ship_addr").val());
	var order_county_code = $("#order_county_code1").val();
	var remark = $("#web_condition_remark").val();

	if("" == ship_name){
		alert("请输入预约受理姓名");
		return false;
	}
	
	if("" == cert_num){
		alert("请输入预约受理身份证");
		return false;
	}
	
	var param = {
		"intent.order_id":order_id,
		"intent.ship_name":ship_name,
		"intent.cert_num":cert_num,
		"intent.ship_addr":ship_addr,
		"intent.order_county_code":order_county_code,
		"remark":remark
	};
	
	$.ajax({
     	url:ctx+"/shop/admin/orderIntentAction!updateOrderIntent.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				alert("修改受理人信息成功");
     			}else{
     				var msg = reply["msg"];
     				alert("修改受理人信息失败："+msg);
     			}
     		}else{
     			alert("修改受理人信息失败");
     		}
     	},
     	error:function(msg){
     		alert("修改受理人信息失败："+msg);
     	}
	});
}

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

function rollback(){
	var canvasInstance;
	
	if(window.parent.canvas)
		canvasInstance = window.parent.canvas;
	else{
		alert("流程画板JS对象为空");
		return false;
	}
	
	var remark = $("#web_condition_remark").val();
	
	if(typeof(remark)=="undefined" || remark==null || ""==remark.trim()){
		remark = "";
	}
	
	canvasInstance.rollback(instance_id,remark);
}
</script>

