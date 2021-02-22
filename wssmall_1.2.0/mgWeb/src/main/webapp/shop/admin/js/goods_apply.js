var GoodsApply = {
	init:function () {
		//申请
		$(".apply_use").click(function () {
			var goodsid = $(this).attr("goodsid");
			var url = ctx + "/shop/admin/goods!showGoodsApply.do?ajax=yes&goods_id=" + goodsid;
			$("#apply_w_dialog").load(url, function (responseText) {
				if(responseText=='{result:0}'){
					alert("很抱歉，此商品已被删除！");
					window.location.reload();
				}else if(responseText=='{result:1}'){
					alert("很抱歉，此商品已被冻结！");
					window.location.reload();
				}else if(responseText=='{result:2}'){
					alert("您的账号已被上级冻结，不能再申请新的商品！");
				}
				else if(responseText=='{result:3}'){
					alert("您的上级分销商已被冻结，故不能再申请新的商品！");
				}else{
					Eop.Dialog.open("apply_w_dialog");
				}
			});
			
		});
		$(".areaMsg").click(function () {
			var goodsid = $(this).attr("goodsid");
			Eop.Dialog.open("auditAreaOk");
			var url = ctx + "/shop/admin/goods_audit!goodsAreaSucc.do?ajax=yes&goodsid=" + goodsid;
			$("#auditAreaOk").load(url, function () {
			});
		});
		$(".stock_detail").click(function(){
			var goodsid = $(this).attr("goodsid");
			$("#hidden_goodsid").val(goodsid);
			var goodsType = $(this).attr("goodsType");
			//熟卡
			if(goodsType == "CLOUD"){
				Eop.Dialog.open("cloud_stock_dialog");
				var url =ctx+"/shop/admin/cloud!list_avaible.do?ajax=yes&goods_id="+goodsid;
				$("#cloud_stock_dialog").load(url,function(){
					GoodsApply.cloud_page_init();
				}); 
			}
			//充值卡和流量卡
			if(goodsType == "CARD" || goodsType == "RECHARGE_CARD"){
				
				Eop.Dialog.open("card_stock_dialog");
				var url =ctx+"/shop/admin/card!list_avaible.do?ajax=yes&goodsId="+goodsid;
				$("#card_stock_dialog").load(url,function(){
					GoodsApply.card_page_init();
				}); 
			}
			//合约卡
			if(goodsType == "CONTRACT"){
				Eop.Dialog.open("contract_stock_dialog");
				var url =ctx+"/shop/admin/contract!list_avialable.do?ajax=yes&goods_id="+goodsid;
				$("#contract_stock_dialog").load(url,function(){
					GoodsApply.contract_page_init();
				}); 
			}
			return false;
			
		});
	},
	cloud_page_init:function(){
		$("#cloud_query_form .comBtn").unbind("click").click(function(){//searchBtn 搜索
			var goodsid = $("#hidden_goodsid").val();
			var url =ctx+"/shop/admin/cloud!list_avaible.do?ajax=yes&goods_id="+goodsid;
			Cmp.ajaxSubmit('cloud_query_form','cloud_stock_dialog',url,{},GoodsApply.cloud_page_init);
			return false;
		});
	},
	card_page_init:function(){
		$("#card_query_form .comBtn").unbind("click").click(function(){//searchBtn 搜索
			var goodsid = $("#hidden_goodsid").val();
			var url =ctx+"/shop/admin/card!list_avaible.do?ajax=yes&goodsId="+goodsid;
			Cmp.ajaxSubmit('card_query_form','card_stock_dialog',url,{},GoodsApply.card_page_init);
			return false;
		});
	},
	contract_page_init:function(){
		$("#accnbr_query_form .comBtn").unbind("click").click(function(){//searchBtn 搜索
			var goodsid = $("#hidden_goodsid").val();
			var url =ctx+"/shop/admin/contract!list_avialable.do?ajax=yes&goods_id="+goodsid;
			Cmp.ajaxSubmit('accnbr_query_form','contract_stock_dialog',url,{},GoodsApply.contract_page_init);
			return false;
		});
	}
};
$(function () {
	Eop.Dialog.init({id:"auditAreaOk", modal:false, title:"\u5546\u54c1\u53d1\u5e03\u533a\u57df", width:"350px"});
	Eop.Dialog.init({id:"apply_w_dialog", modal:false, title:"\u5546\u54c1\u7533\u8bf7", width:"1000px"});
	Eop.Dialog.init({id:"card_stock_dialog",modal:false,title:"充值卡流量卡可用库存",width:"1000px"});
	Eop.Dialog.init({id:"cloud_stock_dialog",modal:false,title:"云卡可用库存",width:"1000px"});
	Eop.Dialog.init({id:"contract_stock_dialog",modal:false,title:"可申请号码",width:"1000px"});
	GoodsApply.init();
});

