<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
	<form method="POST" id="deposit_log_form">
		<grid:grid from="webpage" ajax="yes" formId="serchform">
			<grid:header>
				<grid:cell width="30px">操作时间</grid:cell>
				<grid:cell width="30px">所做操作</grid:cell>
				<grid:cell width="30px">操作金额</grid:cell>
				<grid:cell width="30px">操作前金额</grid:cell>
				<grid:cell width="30px">操作后金额</grid:cell>
				<grid:cell width="40px">状态</grid:cell>
			</grid:header>
			<grid:body item="operDetail">
				<grid:cell>${operDetail.operDate}</grid:cell>
				<grid:cell>${operDetail.operate}</grid:cell>
				<grid:cell>${operDetail.amout}</grid:cell>
				<grid:cell>${operDetail.beAmount}</grid:cell>
				<grid:cell>${operDetail.afAmount}</grid:cell>
				<grid:cell>${operDetail.state_name}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>