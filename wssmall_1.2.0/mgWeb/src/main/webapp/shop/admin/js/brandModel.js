var Model = {
	init:function(){
		var me = this;
		me.initClk();
	},
	initClk : function(){
		var me = this;
		$("#model_search_btn").bind("click",function(){
			me.searchModel();
		});
		$("#model_confirm_btn").bind("click",function(){
//			me.selectModelOk();
		});
	},
	selectModelOk : function(){
		var ids="",checkedNames="";
		$("#brand_model_dialog").find("input[type='checkbox']:checked").each(function(i,data){
			ids+=$(data).val()+",";
			checkedNames+=$(data).attr("code")+",";
		});
		ids=ids.substring(0,ids.length-1);
		checkedNames=checkedNames.substring(0,checkedNames.length-1);
//		alert(ids+"  "+checkedNames);
	},
	searchModel : function(){
		var me = this;
		var url ="brandModel!brandModelList.do?ajax=yes";
		var brand_name = $.trim($("#brand_name").val());
		var machine_name = $.trim($("#machine_name").val());
		var model_name = $.trim($("#model_name").val());
		if(brand_name){
			url += "&brand_name="+encodeURI(encodeURI(brand_name,true),true);
		}
		if(machine_name){
			url += "&machine_name="+encodeURI(encodeURI(machine_name,true),true);
		}
		if(model_name){
			url += "&model_name="+encodeURI(encodeURI(model_name,true),true);
		}
		
		$("#brand_model_dialog").load(url,function(responseText) {
			Eop.Dialog.open("brand_model_dialog");
			$("#model_search_btn").bind("click",function(){
				me.searchModel();
			});
		});	
		
	}
	
};
$(function(){
	Model.init();
});