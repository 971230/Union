<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script>
<form method="post" id="serchform" action='<%=request.getContextPath()%>/shop/admin/ordAuto!getHeadquartersMallStaff.do'>
<div class="searchformDiv">
 <table>
	<tr>
	    <th>用户名：</th>
		<td><input type="text" class="ipttxt"  name="staffName"  id ="staffName"  value="${ staffName}"  /></td>
		

		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
	</tr>
 </table>
</div>
</form>
<div class="grid">
	<!--  <div class="toolbar">
		<ul>
			<li><a href="role!add.do">添加</a></li>
		</ul>
		<div style="clear:both"></div>
	</div>
	-->
	<div class="searchformDiv">
	<a href="ordAuto!addHeadquartersMallStaff.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
</div>
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell sort="name" width="15%">用户名</grid:cell> 
	<grid:cell sort="name" width="15%">用户名称</grid:cell> 
	<grid:cell sort="url">用户ID</grid:cell> 
	<grid:cell width="10%">描述</grid:cell>
	<grid:cell width="10%">状态</grid:cell>
	<grid:cell width="10%">操作</grid:cell> 
	</grid:header>

  <grid:body item="staff">
        <grid:cell>${staff.staff_code } </grid:cell>
        <grid:cell> ${staff.staff_name }</grid:cell>
        <grid:cell> ${staff.staff_id }</grid:cell> 
        <grid:cell> ${staff.remark }</grid:cell> 
        <grid:cell> 
        	<c:if test="${staff.status == '0' }">
        		未登录
        	</c:if>
        	<c:if test="${staff.status == '1' }">
        		已登录
        	</c:if>
        </grid:cell>
        
        <grid:cell>
         <a  href="javascript:void(0);" name="loginDig" staff_code="${staff.staff_code}" pwd="${staff.password}" >登录</a><span class='tdsper'></span>
        			&nbsp;&nbsp; 
         <a  href="ordAuto!getHeadquartersMallStaffDetail.do?headquartersMall.staff_code=${staff.staff_code}" >修改</a><span class='tdsper'></span>
 					&nbsp;&nbsp; 
		  <a href="ordAuto!deleteHeadquartersMallStaff.do?headquartersMall.staff_code=${staff.staff_code}" onclick="javascript:return confirm('确认删除 吗？删除后不可恢复');" >删除</a>
        </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>

<div id="loginDig">
<form method="post" id="saveCommentsForm" action="">
<table  border="0" cellpadding="0">
	<tr>
		<th>用户名：</th><td><input type="text" name="staff_code" id="staff_code" style="width:200px;margin-top:6px;" readonly="readonly" /></td>
	</tr>
	<tr>
		<th>密码：</th><td><input type="password" name="password" id="password" style="width:200px;margin-top:6px;" readonly="readonly" /></td>
	</tr>
	<tr>
		<th>验证码：</th><td><input type="text" name="message_code" id="message_code"  style="width:120px;margin-top:6px;"/><input type="button" id="sendMessage" class="graybtn1" style="width:80px" value="发送验证码" /></td>
	</tr>
	<tr>
		<th></th><td><div id="login-tips" class="login-tips" style="color: red;"></div></td>
	</tr>

</table>
	<input type="button" id="login" style="margin-left:120px;margin-top:6px;" class="graybtn1" value="登录" />
</form>
</div>

<script>
$(function(){
	var time;
	var isSendMsg = false;
	Eop.Dialog.init({id:"loginDig",modal:true,title:"登录",width:'400px'});
	$("a[name='loginDig']").click(function(){
		var staff_code= $(this).attr("staff_code");
		var password = $(this).attr("pwd");
		$("#staff_code").val(staff_code);;
		$("#message_code").val("");
		$("#password").val(password);
		$("#login-tips").text("");
		isSendMsg = false;
		openCommentsDlg();
		return false;
	});
	
	function openCommentsDlg(){
		$("#loginDig").load();
		Eop.Dialog.open("loginDig");
	}
	
	$("#sendMessage").click(function(){
		
		$.ajax({
			type : "post",
			async : false,
			url : ctx + "/shop/admin/ordAuto!sendHeadquartersMallMessage.do?ajax=yes",
			data : {
				staffName:$("#staff_code").val(),
				staffPassword:$("#password").val()
				},
			dataType : "json",
			success : function(data) {
				if(data.result == 0){
					$("#login-tips").text("验证码发送成功");
					isSendMsg = true;
				}else {
					$("#login-tips").text("验证码发送失败");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	});
	
	$("#login").click(function(){
		
		if($("#message_code").val() != ""){

			$("#login-tips").text("");
			var params = {};
			params.staffName = $("#staff_code").val();
			params.staffPassword = $("#password").val();
			params.messageCode = $("#message_code").val();
			$.ajax({
				type : "post",
				async : false,
				url : ctx + "/shop/admin/ordAuto!loginHeadquartersMallMessage.do?ajax=yes",
				data : params,
				dataType : "json",
				success : function(data) {
					if(data.result == 0){
						Eop.Dialog.close("loginDig");
						alert("登录成功");
						$("#searchBtn").click();
					}else {
						alert(data.msg);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

				}
			});
		
		}else {
			$("#login-tips").text("请输入验证码");
		}
			
	});
	
});
</script>