/**
 * 合约机申请
 * @type 
 */
var ContractApply = {
	init : function() {
		var self = this;
		$("#apply_form.validate").validate();
		$("#apply_form [name='submitBtn']").click(function() {
			var url = ctx+ "/shop/admin/paymentn!cancel_pay.do?ajax=yes";
			Cmp.ajaxSubmit('apply_form', '', url, {}, self.jsonBack,'json');
		})
	},
	jsonBack : function(responseText) { // json回调函数
		if (responseText.result == 1) {
			alert(responseText.message);
			Eop.Dialog.close("order_w_dialog");
			window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}
	}
}
$(function() {
	ContractApply.init();
});