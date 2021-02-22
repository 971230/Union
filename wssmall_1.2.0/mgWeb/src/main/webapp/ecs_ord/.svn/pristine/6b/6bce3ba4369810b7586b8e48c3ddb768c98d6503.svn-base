<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blue_grid">
   <tbody>
   <tr name="hidetr" style="display: none;">
   	 <th width="80px">申请人</th>
   	 <th width="150px">申请时间</th>
     <th width="80px">处理人</th>
     <th width="150px">处理时间</th>
     <th>处理意见</th>
     <th width="50px">完成情况</th>
     <th width="150px">外呼类型</th>
     <th width="150px">外呼原因</th>
   </tr>
	<c:forEach items="${outcalllogsList }" var="log" varStatus="ls">
		 <tr name="hidetr" style="display: none;">
			<td>${log.oper_name }</td>
			<td>${log.oper_time }</td>
			<td>${log.deal_name }</td>
			<td>${log.deal_time }</td>
			<td></td>
			<c:choose>
			<c:when test="${log.is_finish == '1' }">
			<td>已完成</td>
			</c:when>
			<c:otherwise>
			<td>未完成</td>
			</c:otherwise>
			</c:choose>
			<td>${log.outcall_type }</td>
			<td>${log.oper_remark }</td>
		 </tr>
	</c:forEach>
   </tbody>
</table>   