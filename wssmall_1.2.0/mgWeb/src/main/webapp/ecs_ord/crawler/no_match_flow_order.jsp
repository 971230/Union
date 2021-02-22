<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<div>
<form method="post" id="serchFlowform1" action='/shop/admin/noMatchFlowAction!queryNoMatchFlowList.do' ajax="yes">
 <div class="searchformDiv">
	 <table>
			<tr>
			<th>用户号码：</th>
			<td><input type="text" class="ipttxt"  id="user_phone_num"  value="${user_phone_num}"/></td>
			<th>订单号：</th>
			<td><input type="text" class="ipttxt"  id="order_id"  value="${order_id}"/></td>
			<th>bss营业款：</th>
			<td><input type="text" class="ipttxt"  id="money"  value="${money}"/></td>
			<th>稽核备注：</th>
			<td><input type="text" class="ipttxt"  id="audit_note"  value="${audit_note}"/></td>
			</tr>
			
		
	 </table>
	 
           
  </div>
</form>
<tr>
			<div class="grid_n_div">
             <h2><a href="javascript:void(0);" class="openArrow"></a>流水信息</h2> 
              	<div class="grid_n_cont">
					<div class="grid" >
						
								<grid:grid from="auditBusi"  formId="serchFlowForm">
									<grid:header>
	    								<grid:cell width="5%" >平台</grid:cell>
										<grid:cell width="10%">流水号</grid:cell>
										<grid:cell width="10%">业务号码</grid:cell>
										<grid:cell width="10%">业务种类</grid:cell>
										<grid:cell width="10%">营业收入</grid:cell>
										<grid:cell width="10%">时间</grid:cell>
										<grid:cell width="10%">操作员</grid:cell>
									</grid:header>
									<grid:body item="auditBusiness">
										<grid:cell>${auditBusiness.data_from}</grid:cell>
										<grid:cell>${auditBusiness.serial_number}</grid:cell>
										<grid:cell>${auditBusiness.phone_number}</grid:cell>
										<grid:cell>${auditBusiness.business_type}</grid:cell>
										<grid:cell>￥${auditBusiness.busi_money}</grid:cell>
										<grid:cell>${auditBusiness.operation_time}</grid:cell>
										<grid:cell>${auditBusiness.operator}</grid:cell>
									</grid:body>
								</grid:grid>
							
					</div>
                </div>
            </div>
            </tr>
             <div  align="center">
            <tr align="center">
			<th></th>
			<td  align="center">
			<input style="width:60px;"  class="comBtn schConBtn" id="selorderBtn"
					value="订单查询" />
			<input style="width:100px;"  class="comBtn schConBtn" id="submitFlowBtn"
					value="无订单流水提交" />		
			</td>
		</tr>
		</div>
		<tr>
			
					<div class="grid" >
						
								<grid:grid from="webpage"  formId="serchOrderForm">
									<grid:header>
	    								<grid:cell width="5%" >选项</grid:cell>
										<grid:cell width="10%">订单号</grid:cell>
										<grid:cell width="10%">地市</grid:cell>
										<grid:cell width="10%">订单类型</grid:cell>
										<grid:cell width="15%">商品名称</grid:cell>
										<grid:cell width="10%">商城实收</grid:cell>
										<grid:cell width="10%">订单营业款</grid:cell>
										<grid:cell width="10%">客户姓名</grid:cell>
										<grid:cell width="10%">商城号码</grid:cell>
									</grid:header>
									<grid:body item="map">
										<grid:cell><input name="lockOrderChk" type="checkbox" value="${map.order_id}"></grid:cell>
										<grid:cell>${map.order_id }</grid:cell>
										<grid:cell>${map.city }</grid:cell>
										<grid:cell>${map.order_type }</grid:cell>
										<grid:cell>${map.goods_name }</grid:cell>
										<grid:cell>${map.paymoney }</grid:cell>
										<grid:cell>${map.busi_money }</grid:cell>
										<grid:cell>${map.uname }</grid:cell>
										<grid:cell>${map.phone_num }</grid:cell>
									</grid:body>
								</grid:grid>
            </div>
            </tr>
            <div  align="center">
            <tr align="center">
			<th></th>
			<input style="width:80px;"  class="comBtn schConBtn" id="addFlowBtn"
					value="添加流水" />		
			</td>
		</tr>
		</div>
</div>
<input type="hidden" value="${operation_time }" id="operation_time">
<input type="hidden" value="${serial_number }" id="serial_number">
<input type="hidden" value="${phone_number }" id="phone_number">
<input type="hidden" value="${busi_money }" id="busi_money">
<input type="hidden" value="${business_type }" id="business_type">
<input type="hidden" value="${operator }" id="operator">
<input type="hidden" value="${data_from }" id="data_from">
</div> 
<script type="text/javascript">
$(function() {
	$("#selorderBtn").click(function(){
			var order_id = $("#order_id").val();
			var url = "/shop/admin/noMatchFlowAction!queryOrder.do?ajax=yes&order_id="+order_id
			+"&serial_number="+$("#serial_number").val()+"&phone_number="+$("#phone_number").val()+"&busi_money="+$("#busi_money").val()
				+"&business_type="+$("#business_type").val()+"&operator="+$("#operator").val()+"&data_from="+$("#data_from").val()
					+"&user_phone_num="+${user_phone_num}+"&money="+${money}+"&operation_time="+$("#operation_time").val();
			Cmp.ajaxSubmit('serchFlowform1','orderDlg',url,
					{},function(){});
		});
	$("#addFlowBtn").click(function(){
		var order_id = $("#order_id").val();
		var audit_note = $("#audit_note").val();
		var serial_number = $("#serial_number").val();
		$.ajax({
			type : "post",
			async : false,
			url: "/shop/admin/noMatchFlowAction!addFlow.do?ajax=yes",
			data : {
				"order_id":order_id,
				"audit_note":audit_note,
				"serial_number":serial_number
			},
			dataType : "json",
			success : function(data) {
				if(data.result==0){
					alert("操作成功！");
					Eop.Dialog.close("orderDlg");
					location.href = "/shop/admin/noMatchFlowAction!queryNoMatchFlowList.do";
				}else{
					alert("操作失败！");
				}
			}
		});
		
	});
	$("#submitFlowBtn").click(function(){
		var serial_number = $("#serial_number").val();
		$.ajax({
			type : "post",
			async : false,
			url: "/shop/admin/noMatchFlowAction!submitFlow.do?ajax=yes",
			data : {
				"audit_note":audit_note,
				"serial_number":serial_number
			},
			dataType : "json",
			success : function(data) {
				if(data.result==0){
					alert("操作成功！");
					Eop.Dialog.close("orderDlg");
					location.href = "/shop/admin/noMatchFlowAction!queryNoMatchFlowList.do";
				}else{
					alert("操作失败！");
				}
			}
		});
	});
});


</script>