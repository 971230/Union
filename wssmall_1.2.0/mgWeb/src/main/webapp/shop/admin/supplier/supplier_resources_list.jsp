<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/show_supplier_resources_add.js"></script>
<c:if test="${flag eq 'add' or flag eq 'edit'}">
	<div class="comBtnDiv">
		<a id="addResourcesBtn" style="margin-right: 10px; cursor: pointer;"
			class="graybtn1"><span>添加</span> </a>
		<a href="javascript:;" id="delResouresBtn" style="margin-right: 10px;"
			class="graybtn1"><span>删除</span> </a>
	</div>
</c:if>
<form id="resourcesgridform" class="grid">
	<div class="grid">
		<grid:grid from="supplierResourcesList">

			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>年份</grid:cell>
				<grid:cell>员工总数</grid:cell>
				<grid:cell>生产人员</grid:cell>
				<grid:cell>管理人员</grid:cell>
				<grid:cell>研发人员</grid:cell>
				<grid:cell>服务支持人员</grid:cell>
				<grid:cell>市场及销售人员</grid:cell>
				<grid:cell>公司概况</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" value="${obj.resources_id}" name="id"
						resources="state" record_state="${obj.record_state}">
				</grid:cell>
				<grid:cell>
					<a title="查看"
						href="supplier!resourcesDetail.do?flag=view&resources_id=${obj.resources_id }">
						<html:dateformat pattern="yyyy-MM-dd" d_time="${obj.year }" /> </a>
				</grid:cell>
				<grid:cell>${obj.employees_number}</grid:cell>
				<grid:cell>${obj.production}</grid:cell>
				<grid:cell>${obj.administration}</grid:cell>
				<grid:cell>${obj.research}</grid:cell>
				<grid:cell>${obj.support}</grid:cell>
				<grid:cell>${obj.marketing_sales}</grid:cell>
				<grid:cell>${obj.company_desc}</grid:cell>
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
								href="supplier!resourcesDetail.do?flag=edit&resources_id=${obj.resources_id }&is_edit=${is_edit }&supplier_id=${supplier_id }">修改
							</a>
						</c:if>
					</c:if>
				</grid:cell>
			</grid:body>

		</grid:grid>
</form>
</div>
<input type="hidden" name="is_edit" value="${is_edit }">
<input type="hidden" name="supplier_id" value="${supplier_id }">
<div id="addResourcesDlg"></div>