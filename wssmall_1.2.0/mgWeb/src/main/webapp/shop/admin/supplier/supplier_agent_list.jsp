<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/show_supplier_agent_add.js"></script>
<c:if test="${flag eq 'add' or flag eq 'edit'}">
	<div class="comBtnDiv">
		<a id="addAgentBtn" style="margin-right: 10px; cursor: pointer;"
			class="graybtn1"><span>添加</span> </a>
		<a href="javascript:;" id="delAgentBtn" style="margin-right: 10px;"
			class="graybtn1"><span>删除</span> </a>
	</div>
</c:if>
<form id="agentgridform" class="grid">
	<div class="grid">
		<grid:grid from="supplierAgentList">

			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>厂商名</grid:cell>
				<grid:cell>代理授权证书编号</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" value="${obj.agent_id}" name="id"
						agent="state" record_state="${obj.record_state}">
				</grid:cell>
				<grid:cell>
					<a title="查看"
						href="supplier!agentDetail.do?flag=view&agent_id=${obj.agent_id }">${obj.agent_name
						} </a>
				</grid:cell>
				<grid:cell>${obj.agent_certificate_number}</grid:cell>
				<grid:cell>
					<c:if test="${obj.record_state eq '0' }">待审核</c:if>
					<c:if test="${obj.record_state eq '1'}">正常</c:if>
					<c:if test="${obj.record_state eq '2' }">注销</c:if>
					<c:if test="${obj.record_state eq '-1' }">审核不通过</c:if>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd"
						d_time="${obj.register_time }" />
				</grid:cell>
				<grid:cell>
					<c:if test="${flag eq 'add' or flag eq 'edit'}">
						<c:if test="${obj.record_state=='1' or obj.record_state=='-1' }">
							<a title="编辑"
								href="supplier!agentDetail.do?flag=edit&agent_id=${obj.agent_id }&is_edit=${is_edit }&supplier_id=${supplier_id }">修改
							</a>
						</c:if>
					</c:if>
				</grid:cell>
			</grid:body>

		</grid:grid>
</form>
</div>
<input type="hidden" value="${supplier_id}" name="supplier_id">

<div id="addAgentDlg"></div>