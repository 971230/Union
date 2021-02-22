<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='order_sh_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${order.order_id}" />
<div class="division">
<table cellspacing="0" cellpadding="0">
	<tbody>
		<tr>
			<th>订单号:</th>
			<td>${order.sn } 【${order.payStatus}】</td>
			<th>下单日期:</th>
			<td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat></td>
		</tr>
		<tr>
			<th>配送方式:</th>
			<td>
			${delivery.ship_type }
			</td>
			<th>配送费用:</th>
			<td><fmt:formatNumber value="${order.shipping_amount }" type="currency" pattern="￥.00"/></td>
		</tr>
		<tr>
		<tr>
			<th>物流公司:</th>
			<td>${delivery.logi_name }
			</td>
			<th>物流单号:</th>
			<td>${delivery.logi_no }</td>
		</tr>
		<tr>
			<th>收货人姓名:</th>
			<td>${order.ship_name}</td>
			<th>电话:</th>
			<td>${order.ship_tel}</td>
		</tr>
		<tr>
			<th>手机:</th>
			<td>${order.ship_mobile}</td>
			<th>邮政编码:</th>
			<td>${order.ship_zip}</td>
		</tr>
		<tr>
			<th>地址:</th>
			<td colspan="3">${ order.ship_addr}</td>
		</tr>
		<tr>
			<th>备注:</th>
			<td colspan="3"><textarea  style="width: 95%;" name="hint" type="textarea"></textarea></td>
		</tr>
	</tbody>
</table>
</div>


<div class="division">
<table cellspacing="0" cellpadding="0" class="finderInform">
	<col style="width: 20%;">
	<col style="width: 35%;">
	<col style="width: 15%;">
	<col style="width: 10%;">
	<col style="width: 10%;">
	<col style="width: 10%;">
	<thead>
		<tr>
			<th>货号</th>
			<th>商品名称</th>
			<th>购买数量</th>
			<th>已发货</th>
		</tr>
	</thead>
	<tbody>
	
	<c:forEach items="${itemList}" var="item">
		<tr>
			<td>${ item.sn}</td>
			<td>${ item.name}
			<input type="hidden" name="shipRequest.goods_idArray" value="${ item.goods_id}"/>
			<input type="hidden" name="shipRequest.goods_nameArray" value="${ item.name}"/>
			<input type="hidden" name="shipRequest.product_idArray" value="${ item.spec_id}"/>
			<input type="hidden" name="shipRequest.goods_snArray" value="${ item.sn}"/>
	 
			</td>
			<td>${ item.num}</td>
			<td>${ item.ship_num}</td>
		</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>

<c:if test="${hasGift}">
<div class="division">
<table cellspacing="0" cellpadding="0" class="finderInform">
      <thead>
        <tr>
          <th>赠品名称</th>
          <th>兑换积分</th>
          <th>已发货/兑换量</th>
          <th>需发货</th>
        </tr>
      </thead>
      <tbody> 
<c:forEach items="${giftList}" var="gift" >
   <tr>
        <td>${gift.gift_name }</td>
        <td>${gift.point }</td>
        <td>${gift.shipnum}/${gift.num }</td>
        <td>
        <input type="text" class="ipttxt"  size="3" value="${gift.num -gift.shipnum}" name="shipRequest.giftnumArray">
        <input type="hidden" name="shipRequest.giftnameArray" value="${gift.gift_name }"/>
        <input type="hidden" name="shipRequest.giftidArray" value="${gift.gift_id }"/>
        </td>
      </tr>
</c:forEach>      
          </tbody></table>
</div>
</c:if>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="确收收货" class="submitBtn" name='sh'/>
	   </td>
	</tr>
 </table>
</div> 
</form>
<script>
$(function(){
	$("#shiptype").val("${order.shipping_type}");
});
</script>