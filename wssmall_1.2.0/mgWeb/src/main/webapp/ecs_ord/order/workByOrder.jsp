<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style type="text/css">
.red {
	color: red;
}
</style>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
<script type="text/javascript">
	$(function() {
		
		 $("#hide_params_tb").bind("click", function() {
			$("#params_tb").hide();
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
		});
		$("#show_params_tb").bind("click", function() {
			$("#params_tb").show();
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
		}); 
		$("#queryWorkByOrderId").bind("click",
				function() {
					var order_id = $("#order_id_work").val();
					var create_start = $("input[name='params.create_start']").attr("value");
					var create_end = $("input[name='params.create_end']").attr("value");
					/* if(order_id == "" && create_start == "" && create_end == ""){
						alert("查询参数不能全为空");
						return;
					} */
					$("#searchWorksListForm").attr("action", ctx + "/shop/admin/ordAuto!searchWorksListForm.do").submit();
				});
		Eop.Dialog.init({id:"show_workList",modal:true,title:"工单列表",width:'900px'});
		$("a[name='queryWorks']").bind("click",
				function() {
					//只有一条记录可以这么取值
					var order_id = $(this).attr("value");
					if(order_id == null || order_id==""){
						alert("异常：order_id为空");
						return;
					}
					Eop.Dialog.open("show_workList");
					var url= ctx+"/shop/admin/ordAuto!workList4Order.do?ajax=yes&order_id="+order_id;
					$("#show_workList").load(url,{},function(){});
				});
		/* var order_from = ${params.order_from};
		if(null!=order_from&&""=order_from){
			$("a[name='params.order_from']").val(order_from);
		};  */
	});
	 (function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {});
			$(this).bind("mouseover", function() {});
		};
	})(jQuery); 
</script>
	<div class="searchBx">
		<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
		<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display: none;">展开</a>
		<form action="/shop/admin/ordAuto!searchWorksListForm.do" method="post" id="searchWorksListForm" >
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>订单来源：</th>
						<td>
							<select name="params.order_from" class="ipt_new">
							<option value="">--请选择--</option>
								<c:forEach var="of" items="${order_from_list}">
									<option value="${of.value}" ${of.value==params.order_from?'selected':'' }>&nbsp;${of.value_desc}</option>
								</c:forEach>
							</select>
						</td>

						<th>订单号：</th>
						<td>
							<input type="text" id="order_id_work" name="params.order_id" value="${params.order_id}" style="width: 145px;" class="ipt_new">
						</td>
						<th>订单时间：</th>
						<td>
							<input type="text" name="params.create_start" value="${params.create_start}" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							-
	                    	<input type="text" name="params.create_end" value="${params.create_end}" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							<span style="width: 20px;"></span>
							<a href="javascript:void(0);" id="queryWorkByOrderId" class="dobtn" style="margin-left: 20px;">查询</a>
						</td>
						
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div>
		<form id="gridform" class="grid">
			<grid:grid from="webpage" formId="searchWorksListForm" action="/shop/admin/ordAuto!searchWorksListForm.do">
				<grid:header>
					<grid:cell>订单来源</grid:cell>
					<grid:cell>订单号</grid:cell>
					<grid:cell>订单时间</grid:cell>
					<grid:cell>工单状态</grid:cell>
					<grid:cell>详情</grid:cell>
				</grid:header>
				<grid:body item="work">
					<grid:cell>&nbsp;${work.order_from} </grid:cell>
					<grid:cell>&nbsp;${work.order_id} </grid:cell>
					<grid:cell>&nbsp;${work.order_time} </grid:cell>
					<grid:cell>&nbsp;${work.N}条未处理工单，${work.M}条已处理工单</grid:cell>
					<grid:cell> 
						<a href="javascript:void(0);" value="${work.order_id}" name="queryWorks" class="dobtn" style="margin-left: 20px;">查明明细</a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
	<div id="show_workList"></div>
	<div id="workDetail"></div>
