var SpreadAdd={
	init:function(){
		$("#parent_name").change(function(){
			if($("select[name='grade_id']").length > 0){
				alert("选择父推荐人导致可推荐业务清空，请重新选择");
			}
			$(".delSelectGoods").trigger("click");
		});
		var s_type = $("select[name='spreadMemberGrade.service_type']").val();
		if(s_type != 'common'){
			$("#addServer").show();
		}
		
		$("select[name='spreadMemberGrade.service_type']").change(function(){
			//通用的情况下不允许选择业务
			if($(this).val() == "common"){
				$(".delSelectGoods").trigger("click");
				$("#addServer").hide();
			}else{
				$("#addServer").show();
			}
		});
		$("#addSpreader").bind("click",function(){
			Eop.Dialog.open("spread_dialog");
			var url = ctx + "/shop/admin/spread!selectSpreadMember.do?ajax=yes";
			$("#spread_dialog").load(url,function(){
				SpreadAdd.selectMemberInit();
			});
		});
		$("#addParent").bind("click",function(){
			Eop.Dialog.open("parent_dialog");
			var url = ctx + "/shop/admin/spread!selectParentMember.do?ajax=yes";
			$("#parent_dialog").load(url,function(){
				SpreadAdd.selectMemberInit();
			}); 
		});
		$("#addServer").bind("click",function(){
			Eop.Dialog.open("server_dialog");
			var parent_id = $("#parent_spread_id").val();
			var url = ctx + "/shop/admin/spread!selectServer.do?ajax=yes&parent_id="+parent_id;
			$("#server_dialog").load(url,function(){
				SpreadAdd.selectGoodsInit();
			}); 
		});
		$(".delSelectGoods").live("click",function(){
			$(this).closest("tr").remove();
		});
		$("#submitSpreadInfo").bind("click",function(){
			$("#theForm.validate").validate();
			var phone_num = $("input[name='spreadMember.mobile']").val().trim();
			var bank_account = $("input[name='spreadMember.bank_account']").val().trim();
			
			if (/^[0-9]{11}$/.test(phone_num) == false) {
				alert("电话号码只能是11位的数字组成");
				return;
			}
			
			if(bank_account != ""){
				if (/^[0-9]/.test(bank_account) == false) {
					alert("银行卡号必须由数字组成");
					return;
				}
			}
			
			
			if($("select[name='spreadMemberGrade.service_type']").val() != "common"){
				if($("select[name='grade_id']").length == 0){
					alert("请选择业务信息！");
					return;
				}
			}
			
			var url = ctx+ "/shop/admin/spread!saveSpreadInfo.do?ajax=yes";
			Cmp.ajaxSubmit('theForm', '', url, {}, SpreadAdd.jsonBack,'json');
		});
		$("input[name='spreadMember.mobile']").bind("blur",function(){
			var spread_name = $("#spreadName");
			if(spread_name.val().trim() == ""){
				spread_name.val($(this).val());
			}
		});
	},
	selectMemberInit:function(){
	},
	selectGoodsInit:function(){
		$("#searchGoods").unbind("click").bind("click",function(){
			var url = ctx + "/shop/admin/spread!selectServer.do?ajax=yes";
			Cmp.ajaxSubmit('goods_list_form','server_dialog',url,{},SpreadAdd.selectGoodsInit);
			return false;
		});
		$(".selectGoods").unbind("click").bind("click",function(){
			var url = ctx + "/shop/admin/spread!getGradeList.do?ajax=yes";
			Cmp.ajaxSubmit('grade_list_div','',url,{},SpreadAdd.printServer);
		});
	},
	printServer:function(responseText){
		responseText = eval("(" + responseText + ")");  
		var list = responseText.list;
		var radio = $("#server_dialog").find("input[type='radio']:checked");
		if(radio.length > 0){
			var goods_name = radio.attr("goodsname");
			var goods_id = radio.attr("goodsid");
			var flag = true;
			$(".hidden_goods_id").each(function(){
				var hidden_goods_id = $(this).val();
				if(goods_id == hidden_goods_id){
					flag = false;
				}
			});
			
			if(flag){
				var html = "";
				html += "<tr>";
				html += "<td style='text-align: center;'>"+goods_name+"</td>";
				html += "<td style='text-align: center;'>"+goods_id+"</td>";
				html += "<td style='text-align: center;'>";
				html += "<select class='ipttxt' style='width: 100px' name='grade_id'>";
				for ( var i = 0; i < list.length; i++) {
					var obj = list[i];
					html += "<option value='"+obj.pkey+"'>"+obj.pname+"</option>";
				}
				html += "</select>";
				html +=	"</td>";
				html += "<td style='text-align: center;'><a href='javascript:void(0);' class='delSelectGoods'>删除</a></td>";
				html += "<input type='hidden' name='goods_id' class='hidden_goods_id' value='"+goods_id+"'/>"
				html += "<input type='hidden' name='goods_names' value='"+goods_name+"'/>"
				html += "</tr>";
				$("#goods_tr").after(html);
			}
		}
		
		Eop.Dialog.close("server_dialog");
	},
	jsonBack:function(responseText){
		if (responseText.result == 1) {
			alert("操作成功");
			window.location.href="spread!listSpreadGrade.do";
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}	
	}
};
$(function(){
	Eop.Dialog.init({id:"spread_dialog",modal:false,title:"选择推荐人",width:"1000px"});
	Eop.Dialog.init({id:"parent_dialog",modal:false,title:"选择父级推荐人",width:"1000px"});
	Eop.Dialog.init({id:"server_dialog",modal:false,title:"添加业务信息",width:"1000px"});
	SpreadAdd.init();
});