<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/shop/admin/js/Order.js"></script>
<form method="post" id="serchform" action='ordern!listn.do'>
<jsp:include page="order_select.jsp"/>
</form>
<div class="grid">
<!-- 
<div class="toolbar">
	
	<ul>
		<li><a href="javascript:;" id="delBtn">删除</a></li>
		<li><a href="order!trash_list.do">回收站</a></li>
	</ul>

	<div style="clear:both"></div>
</div>
-->
<form method="POST" >
<grid:grid  from="webpage" excel="yes">
	<grid:header>
		<grid:cell sort="sn" width="130px">商品名称&nbsp;&nbsp;</grid:cell>
		<grid:cell sort="sn" width="150px">订单号&nbsp;&nbsp;<span class="help_icon" helpid="order_showdetail"></span></grid:cell>
		
		<grid:cell width="80px">下单日期</grid:cell> 
		<!--<grid:cell  width="55px">订单类型</grid:cell>-->
		<grid:cell sort="status" >订单状态</grid:cell> 
		<grid:cell >订单总额</grid:cell>
		
		<grid:cell >申请人</grid:cell> 
		<grid:cell >状态</grid:cell> 
		<grid:cell >受理状态</grid:cell>
		<grid:cell >发货状态</grid:cell>
	
		<!--<grid:cell >配送方式</grid:cell>-->
		<grid:cell >申请区域</grid:cell>
		<!--<grid:cell >支付方式</grid:cell> -->
		<grid:cell>操作</grid:cell> 
		
	</grid:header>
  <grid:body item="order">
  	
  		<grid:cell><input type="checkbox" name="id" value="${order.order_id }" style='display:none;'/>${order.goods_name}</grid:cell>
        <grid:cell>
        	<c:if test="${order.status<3}"><B></c:if><a href="ordern!detail.do?orderId=${order.order_id}">${order.order_id} </a><c:if test="${order.status<3}"></B></c:if>
        	<c:if test="${order.source_from =='00C'}"> <img src='${ctx}/shop/admin/images/w_a.jpg' style='float:right;'/></c:if>
        	
        </grid:cell>
       
        <grid:cell><c:if test="${order.status<3}"></c:if><html:dateformat pattern="yyyy-MM-dd" d_time="${order.create_time}"></html:dateformat> <c:if test="${order.status<3}"></c:if></grid:cell> 
        <!--<grid:cell><c:if test="${order.status<3}"></c:if>${order.orderType}<c:if test="${order.status<3}"></c:if></grid:cell>-->
         <grid:cell><c:if test="${order.status<3}"></c:if>${order.orderStatus}<c:if test="${order.status<3}"></c:if></grid:cell> 
        <grid:cell>￥${order.order_amount}</grid:cell> 
         <grid:cell>
	        ${order.user_name}
         </grid:cell> 
        <grid:cell>${order.payStatus}</grid:cell> 
        <grid:cell>${order.acceptStatus}</grid:cell> 
        <grid:cell>${order.shipStatus}</grid:cell> 
         <grid:cell>${order.lan_name}</grid:cell>
         
        <!--<grid:cell><c:if test="${order.status<3}"></c:if>${order.shipping_type}<c:if test="${order.status<3}"></c:if></grid:cell> -->
        <!--<grid:cell><c:if test="${order.status<3}"></c:if>${order.payment_name}<c:if test="${order.status<3}"></c:if></grid:cell> -->
        <!--<grid:cell><a href="order!detail.do?orderId=${order.order_id }" title="订单详细"><img class="modify" src="images/transparent.gif" ></a></grid:cell> --> 
        <grid:cell>
        	${order.oper_btns}
        	<a title="购物清单打印" href='javascript:void(0);' class="p_prted" id="orderprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!order_prnt.do?orderId=${order.order_id }'); return false;" >购买单</a><span class='tdsper'></span>
        	<a title="配货单打印"  href='javascript:void(0);' class="p_prted" id="deliveryprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!delivery_prnt.do?orderId=${order.order_id }');return false;" >配送单</a><span class='tdsper'></span>
        	<a title="联合打印"  href='javascript:void(0);'  class="p_prted" id="globalprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!global_prnt.do?orderId=${order.order_id }');return false;" >合单</a>
        	<!-- 
        	<button title="快递单打印" class="p_prted" id="shipprntBtn" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!ship_prnt_step1.do?orderId=${order.order_id }');return false;" >递</button>
        	 -->
        	<!-- 操作按钮 -->
			
        	
        </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
<script>
	$(function(){$("[to_detail='yes']").bind("click",function(){
			
			var orderId = $(this).closest("tr").find(":checkbox").val();
			
			location.href=ctx+'/shop/admin/ordern!detail.do?orderId='+orderId;
			return false;
	})})
</script>
