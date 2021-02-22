<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String sql = "select a.partner_name pname from es_partner t, es_adminuser a where t.userid = a.userid and t.sequ = 0 and t.userid = ? ";
%>
<form method="post" id="serchform" action='contractSaleroomReport!reportList.do'>
<jsp:include page="../common/query_form.jsp"/>
</form>           
<form id="cloud_form">
<div class="grid" id="goodslist">
<grid:grid from="webpage">
	<grid:header>
		<grid:cell >本地网名称</grid:cell> 
<%-- 		<grid:cell >分销商名称</grid:cell>  --%>
		<grid:cell >用户工号</grid:cell> 
		<grid:cell >用户名称</grid:cell> 
		<grid:cell >CRM合约机实收金额</grid:cell> 
		<grid:cell >外系统实收金额</grid:cell> 
	</grid:header>
	
	<!-- Map的key有lan_name, userid, username, lan_id, sum_crm_fee, sum_sec_fee -->
	
	<grid:body item="saleroom">
		<grid:cell>${saleroom.lan_name }</grid:cell>
		<grid:cell>${saleroom.username }</grid:cell>
		<grid:cell>${saleroom.realname }</grid:cell>
		<grid:cell> 
				<a href="javascript:void(0);" onclick="saleroom_detail('${saleroom.userid}', '${saleroom.lan_id}', '${startTime}', '${endTime}')">
<%-- 					<fmt:setLocale value="zh_cn"/> --%>
					<fmt:formatNumber value="${saleroom.sum_crm_fee }" type="currency" />
				</a>
		</grid:cell>
		<grid:cell><fmt:formatNumber value="${saleroom.sum_sec_fee }" type="currency" /></grid:cell>
	</grid:body>
</grid:grid>


<div style="clear: both; padding-top: 400px"></div>
</div>


</form>
<script type="text/javascript">
	function saleroom_detail(userid, lan_id, start_time, end_time) {
		window.location = "contractSaleroomReport!reportDetail.do?userid=" + userid + "&lan_id=" + lan_id
				+ "&startTime=" + start_time + "&endTime=" + end_time;
	}

</script>



