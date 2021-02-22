<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>分销商登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="image/x-icon" href="<%=request.getContextPath() %>/publics/user/1/1/attachment/user/tebuy_logo.jpg" rel="icon" />
<link type="image/x-icon" href="<%=request.getContextPath() %>/publics/user/1/1/attachment/user/tebuy_logo.jpg" rel="bookmark" />
<script type="text/javascript" src="/<%=request.getContextPath() %>publics/js/common/jquery-1.3.2.js"></script>
<link href="<%=request.getContextPath() %>/mgWebthemes/default/css/login.css" rel="stylesheet" type="text/css" />

</head>
<body style='overflow:hidden;border:0px solid red;'>
<div class="login_div">
	<div class="login_left"></div>
	<div class="login_right">
    	<div class="login_box">
    	<form id='crmloginwindow' > 
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	    <tr>
    	      <th>用户名：</th>
    	      <td>
           	  <div class="iptdiv" style="width:80%;"  >
                	<input type="text" class="iptClass" id="username" name="username" value="${username}" attr='input_div'  onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"   >
                	<div class="ipt_left"></div>
                    <div class="ipt_right"></div>
                </div>
              </td>
  	      </tr>
    	    <tr>
    	      <th>密  码：</th>
    	      <td>
           	  <div class="iptdiv" style="width:80%;" >
                	<input type="password" id="password" name="password" class="iptClass" attr='input_div' />
                	<div class="ipt_left"></div>
                    <div class="ipt_right"></div>
                </div>
              </td>
  	      </tr>
    	    <tr>
    	      <th>验证码：</th>
    	      <td>
       	  		<div class="iptdiv" style="width:40%;" >
                	<input type="text"  id="valid_code" name="valid_code" class="iptClass"
                	 attr='input_div' onkeyup="value=value.replace(/[^\w]/ig,'')"/>
                	<div class="ipt_left"></div>
                    <div class="ipt_right"></div>
                </div>
                <div class="yzmdiv" style="width: 80xp;">
                	<input type="button" id="apply_sms_code" value="获取短信验证码"/>
                </div>
           	  </td>
  	      </tr>
  	       <tr>
    	      <td colspan="2" id='err_info' style='color:red;font-size:1.3em;font-weight:bold;text-align:center;'></td>
    	  </tr>
  	    </table>
        <div class="iptbtnDiv"><img src="<%=request.getContextPath() %>/mgWebthemes/default/images/loginbtn.jpg" width="106" height="35"  name="login_btn" id="login_btn" style='cursor:hand;border:none;border:0px;' /></div>
   	 	</form>
   	  </div>
  </div>
</div>
<script>
$(function(){
	
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
	
	$("#login_btn").bind("click",function(){
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
	});
});
</script>
</body>
</html>
