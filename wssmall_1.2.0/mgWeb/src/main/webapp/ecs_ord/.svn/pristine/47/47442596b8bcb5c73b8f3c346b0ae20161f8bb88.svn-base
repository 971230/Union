<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div id="order_change_logs_show" style="display:none">
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	class="blue_grid">
	
	<tbody>
	
	  <tr>
				<th>字段元素</th>
				<th>原来值</th>
				<th>最新值</th>
				<th>修改人</th>
				<th>修改时间</th>
				<th>修改批次</th>
				</tr>
				
			<c:forEach items="${orderchangeList }" var="orderchange">
			<tr name="">
				<td>${orderchange.element_name}</td>
				<td>${orderchange.old_value}</td>
				<td>${orderchange.new_value}</td>
				<td>${orderchange.oper_id}</td>
				<td>${orderchange.update_time}</td>
				<td>${orderchange.log_id}</td>
			</tr>
			</c:forEach>
	
	</tbody>
	
</table>
</div>
