var templateList = {
	init:function(){
		var self = this;
		$("#type_id").change(function(){
			self.loadSubType();
		})
		$("#deleteTemplate").click(function(){
			self.deleteTemplate();
		})
		$(".searchBtn2").click(function(){
			$("#btnClick").val("yes");
			$("#searchTemplateForm").submit();
		})
		self.initSubType();
	},
	initSubType:function(){
		var type_id = $("#type_id").val();
		var sub_type_id = $("#hidden_sub_type").val();
		if(type_id){
			$.ajax({
				type: "get",
				url:app_path+"/shop/admin/import!loadSubType.do?ajax=yes",
				data:"type_id=" + type_id +"&m=" + new Date().getTime(),
				dataType:"html",
				success:function(result){
					$("#sub_type_id").empty().append(result);
					if(sub_type_id){
					   	$("#sub_type_id").val(sub_type_id);
					}
				  
				},
				error :function(res){alert("异步读取失败:" + res);}
			});
		}
	},
	deleteTemplate:function(){
		var ids = [];
		$("input[name='id']:checked").each(function(){
			ids.push($(this).val());
		})
		if(ids.length==0){
			MessageBox.show("请选择需要删除的模板 ", 3, 2000);
			return ;
		}
		if(confirm("确定删除选中的模板？")){
			$.ajax({
				type: "get",
				url:app_path+"/shop/admin/import!deleteTemplate.do?ajax=yes",
				data:"template_id=" + ids.join(",") +"&m=" + new Date().getTime(),
				dataType:"json",
				success:function(result){
					if(result.result=='1'){
						window.location="import!searchImportTemplates.do";
					}
					MessageBox.show(result.message, 3, 2000);
//					setTimeout(function(){
//						MessageBox.show(result.message, 3, 2000);
//					},1000);
				},
				error :function(res){alert("异步读取失败:" + res);}
			});
		}
	},
	loadSubType:function(){
		var type_id = $("#type_id").val();
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/import!loadSubType.do?ajax=yes",
			data:"type_id=" + type_id +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				$("#sub_type_id").empty().append(result);
			},
			error :function(res){alert("异步读取失败:" + res);}
			});
	}
}

$(function(){
	templateList.init();
})