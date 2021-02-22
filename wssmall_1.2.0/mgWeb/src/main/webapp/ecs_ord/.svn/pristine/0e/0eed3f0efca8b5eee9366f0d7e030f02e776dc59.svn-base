$(function(){
	RwCard.rwButObj = $("#writeCardBtn");
	WriteCard.init();
});
var WriteCard ={
	   init:function(){
		   var isShowOpenAcct=$("#isShowOpenAcct").val();
		   if(isShowOpenAcct!='true'){ //add zhangjun非开户环节才初始化
		     RwCard.getReaderName();//初始化写卡器列表
		   }
			//读卡
			$("#readCardBtn").bind("click",function(){
				WriteCard.readCard();
			}); 
			//刷新读卡器列表
			$("#refreshCardList").bind("click",function(){
				RwCard.DisConnetReader();//先断开链接
				RwCard.getReaderName();
                
			});
			$("#writeCardBtn").bind("click",function(){ 
				$("#refreshCardList").click();
				RwCard.rwButObj.attr("disabled",true);
				/*var simCardNo = "89860114851059731778"; 
			    var imsi = "460019018503626";
				var option = "!A0A40000023F00,S,,9FXX!A0A40000027F10,S,,9FXX!A0A40000026F42,S,,9FXX!A0DC010428FFFFFFFFFFFFFFFFFFFFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF0891683110808805F0FFFFFFFFFFFF,S,,9000";
				*/
				//RwCard.insertCard(simCardNo, imsi, option); 
				//以上是测试数据
				WriteCard.write();//写卡方法
			});
			//刷新读卡器列表
			$("#refreshCardListNone").bind("click",function(){
				RwCard.DisConnetReader();//先断开链接
				RwCard.getReaderName();				
			});
			$("#writeCardBtnNone").bind("click",function(){ 
				$("#refreshCardList").click();
				RwCard.rwButObj.attr("disabled",true);
				WriteCard.write();//写卡方法
			});
	   },
	readCard:function(){
	       var read_name = $("#cardList").val(); //获取读卡器列表值
	       if(read_name==null||read_name.length==0||read_name=='undefined'){
	    	   alert("请选择写卡器");
	    	   return ;
	       }else{
	    	   readerName = read_name ;//把获取的值存到全局变量中
	       }
	      var result = RwCard.ConnetReader(); // 连接写卡器
	      if ("0" != result) {
	        RwCard.process(currStep, 'err', '连接写卡器失败，请尝试重新插入白卡！');
	        RwCard.rwButObj.removeAttr('disabled');
	        RwCard.DisConnetReader();//读取失败断开写卡链接
	        return;
	      }
	      //第一步 读卡开始
	      currStep = 1;
	      RwCard.process(currStep, 'init','读卡');
	      var cardImsi = "";
	     cardImsi = RwCard.queryCardImsi();
	      if ("-1" == cardImsi) {
	        // 解绑读卡
	        RwCard.process(currStep, 'err','此卡已写，请更换白卡！');
	        RwCard.DisConnetReader();
	        RwCard.rwButObj.removeAttr('disabled');
	        return;
	      }
	      var cardNum = RwCard.queryUsimNo();
	      if(cardNum!=null&&cardNum.length>0&&cardNum!=''){
	    	  $("#ICCID").empty().html(cardNum);
	    	  $('#iccidInput').find('input').val(cardNum);
	    	  RwCard.process(currStep, 'succ');
	      }else{
	    	  RwCard.DisConnetReader();//读取失败断开写卡链接
	    	  RwCard.process(currStep, 'err',"获取ICCID卡号失败");
	    	  RwCard.rwButObj.removeAttr('disabled');
	      }
	      return true;
	},
	 /*通知总部写卡方法*/
	resultToFun:function(){
    	 var order_id = $("#orderId").val();
    	 var resultToZBUrl = ctx+"/shop/admin/orderFlowAction!getResultToZB.do?ajax=yes&order_id="+order_id;
	     var resultToZBBack = function(reply){
	    	var result = reply.result;
	    	if(result==0){
	    		RwCard.process(currStep, 'succ');
	    		RwCard.rwButObj.removeAttr('disabled');
	    		//window.location.href = reply.action_url ;
	    		//写卡完成后调用号卡热敏单打印
	    		WriteCard.doPrintCard();
	    		alert("写卡完成");
	    	}else{
	    		 RwCard.process(currStep, 'err',"通知写卡结果给总部失败 "+reply.message);
	    		 RwCard.DisConnetReader();//读取失败断开写卡链接
	    		 RwCard.rwButObj.removeAttr('disabled');
	    	}
	     };
	     Cmp.excute('',resultToZBUrl,{},resultToZBBack,'json');
	},
	/*调用号卡热敏单打印*/
	doPrintCard : function() {
		var order_id = $("#orderId").val();
		var printUrl=ctx+'/shop/admin/orderPostModePrint!invoiceHotFeePrint.do?ajax=yes&order_id='+order_id+'&print_type=1';
		//弹出打印页面
		var printRe=window.open(printUrl,'','dialogHeight=400px;dialogWidth=500px');
		closeDialog();		
		$.Loading.hide();	
	},
	
	/*保存写卡结果到订单树*/
	saveWriteCardResult:function(result){
		var order_id = $("#orderId").val();
		
		var url = ctx+"/shop/admin/orderFlowAction!saveWriteCardResult.do?ajax=yes&order_id="+order_id+"&writeCardResult="+result;
		var saveResultBack = function(reply){
			if(reply.result!=0){
				alert(reply.message);
				RwCard.DisConnetReader();//读取失败断开写卡链接
		    	RwCard.process(currStep, 'err',"保存写卡结果失败");
		    	RwCard.rwButObj.removeAttr('disabled');
				return false;
			}else{
			      RwCard.process(currStep, 'succ');//写卡成功
			      currStep = 5;//第五步 通知总部 变更写卡成功标志
			      RwCard.process(currStep, 'init',"通知总部写卡结果");
			      WriteCard.resultToFun();//成功后记录写卡成功标志
			}
		};
		Cmp.excute('',url,{},saveResultBack,'json');
	},
	
	//写卡
	write:function(){
		 
		  //读取卡号
		  currStep = 1 ;//第一步  读卡
		  var flag01 = WriteCard.readCard();
		  if(!flag01){
			  RwCard.rwButObj.removeAttr('disabled');
			  RwCard.DisConnetReader();//读取失败断开写卡链接
			  return false;
		  } 
		 
		  currStep = 2; //第二步  开户
		  RwCard.process(currStep, 'init','开户');
		  var openAccountStatus = $("#openAccountStatus").val();
		 
		  if(openAccountStatus){
			  RwCard.process(currStep, 'succ');
		  }else{
			  RwCard.process(currStep, 'err');
			  RwCard.rwButObj.removeAttr('disabled');
			  RwCard.DisConnetReader();//读取失败断开写卡链接
			  return false;
		  }
		  currStep = 3; //第三步  获取写卡数据
		  RwCard.process(currStep, 'init','获取写卡数据');
		  
		  var order_id = $("#orderId").val();
		  var iccid = $("#ICCID").html();
		  
		  // 支持重新写卡
		  WriteCard.writeCardRules(order_id,iccid);
	},
	//获取写卡数据
	getCardInfoBack:function(reply){
		if(reply.result==0){
			 RwCard.process(currStep, 'succ');//第三步 获取写卡数据 成功 
			 var cardInfo = reply.cardInfo;
			 var scriptSeq = cardInfo.script_seq;
			 var ismi = cardInfo.ismi;
			 var iccid = cardInfo.iccid;
			 currStep = 4 ;//第四步  写卡
			 RwCard.process(currStep, 'init',"写卡");
			 
			 var writeResult =  RwCard.insertCard(iccid, ismi, scriptSeq); //执行写卡
			 if (writeResult == -1) {
				 RwCard.process(currStep, 'err',"写卡失败");
			    	RwCard.rwButObj.removeAttr('disabled');
					RwCard.DisConnetReader();//读取失败断开写卡链接
			    	return false;
			 }else{
				 WriteCard.saveWriteCardResult(writeResult); 
			 }
			  
		 }else{
			 alert(reply.message);
			 RwCard.process(currStep, 'err',reply.message);
			 RwCard.rwButObj.removeAttr('disabled');
			 RwCard.DisConnetReader();//读取失败断开写卡链接
			 
			 if(canvas)
				 canvas.loadWorkFlow();
		 } 
	},
	// 支持重新写卡
	writeCardRules:function(orderid,iccid){
		$.ajax({
			url:ctx+"/shop/admin/orderFlowAction!writeCardRules.do?ajax=yes",
			type: "POST",
			dataType: "json",
			async: false,
			data:{
				order_id:orderid,
				ic_Card:iccid
			},
			success: function(data) {
				var code = data.code;
				var msg = data.msg;
				if(code==0){
					//执行写卡方法
					var url = ctx+"/shop/admin/orderFlowAction!getWriteCardInfo.do?ajax=yes&order_id="+orderid+"&ic_Card="+iccid;
					Cmp.excute('',url,{},WriteCard.getCardInfoBack,'json');  
				}else if(code==3){
					var canvasInstance;
					// 刷新页面流程加载页面
					if(window.canvas)
						canvasInstance = window.canvas;
					else if(window.WorkFlowTool)
						canvasInstance = window.WorkFlowTool.canvas;
					if(typeof(canvasInstance) != "undefined" && canvasInstance != null){
						canvasInstance.loadWorkFlow();
						// 刷新写卡页面 
						RwCard.process('', 'clear','未开始');
						//初始化业务 
						WriteCard.init();
						//写卡 
						WriteCard.write();
					}
			    }else{
			    	alert(msg);
			    }
			},
			error: function(msg) {
				alert("写卡过程中出现错误！");
			}
		});
	}
	
}