<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/comm_item_detail.js"></script>
<div class="comBtnDiv">
	<a href="javascript:;" style="margin-right:10px;" class="graybtn1 itemEndApply" ><span>结算申请</span></a>
</div>
<div class="grid">

	<form method="POST" id="comm_itme_form">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell style="width: 20px;">选择</grid:cell>
				<grid:cell style="width: 100px;">清单id</grid:cell>
				<grid:cell style="width: 100px;">详情id</grid:cell>
				<grid:cell style="width: 110px;">业务名称</grid:cell>
				<grid:cell style="width: 50px;">酬金状态</grid:cell>
				<grid:cell style="width: 50px;">酬金金额</grid:cell>
				<grid:cell style="width: 50px;">酬金类型</grid:cell>
				<grid:cell style="width: 100px;">关联订单号</grid:cell>
				<grid:cell style="width: 100px;">生成时间</grid:cell>
				<grid:cell style="width: 100px;">操作</grid:cell>
			</grid:header>
			<grid:body item="itemDetail">
				<grid:cell>
					<input type="checkbox" name="detailIds" class="checkBox"
						attr="${itemDetail.detail_id }" value="${itemDetail.detail_id }" />
				</grid:cell>
				<grid:cell>${itemDetail.list_id}</grid:cell>
				<grid:cell>${itemDetail.detail_id}</grid:cell>
				<grid:cell>${itemDetail.plan_name}</grid:cell>
				<grid:cell>
					<c:if test="${itemDetail.status == 'nopay'}">
					未结算
				</c:if>
					<c:if test="${itemDetail.status == 'payed'}">
					已结算
				</c:if>
				
				</grid:cell>
				<grid:cell>${itemDetail.amount}</grid:cell>
				<grid:cell>
					<c:if test="${itemDetail.detail_type == '01'}">
					一次性
				</c:if>
					<c:if test="${itemDetail.detail_type == '02'}">
					周期性
				</c:if>
					<c:if test="${itemDetail.detail_type == '03'}">
					调整
				</c:if>
				</grid:cell>
				<grid:cell>${itemDetail.rel_order_id}</grid:cell>
				<grid:cell>${itemDetail.create_time}</grid:cell>
				<grid:cell>
					<a href="javascript:void(0);" class="auditmodify itemRecount"
						attr="${itemDetail.detail_id}">重新计算</a>
				</grid:cell>
			</grid:body>
		</grid:grid>
<input type="hidden" value=${commissionDetail.list_id } name="list_id" id = "listId">
	</form>
</div>

<div id="balanceApplyDlg"></div>
