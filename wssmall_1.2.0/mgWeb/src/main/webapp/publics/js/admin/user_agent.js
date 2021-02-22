
/**
 * 供货商选择器
 */
var load = {
	asSubmit : function(url, params, dataType, callBack) {
		var data = jQuery.param(params);
		dataType = dataType || 'html';
		$.ajax({
					type : "post",
					url : url,
					data : data,
					dataType : dataType,
					success : function(result) {

						if (dataType == "json" && result.result == 1) {
							$.Loading.hide();
						}
						callBack(result); // 回调函数

					}
				});
	}
};


function register() {

		//if(!$("#file_url").val()){
		//	alert("请上传廉政协议!");
		//	return false;
		//}
		url = ctx+ "/shop/supplier/supplier!add.do?ajax=yes";
		
		$.Loading.show("正在注册...");
		
		load.asSubmit(url, ShopUtil.getInputDomain(), 'json', function(responseText) {
					$.Loading.hide();
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						alert(responseText.message);
						window.location.href="member_login.html";
					}
				});
}
//修改供应商信息
function register_modify(){
	url = ctx+ "/shop/supplier/supplier!supplierEdit.do?ajax=yes";
	$.Loading.show("正在修改...");
	load.asSubmit(url, ShopUtil.getInputDomain(), 'json', function(responseText) {
		$.Loading.hide();
		if (responseText.result == 1) {
			alert(responseText.message);
		}
		if (responseText.result == 2) {
			alert(responseText.message);
			window.location.href="member_info.html";
		}
	});
}
function back() {
	alert("注册失败!");
}


function secondStep1(){
		
	var supplier_type='';
		var url=window.location.search;
		if(url.indexOf("?")!=-1)   
		{   
		  var str= url.substr(1)   
		  strs = str.split("&");   
		  for(i=0;i<strs.length;i++)   
		  {   
		    if([strs[i].split("=")[0]]=='supplier_type')
		    	 supplier_type=unescape(strs[i].split("=")[1]);
		  }   
		}

		if(supplier_type==1){
			$("#first_step_1").show();
			$("#first_step_2").hide();
			$("#supplier_type").val(1);
			
		}else if(supplier_type==2){
			$("#first_step_1").hide();
			$("#supplier_type").val(2);
			$("#first_step_2").show();
			$("H1").html("经销商注册");
		}
		else if(supplier_type==3){
			$("#first_step_1").hide();
			$("#supplier_type").val(2);
			$("#first_step_2").show();
			$("H1").html("经销商信息修改");
		}
		$("#second_step").hide();
}


function thirdStep1(){
	$("#third_step").hide();
	$("#second_step").show();
}

function fourthStep1(){
	$("#fourth_step").hide();
	$("#third_step").show();
}

function register1(){
	$("#fifth_step").hide();
	$("#fourth_step").show();
}

function forward(){//第一步
	var url =  ctx+"/shop/supplier/supplier!supplierSequences.do?ajax=yes";

	  	load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 0) {
						$("#supplier_id").val(responseText.message);
					}
					
					if (responseText.result == 1) {
						alert(responseText.message);
					}
				});
	
	
	$("#company_name_e").blur(function() {
		var company_name = $.trim($('#company_name_e').val());
		company_name = encodeURI(encodeURI(company_name, true), true);
		url = ctx
				+ "/shop/supplier/supplier!isExits.do?company_name="
				+ company_name + "&ajax=yes";
		if (company_name.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#company_name_message").html(responseText.message);
						$('#company_name_e').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#company_name_message").html(responseText.message);
					}

				});
	});

	$("#account_number").blur(function() {
		var account_number = $.trim($('#account_number').val());
		account_number = encodeURI(encodeURI(account_number, true), true);
		url = ctx
				+ "/shop/supplier/supplier!isExits.do?account_number="
				+ account_number + "&ajax=yes";
		if (account_number.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#account_number_message").html(responseText.message);
						$('#account_number').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#account_number_message").html(responseText.message);
					}
				});
	});
	
	
	$("[name='is_fils']").click( function () {
		if($(this).val()==1){
			$("#parent").hide();
		}else{
			$("#parent").show();
		}
	}); 
	
	$("#conf_passowrd").blur( function () { 
		var password=$("#password").val();
		var conf_passowrd=$("#conf_passowrd").val();
	
		if(password!=conf_passowrd){
			$("#passowrd_message").html("两次输入的密码不一样!请重新输入!");
			$("#passowrd_message").attr('style','color:#CC0000');
			return false;
		}else{
			$("#passowrd_message").html("");
		}
	 }); 
	
	Eop.Dialog.init({id:"parentDialog",modal:false,title:"关联母公司",width:'500px'});
	
	
	$("input[name='show_parent']").focus(function(){
		AgentSelector.open("parentDialog",null);
	}); 
	$("#first_step_1").hide();
	$("#first_step_2").hide();
	$("#second_step").show();
}

