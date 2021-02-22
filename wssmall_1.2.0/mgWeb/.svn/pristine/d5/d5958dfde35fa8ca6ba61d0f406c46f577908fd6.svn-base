var ProductPerAddEdit={
        init:function(){
            var me=this;
            var jsonData={};
        	 jsonData.goods_name=$("#goods_name").val();
        	 jsonData.goods_sn=$("#goods_sn").val();
        	 jsonData.dc_goods_color=$("#dc_goods_color").val();
        	 jsonData.goods_color=$("#dc_goods_color").find("option:selected").text(); 
        	 jsonData.input_prod_id=me.newGuid();
        	me.addConfigProduct(jsonData);
        	
            $("#serButton").bind("click",function() {
                me.searchBottonClks();
           });
        },
        addConfigProduct:function(jsonData){
        	var cloneTr = $("#product_info_tr").clone();
			cloneTr.show();
			cloneTr.find("#tpl_goods_name").html(jsonData.goods_name);
			cloneTr.find("#tpl_goods_sn").html(jsonData.goods_sn);
			cloneTr.find("#tpl_product_id").val(jsonData.input_prod_id);
			cloneTr.find("#tpl_goods_color").attr("dc_goods_color",jsonData.dc_goods_color);
			cloneTr.find("#tpl_goods_color").html(jsonData.goods_color);
			
			$("#prodPersonalBody").append(cloneTr.data("product_data",jsonData));
        },
        addConfigGoods:function(jsonData){
        	var cloneTr = $("#goods_info_tr").clone();
			cloneTr.show();
			cloneTr.find("#g_goods_name").html(jsonData.goods_name);
			cloneTr.find("#tpl_goods_id").val(jsonData.input_prod_id);
			cloneTr.find("#tpl_product_name").html(jsonData.product_name);
			cloneTr.find("#tpl_goods_price").html(jsonData.goods_price);
			$("#goodsMsgBody").append(cloneTr.data("goods_data",jsonData));
        },
    //选择条形码
	showSelectTags:function(sn){
		var me = this;
		var url =ctx + "/shop/admin/goods!snSelectList.do?ajax=yes&type=batch";
		Eop.Dialog.open("tagsDialog1");
	    if(sn){
	    	 url += "&sn="+encodeURI(encodeURI(sn,true),true);
	    }
		$("#tagsDialog1").html("loading...");
		$("#tagsDialog1").load(url,function(){
			 $("#selTagsBtn1").unbind("click").bind("click",function() {
	                me.addSelectTags();
	            });
	            //单选框双击选择
	            $("#listTags1").find(".gridbody tbody tr").live("dblclick",function(){
	                var box = $(this).find("input[type='checkbox'],input[type='radio']");
	                if($(box).attr("type") == "radio"){
	                    $(this).siblings("tr").removeClass("grid-table-row-selected");
	                    $(this).addClass("grid-table-row-selected");
	                    box.attr("checked",true);
	                    me.addSelectTags();
	                }
	            });
	            $("#searchTagBtn1").unbind("click").bind("click",function() {
	                me.searchBottonClks();
	            });
		});
	},
	 addSelectTags:function(){
         var checked = $("#listTags1 input[name='checked_sn']:checked").val();
         $("#per_goods_sn").val(checked);
         Eop.Dialog.close("tagsDialog1");
     },
     searchBottonClks : function() {
         var search_sn = $.trim($("#search_sn1").val());
         ProductPerAddEdit.showSelectTags(search_sn);
    },
    newGuid:function(){
    	   var guid = "";
    	    for (var i = 1; i <= 4; i++){
    	      var n = Math.floor(Math.random()*16.0).toString(16);
    	      guid +=n;
    	    }
    	    guid += new Date().getTime();
    	    return guid.toUpperCase();    
     }
};

