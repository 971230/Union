/**

  添加供货商账号
*/

var AccountAdd ={
	wrapper_id:'addAccountDlg', //弹出页面id
	form_id:'addAccountform',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
        Eop.Dialog.init({id:"addAccountDlg",modal:false,title:"添加账号",width:'600px'});
         $("#addAccountBtn").click(function(){ //点击按钮弹出界面
                var supplier_id=$("input[name='supplier_id']").val();
                self.open(supplier_id);
         });
	},
	page_close:function(){
	   Eop.Dialog.close(this.wrapper_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
	   
		$('input.dateinput').datepicker();
	},
	open:function(supplier_id){ //弹出页面
	    var self=this;
	
		var url=app_path+'/shop/admin/supplier!showAccountAdd.do?ajax=yes&supplier_id='+supplier_id;
		Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_id);
	}
}

var AccountDelete=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		
		//删除账户
		$("#delAccountBtn").click(function(){
			self.doAccountDelete();
		});
	},
	deletePost1:function(url,msg){
		Cmp.ajaxSubmit('accountgridform', '', url+'?ajax=yes', {}, function(result){
					if(result.result==0){
							alert(result.message);
							 SupplierDetail.showaccountTab();
					}else{
						alert(result.message);
					}
				}, 'json');
	},
	doAccountDelete:function(){
				//删除的供货商账户
			 	 if(!this.checkIdSeled()){
					alert("请选择要删除的账户");
					return ;
				}
			 
			 	var state=[];
				$("input[account='state']").each(function(){
			         if($(this).attr("checked")){
			            if($(this).attr("state")=="0"){
			              state.push($(this).attr("state"));
			            }
			         }
			    });
			    if(state.length>0){
					       alert("不能删除正在审核中的信息!");
					       return ;
				}
				
				if(!confirm("确认要将这些账户彻底删除吗？删除后将不可恢复")){	
					return ;
				}
				
				
				this.deletePost1(basePath+"supplier!deleteAccount.do");
			}
});
$(function(){
	AccountAdd.init();
	AccountDelete.init();
})