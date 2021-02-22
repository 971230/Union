
var GoodsAudit = {init:function () {
	//审核
	$(".auditmodify").click(function () {
		var goodsid = $(this).attr("goodsid");
		var lanid = $(this).attr("lanid");
		Eop.Dialog.open("audit_w_dialog");
		
		
		var url = ctx + "/shop/admin/goods!showGoodsAudit.do?ajax=yes&lan_id=" + lanid + "&goods_id=" + goodsid;
		$("#audit_w_dialog").load(url, function () {
		});
		return false;
	});
}};
$(function () {
	Eop.Dialog.init({id:"audit_w_dialog", modal:false, title:"\u5546\u54c1\u5ba1\u6838\u4fe1\u606f", width:"550px"});
	GoodsAudit.init();
});

