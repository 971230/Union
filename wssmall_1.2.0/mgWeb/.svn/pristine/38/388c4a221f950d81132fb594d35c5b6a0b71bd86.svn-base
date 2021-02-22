/**
 * Author: liqingyi
 * */
SelectShop = {
	init : function(){
		var me = this;
		me.initClk();
	},
	isSingle : $("#ae_attribution").val(),
	initClk : function(){
		var me = this;
		me.loadRootTree();
		$("#confirmButton").bind("click",function(){me.confirmSelect();});
	},
	confirmSelect : function(){
		//拼
		var name="",code="";
//		$("#box_0").find("input[type='checkbox']").each(function(i,data){
//			if($(data).is(":checked")){
//				name+=$(data).attr("desc");//+",";
//				code+=$(data).val()+",";
//			}
//		});
		$("#box_86").find("input[type='checkbox']").each(function(i,data){
			if($(data).closest("li").hasClass("selected")){
				name+=$(data).attr("desc")+",";
//				code+=$(data).val()+",";
			}
		});
		$("#ae_org_id_belong").val($("#box_86").find("li.selected").find("input").val());
		$("#box_91").find("input[type='checkbox']").each(function(i,data){
			if($(data).is(":checked")){
				name+=$(data).attr("desc")+",";
				code+=$(data).val()+",";
			}
		});
//		$("#box_none").find("input[type='checkbox']").each(function(i,data){
//			if($(data).is(":checked")){
//				name+=$(data).attr("desc");//+",";
//				code+=$(data).val()+",";
//			}
//		});
		name = name.substring(0,name.length-1);
		code = code.substring(0,code.length-1);
		$("#ae_scope_code").val(code);
		$("#ae_scope_name").val(name);
		Eop.Dialog.close("selectShop_dialog");
	},
	loadRootTree : function(){
		var me = this;
		$.ajax({
			url:"warehouseAction!findFirstList.do?ajax=yes&id=-1",
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
			url:"warehouseAction!findFirstList.do?ajax=yes&id="+region_id,
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