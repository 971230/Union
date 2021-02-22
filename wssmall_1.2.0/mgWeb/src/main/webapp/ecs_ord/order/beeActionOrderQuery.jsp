<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/workCustom/js/workFlow.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>


<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css" rel="stylesheet" type="text/css" />
<style type="text/css">
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
.relaDiv {
    position: relative;
    height: 30px;
    line-height: 30px;
}
.absoDivHead {
    border-bottom: 1px solid #d5d5d5;
    background: #dbdbdb;
    line-height: 20px;
    text-align: left;
    margin: 0px;
    padding-left: 5px;
    font-weight: bold;
}
.absoDivContent {
    padding: 5px;
}
.absoDiv {
    position: absolute;
    display: none;
    line-height: 20px;
    width: 450px;
    top: 24px;
    left: 0px;
    height: auto;
    border: 1px solid #d5d5d5;
    background: #fff;
    z-index: 100;
    background: #FCFCE4;
    text-align: left;
}
</style>
<body style="background: white;">
	<div class="searchBx">
		<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/orderIntentAction!beeActionOrderQuery.do'>
			<input type="hidden" name="params.query_btn_flag" value="yes" />
			 <input type="hidden" name="permission_level" id="permission_level" value="${permission_level }" />
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>外部编号：</th>
						<td><input type="text" name="params.out_tid" value="${params.out_tid}" style="width: 138px;" class="ipt_new"></td>

						<th>证件号码：</th>
						<td><input type="text" name="params.cert_card_num" value="${params.cert_card_num}" style="width: 138px;" class="ipt_new"></td>

						<th>联系人名称：</th>
						<td><input type="text" name="params.contact_name" value="${params.contact_name}" style="width: 138px;" class="ipt_new"></td>
						<th>下单时间：</th>
						<td>
						<input style="width: 150px;" type="text" id="create_start" name="params.create_start" value="${params.create_start }" readonly="readonly"
							class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
							<input style="width: 150px;" type="text" id="create_end"
							name="params.create_end" value="${params.create_end }"
							readonly="readonly" class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</td>
				
					</tr>
					<tr>
						<th>地市：</th>
						<td>
							<script type="text/javascript">
							$(function() {
								$("#region_ivp,#region_a,#region_div").bind("click", function(e) { //给按钮注册单击事件，点击显示DIV
									$("#region_div").show(); //显示DIV
									e.stopPropagation();//阻止事件冒泡
								})
						
								$(document).bind("click", function() {
									$("#region_div").hide(); //隐藏DIV
								})
						
								$("#regionCancel,#regionCancel2").bind("click", function(e) {
									$("#region_div").hide();
									e.stopPropagation();//阻止事件冒泡
								});
								////XMJ修改结束                		
								$("#regioncheckAll").bind("click", function() {
									if (this.checked) {
										$("input[name=region_id]").attr("checked", "checked");
										$("#region_div li").addClass("curr");
									} else {
										$("input[name=region_id]").removeAttr("checked");
										$("#region_div li").removeClass("curr");
									}
									selectRegions();
								});
						
								$("input[name=region_id]").bind("click", function() {
									if (this.checked) {
										$(this).parents("li").addClass("curr");
									} else {
										$(this).parents("li").removeClass("curr");
									}
									selectRegions();
								});
						
								initCheckBox("region_hivp", "region_id");
						
							});
						
							function selectRegions() {
								var regions = $("input[name=region_id]:checked");
								var regionNames = "";
								var regionIds = "";
								regions.each(function(idx, item) {
									var name = $(item).attr("c_name");
									var rid = $(item).attr("value");
									regionNames += name + ",";
									regionIds += rid + ",";
								});
								if (regionIds.length > 1) {
									regionIds = regionIds.substr(0, regionIds.length - 1);
									regionNames = regionNames.substr(0, regionNames.length - 1);
								}
								$("#region_ivp").val(regionNames);
								$("#region_hivp").val(regionIds);
							}
							</script>
							<span class="selBox" style="width: 120px;"> 
							<input type="text" name="params.order_city_code_c" id="region_ivp" value="${params.order_city_code_c }" class="ipt" readonly="readonly">
							<input type="hidden" name="params.order_city_code" value="${params.order_city_code }" id="region_hivp" />
								<a id="region_a" href="javascript:void(0);" class="selArr"></a>
								<div id="region_div" class="selOp" style="display: none;">
									<div class="allSel">
										<label><input type="checkbox" id="regioncheckAll">全选</label>
										<label><a href="javascript:void(0);" class="aCancel"
											id="regionCancel">关闭</a></label> <label><a
											href="javascript:void(0);" class="aClear" id="regionCancel2"></a></label>
									</div>

									<div class="listItem">
										<ul>
											<c:forEach items="${regionList }" var="rg">
												<li><input type="checkbox" name="region_id"
													value="${rg.region_id }" c_name="${rg.local_name }"><span
													name="region_li">${rg.local_name }</span></li>
											</c:forEach>
										</ul>
									</div>
								</div>
						</span>
					</td>
						<th>业务号码：</th>
						<td><input type="text" name="params.serial_number" value="${params.serial_number }" style="width: 138px;" class="ipt_new"></td>
						
							<th>联系人号码：</th>
						<td><input type="text" name="params.contact_phone" value="${params.contact_phone }" style="width: 138px;" class="ipt_new"></td>
						
							<th>处理时间：</th>
						<td><input style="width: 150px;" type="text"
							id="done_time_start" name="params.done_time_start"
							value="${params.done_time_start }" readonly="readonly"
							class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
							<input style="width: 150px;" type="text" id="done_time_end"
							name="params.done_time_end" value="${params.done_time_end }"
							readonly="readonly" class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
					</tr>
					<tr>
						<th>订单类型：</th>
						<td>
							<select name="params.order_type" id="order_type" class="ipt_new" style="width: 145px;">
								<option value ="">请选择</option>
  								<option value ="100104" ${'100104'==params.order_type?'selected':'' }>总部蜂行动</option>
  								<option value ="100101" ${'100101'==params.order_type?'selected':'' }>京东便利店</option>
							</select>
						</td>
						<th>处理状态：</th>
						<td>
							<select name="params.status" id="status" class="ipt_new" style="width: 145px;">
								<option value ="">请选择</option>
  								<option value ="0" ${'0'==params.status?'selected':'' }>未处理</option>
  								<option value ="1" ${'1'==params.status?'selected':'' }>处理中</option>
  								<option value ="2" ${'2'==params.status?'selected':'' }>处理成功</option>
  								<option value ="3" ${'3'==params.status?'selected':'' }>处理失败</option>
							</select>
						</td>
						<td><input class="comBtn" type="button" name="searchBtn"
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
						<td class="tdOne" style="width: 9%">外部编号</td>
						<td class="tdOne" style="width: 9%">证件号码</td>
						<td class="tdOne" style="width: 6%">联系人名称</td>
						<td class="tdOne" style="width: 7%">联系人号码</td>
						<td class="tdOne" style="width: 4%">地市</td>
						<td class="tdOne" style="width: 9%">业务号码</td>
						<td class="tdOne" style="width: 10%">下单时间</td>
						<td class="tdOne" style="width: 7%">订单类型</td>
						<td class="tdOne" style="width: 5%">报文</td>
						<td class="tdOne" style="width:	8%">处理状态</td>
						<td class="tdOne" style="width: 8%">失败原因</td>
						<td class="tdOne" style="width: 10%">处理时间</td>
						<td class="tdOne" style="width: 8%">操作</td>
					</tr>
					</table>
					<grid:grid from="webpage" formId="serchform" >
						<grid:body item="order">
							<grid:cell style="width:9%">${order.order_id}</grid:cell>
							<grid:cell style="width:9%">${order.pspt_id}</grid:cell>
							<grid:cell style="width:6%">${order.contact_name}</grid:cell>
							<grid:cell style="width:7%">${order.contact_phone}</grid:cell>
							<grid:cell style="width:4%">${order.num_city}</grid:cell>
							<grid:cell style="width:9%">${order.serial_number}</grid:cell>
						
							<grid:cell style="width:10%">${order.create_date}</grid:cell>
							<grid:cell style="width:7%">
								<c:if test="${order.order_type == 100101 }">京东便利店</c:if>
								<c:if test="${order.order_type == 100104 }">总部蜂行动</c:if>
							</grid:cell>
							<grid:cell style="width:5%">
