$(function(){
	var flag = $("#flag").val();  //1 新增上传   0  修改上传
//	var truthHidden = $("#truthHidden").val();
	var returnView = $("#returnView").val();  //默认为1
	var select = $("#solution_type").find("option:selected").text();
	if(returnView == "1"){  //从页面点击修改
		$("#flag").val(''); //置空
		if(select == 'DOC'){
			$("#fileName_dis").show();
			$("#solution_value_dis").hide();
		}else{
			$("#fileName_dis").hide();
			$("#solution_value_dis").show();
		}
	}else{ //修改页面上传刷新
		$("#fileName_dis").show();
		$("#solution_value_dis").hide();
		$("#solution_type").attr("selected",false);
		$("#solution_type option[value='DOC']").attr("selected","selected");
		//$("#uploadFlag").html($("#uploadPathHidden").val());
		$("#uploadFlag").show();
		$("#solution_value").text($("#uploadPathHidden").val());
		$("#solution_value_dis").hide();
	}
	
	
	$("#solution_type").change(function(){
		var selectText = $("#solution_type").find("option:selected").text();
		var uploadFlag = $("#uploadFlag").html();
		
		//RULE和PLAN显示是否删除日志选择框
		if(selectText == 'RULE' || selectText == 'PLAN'){
			$("#is_delete_rule_log").closest("tr").show();
			$("#hander_fact").closest("tr").show();
		}else{
			$("#is_delete_rule_log").closest("tr").hide();
			$("#hander_fact").closest("tr").hide();
			//$("#is_delete_rule_log").val("N");
		}
		
		if(returnView == "1"){   //从页面点击修改
			if(selectText == 'DOC'){
				$("#fileName_dis").show();
				$("#solution_value_dis").hide();
				$("#saveSolution_value").val($("#solution_value").val()); //保存配置值
				$("#solution_value").text(''); //清空配置值，防止未上传文档提交
			}else{
				if($("#solution_value").val() == null || $("#solution_value").val() == ''){
					$("#solution_value").val($("#saveSolution_value").val());
				}
				$("#fileName_dis").hide();
				$("#solution_value_dis").show();
			}
		}else{
			if(selectText == 'DOC'){
				$("#fileName_dis").show();
				$("#solution_value_dis").hide();
				var hidePath = $("#uploadPathHidden").val();
				if((hidePath != '' || hidePath != null) && (uploadFlag.length != 0) ){
					if(hidePath.indexOf(".") != -1){
						$("#solution_value").val(hidePath);
					}else{
						$("#solution_value").val('');
					}
				}
			}else{
				$("#fileName_dis").hide();
				$("#solution_value_dis").show();
			}
		}
		
	});
	
});