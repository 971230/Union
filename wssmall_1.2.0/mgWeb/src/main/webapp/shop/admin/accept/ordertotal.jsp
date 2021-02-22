<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody>
	   	<tr>
	   		<th>总商品金额：</th>
	   		<td>${orderPrice.originalPrice}</td>
	   	</tr>
	   	<tr>
	   		<th>配送费用：</th>
	   		<td>${orderPrice.shippingPrice}</td>
	   	</tr>
	   	<c:if test="${orderPrice.protectPrice!=null }">
	   	<tr>
	   		<th>邮政编码：</th>
	   		<td>${orderPrice.protectPrice}</td>
	   	</tr>
	   	</c:if>
	   	<tr>
	   		<th>优惠金额：</th>
	   		<td>
	   			-￥${orderPrice.discountPrice }
	   		</td>
	   	</tr>
	   	<tr>
	   		<th>此订单数获得积分：</th>
	   		<td>
	   			${orderPrice.point}
	   		</td>
	   	</tr>
	   	<tr>
	   		<th>应付总额：</th>
	   		<td>
	   			${orderPrice.orderPrice}
	   		</td>
	   	</tr>
	</tbody>
</table>

