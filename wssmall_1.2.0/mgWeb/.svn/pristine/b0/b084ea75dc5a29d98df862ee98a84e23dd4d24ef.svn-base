var serviceApps = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"staffDiog",modal:false,title:"工号",height:"800px",width:"800px"});
 //弹出服务名称列表对话框
     $("#choiceBotton1").unbind("click").bind("click",function() {
          self.showServiceListDialogs();
     });
  },
   /**
    * 
      * 点击按键弹出服务列表加载数据
      * */
 showServiceListDialogs : function() {
     Eop.Dialog.open("staffDiog");
     var url ="mblLoginAction!staffList.do?ajax=yes"; 
     $("#staffDiog").load(url,function(){
     });
 }
}; 
$(function(){
  serviceApps.init();
});
