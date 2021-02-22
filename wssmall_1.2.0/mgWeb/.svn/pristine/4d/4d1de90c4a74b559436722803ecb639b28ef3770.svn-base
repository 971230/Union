<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String sql = "select a.partner_name pname from es_partner t, es_adminuser a where t.userid = a.userid and t.sequ = 0 and t.userid = ? ";
%>
<form method="post" id="serchform" action='activeCloudReport!reportList.do'>
<jsp:include page="../common/query_form.jsp"/>
</form>           
<form id="cloud_form">
<div class="grid" id="goodslist">
<grid:grid from="webpage" excel="yes">
	<grid:header>
		<grid:cell >本地网名称</grid:cell> 
<%-- 		<grid:cell >分销商名称</grid:cell>  --%>
		<grid:cell >用户工号</grid:cell> 
		<grid:cell >用户名称</grid:cell> 
		<grid:cell >调拨数量</grid:cell> 
		<grid:cell >激活数量</grid:cell> 
		<grid:cell >激活率</grid:cell> 
	</grid:header>
	
	<!-- Map的key有 all_count, active_count, username, lan_name, lan_id, realname, t.first_userid/t.to_userid  as userid-->
	
	<grid:body item="cloud">
		<grid:cell>${cloud.lan_name }</grid:cell>
		<grid:cell>${cloud.username }</grid:cell>
		<grid:cell>${cloud.realname }</grid:cell>
		<grid:cell> 
				<a href="javascript:void(0);" onclick="active_detail('${cloud.userid}', '${cloud.lan_id}', '' , '${startTime}', '${endTime}')">
				${cloud.all_count }
				</a>
		</grid:cell>
		<grid:cell> 
				<a href="javascript:void(0);" onclick="active_detail('${cloud.userid}', '${cloud.lan_id}', '2', '${startTime}', '${endTime}')">
				${cloud.active_count }
				</a>
		</grid:cell>
		<grid:cell>${cloud.active_rate }</grid:cell>
	</grid:body>
</grid:grid>


<div style="clear: both; padding-top: 400px"></div>
</div>

</form>
<script type="text/javascript">
	function active_detail(userid, lan_id, state, start_time, end_time) {
		window.location = "activeCloudReport!reportDetail.do?userid=" + userid + "&lan_id=" + lan_id + "&state=" + state
				+ "&startTime=" + start_time + "&endTime=" + end_time;
	};

</script>



