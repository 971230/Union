/**

  添加供货商员工
*/

var StaffAdd ={
	wrapper_id:'addStaffDlg', //弹出页面id
	form_id:'addParStaffform',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
        Eop.Dialog.init({id:"addStaffDlg",modal:false,title:"添加员工",width:'500px'});
         $("#addStaffBtn").click(function(){ //点击按钮弹出界面
                var is_edit=$("input[name='is_edit']").val();
                var supplier_id=$("input[name='supplier_id']").val();
                self.open(is_edit,supplier_id);
         });
	},
	page_close:function(){
	   Eop.Dialog.close(this.wrapper_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
	   
		$('input.dateinput').datepicker();
	},
	open:function(is_edit,supplier_id){ //弹出页面
	    var self=this;
	    if(supplier_id==""){
             alert('请先保存供货商基本资料！');
             return false;
         }
		var url=app_path+'/shop/admin/supplier!showStaffAdd.do?ajax=yes&supplier_id='+supplier_id+'&is_edit='+is_edit;
		Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_id);
	}
}

$(function(){
	StaffAdd.init();
})