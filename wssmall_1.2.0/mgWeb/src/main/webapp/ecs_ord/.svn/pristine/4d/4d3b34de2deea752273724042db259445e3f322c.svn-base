<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>意向单统计报表</title>


<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>

<style>

.searchformDiv table th {
	width: 30px;
}
.grid {
	height: 300px;
}

</style>

<body>

<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/ordAuto!commerceDevelopChannel.do'>
<div class="searchformDiv">
	<div class="searchBx">
		<input type="hidden" name="params.query_btn_flag" value="yes" />
	   	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
			<tbody id="tbody_A">
		         <tr>
		        <td style="width:100px;">
		        		订单来源：
		        		<script type="text/javascript">
		                		$(function(){ ////XMJ修改开始
		                    		$("#order_from_vp,#order_from_a,#order_from_dv").bind("click",function(e2){    //给按钮注册单击事件，点击显示DIV
		                    			$("#order_from_dv").show();    //显示DIV
		                    	        e2.stopPropagation();//阻止事件冒泡
		                    		})
		                	    		
		    						$(document).bind("click",function(e2){    
		                    	        $("#order_from_dv").hide();    //隐藏DIV
		                    	  	}) 
		
		    					   $("#order_from_cancel1,#order_from_cancel2").bind("click",function(e2){
		                    		   $("#order_from_dv").hide();
		                               e2.stopPropagation();//阻止事件冒泡
		                    		}); 
		                    	   ////XMJ修改结束                			
		                			$("#order_from_chack_all").bind("click",function(){
		                    			if(this.checked){
		                    				$("input[name=params.order_from_d]").attr("checked","checked");
		                    				$("#order_from_dv li").addClass("curr");
		                    			}else{
		                    				$("input[name=params.order_from_d]").removeAttr("checked");
		                    				$("#order_from_dv li").removeClass("curr");
		                    			}
		                    			selectOrderFromsList();
		                    		});
		                    		
		                    		$("input[name=params.order_from_d]").bind("click",function(){
		                    			if(this.checked){
		                    				$(this).parents("li").addClass("curr");
		                    			}else{
		                    				$(this).parents("li").removeClass("curr");
		                    			}
		                    			selectOrderFromsList();
		                    		});
		                    		
		                    		initCheckBoxs("order_from","params.order_from_d");
		                    	});
		                    	function selectOrderFromsList(){
		                			var regions = $("input[name=params.order_from_d]:checked");
		                			var regionNames = "";
		                			var regionIds = "";
		                			regions.each(function(idx,item){
		                				var name = $(item).attr("c_name");
		                				var rid = $(item).attr("value");
		                				if(name!=null&&name!=""){
			                				regionNames += name+",";
			                				regionIds += rid+",";
		                				}
		                			});
		                			if(regionIds.length>1){
		                				regionIds = regionIds.substr(0,regionIds.length-1);
		                				regionNames = regionNames.substr(0,regionNames.length-1);
		                			}
		                			$("#order_from_vp").val(regionNames);
		                			$("#order_from").val(regionIds);
		                		}
		                    	
		                    	//初始化多选框
		                    	function initCheckBoxs(value_id,check_box_name){
		                    		var cv = $("#"+value_id).val();
		                    		if(cv){
		                    			var arr = cv.split(",");
		                    			for(i=0;i<arr.length;i++){
		                    				var item = arr[i];
		                        			var obj = $("input[type=checkbox][name="+check_box_name+"][value="+item+"]");
		                        			obj.attr("checked","checked");
		                        			obj.parents("li").addClass("curr");
		                    			}
		                    		}
		                    	}
		                	</script>
		                	<span class="selBox" style="width: 120px;"> 
			                	<input type="text" name="params.order_from_c" id="order_from_vp" value="${params.order_from_c}" class="ipt" readonly="readonly">
								<input type="hidden" name="params.order_from" id="order_from" value="${params.order_from}"  />  
								<a href="javascript:void(0);" id="order_from_a" class="selArr"></a>
								<div class="selOp" style="display: none;" id="order_from_dv">
									<div class="allSel">
										<label><input type="checkbox" id="order_from_chack_all">全选</label> 
										<label><a href="javascript:void(0);" class="aCancel" id="order_from_cancel1">关闭</a></label> 
										<label><a href="javascript:void(0);" class="aClear" id="order_from_cancel2"></a></label>
									</div>
									<div class="listItem">
										<ul>
											<c:forEach items="${order_from_list}" var="of">
												<li name="order_from_li"><input type="checkbox" name="params.order_from_d" value="${of.value}" c_name="${of.value_desc}">
													<span name="order_from_span">${of.value_desc}</span>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</span>
		            </td>
					<td style="width:150px">
					        归属地市：
							<select name="params.order_city_code" class="ipt_new" style="width: 100px;" onchange="queryCountyByCity(this)">
							<option value="">--请选择--</option>
								<c:forEach items="${dc_MODE_REGIONList}" var="ds">
			               			<option value="${ds.value}" ${ds.value==params.order_city_code?'selected':'' }>${ds.value_desc }</option>
			               		</c:forEach>
							</select>
				    </td>
					<td style="width:150px">
						县分：
							<select id="order_county_code" name="params.order_county_code" class="ipt_new" style="width: 100px;">
								<option value="">--请选择--</option>
								<c:forEach items="${countyList}" var="ds">
			               			<option value="${ds.field_value}" ${ds.field_value==params.order_county_code?'selected':''}>${ds.field_value_desc}</option>
			               		</c:forEach>
							</select>	
					</td>
		            <td style="width: 100px;">
		            	筛选年/月：
		                <input style="width: 150px;" type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM'})">
					</td>
		        </tr>
		        
		        <tr>
		        	<td colspan="4">
		              	<input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="查询" style="margin-right:10px;"/>
		              	<input class="comBtn" type="button" name="excelReportCommer" id="excelReportCommer" value="导出统计信息" style="margin-right:10px;"/>
					</td>
		        </tr>
			</tbody>  
		</table>
	</div>
