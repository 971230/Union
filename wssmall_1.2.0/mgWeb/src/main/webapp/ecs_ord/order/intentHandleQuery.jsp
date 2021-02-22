<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

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
<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workFlow.js"></script>

<body>
	<div class="searchBx">
		<form>
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>订单号：</th>
						<td>
							<input id="input_order_id" type="text" name="params.order_id" style="width: 145px;" class="ipt_new">
						</td>
						
						<th>意向单号：</th>
						<td>
							<input id="intent_order_id" type="text" name="params.intent_order_id" style="width: 145px;" class="ipt_new">
						</td>
						
						<th>时间：</th>
						<td>
							<input id="create_start" type="text" name="params.create_start" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
							-
	                    	<input id="create_end" type="text" name="params.create_end" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</td>
					</tr>
					
					<tr>
						<th>订单状态：</th>
						<td>
		                	<span class="selBox" style="width: 120px;"> 
			                	<input type="text" name="params.status_c" id="status_vp" class="ipt" readonly="readonly">
			                	
								<input type="hidden" name="params.status_ivp" id="status_ivp" />  
								
								<a href="javascript:void(0);" id="status_a" class="selArr"></a>
								
								<div class="selOp" style="display: none;" id="status_dv">
								
									<div class="allSel">
										<label><input type="checkbox" id="status_chack_all">全选</label> 
										<label><a href="javascript:void(0);" class="aCancel" id="status_cancel1">关闭</a></label> 
										<label><a href="javascript:void(0);" class="aClear" id="status_cancel2"></a></label>
									</div>
									
									<div class="listItem">
										<ul>
											<c:forEach items="${intentStatusList}" var="of">
												<li name="status_li"><input type="checkbox" name="params.status" value="${of.value}" c_name="${of.value_desc}">
													<span name="status_span">${of.value_desc}</span>
												</li>
											</c:forEach>
										</ul>
									</div>
									
								</div>
							</span>
						</td>
						
						<th>归属地市：</th>
						<td>
							<select id="order_region_code" name="params.order_city_code" class="ipt_new" style="width: 145px;">
							</select>
						</td>
						
						<th>营业县分：</th>
						<td>
							<select id="order_county_code" name="params.order_county_code" class="ipt_new" style="width: 145px;">
							</select>	
						</td>
					</tr>
					
					<tr>
						<th>业务号码：</th>
						<td>
							<input id="phone_num" type="text"  name="params.phone_num" style="width: 145px;" class="ipt_new">
							<span style="width: 20px;"></span>
						</td>
						
						<th>客户号码：</th>
						<td>
							<input id="ship_tel" type="text"  name="params.ship_tel" style="width: 145px;" class="ipt_new">
							<span style="width: 20px;"></span>
						</td>
						
						<th>订单来源：</th>
						<td>
							<span class="selBox" style="width: 120px;"> 
			                	<input type="text" name="params.order_from_c" id="order_from_vp" class="ipt" readonly="readonly">
								<input type="hidden" name="params.order_from" id="order_from" />  
								
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
							
						<!-- 	<a href="javascript:void(0);" id="queryIntent" onclick="doQuery();" class="dobtn" style="margin-left: 20px;">查询</a> -->
						</td>
					</tr>
					
					<tr>
						<th>顶级种子专业线：</th>
						<td>
							<span class="selBox" style="width: 120px;"> 
			                	<input type="text" name="params.top_seed_professional_line_c" id="top_seed_professional_line_vp" class="ipt" readonly="readonly">
			                	
								<input type="hidden" name="params.top_seed_professional_line_ivp" id="top_seed_professional_line_ivp" />  
								
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
												<li name="top_seed_professional_line_li"><input type="checkbox" name="params.top_seed_professional_line" value="${of.value}" c_name="${of.value_desc}">
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
							<span class="selBox" style="width: 120px;"> 
			                	<input type="text" name="params.top_seed_type_c" id="top_seed_type_vp" class="ipt" readonly="readonly">
			                	
								<input type="hidden" name="params.top_seed_type_ivp" id="top_seed_type_ivp" />  
								
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
												<li name="top_seed_type_li"><input type="checkbox" name="params.top_seed_type" value="${of.value}" c_name="${of.value_desc}">
													<span name="top_seed_type_span">${of.value_desc}</span>
												</li>
											</c:forEach>
										</ul>
									</div>
									
								</div>
							</span>
							<!--  <a href="javascript:void(0);" id="queryIntent" onclick="doQuery();" class="dobtn" style="margin-left: 20px;">查询</a>  -->
						</td>
						<th>顶级种子分组：</th>
						<td>
							<span class="selBox" style="width: 120px;"> 
			                	<input type="text" name="params.top_seed_group_id_c" id="top_seed_group_id_vp" class="ipt" readonly="readonly">
			                	
								<input type="hidden" name="params.top_seed_group_id_ivp" id="top_seed_group_id_ivp" />  
								
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
												<li name="top_seed_group_id_li"><input type="checkbox" name="params.top_seed_group_id" value="${of.value}" c_name="${of.value_desc}">
													<span name="top_seed_group_id_span">${of.value_desc}</span>
												</li>
											</c:forEach>
										</ul>
									</div>
									
								</div>
							</span>
							<a href="javascript:void(0);" id="queryIntent" onclick="doQuery();" class="dobtn" style="margin-left: 20px;">查询</a>
						</td>
						
					</tr>
					
				</tbody>
			</table>
		</form>
	</div>
	
	<div>
		<iframe id="intent_frame" style="width: 100%;height: 600px;"></iframe>
	</div>
	
	<div id="addWork"></div>
	<div id="closeIntent"></div>
	<div id="intentDetails"></div>
	<div id="addRemarks"></div>
	<div id="queryCountyListDlg"></div>
