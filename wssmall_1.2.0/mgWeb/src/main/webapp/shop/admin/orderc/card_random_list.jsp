<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/card_list.js"></script> 

<c:if test="${from_page=='order'}">
	<div class="warm_reminder">
        	<p class="title"><i class="warmico"></i>温馨提示</p>
            <p>1、用户购买卡总数为：<span class='red'>${order.goods_num}</span>（个），卡金额为：${order.goods_amount}（元）,已支付总金额为<span class='red'>${order.order_amount}</span>（元）</p>
            <p>2、目前系统可用库存为：<span class='red'>${countMap.ableCar_count}</span>（个）</p>
   </div>
</c:if>

<form  method="post" id='card_query_form' action='card!list.do'>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
 <input type="hidden" id="from_page" name="from_page" value="${from_page}" />
 <input type="hidden" id="card.type_code" name="card.type_code" value="${card.type_code}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
    	      <th>申请数量:&nbsp;&nbsp;</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="cloud.phone_num" value="${order.goods_num}" class="searchipt" disabled />个</td>
    	      
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
   	<div class="comBtnDiv">
   	<c:if test="${from_page=='order'}">
	   	<c:if test="${card.type_code =='RECHARGE_CARD'}">
			<a  style="margin-right:10px;" class="graybtn1" id='card_tansfer_a'><span>流量卡调拨</span></a>
		</c:if>
		
		<c:if test="${card.type_code =='TIME_CARD'}">
			<a  style="margin-right:10px;" class="graybtn1" id='card_tansfer_a'><span>时长卡调拨</span></a>
		</c:if>
		
	   	<c:if test="${card.type_code =='CARD'}">
			<a  style="margin-right:10px;" class="graybtn1" id='card_tansfer_a'><span>充值卡调拨</span></a>
		</c:if>
	</c:if>
	
	<%--走在线支付，不允许退货 
	<c:if test="${from_page!='order'}">
		 <a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" id='card_returned_apply'><span>退货申请</span></a>
	</c:if>
			 --%>		
	</div>
</form>


