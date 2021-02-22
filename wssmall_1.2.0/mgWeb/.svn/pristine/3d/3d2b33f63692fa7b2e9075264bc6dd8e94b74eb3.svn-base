<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="">
	<div class="grid">
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<thead>
				<tr>
					<th>卡号</th>
					<th>价格</th>
					<th>调拨时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cardLogList}" var="card">   
					<tr>
						<td>${card.card_code }</td>
						<td><fmt:formatNumber value="${card.card_price}" type="currency" pattern="￥.00"/></td>
						
						<td>${card.deal_time }</td>
					</tr>
				</c:forEach>          
			</tbody>
		</table>
	</div>
</div>