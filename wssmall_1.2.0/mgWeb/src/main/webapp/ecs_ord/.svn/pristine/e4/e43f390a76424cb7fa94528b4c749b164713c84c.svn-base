<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单数据查询</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!showMonitorList.do'>
 <div class="searchformDiv">
		<!-- 选择框 -->
	  <jsp:include page="order_data_by_search_report_param.jsp"/>
</div>
</form>
<div class="grid" id="goodslist">
     <form method="POST"  id="order_data_by_search"  >
         <grid:grid from="webpage" formId="serchform" >
			<grid:header>
<grid:cell width="50px">订单号</grid:cell><%-- out_tid--%>
<grid:cell width="50px">订单生成日期</grid:cell><%-- tid_time--%>
<grid:cell width="50px">订单领取时间</grid:cell><%-- order_collect_time--%>
<%-- <grid:cell width="50px">用户签收时间</grid:cell>  package_acp_date--%>
<%-- <grid:cell width="50px">时间差(用户签收时间-订单领取时间)</grid:cell>  time_difference--%>
<%-- <grid:cell width="50px">数据来源</grid:cell>  order_from--%>
<grid:cell width="50px">商城来源</grid:cell><%-- order_origin--%>
<grid:cell width="50px">推广渠道</grid:cell><%-- spread_channel--%>
<grid:cell width="50px">发展点名称</grid:cell><%-- development_name--%>
<%-- <grid:cell width="50px">发展点编码</grid:cell>  development_code--%>
<grid:cell width="50px">订单处理人</grid:cell><%-- handle_name--%>
<%-- <grid:cell width="50px">订单处理时间</grid:cell>  handle_time--%>
<grid:cell width="50px">订单稽核人</grid:cell><%-- f_op_id--%>
<%-- <grid:cell width="50px">订单稽核时间</grid:cell>  f_end_time--%>
<grid:cell width="50px">发货时间</grid:cell><%-- shipping_time--%>
<grid:cell width="50px">物流编号</grid:cell><%-- logi_no--%>
<%-- <grid:cell width="50px">终端串号</grid:cell>  terminal_num--%>
<grid:cell width="50px">退单原因</grid:cell><%-- refund_desc--%>
<grid:cell width="50px">地市</grid:cell><%-- lan_code--%>
<%-- <grid:cell width="50px">商品类型</grid:cell>  goods_type--%>
<grid:cell width="50px">商品名称</grid:cell><%-- goodsName--%>
<grid:cell width="50px">发票串号</grid:cell><%-- invoice_no--%>
<grid:cell width="50px">套餐名称</grid:cell><%-- plan_title--%>
<%-- <grid:cell width="50px">特权包</grid:cell>  privilege_combo--%>
<grid:cell width="50px">用户号码</grid:cell><%-- phone_num--%>
<%-- <grid:cell width="50px">副卡</grid:cell>  zb_fuka_info--%>
<grid:cell width="50px">终端</grid:cell><%-- terminal--%>
<%-- <grid:cell width="50px">合约类型</grid:cell>  prod_cat_id--%>
<grid:cell width="50px">合约期限</grid:cell><%-- contract_month--%>
<grid:cell width="50px">实收(元)</grid:cell><%-- pro_realfee--%>
<%-- <grid:cell width="50px">支付状态</grid:cell>  pay_status--%>
<grid:cell width="50px">支付类型</grid:cell><%-- paytype--%>
<grid:cell width="50px">订单状态</grid:cell><%-- status--%>
<%-- <grid:cell width="50px">是否开户（是/否）</grid:cell>  account_status--%>
<grid:cell width="50px">用户类型</grid:cell><%-- is_old--%>
<grid:cell width="50px">联系人</grid:cell><%-- ship_name--%>
<grid:cell width="50px">联系人电话</grid:cell><%-- reference_phone--%>
<grid:cell width="50px">收货地址</grid:cell><%-- contact_addr--%>
<%-- <grid:cell width="50px">其他联系电话</grid:cell>  carry_person_mobile--%>
<grid:cell width="50px">操作备注</grid:cell><%-- audit_remark--%>
<grid:cell width="50px">订单备注</grid:cell><%-- service_remarks--%>
<%-- <grid:cell width="50px">发展人</grid:cell>  devlopname--%>
<grid:cell width="50px">配送方式</grid:cell><%-- sending_type--%>
<%-- <grid:cell width="50px">奖品</grid:cell>  prize--%>
<%-- <grid:cell width="50px">特色包</grid:cell>  special_combo--%>
<grid:cell width="50px">入网人姓名</grid:cell><%-- phone_owner_name--%>

				
			</grid:header>
		    <grid:body item="order">
