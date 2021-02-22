/**
 * 
 */



function initInfo(order_id){
	var ats;
	var opt;
	
	var field_names = "phone_num,ICCID";
	
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
	$("#ICCID").empty().html(ats.iccid);
	
	if(window.parent.canvas){
		var node = window.parent.canvas.nodes[node_index];
		$("#remark").html(node.remark);
	}
}

function writeCard(){
	hideBtn();
	
	//校验ICCID
	var cardNum = getIccid();
	
	if(cardNum == false){
		showBtn();
		return false;
	}
		
	
	//获取卡信息
	var info = getWriteCardInfo(order_id);
	
	if(cardNum != info.iccid){
		alert("当前白卡的ICCID和开户时的ICCID不一致。当前白卡的ICCID为："+cardNum+"。开户白卡的ICCID为："+info.iccid);
		showBtn();
		return false;
	}
	
	//写卡
	var scriptSeq = info.script_seq;
	var ismi = info.simid;
	var iccid = info.iccid;
	
	var writeResult = 0;
	
	var writeResult =  RwCard.insertCard(iccid, ismi, scriptSeq); //执行写卡
	if (writeResult == -1) {
		alert("写卡失败");
		RwCard.rwButObj.removeAttr('disabled');
		RwCard.DisConnetReader();//读取失败断开写卡链接
		
		showBtn();
		return false;
	}
	
	//保存写卡结果
	var saveResult = saveWriteCardResult(order_id,writeResult);
	
	if(!saveResult){
		showBtn();
		return false;
	}
	
	//执行下一环节
	if(window.parent.canvas){
		window.parent.canvas.runNodeManual(instance_id,"","");
		
		WriteCard.doPrintCard();
		
		alert("写卡完成");
	}else{
		alert("流程画板JS对象为空");
		showBtn();
		return false;
	}
}

function getIccid(){
	RwCard.getReaderName();
	var result = RwCard.ConnetReader(); // 连接写卡器
	
   	if ("0" != result) {
   		alert("连接写卡器失败，请尝试重新插入白卡！");
   		RwCard.DisConnetReader();//读取失败断开写卡链接
   		return false;
   	}
   	
	var cardNum = RwCard.queryUsimNo();
	
   	if(cardNum!=null && cardNum.length>0 && cardNum!=''){
   		return cardNum;
   	}else{
   		alert("获取ICCID卡号失败");
   		RwCard.DisConnetReader();//读取失败断开写卡链接
   		return false;
   	}
}

function getWriteCardInfo(order_id){
	var ats;
	var opt;
	
	var field_names = "ICCID,script_seq,simid";
	
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
	
	return ats;
}

function saveWriteCardResult(order_id,result){
	var param = {
			"order_id":order_id,
			"writeCardResult":result
	};
	
	var saveResult = false;
	
	$.ajax({
     	url:ctx+"/shop/admin/orderFlowAction!saveWriteCardResult.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(reply.result!=0){
     			alert("保存写卡结果失败："+reply.message);
    			RwCard.DisConnetReader();//读取失败断开写卡链接
    			saveResult = false;
    		}else{
    			saveResult = true;
    		}
     	},
     	error:function(msg){
     		alert("保存写卡结果失败："+msg);
     		saveResult = false;
     	}
	});
	
	return saveResult;
}

function checkIccid(){
	RwCard.getReaderName();
	var result = RwCard.ConnetReader(); // 连接写卡器
	
   	if ("0" != result) {
   		alert("连接写卡器失败，请尝试重新插入白卡！");
   		RwCard.DisConnetReader();//读取失败断开写卡链接
   		return false;
   	}
   	
	var cardNum = RwCard.queryUsimNo();
	
   	if(cardNum!=null && cardNum.length>0 && cardNum!=''){
   		//比较读取的卡号和数据库中开户时的卡号是否一致
   		
   		var field_names = "ICCID";
   		
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
   		
   		if(cardNum != ats.iccid){
   			alert("当前白卡的ICCID和开户时的ICCID不一致。当前白卡的ICCID为："+cardNum+"。开户时的白卡ICCID为："+ats.iccid);
   			return false;
   		}
   		
   		return cardNum;
   	}else{
   		alert("获取ICCID卡号失败");
   		RwCard.DisConnetReader();//读取失败断开写卡链接
   		return false;
   	}
}

function doWriteCard(){
	//执行流程
	var param = {
			"ins.instance_id":instance_id
	};
	
	var canvasInstance = this;
	
	$.ajax({
     	url:ctx+"/shop/admin/WorkCustom!runNodeManual.do?ajax=yes",
     	type: "POST",
     	dataType:"json",
     	async:false,
     	data:param,
     	success:function(reply){
     		if(typeof(reply) != "undefined"){
     			if("0" == reply["code"]){
     				alert("执行成功");
     				
     				if("order_intent" == this.order_type){
     					Eop.Dialog.close(this.nodeAttrDiv);
	     				window.location.href=ctx+"/shop/admin/orderIntentAction!intentHandleForm.do";
     				}else{
     					//关闭正式订单页面
     					$(window.top.document).find("#bottom_tab_ul li[order_detail='"+order_id+"'] .tabClose")[0].click();
     				}
     			}else{
     				var msg = reply["msg"];
     				alert(msg);
     				//失败重新加载流程，显示执行到哪一步
     				canvasInstance.loadWorkFlow();
     			}
     		}else{
     			alert("执行环节失败");
     			//失败重新加载流程，显示执行到哪一步
     			canvasInstance.loadWorkFlow();
     		}
     	},
     	error:function(msg){
     		alert("执行环节失败："+msg);
     		//失败重新加载流程，显示执行到哪一步
     		canvasInstance.loadWorkFlow();
     	}
	});
}

function refreshReadCard(){
	hideBtn();
	RwCard.DisConnetReader();//先断开链接
	RwCard.getReaderName();
	showBtn();
}

function hideBtn(){
	//隐藏按钮，防止一个动作在执行时，操作员点击另一个按钮
	$("#refresh_read_card_btn").hide();
	$("#get_iccid_btn").hide();
	$("#open_acct_btn").hide();
	$("#update_busi_money_btn").hide();
	$("#print_card_btn").hide();
}

function showBtn(){
	$("#refresh_read_card_btn").show();
	$("#get_iccid_btn").show();
	$("#open_acct_btn").show();
	$("#update_busi_money_btn").show();
	$("#print_card_btn").show();
}

function doPrintCard(){
	var printUrl=ctx+'/shop/admin/orderPostModePrint!invoiceHotFeePrint.do?ajax=yes&order_id='+order_id+'&print_type=1';
	//弹出打印页面
	var printRe=window.open(printUrl,'','dialogHeight=400px;dialogWidth=500px');
}