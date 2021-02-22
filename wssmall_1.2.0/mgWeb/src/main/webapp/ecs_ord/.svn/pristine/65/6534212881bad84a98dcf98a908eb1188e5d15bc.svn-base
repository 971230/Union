var OrdBtns = {
	showBtns:function(order_id,hide_page,trace_id,btn_box_id,is_order_exp,query_type,callback){
		var url = ctx+"/shop/admin/ordAuto!showBtns.do?ajax=yes&order_id="+order_id+"&query_type="+query_type;
		if(trace_id)url += "&trace_id="+trace_id;
		url = url +"&is_order_exp="+is_order_exp;
		$.ajax({
			url:url,
			dataType:"html",
			async:false,
			success:function(data){
				OrdBtns.removeMenu();
				if(btn_box_id){
					$("#"+btn_box_id).append(data);
				}else{
					$("body").append(data);
				}
				if(hide_page){
					var hides = hide_page.split(",");
					if(hides.length){
						for(i=0;i<hides.length;i++){
							$("a[hide_page*=',"+hides[i]+",']").hide();
						}
					}else{
						$("a[hide_page*=',"+hides+",']").hide();
					}
				}
				if(callback && typeof(callback) == 'function'){
					callback(data);
				}
			}
		});
	},removeMenu:function(){
		var btns = $("#btnlists");
		if(btns && btns.length>0){
			btns.remove();
		}
	}
};
var AutoFlow = {
	getActionUrl:function(order_id){
		var result = "";
		var url = ctx+"/shop/admin/ordAuto!getJspPath.do?ajax=yes&order_id="+order_id;
		Cmp.asExcute('',url,{},function callBack(reply){
			result += reply.result;
			if(reply.result==0){
				result+="|"+reply.action_url;
			}else{
				result +="|"+reply.message;
			}
		},'json');
		return result;
	}, 
	getNewActionUrl:function(order_id){
		var result = "";
		var url = ctx+"/shop/admin/ordAuto!getOrderLockAndJspPath.do?ajax=yes&order_id="+order_id;
		Cmp.asExcute('',url,{},function callBack(reply){
			result += reply.result;
			if(reply.result==0){
				result+="|"+reply.action_url;
			}else{
				result +="|"+reply.message;
			}
		},'json');
		return result;
	}, 
	qeryFlowLogs:function(order_id,trace_id,is_his_order){
		var $this = $(this);
		var order_id_a = $this.attr("order_id");
		var trace_id_a = $this.attr("trace_id");
		var is_his_order_a = $this.attr("is_his_order");
		var is_order_view_a = $this.attr("is_order_view");
		if(order_id_a)order_id=order_id_a;
		if(trace_id_a)trace_id=trace_id_a;
		var url = ctx+"/shop/admin/ordAuto!showFlowsDetail.do?ajax=yes&order_id="+order_id+"&trace_id="+trace_id+"&is_his_order="+is_his_order_a+"&is_order_view="+is_order_view_a
		$.ajax({
			url:url,
			dataType:"html",
			success:function(data){
				$("#auto_flow_logs_dv").empty().append(data);
				$this.siblings("li").removeClass("curr");
				$this.addClass("curr");
				$("#flow_inf_dv").show();
			}
		});
	},
	exeRule:function(){
		var $this = $(this);
		var plan_id = $this.attr("plan_id");
		var rule_id = $this.attr("rule_id");
		var order_id = $this.attr("order_id");
		var exeRelyOnRule = $this.attr("exeRelyOnRule");
		var dType = $("#node_deal_type");
		var dMag = $("#node_deal_message");
		var params = {};
		if(dType && dType.length>0){
			params["dealType"] = dType.val();
		}
		if(dMag && dMag.length>0){
			params["dealDesc"] = dMag.val();
		}
		if(!rule_id)rule_id="";
		var url = ctx+"/shop/admin/orderFlowAction!executeRule.do?ajax=yes&order_id="+order_id+"&plan_id="+plan_id+"&rule_id="+rule_id+"&exeRelyOnRule="+exeRelyOnRule
		$.Loading.show();
		$.ajax({
			url:url,
			dataType:"json",
			data:params,
			type:"post",
			success:function(data){
				alert(data.msg);
				$.Loading.hide();
				if(data.status==0){
					//AutoFlow.qeryFlowLogs(order_id);
					window.location.href=data.action_url;
				}
			},error:function(a,b){
				alert("出错了！")
				$.Loading.hide();
			}
		});
	},
	checkMsg:function(){
		$(".grid_n_cont").each(function(){
			var errorMsgHtml = $(this).find("[class='remind_div']").children("span");
			$(this).find("[class='remind_div']").hide();
			errorMsgHtml.empty();
			var errorMsg = "";
		    $(this).find("[check_message]").each(function(){
		    	errorMsg += $(this).attr("check_message");
		    	$(this).addClass("errorBackColor");
		    });
		    if(errorMsg!=""&& errorMsg!='undefined'){
		    	errorMsgHtml.html(errorMsg);
		    	$(this).find("[class='remind_div']").show();
		    }
       });
	  
	   var spanMsg = $("#specValidateMsg_Span").html();
	   var specValidateMsg = $("#specValidateMsg").val();
	   if(specValidateMsg!='undefined'&&specValidateMsg!=''&&specValidateMsg!=null){
		   var msg = $("#specValidateMsg").val();
		   var error_msg = specValidateMsg.split("|")[0];
		   var error_name = specValidateMsg.split("|")[1];
		  
		   if(error_msg.length>0){
			   $("#specValidateMsg_Span").html(spanMsg+error_msg);
		   }
		   if(error_name.length>0){
			   var nameObj = error_name.split(",");
			   for(var i=0;i<nameObj.length;i++){
				   if(nameObj.length>0){
					   var  name = document.getElementsByName(nameObj[i]);
					   if(name!='undefined'&&name.length>0){
						   document.getElementsByName(nameObj[i])[0].style.background = "#FBE2E2";
					   }
				   }
			   }
		   }
	   }
	   
	}
};

