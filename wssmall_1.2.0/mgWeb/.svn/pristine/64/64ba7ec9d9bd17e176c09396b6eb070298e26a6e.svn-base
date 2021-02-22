var ProductCompanyList ={//$.extend({},Eop.Grid,
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
		me.clickRowEvent();
		$("#addBtn").bind("click",function(){me.addProductCompany();});
		$("#modifyBtn").bind("click",function(){me.modifyProductCompany();});
		//$("#stopBtn").bind("click",function(){me.stopProductCompany();});
		$("#cancelBtn").bind("click",function(){me.cancelProductCompany();});
		$("#selectAddr_input").bind("click",function(){me.selectAddr();});
		$("#confirmBtn").bind("click",function(){me.checkNameAndCode();});
	},
	selectAddr : function(){
		var url = "warehouseAction!selectAddr.do?ajax=yes";
		$("#selectAddress_dialog").load(url,function(resp){
			Eop.Dialog.open("selectAddress_dialog");
		});
	},
	checkNameAndCode : function(){
		var me = this;
		if($("#isae_hidden").val()!=""){
			if($.trim($("#ae_company_name").val())==""){
				alert("货主名不能为空！");
				return ;
			}
			if($.trim($("#ae_company_code").val())==""){
				alert("货主编码不能为空！");
				return ;
			}
			
			if($("#isae_hidden").val()=="edit"){
				//校验货主名和编码重复性
				$.ajax({
					url:app_path+"/shop/admin/warehouseAction!pcCheckNameOrCode.do?ajax=yes",
					data:{house_name:$.trim($("#ae_company_name").val()),house_code:$.trim($("#ae_company_code").val()),house_id:$.trim($("#ae_company_id").val())},
					dataType:"json",
					type:"post",
					success:function(reply){
						if(reply.result>0){
							alert(reply.message);
							return ;
						}else{
							//货主名和编码均不冲突，可修改
							me.doAddOrEdit("edit");
						}
					}
				});
			}else if($("#isae_hidden").val()=="add"){
				//校验货主名和编码重复性
				$.ajax({
					url:app_path+"/shop/admin/warehouseAction!pcCheckNameOrCode.do?ajax=yes",
					data:{house_name:$.trim($("#ae_company_name").val()),house_code:$.trim($("#ae_company_code").val())},
					dataType:"json",
					type:"post",
					success:function(reply){
						if(reply.result>0){
							alert(reply.message);
							return ;
						}else{
							//货主名和编码均不存在，可添加
							me.doAddOrEdit("add");
						}
					}
				});
			}
		}
		//$("#isae_hidden").val("");
	},
	doAddOrEdit : function(flag){
		var me = this;
		if(flag=="add"){
			var url=app_path+"/shop/admin/warehouseAction!pcEdit.do?ajax=yes";
			Cmp.ajaxSubmit('ae_form', '', url, {}, me.jsonBack,'json');
		}else if(flag=="edit"){
			var company_id = $("#ae_company_id").val();
			if(company_id==""){
				alert("请选择一条记录再操作");
				return ;
			}
			var url=app_path+"/shop/admin/warehouseAction!pcEdit.do?ajax=yes";
			Cmp.ajaxSubmit('ae_form', '', url, {}, me.jsonBack,'json');
		
		}else{
			
		}
	},
	addProductCompany : function(){
		$("#isae_hidden").val("add");
		$("#current_operation").val("当前操作:新增");
		//$("#ae_company_id").remove();
		$("#ae_company_name").removeAttr("readonly");
		$("#ae_company_code").removeAttr("readonly");
		//清空
		//$("#ae_company_name").val("");
		//$("#ae_company_code").val("");
		//取消行选中
		//$(".grid").find("input[type='radio']").removeAttr("checked");
	},
	modifyProductCompany : function(){
		if($(".grid").find("input[type='radio']:checked").length>0){
			$("#isae_hidden").val("edit");
			$("#current_operation").val("当前操作:修改");
			//$("#ae_company_name").attr("readonly","readonly");
			$("#ae_company_name").removeAttr("readonly");
			$("#ae_company_code").attr("readonly","readonly");
		}else{
			alert("请选择一条记录再操作！");
		}
	},
	jsonBack : function(json){
			if (json.result == 1) {
				alert(json.message);
			}
			if (json.result == 0) {
				 alert(json.message);
				 window.location.reload();
			}
	},
	cancelProductCompany : function(){
		$("#ae_company_id").val("");
		$("#ae_company_name").val("");
		$("#ae_company_code").val("");
	},
	/**
	 * 行点击事件
	 * */
	clickRowEvent : function(){
		$(".grid").find("tr").each(function(i,data){
			$(data).bind("click",function(){
				$("#ae_company_code").attr("readonly","readonly");
				if(!$(this).find("input[type='radio']").is(":checked")){
					$("#ae_company_id").val($(this).find("td:eq(0)").find("input[name='id']").val());
					$("#ae_company_name").val($(this).find("td:eq(1)").html());
					$("#ae_company_code").val($(this).find("td:eq(2)").html());
				}else{
					$("#ae_company_id").val("");
					$("#ae_company_name").val("");
					$("#ae_company_code").val("");
				}
			});
		});
	}
};
$(function(){
	ProductCompanyList.init();
	//Eop.Dialog.init({id:"selectAddress_dialog",modal:false,title:"选择配送范围",width:"900px"});
});