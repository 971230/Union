<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${title }</title>

<link rel="stylesheet" href="<%=request.getContextPath() %>/mgWebthemes/default/css/reset.css" type="text/css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/mgWebthemes/default/css/login.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/jquery-1.3.2.js"></script>
<script  src="<%=request.getContextPath()%>/publics/js/common/jquery-form-2.33.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/Ztp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/login/login.js"></script>

<script type="text/javascript">
 	var ctx = "<%=request.getContextPath()%>";
 	var source_from = '${source_from}';
</script>

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

</head>

<body>
<form id='crmloginwindow' > 
<div class="login_top"></div>
<div class="login_wrap">
	<div class="login_box">
    	<div class="login_table">
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    	    <tr>
    	      <th>用户名</th>
    	      <td><input type="text" id="username" name="username" value="${username}" class="login_ipt" style="width:260px;" /></td>
  	      	</tr>
    	    <tr>
    	      <th>密码</th>
    	      <td><input type="password" name="password" class="login_ipt" style="width:260px;" /></td>
  	      	</tr>
    	    <tr>
    	      <th>验证码</th>
    	      <td><input type="text" id="valid_code" name="valid_code" class="login_ipt" style="width:100px;" />
   	          <img src="" width="94" height="30" id="code_img" /></td>
  	      	</tr>
  	      	<tr style="display: none;">
    	      <th>系统</th>
    	      <td>
                  <div class="login_sel">
                    <select id="app_source_from">
                    </select>
                    <input type="hidden" value="" id="app_info" name="app_info" />
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
    	    <tr>
    	      <td colspan="2" style="text-align: center;"><a href="javascript:void(0);" name="login_btn" id="login_btn" class="loginbtn">登录</a></td>
  	      </tr>
  	    </table>
    	</div>
    </div>
</div>
</form>
</body>
</html>
