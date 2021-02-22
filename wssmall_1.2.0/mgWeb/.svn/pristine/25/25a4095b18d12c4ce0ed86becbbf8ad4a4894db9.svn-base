<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	.graybtn11{ background-color:#E2E8EB;display:inline-block; height:30px; line-height:30px; border:1px solid #d8d8d8;padding:0 8px 0 8px;  margin:0;font-size:15px; color:#444; text-align:center;margin-left:400px;}
	.graybtn11:hover{ background-color:#B0D3EE;}
</style>
<div class="login_top"></div>
<div style="margin-left:300px;font-size:15pt; height:30px;font-weight:bold">
请输入您要重置密码的账号：
</div>   
<div class="input">
<form  action="javascript:void(0)" class="validate" method="post" name="theForm" id="resetPwdForm" enctype="multipart/form-data">
<table  class="form-table" style="margin-left:450px;">
		<input type="hidden" class="ipttxt"  name="bossPwdMap.flag"  dataType="string"  id ="reset" value="reset" required="true" />
	<tr>
		<th><label class="text"><span class='red'>*</span>用户名：</label></th>
		<td><input type="text" class="ipttx"  name="bossPwdMap.username"  dataType="string"  id ="username" required="true" /></td>
	</tr>
	<tr>
		<th><label class="text"><span class='red'>*</span>手机号码：</label></th>
		<td><input type="text" class="ipttx"  name="bossPwdMap.phone"  dataType="string"  id ="phone" required="true" /></td>
	</tr>

</table>
  
  <div class="submitlist" align="center">
	 <table>
			<tr>
	<th></th>
	<td style="color:#444;">
     <input  type="submit" id="btn"	 value=" 确    定   " class="graybtn11"  />
     </td>

	</tr>
	 </table>
	</div>  


</form>
</div>
<script>
	var ctx ='${ctx}';
</script>
<script>
$(function(){
     $("form.validate").validate();
	 $("#btn").bind("click",function(){
	    var phone  = $("#phone").val();
	    var username = $("#username").val();
	    var flag = $("#flag").val();
	    var bossPwdMap = {username:username,phone:phone,flag:flag};
	     if(username.length==0){
	      alert("用户名不能为空");
	      return false;
	    }
	    if(phone.length==0){
	      alert("电话号码不能为空");
	      return false;
	    }
	    if(phone.length==11){
	        if (!(/^[0-9]+$/.test(phone))) {
	         alert("电话号码只能是11位的数字组成");
	         return false;
	         }
	    }else{
	       alert("电话号码只能是11位的数字组成");
	       return false;
	    }
		
		 var url = ctx + "/core/admin/user/userAdmin!resetBossPwd.do?ajax=yes";
		 Cmp.ajaxSubmit('resetPwdForm', '', url, {},function callBack(reply){
	         if(reply.result==1){
	             alert(reply.message);
	           }
	           if(reply.result==0){
	           	 alert(reply.message);
	           	 window.close();
	           }
         }, 'json');
     });
});
</script>