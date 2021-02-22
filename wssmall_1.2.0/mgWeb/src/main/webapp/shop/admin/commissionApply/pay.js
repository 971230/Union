var pay={
  init:function(){
    var self = this;
    self.showBankList();
    self.bindBtn();
  },
  showBankList:function(){
  $('#payment_cfg_id').bind('change', function(){ 
        var cfg_id = $(this).val();
      
        var url = "/shop/admin/commissionApply!bankList.do?ajax=yes";
        $("#bankListDisplay").empty().load(url,{payment_cfg_id:cfg_id});
    });
  },
  bindBtn:function(){
    $("#commissionPayBtn").click(function(){
      var apply_id = $("#apply_id").val();
      var payment_cfg_id = $("#payment_cfg_id").val();
      var bankId = $("[name='bankId']:checked").val();
      var apply_user_id = $("#apply_user_id").val();
      var params ={apply_id:apply_id,payment_cfg_id:payment_cfg_id,bankId:bankId,apply_user_id:apply_user_id};
      if(payment_cfg_id.length==0){
        alert("请选择支付方式");
        return false;
      }
      if(bankId.length==0){
        alert("请选择银行");
        return false;
      }
      pay.payCommission(params);
  });
  },
  payCommission:function(params){
     var url = ctx+"/shop/admin/commonPay.do?ajax=yes";
     Cmp.ajaxSubmit('commissionform','',url,params,function(data){
			if(data.result==1){
				if(data.onlineflag=='0'){
					Cmp.bankPayBackNew(data);
				}else{
					alert(data.message);
				}
				Eop.Dialog.close("payDlg");
				$("#searchBtn").trigger("click");
			}else{
				alert(data.message);
			}
		},'json');
  }
  
};

$(function(){
  pay.init();
});