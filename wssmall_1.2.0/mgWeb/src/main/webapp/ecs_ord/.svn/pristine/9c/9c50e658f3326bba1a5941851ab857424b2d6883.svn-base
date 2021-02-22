<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>退款订单报表</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!getOrderWhichRefund.do'>
 <div class="searchformDiv">
		<!-- 选择框 -->
	  <jsp:include page="order_refund_report_param.jsp"/>
</div>
</form>

<div class="grid" id="goodslist">
     <form method="POST"  id="order_refund_report"  >
      
         <grid:grid from="webpage" formId="serchform" >
			<grid:header>
				<grid:cell width="50px">订单来源</grid:cell><%-- order_from--%>
<grid:cell width="50px">订单编号</grid:cell><%-- out_tid--%>
<grid:cell width="50px">订单状态</grid:cell><%-- status--%>
<grid:cell width="50px">订单受理日期</grid:cell><%-- bss_account_time--%>
<grid:cell width="50px">退款日期</grid:cell><%-- refund_time--%>
<grid:cell width="50px">地市</grid:cell><%-- order_city--%>
<grid:cell width="50px">商品类型</grid:cell><%-- goods_type--%>
<grid:cell width="50px">商品名称</grid:cell><%-- GoodsName--%>
<grid:cell width="50px">订购号码</grid:cell><%-- phone_num--%>
<grid:cell width="50px">实收</grid:cell><%-- paymoney--%>
<grid:cell width="50px">营业款</grid:cell><%-- busi_money--%>
<grid:cell width="50px">终端品牌</grid:cell><%-- brand_name--%>
<grid:cell width="50px">终端型号</grid:cell><%-- specificatio_nname--%>
<grid:cell width="50px">终端颜色</grid:cell><%-- terminal_color--%>
<grid:cell width="50px">终端串号</grid:cell><%-- terminal_num--%>
<grid:cell width="50px">用户类型</grid:cell><%-- is_old--%>
<grid:cell width="50px">联系人</grid:cell><%-- phone_owner_name--%>
<grid:cell width="50px">联系人电话</grid:cell><%-- ship_tel--%>



				
			</grid:header>
		    <grid:body item="order">
		  	    <grid:cell>${order.order_from}</grid:cell>  <%--订单来源--%>
<grid:cell>${order.out_tid}</grid:cell>  <%--订单编号--%>
<grid:cell>${order.status}</grid:cell>  <%--订单状态--%>
<grid:cell>${order.bss_account_time}</grid:cell>  <%--订单受理日期--%>
<grid:cell>${order.refund_time}</grid:cell>  <%--退款日期--%>
<grid:cell>${order.order_city}</grid:cell>  <%--地市--%>
<grid:cell>${order.goods_type}</grid:cell>  <%--商品类型--%>
<grid:cell>${order.GoodsName}</grid:cell>  <%--商品名称--%>
<grid:cell>${order.phone_num}</grid:cell>  <%--订购号码--%>
<grid:cell>${order.paymoney}</grid:cell>  <%--实收--%>
<grid:cell>${order.busi_money}</grid:cell>  <%--营业款--%>
<grid:cell>${order.brand_name}</grid:cell>  <%--终端品牌--%>
<grid:cell>${order.specificatio_nname}</grid:cell>  <%--终端型号--%>
<grid:cell>${order.terminal_color}</grid:cell>  <%--终端颜色--%>
<grid:cell>${order.terminal_num}</grid:cell>  <%--终端串号--%>
<grid:cell>${order.is_old}</grid:cell>  <%--用户类型--%>
<grid:cell>${order.phone_owner_name}</grid:cell>  <%--联系人--%>
<grid:cell>${order.ship_tel}</grid:cell>  <%--联系人电话--%>


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
		alert("请选择创建时间,并且查询范围小于等于31天!");
		return false;
	}
	
	var startTime = new Date(create_start.val().replace(/-/g, "/"));
 	var endTime = new Date(create_end.val().replace(/-/g, "/"));
	var days = endTime.getTime() - startTime.getTime();
 	var time = parseInt(days / (1000 * 60 * 60 * 24));
 	if (time < 0 || time > 31) {
		alert("创建时间查询范围必须小于等于31天!");
		return false;
 	}
 	
	return true;
}
$(function(){
	$("#searchBtn").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderWhichRefund.do");
			$("#serchform").submit();
		}
	});
	$("#excelOrd").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!getOrderWhichRefund.do?ajax=yes&excel=check";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderWhichRefund.do?ajax=yes&excel=yes");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderWhichRefund.do");
				}
			},'json');
			
		}
	});
	
});
</script>
 