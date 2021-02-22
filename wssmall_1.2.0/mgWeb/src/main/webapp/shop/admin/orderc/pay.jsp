<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='common_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
<div class="division">
<table width="100%">
  <tbody><tr>
    <th>订单号：</th>
    <td>${ord.sn } 【${order.payStatus}】</td>
    <th>下单日期：</th>
    <td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat></td>
      </tr>
  <tr>
    <th>订单总金额：</th>
    <td>￥<fmt:formatNumber value="${order.order_amount}" maxFractionDigits="2" /></td>
    <th>已收金额：</th>
    <td>￥<fmt:formatNumber value="${order.paymoney}" maxFractionDigits="2" /></td>
    </tr>
    <!-- 
  <tr>
    <th>收款银行：</th>
    <td colspan="3"><input type="text" class="ipttxt"   style="width: 100px;" value="" name="payment.bank" id="payBank" autocomplete="off">    </td>
    </tr>
    <tr>
      <th>收款帐号：</th>
      <td colspan="3"><input type="text" class="ipttxt"   style="width: 200px;" value="" name="payment.account" id="payAccount" autocomplete="off"></td>
    </tr>
     -->
    <tr>
      <th>收款金额：</th>
      <td colspan="3"><input type="text" class="ipttxt"   style="width: 100px;" value="${order.order_amount - order.paymoney}" name="payment.money" disabled=true autocomplete="off">      </td>
<!--      <th>收款人：</th>
      <td>admin</td> -->
    </tr>
  <tr>
    <th>付款类型：</th>
    <td colspan="1">
	    <!-- 
		<label><input type="radio" style="" checked="checked" value="offline" name="payment.pay_type">线下支付</label>
		 -->
		<c:if test="${payment_cfg_id==2}">
	    	<label><input type="radio" style="" value="online" name="payment.pay_type" checked>在线支付</label>
	    </c:if>
		 <c:if test="${payment_cfg_id==4}">
			<label><input type="radio" style="" value="deposit" name="payment.pay_type" checked>预存款支付</label>
		</c:if>
	<th>支付方式：</th>
    <td>
    <select  class="ipttxt"  selected="30"  style="" value="30"  name="payment.pay_method" type="select">
    <c:forEach items="${paymentList}"  var="payment">
    <option value="${payment.id}" label="${payment.name }">${payment.name }</option>
    </c:forEach>
	</select></td>
   </tr>
   <c:if test="${payment_cfg_id==2}">
	   <tr>
	    <td  colspan="4" >
	    <%-- 银行列表 --%>
	    
	 	<div align="center" id="bankList" >
	 			<c:set var="count" value="0"></c:set>	
					<c:forEach items="${bankList}" var="bank" varStatus="status">   
						<c:if test="${bank.bank_type == '00A'}">
							<c:set var="count" value="${count + 1}"></c:set>
							<input   type="radio" name="payment.bank_id"  value="${bank.bank_id}"/>&nbsp;
							<IMG src="${ctx}${bank.img_url}" alt="${bank.bank_name}" align="absmiddle" style="height:38px;width:130px;">
							<c:if test="${count%4 == 0}">
								<br/><br/>
							</c:if>
						</c:if>
				</c:forEach>
	 	</div>
		</td>
		<!-- 
	    <th>付款人：</th>
	    <td><input type="text" class="ipttxt"  style="width: 100px;" value="" name="payment.pay_user" autocomplete="off" /></td>
	    </tr>
	<!-- <tr>
	    <th>当前状态:</th>
	    <td>未支付</td>
	      <th>收取支付费用:</th>
	      <td>0.000</td>
	     </tr> -->
	     </tr>
     </c:if>
    <tr>
        <th>收款单备注：</th>
        <td colspan="3"><textarea value="" rows="" cols="40" name="payment.remark" required="true"></textarea></td>
    </tr>
    </tbody></table>
</div>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="支付" class="submitBtn" name='payBtn'/>
	   </td>
	</tr>
 </table>
</div> 

</form>