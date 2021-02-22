<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/Agent.js");
</script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div class="grid">
	<form action="agent!list.do" method="post">
		<div class="toolbar" style="height: auto">
			<div id="searchcbox" style="margin-left: 0px">
				&nbsp;&nbsp;&nbsp;&nbsp;

				<div>
					商户名称：
					<input type="text" class="ipttxt"  style="width: 90px" name="username"
						value="${username}" />
				</div>
				<c:if test="${state==0}">
					<div>
						状态:
						<select  class="ipttxt"  id="state" name="state">
							<!-- <option value=''>全部</option> -->
							<option value=0>
								待审核
							</option>
							<!-- <option value=1>审核通过</option> -->
							<option value=2>
								审核不通过
							</option>
						</select>
					</div>
				</c:if>
				<input type="submit"  value="搜索">
			</div>

			<div style="clear: both"></div>
		</div>
	</form>
	<form id="gridform">
		<grid:grid from="webpage" >

			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<!--<grid:cell sort="agentid" width="80px">商户编码</grid:cell> -->
				<grid:cell sort="username" width="100px">商户名称</grid:cell>
				<grid:cell sort="id_card" width="180px">证件号码</grid:cell>
				<grid:cell sort="mobile" width="100px">联系电话</grid:cell>
				<grid:cell sort="email" width="180px">邮箱</grid:cell>
				<grid:cell sort="state" width="80px">审批状态</grid:cell>
				<grid:cell sort="create_date" width="100px">申请时间</grid:cell>
				<grid:cell width="120px">关联用户</grid:cell>
				<grid:cell width="50px">操作</grid:cell>
				<grid:cell width="50px">删除</grid:cell>
				<grid:cell width="80px">商户首页</grid:cell>
			</grid:header>
			<grid:body item="agent">
				<grid:cell>
					<input type="checkbox" name="id" value="${agent.agentid }" />${agent.agentid }</grid:cell>
				<!-- <grid:cell>&nbsp;${agent.agentid } </grid:cell>-->
				<grid:cell>&nbsp;${agent.username } </grid:cell>
				<grid:cell>&nbsp;${agent.id_card } </grid:cell>
				<grid:cell>&nbsp;${agent.mobile} </grid:cell>
				<grid:cell>&nbsp;${agent.email} </grid:cell>
				<grid:cell>
        	&nbsp;
	         <span <c:if test="${agent.state==0}">style="color:red"</c:if>>
						${agent.state_name} </span>
				</grid:cell>
				<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd"
						time="${agent.create_date}"></html:dateformat>
				</grid:cell>
				<grid:cell>&nbsp;${agent.staff_code} </grid:cell>


				<grid:cell>&nbsp;
        	<c:if test="${state==1}">
						<a href="agent!edit_staff.do?agent_id=${agent.agentid }"> <img
								class="modify" src="${ctx}/shop/admin/images/transparent.gif">
						</a>
					</c:if>
					<c:if test="${state==0}">
						<a href="agent!edit.do?agent_id=${agent.agentid }"> <img
								class="modify" src="${ctx}/shop/admin/images/transparent.gif">
						</a>
					</c:if>
				</grid:cell>
				<grid:cell>
					<a
						href="agent!del.do?agent_id=${agent.agentid }&staff_no=${agent.staff_no}">
						<img class="delete" src="${ctx}/shop/admin/images/transparent.gif">
					</a>
				</grid:cell>
				<grid:cell>
					<a href="${ctx }/index_agent-agn-${agent.staff_no}.html" target="_blank">
						商户首页 </a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>


<script type="text/javascript">

$(function(){
	$("#state").val(${state});
});

</script>
