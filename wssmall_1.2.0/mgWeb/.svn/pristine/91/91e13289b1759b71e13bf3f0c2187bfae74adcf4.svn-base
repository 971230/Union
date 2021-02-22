var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

var param={};
function encryptByDES(message, key) {    
    
    var keyHex = CryptoJS.enc.Utf8.parse(key);  
    var encrypted = CryptoJS.DES.encrypt(message, keyHex, {    
    mode: CryptoJS.mode.ECB,    
    padding: CryptoJS.pad.Pkcs7    
    });   
    return encrypted.toString();    
}
//64位加密。
function base64encode(str) {
	var out, i, len;
	var c1, c2, c3;

	len = str.length;
	i = 0;
	out = "";
	while (i < len) {
		c1 = str.charCodeAt(i++) & 0xff;
		if (i == len) {
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt((c1 & 0x3) << 4);
			out += "==";
			break;
		}
		c2 = str.charCodeAt(i++);
		if (i == len) {
			out += base64EncodeChars.charAt(c1 >> 2);
			out += base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			out += base64EncodeChars.charAt((c2 & 0xF) << 2);
			out += "=";
			break;
		}
		c3 = str.charCodeAt(i++);
		out += base64EncodeChars.charAt(c1 >> 2);
		out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
		out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
		out += base64EncodeChars.charAt(c3 & 0x3F);
	}
	return out;
}

function generateMixed(n) {
	var res = "";
	for ( var i = 0; i < n; i++) {
		var id = Math.ceil(Math.random() * base64EncodeChars.length);
		res += base64EncodeChars[id];
	}
	return res;
}

var ZTP={};
ZTP.LOGIN={
	login_btn:undefined,
	domains:undefined,
	userid:undefined,
	siteid:undefined,
	adminid:undefined,
	loginedCount:0,
	defaults:{
		  
	},
	
	
	
	init:function(opations){
		$("input").attr("autocomplete","off");
		if(opations) this.defaults = opations;
		
		var that =this;

		$(document).keydown(function(event){
			if(event.keyCode==13){
				that.login();
			}
			
		});
			
		this.initValidCode();
		this.login_btn=$("#login_btn");
		
		//this.login_btn.attr("disabled",false).val("确定"); 
		this.login_btn.click(function(){
			that.login();
		});
		
	},
	oneLogin:function(url){
	
		if(this.loginedCount<this.domains.length){
			this.appLogin(url); 
		}else if(this.loginedCount==this.domains.length){
			this.success();
		}
		this.loginedCount ++;
	}
	,
	/*
	 * 进行登录操作
	 */
	login:function(){
		var username = $("#username").val();
		if(username == null || username ==""){
			that.fail("请输入用户名");
			return;
		}
		var password = $("input[name='password']").val();
		var app_info = $("#app_info").val();
		var valid_code = $("#valid_code").val();
		var CSRFToken = $("#CSRFToken").val();
//		var encryptionLength = password.length;
		if(db_source_from == 'ECS' || db_source_from == 'ECSORD'){
//			password += generateMixed(25);
			password =encryptByDES(password,desencrptKey);
			password = base64encode(password);
			username = base64encode(username);
			
		}
		
		//this.login_btn.attr("disabled",true).val("正在登陆..."); 
		var that =this;
			var postData = null;
			var codeImgLen = $("#code_img").length;//某些帐号强制使用验证码登录,所以通过是否存在验证码输入来验证;---zengxianlian
			if(codeImgLen>0){
				postData = {username:username,password:password,valid_code:valid_code,app_info:app_info,CSRFToken:CSRFToken};
			}else{
				postData = {username:username,password:password,valid_code:valid_code,app_info:app_info,type:"smsLogin",CSRFToken:CSRFToken};
			}
			$.ajax({url : "login.do",
				type : "POST",
				data:postData,
				dataType : 'json',
				success : function(data) {
					if(0==data.result){
						that.success();
					}else if(2==data.result){
						login_first();
					}else {
						that.fail(data.message);
						$("#code_img").trigger("click");
						$("#valid_code").val("");
					}
				},error : function(a,b) {
					alert("出现错误 ，请重试");
				}
			});
	},
	
	
	/*
	 * 为每个应用登录
	 */
	appLogin:function(u){
	 
		this.debug("applogin -> "+this.loginedCount );
		var that  = this;
		try{
			var domain = this.domains[this.loginedCount] + "/eop/login";
			domain+="?userid="+this.userid+"&siteid="+ this.siteid +"&adminid="+this.adminid;
			this.debug( "<b>"+ u +" call login to "+ domain+"</b>");
			 
			var frm = $("<iframe style='display:none'>").appendTo($("body"));
			frm.load(function(){
				that.debug("login complete  url is:" + this.src);
				that.oneLogin(this.src);
			});
			frm.attr("src",domain);
//			$.getScript(
//				 domain,
//				 {userid:that.userid,siteid:that.siteid,adminid:that.siteid},
//			     function(){ alert("ok");}
//			 );
	 
		}catch(e){
			that.fail(e);
		}
		
	},
	success:function(){
		this.login_btn.attr("disabled",false).val("确定"); 
		if(typeof this.defaults.success) this.defaults.success();
		this.debug("登陆成功");
	}
	,
	/*
	 * 登录失败
	 */
	fail:function(e){
		this.login_btn.attr("disabled",false).val("确定"); 
		if(this.defaults.fail) this.defaults.fail(e);
		if("验证码输入错误"==e){
			$("#code_img").trigger("click");
		}
		this.debug("登录失败"+e);
	},
	/*
	 * 初始化验证码
	 */
	initValidCode:function(){
		//$("#valid_code").val('');
		$("#username").focus();
	    var that =this;
	    if(1==checkType){
	    	var htmlText='<input type="text" id="valid_code" name="valid_code" class="login_ipt" style="width:100px;" />';
			htmlText+='<button type="button" id="smsBtn">发送短信</button>';
			$("#checkTd").empty().append(htmlText);
			$("#smsBtn").click(function(){
				$("#err_info").html("");
				$.ajax({url : ctx + "/wss_login!sendSmsToUser.do?ajax=yes",
					data:{user_name:$("#username").val()},
					type : "POST",
					dataType : 'json',
					success : function(data) {
						if(null == data){
							alert("短信发送失败!");
						}else{
							var msg = data.message!=null?data.message:data.resp_msg;
							alert(msg);
							if(data.status==null||data.status!='0'){
								var $smsBtn = $("#smsBtn");
								var i =60;//控制按钮时间
								var j = 300;//控制验证码时间
								$smsBtn.html("重新发送("+i+")")
								$smsBtn.attr("disabled","disabled");
								var intervalId = setInterval(function(){
									i--;
									j--;
									if(i>0){
										$smsBtn.html("重新发送("+i+")");
									}else if(i==0){
										$smsBtn.html("重新发送");
										$smsBtn.removeAttr("disabled");
										clearInterval(intervalId);
									}
									if(j==0){
										//把之前的验证码置为无效
										$.ajax({url : ctx + "/wss_login!updateSmsState.do?ajax=yes",
											type : "POST",
											dataType : 'json',
											success : function(data) {
												$("#err_info").html(data.message);
											},
											error : function(a,b) {
												alert("出现错误 ，请重试");
											}
										});
									}
								},1000);
							}
						}
					},error : function(a,b) {
						alert(a+b);
					}
				});
			});
		}else{
//			var htmlText='<input type="text" id="valid_code" name="valid_code" class="login_ipt" style="width:100px;" />';
//			htmlText+='<img src="" width="94" height="30" id="code_img" />';
//			$("#checkTd").empty().append(htmlText);
//			//$("#code_img").attr("src","../validcode.do?vtype=admin&rmd="+new Date().getTime())
//			$("#code_img").click(function(){
//				$(this).attr("src","../validcode.do?vtype=admin&rmd="+new Date().getTime() );
//			});	
//			alert("=============ztp.js");
//			$("#code_img").trigger("click");
		}
	}
	,debug:function(msg){
		//$("#log").html( $("#log").html()+"<br/>" +msg);		
	}
	
	
 
};

