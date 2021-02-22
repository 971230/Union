<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宽带电商各环节订单监控表</title>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
</head>
<style>
#kuandai {
	tr td,th{border:1px solid #fff;}
	border-collapse: collapse;
}
.tdOne{
border:1px solid #6B6B6B;
border:1;
cellpadding:0;
cellspacing:0;
}
.tdTwo{
border:1px solid #6B6B6B;
border:1;
cellpadding:0;
cellspacing:0;
}
.tdThree{
border:1px solid #6B6B6B;
border:1;
cellpadding:0;
cellspacing:0;
}

</style>
<body>

	<form method="post" id="serchform"
		action='<%=request.getContextPath()%>/shop/admin/ordAuto!getBroadbandMonitorReport.do'>
		<div class="searchformDiv">
			<div class="searchBx">
				<input type="hidden" name="params.query_btn_flag" value="yes" />
				<table id="params_tb" width="100%" border="0" cellspacing="0"
					cellpadding="0" class="tab_form">
					<tbody>
						<tr>
							<th>查询时间：</th>
							<td><input type="text" name="params.create_start"
								value="${params.create_start }" readonly="readonly"
								class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"> 
								<input class="comBtn" type="button"
								name="searchBtn" id="searchBtn" value="查询"
								style="margin-right: 10px;" /> 
								<input class="comBtn" type="button"
								name="excelOrd" id="excelOrd" value="导出报表"
								style="margin-right: 10px;" /> </td>

					</tbody>
				</table>
			</div>
		</div>
	</form>
	

	 <div class="grid" id="goodslist">
		<form method="POST" id="OrderBusinessReport">

			<table class="table table-bordered" id="kuandai"  style="border:1px solid #fff"  border="1" cellpadding="0" cellspacing="0" >
				<tbody id="table_B" >
					<tr  style="border:1px solid #6B6B6B"  border="1" cellpadding="0" cellspacing="0">
						<td class="tdOne" rowspan="3" style="width:4%" > 地市</td>
						<td class="tdOne" colspan="2"  > 预约单</td>
						<td class="tdOne" colspan="5"  >中台领单环节</td>
						<td class="tdOne" colspan="7"  >中台派单环节</td>
						<td class="tdOne" colspan="5"  >外线装机</td>
					</tr>
					
					<tr  style="border:1px solid #6B6B6B"  border="1" cellpadding="0" cellspacing="0">
						<td class="tdTwo" rowspan="2" style="width:4%">当日</td>
						<td class="tdTwo" rowspan="2" style="width:4%">月累计</td>
						<td class="tdTwo" colspan="3">已领取</td>
						<td class="tdTwo" colspan="2">未领取</td>
						<td class="tdTwo" colspan="3">已派单</td>
						<td class="tdTwo" colspan="2">已退单</td>
						<td class="tdTwo" colspan="2">订单挂起</td>
						<td class="tdTwo" colspan="2">已竣工</td>
						<td class="tdTwo" colspan="2">已退单</td>
					</tr>
					
					<tr  style="border:1px solid #6B6B6B"  border="1" cellpadding="0" cellspacing="0">
						<td class="tdThree" style="width:4%">当日</td>
						<td class="tdThree" style="width:4%">月累计</td>
						<td class="tdThree" style="width:12%">平均领取时间(Min)</td>
						<td class="tdThree" style="width:4%">当日</td>
						<td class="tdThree" style="width:4%">月累计</td>
						<!-- 中台派单环节 	-->
						<td class="tdThree" style="width:4%">当日</td>
						<td class="tdThree" style="width:4%">月累计</td>
						<td class="tdThree" style="width:12%">平均派单时间(Min)</td>
						<td class="tdThree" style="width:4%">当日</td>
						<td class="tdThree" style="width:4%">月累计</td>
						<td class="tdThree" style="width:4%">当日</td>
						<td class="tdThree" style="width:4%">月累计</td>
						<!-- 外线装机 		 -->
						<td class="tdThree" style="width:4%">当日</td>
						<td class="tdThree" style="width:4%">月累计</td>
					<!--<td class="tdThree" style="width:12%">平均竣工时间</td> -->
						<td class="tdThree" style="width:4%">当日</td>
						<td class="tdThree" style="width:4%">月累计</td>
					</tr>
			<grid:grid from="webpage" formId="serchform">
				<%-- <grid:header > </grid:header> --%>
					<grid:body item="order">
						<grid:cell  style="width:4%">${order.order_city}</grid:cell>
						<grid:cell  style="width:4%">${order.sum00}</grid:cell>
						<grid:cell  style="width:4%">${order.sum0}</grid:cell>
						<grid:cell  style="width:4%">${order.sum01}</grid:cell>
						<grid:cell  style="width:4%">${order.sum1}</grid:cell>	
						<grid:cell  style="width:12%">${order.rate1}</grid:cell>
						<grid:cell  style="width:4%">${order.sum02}</grid:cell>
						<grid:cell  style="width:4%">${order.sum2}</grid:cell>
						<grid:cell  style="width:4%">${order.sum03}</grid:cell>
						<grid:cell  style="width:4%">${order.sum3}</grid:cell>
						<grid:cell  style="width:12%">${order.rate2}</grid:cell>
						<grid:cell  style="width:4%">${order.sum04}</grid:cell>
						<grid:cell  style="width:4%">${order.sum4}</grid:cell>
						<grid:cell  style="width:4%">${order.sum05}</grid:cell>
						<grid:cell  style="width:4%">${order.sum5}</grid:cell>
						<grid:cell  style="width:4%">${order.sum06}</grid:cell>
						<grid:cell  style="width:4%">${order.sum6}</grid:cell>
						<grid:cell  style="width:4%">${order.sum07}</grid:cell>
						<grid:cell  style="width:4%">${order.sum7}</grid:cell>							
				</grid:body> 
			</grid:grid>		
					
				</tbody>
			</table>
		</form>
		<c:if test="${empty webpage }">
			<!-- 当页面还没读取数据的时候用来填充，以防查询条件下拉显示不全 -->
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
function showMask(){     
	
	var mask_bg = document.createElement("div");
    mask_bg.id = "mask_bg";
    mask_bg.style.position = "absolute";
    mask_bg.style.top = "0px";
    mask_bg.style.left = "0px";
    mask_bg.style.width = "100%";
    mask_bg.style.height = "100%";
    mask_bg.style.backgroundColor = "#777";
    mask_bg.style.opacity = 0.6;
    mask_bg.style.zIndex = 10001;
    document.body.appendChild(mask_bg);
    var mask_msg = document.createElement("div");
    mask_msg.style.position = "absolute";
    mask_msg.style.top = "35%";
    mask_msg.style.left = "42%";
    mask_msg.style.backgroundColor = "white";
    mask_msg.style.border = "#336699 1px solid";
    mask_msg.style.textAlign = "center";
    mask_msg.style.fontSize = "1.1em";
    mask_msg.style.fontWeight = "bold";
    mask_msg.style.padding = "0.5em 3em 0.5em 3em";
    mask_msg.style.zIndex = 10002;
    mask_msg.innerText = "正在查询,请稍后...";
    mask_bg.appendChild(mask_msg);

}  
//隐藏遮罩层  
function hideMask(){     
      
    $("#mask").hide();     
} 
$(function(){
	$("#searchBtn").click(function(){
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getBroadbandMonitorReport.do");
			$("#serchform").submit();
			showMask(); 	
	});
	
	$("#excelOrd").click(function(){
			var url = ctx+"/shop/admin/ordAuto!getBroadbandMonitorReport.do?ajax=yes&excel=check&type=report";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getBroadbandMonitorReport.do?ajax=yes&excel=yes&type=report");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!getBroadbandMonitorReport.do");
				}
			},'json');
		
	});
});
</script>
