<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="usersMenuSearchForm" action='usersMenuAction!listUsersMenu.do'>
 <div class="searchformDiv">
 <table>
	<tr>
		<th>菜单名称：</th>
		<td><input type="text" class="ipttxt"  name="menu_name"  value="${menu_name}"/></td>
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
   </tr>
  </table>
 </div>
</form>
<div class="grid" >
 <div class="comBtnDiv">
	<a href="usersMenuAction!add.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
</div>
	<form method="post" action="" id="usersMenuForm"></form>

              <form id="form_tc" class="grid">
				<grid:grid  from="webpage"   formId="usersMenuSearchForm">
					<grid:header>
					  	<grid:cell>菜单名称</grid:cell> 
					  	<grid:cell>用户帐号</grid:cell>
						<grid:cell>菜单路径</grid:cell>
						<grid:cell>菜单排序</grid:cell>
						<grid:cell>创建时间</grid:cell>
						<grid:cell>操作</grid:cell>
					</grid:header>
				    <grid:body item="obj">
					     <grid:cell width="20%">${obj.menu_name}</grid:cell>
					     <grid:cell width="20%">${obj.user_id}</grid:cell>
					     <grid:cell width="30%">${obj.menu_url}</grid:cell>
					    <grid:cell  width="10%">${obj.sort}</grid:cell>
						<grid:cell  width="10%">${obj.create_time}</grid:cell>
						<grid:cell  width="10%">
						  <c:if test="${currUserId==obj.user_id }">
						     <a title ="编辑菜单" href="usersMenuAction!edit.do?user_id=${obj.user_id }&menu_id=${obj.menu_id}" class="p_prted">修改</a><span class='tdsper'></span>
						     <a title ="删除菜单" href="javascript:;" class="p_prted" user_id=${obj.user_id} menu_id ="${obj.menu_id}" name="del">删除</a><span class='tdsper'></span>
		                  </c:if>
						</grid:cell>
						
				  </grid:body>  
				</grid:grid>
		
	</form>
</div>             

<script type="text/javascript">
  $(function(){
	 $("[name='del']").click(function(){
		 var user_id = $(this).attr("user_id");
		 var menu_id = $(this).attr("menu_id");
		 
		 var url =ctx +"/core/admin/usersMenuAction!delete.do?ajax=yes";
		 
		 if(window.confirm('确认要删除此工号？')){
			 Cmp.excute('',url, {user_id:user_id,menu_id:menu_id}, function delJsonBack(reply){
				 alert(reply.message);
				 if(reply.result==0){
					window.location.href =ctx+"/core/admin/usersMenuAction!listUsersMenu.do";
				 }
			 }, 'json');
		 }
	 });
  });
</script>   