//类别、类型、品牌联动
var TypeCat={
	init:function(){
		var self  = this;
	},
	changeCat:function(catid){
//		var option = $("#cat_id>option[value="+catid+"]");
//		option.attr("selected",true);
//		var typeid = option.attr("typeid");
//		this.changeType(catid, typeid);
	}
	,
	loadField:function(typeid){
		$("#custom_field_table").load(app_path+'/shop/admin/field!disInputHtml.do?typeid='+typeid+"&ajax=yes");
	}
	,
	//分类切换事件
	changeType:function(cat_id,type_id){
		if(type_id=='10000'){
			$("#snTr th:eq(0)").html("<span class='red'>*</span>条形码：");
			$("#goods_sn").attr("required","true");
			$("#batchProdBtn").show();
		}
		else{
			$("#snTr th:eq(0)").html("条形码：");
			$("#goods_sn").removeAttr("required");
			$("#batchProdBtn").hide();
		}
		//if(confirm("是否根据所选分类的默认类型重新设定商品类型？\n如果重设，可能丢失当前所输入的类型属性、关联品牌、参数表等类型相关数据。")){
			$("#type_id").val(type_id);
			this.type_change_event(type_id,this.getJoinBrand(type_id));
			this.loadField(type_id);
		//}		
	},
	
	//获取某类型是否关联品牌
	getJoinBrand:function(typeid){
	    var join_brand = 0;
		$("#type_id option").each(function(){
			if($(this).val() == typeid && $(this).attr("join_brand")== 1 ){
				join_brand=1;
			} 
		});
		return join_brand;
	},
	brand_change_event:function(brand_id){
		
	},
	type_change_event1:function(type_id,join_brand,brand_id){
		if(parseInt(join_brand)==1){
			this.loadBrandsInput(type_id,brand_id);
		}else{
			$("#brand_id").empty();
		}
		
		if(type_id==10001){
			$("#productTabli .tab li:last-child").show();
		}
		else{
			$("#productTabli .tab li:last-child").hide();
		}
		
	}
	,
	
	//类型更换事件
	type_change_event:function(type_id,join_brand,brand_id){
	   
		if(parseInt(join_brand)==1){  
			//$("#brand_tr").show();
			this.loadBrandsInput(type_id,brand_id);
		}else{
			 
			$("#brand_id").empty();
		//	$("#brand_tr").hide();
			 
		}
		 
		if(!brand_id){ //如果传递brand_id 说明在编辑
			this.loadPropsInput(type_id);
			this.loadParamsInput(type_id);
		}	
	},
	
	loadBrandModel:function(brand_id,model_code){
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/type!listBrandModel.do?ajax=yes",
			data:"brand_id=" + brand_id +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				 
				   $("#model_code").empty().append(result);
				   if(model_code){
				   	$("#model_code").val(model_code);
				   }
			  
			},
			 error :function(res){alert("异步读取失败:" + res);}
			});
	},
	
	loadMachineType:function(brand_id,sn){
		$.ajax({
			type: "get",
			url:app_path+"/shop/admin/type!listMachineType.do?ajax=yes",
			data:"brand_id=" + brand_id +"&m=" + new Date().getTime(),
			dataType:"html",
			success:function(result){
				 
			   $("#goods_sn").empty().append(result);
			   if(sn){
			   	$("#goods_sn").val(sn);
			   }
			  
			},
			 error :function(res){alert("异步读取失败:" + res);}
			});
	},
	
	//异步读取品牌输入项
	loadBrandsInput:function(type_id,brand_id){
		
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
	//加载参数输入项
	loadParamsInput:function(type_id){
		$.ajax({
		type: "get",
		url:app_path+"/shop/admin/type!disParamsInput.do?ajax=yes",
		data:"type_id=" + type_id +"&m=" + new Date().getTime(),
		dataType:"html",
		success:function(result){
			try{
				$("#tab_params").html(result);
		    }catch(e){ alert(e); }
		  
		},
		 error :function(res){alert("异步读取失败:" + res);}
		});
	},
	 loadPropsInput:function(type_id){
	 
		$.ajax({
		type: "get",
		url:app_path+"/shop/admin/type!disPropsInput.do?ajax=yes",
		data:"type_id=" + type_id +"&m=" + new Date().getTime(),
		dataType:"html",
		success:function(result){
		   document.getElementById("tab_props").innerHTML = result;
		//   $("form.validate").addinput($("#tab_props input,#tab_props select"));
			var catid = $("#cat_id").val();
		 
			if(catid =='3' || catid =='4' || catid =='12' || catid =='19' ){}else {
				$("#weight_tr").hide();
			}
		 
		},
		 error :function(res){alert("异步读取失败:" + res);}
		});
	},
	addBrand:function(){
	  Eop.Dialog.init({id:"add_brand_dialog",modal:false,title:"添加品牌",width:'850px',height:'800px'});
      
      $("#add_brand").bind("click",function(){
            addBrand.open("add_brand_dialog",null);
            $('#up').parent().hide();
	  	  });
	  	
	}
};
