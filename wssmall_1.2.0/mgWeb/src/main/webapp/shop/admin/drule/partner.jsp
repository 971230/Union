<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="searchPartnerListForm">
	<div class="searchformDiv">
		<table class="form-table">
			<tr>
				<th>
					分销商名称：
				</th>
				<td>
					<input type="text" class="ipttxt"  id="partner_name" name="partner_name"  value="${partner_name }"/>
					<a href="javascript:void(0)" id="partner_search" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
				</td>
			</tr>
		</table>		
	</div>
</form>
<form id="gridform1" class="grid" >
	<grid:grid from="webpage" formId="searchPartnerListForm" ajax="yes">
		<grid:header>
			<grid:cell width="30px">
					选择
			</grid:cell>
			<grid:cell>工号</grid:cell>
			<grid:cell>姓名</grid:cell>
			<grid:cell>所在地市</grid:cell>
			<grid:cell>所在区县</grid:cell>
		</grid:header>
		<grid:body item="users">
			<grid:cell><input type="radio" name="partner_attr" userid="${users.userid}" username="${users.username}" realname="${users.realname}" /></grid:cell>
			<grid:cell>${users.username}</grid:cell>
			<grid:cell>${users.realname} </grid:cell>
			<grid:cell>${users.lan_name}</grid:cell>
			<grid:cell>${users.city_name}</grid:cell>
		</grid:body>

	</grid:grid>
</form>