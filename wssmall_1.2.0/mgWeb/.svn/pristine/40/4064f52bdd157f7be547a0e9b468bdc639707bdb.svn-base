<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
	<form class="validate" method="post" id="confirmForm"  >
	 	<div align="center">
	 		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
		     	<tr> 
			       <th><label class="text">用户名:</label></th>
			       <td>				 
			       		<span>${partner.partner_name}</span>
			       		<input type="hidden" value="${partner.partner_id}" name="partnerId"></input>
			       </td>
			     </tr>	
			     <tr> 
			       <th><label class="text">充值金额:</label></th>
			       <td>				 
			       		<span>${amount}</span>
			       		<input type="hidden" name="amount" value="${amount}"/>
			       </td>
			     </tr>
			     <tr> 
			       <th><label class="text">支付银行:</label></th>
			       <td>	
			       		<input type="hidden" name="bankCode" value="${bank.bank_code}"/>			 
			       		<IMG src="${ctx}${bank.img_url}" alt="${bank.bank_name}" align="absmiddle" style="height:38px;width:192px;">
			       </td>
			     </tr>		      
		   	</table>
	 	</div>
	 	<div class="submitlist" align="center">
	 		<table>
			 	<tr>
			 		<td>
			 			<input  type="button"  value=" 支付成功 " class="submitBtn" name='succesBtn'/>
			 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  			<input  type="button"  value=" 支付失败 " class="submitBtn" name='failBtn'/>
			   		</td>
			   	</tr>
			</table>
		</div> 	
	</form>
</div>