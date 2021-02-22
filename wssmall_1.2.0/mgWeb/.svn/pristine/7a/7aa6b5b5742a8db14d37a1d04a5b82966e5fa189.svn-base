var ProductPackage = {
	init : function(){
		var self = this;
		Eop.Dialog.init({id:"goods_pub_dialog",modal:false,title:"商品发布",width:"500px"});
		Eop.Dialog.init({id:"goods_org_list",modal:false,title:"确认停用",width:'600px'});
		Eop.Dialog.init({id:"package_dialog",modal:true,title:"货品包列表",width:'950px'});
		$("#pkgGridForm tbody tr").live("click",function(){
			var md = $("#pkgGridForm input[name='id']:checked").attr("md");
			$("#md").val(md);
			self.searchColor();
		});
		$("#color_all").click(function(){
			self.selectAllColor();
		})
		$("#all_3g").click(function(){
			self.selectAll3G();
		});
		$("#all_4g").click(function(){
			self.selectAll4G();
		});
		$("#searchPkgGoodsBtn").click(function(){
			self.searchPkgGoods();
		});
		$("#publishGoodsBtn").click(function(){
			self.openOrgDialog();
		});
		$("#goodsMarketDisable").click(function(){
			self.openConfirmDialog();
		});
		$("#publishPkgBtn").click(function(){
			self.publishPkgGoods();
		});
		$("#pkgMarketDisable").click(function(){
			self.pkgMarketDisable();
		});
		$("#selectPkgBtn").click(function(){
			self.openPkgDialog();
		});
		
	},
	openPkgDialog : function(params){
		var p = "";
		if(typeof(params)!='undefined'){
			p = "&params.type_id="+params.type_id+"&params.brand_id="+params.brand_id+"&params.model_code="+encodeURI(encodeURI(params.model_code))+"&params.name="+encodeURI(encodeURI(params.name));
		}
		var url =app_path + "/shop/admin/goods!searchProductPkgECS.do?ajax=yes"+p;
	    Eop.Dialog.open("package_dialog"); 
	    $("#package_dialog").load(url,params,function(){});
	},
	pkgMarketDisable : function(){
		var relation_id = $("#relation_id").val();
		if(!relation_id){
			alert("请选择货品包！");
			return ;
		}
		if(confirm("确定停用货品包下的所有商品吗？")){
			$.ajax({
				type: "get",
				url:app_path+"/shop/admin/goods!updatePkgGoodsStatusECS.do?ajax=yes",
				data:"params.relation_id=" + relation_id +"&m=" + new Date().getTime(),
				dataType:"json",
				success:function(result){
					alert(result.message);
				  
				},
				error :function(res){alert("异步读取失败:" + res);}
			});
		}
		
	},
	publishPkgGoods : function(){
		var relation_id = $("#relation_id").val();
		if(!relation_id){
			alert("请选择货品包！");
			return ;
		}
		var url ="goodsOrg!goodsPubtree.do?busqueda=false&relation_id="+relation_id;
		abrirCajaVentana("goods_pub_dialog",url);
	},
	openConfirmDialog : function(){
		var self = this;
		var legal = true;
		var id_arr = new Array();
		$("#goodsGridForm tbody input:checked").each(function(){
			id_arr.push($(this).val());
			if($(this).attr("mk")=='0'){
				legal = false;
			}
		});
		if(id_arr.length==0){
			alert("请选择要停用的商品！");
			return ;
		}
		if(id_arr.length>1){
			alert("一次停用操作只能选择一个商品！");
			return ;
		}
		if(!legal){
			alert("已经停用的商品不能再次停用！");
			return ;
		}
		var url=app_path+'/shop/admin/goods!getOrgByGoodsId.do?ajax=yes&type=goods&goods_id='+id_arr[0];
	    Eop.Dialog.open("goods_org_list");		
		$("#goods_org_list").load(url,function(){
           var conid = "goods_org_list";
		   $("#goods_down_check_btn").click(function(){
			    Eop.Dialog.close(conid);
			    self.marketDisable(id_arr[0]);
			 });
			
			 $("#goods_down_cance_btn").bind("click",function(){
				 Eop.Dialog.close(conid);
		     });
		});
	},
	marketDisable : function(goods_id){
		var mkval = 1;
		var stateCell = $("#goodsGridForm").find("input[value='"+goods_id+"']").parents("tr");
		$.ajax({
			async:false,
			url:"goods!marketEnable.do?ajax=yes",
			data : {"goods_id":goods_id,"market_enable":0},
			dataType:"json",
			success:function(result){
				if(mkval == '0'){
					alert('已启用');
					stateCell.find("td:eq(5)").html('启用 ') ;
				}else{
					alert('已停用');
					stateCell.find("td:eq(5)").html('停用 ') ;
				}	
				//是否上下架状态重新加载
				//window.location.reload();
			},
			error:function(){
				alert("后台出错,请重新操作!");
			}
		});
	},
	openOrgDialog : function(){
		var publishable = true;
		var id_arr = new Array();
		$("#goodsGridForm tbody input:checked").each(function(){
			id_arr.push($(this).val());
			var mk = $(this).attr("mk");
			if(mk==0){
				publishable = false;
			}
		});
		if(id_arr.length==0){
			alert("请选择要发布的商品！");
			return ;
		}
		if(!publishable){
			alert("已经停用的商品不能发布！");
			return ;
		}
		var url ="goodsOrg!goodsPubtree.do?busqueda=false&esgoodscos="+id_arr.join(",");
		abrirCajaVentana("goods_pub_dialog",url);
	},
	loadBrand : function(){
		var type_id = $("#type_id").val();
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/type!listBrand.do?ajax=yes",
			data:"type_id=" + type_id +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				 
				   $("#brand_id").empty().append(result);
				   if(brand_id){
				   	   $("#brand_id").val(brand_id);
				   }
			  
			},
			error :function(res){alert("异步读取失败:" + res);}
		});
	},
	selectAllColor : function(){
		var self = this;
		var selAll = $("#color_all").attr("checked");
		$("#color").find("input:gt(2)").each(function(){
			$(this).attr("checked",selAll);
		})
	},
	selectAll3G : function(){
		var self = this;
		var selAll = $("#all_3g").attr("checked");
		$("#net_3g").find("input:gt(1)").each(function(){
			$(this).attr("checked",selAll);
		})
	},
	selectAll4G : function(){
		var self = this;
		var selAll = $("#all_4g").attr("checked");
		$("#net_4g").find("input:gt(1)").each(function(){
			$(this).attr("checked",selAll);
		})
	},
	searchColor : function(){
		var self = this;
		$("#color").find("input:gt(1)").attr("checked",false);
		$("#net_3g").find("input:gt(0)").attr("checked",false);
		$("#net_4g").find("input:gt(0)").attr("checked",false);
		var md = $("#md").val();
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/goods!listColorByModelCode.do?ajax=yes",
			data:"params.model_code=" + md +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				$("#color").find("td:gt(1)").remove();
				$("#color").find("td:eq(1)").after(result);
			},
			error :function(res){alert("异步读取失败:" + res);}
		});
	},
	search3GLvl : function(){
		
	},
	search4GLvl : function(){
		
	},
	searchPkgGoods : function(){
		var self = this;
		var relation_id = $("#relation_id").val();
		if(!relation_id){
			alert("请选择货品包！");
			return ;
		}
		$("#relation_id").val(relation_id);
		
		var color_arr = new Array();
		var lvls_3g_arr = new Array();
		var lvls_4g_arr = new Array();
		$("#color").find("input:gt(2)").each(function(){
			if($(this).attr("checked")){
				color_arr.push($(this).attr("cc"));
			}
		})
		$("#net_3g").find("input:gt(1)").each(function(){
			if($(this).attr("checked")){
				lvls_3g_arr.push($(this).attr("lvl"))
			}
		})
		$("#net_4g").find("input:gt(1)").each(function(){
			if($(this).attr("checked")){
				lvls_4g_arr.push($(this).attr("lvl"))
			}
		})
		$("input[name='params.colors']").val(color_arr.join(","));
		$("input[name='params.lvls_3g']").val(lvls_3g_arr.join(","));
		$("input[name='params.lvls_4g']").val(lvls_4g_arr.join(","));
		var url=app_path+"/shop/admin/goods!searchPkgGoodsECS.do?ajax=yes";
		Cmp.ajaxSubmit('searchPkgGoodsForm', '', url, {}, self.callBack,'html');
	},
	callBack : function(responseText){
		var obj = $(responseText);
		$("#goodsGridForm").html($("#goodsGridForm",obj).html());
	}
}

$(function(){
	ProductPackage.init();
});

