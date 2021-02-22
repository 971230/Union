var Brand=$.extend({},Eop.Grid,{
	init:function(){
		var self =this;
		$("#delBtn").click(function(){self.doDelete();});
		$("#cleanBtn").click(function(){self.doClean();	});
		$("#revertBtn").click(function(){self.doRevert();});
		$("#toggleChk").click(function(){
			self.toggleSelected(this.checked);}
		);
		$("#confirmBrandBtn").click(function(){
            self.addSelecteBrand();
		});
        //单选框双击选择
    	$("#brankSelectList").find(".gridbody tbody tr").live("dblclick",function(){
        	var box = $(this).find("input[type='checkbox'],input[type='radio']");
        	if($(box).attr("type") == "radio"){
        		$(this).siblings("tr").removeClass("grid-table-row-selected");
        		$(this).addClass("grid-table-row-selected");
        		box.attr("checked",true);
        		self.addSelecteBrand();
        	}
    	});
    	
    	//全量发布
    	$("#publishBrand").click(function(){
    		self.doPublish();
    	});
	},
	
	//全量发布
	doPublish:function() {
		
    	if (!confirm("确定要执行全量发布品牌的操作吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: "brand!doPublish.do",
			 data: "ajax=yes",
			 dataType:"json",
			 success: function(result) {
				 if (result.result == '0') {
					 alert(result.message);
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	},
	
	addSelecteBrand:function(){
		var brand_id = $("input[name='brandid']:checked").val();
		var name = $("input[name='brandid']:checked").attr("nameValue");
		if(!brand_id){
			alert("请选择品牌");
			return ;
		}
		$("#searchGoodsBrandInput").val(brand_id);
		$(".searchformDiv input[name='brand_name']").val(name);
		Eop.Dialog.close("BrandQuickBtn_dialog");
	},
	doDelete:function(){
		
		if(!this.checkIdSeled()){
			alert("请选择要删除的品牌");
			return ;
		}
		
		var self= this;
		$("form").ajaxSubmit({
			url:'brand!checkUsed.do?ajax=yes',
			type:'POST',
			dataType:'json',
			success:function(result){
				if(result.result==1){
					if(confirm("要删除的品牌已经关联商品，确认要删除吗？如果删除对应的商品将不显示品牌。")){
						$.Loading.hide();
						$.Loading.show("正在将品牌放入回收站...");
						self.deletePost("brand!delete.do");
					 
					}
					$.Loading.hide();
				} else{
					if(confirm("确认要将这些品牌放入回收站吗？")){
						$.Loading.hide();
						$.Loading.show("正在将品牌放入回收站...");
						self.deletePost("brand!delete.do");
					}
					$.Loading.hide();
				} 
			},error:function(){
				alert("检测关联性出错");
			}
		});		
		
	},
	doClean:function(){
		if(!this.checkIdSeled()){
			alert("请选择要删除的品牌");
			return ;
		}
	 
		if(!confirm("确认要将这些品牌彻底删除吗？删除后将不可恢复")){	
			return ;
		}
		this.deletePost("brand!clean.do");
	},
	
	doRevert:function(){
		if(!this.checkIdSeled()){
			alert("请选择要还原的品牌");
			return ;
		}
	 
		this.deletePost("brand!revert.do","选择的品牌已被成功还原至品牌列表中");		
	}
	
});

var BrandInput={
		init:function(){
			$(".submitlist .submitBtn").click(function(){
				$.Loading.show("正在检测品牌名是否重复...");
				var name = $("#name").val();
				$("form").ajaxSubmit({
					url:'brand!checkname.do?ajax=yes',
					type:'POST',
					dataType:'json',
					success:function(result){
						var form = $("form");
						if(result.result==1){
							if(confirm("品牌"+name+"已经存在，您确定要保存吗？")){
								$.Loading.hide();
							
								form.submit();							
							}
							$.Loading.hide();
						} else{
							$.Loading.hide();
							form.submit();			
						} 
					},error:function(){
						alert("检测名称出错");
					}
				});
			});	
		}
};

BrandModel = {
	init : function(){
		$("#search_machine").bind("click",function(){
			var url ="brandModel!brandModelList.do?ajax=yes";
			$("#brand_model_dialog").load(url,function(responseText) {
				Eop.Dialog.open("brand_model_dialog");
			});	
		});
	}
};
$(function(){

	Brand.init();
	BrandModel.init();
	Eop.Dialog.init({id:"brand_model_dialog",modal:false,title:"选择机型",width:"900px"});
});