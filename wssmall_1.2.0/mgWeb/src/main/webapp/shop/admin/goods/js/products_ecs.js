$(function() {
	GoodsAddEdit.init();
	Eop.Dialog.init({id:"offerListDialog",modal:true,title:"套餐列表",width:'750px'});
	Eop.Dialog.init({id:"tagsDialog",modal:true,title:"条形码",width:'550px'});
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
		var type_id = $("#type_id").val();
		if(type_id!=10001 || tabid==15){
			$("#publish_table_0 .greenbtnbig:eq(1)").hide();
		}
		else{
			$("#publish_table_0 .greenbtnbig:eq(1)").show();
		}
		
	});

	$("a[name='nextStep']").bind("click",function(){
		var formId = $(this).attr("formId");
		if(GoodsAddEdit.validateForm("next",formId)){
			var tabid = $("#productTabli .active").attr("tabid");
			var type_id = $("#type_id").val();
			if(type_id==10001){
				if(tabid!=3){
					$("#publish_table_0 .greenbtnbig:eq(1)").hide();
				}
				else{
					$("#publish_table_0 .greenbtnbig:eq(1)").show();
				}
			}
			else{
				$("#publish_table_0 .greenbtnbig:eq(1)").hide();
			}
		}
	});
	
	$("a[name='lastStep']").bind("click",function(){
		window.location.href="goods!productList.do?type=product";
	});	
	
	$("#preStep").bind("click",function(){
		var formId = $(this).attr("formId");
		if(GoodsAddEdit.validateForm("prev",formId)){
			var tabid = $("#productTabli .active").attr("tabid");
			var type_id = $("#type_id").val();
			if(type_id==10001){
				if(tabid==3){
					$("#publish_table_0 .greenbtnbig:eq(1)").show();
				}
				else{
					$("#publish_table_0 .greenbtnbig:eq(1)").hide();
				}
			}
			else{
				$("#publish_table_0 .greenbtnbig:eq(1)").hide();
			}
		}
	});
	
	//开启关键属性
	$("#specOpenBtn").click(function(){
		$("#body1_price").hide();
		$("#specbody2").show();
		$("#haveSpec").val(1);
		GoodsAddEdit.openSpecDialog();
		$("#commonPriceTd").hide();
		$("#storeTr").hide();
		$("#colorTr").hide();
		$("snTr").hide();
	});

	//关闭关键属性
	$("#specCloseBtn").click(function(){
		$("#specbody2 tbody").html("");
		$("#specbody2").hide();
		$("#body1_price").show();
		$("#haveSpec").val(0);
		$("#sku_tr").show();
		$("#colorTr").show();
		$("snTr").show();
		$("#base_table #sku_tr #sku").attr("disabled","");
		$("#commonPriceTd").show();
		
	});
	
	$("#tcAddDialog").unbind("click").bind("click",function() {
		GoodsAddEdit.showOffer();
	});
	
	$("#deleteTcBtn").unbind("click").bind("click",function() {
        $("#tc_content").hide();
        $("#tc_content #tcNode").empty();
    });
	
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
	gridTable:undefined,
	init : function() {
		var self=this;
		$("#specbody2").hide();
		this.gridTable = this.createEmptyTable();
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
		
		//初始化选择按钮
		$("#selectTagBtn").click(function(){
			GoodsAddEdit.showSelectTags();
		});
		
		//提交表单
		$('#finishStep').click(function(){
			var type_id = $("#type_id").val();
			var offerNum = $("#contractTcRelTab #tcNode tr").length;
			if(type_id==10001 && offerNum==0){
				alert("合约计划需要关联套餐，请选择套餐！");
				return ;
			}
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
			
			var cat_id = $("#cat_id").val();
			//定制机需要校验唯一性---zengxianlian
			var postData = "";
			if("690002000"==cat_id&&action=="goods!saveAdd.do"){
				var goodsSn=$("#goods_sn").val();
				postData="typeCode=10000&sn="+goodsSn;
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
							   if(action=="goods!saveAdd.do"){
									var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
									Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
												
							   }else{//修改商品信息
									var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
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
				//套餐类别需要判断其ESS编码唯一性
			}else if(("10002"==cat_id||"690107000"==cat_id||"690109000"==cat_id||"690103000"==cat_id||"690102000"==cat_id||"690104000"==cat_id||"690108000"==cat_id||"690105000"==cat_id||"690101000"==cat_id)&&action=="goods!saveAdd.do"){
				var paramnames=$("input[name='paramnames']");
				//console.info($paramnames);
				var bssCode="";
//				$.each($paramnames, function(i, n){
//					bssCode=$(this).val();
//					if("BSS套餐编码"==bssCode){
//						var bss=$(this).parent().next().children().get(0);
//						//console.info(bss);
//						bssCode=$(bss).val();
//						return false;
//					}
//				});
				for(var i=0;i<paramnames.length;i++){
					bssCode=$(paramnames[i]).val();
					if("BSS套餐编码"==bssCode){
						var bss=$(paramnames[i]).parent().next().children().get(0);
						bssCode=$(bss).val().trim();
						break;
					}	
				}
				
				if(""==bssCode||null==bssCode){
					alert("请输入bss编码!");
					return;
				}
				postData="typeCode=10002&sn="+bssCode;
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
							   if(action=="goods!saveAdd.do"){
									var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
									Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
												
							   }else{//修改商品信息
									var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
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
					var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
					Cmp.ajaxSubmit('productForm', '', url, {}, self.jsonBack,'json');
								
				}else{//修改商品信息
					var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
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
		
		/*$("#goods_sn").bind("blur",function() {
			var goods_id = $("input[name='goods.goods_id']").val();
			if (goods_id == undefined && goods_id == null) {
				goods_id = "";
			}
			var goods_sn = $("#goods_sn").val();
			if (goods_sn != undefined && goods_sn != null && goods_sn != "") {
				$.ajax({
					   url:"goods!checkGoodsSN.do?ajax=yes&sn=" + goods_sn + "&goods_id=" + goods_id,
					   type:"POST",
					   dataType:"json",
					   success:function(reply){
						   if(reply.result == "1"){
							   alert(reply.message);
							   $("#goods_sn").val("");
							   return;
						   }
					   },
					   error:function(err_obj, err_str){
						   
						  // alert(err_str);
					   }
				   });
			}
			
	    });*/
		
	},
	jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			//MessageBox.show("操作成功", 1, 2000);
			MessageBox.show(responseText.message, 1, 2000);
	  		window.location.href=responseText.url;
		}else{
			MessageBox.show(responseText.message, 3, 2000);
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
			return true;
		//}
		//return false;
	},
	openSpecDialog:function(){
		var model_code = $("#model_code").find("option:selected").val();
		var name = $("#goods_name").val();
		Eop.Dialog.open("specdlg");
		$("#spec_dialog").html("loding...");
		$("#spec_dialog").load(basePath+'goodsSpec.do?ajax=yes');
	},
	createEmptyTable:function(){
		var table  = $("#specbody2 table").clone();
		var theadHtml ='<tr>';
		theadHtml+='<th style="width: 100px;">SKU</th>';
		theadHtml+='<th style="width: auto;">货品名称</th>';
		theadHtml+='<th style="width: 100px;">规格</th>';
		theadHtml+='<th style="width: auto;">机型编码</th>';
		theadHtml+='<th style="width: auto;">操作</th>';
		theadHtml+='</tr>';
		table.find("thead").empty().append(theadHtml);
		table.find("tbody").empty();
		table.find("tbody").attr("id","specNode");
		return table;
	},
	doCreatePros:function(specs,names){
		var sn = $("#goods_sn").val();
		$("#sku_tr").hide();
		var self =this;
		for(i in names){
			var f = true;
			$("#storeTh").siblings().each(function(){
				if($(this).html()==names[i]){
					f = false ;
					return ;
				}
			})
			if(f){
				$("#storeTh").before("<th>"+names[i]+"</th>");
			}
		}
		var products = combinationAr(specs);
		for(i in products){
			//每个规格的会员价名称为顺序排列，如第一个货品的会员价名称为：lvid_0和lvPrice_0
			var specar = products[i];
			var html ='<tr><td><input type="text"  name="skus" size="15" autocomplete="off"><input type="hidden"  name="productids" ></td>';
			var specvids="";
			var specids ="";
			html+="<td><input type='text'  name='names' size='15' autocomplete='off'></td>";
			html+="<td>" ;
			for(j in specar){
				
				var spec = specar[j];
				if(j!=0){
					specvids+=",";
					specids+=",";
				}
				specvids+=spec.specvid;
				specids +=spec.specid;
				
				if( parseInt(spec.spectype) == 0){
					html+="<div style='float:left' class='select-spec-unselect spec_selected'><span>" + spec.specvalue +"</span></div>";
				}else{
					var img  = '<img height="20" width="40" title="'+spec.specvalue+'" alt="'+spec.specvalue+'" src="'+spec.specimage+'">';
					html+='<div style="float:left" class="select-spec-unselect spec_selected"><center>'+img+'</center></div>';
				}
				html+="<input type=\"hidden\" name=\"specvalue_"+i+"\" value=\""+spec.specvalue+"\" />";
				
			}
			html+="</td>";
			html+="<td>";
			html+="<input type='hidden' name='specids' value='"+ specids +"' />";
			html+="<input type='hidden' name='specvids' value='"+specvids+"' />" + sn +
					"</td>";	
			html+='<td><a href="javascript:;" ><img  class="delete" src="'+ctx+'/shop/admin/images/transparent.gif" ></a></td>';
			html+="</tr>";
			var trEl =$(html);
			$("#specNode").append(trEl);
			trEl.find("img.delete").click(function(){
				self.deleteProRow($(this));
			});	 
		}
	},
	deleteProRow:function(link){
		if(confirm("确定删除此规格吗？删除后不可恢复"))
			link.parents("tr").remove();
	},
	deleteOfferRow:function(link){
		if(confirm("确定删除此套餐吗？删除后不可恢复"))
			link.parents("tr").remove();
	},
	//展示套餐
	showOffer : function() {
		 
	 	var url =ctx + "/shop/admin/goods!offerList.do?ajax=yes";
	    Eop.Dialog.open("offerListDialog");
	    $("#offerListDialog").load(url,{},function(){});
	},
	//选择条形码
	showSelectTags:function(sn){
		var url =ctx + "/shop/admin/goods!snSelectList.do?ajax=yes";
		Eop.Dialog.open("tagsDialog");
	    if(sn){
	    	 url += "&sn="+encodeURI(encodeURI(sn,true),true);
	    }
		$("#tagsDialog").html("loading...");
		$("#tagsDialog").load(url,function(){});
	}
	
}
