<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%String order_id = (String)request.getAttribute("order_id"); %>
<div class="grid_n_cont_sub">
<h3>费用信息：</h3>
<table width="100%" border="0" cellspacing="0" cellpadding="0"class="grid_s">
	<tr>
	<th style="display:none">费用编码</th>
	<th>费用名称</th>
	<th>缴费金额</th>
	<th>减免金额</th>
	<th>最大减免金额</th>
	<th>实收金额</th>
	</tr>
	<c:forEach var="attrFeeInfoBusiRequest" items="${feeInfoList}">
	<!-- c:if test="${attrFeeInfoBusiRequest.origin_mall=='10003' }"> -->
		<c:if test="${attrFeeInfoBusiRequest.is_aop_fee=='1' }">
			<tr>
				<td style="display:none">${attrFeeInfoBusiRequest.fee_inst_id }</td>
				<td>${attrFeeInfoBusiRequest.fee_item_name }</td>
				<td><span id="oFeeNum${attrFeeInfoBusiRequest.fee_inst_id }">${attrFeeInfoBusiRequest.o_fee_num }</span></td>
				<td><input id="discountFee${attrFeeInfoBusiRequest.fee_inst_id }" value="${attrFeeInfoBusiRequest.discount_fee }" type="text" class="ipt_new" 
				style="width:200px;" onkeyup="this.value=value.replace(/[^\d.]/g,'')" feeinstid = "${attrFeeInfoBusiRequest.fee_inst_id }" 
				onblur="feeBlur('discountFee${attrFeeInfoBusiRequest.fee_inst_id }')" /></td>			
				<td><span  id="maxRelief${attrFeeInfoBusiRequest.fee_inst_id }">${attrFeeInfoBusiRequest.max_relief }</span></td>
				<td><span  id="nFeeNum${attrFeeInfoBusiRequest.fee_inst_id }">${attrFeeInfoBusiRequest.n_fee_num }</span></td>
			</tr>
		</c:if>
	</c:forEach>
	</table>
</div>
<script>
function feeBlur(objid){
	var obj = $("#"+objid);
	var feeinstId = obj.attr("feeinstid");
	var oNum = $("#oFeeNum"+feeinstId).text();
	var maxRelief = $("#maxRelief"+feeinstId).text();
	var disCount = $("#discountFee"+feeinstId).val();	
	var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	if(exp.test(disCount)){
		var onum = oNum/1;
		var discount = disCount/1;
		if(discount>onum){
			alert("减免金额不能超过缴费金额，请重新输入");
			$("#"+objid).val("0.0");
			return;
		}

		if(discount>maxRelief){
			alert("减免金额不能超过最大减免金额，请重新输入");
			$("#"+objid).val("0.0");
			return;
		}
		var nnum = onum-discount;
		$("#nFeeNum"+feeinstId).text(nnum);
		var feeInfo = new Object();
		feeInfo.fee_inst_id = feeinstId;
		feeInfo.discount_fee = discount;
		feeInfo.n_fee_num = nnum;
		var feestr = JSON.stringify(feeInfo);
		$.ajax({
			type : "post",
			url : "<%=request.getContextPath() %>/shop/admin/orderFlowAction!updateFeeInfo.do?ajax=yes&order_id=${order_id }&feeInfo="+feestr,
			data : {},
			dataType : "json",
			success : function(data) {
				if(data.result=="1"){
					alert(data.message);
				}
			}
		});
	}else{
		alert("减免金额格式错误");
	}
}
</script>