<grid:cell>${order.out_tid}</grid:cell>  <%--订单号--%>
<grid:cell>${order.tid_time}</grid:cell>  <%--订单生成日期--%>
<grid:cell>${order.order_collect_time}</grid:cell>  <%--订单领取时间--%>
<%-- <grid:cell>${order.package_acp_date}</grid:cell>  用户签收时间--%>
<%-- <grid:cell>${order.time_difference}</grid:cell>  时间差(用户签收时间-订单领取时间)--%>
<%-- <grid:cell>${order.order_from}</grid:cell>  数据来源--%>
<grid:cell>${order.order_origin}</grid:cell>  <%--商城来源--%>
<grid:cell>${order.spread_channel}</grid:cell>  <%--推广渠道--%>
<grid:cell>${order.development_name}</grid:cell>  <%--发展点名称--%>
<%-- <grid:cell>${order.development_code}</grid:cell>  发展点编码--%>
<grid:cell>${order.handle_name}</grid:cell>  <%--订单处理人--%>
<%-- <grid:cell>${order.handle_time}</grid:cell>  订单处理时间--%>
<grid:cell>${order.f_op_id}</grid:cell>  <%--订单稽核人--%>
<%-- <grid:cell>${order.f_end_time}</grid:cell>  订单稽核时间--%>
<grid:cell>${order.shipping_time}</grid:cell>  <%--发货时间--%>
<grid:cell>${order.logi_no}</grid:cell>  <%--物流编号--%>
<%-- <grid:cell>${order.terminal_num}</grid:cell>  终端串号--%>
<grid:cell>${order.refund_desc}</grid:cell>  <%--退单原因--%>
<grid:cell>${order.lan_code}</grid:cell>  <%--地市--%>
<%-- <grid:cell>${order.goods_type}</grid:cell>  商品类型--%>
<grid:cell>${order.goodsName}</grid:cell>  <%--商品名称--%>
<grid:cell>${order.invoice_no}</grid:cell>  <%--发票串号--%>
<grid:cell>${order.plan_title}</grid:cell>  <%--套餐名称--%>
<%-- <grid:cell>${order.privilege_combo}</grid:cell>  特权包--%>
<grid:cell>${order.phone_num}</grid:cell>  <%--用户号码--%>
<%-- <grid:cell>${order.zb_fuka_info}</grid:cell>  副卡--%>
<grid:cell>${order.terminal}</grid:cell>  <%--终端--%>
<%-- <grid:cell>${order.prod_cat_id}</grid:cell>  合约类型--%>
<grid:cell>${order.contract_month}</grid:cell>  <%--合约期限--%>
<grid:cell>${order.pro_realfee}</grid:cell>  <%--实收(元)--%>
<%-- <grid:cell>${order.pay_status}</grid:cell>  支付状态--%>
<grid:cell>${order.paytype}</grid:cell>  <%--支付类型--%>
<grid:cell>${order.status}</grid:cell>  <%--订单状态--%>
<%-- <grid:cell>${order.account_status}</grid:cell>  是否开户（是/否）--%>
<grid:cell>${order.is_old}</grid:cell>  <%--用户类型--%>
<grid:cell>${order.ship_name}</grid:cell>  <%--联系人--%>
<grid:cell>${order.reference_phone}</grid:cell>  <%--联系人电话--%>
<grid:cell>${order.contact_addr}</grid:cell>  <%--收货地址--%>
<%-- <grid:cell>${order.carry_person_mobile}</grid:cell>  其他联系电话--%>
<grid:cell>${order.audit_remark}</grid:cell>  <%--操作备注--%>
<grid:cell>${order.service_remarks}</grid:cell>  <%--订单备注--%>
<%-- <grid:cell>${order.devlopname}</grid:cell>  发展人--%>
<grid:cell>${order.sending_type}</grid:cell>  <%--配送方式--%>
<%-- <grid:cell>${order.prize}</grid:cell>  奖品--%>
<%-- <grid:cell>${order.special_combo}</grid:cell>  特色包--%>
<grid:cell>${order.phone_owner_name}</grid:cell>  <%--入网人姓名--%>


		  	    
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

	</div>
   </c:if>
	
	
	
 </div>
 </div>
</div>
</body>
</html>
<script type="text/javascript">
function check() {

	var invoice_num = $("input[name=params.invoice_num]");
	if ($.trim(invoice_num.val())!="") {
		return true;
	}
	
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
$(function(){//页面搜索按钮
	$("#searchBtn").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderDataBySearch.do");
			$("#serchform").submit();
		}
	});
	$("#excelOrd").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!getOrderDataBySearch.do?ajax=yes&excel=check";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderDataBySearch.do?ajax=yes&excel=yes");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderDataBySearch.do");
				}
			},'json');
			
		}
	});
	
});
</script>
 