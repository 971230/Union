var cardType = "";

var CardList = {
	charge_tansfer:function(){ //流量卡调拨
		var self= this;
		//&cards_ids="+card_idstr+"
		var url =ctx+"/shop/admin/card!transfer.do?ajax=yes&orderid="+$("#orderid").val()+"&from_page="+$("#from_page").val();
		Cmp.ajaxSubmit('card_query_form','',url,{},self.jsonBack,'json');
	},
	jsonBack:function(responseText){ //json回调函数
		if(responseText.result==1){
			alert(responseText.message);
			Eop.Dialog.close("order_w_dialog");
			window.location.reload();
		}
		if(responseText.result==0){
			alert(responseText.message);
		}
	},
	charge_returned:function(){ //充值卡退货
		var len =$("[name='card_checkbox']:checked").length;
		if(len == 0) {
			alert("请选择退货卡");
			return false;
		}
		Eop.Dialog.open("apply_w_dialog");
	},
	init:function(){
		
		if($(".comBtnDiv").children().length == 0){
			$(".comBtnDiv").hide();
		}else{
			$(".comBtnDiv").show();
		}
		
		$("a[id='card_tansfer_a']").bind("click",function(){
			CardList.charge_tansfer();
		});
		$("a[id='card_returned_apply']").bind("click",function(){
			CardList.charge_returned();
		});
		
		//价格计算处理
		$("[name='card_checkbox']").bind("click",function(){
			var checked = $(this).attr("checked");
			var curr_val = new Number($("#all_card_money").text());
			var card_price = new Number($(this).attr("card_price"));
			if(checked)
				$("#all_card_money").text(curr_val+card_price);
			else
				$("#all_card_money").text(curr_val-card_price);
		});
		
		//充值卡导入
		$("a[id='rechargeCard_import_btn']").bind("click",function(){
			cardType = "CARD";
			CardList.importCard(cardType);
		});		
		
		
		//流量卡导入
		$("a[id='trafficCard_import_btn']").bind("click",function(){
			cardType = "RECHARGE_CARD";
			CardList.importCard(cardType);
		});		
		Eop.Dialog.init({id:"importDialog",modal:true,title:"卡导入",width:"750px"});
		
		
		$("#submitButton").bind("click",function(){
			var price = $("input[name='card.card_price']").val();
		 	var reg = /^\d+$/;
			if(price != ""){
				if(!reg.test(price)){
					alert("价格请输入数字！");
					$("input[name='card.card_price']").val("").focus();
					return false;
				}else{
					 document.forms["card_query_form"].submit(); 
				}
			}else{
				 document.forms["card_query_form"].submit(); 
			}
		});
	},
	importCard:function(cardType){
		Eop.Dialog.open("importDialog");
		var url =ctx + "/shop/admin/card!importCard.do?ajax=yes&type_code=" + cardType;
		$("#importDialog").load(url,function(){
			
		});
	},
	returnBatch:function(batchId){
		$("input[name='card.batch_id']").val(batchId);
	},
	afterImport:function(){
		Eop.Dialog.close();
		
		//批处理单号
		var batchId = $("input[name='card.batch_id']").val();
		var typeCode = $("input[name='card.type_code']").val();
//		var url = ctx+ "/shop/admin/card!listImport.do?ajax=yes&card.batch_id=" + batchId; 
//		Cmp.ajaxSubmit('card_query_form', 'cardList', url, {}, function(){
//			
//		},'html');
		//后台同步订单，并展示同步结果		
		window.location.href = ctx+ "/shop/admin/card!list.do?card.batch_id=" + batchId + "&card.type_code=" + typeCode;
	}
};
$(function() {
	if ($("#from_page").val() != 'order') {
		Eop.Dialog.init({id:"apply_w_dialog",modal:false,title:"商品退货申请",width:"650px"});
		var url = ctx+ "/shop/admin/goodsApply!showGoodsReturnedApplyDialog.do?ajax=yes";
		$("#apply_w_dialog").load(url,function(){});
	}
	CardList.init();
});