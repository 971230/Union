<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
//绑定日期控件
$("input[dataType='date']").datepicker();
</script>

<div class="btnWarp">
	<input type="hidden" id="order_payment_id" value="${orderInfo.payment_id }" />
 	<a href="javascript:void(0);" name="order_detail_close" class="dobtn" style="margin-right:5px;"><返回</a>
 	
	<%-- <c:if test="${orderInfo.status!=0 && orderInfo.status!=1 && orderInfo.status!=2 && orderInfo.status!=-10}"> 
	<a class="sgreenbtn" href="javascript:void(0);" id="order_exception_btn">
		 <span id="refOrgBtn">订单异常</span>
	</a>--%>
	<%-- </c:if> --%>
	<%-- <c:if test="${orderInfo.order_record_status==0}">
	<a class="sgreenbtn" href="javascript:void(0);">
		 <span id="refOrgBtn">订单暂停</span>
	</a>
	</c:if>
	<c:if test="${orderInfo.order_record_status==1}">
	<a class="sgreenbtn" href="javascript:void(0);">
		 <span id="refOrgBtn">订单恢复</span>
	</a>
	</c:if>
	<c:if test="${orderInfo.status==-10 || orderInfo.order_record_status==0}">
	<a class="sgreenbtn" href="javascript:void(0);">
		 <span id="refOrgBtn">订单编辑</span>
	</a>
	</c:if> --%>
	
	<%-- <c:if test="${orderInfo.pay_status==0 && orderInfo.status!=-10 && orderInfo.canPay=='yes'}">
		<a href="javascript:void(0);" name="BUTTON_CARD_PAY" payment_id="${orderInfo.payment_id }" class="dobtn" style="margin-right:5px;">支付</a>
	</c:if>
	<c:if test="${orderInfo.status==3 && orderInfo.canShip=='yes'}">
		<a href="javascript:void(0);" name="BUTTON_CUST_ACCEPT_C" class="dobtn" style="margin-right:5px;">备货</a>
	</c:if>
	<c:if test="${(orderInfo.status==4 || orderInfo.ship_status==4) && orderInfo.canShip=='yes'}">
		<a href="javascript:void(0);" name="BUTTON_SHIPPING_C" class="dobtn" style="margin-right:5px;">发货</a>
	</c:if>
	<c:if test="${orderInfo.status==5 && orderInfo.ship_status==1}">
		<a href="javascript:void(0);" name="BUTTON_GET_SHIPPING_C" class="dobtn" style="margin-right:5px;">确认收货</a>
	</c:if>
	<c:if test="${orderInfo.status==6}">
		<a href="javascript:void(0);" name="BUTTON_FINISHED_C" class="dobtn" style="margin-right:5px;">完成</a>
	</c:if> --%>
	
	
	<%-- <c:if test="${orderInfo.status == '6' or orderInfo.status == '7'}">
		<a href="javascript:void(0);" class="dobtn" style="margin-right:5px;" id="order_return_apply">退货申请</a>
		<a href="javascript:void(0);" class="dobtn" style="margin-right:5px;" id="order_back_apply">换货申请</a>
	</c:if> --%>
	
	<!-- 新的订单处理流程 -->
	<%-- <c:if test="${orderInfo.status==0 || orderInfo.status==1 || orderInfo.status==2}">
 		<a href="javascript:void(0);" id="order_cancel_btn" class="dobtn" style="margin-right:5px;">订单取消</a>
	</c:if>
	<c:if test="${toDo.flow_type=='dispatch' }">
		<a href="javascript:void(0);" name="BUTTON_ORDER_DISPATCH" service_type="${toDo.flow_def_id }" class="dobtn" style="margin-right:5px;">分派</a>
	</c:if>
	<c:if test="${toDo.flow_type=='audit' }">
		<a href="javascript:void(0);" name="BUTTON_ORDER_AUDIT" class="dobtn" style="margin-right:5px;">审核</a>
	</c:if>
	<c:if test="${toDo.flow_type=='openAccount' }">
		<a href="javascript:void(0);" name="BUTTON_ORDER_OPENACCOUNT" class="dobtn" style="margin-right:5px;">开户</a>
	</c:if>
	<c:if test="${toDo.flow_type=='confirm' }">
		<a href="javascript:void(0);" name="BUTTON_ORDER_CONFIRM" class="dobtn" style="margin-right:5px;">确认</a>
	</c:if>
	<c:if test="${toDo.flow_type=='package' }">
		<a href="javascript:void(0);" name="BUTTON_CUST_ACCEPT_C" class="dobtn" style="margin-right:5px;">备货</a>
	</c:if>
	<c:if test="${toDo.flow_type=='pay' }">
		<a href="javascript:void(0);" name="BUTTON_CARD_PAY" payment_id="${orderInfo.payment_id }" class="dobtn" style="margin-right:5px;">支付</a>
	</c:if>
	<c:if test="${toDo.flow_type=='delivery' }">
		<a href="javascript:void(0);" name="BUTTON_SHIPPING_C" class="dobtn" style="margin-right:5px;">发货</a>
	</c:if>
	<c:if test="${orderInfo.status==5||orderInfo.status==6||orderInfo.status==7 }">
		<a href="javascript:void(0);" class="dobtn" style="margin-right:5px;" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!order_prnt.do?orderId=${orderInfo.order_id }'); return false;">购买单</a>
		<a href="javascript:void(0);" class="dobtn" style="margin-right:5px;" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!delivery_prnt.do?orderId=${orderInfo.order_id }');return false;">配送单</a>
		<a href="javascript:void(0);" class="dobtn" style="margin-right:5px;" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!global_prnt.do?orderId=${orderInfo.order_id }');return false;">合单</a>
	</c:if>
	<c:if test="${toDo.flow_type=='received' }">
		<a href="javascript:void(0);" name="BUTTON_GET_SHIPPING_C" class="dobtn" style="margin-right:5px;">确认收货</a>
	</c:if>
	<c:if test="${toDo.flow_type=='finish' }">
		<a href="javascript:void(0);" name="BUTTON_FINISHED_C" class="dobtn" style="margin-right:5px;">完成</a>
	</c:if>
	<c:if test="${toDo.flow_type=='returned' }">
		<a href="javascript:void(0);" name="BUTTON_RETURNED_SHIPPING_C" class="dobtn" style="margin-right:5px;">退货</a>
	</c:if>
	<c:if test="${toDo.flow_type=='refund' }">
		<a href="javascript:void(0);" name="BUTTON_REFUND_C" class="dobtn" style="margin-right:5px;">退费</a>
	</c:if>
	<c:if test="${toDo.flow_type=='exchange'}">
		<a href="javascript:void(0);" name="BUTTON_CHANGE_SHIPPING" class="dobtn" style="margin-right:5px;">换货</a>
	</c:if> --%>
