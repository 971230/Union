var hasCommit = false;
var batchReturn = {
	init : function() {
		var self = this;
		$('#upload').click(function(){
			self.upLoad(this);
		});
		$('#export').click(function(){
			self.exportExcel();
		});
		$("#close").bind("click",function(){
			CardList.afterImport();
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
		
		iframeJq.get(0).contentWindow.document
		var fileName = iframeDocument.find("[id='uploadFile']").val();
		
		var iframeDocument = $($(trjQ).find("[name='uploadIframe']").get(0).contentWindow.document); // 获取当前tr的iframe
		var fileName = iframeDocument.find("[id='uploadFile']").val();
		var goods_info = $("#goodsList").val();
		
		if(goods_info == null || goods_info == ""){
			alert("请选择关联商品！");	
			return;
		}
		if (fileName == null || fileName == "") {
			alert("请选择模板单文件！");
			return false;
		} else if (fileName.substring(fileName.length - 3, fileName.length) != "txt") {
			alert("请选择Txt文件！");
			return false;
		}
			
		if(hasCommit == false) {
			var fileType = "";
			if(fileName.substring(fileName.length - 3, fileName.length) == "txt"){
				fileType = "txt";
			}
			iframeDocument.find("form").attr("action",ctx + 
				"/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp&service=card&fileType="+fileType
				+ "&goods_info=" + goods_info).submit();
			$(currA).attr("disabled", true);
			hasCommit = true;
		}
		return true;
	},
	exportExcel : function(){	
		
		var currA = $("input[name='export']");
		var trjQ = $(currA).parents("tr");
		var iframeDocument = $($(trjQ).find("[name='uploadIframe']").get(0).contentWindow.document);
		
		iframeDocument.find("form").attr("action",ctx + 
				"/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp&lx=mb&service=card").submit();
	},
	loadReturnList : function(batchId){
		CardList.returnBatch(batchId);
	}
}
$(function() {
	batchReturn.init();
});