$(function() {
	ProductPerAddEdit.init();
	Eop.Dialog.init({id : "addProdPerConfig_dialog",modal : true,title : "货品个性信息配置",width : "600px"});
	Eop.Dialog.init({id : "batchGoodsCofig_dialog",modal : true,title : "生成商品个性信息配置",width : "600px"});
	Eop.Dialog.init({id:"tagsDialog1",modal:true,title:"条形码",width:'550px'});
	$("#perReturnBtn").bind("click", function() {
		Eop.Dialog.close("batchProdAddBtn_dialog");
	});
	//提交表单
	$('#perInsureBtn').bind("click", function() {
				var trs = $("#prodPersonalBody").find("tr");
				if (trs.length == 0) {
					alert("货品的个性信息列表信息为空，请至少添加一个货品个性信息");
					return;
				}
				$(".jqmOverlay").css("z-index","3006");
				$.Loading.show('正在保存，请稍侯...');
				var productList = new Array();
				for (var i = 0; i < trs.length; i++) {
					var tr = trs[i];
					var products = new Object();
					products.product_name = encodeURI(encodeURI($(tr).find("td:eq(1)").html()));
					products.goods_sn = $(tr).find("td:eq(2)").html();
					products.dc_goods_color = $(tr).find("td:eq(3)").attr(
							"dc_goods_color");
					products.input_prod_id = $(tr).find("td:eq(0)").find("input[name='selectOrg']").val();
					productList.push(products);
				}
				var goodsList = new Array();
				var goodstrs = $("#goodsMsgBody").find("tr");
				if (goodstrs.length >= 0) {
					for (var i = 0; i < goodstrs.length; i++) {
						var tr = goodstrs[i];
						var goods = new Object();
						goods.goods_name = encodeURI(encodeURI($(tr).find("td:eq(1)").html()));
						goods.input_prod_id = $(tr).find("td:eq(0)").find("input[name='selectOrg']").val();
						var price =$(tr).find("td:eq(3)").html();
						if(price==null||price==""){
							alert("批量生成的商品销售价格为空，请填写商品销售价格");
							$(".jqmOverlay").css("z-index","2999");
							return;
						}
						goods.price = $(tr).find("td:eq(3)").html();
						goodsList.push(goods);
					}
				}
				var param="&productList=" +JSON.stringify(productList)+ "&goodsList=" + JSON.stringify(goodsList);
				var url = ctx + "/shop/admin/goods!saveBatchAdd.do?ajax=yes"+param;
				var options = {
						url : url,
						type : "POST",
						dataType : 'json',
						success : function(responseText) { 
							if (responseText.result == 1) {
							//	alert(responseText.message);
								MessageBox.show(responseText.message, 1, 2000);
								Eop.Dialog.close("batchProdAddBtn_dialog");
						  		window.location.href=responseText.url;
							}else{
								$.Loading.hide();
								$(".jqmOverlay").css("z-index","2999");
								alert(responseText.message);
							}	
						},
						error : function(e) {
							$.Loading.hide();
							$(".jqmOverlay").css("z-index","2999");
							alert("出现错误 ，请重试");
						}
					};
				$('#productForm').ajaxSubmit(options);
			});
	$("a[name='delProdPer']").live("click",function(){
		var tr=$(this).parent().parent();
		$(tr).remove();
	});	
	$("a[name='updateProdPer']").live("click",function(){
		var tr=$(this).parent().parent();
		var per_product_name=$(tr).find("td:eq(1)").html();
		var per_goods_sn=$(tr).find("td:eq(2)").html();
		var dc_goods_color=$(tr).find("td:eq(3)").attr("dc_goods_color");
		var input_prod_id=$(tr).find("td:eq(0)").find("input[name='selectOrg']").val();
		var cat_id=$("#cat_id").find("option:selected").val(); 
		var url = "goods!addProdPerConfig.do?ajax=yes&is_edit=updatePer&cat_id="+cat_id;
		$("#addProdPerConfig_dialog").load(url, function(responseText) {
			Eop.Dialog.open("addProdPerConfig_dialog");
			$("#per_product_name").val(per_product_name);
			$("#per_goods_sn").val(per_goods_sn);
			$("#per_goods_color").val(dc_goods_color);
			$("#input_prod_id").val(input_prod_id);
			$("#confirmProdPerBtn1").click(function(){
				var product_name=$("#per_product_name").val();
				var goods_sn=$("#per_goods_sn").val();
				var dc_goods_color1=$("#per_goods_color").find("option:selected").val();
				var goods_color=$("#per_goods_color").find("option:selected").text();
				var input_prod_id1=$("#input_prod_id").val();
				if(product_name==""){
					alert("请填写货品名称");
					return;
				}
				if(goods_sn==""){
					alert("请填写条形码");
					return;
				}
				if(dc_goods_color1==""){
					alert("请选择颜色");
					return;
				}
				var cat_id = $("#cat_id").val();
				//定制机需要校验唯一性---zengxianlian
				var postData = "";
				if("690002000"==cat_id){
					postData="typeCode=10000&sn="+goods_sn;
					$.ajax({
						   url:"goods!checkSaveAdd.do",
						   type:"POST",
						   //dataType:"json",
						   dataType:"html",
						   data :postData,
						   success:function(reply){
							   reply=reply.substring(reply.indexOf("<body >")+7,reply.indexOf("</body>"));
							   	var json= eval("(" + reply+")");
							   if(1==json.result){
								   $(tr).find("td:eq(1)").html(product_name);
									$(tr).find("td:eq(2)").html(goods_sn);
									$(tr).find("td:eq(3)").html(goods_color);
									$(tr).find("td:eq(3)").attr("dc_goods_color",dc_goods_color1);
								    $(tr).find("td:eq(0)").find("input[name='selectOrg']").val(input_prod_id1);
							    	 Eop.Dialog.close("addProdPerConfig_dialog");
							   }else{
								   alert(json.message);
								   return;
							   }
						   },
						   error:function(reply){
							   alert("出错了");
						   }
					   });
				}else{
					    $(tr).find("td:eq(1)").html(product_name);
						$(tr).find("td:eq(2)").html(goods_sn);
						$(tr).find("td:eq(3)").html(goods_color);
						$(tr).find("td:eq(3)").attr("dc_goods_color",dc_goods_color1);
						$(tr).find("td:eq(0)").find("input[name='selectOrg']").val(input_prod_id1);
				    	 Eop.Dialog.close("addProdPerConfig_dialog");
				}
			});
		});
	});	
	$("#addProdPerBtn").bind("click", function() {
		var cat_id=$("#cat_id").find("option:selected").val(); 
		var url = "goods!addProdPerConfig.do?ajax=yes&cat_id="+cat_id;
		$("#addProdPerConfig_dialog").load(url, function(responseText) {
			Eop.Dialog.open("addProdPerConfig_dialog");
			$("#input_prod_id").val(ProductPerAddEdit.newGuid());
			var input_prod_id=$(tr).find("td:eq(0)").find("input[name='selectOrg']").val();
		});
	});
	$("#batchGoodsConfigBtn").bind("click", function() {
		var trs = $("#prodPersonalBody").find("tr");
		if (trs.length == 0) {
			alert("货品的个性信息列表信息为空，请至少添加一个货品个性信息才能批量生成商品");
			return;
		}
		for (var i = 0; i < trs.length; i++) {
			 var tr = trs[i];
			 var jsonData={};
			 var product_name=$(tr).find("td:eq(1)").html();
			 var goods_color = $(tr).find("td:eq(3)").html();
	    	 jsonData.goods_name=product_name+goods_color;
	    	 jsonData.product_name=product_name; 
	    	 jsonData.input_prod_id=$(tr).find("td:eq(0)").find("input[name='selectOrg']").val();
	    	 ProductPerAddEdit.addConfigGoods(jsonData);
		}
		$("#goodsMsg").show();
		$("#batchGoodsConfigBtn").hide();
		$("#cancleBatchGoodsConfigBtn").show();
	});
	$("#cancleBatchGoodsConfigBtn").bind("click", function(){
		$("#goodsMsg").hide();
		$("#goodsMsgBody").find("tr").remove();
		$("#batchGoodsConfigBtn").show();
		$("#cancleBatchGoodsConfigBtn").hide();
	});
		
	$("a[name='delGoodsPer']").live("click",function(){
		var tr=$(this).parent().parent();
		$(tr).remove();
	});	
	$("a[name='updateGoodsPer']").live("click",function(){
		var tr=$(this).parent().parent();
		var per_goods_name=$(tr).find("td:eq(1)").html();
		var per_product_name=$(tr).find("td:eq(2)").html();
		var per_goods_price=$(tr).find("td:eq(3)").html();
		var input_prod_id=$(tr).find("td:eq(0)").find("input[name='selectOrg']").val();
		var url = "goods!batchGoodsConfig.do?ajax=yes";
		$("#batchGoodsCofig_dialog").load(url, function(responseText) {
			Eop.Dialog.open("batchGoodsCofig_dialog");
			$("#per_goods_name").val(per_goods_name);
			$("#per_prod_name").val(per_product_name);
			$("#per_goods_price").val(per_goods_price);
			$("#input_goods_id").val(input_prod_id);
			$("#confirmGoodsPerBtn").click(function(){
				var product_name=$("#per_prod_name").val();
				var goods_name=$("#per_goods_name").val();
				var goods_price=$("#per_goods_price").val();
				var input_goods_id=$("#input_goods_id").val();
				if(goods_price==""){
					alert("请填写价格");
					return;
				}
				$(tr).find("td:eq(0)").find("input[name='selectOrg']").val(input_goods_id);
				$(tr).find("td:eq(1)").html(goods_name);
				$(tr).find("td:eq(2)").html(product_name);
				$(tr).find("td:eq(3)").html(goods_price);
				
		    	 Eop.Dialog.close("batchGoodsCofig_dialog");
			});
			$("#cancleGoodsPerBtn").click(function(){
				Eop.Dialog.close("batchGoodsCofig_dialog");
			});
		});
	});	
});
