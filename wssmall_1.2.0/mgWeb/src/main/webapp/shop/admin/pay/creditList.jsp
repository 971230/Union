<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 

<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<div class="searchformDiv">
   <a href="creditAction!addPay.do" style="margin-right:10px;" name="addPaymentss" class="graybtn1" >新增</a>	 
</div>  	
<form id="gridform" class="grid" ajax="yes">
<input type="hidden" name="ids" value="${id}">
 <div class="grid" id="goodslist" >
	<grid:grid  from="webpage">
	<grid:header>
		<grid:cell >支付方式ID</grid:cell>
		<grid:cell >支付方式名称</grid:cell>
		<grid:cell >支付方式编码</grid:cell> 
		<grid:cell width="180px">操作 </grid:cell>
	</grid:header>
  <grid:body item="obj">
	 <grid:cell >${obj.id} </grid:cell>
     <grid:cell >${obj.name} </grid:cell> 
     <grid:cell >${obj.type} </grid:cell> 
     <grid:cell>  
		   <a href="javascript:;"  name="accountShe"  class="account" value="${obj.id}">设置帐号</a>
		 <a href="creditAction!editPay.do?id=${obj.id }" name="addBtn2" id="editPayments" class="updatePayment" value="${obj.id}">修改</a>
		   <a href="javascript:;"  name="addBtn3" id="" class="delPayment" value="${obj.id}">删除</a>	
		   <a href="javascript:;"  name="showBankLists" id="" class="showBankListsscs" value="${obj.id}">设置银行</a>	
	</grid:cell>
  </grid:body>   
</grid:grid>  
</div>
</form>
<div title="添加银行" id ="addBankDialog"></div> 
<div title="设置帐号" id ="addAccountDialog"></div> 
<div title="银行" id ="adddBankListsss"></div >
<script type="text/javascript">
var addBank = {
	    init:function(){
	    var self = this;
	    Eop.Dialog.init({id:"addBankDialog",modal:true,title:"设置银行",height:"400px",width:"800px"});
	    Eop.Dialog.init({id:"addAccountDialog",modal:true,title:"设置帐号",height:"400px",width:"800px"});
	    Eop.Dialog.init({id:"adddBankListsss",modal:true,title:"设置银行",height:"400px",width:"800px"});
	    Eop.Dialog.init({id:"addPayDialog",modal:true,title:"添加支付方式",height:"700px",width:"800px"});
	    Eop.Dialog.init({id:"editPayDialogoooooooooo",modal:true,title:"修改支付方式",height:"700px",width:"800px"});
	   //弹出设置银行
	     $("a[name=addBtn]").unbind("click").bind("click",function() {
	    	 var id=$(this).attr("value");
	          self.showBankListDialogs(id);
	     });
	     $("a[name=showBankLists]").unbind("click").bind("click",function() {
	    	 var id=$(this).attr("value");
	          self.showBankListdsds(id);
	     });
	   //弹出设置帐户
	     $("a[name=accountShe]").unbind("click").bind("click",function() {
	    	 var id=$(this).attr("value");
	          self.showAccountListDialogs(id);
	     });
	 	//新增
	    // $("a[name=addPaymentss]").unbind("click").bind("click",function() {
	     //     self.showAddPayDialogs();
	    // });
	     //修改
		 $(".updatePayment").unbind("click").bind("click",function() {
			 var id=$(this).attr("value");
			
	          self.showEditPayDialogs(id);
	          
	    });
	 	//删除
	     $(".delPayment").bind("click",function(){
	          var id = $(this).attr("value");
	         
	          self.del(id);
	      });
	 
	  },
	   /**
	    * 
	      * 点击按键弹出银行加载数据
	      * */
	      showBankListdsds : function(id) {
		     Eop.Dialog.open("adddBankListsss");
		     var url ="creditAction!bankList.do?ajax=yes&id="+id; 
		     $("#adddBankListsss").load(url,function(){
	      });
	     },
	     showBankListDialogs : function(cfg_id) {
		     Eop.Dialog.open("addBankDialog");
		     var url ="creditAction!showBankList.do?ajax=yes&cfg_id="+cfg_id; 
		     $("#addBankDialog").load(url,function(){
	      });
	     },
	     //弹出设置账户页面
	     showAccountListDialogs : function(cfg_id) {
		     Eop.Dialog.open("addAccountDialog");
		     var url ="creditAction!showAccount.do?ajax=yes&cfg_id="+cfg_id; 
		     $("#addAccountDialog").load(url,function(){
	      });
	     },
	     //弹出新增页面
	     showAddPayDialogs : function() {
		     Eop.Dialog.open("addPayDialog");
		     var url ="creditAction!addPay.do?ajax=yes"; 
		     $("#addPayDialog").load(url,function(){
	         });
	     },
	   /*   //弹出修改页面
	     showEditPayDialogs:function (id){
			//	$("#editPayDialogoooooooooo").load(ctx+"/shop/admin/creditAction!editPay.do?ajax=yes&id="+id,function(){
				
				$("#editSavePay").click(function(){
					 url =ctx+"/shop/admin/creditAction!editSave.do?ajax=yes&credit.id="+id;
					 Cmp.ajaxSubmit('editParS', '', url, {}, function(reply){
						 if(reply.result==1){
					          alert("操作成功!");
					          Eop.Dialog.close("editPayDialogoooooooooo");
					          window.location.reload();
					     }	 
				        if(reply.result==0){
				          alert(reply.message);
				        }  
		        
					 }, 'json');
				});
			//});
			Eop.Dialog.open("editPayDialogoooooooooo");
		}, */
	     del:function(id){
	         var self = this;
	         if(!confirm("是否确定要删除?")){
	            return ;
	         }  
	    	   url ="creditAction!delete.do?ajax=yes&id="+id;    	   
	           Cmp.excute('', url, {}, self.delJsonBack, 'json');
	           
	    },
	     delJsonBack:function(reply){
			    if(reply.result==0){
			      alert(reply.message);
			    }  
			    if(reply.result==1){
			      alert("操作成功!");
			      window.location.reload();
			    }
		},
	}; 
	$(function(){
		addBank.init();
	});
	
	
</script>