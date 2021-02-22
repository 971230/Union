<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<style>.div1{border:1px solid #FF7F00 ;background-color: #E9C2A6}</style>
<style>.searchformDiv1{ background:#f2f2f2; border-bottom:1px solid #cdcdcd;border-right:1px solid #cdcdcd; border-top:1px solid #f9f9f9; padding:5px;}
.searchformDiv1 table{}
.searchformDiv1 table th{ text-align:right; padding-right:3px; vertical-align:middle; color:#666; width:100px;}
.searchformDiv1 table td{ padding-top:3px; padding-bottom:3px; vertical-align:middle;}</style>
<div class="div1"><spen>1、修改订单信息时，营业款不能为空，物流单号可以修改为空</spen></div>
<div>
<form method="post" id="serchFlowform1" action='/shop/admin/noMatchFlowAction!queryNoMatchFlowList.do' ajax="yes">
 <div class="searchformDiv1">
<table>
<tr>
<th>用户号码：</th>
<td><input type="text" value="${moneyAuditBaseData.phone_num }" disabled="disabled"></td>
<th>订单营业款：</th>
<td><input type="text" value="${moneyAuditBaseData.busi_money }" disabled="disabled"></td>
<th>bss/cbss营业款：</th>
<td><input type="text" value="${moneyAuditBaseData.busi_money_sum }" disabled="disabled"></td>
<th>商城实收：</th>
<td><input type="text" value="${moneyAuditBaseData.paymoney }" disabled="disabled"></td>
</tr>
<tr>
<th>订单号：</th>
<td><input type="text" value="${moneyAuditBaseData.order_id }" disabled="disabled"></td>
<th>商城来源：</th>
<td><input type="text" value="${moneyAuditBaseData.order_from }" disabled="disabled"></td>
<th>商品名称：</th>
<td><input type="text" value="${moneyAuditBaseData.goodsName }" disabled="disabled"></td>
<th>支付类型：</th>
<td><input type="text" value="${moneyAuditBaseData.pay_type }" disabled="disabled"></td>
</tr>
<tr>
<th>订单类型：</th>
<td><input type="text" value="${moneyAuditBaseData.order_type_return }" disabled="disabled"></td>
<th>营业款稽核状态：</th>
<td><input type="text" value="${moneyAuditBaseData.audit_busi_money_status }" disabled="disabled"></td>
<th>实收款稽核状态：</th>
<td><input type="text" value="${moneyAuditBaseData.audit_receive_money_status }" disabled="disabled"></td>
<th>手机串号：</th>
<td><input type="text" value="${moneyAuditBaseData.terminal_num }" disabled="disabled"></td>
</tr>
<tr>
<th>发票号：</th>
<td><input type="text"  value="${moneyAuditBaseData.invoice_no }" disabled="disabled"></td>
<th>物流单号：</th>
<td><input type="text"  value="${moneyAuditBaseData.logi_no }" disabled="disabled"></td>
<th>UDP：</th>
<td><input type="text"  value="${moneyAuditBaseData.audit_udp }" disabled="disabled"></td>
<th>稽核备注：</th>
<td><input type="text"  value="${moneyAuditBaseData.audit_remark }" disabled="disabled"></td>
</tr>

</table>
</div>
</form>

<div class="grid_n_div">
             <h2><a href="javascript:void(0);" class="openArrow"></a>流水信息</h2> 
              	<div class="grid_n_cont">
					<div class="grid" >
						
								<grid:grid from="auditBusi"  formId="serchFlowForm" >
									<grid:header>
	    								<grid:cell width="5%" >平台</grid:cell>
	    								<grid:cell width="10%">地区</grid:cell>
										<grid:cell width="15%">流水号</grid:cell>
										<grid:cell width="10%">业务号码</grid:cell>
										<grid:cell width="10%">业务种类</grid:cell>
										<grid:cell width="10%">营业收入</grid:cell>
										<grid:cell width="10%">时间</grid:cell>
										<grid:cell width="10%">操作员</grid:cell>
										<grid:cell width="10%">操作</grid:cell>
									</grid:header>
									<grid:body item="auditBusiness">
										<grid:cell>${auditBusiness.data_from }</grid:cell>
										<grid:cell>${auditBusiness.area }</grid:cell>
										<grid:cell>${auditBusiness.serial_number }</grid:cell>
										<grid:cell>${auditBusiness.phone_number }</grid:cell>
										<grid:cell>${auditBusiness.business_type }</grid:cell>
										<grid:cell>￥${auditBusiness.busi_money }</grid:cell>
										<grid:cell>${auditBusiness.operation_time }</grid:cell>
										<grid:cell>${auditBusiness.operator }</grid:cell>
										<grid:cell><a href="javascript:void(0);" order_id="${auditBusiness.order_id}" audit_bss_id="${auditBusiness.audit_bss_id }" 
										onclick="updateBusi(this)">删除</a></grid:cell>
									</grid:body>
								</grid:grid>
							
					</div>
                </div>
            </div>
            <div id="ss1" class="grid_n_div" style="display:none">
             <h2><a href="javascript:void(0);" class="openArrow"></a>实收金额信息</h2> 
              	<div class="grid_n_cont" >
					<div class="grid" >
						
								<grid:grid from="moneyForOrderId"  formId="serchMoneyForm" >
									<grid:header>
	    								<grid:cell width="20%">订单号</grid:cell>
										<grid:cell width="25%">支付宝交易号</grid:cell>
										<grid:cell width="20%">支付渠道</grid:cell>
										<grid:cell width="10%">金额</grid:cell>
										<grid:cell width="20%">入账时间</grid:cell>
									</grid:header>
									<grid:body item="moneyForOrderid">
										<grid:cell>${moneyForOrderid.order_id }</grid:cell>
										<grid:cell>${moneyForOrderid.activity_code }</grid:cell>
										<grid:cell>${moneyForOrderid.pay_channel }</grid:cell>
										<grid:cell>￥${moneyForOrderid.receive_money }</grid:cell>
										<grid:cell>${moneyForOrderid.receive_fee_date }</grid:cell>
									</grid:body>
								</grid:grid>
							
					</div>
                </div>
            </div>
            <div id="ss2" class="grid_n_div" style="display:none">
             <h2><a href="javascript:void(0);" class="openArrow"></a>实收金额信息</h2> 
              	<div class="grid_n_cont">
					<div class="grid" >
						
								<grid:grid from="moneyForUDP"  formId="serchMoneyForm">
									<grid:header>
	    								<grid:cell width="10%">订单号</grid:cell>
										<grid:cell width="15%">UDP号</grid:cell>
										<grid:cell width="10%">支付机构</grid:cell>
										<grid:cell width="10%">支付银行</grid:cell>
										<grid:cell width="20%">业务类型</grid:cell>
										<grid:cell width="10%">金额</grid:cell>
										<grid:cell width="20%">支付时间</grid:cell>
										<grid:cell width="20%">品牌</grid:cell>
									</grid:header>
									<grid:body item="moneyForudp">
										<grid:cell>${moneyForudp.order_id }</grid:cell>
										<grid:cell>${moneyForudp.audit_udp }</grid:cell>
										<grid:cell>${moneyForudp.payprovidername }</grid:cell>
										<grid:cell>${moneyForudp.pay_bank }</grid:cell>
										<grid:cell>${moneyForudp.busi_type }</grid:cell>
										<grid:cell>￥${moneyForudp.money }</grid:cell>
										<grid:cell>${moneyForudp.pay_time }</grid:cell>
										<grid:cell>${moneyForudp.brand }</grid:cell>
									</grid:body>
								</grid:grid>
							
					</div>
                </div>
            </div>
            <div id="ss3" class="grid_n_div" style="display:none">
             <h2><a href="javascript:void(0);" class="openArrow"></a>实收金额信息</h2> 
              	<div class="grid_n_cont">
					<div class="grid" >
						
								<grid:grid from="moneyForLogiNo"  formId="serchMoneyForm">
									<grid:header>
	    								<grid:cell width="10%">订单号</grid:cell>
										<grid:cell width="15%">运单号</grid:cell>
										<grid:cell width="10%">支付机构</grid:cell>
										<grid:cell width="10%">支付银行</grid:cell>
										<grid:cell width="20%">业务类型</grid:cell>
										<grid:cell width="10%">金额</grid:cell>
										<grid:cell width="20%">支付时间</grid:cell>
										<grid:cell width="20%">品牌</grid:cell>
									</grid:header>
									<grid:body item="moneyForLogino">
										<grid:cell>${moneyForLogino.order_id }</grid:cell>
										<grid:cell>${moneyForLogino.logi_no }</grid:cell>
										<grid:cell>顺丰</grid:cell>
										<grid:cell></grid:cell>
										<grid:cell></grid:cell>
										<grid:cell>￥${moneyForLogino.cod_amoun }</grid:cell>
										<grid:cell></grid:cell>
										<grid:cell></grid:cell>
									</grid:body>
								</grid:grid>
							
					</div>
                </div>
            </div>
            <div class="grid_n_div" id="changeDiv" style="display:none">
             <h2>信息修改</h2> 
              	<div class="searchformDiv1">
              	<table>
				<tr>
				<th>订单营业款：</th>
				<td>
				<%-- <html:selectdict  id="data_from"   curr_val="${data_from}"   attr_code="DIC_BSS_OR_CBSS" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
				<br> --%>
				<input type="text" value="${moneyAuditBaseData.busi_money }" id="busi_money">
				</td>
				<th>物流单号：</th>
				<td><input type="text" value="${moneyAuditBaseData.logi_no }" id="logi_no"></td>
				<th>订单类型：</th>
				<td>
				<html:selectdict  id="order_type"   curr_val="${moneyAuditBaseData.order_type_return }"   attr_code="DC_ORDER_NEW_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
				</td>
				<th>支付类型：</th>
				<td>
				<html:selectdict  id="paytype"   curr_val="${moneyAuditBaseData.pay_type}"   attr_code="DIC_PAY_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
				</td>
				</tr>
				<tr>
				<th>UDP号：</th>
				<td><input type="text" value="${moneyAuditBaseData.audit_udp }" id="udp_no"></td>
				<th>稽核备注：</th>
				<td><input type="text" value="${moneyAuditBaseData.audit_remark }" id="audit_note"></td>
				</tr>
				</table>
				<div align="right";>
				<input style="width:60px;"  class="comBtn schConBtn" id="saveChangeBtn"
					value="提交" />
				<input style="width:60px;"  class="comBtn schConBtn" id="cancelBtn"
					value="取消" />	
				</div>
				</div>
					
					
		
		</div>
		<div class="grid_n_div" id="flowDiv" style="display:none">
             <h2>添加流水号</h2> 
              	<div class="searchformDiv1">
              	<table>
				<tr>
				<th>流水号：</th>
				<td>
				<select style="width:100px" id="data_from">
  				<option value ="BSS">BSS</option>
  				<option value ="CBSS">CBSS</option>
				</select>
				<select style="width:100px" id="is_minus">
  				<option value ="1">开户</option>
  				<option value ="0">返销</option>
				</select>
				<input type="text" id="serial_number">
				</td>
				
				<td>
				<div align="right";>
				<input style="width:60px;"  class="comBtn schConBtn" id="saveFlowBtn"
					value="添加" />
				<input style="width:60px;"  class="comBtn schConBtn" id="cancelFlowBtn"
					value="取消" />	
				</div>
				</td>
				</tr>
				
				</table>
				
				</div>
					
					
		
		</div>
            
            <div  align="center">
            <tr align="center">
			<th></th>
			<td  align="center">
			<input style="width:60px;"  class="comBtn schConBtn" id="changeBtn"
					value="修改信息" />
			<input style="width:100px;"  class="comBtn schConBtn" id="addFlowBtn"
					value="添加流水信息" />	
			<input style="width:100px;"  class="comBtn schConBtn" id="auditBusiBtn" onclick="auditBusiMoneyByOrderId('${moneyAuditBaseData.order_id }')"
					value="营业款稽核" />
			<input style="width:100px;"  class="comBtn schConBtn" id="auditReceiveBtn"  onclick="auditReceiveByOrderId('${moneyAuditBaseData.order_id }')"
					value="实收款稽核" />					
			</td>
		</tr>
		</div>
		
		
</div>

<div><input type="hidden" id="order_id" value="${moneyAuditBaseData.order_id }"></div>
<script type="text/javascript">
$(function(){
	var audit_related_field = '${moneyAuditBaseData.audit_related_field}';
	if(audit_related_field=="order_id"){
		document.getElementById("ss1").style.display="block";
	}else if(audit_related_field=="udp"){
		document.getElementById("ss2").style.display="block";
	}else if(audit_related_field=="logi_no"){
		document.getElementById("ss3").style.display="block";
	}
	$("#changeBtn").click(function(){
		document.getElementById("changeDiv").style.display="block";
	});
	$("#cancelBtn").click(function(){
		document.getElementById("changeDiv").style.display="none";
	});
	
	$("#saveChangeBtn").click(function(){
		var order_id = $("#order_id").val();
		var busi_money = $("#busi_money").val();
		var logi_no = $("#logi_no").val();
		var order_type = $("#order_type").val();
		var paytype = $("#paytype").val();
		var udp_no = $("#udp_no").val();
		var audit_note = $("#audit_note").val();
		if(busi_money==null||busi_money.size==0||busi_money==""){
			alert("营业款不能为空");
		}else{
			var url= "/shop/admin/moneyAuditAction!updateOrder.do?ajax=yes&order_id="+order_id+"&busi_money="+busi_money
					+"&logi_no="+logi_no+"&order_type="+order_type+"&paytype="+paytype+"&udp_no="+udp_no+"&audit_note="+audit_note;
			 var updateBack = function(reply){
				 if(reply.result==0){
						alert("操作成功");
						var url= ctx+"/shop/admin/moneyAuditAction!manualAudit.do?ajax=yes&order_id="+order_id;
						  
					    $("#manualAuditDlg").load(url,{},function(){});
					 }else{
						 alert(reply.message);
					 }
			 }; 
			Cmp.ajaxSubmit('serchFlowform1', 'manualAuditDlg', url, {}, updateBack, 'json');
			
		}
		
	});
	$("#addFlowBtn").click(function(){
		document.getElementById("flowDiv").style.display="block";
	});
	$("#cancelFlowBtn").click(function(){
		document.getElementById("flowDiv").style.display="none";
	});
	$("#saveFlowBtn").click(function(){
		var order_id = $("#order_id").val();
		var data_from = $("#data_from").val();
		var is_minus = $("#is_minus").val();
		var serial_number = $("#serial_number").val();
		var url= "/shop/admin/moneyAuditAction!addFlow.do?ajax=yes&data_from="+data_from+"&is_minus="+is_minus
		+"&serial_number="+serial_number+"&order_id="+order_id;
 		var updateBack = function(reply){
	 	if(reply.result==0){
			alert("操作成功");
			var url= ctx+"/shop/admin/moneyAuditAction!manualAudit.do?ajax=yes&order_id="+order_id;
			  
		    $("#manualAuditDlg").load(url,{},function(){});
		 }else{
			 alert(reply.message);
		 }
 }; 
Cmp.ajaxSubmit('serchFlowform1', 'manualAuditDlg', url, {}, updateBack, 'json');
	});
});
function updateBusi(e){
	var order_id  = e.getAttribute("order_id");
	var audit_bss_id  = e.getAttribute("audit_bss_id");
	var url= "/shop/admin/moneyAuditAction!updateBusi.do?ajax=yes&order_id="+order_id+"&audit_bss_id="+audit_bss_id;
	 var updateBack = function(reply){
		 if(reply.result==0){
				alert("操作成功");
				
			 }else{
				 alert(reply.message);
			 }
	 }; 
	Cmp.ajaxSubmit('serchFlowform1', 'manualAuditDlg', url, {}, updateBack, 'json');
	var url= ctx+"/shop/admin/moneyAuditAction!manualAudit.do?ajax=yes&order_id="+order_id;
	  
    $("#manualAuditDlg").load(url,{},function(){});
}

function auditBusiMoneyByOrderId(order_id){
	$.ajax({
		type : "post",
		async : false,
		url:"moneyAuditAction!auditBusiMoneyByOrderId.do?ajax=yes",
		data : {
			"order_id" : order_id
		},
		dataType : "json",
		success : function(data) {
			 alert(data.msg);
		}
	});
}
function auditReceiveByOrderId(order_id){
	$.ajax({
		type : "post",
		async : false,
		url:"moneyAuditAction!auditReceiveByOrderId.do?ajax=yes",
		data : {
			"order_id" : order_id
		},
		dataType : "json",
		success : function(data) {
			 alert(data.msg);
		}
	});
}
</script>

