
var AgentCat={
	priceBox:undefined,//当前会员价格div容器，规则为“会员价操作按钮”的class=member_price_box 下一个 节点
	init:function(){
		var self = this;
		Eop.Dialog.init({id:"agentcatdlg",title:"关联工号",width:'500px',height:"600px"});
		self.bindMbCatBtnEvent();
	},
	bindMbCatBtnEvent:function(){
		var self = this;
		
		$(".catsBtn").unbind("click").bind("click",function(){
			Eop.Dialog.open("agentcatdlg");
			$("#agentcatdlg").load('admin/cat!list_select.do?ajax=yes',function(){
				self.initDlg();
			});
		});		
	}
	,
	
	//初始化弹出对话框
	initDlg:function(){
		$("[id='staffRelCatBtn']").click(function(){ //按钮点击处理事件
			var cat_ids =[];
			var cat_names =[];
			if($("[name='chk_cat']:checked").length ==0){
				alert("请选择分类");
				return false;
			}
			$("[name='chk_cat']:checked").each(function(){
				cat_ids.push($(this).attr("cat_id"));
				cat_names.push($(this).attr("cat_name"));
			})
			$("#cat_ids").val(cat_ids.join(","));
			$("#cat_names").val(cat_names.join(","));
			Eop.Dialog.close("agentcatdlg");
			return false;
		});
	}
};
$(function(){
	AgentCat.init();
}); 