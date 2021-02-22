<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
		$("a[name='selectSMS']").click(function() {

			var len = $("[name='orderidArray']:checked").length;
			if(len != 1) {
				alert("请选择一个模版");
				return;
			}
			$("[name='orderidArray']:checked").each(
				function() {
					var sms_template = $(this).val();
					$("#sms_template").val(sms_template);
					Eop.Dialog.close("selectSMSForm"); //关闭页面
				});

		});

		$("a[name='closeSelectSMS']").click(function() {
			Eop.Dialog.close("selectSMSForm"); //关闭页面
		});

	});
	(function($) {
		$.fn.aramsDiv = function() {
			var $this = $(this);
			$this.bind("mouseout", function() {});
			$(this).bind("mouseover", function() {});
		};
	})(jQuery);
</script>
<div>
	<div class="searchBx">
		<!-- <a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
		<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display: none;">展开</a> -->
		<form action="/shop/admin/orderIntentAction!selectSMSForm.do" method="post" id="selectSMSForm2" >
			<%-- <table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
				<tbody>
					<tr>
						<th>模版号：</th>
						<td>
							<input type="text" name="sms_id" value="${sms_id}" style="width: 145px;" class="ipt_new">
						</td>
						<th>内容：</th>
						<td>
							<input type="text" name="sms_template" value="${sms_template}" style="width: 145px;" class="ipt_new">
						</td>
						<th></th>
						<td>
							<a href="javascript:void(0);" id="querysmsTemplateForm" class="dobtn" style="margin-left: 20px;">查 询</a>
						</td>
					</tr>
				</tbody>
			</table> --%>
			<input id="type" name="type" type="hidden" value="${type}" />
		</form>
	</div>
	<div>
		<form id="gridform" class="grid">
			<grid:grid from="webpage" formId="selectSMSForm2" ajax="yes" action="/shop/admin/orderIntentAction!selectSMSForm.do">
				<grid:header>
					<grid:cell> </grid:cell>
					<grid:cell>模版号</grid:cell>
					<grid:cell>内容</grid:cell>
					<grid:cell>类别</grid:cell>
				</grid:header>
				<grid:body item="template">
					<grid:cell style="width: 10%;">
						<input type="radio" name="orderidArray" value="${template.sms_template}" />
					</grid:cell>
					<grid:cell style="width: 20%;">${template.sms_id}</grid:cell>
					<grid:cell style="width: 60%;">${template.sms_template}</grid:cell>
					<grid:cell style="width: 10%;">${template.sms_level}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
	<div>
		<div class="pop_btn" align="center">
			<a name="selectSMS" class="blueBtns"><span>选 择</span></a>
			<a name="closeSelectSMS" class="blueBtns"><span>取 消</span></a>
		</div>
	</div>
</div>