<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="serchform" >
 <div class="searchformDiv">
	 <table>
		<tr>
			<th>权限名称：</th>
			<td><input type="text" class="ipttxt"  name="name"  id="search_auth_name" value="${name}"/></td>
		
			<th></th>
			<td>
		    <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
			</td>
	    </tr>
	 </table>
  </div>
</form>
<div class="grid">
    <form method="POST" id="authForm" >
    
      <grid:grid  from="webpage"  ajax='yes' formId="serchform">
	        <grid:header>
	            <grid:cell width="10%">选择</grid:cell> 
				<grid:cell width="40%">权限编码</grid:cell> 
				<grid:cell width="40%">权限名称</grid:cell> 
				<grid:cell width="10%">类型</grid:cell> 
				
			</grid:header>

            <grid:body item="auth">
                <grid:cell><input type="checkbox" name="selAuthCheck" auth_name="${auth.name}" auth_type="${auth.type}" value="${auth.actid}"></grid:cell>
                <grid:cell>${auth.actid}</grid:cell>
                <grid:cell>${auth.name}</grid:cell>
                <grid:cell>
                   	<c:if test="${auth.type=='app'}">系统</c:if>
                   	<c:if test="${auth.type=='menu'}">菜单</c:if>
                  	<c:if test="${auth.type=='data'}">数据</c:if>
                  	<c:if test="${auth.type=='btn'}">按钮</c:if>
        		</grid:cell>
            
            </grid:body>
      </grid:grid>
       <div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="authSelBtn">
		</div>
    </form>
    
</div>
