<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/partnerAccount.js"></script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div>
	<form action="account!list_acct.do" method="post">

		<div class="comBtnDiv">
			<a href="account!add_act.do" style="margin-right: 10px;"
				class="graybtn1"><span>添加</span>
			</a>
			<a href="javascript:;" id="delAddrBtn" style="margin-right: 10px;"
				class="graybtn1"><span>删除</span>
			</a>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage">

			<grid:header>
				<grid:cell>选择</grid:cell>
				<grid:cell>开户行名称 </grid:cell>
				<grid:cell>开户行帐号 </grid:cell>
			</grid:header>
			<grid:body item="obj">
				<grid:cell>
					<input type="radio" name="radio_account_id" value="${obj.account_id}" />
				</grid:cell>
				<grid:cell>${obj.account_name}</grid:cell>
				<grid:cell>${obj.account_code}</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<script type="text/javascript">
$(function(){
	PartnerAccount.init();
});
</script>


