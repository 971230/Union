<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="chargeaccetpform" action="${pageContext.request.contextPath}/shop/admin/wpdelivery!qryWpDelivery.do?create_type=${create_type}&type=${type }" >
<div class="searchformDiv">
<table class="form-table">
	<tr>
		<th>
		${create_type==6||create_type==7?'退货':'采购' }单名称：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="name"  value="${name }"/>
		</td>
		<th>
		${create_type==6||create_type==7?'退货':'采购' }单号：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="s_delivery_id"  value="${s_delivery_id }"/>
		</td>
		<th>
		${create_type==6||create_type==7?'退货':'采购' }订单号：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="order_id"  value="${order_id }"/>
		</td>
		
		<th>
		物流单号：
		</th>
		<td>
		<input type="text" class="ipttxt"  name="logi_no"  value="${logi_no }"/>
		<a href="javascript:void(0)" id="acceptsearchBtn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div style="clear:both;padding-top:5px;"></div>
<div class="grid">
<grid:grid  from="deliveryPage" >
	<grid:header>
		<grid:cell >${create_type==6||create_type==7?'退货':'采购' }单名称</grid:cell>
		<grid:cell >${create_type==6||create_type==7?'退货':'采购' }单号</grid:cell>
		<grid:cell >${create_type==6||create_type==7?'退货':'采购' }订单号</grid:cell>
		<grid:cell >订单总金额</grid:cell>
		<grid:cell >商品总额</grid:cell> 
		<grid:cell >物流费用</grid:cell> 
		<grid:cell >物流单号</grid:cell>
		<grid:cell >审核状态</grid:cell> 
		<grid:cell >${create_type==6||create_type==7?'退货':'采购' }状态</grid:cell> 
		<grid:cell >经办人</grid:cell> 
		<grid:cell >${create_type==6||create_type==7?'退货':'采购' }时间</grid:cell> 
	</grid:header>
    <grid:body item="ap">
    	 <grid:cell>${ap.pru_order_name}</grid:cell>
    	  <grid:cell>
        	<span name="id">${ap.delivery_id}</span>
        </grid:cell>
    	 <grid:cell>${ap.order_id}</grid:cell>
        <grid:cell>${ap.order_amount}</grid:cell>
        <grid:cell>${ap.goods_amount}</grid:cell>
        <grid:cell>${ap.shipping_amount}</grid:cell>
        <grid:cell>${ap.logi_no}</grid:cell>
        <grid:cell>
        	<c:if test="${ap.audit_status==0 }">未审核</c:if>
        	<c:if test="${ap.audit_status==1 }">审核通过</c:if>
        	<c:if test="${ap.audit_status==2 }">审核不通过</c:if>
        </grid:cell>
        <grid:cell>
        	<c:if test="${ap.ship_status==2 }">已${create_type==6||create_type==7?'退货':'采购' }</c:if>
        	<c:if test="${ap.ship_status==3 }">部分${create_type==6||create_type==7?'退货':'采购' }</c:if>
        	<c:if test="${ap.ship_status==-1 || ap.ship_status==-2 || ap.ship_status==-3 || ap.ship_status==0}">未${create_type==6||create_type==7?'退货':'采购' }</c:if>
        </grid:cell>
        <grid:cell>${ap.op_name}</grid:cell>
        <grid:cell>${ap.create_time}</grid:cell>
    </grid:body>  
    <tr id="iframe_tr" style="display: none;">
  	<td colspan="11">
        <iframe style="width: 100%;height: 400px;" src="">
        </iframe>
    </td>
   </tr>
</grid:grid>  
<div style="clear:both;padding-top:5px;"></div>
</div>
</form>	

<div id="returneddialog" >
</div>
<script type="text/javascript">
var select_order_id="";
$(function(){
	Eop.Dialog.init({id:"returneddialog",modal:false,title:"创库管理", height:"650px",width:"300px"});
	
	$("#acceptsearchBtn").bind("click",function(){
		chargeaccetpform.submit();
	});
	
	$(".grid tbody tr").bind("click",function(){
		var delivery_id = $(this).find("td span[name='id']").html();
		if(delivery_id==select_order_id){
			$("#iframe_tr").hide();
			select_order_id = "";
			return ;
		}
		select_order_id = delivery_id;
		$("#iframe_tr").show().insertAfter($(this)).find("iframe").attr("src","wpdelivery!detial.do?delivery_id="+delivery_id+"&create_type=${create_type}");
	});
	
});
</script>
