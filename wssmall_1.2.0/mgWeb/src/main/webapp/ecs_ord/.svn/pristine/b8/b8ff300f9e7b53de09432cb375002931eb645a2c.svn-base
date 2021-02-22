/**
 * 开户页面JS
 */



function initInfo(order_id){
	var ats;
	var opt;
	
	var field_names = "phone_num,busi_money,ICCID";
	
	var param = {
			"order_id":order_id,
			"field_names":field_names
	};
	
	$.ajax({
     	url:ctx+"/shop/admin/ordAuto!getAttrValuesBatch.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				ats = reply["attrs"];
     				opt = reply["options"];
     			}else{
     				var msg = reply["msg"];
     				alert("查询订单属性失败："+msg);
     			}
     		}else{
     			alert("查询订单属性失败");
     		}
     	},
     	error:function(msg){
     		alert("查询订单属性失败："+msg);
     	}
	});
	
	$("#phone_num").html(ats.phone_num);
	$("#busi_money").val(ats.busi_money);
	$("#ICCID").empty().html(ats.iccid);
	
	if(window.parent.canvas){
		var node = window.parent.canvas.nodes[node_index];
		$("#remark").html(node.remark);
	}
}

function refreshReadCard(){
	hideBtn();
	RwCard.DisConnetReader();//先断开链接
	RwCard.getReaderName();
	showBtn();
}

function openAcct(){
	
	var ats;
	var opt;
	
	var field_names = "busi_money,ICCID";
	
	var param = {
			"order_id":order_id,
			"field_names":field_names
	};
	
	$.ajax({
     	url:ctx+"/shop/admin/ordAuto!getAttrValuesBatch.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				ats = reply["attrs"];
     				opt = reply["options"];
     			}else{
     				var msg = reply["msg"];
     				alert("查询订单属性失败："+msg);
     			}
     		}else{
     			alert("查询订单属性失败");
     		}
     	},
     	error:function(msg){
     		alert("查询订单属性失败："+msg);
     	}
	});
	
	var busi_money = ats.busi_money;
	var ICCID = ats.iccid;
	
	if(typeof(busi_money)=="undefined" || ""==busi_money){
		alert("请先输入BSS/ESS缴费款并保存");
		return false;
	}
	
	if(typeof(ICCID)=="undefined" || ""==ICCID){
		alert("ICCID为空");
		return false;
	}
	
	if(window.parent.canvas){
		hideBtn();
		window.parent.canvas.runNodeManual(instance_id,"","");
		showBtn();
	}else{
		alert("流程画板JS对象为空");
		return false;
	}
}

function updateBusiMoney(){
	
	var busi_money = window.parent.WorkFlowTool.getInputValue($("#busi_money").val());
	
	if("" == busi_money){
		alert("缴费宽为空");
		return false;
	}
	
	var field_names = "busi_money";
	var field_values = busi_money;
	
	var param = {
		"order_id":order_id,
		"field_names":field_names,
		"field_values":field_values
	};
	
	hideBtn();
	
	$.ajax({
     	url:ctx+"/shop/admin/ordAuto!updateAttrsValuesBatch.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				alert("修改缴费款成功");
     			}else{
     				var msg = reply["msg"];
     				alert("修改缴费款失败："+msg);
     			}
     		}else{
     			alert("修改缴费款失败");
     		}
     	},
     	error:function(msg){
     		alert("修改缴费款失败："+msg);
     	}
	});
	
	showBtn();
}

function getIccid(){
	var busi_money = $("#busi_money").val();
	
	if(''==busi_money){
		alert("请先输入BSS/ESS缴费款并保存");
		return false;
	}
	
	RwCard.getReaderName();
	var result = RwCard.ConnetReader(); // 连接写卡器
	
   	if ("0" != result) {
   		alert("连接写卡器失败，请尝试重新插入白卡！");
   		RwCard.DisConnetReader();//读取失败断开写卡链接
   		return false;
   	}
   	
	var cardNum = RwCard.queryUsimNo();
	
	hideBtn();
	
   	if(cardNum!=null && cardNum.length>0 && cardNum!=''){
   		$.ajax({
         	url:app_path+"/shop/admin/orderCrawlerAction!saveICCID.do?ajax=yes&order_id="+order_id+"&ICCID="+cardNum,
         	dataType:"json",
         	async:false,
         	data:{},
         	success:function(reply){
         		if(reply.result != 0){
         			//保存卡信息失败
         			RwCard.DisConnetReader();
         			alert(reply.message);
         		}else{
         			//保存卡信息成功
         			RwCard.DisConnetReader();
         		   	$("#ICCID").empty().html(cardNum);
         		   alert("读卡成功！");
         		}
         	}
		});
   	}else{
   		alert("获取ICCID卡号失败");
   		RwCard.DisConnetReader();//读取失败断开写卡链接
   	}
   	
   	showBtn();
}

function hideBtn(){
	//隐藏按钮，防止一个动作在执行时，操作员点击另一个按钮
	$("#refresh_read_card_btn").hide();
	$("#get_iccid_btn").hide();
	$("#open_acct_btn").hide();
	$("#update_busi_money_btn").hide();
}

function showBtn(){
	$("#refresh_read_card_btn").show();
	$("#get_iccid_btn").show();
	$("#open_acct_btn").show();
	$("#update_busi_money_btn").show();
}