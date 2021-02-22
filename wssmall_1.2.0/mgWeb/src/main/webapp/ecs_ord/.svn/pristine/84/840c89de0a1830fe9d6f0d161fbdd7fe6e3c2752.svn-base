<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />


<!-- 选择工号页面 -->
<div class="searchformDiv">
<form method="post" id="AdminUserForm" action="">
<table>
	<tr>
	    <th>工号：</th>
		<td><input type="text" class="ipttxt" style="width:200px;" name="adminUser.userid"  id ="adminUser.userid" value = "${adminUser.userid}"/></td>
	    <th>姓名：</th>
		<td><input type="text" class="ipttxt" style="width:200px;" name="adminUser.realname"  id ="adminUser.realname" value = "${adminUser.realname}"/></td>
		<th></th>
		<td>
	    <input class="comBtn"  type="button" name="searchAdminUserBtn" id="searchAdminUserBtn" style="width:90px;" value="查询"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           style ="margin-                             right:10px;"/>
		</td>
		<th></th>
		<td>
	    <input class="comBtn"  type="button" name="sure" id="sure" style="width:90px;" value="确定"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           style ="margin-                             right:10px;"/>
		</td>
	</tr>
 </table>
</form>
</div>
<div class="grid">
<grid:grid  from="webpage" ajax="yes" formId ="AdminUserForm">
	<grid:header>
	<grid:cell width="10%"><input type="checkbox" name= "checkbox_all" ></grid:cell> 
	<grid:cell width="40%">工号</grid:cell> 
	<grid:cell width="50%">姓名</grid:cell> 
	</grid:header>
  <grid:body item="adminUserObj">
        <grid:cell>
        <input type="checkbox" name="adminUser_checkbox" user_id= "${adminUserObj.userid}" real_name = "${adminUserObj.realname}">
        </grid:cell>
        <grid:cell>${adminUserObj.userid}</grid:cell>
        <grid:cell>${adminUserObj.realname}</grid:cell> 
  </grid:body>  
</grid:grid>  
</div>
<script>
$(function() {
	$("#sure").click(function(){
		if($("input[type='checkbox'][name='adminUser_checkbox']:checked").size() == 0){
			alert("未选中");
			return ;
		}  
		var userIds = "";
		var userNames = "";
		$("input[type='checkbox'][name='adminUser_checkbox']:checked").each(function(){
			var user_id = $(this).attr("user_id");
			var real_name = $(this).attr("real_name");
			userNames += real_name+",";
			userIds += user_id + ",";
		})
		var method='setAdminUser';
		eval(method+"('"+userIds+"','"+ userNames  + "')");
		Eop.Dialog.close('open_choose_admin_user_window'); 
	});
	$("#searchAdminUserBtn").click(function(){
		var url=ctx+"/shop/admin/workerPoolAction!workerList.do?ajax=yes";
		Cmp.ajaxSubmit('AdminUserForm','open_choose_admin_user_window',url,{},function(){
			
		});
	})
	//解除全选
	$("input[name='adminUser_checkbox']").unbind().bind("click", function() {
		var flag = '0';
		$("input[name='adminUser_checkbox']").each(function(){
			if($(this).is(':checked')){
				
			}else{
				flag = '1';
				return false;
			}
		});
		if(flag == '1'){
			$("input[name='checkbox_all']").attr("checked", false);
		}
	});	
});
function setAdminUser (userIds,userNames){
	$("#add_realname").val(userNames);
	$("#add_userid").val(userIds);
}

</script>