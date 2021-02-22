<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>营业日报</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!getOrderBusinessReport.do'>
 <div class="searchformDiv">
		<!-- 选择框 -->
	  <jsp:include page="order_business_report_param.jsp"/>
</div>
</form>

<div class="grid" id="goodslist">
     <form method="POST"  id="OrderBusinessReport"  >
      
         <grid:grid from="webpage" formId="serchform" >
			<grid:header>
        <%-- <grid:cell width="50px">日期</grid:cell>  tid_time--%>
<grid:cell width="50px">订单处理人</grid:cell><%-- order_operator--%>
<grid:cell width="50px">操作类型</grid:cell><%-- order_type--%>
<%-- <grid:cell width="50px">数据来源</grid:cell>  order_from_str--%>
<grid:cell width="50px">订单来源</grid:cell><%-- order_from_str--%>
<grid:cell width="50px">订单编号</grid:cell><%-- out_order_id--%>
<grid:cell width="50px">订单类型</grid:cell><%-- order_type--%>
<grid:cell width="50px">商品类型</grid:cell><%-- goods_type2--%>
<grid:cell width="50px">商品名称</grid:cell><%-- goodsname--%>
<grid:cell width="50px">套餐名称</grid:cell><%-- plan_title--%>
<%-- <grid:cell width="50px">单卡/合约机</grid:cell>  pack_type--%>
<%-- <grid:cell width="50px">单量</grid:cell>  goods_num--%>
<grid:cell width="50px">新用户/老用户</grid:cell><%-- is_old--%>
<grid:cell width="50px">首月模式</grid:cell><%-- first_payment--%>
<grid:cell width="50px">合约期限</grid:cell><%-- contract_month--%>
<grid:cell width="50px">地市</grid:cell><%-- order_city--%>
<grid:cell width="50px">订购号码</grid:cell><%-- phone_num--%>
<grid:cell width="50px">入网姓名</grid:cell><%-- phone_owner_name--%>
<grid:cell width="50px">证件类型</grid:cell><%-- certi_type--%>
<grid:cell width="50px">证件号码</grid:cell><%-- cert_card_num--%>
<grid:cell width="50px">收件人</grid:cell><%-- ship_name--%>
<grid:cell width="50px">收件人电话</grid:cell><%-- ship_tel--%>
<%-- <grid:cell width="50px">收货地址</grid:cell>  ship_addr--%>
<grid:cell width="50px">支付方式</grid:cell><%-- pay_type--%>
<grid:cell width="50px">开户方式</grid:cell><%-- order_model--%>
<grid:cell width="50px">配送方式</grid:cell><%-- ship_type--%>
<grid:cell width="50px">物流类型</grid:cell><%-- shipping_company--%>
<grid:cell width="50px">物流单号</grid:cell><%-- logi_no--%>
<grid:cell width="50px">手机串号</grid:cell><%-- terminal_num--%>
<%-- <grid:cell width="50px">发票编号</grid:cell>  invoice_no--%>
<grid:cell width="50px">手机</grid:cell><%-- cellphone_type--%>
<%-- <grid:cell width="50px">优惠活动及赠品</grid:cell>  discountname--%>
<%-- <grid:cell width="50px">买家备注或留言</grid:cell>  buyer_message--%>
<grid:cell width="50px">商城实收</grid:cell><%-- paymoney--%>
<%-- <grid:cell width="50px">营业额</grid:cell>  busi_money--%>
<%-- <grid:cell width="50px">自提地址</grid:cell>  pickup_self_address--%>
<%-- <grid:cell width="50px">营业厅收货人</grid:cell>  outlet_receiver--%>
<%-- <grid:cell width="50px">营业厅联系电话</grid:cell>  outlet_contact_number--%>
<%-- <grid:cell width="50px">自提营业厅</grid:cell>  outlet_pickup_selft--%>
        		
			</grid:header>
		    <grid:body item="order">
