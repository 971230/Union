var GoodsAuditYes={
	init:function(){
	//修改库存警告阀值
		$('.auditYes').click(function(){
			var goodsid=$(this).attr('goodsid');
			var usageid=$(this).attr('usageid');
			Eop.Dialog.open("auditYes_dialog");
			var url =ctx+"/shop/admin/goods!showGoodsAuditYes.do?ajax=yes&goods_id="+goodsid+"&usageid="+usageid; 
				$("#auditYes_dialog").load(url,function(){
			});
			return false;
			
		});
	}
}	
$(function() {
	Eop.Dialog.init({id:"auditYes_dialog",modal:false,title:"库存警告阀值",width:"650px"});
	GoodsAuditYes.init();
});