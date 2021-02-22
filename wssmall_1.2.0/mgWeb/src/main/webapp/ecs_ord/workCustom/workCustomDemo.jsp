<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义流程DEMO</title>

<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workFlow.js"></script>
<link href="<%=request.getContextPath() %>/ecs_ord/workCustom/css/workCustom.css" rel="stylesheet" type="text/css" />

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

</head>

<body style="min-width: 150px;">
	<form method="post" id="cfgSearchform">
		<div class="searchformDiv" style="background: white;" >
			<table>
				<tr>
					<th class="table_td_width">订单编号：</th>
					<td class="table_td_width">
						<input id="order_id" type="text" class="ipttxt"/>
					</td>		
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						<input class="comBtn" type="button" onclick="startWorkFlow()" value="启动流程" style="margin-right:10px;"/>
					</td>	
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						<input class="comBtn" type="button" onclick="loadWorkFlow()" value="展示流程" style="margin-right:10px;"/>
					</td>		
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						<input class="comBtn" type="button" onclick="continueWorkFlow()" value="继续执行" style="margin-right:10px;"/>
					</td>	
				</tr>
				
				<tr>
					<th class="table_td_width">实例编号：</th>
					<td class="table_td_width">
						<input id="instance_id" type="text" class="ipttxt"/>
					</td>	
					
					<th class="table_td_width">分支条件：</th>
					<td class="table_td_width">
						<input id="condition" type="text" class="ipttxt"/>
					</td>		
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						<input class="comBtn" type="button" onclick="runNodeManual();" value="执行节点" style="margin-right:10px;"/>
					</td>		
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						<input class="comBtn" type="button" onclick="gotoNode();" value="跳转" style="margin-right:10px;"/>
					</td>	
				</tr>
				
				<tr>
					<th class="table_td_width"></th>
					<td class="table_td_width">
						<input class="comBtn" type="button" onclick="cancelWorkFlow();" value="取消" style="margin-right:10px;"/>
					</td>	
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						
					</td>		
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						
					</td>		
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
						
					</td>	
				</tr>
			</table>
		</div>
	</form>

	<div id ="work_flow_div"></div>
	
	<div id="nest_web_div" style="width: 100%;">
		<div>
			<iframe id="nest_web_frame" style="width: 100%;height: 300px;"></iframe>
		</div>
	</div>
	
	<div id="nodeEditDiag"></div>
	<div id="labelEditDiag"></div>
	<div id="dealerEditDiag"></div>
</body>

<script type="text/javascript">

var canvas;

$(function(){
	canvas = new WorkFlowCanvas("work_flow_div");
	
	canvas.init();
});

function loadWorkFlow(){
	var order_id = $("#order_id").val();
	
	canvas.loadWorkFlow(order_id);
}

function startWorkFlow(){
	var order_id = $("#order_id").val();
	
	canvas.startWorkFlow(order_id);
}

function continueWorkFlow(){
	var order_id = $("#order_id").val();
	
	canvas.continueWorkFlow(order_id);
}

function runNodeManual(){
	var instance_id = $("#instance_id").val();
	var condition = $("#condition").val();
	
	canvas.runNodeManual(instance_id,condition);
}

function gotoNode(){
	var instance_id = $("#instance_id").val();
	
	canvas.gotoNode(instance_id);
}

function cancelWorkFlow(){
	var order_id = $("#order_id").val();
	
	canvas.cancelWorkFlow(order_id);
}
</script>

