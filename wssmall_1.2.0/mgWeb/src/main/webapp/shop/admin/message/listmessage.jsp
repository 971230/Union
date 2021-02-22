<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/commonlibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript" src="../js/PasswordOperator.js"></script>

<form method="post" id="serchform" action='userAdmin.do'>

<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>用户名：</th>
		<td><input type="text" class="ipttxt"  name="username"  value="${username}"/></td>
	    <th>用户ID：</th><td><input type="text" class="ipttxt"  name="userid"  value="${userid}"/></td>
		<th>启用状态：</th>
		<td><input type="text" class="ipttxt"  name="strstate"  value="${strstate}"/></td>	
	    <th>姓名：</th>
		<td><input type="text" class="ipttxt"  name="realname"  value="${realname}"/></td>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
	    </td>
	</tr>
</table>		
</div>
</form>
<div class="grid" id="goodslist">
<div class="comBtnDiv">
	<a href="userAdmin!add.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
</div>
<grid:grid from="webpage">
	<grid:header>
	<grid:cell width="50px">id</grid:cell>
		<grid:cell width="110px">用户名</grid:cell>
		<grid:cell width="200px">姓名</grid:cell>
		<grid:cell>状态</grid:cell>
		<grid:cell width="180px">操作</grid:cell>
	</grid:header>
	<grid:body item="userAdmin">
		<grid:cell>
			${userAdmin.userid}
		</grid:cell>
		<grid:cell>&nbsp;${userAdmin.username }
		</grid:cell>
		<grid:cell>&nbsp;${userAdmin.realname } </grid:cell>
		<grid:cell> 
		 <c:if test="${userAdmin.state==1}">启用</c:if>
		 <c:if test="${userAdmin.state==0}">禁用</c:if>
		</grid:cell>
		<grid:cell>&nbsp; 
			
			<a href="userAdmin!edit.do?id=${userAdmin.userid }"><img class="modify"  src="../images/transparent.gif"></a>
			 &nbsp;<a  href="userAdmin!delete.do?id=${userAdmin.userid }" onclick="javascript:return confirm('确认删除此管理员吗？')"><img class="delete" src="../images/transparent.gif"></a>
		
		</grid:cell>
	</grid:body>
</grid:grid></div>