function unlockCheck(){
	if(window.confirm("是否确定要解锁?")){
		return true;
	}else{
		return false;
	}
}
function unlockCallback(data){
	alert(data.msg);
	if(data.status==0){
		var order_id = data.order_id;
		$("a[order_id="+order_id+"]").closest("tr").find("i.unlock").attr("class", "lock");
	}
}
function setBssCheck(){
	
	return true;
}

function setBssCallback(data){
	var result = data.result;	
	var shield = document.createElement("DIV");
	shield.id = "shield";
	shield.style.position = "absolute";
	shield.style.left = "0px";
	shield.style.top = "0px";
	shield.style.width = "100%";
	shield.style.height = "100%";
	shield.style.background = "#D3D3D3";
	shield.style.textAlign = "center";
	shield.style.zIndex = "2999";
	shield.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
	shield.style.opacity="0.6"; 
	var alertFram = document.createElement("DIV");
	alertFram.id="alertFram";
	//<div id="dlg_bssDiv" class="dialogIf jqmID2" style="width: 800px; margin-top: 50px; z-index: 3000; opacity: 0.8; display: block;">
	alertFram.style.display="block";
	alertFram.style.position = "absolute";
	alertFram.style.width = "800px";
//	alertFram.style.height = "300px";
//	alertFram.style.left = "20%";
	alertFram.style.top="50px";
	alertFram.className = "dialogIf jqmID2";
	alertFram.style.background="white"
	alertFram.style.zIndex = "3000";
	var bssStr = "<div class=\"dialog_box\"><div class=\"head\"><div class=\"title\">BSS业务办理</div><span class=\"closeBtn\" onclick=\"exit()\"></span></div><div class=\"body dialogContent\">";
	bssStr = bssStr + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"class=\"grid_s\">";
	bssStr = bssStr + "<tr><th style=\"display:none\">sku</th><th>赠品名称</th><th>状态</th></tr>";
	//var msg = "[{\"sku\":\"1111\",\"goodsName\":\"显示流量\",\"bssStatus\":\"1\"}]";
	var msg = data.msg;
	var order_id = data.order_id;
	var skuInfo = JSON.parse(msg);
	var flag = false;
	for(var i=0;i<skuInfo.length;i++){
		var sku = skuInfo[i];
		var bssStatus = sku.bssStatus;
		var statusName = "";//待办理  办理中 办理失败 办理成功		
		switch(bssStatus/1){
		case 1:
			flag = true;
			statusName = "待办理"
			break;
		case 2:
			statusName = "办理中"
			break;
		case 3:
			statusName = "办理失败"
			break;
		case 4:
			statusName = "办理成功"
			break;
		default:
			statusName = "线下办理"
			break;
		}
		bssStr = bssStr + "<tr><td style=\"display:none\">"+sku.sku+"</td><td>"+sku.goodsName+"</td><td>"+statusName+"</td></tr>"
	}
	bssStr = bssStr + "<tr><td style=\"display:none\"></td><td colspan=2>";
	if("1"==result){
		bssStr = bssStr + "<input type=\"button\" class=\"graybtn1\" value=\"线下完成\" id=\"setBSSFinish\" onclick=\"setFinish()\"/>&nbsp;&nbsp;";
		if(skuInfo.length>0&&flag){		
			bssStr = bssStr + "<input type=\"button\" class=\"graybtn1\" value=\"模拟办理\" id=\"cbssDeal\" onclick=\"cbssDeal()\"/>&nbsp;&nbsp;";			
		}
	}		
	bssStr = bssStr + "<input type=\"button\" class=\"graybtn1\" value=\"关闭\" id=\"exit\" onclick=\"exit()\"/></td></tr>";
	bssStr = bssStr + "</table>";
	bssStr = bssStr + "</div><div class=\"head\"style=\"height:3px;background:#CAD9E6;cursor:move;\"></div></div>";
	alertFram.innerHTML = bssStr;
	document.body.appendChild(alertFram);
	document.body.appendChild(shield);
	this.exit = function(){
		alertFram.style.display = "none";
		shield.style.display = "none";
	}
	this.setFinish = function(){
		var url = ctx+"/shop/admin/orderFlowAction!setBssFinish.do?ajax=yes&order_id="+order_id;
		$.ajax({
			type : "post",
			async : false,
			url : url,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.msg);
				exit();
			}
		});
	}
	this.cbssDeal = function(){
		var url = ctx+"/shop/admin/orderFlowAction!orderCBSSDeal.do?ajax=yes&order_id="+order_id;
		$.ajax({
			type : "post",
			async : true,
			url : url,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.msg);
			}
		});
		alert("BSS业务办理需较长时间，请稍后查看！");
	}
}

