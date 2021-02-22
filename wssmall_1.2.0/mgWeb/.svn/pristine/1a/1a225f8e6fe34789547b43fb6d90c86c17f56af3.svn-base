<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id='outer_list_form' action="javascript:void(0);">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>姓名:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="spreadMember.name" value="${spreadMember.name}" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn userQryBtn" value="搜&nbsp;索" type="submit" attr="outer"></td>
				</tr>
			</tbody>
		</table>
	</div>
</form>

<div>
	<div class="grid">
		<form method="POST" id="outer_list_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell style="width: 30px;">选择</grid:cell>
					<grid:cell style="width: 100px;">姓名</grid:cell>
					<grid:cell style="width: 100px;">电话号码</grid:cell>
				</grid:header>
				<grid:body item="user">
					<grid:cell>
						<input type="radio" name="select_rule"
							username="${user.name}" userid="${user.spread_id}"
							phone_num="${user.mobile}" bank_account="${user.bank_account}"
							bank_name="${user.bank_name}" bank_account_name="${user.bank_account_name}"
							spread_identity="${user.spread_identity}" spread_address="${user.spread_address}" 
							spread_zip="${user.spread_zip}" ucode="${user.ucode}" attrDiv="outer_div"/>
					</grid:cell>
					<grid:cell>${user.name}</grid:cell>
					<grid:cell>${user.mobile}</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
