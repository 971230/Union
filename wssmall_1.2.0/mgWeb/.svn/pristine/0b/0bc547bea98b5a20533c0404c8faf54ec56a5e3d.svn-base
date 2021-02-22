var StoreManage = {
	init : function(){
		var me = this;
		me.defFormView();
		me.initClk();
	},
	//默认表单的展示
    defFormView:function(){
		$("#prod_house_id").val("");
		$("#prod_house_name").val("");
		$("#product_id_hidden").val("");
		$("#ae_house_name").val("");
		$("#product_store").val("");
		$("#no_apply_store").val("");
		
		$("#no_apply_store_th").hide();
		$("#no_apply_store_td").hide();
		$("#attribution_th").hide();
		$("#attribution_td").hide();
		$("#change_store_num_th").hide();
		$("#change_store_num_td").hide();
		
	},
	//新增时候表单的展示
    addFormView:function(){
    	var me = this;
		//清空
		$("#prod_house_name").val("");
		$("#prod_house_id").val("");
		$("#product_id_hidden").val("");
		$("#product_sku_hidden").val("");
		$("#ae_house_name").val("");
		$("#product_store").val("");
		$("#no_apply_store").val("");
		$("#ae_house_code").val("");
    	
		$("#no_apply_store_th").hide();
		$("#no_apply_store_td").hide();
		$("#attribution_th").hide();
		$("#attribution_td").hide();
		$("#change_store_num_th").hide();
		$("#change_store_num_td").hide();
		me.enableConfirmBtn();
	},
	//修改时候表单展示
	editFormView:function(){
		var me = this;
		$("#no_apply_store_th").show();
		$("#no_apply_store_td").show();
		$("#attribution_th").show();
		$("#attribution_td").show();
		$("#change_store_num_th").show();
		$("#change_store_num_td").show();
		me.disableConfirmBtn();
	},
	//选框生效
	enableSelBtn:function (){
		$("#selectHouseNameBtn").removeClass("comBtnDisable");
		$("#selectHouseNameBtn").addClass("comBtn");
		$("#selectProductBtn").removeClass("comBtnDisable");
		$("#selectProductBtn").addClass("comBtn");
	},
	//确定框生效
	enableConfirmBtn:function(){
		var me = this;
		$("#prodConfirmBtn").removeClass("comBtnDisable");
		$("#prodConfirmBtn").addClass("comBtn");
		$("#prodConfirmBtn").unbind("click").bind("click",function(){me.doAddOrEditProdStore();});
	},
	//确定框失效
	disableConfirmBtn:function(){
		$("#prodConfirmBtn").removeClass("comBtn");
		$("#prodConfirmBtn").addClass("comBtnDisable");
		$("#prodConfirmBtn").unbind("click");
	},
	initClk : function(){
		var me = this;
		me.rowClickEvent();
//		$("#selectHouseNameBtn").bind("click",function(){me.selectHouse();});
		$(".selectProductBtnTop").bind("click",function(){me.selectProduct($(this).attr("name"));});
		//按钮事件
		$("#prodaddBtn").bind("click",function(){me.addProdStore("add");});
		$("#prodEditBtn").bind("click",function(){me.EditProdStore("edit");});
		$("#prodcancelBtn").bind("click",function(){me.cancelProdOp();});
	},
	EditProdStore : function(operate){
		var me = this;
		
		if($(".grid").find("input[type='radio']:checked").length>0){
			$("#operate").val(operate);
			//禁用对应
			$("#product_store").attr("readonly","readonly");
			$("#no_apply_store").attr("readonly","readonly");
			$("#selectHouseNameBtn").unbind("click");
			$(".selectProductBtn").unbind("click");
			me.enableConfirmBtn();
		}else{
			alert("请选择一条记录再操作！");
		}
	},
	addProdStore : function(operate){
		var me = this;
		me.addFormView();
		$("#operate").val(operate);
		//取消行选中
		$(".grid").find("input[type='radio']").removeAttr("checked");
		//取消禁用
		$("#product_store").removeAttr("readonly");
		$("#no_apply_store").removeAttr("readonly");
		
		$("#selectHouseNameBtn").bind("click",function(){me.selectHouse();});
		$(".selectProductBtn").bind("click",function(){me.selectProduct($(this).attr("name"));});
	},
	doAddOrEditProdStore : function(){
		var me = this;
		var operate = $("#operate").val();
		if(operate==""){
			return ;
		}
		var house_id = $("#prod_house_id").val();
		var prod_id = $("#product_id_hidden").val();
		if(house_id!=""||prod_id!=""){
			//检查数字类型
			var storeCount = $("#product_store").val();
			var no_apply_store = $("#no_apply_store").val();
			var changeNum = $("#ae_house_code").val();
			if(!/^\d{1,8}$/.test(storeCount)){
				alert("货品库存为1到8位正整数");
				return ;
			}
			if(!/^\d{0,8}$/.test(changeNum)){
				alert("变动库存为正整数");
				return ;
			}
			if(operate=="add"){
				//alert("新增时“变动库存”将不做处理，请知悉！");
				//检查重复
				$.ajax({
					url:"warehouseAction!isExistsStore.do?ajax=yes",
					data:{house_id:house_id,id:prod_id},
					type:"post",
					dataType:"json",
					success:function(reply){
						if(reply.result==1){
							alert(reply.message);
						}else{
							$("#isae_hidden").val("add");
							var url="warehouseAction!editProdStore.do?ajax=yes";
							Cmp.ajaxSubmit('ae_form', '', url, {}, me.jsonBack,'json');
						}
					},error:function(){
					}
				});
			}else if(operate=="edit"){//修改
				alert("修改时“货品库存”和“未分配库存”由“变动库存”决定！");
				$("#isae_hidden").val("edit");
				if($("select[name='attribution']").val()=="0"){
					
					if(parseInt(changeNum)>parseInt(no_apply_store)){
						alert("出库数量不能大于未分配数量！");
						return;
					}
				}
				var url="warehouseAction!editProdStore.do?ajax=yes";
				Cmp.ajaxSubmit('ae_form', '', url, {}, me.jsonBack,'json');
			}
			$("#operate").val("");
		}else{
			alert("仓库名称和货品名称均不能为空！");
		}
	},
	cancelProdOp : function(){
		$(".addOrEditDiv").find("input.ipttxt").val("");
	},
	jsonBack : function(json){
			if (json.result == 1) {
				alert(json.message);
			}
			if (json.result == 0) {
				 alert(json.message);
				 $("#goodsStoreForm").submit();
				 //window.location.reload();
			}
	},
	/**
	 * 行点击事件
	 * */
	rowClickEvent : function(){
		var me = this;
		$(".grid").find("tr").each(function(i,data){
			$(data).bind("click",function(){
				if(!$(this).find("input[type='radio']").is(":checked")){
					$("#prod_house_id").val($(this).find("td:eq(0)").find("input[name='id']").val());
					$("#prod_house_name").val($.trim($(this).find("td:eq(1)").html()));
					$("#product_id_hidden").val($(this).find("td:eq(0)").find("input[name='product_id']").val());
					$("#ae_house_name").val($.trim($(this).find("td:eq(2)").html()));
					$("#product_store").val($.trim($(this).find("td:eq(3)").html()));
					$("#no_apply_store").val($.trim($(this).find("td:eq(4)").html()));
					me.editFormView();
				}else{
					$("#prod_house_id").val("");
					$("#prod_house_name").val("");
					$("#product_id_hidden").val("");
					$("#ae_house_name").val("");
					$("#product_store").val("");
					$("#no_apply_store").val("");
					me.addFormView();
				}
			});
		});
	},
	selectHouse : function(){
		var url = "warehouseAction!searchHouseList.do?ajax=yes";
		$("#selectHouseName_dialog").load(url,function(){
			Eop.Dialog.open("selectHouseName_dialog");
		});
	},
	selectProduct : function(btnName){
		var url = "warehouseAction!searchProductList.do?ajax=yes&flag="+btnName;
		$("#selectProduct_dialog").load(url,function(){
			Eop.Dialog.open("selectProduct_dialog");
		});
	}
};
$(function(){
	StoreManage.init();
	Eop.Dialog.init({id:"selectHouseName_dialog",modal:false,title:"选择物理仓",width:"900px"});
	Eop.Dialog.init({id:"selectProduct_dialog",modal:false,title:"选择货品",width:"800px"});
});