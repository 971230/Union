/**
 * 合约机受理
 * @type 
 */
var ContractInput = {
	init : function() {
		var self = this;
		$("#contract_form [name='submitBtn']").click(function() {
			var url = ctx+ "/shop/admin/contract!order.do?ajax=yes";
			Cmp.ajaxSubmit('contract_form', '', url, {}, self.jsonBack,'json');
		})
		this.viewInfo();
	},
	jsonBack : function(responseText) { // json回调函数
		if (responseText.result == 1) {
			alert(responseText.message);
			Eop.Dialog.close("order_w_dialog");
			window.location.reload();
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}
	},
	viewInfo : function(){
		$('#acc_nbr').blur(function(){
			var accNbr = $('#acc_nbr').val();
			if(accNbr.length < 11){
				alert('请输入正确的手机号码');				
				return false;
			}
			if($('#checkNo').find('input').length == 1)
				$('#checkNo').append('<input  type="button"  value=" 查询 " class="submitBtn" id="qryNoInfo"/>');	
				
			$("#qryNoInfo").click(function(){
				var url = ctx+ "/shop/admin/contract!noInfo.do?ajax=yes&accNbr.num_code=" + accNbr;
				Cmp.asExcute('', url, {}, function(responseText){
					if(responseText.indexOf("{result:")>-1){
						var responseText = eval('(' + responseText + ')');
						if(responseText.result == 0){
							alert(responseText.message);
						}
					}
					$('#contractInfo').empty().append(responseText);
				},'html');
			});					
		});

	}
}
$(function() {
	$("#contract_form.validate").validate();
	ContractInput.init();
	
});