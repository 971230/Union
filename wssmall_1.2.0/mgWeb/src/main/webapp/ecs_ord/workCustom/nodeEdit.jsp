<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
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
  
</style>

<body>
	<div id="node_edit_div" class="searchformDiv" style="background: white;border: 0px;" >
		<table>
			
			<tr>
				<th><span class="red_mark" style="display: inline;">*</span>环节名称：</th>
				<td>
					<input id="node_name" type="text" class="ipttxt" />
				</td>
			</tr>
			
			<tr>
				<th>环节编码：</th>
				<td>
					<input id="node_code" type="text" class="ipttxt" />
				</td>
			</tr>
			
			<tr>
				<th>旧环节编码：</th>
				<td>
					<input id="old_node_code" type="text" class="ipttxt" title="规则流程环节编码"/>
				</td>
			</tr>
			
			<tr>
				<th>外部规则编码：</th>
				<td>
					<input id="out_node_code" type="text" class="ipttxt" title="传给外部系统的规则编码"/>
				</td>
			</tr>
			
			<tr>
				<th><span class="red_mark" style="display: inline;">*</span>环节类型：</th>
				<td>
					<select id="node_type" class="table_select" disabled="disabled">
						<option value="start">开始</option>
						<option value="back_condition">后台选择环节</option>
						<option value="web_condition">前台选择环节</option>
						<option value="auto">后台处理环节</option>
						<option value="web">前台处理环节</option>
						<option value="back_wait">后台等待环节</option>
						<option value="web_wait">前台等待环节</option>
						<option value="end">结束</option>
	            	</select>
				</td>	
			</tr>
			
			<tr>
				<th id="th_deal_url"></th>
				<td>
					<input id="deal_url" type="text" class="ipttxt" style="width: 400px;" />
				</td>
			</tr>
			
			<tr id="tr_deal_type">
				<th>后台处理方式：</th>
				<td>
					<select id="deal_type" class="table_select">
						
	            	</select>
				</td>
			</tr>
			
			<tr id="tr_deal_bean">
				<th id="th_deal_bean"></th>
				<td>
					<input id="deal_bean" type="text" class="ipttxt" style="width: 340px;" />
					<input class="comBtn" type="button" id="ruleSearchBtn" value="查找" style="margin-right:10px;"/>
				</td>
			</tr>
			
			<tr class="tr_deal_param"  style="display: none;">
				<th ></th>
				<td>
					<span class="red_mark">请实现doBusi方法，该方法可以访问订单树的JS对象orderTree</span>
				</td>
			</tr>
			
			<tr class="tr_deal_param"  style="display: none;">
				<th >JS：</th>
				<td>
					<textarea id="deal_param" rows="4" cols="61"></textarea>
				</td>
			</tr>
			
			<tr class="tr_nodeattr_dealer" style="display: none;">
				<th>处理单位类型：</th>
				<td>
					<select id="nodeattr_dealer_type" class="table_select">
						<option value="org">组织</option>
						<option value="person">人员</option>
						<option value="team">团队</option>
						<option value="default">省中台</option>
	            	</select>
				</td>
			</tr>
			
			<tr class="tr_nodeattr_dealer" style="display: none;">
				<th>处理单位编码：</th>
				<td>
					<input id="nodeattr_dealer_code" type="text" class="ipttxt" />
				</td>
			</tr>
			
			<tr class="tr_nodeattr_dealer" style="display: none;">
				<th>处理单位名称：</th>
				<td>
					<input id="nodeattr_dealer_name" type="text" class="ipttxt" />
				</td>
			</tr>
			
			<tr class="tr_nodeattr_dealer" style="display: none;">
				<th>处理人编码：</th>
				<td>
					<input id="nodeattr_curr_user_id" type="text" class="ipttxt" />
				</td>
			</tr>
			
			<tr class="tr_nodeattr_dealer" style="display: none;">
				<th>处理人名称：</th>
				<td>
					<input id="nodeattr_curr_user_name" type="text" class="ipttxt" />
				</td>
			</tr>
			
			<tr>
				<th id="th_remark">备注：</th>
				<td>
					<textarea id="remark" rows="4" cols="61"></textarea>
				</td>
			</tr>
			
			<tr id="tr_edit_btn">
				<th></th>
				<td>
					<div style="margin-top: 20px;">
				    	<input class="comBtn" type="button" onclick="setNode();" value="确定" style="margin-left:150px;"/>
				    	<input class="comBtn" type="button" onclick="removeNode();" value="删除" style="margin-left:10px;"/>
					</div>
				</td>
			</tr>
			
			<tr class="tr_rollback" style="display: none;">
				<th id="th_rollback_remark">回退备注：</th>
				<td>
					<textarea id="rollback_remark" rows="4" cols="61"></textarea>
				</td>
			</tr>
			
			<tr class="tr_rollback" style="display: none;">
				<th></th>
				<td>
					<div style="margin-top: 20px;">
				    	<input id="rollback_btn" class="comBtn" type="button" onclick="rollback();" value="回退到上一环节" style="margin-left:120px;"/>
					</div>
				</td>
			</tr>
			
			<tr class="tr_flow_btn" style="display: none;">
				<th>分支条件：</th>
				<td>
					<input id="nodeattr_run_condition" type="text" class="ipttxt" />
				</td>
			</tr>
			
			<tr class="tr_flow_btn" style="display: none;">
				<th id="th_flow_remark">跳转备注：</th>
				<td>
					<textarea id="goto_node_remark" rows="4" cols="61"></textarea>
				</td>
			</tr>
			
			<br>
			<tr class="tr_flow_btn" style="display: none;">
				<th></th>
				<td>
					<div style="margin-top: 20px;">
				    	<input id="run_node_btn" class="comBtn" type="button" onclick="runNodeManual();" value="执行本环节" style="margin-left:80px;"/>
				    	<input id="goto_node_btn" class="comBtn" type="button" onclick="gotoNode();" value="跳转到本环节" style="margin-left:10px;"/>
					</div>
				</td>
			</tr>
			
		</table>
	</div>
	
	<div id="select_rule_div"></div>
