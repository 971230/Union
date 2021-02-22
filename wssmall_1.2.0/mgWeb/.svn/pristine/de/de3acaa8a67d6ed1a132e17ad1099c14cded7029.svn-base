/**
 * Author: liqingyi
 * */
SelectAddress = {
	init : function(){
		var me = this;
		me.initClk();
	},
	initClk : function(){
		var me = this;
		me.loadRootTree();
		$("#confirmButton").bind("click",function(){me.confirmSelect();});
	},
	confirmSelect : function(){
		//拼
		var name="",code="";
		$("#box_0").find("input[type='checkbox']").each(function(i,data){
			if($(data).is(":checked")){
				name+=$(data).attr("desc");//+",";
				code+=$(data).val()+",";
			}
		});
		$("#box_86").find("input[type='checkbox']").each(function(i,data){
			if($(data).is(":checked")){
				name+=$(data).attr("desc");//+",";
				code+=$(data).val()+",";
			}
		});
		$("#box_91").find("input[type='checkbox']").each(function(i,data){
			if($(data).is(":checked")){
				name+=$(data).attr("desc");//+",";
				code+=$(data).val()+",";
			}
		});
		$("#box_none").find("input[type='checkbox']").each(function(i,data){
			if($(data).is(":checked")){
				name+=$(data).attr("desc");//+",";
				code+=$(data).val()+",";
			}
		});
		name = name.substring(0,name.length-1);
		code = code.substring(0,code.length-1);
		$("#ae_scope_code").val(code);
		$("#ae_scope_name").val(name);
		Eop.Dialog.close("selectAddress_dialog");
	},
	loadRootTree : function(){
		var me = this;
		$.ajax({
			url:"warehouseAction!findProvinceList.do?ajax=yes",
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
			url:"warehouseAction!findCountryList.do?ajax=yes&house_id="+region_id,
			type:"post",
			dataType:"json",
			success:function(reply){
				if(reply!=null){
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
			for(var i=0,j=jsonStr.length;i<j;i++){
				$("#"+div_id).append('<li><input class="mid" desc="'+jsonStr[i].local_name+'" type="checkbox" pid="'+jsonStr[i].p_region_id+'" value="'+jsonStr[i].region_id+'"><span>'+jsonStr[i].local_name+'</span></li>');
			}
		}
		//渲染完毕绑定事件
		me.bindLiClk(div_id);
	},
	bindLiClk : function(div_id){
		var me = this;
		$("#"+div_id).find("li").each(function(i,data){
			var obj = $(data);
			//复选框事件点击事件处理
			$(obj).find("input").bind("click",function(){
				$(this).next().trigger("click");
				$(this).next().trigger("click");
			});
			$(obj).find("span").bind("click",function(){
				var val = $(obj).find("input[type='checkbox']").val();
				if($(obj).find("input[type='checkbox']").is(":checked")){
					$(obj).find("input[type='checkbox']").removeAttr("checked");
					//取消之前选中的地区
					if(div_id=="box_0"){//省
						//遍历当前省下的市，先删除市下的县
						$("#box_86").find("input[pid='"+val+"']").each(function(i,data){
							$("#box_91").find("input[pid='"+$(data).val()+"']").parent().remove();
						});
						$("#box_86").find("input[pid='"+val+"']").parent().remove();
					}else if(div_id=="box_86"){//市
						$("#box_91").find("input[pid='"+val+"']").parent().remove();
					}else if(div_id=="box_91"){//县
						$("#box_none").find("input[pid='"+val+"']").parent().remove();
					}else{//商圈
						
					}
				}else{
					$(obj).find("input[type='checkbox']").attr("checked","checked");
					//if(div_id!="box_91"){//目前区县下没有商圈数据，有商圈数据时取消此行判断
						if($(obj).data(val)==undefined){
							//查询子级数据
							me.loadChildCity($(obj),val);
						}else{
							//渲染子级数据
							me.dealData($(obj).data(val),$("#"+div_id).parent().next().find("div").attr("id"),false);
						}
					//}
				}
			});
		});
	}
};
$(function(){
	SelectAddress.init();
});