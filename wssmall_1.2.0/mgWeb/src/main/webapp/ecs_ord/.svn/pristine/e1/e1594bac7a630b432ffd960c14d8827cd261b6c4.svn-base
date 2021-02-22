<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<div>
<form method="post" id="serchFlowForm" action='/shop/admin/noMatchFlowAction!queryNoMatchFlowList.do' >
 <div class="searchformDiv">
	 <table>
		
			<th>金额：</th>
			<td><html:selectdict  name="is_zero" id="is_zero" appen_options="<option value=''>---请选择---</option>"  curr_val="${is_zero}"   attr_code="DIC_IS_ZERO" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict></td>
			<th>数据来源：</th>
			<td><html:selectdict  name="bss_or_cbss" id="bss_or_cbss" appen_options="<option value=''>---请选择---</option>"  curr_val="${bss_or_cbss}"   attr_code="DIC_BSS_OR_CBSS" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict></td>
			<th>导入日期：</th>
			<td><input type="text" name="import_date" id="import_date" value="${import_date }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></td>
			<th></th>
			<td>
			<!-- <input style="width:60px;"  class="comBtn schConBtn" id="selFlowBtn"
					value="流水查询" /> -->
					<input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
			</td>
		</tr>
	 </table>
  </div>
</form>

<div class="grid" >
<div class="comBtnDiv">
<form method="POST"  id="FlowFrom" >
<grid:grid from="webpage"  formId="serchFlowForm">
	<grid:header>
	    <grid:cell width="10%" >日期</grid:cell>
		<grid:cell width="15%">网络类型</grid:cell>
		<grid:cell width="15%">流水号</grid:cell>
		<grid:cell >业务号码</grid:cell>
		<grid:cell >营业款</grid:cell>
		<grid:cell >业务种类</grid:cell>
		<grid:cell >操作员</grid:cell>
	</grid:header>
	<grid:body item="auditBusiness">
		<grid:cell>${auditBusiness.operation_time}</grid:cell>
		<grid:cell><c:if test="${auditBusiness.data_from == 'BSS'}">3G</c:if>
		<c:if test="${auditBusiness.data_from == 'CBSS'}">4G</c:if></grid:cell>
		<grid:cell>${auditBusiness.serial_number}</grid:cell>
		<grid:cell><div><a href="javascript:void(0);" 
		operation_time="${auditBusiness.operation_time}" serial_number="${auditBusiness.serial_number}"
		phone_number="${auditBusiness.phone_number}" busi_money="${auditBusiness.busi_money}"
		business_type="${auditBusiness.business_type}" operator="${auditBusiness.operator}"
		data_from="${auditBusiness.data_from}"
		onclick="order(this)">${auditBusiness.phone_number}</a></div> </grid:cell>
		<grid:cell>￥${auditBusiness.busi_money}</grid:cell>
		<grid:cell>${auditBusiness.business_type}</grid:cell>
		<grid:cell>${auditBusiness.operator}</grid:cell>
	</grid:body>
</grid:grid>
</form>
</div>
</div>
</div>

<div id="orderDlg"></div>
<!-- var cobj = $("input[type='radio'][name='radionames']:checked"); -->
<script type="text/javascript">
Eop.Dialog.init({id:"orderDlg",modal:true,title:"订单详情", width:"1000px"});
$(function() {
	
	   					   		   	
});

function order(e){
	Eop.Dialog.open("orderDlg");
	var operation_time=e.getAttribute("operation_time");
	operation_time = operation_time.replace(/ /g, "@") ;
	var serial_number=e.getAttribute("serial_number");
	var phone_number=e.getAttribute("phone_number");
	var busi_money=e.getAttribute("busi_money");
	var business_type=e.getAttribute("business_type");
	var operator=e.getAttribute("operator");
	var data_from = e.getAttribute("data_from");
	var url ="/shop/admin/noMatchFlowAction!openOrder.do?ajax=yes";
	$("#orderDlg").load(url,{serial_number:serial_number,phone_number:phone_number,busi_money:busi_money,
		business_type:business_type,operator:operator,data_from:data_from,operation_time:operation_time},function(){});
}
</script>