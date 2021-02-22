var serviceAppmodule = {
  init:function(){
    var self = this;
    Eop.Dialog.init({id : "moduleEdit",title : "编辑模块列表" , width:"1000px",height:"450px"});
    //弹出服务名称列表对话框
	$("a[name='edits']").unbind("click").bind("click",function() {
		var temp_inst_id=$(this).attr("temp_inst_id");
		self.showServiceListDialogmodule(temp_inst_id);
	});
  },
   /**
    * 
	 * 点击按键弹出服务列表加载数据
	 * */
  showServiceListDialogmodule : function(temp_inst_id) {
 	Eop.Dialog.open("moduleEdit");
 	var goods_id=$("input[name='goods_id_e']").val();
	var url ='goodsPropertyAction!editModList.do?ajax=yes&goods_id='+goods_id+'&temp_inst_id='+temp_inst_id; 
	$("#moduleEdit").load(url,function(){
		
	});
 },

 
}; 
$(function(){
	serviceAppmodule.init();
});
   