<!-- 								<div class="relaDiv"> -->
<!-- 								    <a>查看</a> -->
<!-- 									<div class="absoDiv"> -->
<!-- 									   <div class="absoDivHead">报文</div> -->
<!-- 									   <div class="absoDivContent"> -->
<%-- 									      ${order.req}<c:if test="${empty order.req}">无报文</c:if> --%>
<!-- 									   </div> -->
<!-- 									</div> -->
<!-- 								</div> -->
									<div id="div_req_val${order.order_id}" style="display:none;">${order.req}</div>
									<a href="javascript:void(0);" value="${order.order_id}" name="queryReq" class="dobtn">查看</a>
							</grid:cell>
							<grid:cell style="width:8%">
								<c:if test="${order.done_status == 0 }">未处理</c:if>
								<c:if test="${order.done_status == 1 }">处理中</c:if>
								<c:if test="${order.done_status == 2 }">处理成功</c:if>
								<c:if test="${order.done_status == 3 }">处理失败</c:if>
							</grid:cell>
							<grid:cell style="width:8%">${order.exception_desc}</grid:cell>
							<grid:cell style="width:10%">${order.done_time}</grid:cell>
							<grid:cell style="width:8%">
								<c:if test="${order.done_status == 0 || order.done_status == 3 }">
									<a href="javascript:void(0);" value="${order.order_id}" name="beeActionCreateOrder" class="dobtn" >处理</a>
								</c:if>
								<!--  
								<c:if test="${order.done_status == 1 || order.done_status == 2 }">
									<a href="javascript:void(0);"  class="dobtn" >已处理</a>
								</c:if>
								-->
							</grid:cell>
						</grid:body>
				</grid:grid>
				</tbody>
		</form>
	</div>
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
	<div id="show_req"></div>
