var chargeConfirm = {
	init:function() {
		var self = this;
		$("#confirmForm [name='confirmBtn']").click(function() {
			
			if(!chargeConfirm.validDate()){
				return;
			}
			
			var url = ctx+ "/shop/admin/deposit!depositChargeEnd.do?ajax=yes";
			Cmp.ajaxSubmit('confirmForm', 'wrapper', url, {'insert':'yes'}, self.jsonBack,'html');
			return false;
		});
		
		$("#confirmForm [name='returnBtn']").click(function() {
			window.location.reload();
		});
		
		self.initValidCode();
		Eop.Dialog.init({id:"pay_dialog",modal:false,title:"预存金充值",width:"750px"});
	},
	jsonBack:function(responseText) { // json回调函数
		
		var bankCode = $('#bankCode').val();
		var amount = $('#amount').val();
		var partnerId = $('#partnerId').val();
		var url =ctx + "/shop/admin/deposit!goBank.do?ajax=yes&bankCode=" + bankCode +
			"&amount=" + amount + "&partnerId=" + partnerId;
			
		Cmp.bankPayBack(responseText,url);
	},
	initValidCode:function(){		//初始化验证码
		
		$("#codeImg").attr("src",ctx + "/validcode.do?vtype=pay&rmd="+new Date().getTime())
		.click(function(){
		
			$(this).attr("src",ctx + "/validcode.do?vtype=pay&rmd="+new Date().getTime() );
		});		
	},
	validDate:function(){
		
		var validCode = $('#validCode').val();
		if(validCode == null || validCode == ''){
			alert('请输入验证码');
			return false;
		}
		return true;
	}
}
$(function(){

	chargeConfirm.init();
})