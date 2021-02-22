var pageName = "assign"; //assign 待分配页面  assigned 已分配页面
var isInitAssign = false;
var isInitAssigned = false;

$(function(){
	
});


//切换tab页面
var productTabs ={

		init:function(){
			new Tab(".partner_detail");
			this.bindTabEvent();			
			Eop.Dialog.init({id:'select_pro_div',modal:true,title:'货品',width:'850px'});
			Eop.Dialog.init({id:'assign_pro_div',modal:true,title:'货品分配',width:'850px'});
			Eop.Dialog.init({id:'recover_pro_div',modal:true,title:'货品回收',width:'850px'});
			Eop.Dialog.init({id:'selectShop_dialog',modal:true,title:'销售组织',width:'850px'});
			Eop.Dialog.init({id:'query_sel_vir_dialog',modal:true,title:'虚拟仓',width:'850px'});
			Eop.Dialog.init({id:'sel_virtual_div',modal:true,title:'虚拟仓',width:'650px'});
		},
		/**
		 * 绑定tab事件
		 */
		bindTabEvent:function(){
			var self = this;
			$("#assign").click(function(){
			    if($("#assignTab").html()){
			       return ;
			    }else{
			        self.showAssign(); 
			    }
			});
			$("#assigned").click(function(){ 
			   if($("#assignedTab").html()){
			      return ;
			   }else{
			      self.showAssigned();
			   }
			});
			self.showAssign();
		},
		
		showAssign:function(params){
			pageName = "assign";
			$("#assignTab").empty();
			$("#assignTab").load("warehouseAssign!wareProductList.do?ajax=yes",params,function(){
			    $('input.dateinput').datepicker();
			});
		},
		
		showAssigned:function(params){
			pageName = "assigned";
			$("#assignedTab").empty();
		   $("#assignedTab").load("warehouseAssign!assignedProductList.do?ajax=yes",params,function(){
				$('input.dateinput').datepicker();
			});
		}
		
};

var goodsTabs ={
		init:function(){
			new Tab(".partner_detail");
			this.bindTabEvent();			
			Eop.Dialog.init({id:'select_goods_div',modal:true,title:'商品',width:'850px'});
			Eop.Dialog.init({id:'assign_goods_div',modal:true,title:'商品分配',width:'850px'});
			Eop.Dialog.init({id:'recover_goods_div',modal:true,title:'商品回收',width:'850px'});
			Eop.Dialog.init({id:'selectShop_dialog',modal:true,title:'销售组织',width:'850px'});
			Eop.Dialog.init({id:'query_sel_vir_dialog',modal:true,title:'虚拟仓',width:'850px'});
			Eop.Dialog.init({id:'sel_virtual_div',modal:true,title:'虚拟仓',width:'650px'});
		},
		/**
		 * 绑定tab事件
		 */
		bindTabEvent:function(){
			var self = this;
			$("#assign").click(function(){
			    if($("#assignTab").html()){
			       return ;
			    }else{
			        self.showAssign(); 
			    }
			});
			$("#assigned").click(function(){ 
			   if($("#assignedTab").html()){
			      return ;
			   }else{
			      self.showAssigned();
			   }
			});
			self.showAssign();
		},
		
		showAssign:function(params){
			pageName = "assign";
			$("#assignTab").load("warehouseAssign!wareGoodsList.do?ajax=yes",params,function(){
			    $('input.dateinput').datepicker();
			});
		},
		showAssigned:function(params){
			pageName = "assigned";
		   $("#assignedTab").load("warehouseAssign!assignedGoodsList.do?ajax=yes",params,function(){
				$('input.dateinput').datepicker();
			});
		}
		
};



/**
库存分配
*/

