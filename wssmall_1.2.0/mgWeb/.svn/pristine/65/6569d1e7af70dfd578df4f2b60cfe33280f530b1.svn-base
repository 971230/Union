
var CrmOfferId = {init:function () {
	$(":radio[name='crmRadio']").css("outline", "none");
	$(".submitBtn").click(function () {
		var radioChk = $(":radio[name='crmRadio'][checked='true']");
		var crmId = radioChk.attr("crmId");
		var crmName = radioChk.attr("crmName");
		if (!radioChk.length > 0) {
			alert("\u60a8\u8fd8\u6ca1\u6709\u9009\u62e9");
		} else {
			$("#sellId").val(crmId);
			$("#goods_name").val(crmName);
			Eop.Dialog.close("crmOfferId_dialog");
			$("#up").parent().show();
		}
	});
	var self = this;
	$("#crmSerchBtn").click(function () {
		//var cardTypeCode = $("#cardTypeCode").val();
		//var offer_name = $("input[type='text'][name='offer_name']").val();
		//var offer_id = $("input[type='text'][name='offer_id']").val();
		var url = ctx + "/shop/admin/goods!findCrmOfferId.do?ajax=yes";
		Cmp.ajaxSubmit("gridform", "crmOfferId_dialog", url, {}, function () {
		});
	});
}};
$(function () {
	CrmOfferId.init();
});