</div>
<div id="order_base_context">
<div class="formWarp">
<input id="base_order_id" type="hidden" value="${orderInfo.order_id }" />
	<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="0" class="closeArrow"></a>基本信息<div class="dobtnDiv"></div></div>
    <div id="order_tag_0" class="formGridDiv">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
          <tr>
            <th class="red">订单编号：</th>
            <td><input name="orderInfo.order_id" type="text" class="formIpt" value="${orderInfo.order_id }" /></td>
            <th>订单类型：</th>
            <td><html:selectdict name ="orderInfo.order_type" style="width:120px;" curr_val="${orderInfo.order_type}" attr_code="DC_ORDER_TYPE">
			</html:selectdict></td>
            <th>生成类型：</th>
            <td><html:selectdict name ="orderInfo.create_type" style="width:120px;" curr_val="${orderInfo.create_type}" attr_code="DC_ORDER_CREATE_TYPE">
			</html:selectdict></td>
            <th>订单来源：</th>
            <td><input name="orderInfo.app_key" type="text" class="formIpt" value="${orderInfo.app_key }" /></td>
          </tr>
          <tr>
            <th>支付状态：</th>
            <td><input name="orderInfo.pay_status" type="text" class="formIpt" value="${orderInfo.payStatus }" /></td>
            <th class="red">发货状态：</th>
            <td><input name="orderInfo.ship_status" type="text" class="formIpt" value="${orderInfo.shipStatus }" /></td>
            <th>订单状态：</th>
            <td><input name="orderInfo.status" type="text" class="formIpt" value="${orderInfo.orderStatus }" /></td>
            <th>购买方式：</th>
            <td><html:selectdict name="orderInfo.create_type"
					style="width:120px;" curr_val="${orderInfo.create_type}"
					attr_code="DC_ORDER_CREATE_TYPE">
				</html:selectdict></td>
          </tr>
          <tr>
            <th>开户状态：</th>
				<td><html:selectdict name="orderInfo.accept_status"
						style="width:120px;" curr_val="${orderInfo.accept_status}"
						attr_code="DC_ORDER_ACCEPT_STATUS">
					</html:selectdict></td>
            <th>支付方式：</th>
            <td><input name="orderInfo.payment_id" type="text" class="formIpt" value="${orderInfo.payment_name}" /></td>
            <th>外订单编号：</th>
            <td><input name="orderOuter.old_sec_order_id" type="text"
					class="formIpt" value="${orderOuter.old_sec_order_id}" /></td>
            <th>受理状态：</th>
            <td><input name="orderInfo.accpet_status" type="text" class="formIpt" value="${orderInfo.acceptStatus }" /></td>
          </tr>
          <tr>
            <th>记录状态：</th>
            <td><input name="orderInfo.disabled" type="text" class="formIpt" value="${orderInfo.disabled==0?'有效':'无效'}" /></td>
            <th>发货时间：</th>
				<!-- <html:dateformat pattern="yyyy-MM-dd" d_time="${orderInfo.pay_time }"></html:dateformat> -->
			<td><input name="orderInfo.ship_time" type="text"
				class="formIpt" value="${orderInfo.ship_time}" dataType="date" readonly="readonly"/></td>
			<th>付款时间：</th>
			<td><input name="orderInfo.pay_time" type="text"
				class="formIpt" value="${orderInfo.pay_time }" dataType="date" readonly="readonly"/></td>
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
					class="formIpt" value="${orderInfo.goods_num }" /></td>
				<th>商品总额：</th>
				<td><input name="orderInfo.goods_amount" type="text"
					class="formIpt" value="${orderInfo.goods_amount }" /></td>
				<!--             <th>生成类型：</th> -->
				<!--             <td><input name="orderInfo.create_type" type="text" class="formIpt" value="${orderInfo.create_type }" /></td> -->
				<th>优惠金额：</th>
				<td><input name="orderInfo.order_coupon" type="text"
					class="formIpt"
					value="${orderInfo.order_coupon }" /></td>
				<th>支付金额：</th>
				<td><input name="orderInfo.paymoney" type="text"
					class="formIpt" value="${orderInfo.paymoney }" /></td>	
			</tr>
			<tr>
				<th>订单总额：</th>
				<td><input name="orderInfo.order_amount" type="text"
					class="formIpt" value="${orderInfo.order_amount }" /></td>
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
		<a href="javascript:void(0);" name="show_close_btn" tag_id="2"
			class="closeArrow"></a>收货人信息
		<div class="dobtnDiv">
		</div>
	</div>
	<div id="order_tag_2" class="formGridDiv">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="formGrid">
			<tr>
				<th>购买人ID：</th>
				<td><input name="orderInfo.member_id" type="text"
					class="formIpt" value="${orderInfo.member_id }" /></td>
				<th>购买人：</th>
				<td><input name="member.uname" type="text" class="formIpt"
					value="${member.uname}" /></td>
				<th>E-mail：</th>
				<td><input name="member.email" type="text" class="formIpt"
					value="${member.email}" /></td>
				<th>电话：</th>
				<td><input name="orderInfo.ship_tel" type="text"
					class="formIpt" value="${orderInfo.ship_tel }" /></td>
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
				<th>地区：</th>
				<td><input name="orderInfo.shipping_area" type="text"
					class="formIpt" value="${orderInfo.shipping_area }" /></td>
			</tr>
			<tr>
				<th>地址：</th>
				<td colspan="7"><input name="orderInfo.ship_addr" type="text" style="width: 90%;"
					class="formIpt" value="${orderInfo.ship_addr }" /></td>
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
					class="formIpt" value="${orderInfo.ship_time }" dataType="date" readonly="readonly"/></td>
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
				<th>推荐人：</th>
				<td><input name="spread.name" type="text" class="formIpt"
					value="${spread.name}" /></td>
				<th>手机：</th>
				<td><input name="spread.mobile" type="text" class="formIpt"
					value="${spread.mobile}" /></td>
				<th>编号：</th>
				<td><input name="spread.spread_id" type="text" class="formIpt"
					value="${spread.spread_id}" /></td>
				<th>获取时间：</th>
				<td><input name="spread.create_time" type="text"
					class="formIpt" value="${spread.create_time }" dataType="date" readonly="readonly"/></td>
			</tr>
			<tr>
				<th>发展人姓名：</th>
				<td><input name="p_spread.name" type="text" class="formIpt"
					value="${p_spread.name }" /></td>
				<th>发展编号：</th>
				<td><input name="p_spread.spread_id" type="text"
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
</div>
<div id="order_goods_refund_dialog"></div>
<div id="order_goods_pay_dialog"></div>
<div id="order_goods_exchange_dialog"></div>
<div id="order_goods_bh_dialog"></div>
<div id="order_confirm_dialog"></div>
<!-- 订单分派 -->
<div id="order_dispatch_dlg"></div>
<div id="order_goods_finish_dialog"></div>
<div id="order_openaccount_dialog"></div>
<div id="order_goods_receive_dialog"></div>
<div id="order_goods_shipping_dialog"></div>
<div id="order_goods_returned_dialog"></div>
<div id="order_cancel_dialog"></div>
<script type="text/javascript">
	$(function(){
		$("#order_base_context input[type=text]").attr("readonly","readonly");
		$("#order_base_context select").attr("disabled","disabled");
		Eop.Dialog.init({id:"order_goods_refund_dialog",modal:true,title:"订单退款", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_goods_pay_dialog",modal:true,title:"订单支付", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_goods_exchange_dialog",modal:true,title:"订单换货", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_goods_bh_dialog",modal:true,title:"订单备货", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_confirm_dialog",modal:true,title:"订单确认", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_dispatch_dlg",modal:true,title:"订单分派", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_goods_finish_dialog",modal:true,title:"完成订单", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_openaccount_dialog",modal:true,title:"订单开户", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_goods_receive_dialog",modal:true,title:"确认收货", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_goods_shipping_dialog",modal:true,title:"订单发货", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_goods_returned_dialog",modal:true,title:"订单退货", height:"600px",width:"800px"});
		Eop.Dialog.init({id:"order_cancel_dialog",modal:true,title:"订单退货", height:"600px",width:"800px"});
	});
</script>
<script src="<%=request.getContextPath() %>/shop/admin/ddgj/js/orderbusiness.js"></script>
