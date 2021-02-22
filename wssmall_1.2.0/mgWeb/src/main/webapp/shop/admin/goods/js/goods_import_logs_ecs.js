function checkDate(){
	var start = $("#start_date").val();
	var end =$("#end_date").val();
	if(start!=""&&end!=""){
		if(start>end){
			alert("开始时间不能大于结束时间，请检查！");
		}else{
			$("#searchGoodsImportLogsForm").submit();
		}
	}else{
		$("#searchGoodsImportLogsForm").submit();
	}
}

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
			var url = ctx + "/shop/admin/goods!importGoods.do?fileNameFileName="+encodeURI(encodeURI(fileName));
			$("#goodsUploadForm").attr("action",url);
			if ($.trim(fileName) != "") {
				if (extPattern.test(fileName)) {
					$("#goodsUploadForm").submit();
					//Cmp.ajaxSubmit('goodsUploadForm', '', url, {}, self.jsonBack,'json');
				} else {
					MessageBox.show("只能上传EXCEL文件！", 2, 3000);
					return;
				}
			}

		},
		downloadTemplate : function(){
			var url = ctx + "/servlet/batchAcceptExcelServlet?url=/shop/admin/goods/goods_import_logs_ecs.jsp&lx=mb&service=cm";
			var temp_form = '<form id="hidden_export_excel_form" action="'+url+'" method="post"></form>';
			if($("#hidden_export_excel_form").length>0)
				$("#hidden_export_excel_form").remove();
			$("#goodsImportDiv").append(temp_form);
			$("#hidden_export_excel_form").submit();
		}
	}

$(function(){
	ImportGoods.init();
});