<%-- <grid:cell>${order.tid_time}</grid:cell>  日期--%>
<grid:cell>${order.order_operator}</grid:cell>  <%--订单处理人--%>
<grid:cell>${order.order_type}</grid:cell>  <%--操作类型--%>
<%-- <grid:cell>${order.order_from_str}</grid:cell>  数据来源--%>
<grid:cell>${order.order_from_str}</grid:cell>  <%--订单来源--%>
<grid:cell>${order.out_order_id}</grid:cell>  <%--订单编号--%>
<grid:cell>${order.order_type}</grid:cell>  <%--订单类型--%>
<grid:cell>${order.goods_type2}</grid:cell>  <%--商品类型--%>
<grid:cell>${order.goodsname}</grid:cell>  <%--商品名称--%>
<grid:cell>${order.plan_title}</grid:cell>  <%--套餐名称--%>
<%-- <grid:cell>${order.pack_type}</grid:cell>  单卡/合约机--%>
<%-- <grid:cell>${order.goods_num}</grid:cell>  单量--%>
<grid:cell>${order.is_old}</grid:cell>  <%--新用户/老用户--%>
<grid:cell>${order.first_payment}</grid:cell>  <%--首月模式--%>
<grid:cell>${order.contract_month}</grid:cell>  <%--合约期限--%>
<grid:cell>${order.order_city}</grid:cell>  <%--地市--%>
<grid:cell>${order.phone_num}</grid:cell>  <%--订购号码--%>
<grid:cell>${order.phone_owner_name}</grid:cell>  <%--入网姓名--%>
<grid:cell>${order.certi_type}</grid:cell>  <%--证件类型--%>
<grid:cell>${order.cert_card_num}</grid:cell>  <%--证件号码--%>
<grid:cell>${order.ship_name}</grid:cell>  <%--收件人--%>
<grid:cell>${order.ship_tel}</grid:cell>  <%--收件人电话--%>
<%-- <grid:cell>${order.ship_addr}</grid:cell>  收货地址--%>
<grid:cell>${order.pay_type}</grid:cell>  <%--支付方式--%>
<grid:cell>${order.order_model}</grid:cell>  <%--开户方式--%>
<grid:cell>${order.ship_type}</grid:cell>  <%--配送方式--%>
<grid:cell>${order.shipping_company}</grid:cell>  <%--物流类型--%>
<grid:cell>${order.logi_no}</grid:cell>  <%--物流单号--%>
<grid:cell>${order.terminal_num}</grid:cell>  <%--手机串号--%>
<%-- <grid:cell>${order.invoice_no}</grid:cell>  发票编号--%>
<grid:cell>${order.cellphone_type}</grid:cell>  <%--手机--%>
<%-- <grid:cell>${order.discountname}</grid:cell>  优惠活动及赠品--%>
<%-- <grid:cell>${order.buyer_message}</grid:cell>  买家备注或留言--%>
<grid:cell>${order.paymoney}</grid:cell>  <%--商城实收--%>
<%-- <grid:cell>${order.busi_money}</grid:cell>  营业额--%>
<%-- <grid:cell>${order.pickup_self_address}</grid:cell>  自提地址--%>
<%-- <grid:cell>${order.outlet_receiver}</grid:cell>  营业厅收货人--%>
<%-- <grid:cell>${order.outlet_contact_number}</grid:cell>  营业厅联系电话--%>
<%-- <grid:cell>${order.outlet_pickup_selft}</grid:cell>  自提营业厅--%>

		  	</grid:body>
		</grid:grid>
	</form>
	
	<c:if test="${empty webpage }"><%--当页面还没读取数据的时候用来填充，以防查询条件下拉显示不全 --%>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	<br></br>
	</div>
   </c:if>
	

	
 </div>
 </div>
</div>
</body>
</html>
<script type="text/javascript">
function check() {

	var create_start = $("input[name=params.create_start]");
	var create_end = $("input[name=params.create_end]");
	
	if ($.trim(create_start.val())=="" || $.trim(create_end.val())=="") {
		alert("请选择创建时间,并且查询范围小于等于10天!");
		return false;
	}
	
	var startTime = new Date(create_start.val().replace(/-/g, "/"));
 	var endTime = new Date(create_end.val().replace(/-/g, "/"));
	var days = endTime.getTime() - startTime.getTime();
 	var time = parseInt(days / (1000 * 60 * 60 * 24));
 	if (time < 0 || time > 10) {
		alert("创建时间查询范围必须小于等于10天!");
		return false;
 	}
 	
	return true;
}
$(function(){
	$("#searchBtn").unbind("click").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderBusinessReport.do");
			$("#serchform").submit();
		}
	});
	$("#excelOrd").unbind("click").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!getOrderBusinessReport.do?ajax=yes&excel=check";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过10000，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderBusinessReport.do?ajax=yes&excel=yes");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderBusinessReport.do");
				}
			},'json');
			
		}
	});
	
});
</script>
 