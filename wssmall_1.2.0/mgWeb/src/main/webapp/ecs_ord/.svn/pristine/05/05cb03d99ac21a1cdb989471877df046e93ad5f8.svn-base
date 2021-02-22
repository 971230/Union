<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%-- <script type="text/javascript"  src="<%=request.getContextPath() %>/ecs_ord/js/invoice_print.js"></script> --%>
<script type="text/javascript" >

var ActivityList = {
		init:function(){
		$("#searchFormSubmit").bind("click",function(){
				$("#queryListForm").submit();
						
			});
		
	   }
}

</script> 
<div >
 <div class="comBtnDiv">
	<a href="<%=request.getContextPath() %>/shop/admin/orderWarningAction!toAdd.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
</div>
<form method="post" 
	action='orderWarningAction!searchList.do' id="queryListForm">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>规则名称：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="ipttxt" style="width: 190px;"	name="orderWarning.group_name"
						 value="${orderWarning.group_name}" class="searchipt" />
					</td>
					 <th>归属地市：</th>
		             <td>
		                	   	<select name="orderWarning.order_origin" class="ipt_new" style="width:138px;">
               		<c:forEach items="${regionList }" var="ds">
               			<option value="${ds.region_id }" ${ds.region_id==orderWarning.order_origin ?'selected':'' } >${ds.local_name }</option>
               		</c:forEach>
				</select>
							
							<td>
            
              </td>
		              </td>
		               <th>订单来源：</th>
		             <td>
		                	<select name="orderWarning.order_from" class="ipt_new" style="width:138px;">
		                		<c:forEach items="${order_from_list }" var="ds">
		                			<option value="${ds.pkey }" ${ds.pkey==orderWarning.order_from?'selected':'' }>${ds.pname }</option>
		                		</c:forEach>
							</select>
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
<grid:grid  from="webpage" ajax="yes" formId ="queryListForm" >
	<grid:header >
		<span>
			<grid:cell>操作</grid:cell>
			<grid:cell>规则组名称</grid:cell>
			<grid:cell>订单环节</grid:cell>
			<grid:cell>订单来源</grid:cell>
			<grid:cell>商品类型</grid:cell>
			<grid:cell>创建时间</grid:cell>
		</span>
	</grid:header>

  <grid:body item="pmt" >
    <grid:cell> <a href="orderWarningAction!queryDetails.do?queryType=edit&orderWarning.warning_id=${pmt.warning_id}" >编辑</a></grid:cell>
	<grid:cell>${pmt.group_name}</grid:cell>
	<grid:cell>
	   <c:forEach items="${flowTraceList }" var="ds">
		   <c:if test="${ds.pkey==pmt.flow_id}">
		      ${ds.pname }
		   </c:if>
	  </c:forEach>
	</grid:cell>
	<grid:cell>
	  <c:forEach items="${order_from_list }" var="ds">
		   <c:if test="${ds.pkey==pmt.order_from}">
		      ${ds.pname }
		   </c:if>
	  </c:forEach>
	</grid:cell>
	<grid:cell>
	   <c:forEach items="${goods_type_list }" var="ds">
		   <c:if test="${ds.pkey==pmt.product_type}">
		      ${ds.pname }
		   </c:if>
	  </c:forEach>
	</grid:cell>
	<grid:cell>${pmt.create_time}</grid:cell>
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
