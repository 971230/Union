$(function () {
    //初始化商品会员价
    var goods_id = $("#goods_id").val();
    var datas = "";
    if (goods_id)
        datas = "goodsid=" + goods_id;
    $("select[name='goodsExtData.lv_discount']").change(function(){
		var mkprice = $("input[name='goods.mktprice']").val();
		if(!mkprice)
			mkprice = 0;
		var dd = $(this).val();
		$(this).closest("tr").find("input[name='goodsExtData.lvPrice']").val((mkprice*dd).toFixed(2));
    });
    $("input[name='goods.mktprice']").bind("blur",function(){
    	var lvprices = $("input[name='goodsExtData.lvPrice']");
		var mkprice = $("input[name='goods.mktprice']").val();
		if(!mkprice)
			mkprice = 0;
		$.each(lvprices,function(idx,item){
			var dd = $(item).closest("tr").find("select").val();
			if(dd!=-1){
				$(item).val((mkprice*dd).toFixed(2));
			}
		});
	});
    
})