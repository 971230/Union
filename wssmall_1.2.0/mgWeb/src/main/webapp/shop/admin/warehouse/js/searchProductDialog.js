var SearchProductDialog = {
	init : function(){
		var me = this;
		me.initClk();
	},
	initClk : function(){
		var me = this;
		$("#submitProdutsBtn").bind("click",function(){
			me.dialogSearchHouse();
		});
		$("#dialogProdConfirmBtn").bind("click",function(){
			me.confirmBtnClk();
		});
	},
	confirmBtnClk : function(){
		if($("#gridform3").find("input[type='radio']:checked").length>0){
			var td = $("#gridform3").find("input[type='radio']:checked").parent();
			if($("#clickFlag").val()=="button"){//搜索
				$("#sku_input").val($(td).find("input[name='sku']").val());
				$("#sku_desc_name").val($(td).find("input[name='house_name']").val());
			}else{
				$("#ae_house_name").val($(td).find("input[name='house_name']").val());
				$("#product_id_hidden").val($(td).find("input[name='id']").val());
				$("#product_sku_hidden").val($(td).find("input[name='sku']").val());
			}
			Eop.Dialog.close("selectProduct_dialog");
		}else{
			alert("您还没有选择货品");
		}
	},
	dialogSearchHouse : function(){
//		var me =  this;
		var flag = $("#clickFlag").val();
		var product_name = $.trim($("#product_name_input").val());
		var select_attribution = $.trim($("#select_attribution2").val());
		var sku = $.trim($("#sku").val());
		var url ="warehouseAction!searchProductList.do?ajax=yes&flag="+$("#clickFlag").val()+"&product_name="+encodeURI(encodeURI(product_name,true),true)+"&attribution="+encodeURI(encodeURI(select_attribution,true),true)+"&sku="+encodeURI(encodeURI(sku,true),true);
		
        $("#selectProduct_dialog").load(url,function(){
        	Eop.Dialog.open("selectProduct_dialog");
//        	$("#submitProdBtn").bind("click",function(){
//				me.dialogSearchHouse();
//			});
//			$("#dialogProdConfirmBtn").bind("click",function(){
//				me.confirmBtnClk();
//			});
        });
	}
};
$(function(){
	SearchProductDialog.init();
});