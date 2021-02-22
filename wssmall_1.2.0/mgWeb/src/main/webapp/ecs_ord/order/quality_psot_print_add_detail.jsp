<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
     
	

	<tr>
		<th>原寄地:</th>   
		<td><input type="text"  maxlength="16"  class="ipt_new"	style="width:200px;" id="origin" name="prints.origin"	value="${postModeMap.origin }" /></td>
		<th>物流收件员:</th>
		<td><input type="text" maxlength="12"  class="ipt_new"	style="width:200px;" id="pickup_user"	name="prints.pickup_user" value="${postModeMap.pickup_user }" /></td>
	</tr>
	<tr>
		<th>发件公司:</th>
		<td><input type="text" maxlength="32"  class="ipt_new"	style="width:200px;" id="post_comp" name="prints.post_comp"	value="${postModeMap.post_comp }" /></td>
		<th>发件区号:</th>
		<td><input type="text" maxlength="32" class="ipt_new"	style="width:200px;" id="post_code"	name="prints.post_code" value="${ postModeMap.post_code}" /></td>
	</tr>
	<tr>
		<th>发件人:</th>
		<td><input type="text" maxlength="16"  class="ipt_new"	style="width:200px;" id="post_link_man" name="prints.post_link_man"	value="${postModeMap.post_linkman }" /></td>
		<th>发件人电话:</th>
		<td><input type="text" maxlength="32" class="ipt_new"	style="width:200px;" id="post_tel"	name="prints.post_tel" value="${postModeMap.post_tel }" /></td>
	</tr>
	
	<tr>
		<th>发件地址:</th>
		<td colspan="3"><input type="text"  maxlength="128" class="ipt_new"	style="width:400px;" id="post_address" name="prints.post_address"	value="${postModeMap.post_address }" /></td>
	</tr>
	<tr>
		<th>收款账号:</th>
		<td><input type="text"  maxlength="32" class="ipt_new"	style="width:200px;" id="receiv_account" name="prints.receiv_account"	value="${postModeMap.receiv_account }" /></td>
		<th>月结账号:</th>
		<td><input type="text" maxlength="32" class="ipt_new"	style="width:200px;" id="monthly_payment"	name="prints.monthly_payment" value="${postModeMap.monthly_payment }" /></td>
	</tr>
	<tr>
		<th>客户编码:</th>
		<td><input type="text" maxlength="20" class="ipt_new"	style="width:200px;" id="customer_account" name="prints.customer_account"	value="${postModeMap.customer_account }" /></td>
		<th>邮费支付方式:</th>
		<td>
		<select name="prints.postage_mode" id="postage_mode" >
			<c:forEach items="${postPayModeList }" var="pm">
				<option value='${pm.pkey }' ${pm.pkey == prints.postage_mode ? 'selected=selected' : ''} >${pm.pname }</option>
			</c:forEach>
		</select>
	</tr>
	
	<tr>
		<th>基本邮资:</th>
		<td><input type="text" maxlength="4" class="ipt_new"	style="width:200px;" id="carry_fee" name="prints.carry_fee"	value="" onchange="this.value=this.value.replace(/\D/g,'');" />元</td>
		<th>计费重量:</th>
		<td><input type="text" maxlength="6" class="ipt_new"	style="width:200px;" id="weight"	name="prints.weight" value=""  onchange="this.value=this.value.replace(/\D/g,'');" />克</td>
	</tr>
	
	<tr>
		<th>声明物品:</th>
		<td colspan="3">
		<textarea rows="2" cols="74" id="post_items" name="prints.post_items" >${prints.post_items }</textarea>
		</td>
		
	</tr>
	
	<tr>
		<th>是否代收:</th>
		<td>
		<c:if test="${prints.is_cod=='1' }">
		  <input type="hidden" name="prints.is_cod"  value="1"/>
		  <select  id="is_cod"  disabled="disabled"  >
			<c:forEach items="${isCodList }" var="pm">
				<option value='${pm.pkey }' ${pm.pkey == prints.is_cod ? 'selected=selected' : ''} >${pm.pname }</option>
			</c:forEach>
		  </select>
		</c:if>
		<c:if test="${prints.is_cod!='1' }">
		   <select name="prints.is_cod" id="is_cod" onchange="changeCOD()">
			<c:forEach items="${isCodList }" var="pm">
				<option value='${pm.pkey }' ${pm.pkey == prints.is_cod ? 'selected=selected' : ''} >${pm.pname }</option>
			</c:forEach>
		  </select>
		</c:if>
		
		<th><div class='daishou' ${prints.is_cod==1 ? 'style=display:block' : 'style=display:none' } >代收货款:</div></th>
		<td><div class='daishou' ${prints.is_cod==1 ? 'style=display:block' : 'style=display:none' } >
		<input type="hidden" id="cod_price"	name="prints.cod_price"  value="${prints.is_cod==1 ? prints.cod_price : '' }"  />
		<input type="text" class="ipt_new"	style="width:200px;" id="cod_price_viwe"	  value="${prints.cod_price }"  disabled="disabled" />
		
		元</div></td>
	</tr>
	
	<tr>
		<th>是否保价:</th>
		<td>
		  <select name="prints.is_insur" id="is_insur"  onchange="changeInsur()">
			<c:forEach items="${isInsurList }" var="pm">
				<option value='${pm.pkey }' ${pm.pkey == prints.is_insur ? 'selected=selected' : ''} >${pm.pname }</option>
			</c:forEach>
		  </select>
			<th><div class='baojia' ${prints.is_insur==1 ? 'style=display:block' : 'style=display:none' } >保价金额(声明价值):</div></th>
			<td><div class='baojia' ${prints.is_insur==1 ? 'style=display:block' : 'style=display:none' } >
			<input type='text'  ${prints.is_insur==1 ?  '' : 'disabled=disabled' }  class='ipt_new'	style='width:200px;' id='insur_value'	name='prints.insur_value' value='${ prints.insur_value}'  onchange="this.value=this.value.replace(/[^\d\.]/g,'');" />
			元</div></td>
	</tr>
	
	<c:if test="${postModeMap!=null && postModeMap.is_signback!=null &&postModeMap.is_signback!=''}">
		<tr>
			<th align="right" nowrap="nowrap">是否签回单:</th>
			<td  align="left" colspan="3"><input checked="checked" type="checkbox" style="width:20px" id="is_signback"  name="prints.is_signback" value="1" />是
			</td>
		</tr>
	</c:if>
	
	<tr>
		<th align="right" nowrap="nowrap">回执单:</th>
		<td  align="left" colspan="3"><input checked="checked" type="checkbox" style="width:20px" id="receipt_detail"  name="prints.receipt_detail" value="receiptDetail" />详情单回执联
		<input checked="checked"  type="checkbox" style="width:20px"  id="receipt_designated_" name="prints.receipt_designated_" value="receiptDesignated"  />代收指定回执
	</td>
	</tr>
	<tr>
		<th align="right" nowrap="nowrap">投递要求(备注):</th>
		<td align="left" colspan="3"> 
			<textarea rows="6" cols="60" id="deliver_info"  name="prints.deliver_info">${ postModeMap.post_remarks}</textarea>
		</td>
	</tr>
	
	
	
	
	