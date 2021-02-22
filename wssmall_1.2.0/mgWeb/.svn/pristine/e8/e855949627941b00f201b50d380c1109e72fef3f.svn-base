var Publish = {
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
	//发布
	publish : function() {
		var me = this;
		var ids = "";//belong_ids="";
		var names = "";
		var k = 0;
		$("#menubox").find("div.checked").each(function(i, data) {
			//ids += $(data).next().attr("id") + ":"+$(data).next().attr("level") +",";
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
		//checkbox half_checked
		/*$("#menubox").find("ul.second").find("div.checkbox").each(function(i,data){
			//.checked","div.half_checked
			if($(data).hasClass("checked")||$(data).hasClass("half_checked")){
				if(!$(data).closest("ul").hasClass("third")){
					belong_ids+=$(data).next().attr("id")+",";
				}
			}
		});
		$("#menubox").find("ul.third").find("div.checked").each(function(i,data){
			ids+=$(data).next().attr("id")+",";
		});*/
		
		if(ids==""){
			alert("请选择要发布的组织");
			return ;
		}
//		else{
//			ids = ids.substring(0,ids.length-1);
//			belong_ids = belong_ids.substring(0,belong_ids.length-1);
//		}
		$.ajax({
			url : "numero!confirmPublish.do?ajax=yes",
			data :{"numbers":$("#resp_numberid").val(),"ids":ids,"id":""},
			type : "POST",
			dataType : 'json',
			success : function(reply) {
				if(reply.length==0){
					alert("操作成功（已发布的组织不会重复发布）！");
		    		Eop.Dialog.close("numbers_pub_dialog");
				}else{
					var orgNames = "";
					for(var i=0,j=reply.length;i<j;i++){
						orgNames+=reply[i]["orgid"+(i+1)].org_name+"、";
					}
					orgNames=orgNames.substring(0, orgNames.length-1);
					alert("操作成功,组织："+orgNames+"已到门限上限，不能再向该组织发布！");
					Eop.Dialog.close("numbers_pub_dialog");
				}
				window.location.reload();
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
		//默认展开所有节点
		me.openTreeNode();
		//销售组织加标识
		$("#menubox").children("ul").addClass("first");
		$("#menubox").children().children().children("ul").addClass("second");
		$("#menubox").children().children().children("ul").children().children("ul").addClass("third");
	},
	openTreeNode : function(){
		$("ul.checktree").find("div.collapsed").trigger("click");
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