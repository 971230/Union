var FindCatListByParentId = {
	init : function() {
		var me = this;
		me.initClk();
		me.loadFirstNode();
	},
	initClk : function() {
		$("#confirmSelectBtn").bind("click",function(){
			var catid;
			var div1 = $("#box_0").find("li.selected").attr("catid");
			var div2 = $("#box_86").find("li.selected").attr("catid");
			var div3 = $("#box_91").find("li.selected").attr("catid");
			if(div3==undefined||div3=="undefined"){
				if(div2==undefined||div2=="undefined"){
					if(div1==undefined||div1=="undefined"){
					}else{
						catid = div1;
					}
				}else{
					catid = div2;
				}
			}else{
				catid = div3;
			}
			if(catid==undefined||catid=="undefined"){
				alert("您还没有选择商品分类");
			}else{
				$("#cat_id").val(catid);
				var cname=$("#cate-container").find("li[catid="+catid+"]").attr("catname");
				$("#goodsCatInputDialog").val(cname);
				$("#searchGoodsCatidInput").val(catid);
				$("#up").show();
				Eop.Dialog.close("goodsTypeQuickBtn_dialog");			
			}
		});
		
		//点击关闭按键
		$(".closeBtn").bind("click",function(){
			$('#up').show();
			Eop.Dialog.close("goodsTypeQuickBtn_dialog");
		});
	},
	/**
	 * 默认加载一级商品类型
	 * */
	loadFirstNode : function() {
		var me = this;
		$.ajax({
			url : "cat!findCatListByParentId.do?ajax=yes&parentid=0",
			type : "post",
			dataType : "json",
			success : function(result) {
				if(result.length>0){
					me.dataDeal(result,"box_0");
				}
			}
		});
	},
	/**
	 * 处理后台返回的json数据
	 * */
	dataDeal : function(datas,divId) {
		var me = this;
		var $obj = $("#"+divId);
		$obj.empty();
		for(var i=0;i<datas.length;i++){
			$obj.append('<li catid="'+datas[i].cat_id+'" parentid="'+datas[i].parent_id+'" catname="'+datas[i].name+'" class="">'+datas[i].name+'</li>');
		}
		$obj.each(function(i,data){
			$(data).find("li").bind("click",function() {
				$(this).siblings().removeClass("selected");
				$(this).addClass("selected");
				
				//点一级节点时清空二、级节点的脏数据
				if(divId=="box_0"){
					$("#box_86").empty();
					$("#box_91").empty();
				}
				//点二级节点时清空三级节点的脏数据
				if(divId=="box_86"){
					$("#box_91").empty();
				}
				//赋值"您当前选择的是……"
				var secdiv = $("#box_86").find("li.selected").html();
				var thirdiv = $("#box_91").find("li.selected").html();
				
				if(secdiv==null||secdiv=="null"){
					secdiv="";
				}else{
					secdiv = "> "+secdiv;
				}
				
				if(thirdiv==null||thirdiv=="null"){
					thirdiv="";
				}else{
					thirdiv = "> "+thirdiv;
				}
				$(".category-path").empty().append('<span>'
								+ $("#box_0").find("li.selected").html()
								+ '</span>&nbsp;&nbsp;<span>'
								+ secdiv
								+ '</span>&nbsp;&nbsp;<span>'
								+ thirdiv
								+ '</span>');
				//请求下一级节点列表 
				me.findNextNodes($(this).attr("catid"),$(this).parent().next().attr("id"));
			});
		});
	},
	/**
	 * 请求下一级节点列表 
	 * */
	findNextNodes : function(catid,divid) {
		var me = this;
		if(divid!="box_none"){//点击第三级菜单时不再请求 
			$.ajax({
				url : "cat!findCatListByParentId.do?ajax=yes&parentid="+catid,
				type : "post",
				dataType : "json",
				success : function(result) {
					if(result.length>0){
						me.dataDeal(result,divid);
					}
				}
			});
		}
	}
};
$(function() {
	FindCatListByParentId.init();
});