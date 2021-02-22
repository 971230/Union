<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>V计划统计报表</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>

<style>

.searchformDiv table th {
	width: 30px;
}
.grid {
	height: 300px;
}

</style>

<body>

	<form method="post" id="serchform"
		action='<%=request.getContextPath()%>/shop/admin/ordAuto!vPlanOrderReportQuery.do'>
		<div class="searchformDiv">
			<div class="searchBx">
				<input type="hidden" name="params.query_btn_flag" value="yes" />
				<table id="params_tb" width="100%" border="0" cellspacing="0"
					cellpadding="0" class="tab_form">
					<tbody id="tbody_A">
						<tr>
							<td style="width: 50px;">活动名称： <select
								name="params.order_from" id="order_from">
									<option value="">--请选择--</option>
									<c:forEach items="${order_vplan_list}" var="of">
										<option value="${of.value}"
											${of.value==params.order_from?'selected':'' }
											>${of.value_desc }</option>
									</c:forEach>
							</select>
							</td>
							<td style="width: 300px;">创建时间： <input style="width: 150px;"
								type="text" name="params.create_start"
								value="${params.create_start }" readonly="readonly"
								class="ipt_new"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
									<input style="width: 150px;" type="text"
									name="params.create_end" value="${params.create_end }"
									readonly="readonly" class="ipt_new"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
						</tr>

						<tr>
							<td colspan="4"><input class="comBtn" type="button"
								name="searchBtn" id="searchBtn" value="查询"
								style="margin-right: 10px;" /> <input class="comBtn"
								type="button" name="excelReport" id="excelReport"
								value="导出V计划统计信息" style="margin-right: 10px;" /> 
								<input
								class="comBtn" type="button" name="excel" id="excelOrd"
								value="导出V计划订单信息" style="margin-right: 10px;" />
								</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form>

	<div class="grid" id="goodslist">
     <form method="POST"  id="intent_order_report"  >

      			<grid:grid from="webpage" formId="serchform" >
					<grid:header>
						<grid:cell width="50px">地市</grid:cell><%-- city_name--%>
						<grid:cell width="50px">王卡订单总量</grid:cell><%-- total--%>
						<grid:cell width="50px">王卡办理成功</grid:cell><%-- undeal--%>
						<grid:cell width="50px">宽带订单总量</grid:cell><%-- total--%>
						<grid:cell width="50px">有线订单量</grid:cell><%-- undeal--%>
						<grid:cell width="50px">有线办理成功</grid:cell><%-- undeal--%>
						<grid:cell width="50px">无线订单量</grid:cell><%-- undeal--%>
						<grid:cell width="50px">无线办理成功</grid:cell><%-- undeal--%>					
						<grid:cell width="50px">副卡订单总量</grid:cell><%-- total--%>
						<grid:cell width="50px">线上订单量</grid:cell><%-- total--%>
						<grid:cell width="50px">线上已开户</grid:cell><%-- undeal--%>
						<!-- 线下副卡 -->
						<grid:cell width="50px">线下订单量</grid:cell><%-- total--%>
						<!-- 结单原因 -->
						<grid:cell width="50px">不需要</grid:cell><%-- undeal--%>
						<grid:cell width="50px">不符合办理条件</grid:cell><%-- dealing--%>
						<grid:cell width="50px">测试单</grid:cell><%-- done--%>
						<grid:cell width="50px">开户其他号码</grid:cell><%-- can--%>
						<grid:cell width="50px">再考虑</grid:cell><%-- total--%>
						<grid:cell width="50px">需要邮寄</grid:cell><%-- undeal--%>
						<grid:cell width="50px">无法联系上</grid:cell><%-- dealing--%>
						<grid:cell width="50px">操作错误</grid:cell><%-- done--%>
						<grid:cell width="50px">其他</grid:cell><%-- can--%>
						<grid:cell width="50px">系统原因</grid:cell><%-- dealing--%>
						
						<grid:cell width="50px">未处理</grid:cell><%-- done--%>
						<grid:cell width="50px">工单已派发</grid:cell><%-- can--%>
						<grid:cell width="50px">办理成功</grid:cell><%-- can--%>
					</grid:header>
	
				    <grid:body item="order">
						<grid:cell  width="50px">${order.city}</grid:cell>  <%--地市名称--%>
						<grid:cell  width="50px">${order.counts1}</grid:cell>  <%--总量--%>
						<grid:cell  width="50px">${order.counts2}</grid:cell>  <%--未处理--%>
						<grid:cell  width="50px">${order.counts3}</grid:cell>  <%--处理中--%>
						<grid:cell  width="50px">${order.counts4}</grid:cell>  <%--已完成--%>
						<grid:cell  width="50px">${order.counts5}</grid:cell>  <%--退单--%>
						<grid:cell  width="50px">${order.counts6}</grid:cell>  <%--处理中--%>
						<grid:cell  width="50px">${order.counts7}</grid:cell>  <%--已完成--%>
						<grid:cell  width="50px">${order.counts8}</grid:cell>  <%--退单--%>
						<grid:cell  width="50px">${order.counts9}</grid:cell>  <%--处理中--%>
						<grid:cell  width="50px">${order.counts10}</grid:cell>  <%--已完成--%>
						<grid:cell  width="50px">${order.counts11}</grid:cell>  <%--退单--%>
						<!-- 结单原因 -->
						<grid:cell  width="50px">${order.counts12}</grid:cell>  <%--处理中--%>
						<grid:cell  width="50px">${order.counts13}</grid:cell>  <%--已完成--%>
						<grid:cell  width="50px">${order.counts14}</grid:cell>  <%--退单--%>
						<grid:cell  width="50px">${order.counts15}</grid:cell>  <%--处理中--%>
						<grid:cell  width="50px">${order.counts16}</grid:cell>  <%--已完成--%>
						<grid:cell  width="50px">${order.counts17}</grid:cell>  <%--退单--%>
						<grid:cell  width="50px">${order.counts18}</grid:cell>  <%--处理中--%>
						<grid:cell  width="50px">${order.counts19}</grid:cell>  <%--已完成--%>
						<grid:cell  width="50px">${order.counts20}</grid:cell>  <%--退单--%>
						<grid:cell  width="50px">${order.counts21}</grid:cell>  <%--处理中--%>
						<%-- <grid:cell  width="50px">${order.counts22}</grid:cell>  已完成 --%>
						<grid:cell  width="50px">${order.counts23}</grid:cell>  <%--退单--%>
						<grid:cell  width="50px">${order.counts24}</grid:cell>  <%--处理中--%>
						<grid:cell  width="50px">${order.counts25}</grid:cell>  <%--已完成--%>
				  	</grid:body>
				</grid:grid>
	</form>
 </div>
 </div>
</div>
</body>
</html>
<script type="text/javascript">
function check() {
	return true;
}

$(function(){
	$("#searchBtn").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!vPlanOrderReportQuery.do");
			$("#serchform").submit();
		}
	});
	
	$("#excelOrd").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!vPlanOrderReportQuery.do?ajax=yes&excel=check&type=dtl";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!vPlanOrderReportQuery.do?ajax=yes&excel=yes&type=dtl");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!vPlanOrderReportQuery.do");
				}
			},'json');
		}
	});
	
	$("#excelReport").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!vPlanOrderReportQuery.do?ajax=yes&excel=check&type=report";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!vPlanOrderReportQuery.do?ajax=yes&excel=yes&type=report");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!vPlanOrderReportQuery.do");
				}
			},'json');
		}
	});
});
</script>
 