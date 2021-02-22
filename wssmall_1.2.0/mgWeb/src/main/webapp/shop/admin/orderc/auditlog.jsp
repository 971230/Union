<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="">
	<div class="grid">
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<thead>
				<tr>
					
					<th>申请人</th>
					<th>申请原因</th>
					
					<!-- 二级分销商订单需要展示一级的 -->
					<c:forEach items="${auditLogList}" var="auditlog" begin="0" end ="0">   
						<c:if test="${auditlog.founder ==2}">
							<th>一级分销商审核人</th>
							<th>一级分销商审核原因</th>
						</c:if>
					</c:forEach>
					
					<th>电信审核人</th>
					<th>电信审核原因</th>
					
					<th>申请类型</th>
					<th>处理状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${auditLogList}" var="auditlog">   
					<tr>
						
						<td>${auditlog.from_username }</td>
						<td>${auditlog.apply_message }</td>
						
						<c:if test="${auditlog.founder ==2}">
							<td>${auditlog.to_username }</td>
							<td>${auditlog.deal_message }</td>
						</c:if>
						<td>${auditlog.to_mgr_username }</td>
						<td>${auditlog.mgr_deal_message }</td>
						
						
						<td>${auditlog.audit_type_name }</td>
						<td>${auditlog.state_name }</td>
						
					</tr>
				</c:forEach>          
			</tbody>
		</table>
	</div>
</div>