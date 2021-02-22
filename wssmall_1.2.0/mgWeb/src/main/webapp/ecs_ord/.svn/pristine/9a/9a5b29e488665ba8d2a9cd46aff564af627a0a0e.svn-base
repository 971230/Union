<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String order_role = request.getParameter("order_role");

String permission_level = request.getParameter("permission_level");

%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workFlow.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/publics/js/admin/ztp-min.js"></script>



<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet"
	type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.red {
	color: red;
}

.icoFontlist {
	width: 100px;
	font-size: 12px;
	border: 0px solid #ddd;
	color: #5f5f5f;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.icoFontlist:hover {
	width: 100px;
	font-size: 12px;
	border: 0px solid #ddd;
	overflow: scroll;
	text-overflow: ellipsis;
	white-space: nowrap;
	cursor: pointer;
}

.detailDiv {
	display: none;
}

#orderQuantity {
	tr td,th{border: 1.5px solid #fff;
}
border-collapse
:
 
collapse
;

}
.tdOne {
	border: 1.5px solid #6B6B6B;
	border: 1;
	cellpadding: 0;
	cellspacing: 0;
}

.tdTwo {
	border: 1px solid #6B6B6B;
	border: 1;
	cellpadding: 0;
	cellspacing: 0;
}
</style>
<body style="background: white;">
	<div class="searchBx">
		<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/ordAuto!jointCardDevelopmentDaily.do'>
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<input type="hidden" name="params.query_btn_flag" value="yes" />
					<tr>
						<th>日报查询时间：</th>
						<td>
							<input style="width: 150px;" type="text" id="create_start" name="params.create_date" value="${params.create_date }"
							readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<th>详单查询时间：</th>
		        		<td>
		        			<input style="width: 150px;" type="text" id="create_start" name="params.create_start" value="${params.create_start }"
							readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> -
							<input style="width: 150px;" type="text" id="create_end" name="params.create_end"
							value="${params.create_end }" readonly="readonly" class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
						</td>
		        		<td>
		        			<input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="查询" style="margin-right: 10px;" />
			            	<input class="comBtn" type="button" name="excelReport" id="excelReport" value="导出发展日报" style="margin-right:10px;"/>
		        			<input class="comBtn" type="button" name="excel" id="excelOrd" value="导出发展详单" style="margin-right:10px;"/>
		        		</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<div class="grid" id="orderslist">
		<form method="POST" id="OrderQuantityReport">
			<table class="table table-bordered" id="orderQuantity"
				style="border: 1px solid #fff" border="1" cellpadding="0"
				cellspacing="0">
				<tbody id="table_B">
					<tr style="border: 1.5px solid #6B6B6B" border="1" cellpadding="0" cellspacing="0">
						<td class="tdOne" rowspan="2" style="width: 6%">地市</td>
						<td class="tdOne" colspan="2" style="width: 14%">冰激凌</td>
						<td class="tdOne" colspan="3" style="width: 21%">腾迅王卡</td>
						<td class="tdOne" colspan="2" style="width: 14%">当日</td>
						<td class="tdOne" colspan="3" style="width: 20%">当月</td>
						<td class="tdOne" colspan="2" style="width: 17%">累计</td>
						<td class="tdOne" rowspan="2" style="width: 10%">转化率</td>
					</tr>

					<tr style="border: 1.5px solid #6B6B6B" border="1" cellpadding="0" cellspacing="0">
						<!-- 冰激凌 -->
						<td class="tdTwo" style="width: 6%">129</td>
						<td class="tdTwo" style="width: 6%">199</td>
						<!--腾迅王卡  -->		
						<td class="tdTwo" style="width: 7%">天王卡</td>
						<td class="tdTwo" style="width: 7%">地王卡</td>
						<td class="tdTwo" style="width: 7%">大王卡</td>
						<!-- 当日 -->
						<td class="tdTwo" style="width: 7%">发展量</td>
						<td class="tdTwo" style="width: 7%">激活量</td>
						<!-- 当月 -->
						<td class="tdTwo" style="width: 6%">发展量</td>
						<td class="tdTwo" style="width: 6%">发展激活量</td>
						<td class="tdTwo" style="width: 8%">累计激活量</td>
						
						<!-- 累计 -->
						<td class="tdTwo" style="width: 8%">发展量</td>
						<td class="tdTwo" style="width: 9%">激活量</td>
						<!-- <td class="tdTwo" style="width: 10%">累计激活量</td> -->
					</tr>
					
			</table>
			<grid:grid from="webpage" formId="serchform">
				<grid:body item="report">
					<grid:cell style="width:7%">${report.cityName}</grid:cell>
					
					<grid:cell style="width:6%">${report.bjl129}</grid:cell>
					<grid:cell style="width:6%">${report.bjl199}</grid:cell>
				
					<grid:cell style="width:7%">${report.skyCard}</grid:cell>
					<grid:cell style="width:7%">${report.groundCard}</grid:cell>
					<grid:cell style="width:7%">${report.bigCard}</grid:cell>
						
					<grid:cell style="width:7%">${report.dayDev}</grid:cell>
					<grid:cell style="width:7%">${report.dayact}</grid:cell>
					
					<grid:cell style="width:6%">${report.monthDev}</grid:cell>
					<grid:cell style="width:6%">${report.monthAct}</grid:cell>
					<grid:cell style="width:8%">${report.totalAct}</grid:cell>
					
					<grid:cell style="width:8%">${report.leiJiDev}</grid:cell>
					<grid:cell style="width:9%">${report.leiJiDevAct}</grid:cell>
					<!--<grid:cell style="width:8%">${report.leiJiAct}</grid:cell>-->
					
					<grid:cell style="width:10%">${report.zhuanhualv}</grid:cell>
				</grid:body>
			</grid:grid>
			</tbody>

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
		</c:if>
	</div>

	<div id="showDataByName" style='height: 500px'></div>
</body>

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

function hideMask(){     
    
    $("#mask").hide();     
} 
function check() {
	return true;
}

$(function(){
	$("#searchBtn").click(function(){
		$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!jointCardDevelopmentDaily.do");
		$("#serchform").submit();
		showMask(); 	
	});

	
	//发展详单
	$("#excelOrd").click(function(){
		var url = ctx+"/shop/admin/ordAuto!jointCardDevelopmentDaily.do?ajax=yes&excel=check&type=dtl";
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			if(data.result=='0'){
				alert("数据量超过"+ data.count +"，请按时间分次导出。");
			}else{
				$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!jointCardDevelopmentDaily.do?ajax=yes&excel=yes&type=dtl");
				$("#serchform").submit();
				$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!jointCardDevelopmentDaily.do");
			}
		},'json');
		
	});
	
	
	//日报
	$("#excelReport").click(function(){
		var url = ctx+"/shop/admin/ordAuto!jointCardDevelopmentDaily.do?ajax=yes&excel=check&type=report";
		Cmp.ajaxSubmit('serchform','',url,{},function(data){
			if(data.result=='0'){
				alert("数据量超过"+ data.count +"，请按时间分次导出。");
			}else{
				$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!jointCardDevelopmentDaily.do?ajax=yes&excel=yes&type=report");
				$("#serchform").submit();
				$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!jointCardDevelopmentDaily.do");
			}
		},'json');
	});
});
</script>
