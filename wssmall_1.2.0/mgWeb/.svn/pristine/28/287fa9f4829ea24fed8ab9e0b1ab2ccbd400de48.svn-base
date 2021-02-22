<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/show_supplier_account_add.js"></script>
<c:if test="${flag eq 'add' or flag eq 'edit'}">
	<div class="comBtnDiv">
		<a id="addAccountBtn" style="margin-right: 10px; cursor: pointer;"
			class="graybtn1"><span>添加</span> </a>
		<a href="javascript:;" id="delAccountBtn" style="margin-right: 10px;"
			class="graybtn1"><span>删除</span> </a>
	</div>
</c:if>
<form id="accountgridform" class="grid">
	<div class="grid">
		<grid:grid from="supplierAccountList">

			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>开户名</grid:cell>
				<grid:cell>开户银行</grid:cell>
				<grid:cell>开户银行地址</grid:cell>
				<grid:cell>账号</grid:cell>
				<grid:cell>是否默认账号</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" value="${obj.account_id}" name="id"
						account="state" state="${obj.state}">
				</grid:cell>
				<grid:cell>
					<a title="查看"
						href="supplier!accountDetail.do?flag=view&account_id=${obj.account_id }">${obj.accoun_name
						} </a>
				</grid:cell>
				<grid:cell>${obj.open_bank}</grid:cell>
				<grid:cell>${obj.address}</grid:cell>
				<grid:cell>${obj.bank_account}</grid:cell>
				<grid:cell>
					<c:if test="${obj.is_default eq 1}">
	        		是
		        </c:if>
					<c:if test="${obj.is_default eq 2}">
		        	否
		        </c:if>
				</grid:cell>

				<grid:cell>
					<c:if test="${obj.state eq '0' }">待审核</c:if>
					<c:if test="${obj.state eq '1'}">正常</c:if>
					<c:if test="${obj.state eq '2' }">注销</c:if>
					<c:if test="${obj.state eq '-1' }">审核不通过</c:if>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd"
						d_time="${obj.register_time }" />
				</grid:cell>
				<grid:cell>
					<c:if test="${flag eq 'add' or flag eq 'edit'}">
						<c:if test="${obj.state=='1' or obj.state=='-1' }">
							<a title="编辑"
								href="supplier!accountDetail.do?flag=edit&account_id=${obj.account_id }&is_edit=${is_edit }&supplier_id=${supplier_id }">修改
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
<div id="addAccountDlg"></div>
<script type="text/javascript">

</script>