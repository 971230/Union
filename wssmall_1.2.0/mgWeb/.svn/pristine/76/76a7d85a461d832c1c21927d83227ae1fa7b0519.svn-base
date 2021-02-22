var distributorList = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"showWarehouseDiog",modal:false,title:"仓库",height:"800px",width:"800px"});
  },
   /**
    * 
      * 点击按键弹出仓库列表加载数据
      * */
 showdistributorListDialogs : function() {
     Eop.Dialog.open("showWarehouseDiog");
     var url ="warehouseGoodsStoreAction!showWarehouse.do?ajax=yes"; 
     $("#showWarehouseDiog").load(url,function(){
     });
 }
}; 
$(function(){
 	 distributorList.init();
 	 $("#house_name").focus(function(){
		distributorList.showdistributorListDialogs();
	 });
});
