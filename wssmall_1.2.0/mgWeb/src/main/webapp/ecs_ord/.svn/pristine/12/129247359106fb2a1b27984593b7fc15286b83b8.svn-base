<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ include file="/commons/taglibs.jsp"%>

<%
  String order_id = (String)request.getAttribute("order_id");
%>

</style>

<body style="min-width: 150px;">
	<div id="nest_web_div" style="width: 100%;display: none;">
		<div class="pop_btn" align="center" style="margin-bottom: 20px;margin-top: 40px;">
			<span id="nest_web_title" style="font-size: 24px;"></span>
		</div>
		<div>
			<iframe id="nest_web_frame" style="width: 100%;height: 400px;"></iframe>
		</div>
	</div>
	
	<div>
		<input id="radio_hide_workflow" onclick="workflowInfoShift();" name="radion_workflow" type="radio" checked="checked" style="margin-left: 45px;"/>隐藏流程信息
		<input id="radio_show_workflow" onclick="workflowInfoShift();" name="radion_workflow" type="radio" style="margin-left: 30px;"/>显示流程信息
	</div>
	
	<div id="workflow_container_div" style="width: 100%;height: 800px;">
		<div class="pop_btn" align="center" style="margin-bottom: 20px;">
			<span style="font-size: 24px;">流程信息</span>
		</div>
   		<div id ="work_flow_div"></div>

		<div id="nodeEditDiag"></div>
		<div id="labelEditDiag"></div>
		<div id="dealerEditDiag"></div>
	</div>
</body>

<script type="text/javascript">

var canvas;

$(function(){
	var order_id = "${order_id}";
	
	canvas = new WorkFlowCanvas("work_flow_div");
	canvas.order_id = order_id;
	canvas.init();
});

function workflowInfoShift(){
	var isShow = $('#radio_show_workflow').is(":checked");
	
	if(isShow){
		$("#workflow_container_div").show();
	}else{
		$("#workflow_container_div").hide();
	}
}
</script>