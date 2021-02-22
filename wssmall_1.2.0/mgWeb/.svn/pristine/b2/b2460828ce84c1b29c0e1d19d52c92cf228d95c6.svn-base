var serviceAppaa = {
  init:function(){
    var self = this;
    Eop.Dialog.init({id : "goodModule",title : "模块列表" , width:"1000px",height:"450px"});
    //弹出服务名称列表对话框
	$("#moduleAdd").unbind("click").bind("click",function() {
		self.showServiceListDialogs();
	});
  },
   /**
    * 
	 * 点击按键弹出服务列表加载数据
	 * */
 showServiceListDialogs : function() {
 	Eop.Dialog.open("goodModule");
 	var goods_id=$("input[name='goods_id_e']").val();
	var url ='goodsPropertyAction!modList.do?ajax=yes&goods_id='+goods_id; 
	$("#goodModule").load(url,function(){
	});
 },
 
}; 
$(function(){
  serviceAppaa.init();
});
   