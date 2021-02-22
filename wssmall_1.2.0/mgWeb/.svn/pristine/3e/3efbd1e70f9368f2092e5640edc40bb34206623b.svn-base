<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="/shop/admin/css/order.css" type="text/css">
<form id="serchform" action= "<%=request.getContextPath() %>/shop/admin/commissionApply!paymentList.do" method="post">
 <div class="searchformDiv">
   <table>
	 <tr>
	 	<th>支付订单号：</th>
	    <td><input type="text" class ="ipttxt" name="transactionid" value="${transactionid}"></td>
	    <th>清单ID：</th>
	    <td><input type="text" class ="ipttxt" name="list_id" value="${list_id}"></td>
	    <th>开始时间：</th>
		<td><input type="text"  class="ipttxt dateinput"   name="start_time" id="start_time" readonly="readonly"   value='${start_time}'  maxlength="60"  dataType="date" />
		</td>
	    <th>结束时间：</th>
		<td><input type="text"  class="ipttxt dateinput"   name="end_time" id="end_time" readonly="readonly"   value='${end_time}'  maxlength="60"  dataType="date" />
		</td>
	 </tr>
	 <tr>
	 <th>申请人：</th>
	    <td><input type="text" class ="ipttxt" name="apply_name" value="${apply_name}"></td>
	    <th>支付状态：</th>
	    <td>
	         <select class="ipttxt" id="pay_status" name="pay_status" >
		          <option value="-1">--请选择--</option>
		          <option value='nopay'>未支付</option>
		          <option value="payed">已支付</option>
	         </select>
	    </td>
	       <script type="text/javascript">
	           $("#pay_status option[value='${pay_status}']").attr("selected",true);
	       </script>
	    <th></th><td> <input class="comBtn" type="submit" name="searchBtn"  id="searchBtn" value="搜索" style="margin-right:10px;"/></td>
	 </tr>
	</table>
     <div style="clear:both"></div>
</div>
</form>

<form>
<div class="grid">
<grid:grid  from="webpage"  ajax="yes">
 <grid:header>
		
		<grid:cell  width="140px">支付订单号</grid:cell> 
		<grid:cell  width="140px">清单ID</grid:cell> 
		<grid:cell  width="60px">支付状态</grid:cell>
		<grid:cell  width="60px">支付金额</grid:cell>
		<grid:cell  width="60px">结算时间</grid:cell>
		<grid:cell  width="140px">支付流水号</grid:cell>
		<grid:cell  width="60px">申请人</grid:cell>
		<grid:cell  width="140px">申请单号</grid:cell>
	    <grid:cell  width="60px">操作</grid:cell>
  </grid:header>
  <grid:body item="commissionPayment" > 
        <grid:cell>&nbsp;${commissionPayment.pay_order_id} </grid:cell>
        <grid:cell>&nbsp;<a
						href="<%=request.getContextPath() %>/shop/admin/commission!turnToDetail.do?from=list&commissionDetail.list_id=${commissionPayment.list_id}"
						class="auditmodify">${commissionPayment.list_id}</a> </grid:cell>
        <grid:cell>&nbsp;
            <c:if test="${commissionPayment.pay_status=='nopay'}">未支付</c:if>
            <c:if test="${commissionPayment.pay_status=='payed'}">已支付</c:if>
            <c:if test="${commissionPayment.pay_status=='paying'}">正在支付</c:if>
        </grid:cell>
         <grid:cell>${commissionPayment.amount}</grid:cell>
         <grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${commissionPayment.payd_time}"></html:dateformat> </grid:cell>
         <grid:cell><span class="transaction_id">${commissionPayment.transaction_id}</span></grid:cell>
         <grid:cell>${commissionPayment.apply_name}</grid:cell>
         <grid:cell>${commissionPayment.apply_id}</grid:cell>
        <grid:cell>
             
          <c:if test="${commissionPayment.pay_status=='nopay'}">
            <a title ="支付" amount=${commissionPayment.amount } apply_id = "${commissionPayment.apply_id }" apply_user_id="${commissionPayment.apply_user_id }" href="javascript:;" class="p_prted">支付</a>
          </c:if>
        </grid:cell>
  </grid:body>
  
</grid:grid>
</div>
<div id="payDlg"></div>
<div id="isExistsDlg"></div>
</form>


<script>
var apply_id;
var apply_user_id;
var amount;
 $(function(){
   Eop.Dialog.init({id:"payDlg",modal:false,title:"佣金支付",width:'800px'});
   $(".p_prted").click(function(){
      apply_id = $(this).attr("apply_id");
      apply_user_id = $(this).attr("apply_user_id");
      amount = $(this).attr("amount");
      
      var trans_url = ctx + "commissionApply!isExistTrans.do?ajax=yes&apply_id="+apply_id;
      Cmp.ajaxSubmit('isExistsDlg', '', trans_url, {}, PaymentList.jsonBack,'json');
   });
 });
 
 var PaymentList = {
 	jsonBack:function(responseText){
 		if(responseText.result == 0){
 			var url ="/shop/admin/commissionApply!payCfgList.do?ajax=yes";
		    $("#payDlg").load(url,{apply_id:apply_id,apply_user_id:apply_user_id,amount:amount});
		    Eop.Dialog.open("payDlg");
 		}else{
 			alert("此订单已支付，请勿重新支付！");
 		}
 	}
 };
</script>
