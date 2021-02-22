<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <div class="btnWarp">
 </div> -->
<form  method="post" id="common_form" > 
<div class="formWarp">
<input type="hidden" name="order_id" value="${order_id }" />
<input type="hidden" name="orderId" value="${order_id }" />
  <div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5000" class="closeArrow"></a>退款信息
  <div class="dobtnDiv"></div></div>
    <div id="order_tag_5000" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
            <th>订单编号：</th>
            <td>${order.order_id }</td>
            <th>下单日期：</th>
            <td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat></td>
            <th>订单总价：</th>
            <td><fmt:formatNumber value="${order.order_amount }" type="currency" pattern="￥.00"/></td>
          </tr>
          <tr>
          	<th>已收金额：</th>
            <td><fmt:formatNumber value="${order.paymoney}" type="currency" pattern="￥.00"/></td>
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
            <th>&nbsp;</th>
            <td>&nbsp;</td>
          </tr>
        </table>
    </div>
</div> 

<div class="formWarp">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5001" class="closeArrow"></a>商品明细<div class="dobtnDiv"></div></div>
<div id="order_tag_5001" class="formGridDiv">
    <table id="goods_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
      <tr>
        <th>退款方式</th>
        <th>退款金额</th>
        <th>收款信息</th>
      </tr>
      <tbody>
      <c:forEach items="${applylist }" var="apl">
	      <tr>
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
</div>

<div class="formWarp">
    <div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5009" class="closeArrow"></a>环节信息<div class="dobtnDiv"></div></div>
    <div id="order_tag_5009" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
          	<th>处理意见：</th>
          	<td colspan="5">
          		<input type="radio" name="flag_status" value="1" checked="checked" />通过&nbsp;&nbsp;<input type="radio" name="flag_status" value="2" />不通过&nbsp;&nbsp;
          	</td>
          </tr>
          <tr>
            <th>备注：</th>
            <td colspan="5">
            	<textarea  style="width: 95%;" name="hint" type="textarea"></textarea>
            </td>
          </tr>
          <tr>
	         <th colspan="6" style="text-align: center;">
	         	<a href="javascript:void(0);" id="allowRefundBtn_c" class="dobtn" style="margin-right:5px;">确认退款</a>
	         </th>
	       </tr>
        </table>
    </div>
</div> 

</form>
