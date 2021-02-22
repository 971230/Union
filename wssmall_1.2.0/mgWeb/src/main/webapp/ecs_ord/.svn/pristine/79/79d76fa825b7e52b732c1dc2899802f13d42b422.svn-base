<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="inf_compare_list" action="${pageContext.request.contextPath}/shop/admin/ordInf!getInfList.do" >
	<div class="searchformDiv">
		<table class="form-table">
			<tr>
				<th>日志ID</th>
				<td>
					<input type="text" class="ipttxt"  name="rec_id"  value="${rec_id}"/>
				</td>
				<th>内部订单号：</th>
				<td>
					<input type="text" class="ipttxt"  name="orderId"  value="${orderId}"/>
				</td>
				<th>外部订单号：</th>
				<td>
					<input type="text" class="ipttxt"  name="outId"  value="${outId}"/>
				</td>
				<th>接口编码：</th>
				<td>
					<input type="text" class="ipttxt"  name="opCode"  value="${opCode}"/>
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" id="get_inf_list" style="margin-right:10px;" class="graybtn1"><span>查询</span></a>
				</td>
			</tr>
		</table>		
	</div>
	
	<div class="grid">
		<grid:grid from="webpage" ajax="yes" formId="inf_compare_list">
			<grid:header>
				<grid:cell style="width: 30px;">日志ID</grid:cell>
				<grid:cell style="width: 100px;">订单ID</grid:cell>
				<grid:cell style="width: 100px;">接口编码</grid:cell>
				<grid:cell style="width: 100px;">对比时间</grid:cell>
			</grid:header>
			<grid:body item="inf">
				<grid:cell>
					<a name="view_compare_detail" href="ordInf!infCompareDetail.do?rec_id=${inf.rec_id}">${inf.rec_id}</a>
				</grid:cell>
				<grid:cell>${inf.order_id}</grid:cell>
				<grid:cell>${inf.op_code}</grid:cell>
				<grid:cell>${inf.compare_time}</grid:cell>
			</grid:body>
		</grid:grid>
		<div style="clear:both;padding-top:5px;"></div>
	</div>
</form>
<script src="${pageContext.request.contextPath}/ecs_ord/inf/js/inf_compare_list.js" type="text/javascript"></script>