</body>

<script type="text/javascript">

$(function(){
	if(window.canvas)
		window.canvas.showNodeAttr();
	else if(window.WorkFlowTool)
		window.WorkFlowTool.canvas.showNodeAttr();
});	

function setNode(){
	if(window.canvas)
		windowcanvas.setNode();
	else if(window.WorkFlowTool)
		window.WorkFlowTool.canvas.setNode();
}

function removeNode(){
	if(window.canvas)
		window.canvas.removeNode();
	else if(window.WorkFlowTool)
		window.WorkFlowTool.canvas.removeNode();
}

function runNodeManual(){
	var condition = $("#nodeattr_run_condition").val();
	
	var canvasInstance;
	
	if(window.canvas)
		canvasInstance = window.canvas;
	else if(window.WorkFlowTool)
		canvasInstance = window.WorkFlowTool.canvas;
	
	if(typeof(canvasInstance) == "undefined" || canvasInstance == null){
		alert("流程画板JS对象为空");
		return false;
	}
	
	var node_index = $("#"+canvasInstance.curr_node).attr("node_index");
	
	if(typeof(canvasInstance.nodes[node_index])!="undefined" && canvasInstance.nodes[node_index]!=null){
		var node = canvasInstance.nodes[node_index];
		
		var instance_id = node.instance_id;
		
		canvasInstance.runNodeManual(instance_id,condition);
	}
}

function gotoNode(){
	var canvasInstance;
	
	if(window.canvas)
		canvasInstance = window.canvas;
	else if(window.WorkFlowTool)
		canvasInstance = window.WorkFlowTool.canvas;
	
	if(typeof(canvasInstance) == "undefined" || canvasInstance == null){
		alert("流程画板JS对象为空");
		return false;
	}
	
	var node_index = $("#"+canvasInstance.curr_node).attr("node_index");
	
	if(typeof(canvasInstance.nodes[node_index])!="undefined" && canvasInstance.nodes[node_index]!=null){
		var node = canvasInstance.nodes[node_index];
		
		var instance_id = node.instance_id;
		var remark = $("#goto_node_remark").val();
		
		canvasInstance.gotoNode(instance_id,remark);
	}
}

function rollback(){
	var canvasInstance;
	
	if(window.canvas)
		canvasInstance = window.canvas;
	else if(window.WorkFlowTool)
		canvasInstance = window.WorkFlowTool.canvas;
	
	if(typeof(canvasInstance) == "undefined" || canvasInstance == null){
		alert("流程画板JS对象为空");
		return false;
	}
	
	var node_index = $("#"+canvasInstance.curr_node).attr("node_index");
	
	if(typeof(canvasInstance.nodes[node_index])!="undefined" && canvasInstance.nodes[node_index]!=null){
		var node = canvasInstance.nodes[node_index];
		
		var instance_id = node.instance_id;
		var remark = $("#rollback_remark").val();
		
		canvasInstance.rollback(instance_id,remark);
	}
}

</script>