var Warehouse = {
	
		openAssignWin:function(item,div){
			
			//获取行数据,并放到表单里
			 var row = $(item).closest("tr");
			 var rowData = {};
			 rowData["apply.product_id"] = $(row).find("input[name='product_id']").val();
			 rowData["apply.house_id"] = $(row).find("input[name='house_id']").val();
			 rowData["apply.goods_id"] = $(row).find("input[name='goods_id']").val();
			 rowData["apply.sku"] = $(row).find("input[name='sku']").val();
			 rowData["apply.house_name"] = $(row).find("span[name='house_name']").text();
			 rowData["apply.name"] = $(row).find("span[name='name']").text();
			 rowData["apply.no_apply_num"] = $(row).find("span[name='no_apply_num']").text();

		 	 var url =app_path + "/shop/admin/warehouse/assign_product_win.jsp?ajax=yes";
		 	 $("#"+div).empty();
		     $("#"+div).load(url,function(){
		    	 $("#assign_pro_win").find("input").each(function(idx,inputItem){
		    		 var inputName = $(inputItem).attr("name");
		    		 $(inputItem).val(rowData[inputName]);
		    	 });
		     });
		     
		     
		     Eop.Dialog.open(div); 
		},
		openRecoverWin:function(item,div){
			
			 //获取行数据,并放到表单里
			 var row = $(item).closest("tr");
			 var rowData = {};
			 rowData["recover.house_id"] = $(row).find("input[name='house_id']").val();
			 rowData["recover.virtual_house_id"] = $(row).find("input[name='virtual_house_id']").val();
			 rowData["recover.org_id"] = $(row).find("input[name='org_id_str']").val();
			 rowData["recover.goods_id"] = $(row).find("input[name='goods_id']").val();
			 rowData["recover.product_id"] = $(row).find("input[name='product_id']").val();	 
			 rowData["recover.sku"] = $(row).find("input[name='sku']").val();
			 //rowData["recover.org_id"] = $(row).find("input[name='org_id']").val();
			 rowData["recover.house_name"] = $(row).find("span[name='house_name']").text();
			 rowData["recover.is_share"] = $(row).find("span[name='is_share']").text();
			 rowData["recover.v_house_name"] = $(row).find("span[name='v_house_name']").text();
			 rowData["recover.name"] = $(row).find("span[name='name']").text();
			 rowData["recover.inventory_num"] = $(row).find("span[name='inventory_num']").text();
			 rowData["inventory_num"] = $(row).find("span[name='inventory_num']").text();
			 rowData["recover.org_name"] = $(row).find("input[name='org_name_str']").val();
			 
			 
		 	 var url =app_path + "/shop/admin/warehouse/recover_product_win.jsp?ajax=yes";
		     $("#"+div).load(url,function(){
		    	 $("#recover_pro_win").find("input").each(function(idx,inputItem){
		    		 var inputName = $(inputItem).attr("name");
		    		 $(inputItem).val(rowData[inputName]);
		    	 });
		    	 $("#recover_pro_win").find("select[name='recover.is_share']").val(rowData["recover.is_share"]);

		     });
		     
		     
		     Eop.Dialog.open(div); 
		}	
};
//货品分配
var WarehouseProduct = {
	//初始化待分配页面
	initAssign:function(){
		//alert("initAssign");
		var self =this;
		
		$("#select_pro_btn").unbind("click").bind("click",function() {
			self.showProduct();
		});
		$("#query_house_btn").unbind("click").bind("click",function() {
			self.showAssignPro();
		});
		//分配按钮事件
		$("#assign_pro_list a[name='assign']").live("click",function(){
			Warehouse.openAssignWin(this,"assign_pro_div");
		});
		
	},
	//初始化已分配页面
	initAssigned:function(){
		//alert("initAssigned");
		var self =this;
		//Eop.Dialog.init({id:'v_select_pro_div',modal:true,title:'货品',width:'850px'});
		
		
		$("#v_select_pro_btn").unbind("click").bind("click",function() {
			self.showProductV();
		});
		$("#v_query_house_btn").unbind("click").bind("click",function() {

			self.showAssignedPro();
		});
		//回收按钮事件
		$("#recover_pro_list a[name='recover']").live("click",function(){
			Warehouse.openRecoverWin(this,"recover_pro_div");
		});
		//选择销售组织
		$("#select_org_input").bind("click",function(){self.selectOrg();});
		//选择虚拟仓
		$("#query_sel_vir_btn").bind("click",function(){self.showQueryVir();});
	},
	//弹出货品选择框
	showProduct:function(name){
	 	 var url = "warehouseAssign!selectProductList.do?ajax=yes";
	 	 if(!name){
	 		name="";
	 	 }
	     $("#select_pro_div").load(url,{name:name},function(){});
	     Eop.Dialog.open("select_pro_div"); 
	},
	//弹出货品选择框
	showProductV:function(name){
	 	 var url = "warehouseAssign!selectProductList.do?ajax=yes";
	 	 if(!name){
	 		name="";
	 	 }
	     $("#select_pro_div").load(url,{name:name},function(){});
	     Eop.Dialog.open("select_pro_div"); 
	},
	//展示货品库存
	showAssignPro:function(){
		var params = {};
		params["goodsInventory.house_id"] = $("#assign_pro_list").find("#house_id").val();
		params["goodsInventory.product_id"] = $("#assign_pro_list").find("#product_id").val();
		params["goodsInventory.product_name"] = $("#assign_pro_list").find("#product_name").val();
		
		productTabs.showAssign(params);
	},
	//展示已分配的货品库存
	showAssignedPro:function(){
		var params = {};
		var form  = $("#recover_pro_list form[name='searchFrom']");
		params["apply.org_id"] = $(form).find("input[name='apply.org_id']").val();
		params["virtualWarehouse.org_name_str"] = $(form).find("input[name='virtualWarehouse.org_name_str']").val();
		params["apply.goods_id"] = $(form).find("input[name='apply.goods_id']").val();
		params["name"] = $(form).find("input[name='name']").val(); 
		params["apply.house_id"] = $(form).find("select[name='apply.house_id']").val();
		params["apply.virtual_house_id"] = $(form).find("input[name='apply.virtual_house_id']").val();
		params["virtual_house_name"] = $(form).find("input[name='virtual_house_name']").val();
		
		productTabs.showAssigned(params);
	},
	//已分配货品--选择销售组织
	selectOrg : function(){
		//alert("selectOrg");
		var url = "warehouseAssign!selectSaleOrg.do?ajax=yes";
		$("#selectShop_dialog").load(url,function(resp){
			Eop.Dialog.open("selectShop_dialog");
			if($("#ae_attribution").val()=="0"){
				$("#dialog_isSingle").val("0");
			}else{
				$("#dialog_isSingle").val("1");
			}
		});
	},		
	//已分配货品--选择虚拟仓
	showQueryVir:function(params){
	 	 var url = "warehouseAssign!selectVirList.do?ajax=yes";
	     $("#query_sel_vir_dialog").load(url,params,function(){});
	     Eop.Dialog.open("query_sel_vir_dialog"); 
	}

};

