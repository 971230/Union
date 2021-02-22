$(function() {
	GoodsAddEdit.init();
	//tab页切换
	$(".goods_input .tab>li").click(function() {
			var tabid = $(this).attr("tabid");
			$(".goods_input .tab>li").removeClass("active");
			$(this).addClass("active");
			$(".tab-page .tab-panel").hide();
			$("div[tabid=tab_" + tabid + "]").show();
	});
	
	//验证
	$("form.validate").validate();
	$("input[type=text]").attr("autocomplete", "off");
	if (CKEDITOR.instances['intro']) {
		CKEDITOR.remove(CKEDITOR.instances['intro']);
	}
	$('#intro').ckeditor();
	$("input[type=submit]").click(function() {
		$.Loading.text = "正在生成缩略图，请稍候...";
	});
});
var GoodsAddEdit={
	init : function() {
		$("[tabid='no']").appendTo($(".append_tab_div"));
		
		//根据类型展示插件桩
		$("#stype_id").bind("change",function(){
			var stype_code = $(this).find("option:selected").attr("stype_code");
			var tab_div = $("div[style_code='"+stype_code+"']");
			//alert(stype_code);
			//add by wui tab页面
			$("[tabid='no']").hide();
			$("[li_type='stype']").hide();
			if(stype_code != ""){
				$("li[li_stype_code='"+stype_code+"']").show().trigger("click");
			}
			tab_div.length>0 && tab_div.show();
		});
		var self = this;
		var action=$("#theForm").attr("action");
		$("#theForm.validate").validate();
		//提交表单
		$('.greenbtnbig').click(function(){
		
			try{
				//GoodsArea.beforeSave();
			}catch(e){}
		
			var sn=$("input[name='goods.sn']").val();
			var goods_id=$("input[name='goods.goods_id']").val();
			
			
			var p_index =0;
			$("table[name='pTable']").each(function(){
				$(this).find("[p_attr='ad_product_id']").each(function(){
					$(this).attr("name","productid_"+p_index);
				})
				p_index++;
			})
			
			if(goods_id == undefined || goods_id == null){
				goods_id = '';
			}
			
			//统一设计规格价格序列 add by wui 
			$(".member_price_box").each(function(index){
				$(".lvid",this).attr("name","lvid_"+(new Number(index)));
				$(".lvPrice",this).attr("name","lvPrice_"+(new Number(index)));
				//alert($(this).html())
			})
			
			//组合【商家_分类_品牌_商品名称】search_key
			var search_key = "";
			var cat_id = $("#cat_id option:selected").text();//商品分类
			if(cat_id != undefined && cat_id != null){
				search_key += "_"+jQuery.trim(cat_id)+"_";
			}
			var brand_id = $("#brand_id option:selected").text();//商品品牌
			if(brand_id != undefined && brand_id != null && "选择品牌" != brand_id){
				search_key += "_"+jQuery.trim(brand_id)+"_";
			}
			var goods_name = $("#goods_name").val();//商品名称
			if(goods_name != undefined && goods_name != null){
				search_key += "_"+jQuery.trim(goods_name)+"_";
			}
			$("#search_key").val(search_key);
			
			if(action=="goods!saveAdd.do"){
				var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
				Cmp.ajaxSubmit('theForm', '', url, {}, self.jsonBack,'json');
							
			}else{//修改商品信息
				var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
				Cmp.ajaxSubmit('theForm', '', url, {}, self.jsonBack,'json');
			} 

		});
		
		$("#turnToImport").bind("click",function(){
				alert("请选择商品类型！");
		});	
		//解决"上传"按钮总在最前
		$('.closeBtn').click(function(){
			$('#up').parent().show();
		});
	},
	jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			alert("操作成功");
			window.location.href="goods!list.do";
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	}
}
