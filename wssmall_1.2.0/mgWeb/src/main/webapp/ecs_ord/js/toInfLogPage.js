$(function() {
	infLog.init();
});
var infLog = {
	init : function() {
		infLog.qryBtn();
		infLog.dealBtn();
	},
	qryBtn : function() {
		$("#qryBtn").bind("click", function() {
			if (infLog.checkSub() == true) {
				$("#log_list_f").attr("action",ctx+"/shop/admin/infLogMonitManagerAction!toInfLogPage.do").submit();
			}
		});
	},
	dealBtn : function() {
		$("#dealBtn").bind("click", function() {
			var startTime = $("input[name='startTime']").val();
			var endTime = $("input[name='endTime']").val();
			if(startTime == "" || endTime == "") {
				alert("请选择开始和结束时间!");return;
			}
			$.Loading.show("数据正在分析中，请稍侯...");
			$.ajax({
				type : "post",
				async : false,
				url : "infLogMonitManagerAction!toInfLogAnalyze.do?ajax=yes",
				data : {"startTime":startTime, "endTime":endTime},
				dataType : "json",
				success : function(data) {
					$.Loading.hide();
				}
			});
		});
	},
	checkSub: function() {
		var bool = true;
		return bool;
	}
};