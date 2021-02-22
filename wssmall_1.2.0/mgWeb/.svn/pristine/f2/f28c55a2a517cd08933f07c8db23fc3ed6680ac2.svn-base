var importDownload = {
	init:function(){
		var self = this;
		$(".uploadList ul li").click(function(){
			$(this).addClass("curr");
			$(this).siblings().removeClass("curr");
			var rule_desc = $(".uploadList ul li[class='curr']").attr("rule_desc");
			if(!rule_desc)
				rule_desc = "无规则描述";
			$(".stepEx").text(rule_desc);
		})
		$(".blue_b").click(function(){
			self.downloadTemplate();
		})
		$(".orgbtn").click(function(){
			self.importacion();
		})
		self.initRuleDesc();
	},
	importacion:function() {
		var self = this;
		var template_id = $(".uploadList ul li[class='curr']").attr("tpid");
		if(!template_id){
			MessageBox.show("请选择模板！", 2, 2000);
			return ;
		}
		var fileName = $("#import_file").val();
		if (!fileName) {
			MessageBox.show("上传文件为空 ", 3, 2000);
			return;
		}
		var extPattern = /.+\.(xls|xlsx|csv)$/i;
		var url = ctx + "/shop/admin/import!importFile.do?ajax=yes&fileName="+encodeURI(encodeURI(fileName))+"&template_id="+template_id;
		$("#importForm").attr("action",url);
		if ($.trim(fileName) != "") {
			if (extPattern.test(fileName)) {
				Cmp.ajaxSubmit('importForm', '', url, {}, self.jsonBack,'json');
			} else {
				MessageBox.show("只能上传EXCEL、CSV文件！", 2, 3000);
				return;
			}
		}
		$.Loading.show("正在导入数据，请耐心等待...");
	},
	jsonBack : function(responseText){
		if (responseText.result == 1) {
			//window.location.href="import!importLogList.do";
			alert("数据导入成功！");
			window.location.href="import!importLogList.do";
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	},
	initRuleDesc:function(){
		var rule_desc = $(".uploadList ul li[class='curr']").attr("rule_desc");
		$(".stepEx").text(rule_desc);
	},
	downloadTemplate:function(){
		var template_id = $(".uploadList ul li[class='curr']").attr("tpid");
		if(!template_id){
			MessageBox.show("请选择模板！", 2, 2000);
			return ;
		}
		var template_type = $("#template_type").val();
		if(!template_type){
			MessageBox.show("请选择下载的模板文件类型！", 2, 2000);
			return ;
		}
		var url = ctx + "/servlet/importTemplateServlet?template_id="+template_id+"&template_type="+template_type;
		var temp_form = '<form id="hidden_export_temp_form" action="'+url+'" method="post"></form>';
		if($("#hidden_export_temp_form").length>0)
			$("#hidden_export_temp_form").remove();
		$("#templateExportDiv").append(temp_form);
		$("#hidden_export_temp_form").submit();
	}
}

$(function(){
	importDownload.init();
	$(".uploadList ul li:eq(0)").trigger("click");
})