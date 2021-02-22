var templateList = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"showAlarmTaskDalog",modal:false,title:"告警列表",height:"800px",width:"800px"});
  },
   /**
    * 
    * 点击按键弹出任务列表加载数据
   	* */
 showAlarmTaskDialogs : function() {
     Eop.Dialog.open("showAlarmTaskDalog");
     var url ="alarm!search_applic_scene.do?ajax=yes"; 
     $("#showAlarmTaskDalog").load(url,function(){
     });
 }
 
}; 
$(function(){
 	 templateList.init();
 	 $("#tapplic_scene_btn").click(function(){
		templateList.showAlarmTaskDialogs();
	 });
});
