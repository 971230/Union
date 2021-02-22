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
				"/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp&service=cust").submit();
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
				"/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp&lx=mb&service=cust").submit();
	},
	loadReturnList : function(batchId){
		var url = ctx + "/shop/admin/custReturned!batchList.do?ajax=yes&batchId=" + batchId;
//		Cmp.asExcute('', url, {}, function(responseText){
//					if(responseText.indexOf("{result:")>-1){
//						var responseText = eval('(' + responseText + ')');
//						if(responseText.result == 0){
//							alert(responseText.message);
//						}
//					}
//					$('#returnList').empty().append(responseText);
//				},'html');
		//导入后reload数据  翻页需要使用get方式
		Cmp.load("returnList", url);
	}
}
$(function() {
	batchReturn.init();
});