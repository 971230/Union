<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="btnWarp">
 </div>
 
<div class="formWarp" style="border-bottom: none;">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="300" class="closeArrow"></a>收款记录<div class="dobtnDiv"></div></div>
<div id="order_tag_300" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th style="width: 25%;">交易流水号</th>
        <th style="width: 25%;">支付金额</th>
        <th style="width: 25%;">支付方式</th>
        <th style="width: 25%;">支付时间</th>
      </tr>
      <tbody>
      <c:forEach items="${payLogList }" var="payment">
	      <tr>
	        <td style="text-align: center;"><a href="javascript:void(0);">${payment.payment_id }</a></td>
	        <td class="red" style="text-align: center;">${payment.money }</td>
	        <td style="text-align: center;">
	        	${payment.payment_name }
	        </td>
	        <td style="text-align: center;">${payment.status_time }</td>
	      </tr>
      </c:forEach>
      </tbody>
    </table>
</div>    

<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="300" class="closeArrow"></a>退款记录<div class="dobtnDiv"></div></div>
<div id="order_tag_300" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th style="width: 25%;">交易流水号</th>
        <th style="width: 25%;">退款金额</th>
        <th style="width: 25%;">支付方式</th>
        <th style="width: 25%;">退款时间</th>
      </tr>
      <tbody>
      <c:forEach items="${refundList }" var="payment">
	      <tr>
	        <td style="text-align: center;"><a href="javascript:void(0);">${payment.payment_id }</a></td>
	        <td class="red" style="text-align: center;">${payment.money }</td>
	        <td style="text-align: center;">
	        	${payment.payment_name }
	        </td>
	        <td style="text-align: center;">${payment.status_time }</td>
	      </tr>
      </c:forEach>
      </tbody>
    </table>
</div>    
</div>