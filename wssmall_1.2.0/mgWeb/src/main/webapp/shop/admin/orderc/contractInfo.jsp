<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/contract_add.js"></script>
<form  class="validate" method="post" action="" id='contract_form' validate="true">
<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
  <tr> 
    <th><label class="text">业务号码</label></th>
    <td id="checkNo">
    		<input type="text" class="ipttxt"  name="contract.acc_nbr" id="acc_nbr"   maxlength="60" value="${contract.acc_nbr}"  dataType="string" required="true"/>	       		      
  	</td>
</tr>
<tr> 
  <th><label class="text">客户名称</label></th>
  <td><input type="text" class="ipttxt"  name="contract.cust_name" id="cust_name"   maxlength="60" value="${contract.cust_name}"  dataType="string" required="true"/>       </td>
</tr>
<tr> 
  <th><label class="text">证件类型</label></th>
  <td>
   <html:selectdict name="contract.certi_type" curr_val="${contract.certi_type}" attr_code="ICARD_TYPE" style='width:155px;'>
</html:selectdict>
  </td>
   </tr>
   <tr> 
     <th><label class="text">证件号码</label></th>
     <td><input type="text" class="ipttxt"  name="contract.certi_number" id="certi_number"   maxlength="60" value="${contract.certi_number}"  dataType="string" required="true"/>       </td>
</tr>
 <tr> 
  <th><label class="text">销售品名称</label></th>
  <td><input type="text" class="ipttxt"  name="contract.offer_name" id="offer_name"   maxlength="60" value="${contract.offer_name}"  dataType="string" required="true"/>       </td>
</tr>

<tr> 
  <th><label class="text">CRM销售品id</label></th>
  <td><input type="text" class="ipttxt"  name="contract.crm_offer_id" id="crm_offer_id"   maxlength="60" value="${contract.crm_offer_id}"  dataType="string" required="true"/>       </td>
</tr>
<tr> 
  <th><label class="text">终端名称</label></th>
  <td><input type="text" class="ipttxt"  name="contract.terminal_name" id="terminal_name"   maxlength="60" value="${contract.terminal_name}"  dataType="string" required="true"/>       </td>
</tr>
<tr> 
  <th><label class="text">终端串码</label></th>
  <td><input type="text" class="ipttxt"  name="contract.terminal_code" id="terminal_code"   maxlength="60" value="${contract.terminal_code}"  dataType="string" required="true"/>       </td>
    </tr>
 </table> 
<div class="submitlist" align="center">
 <table>
	 <tr>
	 	<td >
	 	</td>
	 	<td >
           <input  type="button"  value=" 确定 " class="submitBtn" name='submitBtn'/>
           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
	   </td>
	</tr>
 </table>
</div>   
</form>