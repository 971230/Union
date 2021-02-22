<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<div class="grid_n_cont_sub" >
	<div class="grid_w_div">
		<form method="post" id="crawlerFeeInfoForm" class="validate">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"class="grid_s">
			<tr>
				<th style="display:none">费用编码</th>
				<th>费用名称</th>
				<th>缴费金额</th>
				<th>减免金额</th>
				<th>减免原因</th>
				<th>最大减免金额</th>
				<th>实收金额</th>
			</tr>
			<c:forEach var="attrFeeInfoBusiRequest" items="${feeInfoList}">
				<tr>
					<td style="display:none"><input name="fee_inst_id" value="${attrFeeInfoBusiRequest.fee_inst_id }" type="hidden"></td>
					<td><span id="feeItemName${attrFeeInfoBusiRequest.fee_inst_id }">${attrFeeInfoBusiRequest.fee_item_name }</span></td>
					<td>
						<c:if test="${attrFeeInfoBusiRequest.fee_category == '99' }">
						<input id="oFeeNum${attrFeeInfoBusiRequest.fee_inst_id }" value="${attrFeeInfoBusiRequest.o_fee_num }" type="text" class="ipt_new" 
							style="width:60px;" onkeyup="this.value=value.replace(/[^\d.]/g,'')" feeinstid = "${attrFeeInfoBusiRequest.fee_inst_id }" /></td>			
						</c:if>
						<c:if test="${attrFeeInfoBusiRequest.fee_category != '99' }">
							<span id="oFeeNum${attrFeeInfoBusiRequest.fee_inst_id }">${attrFeeInfoBusiRequest.o_fee_num }</span>
						</c:if>
					</td>
					
					<td>
						<c:if test="${attrFeeInfoBusiRequest.fee_category != '99' }">
						<input id="discountFee${attrFeeInfoBusiRequest.fee_inst_id }" value="${attrFeeInfoBusiRequest.discount_fee }" type="text" class="ipt_new" 
							style="width:60px;" onkeyup="this.value=value.replace(/[^\d.]/g,'')" feeinstid = "${attrFeeInfoBusiRequest.fee_inst_id }" 
							onblur="feeBlur('discountFee${attrFeeInfoBusiRequest.fee_inst_id }')" />			
						</c:if>
					</td>
					<td>
						<c:if test="${attrFeeInfoBusiRequest.fee_category != '99' }">
						<input id="discountReason${attrFeeInfoBusiRequest.fee_inst_id }" value="${attrFeeInfoBusiRequest.discount_reason }" type="text" class="ipt_new" 
							style="width:100px;" feeinstid = "${attrFeeInfoBusiRequest.fee_inst_id }"/>			
						</c:if>
					</td>
					<td>
						<c:if test="${attrFeeInfoBusiRequest.fee_category != '99' }">
							<span id="maxRelief${attrFeeInfoBusiRequest.fee_inst_id }">${attrFeeInfoBusiRequest.max_relief }</span>
						</c:if>
					</td>
					<td>
						<c:if test="${attrFeeInfoBusiRequest.fee_category != '99' }">
							<span id="nFeeNum${attrFeeInfoBusiRequest.fee_inst_id }">${attrFeeInfoBusiRequest.n_fee_num }</span>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
	</div>
	<br>
	<div style="margin-left: auto;margin-right: auto;" align="center">
       <input name="save_btn" type="button" id="save_btn" value="保存"  class="graybtn1" />
    </div>
</div>
<div id="selFukaPhoneDlg"></div>
<script type="text/javascript">
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
		if(discount>0){
			$("#discountReason"+feeinstId).attr("required","true");
		}
	}else{
		alert("减免金额格式错误");
	}
}
$(function(){
	$("input[name='save_btn']").unbind().bind("click", function() {
		var feeArr = [];
		var msg = "";
		$("#crawlerFeeInfoForm table tr:gt(0)").each(function(){
			var feeinstId = $("input[name='fee_inst_id']",$(this)).val();
			var oNum = $("#oFeeNum"+feeinstId).val();
			if(!oNum){
				oNum = $("#oFeeNum"+feeinstId).text();
			}
			var discount = $("#discountFee"+feeinstId).val();
			var discountReason = $("#discountReason"+feeinstId).val();
			var nNum = $("#nFeeNum"+feeinstId).text();
			var feeItemName = $("#feeItemName"+feeinstId).text();
			var feeInfo = new Object();
			feeInfo.fee_inst_id = feeinstId;
			feeInfo.o_fee_num = oNum;
			feeInfo.discount_fee = discount;
			feeInfo.discount_reason = encodeURI(encodeURI(discountReason));
			feeInfo.n_fee_num = nNum;
			var feestr = JSON.stringify(feeInfo);
			feeArr.push(feestr);
			if(discount>0 && !discountReason){
				msg = msg + feeItemName+"的减免原因不能为空;"
			}
		});
		if(msg){
			alert(msg);
			return ;
		}
		var feeInfoStr = "["+feeArr.toString()+"]";
		$.ajax({
			type : "post",
			url : ctx + "/shop/admin/orderCrawlerAction!flowDealOpenAcct_pc_save.do?ajax=yes&order_id=${order_id }&feiyongmingxi_s="+feeInfoStr,
			data : {},
			dataType : "json",
			success : function(data) {
				alert(data.message);
				if(data.result=="0"){
					Eop.Dialog.close("open_acct_fee_list");
				}
			},
			error:function(){
				
			}
		});
		
	});
});
	
</script>