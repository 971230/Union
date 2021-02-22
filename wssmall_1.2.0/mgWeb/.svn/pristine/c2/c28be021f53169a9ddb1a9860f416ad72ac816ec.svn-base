
var SolutionAdd = {
		
		init:function(){
			var self = this;
			
			$("#fileName_dis").hide();
			$("#solution_type").change(function(){
				var selectText = $("#solution_type").find("option:selected").text();
				var uploadFlag = $("#uploadFlag").html();
				if(selectText == 'DOC'){
					$("#fileName_dis").show();
					$("#solution_value_dis").hide();
					var hidePath = $("#uploadPathHidden").val();
					if((hidePath != '' || hidePath != null) && (uploadFlag.length != 0) ){
						$("#solution_value").val(hidePath);
					}
				}else{
					$("#fileName_dis").hide();
					$("#solution_value_dis").show();
					$("#solution_value").text('');
				}
				//RULE和PLAN显示是否删除日志选择框
				if(selectText == 'RULE' || selectText == 'PLAN'){
					$("#is_delete_rule_log").closest("tr").show();
					$("#hander_fact").closest("tr").show();
				}else{
					$("#is_delete_rule_log").closest("tr").hide();
					$("#is_delete_rule_log").val("N");
					
					$("#hander_fact").closest("tr").hide();
					$("#hander_fact").val("");		
				}
				//RULE、PLAN和SQL显示是否支持批处理
				if(selectText == "RULE" || selectText == "PLAN" || selectText == "SQL"){
					$("#is_batch_process").closest("tr").show();
					$("#is_batch_process").val("");
				}else{
					$("#is_batch_process").closest("tr").hide();
					$("#is_batch_process").val("");
				}
			});
			
			if(uploadFlag == null || uploadFlag == ""){
				$("#fileName_dis").hide();
				$("#solution_value_dis").show();
				$("#solution_value").text('');
			}else{
				$("#fileName_dis").show();
				$("#solution_value_dis").hide();
				$("#solution_type option[value='DOC']").attr("selected", "selected");
			}
		}
		
}

$(function(){
	SolutionAdd.init();
});