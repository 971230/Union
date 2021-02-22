$(function(){
	ImportGoods.init();
});

var ImportGoods = {
	
	init : function(){
		var self = this;
		$("#importActBtn").click(function(){
			self.importacion();
		});
		$("#downloadBtn").click(function(){
			self.downloadTemplate();
		});
	},
	importacion:function() {
		var self = this;
		var fileName = $("#goodsUploadFile").val();
		if (isNull(fileName)) {
			MessageBox.show("上传文件为空 ", 3, 2000);
			return;
		}
		var extPattern = /.+\.(xls|xlsx)$/i;
		var url = ctx + "/shop/admin/goods!importGoods.do?ajax=yes&fileNameFileName="+encodeURI(encodeURI(fileName));
		if ($.trim(fileName) != "") {
			if (extPattern.test(fileName)) {
				Cmp.ajaxSubmit('goodsUploadForm', '', url, {}, self.jsonBack,'json');
			} else {
				MessageBox.show("只能上传EXCEL文件！", 2, 3000);
				return;
			}
		}

	},
	downloadTemplate : function(){
		var url = "template!dowloadTemplate.do?type=goods";
		var temp_form = '<form id="hidden_export_excel_form" action="'+url+'" method="POST"></form>';
		if($("#hidden_export_excel_form").length>0)
			$("#hidden_export_excel_form").remove();
		$("#goodsImportDiv").append(temp_form);
		$("#hidden_export_excel_form").submit();
	},
	jsonBack : function(responseText) { // json回调函数
		if (responseText.result == 0) {
			alert(responseText.message);
			window.location.href="goods!goodsImportLogsECS.do";
		}
		if (responseText.result == 1) {
			alert(responseText.message);
		}
	}
}

