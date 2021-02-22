<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!-- 订单补录tab页  show_click_9 -->




<script type="text/javascript">
	//绑定日期控件
	$("input[dataType='date']").datepicker();
</script>
<div class="btnWarp">
	<a href="javascript:void(0);" class="dobtn" id="submit_supply_info"
		style="margin-right:5px;">保存</a> 
		<!-- <a href="javasript:void(0);"
		class="dobtn" style="margin-right:5px;">操作</a> <a
		href="javasript:void(0);" class="dobtn" style="margin-right:5px;">操作</a>
	<a href="javasript:void(0);" class="dobtn" style="margin-right:5px;">操作</a>
	<a href="javasript:void(0);" class="dobtn" style="margin-right:5px;">操作</a> -->
</div>

<form action="javascript:void(0);" class="validate" method="post"
	name="order_supply_form" id="order_supply_form"
	enctype="multipart/form-data">
	<div class="formWarp">
		<div class="tit">
			<a href="javascript:void(0);" name="show_close_btn" tag_id="0"
				class="closeArrow"></a>基本信息
			<div class="dobtnDiv">
			</div>
		</div>
		<div id="order_tag_0" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<tr>
					<th class="red">订单编号：</th>
					<td><input name="orderInfo.order_id" type="text" readonly="readonly"
						class="formIpt" value="${orderInfo.order_id }" /></td>
					<th>订单类型：</th>
					<td><html:selectdict name="orderInfo.order_type" disabled="true"
							style="width:120px;" curr_val="${orderInfo.order_type}"
							attr_code="DC_ORDER_TYPE">
						</html:selectdict></td>
					<th>购买方式：</th>
					<td><html:selectdict name="orderInfo.create_type" disabled="true"
							style="width:120px;" curr_val="${orderInfo.create_type}"
							attr_code="DC_ORDER_CREATE_TYPE">
						</html:selectdict></td>
					<th>订单来源：</th>
					<td><input name="orderInfo.app_key" type="text"
						class="formIpt" value="${orderInfo.app_key }" /></td>
				</tr>
				<tr>
					<th >支付状态：</th>
					<td><html:selectdict name="orderInfo.pay_status"  disabled="true"
							style="width:120px;" curr_val="${orderInfo.pay_status}"
							attr_code="DC_ORDER_PAY_STATUS">
						</html:selectdict></td>
					<th >发货状态：</th>
					<td><html:selectdict name="orderInfo.ship_status" disabled="true"
							style="width:120px;" curr_val="${orderInfo.ship_status}"
							attr_code="DC_ORDER_SHIP_STATUS">
						</html:selectdict></td>
					<th>订单状态：</th>
					<td><html:selectdict name="orderInfo.status" disabled="true"
							style="width:120px;" curr_val="${orderInfo.status}"
							attr_code="DC_ORDER_STATUS">
						</html:selectdict></td>
					<th>开户状态：</th>
					<td><html:selectdict name="orderInfo.accept_status" disabled="true"
							style="width:120px;" curr_val="${orderInfo.accept_status}"
							attr_code="DC_ORDER_ACCEPT_STATUS">
						</html:selectdict></td>
					<!-- 			<th>生成类型：</th> -->
					<!--             <td><input name="orderInfo.create_type" type="text" class="formIpt" value="${orderInfo.create_type }" /></td>  -->
				</tr>
				<tr>
					<!--             <th>异常状态：</th> -->
					<!--             <td><input name="textfield" type="text" class="formIpt" value="---" /></td> -->
					<th >外订单编号：</th>
					<td><input name="orderOuter.old_sec_order_id" type="text" readonly="readonly"
						class="formIpt" value="${orderOuter.old_sec_order_id}" /></td>
					<!--             <th>CRM单编号：</th> -->
					<!--             <td><input name="textfield" type="text" class="formIpt" value="---" /></td> -->
					<th>记录状态：</th>
					<td><html:selectdict name="orderInfo.disabled" disabled="true"
							style="width:120px;" curr_val="${orderInfo.disabled}"
							attr_code="DC_ORDER_DISABLED">
						</html:selectdict></td>
