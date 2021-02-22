var assginWin = {
		
		init:function(){
			var self =this;
			
			if(type == 'product'){
				$("#win_pro_or_goods_name").html('<span class="red">*</span>货品名称：');
			}else{
				$("#win_pro_or_goods_name").html('<span class="red">*</span>商品名称：');
			}
			$("#assign_pro_win").find("input[name='select_virtual']").click(function() {
				self.showVitrtaultWare();
			});
			$("#goodsInventoryApplyBtn").click(function(){
				self.apply();
			});
			
		},
		//选择虚拟仓
		showVitrtaultWare:function(params){
		 	 var url = "warehouseAssign!selectVWareList.do?ajax=yes";
		     $("#sel_virtual_div").load(url,params,function(){

		     });
		     Eop.Dialog.open("sel_virtual_div"); 
		},
		//保存分配验证表单
		checkApply:function(){
			var v_house_id = $("#assign_form").find("input[name='apply.virtual_house_id']").val();
			var org_id = $("#assign_form").find("input[name='apply.org_id']").val();
			var inventory_num = $("#assign_form").find("input[name='apply.inventory_num']").val();
			var no_apply_num = $("#assign_form").find("input[name='apply.no_apply_num']").val();
			
			if(!v_house_id){
				alert("请选择虚拟仓！");
				return false;
			}
			if(!org_id){
				alert("请选择有商城的虚拟仓！");
			    return false;
		    }
		    if(!inventory_num){
				alert("请输入分配数量！");
			    return false;
		    }else{
		    	if(!no_apply_num || parseInt(inventory_num)>parseInt(no_apply_num) ){
					alert("您输入的分配数量要小于等于可分配数量！");
				    return false;
		    	}
		    	if(parseInt(inventory_num)<=0){
		    		alert("您输入的分配数量不能小于等于0！");
				    return false;
		    	}
		    }
            return true;
			
		},
		//保存分配
		apply:function(){
			var self =this;
			if(!self.checkApply()){
				return;
			}
			var params = $("#assign_form").serialize();
			params +="&apply.type="+type;
			
			var is_share = $("#v_attr_inline_type").val();
			params +="&apply.is_share="+is_share;

			var url = "warehouseAssign!saveInventoryApply.do?ajax=yes&"+params;
			//alert(url);

			$.ajax({
				async:false,
				type : "POST",
				dataType : 'json',
				url : url,
				success : function(result) {
					if(result){
				    	if(result.result=='true'){
				    		alert("分配成功!");
				    		
				    		if(type == "product"){
				    			WarehouseProduct.showAssignPro();
				    			Eop.Dialog.close("assign_pro_div");
				    			
				    		}else if(type == "goods"){
				    			WarehouseGoods.showAssignGoods();
				    			Eop.Dialog.close("assign_goods_div");
				    		}
				    		
				    	}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert("出错了");
				}
			}); 
		}
};
assginWin.init();