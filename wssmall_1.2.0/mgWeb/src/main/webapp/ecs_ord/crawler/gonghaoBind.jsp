<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/gonghao_bind.js"></script>

<form id="goback"></form>
<form id="searchUser" method="post">
<div class="searchformDiv">
	<table>
		<th>用户名：</th>
		<td><input type="text" name="username_search" class="ipttxt"></td>
		<th>姓名：</th>
		<td><input type="text" name="realname" class="ipttxt"></td>
		<th>手机：</th>
		<td><input type="text" name="phone_num" class="ipttxt"></td>
		<th></th>
		<td>
			<input type="button" value="搜&nbsp索" class="comBtn" id="searchUser_btn"/>
			<input type="button" value="返&nbsp回" class="comBtn" id="goback_btn"/>
		</td>
	</table>
	</div>
</form>

	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchAdminUserForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="30px">用户名</grid:cell>
				<grid:cell width="110px">姓名</grid:cell>
				<grid:cell width="150px">手机</grid:cell>
				<grid:cell width="30px">工号绑定</grid:cell>
			</grid:header>
			<grid:body item="queue">
				<grid:cell>
					${queue.username}
				</grid:cell>
				<grid:cell>
					${queue.realname}
				</grid:cell>
				<grid:cell>
					${queue.phone_num}
				</grid:cell>
				<grid:cell>
					<a href="javascript:void(0);" class="gonghaoBindWindow">工号绑定</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>