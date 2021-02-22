<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单领取报表</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!getOrderReceiveReport.do'>
 <div class="searchformDiv">
		<!-- 选择框 -->
	  <jsp:include page="ord_receive_report_param.jsp"/>
</div>
</form>

<div class="grid" id="goodslist">
     <form method="POST"  id="warehouse_daily_report"  >
      
         <grid:grid from="webpage" formId="serchform" >
			<grid:header>
				<grid:cell width="80px">订单编号</grid:cell>
				<grid:cell width="80px">订单来源</grid:cell>
				<grid:cell width="80px">地市</grid:cell>
				<grid:cell width="80px">商品名称</grid:cell>
				<grid:cell width="80px">订单状态</grid:cell>
				<grid:cell width="80px">是否领取</grid:cell>
				<grid:cell width="80px">领取人</grid:cell>
				<grid:cell width="80px">订购号码</grid:cell>
				<grid:cell width="80px">套餐名称</grid:cell>
				<grid:cell width="80px">成交时间</grid:cell>
				<grid:cell width="80px">支付状态</grid:cell>
				<grid:cell width="80px">支付类型</grid:cell>
				<grid:cell width="80px">订单金额</grid:cell>
				<grid:cell width="80px">实收金额</grid:cell>
				<grid:cell width="80px">终端类型</grid:cell>
				<grid:cell width="80px">终端品牌</grid:cell>
				<grid:cell width="80px">终端型号</grid:cell>
				<grid:cell width="80px">终端串号</grid:cell>
				<grid:cell width="80px">物流单号</grid:cell>

				
			</grid:header>
		    <grid:body item="order">
                <grid:cell>${order.order_id}</grid:cell>
                <grid:cell>${order.order_from_str}</grid:cell>
                <grid:cell>${order.order_city}</grid:cell>
                <grid:cell>${order.order_goodsname}</grid:cell>
                <grid:cell>${order.order_status}</grid:cell>
                <grid:cell>${order.order_receive}</grid:cell>
                <grid:cell>${order.lock_user_name}</grid:cell>
                <grid:cell>${order.phone_num}</grid:cell>
                <grid:cell>${order.plan_title}</grid:cell>
                <grid:cell>${order.tid_time}</grid:cell>
                <grid:cell>${order.pay_status}</grid:cell>
                <grid:cell>${order.pay_type}</grid:cell>
                <grid:cell>${order.order_amount}</grid:cell>
                <grid:cell>${order.paymoney}</grid:cell>
                <grid:cell>${order.pack_type}</grid:cell>
                <grid:cell>${order.brand_name}</grid:cell>
                <grid:cell>${order.specificatio_nname}</grid:cell>
                <grid:cell>${order.terminal_num}</grid:cell>
                <grid:cell>${order.logi_no}</grid:cell>


		  	</grid:body>
		</grid:grid>
	</form>
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
	$("#searchBtn").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderReceiveReport.do");
			$("#serchform").submit();
		}
	});
	$("#excelOrd").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!getOrderReceiveReport.do?ajax=yes&excel=check";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderReceiveReport.do?ajax=yes&excel=yes");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderReceiveReport.do");
				}
			},'json');
			
		}
	});
	
});
</script>
 