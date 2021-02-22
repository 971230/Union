<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/cust_returned_add.js"></script>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">资料返档</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>        
<div class="input">
 <form  class="validate" method="post" action="" id='cust_form' validate="true">
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" dataType="string" required="true" />
 
 
   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
     <tr> 
       <th><label class="text">客户名称</label></th>
       <td><input type="text" class="ipttxt"  name="custReturned.cust_name" id="cust_name"   maxlength="60" value="${custReturned.cust_name}"  dataType="string" required="true"/>       </td>
     </tr>
     <tr>
     	<th><label class="text">客户密码</label></th>
       <td><input type="password" class="ipttxt"  name="custReturned.cust_pwd" id="cust_pwd"   maxlength="60" value="${custReturned.cust_pwd}"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text">证件类型</label></th>
       <td>
	        <html:selectdict name="custReturned.certi_type"  curr_val="${custReturned.certi_type}" attr_code="ICARD_TYPE" style='width:155px;disabled'>
			</html:selectdict>
		</td>
     </tr>
     <tr> 
       <th><label class="text">证件号码</label></th>
       <td><input type="text" class="ipttxt"  name="custReturned.certi_number" id="certi_number"   maxlength="60" value="${custReturned.certi_number}"  dataType="string" required="true"/>       </td>
     </tr>
     <tr>
     	<th><label class="text">客户地址</label></th>
       <td><input type="text" class="ipttxt"  name="custReturned.cust_addr" id="cust_addr"   maxlength="60" value="${custReturned.cust_addr}"  dataType="string" required="true"/>       </td>
     </tr>
      <tr> 
       <th><label class="text">套餐名称</label></th>
       <td><input type="text" class="ipttxt"  name="custReturned.offer_name" id="offer_name"   maxlength="60" value="${custReturned.offer_name }"  dataType="string" required="true"/>       </td>
     </tr>
    
      <tr> 
       <th><label class="text">业务号码</label></th>
       <td><input type="text" class="ipttxt"  name="custReturned.acc_nbr" id="acc_nbr"   maxlength="60" value="${custReturned.acc_nbr }"  dataType="string" required="true"/>       </td>
     </tr>
     <tr> 
       <th><label class="text">本地网</label></th>
       <td>
       	<html:selectdict name="custReturned.lan_id" curr_val="${custReturned.lan_id}" attr_code="dc_area_lan" style='width:155px;' >
		</html:selectdict>       
       </td>
     </tr>
     
     <c:if test="${custReturned.terminal_name !=null}">
	      <tr> 
	       <th><label class="text">终端名称</label></th>
	       <td><input type="text" class="ipttxt"  name="custReturned.terminal_name" id="terminal_name"   maxlength="60" value="${custReturned.terminal_name}"  dataType="string" required="true"/>       </td>
	     </tr>
	     <tr> 
	       <th><label class="text">终端串码</label></th>
	       <td><input type="text" class="ipttxt"  name="custReturned.terminal_code" id="terminal_code"   maxlength="60" value="${custReturned.terminal_code}"  dataType="string" required="true"/>       </td>
	     </tr>
     </c:if>
     
	 </table> 
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		 	<td>&nbsp;</td>
		 	<td >
	           <input  type="button"  value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		   
		</tr>
	 </table>
	</div>   
   </form>
 </div>
<script>
	$(function(){
		$("select[attr_code='dc_area_lan']").attr("disabled",true)
	})
</script>