function goBackCheck(order_id,form_id){
	var save_flag = true;
//	$.Loading.show();
//	var userid = $("#userid").val();
//	var lock_user_id = $("#lock_order_user_id").val();
//	if (userid != lock_user_id) {
//		return save_flag;
//	}
//	if(window.confirm("是否保存再返回？")){
//		var ac_url = ctx+"/shop/admin/orderFlowAction!flowSave.do?order_id="+order_id+"&ajax=yes";
//		var options = {
//			type : "post",
//			url : ac_url,
//			async : false,
//			dataType : "json",
//			success : function(result) {
//				alert("保存成功!");
//				$.Loading.hide();
//			},
//			error : function(e,b) {
//				alert("保存失败!");
//				$.Loading.hide();
//				save_flag =  false;
//			}
//		}
//		if(form_id){
//			var form = $("#"+form_id);
//			if(form && form.length>0){
//				form.ajaxSubmit(options);
//			}else{
//				$($("form")[0]).ajaxSubmit(options);
//			}
//		}else{
//			$($("form")[0]).ajaxSubmit(options);
//		}
//	}
	return save_flag;
}

function returnedCheck(){
	if(window.confirm("确定申请退单？")){
		return true;
	}else{
		return false;
	}
}
function returnedCallback(data){
	if(data.status==0){
		$("li[trace_id='RETURNEDCONFIRM']").addClass("complete");
		$("li[trace_id='RETURNEDCONFIRM']").addClass("curr");
	}
	alert(data.msg);
}

function reBackCheck(){
	if(window.confirm("确定处理订单？")){
		return true;
	}else{
		return false;
	}
}
function reBackCallback(data){
	alert(data.msg);
	var url = window.location.href;
	window.location.href  = url;
}

