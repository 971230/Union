/**

  修改供货商员工
*/

var StaffEdit ={
	wrapper_id:'editStaffDlg', //弹出页面id
	form_id:'editStaffform',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
        Eop.Dialog.init({id:"editStaffDlg",modal:false,title:"修改员工",width:'500px'});
         $("#editStaffBtn").click(function(){ //点击按钮弹出界面
         		if(self.checkIdSeled()==0){
					alert("请选择要修改的供货商员工");
					return ;
				}
				
				if(self.checkIdSeled()>1){
					alert("一次只能修改一个供货商员工");
					return ;
				}
				
				var userid=[];
				$("input[name='id']").each(function(){
			         if($(this).attr("checked")){
			            if($(this).attr("userid")!=""){
			              userid.push($(this).attr("userid"));
			            }
			         }
			    });
			    if(userid.length>0){
					       alert("不能修改已关联工号的供货商员工!请先解绑!");
					       return ;
				}
				var staff_id=$("input[type='checkbox']:checked").val();
                self.open(staff_id);
         });
	},
	page_close:function(){
	   Eop.Dialog.close(this.wrapper_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
	   
		$('input.dateinput').datepicker();
	},
	checkIdSeled : function() {
		var a = false;
		var count=0;
		$("input[name='id']").each(function() {
					if ($(this).attr("checked")) {
						a = true;
						count++;
						return
					}
				});
		return count;
	},
	open:function(staff_id){ //弹出页面
	    var self=this;
	
		var url=app_path+'/shop/admin/supplier!showStaffEdit.do?ajax=yes&staff_id='+staff_id;
		Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_id);
	}
}

$(function(){
	StaffEdit.init();
})