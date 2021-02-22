<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/card_list.js"></script> 


<c:if test="${from_page=='order'}"> 
	<div class="warm_reminder">
        	<p class="title"><i class="warmico"></i>温馨提示</p>
            <p>1、用户已支付金额为${order.order_amount}（元）</p>
            <p>1、选择充值卡总面额为<span id='all_card_money'>0</span>（元）</p>
   </div>
</c:if>

<form  method="post" id='card_query_form' action='card!list.do'>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
 <input type="hidden" id="from_page" name="from_page" value="${from_page}" />
 <input type="hidden" id="card.type_code" name="card.type_code" value="${card.type_code}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
   	    	   <th>号码状态:&nbsp;&nbsp;</th>
    	      <td>
    	      		<c:if test="${from_page!='order'}">
		    	      	<html:selectdict name="card.state" curr_val="${card.state}" appen_options="<option value=''>--全部--</option>" attr_code="DC_NUM_STATE">
						</html:selectdict>
					</c:if>
					<c:if test="${from_page=='order'}">
		    	      	<select  class="ipttxt"  name="card.state" ><option value='0'>可用</option></select>
					</c:if>
    	      </td>
    	      <th>卡号:&nbsp;&nbsp;</th>
    	      <td><input type="text" class="ipttxt"   name="card.card_code" value="${card.card_code}" class="searchipt" /></td>
    	      <th>批次号:</th>
    	      <td><input type="text" class="ipttxt"   name="card.batch_id" value="${card.batch_id}" class="searchipt" /></td>
   	       	  <th>价格:</th>
    	      <td><input type="text" class="ipttxt"   name="card.card_price" value="${card.card_price}" class="searchipt" dataType="int" /></td>
    	      <td>	<input style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
   	<div class="comBtnDiv">
   	<c:if test="${from_page=='order'}">
	   	<c:if test="${card.type_code =='RECHARGE_CARD'}">
			<a  style="margin-right:10px;" class="graybtn1" id='card_tansfer_a'><span>流量卡调拨</span></a>
		</c:if>
	   	<c:if test="${card.type_code =='CARD'}">
			<a  style="margin-right:10px;" class="graybtn1" id='card_tansfer_a'><span>充值卡调拨</span></a>
		</c:if>
	</c:if>
	
	<c:if test="${from_page!='order'}">
		<c:if test="${adminUser.founder==2 or adminUser.founder == 3}">
		 	<!-- <a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" id='card_returned_apply'><span>退货申请</span></a> -->
		</c:if>
	</c:if>
	
	
	<c:if test="${adminUser.founder==0 or adminUser.founder == 1}">
		<c:if test="${card.type_code=='CARD'}">
		<a  style="margin-right:10px;" class="graybtn1" id='rechargeCard_import_btn'><span>充值卡导入</span></a>
		</c:if>
		<c:if test="${card.type_code=='RECHARGE_CARD'}">
			<a  style="margin-right:10px;" class="graybtn1" id='trafficCard_import_btn'><span>流量卡导入</span></a>
		</c:if>
	</c:if>
	</div>
	<div id="importDialog">
	
	</div>
</form>

<div id="cardList">
	<jsp:include page="import_card_all_list.jsp"></jsp:include>
</div>
