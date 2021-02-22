$.getScript(basePath + "js/Selector.js");
var relation = {
	init : function(){
		var self = this;
        function appendGoods(goodsList) {
            self.appendGoods(goodsList);
        }

        $("#offerAddDialog").bind("click", function () {
            GoodsSelector.open("relation_selected", appendGoods);
        });
        $("a[name='deleteTc']").bind("click", function () {
            $(this).closest("tr").remove();
            var complex_num = $("#goodsRelNode tr").size();
            if(complex_num == 0){
            	$("#goodsRelDiv .remind_n_div").show();
            }
        })
	},
	appendGoods: function (goodsList) {
		if(goodsList.length>0){
			$("#goodsRelDiv .remind_n_div").hide();
		}
		var type_id = $("#type_select").val();
        for (i in goodsList) {
            var goods = goodsList[i];
            var product_id = "";//goods.product_id
            var html = '<tr>';
            html += '<td style="text-align:center;">'+goods.sku;
            html += '<input type="hidden" value="'+goods.goods_id+'" name="goodsExtData.rel_goods_ids"><input type="hidden" value="'+product_id+'" name="goodsExtData.rel_product_ids"><input type="hidden" value="" name="goodsExtData.rel_codes">';
            if("10001" == type_id){//合约关联套餐
            	html += '<input type="hidden" value="CONTRACT_OFFER" name="goodsExtData.rel_types">';
            }
            else if("10000" == type_id){//终端关联购买计划
            	html += '<input type="hidden" value="TERMINAL_PLAN" name="goodsExtData.rel_types">';
            }
            else{
            	html += '<input type="hidden" value="" name="goodsExtData.rel_types">';
            }
            html += '</td>';
            html += '<td style="text-align:center;">'+goods.name+'</td>';
            html += '<td style="text-align:center;">'+goods.stype_id+'</td>';
            html += '<td style="text-align:center;"><a href="javascript:;" name="deleteTc"><img  class="delete" src="'+ctx+'/shop/admin/images/transparent.gif" >删除</a></td>';
            html += '</tr>';
            var htmlJq = $(html);
            htmlJq.appendTo($("#goodsRelNode"));
            htmlJq.find("[name='deleteTc']").bind("click", function () {
                $(this).closest("tr").remove();
                var complex_num = $("#goodsRelNode tr").size();
                if(complex_num == 0){
                	$("#goodsRelDiv .remind_n_div").show();
                }
            })
        }
    }
};
$(function(){
	Eop.Dialog.init({id: "relation_selected", modal: false, title: "添加关联套餐", width: "730px"});
	relation.init();
})