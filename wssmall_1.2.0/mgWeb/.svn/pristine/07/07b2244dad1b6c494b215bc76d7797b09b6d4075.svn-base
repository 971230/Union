var hasCommit = false;
var batchReturn = {
	init : function() {
		var self = this;
		$('#upload').unbind("click").bind("click",function(){
			self.upLoad(this);
		});
		$('#export').bind("click",function(){
			self.exportExcel();
		});
		$("#close").bind("click",function(){
			Eop.Dialog.close("adjunctInputDialog");
			//后台同步配件，并展示同步结果		
			//window.location.href = ctx+ "/shop/admin/cloud!list.do?cloud.batch_id=" + batchId ;
			window.location.reload();
			$(".goods_input .tab>li[tabid='5']").trigger("click");
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
		var goods_id = $("#theForm").find("input[type='hidden'][name='goods.goods_id']").val();
		
		if (fileName == null || fileName == "") {
			alert("请选择模板单文件！");
			return false;
		} else if (fileName.substring(fileName.length - 3, fileName.length) != "xls") {
			alert("请选择Excel文件！");
			return false;
		}
		var nagn=encodeURI(encodeURI($("#nowAdjunctGroupName").val(),true),true);
		var namn=$("#nowAdjunctMinNum").val();
		var naxn=$("#nowAdjunctMaxNum").val();
		var napt=$("#nowAdjunctPriceType").val();
		var nayp=$("#nowAdjunctYouhuiPrice").val();
		if(hasCommit == false) {
			iframeDocument.find("form").attr("action",ctx + 
				"/servlet/batchAcceptExcelServlet?url=/shop/admin/batch/UpLoadFile.jsp&group="+nagn+"&min="+namn+"&max="+naxn+"&ptype="+napt+"&pprice="+nayp+"&nowIsUploadExcel=100&service=adjunct&goods_id="+goods_id).submit();
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
				"/servlet/batchAcceptExcelServlet?lx=mb&service=adjunct").submit();
	}
}
$(function() {
	batchReturn.init();
});