</body>

<script type="text/javascript">

	$(function() {

		var jsonBack = function(reply) {
			alert(reply.message);
			if (reply.result == 0) {
				$("#searchBtn").click();
			} 
		}
		
		$("#searchBtn").bind("click",function() {
					$.Loading.show("正在加载所需内容,请稍侯...");
					$("#serchform").attr("action",ctx+"/shop/admin/orderIntentAction!beeActionOrderQuery.do").submit();
				});
		//处理
		$("a[name='beeActionCreateOrder']").bind("click",function(){
			var order_id = $(this).attr("value");
			debugger;
			if(order_id == null || order_id==""){
				alert("异常：order_id为空");
				return;
			}
			var url = "/shop/admin/orderIntentAction!beeActionIntenCreate.do?ajax=yes&order_id="+order_id;
			Cmp.excute('', url, {}, jsonBack, 'json');
			showMask(); 	
		});
		$(".relaDiv").mouseenter(function(){
	        showOrg(this);   
		});
		
		$(".relaDiv").mouseleave(function(){
	        hideOrg(this);   
		});

	});
	
	//初始化多选框
	function initCheckBox(value_id,check_box_name){
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
	
	function showOrg(item){
		var row = $(item).closest("tr");
		var orgDiv = $(row).find(".absoDiv");
		var orgContent = $(row).find(".absoDivContent");
		$(orgDiv).attr("style","display:block");

	}
	
	function hideOrg(item){
		var row = $(item).closest("tr");
		$(row).find(".absoDiv").attr("style","display:none");
	}
	
	function handle(orderid){
		var url = "/shop/admin/orderIntentAction!beeActionIntenCreate.do?ajax=yes&order_id="+order_id;
		Cmp.excute('', url, {}, jsonBack, 'json');
	}
	
	function showMask(){     
		var mask_bg = document.createElement("div");
	    mask_bg.id = "mask";
	    mask_bg.style.position = "absolute";
	    mask_bg.style.top = "0px";
	    mask_bg.style.left = "0px";
	    mask_bg.style.width = "100%";
	    mask_bg.style.height = "100%";
	    mask_bg.style.backgroundColor = "#333";
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
	    mask_msg.innerText = "正在处理,请稍后...";
	    mask_bg.appendChild(mask_msg);
	};
	
	Eop.Dialog.init({id:"show_req",modal:true,title:"报文",width:'900px'});
	$("a[name='queryReq']").bind("click",
			function() {
				var divValue = $("#div_req_val"+$(this).attr("value")).text();
				$('#show_req').html(divValue);
				Eop.Dialog.open("show_req");
			});
</script>
