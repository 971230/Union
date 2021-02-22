<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script>
<form method="post" id="serchform" action='<%=request.getContextPath()%>/core/admin/role!list.do'>
<div class="searchformDiv">
 <table>
	<tr>
	    <th>角色编码：</th>
		<td><input type="text" class="ipttxt"  name="roleid"  id ="roleid"  value="${roleid}"  /></td>
		<th>角色名称：</th>
		<td><input type="text" class="ipttxt"  name="rolename"  value="${rolename}"/></td>
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
		<th>角色分组：</th>
		<td>  
		      <select name ="role_group" class="ipttxt" value="${auth_type}" style="width:150px;">
			     <option value="">---请选择---</option>
			     <c:forEach var="role" items="${roleGroupListMap}">			     
			     <option value="${role.pkey}">${role.pname}</option>
			     </c:forEach>
			  </select>
		</td>
		 <script type="text/javascript">
			    $("[name='auth_type'] option[value='${auth_type}']").attr("selected","selected");
	     </script>
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
	<a href="role!add.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
</div>
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell sort="name" width="15%">编码</grid:cell> 
	<grid:cell sort="name" width="15%">角色名称</grid:cell> 
	<grid:cell sort="url">描述</grid:cell> 
	<grid:cell width="10%">创建类型</grid:cell>
	<grid:cell sort="url" width="15%">创建时间</grid:cell> 
	<grid:cell width="10%">操作</grid:cell> 
	</grid:header>

  <grid:body item="role">
        <grid:cell>${role.roleid } </grid:cell>
        <grid:cell>${role.rolename } </grid:cell>
        <grid:cell>${role.rolememo} </grid:cell> 
        <grid:cell>
           <c:if test="${role.createuserid==currUserId}">自建</c:if>
           <c:if test="${role.createuserid!=currUserId}">上级分配</c:if>
        </grid:cell>
        <grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${role.create_time}"></html:dateformat></grid:cell>
        <grid:cell>
         <c:if test="${role.createuserid==currUserId || currUserId==1}">
         <a  href="role!edit.do?roleid=${role.roleid }" >修改</a><span class='tdsper'></span>
 					&nbsp;&nbsp; 
		  <a  href="role!delete.do?roleid=${role.roleid }" onclick="javascript:return confirm('确认删除此角色吗？删除后不可恢复');" >删除</a> </c:if>
        	<!-- add by wui 缺省权限只读 -->
       	<!-- 	<c:if test="${role.is_def =='yes'}">
       			<a  href="javascript:void(0);" name='view_role' roleid = "${role.roleid}" >查看</a>
       		</c:if>
       		<c:if test="${role.is_def !='yes'}">
       				 <a  href="role!edit.do?roleid=${role.roleid }" >修改</a> 
 					&nbsp;&nbsp;<a  href="role!delete.do?roleid=${role.roleid }" onclick="javascript:return confirm('确认删除此角色吗？删除后不可恢复');" >删除</a> 
       		</c:if>
       	 -->
        </grid:cell> 
  </grid:body>  
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>

<div id="actionDlg"></div>

<script>
	$(function(){
		Eop.Dialog.init({id : "actionDlg",modal : true,title : "角色信息",width : "750px"});
		$("[name='view_role']").bind("click",function(){
				//Eop.Dialog.open("actionDlg");
				var role_id =$(this).attr("roleid");
				
				AuthAction.viewAuth(role_id);
				
				
				//$("#actionDlg").load('auth!edit.do?ajax=yes&role_id=' + role_id,function() {});
		})
	})
</script>