</body>

<script type="text/javascript">
var permission;
var regionCountyChanger;

$(function() {
	initPermission();
	
	initStatus();
	
	initOrderFrom();
	
	initTopSeedProfessionalLine();
	
	initTopSeedType();
	initTopSeedGroupId();
	
	Eop.Dialog.init({id:"intentDetails",modal:true,title:"意向单详情",width:'1100px'});
	Eop.Dialog.init({id:"addRemarks",modal:true,title:"添加备注",width:'900px'});
	
	doQuery();
});
	
(function($) {
	$.fn.aramsDiv = function() {
		var $this = $(this);
		$this.bind("mouseout", function() {});
		$(this).bind("mouseover", function() {});
	};
})(jQuery); 

function initPermission(){
	permission = WorkFlowTool.getWorkCustomPermission();
	
	if(typeof(permission)!="undefined" && "1" == permission.level){
		regionCountyChanger = new RegionCountyChanger("order_region_code","order_county_code");
	}else if(typeof(window.permission)!="undefined"){
		regionCountyChanger = new RegionCountyChanger("order_region_code","order_county_code",
				permission.region,permission.county,true);
	}
}

function initStatus(){
	$("#status_vp,#status_a,#status_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
        $("#status_dv").show();    //显示DIV
        e1.stopPropagation();//阻止事件冒泡
	})
		
	$(document).bind("click",function(e1){    
        $("#status_dv").hide();    //隐藏DIV
  	}) 

    $("#status_cancel1,#status_cancel2").bind("click",function(e1){
	   $("#status_dv").hide();
       e1.stopPropagation();//阻止事件冒泡
	}); 
       			
	$("#status_chack_all").bind("click",function(){
		if(this.checked){
			$("input[name=params.status]").attr("checked","checked");
			$("#status_dv li").addClass("curr");
		}else{
			$("input[name=params.status]").removeAttr("checked");
			$("#status_dv li").removeClass("curr");
		}
		
		selectOrderStatus();
	});
	
	$("input[name=params.status]").bind("click",function(){
		if(this.checked){
			$(this).parents("li").addClass("curr");
		}else{
			$(this).parents("li").removeClass("curr");
		}
		
		selectOrderStatus();
	});
	
	initStatusCheckBox("status_ivp","params.status");
}

function selectOrderStatus(){
	var regions = $("input[name=params.status]:checked");
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
	
	$("#status_vp").val(regionNames);
	$("#status_ivp").val(regionIds);
}

