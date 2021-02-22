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

<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/ordAuto!intentOrderReportQuery.do'>
<div class="searchformDiv">
	<div class="searchBx">
		<input type="hidden" name="params.query_btn_flag" value="yes" />
	   	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
			<tbody id="tbody_A">
		        <tr>
		        <td style="width:50px;">
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
		            
		            <td style="width: 100px;">
		            	查询范围：
		            	<select id="select_order_city_code" name="params.order_city_code" >
		            	</select>
		            	<script type="text/javascript">
			            	$.ajax({
			                 	url:ctx+"/core/admin/org/orgAdmin!getPermissionRegionCounty.do?ajax=yes",
			                 	dataType:"json",
			                 	async:false,
			                 	data:{},
			                 	success:function(reply){
			                 		if(typeof(reply) != "undefined" && typeof(reply["lan"]) != "undefined" &&
			                 				typeof(reply["region"]) != "undefined"){
			                 			regions = reply["lan"];
			                 			countys = reply["region"];
			                 			
			                 			$("#select_order_city_code").empty();
			                 			
			                 			$("#select_order_city_code").append("<option value=''></option>");
			                 			
			                 			for(var i=0;i<regions.length;i++){
			                 				var option = "<option value="+regions[i].lan_id+">"+regions[i].lan_name+"</option>";
			                 				
			                 				$("#select_order_city_code").append(option);
			                 			}
			                 		}
			                 	},
			                 	error:function(msg){
			                 		alert(msg);
			                 	}
			            	});
		            	
		            		$("select[name=params.order_city_code]").val(${params.order_city_code });
						</script>
		            </td>
		            
		        	<td style="width: 100px;">
		        		统计方式：
		        		<select name="params.report_search_type" >
		            		<option value="1">按地市统计</option>
		            		<option value="2">按县分统计</option>
		            	</select>
		            	<script type="text/javascript">
		            		$("select[name=params.report_search_type]").val(${params.report_search_type });
						</script>
		            </td>
		            <td style="width: 300px;">
		            	创建时间：
		                <input style="width: 150px;" type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
		                <input style="width: 150px;" type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
		        </tr>
		        
		        <tr>
		        	<td colspan="4">
		              	<input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="查询" style="margin-right:10px;"/>
		              	<input class="comBtn" type="button" name="excelReport" id="excelReport" value="导出意向单统计信息" style="margin-right:10px;"/>
		              	<input class="comBtn" type="button" name="excel" id="excelOrd" value="导出意向单订单信息" style="margin-right:10px;"/>
					</td>
		        </tr>
			</tbody>  
		</table>
	</div>
</div>
</form>

<div class="grid" id="goodslist">
     <form method="POST"  id="intent_order_report"  >
      	<c:choose>
      		<c:when test="${params.report_search_type == '1'}">
      			<grid:grid from="webpage" formId="serchform" >
					<grid:header>
						<grid:cell width="50px">地市名称</grid:cell><%-- city_name--%>
						<grid:cell width="50px">总量</grid:cell><%-- total--%>
						<grid:cell width="50px">未处理</grid:cell><%-- undeal--%>
						<grid:cell width="50px">处理中</grid:cell><%-- dealing--%>
						<grid:cell width="50px">已完成</grid:cell><%-- done--%>
						<grid:cell width="50px">结单</grid:cell><%-- can--%>
					</grid:header>
	
				    <grid:body item="order">
						<grid:cell>${order.city_name}</grid:cell>  <%--地市名称--%>
						<grid:cell>${order.total}</grid:cell>  <%--总量--%>
						<grid:cell>${order.undeal}</grid:cell>  <%--未处理--%>
						<grid:cell>${order.dealing}</grid:cell>  <%--处理中--%>
						<grid:cell>${order.done}</grid:cell>  <%--已完成--%>
						<grid:cell>${order.can}</grid:cell>  <%--退单--%>
				  	</grid:body>
				</grid:grid>
      		</c:when>
      		<c:otherwise>
      			<grid:grid from="webpage" formId="serchform" >
					<grid:header>
						<grid:cell width="50px">地市名称</grid:cell><%-- city_name--%>
						<grid:cell width="50px">县分名称</grid:cell><%-- county_name--%>
						<grid:cell width="50px">总量</grid:cell><%-- total--%>
						<grid:cell width="50px">未处理</grid:cell><%-- undeal--%>
						<grid:cell width="50px">处理中</grid:cell><%-- dealing--%>
						<grid:cell width="50px">已完成</grid:cell><%-- done--%>
						<grid:cell width="50px">结单</grid:cell><%-- can--%>
					</grid:header>
					
				    <grid:body item="order">
						<grid:cell>${order.city_name}</grid:cell>  <%--地市名称--%>
						<grid:cell>${order.county_name}</grid:cell>  <%--县分名称--%>
						<grid:cell>${order.total}</grid:cell>  <%--总量--%>
						<grid:cell>${order.undeal}</grid:cell>  <%--未处理--%>
						<grid:cell>${order.dealing}</grid:cell>  <%--处理中--%>
						<grid:cell>${order.done}</grid:cell>  <%--已完成--%>
						<grid:cell>${order.can}</grid:cell>  <%--退单--%>
				  	</grid:body>
				</grid:grid>
      		</c:otherwise>
      	</c:choose>
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

$(function(){
	$("#searchBtn").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!intentOrderReportQuery.do");
			$("#serchform").submit();
		}
	});
	
	$("#excelOrd").click(function(){
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
	});
	
	$("#excelReport").click(function(){
		if (check()) {
			var url = ctx+"/shop/admin/ordAuto!intentOrderReportQuery.do?ajax=yes&excel=check&type=report";
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				if(data.result=='0'){
					alert("数据量超过"+ data.count +"，请按时间分次导出。");
				}else{
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!intentOrderReportQuery.do?ajax=yes&excel=yes&type=report");
					$("#serchform").submit();
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!intentOrderReportQuery.do");
				}
			},'json');
		}
	});
});
</script>
 