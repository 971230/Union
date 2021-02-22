var SearchPCDialog = {
	init : function(){
		var me = this;
		me.initClk();
		//模糊搜索还原
		/**
		 * 
		if($("#attribution_hide").val()!=""){
			$("#select_attribution").find("option[value='"+$("#attribution_hide").val()+"']").attr("selected","selected");
		}
		if($("#status_hide").val()!=""){
			$("#select_status").find("option[value='"+$("#status_hide").val()+"']").attr("selected","selected");
		}
		 */
	},
	initClk : function(){
		var me = this;
		$("#submitBtn").bind("click",function(){
			me.dialogSearcPC();
		});
		$("#dialogConfirmBtn").bind("click",function(){
			me.confirmBtnClk();
		});
	},
	confirmBtnClk : function(){
		if($("#gridform2").find("input[type='radio']:checked").length>0){
			var td = $("#gridform2").find("input[type='radio']:checked").parent();
			var prod_comp_sel = $.trim($("#prod_comp_sel").val());
			if(prod_comp_sel=="selectProductCompany"){
				$("#prod_comp_name").val($(td).find("input[name='comp_name']").val());
				$("#prod_comp_code").val($(td).find("input[name='comp_code']").val());
			}else{
				$("#prod_comp_name_sel").val($(td).find("input[name='comp_name']").val());
				$("#prod_comp_code_sel").val($(td).find("input[name='comp_code']").val());
			}
			//$("#prod_comp_id").val($(td).find("input[name='id']").val());
			Eop.Dialog.close("selectProductCompany_dialog");
		}else{
			alert("您还没有选择货主");
			return ;
		}
	},
	dialogSearcPC : function(){
		var comp_name = $.trim($("#comp_name_input").val());
		var comp_code = $.trim($("#comp_code_input").val());
		var url ="warehouseAction!searchPCList.do?ajax=yes&company_name="+encodeURI(encodeURI(comp_name,true),true)+"&company_code="+comp_code;
        $("#selectProductCompany_dialog").load(url,function(){
        	Eop.Dialog.open("selectProductCompany_dialog");
        });
	}
};
$(function(){
	SearchPCDialog.init();
});