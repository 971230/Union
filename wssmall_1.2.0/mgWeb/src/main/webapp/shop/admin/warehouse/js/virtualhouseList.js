var VirtualHouseList ={//$.extend({},Eop.Grid,
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
		$("#selectAddr_input").bind("click",function(){me.selectAddr();});
		$("#confirmBtn").bind("click",function(){me.checkNameAndCodeAndArea();});
	},
	checkNameAndCodeAndArea : function(){
		var me = this;
		if($("#isae_hidden").val()!=""){
			if($.trim($("#ae_house_name").val())==""){
				alert("虚拟仓名称不能为空！");
				return ;
			}
			if($.trim($("#ae_house_code").val())==""){
				alert("虚拟仓编码不能为空！");
				return ;
			}
			if($.trim($("#ae_scope_name").val())==""){
				alert("对应商城不能为空！");
				return ;
			}
			if($("#isae_hidden").val()=="edit"){
				$("#operation_input").val("edit");
				me.doAddOrEdit("edit");
			}else if($("#isae_hidden").val()=="add"){
				$("#operation_input").val("add");
				//校验仓库名和编码重复性
				$.ajax({
					url:app_path+"/shop/admin/warehouseAction!vcheckNameOrCode.do?ajax=yes",
					data:{house_name:$.trim($("#ae_house_name").val()),house_code:$.trim($("#ae_house_code").val())},
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
		$("#isae_hidden").val("");
		}
	},
	doAddOrEdit : function(flag){
		var me = this;
		if(flag=="add"){
			var url=app_path+"/shop/admin/warehouseAction!virtualHouseEdit.do?ajax=yes";
			Cmp.ajaxSubmit('ae_form', '', url, {}, me.jsonBack,'json');
		}else if(flag=="edit"){
			var house_id = $("#ae_house_id").val();
			if(house_id==""){
				alert("请选择一条记录再操作");
				return ;
			}
			me.checkStoreSize(house_id,"warehouseAction!virtualHouseEdit.do?ajax=yes&flag=edit");
		}else{
			
		}
	},
	selectAddr : function(){
		var url = "warehouseAction!selectShop.do?ajax=yes";
		$("#selectShop_dialog").load(url,function(resp){
			Eop.Dialog.open("selectShop_dialog");
			if($("#ae_attribution").val()=="0"){
				$("#dialog_isSingle").val("0");
			}else{
				$("#dialog_isSingle").val("1");
			}
		});
	},
	addWarehouse : function(){
		var me = this;
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
			$("#operation_input").val("edit");
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
		$("#operation_input").val("edit");
		var house_id = $("#ae_house_id").val();
		if(house_id==""){
			alert("请选择一条记录再操作");
			return ;
		}else{
			 if(confirm("您确定要停用此虚拟仓吗？")){
				//变更停用状态
				$("#temp_status").attr("name",$("#ae_status").attr("name"));
				$("#ae_status").attr("name","");
				me.checkStoreSize(house_id,"warehouseAction!virtualHouseEdit.do?ajax=yes&flag=edit");
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
			url:"warehouseAction!checkVistualStoreSize.do?ajax=yes&house_id="+house_id,
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
				//alert("检测库存出错");
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
					$("#ae_attribution").find("option[value='"+$(this).find("td:eq(4)").find("input[name='attribution']").val()+"']").attr("selected","selected");
					$("#ae_status").find("option[value='"+$(this).find("td:eq(3)").find("input[name='status']").val()+"']").attr("selected","selected");
					$("#ae_scope_name").val($(this).find("td:eq(2)").html());
					$("#ae_scope_code").val($(this).find("td:eq(0)").find("input[name='org_id_str']").val());
					$("#ae_house_code").val($(this).find("td:eq(0)").find("input[name='house_code']").val());
					$("#ae_remarks").val($(this).find("td:eq(5)").html());
				}else{
					$("#ae_house_id").val("");
					$("#ae_house_name").val("");
					$("#ae_scope_name").val("");
					$("#ae_scope_code").val("");
					$("#ae_house_code").val("");
					$("#ae_remarks").val("");
				}
			});
		});
	}
};
$(function(){
	VirtualHouseList.init();
	Eop.Dialog.init({id:"selectShop_dialog",modal:false,title:"选择商城",width:"900px"});
});