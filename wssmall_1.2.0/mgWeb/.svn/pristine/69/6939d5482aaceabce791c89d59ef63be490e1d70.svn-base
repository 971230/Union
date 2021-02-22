<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="division">
<table cellspacing="0" cellpadding="0">
	<tbody>
		<tr>
			<th>订单号:</th>
			<td>${ord.sn } 【${ord.payStatus}】</td>
			<th>下单日期:</th>
			<td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${ord.create_time}"></html:dateformat></td>
		</tr>
		<tr>
			<th>配送方式:</th>
			<td>
			<select  class="ipttxt"   name="delivery.ship_type" id="shiptype">
				<c:forEach items="${dlyTypeList}" var="type" >
					<option value="${type.name }" label="${type.name }">${type.name }</option>
				</c:forEach>
			</select>
			</td>
			<th>配送费用:</th>
			<td><fmt:formatNumber value="${ord.shipping_amount }" type="currency" pattern="￥.00"/></td>
		</tr>
		<tr>
		<tr>
			<th>物流公司:</th>
			<td><select  class="ipttxt"    name="delivery.logi_id" >
				<c:forEach items="${logiList}" var="logi" >
					<option value="${logi.id }" label="${logi.name }">${logi.name }</option>
				</c:forEach>
			   </select> 
			</td>
			<th>物流单号:</th>
			<td><input type="text" class="ipttxt"   style="width: 100px;" name="delivery.logi_no" autocomplete="off"></td>
		</tr>
		<tr>
			<th>收货人姓名:</th>
			<td><input type="text" class="ipttxt"  style="width: 80px;" value="${ord.ship_name}" name="delivery.ship_name" autocomplete="off"></td>
			<th>电话:</th>
			<td><input type="text" class="ipttxt" style="width: 150px;" value="${ord.ship_tel}" name="delivery.ship_tel" autocomplete="off"></td>
		</tr>
		<tr>
			<th>手机:</th>
			<td><input type="text" class="ipttxt"  style="width: 150px;" value="${ord.ship_mobile}" name="delivery.ship_mobile" autocomplete="off"></td>
			<th>邮政编码:</th>
			<td><input type="text" class="ipttxt"  style="width: 80px;" value="${ord.ship_zip}" name="delivery.ship_zip" autocomplete="off"></td>
		</tr>
		<tr>
			<th>地址:</th>
			<td colspan="3"><input type="text" class="ipttxt"   style="width: 360px;" value="${ ord.ship_addr}" name="delivery.ship_addr" autocomplete="off"></td>
		</tr>
		<tr>
			<th>发货单备注:</th>
			<td colspan="3"><textarea  style="width: 95%;" name="delivery.remark"
				type="textarea"></textarea></td>
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
			<th>当前库存</th>
			<th>购买数量</th>
			<th>已发货</th>
		</tr>
	</thead>
	<tbody>
	
	<c:forEach items="${itemList}" var="item">
		<tr>
			<td>${ item.sn}</td>
			<td>${ item.name}
			<input type="hidden" name="goods_idArray" value="${ item.goods_id}"/>
			<input type="hidden" name="goods_nameArray" value="${ item.name}"/>
			<input type="hidden" name="product_idArray" value="${ item.spec_id}"/>
			<input type="hidden" name="goods_snArray" value="${ item.sn}"/>
	 
			</td>
			<td>${ item.store}</td>
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
        <input type="text" class="ipttxt"  size="3" value="${gift.num -gift.shipnum}" name="giftnumArray">
        <input type="hidden" name="giftnameArray" value="${gift.gift_name }"/>
        <input type="hidden" name="giftidArray" value="${gift.gift_id }"/>
        </td>
      </tr>
</c:forEach>      
          </tbody></table>
</div>
</c:if>
<script>
$(function(){
	$("#shiptype").val("${ord.shipping_type}");
});
</script>