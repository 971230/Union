
var BrandModel =$.extend({},Eop.Grid,{
	
	init:function() {
		
		var self = this;
		
		//删除
    	$("#delBtn").click(function(){
    		self.doDelete();
    	});
    	
		//全量发布
    	$("#publishBrandModel").click(function(){
    		self.doPublish();
    	});
    	
    	//全量发布
    	$("#bacthPublishBrandModel").click(function(){
    		self.doBatchPublish();
    	});
    	
    	
	},
	
	//删除
	doDelete:function() {
		var ids="";
		if(!this.checkIdSeled()){
			alert("请选择要删除的品牌型号！");
			return ;
		}else{
			$(".grid").find("tbody").find("tr").find("td:eq(0)").find("input[type='checkbox']:checked").each(function(i,data){
				ids+=$(data).val()+",";
			});
			ids=ids.substring(0,ids.length-1);
		}
		
		var self= this;
		$("form").ajaxSubmit({
			url:'brandModel!checkUsed.do?ajax=yes',
			type:'POST',
			dataType:'json',
			success:function(result){
				if(result.result==1){
					if(confirm("要删除的品牌型号已经关联商品，确认要删除吗？")){
						$.Loading.hide();
						$.Loading.show("正在删除品牌型号...");
						self.deletePost("brandModel!doDelete.do?ajax=yes&id="+ids);
					 
					}
					$.Loading.hide();
				} else{
					if(confirm("确认要将这些品牌型号删除吗？")){
						$.Loading.hide();
						$.Loading.show("正在删除品牌型号...");
						self.deletePost("brandModel!doDelete.do?ajax=yes&id="+ids);
					}
					$.Loading.hide();
				} 
			},error:function(){
				alert("检测关联性出错");
			}
		});		
	},
	
	//全量发布
	doPublish:function() {
		
    	if (!confirm("确定要执行全量发布品牌型号的操作吗？")) {
			return false;
		}
		
		$.ajax({
			 type: "POST",
			 url: "brandModel!doPublish.do",
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
	doBatchPublish:function(search_tag_name){
		var $checkedBrandModel = $('input[name="id"]:checked');
		if($checkedBrandModel.length<1){
			alert("请选择需要发布的品牌型号!");
			return;
		}
		var ids = [];
		var names = [];
		var id = "";
		$('input[name="id"]:checked').each(function(){
			id = $(this).val();
			ids.push(id);
			names.push($("#model_name_"+id).text());
		});
		var url =ctx + "/shop/admin/brandModel!goBatchPublish.do?ids="+ids.join(",")+"&names="+encodeURI(encodeURI(names.join(",")));
		window.location.href = url;
//		var url =ctx + "/shop/admin/goods!tagsSelectList.do?ajax=yes";
//		Eop.Dialog.open("orgListDialog");
//	    if(search_tag_name){
//	    	 url += "&search_tag_name="+encodeURI(encodeURI(search_tag_name,true),true);
//	    }
//		$("#orgListDialog").html("loading...");
//		$("#orgListDialog").load(url,function(){});
	}
	
});

var BrandModelInput={
		init:function(){
			var self = this;
			Eop.Dialog.init({id:"brand_dialog",modal:false,title:"选择品牌",width:"450px"});
			
			$("#select_brand_btn").click(function(){
				selectBrand.showBrandList();
			});
			
			$("#model_code").bind("blur",function(){
				checkModelName();
			});
			$("#submitBtn").click(function(){
				self.saveBrandModel();
			});
			$("#backBtn").click(function(){
				history.go(-1);
			});
			
			
		},
		
		saveBrandModel:function(){
			if($("#editForm").do_validate()){
				$("#editForm").ajaxSubmit({
					url:'brandModel!saveOrUpdateBrandMode.do?ajax=yes',
					type:'POST',
					dataType:'json',
					success:function(reply){
						if(reply.result == true){
							alert("保存成功");
							window.location.href = ctx + "/shop/admin/brandModel!brandModelListECS.do";
						}else{
							if(null!=reply.msg&&""!=reply.msg){
								alert("保存失败,"+reply.msg);
							}else{
								alert("保存失败");
							}
						}
					},
					error:function(){
						alert("出错了");
					}
				});				
			}else{
				//$("#brand_name").focus();
			}
		}
};

function checkModelName(){
	  var result = false;
	  var action = $("#editForm input[name='action']").val();
	  var model_name = $("#model_name").val();
	  var params = {"brandModel.model_name":model_name};
	  if(action == 'add'){
			$.ajax({
				async:false,
				type : "POST",
				dataType : 'json',
				data :params,
				url : "brandModel!checkModelCode.do?ajax=yes",
				success : function(reply) {
						result = reply.result;
				},
				error : function(XMLHttpRequest, textStatus, errorThrown){
					//alert("出错了!");
				}
			});
	  }
	  if(result){
		  return "该型号已经存在！";
	  }else{
		  return true; 
	  }
	  
}


//选择品牌
var selectBrand= {
    init:function(){
        var me=this;
        $("#selBrandBtn").live("click",function() {
	        me.addSelectBrand();
        });
        $("#searchBrandBtn").live("click",function() {
            me.searchBottonClks();
        });
        //单选框双击选择
    	$("#listBrand").find(".gridbody tbody tr").live("dblclick",function(){
        	var box = $(this).find("input[type='checkbox'],input[type='radio']");
        	if($(box).attr("type") == "radio"){
        		box.attr("checked",true);
        		me.addSelectBrand();
        	}
    	});
    },
    showBrandList : function(parmas) {
 	  	 var url = "brandModel!brandList.do?ajax=yes";
 	     $("#brand_dialog").load(url,parmas,function(){});
 	     Eop.Dialog.open("brand_dialog");
    },
    addSelectBrand:function(){
	    var me=this;
    	var brand = $("#listBrand input[name='brand']:checked");
    	var brand_name = $(brand).val();
    	var brand_code = $(brand).attr("brand_code");

	    $("#brand_name").val(brand_name);
	    $("#brand_code").val(brand_code); 

        Eop.Dialog.close("brand_dialog");
    },
    searchBottonClks : function() {
    	self =this;
    	var name =  $.trim($("#listBrand input[name='name']").val());
		var params = {"brand.name":name};
		self.showBrandList(params);
		
   }
}

$(function() {
	
	BrandModel.init();
});