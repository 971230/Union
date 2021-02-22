<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link href="commissionApply/commissionPay.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="commissionApply/pay.js"></script>

<form  class="validate" method="post" action="" id='commissionform' validate="true">
<input type="hidden" id="apply_id" name="apply_id" value="${apply_id}" />
<input type="hidden" id="apply_user_id" name="apply_user_id" value="${apply_user_id}" />
<input type="hidden" name="beanName" value="comminssionPaymentServ" /> 
<div class="division">
<table width="100%">
  <tbody>
  <tr>
    <th>支付Id：</th>
    <td>【${apply_id}】</td>
    <th>佣金总金额：</th>
    <td>￥<fmt:formatNumber value="${amount}" maxFractionDigits="2" /></td>
    </tr>
    <tr>
	<th>支付方式：</th>
    <td>
    	<span name="paycfgsp" payment_cfg_id="${payCfg.id }" online_flag="${payCfg.online_flag }">${payCfg.name }</span>
        <select  name="payment_cfg_id" id ="payment_cfg_id" >
             <option value="">--请选择--</option>
           <c:forEach var ="payCfgList" items ="${payCfgList}">
             <option value="${payCfgList.id }">${payCfgList.name }</option>
           </c:forEach>
        </select>
        <script type="text/javascript">
          $("#payment_cfg_id option[value='${payment_cfg_id}']").attr("selected",true);
        </script>
	</td>
	<th></th>
	<td></td>
   </tr>
  </tbody>
</table>
<table width="100%">  
  <tbody>
   <tr id="bankListDisplay">
		
				    <%-- 银行列表 --%>
				 	<div id="bankList" >
				 			<c:set var="count" value="0"></c:set>	
								<c:forEach items="${bankList}" var="bank" varStatus="status">   
									<c:if test="${bank.bank_type == '00A'}">
										<c:set var="count" value="${count + 1}"></c:set>
										<%-- <input   type="radio" name="payment.bank_id"  value="${bank.bank_id}"/>&nbsp; --%>
										<input   type="radio" name="bankId"  value="${bank.bank_id}"/>&nbsp;
										<IMG src="${ctx}${bank.img_url}" alt="${bank.bank_name}" align="absmiddle" style="height:38px;width:130px;">
										<c:if test="${count%4 == 0}">
											<br/><br/>
										</c:if>
									</c:if>
							</c:forEach>
				 	</div>
			
					
	</tr>
	  
   </tbody>
  </table>
</div>
<div class="clear:both"></div>
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td>
           <input  type="button" id="commissionPayBtn"  value="支付" class="submitBtn" name='payBtn'/>
	   </td>
	</tr>
 </table>
</div> 

</form>

