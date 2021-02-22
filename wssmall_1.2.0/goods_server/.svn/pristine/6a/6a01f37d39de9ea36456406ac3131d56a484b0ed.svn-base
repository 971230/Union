var addBrand={
		conid:undefined,
		init:function(conid,onConfirm)	{
			this.conid = conid;
			var self  = this;
			$("#brandInfo .submitBtn").click(function() {
			   var url_validate =  $("#url").val().substring(0,7);
			    if(url_validate!="http://"){
			     alert("品牌网址格式不正确");
			     return false;
			    }
			   
				var url = ctx+ "/shop/admin/brand!save.do?ajax=yes";
				
				Cmp.ajaxSubmit('brandForm', '', url, {}, self.jsonBack,'json');
				
			});
			
			$(".closeBtn").bind("click",function(){
				Eop.Dialog.close(conid);
				$('#up').parent().show();
			});
		},
		jsonBack : function(responseText) { // json回调函数
			if (responseText.result == 1) {
				alert(responseText.message);
			}else{
				Eop.Dialog.close("add_brand_dialog");
				$("#brand_select").append("<option value='"+responseText.brandid+"'>"+responseText.name+"</option>")	
			}
			
		},
		open:function(conid,onConfirm){
		  
		  	var self= this;
		  	$("form.validate").validate();
		  	 var type_id = $("#type_select").val();
		  	if(type_id.length == 0){
		  	   alert("请先选择商品类型");
		  	   return false;
		  	 }
		    var url=app_path+"/shop/admin/brand!add.do?selfProdType=F&type_id="+type_id+"&ajax=yes";
			$("#add_brand_dialog").load(url,function(){
			   self.init(conid,onConfirm);
			  
			});
			Eop.Dialog.open(conid);		
		}
		
	};
$(function(){
	Eop.Dialog.init({id:"add_brand_dialog",modal:false,title:"添加品牌",width:"850px",height:"800px"});
	Eop.Dialog.init({id:"goodsTypeQuickBtn_dialog",modal:true,title:"商品分类",width:"1000px"});
	$("#cat_id").change(function(){
		var typeid = $("#cat_id option:selected").attr("typeid");
		$("#type_select").val(typeid);
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/type!listBrand.do?ajax=yes",
			data:"type_id=" + typeid +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				   $("#brand_select").empty().append(result);
				   //if(brand_id){
				   //$("#brand_select").val(brand_id);
				   //}
			},
			error :function(res){alert("异步读取商品品牌失败:" + res);}
		});
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/type!disPropsInputN.do?ajax=yes",
			data:"type_id=" + typeid +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				   $("#goods_props .grid_n_cont").empty().append(result);
			},
			error :function(res){alert("异步读取商品属性失败:" + res);}
		});
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/type!disParamsInputN.do?ajax=yes",
			data:"type_id=" + typeid +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				   $("#goods_params .grid_n_cont").empty().append(result);
			},
			error :function(res){alert("异步读取商品参数失败:" + res);}
		});
		//合约计划10001，终端10000
		if("10001" == typeid || "10000" == typeid){
			$("#goodsRelDiv").show();
			$("#goodsRelNode").empty();
			if("10001" == typeid){//合约关联套餐
				$("#goodsRelDiv h2").html("<a href='javascript:void(0);' class='closeArrow'></a>合约关联套餐");
				$("#goodsRelDiv .remind_n_div p").text("您没有关联任何套餐，请添加");
			}
			else if("10000" == typeid){//终端关联购买计划
				$("#goodsRelDiv h2").html("<a href='javascript:void(0);' class='closeArrow'></a>终端关联购买计划");
				$("#goodsRelDiv .remind_n_div p").text("您没有关联任何购买计划，请添加");
			}
		}
		else{
			$("#goodsRelDiv").hide();
		}
	});
	$("#goodsTypeQuickBtn").bind("click",function(){
		var url = "cat!showCatList.do?ajax=yes";
		$("#goodsTypeQuickBtn_dialog").load(url, function (responseText) {
				Eop.Dialog.open("goodsTypeQuickBtn_dialog");
				$("#up").hide();
		});
	});
	$("#addBrandDialog").bind("click",function(){
		addBrand.open("add_brand_dialog", null);
	})
})