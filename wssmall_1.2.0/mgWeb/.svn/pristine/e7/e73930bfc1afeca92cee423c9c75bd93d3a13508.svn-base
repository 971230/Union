<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="/mgWebthemesECSORD/default/css/login_hn.css" type="text/css" />
<style>
	.ipttx{
		background:#FFFFFF;
    	border: 1px solid #CECECE;
    	color: #666666;
   	 	height: 30px;
   	 	width: 200px;
    	line-height: 22px;
   		padding-left: 3px;
	}
	.text{
		font-size:12pt;
	}
	.graybtn11{ background-color:#E2E8EB;display:inline-block; height:30px; line-height:30px; border:1px solid #d8d8d8;padding:0 8px 0 8px;  margin:0;font-size:15px; color:#444; text-align:center;margin-left:330px;}
	.graybtn11:hover{ background-color:#B0D3EE;}
</style>
<div class="login_top"></div>
<div style="margin-left:300px;font-size:15pt; height:30px;font-weight:bold">
首次登录，修改密码：(新密码不能包含 *、' 、-- 、<  字符)
</div> 
<div class="input">
<form  action="javascript:void(0)" class="validate" method="post" name="theForm" id="updatePwdForm" enctype="multipart/form-data">
<table  class="form-table" style="margin-left:390px;">

	<!--  <tr>
		<th><label class="text"><span class='red'>*</span>手机号码：</label></th>
		<td><input type="text" class="ipttxt"  name="bossPwdMap.serial_number"  dataType="string"  id ="phone" required="true" /></td>
	</tr>-->
	<tr>
		<th><label class="text"><span class='red'>*</span>原密码：</label></th>
		<td><input type="password" class="ipttx"  name="bossPwdMap.passwd"  dataType="string"  id ="oldPwd" required="true" /></td>
	</tr>
	<tr>
		<th><label class="text"><span class='red'>*</span>新密码：</label></th>
		<td><input type="password" class="ipttx"  name="bossPwdMap.staff_passwd"  dataType="string"  id ="newPwd" required="true" /></td>
	</tr>
	<tr>
		<th><label class="text"><span class='red'>*</span>确认密码：</label></th>
		<td><input type="password" class="ipttx"   dataType="string"  id ="qrPwd"  /></td>
	</tr>

</table>
  
  <div class="submitlist" align="center">
	 <table>
			<tr>
	<th></th>
	<td>
     <input  type="submit" id="btn"	 value=" 确    定   "  class="graybtn11"  /></td>

	</tr>
	 </table>
	</div>  


</form>
</div>
<script>var ctx ='${ctx}';</script>
<script>
<%
	AdminUser adminUser=ManagerUtils.getAdminUser();
	String username=adminUser.getUsername();
%>
</script>
<script>
$(function(){
     $("form.validate").validate();
	 $("#btn").bind("click",function(){
	    //$("form.validate").validate();
	  //  var phone  = $("#phone").val();
	    var oldPwd = $("#oldPwd").val();
	    var newPwd = $("#newPwd").val();
	    var qrPwd  = $("#qrPwd").val();
	/*    if(phone.length==11){
	        if (!(/^[0-9]+$/.test(phone))) {
	         alert("电话号码只能是11位的数字组成");
	         return false;
	         }
	    }else{
	       alert("电话号码只能是11位的数字组成");
	       return false;
	    }
	  */  
	    if(oldPwd.length==0){
	      alert("原密码不能为空");
	      return false;
	    }
	    if(newPwd.length==0){
	      alert("新密码不能为空");
	      return false;
	    }else if(!checkPwd(newPwd)){
	    	alert("新密码需要由8位以上的大写字母.小写字母.数字.特殊符号中的三项组成");
	    	return false;
	    }else if(!checkPwdIsContainUsername(newPwd)){
	    	alert("新密码不能包含用户名，包括大小写（全量）");
	    	return false;
	    }
	    if(newPwd.indexOf("*") != -1||newPwd.indexOf("--") != -1||newPwd.indexOf("<") != -1||newPwd.indexOf("'") != -1){
			  alert("新密码不能包含 *、' 、-- 、<  字符");
			  return false;
		  }
	    if(newPwd!=qrPwd){
	      alert("确认密码与新密码不一致");
	      return false;
	    }
		
		 var url = ctx + "/core/admin/user/userAdmin!updateBossPwd.do?ajax=yes";
         Cmp.ajaxSubmit('updatePwdForm', '', url, {},function callBack(reply){
	           if(reply.result==0){
	             alert(reply.message);
	             $("#oldPwd").val("");
	             $("#newPwd").val("");
	             $("#qrPwd").val("");
	           }
	           if(reply.result==1){
	             alert(reply.message);
	           }
	           if(reply.result==2){
	           	 alert(reply.message);
	           	 window.close();
	           }
         }, 'json');
     });
});
function checkPwd(pwd){
	var regex = new RegExp("^(?=.{8,}).*$","g");
	  if(regex.test(pwd)){
		  regex = new RegExp("^(?=.*[A-Z]).*$","g");
		  var index = 2;
		  if(!regex.test(pwd)){
			  index--;
		  }
		  regex = new RegExp("^(?=.*[a-z]).*$","g");
		  if(!regex.test(pwd)){
			  index--;
		  }
		  regex = new RegExp("^(?=.*[0-9]).*$","g");
		  if(!regex.test(pwd)){
			  index--;
		  }
		  regex = new RegExp("^(?=.*\\W).*$","g");
		  if(!regex.test(pwd)){
			  index--;
		  }
		  
		  if(index<=0){
			  //alert("新密码需要由6位以上的大写字母.小写字母.数字.特殊符号中的三项组成");
			  return false;
		  }else{
			  return true;
		  } 
	  }else{
		  return false;
	  }
}
function checkPwdIsContainUsername(pwd){
	var username = '<%=username%>';
	  if(pwd.toUpperCase().indexOf(username.toUpperCase())>=0){
	  	  return false;
	  }else{
		  return true;
	  }
}
</script>