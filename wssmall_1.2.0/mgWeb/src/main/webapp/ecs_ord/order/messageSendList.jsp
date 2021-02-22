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
		<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/orderIntentAction!smsAsysSend.do'>
			<input type="hidden" name="params.query_btn_flag" value="yes" />
			<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>发送号码：</th>
						<td><input type="text" name="params.bill_num" value="${params.bill_num}" style="width: 138px;" class="ipt_new"></td>

						<th>接收号码：</th>
						<td><input type="text" name="params.service_num" value="${params.service_num}" style="width: 138px;" class="ipt_new"></td>
						<th>创建时间：</th>
						<td>
						<input style="width: 150px;" type="text" id="create_start" name="params.create_start" value="${params.create_start }" readonly="readonly"
							class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
						<input style="width: 150px;" type="text" id="create_end" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</td>
					</tr>
					<tr>
						
						<th>短信内容：</th>
						<td><input type="text" name="params.sms_data" value="${params.sms_data }" style="width: 138px;" class="ipt_new"></td>
						
						<th>序列号：</th>
						<td><input type="text" name="params.sms_id" value="${params.sms_id }" style="width: 138px;" class="ipt_new"></td>
						<!-- 
						<th>内容长度：</th>
						<td>
							<select name="params.sms_flag" id="sms_flag" class="ipt_new" style="width: 145px;">
								<option value ="">请选择</option>
  								<option value ="1" ${'1'==params.sms_flag?'selected':'' }>长内容</option>
  								<option value ="0" ${'0'==params.sms_flag?'selected':'' }>短内容</option>
							</select>
						</td>
						 -->
						
						<th>发送时间：</th>
						<td><input style="width: 150px;" type="text"id="done_time_start" name="params.done_time_start"
							value="${params.done_time_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
						<input style="width: 150px;" type="text" id="done_time_end" name="params.done_time_end" value="${params.done_time_end }"
							readonly="readonly" class="ipt_new"onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
						</td>
					</tr>
					<tr>
						<th>短信类型：</th>
						<td>
							<select name="params.sms_type" id="sms_type" class="ipt_new" style="width: 145px;">
								<option value ="">请选择</option>
  								<option value ="sms" ${'sms'==params.sms_type?'selected':'' }>本网</option>
  								<option value ="cmc" ${'cmc'==params.sms_type?'selected':'' }>异网</option>
							</select>
						</td>
						<th>发送状态：</th>
						<td>
							<select name="params.send_status" id="status" class="ipt_new" style="width: 145px;">
								<option value ="">请选择</option>
  								<option value ="0" ${'0'==params.send_status?'selected':'' }>未发送</option>
  								<option value ="1" ${'1'==params.send_status?'selected':'' }>发送成功</option>
  								<option value ="2" ${'2'==params.send_status?'selected':'' }>发送失败</option>
							</select>
						</td>
						<th>订单编号：</th>
						<td>
							<input type="text" name="params.order_id" value="${params.order_id}" style="width: 138px;" class="ipt_new">
							<input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="查询" style="margin-right: 10px;" />
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
					<tr style="border: 1.5px solid #6B6B6B" border="1" cellpadding="0"
						cellspacing="0">
						<td class="tdOne" style="width: 12%">序列</td>
						<td class="tdOne" style="width: 8%">发送号码</td>
						<td class="tdOne" style="width: 8%">接收号码</td>
						<td class="tdOne" style="width: 8%">短信类型</td>
						<td class="tdOne" style="width: 8%">发送状态</td>
						<td class="tdOne" style="width: 10%">创建时间</td>
						<td class="tdOne" style="width: 10%">发送时间</td>
						<td class="tdOne" style="width: 10%">失败原因</td>
						<td class="tdOne" style="width: 10%">订单编号</td>
						<td class="tdOne" style="width: 8%">短信内容</td>
						<td class="tdOne" style="width: 8%">操作</td>
					</tr>
					</table>
					<grid:grid from="webpage" formId="serchform" >
						<grid:body item="message">
							<grid:cell style="width:12%">${message.sms_id}</grid:cell>
							<grid:cell style="width:8%">${message.bill_num}</grid:cell>
							<grid:cell style="width:8%">${message.service_num}</grid:cell>
							<!-- 
							<grid:cell style="width:8%">
								<c:if test="${message.sms_flag == 1 }">长内容</c:if>
								<c:if test="${message.sms_flag == 0 }">短内容</c:if>
							</grid:cell>
							 -->
							<grid:cell style="width:8%">${message.sms_type}</grid:cell>
							<grid:cell style="width:8%">
								<c:if test="${message.send_status == 1 }">发送成功</c:if>
								<c:if test="${message.send_status == 0 }">未发送</c:if>
								<c:if test="${message.send_status == 2 }">发送失败</c:if>
							</grid:cell>
							
							<grid:cell style="width:10%">${message.create_time}</grid:cell>
							<grid:cell style="width:10%">${message.done_time}</grid:cell>
							<grid:cell style="width:10%">${message.exception_desc}</grid:cell>
							<grid:cell style="width:10%">${message.order_id}</grid:cell>
							<grid:cell style="width:8%">
								<a href="javascript:void(0);" value="${message.sms_data}" name="queryReq" class="dobtn">查看</a>
							</grid:cell>
							<grid:cell style="width:8%">
								<c:if test="${(message.send_status == 0 ||  message.send_status == 2)&&message.send_num<=3}">
									<a href="javascript:void(0);" value="${message.sms_id}" name="smsCreateOrder" class="dobtn" >发送</a>
								</c:if>
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
					$("#serchform").attr("action",ctx+"/shop/admin/orderIntentAction!smsAsysSend.do").submit();
				});
		//处理
		$("a[name='smsCreateOrder']").bind("click",function(){
			var sms_id = $(this).attr("value");
			if(sms_id == null || sms_id==""){
				alert("异常：sms_id为空");
				return;
			}
			var url = "/shop/admin/orderIntentAction!smsAsysSendByBtn.do?ajax=yes&sms_id="+sms_id;
			Cmp.excute('', url, {}, jsonBack, 'json');
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
	
	function handle(sms_id){
		var url = "/shop/admin/orderIntentAction!beeActionIntenCreate.do?ajax=yes&order_id="+sms_id;
		Cmp.excute('', url, {}, jsonBack, 'json');
	}
	
	
	Eop.Dialog.init({id:"show_req",modal:true,title:"短信内容",width:'900px'});
	$("a[name='queryReq']").bind("click",
			function() {
				var divValue = $(this).attr("value");
				$('#show_req').html(divValue);
				Eop.Dialog.open("show_req");
			});
</script>
