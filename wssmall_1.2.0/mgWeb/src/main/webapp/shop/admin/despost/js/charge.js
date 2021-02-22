var ChargeInput = {
	init : function() {
		var self = this;
		$("#chargeForm [name='submitBtn']").click(function() {
			if(!self.validDate()){
				return ;
			}
			var url = ctx+ "/shop/admin/deposit!depositChargeCfm.do?ajax=yes";
			Cmp.ajaxSubmit('chargeForm', 'wrapper', url, {'insert':'yes'}, self.jsonBack,'html');
			return false;
		});
	},
	jsonBack : function(responseText) { // json回调函数
			
		if(responseText.indexOf("{result:")>-1){
		
			var responseText = eval('(' + responseText + ')');   
			if (responseText.result == 1) {
				alert(responseText.message);
			}
			if (responseText.result == 0) {
			
				alert(responseText.message);
			}
		
		}else{
			
			$("#wrapper").empty().append($(responseText));
		}
		
	},
	validDate : function() {
		//验证金额
		var amount = $('#amount').val();
		if(amount <= 0){
			alert('请输入大于0的整数');
			return false;
		}
		if(amount > 20000000){
			alert('请输入小于20000000的金额');
			return false;
		}
		//查找选择的银行
		var iLoopNum = 0;
		var iLoop = 0;
		var bankId = "";
		var hControl = null;
		var hControls = document.getElementsByName("bankCode");
		if(hControls!=null) iLoopNum=hControls.length;
		for(iLoop=0;iLoop<iLoopNum;iLoop++){
			hControl    = hControls[iLoop];
			if(hControl==null) continue;
			if(hControl.checked){
				bankId=hControl.value;
			}
		}	
		if(bankId == null|| bankId == 'undefined' || bankId == ''){
			alert('请选择支付银行');
			return false;
		}
		return true;
	}
}
$(function(){
	$("#bankList").load('pay!getBkList.do?ajax=yes',{},function(){
		
	});
	$("#chargeForm.validate").validate();
	ChargeInput.init();
})