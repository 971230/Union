var GoodsN = {
		spec_imgs:undefined,
		init : function(){
			var self = this;
			Eop.Dialog.init({id:"specCfgDialog",modal:false,title:"选择商品规格",width:"750px"});
			Eop.Dialog.init({id:"memberpricedlg",title:"编辑会员价格",width:'500px'});
			Eop.Dialog.init({id:"goods_img_selected",title:"关联相册",height:"300px"});
			$("#openSpec").click(function(){ //开启规格，弹出规格选择框
				self.openSpecCfg();
				$("#lvPriceDiv").hide();
				$("#haveSpec1111111").val("1");
				$("#goodsPriceTr th:eq(1)").css("visibility","hidden");//隐藏销售价
				$("#goodsPriceTr td:eq(1)").css("visibility","hidden");//隐藏销售价
				$("#goodsPriceTr th:eq(2)").css("visibility","hidden");//隐藏销售价
				$("#goodsPriceTr td:eq(2)").css("visibility","hidden");//隐藏销售价
				$("#price").removeAttr("required");
				$("#price").val("");
			})
			$("#closeSpec").click(function(){ //关闭规格
				$("#specDiv tr:gt(0)").remove();
				$("#lvPriceDiv").show();
				$("#specDiv").hide();
				$("#haveSpec1111111").val("0");
				$("#goodsPriceTr th:eq(1)").css("visibility","visible");//隐藏销售价
				$("#goodsPriceTr td:eq(1)").css("visibility","visible");//隐藏销售价
				$("#goodsPriceTr th:eq(2)").css("visibility","visible");//隐藏销售价
				$("#goodsPriceTr td:eq(2)").css("visibility","visible");//隐藏销售价
				$("#price").attr("required","true");
			})
			$(".pop_close").click(function(){ //关闭规格弹出框
				$("#pop_div").hide();
				var spec_num = $("#pop_div .grid_g_n tr").size()-1; //选择的商品规格数【总行数减去标题行】
				if(spec_num==0){
//					$("#specDiv").hide();
				}
				var prod_num = $("#specDiv .grid_b_su tr").size()-1; //已生成的规格货品数量【总行数减去标题行】
				if(prod_num==0){
					$("#lvPriceDiv").show();
					$("#haveSpec1111111").val("0");
					$("#goodsPriceTr th:eq(1)").css("visibility","visible");//隐藏销售价
					$("#goodsPriceTr td:eq(1)").css("visibility","visible");//隐藏销售价
					$("#goodsPriceTr th:eq(2)").css("visibility","visible");//隐藏销售价
					$("#goodsPriceTr td:eq(2)").css("visibility","visible");//隐藏销售价
					$("#price").attr("required","true");
//					$("#specDiv").hide();
				}
			})
			$(".graph_tab li").click(function(){//切换规格tab页
				self.switchTab($(this));
			})
			$(".gray_btn").click(function(){//全部添加规格
				var specdiv = $(this).parent().parent();
				$("li",$(specdiv)).each(function(){
					$(this).addClass("curr");
					self.addSpecTr($(this));
					self.saveSpec($(this));
				})
			})
			$(".pop_div .pop_btn_box .blueBtns").click(function(){//根据所选则的规格生成所有货品
				self.doGenerateProd();
			})
			$(".blue_a").click(function(){//清空规格
				var specdiv = $(this).parent().parent();
				$("li",$(specdiv)).each(function(){
					$(this).removeClass("curr");
					self.removeSpecTr($(this));
					var specid = $(this).attr("specid");
					$("#spec_"+specid).val(""); //清空保存的选中规格
					$("#spec_"+specid).attr("specid","");
					$("#spec_"+specid).attr("specid","");
				})
			})
			$(".pop_warp .size_list .clearfix li").live("click",function(){//选择商品规格
				if($(this).attr("class")=="curr"){
					self.removeSpecTr($(this));
					self.removeSpec($(this));
				}
				else{
					self.addSpecTr($(this));
					self.saveSpec($(this));
				}
			})
			$(".grid_n_div h2 a").live("click",function(){//展开收起
				var clz = $(this).attr("class");
				if("closeArrow"==clz){
					$(this).attr("class","openArrow");
					$(this).parent().next().hide();
				}
				else if("openArrow"==clz){
					$(this).attr("class","closeArrow");
					$(this).parent().next().show();
				}
			})
			$("#addSaveBtn").click(function(){
				self.saveGoods();
			});
			
		},
		saveGoods : function(){
			var self = this;
			/*处理配件开始*/
			self.saveAdjunct();
			/*处理配件结束*/
			/*会员价格处理开始*/
			self.saveLvPrice();
			/*会员价格处理结束*/
			/*图片处理*/
			var flag =  GoodsImgUpload.setPicValue();
			if(!flag){
				return false;
			}
			/*图片处理*/
			var action = $("#goodsEditForm").attr("action");
			var url = ctx+ "/shop/admin/"+action+"?ajax=yes";
			Cmp.ajaxSubmit("goodsEditForm", "", url, {}, self.jsonBack,"json");
		},
		jsonBack : function(responseText) { 
			if (responseText.result == 1) {
				alert("操作成功");
				window.location.href="goodsN!list.do";
			}
			if (responseText.result == 0) {
				alert(responseText.message);
			}	
		},
		saveLvPrice : function(){
			var self = this;
			$("#productNode tr").each(function(){
				var tr = $(this);
				var box = $(".member_price_box",tr);
				var lvid = "";
				var lvPrice = [];
				$(box).find("input[class='lvid']").each(function(){
					var _lvid = $(this).val();
					var _lvPrice = $("input[lvid='"+_lvid+"']",box).val();
					if(lvid == ""){
						lvid = _lvid;
					}
					else{
						lvid = lvid + "," + _lvid;
					}
					lvPrice.push(_lvPrice);
				})
				tr.find("input[attrName='lvIdValues']").val(lvid);
				tr.find("input[attrName='lvPriceValues']").val(lvPrice.join(","));
			});
		},
		saveAdjunct : function(){
			$("#adjunct_content table[name='pTable']").each(function(){
				var product_ids = "";
				var table = $(this);
				$(".adjunctitem_body tr",table).each(function(){
					product_ids = product_ids + $(this).find("input[p_attr='ad_product_id']").val();
					product_ids += ",";
				})
				if(product_ids != ""){
					product_ids = product_ids.substring(0, product_ids.length-1);
				}
				table.find("input[attrName='adj_productids']").val(product_ids);
			})
		},
		openSpecCfg : function(){
			$("#pop_div").show();
//			Eop.Dialog.open("pop_div");
		},
		switchTab : function(tab){
			$(tab).addClass("selected"); //选中当前
			$(tab).siblings().removeClass("selected"); //其他兄弟节点取消选中
			var idx = $(tab).attr("tabid");
			$(".pop_warp div").each(function(){
				var id = $(this).attr("id");
				if(id){
					if(("tab"+idx)==id){
						$(this).show();
					}
					else{
						$(this).hide();
					}
				}
			})
		},
		addSpecTr : function(li){//添加规格
			var self = this;
			$(li).addClass("curr"); //选中
			var specid = $(li).attr("specid");
			var specvid = $(li).attr("specvid");
			var value = $(li).attr("val");
			var spectype = $(li).attr("spectype");
			var imgurl = $(li).attr("imgurl");
			var exists = false;
			$(".grid_g_n tr:gt(0)").each(function(){
				var text = $(".redFont", $(this)).attr("val");
				if(value == text){
					exists = true;
				}
			})
			if(!exists){//如果此规格没有选中，则添加规格
				var trHtml = "";
				if(spectype == "0"){//文字
					trHtml = '<tr><td class="redFont" val="'+value+'" specid="'+specid+'" spectype="'+spectype+'" imgurl="">'+
						'<input type="hidden" name="specvalues" val="'+value+'" specvid="'+specvid+'" specid="'+specid+'" spectype="'+spectype+'" imgurl=""/>'+value+'</td><td class="align_left"><input type="hidden" name="goodsExtData.spec_imgs" specimgs="" specvid=""/><a name="imgLinkBtn" clk="" href="javascript:void(0);">关联</a></td><td><a href="#" class="del">删除</a></td></tr>';
				}
				else if(spectype == "1"){//图片
					trHtml = '<tr><td class="redFont" val="'+value+'" specid="'+specid+'" spectype="'+spectype+'" imgurl="'+imgurl+'"><input type="hidden" name="specvalues" val="'+value+'" specvid="'+specvid+'" specid="'+specid+'" spectype="'+spectype+'" imgurl="'+imgurl+'"/>'+
						'<img title="'+value+'" height="20" width="34" src="'+imgurl+'"></img></td><td class="align_left"><input type="hidden" name="goodsExtData.spec_imgs" specimgs="" specvid=""/><a name="imgLinkBtn" clk="" href="javascript:void(0);">关联</a></td><td><a href="#" class="del">删除</a></td></tr>';
				}
				$(".grid_g_n").append(trHtml);
				$(trHtml).find("a.del").live("click",function(){//注册删除点击事件
					$(this).parents("tr").remove(); //删除规格
					var specvalue = $(this).parents("tr").find("td:eq(0)").attr("val");
					$(".size_list li[val='"+specvalue+"']").removeClass("curr"); //取消选中
					self.removeSpec($(".size_list li[val='"+specvalue+"']"));
					return false;
				});
				$(trHtml).find("a[name='imgLinkBtn']").live("click",function(){
					$(this).attr("clk","yes");//记录点击的关联超链接
					self.addSpecImg(specvid,$(this));
					return false;
				})
			}
		},
		addSpecImg:function(specvid,link){
			$("#goods_img_selected ul li").remove();
			$(".img_list .img_box").each(function(){
				var img = $(this).find("input[name='picnames']").val();
				if(typeof(img)!='undefined'){
					$("#goods_img_selected ul").append("<li><input type='radio'  name='gsimg' value='" +  img +"'/><img src=\""+ img +"\" width='30px' height='30px' /></li>" );	
				}
			});
			$("#goods_img_selected .blueBtns").click(function(){
				var temp_specvid = $("#spec_vid").val();
				var _specvid = ","+$("#spec_vid").val()+",";
				if(_specvid.length<=2){
					temp_specvid = specvid;
				}
				else{
					if(_specvid.indexOf(","+specvid+",")<0){
						temp_specvid = temp_specvid+","+ specvid;
					}
				}
				var spec_img = $("#goods_img_selected input[@name='gsimg'][checked]").val();
				var _spec_img = $("#spec_imgs").val();
				if(_spec_img == ""){
					_spec_img = spec_img;
				}
				else{
					if(_spec_img.indexOf(spec_img)<0){
						_spec_img = _spec_img + ","+ spec_img;
					}
				}
				$(".pop_warp .grid_g_n a[clk='yes']").parent().find("input[name='goodsExtData.spec_imgs']").attr("specvid",temp_specvid);
				$(".pop_warp .grid_g_n a[clk='yes']").parent().find("input[name='goodsExtData.spec_imgs']").attr("specimgs",_spec_img);
				$(".pop_warp .grid_g_n a[clk='yes']").before("<img src=\""+ spec_img +"\" width='30px' height='30px'/>");
				
				$(".pop_warp .grid_g_n a[clk='yes']").attr("clk","");
				Eop.Dialog.close("goods_img_selected");
				return false;
			});
			Eop.Dialog.open("goods_img_selected");
		},
		removeSpecTr:function(li){//删除规格
			$(li).removeClass("curr");//取消选中
			var value = $(li).attr("val");
			$(".grid_g_n tr:gt(0)").each(function(){
				var text = $(".redFont", $(this)).attr("val");
				if(value == text){
					$(this).remove(); //把规格从列表中删除
				}
			})
		},
		generateProd : function(lvPriceInputHtml){
			//alert(lvPriceInputHtml);
			var self = this;
			var specvalues = [];
			var specnameArr = [];
			var i = 0;
			$("#specDiv").show();
			var spec_num = $("#pop_div .grid_g_n tr").size()-1;
			if(spec_num==0){
				alert("请选择商品规格");
				return ;
			}
			$("#specDiv .grid_b_su tr:gt(0)").remove();
			$("input[name='selected_specs']").each(function(index,element){
				if($(element).val()!=""){
					specvalues[i] = [];
					specnameArr[i] = $(element).attr("specname");
					var j = 0;
					$(".grid_g_n input[specid='"+$(element).attr("specid")+"']").each(function(){
						var specid = $(this).attr("specid");
						var spectype = $(this).attr("spectype");
						var specvid = $(this).attr("specvid");
						var specvalue = $(this).attr("val");
						var specimage = $(this).attr("imgurl");
						var spec = {'specid':specid,'specvid':specvid,'specvalue':specvalue,'spectype':spectype,'specimage':specimage};
						specvalues[i][j] = spec;
						j++;
					});
					i++;
				}
			});
			for(i in specnameArr){
				var name = specnameArr[i];
				var th = "<th>"+name+"</th>";
				var exists = false;
				$(".grid_b_su tr:eq(0) th").each(function(index,element){
					if($(element).text() == name){
						exists = true;
					}
				})
				if(!exists){
					$(".grid_b_su tr:eq(0) th:eq("+i+")").after(th);
				}
			}
			var products = self.combArr(specvalues);
			for(i in products){
				var specar = products[i];
				var specvids="";
				var specvalues = "";
				var specids ="";
				var priceInputHtml = lvPriceInputHtml.replace(/name="lvid"/g,'name="lvid_'+i+'"');
				priceInputHtml = priceInputHtml.replace(/name="lvPrice"/g,'name="lvPrice_'+i+'"');
				var trHtml = '<tr><td><input type="text" name="goodsExtData.sns" id="sns" value="" class="ipt_new" required="true"/><input type="hidden" name="goodsExtData.spec_productids" id="spec_productids" value="" class="ipt_new"/></td>';
				for(j in specar){
					
					var spec = specar[j];
					if(j!=0){
						specvids+=",";
						specids+=",";
						specvalues+=",";
					}
					specvids += spec.specvid;
					specids += spec.specid;
					specvalues += spec.specvalue;
					if(spec.spectype == 1){
						
						trHtml += '<td><div class="color_box" style="background:url('+spec.specimage+');"></div></td>';
					}
					else{
						trHtml += '<td><div class="size_box">'+spec.specvalue+'</div></td>';
					}
				}
				trHtml += '<td>';
				trHtml += '<input type="text" name="goodsExtData.stores" value="" class="ipt_new" />';
				trHtml += '<input type="hidden" name="goodsExtData.specids" value="'+specids+'"/>';
				trHtml += '<input type="hidden" name="goodsExtData.specvids" value="'+specvids+'"/>';
				trHtml += '<input type="hidden" name="goodsExtData.specValues" value="'+specvalues+'"/>';
				trHtml += '</td>';
				trHtml += '<td>';
				trHtml += '<input type="text" name="goodsExtData.spec_prices" value="" class="ipt_new" required="true"/><input type="hidden" attrName="lvPriceValues" name="goodsExtData.lvPriceValues" value=""/><input type="hidden" attrName="lvIdValues" name="goodsExtData.lvIdValues" value=""/><a name="lvPriceCgf" href="#" class="gray_btn" style="margin-left:5px;">会员价</a>';
				trHtml += '<div class="member_price_box" index="'+i+'">'+priceInputHtml+'</div>';
				trHtml += '</td>';
				trHtml += '<td><input type="text" name="goodsExtData.weights" value="" class="ipt_new" /></td>';
				trHtml += '<td><a href="#" class="delprod"><img src="'+ctx+'/shop/admin/goodsN/images/ico_close.png" width="16" height="16" />删除</a></td>'
				trHtml += '</tr>';
				
				$("#specDiv .grid_b_su").append(trHtml);
				$(trHtml).find("a.delprod").live("click",function(){
					self.deleteProd($(this));
					return false;
				});
				$(trHtml).find("a[name='lvPriceCgf']").live("click",function(){
					self.bindMbPricBtnEvent($(this));
					return false;
				});
				$("#pop_div").hide();
			}
			
		},
		doGenerateProd : function(){
			var self = this;
			$.ajax({
				url:ctx+'/shop/admin/memberPrice!disLvInputN.do?ajax=yes',
				dataType:'html',
				success:function(html){
					self.generateProd(html);
				},
				error:function(){
					alert("获取会员价出错");
				}
			});
		},
		deleteProd : function(link){
			if(confirm("确定删除此规格吗？删除后不可恢复"))
				link.parent().parent().remove();
		},
		bindMbPricBtnEvent:function(link){
			var self = this;
			self.priceBox= $(link).next(".member_price_box"); //当前的会员价box
			var price = $(link).siblings(".ipt_new").val(); // 获取价格，规则为当前操作按钮的
			if( parseFloat(price)!= price) {
				alert("价格必须是数字");
				return false;
			}else{
				Eop.Dialog.open("memberpricedlg");
				var url = 'memberPrice!listMemberPriceN.do?price='+price+'&ajax=yes';
				$("#memberpricedlg").load(url,function(){
					self.initDlg();
				});
			}
		},
		initDlg:function(){
			var self = this;
			var editor = $("#levelPriceEditor");
			this.priceBox.find(".lvPrice").each(function(){//由.lvPrice找到 价格输入框
				var lvid = $(this).attr("lvid");
				$("#levelPriceEditor input.lvPrice[lvid='"+lvid+"']").val(this.value);
			});
			
			$("#mbPriceBtn").click(function(){
				self.createPriceItems();
			});
		},
		//由对话框的input 创建会员价格表单项
		createPriceItems:function(){
			var member_price_box = this.priceBox;
			member_price_box.empty();
			var index =  member_price_box.attr("index");
			var haveSpec = $('#haveSpec1111111').val() ;
			$("#levelPriceEditor>tbody>tr").each(function(){
				var tr = $(this);
				var lvid = tr.find(".lvid").val(); 
				var lvPrice = tr.find(".ipt_new").val(); 
				//如果开启规格，货品生成会员价时要按顺序指定name_i，为了后台读取每个货品的会员价方便
				//没开启规格，生成此商品的会员价不需要指index，即name直接为lvid,lvPric即可
				if(index || haveSpec == '1'){
					member_price_box.append("<input type='hidden' class='lvid' name='lvid_"+index+"' value='"+lvid+"' />");
					member_price_box.append("<input type='hidden' class='lvPrice' name='lvPrice_"+index+"' value='"+lvPrice+"' lvid='"+lvid+"'/>");
				}else{
					member_price_box.append("<input type='hidden' class='lvid' name='lvid' value='"+lvid+"' />");
					member_price_box.append("<input type='hidden' class='lvPrice' name='lvPrice' value='"+lvPrice+"' lvid='"+lvid+"'/>");
				}
			});
			Eop.Dialog.close("memberpricedlg");
		},
		saveSpec : function(li){
			var specid = $(li).attr("specid");
			var specvalue = $(li).attr("val");
			var specvalues = $("#spec_"+specid).val();
			if(specvalues != ""){
				if(specvalues.indexOf(specvalue)<0){
					specvalues = specvalues + "," + specvalue;
				}
			}
			else{
				specvalues = specvalue;
			}
			$("#spec_"+specid).val(specvalues);
		},
		removeSpec : function(li){
			var specid = $(li).attr("specid");
			var specvalue = $(li).attr("val");
			var specvalues = $("#spec_"+specid).val();
			var specArr = specvalues.split(",");
			specArr.splice($.inArray(specvalue,specArr),1); //删除数组中的一个规格值
			$("#spec_"+specid).val(specArr.join(","));
		},
		combArr:function(spec_ar){
			var self = this;
			var ar;
			var m =0 ;
			if(spec_ar.length==1){ return self.combination(spec_ar[0]);}
			while(m<spec_ar.length-1){
				if(m==0){
					ar = spec_ar[0];
				}
				ar = self.combination(ar,spec_ar[m+1]);
				m++;
			};
			return ar;
			
		},
		combination:function(ar1,ar2){
			var self = this;
			var ar = new Array();
			var k=0;
			if(!ar2) { //数组只有一唯的情况
				for(var i=0;i<ar1.length;i++){
					ar[k] = [ar1[i]];
					k++;
				}	
				return ar;
			}
			for(var i=0;i<ar1.length;i++){
				for(var j=0;j<ar2.length;j++){
					if(ar1[i].constructor == Array ){
						ar[k]= self.putAr(ar1[i],ar2[j]);
					}else{
						ar[k] = [ar1[i],ar2[j]];
					}	 
					k++;
				}
			}
			return  ar;
		},
		putAr:function(ar1,obj){		
			/**
			* 将一个值放在一个数组未尾，形成新的数据
			*/
			var newar =[];
			for(var i=0;i<ar1.length;i++){
			    	newar[i] =ar1[i];
			}
			newar[ar1.length] = obj;
			return newar;
		}
}
$(function(){
	GoodsN.init();
})