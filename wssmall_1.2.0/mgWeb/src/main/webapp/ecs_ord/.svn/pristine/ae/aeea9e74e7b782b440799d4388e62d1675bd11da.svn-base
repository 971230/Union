<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宽带电商化报表</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!getOrderBroadbandReport.do'>
 <div class="searchformDiv">
		<!-- 选择框 -->
	  <jsp:include page="order_broadband_commerce_param.jsp"/>
</div>
</form>

<div class="grid" id="goodslist">
     <form method="POST"  id="OrderBroadbandReport"  >
         <grid:grid from="webpage" formId="serchform" >
			<grid:header>
<grid:cell width="30px">地市</grid:cell> 
<grid:cell width="30px">县分</grid:cell>
<grid:cell width="50px">订单来源</grid:cell>
<grid:cell width="50px">内部订单号</grid:cell>
<grid:cell width="50px">外部订单号</grid:cell>
<grid:cell width="50px">收单时间</grid:cell>
<grid:cell width="50px">联系人</grid:cell>
<grid:cell width="50px">联系电话</grid:cell>
<grid:cell width="50px">联系地址</grid:cell>
<grid:cell width="50px">商品编码</grid:cell>
<grid:cell width="50px">商品名称</grid:cell>
<grid:cell width="50px">下单渠道编码</grid:cell>
<grid:cell width="50px">下单渠道名称</grid:cell>
<grid:cell width="50px">下单人编码</grid:cell>
<grid:cell width="50px">下单人名称</grid:cell>
<grid:cell width="50px">发展点编码</grid:cell>
<grid:cell width="50px">发展点名称</grid:cell>
<grid:cell width="50px">发展人编码</grid:cell>
<grid:cell width="50px">发展人名称</grid:cell>
<grid:cell width="50px">订单状态</grid:cell>
<grid:cell width="50px">受理点编码</grid:cell>
<grid:cell width="50px">受理点名称</grid:cell>
<grid:cell width="50px">受理人工号</grid:cell>
<grid:cell width="50px">受理人姓名</grid:cell>
<grid:cell width="50px">订单金额</grid:cell>
<grid:cell width="50px">外线施工状态</grid:cell>
<grid:cell width="50px">竣工时间</grid:cell>
<grid:cell width="50px">订单派单时间</grid:cell>
        		
			</grid:header>
		    <grid:body item="order">
		    
<grid:cell>${order.order_city}</grid:cell>  
<grid:cell>${order.order_county}</grid:cell>  
<grid:cell>${order.order_from}</grid:cell>  
<grid:cell>${order.order_id}</grid:cell>
<grid:cell>${order.out_tid}</grid:cell>  
<grid:cell>${order.receipt_time}</grid:cell> 
<grid:cell>${order.ship_name}</grid:cell>  
<grid:cell>${order.ship_tel}</grid:cell>  
<grid:cell>${order.ship_addr}</grid:cell>  
<grid:cell>${order.goods_id}</grid:cell> 
<grid:cell>${order.goodsname}</grid:cell>  
<grid:cell>${order.office_id}</grid:cell>  
<grid:cell>${order.office_name}</grid:cell> 
<grid:cell>${order.deal_operator_num}</grid:cell> 
<grid:cell>${order.deal_operator_name}</grid:cell>  
<grid:cell>${order.development_point_code}</grid:cell>  
<grid:cell>${order.development_point_name}</grid:cell>  
<grid:cell>${order.development_code}</grid:cell>  
<grid:cell>${order.development_name}</grid:cell>  
<grid:cell>${order.status}</grid:cell>
<grid:cell>${order.out_office_id}</grid:cell> 
<grid:cell>${order.out_office_name}</grid:cell> 
<grid:cell>${order.operator_id}</grid:cell> 
<grid:cell>${order.operator_name}</grid:cell>
<grid:cell>${order.pro_realfee}</grid:cell>  
<grid:cell>${order.wxsg_status}</grid:cell> 
<grid:cell>${order.complete_time}</grid:cell> 
<grid:cell>${order.dispatch_time}</grid:cell>  

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
	
 	
	return true;
}
$(function(){
	$("#searchBtn").unbind("click").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderBroadbandReport.do");
			$("#serchform").submit();
		}
	});
	$("#excelOrd").unbind("click").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!getOrderBroadbandReport.do?ajax=yes&excel=check";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderBroadbandReport.do?ajax=yes&excel=yes");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getOrderBroadbandReport.do");
				}
			},'json');
			
		}
	});
	
});
</script>
 