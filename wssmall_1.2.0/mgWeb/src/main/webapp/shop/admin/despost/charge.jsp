<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="despost/js/charge.js"></script>
<div id='wrapper'>
	<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">预存金充值</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
	<div class="input">
		<form class="validate" method="post" id="chargeForm"  >
			<%-- 用户信息 --%>
		 	<div align="center">
		 		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			     	<tr> 
				       <th><label class="text">用户名</label></th>
				       <td>				 
				       		<span>${partner.partner_name}</span>
				       		<input type="hidden" value="${partner.partner_id}" name="partnerId" id="partnerId"></input>
				       </td>
				     </tr>
				     <tr> 
				       <th><label class="text">当前预存款</label></th>
				       <td>				 
				       		<span>${partner.deposit}</span>			       		
				       </td>
				     </tr>
				     <c:if test="${userFlag == 'T'}">
				     	<tr> 
					       <th><label class="text">本地网</label></th>
					       <td>				 
					       		<html:selectdict name="lan_id" curr_val="${lan_id}" attr_code="dc_area_lan" style='width:155px;' >
								</html:selectdict>
								<input type="hidden" value="${userFlag}" name="userFlag" id="userFlag"></input>	
					       </td>
					   	</tr>
				     </c:if>
				     <tr> 
				       <th><label class="text">充值金额</label></th>
				       <td>	
				       		<input type="text" class="ipttxt"  name="amount" id="amount"   maxlength="60" value=""  dataType="int" required="true"/>				       					 
				       </td>
				     </tr>
				     <tr> 
				       <th vertical-align="middle"><label class="text">会员备注</label></th>
				       <td>   
				       		<textarea rows="5" cols="25" name = "comments"></textarea>
				       </td>
				     </tr>				      
			   	</table>
		 	</div>
		 	<%-- 银行列表 --%>
		 	<div align="center" id="bankList">
		 		
		 	</div>
		 	
		   	<div class="submitlist" align="center">
		 		<table>
				 	<tr>
				 		<td>
				  			<input  type="button"  value=" 充 值 " class="submitBtn" name='submitBtn'/>
				   		</td>
				   	</tr>
				</table>
			</div>
		</form>
	</div>
</div>