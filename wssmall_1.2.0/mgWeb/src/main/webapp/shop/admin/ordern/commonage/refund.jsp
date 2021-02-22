<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='common_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
<div class="division">
<table width="100%">
  <tbody>
	  <tr>
	    <th>订单号：</th>
	    <td>${order.sn } 【${order.payStatus}】</td>
	    <th>下单日期：</th>
	    <td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat>:</td>
      </tr>
	  <tr>
	    <th>订单总金额：</th>
	    <td><fmt:formatNumber value="${order.order_amount}" type="currency" pattern="￥.00"/></td>
	    <th>已收金额：</th>
	    <td><fmt:formatNumber value="${order.paymoney}" type="currency" pattern="￥.00"/></td>
	 </tr>
	 <tr>
	    <th>折旧费用：</th>
	    <td><fmt:formatNumber value="${depreciation_price}" type="currency" pattern="￥.00"/></td>
	    <th>物流费用：</th>
	    <td><fmt:formatNumber value="${ship_price}" type="currency" pattern="￥.00"/></td>
	 </tr>
	 <tr>
	 	<th>应退款金额：</th>
	    <td><fmt:formatNumber value="${returned_price}" type="currency" pattern="￥.00"/></td>
	    <th>实退款金额：</th>
	    <td><fmt:formatNumber value="${refundAmount}" type="currency" pattern="￥.00"/></td>
      </tr>
    </tbody>
  </table>

</div>

<div class="division">
	<table cellspacing="0" cellpadding="0" class="finderInform" style="width: 100%;">
		<thead>
			<tr>
				<th>商品名称</th>
				<th style="width: 60px;">退款方式</th>
				<th style="width: 40px;">退款金额</th>
				<th>收款信息</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${applylist }" var="apl">
				<tr>
					<td>${apl.itemName }</td>
					<td>
						<c:if test="${apl.refund_type==1 }">退款至账户余额</c:if>
						<c:if test="${apl.refund_type==2 }">原支付方式返回</c:if>
						<c:if test="${apl.refund_type==3 }">退款至银行卡</c:if>
					</td>
					<td>
						${apl.pay_price }
					</td>
					<td>
						<c:if test="${apl.refund_type==1 }">退款至账户余额</c:if>
						<c:if test="${apl.refund_type==2 }">原支付方式返回</c:if>
						<c:if test="${apl.refund_type==3 }">
							<p>银行信息：${apl.bank_info }</p>
							<p>开户人姓名：${apl.account_holder_name }</p>
							<p>银行账号：${apl.bank_account }</p>
						</c:if>
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

 <div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value=" 退费 " class="submitBtn" name='allowRefundBtn_c'/>
	   </td>
	</tr>
 </table>
</div> 
</form>