//商品分配
var WarehouseGoods = {
		
		//初始化待分配页面
		initAssign:function(){
			//alert("initAssign");
			var self =this;

			$("#select_goods_btn").unbind("click").bind("click",function() {
				self.showGoods();
			});	
			$("#query_house_btn").unbind("click").bind("click",function() {
				self.showAssignGoods();
			});
			//分配按钮事件
			$("#assign_goods_list a[name='assign']").live("click",function(){
				Warehouse.openAssignWin(this,"assign_goods_div");
			});
		},
		//初始化已分配页面
		initAssigned:function(){
			//alert("initAssigned");
			var self =this;
			//Eop.Dialog.init({id:'select_goods_div',modal:true,title:'商品',width:'850px'});

			$("#v_select_goods_btn").unbind("click").bind("click",function() {
				self.showGoodsV();
			});	
			$("#v_query_house_btn").unbind("click").bind("click",function() {
				self.showAssignedGoods();
			});
			//回收按钮事件
			$("#recover_goods_list a[name='recover']").live("click",function(){
				Warehouse.openRecoverWin(this,"recover_goods_div");
			});
			//选择销售组织
			$("#select_org_input").bind("click",function(){self.selectOrg();});
			//选择虚拟仓
			$("#query_sel_vir_btn").bind("click",function(){self.showQueryVir();});
		},
		//选择商品
		showGoods:function(name){
			 if(!name) name = "";
		 	 var url = "warehouseAssign!selectGoodsList.do?ajax=yes";
		 	 $("#select_goods_div").empty();
		     $("#select_goods_div").load(url,{name:name},function(){});
		     Eop.Dialog.open("select_goods_div"); 
		},
		//选择商品
		showGoodsV:function(name){
			 if(!name) name = "";
		 	 var url = "warehouseAssign!selectGoodsList.do?ajax=yes";
		 	 $("#select_goods_div").empty();
		     $("#select_goods_div").load(url,{name:name},function(){});
		     Eop.Dialog.open("select_goods_div"); 
		},
		//展示待分配的商品库存
		showAssignGoods:function(){
			var params = {};
			var goods_id = $("#warehouseForm").find("input[name='goodsInventory.goods_id']").val();
			if(!goods_id){
				alert("请选择商品！");
				return;
			}
			params["goodsInventory.goods_id"] = goods_id;
			params["goodsInventory.house_id"] = $("#warehouseForm").find("select[name='goodsInventory.house_id']").val();
			
			params["goodsInventory.product_id"] = $("#warehouseForm").find("input[name='goodsInventory.product_id']").val();
			params["goodsInventory.product_name"] = $("#warehouseForm").find("input[name='goodsInventory.product_name']").val();
			
			
			goodsTabs.showAssign(params);
		},
		//展示已分配的商品库存
		showAssignedGoods:function(){
			var params = {};
			var form  = $("#recover_goods_list form[name='searchFrom']");
			params["apply.org_id"] = $(form).find("input[name='apply.org_id']").val();
			params["virtualWarehouse.org_name_str"] = $(form).find("input[name='virtualWarehouse.org_name_str']").val();
			params["apply.goods_id"] = $(form).find("input[name='apply.goods_id']").val();
			params["name"] = $(form).find("input[name='name']").val(); 
			params["apply.house_id"] = $(form).find("select[name='apply.house_id']").val();
			params["apply.virtual_house_id"] = $(form).find("input[name='apply.virtual_house_id']").val();
			params["virtual_house_name"] = $(form).find("input[name='virtual_house_name']").val();

			goodsTabs.showAssigned(params);
		},
		//已分配货品--选择销售组织
		selectOrg : function(){
			//alert("selectOrg");
			var url = "warehouseAssign!selectSaleOrg.do?ajax=yes";
			$("#selectShop_dialog").load(url,function(resp){
				Eop.Dialog.open("selectShop_dialog");
				if($("#ae_attribution").val()=="0"){
					$("#dialog_isSingle").val("0");
				}else{
					$("#dialog_isSingle").val("1");
				}
			});
		},		
		//已分配货品--选择虚拟仓
		showQueryVir:function(params){
		 	 var url = "warehouseAssign!selectVirList.do?ajax=yes";
		     $("#query_sel_vir_dialog").load(url,params,function(){});
		     Eop.Dialog.open("query_sel_vir_dialog"); 
		}
};

