<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>
  .clickClass{
     background:#f7f7f7
  }
</style>
<form method="post" id="roleserchform" >
 <div class="searchformDiv" style ='width:100%' >
 <table>
	<tr>
	    <th>角色编码：</th>
		<td><input type="text" class="ipttxt"  name="role_code"  id ="role_code" value="${role_code}"/></td>
		<th>角色名称：</th>
		<td><input type="text" class="ipttxt"  name="role_name"  id ="role_name" value="${role_name}"/></td>
		<th>角色类型：</th>
		<td>  
		      <select name ="auth_type" class="ipttxt" value="${auth_type}" style="width:150px;">
			     <option value="">---请选择---</option>
			     <option value="menu">菜单权限</option>
			     <option value="app">系统权限</option>
			     <option value="btn">按钮权限</option>
			     <option value="data">数据权限</option>
			  </select>
		</td>
		<script type="text/javascript">
			    $("[name='auth_type'] option[value='${auth_type}']").attr("selected","selected");
	     </script>
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="searchBtn" id="rolesearchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
    </tr>
  </table>
</div>

</form>
 <div class="grid"  >
     <form method="POST"  id="roleform" >
       <grid:grid from="rolePage"  formId="roleserchform" ajax="yes">
                    <grid:header>
                        <grid:cell>选择</grid:cell>
					  	<grid:cell>角色编码</grid:cell> 
					  	<grid:cell>角色名称</grid:cell> 
					  	<grid:cell>是否存在数据权限</grid:cell>
					</grid:header>
				    <grid:body item="obj">
					     <grid:cell><input type="checkbox" name="selRoleCheck" role_name="${obj.rolename}" value="${obj.roleid}"></grid:cell>
					     <grid:cell>${obj.roleid}</grid:cell>
					     <grid:cell>${obj.rolename}</grid:cell>
					     <grid:cell>
					        <c:if test="${obj.is_def>0}">
					                             存在
					        </c:if>
					        <c:if test="${obj.is_def==0}">
					                            不 存在
					        </c:if>
					     </grid:cell>
					</grid:body>  
				</grid:grid>
				 <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="roleSelInsureBtn">
		         </div>
</form>
</div>

<script type="text/javascript">
var roleList={
		 search:function(){
			 $("#rolesearchBtn").click(function(){
				
				 var role_name = $("#role_name").val();
				 var role_code = $("#role_code").val();
				 
				 var url= ctx + "/core/admin/user/userAdmin!roleList.do?ajax=yes";
				 Cmp.ajaxSubmit('roleserchform','selRoleDlg',url,{"role_name":role_name,"role_code":role_code},roleList.search);
				  
			 });
		 },
		 initFun:function(){
		    	roleList.search();
		    	roleList.checkRole();
		 },
			
			checkRole:function(){
				$("#roleSelInsureBtn").unbind("click").bind("click",function(){
					var arr = [];
					var arrLength = $("[name='roleids']").length;
					
					$("[name='selRoleCheck']:checked").each(function(){
						var count = 0;
						var roleid = $(this).val();
						var name = $(this).attr("role_name");
						
						   if(arrLength>0){
							    $("[name='roleids']").each(function(){
							    	if(roleid==$(this).val()){
										count+=1;
									}
							    });
						   }
							
						if(count==0){
							$("#roleBody").append(roleList.appendHtmlStr(roleid,name));
						}
					});
					roleList.removeTr();
					Eop.Dialog.close();
				});
				
			},
			appendHtmlStr:function(roleid,name){
				var htmlStr =   "<tr id='tr_"+roleid+"'>"+
								" <input type='hidden' value='"+roleid+"' name=roleids>"+
								"<td style='text-align:center'>"+roleid+"</td>"+
								"<td style='text-align:center'>"+name+"</td>"+
								"<td style='text-align:center'>"+
								" <a title ='删除' href='javascript:;' class='p_prted' roleid='"+roleid+"' name=removeRole>删除</a>"+
								"</td>"+
								"</tr>";
				return htmlStr;
			},
			removeTr:function(){
				 $("[name='removeRole']").each(function(){
					 $(this).unbind("click").bind("click",function(){
						 var roleid = $(this).attr("roleid");
						  $("#tr_"+roleid).remove();
					 });
				   });
			}
};
 $(function(){
	 roleList.initFun();
 });


</script>
