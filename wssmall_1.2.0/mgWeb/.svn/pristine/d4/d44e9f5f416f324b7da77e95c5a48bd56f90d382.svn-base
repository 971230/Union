/**
 * 合约机审核
 * @type 
 */
var ContractAudit = {
	init : function() {
		var self = this;
		$("#contract_audit_form.validate").validate();
		$("#contract_audit_form [name='submitBtn']").click(function() {
			var url = ctx+ "/shop/admin/contract!order.do?ajax=yes";
			Cmp.ajaxSubmit('contract_audit_form', '', url, {}, self.jsonBack,'json');
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
	
	ContractAudit.init();
});