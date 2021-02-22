/**
 * 商品申请
 * @type 
 */
var sel_type; 
var GoodsAudit = {
	init : function() {
		var self = this;
		$("#goods_apply_form.validate").validate();
		$("#goods_apply_form [name='submitBtn']").click(function() {
			var goods_num =$("input[name='goodsApply.goods_num']").val();
			if(goods_num<= 0 ){ // 0 也不能申请
				$("input[name='goodsApply.goods_num']").focus().val("");
				alert("请校验申请数量！");
				return;
			}
			
			if(!$("input[name='goodsApply.goods_num']").checkInt()){
				alert("订购数量必须为整数!")
				return;
			}
				
			//开始号码
			if($("[attr='p_begin_nbr']:visible").length>0)
			{
				if(!$("[attr='p_begin_nbr']").val()){
					alert("开始号码不能为空！");
					return;
				}
			}
			
			//结束号码
			if($("[attr='p_end_nbr']:visible").length>0)
			{
				if(!$("[attr='p_end_nbr']").val()){
					alert("结束号码不能为空！");
					return;
				}
			}
			
			
			
			var url = ctx+ "/shop/admin/goodsApply!apply.do?ajax=yes";
			Cmp.ajaxSubmit('goods_apply_form', '', url, {}, self.jsonBack,'json');
		});
		
		//本地网改变时清空号码
		$("#out_lan_id").unbind("change").bind("change",function(){
			$("[attr='p_begin_nbr'],[attr='p_end_nbr'],[attr='goods_num']").val("");
		});
		
		
		//配置新地址
		$("#goods_apply_form [name='newAddress']").bind("click",function() {
			
			Eop.Dialog.open("newAddr_dialog");
			var goodsid=$(this).attr("goodsid");
			var begin_nbr =$("[attr='p_begin_nbr']").val();
			var end_nbr=$("[attr='p_end_nbr']").val();
			var goods_num =$("[attr='goods_num']").val();
			var apply_desc =$.trim($("[attr='apply_desc']").val());
			var lan_id = $("#out_lan_id").val();
			apply_desc=encodeURI(encodeURI(apply_desc,true),true);//解决乱码
			
			var url =ctx+"/shop/admin/goods!parAddrAdd.do?ajax=yes&goods_id=" + 
						goodsid+"&begin_nbr="+begin_nbr+"&end_nbr="+end_nbr+"&goods_num="
							+goods_num+"&apply_desc="+apply_desc+"&lan_id="+lan_id ;
			$("#newAddr_dialog").load(url,function(){
			});
		});
		//还原申请说明
		$("[attr='apply_desc']").val($('#hide_apply_desc').val());
		//查看云卡可用库存
		if(type_code == "CLOUD"){
			Eop.Dialog.init({id:"stock_dialog",modal:false,title:"云卡可用库存",width:"1000px"});
			$("[name='cloudStockView']").bind("click",function(){
				Eop.Dialog.open("stock_dialog");
				var p = "";
				var outLanId = $("#out_lan_id").val();
				if ($("#hidden_lan_id").val() != '' || outLanId != "") {
					if($("#hidden_lan_id").val() != ''){
						p = "&lan_id=" + $("#hidden_lan_id").val();
					}
					if(outLanId != ""){
						p = "&lan_id=" + outLanId;
					}
				}
				
				var url =ctx+"/shop/admin/cloud!list_avaible_forSearch.do?ajax=yes&goods_id=" + goods_id + p + "&";
				sel_type = $(this).attr("sel_type");
				
				//标志是从开始号码还是结束号码进入页面
				$("#hidden_sel_type").val(sel_type);
				
				$("#stock_dialog").load(url,function(){
					self.card_page_init();
				}); 
			});
		}else if(type_code == "CONTRACT"){
			Eop.Dialog.init({id:"accnbr_dialog",modal:false,title:"可申请号码",width:"1000px"});
			$("[name='cloudStockView']").bind("click",function(){
				Eop.Dialog.open("accnbr_dialog");
				var p = "";
				var outLanId = $("#out_lan_id").val();
				if ($("#hidden_lan_id").val() != '' || outLanId != "") {
					if($("#hidden_lan_id").val() != ''){
						p = "&lan_id=" + $("#hidden_lan_id").val();
					}
					if(outLanId != ""){
						p = "&lan_id=" + outLanId;
					}
				}
				var url =ctx+"/shop/admin/contract!list_avaible_forSearch.do?ajax=yes&goods_id=" + goods_id + p;
				sel_type = $(this).attr("sel_type");
				//标志是从开始号码还是结束号码进入页面
				$("#hidden_sel_type").val(sel_type);
				$("#accnbr_dialog").load(url,function(){
					self.card_page_init();
				}); 
			});
		}
		$("[attr='p_begin_nbr'],[attr='p_end_nbr']").bind("blur",function(){
			var begin_nbr =$("[attr='p_begin_nbr']").val();
			var end_nbr = $("[attr='p_end_nbr']").val();
			if (begin_nbr && begin_nbr.length == 11 &&	end_nbr && end_nbr.length == 11 ){
				//调用后台查询可用号码
					if (end_nbr < begin_nbr) {
						return;
					}
				
				 	var url ="";
				 	if(type_code == "CLOUD"){
				 		url = ctx+"/shop/admin/cloud!listCount.do?ajax=yes&begin_nbr="+begin_nbr+"&end_nbr="+end_nbr+"&goods_id="+goods_id;
				 	}else if(type_code == "CONTRACT"){
				 		url = ctx+"/shop/admin/contract!listCount.do?ajax=yes&begin_nbr="+begin_nbr+"&end_nbr="+end_nbr+"&goods_id="+goods_id;
				 	}
					Cmp.ajaxSubmit('acc_number_form','',url,{},function(result){
						$("#order_goods_count_span").html("说明：选择号码段区间可用库存数为"+result['ableCloud_count']+"（个）");
						$("[attr='goods_num']").val(result['ableCloud_count']);
					},'json');
			}
		});
	},
	card_page_init:function(){ //充值卡页面inti操作
		var self =this;
		var sel_type = $("#hidden_sel_type").val();
		if(type_code == "CLOUD"){
			$("#cloud_forSearch_query_form .comBtn").unbind("click").bind("click",function(){
				var url =ctx+"/shop/admin/cloud!list_avaible_forSearch.do?ajax=yes&goods_id="+goods_id;
				Cmp.ajaxSubmit('cloud_forSearch_query_form','stock_dialog',url,{},GoodsAudit.card_page_init); // 之前页面这里会有问题 直接把result当成了sel_type 
				return false;
			});
		
			$("#cloudInsureBtn").unbind("click").bind("click",function(){
				var selectNum = $("input[name='cloud_checkbox']:checked").attr("phone_num");
				if(sel_type =="begin"){
					
					var end_nbr =$("[attr='p_end_nbr']").val();
					if (end_nbr != "" && end_nbr.length == 11) {
						if (selectNum.substring(0,3) != end_nbr.substring(0,3)) {	
							alert("开始号码 " + selectNum + "结束号码 " +  end_nbr + " 与 不是同个号段，请检查！");
							return false;
						}
						if (selectNum > end_nbr) {	
							alert("开始号码 " +  selectNum + " 大于结束号码 " + end_nbr + " ，请检查！");
							return false;
						}
					}
					$("[attr='p_begin_nbr']").val(selectNum);
					$("[attr='p_begin_nbr']").trigger("blur");
				}else{
					
					var begin_nbr =$("[attr='p_begin_nbr']").val();
					if (selectNum.substring(0,3) != begin_nbr.substring(0,3)) {	
						alert("结束号码 " +  selectNum + " 与开始号码 " + begin_nbr + " 不是同个号段，请检查！");
						return false;
					}
					if (selectNum < begin_nbr) {	
						alert("结束号码 " +  selectNum + " 小于开始号码 " + begin_nbr + " ，请检查！");
						return false;
					}
					$("[attr='p_end_nbr']").val(selectNum);
					$("[attr='p_end_nbr']").trigger("blur");
				}
				Eop.Dialog.close("stock_dialog");
				return false;
			});
		}else if(type_code == "CONTRACT"){
			$("#accnbr_forSearch_query_form .comBtn").unbind("click").bind("click",function(){
				var url =ctx+"/shop/admin/contract!list_avaible_forSearch.do?ajax=yes";
				Cmp.ajaxSubmit('accnbr_forSearch_query_form','accnbr_dialog',url,{},GoodsAudit.card_page_init);
				return false;
			});
		    
			$("#accInsureBtn").unbind("click").bind("click",function(){
				
				var selectNum = $("input[name='accnbr_checkbox']:checked").attr("num_code");
				if(sel_type =="begin"){
					$("[attr='p_begin_nbr']").val(selectNum);
					var end_nbr =$("[attr='p_end_nbr']").val();
					if (end_nbr != "" && end_nbr.length == 11) {
						if (selectNum.substring(0,3) != end_nbr.substring(0,3)) {	
							alert("开始号码 " + selectNum + "结束号码 " +  end_nbr + " 与 不是同个号段，请检查！");
							return false;
						}
						if (selectNum > end_nbr) {	
							alert("开始号码 " +  selectNum + " 大于结束号码 " + end_nbr + " ，请检查！");
							return false;
						}
					}
					$("[attr='p_begin_nbr']").trigger("blur");
				}else{
					$("[attr='p_end_nbr']").val(selectNum);
					var begin_nbr =$("[attr='p_begin_nbr']").val();
					if (selectNum.substring(0,3) != begin_nbr.substring(0,3)) {	
						alert("结束号码 " +  selectNum + " 与开始号码 " + begin_nbr + " 不是同个号段，请检查！");
						return false;
					}
					if (selectNum < begin_nbr) {	
						alert("结束号码 " +  selectNum + " 小于开始号码 " + begin_nbr + " ，请检查！");
						return false;
					}
					$("[attr='p_end_nbr']").trigger("blur");
				}
				Eop.Dialog.close("accnbr_dialog");
				return false;
			});
		}
	},
	jsonBack : function(responseText) { 
		if (responseText.result == 1) {
			alert(responseText.message);
			//alert("请到订单管理查看申请单信息!");
			Eop.Dialog.close("apply_w_dialog");
			window.location.reload(); //刷新页面
		}
		if (responseText.result == 0) {
			alert(responseText.message);
		}
	}
	
}
$(function() {
	Eop.Dialog.init({id:"newAddr_dialog",modal:false,title:"配置新地址",width:"650px"});
	GoodsAudit.init();
});