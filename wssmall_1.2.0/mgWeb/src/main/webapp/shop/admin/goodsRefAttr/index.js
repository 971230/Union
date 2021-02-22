var addModulepro = {
  init:function(){
    var self = this;
    Eop.Dialog.init({id:"listAllGoods",modal:true,title:"商品列表",width:'750px'});
    Eop.Dialog.init({id : "addProperty",title : "属性管理" ,width:"380px", height:"450px"});
    //弹出服务名称列表对话框
	$("#allGoodsBtn").unbind("click").bind("click",function() {
		self.showAddPrperty();
	});
	$('#addProperty').unbind("click").bind('click', function () {
        self.addPro();
     });
  },
   /**
    * 
	 * 点击按键弹出服务列表加载数据
	 * */
 showAddPrperty : function() {
 	 var url =app_path + "/shop/admin/goodsPropertyAction!listAllGoods.do?ajax=yes";
     Eop.Dialog.open("listAllGoods"); 
     $("#listAllGoods").load(url,function(){});
 },
 addPro: function () {
     var editGoodsId=$('#editGoodsId').val();
     $("#addGoodsProperties").load('goodsPropertyAction!saveProperty.do?ajax=yes&editGoodsId='+editGoodsId, function () {
				
     });
     Eop.Dialog.open("addGoodsProperties"); 
 
},
}
$(function(){
	addModulepro.init();
});
   