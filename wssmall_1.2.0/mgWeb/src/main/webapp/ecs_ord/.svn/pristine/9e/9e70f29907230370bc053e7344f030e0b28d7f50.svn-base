<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
	<form method="post" id="addSMSForm" >
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th>添加内容：</th>
							<td>
								<textarea rows="5" cols="88" id="sms_template" name="sms_template" value=""></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="pop_btn" align="center">
				<a id="saveSMS" class="blueBtns"><span>保 存</span></a>
				<a id="closeAddSMSForm" class="blueBtns"><span>取 消</span></a>
			</div>
		</div>
	</form>
</div>

<script>
$(function() {
	$("#saveSMS").click(function() {
		var sms_template = $("#sms_template").val();
		if ( sms_template == null || sms_template == "") {
			alert("请选择输入模版内容！");
			return;
		}
		if(window.confirm('你确定要新增吗？')){}else{
               return;
		}
	    var url = ctx + "/shop/admin/orderIntentAction!addSMS.do?ajax=yes";
	    var param = {
	    		"sms_template":sms_template
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
	    				Eop.Dialog.close("addSMSForm");//关闭页面
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
	
	$("#closeAddSMSForm").click(function() {
		Eop.Dialog.close("addSMSForm");//关闭页面
	}); 
	
});
</script>
