var AgentStaff={
	priceBox:undefined,//当前会员价格div容器，规则为“会员价操作按钮”的class=member_price_box 下一个 节点
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"agentstaffdlg",title:"关联工号",width:'500px'});
		self.bindMbPricBtnEvent();
	},
	bindMbPricBtnEvent:function(){
		var self = this;
		$(".mempriceBtn").unbind("click").bind("click",function(){
			Eop.Dialog.open("agentstaffdlg");
			$("#agentstaffdlg").load('admin/agent!getstaff.do?ajax=yes',function(){
				self.initDlg();
			});
		});		
	}
	,
	
	//初始化弹出对话框
	initDlg:function(){
		$("[name='selectStaffBtn']").click(function(){
			var userid= $(this).attr("userid");
			var username= $(this).attr("username");
			var realname= $(this).attr("realname");
			$("#staff_code").val(username);
			$("#staff_no").val(userid);
			Eop.Dialog.close("agentstaffdlg");
			return false;
		});
	},
	//由对话框的input 创建会员价格表单项
	createPriceItems:function(){
		Eop.Dialog.close("agentstaffdlg");
	}
};
$(function(){
	AgentStaff.init();
}); 