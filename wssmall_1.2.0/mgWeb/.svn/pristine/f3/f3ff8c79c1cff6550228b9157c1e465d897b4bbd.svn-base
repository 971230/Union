$(function() {
	SaleGoodsAddEdit.init();
	//tab页切换
	$(".goods_input .tab>li").click(function() {
		var formJq =$("#productForm");
		var tabid = $(this).attr("tabid");
		$(".goods_input .tab>li").removeClass("active");
		$(this).addClass("active");
		$(".tab-page .tab-panel").hide();
		$("div[tabid=tab_" + tabid + "]").show();
		
		if ($(this).attr("tabid") == 0) {
            $("#finishStep").hide();
            $("#preStep").hide();
            $("#nextStep").show();
            $("#lastStep").show();
        } else if($(this).attr("tabid") == 5){
     	    $("#nextStep").hide();
            $("#lastStep").hide();
            $("#finishStep").show();
            $("#preStep").show();
        }else{
     	    $("#lastStep").hide();
            $("#finishStep").hide();
            $("#nextStep").show();
            $("#preStep").show();
        }
	});
	
	$("#lastStep").click(function(){
		history.go(-1);
	});
	
	$("#nextStep").bind("click",function(){
		var formId = $(this).attr("formId");
		SaleGoodsAddEdit.validateForm("next",formId);
	});
	
	
	$("#preStep").bind("click",function(){
		var formId = $(this).attr("formId");
		SaleGoodsAddEdit.validateForm("prev",formId);
	});
	
	$("img.delete").live("click",function(){
		if(confirm("确定删除该条信息吗？删除后不可恢复")){
			$(this).parents("tr").remove();
		}
	});
	
	//添加商品打开页面
	$("#addGoodsBtn").click(function(){
		var goods_type=$("#goods_type option:selected").val();  
		var url =ctx + "/shop/admin/goods!addGoodsFromSale.do?ajax=yes&goods_type="+goods_type;
		Eop.Dialog.open("goodsList");
		$("#goodsDialog").html("loading...");
		$("#goodsDialog").load(url,function(){});
	});
	
	//添加商品标签打开页面
	$("#addGoodsTagBtn").click(function(){
		var url =ctx + "/shop/admin/goods!tagSelectList.do?ajax=yes&tag_group_type=goods";
		Eop.Dialog.open("tagList");
		$("#tagDialog").html("loading...");
		$("#tagDialog").load(url,function(){});
	});
	
	//添加营销标签打开页面
	$("#addSaleTagBtn").click(function(){
		var url =ctx + "/shop/admin/goods!tagSelectList.do?ajax=yes&tag_group_type=sale";
		Eop.Dialog.open("tagList");
		$("#tagDialog").html("loading...");
		$("#tagDialog").load(url,function(){});
	});
	
	//选择销售组织
	$("#select_org_input").bind("click",function(){
		selOrg.selectOrg("activity_orgs_dialog");
	});
});

var SaleGoodsAddEdit = {
   init:function(){
	   $("#channel_type").val($("#channel_type_id").val());
	   $("#goods_type").val($("#type_id").val());

	   var self =this;
	   var action=$("#productForm").attr("action");
	   Eop.Dialog.init({id:'activity_orgs_dialog',modal:true,title:'活动商城',width:'450px'});
	   Eop.Dialog.init({id:"goodsList",modal:true,title:"选择商品",width:"1000px",height:'495px'});
	   Eop.Dialog.init({id:"tagList",modal:true,title:"选择标签",width:"1000px",height:'495px'});
//	   $("div[tabid=tab_0]").hide();
	   $("div[tabid=tab_1]").hide();
	   $("div[tabid=tab_2]").hide();
	   $("div[tabid=tab_3]").hide();
	   $("div[tabid=tab_4]").hide();
	   $("div[tabid=tab_5]").hide();
	   $("#finishStep").hide();
       $("#preStep").hide();
       $("#nextStep").show();
       $("#lastStep").show();
       
	    //提交表单
		$('#finishStep').click(function(){
			var sn=$("input[name='goods.sn']").val();
			var goods_id=$("input[name='goods.goods_id']").val();
			
			
			if(goods_id == undefined || goods_id == null){
				goods_id = '';
			}
			
			//销售商品关联的商品
			var sku = '';
			var skus=$("input[name='sku']");
			for(var i=0;i<skus.length;i++){
				sku+=$(skus[i]).val()+",";
			}
			sku=sku.substring(0, sku.length-1);
			
			//销售商品关联的商品标签
			var tag_code = '';
			var tag_codes=$("input[name='tag_code']");
			for(var i=0;i<tag_codes.length;i++){
				tag_code+=$(tag_codes[i]).val()+",";
			}
			tag_code=tag_code.substring(0, tag_code.length-1);
			
			//销售商品关联的营销标签
			var sale_tag_code = '';
			var sale_tag_codes=$("input[name='sale_tag_code']");
			for(var i=0;i<sale_tag_codes.length;i++){
				sale_tag_code+=$(sale_tag_codes[i]).val()+",";
			}
			sale_tag_code=sale_tag_code.substring(0, sale_tag_code.length-1);
			
			//销售商品关联的商品标签排序
			var sort = '';
			var sorts=$("input[name='sort']");
			for(var i=0;i<sorts.length;i++){
				sort+=$(sorts[i]).val()+",";
			}
			sort=sort.substring(0, sort.length-1);
			
			if(action=="goods!saveAdd.do"){
				var url = ctx+ "/shop/admin/"+action+"?ajax=yes&skus="+sku+"&tag_codes="+tag_code+"&sale_tag_codes="+sale_tag_code+"&sort="+sort;
				Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
							
			}else{//修改商品信息
				var url = ctx+ "/shop/admin/"+action+"?ajax=yes&skus="+sku+"&tag_codes="+tag_code+"&sale_tag_codes="+sale_tag_code+"&sort="+sort;
				Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
			} 
		});
		
	    if(action == "goods!saveEdit.do"){
		   //设置发布商城是否可以修改,发布中和已经发布的不可以修改
		   var publish_status = $("#editForm input[name='publish_status']").val();
		   if(publish_status != "0"){
			   $("#select_org_input").attr("disabled",true);
		   }
		   
	    }
		
   },
   
   jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			alert("操作成功");
			window.location.href="goods!saleGoodsList.do";
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	},
	
   validateForm:function(step,formId){
		
			var obj = null;
			if(step=="next"){
				obj = $(".goods_input .tab li[class='active']").next();
			}
			else{
				obj = $(".goods_input .tab li[class='active']").prev();
			}
			var tabid = $(obj).attr("tabid");
			$(".goods_input .tab>li").removeClass("active");
			$(obj).addClass("active");
			$(".tab-page .tab-panel").hide();
			$("div[tabid=tab_" + tabid + "]").show();
			
			if ($(obj).attr("tabid") == 0) {
	            $("#finishStep").hide();
	            $("#preStep").hide();
	            $("#nextStep").show();
	            $("#lastStep").show();
	        } else if($(obj).attr("tabid") == 5){
	     	    $("#nextStep").hide();
	            $("#lastStep").hide();
	            $("#finishStep").show();
	            $("#preStep").show();
	        }else{
	     	    $("#lastStep").hide();
	            $("#finishStep").hide();
	            $("#nextStep").show();
	            $("#preStep").show();
	        }
	},
}


var selOrg = {
   //选择销售组织
	selectOrg : function(dialog){
		var url = "activity!selectSaleOrg.do?ajax=yes";
		$("#"+dialog).load(url,{"dialog":dialog},function(resp){
			Eop.Dialog.open(dialog);
		});
	}
}



