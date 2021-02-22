<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
  </head>
<script src="<%=request.getContextPath() %>/publics/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<body>
<div>

	<form method="post" action='/shop/admin/auditZBAction!qryBSSOrderStatus.do' id="qryOutFrom">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
					<th>外部单号：</th>
					<td><input type="text" name="auditZBParams.out_tid" value="${auditZBParams.out_tid }" ></td>
					<th>内部单号：</th>
					<td><input type="text" name="auditZBParams.order_id" value="${auditZBParams.order_id }" ></td>
					</tr> 
					
				</tbody>
			</table>
		</div>
	</form>
	
	<!-- 查询按钮 -->
	<div class="grid comBtnDiv" >
		<ul>
			<li>
				<a href="javascript:void(0);" id="searchFormSubmit" style="margin-right:10px;" class="graybtn1">搜索</a>
			</li>
			
	    </ul>
		<div style="clear:both;"></div>
	</div>
	<div class="grid" >
<div class="comBtnDiv">
      <form method="POST"  id="out_tid_list" >
<grid:grid from="webpage" ajax="yes" formId="qryOutFrom">
	<grid:header>
		<grid:cell >外部单号</grid:cell>
		<grid:cell >内部单号</grid:cell>
		<grid:cell >BSS状态</grid:cell>
	</grid:header>
	<grid:body item="auditlist">
		<grid:cell>${auditlist.out_tid }</grid:cell>
		<grid:cell>${auditlist.order_id }</grid:cell>
		<grid:cell>${auditlist.status }</grid:cell>
	</grid:body>
</grid:grid>
</form>
</div>
</div>
</div>
 

</body>
</html>

<script type="text/javascript">
$(function (){
	$("#searchFormSubmit").click(function(){
		$("#qryOutFrom").attr('action','auditZBAction!qryBSSOrderStatus.do');
		$("#qryOutFrom").submit();
	})
});
</script>
	

