<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
	<form method="post" id="sendSMSForm">
		<div>
			<div style="margin-top: 5px;">
				<table cellspacing="0" cellpadding="0" border="0" width="100%">
					<tbody>
						<tr>
							<th>添加内容：</th>
							<td>
								<textarea rows="5" cols="88" id="sms_template" name="sms_template" 
								value="" <c:if test="${type!='unicom'}">disabled placeholder="请选择模版"</c:if>
								></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="pop_btn" align="center">
				<a name="selectSMSForm" class="blueBtns"><span>选择模版</span></a>
				<a id="sendSMS" class="blueBtns"><span>发 送</span></a>
				<a id="closeSendSMS" class="blueBtns"><span>取 消</span></a>
			</div>
			<input id="order_id" name="order_id" type="hidden" value="${order_id}" />
			<input id="type" name="type" type="hidden" value="${type}" />
			<input id="ship_tel" name="ship_tel" type="hidden" value="${ship_tel}" />
		</div>
	</form>
</div>
<div id="selectSMSForm"></div> 
<script>
	$(function() {

		$("#sendSMS").click(function() {
			var sms_template = $("#sms_template").val();
			if(sms_template == null || sms_template == "") {
				alert("请输入/选择发送内容！");
				return;
			}
			if(window.confirm('你确定要发送吗？')) {} else {
				return;
			}
			var order_id = $("#order_id").val();
			var type = $("#type").val();
			var ship_tel = $("#ship_tel").val();

			var url = ctx + "/shop/admin/orderIntentAction!sendSMS.do?ajax=yes";
			var param = {
				"sms_template": sms_template,
				"order_id": order_id,
				"type": type,
				"ship_tel": ship_tel
			};

			$.ajax({
				url: url,
				type: "POST",
				dataType: "json",
				data: param,
				success: function(reply) {
					if(typeof(reply) != "undefined") {
						if("0" == reply["result"]) {
							alert(reply["message"]);
							Eop.Dialog.close("sendingSMSForm"); //关闭页面
							Eop.Dialog.close("order_btn_event_dialog"); //关闭页面-订单处理自动生成的id
						} else {
							alert(reply["message"]);
						}
					} else {
						alert("保存失败");
					}
				},
				error: function(msg) {
					alert("保存失败：" + msg);
				}
			});
		});
		$("#closeSendSMS").click(function() {
			Eop.Dialog.close("sendingSMSForm"); //关闭页面
			Eop.Dialog.close("order_btn_event_dialog"); //关闭页面
		});

		Eop.Dialog.init({
			id: "selectSMSForm",
			modal: true,
			title: "选择短信模版",
			width: '800px'
		});
		$("a[name='selectSMSForm']").bind("click", function() {
			var type = $("#type").val();
			var url = ctx + "/shop/admin/orderIntentAction!selectSMSForm.do?ajax=yes&type=" + type;
			$("#selectSMSForm").load(url, {}, function() {});
			Eop.Dialog.open("selectSMSForm");
		});

	});
</script>