$(function(){
	initDialog("numbers_pub_dialog", true, "号码发布", "400px", "500px");
});

function importacion() {
	var fileName = document.getElementById("uploadFile").value;
	if (isNull(fileName)) {
		MessageBox.show("上传文件为空 ", 3, 2000);
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	var url = $("#batchForm").attr("action");
	if(url.indexOf("?")>=0){
		url = url +"&fileFileName="+fileName;
	}
	else{
		url = url +"?fileFileName="+fileName;
	}
	$("#batchForm").attr("action",url);
	$("#action_code").val("A");
	var extPattern = /.+\.(xls|xlsx)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			$("#batchForm").submit();
		} else {
			MessageBox.show("只能上传EXCEL文件！", 2, 3000);
			return;
		}
	}
	$.Loading.show("正在导入号码，请耐心等待...");

}

function batchModificar(){
	var fileName = document.getElementById("uploadFile").value;
	if (isNull(fileName)) {
		MessageBox.show("上传文件为空 ", 3, 2000);
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	$("#fileFileName").val(fileName);
	$("#action_code").val("M");
	var extPattern = /.+\.(xls|xlsx)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			$("#batchForm").submit();
		} else {
			MessageBox.show("只能上传EXCEL文件！", 2, 3000);
			return;
		}
	}
	$.Loading.show("正在导入号码，请耐心等待...");
}

function batchPublish(){
	var fileName = document.getElementById("uploadFile").value;
	if (isNull(fileName)) {
		MessageBox.show("上传文件为空 ", 3, 2000);
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	$("#fileFileName").val(fileName);
	$("#action_code").val("B");
	var url = "numero!numeroBatchPubTree.do?busqueda=false&batchPublishWay=import";
	abrirCajaVentana("numbers_pub_dialog",url);

}

function batchRecycle(){
	var fileName = document.getElementById("uploadFile").value;
	if (isNull(fileName)) {
		MessageBox.show("上传文件为空 ", 3, 2000);
		return;
	}
	fileName = encodeURI(encodeURI(fileName));
	$("#fileFileName").val(fileName);
	$("#action_code").val("D");
	var extPattern = /.+\.(xls|xlsx)$/i;
	if ($.trim(fileName) != "") {
		if (extPattern.test(fileName)) {
			$("#batchForm").submit();
		} else {
			MessageBox.show("只能上传EXCEL文件！", 2, 3000);
			return;
		}
	}
	$.Loading.show("正在导入号码，请耐心等待...");
}

function download(){
	var url = ctx+ "/servlet/batchAcceptExcelServlet?url=/shop/admin/activities/numero_import_logs_ecs.jsp&lx=mb&service=numeroEcs";
	var temp_form = "<form id='hidden_export_excel_form' action='"+url+"' method='POST'></form>";
	if($("#hidden_export_excel_form").length>0)
		$("#hidden_export_excel_form").remove();
	$("#goodsImportDiv").append(temp_form);
	$("#hidden_export_excel_form").submit();
}

function checkDate(){
	var start = $("#start_date").val();
	var end =$("#end_date").val();
	if(start!=""&&end!=""){
		if(start>end){
			alert("开始时间不能大于结束时间，请检查！");
		}else{
			$("#searchNumeroImportLogsForm").submit();
		}
	}else{
		$("#searchNumeroImportLogsForm").submit();
	}
}
