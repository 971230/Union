
/**
 * 商品审核日志
 * @type 
 */
var GoodsAreaAuditLog = {init:function () {
	$(".submitBtn").click(function () {
		Eop.Dialog.close("auditDialog_log");
		Eop.Dialog.close("auditAreaOk");
	});
}};
$(function () {
	GoodsAreaAuditLog.init();
});