function entrust_check() {
	return true;
}
function entrust_back(){
	var $this = $("a[name=o_entrust]");
	selectObj($this);
}
function dealCheck(){
	if(window.confirm("确定处理订单？")){
		return true;
	}else{
		return false;
	}
}
function dealCallback(data,order_id){
	alert(data.message);
	debugger;
	if(data.result==9){//不影响其余情况下处理信息
		var printUrl=ctx+'/shop/admin/orderPostModePrint!invoiceHotFeePrint.do?ajax=yes&order_id='+order_id+'&print_type=1';
		//弹出打印页面
		var printRe=window.open(printUrl,'','dialogHeight=400px;dialogWidth=500px');
		closeDialog();		
		$.Loading.hide();
		//获取订单管理菜单的main_frame
		var main_frame = $(window.top.document).find("iframe[app_menu_id='2014112501']");
		if($(main_frame).length < 1){
			return;
		}
		//获取订单管理菜单的auto_frame
		var auto_frame = $(main_frame).contents().find(".auto_frame");
		if($(auto_frame).length < 1){
		}else{
			for (var i=0;i<$(auto_frame).length;i++){
				if($(auto_frame)[i].attributes["0"].nodeValue=="99992" || $(auto_frame)[i].attributes["0"].nodeValue=="2014112501" || $(auto_frame)[i].attributes["0"].nodeValue=="20141014227"){
					$(auto_frame)[i].contentWindow.orderListReQuery();
				}
			}
		}
		
		var bottom_tab_ul = $(window.top.document).find("#bottom_tab_ul");
		$(window.top.document).find(".main_all_iframe").each(function() {
			if($(this).is(':visible')) {
				$($(this)).contents().find("#right_content").find(".auto_frame").each(function() {
					if($(this).is(':visible')) {
						if("yes" == $(this).attr("order_sub")) { //详情页面
							var li_curr = bottom_tab_ul.find("li[order_detail='" + order_id + "']");
							$(li_curr).prev().addClass("curr").show();
							$(li_curr).prev().trigger("click");
							$(li_curr).removeClass("curr").hide().remove();
							$(this).hide();
							$(this).prev().show();
							$(this).remove();
							return false;
						}
					} 
				});
			}
		})
  	}else{
		if(data.result==0){
			var listBtn = $("#isListBtn");
			if(listBtn&&listBtn.val()){
				var url = window.location.href;
				window.location.href  = url;
			}else{
				//详情页重新加载一遍
				//window.location.href=data.action_url;
				// 关闭详情页
				//获取订单管理菜单的main_frame
				var main_frame = $(window.top.document).find("iframe[app_menu_id='2014112501']");
				if($(main_frame).length < 1){
					return;
				}
				//获取订单管理菜单的auto_frame
				var auto_frame = $(main_frame).contents().find(".auto_frame");
				if($(auto_frame).length < 1){
				}else{
					for (var i=0;i<$(auto_frame).length;i++){
						if($(auto_frame)[i].attributes["0"].nodeValue=="99992" || $(auto_frame)[i].attributes["0"].nodeValue=="2014112501" || $(auto_frame)[i].attributes["0"].nodeValue=="20141014227"){
							$(auto_frame)[i].contentWindow.orderListReQuery();
						}
					}
				}
				var bottom_tab_ul = $(window.top.document).find("#bottom_tab_ul");
				$(window.top.document).find(".main_all_iframe").each(function() {
					if($(this).is(':visible')) {
						$($(this)).contents().find("#right_content").find(".auto_frame").each(function() {
							if($(this).is(':visible')) {
								if("yes" == $(this).attr("order_sub")) { //详情页面
									var li_curr = bottom_tab_ul.find("li[order_detail='" + order_id + "']");
									$(li_curr).prev().addClass("curr").show();
									$(li_curr).prev().trigger("click");
									$(li_curr).removeClass("curr").hide().remove();
									$(this).hide();
									$(this).prev().show();
									$(this).remove();
									return false;
								}
							} 
						});
					}
				})
			}
		}else{
			if(data.validateMsgList){
				var list = data.validateMsgList;
				if(list.length){
					for(var i=0;i<list.length;i++){
						var field_name = list[i].field_name;
						var check_msg = list[i].check_msg;
						$("[field_name='"+field_name+"']").attr("check_message",check_msg);
						$("[field_name='"+field_name+"']").addClass("errorBackColor");
					}
				}
			}
			if(window.canvas){
				//刷新流程图
				window.canvas.loadWorkFlow();
			}
		}
	}
}
function ordOutCallApplyCallback(data){
    alert(data.message);
    if(data.result == 0.) {
    	window.location.href=data.action_url;
    }
}
function dealCallbackNoRefresh(data){
	alert(data.message);
	if(data.result==0){
		var listBtn = $("#isListBtn");
		if(listBtn&&listBtn.val()){
			var url = window.location.href;
			window.location.href  = url;
		}else{
			// add by shusx 详细页面操作时候不刷新页面
			//window.location.href=data.action_url;
		}

	}else{
		if(data.validateMsgList){
			var list = data.validateMsgList;
			if(list.length){
			  for(var i=0;i<list.length;i++){
				  var field_name = list[i].field_name;
				  var check_msg = list[i].check_msg;
				  $("[field_name='"+field_name+"']").attr("check_message",check_msg);
				  $("[field_name='"+field_name+"']").addClass("errorBackColor");
			  }
			}
		}
	}
}

