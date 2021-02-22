var SearchHouseDialog = {
	init : function(){
		var me = this;
		me.initClk();
		//模糊搜索还原
		if($("#attribution_hide").val()!=""){
			$("#select_attribution").find("option[value='"+$("#attribution_hide").val()+"']").attr("selected","selected");
		}
		if($("#status_hide").val()!=""){
			$("#select_status").find("option[value='"+$("#status_hide").val()+"']").attr("selected","selected");
		}
	},
	initClk : function(){
		var me = this;
		$("#submitBtn").bind("click",function(){
			me.dialogSearchHouse();
		});
		$("#dialogConfirmBtn").bind("click",function(){
			me.confirmBtnClk();
		});
	},
	confirmBtnClk : function(){
		if($("#gridform2").find("input[type='radio']:checked").length>0){
			var td = $("#gridform2").find("input[type='radio']:checked").parent();
			$("#prod_house_name").val($(td).find("input[name='house_name']").val());
			$("#prod_house_id").val($(td).find("input[name='id']").val());
			Eop.Dialog.close("selectHouseName_dialog");
		}else{
			alert("您还没有选择物理仓");
			return ;
		}
	},
	dialogSearchHouse : function(){
		var house_name = $.trim($("#house_name_input").val());
		var select_status = $.trim($("#select_status").val());
		var select_attribution = $.trim($("#select_attribution").val());
		var prod_comp_name_sel = $.trim($("#prod_comp_name_sel").val());
		var url ="warehouseAction!searchListDialog.do?ajax=yes&house_name="+encodeURI(encodeURI(house_name,true),true)+"&status="+select_status+"&attribution="+select_attribution+"&comp_name_sel="+encodeURI(encodeURI(prod_comp_name_sel,true),true);
        $("#selectHouseName_dialog").load(url,function(){
        	Eop.Dialog.open("selectHouseName_dialog");
//        	$("#submitBtn").bind("click",function(){
//				me.dialogSearchHouse();
//			});
//			$("#dialogConfirmBtn").bind("click",function(){
//				me.confirmBtnClk();
//			});
        });
	}
};
$(function(){
	SearchHouseDialog.init();
});