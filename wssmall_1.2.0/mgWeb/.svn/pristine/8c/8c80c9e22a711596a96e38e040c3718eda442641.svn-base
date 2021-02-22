var distributorList = {
    init:function(){
    var self = this;
    Eop.Dialog.init({id:"showWarehouseSeat",modal:false,title:"货位详情",height:"300px",width:"800px"});
 	//弹出货位列表对话框
     $("a[name='warehouse_seat_desc']").unbind("click").bind("click",function() {
     	  var goods_id=$(this).attr("goods_id");
     	  var house_name=$(this).attr("house_name");
     	  house_name = encodeURI(encodeURI(house_name, true), true);
          self.showdistributorListDialogs(goods_id,house_name);
     });
  },
   /**
    * 
    * 点击按键弹出货位列表加载数据
   * */
 showdistributorListDialogs : function(goods_id,house_name) {
     Eop.Dialog.open("showWarehouseSeat");
     var url ="warehouseGoodsStoreAction!warehouseSeatList.do?ajax=yes&goods_id="+goods_id+"&house_name="+house_name; 
     $("#showWarehouseSeat").load(url,function(){
     });
 }
}; 
$(function(){
 	 distributorList.init();
});
