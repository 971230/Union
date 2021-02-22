<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${title }</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="image/x-icon" href="${ico}" rel="icon" />
<link type="image/x-icon" href="${ico}" rel="bookmark" />
    <script type="text/javascript">
        var ctx = "<%=request.getContextPath()%>";
        var source_from = '${source_from}';
    </script>
<script type="text/javascript" src="${staticserver }/js/common/jquery-1.3.2.js"></script>
<SCRIPT  src="${staticserver }/js/common/jquery-form-2.33.js" type="text/javascript"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/Ztp.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/login/login.js"></script>
<link href="${context}/css/login.css" rel="stylesheet" type="text/css" />
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
  	      <tr style="display: none;">
	   	  	<th>系统：</th>
	   	    <td>
               <div class="iptdiv" style="width:80%;padding-right:0px;" >
                 <select id="app_source_from">
                 	
                 </select>
                 <input type="hidden" value="" id="app_info" name="app_info" />
                 <div class="ipt_left"></div>
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
<script>
$(function(){
	var bkloginpicfile = '${bkloginpicfile}';
	if(bkloginpicfile!=''){
		$(".logo").css("background","transparent url(${bkloginpicfile}) no-repeat scroll 0 0");
	}
	if($("#username").val()){$("#psdinput").focus();}
});
</script>
</body>
</html>
