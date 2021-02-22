var distributorList = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"showWarehouseDiog",modal:false,title:"仓库",height:"800px",width:"800px"});
 	//弹出仓库列表对话框
     $("#choiceBotton1").unbind("click").bind("click",function() {
          self.showdistributorListDialogs();
     });
  },
   /**
    * 
      * 点击按键弹出仓库列表加载数据
      * */
 showdistributorListDialogs : function() {
     Eop.Dialog.open("showWarehouseDiog");
     var url ="warehouseSeatAction!showWarehouseList.do?ajax=yes"; 
     $("#showWarehouseDiog").load(url,function(){
     });
 }
}; 
$(function(){
 	 distributorList.init();
 	 $("#house_name_btn").focus(function(){
		distributorList.showdistributorListDialogs();
	 });
});
