var hasCommit = false;
var batchReturn = {
	init : function() {
		var self = this;
		$('#upload').click(function(){
			self.upLoad(this);
		});
		$('#exportComtract').click(function(){
			var order_type = "CONTRACT";
			self.exportExcel(order_type);
		});
		$('#exportCloud').click(function(){
			var order_type = "CLOUD";
			self.exportExcel(order_type);
		});
		$('#exportCloudWithLan').click(function(){
			var order_type = "CLOUDWITHLAN";
			self.exportExcel(order_type);
		});
		$("#close").bind("click",function(){
			OrderOuter.afterImport();
		});
	},
	upLoad : function(currA) {
	
		$(currA).attr("disabled", true);
		var result = true;
		
		result= batchReturn.upLoadExcel(currA);
		
		if(result == false) {
			hasCommit = false;
			$(currA).removeAttr("disabled");
		}
		return result;
	},
	upLoadExcel : function(currA) {
		var trjQ = $(currA).parents("tr");
		var iframeDocument = $($(trjQ).find("[name='uploadIframe']").get(0).contentWindow.document); // 获取当前tr的iframe
		var fileName = iframeDocument.find("[id='uploadFile']").val();
	
		if (fileName == null || fileName == "") {
			alert("请选择模板单文件！");
			return false;
		} else if (fileName.substring(fileName.length - 3, fileName.length) != "xls") {
			alert("请选择Excel文件！");
			return false;
		}
			
		if(hasCommit == false) {
			iframeDocument.find("form").attr("action",ctx + 
				"/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp&service=order").submit();
			$(currA).attr("disabled", true);
			hasCommit = true;
		}
		return true;
	},
	exportExcel : function(order_type){	
		
//		var currA = $("input[name='export']");
//		var trjQ = $(currA).parents("tr");
//		var iframeDocument = $($(trjQ).find("[name='uploadIframe']").get(0).contentWindow.document);
		
		var iframeDocument = $($("#uploadIframe").get(0).contentWindow.document);
		
		iframeDocument.find("form").attr("action",ctx + 
				"/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp&lx=mb&service=order&order_type="+order_type).submit();
	},
	loadReturnList : function(batchId){
		
		OrderOuter.returnBatch(batchId);
	}
}
$(function() {
	batchReturn.init();
});