function secondStep(){//第二步
	var formJq = $("#userAgentForm1");
	if (!formJq.do_validate()) {
		return false;
	}

	var password=$("#password").val();
	var conf_passowrd=$("#conf_passowrd").val();
	
	if(password!=conf_passowrd){
		alert("两次输入的密码不一样!请重新输入!");
		return false;
	}
		
	$("#second_step").hide();
	$("#third_step").show();
	$("#third_step").find("input[id='company_name']").val($("#second_step").find("input[id='company_name_e']").val());
}


function thirdStep(){//第三步
	var formJq = $("#userAgentForm2");
		if (!formJq.do_validate()) {
			return false;
		}
		
		
		
	if(!$("#license_url").val()){
		alert("请上传营业执照图片附件!");
		return false;
	}
	
	if(!$("#general_tax_url").val()){
		alert("请上传一般纳税人证件图片附件!");
		return false;
	}
	
	if(!$("#place_registr_url").val()){
		alert("请上传地方税务登记号附件图片附件!");
		return false;
	}
	$("#third_step").hide();
	$("#fourth_step").show();
	
	/**
	 * 添加供货商账户
	 * */
	$("#addAccount").unbind("click").bind("click",function() {
		var $table = $(this).closest("div").parent().next().find("table:eq(0)").clone();
		$table.find("input[type='text']").val("");
		var $delButton = $('<a href="javascript:void(0)" class="graybtn_blue"><span id="delete">删除</span></a>');
		//var size=uploadBtns.length;
		//uploadBtns[size] = {'btnId':'spanButtonPlaceHolder'+size,'showimgId':'imgPrivew'+size,'imgfilePath':'certificate_url',"uploadProgressId":'uploadProgress'+size};
		$table.find("tr:eq(2)").empty();
		$table.find("tr:eq(3)").find("td:eq(1)").append($delButton);
		
		//$table.find("tr:eq(2)").find("div#album_tab").find("input[type='hidden']").val("");
		//$table.find("div#imgPrivew"+size).empty().append('<p style="margin-top: 100px">此处显示营业执照图片<br>[您还未上传营业执照图片！]</p>');
		$table.appendTo(".addAccount_div");
		$("form.validate").validate();
		$delButton.bind("click",function() {
			$(this).closest("table").remove();
		});
	});
	/**
	 * 添加代理原产商及产品列表
	 * */
	$("#addAgent").unbind("click").bind("click",function() {
		var $table = $(this).closest("div").parent().next().find("table:eq(0)").clone();
		$table.find("input[type='text']").val("");
		var $delButton = $('<a href="javascript:void(0)" class="graybtn_blue"><span id="delete">删除</span></a>');
		$table.find("tr:eq(2)").empty();
		$table.find("tr:eq(1)").find("td:eq(1)").append($delButton);
		$table.appendTo(".addAgent_div");
		$("form.validate").validate();
		$delButton.bind("click",function() {
			$(this).closest("table").remove();
		});
	});
	/**
	 * 添加认证证书列表
	 * */
	$("#addCertificate").unbind("click").bind("click",function() {
		var $table = $(this).closest("div").parent().next().find("table:eq(0)").clone();
		$table.find("input[type='text']").val("");
		var $delButton = $('<a href="javascript:void(0)" class="graybtn_blue"><span id="delete">删除</span></a>');
		$table.find("tr:eq(2)").empty();
		$table.find("tr:eq(2)").append("<td></td>").append($delButton);
		$table.appendTo(".addCertificate_div");
		$("form.validate").validate();
		$delButton.bind("click",function() {
			$(this).closest("table").remove();
		});
	});
	/**
	 * 添加人力资料情况
	 * */
	$("#addHR").unbind("click").bind("click",function() {
		var $table = $(this).closest("div").parent().next().find("table:eq(0)").clone();
		$table.find("input[type='text']").val("");
		var $delButton = $('<a href="javascript:void(0)" class="graybtn_blue"><span id="delete">删除</span></a>');
		$table.find("tr:eq(4)").find("td:eq(0)").append($delButton);
		$table.appendTo(".addHR_div");
		$("form.validate").validate();
		$delButton.bind("click",function() {
			$(this).closest("table").remove();
		});
	});
	
}

