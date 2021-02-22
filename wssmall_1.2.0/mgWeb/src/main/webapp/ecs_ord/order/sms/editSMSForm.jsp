<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
	<form method="post" id="editSMSForm" >
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th>修改内容：</th>
							<td>
								<textarea rows="5" cols="88" id="sms_template_new" name="sms_template_new" 
								value="${smsDetail.sms_template}" <c:if test="${smsDetail.status=='禁用'}">disabled</c:if>>${smsDetail.sms_template}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<input id="sms_id" type="hidden" value="${smsDetail.sms_id}" />
			<input id="sms_template" type="hidden" value="${smsDetail.sms_template}" />
			<div class="pop_btn" align="center">
				<c:if test="${smsDetail.status=='正常'}">
					<a id="editSMS" class="blueBtns"><span>保 存</span></a>
					<a id="banSMS" class="blueBtns"><span>禁 用</span></a>
				</c:if>
				<c:if test="${smsDetail.status=='禁用'}">
					<a id="pickSMS" class="blueBtns"><span>启 用</span></a>
				</c:if>
				<a id="deleteSMS" class="blueBtns"><span>删 除</span></a>
				<a id="closeEditSMSForm" class="blueBtns"><span>取 消</span></a>
			</div>
		</div>
	</form>
</div>

<script>
$(function() {
	$("#editSMS").click(function() {
		var sms_template_new = $("#sms_template_new").val();
		if ( sms_template_new == null || sms_template_new == "") {
			alert("请选择输入内容！");
			return;
		}
		var sms_template = $("#sms_template").val();
		if(sms_template==sms_template_new){
			alert("内容未修改！");
			return;
		}
		if(window.confirm('你确定要修改吗？')){}else{
               return;
		}
		var sms_id = $("#sms_id").val();
	    var url = ctx + "/shop/admin/orderIntentAction!editSMS.do?ajax=yes";
	    var param = {
	    		"sms_template":sms_template,
	    		"sms_id":sms_id,
	    		"sms_template_new":sms_template_new
	    };
	    $.ajax({
	     	url:url,
	     	type: "POST",
	     	dataType:"json",
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["result"]){
	     				alert(reply["message"]);
	    				Eop.Dialog.close("editSMSForm");//关闭页面
	    				$("#smsTemplateForm").attr("action", ctx + "/shop/admin/orderIntentAction!smsTemplateForm.do").submit();
	     			}else{
	     				alert(reply["message"]);
	     			}
	     		}else{
	     			alert("保存失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("保存失败："+msg);
	     	}
		});
	}); 
	
	$("#banSMS").click(function() {
		if(window.confirm('你确定要禁用吗？')){}else{
               return;
		}
		var sms_id = $("#sms_id").val();
	    var url = ctx + "/shop/admin/orderIntentAction!banSMS.do?ajax=yes";
	    var param = {
	    		"sms_id":sms_id
	    };
	    $.ajax({
	     	url:url,
	     	type: "POST",
	     	dataType:"json",
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["result"]){
	     				alert(reply["message"]);
	    				Eop.Dialog.close("editSMSForm");//关闭页面
	    				$("#smsTemplateForm").attr("action", ctx + "/shop/admin/orderIntentAction!smsTemplateForm.do").submit();
	     			}else{
	     				alert(reply["message"]);
	     			}
	     		}else{
	     			alert("禁用失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("禁用失败："+msg);
	     	}
		});
	}); 
	
	$("#pickSMS").click(function() {
		if(window.confirm('你确定要启用吗？')){}else{
               return;
		}
		var sms_id = $("#sms_id").val();
	    var url = ctx + "/shop/admin/orderIntentAction!pickSMS.do?ajax=yes";
	    var param = {
	    		"sms_id":sms_id
	    };
	    $.ajax({
	     	url:url,
	     	type: "POST",
	     	dataType:"json",
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["result"]){
	     				alert(reply["message"]);
	    				Eop.Dialog.close("editSMSForm");//关闭页面
	    				$("#smsTemplateForm").attr("action", ctx + "/shop/admin/orderIntentAction!smsTemplateForm.do").submit();
	     			}else{
	     				alert(reply["message"]);
	     			}
	     		}else{
	     			alert("启用失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("启用失败："+msg);
	     	}
		});
	});
	
	$("#deleteSMS").click(function() {
		if(window.confirm('你确定要删除吗？')){}else{
               return;
		}
		var sms_id = $("#sms_id").val();
	    var url = ctx + "/shop/admin/orderIntentAction!deleteSMS.do?ajax=yes";
	    var param = {
	    		"sms_id":sms_id
	    };
	    $.ajax({
	     	url:url,
	     	type: "POST",
	     	dataType:"json",
	     	data:param,
	     	success:function(reply){
	     		if(typeof(reply) != "undefined"){
	     			if("0" == reply["result"]){
	     				alert(reply["message"]);
	    				Eop.Dialog.close("editSMSForm");//关闭页面
	    				$("#smsTemplateForm").attr("action", ctx + "/shop/admin/orderIntentAction!smsTemplateForm.do").submit();
	     			}else{
	     				alert(reply["message"]);
	     			}
	     		}else{
	     			alert("启用失败");
	     		}
	     	},
	     	error:function(msg){
	     		alert("启用失败："+msg);
	     	}
		});
	}); 
	
	$("#closeEditSMSForm").click(function() {
		Eop.Dialog.close("editSMSForm");//关闭页面
	}); 
	
});
</script>
