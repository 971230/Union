<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="POST"  id="userform" action="<%=request.getContextPath() %>/shop/admin/commissionApply!payApplyAuditList.do">
<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>
						申请单号：
					</th>
					<td>
						<input type='text' class="ipttxt"
							name='apply_id'
							value="${apply_id}" style="width: 120px;" />
					<td>
					<th>
						清单id：
					</th>
					<td>
						<input type='text' class="ipttxt"
							name='list_id'
							value="${list_id}" style="width: 120px;" />
					<td>
					<th>
						审核状态：
					</th>
					<td>
						<select class="ipttxt" style="width: 100px"
							name="autid_status" >
							<option value="0" ${autid_status==0?'selected=selected':'' }>
								--请选择--
							</option>
							<option value="1" ${autid_status==1?'selected=selected':'' } >待审核</option>
							<option value="2" ${autid_status==2?'selected=selected':'' }>审核通过</option>
							<option value="3" ${autid_status==3?'selected=selected':'' }>审核不通过</option>
						</select>
					</td>
					<td>
						<input type="submit" style="margin-right: 10px;" class="comBtn"
							value="搜&nbsp;索" type="submit" id="submitButton" name="button">
					</td>
				</tr>
			</tbody>
		</table>
	</div>

<div class="grid" id="goodslist">

	
<grid:grid from="webpage"  formId="userform">
	<grid:header>
		<grid:cell width="200px">申请单号</grid:cell>
		<grid:cell width="200px">清单id</grid:cell>
		<grid:cell width="200px">申请人</grid:cell>
		<grid:cell width="200px">申请金额</grid:cell>
		<grid:cell width="200px">申请原因</grid:cell>
		<grid:cell width="200px">审核状态</grid:cell>
		<grid:cell width="150px">操作</grid:cell>
	</grid:header> 
	<grid:body item="payApply">
		<grid:cell>&nbsp;${payApply.apply_id }</grid:cell>
		<grid:cell>&nbsp;${payApply.list_id }</grid:cell>
		<grid:cell>&nbsp;${payApply.realname }</grid:cell>
		<grid:cell>&nbsp;${payApply.apply_price } </grid:cell>
		<grid:cell>${payApply.apply_reason}</grid:cell>
		<grid:cell>
			<c:if test="${payApply.status==1 }">待审核</c:if>
			<c:if test="${payApply.status==2 }">审核通过</c:if>
			<c:if test="${payApply.status==3 }">审核不通过</c:if>
		</grid:cell>
		<grid:cell>
		<c:if test="${payApply.status==1 }">
			<a title ="调价审核" name="forwardPayApplyAudit" list_id=${payApply.list_id} apply_id = "${payApply.apply_id }" apply_price="${payApply.apply_price }" href="javascript:;" class="p_prted">审核</a>
			</c:if>
		</grid:cell>
	</grid:body>
</grid:grid>

</div>
</form>
<div id="payApplyAuditDlg"></div>
<!-- userAdmin!delete.do?id=${userAdmin.userid }" onclick="javascript:return confirm('确认删除此管理员吗？')" -->
 <script type="text/javascript">
$(function(){
  Eop.Dialog.init({id:"payApplyAuditDlg", modal:false, title:"调价审核", width:"500px"});

  $("a[name=forwardPayApplyAudit]").click(function(){
     var apply_id = $(this).attr("apply_id");
     var list_id = $(this).attr("list_id");
     var apply_price = $(this).attr("apply_price");
     var url = ctx + "/shop/admin/commissionApply!forwardPayApplyAudit.do?ajax=yes";
     $("#payApplyAuditDlg").load(url,{apply_id:apply_id,list_id:list_id,apply_price:apply_price});
     Eop.Dialog.open("payApplyAuditDlg");
  });
});
</script>
