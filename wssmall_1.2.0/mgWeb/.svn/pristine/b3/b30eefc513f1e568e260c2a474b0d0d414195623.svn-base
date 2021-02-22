<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="serchform" action='buyoutCloudReport!reportList.do'>
<jsp:include page="../common/query_form.jsp"/>
</form>           
<form id="cloud_form">
<div class="grid" id="goodslist">
<grid:grid from="webpage" excel="yes">
	<grid:header>

<%-- 		<grid:cell width="200px">分销商名称</grid:cell> --%>
		<grid:cell width="200px">用户工号</grid:cell>
		<grid:cell width="200px">用户名称</grid:cell>
		<grid:cell width="200px">本地网</grid:cell>
		<grid:cell width="100px">支付金额</grid:cell>
	</grid:header>
	
	<!-- 返回的Map中key为 lan_name, username, sum_pay, userid lan_id -->
	
	<grid:body item="cloud">
		<grid:cell>${cloud.username }</grid:cell>
		<grid:cell>${cloud.realname }</grid:cell>
		<grid:cell>${cloud.lan_name }</grid:cell>
		<grid:cell>
				<a href="javascript:void(0);" onclick="buyout_card_detail('${cloud.userid}', '${cloud.lan_id}', '${startTime}', '${endTime}')">
					<fmt:formatNumber value="${cloud.sum_pay}" type="currency" />
				</a>
		</grid:cell>
	</grid:body>
</grid:grid>
<div style="clear: both; padding-top: 400px"></div>
</div>
</form>
<script type="text/javascript">
	function buyout_card_detail(userid, lan_id, start_time, end_time) {
		window.location = "buyoutCloudReport!reportDetail.do?userid=" + userid + "&lan_id=" + lan_id
				+ "&startTime=" + start_time + "&endTime=" + end_time;
	}

</script>

