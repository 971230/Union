<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='admin_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>用户组名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="userName" value="${userName}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn userQryBtn" value="搜&nbsp;索" type="submit" attr="admin"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a href="javascript:void(0);" id="select_user_insure" class="searchBtn"><span>确&nbsp;定</span></a>
	</div>
</form>

<div>
	<div class="grid">
		<form method="POST">
			<grid:grid from="webpage" ajax="yes" formId="admin_list_form">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">用户组名称</grid:cell>
					<grid:cell style="width: 100px;">电话号码</grid:cell>
				</grid:header>
				<grid:body item="user">
					<grid:cell>
						<input type="radio" name="select_user_radio"
							username="${user.partner_name}" userid="${user.userid}"
							phone_num="${user.phone_no}" attrDiv="admin_div" partnerid="${user.partner_id}"/>
					</grid:cell>
					<grid:cell>${user.partner_name}</grid:cell>
					<grid:cell>${user.phone_no}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
