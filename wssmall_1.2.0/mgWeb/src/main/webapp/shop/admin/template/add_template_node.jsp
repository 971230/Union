<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
	
.noborder {
    border-style: none;
}
-->
</style>

<div class="input" >
	<form class="validate" method="post" id="saveTemplateNodeForm" validate="true">
	<div>
		<div style="margin-left:10%;margin-top:5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody>
				<tr  style="display: none;">
					<th><span class="red">*</span>模板ID：</th>
					<td>
					<c:choose>
					<c:when test="${flag != 'add'}">
					     <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.order_template_id" id="order_template_id" value="${orderTemplateNode.order_template_id}" required="true" />
					</c:when>
					<c:otherwise>
					     <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.order_template_id" id="order_template_id" value="${orderTemplateId}" required="true" />
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr  style="display: none;">
					<th><span class="red">*</span>节点ID：</th>
					<td>
					<c:choose>
					<c:when test="${flag != 'add'}">
					    <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_id" id="node_id" value="${id}"/>
					</c:when>
					<c:otherwise>
					    <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_id" id="node_id" value="${orderTemplateNode.node_id}"/>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
					<th><span class="red">*</span>节点名称：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_name" id="node_name" value="${orderTemplateNode.node_name}"  required="true" /></td>
				</tr>
				<tr>
					<th><span class="red">*</span>节点编码：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_code" id="node_code" value="${orderTemplateNode.node_code}" onblur="setNodePath()" required="true" /></td>
				</tr>
				<tr>
					<th><span class="red">*</span>节点类型：</th>
					<td><html:selectdict curr_val="${orderTemplateNode.node_type}"  style="width:90px" id="node_type" name ="orderTemplateNode.node_type"  attr_code="DC_TEMPLATE_NODE_TYPE"></html:selectdict>
					</td>
				</tr>
				<tr  style="display: none;">
					<th>上级节点ID：</th>
					<td>
					<c:choose>
					<c:when test="${flag != 'add'}">
					    <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.super_node_id" id="super_node_id" value="${orderTemplateNode.super_node_id}"/>
					</c:when>
					<c:otherwise>
					     <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.super_node_id" id="super_node_id" value="${id}"/>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
					<th>上级节点：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.super_node_name" id="super_node_name" value="${nodeName}"/></td>
				</tr>
				<tr   style="display: none;">
					<th>节点路径：</th>
					<td>
					<c:choose>
					<c:when test="${flag == 'add'}">
					    <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_path" id="node_path" value="${nodePath}"/>
					    <input class="resigterIpt _x_ipt" style="display:none" type="text" dataType="string" name="orderTemplateNode.node_path" id="node_path_tmp" value="${nodePath}"/>
					</c:when>
					<c:otherwise>
					     <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_path" id="node_path" value="${orderTemplateNode.node_path}"/>
					     <input class="resigterIpt _x_ipt" style="display:none" type="text" dataType="string" name="orderTemplateNode.node_path" id="node_path_tmp" value="${orderTemplateNode.node_path}"/>
					</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr  style="display: none;">
					<th>节点深度：</th>
					<td>
					<c:choose>
					<c:when test="${flag == 'add'}">
					   <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_depth" id="node_depth" value="${nodeDepth}"/>
				    </c:when>
				    <c:otherwise>
				        <input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_depth" id="node_depth" value="${orderTemplateNode.node_depth}"/>
				    </c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr  style="display: none;">
					<th>节点归属实例表：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_table_code" id="node_table_code" value="${orderTemplateNode.node_table_code}"/>
					    <a href="javascript:void(0);" class="blueBtns" id="getNodeTableCode" ><span>选择</span></a>
					</td>
				</tr>
				<tr  style="display: none;">
					<th>节点归属实例字段：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_table_field_code" id="node_table_field_code" value="${orderTemplateNode.node_table_field_code}"/>
					</td>
				</tr>
				<tr>
					<th>节点解释辅助字段：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_read_comments" id="node_read_comments" value="${orderTemplateNode.node_read_comments}"/></td>
				</tr>
				<tr>
					<th>可重复节点标志：</th>
					<td><html:selectdict curr_val="${orderTemplateNode.node_repeat}"  style="width:90px" id="node_repeat" name ="orderTemplateNode.node_repeat"  attr_code="DC_TEMPLATE_NODE_REPEAT"></html:selectdict>
					</td>
				</tr>
				<tr>
					<th>可重复节点外层编码：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_repeat_desc" id="node_repeat_desc" value="${orderTemplateNode.node_repeat_desc}"/></td>
				</tr>
				<tr  style="display: none;">
					<th>节点状态：</th>
					<td><html:selectdict curr_val="${orderTemplateNode.node_status}"  style="width:90px" id="node_status" name ="orderTemplateNode.node_status"  attr_code="DC_TEMPLATE_NODE_STATUS"></html:selectdict>
					</td>
				</tr>
				<tr  style="display: none;">
					<th>节点值最大长度：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_value_length" id="node_value_length" value="${orderTemplateNode.node_value_length}"/></td>
				</tr>
				<tr  style="display: none;">
					<th>节点值正则表达式：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_exp" id="node_exp" value="${orderTemplateNode.node_exp}"/></td>
				</tr>
				<tr  style="display: none;">
					<th>节点注释：</th>
					<td><input class="resigterIpt _x_ipt" type="text" dataType="string" name="orderTemplateNode.node_comments" id="node_comments" value="${orderTemplateNode.node_comments}"/></td>
				</tr>
				</tbody>	
			</table>
		</div>
		<div class="pop_btn" align="center">
		 	<c:if test="${flag != 'view'}">
			<a id="savebtn" class="blueBtns"><span>保 存</span></a>
	     	<a id="resetbtn" class="blueBtns"><span>重 置</span></a>
	     	</c:if>
		</div>
	</div>
	<input type="hidden" id="flag" name="flag" value="${flag}" />
	<input type="hidden" id="nodeId" value="${id }" />
	</form>
