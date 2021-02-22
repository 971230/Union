<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head lang="en">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${title }</title>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>${themes}/default/css/zj_style.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/jquery-1.3.2.js"></script>
	<script  src="<%=request.getContextPath()%>/publics/js/common/jquery-form-2.33.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath()%>/publics/js/common/desencrypt/tripledes.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath()%>/publics/js/common/desencrypt/cipher-core.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath()%>/publics/js/common/desencrypt/core.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath()%>/publics/js/common/desencrypt/mode-ecb.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath()%>/publics/js/common/desencrypt/md5.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>${themes}/default/js/Ztp.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/login/login.js"></script>
	<script type="text/javascript">
	 	var ctx = "<%=request.getContextPath()%>";
	 	var source_from = '${source_from}';
	 	var db_source_from = '${db_source_from}';
	 	var checkType = '${checkType}';
		var desencrptKey = '${desencrptKey}';
		$(function(){
			$("#loss_pwd").bind("click",function(){
	      	  	var url =ctx+"/core/admin/user/userAdmin!forwardResetBossPwdJsp.do?prov=zj"; //addby wui修改，不能再动了
				window.open(url);
			});
			$("a[name='admin_login_a']").bind("click",function(){
				$("#login_div").show();
				$("#login_partner_div").hide();
			});
			$("a[name='partner_login_a']").bind("click",function(){
				$("#login_div").hide();
				$("#login_partner_div").show();
			});
			
			var bkloginpicfile = '${bkloginpicfile}';
			if(bkloginpicfile!=''){
				//$(".logo").css("background","transparent url(${bkloginpicfile}) no-repeat scroll 0 0");
			}
			if(!$("#username").val()){
				//$("#psdinput").focus();
				$("#username").focus();
			}
			
			
			$("#apply_sms_code").bind("click",function(){
				var uname = $("#username_p").val();
				var pwd = $("#password_p").val();
				if(!uname){
					$("#err_info_p").html("请输入用户名");
					return ;
				}
				if(!pwd){
					$("#err_info_p").html("请输入密码");
					return ;
				}
				$.ajax({url : ctx+"/login!applySmsCode.do?ajax=yes",
					type : "POST",
					data:{staff_code:uname,password:pwd},
					dataType : 'json',
					success : function(data) {
						$("#err_info_p").html(data.msg);
					},error : function(a,b) {
						alert(a+b);
					}
				});
			});
			
			$("#login_btn_p").bind("click",function(){
				var uname = $("#username_p").val();
				var pwd = $("#password_p").val();
				var code = $("#valid_code_p").val();
				if(!uname){
					$("#err_info_p").html("请输入用户名");
					return ;
				}
				if(!pwd){
					$("#err_info_p").html("请输入密码");
					return ;
				}
				if(!code){
					$("#err_info_p").html("请输入验证码");
					return ;
				}
				$.ajax({url : ctx+"/wss_login!login.do?ajax=yes",
					type : "POST",
					data:{staff_code:uname,password:pwd,sms_code:code},
					dataType : 'json',
					success : function(data) {
						if(0==data.status){
							var width = screen.availWidth-3;
							var height = screen.availHeight-20;
							var left = -4;
							var top = -4;
							var url =ctx+"/mgWeb/main.jsp";
							alert(url)
							var windowPar='toolbar=no,alwaysLowered=yes,status=no,location=no,titlebar=no,menubar=no, scrollbars=yes,resizable=yes,width='+width+',height='+height+',top=0,left=0';
							var loginMainWin = window.open(url,"crmloginwindow",windowPar);
						}else{
							$("#err_info_p").html(data.msg);
						}
					},error : function(a,b) {
						alert(a+b);
					}
				});
			});
		});
	</script>
	<style type="text/css">
	a{
		color:black;
	}
	a:hover {
		text-decoration: underline;
		 color:red;
	}
	</style>
</head>
<body class="login-bg">
<form id='crmloginwindow' > 
    <div class="login-wrap">
        <div class="login-head clear-fix">
            <div class="login-logo"><img src="<%=request.getContextPath()%>${themes}/default/images/zj_logo.png" alt=""></div>
            <div class="poj-name">${title }</div>
        </div>
        
        <div class="login-main clear-fix">
            <div class="login-content">
                <div class="login-title">欢迎登录</div>
                <ul class="login-list">
                    <li class="login-item">
                        <div class="login-text-label login-ico-label">
                            <label>
                                <i class="login-user-ico"></i>
                                <input id="username" name="username" value="${username}" type="text" class="login-inp-text" placeholder="请输入您的用户名">
                            </label>
                        </div>
                    </li>
                    <li class="login-item">
                        <div class="login-text-label login-ico-label">
                            <label>
                                <i class="login-psw-ico"></i>
                                <input id="password" name="password" type="password" class="login-inp-text" placeholder="请输入您的密码">
                                <input type="hidden" id="CSRFToken" name="CSRFToken" value="${CSRFToken}"/>
                            </label>
                        </div>
                    </li>
                    <!-- <li class="login-item">
                        <div class="login-text-label login-code-label"">
                            <label>
                                <input id="valid_code" name="valid_code" type="text" class="login-inp-text" placeholder="请输入短信验证码">
                            </label>
                            <button id="getValidCodeBtn" name="getValidCodeBtn" class="login-code-btn" type="button"><span class="time-txt"></span>获取验证码</button>
                        </div>
                    </li> -->
                    <li class="login-item">
                        <label class="login-text-label login-full-label">
                            <select id="app_source_from" class="login-inp-select" ></select>
			                <input type="hidden" value="" id="app_info" name="app_info" />
                        </label>
                    </li>
                </ul>
                <div id="login-tips" class="login-tips"></div>
                <div class="login-bottom">
                    <button name="login_btn" id="login_btn" class="login-submit-btn" type="button">登录</button>
                </div>
                <div style="width:300px;padding-right:5px;padding-top:10px;text-align: right;">
              	 	<a href="javascript:void(0);"  name="loss_pwd" id="loss_pwd">忘记密码？</a>
                </div>
            </div>
        </div>

    </div>

</form>
</body>
</html>