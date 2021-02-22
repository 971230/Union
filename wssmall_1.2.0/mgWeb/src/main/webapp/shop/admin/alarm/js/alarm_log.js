var alarmLogList = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"showAlarmLogDalog",modal:false,title:"日志信息",height:"800px",width:"800px"});
  },
   /**
    * 
    * 点击按键弹出日志信息列表加载数据
   	* */
 showAlarmLogDialogs : function(alarm_log_id) {
     //Eop.Dialog.open("showAlarmLogDalog");
     var url ="alarm!showAlarmLogBase.do?alarm_log_id="+alarm_log_id; 
     //$("#showAlarmLogDalog").load(url,function(){
     //});
     location.href=url;
 }
 
}; 
$(function(){
 	 alarmLogList.init();
 	 $("[name='showAlarmLogDialogs']").live("click",function(){
 	 	alarmLogList.showAlarmLogDialogs($(this).attr('alarm_log_id'));
 	 })
});
