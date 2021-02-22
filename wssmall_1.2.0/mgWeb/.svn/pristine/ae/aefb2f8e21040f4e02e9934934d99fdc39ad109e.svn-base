var serviceAppss = {
  init:function(){
    var self = this;
    Eop.Dialog.init({id:"editPayDialog",modal:false,title:"修改支付方式",height:"700px",width:"500px"});
  //修改
	 $("#editPayments").unbind("click").bind("click",function() {
		  var id=$(this).attr("value");
         self.edit(id);
    });
  },
   edit:function (id){
			$("#editPayDialog").load(ctx+"/shop/admin/creditAction!editPay.do?ajax=yes&id="+id,function(){
			$("#butSaves").click(function(){
				 url =ctx+"/shop/admin/creditAction!editSave.do?ajax=yes&id="+id;
				 Cmp.ajaxSubmit('editParS', '', url, {}, function(reply){
				 
        if(reply.result==0){
          alert(reply.message);
        }  
        if(reply.result==1){
          alert("操作成功!");
          Eop.Dialog.close("editPayDialog");
     	  window.location.reload();
        }
   
				 }, 'json');
			});
		});
		Eop.Dialog.open("editPayDialog");
	},
};
$(function(){
	alert("sdsdd");
  serviceAppss.init();
});
   