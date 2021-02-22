//活动列表
var ActivityList = {
	   init:function(){
		   var self =this;
		   Eop.Dialog.init({id:'q_activity_goods_dialog',modal:true,title:'货品包选择',width:'850px'});
		   Eop.Dialog.init({id:'q_activity_orgs_dialog',modal:true,title:'活动商城',width:'450px'});
		   Eop.Dialog.init({id:"activitySelectShopDialog",modal:true,title:"选择组织",width:"1000px"});
		   //选择商品
		   $("#q_select_goods_btn").unbind("click").bind("click",function() {
			   var params = {"dialog":"q_activity_goods_dialog","goods.type":"package"};
			   addGoods.showGoods(params);
		   });
			//选择销售组织
			$("#q_select_org_input").bind("click",function(){
				selOrg.selectOrg("q_activity_orgs_dialog");
			});
			//批量发布活动
			$("#batchPubishBtn").bind("click",function(){
				self.batchPublishActivity();
			});
			
			$(".gridbody").find("a[name='enableActivity']").live("click",function(){
				
				if(confirm("您确定要生效活动吗?")){
					var activity_id = $(this).closest("tr").find("input[name='activity_id']").val();
					var org_ids = $(this).closest("tr").find("input[name='org_ids']").val();
					self.editEnable(activity_id,1,org_ids);
				}
			});
			$(".gridbody").find("a[name='disableActivity']").unbind("click").bind("click",function(){
				
				/**
				 * 正在发布的不可以修改状态
				 * 未发布的可以修改状态
				 * 已经发布的修改状态需要些队列
				 */
				var pub_status = $(this).closest("tr").find("input[name='pub_status']").val();
				if(pub_status == '2'){
					alert('该活动状态为发布中，不可以启用或停用！');
					return ;
				}
				if(confirm("您确定要停用活动吗?")){
					var activity_id = $(this).closest("tr").find("input[name='activity_id']").val();
					var org_ids = $(this).closest("tr").find("input[name='org_ids']").val();
					self.editEnable(activity_id,0,org_ids);
				}
			});
			//活动发布
			$("#do_publish_activity").unbind("click").bind("click",function(){
				self.publishActivity();
			});
			//搜索
			$("#searchFormSubmit").unbind("click").bind("click",function(){
				$("#queryActivityForm").attr("action",'activity!listActivityECS.do')
				$("#queryActivityForm").submit();
			});
			$("#do_export_activity").unbind("click").bind("click",function(){
				var begin_time=$("input[name='activity.begin_time']").val();
				var end_time=$("input[name='activity.end_time']").val();
				if((begin_time==null&&end_time==null)||(begin_time==""&&end_time=="")){
					alert("请先填写活动生效的时间");
				}
				else{
					$("#queryActivityForm").attr("action","activity!exportActivity.do?ajax=yes&excel=yes");
					$("#queryActivityForm").submit();
				}
			})
			
	   },
	   batchPublishActivity:function(){
		   $("#queryActivityForm").attr("action","activity!batchActivityPublish.do");
			var reg = new RegExp("^[0-9]*$");
			var batchNum = $.trim($("#batchNumInput").val());
			if(batchNum!=""){
				if(!reg.test(batchNum)){
					alert("请输入不能大于500的有效数字！");
					return ;
				}
				if(parseInt(batchNum)>500){
					alert("请输入不能大于500的有效数字！");
					return ;
				}else{
					$("#batchNumInput2").val(batchNum);
//					var url = "goods!selectShopDialog.do?ajax=yes";
//					$("#activitySelectShopDialog").load(url, function (responseText) {
//							Eop.Dialog.open("activitySelectShopDialog");
//					});
					$.Loading.hide();
					$.Loading.show("正在发布，请稍后...");
					$("#queryActivityForm").submit();//活动批量发布
				}
			}else{
				alert("请输入批量发布的数量！");
			}
	   },
	   publishActivity:function(){
		   var me = this;
		   var arr = $(".gridbody").find("tbody").find("input[type='checkbox']:checked");
		   if(arr.length>0){
			   var id = '';
			   var orgids = '';
			   var tingyong_names = '';
			   var yifabu_names = '';
			   var endable_flag = true;
			   var status_flag = true;
			   arr.each(function(index,item){
				   id = id + $(item).val()+";";
				   orgids = orgids + $(item).attr("org_ids")+";";
				   var status = $(item).attr("statustxt");
				   var enable = $(item).attr("isenable");
				   if(enable!="1"){
					   tingyong_names = tingyong_names + $(item).attr("act_name")+",";
					   endable_flag = false;
				   }
				   if(status!="0"){
					   yifabu_names = yifabu_names + $(item).attr("act_name")+",";
					   status_flag = false;
				   }
			   })
			   if(!endable_flag){
				   alert("活动："+tingyong_names.substring(0, tingyong_names.length-1)+" 已经停用，不能发布！");
				   return ;
			   }
			   if(!status_flag){
				   alert("活动："+yifabu_names.substring(0, yifabu_names.length-1)+" 已经发布，不能再次发布！");
				   return ;
			   }
			   $.Loading.show('正在响应您的请求，请稍侯...');
			   $.ajax({
				   url:"activity!doPublishActivity.do?ajax=yes&activity_id="+id.substring(0, id.length-1)+"&org_ids="+orgids.substring(0, orgids.length-1),
				   type:"POST",
				   dataType:"json",
				   success:function(reply){
					   if(reply.result=="0"){
						   alert(reply.message);
					   }else{
						   alert(reply.message);
					   }
					   window.location.href = ctx + "/shop/admin/activity!listActivityECS.do";
				   },
				   error:function(){
				   }
			   });
			   
		   }else{
			   alert("请选择要发布的活动！");
		   }
	   },
	   //修改活动状态
	   editEnable:function(activity_id,enable,org_ids){
			$.ajax({
				async:false,
				type : "POST",
				dataType : 'json',
				data :{"activity.id":activity_id,"activity.enable":enable,"activityAttr.act_org_ids":org_ids},
				url : "activity!editEnableECS.do?ajax=yes",
				success : function(reply) {
					if(reply.result == true){
						
						alert("操作成功!");
						$("#queryActivityForm").attr("action",'activity!listActivityECS.do')
						$("#queryActivityForm").submit();
						//window.location.href = ctx + "/shop/admin/activity!listActivityECS.do";
					}else{
						alert("操作失败!");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
//					alert("出错了");
				}
			});
			
	   }
}

//新增或修改活动
var ActivityAddEdit = {
   init:function(){
	   var self =this;
	   Eop.Dialog.init({id:'activity_goods_dialog',modal:true,title:'货品包选择',width:'850px'});
	   Eop.Dialog.init({id:'activity_orgs_dialog',modal:true,title:'活动商城',width:'450px'});
	   Eop.Dialog.init({id:'activity_prop_dialog',modal:true,title:'活动赠品',width:'850px'});
	   Eop.Dialog.init({id:'activity_goods_sel_dialog',modal:true,title:'商品选择',width:'850px'});
		$("a[name='gooddelete']").live("click",function(){
		 	var tr = $(this).closest("tr");
		 	tr.remove();
		});
		$("a[name='gooddeletep']").live("click",function(){
		 	var tr = $(this).closest("tr");
		 	tr.remove();
		});
	   var action = $("#editForm input[name='action']").val();
	   if(action == 'edit'){
		   //$("#editForm input[name='activity.pmt_code']").attr("disabled",true);
		   //$("#editForm select[name='activity.enable']").attr("disabled",true);
		   
		   //设置发布商城是否可以修改,发布中和已经发布的不可以修改
		   var publish_status = $("#editForm input[name='publish_status']").val();
		   if(publish_status != "0"){
			   $("#select_org_input").attr("disabled",true);
		   }
		   
	   }
	   //选择商品
	   $("#select_package_btn").bind("click",function() {
		   var params = {"dialog":"activity_goods_dialog","goods.type":"package"};
		   addGoods.showGoods(params);
	   });
		//选择销售组织
		$("#select_org_input").bind("click",function(){
			selOrg.selectOrg("activity_orgs_dialog");
		});
		//选择赠品
		$("#addPropertyBtn").bind("click",function(){
			var params = {"dialog":"activity_prop_dialog","goods.type":"product"};
			addGoods.showGoods(params);
		});
		$("a[name=add_goods_dg]").bind("click",function(){
			var op = $(this).attr("op");//选择类型
			var type_id=$(this).attr("type_id");
			var params = {"dialog":"activity_prop_dialog","goods.type":"product","s_type":op,"type_id":type_id};
			addGoods.showGoods(params);
		});
		$("input[name=add_goods_dg]").bind("click",function(){
			var op = $(this).attr("op");//选择类型
			var type_id=$(this).attr("type_id");
			var params = {"dialog":"activity_goods_sel_dialog","goods.type":"goods","s_type":op};
			addGoods.showGoods(params);
		});
		$("#saveBtn").bind("click",function(){
			self.saveActivity();
		});
		$("#saveAndPublishBtn").bind("click",function(){
			self.saveActivity();
		});
		$("#activity_pmt_code").bind("blur",function(){
			checkPmtCode();
		});
		$("#promotion_pmt_type").change(function(){
			if($(this).val() == '003'){
				self.hideGoods();
			}else{
				self.showGoods();
			}
		});
   },
   hideGoods:function(){
	   $("#pmt_goods_names_tr").hide();
	   $("#pmt_goods_ids").val("");
	   $("#pmt_goods_names").val("");
   },
   showGoods:function(){
	   $("#pmt_goods_names_tr").show(); 
   },
   //保存活动
   saveActivity:function(){
	   var self =this;
	   var t1=$("input[name='activity.begin_time']").val();
	   var t2=$("input[name='activity.end_time']").val();
	   if(t1!=""&&t2!=""){
		   if(t1>=t2){
			   alert("生效时间不能大于失效时间，请检查！");
			   return ;
		   }
	   }
	   var regions=$("#region_hivp");
	   var region=regions[0].value;
	   if(region.length<=2){
		   alert("至少选择1个活动地市!");
		   return;
	   }
	   var pmt_type=$("#promotion_pmt_type").find("option:selected").val();
	   if("021"==pmt_type){
		   var table_kdzd = $("#kdzd_goods_tb");
		   var cat_ids = table_kdzd.find("input[name='cat_id']");
		   var c694301000 = 0;//普通宽带终端
		   var c694302000 = 0;//增值宽带终端
		   var t42000 = 0;//宽带增值业务
		   for(var i=0;i<cat_ids.length;i++){
			   var cat_id = cat_ids[i].value;
			   if("694301000"==cat_id){
				   c694301000++;
			   }else if("694302000"==cat_id){
				   c694302000++;
			   }else if("694201000"==cat_id||"694202000"==cat_id){
				   t42000++;
			   }
		   }
		   if(!((c694301000==1&&c694302000==0&&t42000==0)
				   ||(c694301000==0&&c694302000==1&&t42000==1))){
			   alert("宽带终端组成规则:\n一个普通终端货品;\n或一个增值终端货品+一个增加业务货品");
			   return;
		   }
	   }
	   var formJq =$("#editForm");
		if(formJq.do_validate()) { 
			$("#saveBtn").attr("disabled",true);
			$("#saveBtn").text('正在保存中');
			
			//用戶保存時的AJAX選項
			var options = {
					async:false,
					type : "POST",
					dataType : 'json',
					url : "activity!saveOrUpdateActECS.do?ajax=yes",
					success : function(reply) {
						if(reply.result == 'true'){
							alert("保存成功!");
							$("#saveBtn").removeAttr("disabled");
							$("#saveBtn").text("保存");
							window.location.href = ctx + "/shop/admin/activity!listActivityECS.do";;
						}
						else{
							alert("保存失败!");
							$("#saveBtn").removeAttr("disabled");
							$("#saveBtn").text("保存");
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						alert("出错了 ! XMLHttpRequest="+XMLHttpRequest);
						alert("错误类型textStatus: "+textStatus);
						alert("异常对象errorThrown: "+errorThrown);
						$("#saveBtn").removeAttr("disabled");
						$("#saveBtn").text("保存");
					}
				};
			
			//先檢驗
			var checkoptions = 
				{
					async:false,
					type:'POST',
					dataType:'json',
					url:'activity!checkBeforeSave.do?ajax=yes',
					//{"code":,"message":}
					success:function(reply)
					{
						if(0==reply.code)
							{
								$("#editForm").ajaxSubmit(options);
							}
						else
							{
								var r=confirm("提示: "+reply.message);
								if(r==true)
									{
										$("#editForm").ajaxSubmit(options);										
									}
							}
						$("#saveBtn").removeAttr("disabled");
						$("#saveBtn").text("保存");
					},
					error:function(XMLHttpRequest, textStatus, errorThrown)
					{
						alert("出错了 ! XMLHttpRequest="+XMLHttpRequest);
						alert("错误类型textStatus: "+textStatus);
						alert("异常对象errorThrown: "+errorThrown);
						$("#saveBtn").removeAttr("disabled");
						$("#saveBtn").text("保存");
					}
				};	
			
			$("#editForm").ajaxSubmit(checkoptions);
		}
   },
   //获取活动表单数据   
   getActivityData:function(){
	  var params = {};
	  $("#editForm input").each(function(idx,item){
		  var name = $(item).attr("name");
		  var value = $(item).val();
		  params[name] = value;
	  });
	  $("#editForm select").each(function(idx,item){
		  var name = $(item).attr("name");
		  var value = $(item).val();
		  params[name] = value;
	  });
	  $("#editForm textarea").each(function(idx,item){
		  var name = $(item).attr("name");
		  var value = $(item).val();
		  params[name] = value;
	  });
	  var gift_goods_ids = [];
	  $("#propertyTables input[name='goods_id']").each(function(idx,item){
		  var goods_id = $(item).val();
		  if(goods_id){
			  gift_goods_ids.push(goods_id); 
		  }
	  });
	  params["activityAttr.gift_goods_ids"] = gift_goods_ids.join(",");
	  
	  //套餐分类
	  var package_class_arr = [];
	  $("input[name='package_class_chk']:checkbox").each(function() {
		  if ($(this).attr("checked")) {
			  package_class_arr.push($(this).val());
		  }
	  });
	  params["activity.package_class"] = package_class_arr.join(",");
	  
	  //靓号减免类型
	  var relief_no_class_arr = [];
	  $("input[name='relief_no_class_chk']:checkbox").each(function() {
		  if ($(this).attr("checked")) {
			  relief_no_class_arr.push($(this).val());
		  }
	  });
	  params["activity.relief_no_class"] = relief_no_class_arr.join(",");
	  
	  return params;
   }	
}

function checkPmtCode(){
	  var result = false;
	  var action = $("#editForm input[name='action']").val();
	  var pmt_code = $("#activity_pmt_code").val();
	  var params = {"activity.pmt_code":pmt_code};
	  if(action == 'add'){
			$.ajax({
				async:false,
				type : "POST",
				dataType : 'json',
				data :params,
				url : "activity!checkPmtCode.do?ajax=yes",
				success : function(reply) {
						result = reply.result;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					//alert("出错了!");
				}
			});
	  }
	  if(result){
		  return "该编码已经被占用！";
	  }else{
		  return true; 
	  }
	  
}
	

//选择商品
var addGoods= {
        init:function(){
            var me=this;
            $("#selGoodsBtn").live("click",function() {
		        me.addSelectGoods();
	        });
            $("#searchGoodsBtn").live("click",function() {
                me.searchBottonClks();
            });
        },
 	   //选择商品
 	   showGoods : function(parmas) {
 	  	 var url = "activity!goodsList.do?ajax=yes";
 	  	 var dialog = parmas.dialog;
 	  	 
 	  	 //先清空弹出框
 	  	 if($("#activity_goods_dialog")){
 	  		$("#activity_goods_dialog").html("");
 	  	 }
 	  	 if($("#activity_prop_dialog")){
 	  		$("#activity_prop_dialog").html("");
 	  	 }
 	  	 if($("#q_activity_goods_dialog")){
 	  		$("#q_activity_goods_dialog").html("");
 	  	 }
 	  	 if($("#activity_goods_sel_dialog")){
 	  		 $("#activity_goods_sel_dialog").html("");
 	  	 }
 	  	 
 	     $("#"+dialog).load(url,parmas,function(){});
 	     Eop.Dialog.open(dialog);
 	     
 	     
 	     
 	   },
       addSelectGoods:function(){
    	    var me=this;
        	var dialog = $("#listGoods input[name='dialog']").val();
        	var goodsid = $("#"+dialog).find("#listGoods input[name='goodsId']:checked");
        	if(dialog == 'activity_goods_dialog'){//活动商品(货品包)
        		var gids = [];
        		var goods_names = [];
        		//获取父页面已经选择的数据
        		//var selectedIds = $("#editForm").find("input[name='activity.relation_id']").val();
        		//var selectedNames = $("#editForm").find("input[name='activity.relation_name']").val();
        		//var selectedSkus = $("#editForm").find("input[name='activityAttr.pmt_goods_skus']").val();
        		//if(selectedIds){
        			//gids = selectedIds.split(",");
        		//}
        		//if(selectedNames){
        			//goods_names = selectedNames.split(",");
        		//}
        		
    			goodsid.each(function(idx,item){
 				   var gid = $(item).attr("gid");
 				   var goods_name = $(item).attr("goods_name");
 				   /*
 				   var isExist = activityUtil.checkIds(gids,gid);
 				   if(isExist){
 					   alert("'"+goods_name+"'已经选择！");
 				   }else{*/
 	 				   gids.push(gid);
 	 				   goods_names.push(goods_name);		   
 				   //}
 	            });
			   $("#editForm").find("input[name='activity.relation_id']").val(gids.join(","));
			   $("#editForm").find("input[name='activity.relation_name']").val(goods_names.join(",")); 
			   
        	}else if(dialog == 'q_activity_goods_dialog'){//查询条件活动商品
        		var gids = [];
        		var goods_names = [];
    			goodsid.each(function(idx,item){
 				   var gid = $(item).attr("gid");
 				   var goods_name = $(item).attr("goods_name");
 				   gids.push(gid);
 				   goods_names.push(goods_name);
 	            });
			   $("#queryActivityForm").find("input[name='activity.relation_id']").val(gids.join(","));
			   $("#queryActivityForm").find("input[name='activity.relation_name']").val(goods_names.join(",")); 
			   
        	}else if(dialog == 'activity_prop_dialog'){//赠品
        	   
   			   goodsid.each(function(idx,item){
				   var gid = $(item).attr("gid");
 				   var goods_name = $(item).attr("goods_name");
 				   var sku = $(item).attr("sku");
 				   var s_type = $(item).attr("s_type");
 				   var cid = $(item).attr("cid");
   				   me.writeGift(gid,goods_name,sku,s_type,cid);
	            });
			   
		   }else if(dialog == 'activity_goods_sel_dialog'){//活动商品
        	   
   			   goodsid.each(function(idx,item){
				   var gid = $(item).attr("gid");
 				   var goods_name = $(item).attr("goods_name");
 				   var sku = $(item).attr("sku");
 				   var s_type = $(item).attr("s_type");
   				   me.writeGoods(gid,goods_name,sku,s_type);
	            });
			   
		   }
           Eop.Dialog.close(dialog);
    },
    searchBottonClks : function() {
//    	zhengchuiliu
        var sku = $.trim($("#listGoods input[name='product.sku']").val());
    	var name =  $.trim($("#listGoods input[name='product.name']").val());
    	var type = $("#listGoods input[name='goods.type']").val();
    	var dialog = $("#listGoods input[name='dialog']").val();
    	var type_id = $("#listGoods input[name='type_id']").val();
    	var s_type = $("#listGoods input[name='s_type']").val();
		var params = {"dialog":dialog,"goods.type":type,"product.name":name,"type_id":type_id,"s_type":s_type,"product.sku":sku};
		addGoods.showGoods(params);
		
   },
   //画赠品类表
   writeGift:function(gid,goods_name,sku,s_type,cid){
	   var html = "<tr><td style='text-align: center;'><input type='hidden' name='cat_id' value='"+cid+"' />" +
	   		"<input type='hidden' name='goods_id' value='"+gid+"' />"+goods_name+"</td>" +
	   		"<td style='text-align: center;'>"+sku+"</td>"+
	   		"<td style='text-align: center;'><a href='javascript:void(0);' name='gooddelete'>删除</a></td></tr>";
	   $("#"+s_type+"_goods_body").append(html);
   },
   //画商品
   writeGoods:function(gid,goods_name,sku,s_type){
	   var html = '<tr>'+
				  '		<td style="text-align:center;">'+goods_name+
				  '			<input type="hidden" name="activityAttr.goods_ids" value="'+gid+'" />'+
				  '		</td>'+
				  '		<td style="text-align:center;">'+sku+'</td><td><a href="javascript:void(0);" name="gooddeletep">删除</a></td>'+
				  '	</tr>';
	   /*var html = "<p><input type='text' style='width:354px' value='"+goods_name+"' disabled='true'>" +
	   			"<input value='"+sku+"' disabled='true'>" +
	   			"<input type='hidden' name='activityAttr.goods_ids' value='"+gid+"' />" +
	   			"<a href='javascript:void(0);' name='gooddeletep'>删除</a></p>";
	   $("#"+s_type+"_goods_body").append(html);*/
	   $("#pmtGoods").append(html);
   }
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

var activityUtil = {
	//判断id是否在ids串中存在，ids用逗号隔开
	checkIds:function(ids,id){
		var isExist = false;
		for(var i=0;i<ids.length;i++){
			if(ids[i] == id){
				isExist = true;
				break;
			}
		}
		return isExist;
	}
}


