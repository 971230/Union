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

<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/SelectTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script> --%>


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
	<input type="hidden" name="permission_level" id="permission_level" value="${permission_level }" />
		<form method="post" id="serchform"
			action='<%=request.getContextPath()%>/shop/admin/ordAuto!personnelOrderQuantityQuery.do'>
			<input type="hidden" name="params.query_btn_flag" value="yes" />
			<input type="hidden" name="permission_level" id="permission_level" value="${permission_level }" />
			<table id="params_tb" width="100%" border="0" cellspacing="0"
				cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>归属地市：</th>
						<td>
							<select name="params.order_city_code" id="order_city_code" class="ipt_new" style="width: 145px;" onchange="queryCountyByCity(this)">
								<c:forEach items="${dc_MODE_REGIONList}" var="ds">
			               			<option value="${ds.value }" ${ds.value==params.order_city_code?'selected':'' }>${ds.value_desc }</option>
			               		</c:forEach>
							</select>
						</td>
						<th>营业县分：</th>
							<td>
							<select id="order_county_code" name="params.order_county_code" class="ipt_new" style="width: 145px;">
								<c:forEach items="${countyList}" var="ds">
			               			<option value="${ds.field_value}" ${ds.field_value==params.order_county_code?'selected':''}>${ds.field_value_desc}</option>
			               		</c:forEach>
							</select>	
							</td>
						<th>时间：</th>
						<td><input style="width: 150px;" type="text" id="create_start"
							name="params.create_start" value="${params.create_start }"
							readonly="readonly" class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
							<input style="width: 150px;" type="text" id="create_end" name="params.create_end"
							value="${params.create_end }" readonly="readonly" class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>

					</tr>

					<tr>
						<th>人员角色：</th>
						<td style="width: 50px;"><select name="params.order_role"
							id="order_role">
								<option value="">--请选择--</option>
								<c:forEach items="${order_role_list}" var="of">
									<option value="${of.value}"
										${of.value==params.order_role?'selected':'' }>${of.value_desc }</option>
								</c:forEach>
						</select></td>

						<th>订单来源：</th>
						<td><span class="selBox" style="width: 120px;"> <input
								type="text" name="params.order_from_c" id="order_from_vp"
								class="ipt" readonly="readonly"> <input type="hidden"
								name="params.order_from" id="order_from"
								value="${params.order_from }" /> <a href="javascript:void(0);"
								id="order_from_a" class="selArr"></a>

								<div class="selOp" style="display: none;" id="order_from_dv">
									<div class="allSel">
										<label><input type="checkbox"
											id="order_from_chack_all">全选</label> <label><a
											href="javascript:void(0);" class="aCancel"
											id="order_from_cancel1">关闭</a></label> <label><a
											href="javascript:void(0);" class="aClear"
											id="order_from_cancel2"></a></label>
									</div>

									<div class="listItem">
										<ul>
											<c:forEach items="${order_from_list}" var="of">
												<li name="order_from_li"><input type="checkbox"
													name="params.order_from_d" value="${of.value}"
													c_name="${of.value_desc}"> <span
													name="order_from_span">${of.value_desc}</span></li>
											</c:forEach>
										</ul>
									</div>
								</div>
						</span> <script type="text/javascript">
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
		                    			
		                    			selectOrderFromsList();
		                    		}
		                    	}
		                	</script> <input class="comBtn" type="button" name="searchBtn"
							id="searchBtn" value="查询" style="margin-right: 10px;" /></td>
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
					<tr style="border: 1.5px solid #6B6B6B" border="1" cellpadding="0"
						cellspacing="0">
						<td class="tdOne" rowspan="2" style="width: 4%">地市</td>
						<td class="tdOne" rowspan="2" style="width: 5%">县分</td>
						<td class="tdOne" rowspan="2" style="width: 8%">人员角色</td>
						<td class="tdOne" rowspan="2" style="width: 5%">人员姓名</td>
						<td class="tdOne" rowspan="2" style="width: 5%">工号</td>
						<td class="tdOne" rowspan="2" style="width: 5%">联系电话</td>
						<td class="tdOne" colspan="5">订单</td>
						<td class="tdOne" colspan="6">工单</td>
					</tr>

					<tr style="border: 1.5px solid #6B6B6B" border="1" cellpadding="0"
						cellspacing="0">
						<!-- 订单 -->
						<td class="tdTwo" style="width: 4%">未处理</td>
						<td class="tdTwo" style="width: 4%">处理中</td>
						<td class="tdTwo" style="width: 4%">结单</td>
						<td class="tdTwo" style="width: 4%">完成</td>
						<td class="tdTwo" style="width: 4%">订单合计</td>
						<!-- 工单 -->
						<td class="tdTwo" style="width: 4%">未处理</td>
						<td class="tdTwo" style="width: 4%">处理中</td>
						<td class="tdTwo" style="width: 4%">处理成功</td>
						<td class="tdTwo" style="width: 4%">处理失败</td>
						<td class="tdTwo" style="width: 4%">已撤单</td>
						<td class="tdTwo" style="width: 4%">工单合计</td>
					</tr>
			</table>
			<grid:grid from="webpage" formId="serchform">
				<grid:body item="order">
					<grid:cell style="width:4%">${order.city_name}</grid:cell>
					<grid:cell style="width:5%">${order.county_name}</grid:cell>
					<grid:cell style="width:8%">${order.rolename}</grid:cell>
					<grid:cell style="width:5%">
						<a href="javascript:void(0)" id="getorders"
							onclick="getOrdersByOne('${order.realname}','${order.phone_num}','${order.city_name}','${order.county_name}')">${order.realname}</a>
					</grid:cell>
					<grid:cell style="width:5%">${order.userid}</grid:cell>
					<grid:cell style="width:5%">${order.phone_num}</grid:cell>
					<!-- 订单 -->
					<grid:cell style="width:4%">${order.untreat_order_counts}</grid:cell>
					<grid:cell style="width:4%">${order.in_hand_order_counts}</grid:cell>
					<grid:cell style="width:4%">${order.finish_order_counts}</grid:cell>
					<grid:cell style="width:4%">${order.statement_order_counts}</grid:cell>
					<grid:cell style="width:4%">${order.order_total}</grid:cell>
					<!-- 工单 -->
					<grid:cell style="width:4%">${order.untreat_work_counts}</grid:cell>
					<grid:cell style="width:4%">${order.in_hand_work_counts}</grid:cell>
					<grid:cell style="width:4%">${order.success_work_counts}</grid:cell>
					<grid:cell style="width:4%">${order.fail_work_counts}</grid:cell>
					<grid:cell style="width:4%">${order.cancle_work_counts}</grid:cell>
					<grid:cell style="width:4%">${order.work_total}</grid:cell>
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

