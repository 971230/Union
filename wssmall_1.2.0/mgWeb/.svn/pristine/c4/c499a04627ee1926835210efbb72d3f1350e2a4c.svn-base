var alarmList = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"showPhone",modal:false,title:"号码列表",height:"800px",width:"800px"});
    Eop.Dialog.init({id:"showPartner",modal:false,title:"分销商列表",height:"800px",width:"800px"});
    Eop.Dialog.init({id:"showTask",modal:false,title:"任务列表",height:"800px",width:"800px"});
  },
   /**
    * 
    * 点击按键弹出号码列表加载数据
   	* */
 showPhoneListDialogs : function() {
     Eop.Dialog.open("showPhone");
     var url ="alarm!userPhone.do?ajax=yes"; 
     $("#showPhone").load(url,function(){
     });
 },
 /**
    * 
    * 点击按键弹出分销商列表加载数据
   	* */
 showPartnerListDialogs : function() {
     Eop.Dialog.open("showPartner");
     var url ="alarm!partnerList.do?ajax=yes"; 
     $("#showPartner").load(url,function(){
     });
 },
  /**
    * 
    * 点击按键弹出任务列表加载数据
   	* */
 showTaskListDialogs : function() {
     Eop.Dialog.open("showTask");
     var url ="alarm!taskList.do?ajax=yes"; 
     $("#showTask").load(url,function(){
     });
 }
 
}; 
$(function(){
 	 alarmList.init();
 	 $("#phone_btn").click(function(){
		alarmList.showPhoneListDialogs();
	 });
	 
	  $("#partner_name_btn").click(function(){
		alarmList.showPartnerListDialogs();
	 });
	 $("#task_name_btn").click(function(){
		alarmList.showTaskListDialogs();
	 });
});
