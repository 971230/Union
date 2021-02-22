<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form id="refund_form" method="POST" action="${pageContext.request.contextPath}/shop/admin/orderReport!refundList.do">
<input type="hidden" name="create_type" value="${create_type }" />
<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
		支付单号：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="payment_id"  value="${payment_id }"/>
		</td>
		<th>
		订单号：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="order_id"  value="${order_id }"/>
		</td>
		<th>
		会员用户名：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="member_name"  value="${member_name }"/>&nbsp;&nbsp;
		<a href="javascript:void(0)" id="refundsearchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div class="grid">
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell sort="payment_id" width="250px">支付单号</grid:cell> 
	<grid:cell>订单号</grid:cell>
	<grid:cell >支付金额</grid:cell>
	<grid:cell >支付方式</grid:cell>
	<grid:cell sort="member_name">会员用户名</grid:cell>
	<grid:cell >支付时间</grid:cell>
	<grid:cell>操作</grid:cell> 
	</grid:header>

  <grid:body item="payment">
        <grid:cell>${payment.payment_id } </grid:cell>
        <grid:cell>${payment.order_id } </grid:cell>
        <grid:cell><fmt:formatNumber value="${payment.money }" type="currency" pattern="￥.00"/></grid:cell>
        <grid:cell>${payment.payMethod} </grid:cell>
        <grid:cell>${payment.member_name} </grid:cell>
        <grid:cell>${fn:substring(payment.create_time  , 0 , 19)} </grid:cell>
        <grid:cell><a href="orderReport!refundDetail.do?id=${payment.payment_id }">查看详细</a></grid:cell>
  </grid:body>  
  
</grid:grid>  
</div>
</form>	

<script type="text/javascript">
$(function(){
	$("#refundsearchBtn").bind("click",function(){
		$("#refund_form").submit();
	});	
});
</script>
