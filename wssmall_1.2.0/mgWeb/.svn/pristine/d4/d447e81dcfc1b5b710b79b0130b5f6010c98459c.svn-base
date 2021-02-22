var CustInput = {
	init : function() {
		var self = this;
		
		$("#accountForm [name='submitBtn']").click(function() {
			if($("#amount").val()<=0 || $("#amount").val() > 20000000){
				$("#amount").focus().val("");
				alert("请校验操作金额！");
				return;
			}
			var url = ctx+ "/shop/admin/freeze!actChange.do?ajax=yes";
			Cmp.ajaxSubmit('accountForm', '', url, {}, self.jsonBack,'json');
		})
		
		
		$("#show_change_log_a").bind("click",function(){
			$("#show_change_log_div").toggle(); //联动处理
		})
	},
	jsonBack : function(responseText) { // json回调函数
		if (responseText.result == 1) {
			alert(responseText.message);
			window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}
	},
	validData : function(){
		var accountAmount = $("#account_amount").val();
		var changeAmount = $("#amount").val();
	}
}
$(function() {
	$("#accountForm.validate").validate();
	CustInput.init();
});