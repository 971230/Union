/**

  添加行业用户员工
*/

var StaffAdd ={
	wrapper_id:'addStaffDlg', //弹出页面id
	form_id:'addParStaffform',//弹出页面表单id
	params:{},
	init:function(){ //当前页面init
		var self=this;
        Eop.Dialog.init({id:"addStaffDlg",modal:true,title:"添加行业用户",width:'600px'});
	    $("#addStaffBtn").click(function(){ //点击按钮弹出界面
            is_edit=$("input[name='is_edit']").val();
            partner_id=$("input[name='partner_id']").val();
            self.open(is_edit,partner_id);
	    });
	    $("a[name='editStaff']").unbind('click').bind('click',function(){
	    	var partner_id = $(this).attr('partner_id');
	    	var staff_code = $(this).attr('staff_code');
	    	var url = app_path + '/shop/admin/cmsAgent!showStaffAdd.do?ajax=yes&partner_id=' + partner_id 
	    			+'&flag=edit&staff_code=' + staff_code;
	    	
			Cmp.excute(self.wrapper_id,url,self.params,self.onAjaxCallBack);
			Eop.Dialog.open(self.wrapper_id);
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
             alert('请先保存行业用户基本资料！');
             return false;
         }
		var url=app_path+'/shop/admin/cmsAgent!showStaffAdd.do?ajax=yes&partner_id='+partner_id+'&is_edit='+is_edit;
		Cmp.excute(this.wrapper_id,url,this.params,this.onAjaxCallBack);
		Eop.Dialog.open(this.wrapper_id);
	}
};

$(function(){
	StaffAdd.init();
});