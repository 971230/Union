/**

  添加人力资源信息
*/

var ResourcesAdd ={
	wrapper_id:'addResourcesDlg', //弹出页面id
	form_id:'addResouresform',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
        Eop.Dialog.init({id:"addResourcesDlg",modal:false,title:"添加人力资源信息",width:'500px'});
         $("#addResourcesBtn").click(function(){ //点击按钮弹出界面
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
	
		var url=app_path+'/shop/admin/supplier!showResourcesAdd.do?ajax=yes&supplier_id='+supplier_id;
		Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_id);
	}
}

var ResourcesDelete=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		
		//全选
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		
		//删除信息
		$("#delResouresBtn").click(function(){self.doAccountDelete();});
	},
	deletePost1:function(url,msg){
		Cmp.ajaxSubmit('resourcesgridform', '', url+'?ajax=yes', {}, function(result){
					if(result.result==0){
							alert(result.message);
							SupplierDetail.showresourcesTab();
					}else{
						alert(result.message);
					}
				}, 'json');
	},
	doAccountDelete:function(){
				//删除的资源信息
			 	 if(!this.checkIdSeled()){
					alert("请选择要删除的信息");
					return ;
				}
			 
			 	var record_state=[];
				$("input[resources='state']").each(function(){
			         if($(this).attr("checked")){
			            if($(this).attr("record_state")=="0"){
			              record_state.push($(this).attr("record_state"));
			            }
			         }
			    });
			    if(record_state.length>0){
					       alert("不能删除正在审核中的信息!");
					       return ;
				}
				
				if(!confirm("确认要将这些信息彻底删除吗？删除后将不可恢复")){	
					return ;
				}
				
				this.deletePost1(basePath+"supplier!deleteResources.do");
			}
});

$(function(){
	ResourcesAdd.init();
	ResourcesDelete.init();
})