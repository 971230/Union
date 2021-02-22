var Publish = {
	init:function(){
		var me = this;
		me.initClk();
	},
	initClk:function(){
		var me = this;
		me.loadTree();
		$("#confirm_publish").bind("click",function(){
			me.publish();
		});
	},
	//发布
	publish : function() {
		var me = this;
		var ids = "";
		$("#menubox").find("div.checked").each(function(i,data){
			ids+=$(data).next().attr("id")+",";
		});
		ids = ids.substring(0,ids.length-1);
		if(ids==""){
			alert("请选择要发布的组织");
			return ;
		}
		var val=$("#resp_goodsid").attr("value");
		var type = $("#resp_goodsid").attr("ptype");
		$.ajax({
			url : "goodsOrg!confirmPublish.do?ajax=yes",
			data :{"goodsId":val,"type":type,"ids":ids},
			type : "POST",
			dataType : 'json',
			success : function(result) {
		    	if(result.result=="1"){
		    		alert(result.message);
		    		Eop.Dialog.close("goods_publish_dialog");
		    	}
			},
			error : function(){
				//alert("发布出错！");
			}
		});
	},
	loadTree : function() {
		var me = this;
		$.ajax({
			url : "goodsOrg!qryTree.do?ajax=yes",
			type : "POST",
			dataType : 'json',
			success : function(result) {
		    	if(result != null && result != '[]'){
		    		me.randerTree(result);
		    	}
			},
			error : function(){
			}
		});
	},
	randerTree : function(jsonStr){
		var me = this;
		var $container = $("#menubox");
		for(var i= 0,j=jsonStr.length;i<j;i++){
			me.createNodes($container,jsonStr[i]);
		}
		//等数据加载完毕
		$("#menubox").checkTree();
		//查询已发布的商场
		me.alreadyPublish();
		//默认展开所有节点
		me.openTreeNode();
	},
	openTreeNode : function(){
		$("ul.checktree").find("div.collapsed").trigger("click");
	},
	alreadyPublish : function (){
		var me = this;
		var goodsid=$("#resp_goodsid").attr("value");
		$.ajax({
			url : "goodsOrg!alreadyPublish.do?ajax=yes&goodsId="+goodsid,
			type : "POST",
			dataType : 'json',
			success : function(result) {
		    	if(result != null && result != '[]'){
		    		me.checkAlreadyPublish(result);
		    	}
			},
			error : function(){
			}
		});
	},
	checkAlreadyPublish : function(jsonStr){
		for(var i=0,j=jsonStr.length;i<j;i++){
			var obj = $("ul.checktree").find("input[type='checkbox'][id='"+jsonStr[i].org_id+"']");
			$(obj).prev().addClass("checked");
			var desc = obj.next().html();
			if(desc.indexOf("（")>0){
				desc = desc.replace(desc.substring(desc.indexOf("（"),desc.indexOf("）")+1),"");
			}
			obj.next().html(desc+"（"+jsonStr[i].status+"）");
		}
	},
	createNodes : function(con,data){
		var me = this;
		var ul = $("<ul class='checktree'></ul>");
		var li = $("<li></li>");
		var node = $('<input type="checkbox" name="" id="'+data.party_id+'" pid="'+data.parent_party_id+'"> <label>'+data.name+'</label>');
		$(li).append(node);
		$(ul).append(li);
		$(con).append(ul);
		var sub_node = data["sub_node"];
		if(sub_node!=undefined){
			for(var i=0,j=sub_node.length;i<j;i++){
				me.createNodes($(li),sub_node[i]);
			}
			
		}
	}

};
$(function(){
	Publish.init();
});