<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="despost/js/chargeConfirm.js"></script>
<div id='wrapper'>
	<div style="position: relative;" class="tab-bar">
		<ul class="tab">
			<li class="active" id="base">预存金充值</li>
	
		</ul>
	</div>
	<div class="input">
		<form class="validate" method="post" id="confirmForm"  >
		 	<div align="center">
		 		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			     	<tr> 
				       <th><label class="text">用户名:</label></th>
				       <td>				 
				       		<span>${partner.partner_name}</span>
				       		<input type="hidden" value="${partner.partner_id}" name="partnerId" id="partnerId"></input>
				       </td>
				     </tr>
				     <c:if test="${userFlag == 'T'}">
				     	<tr> 
					       <th><label class="text">本地网</label></th>
					       <td>				 
					      		<span>${lan_name}</span>
					      		<input type="hidden" id="lan_id" name="lan_id" value="${lan_id}"/>
					       </td>
					   	</tr>
				     </c:if>	
				     <tr> 
				       <th><label class="text">充值金额:</label></th>
				       <td>				 
				       		<span>${amount}</span>
				       		<input type="hidden" id="amount" dataType="int" name="amount" value="${amount}"/>
				       </td>
				     </tr>
				     <tr> 
				       <th><label class="text">支付银行:</label></th>
				       <td>	
				       		<input type="hidden" name="bankCode" id="bankCode" value="${bank.bank_code}"/>			 
				       		<IMG src="${ctx}${bank.img_url}" alt="${bank.bank_name}" align="absmiddle" style="height:38px;width:192px;">
				       </td>
				     </tr>	
				     <tr> 
				       <th><label class="text">验证码:</label></th>
				       <td>
				       		<input type="input" id="validCode" name="validCode" class="inputstyle"  dataType="String" required="true" /> 
				       		<img id="codeImg" class="codeImg" style="height:22px;width:60px;vertical-align: middle;"/>
				       	</td>
				     </tr>			      
			   	</table>
		 	</div>
		 	<div class="submitlist" align="center">
		 		<table>
				 	<tr>
				 		<td>
				 			<input  type="button"  value=" 确认支付 " class="submitBtn" name='confirmBtn'/>
				 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  			<input  type="button"  value=" 返回修改 " class="submitBtn" name='returnBtn'/>
				   		</td>
				   	</tr>
				</table>
			</div> 	
		</form>
	</div>
	<div id="pay_dialog" align="center">
	
	</div>
	<jsp:include page="/shop/admin/pay/to_pay.jsp" flush="true" >
		<jsp:param name="level_path" value="" />
	</jsp:include>
</div>