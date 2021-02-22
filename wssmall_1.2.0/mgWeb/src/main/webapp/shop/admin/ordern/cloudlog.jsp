<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="">
	<div class="grid">
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<thead>
				<tr>
					
					<th>销售品名称</th>
					<th>业务号码</th>
					<th>调拨时间</th>
					<th>金额（元）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cloudLogList}" var="cloud">   
					<tr>
						<td>${cloud.offer_name }</td>
						<td>${cloud.phone_num }</td>
						<td>${cloud.deal_time }</td>
						<td>
						<fmt:formatNumber value="${cloud.pay_money}" type="currency" pattern="￥.00"/>
						</td>
					</tr>
				</c:forEach>          
			</tbody>
		</table>
	</div>
</div>