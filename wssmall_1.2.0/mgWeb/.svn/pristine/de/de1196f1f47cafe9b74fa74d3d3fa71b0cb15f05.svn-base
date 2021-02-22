var serviceAppmodules = {
  init:function(){
    var self = this;
    Eop.Dialog.init({id : "moduleEdits",title : "编辑模块列表" , width:"1000px",height:"450px"});
    //弹出服务名称列表对话框
	$("a[name='editsList']").unbind("click").bind("click",function() {
		var temp_inst_id=$(this).attr("temp_inst_id");
		self.showServiceListDialog(temp_inst_id);
	});
  },
   /**
    * 
	 * 点击按键弹出服务列表加载数据
	 * */
  showServiceListDialog : function(temp_inst_id) {
 	Eop.Dialog.open("moduleEdits");
 	var goods_id=$("input[name='goods_id_e']").val();
 	
	var url ='goodsPropertyAction!editModList.do?ajax=yes&goods_id='+goods_id+'&temp_inst_id='+temp_inst_id; 
	$("#moduleEdits").load(url,function(){
	});
 },
 
}; 
$(function(){
	serviceAppmodules.init();
});
   