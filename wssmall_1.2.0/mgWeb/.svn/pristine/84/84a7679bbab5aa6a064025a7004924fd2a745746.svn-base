<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input" style="align:left;">
<form  action="javaScript:;" class="validate"  method="post" name="selRoleForm" id="selRoleForm" enctype="multipart/form-data">
    <input type="hidden" name="adminUser.userid" value="${id}" id="roleUserid">
    <input type="hidden" name="adminUser.state" value="${userState}" id="userState">
    <table class="form-table" style="align:left;">
		        <!-- 一级分销商 查看自己时候 不显示角色· -->
					<c:if test="${roleList !=null && fn:length(roleList) > 0 }">
					<tr id="selroletr">
						<th><label class="text"><span class='red'>*</span>人员角色：</label></th>
						<td>
							<ul style="width:100%" id="selrolesbox">
								<c:forEach var="role" items="${roleList }">
					<li style="width:33%;display:block"><input type="checkbox" name="selroleids" rolename="${role.rolename }" id="${role.roleid}" value="${role.roleid }"  />
					${role.rolename }&nbsp;</li>
									</c:forEach>
								</ul> 
							</td>		
						</tr>
					</c:if>
		  </table>
		  
		   <div class="submitlist" align="center">
			 <table>
			 <tr>
			 <th></th>
			 <td >
			  <input  type="button"	id="saveRoleBtn"  value="保存角色" class="submitBtn" />
			  
			   </td>
			   </tr>
			 </table>
		   </div>
</form>
</div>
<script>
 <c:forEach var="userRole" items="${userRoles }">
		$("#selroletr input[value='${userRole.roleid}']").attr("checked",true);
 </c:forEach>
 
 $("#saveRoleBtn").unbind("click").click(function(){
 
      if($("input[name='selroleids']:checked").length==0){
		  alert("请至少选择一个角色");
		    return false;
		}
    var id =$("#roleUserid").val();
    
    var url=app_path+'/core/admin/user/userAdmin!saveRoles.do?ajax=yes&id='+id;
    Cmp.ajaxSubmit('selRoleForm', '', url, {},function replyBack(reply){
        if(reply.result==0){
          alert("操作成功!");
          $("#rolesbox").empty();
          $("input[name='selroleids']:checked").each(function(){
                var roleid = $(this).val();
                var rolename =$(this).attr("rolename");
                var htmlStr ="<li style='width:33%;display:block'><input type='hidden' name='roleids' id='"+roleid+"' value='"+roleid+"'/>"+
					rolename+"&nbsp;</li>";
					$("#rolesbox").append(htmlStr);	
						
          });
          Eop.Dialog.close("selRoleDlg");
        }else{
          alert(result.message);
        }
    }, 'json');
 });
</script>