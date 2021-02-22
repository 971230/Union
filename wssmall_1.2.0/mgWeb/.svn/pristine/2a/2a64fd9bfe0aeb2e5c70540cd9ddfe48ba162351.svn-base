var PublishPromotion = {
	init:function(){
		var me = this;
		me.loadTree();
		me.initClk();
	},
	initClk:function(){
		var me = this;
		$("#confirm_publish").bind("click",function(){
			me.publish();
		});
	},
	//
	publish : function() {
		var me = this;
		var ids = "";
		var names = "";
		var k = 0;
		$("#menubox").find("div.checked").each(function(i,data){
			//中国联通，新商城本身不记录
			var id = $(data).next().attr("id");
			var name = $(data).next().attr("name");
			if(id != "10008" && id != "10000"){
				if(k++>0){
					ids += ",";
					names += ",";
				}
				ids += id;
				names += name;
			}
		});
		var dialog = $("#publish_div input[name='dialog']").val();
		if(dialog == "activity_orgs_dialog"){
			$("#act_org_ids").val(ids);
			$("#act_org_names").val(names);
			$("#isSaveOrg").val("1");
			
		}else if(dialog == "q_activity_orgs_dialog"){
			$("#q_act_org_ids").val(ids);
			$("#q_act_org_names").val(names);
		}
		Eop.Dialog.close(dialog);
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
		//默认展开所有节点
		me.openTreeNode();
	},
	openTreeNode : function(){
		$("ul.checktree").find("div.collapsed").trigger("click");
	},
	createNodes : function(con,data){
		
		//如果是WMS的話,活動添加的時候不需要顯示WMS的選項
		if("20001"==data.party_id)
			return;
		
		var me = this;
		var ul = $("<ul class='checktree'></ul>");
		var li = $("<li></li>");
		var node = $('<input type="checkbox" name="'+data.name+'" id="'+data.party_id+'" pid="'+data.parent_party_id+'"> <label>'+data.name+'</label>');
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
	PublishPromotion.init();
});