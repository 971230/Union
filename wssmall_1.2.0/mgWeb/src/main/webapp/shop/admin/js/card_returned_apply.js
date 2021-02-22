var GoodsReturnedApply = {
	
	init:function(){
		 var self = this;
		
		$("#goods_returned_apply_form.validate").validate();
		$("#goods_returned_apply_form [name='submitBtn']").click(function() {
			var len =$("[name='card_checkbox']:checked").length;
			if(len == 0) {
				alert("请选择退货卡");
				return;
			}
			
			var card_idArr = [];
			$("[name='card_checkbox']:checked").each(function(){
				var card_id = $(this).val();
				card_idArr.push(card_id);
			});
			if(!confirm("确认退掉选择卡？"))
				return;
			
			var card_idstr =card_idArr.join(","); 
			var url = ctx + "/shop/admin/goodsApply!chargeReturnedApply.do?ajax=yes&returned_ids=" + card_idstr;
			Cmp.ajaxSubmit('goods_returned_apply_form', '', url, {}, self.jsonBack,'json');
		});
	},
	jsonBack:function(responseText){ //json回调函数
		if(responseText.result==1){
			alert(responseText.message);
			Eop.Dialog.close("order_w_dialog");
			window.location.reload(); //刷新页面
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	}
};
$(function() {
	GoodsReturnedApply.init();
});