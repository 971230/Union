var payApplyDlg ={
   init:function(){
      payApplyDlg.savePayApply();
   },
   savePayApply:function(){
     $("#payApplyBtn").click(function(){
         var apply_price = $("#apply_price").val();
         if(isNaN(apply_price)){
            alert("申请价格必须是数字");
            return false;
         }
 		if(parseFloat(apply_price)<=0){
 			alert("调价金额必须大于0");
 			return false;
 		}
         var url = ctx+"/shop/admin/commissionApply!saveAddCommssionApply.do?ajax=yes";
         Cmp.ajaxSubmit('payApplyForm', '', url, {},payApplyDlg.addJsonBack, 'json');
     });
   },
   addJsonBack:function(reply){
    
     if(reply.result == 0){
       alert("操作成功");
       Eop.Dialog.close("payApplyDlg");
     }
     if(reply.result==1){
       alert(reply.message);
     }
   }
  
};

$(function(){
 
  payApplyDlg.init();
});
