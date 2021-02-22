/**
 * Author: liqingyi
 * */
SelectShop = {
	init : function(){
		var me = this;
		me.initClk();
		//检测批量发布（号码是否为空）
//		if($("#numbers").val()==""){//批量发布
//			var confirmBtn = $('<input type="button" class="comBtn" value="确&nbsp;定" id="confirmBatchButton" name="button">');
//			$("#cateBottom").empty().append(confirmBtn);
//			$(confirmBtn).bind("click",function(){
//				var org_ids = me.confirmSelect();
//				me.doBatchPublish(org_ids);
//			});
//		}
	},
	isSingle : "1",
	initClk : function(){
		var me = this;
		me.loadRootTree();
		$("#confirmButton").bind("click", function() {
			$.Loading.hide();
			$.Loading.show("正在发布，请稍后...");
			var org_ids = me.confirmSelect();
			if(org_ids!=""){
				$("#batchOrgIds").val(org_ids);
				Eop.Dialog.close("goodsSelectShopDialog");
//				Eop.Dialog.close("activitySelectShopDialog");
				$("#searchGoodsListForm").submit();//商品批量发布
				$("#searchProductsListForm").submit();//货品批量发布
//				$("#queryActivityForm").submit();//活动批量发布
			}
			//me.doPublish(org_ids);
		});
	},
	confirmSelect : function(){
		var me = this;
		//拼
		var name="",org_ids="";
		//判断是否有二级
		if($("#box_86").html()==""){
			alert("请选择销售组织！");
			return "";
		}
		
		//判断是否有三级
		if($("#box_91").html()==""){
			//二级
			$("#box_86").find("input[type='checkbox']").each(function(i,data){
				if($(data).closest("li").hasClass("selected")){
					if($(data).val()!="10000"||$(data).val()!="10008"){
						org_ids+=$(data).val()+",";
					}
				}
			});
		}else{
			$("#box_91").find("input[type='checkbox']").each(function(i,data){
				if($(data).is(":checked")){
					org_ids+=$(data).val()+",";
				}
			});
		}
		
		org_ids = org_ids.substring(0,org_ids.length-1);
		if(org_ids==""){
			alert("请选择销售组织！");
			return "";
		}else{
			//发布
			return org_ids;
			//me.doPublish(org_ids);
		}
//		Eop.Dialog.close("numbers_pub_dialog");
	},
	doBatchPublish : function(org_ids){
		$("#orgids_input").val(org_ids);
		$("#batchForm").attr("action","numero!listBatchPublish.do");
		Eop.Dialog.close("numbers_pub_dialog");
		$("#batchForm").submit();
	},
	doPublish : function(org_ids){
		$.ajax({
			url : "numero!confirmPublish.do?ajax=yes",
			data :{"numbers":$("#numbers").val(),"ids":org_ids,"id":""},
			type : "POST",
			dataType : 'json',
			success : function(reply) {
//				if(reply.length==0){
					alert("操作成功！");//（已发布的组织不会重复发布）
		    		Eop.Dialog.close("numbers_pub_dialog");
//				}else{
//					var orgNames = "";
//					for(var i=0,j=reply.length;i<j;i++){
//						orgNames+=reply[i]["orgid"+(i+1)].org_name+"、";
//					}
//					orgNames=orgNames.substring(0, orgNames.length-1);
//					alert("操作成功,组织："+orgNames+"已到门限上限，不能再向该组织发布！");
//					Eop.Dialog.close("numbers_pub_dialog");
//				}
				window.location.reload();
			},
			error : function(){
				//alert("发布出错！");
			}
		});
	},
	loadRootTree : function(){
		var me = this;
		$.ajax({
			url:"numero!findFirstList.do?ajax=yes&id=-1",
			type:"post",
			dataType:"json",
			success:function(reply){
				if(reply!=null){
					me.dealData(reply,"box_0",true);
				}
			}
		});
	},
	/**
	 * 查询点击节点的子节点数据并渲染
	 * */
	loadChildCity : function(obj,region_id){
		var me = this;
		$.ajax({
			url:"numero!findFirstList.do?ajax=yes&id="+region_id,
			type:"post",
			dataType:"json",
			success:function(reply){
				if(reply.length!=0){
					$(obj).data(region_id,reply);//绑定数据
					me.dealData(reply,$(obj).closest("td").next().find("div").attr("id"),true);
				}
			},error:function(reply){
			}
		});
	},
	dealData : function(jsonStr,div_id,flag){
		var me = this;
		//$("#"+div_id).empty();
		if(flag){//已查询过的新数据 
			me.dealAllData(jsonStr,div_id);
		}else{
			//重复点击的节点不用再渲染数据,但得查看页面元素是否已删除
			var size=$("#"+div_id).find("input[pid='"+jsonStr[0].p_region_id+"']").length;
			if(size==0){
				me.dealAllData(jsonStr,div_id);
			}
		}
	},
	dealAllData : function(jsonStr,div_id) {
		var me = this;
		if(jsonStr!=null){
			var isLast="",chk="";
			if(div_id=="box_91"||div_id=="box_none"){
				isLast="";
			}else{
				isLast="none;";
			}
			if(me.isSingle=="0"){
				chk="";
			}else{
				chk="checked";
			}
			if(div_id!="box_none"){//四级不展示
				for(var i=0,j=jsonStr.length;i<j;i++){
					$("#"+div_id).append('<li><input class="mid" style="display:'+isLast+'" '+chk+' desc="'+jsonStr[i].org_name+'" type="checkbox" pid="'+jsonStr[i].parent_party_id+'" value="'+jsonStr[i].party_id+'"><span>'+jsonStr[i].org_name+'</span></li>');
				}
			}
		}
		//渲染完毕绑定事件
		me.bindLiClk(div_id);
	},
	bindLiClk : function(div_id){
		var me = this;
		$("#"+div_id).find("li").each(function(i,data){
			var obj = $(data);
			//一二级选中样式处理
			if(div_id=="box_0"||div_id=="box_86"){
				$(obj).bind("click",function(){
					$(this).siblings().removeClass("selected");
					$(this).addClass("selected");
					if(div_id=="box_0"){
						$("#box_86").empty();
						$("#box_91").empty();
						$("#box_none").empty();
					}else{
						$("#box_91").empty();
						$("#box_none").empty();
					}
					//$(obj).closest("td").next().find("div").empty();
					var val = $(obj).find("input[type='checkbox']").val();
					if($(obj).data(val)==undefined){
						//查询子级数据
						me.loadChildCity($(obj),val);
					}else{
						//渲染子级数据
						me.dealData($(obj).data(val),$("#"+div_id).parent().next().find("div").attr("id"),false);
					}
				});
			}else{
				//复选框事件点击事件处理
				$(obj).find("input").bind("click",function(){
					var val = $(obj).find("input[type='checkbox']").val();
					if(me.isSingle=="0"){//独享
						if(div_id=="box_91"){//县
							$("#box_none").empty();
						}
						$(obj).siblings().find("input").removeAttr("checked");
//						$(this).attr("checked",true);
					}else{//共享
						
					}
					
					if ($(this).attr("checked") == true) {
						if($(obj).data(val)==undefined){
							//查询子级数据
							me.loadChildCity($(obj),val);
						}else{
							//渲染子级数据
							me.dealData($(obj).data(val),$("#"+div_id).parent().next().find("div").attr("id"),false);
						}
					} else {
						if(div_id=="box_91"){//县
							$("#box_none").find("input[pid='"+val+"']").parent().remove();
						}
					}
				});
			}
//			$(obj).find("span").bind("click",function(){
				//清空下一级
//				$(obj).closest("td").next().find("div").empty();
//				var val = $(obj).find("input[type='checkbox']").val();
//				if($(obj).find("input[type='checkbox']").is(":checked")){
//					$(obj).find("input[type='checkbox']").removeAttr("checked");
					//取消之前选中的地区
//					if(div_id=="box_0"){//省
//						//遍历当前省下的市，先删除市下的县
//						$("#box_86").find("input[pid='"+val+"']").each(function(i,data){
//							$("#box_91").find("input[pid='"+$(data).val()+"']").parent().remove();
//						});
//						$("#box_86").find("input[pid='"+val+"']").parent().remove();
//					}else if(div_id=="box_86"){//市
//						$("#box_91").find("input[pid='"+val+"']").parent().remove();
//					}else if(div_id=="box_91"){//县
//						$("#box_none").find("input[pid='"+val+"']").parent().remove();
//					}else{//商圈
//						
//					}
//				}else{
//					$(obj).find("input[type='checkbox']").attr("checked","checked");
					//if(div_id!="box_91"){//目前区县下没有商圈数据，有商圈数据时取消此行判断
//						if($(obj).data(val)==undefined){
//							//查询子级数据
//							me.loadChildCity($(obj),val);
//						}else{
//							//渲染子级数据
//							me.dealData($(obj).data(val),$("#"+div_id).parent().next().find("div").attr("id"),false);
//						}
					//}
//				}
//			});
		});
	}
};
$(function(){
	SelectShop.init();
});