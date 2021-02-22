<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/purchase_order_list.js"></script>
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
	<form action="purchaseOrderAction!purchaseOrderList.do" method="post" id="warehouseForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
							
						<td>
						<c:if test="${create_type==6 ||create_type==7}">退货单名称:</c:if>
							<c:if test="${create_type==5}">采购单名称:</c:if>
							<input type="text" class="ipttxt" size='18'
								name="pru_order_name" value="${pru_order_name }" class="searchipt" />
						</td>
						<c:if test="${create_type==5}">
						<!-- 
						<th>
							采购单编号:
						</th>
						<td>
							<input type="text" class="ipttxt" size='18'
								name="pru_order_id" value="${pru_order_id }" class="searchipt" />
						</td>
						 -->
						</c:if>
							
						<td>
							<c:if test="${create_type==6 ||create_type==7}">退货订单号:</c:if>
							<c:if test="${create_type==5}">采购订单号:</c:if>
							<input type="hidden" name="create_type" value="${create_type }">
							<input type="hidden" name="store_action_type" value="${store_action_type }">
							
							<input type="text" class="ipttxt"  size='18'
								name="order_id" value="${order_id }" class="searchipt" />
						</td>
							
						<td>
							<c:if test="${create_type==6 ||create_type==7}">退货仓库名称:</c:if>
							<c:if test="${create_type==5}">到货仓库名称:</c:if>
							<input type="text" class="ipttxt"  size='18'
								name="house_name" value="${house_name}" class="searchipt" />
						</td>
							
						<td>
							供应商:
							<input type="text" class="ipttxt" size='18'
								name="company_name" value="${company_name }" class="searchipt" />
						</td>
						<td>
							审核状态:
							<select id='audit_status' name='audit_status'>
								<option value="">全部</option>
								<option value="0">未审核</option>
								<option value="1">审核通过</option>
								<option value="2">审核不通过</option>
							</select>
						</td>
						<td >
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">
			<a href="javascript:void(0);" id="addPurorderOrederBtn"style="margin-right: 10px;" class="graybtn1"><span>添加</span></a>
		</div>
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>${create_type==6||create_type==7?'退货':'采购' }单名称</grid:cell>
				<grid:cell>${create_type==6||create_type==7?'退货':'采购' }单号</grid:cell>
				<grid:cell>${create_type==6||create_type==7?'退货':'采购' }订单号</grid:cell>
				<grid:cell>${create_type==6||create_type==7?'退货':'到货' }仓库名称</grid:cell>
				<grid:cell>供应商</grid:cell>
				<grid:cell>预付款</grid:cell>
				<grid:cell>${create_type==6||create_type==7?'退货':'采购' }状态</grid:cell>
				<grid:cell>审核状态</grid:cell>
				<grid:cell>${create_type==6||create_type==7?'退货':'采购' }预计到达时间</grid:cell>
				<grid:cell>创建时间</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					${obj.pru_order_name}
					<input type="hidden" name='pru_order_id' value="${obj.pru_order_id }">
				</grid:cell>
				<grid:cell>${obj.delivery_id}</span></grid:cell>
				<grid:cell><span name="id">${obj.order_id}</span></grid:cell>
				<grid:cell>${obj.house_name} </grid:cell>
				<grid:cell>${obj.company_name} </grid:cell>
				<grid:cell>${obj.deposit}</grid:cell>
				<grid:cell>
					<c:if test="${obj.pru_status eq '0'}">
						已新建
					</c:if>
					<c:if test="${obj.pru_status eq '1'}">
						已完成
					</c:if>
				 </grid:cell>
				 <grid:cell>
					<c:if test="${obj.audit_status eq '0'}">
						未审核
					</c:if>
					<c:if test="${obj.audit_status eq '1'}">
						审核通过
					</c:if>
					<c:if test="${obj.audit_status eq '2'}">
						审核不通过
					</c:if>
				 </grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${obj.pru_delvery_finish_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${obj.create_time}"></html:dateformat>
				</grid:cell>
			</grid:body>
			<tr id="iframe_tr" style="display: none;">
			  	<td colspan="10">
			        <iframe style="width: 100%;height: 400px;" src="">
			        </iframe>
			    </td>
		   </tr>
		</grid:grid>
	</form>
	<div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<script type="text/javascript">
$(function(){
    $("#audit_status option[value='${audit_status}']").attr("selected", true);
    $("#audit_status").change(function (){
       $("#button").trigger("click")
    });
});
</script>