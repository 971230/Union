var cardCharge = {
	init : function(){
		var self = this;
		$("#card_charge_form.validate").validate()
		$("#card_charge_form [name='submit']").click(function() {
			var goods_info = $("#goodsList").val();
			if(goods_info == null || goods_info == ""){
				alert("请选择关联商品！");	
				return;
			}
			var goodsId = goods_info.split("_")[0];
			var goodsPrice = goods_info.split("_")[1];
			var amount = $("#amount").val();
			if(goodsPrice != amount){
				alert("商品价格与充值价格不符，请重新选择！")
				return;
			}

			var url = ctx+ "/shop/admin/card!cardCharge.do?ajax=yes&bill_flag=F&goodsId=" + goodsId;
			Cmp.ajaxSubmit('card_charge_form', '', url, {}, self.jsonBack,'json');
		});
	},
	jsonBack : function(responseText){
		alert(responseText.message);
	}
}
$(function(){
	cardCharge.init();
});