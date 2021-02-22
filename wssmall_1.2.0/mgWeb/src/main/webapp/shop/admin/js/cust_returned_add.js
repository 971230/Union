var CustInput = {
	init : function() {
		var self = this;
		$("#cust_form.validate").validate();
		$("#cust_form [name='submitBtn']").click(function() {
			var url = ctx+ "/shop/admin/custReturned!custReturned.do?ajax=yes";
			
			Cmp.ajaxSubmit('cust_form', '', url, {}, self.jsonBack,'json');
			
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
	
	CustInput.init();
});
