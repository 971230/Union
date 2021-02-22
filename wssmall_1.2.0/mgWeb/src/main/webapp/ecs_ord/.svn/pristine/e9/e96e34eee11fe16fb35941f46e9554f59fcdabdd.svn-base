<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<form id="updateForm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-collapse:separate; border-spacing:0px 10px;">
<tr>
<th>业务受理单号：</th><td><input type="text" class="top_up_ipt" name="updateParam.receiving_order_id"  id="receiving_order_id" value="${ updateParam.receiving_order_id}"/></td>
<th><span class="red">*</span>业务号码：</th><td><input type="text" class="top_up_ipt" name="updateParam.phone_num"  id="phone_num" value ="${ updateParam.phone_num}" required="true" /></td>
<th><span class="red">*</span>ICCID：</th><td><input type="text" class="top_up_ipt" name="updateParam.iccid"  id="iccid" value="${ updateParam.iccid}" required="true" /></td>
</tr>
<tr>
<input type="hidden" class="top_up_ipt"   id="order_id" value="${order_id }"/>
</tr>
</table>
<div align="center">
<input name="btn" type="button" id="do_btn" value=" 确    定   "  class="submitBtn" />
</div>
</form>



<script type="text/javascript">
$(function() {
});
$(document).ready(function(){
	$("#do_btn").click(function(){
		var order_id = $("#order_id").val();
		var phone_num = $("#phone_num").val();
		var iccid = $("#iccid").val();
		if(phone_num.length==0){
			alert("业务号码不能为空!");
			return;
		}
		
		if(iccid.length==0){
			alert("ICCID不能为空!");
			return;
		}else{
			if(iccid.length!=20){
				alert("ICCID位数必须是20位!");
				return;
			}
		}
		$('#updateForm').ajaxSubmit({
				type : "post",
				async : false,
				url:"orderFlowAction!nextStep.do?rule_id=170351621300000844&order_id="+order_id+"&ajax=yes",
				data : {
				},
				dataType : "json",
				success : function(data) {
					if(data.result==0){
						alert(data.message);
						Eop.Dialog.close("order_btn_event_dialog");
						window.location.href=ctx+ "/" + data.action_url;
						
					}else{
						alert(data.message);
					}
				}
			});
		});
		
	});
</script>
