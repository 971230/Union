<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="blue_grid">
	<tbody>
		<tr>
			<th>闪电送公司名称</th>
			<th>闪电送状态名称</th>
			<th>处理时间</th>
			<th>处理人</th>
		</tr>
		<c:forEach items="${sdsLogsList }" var="sdsLog">
			<tr name="">
				<td>${sdsLog.wl_comp_name}</td>
				<td>${sdsLog.status_name}</td>
				<td>${sdsLog.create_time}</td>
				<td>${sdsLog.create_user}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
