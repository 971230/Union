//var ctx ='/wssmall';
var Cat ={
    
	opBundle:undefined,
	init:function(){
		this.conid = "goods_select_dialog";
		var self = this;
		$("#cat_list .delete").click(function(){
			if( confirm("确定要删除此类别吗") ){
				$.Loading.show('正在响应您的请求，请稍侯...');
				self.opBundle=$(this);
				self.doDelete( self.opBundle.attr("catid") );
			}
		});
		
		function  appendGoods(goodsList){
			self.appendGoods(goodsList);
		}
			
		$("#sortBtn").click(function(){
			self.saveSort();
		});
		//是否展示在楼层
		$("#cat_list").find("table:eq(0)").find("tbody:eq(0)").find("tr").each(function(i,data){
			var $input = $(data).find("td:eq(3)").find("input[type='checkbox']");
			if($input.val()=="1"){
				$input.attr("checked",true);
				
			}
		});
		
		//tab页切换
		$(".tab-bar .tab>li").click(function() {
					var tabid = $(this).attr("tabid");
					$(".tab-bar .tab>li").removeClass("active");
					$(this).addClass("active");
					$(".input .tab-panel").hide();
					$("div[tabid=tab_" + tabid + "]").show();
		});
		
		//添加相关商品
		$("#complexOpenDialog").bind("click",function() {
			var url ="selector!goods.do?ajax=yes";// ctx + "/shop/admin/
			$("#goods_select_dialog").load(url,function(responseText) {
				Eop.Dialog.open("goods_select_dialog");
				//对话框“搜索”按键
				$("#searchBtn").click(function(){
					self.search(appendGoods);
				});
				//确定按钮
				$("#goods_select_dialog .submitBtn").click(function(){
					self.getGoodsList(appendGoods);
					Eop.Dialog.close("goods_select_dialog");
				});
			});
			
		});
		//清空
		$("#complexEmpty").click(function(){
			$("#complexContent").empty();
		});
		//对话框“搜索”按键
		$("#searchBtn").click(function(){
			self.search(appendGoods);
		});
		
		$("#imageClean").click(function(){
			var file = $("#catimage");
			file.after(file.clone().val("")); 
			file.remove();   
		});
		
		$("#publishCat").click(function(){
			self.doPublish();
		});
	},
	doPublish:function(){
		if (!confirm("确定要执行全量发布分类的操作吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: "cat!doPublish.do",
			 data: "ajax=yes",
			 dataType:"json",
			 success: function(result) {
				 if (result.result == '0') {
					 alert(result.message);
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	},
	search:function(onConfirm){
		var self = this;
		var options = {
				url :"selector!goods.do?ajax=yes",//ctx+"/shop/admin/
				type : "GET",
				dataType : 'html',
				success : function(result) {				
					$("#"+self.conid).empty().append(result);
					self.init(self.conid,onConfirm);
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
 
		$("#serchform").ajaxSubmit(options);
	},
	appendGoods:function(goodsList){
		var now_cat_id = $("#cat_id").val();
		for(i in goodsList){
			var goods = goodsList[i];
			var html = "<tr id='complex_" + goods.goods_id + "'><td>&nbsp;</td><td>" 
					+ goods.name+"<input type='hidden' name='catCom.goods_id' value='"+goods.goods_id +"' />"
					+ "<input type='hidden' name='catCom.goods_name' value='"+goods.name +"' /></td>"
					+ "<td>"
					+"<select  class='ipttxt' name='catCom.manual'>"
					+ "<option value='left'>单向相关</option>"
					+ "<option value='both' >双向相关</option>"
					+ "</select></td>"
					+"<td><a href='javascript:void(0);' nowCatId='"+now_cat_id+"' goodsId='"+goods.goods_id+"' class='delete'>&nbsp;&nbsp;&nbsp;&nbsp;</a>"
					+"<span style='display:none;'></span></td></tr>";
			$(html).appendTo($("#complexContent"));				
		}
		
		//绑定事件
				$(".delete").bind("click",function(){
					var catId=$(this).attr("nowCatId");
					var goodsId=$(this).attr("goodsId");
					$.ajax({
						url:"catComplexAction!delByCatId.do?ajax=yes&cat_id="+catId+"&goodsId="+goodsId,
						type:"post",
						dataType:"json",
						success:function(reply){
							var form = $("form");
							if(reply.result=='1'){
								$("#complex_"+goodsId).remove();
								//form.submit();
								//if( form.attr("validate")=="true" ){
								//	form[0].submit();
								//}
								//$.Loading.hide();
							}
						},
						error:function(){
							alert("删除相关商品出错");
						}
					});
				});
	},
	getGoodsList:function(callback){
		var options = {
				url :"selector!listGoods.do?ajax=yes",//ctx+"/shop/admin/
				type : "GET",
				dataType : 'json',
				success : function(result) {				
					if(callback){
						callback(result);
					}
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
 
		$("#serchform").ajaxSubmit(options);				
	},
	doDelete:function(catid){
		var self =this;
		$.ajax({
			 type: "POST",
			 url: "cat!delete.do",
			 data: "ajax=yes&cat_id="+catid,
			 dataType:"json",
			 success: function(result){
				 if(result.result==1){
					$.Loading.hide();
				 	alert(result.message);
			     }else{
			 		self.opBundle.parents("tr").remove();
				    $.Loading.hide();
				 }
			 },
			 error:function(){
				 $.Loading.hide();
				 alert("操作失败，请重试");
			 }
		}); 		
	},
	saveSort:function(){
		$.Loading.show('正在保存排序，请稍侯...');
		var that =this;
		var options = {
			url : "cat!saveSort.do?ajax=yes",
			type : "POST",
			dataType : 'json',
			success : function(result) {	
				$.Loading.hide();
				if(result.result==0){
					location.reload();
				}else{
					alert(result.message);
				}
			},
			error : function(e) {
				$.Loading.hide();
				alert("出现错误 ，请重试");
			}
		};

		$('form').ajaxSubmit(options);		
	},
	
	intChkNameEvent:function(){
		$(".submitlist .submitBtn").click(function(){
			if($(this).attr("type")=="reset"){
				return ;
			}else if($(this).attr("name")=="back"){
				history.go(-1);
				return;
			}
			$.Loading.show("正在检测类别名是否重复...");
			var name = $("#name").val();
			$("form").ajaxSubmit({
				url:'cat!checkname.do?ajax=yes',
				type:'POST',
				dataType:'json',
				success:function(result){
					var form = $("form");
					if(result.result==1){
						if(confirm("类别"+name+"已经存在，您确定要保存吗？")){
							$.Loading.hide();
							form.submit();
							if( form.attr("validate")=="true" ){
								form[0].submit();
							}	
						}
						$.Loading.hide();
					} else{
						$.Loading.hide();
						form.submit();
						if( form.attr("validate")=="true" ){
							form[0].submit();
						}	
					} 
				},error:function(){
					$.Loading.hide();
					alert("检测名称出错");
				}
			});
		});	
	}
};

function checkboxChange(obj){
	var cat_id = $(obj).prev().attr("catid");
	if($(obj).is(":checked")){
		$.ajax({
			 type: "POST",
			 url: "cat!editFloorListShow.do",
			 data: "ajax=yes&floor_list_show=1&cat_id="+cat_id,
			 dataType:"json",
			 success: function(result){
				 if(result.result==1){
					$.Loading.hide();
				 	alert(result.message);
			     }else{
			 		self.opBundle.parents("tr").remove();
				    $.Loading.hide();
				 }
			 },
			 error:function(){
				 $.Loading.hide();
				 alert("操作失败，请重试");
			 }
		}); 	
	}else{
		$.ajax({
			 type: "POST",
			 url: "cat!editFloorListShow.do",
			 data: "ajax=yes&floor_list_show=0&cat_id="+cat_id,
			 dataType:"json",
			 success: function(result){
				 alert(result.message);
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		}); 	
	}
}

function memberLvChange(obj){
	var cat_id = $(obj).attr("catid");
	var parent_id = $(obj).attr("parent_id");
	var lv_id=$(obj).val();
	var arrayCatId = new Array();
	
	if($(obj).is(":checked")){
		$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").attr("checked",true);
		
		arrayCatId.push(cat_id);
		$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").each(function(){
			cat_id=$(this).attr("catid");
			arrayCatId.push(cat_id);
			if($("[parent_id='"+cat_id+"'][value='"+lv_id+"']").length>0){
				$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").attr("checked",true);
				$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").each(function(){
					arrayCatId.push($(this).attr("catid"));
				});
				
			}
		});
		
		$.ajax({
			 type: "POST",
			 url: "cat!editMemberLv.do",
			 data: "ajax=yes&is_show=1&arrayCatId="+arrayCatId+"&lv_id="+lv_id,
			 dataType:"json",
			 success: function(result){
				 if(result.result==1){
					$.Loading.hide();
				 	alert(result.message);
			     }else{
			 		//self.opBundle.parents("tr").remove();
				    $.Loading.hide();
				 }
			 },
			 error:function(){
				 $.Loading.hide();
				 alert("操作失败，请重试");
			 }
		});
	}else{
		$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").attr("checked",false);
		
		arrayCatId.push(cat_id);
		$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").each(function(){
			cat_id=$(this).attr("catid");
			arrayCatId.push(cat_id);
			if($("[parent_id='"+cat_id+"'][value='"+lv_id+"']").length>0){
				$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").attr("checked",false);
				$("[parent_id='"+cat_id+"'][value='"+lv_id+"']").each(function(){
					arrayCatId.push($(this).attr("catid"));
				});
			}
		});
		
		$.ajax({
			 type: "POST",
			 url: "cat!editMemberLv.do",
			 data: "ajax=yes&is_show=0&arrayCatId="+arrayCatId+"&lv_id="+lv_id,
			 dataType:"json",
			 success: function(result){
				 alert(result.message);
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}
	
		
	/*
	if($(obj).is(":checked")){
		$.ajax({
			 type: "POST",
			 url: "cat!editMemberLv.do",
			 data: "ajax=yes&is_show=1&cat_id="+cat_id+"&lv_id="+lv_id,
			 dataType:"json",
			 success: function(result){
				 if(result.result==1){
					$.Loading.hide();
				 	alert(result.message);
			     }else{
			 		self.opBundle.parents("tr").remove();
				    $.Loading.hide();
				 }
			 },
			 error:function(){
				 $.Loading.hide();
				 alert("操作失败，请重试");
			 }
		}); 	
	}else{
		$.ajax({
			 type: "POST",
			 url: "cat!editMemberLv.do",
			 data: "ajax=yes&is_show=0&cat_id="+cat_id+"&lv_id="+lv_id,
			 dataType:"json",
			 success: function(result){
				 alert(result.message);
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		}); 	
	}
	*/
}
 
$(function(){
	Eop.Dialog.init({id:"goods_select_dialog",modal:false,title:"添加相关商品",width:"800px"});
	Cat.init();
});