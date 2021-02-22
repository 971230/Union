<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workFlow.js"></script>
<link href="<%=request.getContextPath() %>/ecs_ord/workCustom/css/workCustom.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.red {
	color: red;
}
.icoFontlist{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    color:#5f5f5f;  
    overflow: hidden;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
} 

.icoFontlist:hover  
{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    overflow: scroll;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
    cursor:pointer;   
} 
.detailDiv 
{  
    display: none;   
} 
</style>
<script src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
<script type="text/javascript">
	$(function() {
		 $("#hide_params_tb").bind("click", function() {
			$("#params_tb").hide();
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
		});
		$("#show_params_tb").bind("click", function() {
			$("#params_tb").show();
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
		}); 
		$("#queryIntent").bind("click",
				function() {
					var order_id = $("#order_id_work").val();
					var create_start = $("input[name='params.create_start']").attr("value");
					var create_end = $("input[name='params.create_end']").attr("value");
					/* if(order_id == "" && create_start == "" && create_end == ""){
						alert("查询参数不能全为空");
						return;
					} */
					$("#intentHandleForm").attr("action", ctx + "/shop/admin/orderIntentAction!intentHandleForm.do").submit();
				});

		//处理
		$("a[name='handleIntent']").bind("click",function(){
			var order_id = $(this).attr("value");
			if(order_id == null || order_id==""){
				alert("异常：order_id为空");
				return;
			}
			
			var url= ctx+"/shop/admin/orderIntentAction!handleIntent.do?ajax=yes&order_id="+order_id;
			Eop.Dialog.init({id:"handleIntent",modal:false,title:"意向单处理",width:'1100px'});
			
			Eop.Dialog.open("handleIntent");
			
			$("#handleIntent").load(url);
			
			moveDlg2Mid("dlg_handleIntent");
		});
		
		Eop.Dialog.init({id:"intentDetails",modal:true,title:"意向单详情",width:'1100px'});
		//详情
		$("a[name='intentDetails']").bind("click",
				function() {
					$("#intentDetails").empty();
					var order_id = $(this).attr("value");
					var is_work_custom = $(this).attr("id");
					if(order_id == null || order_id==""){
						alert("异常：order_id为空");
						return;
					}
					Eop.Dialog.open("intentDetails");
					var url= ctx+"/shop/admin/orderIntentAction!intentDetails.do?ajax=yes&order_id="+order_id+"&is_work_custom="+is_work_custom;
					$("#intentDetails").load(url,{},function(){});
				});
		Eop.Dialog.init({id:"closeOrdWorkbtn",modal:true,title:"回收工单",width:'600px'});
		//回收工单
		$("a[name='closeOrdWorkbtn']").bind("click",
				function() {
					var order_id = $(this).attr("value");
					var status = $(this).attr("status");
					if(order_id == null || order_id==""){
						alert("异常：order_id为空");
						return;
					}
					Eop.Dialog.open("closeOrdWorkbtn");
					var url = ctx + "/shop/admin/ordAuto!closeWork.do?ajax=yes&order_id=" + order_id;
					$("#closeOrdWorkbtn").load(url,{},function(){});
					/* var url = ctx + "/shop/admin/ordAuto!closeOrdWork.do?ajax=yes&order_id=" + order_id;
					Cmp.ajaxSubmit('intentHandleForm', '', url, {}, function(responseText) {
						if (responseText.result == 0) {
							alert(responseText.message);
						} else {
							alert(responseText.message);
						}
					}, 'json'); */
				});
		
	});
	 (function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {});
			$(this).bind("mouseover", function() {});
		};
	})(jQuery); 
	 
	//县分由地市联动展示
	function queryCountyByCity(e){
		//var city = e.value;//表单intentHandleForm会把值带过去
		var url = ctx + "/shop/admin/ordAuto!getCountyListByCity.do?ajax=yes";
		Cmp.ajaxSubmit('intentHandleForm', '', url, {}, function(responseText) {
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
	//关闭挥手工单页面
	function closeWorkForm(){
		Eop.Dialog.close("closeOrdWorkbtn");
	};

function closeCustomWork(){
	Eop.Dialog.close("handleIntent");
	window.location.href=ctx+"/shop/admin/orderIntentAction!intentHandleForm.do";
};

function moveDlg2Mid(id){
	var w = $("#"+id).width();
	var c_width = $($(".jqmOverlay")[0]).width();
	var left = (c_width - w)/2;
	
	if(left > 0){
		$("#"+id).css("left",left+"px");
	}
	
	$("#"+id).css("opacity","1");
};
	
</script>
	<div class="searchBx">
		<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
		<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display: none;">展开</a>
		<form action="/shop/admin/orderIntentAction!intentHandleForm.do" method="post" id="intentHandleForm" >
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>订单号：</th>
						<td>
							<input type="text" name="params.order_id" value="${params.order_id}" style="width: 145px;" class="ipt_new">
						</td>
						<th>意向单号：</th>
						<td>
							<input type="text" name="params.intent_order_id" value="${params.intent_order_id}" style="width: 145px;" class="ipt_new">
						</td>
						<th>时间：</th>
						<td>
							<input type="text" name="params.create_start" value="${params.create_start}" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							-
	                    	<input type="text" name="params.create_end" value="${params.create_end}" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</td>
					</tr>
					<tr>
						<th>订单状态：</th>
						<td>
							<select name="params.status" class="ipt_new" style="width: 145px;">
							<option value="">--请选择--</option>
								<c:forEach items="${intentStatusList}" var="ds">
			               			<option value="${ds.value}" ${ds.value==params.status?'selected':''}>${ds.value_desc}</option>
			               		</c:forEach>
							</select>	
						</td>
						<th>归属地市：</th>
						<td>
							<select name="params.order_city_code" class="ipt_new" style="width: 145px;" onchange="queryCountyByCity(this)">
							<option value="">--请选择--</option>
								<c:forEach items="${dc_MODE_REGIONList}" var="ds">
			               			<option value="${ds.value }" ${ds.value==params.order_city_code?'selected':'' }>${ds.value_desc }</option>
			               		</c:forEach>
							</select>
						</td>
						<th>营业县分：</th>
						<td>
							<select id="order_county_code" name="params.order_county_code" class="ipt_new" style="width: 145px;">
								<option value="">--请选择--</option>
								<c:forEach items="${countyList}" var="ds">
			               			<option value="${ds.field_value}" ${ds.field_value==params.order_county_code?'selected':''}>${ds.field_value_desc}</option>
			               		</c:forEach>
							</select>	
						</td>
						<%--
						 <th>订单来源：</th>
						<td>
							<select name="params.order_from" class="ipt_new">
							<option value="">--请选择--</option>
								<c:forEach var="of" items="${order_from_list}">
									<option value="${of.value}" ${of.value==params.order_from?'selected':'' }>&nbsp;${of.value_desc}</option>
								</c:forEach>
							</select>
						</td> 
						<th>行政县分：</th>
						<td>
							<input type="text" class="ipt_new" name="district_code" id="district_code" value="${district_code}" onfocus="querycountyId()" /> 
							<input type="hidden" class="ipt_new" name="params.order_county_code" id="order_county_name" value="${params.order_county_code}">
							<a href="javascript:void(0);" id="resetBtn" class="dobtn" style="margin-right: 10px;">清除</a> 
							<!-- <input class="comBtn" type="button"  value="清除" style="margin-right:10px;" /> -->
						</td>
						--%>
					</tr>
					<tr>
						<th>业务号码：</th>
						<td>
							<input type="text"  name="params.phone_num" value="${params.phone_num}" style="width: 145px;" class="ipt_new">
							<span style="width: 20px;"></span>
						</td>
						<th>客户号码：</th>
						<td>
							<input type="text"  name="params.ship_tel" value="${params.ship_tel}" style="width: 145px;" class="ipt_new">
							<span style="width: 20px;"></span>
						<!-- 	<a href="javascript:void(0);" id="queryIntent" class="dobtn" style="margin-left: 20px;">查询</a> -->
						</td>
							<th>订单来源：</th>
						<td>
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
							<!-- <a href="javascript:void(0);" id="queryIntent" class="dobtn" style="margin-left: 20px;">查询</a> -->
						</td>
					</tr>
					
					<tr>
						<th>顶级种子专业线：</th>
						<td>
							<script type="text/javascript">
		                		$(function(){ ////XMJ修改开始
		                			$("#top_seed_professional_line_vp,#top_seed_professional_line_a,#top_seed_professional_line_dv").bind("click",function(e2){    //给按钮注册单击事件，点击显示DIV
		                    			$("#top_seed_professional_line_dv").show();    //显示DIV
		                    	        e2.stopPropagation();//阻止事件冒泡
		                    		})
		                	    		
		    						$(document).bind("click",function(e2){    
		                    	        $("#top_seed_professional_line_dv").hide();    //隐藏DIV
		                    	  	}) 
		
		    					   $("#top_seed_professional_line_cancel1,#top_seed_professional_line_cancel2").bind("click",function(e2){
		                    		   $("#top_seed_professional_line_dv").hide();
		                               e2.stopPropagation();//阻止事件冒泡
		                    		}); 
		                    	   ////XMJ修改结束                			
		                			$("#top_seed_professional_line_all").bind("click",function(){
		                    			if(this.checked){
		                    				$("input[name=params.top_seed_professional_line_d]").attr("checked","checked");
		                    				$("#top_seed_professional_line_dv li").addClass("curr");
		                    			}else{
		                    				$("input[name=params.top_seed_professional_line_d]").removeAttr("checked");
		                    				$("#top_seed_professional_line_dv li").removeClass("curr");
		                    			}
		                    			selectTopSeedProfessionalLine();
		                    		});
		                    		
		                    		$("input[name=params.top_seed_professional_line_d]").bind("click",function(){
		                    			if(this.checked){
		                    				$(this).parents("li").addClass("curr");
		                    			}else{
		                    				$(this).parents("li").removeClass("curr");
		                    			}
		                    			selectTopSeedProfessionalLine();
		                    		});
		                    		
		                    	
		                    		initTopSeedProfessionalLineCheckBox("top_seed_professional_line","params.top_seed_professional_line_d");
		                    	});
		                    	

		                		function selectTopSeedProfessionalLine(){
		                			var regions = $("input[name=params.top_seed_professional_line_d]:checked");
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
		                			$("#top_seed_professional_line_vp").val(regionNames);
		                			$("#top_seed_professional_line").val(regionIds);
		                		}

		                		//初始化多选框
		                		function initTopSeedProfessionalLineCheckBox(value_id,check_box_name){
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
			                	<input type="text" name="params.top_seed_professional_line_c" id="top_seed_professional_line_vp" value="${params.top_seed_professional_line_c}" class="ipt" readonly="readonly">
								<input type="hidden" name="params.top_seed_professional_line" id="top_seed_professional_line" value="${params.top_seed_professional_line}"  />  
								<a href="javascript:void(0);" id="top_seed_professional_line_a" class="selArr"></a>
								<div class="selOp" style="display: none;" id="top_seed_professional_line_dv">
									<div class="allSel">
										<label><input type="checkbox" id="top_seed_professional_line_chack_all">全选</label> 
										<label><a href="javascript:void(0);" class="aCancel" id="top_seed_professional_line_cancel1">关闭</a></label> 
										<label><a href="javascript:void(0);" class="aClear" id="top_seed_professional_line_cancel2"></a></label>
									</div>
									<div class="listItem">
										<ul>
											<c:forEach items="${intentTopSeedProLineList}" var="of">
												<li name="top_seed_professional_line_li"><input type="checkbox" name="params.top_seed_professional_line_d" value="${of.value}" c_name="${of.value_desc}">
													<span name="top_seed_professional_line_span">${of.value_desc}</span>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</span>
						
						</td>					
						
						<th>顶级种子类型：</th>
						<td>
						<script type="text/javascript">
		                		$(function(){ ////XMJ修改开始
		                			$("#top_seed_type_vp,#top_seed_type_a,#top_seed_type_dv").bind("click",function(e2){    //给按钮注册单击事件，点击显示DIV
		                    			$("#top_seed_type_dv").show();    //显示DIV
		                    	        e2.stopPropagation();//阻止事件冒泡
		                    		})
		                	    		
		    						$(document).bind("click",function(e2){    
		                    	        $("#top_seed_type_dv").hide();    //隐藏DIV
		                    	  	}) 
		
		    					   $("#top_seed_type_cancel1,#top_seed_type_cancel2").bind("click",function(e2){
		                    		   $("#top_seed_type_dv").hide();
		                               e2.stopPropagation();//阻止事件冒泡
		                    		}); 
		                    	   ////XMJ修改结束                			
		                			$("#top_seed_type_all").bind("click",function(){
		                    			if(this.checked){
		                    				$("input[name=params.top_seed_type_d]").attr("checked","checked");
		                    				$("top_seed_type_dv li").addClass("curr");
		                    			}else{
		                    				$("input[name=params.top_seed_type_d]").removeAttr("checked");
		                    				$("#top_seed_type_dv li").removeClass("curr");
		                    			}
		                    			selectTopSeedType();
		                    		});
		                    		
		                    		$("input[name=params.top_seed_type_d]").bind("click",function(){
		                    			if(this.checked){
		                    				$(this).parents("li").addClass("curr");
		                    			}else{
		                    				$(this).parents("li").removeClass("curr");
		                    			}
		                    			selectTopSeedType();
		                    		});
		                			
		                			initTopSeedTypeCheckBox("top_seed_type","params.top_seed_type_d");
		                    	});
		                    	


		                		function selectTopSeedType(){
		                			var regions = $("input[name=params.top_seed_type_d]:checked");
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
		                			$("#top_seed_type_vp").val(regionNames);
		                			$("#top_seed_type").val(regionIds);
		                		}

		                		//初始化多选框
		                		function initTopSeedTypeCheckBox(value_id,check_box_name){
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
			                	<input type="text" name="params.top_seed_type_c" id="top_seed_type_vp" value="${params.top_seed_type_c}" class="ipt" readonly="readonly">
								<input type="hidden" name="params.top_seed_type" id="top_seed_type" value="${params.top_seed_type}"  />  
								<a href="javascript:void(0);" id="top_seed_type_a" class="selArr"></a>
								<div class="selOp" style="display: none;" id="top_seed_type_dv">
									<div class="allSel">
										<label><input type="checkbox" id="top_seed_type_chack_all">全选</label> 
										<label><a href="javascript:void(0);" class="aCancel" id="top_seed_type_cancel1">关闭</a></label> 
										<label><a href="javascript:void(0);" class="aClear" id="top_seed_type_cancel2"></a></label>
									</div>
									<div class="listItem">
										<ul>
											<c:forEach items="${intentTopSeedTypeList}" var="of">
												<li name="top_seed_type_li"><input type="checkbox" name="params.top_seed_type_d" value="${of.value}" c_name="${of.value_desc}">
													<span name="top_seed_type_span">${of.value_desc}</span>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</span>
							 <!-- <a href="javascript:void(0);" id="queryIntent" class="dobtn" style="margin-left: 20px;">查询</a>  -->
						</td>
						<th>顶级种子分组：</th>
						<td>
						<script type="text/javascript">
		                		$(function(){ ////XMJ修改开始
		                			$("#top_seed_group_id_vp,#top_seed_group_id_a,#top_seed_group_id_dv").bind("click",function(e2){    //给按钮注册单击事件，点击显示DIV
		                    			$("#top_seed_group_id_dv").show();    //显示DIV
		                    	        e2.stopPropagation();//阻止事件冒泡
		                    		})
		                	    		
		    						$(document).bind("click",function(e2){    
		                    	        $("#top_seed_group_id_dv").hide();    //隐藏DIV
		                    	  	}) 
		
		    					   $("#top_seed_group_id_cancel1,#top_seed_group_id_cancel2").bind("click",function(e2){
		                    		   $("#top_seed_group_id_dv").hide();
		                               e2.stopPropagation();//阻止事件冒泡
		                    		}); 
		                    	   ////XMJ修改结束                			
		                			$("#top_seed_group_id_all").bind("click",function(){
		                    			if(this.checked){
		                    				$("input[name=params.top_seed_group_id_d]").attr("checked","checked");
		                    				$("top_seed_group_id_dv li").addClass("curr");
		                    			}else{
		                    				$("input[name=params.top_seed_group_id_d]").removeAttr("checked");
		                    				$("#top_seed_group_id_dv li").removeClass("curr");
		                    			}
		                    			selectTopSeedGroupId();
		                    		});
		                    		
		                    		$("input[name=params.top_seed_group_id_d]").bind("click",function(){
		                    			if(this.checked){
		                    				$(this).parents("li").addClass("curr");
		                    			}else{
		                    				$(this).parents("li").removeClass("curr");
		                    			}
		                    			selectTopSeedGroupId();
		                    		});
		                			
		                			initTopSeedGroupIdCheckBox("top_seed_group_id","params.top_seed_group_id_d");
		                    	});
		                    	


		                		function selectTopSeedGroupId(){
		                			var regions = $("input[name=params.top_seed_group_id_d]:checked");
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
		                			$("#top_seed_group_id_vp").val(regionNames);
		                			$("#top_seed_group_id").val(regionIds);
		                		}

		                		//初始化多选框
		                		function initTopSeedGroupIdCheckBox(value_id,check_box_name){
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
			                	<input type="text" name="params.top_seed_group_id_c" id="top_seed_group_id_vp" value="${params.top_seed_group_id_c}" class="ipt" readonly="readonly">
								<input type="hidden" name="params.top_seed_group_id" id="top_seed_group_id" value="${params.top_seed_group_id}"  />  
								<a href="javascript:void(0);" id="top_seed_group_id_a" class="selArr"></a>
								<div class="selOp" style="display: none;" id="top_seed_group_id_dv">
									<div class="allSel">
										<label><input type="checkbox" id="top_seed_group_id_chack_all">全选</label> 
										<label><a href="javascript:void(0);" class="aCancel" id="top_seed_group_id_cancel1">关闭</a></label> 
										<label><a href="javascript:void(0);" class="aClear" id="top_seed_group_id_cancel2"></a></label>
									</div>
									<div class="listItem">
										<ul>
											<c:forEach items="${intentTopSeedGroupIdList}" var="of">
												<li name="top_seed_group_id_li"><input type="checkbox" name="params.top_seed_group_id_d" value="${of.value}" c_name="${of.value_desc}">
													<span name="top_seed_group_id_span">${of.value_desc}</span>
												</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</span>
							
							<a href="javascript:void(0);" id="queryIntent" class="dobtn" style="margin-left: 20px;">查询</a>
						</td>
						
						
					</tr> 
					 
				</tbody>
			</table>
		</form>
	</div>
	<div>
		<form id="gridform" class="grid">
			<grid:grid from="webpage" formId="intentHandleForm" action="/shop/admin/orderIntentAction!intentHandleForm.do">
				<grid:header>
					<grid:cell>地市</grid:cell>
					<grid:cell>营业县分</grid:cell>
					<%-- <grid:cell>订单来源</grid:cell>--%>
					<grid:cell>订单号</grid:cell>
					<%--<grid:cell>意向单号</grid:cell>--%>
					<grid:cell>商品名称</grid:cell>
					<grid:cell>客户姓名</grid:cell>
					<grid:cell>客户号码</grid:cell>
					<grid:cell>客户地址</grid:cell>
					<%-- <grid:cell>推荐人</grid:cell>
					<grid:cell>推荐人号码</grid:cell> --%>
					<grid:cell>创建时间</grid:cell>
					<grid:cell style="">备注</grid:cell>
					<grid:cell>状态</grid:cell>
					<grid:cell>锁定信息</grid:cell>
					<grid:cell>处理</grid:cell>
				</grid:header>
				<grid:body item="intent">
					<grid:cell style="width: 47px;">${intent.order_city_code}</grid:cell>
					<grid:cell style="width: 55px;">${intent.order_county_code}</grid:cell>
					<%--<grid:cell>&nbsp;${intent.source_system_type} </grid:cell>--%>
					<grid:cell style="width: 140px;">${intent.order_id} </grid:cell>
					<%--<grid:cell style="width: 145px;">${intent.intent_order_id} </grid:cell>--%>
					<grid:cell style="width: 80px;">${intent.goods_name}</grid:cell>
					<grid:cell style="width: 70px;">${intent.ship_name}</grid:cell>
					<grid:cell style="width: 85px;">${intent.ship_tel}</grid:cell>
					<grid:cell style="width: 120px;">${intent.ship_addr}</grid:cell>
					<%-- <grid:cell>&nbsp;${intent.referee_name}</grid:cell>
					<grid:cell>&nbsp;${intent.referee_num}</grid:cell> --%>
					<grid:cell style="width: 115px;">${intent.create_time}</grid:cell>
					<grid:cell style="width: 100px;">
						<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="${intent.remark}" >&nbsp;${intent.remark}</div>
					</grid:cell>
					<grid:cell style="width: 120px;">
						<div class="list_t">
                        	<ul>
                            	<li><span style="width:90px">订单：</span><div>${intent.status}</div></li>
                            	<li><span style="width:90px">工单：</span><div>${intent.work_status}</div></li>
                            </ul>
                        </div>
                    </grid:cell>
					<grid:cell style="width: 100px;">${intent.lock_id}</grid:cell>
					<grid:cell> 
						<a href="javascript:void(0);" value="${intent.order_id}" name="handleIntent" class="dobtn" style="margin-left: 1px;">处理</a>
						<a href="javascript:void(0);" value="${intent.order_id}" name="closeOrdWorkbtn" class="dobtn" style="margin-left: 1px;">工单回收</a>
						<a href="javascript:void(0);" value="${intent.order_id}" id="${intent.is_work_custom }" name="intentDetails" class="dobtn" style="margin-left: 1px;">详情</a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
	<div id="handleIntent"></div>
	<div id="closeOrdWorkbtn"></div>
	<div id="intentDetails"></div>
