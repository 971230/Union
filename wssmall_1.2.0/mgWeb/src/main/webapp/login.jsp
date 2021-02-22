<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--  %response.sendRedirect(request.getContextPath()+ "/member_login.html");%-->
<html>
<head>
<script type="text/javascript" src="${staticserver }/js/common/jquery-1.3.2.js"></script>
<SCRIPT  src="${staticserver }/js/common/jquery-form-2.33.js" type="text/javascript"></SCRIPT>
<style>
body{TEXT-ALIGN: center;}
#center{ MARGIN-RIGHT: auto;
MARGIN-LEFT: auto;
height:200px;
width:400px;
vertical-align:middle;
line-height:200px;
}
</style>

</head>
<body>
   <div id="center">

     <span><font size="5">登录帐号不存在或验证码已过期</font></span><br>
     <div id="login_error" style="color:red;font-size: 200%;">&nbsp;</div>
     <div id="login_uri" style="color:red;font-size: 200%;"><a href="../tebai/member_login.html">手动登录百特商城</a></div>
   </div>
   <script type="text/javascript">
   
    var url ="../tebai/member_login.html";
    var wait = 3; //设置秒数(单位秒) 
    function openPage() {
	    for(var i=1;i<=wait;i++) 
		{ 
		 window.setTimeout("sTimer("+i+")",i*1000); 
		}
	}
    function sTimer(num) 
	{ 
		if(num==wait) {
		   location.href = url;
		} else { 
		   secs=wait-num; 
		   $("#login_error").text(secs + "秒之后自动跳转到登录页面");
		} 
	} 
	window.onload(openPage());
   </script>
</body>
</html>