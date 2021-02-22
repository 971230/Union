$.getScript(basePath + "js/Selector.js");
var Complex = {
    init: function () {
        this.bindEvent();
        var self = this;

        function appendGoods(goodsList) {
            self.appendGoods(goodsList);
        }

        $("#complexOpenDialog").bind("click", function () {
            GoodsSelector.open("complex_selected", appendGoods);
        });

    },
    openDialog:function(conid,onConfirm){
    	var self = this;
    	$("#"+conid).load(ctx+'/shop/admin/selector!goods.do?ajax=yes',function(){
			self.init(conid,onConfirm);
		});
		Eop.Dialog.open(conid);	
    },
    bindEvent: function () {
        $("#complexContent .delete").unbind("click");
        $("#complexContent .delete").bind("click", function () {
            $(this).parents("tr").remove();
        });
    },
    appendGoods: function (goodsList) {
    	$("#goodsComplexDiv .remind_n_div").hide();
        for (i in goodsList) {
            var goods = goodsList[i];
            var html = "<tr id='complex_" + goods.goods_id + "'><td><a href='javascript:void(0);' class='delete'></a></td><td style='text-align:center;'>" + goods.name + "<input type='hidden' name='goodsExtData.complex_goods_ids' value='" + goods.goods_id + "' /></td><td style='text-align:center;'><select  class='ipttxt'   name='goodsExtData.manual'><option value='left' selected>单向相关</option><option value='both' >双向相关</option></select></td>"
                    + "<td style='text-align:center;'><a href='javascript:void(0)' name='delComplex'><img src='"+ctx+"/shop/admin/goodsN/images/ico_close.png' width='16' height='16' />删除</a></td>"
                    + "</tr>";
            var htmlJq = $(html);
            htmlJq.appendTo($("#complexContent"));
            htmlJq.find("[name='delComplex']").bind("click", function () {
                $(this).closest("tr").remove();
                var complex_num = $("#complexContent tr").size();
                if(complex_num == 0){
                	$("#goodsComplexDiv .remind_n_div").show();
                }
            })
        }
        this.bindEvent();
    }
};

Eop.Dialog.init({id: "complex_selected", modal: false, title: "添加相关商品", width: "730px"});
Complex.init();
$("#complexEmpty").bind('click', function () {
    $("#complexContent").empty();
})