String.prototype.trim = function (){
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
};

getIpPort = function(url){
	var reg = new RegExp("(http[s]?:\/\/)?")
	url = url.replace(reg, "");
	url = url.substring(0,url.indexOf('/'));
	return url;
}

function login_first(){
	  var url =ctx+"/core/admin/user/userAdmin!forwardBossPwdFirstJsp.do"; //addby wui修改，不能再动了
	  if(cur_app.themeSourceFrom == 'SHP' || cur_app.themeSourceFrom == "CMS"){
		  window.open(url,'_blank');
	  }else {
		  window.open(url);
	  }
}
function win_login_open(){
	  var width = screen.availWidth-3;
	  var height = screen.availHeight-20;
	  var left = -4;
	  var top = -4;
	  var url =ctx+"/mgWeb/main.jsp"; //addby wui修改，不能再动了
//	  if()
//	  {
////		  	window.location.href = url;
//////	  		window.close();
//		    var windowPar='toolbar=no,alwaysLowered=yes,status=no,location=no,titlebar=no,menubar=no, scrollbars=yes,resizable=yes,width='+width+',height='+height+',top=0,left=0';
//			  //add by hu.yi 清空登录界面的用户信息
//			  $("input[class='iptClass']").val("");
//			  var loginMainWin = window.open(url,"crmloginwindow",windowPar);
//	  }else 
	  if(cur_app.themeSourceFrom == 'SHP' || cur_app.themeSourceFrom == "CMS"){
		  window.open(url,'_blank');
	  }else {
		  var windowPar='toolbar=no,alwaysLowered=yes,status=no,location=no,titlebar=no,menubar=no, scrollbars=yes,resizable=yes,width='+width+',height='+height+',top=0,left=0';
		  //add by hu.yi 清空登录界面的用户信息
		  $("input[class='iptClass']").val("");
		  var loginMainWin = window.open(url,"crmloginwindow",windowPar);
	  }
}
$(function(){
	ZTP.LOGIN.init({
		success:function(){
		  //window.top.location.href ="main.jsp";
		  win_login_open();
		  $("#err_info").html("");
//		  var width = screen.availWidth-3;
//		  var height = screen.availHeight-20;
//		  var left = -4;
//		  var top = -4;
//		  var url ="main.jsp";
//		  var windowPar='toolbar=no,alwaysLowered=yes,status=no,location=no,titlebar=no,menubar=no, scrollbars=yes,resizable=yes,width='+width+',height='+height+',top=0,left=0';
//		  var loginMainWin = window.open(url,"crmloginwindow",windowPar);
//		  
//		  window.self.close();
		},
		fail:function(e){
			//alert(e);
			$("#err_info").html(e);
		}
		
	});
});