</div>
<div id="choose_assembly_div"></div>
<script>

$(function (){
	
	//不可编辑字段
	$("#node_depth").attr("disabled","disabled");
	$("#node_path").attr("disabled","disabled");
	$("#super_node_name").attr("disabled","disabled");
	$("#node_table_code").attr("disabled","disabled");
	$("#node_table_field_code").attr("disabled","disabled");
	
	if ($("#flag").val() == "view"){
		$("#order_template_id").attr("disabled","disabled");
		$("#node_id").attr("disabled","disabled");
		$("#node_name").attr("disabled","disabled");
		$("#node_code").attr("disabled","disabled");
		$("#node_type").attr("disabled","disabled");
		$("#node_path").attr("disabled","disabled");
		$("#node_depth").attr("disabled","disabled");
		$("#super_node_id").attr("disabled","disabled");
		$("#node_table_code").attr("disabled","disabled");
		$("#node_table_field_code").attr("disabled","disabled");
		$("#node_comments").attr("disabled","disabled");
		$("#node_read_comments").attr("disabled","disabled");
		$("#node_repeat").attr("disabled","disabled");
		$("#node_status").attr("disabled","disabled");
		$("#node_value_length").attr("disabled","disabled");
		$("#node_exp").attr("disabled","disabled");
	}
	if ($("#flag").val() == "edit") {
		$("#order_template_id").attr("readonly","readonly");
		$("#node_id").attr("readonly","readonly");
	}
	
	$("#saveTemplateNodeForm").validate();
	$("#savebtn").click(function() {
		var  url = ctx+ "/shop/admin/ordTplAction!saveTemplateNode.do?ajax=yes";
		Cmp.ajaxSubmit('saveTemplateNodeForm', '', url, {}, function(responseText) {			
			if (responseText.result == 0) {
				alert(responseText.message);
				NodeManager.page_close();
				var nodeId = $("#node_id").val();
				var nodeName=$("#node_name").val();
				var nodeCode=$("#node_code").val();
				//$("a[id='root_id"+$("#node_id").val()+"']").click();
				//$("#root_id"+nodeId).html("<i class='treeic1'></i>"+$("#node_name").val());
				
				var td1=$("tr[id='"+nodeId+"']").find("td[field_name='node_name']");
				var td2=$("tr[id='"+nodeId+"']").find("td[field_name='node_code']").find("span");
				td1[0].innerText=nodeName;
				td2[0].innerText=nodeCode;
				
			} else {
				alert(responseText.message);
			}
		}, 'json');
	});	
});

function setNodePath(){
	var flag=$("#flag").val(); 
	if(flag=="add"){
		var nodePath=$("#node_path_tmp").val(); 
		var nodeCode=$("#node_code").val(); 
		nodePath=nodePath+nodeCode;
		$("#node_path").val(nodePath);
	}else{
		var nodePath=$("#node_path_tmp").val(); 
		var nodeCode=$("#node_code").val(); 
		if(nodePath.length>0){
			nodePath=nodePath.substring(0,nodePath.lastIndexOf("->"));
			nodePath=nodePath+"->"+nodeCode;
		}
		
		$("#node_path").val(nodePath);
	}
	
}

function setTableEnabled(){
	alert("dd");
}

var Rule = {
		init:function(){
			var self = this;
			Eop.Dialog.init({id:"choose_assembly_div",modal:false,title:"对象选择", height:"600px",width:"750px"});
			$("#getNodeTableCode").bind("click",function(){
				var self = this;
				Eop.Dialog.open("choose_assembly_div");
				Rule.selectObj(self);
			});
		},
		selectObj:function(cobj){
			var url = ctx+"/shop/admin/selectTemplateAction!selectTempleteComp.do?ajax=yes";
			var rel_table_name="";
			var field_name="";
			var obj1 = $("input[id='esAttrDef_rel_table_name']");
			var obj2 = $("input[id='esAttrDef_field_name']");
			if(obj1 && obj1.val()){
				rel_table_name = obj1.val();
			}
			if(obj2 && obj2.val()){
				field_name = obj2.val();
			}
			$("#choose_assembly_div").load(url,{nodeName:rel_table_name,nodePath:field_name},function(){
				$("#rule_obj_searchBtn").bind("click",function(){Rule.selectObj(cobj);});   //查询按钮
				$("#obj_select_confirm_btn").bind("click",function(){Rule.addSelectObj(cobj);});
				$("#obj_select_not_btn").bind("click",function(){Rule.addSelectObj(cobj);});
			});
		},addSelectObj:function(cobj){
			var obj_id = $("input[type='radio'][name='radionames']:checked").val();
			if(obj_id == undefined){
				alert("请选择一个对象");
				return ;
			}
			var obj_ids = obj_id.split("##");
			$("#node_table_code").val(obj_ids[0]!=undefined?obj_ids[0]:"");
			$("#node_table_field_code").val(obj_ids[1]!=undefined?obj_ids[1]:"");
			
			Eop.Dialog.close("choose_assembly_div");		
		}
};

$(function(){
	Rule.init();
 });

</script>
