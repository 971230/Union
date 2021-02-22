<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="despost/js/editAccount.js"></script>

<div>
<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">冻结解冻预存金额</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>
	<div class="input">
		 <form class="validate" method="post"  id="accountForm"  >
		 	<input type="hidden" id="account_amount" name="partnerAccount.account_amount" value="${partnerAccount.account_amount}"></input>
		 	<div align="center">
		 		<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
			     	<tr> 
				       <th><label class="text">会员用户名</label></th>
				       <td>				 
				       		<span>${partner.partner_name}</span>
				       		<input type="hidden" value="${partner.partner_id}" name="partnerId"></input>
				       </td>
				     </tr>
				     <tr> 
				       <th><label class="text">当前预存款</label></th>
				       <td>	
				       		<span>￥${partnerAccount.account_amount}</span>
				       </td>
				     </tr>
				     <tr> 
				       <th><label class="text">已冻结预存款</label></th>
				       <td>	
				       		<span>￥${partnerAccount.frost_deposit}</span> <span><a  class="p_prted" id='show_change_log_a' style='color: #0081CC;cursor:hand;padding-left:20px;'>查看变更信息</a></span>
				       </td>
				     </tr>
				     <tr> 
				       <th><label class="text">冻结|解冻金额</label></th>
				       <td>
				       		<html:selectdict name="partnerFreezeLog.op_type" attr_code="DC_OP_TYPE"></html:selectdict>
				       		<input type="text" class="ipttxt"  name="partnerFreezeLog.amount" id="amount"   maxlength="60" value=""  dataType="int" required="true"/>
				       </td>
				     </tr>
				     <tr> 
				       <th vertical-align="middle"><label class="text">备注</label></th>
				       <td>   
				       		<textarea rows="5" cols="25" name = "partnerFreezeLog.comments"></textarea>
				       </td>
				     </tr>
			   	</table>
		 	</div>
		   	<div class="submitlist" align="center">
		 		<table>
				 	<tr>
				 		<td>
				  			<input  type="button"  value=" 确定 " class="submitBtn" name='submitBtn'/>
	           				<input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
	           			<!--  	<input type="button"  value=" 返回列表 " class="submitBtn" name='returnBtn'></input>-->
				   		</td>
				   	</tr>
				</table>
			</div>
		</form>
	</div>
</div>

<div id='show_change_log_div' style='display:none;'>
	<!-- 
		<form action="freeze!list.do" method="post">		
			<div class="searchformDiv">
				
		    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
		    	    <tbody><tr>
		    	      <th>冻结日期:</th>	    	      
		    	      <td valign="middle">&nbsp;
		    	      	<input type="hidden" name="partnerId" value="${partnerId}"></input>
		    	      	<input type="text"  name="beginTime" 
		    	      		value="${beginTime}" 
		    	      		maxlength="60" dataType="date" size="10" required="true" class="dateinput ipttxt"/>
		    	      	到	
		    	      	<input type="text"   name="endTime" 
		    	      		value="${endTime}" 
		    	      		maxlength="60" dataType="date" size="10" required="true" class="dateinput ipttxt"/>
		    	      </td>	    	    
		    	      <th>冻结类型:</th>
		    	      <td>
							<div id="typespan">
								<html:selectdict name="opType" appen_options="<option value=''>--全部--</option>" curr_val="${opType}" attr_code="DC_OP_TYPE">
								</html:selectdict>
							</div>
		    	      </td>
		    	      <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
		  	      </tr>
		  	    </tbody>
		  	    </table>
	    	</div>
		</form>
	 -->
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell sort="op_time">操作日期</grid:cell>
				<grid:cell sort="op_type">操作类型</grid:cell>
				<grid:cell sort="amount">冻结|解冻的预存金</grid:cell>
				<grid:cell sort="old_amount">操作前可用预存金</grid:cell>
				<grid:cell sort="new_amount">操作后可用预存金</grid:cell>
				<grid:cell sort="comments">备注</grid:cell>
			</grid:header>
			<grid:body item="freezeLog">
				<grid:cell>&nbsp;
					<html:dateformat pattern="yyyy-MM-dd" d_time="${freezeLog.op_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>&nbsp;
					${freezeLog.op_type } 
				</grid:cell>
				<grid:cell>&nbsp;
					<fmt:formatNumber value="${freezeLog.amount}" type="currency"/>
				</grid:cell>
				<grid:cell>&nbsp;
					<fmt:formatNumber value="${freezeLog.old_amount}" type="currency"/>
				</grid:cell>
				<grid:cell>&nbsp;
					<fmt:formatNumber value="${freezeLog.new_amount}" type="currency"/>
				</grid:cell>
				<grid:cell>&nbsp;
					${freezeLog.comments} 
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
