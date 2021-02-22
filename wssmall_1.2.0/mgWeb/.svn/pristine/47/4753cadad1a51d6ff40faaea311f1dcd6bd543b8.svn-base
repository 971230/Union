$(function(){
	$("#searchFormSubmit").click(function(){
		$("#queryActivityLogsForm").submit();
	});
	//导入
	$("#importActBtn").bind("click",function(){
		uploadActivity.importAct();
	});
	$("#downloadBtn").bind("click",function(){
		uploadActivity.downloadTemplate();
	});
	$("#checkBtn").bind("click",function(){
		uploadActivity.checkImport();
	});
});
var uploadActivity = {

	 importAct: function() {
		var self = this;
		var fileName = $("#uploadFile").val();
		if (!fileName) {
			alert("请选择导入的文件 ");
			return;
		}
		var extPattern = /.+\.(xls|xlsx)$/i;
		var url = ctx + "/shop/admin/activity!importActivity.do?fileFileName="+encodeURI(encodeURI(fileName));
		$("#importForm").attr("action",url);
		if ($.trim(fileName) != "") {
			if (extPattern.test(fileName)) {
				$("#importForm").submit();
				//$.Loading.show("正在导入活动，请不要关闭页面...")
				//Cmp.ajaxSubmit('importForm', '', url, {}, self.jsonBack,'json');
			} else {
				alert("只能上传EXCEL文件！");
				return;
			}
		}
		$.Loading.show("正在导入活动，请耐心等待...");

	},
	jsonBack : function(responseText) { // json回调函数
		$.Loading.hide();
		if (responseText.result == 1) {
			alert("读取总记录数："+responseText.totalCount+ "  导入成功数："+responseText.successCount+ "  导入失败数：" +responseText.failureCount);
			$("#queryActivityLogsForm").submit();
		}
		if (responseText.result == -1) {
			alert(responseText.message);
		}
	},
	downloadTemplate:function(){
		var url = ctx+ "/servlet/batchAcceptExcelServlet?url=/shop/admin/activities/activity_import_logs_ecs.jsp&lx=mb&service=activityEcs";
		var temp_form = "<form id='hidden_export_excel_form' action='"+url+"' method='post'></form>";
		if($("#hidden_export_excel_form").length>0)
			$("#hidden_export_excel_form").remove();
		$("#activityTemplateDownload").append(temp_form);
		$("#hidden_export_excel_form").submit();
	},
	checkImport:function(){
		var self = this;
		var fileName = $("#uploadFile").val();
		if (!fileName) {
			alert("请选择校验的文件 ");
			return;
		}
		var extPattern = /.+\.(xls|xlsx)$/i;
		var url = ctx + "/shop/admin/activity!checkActivity.do?ajax=yes&excel=yes&fileFileName="+encodeURI(encodeURI(fileName));
		$("#importForm").attr("action",url);
		if ($.trim(fileName) != "") {
			if (extPattern.test(fileName)) {
				$("#importForm").submit();
				//$.Loading.show("正在导入活动，请不要关闭页面...")
				//Cmp.ajaxSubmit('importForm', '', url, {}, self.jsonBack,'json');
			} else {
				alert("只能校验EXCEL文件！");
				return;
			}
		}
	}
}