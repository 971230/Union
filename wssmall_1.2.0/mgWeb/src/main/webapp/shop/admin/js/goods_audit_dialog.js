/**
 * 商品发布审核
 * @type 
 */
var GoodsAudit = {
	init : function() {
		var self = this;
		$("#goods_audit_form.validate").validate();
		$("#goods_audit_form [name='submitBtn']").click(function() {
			
			if(!$("[attr='deal_state']").val())
			{
				alert("处理状态不能为空");
				
				return;
				
				
			}
			
			var url = ctx+ "/shop/admin/goods_audit!goodsAuditResult.do?ajax=yes";
			Cmp.ajaxSubmit('goods_audit_form', '', url, {}, self.jsonBack,'json');
		})
	},
	jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			alert(responseText.message);
			Eop.Dialog.close("audit_w_dialog");
			location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}
	}
}
$(function() {
	
	GoodsAudit.init();
});