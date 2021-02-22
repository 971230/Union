<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/authList.js"></script>
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>

<form method="post" id="serchform" action='<%=request.getContextPath()%>/core/admin/auth!authList.do'>
 <div class="searchformDiv">
	 <table>
		<tr>
			<th>权限名称：</th>
			<td><input type="text" class="ipttxt"  name="name"  value="${name}"/></td>
		    <th>权限编码：</th>
			<td><input type="text" class="ipttxt"  name="authid"  value="${authid}"/></td>
			<th>权限类型：</th>
			<td>
			  <select name ="type" class="ipttxt" value="${type}" style="width:150px;">
			     <option value="">---请选择---</option>
			     <option value="menu">菜单权限</option>
			     <option value="app">系统权限</option>
			     <option value="btn">按钮权限</option>
			     <option value="data">数据权限</option>
			  </select>
			  <script type="text/javascript">
			    $("[name='type'] option[value='${type}']").attr("selected","selected");
			  </script>
			</td>
			<th></th>
			<td>
		    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
			</td>
	    </tr>
	 </table>
  </div>
</form>
<div class="grid">
	<div class="comBtnDiv">
	<a href="<%=request.getContextPath()%>/core/admin/auth!add.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
    </div>
    
    <form method="POST" id="authForm" >
    
      <grid:grid  from="webpage" formId="serchform">
	        <grid:header>
				<grid:cell width="10%">权限编码</grid:cell> 
				<grid:cell width="30%">权限名称</grid:cell>
<!-- 				<grid:cell width="15%">角色描述</grid:cell> -->
				<grid:cell width="15%">权限类型</grid:cell>
				<grid:cell width="15%">创建时间</grid:cell>
				<grid:cell width="30%">操作</grid:cell> 
			</grid:header>

            <grid:body item="auth">
                <grid:cell>${auth.auth_id}</grid:cell>
<%--                 <grid:cell>
               		<c:if test="${auth.auth_id==currUserId}">自建</c:if>
                	<c:if test="${auth.auth_id!=currUserId}">上级分配</c:if>
        		</grid:cell> --%>
					<grid:cell>${auth.role_name}</grid:cell>
<!--         		<grid:cell>${auth.role_desc}</grid:cell> -->
              <!--  <grid:cell>
                   <c:if test="${auth.type=='menu'}">菜单</c:if>
                   <c:if test="${auth.type=='app'}">应用</c:if>
                   <c:if test="${auth.type=='btn'}">按钮</c:if>
                </grid:cell>
               --> 
                <grid:cell>
                   	<c:if test="${auth.type=='app'}">系统</c:if>
                   	<c:if test="${auth.type=='menu'}">菜单</c:if>
                  	<c:if test="${auth.type=='data'}">数据</c:if>
                  	<c:if test="${auth.type=='btn'}">按钮</c:if>
        		</grid:cell>
        		<grid:cell>${auth.create_time}</grid:cell>
                <grid:cell>
			    <a title ="编辑权限信息" href="javascript:;" authid="${auth.auth_id}" authtype=${auth.type} name="editAuth" class="p_prted">修改</a><!-- <span class='tdsper'></span> -->
		        <span class='tdsper'></span>
		        <a title="删除权限信息" href="javascript:;" authid="${auth.auth_id}" authtype=${auth.type} style="margin-right:10px;" name="delAuth" class="p_prted" >删除</a><!--  -->
		        </grid:cell>
            </grid:body>
      </grid:grid>
      
    </form>
    
</div>
<div id="dataAuthPack"></div>
<script type="text/javascript">
$(function(){
	Eop.Dialog.init({id:"dataAuthPack", modal:false, title:"数据权限", width:"800px"});
})
</script>
