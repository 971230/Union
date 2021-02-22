var WareHouseSearchList ={//$.extend({},Eop.Grid,
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
		$("#addBtn").bind("click",function(){me.addWarehouse();});
		$("#modifyBtn").bind("click",function(){me.modifyWarehouse();});
		$("#stopBtn").bind("click",function(){me.stopWarehouse();});
		$("#cancelBtn").bind("click",function(){me.cancelWarehouse();});
		$("#selectCompNameBtn").bind("click",function(){me.selectProductCompany();});
		$("#selectCompNameBtnSel").bind("click",function(){me.selectCompNameBtnSel();});
		$("#clearCompNameBtnSel").bind("click",function(){me.clearCompNameBtnSel();});
		$("#selectAddr_input").bind("click",function(){me.selectAddr();});
		$("#confirmBtn").bind("click",function(){me.checkNameAndCodeAndArea();});
	},
	selectProductCompany : function(){
		$("#prod_comp_sel").val("selectProductCompany");
		var url = "warehouseAction!searchPCList.do?ajax=yes";
		$("#selectProductCompany_dialog").load(url,function(resp){
			Eop.Dialog.open("selectProductCompany_dialog");
		});
	},
	selectCompNameBtnSel : function(){
		$("#prod_comp_sel").val("selectCompNameBtnSel");
		var url = "warehouseAction!searchPCList.do?ajax=yes";
		$("#selectProductCompany_dialog").load(url,function(resp){
			Eop.Dialog.open("selectProductCompany_dialog");
		});
	},
	clearCompNameBtnSel : function(){
		$("#prod_comp_code_sel").val("");
		$("#prod_comp_name_sel").val("");
	},
	selectAddr : function(){
		var url = "warehouseAction!selectAddr.do?ajax=yes";
		$("#selectAddress_dialog").load(url,function(resp){
			Eop.Dialog.open("selectAddress_dialog");
		});
	},
	checkNameAndCodeAndArea : function(){
		var me = this;
		if($("#isae_hidden").val()!=""){
			if($.trim($("#ae_house_name").val())==""){
				alert("仓库名不能为空！");
				return ;
			}
			if($.trim($("#ae_house_code").val())==""){
				alert("仓库编码不能为空！");
				return ;
			}
			if($.trim($("#prod_comp_name").val())==""){
				alert("货主不能为空！");
				return ;
			}
			if($.trim($("#ae_scope_name").val())==""){
				alert("配送范围不能为空！");
				return ;
			}
			
			if($("#isae_hidden").val()=="edit"){
				//校验仓库名和编码重复性
				$.ajax({
					url:app_path+"/shop/admin/warehouseAction!checkNameOrCode.do?ajax=yes",
					data:{house_name:$.trim($("#ae_house_name").val()),house_code:$.trim($("#ae_house_code").val()),company_code:$.trim($("#prod_comp_code").val()),company_name:$.trim($("#prod_comp_name").val()),house_id:$.trim($("#ae_house_id").val())},
					dataType:"json",
					type:"post",
					success:function(reply){
						if(reply.result>0){
							alert(reply.message);
							return ;
						}else{
							//仓库名和编码均不存在，可添加
							me.doAddOrEdit("edit");
						}
					}
				});
			}else if($("#isae_hidden").val()=="add"){
				//校验仓库名和编码重复性
				$.ajax({
					url:app_path+"/shop/admin/warehouseAction!checkNameOrCode.do?ajax=yes",
					data:{house_name:$.trim($("#ae_house_name").val()),house_code:$.trim($("#ae_house_code").val()),company_code:$.trim($("#prod_comp_code").val()),company_name:$.trim($("#prod_comp_name").val())},
					dataType:"json",
					type:"post",
					success:function(reply){
						if(reply.result>0){
							alert(reply.message);
							return ;
						}else{
							//仓库名和编码均不存在，可添加
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
			var url=app_path+"/shop/admin/warehouseAction!edit.do?ajax=yes";
			Cmp.ajaxSubmit('ae_form', '', url, {}, me.jsonBack,'json');
		}else if(flag=="edit"){
			var house_id = $("#ae_house_id").val();
			if(house_id==""){
				alert("请选择一条记录再操作");
				return ;
			}
			me.checkStoreSize(house_id,"warehouseAction!edit.do?ajax=yes");
		}else{
			
		}
	},
	addWarehouse : function(){
		$("#isae_hidden").val("add");
		$("#ae_house_id").remove();
		$("#ae_house_name").removeAttr("readonly");
		$("#ae_house_code").removeAttr("readonly");
		$("#ae_remarks").removeAttr("readonly");
		//清空
		$("#ae_house_name").val("");
		$("#ae_house_code").val("");
		$("#ae_org_id_belong").val("");
		$("#ae_scope_code").val("");
		$("#ae_scope_name").val("");
		$("#ae_remarks").val("");
		//取消行选中
		$(".grid").find("input[type='radio']").removeAttr("checked");
	},
	modifyWarehouse : function(){
		if($(".grid").find("input[type='radio']:checked").length>0){
			$("#isae_hidden").val("edit");
			$("#ae_house_name").attr("readonly","readonly");
			$("#ae_house_code").attr("readonly","readonly");
			$("#ae_remarks").removeAttr("readonly");
		}else{
			alert("请选择一条记录再操作！");
		}
	},
	stopWarehouse : function(){
		var me = this;
		$("#isae_hidden").val("edit");
		var house_id = $("#ae_house_id").val();
		if(house_id==""){
			alert("请选择一条记录再操作");
			return ;
		}else{
			 if(confirm("您确定要停用此物理仓吗？")){
				//变更停用状态
				$("#temp_status").attr("name",$("#ae_status").attr("name"));
				$("#ae_status").attr("name","");
				me.checkStoreSize(house_id,"warehouseAction!edit.do?ajax=yes");
			 }
		}
		
	},
	/**
	 * 检测是否有库存
	 * */
	checkStoreSize : function(house_id,url){
		var me = this;
		$.ajax({
			async:"false",
			url:"warehouseAction!checkStoreSize.do?ajax=yes&house_id="+house_id,
			type:"post",
			dataType:"json",
			success:function(json){
				if(json.result==1){
					alert(json.message);
				}
				if(json.result==0){
					//alert(json.message);
					Cmp.ajaxSubmit('ae_form', '', url, {}, me.jsonBack,'json');
				}
			},error:function(){
				alert("检测库存出错");
			}
		});
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
	cancelWarehouse : function(){
		$("#ae_house_id").val("");
		$("#ae_house_name").val("");
		$("#ae_scope_name").val("");
		$("#ae_scope_code").val("");
		$("#ae_house_code").val("");
		$("#ae_remarks").val("");
	},
	/**
	 * 行点击事件
	 * */
	clickRowEvent : function(){
		$(".grid").find("tr").each(function(i,data){
			$(data).bind("click",function(){
				if(!$(this).find("input[type='radio']").is(":checked")){
					$("#ae_house_id").val($(this).find("td:eq(0)").find("input[name='id']").val());
					$("#ae_house_name").val($(this).find("td:eq(1)").html());
					$("#prod_comp_name").val($(this).find("td:eq(3)").html());
					$("#prod_comp_code").val($(this).find("td:eq(0)").find("input[name='comp_code']").val());
					$("#ae_attribution").find("option[value='"+$(this).find("td:eq(5)").find("input[name='attribution']").val()+"']").attr("selected","selected");
					$("#ae_status").find("option[value='"+$(this).find("td:eq(3)").find("input[name='status']").val()+"']").attr("selected","selected");
					$("#ae_scope_name").val($(this).find("td:eq(6)").html());
					$("#ae_scope_code").val($(this).find("td:eq(0)").find("input[name='scope_code']").val());
					$("#ae_house_code").val($(this).find("td:eq(2)").html());
					$("#ae_remarks").val($(this).find("td:eq(7)").html());
					//$("#modifyBtn").attr("class","comBtn");
				}else{
					$("#ae_house_id").val("");
					$("#ae_house_name").val("");
					$("#prod_comp_name").val("");
					$("#prod_comp_code").val("");
					$("#ae_scope_name").val("");
					$("#ae_scope_code").val("");
					$("#ae_house_code").val("");
					$("#ae_remarks").val("");
					//$("#modifyBtn").attr("class","comBtnDisable");
				}
			});
		});
	}
};
$(function(){
	WareHouseSearchList.init();
	Eop.Dialog.init({id:"selectAddress_dialog",modal:false,title:"选择配送范围",width:"900px"});
	Eop.Dialog.init({id:"selectProductCompany_dialog",modal:false,title:"选择货主",width:"900px"});
});