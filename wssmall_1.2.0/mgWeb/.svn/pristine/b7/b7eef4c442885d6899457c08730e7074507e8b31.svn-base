<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String sql = "select a.partner_name pname from es_partner t, es_adminuser a where t.userid = a.userid and t.sequ = 0 and t.userid = ? ";
%>
<form method="post" id="serchform" action='contractHandleReport!reportList.do'>
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
		<grid:cell >合约计划名称</grid:cell> 
		<grid:cell >受理总数</grid:cell> 
<%-- 		<grid:cell >待受理数</grid:cell>  --%>
		<grid:cell >受理成功总数</grid:cell> 
		<grid:cell >受理失败总数</grid:cell> 
	</grid:header>
	
	<!-- Map的key有 all_count,sum(decode(c.state,'1',1,0)) succ_count,sum(decode(c.state,'-1',1,0))
	 fail_count, sum(decode(c.state,'0',1,0)) wait_count, u.username,c.lan_name, u.realname , c.lan_id,c.offer_name, c.userid -->
	
	<grid:body item="contract">
		<grid:cell>${contract.lan_name }</grid:cell>
		<grid:cell>${contract.username }</grid:cell>
		<grid:cell>${contract.realname }</grid:cell>
		<grid:cell>${contract.offer_name }</grid:cell>
		<grid:cell> 
				<a href="javascript:void(0);" onclick="contract_detail('${contract.userid}', '${contract.lan_id}', '', '${startTime}', '${endTime}')">
				${contract.all_count }
				</a>
		</grid:cell>
<%-- 		<grid:cell>  --%>
<%-- 				<a href="javascript:void(0);" onclick="contract_detail('${contract.userid}', '${contract.lan_id}', '0', '${startTime}', '${endTime}')"> --%>
<%-- 				${contract.wait_count } --%>
<!-- 				</a> -->
<%-- 		</grid:cell> --%>
		<grid:cell> 
				<a href="javascript:void(0);" onclick="contract_detail('${contract.userid}', '${contract.lan_id}', '1', '${startTime}', '${endTime}')">
				${contract.succ_count }
				</a>
		</grid:cell>
		<grid:cell> 
				<a href="javascript:void(0);" onclick="contract_detail('${contract.userid}', '${contract.lan_id}', '-1', '${startTime}', '${endTime}')">
				${contract.fail_count }
				</a>
		</grid:cell>
	</grid:body>
</grid:grid>


<div style="clear: both; padding-top: 400px"></div>
</div>


</form>
<script type="text/javascript">
	function contract_detail(userid, lan_id, state, start_time, end_time) {
		window.location = "contractHandleReport!reportDetail.do?userid=" + userid + "&lan_id=" + lan_id + "&state=" + state
				+ "&startTime=" + start_time + "&endTime=" + end_time;
	}

</script>