<!-- 					<th>下单人：</th> -->
<!-- 					<td><input name="member.name" type="text" -->
<!-- 						class="formIpt" value="${member.name}" /></td> -->
					<th>支付方式：</th>
					<td><input name="payCfg.name" type="text" class="formIpt"
						value="${payCfg.name}" /></td>
					<th>付款时间：</th>
					<td><input name="orderInfo.pay_time" type="text"
						class="formIpt" value="${orderInfo.pay_time }" dataType="date"
						readonly="readonly" /></td>
				</tr>
				<tr>
					
					<th>发货时间：</th>
					<!-- <html:dateformat pattern="yyyy-MM-dd" d_time="${orderInfo.pay_time }"></html:dateformat> -->
					<td><input name="orderInfo.ship_time" type="text"
						class="formIpt" value="${orderInfo.ship_time}" dataType="date"
						readonly="readonly" /></td>
					
					<th>开户时间：</th>
					<td><input name="orderInfo.accept_time" type="text"
						class="formIpt" value="${orderInfo.accept_time}" dataType="date"
						readonly="readonly" /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>	
				</tr>
			</table>
		</div>
	</div>
	<div class="formWarp">
		<div class="tit">
			<a href="javascript:void(0);" name="show_close_btn" tag_id="1"
				class="closeArrow"></a>商品价格
			<div class="dobtnDiv">
			</div>
		</div>
		<div id="order_tag_1" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<tr>
					<th>订购数量：</th>
					<td><input name="orderInfo.goods_num" type="text"
						class="formIpt" value="${orderInfo.goods_num }" readonly="readonly"/></td>
					<th>商品总额：</th>
					<td><input name="orderInfo.goods_amount" type="text"
						class="formIpt" value="${orderInfo.goods_amount }" readonly="readonly"/></td>
					<!--             <th>生成类型：</th> -->
					<!--             <td><input name="orderInfo.create_type" type="text" class="formIpt" value="${orderInfo.create_type }" /></td> -->
					<th>订单总额：</th>
					<td><input name="orderInfo.order_amount" type="text"
						class="formIpt" value="${orderInfo.order_amount }" /></td>
					<%-- <th>优惠金额：</th>
					<td><input name="orderInfo.order_coupon" type="text"
						class="formIpt"
						value="${orderInfo.order_coupon }" /></td> --%>
					<th>支付金额：</th>
					<td><input name="orderInfo.paymoney" type="text"
						class="formIpt" value="${orderInfo.paymoney }" /></td>	
				</tr>
				<%-- <tr>
					
					<th>订单总额：</th>
					<td><input name="orderInfo.order_amount" type="text"
						class="formIpt" value="${orderInfo.order_amount }" /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr> --%>
			</table>
		</div>
	</div>
	<div class="formWarp">
		<div class="tit">
			<a href="javascript:void(0);" name="show_close_btn" tag_id="2"
				class="closeArrow"></a>收货人信息
			<div class="dobtnDiv">
			</div>
		</div>
		<div id="order_tag_2" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<tr>
					<th >购买人ID：</th>
					<td><input name="orderInfo.member_id" type="text" readonly="readonly"
						class="formIpt" value="${orderInfo.member_id }" /></td>
					<th >购买人：</th>
					<td><input name="member.uname" type="text" class="formIpt" readonly="readonly"
						value="${member.uname}" /></td>
					<th>E-mail：</th>
					<td><input name="member.email" type="text" class="formIpt"
						value="${member.email}" /></td>
					<th>地区：</th>
					<td><input name="orderInfo.shipping_area" type="text"
						class="formIpt" value="${orderInfo.shipping_area }" /></td>
				</tr>
				<tr>
					<th>收货人：</th>
					<td><input name="orderInfo.ship_name" type="text"
						class="formIpt" value="${orderInfo.ship_name }" /></td>
					<th>手机：</th>
					<td><input name="orderInfo.ship_mobile" type="text"
						class="formIpt" value="${orderInfo.ship_mobile }" /></td>
					<th>邮编：</th>
					<td><input name="orderInfo.ship_zip" type="text"
						class="formIpt" value="${orderInfo.ship_zip }" /></td>
					<th>地址：</th>
					<td><input name="orderInfo.ship_addr" type="text"
						class="formIpt" value="${orderInfo.ship_addr }" /></td>
				</tr>
				<tr>
					<th>电话：</th>
					<td><input name="orderInfo.ship_tel" type="text"
						class="formIpt" value="${orderInfo.ship_tel }" /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="formWarp">
		<div class="tit">
			<a href="javascript:void(0);" name="show_close_btn" tag_id="3"
				class="closeArrow"></a>配送信息
			<div class="dobtnDiv">
			</div>
		</div>
		<div id="order_tag_3" class="formGridDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				class="formGrid">
				<tr>
					<th>配送方式：</th>
					<td><input name="orderInfo.shipping_type" type="text"
						class="formIpt" value="${orderInfo.shipping_type }" /></td>
					<th>是否保价：</th>
					<td><html:selectdict name="orderInfo.is_protect"
							style="width:120px;" curr_val="${orderInfo.is_protect}"
							attr_code="DC_ORDER_IS_PROJECT">
						</html:selectdict></td>
					<th>配送保价：</th>
					<td><input name="orderInfo.protect_price" type="text"
						class="formIpt"
						value="${orderInfo.protect_price }" /></td>
					<th>商品重量：</th>
					<td><input name="orderInfo.weight" type="text" class="formIpt"
						value="${orderInfo.weight }" /></td>
				</tr>
				<tr>
					<th>配送时间：</th>
					<td><input name="orderInfo.ship_time" type="text"
						class="formIpt" value="${orderInfo.ship_time }" dataType="date"
						readonly="readonly" /></td>
					<th>发票类型：</th>
					<td><html:selectdict name="orderInfo.invoice_type"
							style="width:120px;" curr_val="${orderInfo.invoice_type}"
							attr_code="DC_ORDER_INVOICE_TYPE">
						</html:selectdict></td>
					<th>发票抬头：</th>
					<td><input name="orderInfo.invoice_title_desc" type="text"
						class="formIpt" value="${orderInfo.invoice_title_desc }" /></td>
					<!--             <th>单位名称：</th> -->
					<!--             <td><input name="orderInfo.invoice_title_desc" type="text" class="formIpt" value="${orderInfo.invoice_title_desc }" /></td> -->
				</tr>
			</table>
		</div>
	</div>
