var Param={
		inputHtml:undefined,
		init:function(){
			var self=this;
			this.initInputHtml();
			
			//添加一个组
			$("#paramAddBtn").click(function(){self.addGroupInput();});
			
			//删除组
			$("tr.group .delete").click(function(){
				$(this).parents("table").remove();
			});
			
			//删除参数项
			$("tr.param .delete").click(function(){
				$(this).parents("tr").remove();
			});
			
			$("tr.group>td a.addBtn").click(function(){
				self.addParamItemInput( $(this).parents("table") );	
			});
			$("#saveParamsBtn").click(function(){
				$("#param_div>table").each(function(){
					var table = $(this);
					var param_num = table.find("tr.param").size();
					alert(param_num);
					table.find("input[name='paramnums']").val(param_num);
				})
				Cmp.ajaxSubmit("paramsEditForm", "", "type!saveParamsEdit.do?ajax=yes", {}, function(responseText){
					if(responseText.result=="0"){
						Eop.Dialog.close("paramsEditDialog");
						alert("参数添加成功");
						$.ajax({
							type: "get",
							url:app_path+"/shop/admin/type!disParamsInputN.do?ajax=yes",
							data:"type_id=" + responseText.type_id +"&m=" + new Date().getTime(),
							dataType:"html",
							success:function(result){
								   $("#goods_params .grid_n_cont").empty().append(result);
							},
							error :function(res){alert("异步读取商品参数失败:" + res);}
						});
					}
				},"json");
			})
		},
		
		/*
		 * 初始化属性输入模板html
		 */
		initInputHtml:function(){
			var self=this;
			$.ajax({
				 type: "POST",
				 url: "type/param_input_item.jsp?ajax=yes",
				 dataType:'html',
				 success: function(html){ 
					self.inputHtml=html;
				 },
				 error:function(){
					 alert("抱歉，参数模板加载失败，请重试..");
				 }
				});
			
		}
		,
		/*
		 * 增加一个参数组输入项
		 */
		addGroupInput:function(){
			var self=this;
			var group=$(this.inputHtml).appendTo($("#param_div"));
			group.find("tbody>tr.group>td .delete").click(function(){
				$(this).parents("table").remove();
			});
			
			group.find("tbody>tr.group>td .addBtn").click(function(){
				self.addParamItemInput( $(this).parents("table") );
			});
			
			group.find("tbody>tr>td.attrvalselect").find("select").change(function(){
				if($(this).val()=="0"){
					$(this).closest("tr").find("td.isSelect").css("visibility","hidden");
				}else{
					$(this).closest("tr").find("td.isSelect").css("visibility","visible");
					
				}
			});
		},
		
		/*
		 * 增加一个参数输入项
		 */
		addParamItemInput:function(table){
			var me = this;
			$(this.inputHtml).find("tbody>tr:eq(1)")
			.insertAfter(table.find("tbody>tr:last"))
			.find("td .delete").click(function(){
				$(this).parents("tr").remove();
			});
			me.bindChange();
		},
		bindChange : function(){
			$("#param_div").find("table").each(function(i,data){
				
				$(data).find("tbody>tr>td.attrvalselect").find("select").change(function(){
					if($(this).val()=="0"){
						$(this).closest("tr").find("td.isSelect").css("visibility","hidden");
					}else{
						$(this).closest("tr").find("td.isSelect").css("visibility","visible");
					}
				});
			});
		}
		
};