var permission_level = $("#permission_level").val();

$(function(){
	 
	 Eop.Dialog.init({
		 id: "showDataByName",
		 modal: true, 
		 title: "人员各环节订单查询", 
		 width: '1150px'
	 });
	 
	$("#searchBtn").click(function(){
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!personnelOrderQuantityQuery.do");
			$("#serchform").submit();
	});
	
});

//县分由地市联动展示
function queryCountyByCity(e){
	var city_code=$("#order_city_code option:selected").val();
	if(permission_level==3){
		var url = ctx + "/shop/admin/ordAuto!getCountyListByPower.do?ajax=yes";
		url += "&city_code="+city_code;
		Cmp.ajaxSubmit('serchform', '', url, {}, function(responseText) {
		if (responseText.result == 0) {
			var str = responseText.list;
			var list=eval("("+str+")"); 
			$("#order_county_code").empty(); //清空
			$.each(list, function (index, obj) {
				$("#order_county_code").append("<option value='"+obj.field_value+"'>"+obj.field_value_desc+"</option>");
			});
		} else {
			alert(responseText.message);
		}
	}, 'json');
		return;
	}else{
		var url = ctx + "/shop/admin/ordAuto!getCountyListByCity.do?ajax=yes";
		Cmp.ajaxSubmit('serchform', '', url, {}, function(responseText) {
			if (responseText.result == 0) {
				var str = responseText.list;
				var list=eval("("+str+")"); 
				$("#order_county_code").empty(); //清空
				/* $("#order_county_code").append("<option value=''>--请选择--</option>"); */
				$.each(list, function (index, obj) {
					$("#order_county_code").append("<option value='"+obj.field_value+"'>"+obj.field_value_desc+"</option>");
				});
			} else {
				alert(responseText.message);
			}
		}, 'json');
	}
};
	
function doRegionCountyChange(regionId,countyId){
	$("#order_region_code").val(regionId);
	
	$("#order_county_code").empty();
	$("#order_county_code").append("<option value=''></option>");
		
	for(var i=0;i<countys.length;i++){
		var region_Id = countys[i]["region_id"];
		var region_Name = countys[i]["region_name"];
		var pRegion_Id = countys[i]["parent_region_id"];
		
		if(regionId == pRegion_Id){
			var option = "<option value="+region_Id+">"+region_Name+"</option>";
		
			$("#order_county_code").append(option);
		}
	}
	
	if(countyId)
		$("#order_county_code").val(countyId);
};

function getOrdersByOne(username,phone_num,city_name,county_name){
	var order_role = WorkFlowTool.getInputValue($("#order_role").val());
	var create_start = WorkFlowTool.getInputValue($("#create_start").val());
	var create_end = WorkFlowTool.getInputValue($("#create_end").val());
	var order_from = WorkFlowTool.getInputValue($("#order_from").val());
	
	create_start = create_start.replace(" ", "");
	create_end = create_end.replace(" ", "");
	
	var url=ctx+"/shop/admin/ordAuto!personnelOrderQuantityQueryByName.do?ajax=yes";
  	
	url += "&username="+username;
	url += "&phone_num="+phone_num;
	url += "&order_city_code="+city_name;
	url += "&order_county_code="+county_name;
 	url += "&order_role="+order_role;
	url += "&create_start="+create_start;
	url += "&create_end="+create_end;
	url += "&order_from="+order_from;  
	
	$("#showDataByName").load(url); 
	Eop.Dialog.open("showDataByName"); 
}
</script>