</form>
<div class="formWarp">
	<div class="tit">
		<a href="javascript:void(0);" name="show_close_btn" tag_id="4"
			class="closeArrow"></a>推荐人信息
		<div class="dobtnDiv">
		</div>
	</div>
	<div id="order_tag_4" class="formGridDiv">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="formGrid">
			<tr>
				<th >推荐人：</th>
				<td><input name="spread.name" type="text" class="formIpt" readonly="readonly"
					value="${spread.name}" /></td>
				<th >手机：</th>
				<td><input name="spread.mobile" type="text" class="formIpt" readonly="readonly"
					value="${spread.mobile}" /></td>
				<th >编号：</th>
				<td><input name="spread.spread_id" type="text" class="formIpt" readonly="readonly"
					value="${spread.spread_id}" /></td>
				<th >获取时间：</th>
				<td><input name="spread.create_time" type="text"
					class="formIpt" value="${spread.create_time }" dataType="date"
					readonly="readonly" /></td>
			</tr>
			<tr>
				<th >发展人姓名：</th>
				<td><input name="p_spread.name" type="text" class="formIpt" readonly="readonly"
					value="${p_spread.name }" /></td>
				<th >发展编号：</th>
				<td><input name="p_spread.spread_id" type="text" readonly="readonly"
					class="formIpt" value="${p_spread.spread_id }" /></td>
				<!--             <th>确认时间：</th> -->
				<!--             <td><input name="textfield" type="text" class="formIpt" value="---" /></td> -->
			</tr>
			<tr>
				<!--             <th>归档时间：</th> -->
				<!--             <td><input name="textfield" type="text" class="formIpt" value="不缺货" /></td> -->
				<!--             <th>回访时间：</th> -->
				<!--             <td><input name="textfield" type="text" class="formIpt" value="---" /></td> -->
			</tr>
			<tr>
				<!--             <th>审核人：</th> -->
				<!--             <td><input name="textfield" type="text" class="formIpt" value="---" /></td> -->
				<th>&nbsp;</th>
				<td>&nbsp;</td>
				<th>&nbsp;</th>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
</div>
<div id="submit_supply_div"></div>