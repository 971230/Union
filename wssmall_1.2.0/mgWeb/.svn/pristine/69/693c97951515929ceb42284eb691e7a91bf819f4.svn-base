var orderReportList={
	init : function(){
		var self = this;
		self.initClk();
	},
	initClk : function() {
		$(".showSupplierOrder").bind("click",function() {
			var id = $(this).attr("staff_no");
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var url= ctx + "/shop/admin/orderReport!supplierOrderDetail.do?id="+id+"&startDate="+startDate+"&endDate="+endDate;
			self.location.href=url;
//			$("#reportDetail_dialog").load(url, function (responseText) {
//					Eop.Dialog.open("reportDetail_dialog");
//			});
		});
	}
}
$(function(){
	Eop.Dialog.init({id:"reportDetail_dialog",modal:false,title:"报表详情",width:"1000px"});
	orderReportList.init();
});