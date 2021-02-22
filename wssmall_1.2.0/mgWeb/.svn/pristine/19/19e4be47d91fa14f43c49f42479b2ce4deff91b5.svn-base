$(function() {
	GoodsAddEdit.init();
	//tab页切换
	$(".goods_input .tab>li").click(function() {
		var formJq =$("#productForm");
		var tabid = $(this).attr("tabid");
		$(".goods_input .tab>li").removeClass("active");
		$(this).addClass("active");
		$(".tab-page .tab-panel").hide();
		$("div[tabid=tab_" + tabid + "]").show();
		
		if ($(this).attr("tabid") == 0) {
            $("#publish_table_0").hide();
        } else {
            $("#publish_table_0").show();
        }
	});
	
	$("img.delete").live("click",function(){
		if(confirm("确定删除该条信息吗？删除后不可恢复")){
			$(this).parents("tr").remove();
		}
	});
	
	$("#selectTagBtn").click(function(){
		GoodsAddEdit.showSelectTags();
	});
	
	$("#productOpenBtn").click(function(){
		//$("#productsSearchBtn").trigger("click");
		var url =ctx + "/shop/admin/goods!productSelectList.do?ajax=yes";
		Eop.Dialog.open("listAllProducts");
		$("#productDialog").html("loading...");
		$("#productDialog").load(url,function(){});
	});
	
	$("#productSelConfirm").click(function(){
		GoodsAddEdit.doCreateTable();
	});
	
	$("#elementOpenBtn").click(function(){
		var url =ctx + "/shop/admin/goods!elementSelectList.do?ajax=yes";
		Eop.Dialog.open("listAllElements");
		$("#elementDialog").html("loading...");
		$("#elementDialog").load(url,function(){});
	});
	
	//商品关联小区打开页面
	$("#communityOpenBtn").click(function(){
		var url =ctx + "/shop/admin/goods!communitySelectList.do?ajax=yes";
		Eop.Dialog.open("listAllCommunitys");
		$("#communityDialog").html("loading...");
		$("#communityDialog").load(url,function(){});
	});
	
	//商品关联县分打开页面
	$("#countryOpenBtn").click(function(){
		var region_type=$("#region_type option:selected").val();  
		if(region_type == '' || region_type == null){
			alert("请选择县分类型！");
			return;
		}
		var url =ctx + "/shop/admin/goods!countrySelectList.do?ajax=yes&region_type="+region_type;
		Eop.Dialog.open("listAllCountrys");
		$("#countryDialog").html("loading...");
		$("#countryDialog").load(url,function(){});
	});
	
	//关联客户
	$("#custIdOpenBtn").click(function(){
		var inputname = "custid";
		var url =ctx + "/shop/admin/goods!inputRelation.do?ajax=yes&inputname="+inputname+"&body="+"custidbody"+"&node="+"custidNode";
		Eop.Dialog.open("listRelation");
		$("#relationDialog").html("loading...");
		$("#relationDialog").load(url,function(){});
	});

	$("#staffIdOpenBtn").click(function(){
		var inputname = "staffid";
		var url =ctx + "/shop/admin/goods!inputRelation.do?ajax=yes&inputname="+inputname+"&body="+"staffidbody"+"&node="+"staffidNode";
		Eop.Dialog.open("listRelation");
		$("#relationDialog").html("loading...");
		$("#relationDialog").load(url,function(){});
	});
	
	$("#developGoodsBtn").click(function(){
		var inputname = "develop";
		var url =ctx + "/shop/admin/goods!inputRelation.do?ajax=yes&inputname="+inputname+"&body="+"developbody"+"&node="+"developNode";
		Eop.Dialog.open("listRelation");
		$("#relationDialog").html("loading...");
		$("#relationDialog").load(url,function(){});
	});
	
	$("#officeRealBtn").click(function(){
		var inputname = "officereal";
		var url =ctx + "/shop/admin/goods!inputRelation.do?ajax=yes&inputname="+inputname+"&body="+"officebody"+"&node="+"officeNode";
		Eop.Dialog.open("listRelation");
		$("#relationDialog").html("loading...");
		$("#relationDialog").load(url,function(){});
	});
	
	
	
	
	
		
	$("#region_type").change(function(){ 
		var region_type_show=$("#region_type_show").val();  
		if(region_type_show != '' && region_type_show != null){
			if(confirm("修改县分类型会清空县分列表，请确认是否修改？")){
				$("#countryNode").html("");
			}else{
				$("#region_type").val($("#region_type_show").val());
			}
		}
	}) 
	
	$("#lastStep").click(function(){
		history.go(-1);
	});
	
	$("#nextStep").bind("click",function(){
		var formId = $(this).attr("formId");
		GoodsAddEdit.validateForm("next",formId);
	});
	
	$("#preStep").bind("click",function(){
		var formId = $(this).attr("formId");
		GoodsAddEdit.validateForm("prev",formId);
	});
	
	$("#price").blur(function(){
		$("input[name='price.lvPrice']").val($(this).val());
	})
	
	//验证
	$("form.validate").validate();
	$("input[type=text]").attr("autocomplete", "off");
	if (CKEDITOR.instances['intro']) {
		CKEDITOR.remove(CKEDITOR.instances['intro']);
	}
	$('#intro').ckeditor();
	$("input[type=submit]").click(function() {
		$.Loading.text = "正在生成缩略图，请稍候...";
	});
});
var GoodsAddEdit={
	init : function() {
		$("[tabid='no']").appendTo($(".append_tab_div"));
		
		//根据类型展示插件桩
		$("#stype_id").bind("change",function(){
			var stype_code = $(this).find("option:selected").attr("stype_code");
			var tab_div = $("div[style_code='"+stype_code+"']");
			//alert(stype_code);
			//add by wui tab页面
			$("[tabid='no']").hide();
			$("[li_type='stype']").hide();
			if(stype_code != ""){
				$("li[li_stype_code='"+stype_code+"']").show().trigger("click");
			}
			tab_div.length>0 && tab_div.show();
		});
		var self = this;
		var action=$("#productForm").attr("action");
		$("#productForm.validate").validate();
		Eop.Dialog.init({id:"listAllProducts",modal:true,title:"货品",width:"1000px",height:'495px'});
		Eop.Dialog.init({id:"listAllCommunitys",modal:true,title:"选择小区",width:"1000px",height:'495px'});
		Eop.Dialog.init({id:"tagsDialog",modal:true,title:"商品包",width:'550px'});
		Eop.Dialog.init({id:"listAllCountrys",modal:true,title:"选择区县",width:"1000px",height:'495px'});
		Eop.Dialog.init({id:"listRelation",modal:true,title:"关联信息",width:"1000px",height:'495px'});
		Eop.Dialog.init({id:"listAllElements",modal:true,title:"同步活动元素",width:"1000px",height:'495px'});
		$("#region_type").val($("#region_type_show").val());
		//提交表单
		$('#finishStep').click(function(){
			try{
				//GoodsArea.beforeSave();
			}catch(e){}
		
			var sn=$("input[name='goods.sn']").val();
			var goods_id=$("input[name='goods.goods_id']").val();
			
			var p_index =0;
			$("table[name='pTable']").each(function(){
				$(this).find("[p_attr='ad_product_id']").each(function(){
					$(this).attr("name","productid_"+p_index);
				})
				p_index++;
			})
			
			if(goods_id == undefined || goods_id == null){
				goods_id = '';
			}
			
			//统一设计规格价格序列 add by wui 
			$(".member_price_box").each(function(index){
				$(".lvid",this).attr("name","lvid_"+(new Number(index)));
				$(".lvPrice",this).attr("name","lvPrice_"+(new Number(index)));
				//alert($(this).html())
			})
			
			//组合【商家_分类_品牌_商品名称】search_key
			var search_key = "";
			var cat_id = $("#cat_id option:selected").text();//商品分类
			if(cat_id != undefined && cat_id != null){
				search_key += "_"+jQuery.trim(cat_id)+"_";
			}
			var brand_id = $("#brand_id option:selected").text();//商品品牌
			if(brand_id != undefined && brand_id != null && "选择品牌" != brand_id){
				search_key += "_"+jQuery.trim(brand_id)+"_";
			}
			var goods_name = $("#goods_name").val();//商品名称
			if(goods_name != undefined && goods_name != null){
				search_key += "_"+jQuery.trim(goods_name)+"_";
			}
			$("#search_key").val(search_key);
			
			//商品关联小区
			var communityCode="";
			var communityCodes=$("input[name='community_code']");
			for(var i=0;i<communityCodes.length;i++){
				communityCode+=$(communityCodes[i]).val()+",";
			}
			communityCode=communityCode.substring(0, communityCode.length-1);
			
			var countyId="";
			var countyIds=$("input[name='countyid']");
			
			for(var i=0;i<countyIds.length;i++){
				countyId+=$(countyIds[i]).val()+",";
			}
			countyId=countyId.substring(0, countyId.length-1);
			
			//商品关联工号
			var staffId="";
			var staffIds=$("input[name='staff_id']");
			
			for(var i=0;i<staffIds.length;i++){
				staffId+=$(staffIds[i]).val()+",";
			}
			staffId=staffId.substring(0, staffId.length-1);
		
			
			//商品关联客户
			var custId="";
			var custIds=$("input[name='cust_id']");
			
			for(var i=0;i<custIds.length;i++){
				custId+=$(custIds[i]).val()+",";
			}
			custId=custId.substring(0, custId.length-1);
			
			
			//商品发展渠道
			var developId="";
			var developIds=$("input[name='develop_id']");
			
			for(var i=0;i<developIds.length;i++){
				developId+=$(developIds[i]).val()+",";
			}
			developId=developId.substring(0, developId.length-1);
		
			
			//商品受理渠道
			var officeId="";
			var officeIds=$("input[name='office_id']");
			
			for(var i=0;i<officeIds.length;i++){
				officeId+=$(officeIds[i]).val()+",";
			}
			officeId=officeId.substring(0, officeId.length-1);

			
			custId = encodeURI(encodeURI(custId)); 
			staffId = encodeURI(encodeURI(staffId)); 
			officeId = encodeURI(encodeURI(officeId)); 
			developId = encodeURI(encodeURI(developId)); 
		
			var val=$("#cat_id").val();
			var typeId=$("option[value='"+val+"']").attr("typeid");
			var matnr = "";
			//裸机商品才需要物料号
			if(typeId == "20003"){
				matnr = $("#hs_matnr").val();
			}
			var s_scheme_id = $("#s_scheme_id").val();
			var s_element_type = $("#s_element_type").val();
			
			if(typeof(s_scheme_id) == "undefined"){
				s_scheme_id = "";
			}
			
			if(typeof(s_element_type) == "undefined"){
				s_element_type = "";
			}
			
			//合约机需要校验唯一性---zengxianlian		所有商品都校验20160504
			if(/*20002==typeId&&*/action=="goods!saveAdd.do"){
				var goodsId="";
				var gIds=$("input[name='goods_ids']");
				for(var i=0;i<gIds.length;i++){
					goodsId+=$(gIds[i]).val()+",";
				}
				goodsId=goodsId.substring(0, goodsId.length-1);
				//var postData="typeCode=20002&sn="+goodsId;
				var postData="sn="+goodsId+"&typeCode="+typeId+"&cat_id="+val+"&matnr=" + matnr;
				
				$.ajax({
				   url:"goods!checkGoodsSaveAdd.do",
				   type:"POST",
				   //dataType:"json",
				   dataType:"html",
				   data :postData,
				   success:function(reply){
					   reply=reply.substring(reply.indexOf("<body >")+7,reply.indexOf("</body>"));
					   	var json= eval("(" + reply+")");
					   if(1==json.result){
						   if(action=="goods!saveAdd.do"){
								var url = ctx+ "/shop/admin/"+action+"?ajax=yes&communityCodes="+communityCode+"&countyIds="+countyId+"&custIds="+custId+"&staffIds="+staffId+"&developIds="+developId+"&officeIds="+officeId+"&s_scheme_id="+s_scheme_id+"&s_element_type="+s_element_type;
								Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
								
							}else{//修改商品信息
								var url = ctx+ "/shop/admin/"+action+"?ajax=yes&communityCodes="+communityCode+"&countyIds="+countyId+"&custIds="+custId+"&staffIds="+staffId+"&developIds="+developId+"&officeIds="+officeId+"&s_scheme_id="+s_scheme_id+"&s_element_type="+s_element_type;
								Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
							} 
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
				if(action=="goods!saveAdd.do"){
					var url = ctx+ "/shop/admin/"+action+"?ajax=yes&communityCodes="+communityCode+"&countyIds="+countyId+"&custIds="+custId+"&staffIds="+staffId+"&developIds="+developId+"&officeIds="+officeId+"&s_scheme_id="+s_scheme_id+"&s_element_type="+s_element_type;
					Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
								
				}else{//修改商品信息
					var url = ctx+ "/shop/admin/"+action+"?ajax=yes&communityCodes="+communityCode+"&countyIds="+countyId+"&custIds="+custId+"&staffIds="+staffId+"&developIds="+developId+"&officeIds="+officeId+"&s_scheme_id="+s_scheme_id+"&s_element_type="+s_element_type;
					Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
				} 
			}
			
		});
		
		$("#turnToImport").bind("click",function(){
				alert("请选择商品类型！");
		});	
		//解决"上传"按钮总在最前
		$('.closeBtn').click(function(){
			$('#up').parent().show();
		});
	},
	jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			alert("操作成功");
			window.location.href="goods!goodsList.do?type=goods";
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	},
	validateForm:function(step,formId){
		
		//var formJq =$("#"+formId);
		//if(formJq.do_validate()) {
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
                $("#publish_table_0").hide();
            } else {
                $("#publish_table_0").show();
            }
		//}
	},
	
	doCreateTable:function(){
		$("#proGridform ")
	},
	
	showSelectTags:function(search_tag_name){
		var url =ctx + "/shop/admin/goods!tagsSelectList.do?ajax=yes";
		Eop.Dialog.open("tagsDialog");
	    if(search_tag_name){
	    	 url += "&search_tag_name="+encodeURI(encodeURI(search_tag_name,true),true);
	    }
		$("#tagsDialog").html("loading...");
		$("#tagsDialog").load(url,function(){});
	},
	deleteProductRow:function(link){
		if(confirm("确定删除该条信息吗？删除后不可恢复"))
			link.parents("tr").remove();
	}
}
