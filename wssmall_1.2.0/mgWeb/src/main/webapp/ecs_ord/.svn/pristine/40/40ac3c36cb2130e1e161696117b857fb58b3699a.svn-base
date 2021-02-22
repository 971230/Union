<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="blue_grid">
	<tbody>
		<tr>
			<th width="100px">环节</th>
			<th width="200px">异常类型</th>
			<th>异常备注</th>
			<th width="150px">处理时间</th>
			<th width="150px">处理人</th>
		</tr>
		<c:forEach items="${exceptionLogsList }" var="exceptionLogs" varStatus="lis">
			<tr name="hidetr_exception">
				<td>${exceptionLogs.trace_name }</td>
				<c:if test="${exceptionLogs.op_name=='' && exceptionLogs.exception_type_name==''}">
					<td>接口异常</td>
				</c:if>
				<c:if test="${exceptionLogs.op_name!='' || exceptionLogs.exception_type_name!='' }">
					<td>${exceptionLogs.exception_type_name}</td>
				</c:if>
				<td>${exceptionLogs.comments }</td>
				<td>${exceptionLogs.create_time }</td>
				<td>${exceptionLogs.op_name }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