function authSuccCallBack(data){
	alert(data.message);
}

function authFailCallBack(data){
	alert(data.message);
}

function authBackCallBack(data){
	alert(data.message);
}

function authSuccCheck() {
	if(window.confirm("确定认证成功？")){
		return true;
	} else {
		return false;
	}
}

function authFailCheck() {
	if(window.confirm("确定认证失败？")){
		return true;
	} else {
		return false;
	}
}

function authBackCheck() {
	if(window.confirm("确定退单？")){
		return true;
	} else {
		return false;
	}
}

function listDealCheck(){
	if(!window.confirm("确定处理订单？")){
		return false;
	}
	var orderids = $("input[type=checkbox][name=orderidArray]:checked");
	if(!orderids || orderids.length==0){
		alert("最少选择一张订单!");
		return false;
	}else{
		return true;
	}
}

//刷新当前页
function reloadCurrPage(result){
	alert(result.error_msg+"\n\n"+result.success_msg)
	window.location.reload();
}

function reloadPage(){
	
}
function matchCallBack(data){
	alert(data.msg);
}

function openDiv(){
	var me = window.splitScreen;
	$(".openArrow,.closeArrow").each(function(){
		$(this).css("cursor","pointer");
		
		if($(this).find(".grid_n_cont").is(":visible")){
			$(this).attr("class","openArrow");
		}else{
			$(this).attr("class","closeArrow");
		}
		
		//为防止多个页面引用autoord.js导致重复绑定click事件导致的展开、收缩失效问题
		//先解除绑定的click事件再重新绑定
		$(this).unbind('click').click(function(){
		  var grid_n_div = $(this).parent().parent();
		  var grid_n_cont = grid_n_div.find("[class='grid_n_cont']");
		
		  var openOrCloseClass = $(this).attr("class");
		  if(openOrCloseClass =="openArrow"){
			  $(this).attr("class","closeArrow");
			  grid_n_cont.show();
		  }
		  if(openOrCloseClass =="closeArrow"){
			  $(this).attr("class","openArrow");
			  grid_n_cont.hide();
		  }
		  if(me.canNext && me.waitInsts.length>0){
			  window.setTimeout(function(){me.scrollJspInst($(this))},150);
				return false
		  }
		});
	});
}
//校验串号并保存
function saveTerminalNum(order_id){
	$.Loading.show("订单正在配货处理，请稍侯...");
	  var terminal_num=$("#terminal_num_"+order_id).val();
	  if(terminal_num){
		  $.ajax({
				type : "post",
				async : false,
				url : "ordAuto!saveTerminalNum.do?ajax=yes",
				data : {
					"terminal_nums" : terminal_num,
					"order_id" : order_id
				},
				dataType : "json",
				success : function(data) {
					$.Loading.hide();	
					 if(data.result=='1'){//成功
						$("#terminal_num_bt_"+order_id).attr("value","已保存");
						$("#terminal_num_bt_"+order_id).attr("disabled","disabled");
						//打印
						 doPrint(order_id);
						 peihuoPlan(order_id);
					 }else if(data.result=='0'){//校验失败
						 alert(order_id+" "+data.msg);
						 $("#terminal_num_"+order_id).val("");
					 }
				},
				error : function(){
					$.Loading.hide();
				}
			});
	  }
};
function peihuoPlan(order_id){
	$.Loading.show("订单正在配货处理，请稍侯...");
	 $.ajax({
			type : "post",
			async : false,
			url : ctx + "/shop/admin/ordAuto!exePhPlan.do?ajax=yes",
			data : {
				"order_id" : order_id
			},
			dataType : "json",
			success : function(result) {
				 $.Loading.hide();		 
			    // alert(result.message);
			},
			error : function(){
				$.Loading.hide();
			}
	});
}
//校验串号并保存-批量
function saveTerminalNums(){
	 var isChecked=false;
	 $('input:checkbox[name=orderidArray]:checked').each(function(i){
		 var order_id=$(this).val()
		 var logi_ipt=$("#terminal_num_"+order_id);
		 var logi_ipt_value=logi_ipt.attr("value");
		 if(logi_ipt_value!=''){
			 isChecked=true;
			 saveTerminalNum(order_id);
		 }
	 });
	 if(!isChecked){
		 alert("请选择一个多或多个订单，并输入终端串号！");
	 }	
}


