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
	<div class="searchformDiv" style="background: white;border-bottom:0px;border-right:0px;border-top:0px;" >
		<table>
			<tr>
				<th class="thstyle"><span class="red_mark">流转信息：</span></th>
				<td>
					<span id="span_flow_info" class="red_mark"></span>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle"><span class="red_mark" style="display: inline;">*</span>判断结果：</th>
				<td>
					<select id="web_condition_select" class="sl">
	            	</select>
				</td>
			</tr>
			
			<tr>
				<th class="thstyle">用户县分：</th>
				<td><select id="order_county_code1" name="order_county_code1"
					class="sl">
						<c:forEach items="${countyList}" var="ds">
							<option value="${ds.field_value}"
								${ds.field_value==acceptDetail.order_county_code? 'selected': ''}>${ds.field_value_desc}</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<th class="thstyle">预约受理姓名：</th>
				<td><input id="acceptName" name="acceptDetail.acceptName"
					value="${acceptDetail.acceptName}" class="accept"
					autocomplete="off" /></td>
			</tr>
			<tr>
				<th class="thstyle">预约受理身份证：</th>
				<td><input id="acceptCert" name="acceptDetail.acceptCert"
					value="${acceptDetail.acceptCert}" class="accept"
					autocomplete="off" /></td>
			</tr>
			<tr>
				<th class="thstyle">预约受理地址：</th>
				<td><input id="acceptAddr" name="acceptDetail.acceptAddr"
					value="${acceptDetail.acceptAddr}" class="accept"
					autocomplete="off" /></td>
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
	    <input type="hidden" id="order_id"  value="${order_id}" />
	    <a name="saveIntent" onclick="saveIntent();" class="blueBtns"><span>修改受理人信息</span></a>
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
	if(window.parent.canvas){
		for(var i=0;i<window.parent.canvas.connects.length;i++){
			var connect = window.parent.canvas.connects[i];
			
			if(node_index == connect.source_node_index){
				var html = "<option value='"+select_index+"'>"+connect.connect_label+"</option>";
				
				$("#web_condition_select").append(html);
				
				select_index++;
			}
		}
		
		var node = window.parent.canvas.nodes[node_index];
		$("#span_flow_info").html(node.remark);
	}
});

function runNodeManual(){
	var condition = $("#web_condition_select option:selected").html();
	var remark = $("#web_condition_remark").val();
	
	if(typeof(remark)=="undefined" || remark==null || ""==remark.trim()){
		alert("请填写备注信息！");
		return false;
	}
	
	if(window.parent.canvas){
		window.parent.canvas.runNodeManual(instance_id,condition,remark);
	}
}
function saveIntent(){
	var order_id = $("#order_id").val();
	if(order_id == null || order_id == "") {
		alert("异常：order_id为空");
		return;
	}
	var acceptName = $("#acceptName").val();
	var acceptCert = $("#acceptCert").val();
	var acceptAddr = $("#acceptAddr").val();
	var order_county_code = $("#order_county_code1").val();
	
	
	if("" == acceptName){
		alert("请输入预约受理姓名");
		return false;
	}
	
	if("" == acceptCert){
		alert("请输入预约受理身份证");
		return false;
	}
	
	console.log("order_county_code:"+order_county_code);
	
	if(confirm("请确认是否保存!")) {} else {
		return;
	}
	var param = {
			"intent.order_id":order_id,
			"intent.ship_name":acceptName,
			"intent.cert_num":acceptCert,
			"intent.ship_addr":acceptAddr,
			"intent.order_county_code":order_county_code
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
</script>

