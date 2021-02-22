<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blue_grid">
   <tbody>
   <tr name="hidetr" style="display: none;">
     <th width="100px">环节</th>
     <th width="200px">处理方式</th>
     <th>处理意见</th>
     <th width="150px">处理时间</th>
     <th width="150px">处理人</th>
   </tr>
	<c:forEach items="${logsList }" var="log" varStatus="ls">
		 <tr name="hidetr" style="display: none;">
			<td>${log.trace_name }</td>
			<td>${log.type_name }</td>
			<td>${log.comments }</td>
			<td>${log.create_time }</td>
			<td>${log.op_name }</td>
		 </tr>
	</c:forEach>
   </tbody>
</table>   