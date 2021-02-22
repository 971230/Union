var Prop={
	inputHtml:undefined,
	init:function(){
		var self=this;
		this.initinputHtml();
		$("#propAddBtn").click(function(){self.addPropInput();});
		
		$("#props_table  .delete").click(function(){
			$(this).parents("tr").remove();
		});
		$("#savePropsBtn").click(function(){
			var result = true;
			$("[name=propnames]").each(function(){
				 if($.trim($(this).val()) == ''){
					 result =false;
				 }
			});
			
			if(!result){
				alert("请填写属性名");
				return false;
			}
			Cmp.ajaxSubmit("propsEditForm", "", "type!savePropsEdit.do?ajax=yes", {}, function(responseText){
				if(responseText.result=="0"){
					Eop.Dialog.close("goodsTypeEditDialog");
					alert("属性添加成功");
					$.ajax({
						type: "get",
						url:app_path+"/shop/admin/type!disPropsInputN.do?ajax=yes",
						data:"type_id=" + responseText.type_id +"&m=" + new Date().getTime(),
						dataType:"html",
						success:function(result){
							   $("#goods_props .grid_n_cont").empty().append(result);
						},
						error :function(res){alert("异步读取商品属性失败:" + res);}
					});
				}
			},"json");
		})
		$("#theForm").submit(function(){
			var result = true;
			$("[name=propnames]").each(function(){
				 if($.trim($(this).val()) == ''){
					 result =false;
				 }
			});
			
			if(!result){
				alert("请填写属性名");
				return false;
			}
			return true;
		});
	},
	
	/*
	 * 初始化属性输入模板html
	 */
	initinputHtml:function(){
		var self=this;
		$.ajax({
			 type: "POST",
			 url: "type/prop_input_item.jsp?ajax=yes",
			 dataType:'html',
			 success: function(html){ 
				self.inputHtml=html;
			 },
			 error:function(){
				 alert("抱歉，属性模板加载失败，请重试..");
			 }
			});
		
	}
	,
	/*
	 * 增加一个属性输入项
	 */
	addPropInput:function(){
		$(this.inputHtml).insertAfter($("#props_table>tbody>tr:last"))
		.find("td .delete").click(function(){
			$(this).parents("tr").remove();
		});
	}
};