<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<%--时间控件 --%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<style>
.mutil_info{
	margin-top:3px;
	margin-bottom:2px;
}
.mutil_info_div1{
	text-align:right;
	width:63px;
	float:left;
}
.mutil_info_div2{
	padding-left:8px;
	text-align:center;
	float:left;
	
	width:70px;
	word-break:keep-all;
	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
}
.mutil_info_div3{
	padding-left:8px;
	text-align:center;
	float:left;
	
	width:150px;
	word-break:keep-all;
	white-space:nowrap;
	overflow:hidden;
	text-overflow:ellipsis;
}
.mutil{
	margin-top:10px;
	margin-bottom:10px;
}
</style>
<div>
	<form action="${ctx}/shop/admin/orderBatchImportAction!batchImportList.do" method="post" id="searchNoSegForm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <%--<th>接口名称：</th>
	    	      <td>
	    	           <html:selectdict name="params.region_id" curr_val="${params.region_id }"
	    	              attr_code="DC_COMMON_REGION_GUANGDONG" style="width:150px" 
					      appen_options='<option value="">--请选择--</option>'></html:selectdict>
	    	      </td> --%>
	    	      <th>导入时间</th>
	    	      <td>
	    	      	<input type="text" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="ipttxt" name="orderBatchImport.start_time" value="${orderBatchImport.start_time}" />
	    	      	-
	    	      	<input type="text" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="ipttxt" name="orderBatchImport.end_time" value="${orderBatchImport.end_time}" />
	    	      </td>
	    	      <th>用户号码</th>
	    	      <td>
	    	      	<input type="text" class="ipttxt" name="orderBatchImport.acc_nbr" value="${orderBatchImport.acc_nbr}" />
	    	      </td>
	    	      <th>用户姓名：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="orderBatchImport.cust_name" value="${orderBatchImport.cust_name}" /> <input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	      </td>	      
				 </tr>
				 <tr>
				 	<th>地市：</th>
				 	<td>
	    	      		<html:selectdict  name="orderBatchImport.order_city_code"  appen_options="<option value=''>---请选择---</option>"  curr_val="${orderBatchImport.order_city_code}"   attr_code="DC_BATCH_ORDER_CITY_CODE" style="width:173px"></html:selectdict>    	      		
	    	      	</td>
	    	      	<th>证件号码：</th>
		    	    <td>
		    	        <input type="text" class="ipttxt" name="orderBatchImport.certi_num" value="${orderBatchImport.certi_num}" />
		    	    </td>
		    	    <th>商品名称：</th>
		    	    <td>
		    	        <input type="text" class="ipttxt" name="orderBatchImport.prod_offer_name" value="${orderBatchImport.prod_offer_name}" />
		    	    </td>
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>
	  	 
	  	 <div class="comBtnDiv">
	  	     <input class="comBtn" type="button" id="batchImportBtn" value="批量导入" style="margin-right:10px;"
				onclick="javascript:void(0);" /> 
		</div>			
	</form>

    <form id="theForm" class="grid">
	<grid:grid from="webpage" formId="searchNoSegForm" action="${ctx}/shop/admin/orderBatchImportAction!batchImportList.do">
	    <grid:header>
	    	<grid:cell style="width:86px;">用户号码</grid:cell>
	    	<grid:cell style="width:60px;">用户名称</grid:cell>
		    <grid:cell style="width:40px;">地市</grid:cell>
		    <grid:cell style="width:85px;">证件类型</grid:cell>
		    <grid:cell style="width:145px;">证件号码</grid:cell>
		    <grid:cell style="width:60px;">订单来源</grid:cell>
		    <grid:cell style="width:155px;">订单编码</grid:cell>
		    <grid:cell>商品编码</grid:cell>
		    <grid:cell style="width:150px;">商品名称</grid:cell>
		    <grid:cell style="width:130px;">导入时间</grid:cell>
		    <grid:cell style="width:80px;">导入状态</grid:cell>
			<grid:cell style="width:30px;">操作</grid:cell>
		</grid:header>

		<grid:body item="objeto">
			<grid:cell>${objeto.acc_nbr }  </grid:cell>
			<grid:cell>${objeto.cust_name }  </grid:cell>
		    <grid:cell>${objeto.order_city_code }</grid:cell>
		    <grid:cell>${objeto.certi_type }</grid:cell>
		    <grid:cell>${objeto.certi_num }</grid:cell>
		    <grid:cell>${objeto.order_from }</grid:cell>
		    <grid:cell>${objeto.out_tid }</grid:cell>
		    <grid:cell>${objeto.prod_offer_code }</grid:cell>
		    <grid:cell>
		    	<div class="mutil">
	                <div class="mutil_info">
	                	<div class="mutil_info_div3" title="${objeto.prod_offer_name }">
	                		${objeto.prod_offer_name }
	                	</div>
	                </div>
		    	</div>
		    </grid:cell>
		    <grid:cell><fmt:formatDate value="${objeto.create_time }" pattern="yyyy-MM-dd HH:mm:ss" /></grid:cell>
		    <grid:cell>
		    	<c:if test="${objeto.import_status eq '1' }">
		    		导入成功
		    	</c:if>
		    	<c:if test="${objeto.import_status eq '0' }">
		    		
		    		<div class="mutil">
		                <div class="mutil_info">
		                	<div class="mutil_info_div2" title="${objeto.fail_reason }">
		                		<span style="color:red;">导入失败</span>${objeto.fail_reason }
		                	</div>
		                </div>
		    		</div>
		    	</c:if>
		    </grid:cell>
			<grid:cell>
				<c:if test="${objeto.import_status eq '0' }">
					<a href="${ctx }/shop/admin/orderBatchImportAction!toEditPage.do?orderBatchImport.import_id=${objeto.import_id }" onclick="javascript:void(0);" >修改</a>
				</c:if>
			</grid:cell>
		</grid:body>
	</grid:grid>
    </form>
    
<div style="clear:both;padding-top:5px;"></div>
</div>
<div id="order_batch_import_dialog"></div>
<script>
$(function(){
	Eop.Dialog.init({id:"order_batch_import_dialog",modal:false,title:"订单批量导入",width:'550px'});
	//绑定订单批量导入按钮点击事件
	$("#batchImportBtn").unbind("click").bind("click",function(){
		var url = ctx + "/shop/admin/orderBatchImportAction!toImportExcel.do?ajax=yes";
		$("#order_batch_import_dialog").load(url,{},function(){});
		Eop.Dialog.open("order_batch_import_dialog");
	});
});
</script>