//货品选择
var selectPro= {
        init:function(){
            var me=this;
            $("#selProductBtn").live("click",function() {
		        me.addSelectGoods();
	        });
            //单选框双击选择
        	$("#listProduct").find(".gridbody tbody tr").live("dblclick",function(){
            	//alert("doubleClick");
            	var box = $(this).find("input[type='checkbox'],input[type='radio']");
            	if($(box).attr("type") == "radio"){
            		box.attr("checked",true);
            		me.addSelectGoods();
            	}
        	});
            $("#serProductButton").live("click",function() {
                me.searchBottonClks();
            });
        },
        addSelectGoods:function(){
			/* var goods=$("input[name=goodsId][type=checkBox]:checked").val();
        	if(!goods){
    			alert("请选择商品！");
    			return ;
    		} */
        	pageName = $(".tab .active").attr("id");
        	var goodsid = $("#listProduct input[name='goodsId']:checked");
			goodsid.each(function(idx,item){
			   var pid = $(item).attr("pid");
			   var goods_name = $(item).attr("goods_name");
			   if(pageName == "assign"){
				   $("#product_id").val(pid);
				   $("#product_name").val(goods_name); 
				   Eop.Dialog.close("select_pro_div");
			   }else if(pageName == "assigned"){
				   $("#v_product_id").val(pid);
				   $("#v_product_name").val(goods_name); 
				   Eop.Dialog.close("select_pro_div");
			   }

	         });
			
    },
    searchBottonClks : function() {
    	var name = $.trim($("#choiceProform").find("input[name='name']").val());
    	WarehouseProduct.showProduct(name);
		
   }
}

