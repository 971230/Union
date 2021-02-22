<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form  class="validate" method="post" action="" id='common_form_e' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
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
			<th>换货原因:</th>
			<td colspan="3">
		<select  class="ipttxt"  name="delivery.reason">
	      <option value="质量原因">质量原因</option>
	      <option value="无理由">无理由</option>
	      <option value="其他">其他</option>
	    </select>			
			</td>
		</tr>
		<tr>
			<th>物流公司:</th>
			<td><select  class="ipttxt"    name="logi_id_name"  onchange="logiChange(this);">
				<c:forEach items="${logiList}" var="logi" >
					<option value="${logi.id }|${logi.name }" label="${logi.name }">${logi.name }</option>
				</c:forEach>
			   </select> 
			</td>
			<th>物流单号:</th>
			<td><input type="text" class="ipttxt"   style="width: 100px;" name="delivery.logi_no" autocomplete="off"></td>
		</tr>
		<tr id="flowInfo" style="display: none;">
			<th>物流公司名称:</th>
			<td><input type="text" class="ipttxt"  style="width: 95%;" value="" name="flow.logi_name" autocomplete="off"></td>
			<th>物流描述:</th>
			<td>
				<textarea  style="width: 95%;" name="flow.description" type="textarea"></textarea>
			</td>
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
			<th>退货备注:</th>
			<td colspan="3"><textarea  style="width: 95%;" name="delivery.remark"
				type="textarea"></textarea></td>
		</tr>
	</tbody>
</table>
</div>


<div class="division">
<table>
	<thead>
		<tr>
			<th>原商品</th>
			<th>目标商品</th>
		</tr>
	</thead>
	<tbody>
	<tr>
		<td>
			
			<table cellspacing="0" cellpadding="0" class="finderInform">
				<thead>
					<tr>
						<th>商品名称</th>
						<th>已发货</th>
						<th>退货数量</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${itemList}" var="item">
					<tr <c:if test="${item.order_apply_id=='0'}"> style='display:none;'</c:if> >
						<td>${ item.name}
						<input type="hidden" name="goods_idArray" value="${ item.goods_id}"/>
						<input type="hidden" name="goods_nameArray" value="${ item.name}"/>
						<input type="hidden" name="product_idArray" value="${ item.spec_id}"/>
						 <input type="hidden" name="goods_snArray" value="${ item.sn}"/>
						 <input type="hidden" name="itemid_idArray" value="${ item.item_id}"/>
						 <input type="hidden" name="order_applyArray" value="${ item.order_apply_id}"/>
						</td>
						<td>${ item.ship_num}</td>
						<td><input type="text" class="ipttxt"  style="width: 50px;" value="${item.back_num}" name="numArray" autocomplete="off" readonly="readonly"></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			
		</td>
		<td>
			<table cellspacing="0" cellpadding="0" class="finderInform">
				<thead>
					<tr>
						<th>商品名称</th>
						<th>购买数量</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${orderApplyItemList}" var="oai">
					<c:if test="${oai.item_type==3 }">
					<tr>
						<td>${ oai.itemName}
						</td>
						<td>${ oai.returned_num}</td>
					</tr>
					</c:if>
				</c:forEach>
				</tbody>
			</table>
		</td>
	</tr>
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
        <input type="text" class="ipttxt"  size="3" value="${gift.shipnum}" name="giftnumArray">
        <input type="hidden" name="giftnameArray" value="${gift.gift_name }"/>
        <input type="hidden" name="giftidArray" value="${gift.gift_id }"/>
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
           <input  type="button"  value="允许换货 " class="submitBtn" name='allowChangeBtn'/>
	   </td>
	</tr>
 </table>
</div> 
</form>
