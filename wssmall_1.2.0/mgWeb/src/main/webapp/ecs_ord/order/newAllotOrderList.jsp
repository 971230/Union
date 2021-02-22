<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<style>
.relaDiv1{ position:relative;height:30px;line-height:30px; }
.absoDiv1{ position:absolute;display:none;line-height:20px; width:350px;top:24px; right:0px;  border:1px solid #d5d5d5; background:#fff;z-index:100; background:#FCFCE4;text-align:left;}
.absoDivHead1{border-bottom:1px solid #d5d5d5;background:#dbdbdb;line-height:20px;text-align:left;margin:0px;padding-left:5px; font-weight:bold;}
.absoDivContent1{padding:5px;}
select{height: 20px; width: 171px;}

</style>
<form method="post" id="serchform" action='${ctx}/shop/admin/ordAuto!NewAllotOrderList.do'>
	 <div class="searchformDiv">
		 <table >
				<tr>
					<th>内部订单号：</th>
					<td>
						<input type="text" class="ipt_new" name="params.order_id" value="${params.order_id}" />
					</td>
					<th>外部订单号：</th>
					<td>
						<input type="text" class="ipt_new" name="params.out_tid" value="${params.out_tid}" />
					</td>
					<th>客户姓名：</th>
					<td>
						<input type="text" class="ipt_new" name="params.ship_name" value="${params.ship_name}" />
					</td>
				</tr>
				<tr>
					<th>归属地市：</th>
					<td>
						<select name="params.order_city_code" class="ipt_new" onchange="queryCountyByCity(this)" style="width: 171px;height: 20px;">
							<option value="">请选择</option>
							<c:forEach var="of" items="${regionList}">
								<option value="${of.region_id}" ${of.region_id==params.order_city_code?'selected':'' }>&nbsp;${of.local_name}</option>
							</c:forEach>
						</select>
					</td>
					<th>县分：</th>
					<td>
						<select id="order_county_code" name="params.order_county_code" class="ipt_new" style="width: 171px;height: 20px;">
							<option value="" >--请选择--</option>
							<c:forEach items="${countyList}" var="ds">
		               			<option value="${ds.field_value}" ${ds.field_value==params.order_county_code?'selected':''}>${ds.field_value_desc}</option>
		               		</c:forEach>
						</select>	
					</td>
					<th>联系号码：</th>
					<td>
						<input type="text" class="ipt_new" name="params.ship_tel" value="${params.ship_tel}" />
					</td>
					<%-- <th>县分：</th>
					<td><input type="text" class="ipt_new" name="district_code"
						id="district_code" value="${district_code}"
						onfocus="querycountyId()" /> 
						<input type="hidden" class="ipt_new"
						name="order_county_name" id="order_county_name" value="${order_county_name}"></td>
					<td><a href="javascript:void(0);" id="resetBtn" class="dobtn"
						style="margin-right: 10px;">清除</a> <!-- <input class="comBtn" type="button"  value="清除" style="margin-right:10px;" /> -->
					</td> --%>
				</tr>
				<tr>
				     <th>商品名称：</th>
					<td>
						<input type="text" class="ipt_new" name="params.goods_name" value="${params.goods_name}" />
					</td>
				     <th>业务号码：</th>
					<td>
						<input type="text" class="ipt_new" name="params.phone_num" value="${params.phone_num}" />
					</td>
					<th>证件上传状态：</th>
					<td>
						<select name="params.order_status" class="ipt_new" id="order_status" style="width: 171px;height: 20px;">
							<option value="">请选择</option>
								<c:forEach items="${order_vplan_list}" var="of">
									<option value="${of.value}" ${of.value==params.order_status?'selected':'' } >${of.value_desc }</option>
								</c:forEach>
						</select>
					</td>
					
			    </tr>
				<tr>
					<th>创建时间：</th>
		     		<td>
				     <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
				     <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
				     </td>
		    		 <th></th>
		     		 <td>
			   			 <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
			    	</td>
			    </tr>
		 </table>
	  </div>
</form>

	<!-- 	<a href="javascript:void(0);" id="queryUser" style="margin-right:10px;" class="graybtn1" ><span>批量分配</span></a> -->
<form method="POST"  id="lockOrderFrom" class="grid">
	<grid:grid from="webpage"  formId="serchform">
		<grid:header>
		    <grid:cell width="2%" ><!-- <input type="checkbox" id="checkAlls" /> -->选择</grid:cell>
			<grid:cell width="5%">订单来源</grid:cell>
			<grid:cell width="5%">归属地市</grid:cell>
			<grid:cell width="5%">县分</grid:cell>
			<grid:cell width="7%">内部订单号</grid:cell>
			<grid:cell width="7%">外部订单号</grid:cell>
			<grid:cell width="7%">商品名称</grid:cell>
			<grid:cell width="7%">下单时间</grid:cell>
			<grid:cell width="7%">客户姓名</grid:cell>
			<grid:cell width="7%">联系号码</grid:cell>
			<grid:cell width="7%">分配状态</grid:cell>
			<grid:cell width="7%">锁定人</grid:cell>
			<grid:cell width="3%">自定义</grid:cell>
			<grid:cell width="7%">操作记录</grid:cell>
			<grid:cell width="20%">操作</grid:cell>
		</grid:header>
		<grid:body item="lockOrder">
			<grid:cell><input name="lockOrderChk" type="checkbox" value="${lockOrder.order_id}"></grid:cell>
			<grid:cell>${lockOrder.order_from_n}</grid:cell>
			<grid:cell>${lockOrder.order_city_code}</grid:cell>
			<grid:cell>${lockOrder.order_county_code}</grid:cell>
			<grid:cell>${lockOrder.order_id}</grid:cell>
			<grid:cell>${lockOrder.out_tid}</grid:cell>
			<grid:cell>${lockOrder.goods_name}</grid:cell>
			<grid:cell>${lockOrder.tid_time}</grid:cell>
			<grid:cell>${lockOrder.ship_name}</grid:cell>
			<grid:cell>${lockOrder.ship_tel}</grid:cell>
			<grid:cell >${lockOrder.allot_status}</grid:cell>
			<grid:cell>
				<c:if test="${ lockOrder.lock_user_name !=null && lockOrder.lock_user_realname !=null}">
	            	${lockOrder.lock_user_name }/${lockOrder.lock_user_realname }
	        	</c:if>
			</grid:cell>
			<grid:cell >
			<input type="hidden" id="is_work_custom" value="${lockOrder.is_work_custom}">
			<input type="hidden" id="county_code" value="${lockOrder.county_code}">
			<c:if test="${lockOrder.is_work_custom == 1}">是</c:if>
			<c:if test="${lockOrder.is_work_custom == 0}">否</c:if>
			<c:if test="${lockOrder.is_work_custom == null || lockOrder.is_work_custom ==''}"> </c:if>
			</grid:cell>
			<grid:cell>
			<div class="relaDiv1" >
			<a>操作记录</a>
			<div class="absoDiv1">
								   <div class="absoDivHead1">操作记录</div>
								   <div class="absoDivContent1">
							   	   	<table cellspacing="0" cellpadding="0" border="0">
									<thead id="intentHandleTitle">
										<tr>
											<th style="width: auto;">操作记录</th>
											<th style="width: auto;">操作时间</th>
											<th style="width: auto;">操作人员</th>
										</tr>
									</thead>
									<tbody id="intentHandleNode">
										<c:forEach items="${lockOrder.intenthandle }" var="intenthandle">
											<tr>
												<td><input type="hidden" name="intenthandle" id="${intenthandle }" value="${intenthandle.remark }"/>${intenthandle.remark }</td>
												<td><input type="hidden" name="intenthandle" id="${intenthandle }" value="${intenthandle.create_time }"/>${intenthandle.create_time }</td>
												<td><input type="hidden" name="intenthandle" id="${intenthandle }" value="${intenthandle.username }"/>${intenthandle.username }</td>
											</tr>
										
										</c:forEach>
									</tbody>
								</table>
							  	 </div>
								</div>
							</div>	
			</grid:cell>
			<grid:cell>
				<a href="javascript:void(0);" id="queryUserOne" style="margin-right:10px;" class="graybtn1" order_id="${lockOrder.order_id}" is_work_custom="${lockOrder.is_work_custom}" county_code="${lockOrder.county_code}" allotType="allot" onclick="queryUserOne(this)">
					<span>订单分配</span>
				</a>
				<a href="javascript:void(0);" id="returnUserOne" style="margin-right:10px;" class="graybtn1" order_id="${lockOrder.order_id}" allotType="return" onclick="queryUserOne(this)">
					<span>订单回退</span>
				</a>
			</grid:cell>
		</grid:body>
	</grid:grid>
</form>
<div id="queryUserListDlg">
</div> 
<div id="queryCountyListDlg"></div>
<script type="text/javascript">
$(function (){
	Eop.Dialog.init({id:"queryUserListDlg",modal:true,title:"订单分配", width:"1100px"});
	Eop.Dialog.init({id:"queryCountyListDlg",modal:true,title:"订单分配", width:"1100px"});
	$("#queryUser").click(function(){
		  var len = $("[name='lockOrderChk']:checked").length;
		  if(len==0){
			  alert("请选择要分配的锁定订单");
			  return false;
		  }
		  if(window.confirm("确认分配选中的锁定订单吗?")){
			  var order_idArr = [];
			  $("[name='lockOrderChk']:checked").each(function(){
				 var order_id = $(this).val();
				 order_idArr.push(order_id);
			  });
			  var lockOrderIdStr = order_idArr.join(",");
			  checkFlowTrace(lockOrderIdStr);
		  }
	  });
	
	$("#resetBtn").click(function (){
		document.getElementById("district_code").value = "";
		document.getElementById("order_county_name").value = "";
	});
	
	$("#searchBtn").click(function(){
		$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!NewAllotOrderList.do");
		$("#serchform").submit();
		showMask(); 	
});
	
	$(".relaDiv1").mouseenter(function(){
        showOrg(this);   
	});
	
	$(".relaDiv1").mouseleave(function(){
        hideOrg(this);   
	});
	
});

//选择人员
function showUserSelect(lockOrderIdStr,allotType,is_work_custom,county_code){
	 var url= ctx+"/shop/admin/ordAuto!newQueryUser.do?ajax=yes&allotType="+allotType+"&lockOrderIdStr="+lockOrderIdStr+"&is_work_custom="+is_work_custom+"&county_code="+county_code;
	 console.log("订单分配url:"+url);
	 $("#queryUserListDlg").load(url,{},function(){});
	 Eop.Dialog.open("queryUserListDlg");
};


function queryUserOne(e){
	var lockOrderIdStr =e.getAttribute("order_id");
	var allotType = e.getAttribute("allotType");
	var is_work_custom = e.getAttribute("is_work_custom");
    var county_code = e.getAttribute("county_code");
	//document.getElementById("is_work_custom").value;
	console.log("is_work_custom:"+is_work_custom);
	  if(window.confirm("确认分配选中的锁定订单吗?")){
		  showUserSelect(lockOrderIdStr,allotType,is_work_custom,county_code);
	  }
};

$("#checkAlls").bind("click", function() {
	$("input[type=checkbox][name=lockOrderChk]").trigger("click");
});

//弹出选择县分
function querycountyId(){
	Eop.Dialog.open("queryCountyListDlg");
	var url= ctx+"/shop/admin/ordAuto!list.do?ajax=yes";
	$("#queryCountyListDlg").load(url,{},function(){});
};

function checkFlowTrace(lockOrderIdStr){	
	$.ajax({
		type : "post",
		async : false,
		url:"ordAuto!checkFlowTrace.do?ajax=yes",
		data : {
			"lockOrderIdStr" : lockOrderIdStr
		},
		dataType : "json",
		success : function(data) {
			if(data.result==0){
				showUserSelect(lockOrderIdStr,"allot");
			}else{
				alert(data.msg);
				 return;
			}
		}
	});
};  

function showOrg(item){
	var row = $(item).closest("tr");
	var orgDiv = $(row).find(".absoDiv1");
	var orgContent = $(row).find(".absoDivContent1");
	$(orgDiv).attr("style","display:block");

};

function hideOrg(item){
	var row = $(item).closest("tr");
	$(row).find(".absoDiv1").attr("style","display:none");
};

//县分由地市联动展示
function queryCountyByCity(e){
	//var city = e.value;//表单会把值带过去
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
    mask_msg.innerText = "正在查询,请稍后...";
    mask_bg.appendChild(mask_msg);
};

</script>