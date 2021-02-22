/**
  流程审核
*/
var auditObjMsgUrl ="";

var FlowAudit ={
		init:function(){ //当前页面init
			var self=this;

	         $("#nextStep").bind("click",function(){//审核下一步提交
	             
	             var auditNote = $("#textarea").val();
	             var auditState = $("input[name='RadioGroup1']:checked").val();   
	             $("input[name='modInfoAudit.audit_note']").val(auditNote);
	             $("input[name='modInfoAudit.audit_state']").val(auditState);
	             if(auditState == null || auditState == ''){
	                alert("请选择审批决策！");
	                return false;
	             }
	             
	             if(auditNote == null || auditNote == ''){
	                alert("请输入审批意见！");
	                return false;
	             }
	             
	  		    var url ="workflow!doNext.do?ajax=yes";
	  			Cmp.ajaxSubmit('editAuditform', '', url, {}, function(responseText){
	  				    if (responseText.result == 1) {
	  							alert("审核成功");
	  							location.href=ctx+'/shop/admin/workflow!getWorkList.do';	
	  					}else{
	  					    alert("审核失败");
	  					}
	  			},'json');
	  		})
	  		
	  		//查看审批详情
	  		$("#showAuditInfo").bind("click",function(){//审核下一步提交
	  			var url =auditObjMsgUrl+"&ajax=yes";
	  			
	  			Eop.Dialog.open("auditObjMsgDialog",url);
	  			$("#auditObjMsgDialog").load(url,function(){
	  				
	  		    });

	  		})
	  		
		}

}

$(function(){
	FlowAudit.init();

	Eop.Dialog.init({id:"auditObjMsgDialog",modal:false,title:"审核业务表单提示",width:"1000px"});
	
})