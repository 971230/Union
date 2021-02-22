function checkDate(){
	var start = $("#start_date").val();
	var end =$("#end_date").val();
	var url = ctx + "/shop/admin/goods!zdbImportLogsEcs.do?"
	$("#searchZdbsImportLogsForm").attr("action",url);
	if(start!=""&&end!=""){
		if(start>end){
			alert("开始时间不能大于结束时间，请检查！");
		}else{
			$("#searchZdbsImportLogsForm").submit();
		}
	}else{
		$("#searchZdbsImportLogsForm").submit();
	}
}

var ImportZdbs = {
		init : function(){
			var self = this;
			$("#importActBtn").click(function(){
				self.importacion("import");
			});
			$("#editActBtn").click(function(){
				self.importacion("edit");
			});
			$("#deleteActBtn").click(function(){
				self.importacion("delete");
			});
			$("#downloadBtn").click(function(){
				self.downloadTemplate();
			});
			$("#exportBtn").click(function(){
				self.exportZdbs();
			});

		},
		importacion:function(act) {
			var self = this;
			var fileName = $("#zdbsUploadFile").val();
			if (isNull(fileName)) {
				MessageBox.show("上传文件为空 ", 3, 2000);
				return;
			}
			var extPattern = /.+\.(xls|xlsx)$/i;
			if(act=="import"){
				var url = ctx + "/shop/admin/goods!importzdbs.do?fileNameFileName="+encodeURI(encodeURI(fileName));
			}
			if(act=="edit"){
				var url = ctx + "/shop/admin/goods!editzdbs.do?fileNameFileName="+encodeURI(encodeURI(fileName));
			}
			if(act=="delete"){
				var url = ctx + "/shop/admin/goods!deletezdbs.do?fileNameFileName="+encodeURI(encodeURI(fileName));
			}
			
			$("#zdbsUploadForm").attr("action",url);
			if ($.trim(fileName) != "") {
				if (extPattern.test(fileName)) {
					$("#zdbsUploadForm").submit();
				} else {
					MessageBox.show("只能上传EXCEL文件！", 2, 3000);
					return;
				}
			}

		},
		exportZdbs:function(){
			var start_date=$("input[name='start_date']").val();
			var end_date=$("input[name='end_date']").val();
			if((start_date==null&&end_date==null)||(start_date==""&&end_date=="")){
				alert("请先填写要导出转兑包的创建时间范围！");
			}
			else{
				debugger;
				var url = ctx + "/shop/admin/goods!exportZdbs.do?ajax=yes&excel=yes";
				$("#searchZdbsImportLogsForm").attr("action",url);
				$("#searchZdbsImportLogsForm").submit();
			}
		},
		
		downloadTemplate : function(){
			var url = ctx + "/servlet/batchAcceptExcelServlet?url=/shop/admin/goods/zdb_import_logs_ecs.jsp&lx=mb&service=zdbsEcs";
			var temp_form = '<form id="hidden_export_excel_form" action="'+url+'" method="post"></form>';
			if($("#hidden_export_excel_form").length>0)
				$("#hidden_export_excel_form").remove();
			$("#zdbsImportDiv").append(temp_form);
			$("#hidden_export_excel_form").submit();
		}
		
	}

$(function(){
	ImportZdbs.init();
});

