<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='order_fh_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
<div class="division">
<table cellspacing="0" cellpadding="0" style="width:100%;">
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
			<select  class="ipttxt"   name="delivery.ship_type" id="shiptype">
				<c:forEach items="${dlyTypeList}" var="type" >
					<option value="${type.name }" label="${type.name }">${type.name }</option>
				</c:forEach>
			</select>
			</td>
			<th>发货仓库</th>
			<td>
				<select name="delivery.house_id">
					<c:forEach items="${wareHouseList }" var="wh">
						<option value="${wh.house_id }">${wh.house_name }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
		<tr>
			<th>物流公司:</th>
			<td><select  class="ipttxt"    name="logi_id_name" onchange="logiChange(this);">
				<c:forEach items="${logiList}" var="logi" >
					<option value="${logi.id }|${logi.name }" label="${logi.name }">${logi.name }</option>
				</c:forEach>
			   </select> 
			</td>
			<th>物流单号:</th>
			<td><input type="text" class="ipttxt"   style="width: 100px;" name="delivery.logi_no" autocomplete="off"></td>
		</tr>
		<script type="text/javascript">
			function logiChange(obj){
				if(obj.value=='0|--其它--'){
					$("#flowInfo").show();
				}else{
					$("#flowInfo").hide();
				}
			}
		</script>
		<tr id="flowInfo" style="display: none;">
			<th>物流公司名称:</th>
			<td><input type="text" class="ipttxt"  style="width: 95%;" value="" name="flow.logi_name" autocomplete="off"></td>
			<th>物流描述:</th>
			<td>
				<textarea  style="width: 95%;" name="flow.description" type="textarea"></textarea>
			</td>
		</tr>
		<tr>
			<th>收货人姓名:</th>
			<td><input type="text" class="ipttxt"  style="width: 80px;" value="${order.ship_name}" name="delivery.ship_name" autocomplete="off"></td>
			<th>电话:</th>
			<td><input type="text" class="ipttxt" style="width: 150px;" value="${order.ship_tel}" name="delivery.ship_tel" autocomplete="off"></td>
		</tr>
		<tr>
			<th>手机:</th>
			<td><input type="text" class="ipttxt"  style="width: 150px;" value="${order.ship_mobile}" name="delivery.ship_mobile" autocomplete="off"></td>
			<th>邮政编码:</th>
			<td><input type="text" class="ipttxt"  style="width: 80px;" value="${order.ship_zip}" name="delivery.ship_zip" autocomplete="off"></td>
		</tr>
		<tr>
			<th>地址:</th>
			<td colspan="3"><input type="text" class="ipttxt"   style="width: 360px;" value="${ order.ship_addr}" name="delivery.ship_addr" autocomplete="off"></td>
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
	<col >
	<col >
	<col >
	<col>
	<col >
	<col >
	<thead>
		<tr>
			<th>货号</th>
			<th>商品名称</th>
			<th>购买数量</th>
			<th>已发货</th>
			<th>此单发货</th>
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
			<input type="hidden" name="shipRequest.itemid_idArray" value="${ item.item_id}"/>
			</td>
		
			<!-- <td <c:if test="${item.have_stock==0}"> style='display:none;'</c:if>>${ item.store}</td> -->
			<td>${ item.num}</td>
			<td>${ item.ship_num}</td>
			<td><input type="text" class="ipttxt"  style="width: 50px;" value="${ item.num-item.ship_num}" name="shipRequest.numArray" autocomplete="off"></td>
			
			
			
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
        <input type="hidden" name="shipRequest.giftnameArray" value="${gift.gift_name }"/>
        <input type="hidden" name="shipRequest.giftidArray" value="${gift.gift_id }"/>
        </td>
      </tr>
</c:forEach>      
          </tbody></table>
</div>
</c:if>
<script>
$(function(){
	$("#shiptype").val("${order.shipping_type}");
});
</script>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
           <input  type="button"  value="发货" class="submitBtn" name='fh'/>
	   </td>
	</tr>
 </table>
</div> 
</form> 
