var refreshGoodsCache = {
	init:function(){
		var self = this;
		$('input[jqType="goodsCache"]').each(function(){
			$(this).click(function(){
				self.loadCache(this.id);
			});
		});
	},
	loadCache:function(method){
		$("#rfsResult").load('goodsCache!refreshGoods.do?ajax=yes&refreshMethod='+method,{},function(reply){
			var me = this;
			var reply = eval('('+reply+')');
			if(reply.result=="0"){
				me.innerHTML="";
				alert("【"+$("#"+reply.refreshMethod).val()+"】刷新成功!");
			}
		});
	}
	
}
$(function(){
	refreshGoodsCache.init();
});