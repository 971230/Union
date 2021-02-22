<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分销商登录</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/mgWebthemes/default/css/reset_hn.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/mgWebthemes/default/css/login_hn.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/common/jquery-1.3.2.js"></script>

<script>

function login(uname,pwd,code){
	$.ajax({url : "login!login.do?ajax=yes",
		type : "POST",
		data:{staff_code:uname,password:pwd,sms_code:code},
		dataType : 'json',
		success : function(data) {
			if(0==data.status){
				var width = screen.availWidth-3;
				var height = screen.availHeight-20;
				var left = -4;
				var top = -4;
				var url ="<%=request.getContextPath() %>/mgWeb/main.jsp";
				var windowPar='toolbar=no,alwaysLowered=yes,status=no,location=no,titlebar=no,menubar=no, scrollbars=yes,resizable=yes,width='+width+',height='+height+',top=0,left=0';
				var loginMainWin = window.open(url,"crmloginwindow",windowPar);
			}else{
				$("#err_info").html(data.msg);
			}
		},error : function(a,b) {
			alert(a+b);
		}
	});
}

function loginSubmit(){
	var uname = $("#username").val();
	var pwd = $("#password").val();
	var code = $("#valid_code").val();
	if(!uname){
		$("#err_info").html("请输入用户名");
		return ;
	}
	if(!pwd){
		$("#err_info").html("请输入密码");
		return ;
	}
	if(!code){
		$("#err_info").html("请输入验证码");
		return ;
	}
	$.ajax({url : "login!checkIsLogin.do?ajax=yes",
		type : "POST",
		data:{staff_code:uname,password:pwd},
		dataType : 'json',
		success : function(data) {
			if(data.status==1){
				if(window.confirm(uname+"用户已经在另一地点登录，如继续登录系统强制下线另一地点登录账户")){
					login(uname,pwd,code);
				}else{
					return ;
				}
			}else if(data.status==2){
				alert(data.msg);
			}else{
				login(uname,pwd,code);
			}
		},error : function(a,b) {
			alert(a+b);
		}
	});
}

$(function(){
	$(document).keydown(function(event){
		if(event.keyCode==13){
			loginSubmit();
		}
	});
	
	$("#apply_sms_code").bind("click",function(){
		var uname = $("#username").val();
		var pwd = $("#password").val();
		if(!uname){
			$("#err_info").html("请输入用户名");
			return ;
		}
		if(!pwd){
			$("#err_info").html("请输入密码");
			return ;
		}
		$.ajax({url : "login!applySmsCode.do?ajax=yes",
			type : "POST",
			data:{staff_code:uname,password:pwd},
			dataType : 'json',
			success : function(data) {
				$("#err_info").html(data.msg);
			},error : function(a,b) {
				alert(a+b);
			}
		});
	});
	
	$("#login_btn").bind("click",loginSubmit);
});
</script>
</head>

<body>
<div class="login_top"></div>
<div class="login_wrap">
	<div class="login_box">
    	<div class="login_table">
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	    <tr>
    	      <th>用户名</th>
    	      <td><input type="text" id="username" name="username" value="${username}" attr='input_div'  onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" class="login_ipt" style="width:260px;" /></td>
  	      	</tr>
    	    <tr>
    	      <th>密码</th>
    	      <td><input type="password" id="password" name="password" attr='input_div' class="login_ipt" style="width:260px;" /></td>
  	      	</tr>
    	    <tr>
    	      <th>验证码</th>
    	      <td><input type="text" id="valid_code" name="valid_code" class="login_ipt" style="width:100px;" />
   	          <a id="apply_sms_code" href="javascript:void(0);" class="txt">获取短信验证码</a></td>
  	      	</tr>
  	      	<tr>
    	      <td colspan="2" id='err_info' style='color:red;font-size:1.3em;font-weight:bold;text-align:center;'></td>
    	    </tr>
    	    <tr>
    	      <td colspan="2" style="text-align: center;"><a href="javascript:void(0);" name="login_btn" id="login_btn" class="loginbtn">登录</a></td>
  	      </tr>
  	    </table>
    	</div>
    </div>
</div>
</body>
</html>