//初始化多选框
function initStatusCheckBox(value_id,check_box_name){
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

function initOrderFrom(){
	$("#order_from_vp,#order_from_a,#order_from_dv").bind("click",function(e2){    //给按钮注册单击事件，点击显示DIV
		debugger;
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
      			
	$("#order_from_chack_all").bind("click",function(){
		debugger;
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
		debugger;
		if(this.checked){
			$(this).parents("li").addClass("curr");
		}else{
			$(this).parents("li").removeClass("curr");
		}
		selectOrderFromsList();
	});
	
	initFromCheckBoxs("order_from","params.order_from_d");
}

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
function initFromCheckBoxs(value_id,check_box_name){
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


//顶级种子专线

 function initTopSeedProfessionalLine(){
	$("#top_seed_professional_line_vp,#top_seed_professional_line_a,#top_seed_professional_line_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
        $("#top_seed_professional_line_dv").show();    //显示DIV
        e1.stopPropagation();//阻止事件冒泡
	})
		
	$(document).bind("click",function(e1){    
        $("#top_seed_professional_line_dv").hide();    //隐藏DIV
  	}) 

    $("#top_seed_professional_line_cancel1,#top_seed_professional_line_cancel2").bind("click",function(e1){
	   $("#top_seed_professional_line_dv").hide();
       e1.stopPropagation();//阻止事件冒泡
	}); 
       			
	$("#top_seed_professional_line_chack_all").bind("click",function(){
		if(this.checked){
			$("input[name=params.top_seed_professional_line]").attr("checked","checked");
			$("#top_seed_professional_line_dv li").addClass("curr");
		}else{
			$("input[name=params.top_seed_professional_line]").removeAttr("checked");
			$("#top_seed_professional_line_dv li").removeClass("curr");
		}
		
		selectTopSeedProfessionalLine();
	});
	
	$("input[name=params.top_seed_professional_line]").bind("click",function(){
		if(this.checked){
			$(this).parents("li").addClass("curr");
		}else{
			$(this).parents("li").removeClass("curr");
		}
		
		selectTopSeedProfessionalLine();
	});
	
	initTopSeedProfessionalLineCheckBox("top_seed_professional_line_ivp","params.top_seed_professional_line");
}

function selectTopSeedProfessionalLine(){
	var regions = $("input[name=params.top_seed_professional_line]:checked");
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
	$("#top_seed_professional_line_ivp").val(regionIds);
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
//顶级种子类型top_seed_type
function initTopSeedType(){
	$("#top_seed_type_vp,#top_seed_type_a,#top_seed_type_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
        $("#top_seed_type_dv").show();    //显示DIV
        e1.stopPropagation();//阻止事件冒泡
	})
		
	$(document).bind("click",function(e1){    
        $("#top_seed_type_dv").hide();    //隐藏DIV
  	}) 

    $("#top_seed_type_cancel1,#top_seed_type_cancel2").bind("click",function(e1){
	   $("#top_seed_type_dv").hide();
       e1.stopPropagation();//阻止事件冒泡
	}); 
       			
	$("#top_seed_type_chack_all").bind("click",function(){
		if(this.checked){
			$("input[name=params.top_seed_type]").attr("checked","checked");
			$("#top_seed_type_dv li").addClass("curr");
		}else{
			$("input[name=params.top_seed_type]").removeAttr("checked");
			$("#top_seed_type_dv li").removeClass("curr");
		}
		
		selectTopSeedType();
	});
	
	$("input[name=params.top_seed_type]").bind("click",function(){
		if(this.checked){
			$(this).parents("li").addClass("curr");
		}else{
			$(this).parents("li").removeClass("curr");
		}
		
		selectTopSeedType();
	});
	
	initTopSeedTypeCheckBox("top_seed_type_ivp","params.top_seed_type");
}

function selectTopSeedType(){
	var regions = $("input[name=params.top_seed_type]:checked");
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
	$("#top_seed_type_ivp").val(regionIds);
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


//顶级种子类型top_seed_group_id
function initTopSeedGroupId(){
	$("#top_seed_group_id_vp,#top_seed_group_id_a,#top_seed_group_id_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
        $("#top_seed_group_id_dv").show();    //显示DIV
        e1.stopPropagation();//阻止事件冒泡
	})
		
	$(document).bind("click",function(e1){    
        $("#top_seed_group_id_dv").hide();    //隐藏DIV
  	}) 

    $("#top_seed_group_id_cancel1,#top_seed_group_id_cancel2").bind("click",function(e1){
	   $("#top_seed_group_id_dv").hide();
       e1.stopPropagation();//阻止事件冒泡
	}); 
       			
	$("#top_seed_group_id_chack_all").bind("click",function(){
		if(this.checked){
			$("input[name=params.top_seed_group_id]").attr("checked","checked");
			$("#top_seed_group_id_dv li").addClass("curr");
		}else{
			$("input[name=params.top_seed_group_id]").removeAttr("checked");
			$("#top_seed_group_id_dv li").removeClass("curr");
		}
		
		selectTopSeedGroupId();
	});
	
	$("input[name=params.top_seed_group_id]").bind("click",function(){
		if(this.checked){
			$(this).parents("li").addClass("curr");
		}else{
			$(this).parents("li").removeClass("curr");
		}
		
		selectTopSeedGroupId();
	});
	
	initTopSeedGroupIdCheckBox("top_seed_group_id_ivp","params.top_seed_group_id");
}

function selectTopSeedGroupId(){
	var regions = $("input[name=params.top_seed_group_id]:checked");
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
	$("#top_seed_group_id_ivp").val(regionIds);
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
 


function doQuery(){
	var order_id = $("#input_order_id").val();
	var intent_order_id = $("#intent_order_id").val();
	var create_start = $("#create_start").val();
	var create_end = $("#create_end").val();
	var status = $("#status_ivp").val();
	
	var order_city_code = $("#order_region_code").val();
	var order_county_code = $("#order_county_code").val();
	var phone_num = $("#phone_num").val();
	var ship_tel = $("#ship_tel").val();
	var order_from = $("#order_from").val();
	
	//新增顶级专业线及顶级种子类型
	var top_seed_professional_line = $("#top_seed_professional_line_ivp").val();
	var top_seed_type = $("#top_seed_type_ivp").val();
	var top_seed_group_id = $("#top_seed_group_id_ivp").val();
	
	
	var url = ctx+"/shop/admin/orderIntentAction!intentQueryTable.do?ajax=yes";
	
	url += "&params.order_id="+order_id;
	url += "&params.intent_order_id="+intent_order_id;
	url += "&params.create_start="+create_start;
	url += "&params.create_end="+create_end;
	
	url += "&params.status="+status;
	url += "&params.order_city_code="+order_city_code;
	url += "&params.order_county_code="+order_county_code;
	url += "&params.phone_num="+phone_num;
	
	url += "&params.ship_tel="+ship_tel;
	url += "&params.order_from="+order_from;
	
	url += "&params.top_seed_professional_line="+top_seed_professional_line;
	url += "&params.top_seed_type="+top_seed_type;
	url += "&params.top_seed_group_id="+top_seed_group_id; 
	
	
	
	$("#intent_frame").attr("src",url);
}

function showIntentInfo(order_id,is_work_custom){
	$("#intentDetails").empty();
	
	if(order_id == null || order_id==""){
		alert("异常：order_id为空");
		return;
	}
	
	Eop.Dialog.open("intentDetails");
	var url= ctx+"/shop/admin/orderIntentAction!intentDetails.do?ajax=yes&order_id="+order_id+"&is_work_custom="+is_work_custom;
	$("#intentDetails").load(url,{},function(){});
}

function addIntentRemark(order_id){
	$("#addRemarks").empty();
	
	if(order_id == null || order_id==""){
		alert("异常：order_id为空");
		return;
	}
	
	Eop.Dialog.open("addRemarks");
	var url= ctx+"/shop/admin/orderIntentAction!intentAddRemark.do?ajax=yes&order_id="+order_id+"&type=remark";
	$("#addRemarks").load(url,{},function(){});
}

function intentBack(order_id){
	if(order_id == null || order_id==""){
		alert("异常：order_id为空");
		return;
	}
	if(confirm("请确认是否回收!")){}else{
		return;
	}
	$.ajax({  
        type: "POST",  
        data:"ajax=yes&order_id=" + order_id,  
        url: ctx + "/shop/admin/orderIntentAction!intentBack.do",  
        dataType: "json",  
        cache: false,  
        success: function(responseText){   
        	if (responseText.result == 0) {
    			alert(responseText.message);
    		} else {
    			alert(responseText.message);
    		} 
        }  
    });
	
}

</script>