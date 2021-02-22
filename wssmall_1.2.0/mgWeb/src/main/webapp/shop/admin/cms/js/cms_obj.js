var CmsObj = {
		/**
		 * 展示弹出框
		 * @param res_type 	查询类型。goods-商品，adv_0-广告，adv_1-视频，notice-公告
		 * @param res_id	返回的主键，与数据库字段对应，例如goods_id
		 * @param res_value	返回的值，字段与数据库字段对应，以"-"分隔，例如name-price-img_dafault
		 * 选择确定后，将res_id res_value res_type所需要的值，返回约定的承载体中，res_id对应final_res_id，res_value对应final_res_value
		 * 最终得到id以","分隔，值以"-"分隔，多个值之间以","分隔
		 */
		showDialog:function(res_type,res_id,res_value){
			$("#cms_obj").remove();
			$("#get_res_value").remove();
			$(document.body).append("<div id='cms_obj'></div>"); 
			Eop.Dialog.open("cms_obj");
			var url = ctx+ "/shop/admin/cmsObj!getCmsObj.do?ajax=yes&res_type="+res_type
								+"&res_value="+res_value+"&res_id="+res_id;
			
			$("#cms_obj").load(url, function(){
				CmsObj.init();
			});
		},
		init:function(){
			//解绑grid事件，防止同tr下的checkbox复选
			$("#cms_obj .gridbody tbody tr").die("click");
			
			//绑定左侧li点击事件
			CmsObj.liClick();
			
			//绑定根据类型指定点击li事件
			CmsObj.typeClick();
			
			//绑定确认按钮点击事件
			CmsObj.confirmClk();
			
		},
		liClick:function(){
			$(".treeList li").unbind("click").bind("click",function(){
				$(".clk").removeClass("curr");
				$(this).addClass("curr");
				var url = $(this).attr("url");
				$("#right_panel").empty();
				$(".submitlist").show();
				$("#right_panel").load(url, function() {
					if (url.indexOf("getGoods.do") > -1) {
						CmsObj.initGoodsClk();
					}
					if (url.indexOf("getAdv.do") > -1) {
						CmsObj.initAdvClk();
					}
					if(url.indexOf("getNotice.do") > -1){
						CmsObj.initNoticeClk();
					}
				});
			});
		},
		typeClick:function(){
			var res_type = $("input[name='res_type']").val();
			if(res_type != ""){
				$("li[name='"+res_type+"']").trigger("click");
			}
		},
		initGoodsClk:function(){
			$("#goods_form #search_goods").unbind("click").click(function(){
				var url = ctx + "/shop/admin/cmsObj!getGoods.do?ajax=yes" ;
 				Cmp.ajaxSubmit('goods_form', 'right_panel', url, {}, CmsObj.initGoodsClk);
			});
		},
		initAdvClk:function(){
			var atype_val = $("#adv_form select[name='advertisement.atype']").val();
			$("#adv_form .sub_stype").hide();
			$("#adv_form .sub_stype").each(function(){
				var val = $(this).attr("a_val");
				if(atype_val == val){
					$(this).show();
					var hidden_val = $("#adv_form #hidden_subtype").val();
					$(this).find("select").val(hidden_val);
				}
			});
			$("#adv_form #search_adv").unbind("click").click(function(){
				var url = ctx + "/shop/admin/cmsObj!getAdv.do?ajax=yes" ;
 				Cmp.ajaxSubmit('adv_form', 'right_panel', url, {}, CmsObj.initAdvClk);
			});
			
			$("#adv_form select[name='advertisement.atype']").unbind("change").bind("change",function(){
				var a_val = $(this).val();
				$("#adv_form .sub_stype").hide();
				$("#adv_form .sub_stype").each(function(){
					$(this).find("select").attr("name","");
					var val = $(this).attr("a_val");
					if(a_val == val){
						$(this).show();
						$(this).find("select").attr("name","advertisement.subtype");
					}
				});
			});
		},
		initNoticeClk:function(){
			$("#notice_form #search_notice").unbind("click").click(function(){
				var url = ctx + "/shop/admin/cmsObj!getNotice.do?ajax=yes" ;
 				Cmp.ajaxSubmit('notice_form', 'right_panel', url, {}, CmsObj.initNoticeClk);
			});
		},
		confirmClk:function(){
			$("#confirm_btn").bind("click",function(){
				var res_id = $("input[name='res_id']").val();
				var res_value = $("input[name='res_value']").val();
				
				var ids = "";
				var values = "";
				
				var select_box;
				if($("#cms_obj input[type='checkbox']:checked").length > 0){
					select_box = $("#cms_obj input[type='checkbox']:checked");
				}else{
					select_box = $("#cms_obj input[type='radio']:checked");
				}
				select_box.each(function(){
					var checkbox = $(this);
					var id = checkbox.siblings("input[name='"+res_id+"']").val();
					ids += id + ",";
				});
				
				var res_values = res_value.split("-");
				var values = "";
				for ( var i = 0; i < res_values.length; i++) {
					var r_value = res_values[i];
					var each_value = "";
					select_box.each(function(){
						var checkbox = $(this);
						var data_value = checkbox.siblings("input[name='"+r_value+"']").val();
						data_value = typeof(data_value)=="undefined" ? "":data_value;
						each_value += data_value + ",";
					});
					if(each_value != ""){
						each_value = each_value.substring(0, each_value.length-1);
					}
					values += each_value + "-";
				}
				
				
				if(ids != ""){
					ids = ids.substring(0, ids.length-1); 
				}
				if(values != ""){
					values = values.substring(0, values.length-1);
				}
				confirm_return(ids,values,$("input[name='res_type']").val());
				
				Eop.Dialog.close("cms_obj");
			});
		},
		mapperClick : function(column_id){
			$("#column_" + column_id).trigger('click');
		}

}


$(function() {
	Eop.Dialog.init({id:"cms_obj", modal:false, title:"选择元素", width:"800px"});
});
