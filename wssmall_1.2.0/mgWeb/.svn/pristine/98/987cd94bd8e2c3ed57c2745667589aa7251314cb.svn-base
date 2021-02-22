var LimitBuyGoods={
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"goods_dlg",modal:false,title:"添加相关商品", width:"600px"});
		function  appendGoods(goodsList){
			self.appendGoods(goodsList);
		}
		
		$("#selgoodsBtn").click(function(){
			GoodsSelector.open("goods_dlg",appendGoods);
		});	
		
	},
	appendGoods:function(goodsList){
		if(goodsList.length>1) {alert("只能选择一个商品"); return ;}
		if(goodsList.length==0) {
			//alert("请选择一个商品");
			$("#goodsid").val("");
			$("#goodsname").val("");
			return ;
		}
		var goods = goodsList[0];
		$("#goodsid").val(goods.goods_id);
		$("#goodsname").val(goods.name);
	}
};

$(function(){
	LimitBuyGoods.init();
	GoodsInfo.init();
});

var GoodsInfoSelector={
		conid:undefined,
		init:function(conid,onConfirm) {
			this.conid = conid;
			var self  = this;
		},
		open:function(conid,onConfirm,limitId) {
			var self= this;
			$("#"+conid).load(ctx+'/shop/admin/limitBuy!goodsInfoList.do?ajax=yes&id='+limitId,function(){
				self.init(conid,onConfirm);
			});
			Eop.Dialog.open(conid);	
		}
}

var GoodsInfo={
		init:function(){
			var self = this;
			Eop.Dialog.init({id:"goodsInfo_dlg",modal:false,title:"限时购商品信息", width:"600px"});
		},
		
		openDialog:function (limitId){
			$("#goodsInfo_dlg").html("");
			GoodsInfoSelector.open("goodsInfo_dlg",null,limitId);
		}
}

function openDialog(limitId){
	GoodsInfo.openDialog(limitId);
}