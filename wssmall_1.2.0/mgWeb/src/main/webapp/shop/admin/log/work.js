var serviceAppa = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"workList",modal:false,title:"工号",height:"800px",width:"800px"});
 //弹出服务名称列表对话框
     $("#choiceBotton").unbind("click").bind("click",function() {
          self.showServiceListDialogs();
     });
  },
   /**
    * 
      * 点击按键弹出服务列表加载数据
      * */
 showServiceListDialogs : function() {
     Eop.Dialog.open("workList");
     var url ="mblLoginAction!workList.do?ajax=yes"; 
     $("#workList").load(url,function(){
     });
 }
}; 
$(function(){
  serviceAppa.init();
});