//商品选择
var selectGoods= {
        init:function(){
            var me=this;
            $("#selGoodsBtn").live("click",function() {
		        me.addSelectGoods();
	        });
            //单选框双击选择
        	$("#listGoods").find(".gridbody tbody tr").live("dblclick",function(){
            	//alert("doubleClick");
            	var box = $(this).find("input[type='checkbox'],input[type='radio']");
            	if($(box).attr("type") == "radio"){
            		box.attr("checked",true);
            		me.addSelectGoods();
            	}
        	});
            $("#searchGoodsBtn").live("click",function() {
                me.searchBottonClks();
            });
        },
        addSelectGoods:function(){
        	pageName = $(".tab .active").attr("id");
        	var goodsid = $("#listGoods input[name='goodsId']:checked");
			goodsid.each(function(idx,item){
			   var gid = $(item).attr("gid");
			   var pid = $(item).attr("pid");
			   var goods_name = $(item).attr("goods_name");
			   
			   if(pageName == "assign"){
				   $("#goods_id").val(gid);
				   $("#product_id").val(pid);
				   $("#product_name").val(goods_name);
				   Eop.Dialog.close("select_goods_div");
			   }else if(pageName == "assigned"){
				   $("#v_goods_id").val(gid);
				   $("#v_product_id").val(pid);
				   $("#v_product_name").val(goods_name);
				   Eop.Dialog.close("select_goods_div");
			   }
			   

	         });
			
    },
    searchBottonClks : function() {
    	var name = $.trim($("#choiceGoodsform").find("input[name='name']").val());
    	if(pageName == "assign"){
    		WarehouseGoods.showGoods(name);
    	}else if(pageName == "assigned"){
    		WarehouseGoods.showGoodsV(name);
    	}
    	
   }
}


var selVirtual= {
        init:function(){
//        	alert("selVirtual.init");
            var me=this;
            $("#selVirtualBtn").live("click",function() {
		        me.addSelect();
	        });
            //单选框双击选择
        	$("#listVirtual").find(".gridbody tbody tr").live("dblclick",function(){
        		//alert("dblclick");
            	var box = $(this).find("input[type='checkbox'],input[type='radio']");
            	if($(box).attr("type") == "radio"){
            		box.attr("checked",true);
            		selVirtual.addSelect();
            	}
        	});
            $("#serVirtualButton").live("click",function() {
                me.searchBottonClks();
            });
        },
        addSelect:function(){
        	
        	var box = $("#listVirtual input[name='house_id']:checked");
        	box.each(function(idx,item){
			   var house_id = $(item).val();
			   var house_name = $(item).attr("house_name");
			   var org_id_str = $(item).attr("org_id_str");
			   var org_name_str = $(item).attr("org_name_str");
			   var attr_inline_type = $(item).attr("attr_inline_type");
			   
			   $("#v_house_id").val(house_id);
			   $("#v_house_name").val(house_name);
			   $("#v_attr_inline_type").val(attr_inline_type);
			   $("#org_id_str").val(org_id_str);
			   $("#org_name_str").val(org_name_str);
			   
	         }),
			Eop.Dialog.close("sel_virtual_div");
    },
    searchBottonClks : function() {
    	var house_name = $.trim($("#listVirtual form[name='choiceVirform']").find("input[name='house_name']").val());
    	var attr_inline_type = $.trim($("#listVirtual form[name='choiceVirform']").find("select[name='attr_inline_type']").val());
    	var params = {"virtualWarehouse.house_name":house_name,"virtualWarehouse.attr_inline_type":attr_inline_type};
    	assginWin.showVitrtaultWare(params);
		
   }
}
