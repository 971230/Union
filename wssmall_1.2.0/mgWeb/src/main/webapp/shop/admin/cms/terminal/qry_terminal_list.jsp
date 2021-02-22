<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="mk_div mtt">
	<div class="mk_title">
		<h2>终端列表</h2>
	</div>
	<div class="searchformDiv">
		<form method="post" action="terminal!qryTermList.do" name="qryForm">
			<table width="90%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<th>行业用户组名称:</th>
					<td><input type="text" name="terminal.partner_name"
						value="${terminal.partner_name}" class="searchipt" /></td>
					<th>行业用户号码:</th>
					<td>
						<input type="text" name="terminal.staff_phone_no"
							value="${terminal.staff_phone_no}" class="searchipt" />
					</td>
					<th>MAC地址:</th>
					<td><input type="text" name="terminal.mac"
						value="${terminal.mac}" class="searchipt" /></td>
					<td style="text-align:center;"><a href="javascript:void;"
						onclick="document.forms.qryForm.submit();" class="searchBtn"><span>搜&nbsp;索</span></a></td>
				</tr>
			</table>
		</form>
	</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>终端名称</grid:cell>
				<grid:cell>终端品牌</grid:cell>
				<grid:cell>终端型号</grid:cell>
				<grid:cell>行业用户组名称</grid:cell>
				<grid:cell>MAC地址</grid:cell>
				<grid:cell>行业用户 号码</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>${obj.name}</grid:cell>
				<grid:cell>${obj.brand }</grid:cell>
				<grid:cell>${obj.model }</grid:cell>
				<grid:cell>${obj.partner_name }</grid:cell>
				<grid:cell>${obj.mac }</grid:cell>
				<grid:cell>${obj.staff_phone_no }</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
</div>
