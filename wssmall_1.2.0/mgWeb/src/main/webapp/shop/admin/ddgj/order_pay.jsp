<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <div class="btnWarp">
 </div> -->
<form  method="post" id="order_pay_form" > 
<div class="formWarp">
<input type="hidden" name="order_id" value="${order_id }" />
<input type="hidden" id="orderId" name="orderId" value="${order_id}" />
<input type="hidden" name="beanName" value="orderPaymentServ" /> 
  <div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5000" class="closeArrow"></a>订单信息
  <div class="dobtnDiv"></div></div>
    <div id="order_tag_5000" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
            <th>订单编号：</th>
            <td>${order.order_id }</td>
            <th>下单日期：</th>
            <td><html:dateformat pattern="yy-MM-dd hh:mm:ss" d_time="${order.create_time}"></html:dateformat></td>
            <th>订单总价：</th>
            <td><fmt:formatNumber value="${order.order_amount }" type="currency" pattern="￥.00"/></td>
          </tr>
          <tr>
            <th>已收金额：</th>
            <td>￥<fmt:formatNumber value="${order.paymoney}" maxFractionDigits="2" /></td>
            <th>收款金额：</th>
            <td>
            	<input type="text" class="formIpt" style="width: 136px;" value="${order.order_amount - order.paymoney}" name="payment.money" disabled=true autocomplete="off">
            </td>
            <th>支付方式：</th>
            <td>
            	<span name="paycfgsp" payment_cfg_id="${payCfg.id }" online_flag="${payCfg.online_flag }">${payCfg.name }</span>
            </td>
          </tr>
          <c:if test="${payment_cfg_id==2}">
          	<tr>
          		<th>银行列表：</th>
          		<td colspan="5" style="text-align: left;">
          			<div id="bankList" >
			 			<c:set var="count" value="0"></c:set>	
							<c:forEach items="${bankList}" var="bank" varStatus="status">   
								<c:if test="${bank.bank_type == '00A'}">
									<c:set var="count" value="${count + 1}"></c:set>
									<%-- <input   type="radio" name="payment.bank_id"  value="${bank.bank_id}"/>&nbsp; --%>
									<input   type="radio" name="bankId"  value="${bank.bank_id}"/>&nbsp;
									<IMG src="${ctx}${bank.img_url}" alt="${bank.bank_name}" align="absmiddle" style="height:38px;width:192px;">
									<c:if test="${count%3 == 0}">
										<br/><br/>
									</c:if>
								</c:if>
						</c:forEach>
			 		</div>
          		</td>
          	</tr>
          </c:if>
        </table>
    </div>
</div> 


<div class="formWarp">
  <div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="5009" class="closeArrow"></a>环节信息<div class="dobtnDiv"></div></div>
    <div id="order_tag_5009" class="formGridDiv" >
    	<table style="width: 100%;" border="0" cellspacing="0" cellpadding="0" class="formGrid" >
          <tr>
          	<th>选择下一环节:</th>
            <td colspan="5">
            	<input type="hidden" name="flow_def_id" value="${flowDef.flow_def_id }" /> 
            	<select id="order_flow_select" name="flow_def_id_disable" disabled="disabled">
            		<c:forEach items="${flowDefList }" var="flow">
            			<option value="${flow.flow_def_id }" ${flowDef.flow_type==flow.flow_type?'selected':'' }>${flow.flow_name }</option>
            		</c:forEach>
            	</select>
            </td>
            <th style="display: none;">订单处理组:</th>
            <td style="display: none;">
            	<select id="order_group_select" name="flow_group_id">
            		<c:forEach items="${orderGroupList }" var="og">
            			<option value="${og.group_id }"  ${og.group_id==orderGroup.group_id?'selected':'' }>${og.group_name }</option>
            		</c:forEach>
            	</select>
            </td>
            <th style="display: none;">订单处理人:</th>
            <td style="display: none;">
            	<p style="height:26px;">
	            	<input type="hidden"  name="flow_user_id" value="${user.userid }" />
	            	<input name="flow_user_name" type="text" class="formIpt" value="${user.username }"  readonly="readonly" />
	            	<a href="javascript:void(0);" name="user_clear_btn" class="dobtn" style="margin-right:5px;">清空</a>
	            	<a href="javascript:void(0);" name="user_add_btn" class="dobtn" style="margin-right:5px;">+</a>
            	</p>
            	<span></span>
            </td>
          </tr>
          <tr>
          	<th>处理意见：</th>
          	<td colspan="5">
          		<input type="radio" name="flag_status" value="1" checked="checked" />通过&nbsp;&nbsp;<input type="radio" name="flag_status" value="2" />不通过&nbsp;&nbsp;
          	</td>
          </tr>
          <tr>
            <th>备注：</th>
            <td colspan="5">
            	<textarea  style="width: 95%;" name="hint" type="textarea"></textarea>
            </td>
          </tr>
          <tr>
	         <th colspan="6" style="text-align: center;">
	         	<a href="javascript:void(0);" id="order_pay_btn_submit" class="dobtn" style="margin-right:5px;">支付</a>
	         </th>
	       </tr>
        </table>
    </div>
</div> 

<div id="pay_success_div" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
          <tr>
	         <th>
	         	<a href="javascript:void(0);" id="order_pay_success_btn_submit" class="dobtn" style="margin-right:5px;">支付完成</a>
	         	<a href="javascript:void(0);" id="order_pay_fail_btn_submit" class="dobtn" style="margin-right:5px;">关闭</a>
	         </th>
	       </tr>
        </table>
</div>
</form>

<div id="select_admin_dialog"></div>
<script type="text/javascript">
	$(function(){
		Eop.Dialog.init({id:"select_admin_dialog",modal:true,title:"选择处理用户", height:"600px",width:"600px"});
		Eop.Dialog.init({id:"pay_success_div",modal:true,title:"支付结果", height:"300px",width:"300px"});
	});
</script>

