var distributorList = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"goodsShelvesDlg",modal:false,title:"货位",height:"800px",width:"800px"});
 	//弹出货位列表对话框
     $("#choiceBotton1").unbind("click").bind("click",function() {
          self.showdistributorListDialogs();
     });
  },
   /**
    * 
    * 点击按键弹出货位列表加载数据
   * */
 showdistributorListDialogs : function() {
     Eop.Dialog.open("goodsShelvesDlg");
     var url ="warehouseSeatAction!findGoodsSeat.do?ajax=yes"; 
     $("#goodsShelvesDlg").load(url,function(){
     });
 }
}; 
$(function(){
 	 distributorList.init();
 	 $("#seatNameBtn").focus(function(){
		distributorList.showdistributorListDialogs();
	 });
});
