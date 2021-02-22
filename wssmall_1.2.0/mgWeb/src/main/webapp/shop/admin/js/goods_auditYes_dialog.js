var GoodsAuditYes = {
	init : function() {
		var self = this;
		$("#goods_auditYes_form.validate").validate();
		$("#goods_auditYes_form [name='submitBtn']").click(function() {
			var url = ctx+ "/shop/admin/usage!editByGoodsid.do?ajax=yes";
			Cmp.ajaxSubmit('goods_auditYes_form', '', url, {}, self.jsonBack,'json');
		})
	},
	jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			//alert(responseText.message);
			Eop.Dialog.close("auditYes_dialog");
			alert("修改成功！");
			location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}
	}
	
}
$(function() {
	GoodsAuditYes.init();
	$('#cancleBtn').click(function(){
		Eop.Dialog.close("auditYes_dialog");
	});
});