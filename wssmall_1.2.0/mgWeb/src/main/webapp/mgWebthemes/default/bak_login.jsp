<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${title }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="image/x-icon" href="${ico}" rel="icon" />
<link type="image/x-icon" href="${ico}" rel="bookmark" />
<script type="text/javascript" src="${staticserver }/js/common/jquery-1.3.2.js"></script>
<SCRIPT  src="${staticserver }/js/common/jquery-form-2.33.js" type="text/javascript"></SCRIPT>
<script type="text/javascript" src="${staticserver }/js/admin/Ztp.js"></script>
<link href="${context}/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
 	var ctx = "<%=request.getContextPath()%>";
</script>
</head>
<body style='overflow:hidden;border:0px solid red;'>
<div id="login_div" class="login_div">
	<div class="login_left"></div>
	<div class="login_right">
    	<div class="login_box">
    	<form id='crmloginwindow' > 
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	  <tr>
    	  	<td colspan="2" style="text-align: center;display: none;">
    	  		<a href="javascript:void(0);" name="admin_login_a">管理员登录</a>
    	  		<a href="javascript:void(0);" name="partner_login_a">分销商登录</a>
    	  	</td>
    	  </tr>
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
                	<input type="password" name="password" class="iptClass" attr='input_div' />
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
                <div class="yzmdiv"><img  width="53" height="26"  id="code_img" class="code_img" />
                </div>
           	  </td>
  	      </tr>
  	      <script>
  	      	$(function(){
  	      		$("[attr='input_div']").bind("focus",function(){
  	      				$("[attr='input_div']").closest("div").removeClass("iptdivfoucs").addClass("iptdiv");
  	      				$(this).closest("div").addClass("iptdivfoucs");
  	      		})
  	      		
  	      		$("[attr='input_div']").bind("blur",function(){
  	      				$(this).closest("div").removeClass("iptdivfoucs");
  	      			    $(this).closest("div").addClass("iptdiv");
  	      		})
  	      	})
  	      </script>
  	       <tr>
    	      <td colspan="2" id='err_info' style='color:red;font-size:1.3em;font-weight:bold;text-align:center;'></td>
    	  </tr>
  	    </table>
        <div class="iptbtnDiv"><img src="${context}/images/loginbtn.jpg" width="106" height="35"  name="login_btn" id="login_btn" style='cursor:hand;border:none;border:0px;' /></div>
   	 	</form>
   	  </div>
  </div>
</div>


<div id="login_partner_div" class="login_div" style="display: none;">
	<div class="login_left"></div>
	<div class="login_right">
    	<div class="login_box">
    	<form id='crmloginwindow' > 
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	  <tr>
    	  	<td colspan="2" style="text-align: center;display: none;">
    	  		<a href="javascript:void(0);" name="admin_login_a">管理员登录</a>
    	  		<a href="javascript:void(0);" name="partner_login_a">分销商登录</a>
    	  	</td>
    	  </tr>
    	    <tr>
    	      <th>用户名：</th>
    	      <td>
           	  <div class="iptdiv" style="width:80%;"  >
                	<input type="text" class="iptClass" id="username_p" name="username_p" value="${username}" attr='input_div'  onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"   >
                	<div class="ipt_left"></div>
                    <div class="ipt_right"></div>
                </div>
              </td>
  	      </tr>
    	    <tr>
    	      <th>密  码：</th>
    	      <td>
           	  <div class="iptdiv" style="width:80%;" >
                	<input type="password" id="password_p" name="password_p" class="iptClass" attr='input_div' />
                	<div class="ipt_left"></div>
                    <div class="ipt_right"></div>
                </div>
              </td>
  	      </tr>
    	    <tr>
    	      <th>验证码：</th>
    	      <td>
       	  		<div class="iptdiv" style="width:40%;" >
                	<input type="text"  id="valid_code_p" name="valid_code_p" class="iptClass"
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
    	      <td colspan="2" id='err_info_p' style='color:red;font-size:1.3em;font-weight:bold;text-align:center;'></td>
    	  </tr>
  	    </table>
        <div class="iptbtnDiv"><img src="<%=request.getContextPath() %>/mgWebthemes/default/images/loginbtn.jpg" width="106" height="35"  name="login_btn_p" id="login_btn_p" style='cursor:hand;border:none;border:0px;' /></div>
   	 	</form>
   	  </div>
  </div>
</div>

<script>
$(function(){
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
		$(".logo").css("background","transparent url(${bkloginpicfile}) no-repeat scroll 0 0");
	}
	if($("#username").val()){$("#psdinput").focus();}
	
	
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
		$.ajax({url : ctx+"/login!login.do?ajax=yes",
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
</body>
</html>
