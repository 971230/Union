<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div>
<a href="javascript:;" class="export_excle graybtn1" parentid="0"><span>营业款稽核报表导出 </span></a>
</div>
<div>
<form method="POST"  id="from3"  >

</form>
<form method="POST"  id="from4"  action="/shop/admin/queueCardMateManagerAction!showOrderItemsByStatus.do" >

</form>
<div class="grid" >
	<grid:grid from="webpage" formId="from4" ajax="yes" >
			<grid:header>
			<grid:cell style="text-align:center; width:7%;vertical-align:middle">订单号</grid:cell>
				<grid:cell width="7%" >订单时间</grid:cell>
				<grid:cell width="7%">商品名称</grid:cell>
				<grid:cell width="7%">订单来源</grid:cell>
				<grid:cell width="7%">订单状态</grid:cell>
				<grid:cell width="7%">订单环节</grid:cell>
				<grid:cell width="7%">号码</grid:cell>
				<grid:cell width="7%">姓名</grid:cell>
				<grid:cell width="10%">证件号</grid:cell>
				<grid:cell width="7%">联系人</grid:cell>
				<grid:cell width="7%">联系电话</grid:cell>
				<grid:cell width="7%">写卡结果</grid:cell>
				<grid:cell width="13%" >失败原因</grid:cell>
			</grid:header>
			<grid:body item="orderotem" >
			<grid:cell>${orderotem.out_tid }</grid:cell>
			<grid:cell>${orderotem.tid_time }</grid:cell>
			<grid:cell>${orderotem.goodsName }</grid:cell>
			<grid:cell>${orderotem.order_from_n }</grid:cell>
			<grid:cell>${orderotem.order_status }</grid:cell>
			<grid:cell>${orderotem.flow_trace_name }</grid:cell>
			<grid:cell>${orderotem.phone_num }</grid:cell>
			<grid:cell>${orderotem.phone_owner_name }</grid:cell>
			<grid:cell>${orderotem.cert_card_num }</grid:cell>
			<grid:cell>${orderotem.ship_name }</grid:cell>
			<grid:cell>${orderotem.ship_mobile }</grid:cell>
			<grid:cell>${orderotem.card_status }</grid:cell>
			<grid:cell><textarea rows="1" cols="20">${orderotem.exception_desc }</textarea></grid:cell>
			</grid:body>
	</grid:grid>
</div>
</div>
<input type="hidden" id="cardmateid" value="${cardmateid }"/>
<input type="hidden" id="sucflag" value="${sucflag }"/>
<input type="hidden" id="sta_time" value="${sta_time }"/>
<input type="hidden" id="end_time" value="${end_time }"/>
<script type="text/javascript">
$(".export_excle").click(function(){
	$("#from4").attr('action',"queueCardMateManagerAction!showOrderItemsByStatus.do?excel=yes&cardmateid="+$("#cardmateid").val()+"&sucflag="+$("#sucflag").val()+"&sta_time="+$("#sta_time").val()+"&end_time="+$("#end_time").val());
	$("#from4").submit();
});
</script>