<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.division th{font-weight:normal;}
-->
</style>    
<div class="tableform">
<table cellspacing="10" cellpadding="0" border="0" width="100%">
	<tbody>
		<tr>
			<td style="vertical-align: top;">
			<h4>发货单据列表</h4>
			<div class="division">
			<table cellspacing="0" cellpadding="0" border="0" class="gridlist" width="100%">
				<thead>
					<tr>
						<th>建立日期</th>
						<th>发货单号</th>
						<th>物流单号</th>
						<th>收件人</th>
						<th>物流公司</th>
						<th>配送方式</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${shipLogList }" var="ship">
					<tr>
						<td><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${ship.create_time}" /></td>
						<td>${ship.delivery_id }</td>
						<td>${ship.logi_no }</td>
						<td>${ship.ship_name }</td>
						<td>${ship.logi_name }</td>
						<td>${ship.ship_type }</td>
						<td>
							<a href="javascript:OrderDetail.showFlowList('${ship.delivery_id }','${ship.logi_id }','${ship.logi_no }','yes');">查看物流信息</a>&nbsp;
							<c:if test="${ship.logi_id==0 }">
								<a href="javascript:OrderDetail.showFlowAdd('${ship.delivery_id }','${ship.logi_id }','yes');">新增物流信息</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			</td>
		</tr>
		
		<tr>
			<td style="vertical-align: top;">
			<h4>退货单据列表</h4>
			<div class="division">
			<table cellspacing="0" cellpadding="0" border="0" width="100%"
				class="gridlist">
				<thead>
					<tr>
						<th>建立日期</th>
						<th>退货单号</th>
						
							<th>物流单号</th>
						<th>退货人</th>
						<th>配送方式</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${reshipLogList }" var="ship">
					<tr>
						<td><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${ship.create_time}" /></td>
						<td>${ship.delivery_id }</td>
						
							<td>${ship.logi_no }</td>
						<td>${ship.ship_name }</td>
						<td>${ship.ship_type }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			</td>
		</tr>

		<tr>
			<td style="vertical-align: top;">
			<h4>换货单据列表</h4>
			<div class="division">
			<table cellspacing="0" cellpadding="0" border="0" class="gridlist" width="100%">
				<thead>
					<tr>
						<th>建立日期</th>
						<th>换货单号</th>
						
							<th>物流单号</th>
						<th>收件人</th>
						<th>配送方式</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${chshipLogList }" var="ship">
					<tr>
						<td><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${ship.create_time}" /></th>
						<td>${ship.delivery_id }</th>
						
							<th>${ship.logi_no }</th>
						<td>${ship.ship_name }</td>
						<td>${ship.ship_type }</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
			</td>
		</tr>
		
				
	</tbody>
</table>
</div>