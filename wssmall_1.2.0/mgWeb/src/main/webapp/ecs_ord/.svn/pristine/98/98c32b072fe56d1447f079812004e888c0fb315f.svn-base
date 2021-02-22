<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%-- <script type="text/javascript"  src="<%=request.getContextPath() %>/ecs_ord/js/invoice_print.js"></script> --%>
<script type="text/javascript" >

var ActivityList = {
		init:function(){
		$("#searchFormSubmit").bind("click",function(){
				$("#queryPrintForm").submit();
						
			});
		
	   }
}

</script> 
<div >
<form method="post" 
	action='invoicePrintAction!search.do' id="queryPrintForm">


	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
			    <input type="hidden" name="isSearch" value="true" />
				<tr>
					<th>模板名称：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="ipttxt" style="width: 190px;"
							name="invoice.model_nam"
							value="${invoice.model_nam}" class="searchipt" />
					</td>
					
				</tr>
			</tbody>
		</table>
	</div>
</form>

	<div class="grid comBtnDiv" >
		<ul>
			
			<li>
				<a href="javascript:void(0);" id="searchFormSubmit" style="margin-right:10px;" class="graybtn1">搜索</a>
				<!-- <input type="submit" style="margin-left:5px;" class="comBtn" value="搜&nbsp;索" type="submit" name="button"> -->
			</li>
			
	    </ul>
		<div style="clear:both;"></div>
	</div>

<div class="grid"  >
<form method="POST" id="print_tpl_list">
<grid:grid  from="webpage" ajax="yes" formId ="queryPrintForm" >

	<grid:header >
	<span>
		<grid:cell></grid:cell>
		<grid:cell>模板名称</grid:cell>
		
		</span>
		</grid:header>
		</span>

  <grid:body item="pmt" >
  <grid:cell> <a href="invoicePrintAction!selectOne.do?invoiceModeFieldParams.model_cd=${pmt.model_cd}" id="information">模板编辑</a></grid:cell>
  	<grid:cell>  <a href="invoicePrintAction!templateInformation.do?invoice.model_cd=${pmt.model_cd}" id="information">${pmt.model_nam}</a></grid:cell> 
  	 </grid:body>
 
</grid:grid>  
</form>	
<div style="clear:both;"></div>
</div>

</div>

</div>
 <script type="text/javascript">
 $(function(){
ActivityList.init();
	
}); 
</script>
