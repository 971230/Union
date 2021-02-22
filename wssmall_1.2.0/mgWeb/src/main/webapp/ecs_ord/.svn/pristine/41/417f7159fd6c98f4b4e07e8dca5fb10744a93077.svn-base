<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>库管日报</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!getOrdDistributionReport.do'>
 <div class="searchformDiv">
		<!-- 选择框 -->
	  <jsp:include page="ord_distribution_report_param.jsp"/>
</div>
</form>

<div class="grid" id="goodslist">
     <form method="POST"  id="warehouse_daily_report"  >
      
         <grid:grid from="webpage" formId="serchform" >
			<grid:header>
        <%-- <grid:cell width="80px">序号</grid:cell> --%>
        <grid:cell width="80px">订单编号</grid:cell>
        <grid:cell width="50px">订单状态</grid:cell>
        <grid:cell width="50px">发货人</grid:cell>
        <%--<grid:cell width="80px">发货时间</grid:cell> --%>
        <grid:cell width="80px">商品名称</grid:cell>
        <grid:cell width="80px">手机型号</grid:cell>
        <grid:cell width="80px">手机串号</grid:cell>
        <grid:cell width="80px">成交时间</grid:cell>
        <grid:cell width="80px">稽核人</grid:cell>
        <grid:cell width="80px">稽核时间</grid:cell>
        <grid:cell width="80px">新老用户</grid:cell>
        <grid:cell width="80px">入网姓名</grid:cell>
        <grid:cell width="80px">入网号码</grid:cell>
        <grid:cell width="80px">联系人姓名</grid:cell>
        <grid:cell width="80px">联系电话</grid:cell>
        <grid:cell width="80px">物流单号</grid:cell>
        <grid:cell width="80px">返回单号</grid:cell>
        <grid:cell width="80px">物流地址</grid:cell>
        
        <grid:cell width="50px">物流公司</grid:cell> <%-- shipping_company--%>
<%-- <grid:cell width="50px">付款方式</grid:cell>  pay_method--%>
<%-- <grid:cell width="50px">金额</grid:cell>  fee--%>
<%-- <grid:cell width="50px">发货批次</grid:cell>  ship_batch--%>
<%-- <grid:cell width="50px">发货人</grid:cell>  goods_shipper--%>
<%-- <grid:cell width="50px">发货时间</grid:cell>  goods_send_time--%>
<%-- <grid:cell width="50px">入网证件</grid:cell>  net_certi--%>
<%-- <grid:cell width="50px">受理协议</grid:cell>  accept_agree--%>
<%-- <grid:cell width="50px">靓号协议</grid:cell>  liang_agree--%>
<%-- <grid:cell width="50px">存档编号</grid:cell>  receive_num--%>
<%-- <grid:cell width="50px">资料归档人</grid:cell>  eost.j_op_id--%>
<%-- <grid:cell width="50px">资料归档时间</grid:cell>  archive_time--%>


        

				
			</grid:header>
		    <grid:body item="order">
                           <%-- <grid:cell>${order.sequence_id}</grid:cell> --%>
                           <grid:cell>${order.out_order_id}</grid:cell>
                           <grid:cell>${order.status}</grid:cell>
                           <grid:cell>${order.opname}</grid:cell>
                            <%--<grid:cell>${order.h_end_time}</grid:cell> --%>                          
                           <grid:cell>${order.goodsname}</grid:cell>
                           <grid:cell>${order.specificatio_nname}</grid:cell>
                           <grid:cell>${order.terminal_num}</grid:cell>
                           <grid:cell>${order.tid_time}</grid:cell>
                           <grid:cell>${order.lock_user_name}</grid:cell>
                           <grid:cell>${order.lock_time}</grid:cell>
                           <grid:cell>${order.is_old}</grid:cell>
                           <grid:cell>${order.phone_owner_name}</grid:cell>
                           <grid:cell>${order.phone_num}</grid:cell>
                           <grid:cell>${order.ship_name}</grid:cell>
                           <grid:cell>${order.ship_tel}</grid:cell>
                           <grid:cell>${order.logi_no}</grid:cell>
                           <grid:cell>${order.receipt_no}</grid:cell>
                           <grid:cell>${order.ship_addr}</grid:cell>
                           
                           <grid:cell>${order.shipping_company}</grid:cell>  <%--  物流公司--%>
                           
<%-- <grid:cell>${order.pay_method}</grid:cell>  付款方式--%>
<%-- <grid:cell>${order.fee}</grid:cell>  金额--%>
<%-- <grid:cell>${order.ship_batch}</grid:cell>  发货批次--%>
<%-- <grid:cell>${order.goods_shipper}</grid:cell>  发货人--%>
<%-- <grid:cell>${order.goods_send_time}</grid:cell>  发货时间--%>
<%-- <grid:cell>${order.net_certi}</grid:cell>  入网证件--%>
<%-- <grid:cell>${order.accept_agree}</grid:cell>  受理协议--%>
<%-- <grid:cell>${order.liang_agree}</grid:cell>  靓号协议--%>
<%-- <grid:cell>${order.receive_num}</grid:cell>  存档编号--%>
<%-- <grid:cell>${order.eost.j_op_id}</grid:cell>  资料归档人--%>
<%-- <grid:cell>${order.archive_time}</grid:cell>  资料归档时间--%>
                           
                           

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
 	
 	
	return true;
}
$(function(){
	$("#searchBtn").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrdDistributionReport.do");
			$("#serchform").submit();
		}
	});
	$("#excelOrd").click(function(){
		
			var url = ctx+"/shop/admin/ordAuto!getOrdDistributionReport.do?ajax=yes&excel=check";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrdDistributionReport.do?ajax=yes&excel=yes");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrdDistributionReport.do");
				}
			},'json');
			
		
	});
	
});
</script>
 