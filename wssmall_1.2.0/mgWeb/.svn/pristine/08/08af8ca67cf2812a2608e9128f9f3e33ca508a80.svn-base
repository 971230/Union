//活动列表
var ActivityList = {
	   init:function(){
		   var self =this;
		   Eop.Dialog.init({id:'q_activity_goods_dialog',modal:true,title:'活动商品',width:'850px'});
		   Eop.Dialog.init({id:'q_activity_orgs_dialog',modal:true,title:'活动商城',width:'450px'});
		   Eop.Dialog.init({id:"activitySelectShopDialog",modal:true,title:"选择组织",width:"1000px"});
		   //选择商品
		   $("#q_select_goods_btn").unbind("click").bind("click",function() {
			   var params = {"dialog":"q_activity_goods_dialog","goods.type":"goods"};
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
			$(".gridbody").find("a[name='disableActivity']").live("click",function(){
				
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
			$("#searchFormSubmit").bind("click",function(){
				$("#queryActivityForm").submit();
			});
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
		   if($(".gridbody").find("tbody").find("input[type='radio']:checked").length>0){
			   var obj = $(".gridbody").find("tbody").find("input[type='radio']:checked");
			   var orgids = obj.attr("org_ids");
			   var status = obj.attr("statustxt");
			   var enable = obj.attr("isenable");
			   var id = obj.val();
			   if(enable!="1"){
				   alert("不能发布已停用的活动！");
				   return ;
			   }
			   if(status!="0"){
				   alert("请选择未发布的活动再发布！");
				   return ;
			   }
			   $.Loading.show('正在响应您的请求，请稍侯...');
			   $.ajax({
				   url:"activity!doPublishActivity.do?ajax=yes&activity_id="+id+"&org_ids="+orgids,
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
						$("#queryActivityForm").submit();
						//window.location.href = ctx + "/shop/admin/activity!listActivityECS.do";
					}else{
						alert("操作失败!");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					//alert("出错了");
				}
			});
			
	   }
}

//新增或修改活动
var ActivityAddEdit = {
   init:function(){
	   var self =this;
	   Eop.Dialog.init({id:'activity_goods_dialog',modal:true,title:'活动商品',width:'850px'});
	   Eop.Dialog.init({id:'activity_orgs_dialog',modal:true,title:'活动商城',width:'450px'});
	   Eop.Dialog.init({id:'activity_prop_dialog',modal:true,title:'活动赠品',width:'850px'});
		$("a[name='gooddelete']").live("click",function(){
		 	var tr = $(this).closest("tr");
		 	tr.remove();
		});
	   var action = $("#editForm input[name='action']").val();
	   if(action == 'edit'){
		   $("#editForm input[name='activity.pmt_code']").attr("disabled",true);
		   $("#editForm select[name='activity.enable']").attr("disabled",true);
		   
		   //设置发布商城是否可以修改,发布中和已经发布的不可以修改
		   var publish_status = $("#editForm input[name='publish_status']").val();
		   if(publish_status != "0"){
			   $("#select_org_input").attr("disabled",true);
		   }
		   
	   }
	   //选择商品
	   $("#select_goods_btn").unbind("click").bind("click",function() {
		   var params = {"dialog":"activity_goods_dialog","goods.type":"goods"};
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
	   var formJq =$("#editForm");
		if(formJq.do_validate()) { 
			//$("#saveAndPublishBtn").attr("disabled",true);
			var params = self.getActivityData();
			$.ajax({
				async:true,
				type : "POST",
				dataType : 'json',
				data :params,
				url : "activity!saveOrUpdateActECS.do?ajax=yes",
				success : function(reply) {
					if(reply.result == 'true'){
						
						alert("保存成功!");
						window.location.href = ctx + "/shop/admin/activity!listActivityECS.do";;
					}else{
						alert("保存失败!");
						//$("#saveAndPublishBtn").attr("disabled",false);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					alert("出错了 ! XMLHttpRequest="+XMLHttpRequest);
					//$("#saveAndPublishBtn").attr("disabled",false);
				}
			});
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
 	  	 var url = "activity!goodsList.do?ajax=yes&goods.type_id=" + parmas["goods.type"];
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
 	  	 
 	     $("#"+dialog).load(url,parmas,function(){});
 	     Eop.Dialog.open(dialog);
 	     
 	     
 	     
 	   },
       addSelectGoods:function(){
    	    var me=this;
        	var dialog = $("#listGoods input[name='dialog']").val();
        	var goodsid = $("#"+dialog).find("#listGoods input[name='goodsId']:checked");
        	if(dialog == 'activity_goods_dialog'){//活动商品
        		var gids = [];
        		var goods_names = [];
        		//获取父页面已经选择的数据
        		var selectedIds = $("#editForm").find("input[name='activityAttr.pmt_goods_ids']").val();
        		var selectedNames = $("#editForm").find("input[name='activityAttr.pmt_goods_names']").val();
        		if(selectedIds){
        			gids = selectedIds.split(",");
        		}
        		if(selectedNames){
        			goods_names = selectedNames.split(",");
        		}
        		
    			goodsid.each(function(idx,item){
 				   var gid = $(item).attr("gid");
 				   var goods_name = $(item).attr("goods_name");
 				   var isExist = activityUtil.checkIds(gids,gid);
 				   if(isExist){
 					   alert("'"+goods_name+"'已经选择！");
 				   }else{
 	 				   gids.push(gid);
 	 				   goods_names.push(goods_name);		   
 				   }
 	            });
			   $("#editForm").find("input[name='activityAttr.pmt_goods_ids']").val(gids.join(","));
			   $("#editForm").find("input[name='activityAttr.pmt_goods_names']").val(goods_names.join(",")); 
			   
        	}else if(dialog == 'q_activity_goods_dialog'){//查询条件活动商品
        		var gids = [];
        		var goods_names = [];
    			goodsid.each(function(idx,item){
 				   var gid = $(item).attr("gid");
 				   var goods_name = $(item).attr("goods_name");
 				   gids.push(gid);
 				   goods_names.push(goods_name);
 	            });
			   $("#queryActivityForm").find("input[name='activityAttr.pmt_goods_ids']").val(gids.join(","));
			   $("#queryActivityForm").find("input[name='activityAttr.pmt_goods_names']").val(goods_names.join(",")); 
			   
        	}else if(dialog == 'activity_prop_dialog'){//赠品
        	   
   			   goodsid.each(function(idx,item){
				   var gid = $(item).attr("gid");
 				   var goods_name = $(item).attr("goods_name");
   				   me.writeGift(gid,goods_name);
	            });
			   
		   }
           Eop.Dialog.close(dialog);
    },
    searchBottonClks : function() {
    	var name =  $.trim($("#listGoods input[name='name']").val());
    	var type = $("#listGoods input[name='goods.type']").val();
    	var dialog = $("#listGoods input[name='dialog']").val();
		var params = {"dialog":dialog,"goods.type":type,"product.name":name};
		addGoods.showGoods(params);
		
   },
   //画赠品类表
   writeGift:function(gid,goods_name){
	   var html = "<tr><td><input type='hidden' name='goods_id' value='"+gid+"' />"+goods_name+"</td>" +
	   		"<td><a href='javascript:void(0);' name='gooddelete'>删除</a></td></tr>";
	   $("#propertyTables").append(html);
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