</div>
</form>

<div class="grid" id="goodslist">
     <form method="POST"  id="intent_order_report"  >
      		
      			<grid:grid from="webpage" formId="serchform" >
					<grid:header>
						<grid:cell width="50px">地市名称</grid:cell><%-- city_name--%>
						<grid:cell width="50px">县分名称</grid:cell><%-- county_name--%>
						<grid:cell width="50px">订单来源</grid:cell><%-- pname--%>
						<grid:cell width="50px">预约/竣工率</grid:cell><%-- p1--%>
						<grid:cell width="50px">派单/竣工率</grid:cell><%-- p2--%>
						<grid:cell width="50px">当日预约量</grid:cell><%-- p3--%>
						<grid:cell width="50px">当月预约量</grid:cell><%-- p4--%>
						<grid:cell width="50px">当日派单量</grid:cell><%-- p5--%>
						<grid:cell width="50px">当月派单量</grid:cell><%-- p6--%>
						<grid:cell width="50px">当日竣工量</grid:cell><%-- p7--%>
						<grid:cell width="50px">当月竣工量</grid:cell><%-- p8--%>
						<grid:cell width="50px">当日未处理订单</grid:cell><%-- p9--%>
						<grid:cell width="50px">当月未处理订单</grid:cell><%-- p10--%>
					</grid:header>
				    <grid:body item="order">
						<grid:cell>${order.city_name}</grid:cell>  
						<grid:cell>${order.county_name}</grid:cell>  
						<grid:cell>${order.pname}</grid:cell>
						<grid:cell>${order.p1}</grid:cell>  
						<grid:cell>${order.p2}</grid:cell>  
						<grid:cell>${order.p3}</grid:cell> 
						<grid:cell>${order.p4}</grid:cell> 
						<grid:cell>${order.p5}</grid:cell>  
						<grid:cell>${order.p6}</grid:cell>  
						<grid:cell>${order.p7}</grid:cell>  
						<grid:cell>${order.p8}</grid:cell>  
						<grid:cell>${order.p9}</grid:cell>  
						<grid:cell>${order.p10}</grid:cell>  
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
	return true;
}

function queryCountyByCity(e){
	var url = ctx + "/shop/admin/ordAuto!getCountyListByCity.do?ajax=yes";
	Cmp.ajaxSubmit('serchform', '', url, {}, function(responseText) {
		if (responseText.result == 0) {
			//alert(responseText.message);
			var str = responseText.list;
			var list=eval("("+str+")"); 
			$("#order_county_code").empty(); //清空
			$("#order_county_code").append("<option value=''>--请选择--</option>");
			$.each(list, function (index, obj) {
				$("#order_county_code").append("<option value='"+obj.field_value+"'>"+obj.field_value_desc+"</option>");
			});
		} else {
			alert(responseText.message);
		}
	}, 'json');
};

$(function(){
	$("#searchBtn").click(function(){
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!commerceDevelopChannel.do");
			$("#serchform").submit();
	}); 
	
/* 	$("#excelOrd").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!intentOrderReportQuery.do?ajax=yes&excel=check&type=dtl";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!intentOrderReportQuery.do?ajax=yes&excel=yes&type=dtl");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!intentOrderReportQuery.do");
				}
			},'json');
		}
	}); */
 	$("#excelReportCommer").click(function(){
			var url = ctx+"/shop/admin/ordAuto!commerceDevelopChannel.do?ajax=yes&excel=check&type=report";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!commerceDevelopChannel.do?ajax=yes&excel=yes&type=report");
					$("#serchform").submit();
				}
				/* 	$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!commerceDevelopChannel.do"); */
			},'json');
	}); 
 }); 
</script>
 