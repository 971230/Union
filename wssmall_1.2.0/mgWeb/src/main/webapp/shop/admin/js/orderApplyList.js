var OrderApply=$.extend({},Eop.Grid,{
    wrapper_id:'orderApply_dialog', //弹出页面id
	form_id:'auditParform',//弹出页面表单id
    init:function(){
        Eop.Dialog.init({id:this.wrapper_id,modal:false,title:'申请单审核',width:'850px'});
		//电信员工审核
		$(".auditName").click(function(){
             //alert(111);
		     var order_apply_id=$(this).attr("order_apply_id");
		     var good_name=$(this).attr("good_name");
		     var service_type=$("#service_type").val();
             var url="orderApply!getTails.do?ajax=yes&order_apply_id="+order_apply_id;
                Eop.Dialog.open("orderApply_dialog");
				$("#orderApply_dialog").load(url,function(){
					$("#auditParform #auditSubmitBtn").unbind("click");
					$("#auditParform #auditSubmitBtn").bind("click",function(){
                        if($("#audit_idea").val()==""){
			              alert("审核描述不能为空！");
			              return false;
			           }
			           if($("#state").val()==""){
			              alert("请选择处理！");
			              return false;
			           }
			      
						var url ="orderApply!updateOrderApplyState.do?ajax=yes&order_apply_id="+order_apply_id;
						Cmp.ajaxSubmit('auditParform', '', url, {}, function(responseText){
						        
								if (responseText.result == 1) {
									alert(responseText.message);
									window.location=app_path+'/shop/admin/orderApply!list.do?service_type='+service_type;
								}
								if (responseText.result == 0) {
									 alert(responseText.message);
								}
								Eop.Dialog.close("orderApply_dialog");
								
						},'json');
						//self.audit();
						OrderApply.onAjaxCallBack();
						return  false;
					});
				});
			
             return false;
		});
    
    },
    onAjaxCallBack:function(){//ajax回调函数
	    
		$('input.dateinput').datepicker();
	}
});


$(function(){
    OrderApply.init();
  
});