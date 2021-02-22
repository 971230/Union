<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
<table>
	<thead>
		<tr>
			<th>商品名称</th>
			<th>限购数量</th>
			<th>购物车限购数量</th>
			<th>已购买数量</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${limitBuy.goodsList }" var="goods" >
			<tr>
				<td>${goods.name }</td>
				<td>${goods.num }</td>
				<td>${goods.cart_num }</td>
				<td>${goods.buy_num }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
