function checkDate(){
	var start = $("#start_date").val();
	var end =$("#end_date").val();
	var url=ctx+"/shop/admin/orderBatchImportAction!importOrderLogiMsg.do";
	$("#terminalUploadForm").attr("action",url);
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
			$("#importActBtnLogi").click(function(){
				self.importacion();
			});
			$("#downloadBtnLogi").click(function(){
				self.downloadTemplate();
			});
		},
		importacion:function() {
			var self = this;
			var fileName = $("#terminalImportFileLogi").val();
			var terminalImportFileLogi ="terminalImportFileLogi";
			var extPattern = /.+\.(xls|xlsx|csv)$/i;
			var url = ctx + '/shop/admin/orderBatchImportAction!importOrderLogUpdate.do?ajax=yes&fileName='+ encodeURI(encodeURI(fileName));
			if ($.trim(fileName) != "") { 
				if (extPattern.test(fileName)) {
					$.Loading.show("正在导入，请稍侯...");
					$.ajaxFileUpload({
						url: url, //用于文件上传的服务器端请求地址
						secureuri: false, //一般设置为false
						fileElementId: terminalImportFileLogi,
						dataType: 'json',
						success: function(data) { 
							$.Loading.hide();
							if(data.result == 1) {
								alert(data.message);
							} else {
								alert(data.message);
								window.location.href = ctx + "/shop/admin/orderBatchImportAction!importOrderLogiMsg.do";
							}
						},
						error: function(data, status, e) {
							$.Loading.hide();
							alert(e);
						}
					});
				} else {
					alert("只能上传EXCEL文件！", 1, 1000);
					return;
				}
			}else{
				alert("上传文件为空！");
				return;
		}
		},
		downloadTemplate : function(){
			$("#hidden_export_excel_form").submit();
		}
	}

$(function(){
    // 初始化 
	ImportGoods.init();
});