function doPrint(order_id){
	var printUrl=ctx+'/shop/admin/orderPostModePrint!invoiceHotFeePrint.do?ajax=yes&order_id='+order_id;
	//弹出打印页面
	var printRe=window.open(printUrl,'','dialogHeight=400px;dialogWidth=500px');
	closeDialog();		
	$.Loading.hide();	
}


//改变串号按钮的可用状态
function initBt(){
	$("a[name=terminal_num_bt]").bind("click",function(){
		 saveTerminalNum($(this).attr("order_id"));
	});
	$("a[name=terminal_num_batch_bt]").bind("click",function(){
		 saveTerminalNums();
	});

	//得到焦点
		 $("input[name=terminal_num]").focus(function() {
			 var order_id=$(this).attr("order_id");
			 var logi_bt=$("#terminal_num_bt_"+order_id);
			 var logi_ipt=$("#terminal_num_"+order_id);
			 var logi_bt_value=logi_bt.attr("value");
			  if('已保存'==logi_bt_value){
				  
			  }else{
				  logi_bt.removeAttr("disabled");
			  }
			 
		 });
		//失去焦点
		 $("input[name=terminal_num]").blur(function() {
			 var order_id=$(this).attr("order_id");
			 var logi_bt=$("#terminal_num_bt_"+order_id);
			 var logi_ipt=$("#terminal_num_"+order_id);
			 var logi_ipt_value=logi_ipt.val();
			 var logi_bt_value=logi_bt.attr("value");
			  if('已保存'==logi_bt_value){
				  
			  }else{
				  if(logi_ipt_value==''){
					  logi_bt.attr("disabled","disabled");
				  }
			  }
		 });
};
$(function(){
	openDiv();
	initBt();
});


//开户写卡合并页面，操作返回刷新
function dealCallbackRefreshNewAcc(data){
	alert(data.message);
	var url = ctx+"/shop/admin/orderFlowAction!toAutoFlow.do?ajax=yes&order_id="+data.order_id;
	if(data.result==0){	
		//刷新顶部流程图
		if(typeof(window.canvas)!="undefined" && window.canvas!=null
				&& typeof(window.canvas.order_id)!="undefined" && window.canvas.order_id!=null
				&& window.canvas.order_id!=""){
			window.canvas.loadWorkFlow();
		}else{
			$.ajax({			
		          url: url,
		          dataType: "html",
		          success: function(data){
		              $('#auto_flows_div').html(data);
		          }
		      });
		}
		
		//刷新开户写卡按钮
		//火狐对disable支持不好，暂时先这么坑爹的实现，以后优化
		$("#refreshCardListNone").attr("id","refreshCardList");
		$("#refreshCardList").attr("style","margin-left:5px;");

		$("#writeCardBtnNone").attr("id","writeCardBtn");
		$("#writeCardBtn").attr("style","margin-left:5px;");
		//alert("dd");
		$("#openAcctBtn").attr("style","margin-left:5px;border:1px solid #DDD;background-color:#F5F5F5;color:#ACA899;a:hover{};");
		$("#openAcctBtn").removeAttr("orderbtns");//上面只是样式伪装disable效果。实际上还是可以点击的，需要把控制点击的元素remove掉
		//刷新底部按钮
//		if($("#cardList").val()==""){
//			$("#refreshCardList").click();
//		}else{
//			$("#writeCardBtn").click();
//		}
		OrdBtns.showBtns(data.order_id,",detail,exception,");
	}else{
		if(data.validateMsgList){
			var list = data.validateMsgList;
			if(list.length){
			  for(var i=0;i<list.length;i++){
				  var field_name = list[i].field_name;
				  var check_msg = list[i].check_msg;
				  $("[field_name='"+field_name+"']").attr("check_message",check_msg);
				  $("[field_name='"+field_name+"']").addClass("errorBackColor");
			  }
			}
		}
	}
}


