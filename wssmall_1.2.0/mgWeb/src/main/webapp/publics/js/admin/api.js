/**
 * add by wui 分销系统核心api
 */

var Cmp = Cmp || {};
 Cmp = {
	/* ajax装载页面数据 */  
	load : function(id, url, params, callBack) { //url 漏掉了 当params为空时为get请求，否则post
		$("#" + id).load(url, params, callBack);
	},
	/* 异步处理 */
	excute : function(wrapper_id, url, params, callBack, dataType) { // dataType:html,json
		var self = this;
		var data = jQuery.param(params);
		dataType = dataType || 'html';
		//alert(url+":"+dataType)
		// alert(wrapper_id+":"+url+":"+data)
		$.ajax({
					type : "post",
					url : url,
					data : data,
					dataType : dataType,
					success : function(result) {

						if (dataType == "html" && wrapper_id) {
							$("#" + wrapper_id).empty().append($(result));
						}
						
						if (dataType =="json" && result.result == 1) {
							$.Loading.hide();
						}
						callBack(result); // 回调函数
						
					},
					error : function() {
						$.Loading.hide();
						alert("操作失败，请重试");
					}
				});
	},
	/* 同步处理 */
	asExcute : function(wrapper_id, url, params, callBack, dataType) {// dataType:html,json
		var self = this;
		var data = jQuery.param(params);
		dataType = dataType || 'html';
		$.ajax({
					type : "post",
					url : url,
					data : data,
					async : false,
					dataType : dataType,
					success : function(result) {
						if (dataType == "html" && wrapper_id) {
							$("#" + wrapper_id).empty().append($(result));
						}
						callBack(result); // 回调函数
						
						if (dataType =="json" && result.result == 1) {
							$.Loading.hide();
						}
					},
					error : function() {
						$.Loading.hide();
						alert("操作失败，请重试");
					}
				});
	},
	ajaxSubmit : function(form_id, wrapper_id, url, params, callBack, dataType) { // dataType:html,json
		if (dataType =="json")
			$.Loading.show('正在响应您的请求，请稍侯...');
		var self = this;
		var data = jQuery.param(params);  // 这里应该是data = params see http://malsup.com/jquery/form/#options-object
		dataType = dataType || 'html';
		var options = {
			type : "post",
			url : url,
			data : data,
			dataType : dataType,
			success : function(result) {
				if (dataType == "html" && wrapper_id && !params['insert']) { //params['insert']前面似乎不该有！
					$("#" + wrapper_id).empty().append($(result));
				}
				
				if (dataType =="json" && result.result == 1) {
					$.Loading.hide();
				}
				
				if(result.result == 0){
					if($(".jqmOverlay").length>1)
					{
						$("#loading").hide();
					 	$(".jqmOverlay:gt(0)").remove();
					}else
						$.Loading.hide();
				}
				
				callBack && callBack(result); // 回调函数
				
				
			},
			error : function(e) {
				$.Loading.hide();
				alert("操作失败，请重试"+e);
			}
		}
		var formJq =$("#" + form_id);
		if(!formJq.do_validate()) {
			if (dataType == "json") {
				$.Loading.hide(); //当页面验证不通过 && datatype="json" loading页面要关闭。
			}
			return;
		}
//		var msg ="";
//		for( p in options)
//		 	msg+=p+":"+options[p];
//		 alert(msg);
		formJq.ajaxSubmit(options);
	},
	bankPayBack:function(responseText,url){
	
		if(responseText.indexOf("{result:")>-1){
			var responseText = eval('(' + responseText + ')');   
			if (responseText.result == 1) {
					
				
				$("#pay_dialog").load(url,function(){});
				var bankForm = document.getElementById("bankForm");
				bankForm.action = responseText.bankURL;
//				var msg ="";
//				for(p in responseText)
//					msg+=p+":"+responseText[p];
//				alert(msg);
				bankForm.target = "_blank";
				bankForm['MERCHANTID'].value = responseText.MERCHANTID;
				bankForm['SUBMERCHANTID'].value = responseText.SUBMERCHANTID;
				bankForm['ORDERSEQ'].value = responseText.ORDERSEQ;
				bankForm['ORDERREQTRANSEQ'].value = responseText.ORDERREQTRANSEQ;
				bankForm['ORDERDATE'].value = responseText.ORDERDATE;
				bankForm['ORDERAMOUNT'].value = responseText.ORDERAMOUNT;
				bankForm['PRODUCTAMOUNT'].value = responseText.PRODUCTAMOUNT;
				bankForm['ATTACHAMOUNT'].value = responseText.ATTACHAMOUNT;
				bankForm['CURTYPE'].value = responseText.CURTYPE;
				bankForm['ENCODETYPE'].value = responseText.ENCODETYPE;
				bankForm['MERCHANTURL'].value = responseText.MERCHANTURL;
				bankForm['BACKMERCHANTURL'].value = responseText.BACKMERCHANTURL;
				bankForm['BANKID'].value = responseText.BANKID;
				bankForm['ATTACH'].value = responseText.ATTACH;
				bankForm['BUSICODE'].value = responseText.BUSICODE;
				bankForm['TMNUM'].value = responseText.TMNUM;
				bankForm['CUSTOMERID'].value = responseText.CUSTOMERID;
				bankForm['PRODUCTID'].value = responseText.PRODUCTID;
				bankForm['PRODUCTDESC'].value = responseText.PRODUCTDESC;
				bankForm['MAC'].value = responseText.MAC;
				bankForm['CLIENTIP'].value = responseText.CLIENTIP;
				bankForm.submit();
				
			}
			if (responseText.result == 0) {
				
				alert(responseText.message);
			}
		
		}else{
			$("#wrapper").empty().append($(responseText));
		}
	},bankPayBackNew:function(data){
		//跳转到银行支付界面
		var form = $("<form action='"+data.bankUrl+"' method='post' target='_blank'></form>");
		$.each(data.attrs,function(idx,item){
			form.append("<input type='hidden' name='"+item.key+"' value='"+item.value+"' />")
		});
		form.submit();
	}
}
/*
 * 
 * 举例说明：
 * 
 */