<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<div >
	<form id="gridform" class="grid">
		<grid:grid from="webpage"  >
			<grid:header>
				<grid:cell width="30px">销售组织</grid:cell>
				<grid:cell width="30px">发布状态</grid:cell>
				<grid:cell width="30px">发布时间</grid:cell>
			</grid:header>
			<grid:body item="objeto">

				<grid:cell>
				${objeto.org_id }
				</grid:cell>
				<grid:cell>
				${objeto.status }
				</grid:cell>
				<grid:cell>
				${objeto.created_date }
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<!-- 审核日志页面 -->
	</div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
