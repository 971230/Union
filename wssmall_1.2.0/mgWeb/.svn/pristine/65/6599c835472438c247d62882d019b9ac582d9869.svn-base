/**

  添加分销商员工
*/

var StaffAdd ={
	wrapper_id:'addStaffDlg', //弹出页面id
	form_id:'addParStaffform',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
        Eop.Dialog.init({id:"addStaffDlg",modal:false,title:"添加员工",width:'500px'});
         $("#addStaffBtn").click(function(){ //点击按钮弹出界面
                is_edit=$("input[name='is_edit']").val();
                partner_id=$("input[name='partner_id']").val();
                self.open(is_edit,partner_id);
         });
	},
	page_close:function(){
	   Eop.Dialog.close(this.wrapper_id);
	},
	onAjaxCallBack:function(){//ajax回调函数
	   
		$('input.dateinput').datepicker();
	},
	open:function(is_edit,partner_id){ //弹出页面
	    var self=this;
	    if(partner_id==""){
             alert('请先保存分销商基本资料！');
             return false;
         }
		var url=app_path+'/shop/admin/partner!showStaffAdd.do?ajax=yes&partner_id='+partner_id+'&is_edit='+is_edit;
		Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_id);
	}
}

$(function(){
	StaffAdd.init();
})