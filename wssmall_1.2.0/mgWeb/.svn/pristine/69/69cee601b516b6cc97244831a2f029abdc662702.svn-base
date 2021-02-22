function checkDate(){
	var start = $("#start_date").val();
	var end =$("#end_date").val();
	if(start!=""&&end!=""){
		if(start>end){
			alert("开始时间不能大于结束时间，请检查！");
		}else{
			$("#searchTerminalImportLogsForm").submit();
		}
	}else{
		$("#searchTerminalImportLogsForm").submit();
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
			var catId = $("#cat_id").val();
			if(!catId){
				MessageBox.show("请选择手机分类", 3, 2000);
				return ;
			}
			var fileName = $("#terminalImportFile").val();
			if (isNull(fileName)) {
				MessageBox.show("上传文件为空 ", 3, 2000);
				return;
			}
			var extPattern = /.+\.(xls|xlsx)$/i;
			var url = ctx + "/shop/admin/goods!importTerminals.do?fileNameFileName="+encodeURI(encodeURI(fileName));
			$("#terminalUploadForm").attr("action",url);
			if ($.trim(fileName) != "") {
				if (extPattern.test(fileName)) {
					$("#terminalUploadForm").submit();
				} else {
					MessageBox.show("只能上传EXCEL文件！", 2, 3000);
					return;
				}
			}
			$.Loading.show("正在导入手机货品，请耐心等待...");
		},
		downloadTemplate : function(){
			var url = ctx+ "/servlet/batchAcceptExcelServlet?url=/shop/admin/activities/numero_import_logs_ecs.jsp&lx=mb&service=terminal";
			var temp_form = '<form id="hidden_export_excel_form" action="'+url+'" method="POST"></form>';
			if($("#hidden_export_excel_form").length>0)
				$("#hidden_export_excel_form").remove();
			$("#terminalImportDiv").append(temp_form);
			$("#hidden_export_excel_form").submit();
		}
	}

$(function(){
	ImportGoods.init();
});