function fourthStep(){//第四步

	var formJq = $("#userAgentForm3");
	if (!formJq.do_validate()) {
		return false;
	}
	
	if(!$("#accoun_attachment").val()){
		alert("请上传开户许可证附件图片!");
		return false;
	}
	
	if(!$("#agent_attachment").val()){
		alert("请上传代理原厂商附件图片!");
		return false;
	}
	
	if(!$("#certificate_url").val()){
		alert("请上传认证证书附件图片!");
		return false;
	}
	$("#fourth_step").hide();
	$("#fifth_step").show();
}

function uploadFile(){//第五步 上传
       var   url = ctx+ "/shop/supplier/supplier!uploadFile.do?ajx=yes";
      Cmp.ajaxSubmit('supplierAgtForm', '', url, {}, uploadFileJsonBack, 'json');
}

function  download(){//下载
     
     var url =  ctx+"/servlet/supplierAgtDownLoadServlet?fileName=a.pdf&fileType=pdf";
    
	 window.location.href = url;
	
}
function uploadFileJsonBack(responseText){
  if (responseText.result == 1) {
			alert(responseText.message);
		}
   if (responseText.result == 0) {
			alert(responseText.message);
	  }
}



var AgentSelector={
	conid:undefined,
	init:function(conid,onConfirm)	{
		this.conid = conid;
		var self  = this;
		$("#searchBtn").click(function(){//searchBtn 搜索
			self.search(onConfirm);
		});
		
		$("#"+conid+" .submitBtn").click(function(){
					if($('input[name="company_id"]:checked').length >0 ) {	
						//赋值
						var value = $('input[name="company_id"]:checked').val();
						var arr = value.split(",");
						$("#parent_id").val(arr[0]);
						$("#show_parent").val(arr[1]);
					}
					Eop.Dialog.close(conid);
				});
		 
	},
	search:function(onConfirm){
	    
		var self = this;
		
		var company_name=encodeURI(encodeURI($("#parentDialog [name='parent_dialog']").val()));
		var options = {
		       url : ctx+"/shop/supplier/supplier!showParentDialogByName.do?ajax=yes&company_name="+company_name,
				type : "post",
				dataType : 'html',
				success : function(result) {				
					$("#"+self.conid).empty().append(result);
					self.init(self.conid,onConfirm);
				},
				error : function(e) {
					alert("出错啦:(");
 				}
 		};
 
		$("#query_agent_form").ajaxSubmit(options);		
	},
	
	open:function(conid,onConfirm){
	  	var self= this;
		var url=ctx+'/shop/supplier/supplier!showParentDialogByName.do?ajax=yes';
		$("#"+conid).load(url,function(){
		  self.init(conid,onConfirm);
		});
		Eop.Dialog.open(conid);		
	}
};

