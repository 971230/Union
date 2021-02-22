
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.StypeConsts"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
 <%
 	    String order_id = (String)request.getAttribute("order_id");
 	    String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
  	   String bss_refund_satus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS);
  	   String refund_satus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFUND_STATUS);
  	   bss_refund_satus = StringUtils.isEmpty(bss_refund_satus)?"0":bss_refund_satus;
  	   String refund_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_BSS_REFUND_STATUS, bss_refund_satus);
  	 String pay_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE);
  	  if(StringUtils.equals(pay_type, EcsOrderConsts.PAY_TYPE_HDFK)){
  		  refund_name ="";
  	  }
%>
        	        <!--订单基本信息开始--> 
        	        <div id="selDevelopmentDlg"></div>
        	        <div id="selXcountyDlg"></div>
        	        <div id="selStdAddressDlg"></div>
        	        <div id="selModermIDDlg"></div>
        	         <div id="selGoodsDlg"></div>
        	        <input type="hidden" id="stdOrderId" name="stdOrderId" value="${order_id}" />
        	        <div class="grid_n_cont_sub">
                        <h3>订单基本信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          	<tr>
                                <th><span>*</span>内部订单编号：</th>
                                <td>${order_id}</td>
                                <th><span>*</span>外部订单编号：</th>
                                <td>${ext_order_id}</td>
                                <th><span>*</span>订单来源：</th>
                                <td>
                                    <html:orderattr disabled="disabled"  attr_code="ORDER_FROM"  order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select" ></html:orderattr>
                                </td>
                          	</tr>
                          	<tr>
                          		<th><span>*</span>订单类型：</th>
                                <td>
                                 <html:orderattr disabled="disabled"  attr_code="DC_ORDER_NEW_TYPE" order_id="${order_id}" field_name="order_type"  field_desc ="订单类型" field_type="select" ></html:orderattr>
                            	</td>
                                <th><!-- <span>*</span> -->订单类别：</th>
                                <td>
                                    <html:orderattr attr_code="DIC_SUB_ORDER_TYPE" order_id="${order_id}" field_name="tid_category"  field_desc ="订单类别" field_type="select" ></html:orderattr>
                            	</td>
                                <th><span>*</span>归属地市：</th>
                                <td>
                                    <html:orderattr attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" disabled="disabled"></html:orderattr>
                            	</td>
                                
                          	</tr>
                          	<tr>
                          		<th><span>*</span>渠道标记：</th>
                                <td>
                                   <html:orderattr disabled="disabled" attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="channel_mark"  field_desc ="渠道标识" field_type="select"></html:orderattr>
                            	 </td>
                                <th>发展人编码：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="develop_code_new"  field_desc ="发展人编码" field_type="input"></html:orderattr></td>
                                 <th>发展人名称：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="develop_name_new"  field_desc ="发展人名称" field_type="input"></html:orderattr></td>
                          	</tr>
                          	<tr>
                                <th>发展点编码：</th>
                                <td>${development_point_code }</td>
                                <th>发展点名称：</th>
                                <td>${development_point_name }</td>
                                
                          	</tr>
                          	<tr>
                          		<th>推荐人手机：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="recommended_phone"  field_desc ="推荐人手机" field_type="input"></html:orderattr></td>
                                <th>推荐人名称：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="recommended_name"  field_desc ="推荐人名称" field_type="input"></html:orderattr></td>
                                <th><span>*</span>下单时间：</th>
                                <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="tid_time"   field_desc ="下单时间" field_type="date"></html:orderattr></td>
                                
                                  <script>
		                          	$("[field_name='development_code']").blur(function(){
		                     	    	   var devep_code = $(this).val();
		                     	    	 
		                     	    	   var order_from = "<%=order_from%>";
		                     	    	   var devepNameUrl = ctx+"/shop/admin/orderFlowAction!getDevelopnmentName.do?ajax=yes&developmentCode="+devep_code+"&order_from="+order_from;
		                     	    	   Cmp.excute('',devepNameUrl,{},function jsonBack(reply){
		                     	    		   if(reply.result==0){
		                     	    			  $("[field_name='development_name']").val(reply.developmentName);
		                     	    		   }else{
		                     	    			   alert(result.message);
		                     	    			   $("[field_name='development_code']").val("");
		                     	    		   }
		                     	    	   },'json');
		                     	       });
		                          	     /*  $("[field_name='development_code']").change(function(){
		                          	    	   var selText  = $("[field_name='development_code'] option:selected").html();  
		                          	    	   $("[field_name='development_name']").val(selText);
		                          	       });*/
                          	    </script>
                          	 
                            </tr>
                            
                            <tr>
                             <th>订单来源系统：</th>
	                         <td><html:orderattr attr_code="DIC_PLAT_TYPE" order_id="${order_id}" disabled="disabled" field_name="plat_type"  field_desc ="订单来源系统" field_type="select"></html:orderattr></td>
                             <th>外部状态：</th>
	                         <td><html:orderattr disabled="disabled" attr_code="DIC_ORDER_EXT_STATUS"  order_id="${order_id}"  field_name="platform_status"  field_desc ="外部状态" field_type="select"></html:orderattr></td>
                             <th>订单生产模式：</th>
                             <td><html:orderattr  attr_code="DC_MODE_OPER_MODE"  order_id="${order_id}"  field_name="order_model"  field_desc ="订单生产模式" field_type="select"></html:orderattr></td>
                            
                            <%-- <th>订单处理类型：</th>
                             <td><html:orderattr  order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE"  field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
                            --%>
                            </tr>
                            <tr>
                                <th style="display:none;">是否WMS发送 ：</th>
                                <td style="display:none;"><html:orderattr attr_code="DC_IS_OR_NO"  order_id="${order_id}"  field_name="is_send_wms"  field_desc ="是否WMS发送"  field_type="select"></html:orderattr></td>
                                
                               <th>异常类型：</th>
                               <td>
                                <html:selectdict name="abnormal_type"  disabled="true"  curr_val="${abnormal_type}"    attr_code="ORDER_ABNORMAL_TYPE"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                               </td>
                               <th>异常状态：</th>
                               <td><html:orderattr disabled="disabled" attr_code="DC_ABNORMAL_STATUS"  order_id="${order_id}"  field_name="abnormal_status"  field_desc ="异常状态" field_type="select"></html:orderattr></td>
                              
	                          <th>特殊业务类型：</th>
	                             <td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" ></html:orderattr></td>
	                      </tr>
                          	<tr>
                          	 	<th>总部订单号：</th>
                                <td>
                                	<html:orderattr  disabled="disabled" order_id="${order_id}" field_name="zb_inf_id"  field_desc ="总部交互订单号" field_type="input"></html:orderattr>
                                </td>
                               <th>是否预约单：</th>
                               <td><html:orderattr disabled="disabled" order_id="${order_id}"  field_name="wm_isreservation_from"  field_desc ="是否预约单" field_type="select"></html:orderattr></td>
                          	 <th>关联单号：</th>
                                <td>
                                	<html:orderattr  disabled="disabled" order_id="${order_id}" field_name="related_order_id"  field_desc ="关联单号" field_type="input"></html:orderattr>
                                </td>
                            </tr>
                          	<tr>
                                <th>实名制方式：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.later_active_flag }" attr_code="DC_LATER_ACTIVE_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                                <th>证件照上传状态：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="if_Send_Photos"  field_desc ="照片上传状态" field_type="select"></html:orderattr></td>
                                <th>激活状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.active_flag }" attr_code="DC_ACTIVE_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                          	</tr>
                            <tr>
                                <th>证件审核状态：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="account_vali"  field_desc ="开户人身份验证" field_type="select"></html:orderattr></td>
                                <th>撤单状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.cancel_flag }" attr_code="DC_CANCEL_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                                <th>订单签收状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${delivery.sign_status }" attr_code="DC_SIGN_STATUS"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                          	</tr>
                          	<tr>
                                <th><span>*</span>应收金额（元）：</th>
                                <td>  
                                <input class='ipt_new' style="width:200px;" onkeypress="if ((event.keyCode <48 && event.keyCode!=46) || event.keyCode > 57) event.returnValue = false;"
                                 name="order_amount" value="${order_amount}"/></td>
                                <th><span>*</span>优惠金额（元）：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="order_disfee"  field_desc ="优惠金额（元）" field_type="input"></html:orderattr></td>
                                <th><span>*</span>实收金额（元）：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
                          	</tr>
                          	<tr>
                                <th><span>*</span>商品原价（系统价）：</th>
                                <td>${good_price_system}</td>
                                <th><span>*</span>靓号预存（元）：</th>
                                <td>${num_price}</td>
                                <th><span>*</span>多缴预存（元）：</th>
                                <td>${deposit_price}</td>
                          	</tr> 
                            <tr>
                                <th><span>*</span>开户费（元）：</th>
                                <td>${openAcc_price}</td>
                                <th><span>*</span>对应王卡状态：</th>
                                <td>${kingcard_status}</td>
                           	<% if(!StringUtils.isEmpty(refund_satus) 
                           			&& !StringUtils.equals(refund_satus, EcsOrderConsts.REFUND_STATUS_00)){%>
	                          <th style="color:red;">退款状态：</th>
	                          <td><%=refund_name%></td>
                            <%} %>
                                <th><span>*</span>主卡号码：</th>
                                <td>${mainnumber}</td>
                            	
                            </tr>
                             	<tr>
                                <th><span></span>短信最近发送时间：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="messages_send_lasttime"  field_desc ="证件审核短信最近发送时间" field_type="input"></html:orderattr></td>
                                <th><span></span>短信发送次数：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="messages_send_count"  field_desc ="证件审核短信发送次数" field_type="input"></html:orderattr></td>
                          	</tr> 
                        </table>
                    </div>      
        	        <!-- 订单基本信息结束 -->
        	        
        	        <!-- 支付信息开始 -->
        	        <div class="grid_n_cont_sub">
                      	<h3>支付信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>支付时间：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr></td>
                              	<th><span>*</span>支付类型：</th>
                              	<td>
                                    <html:orderattr  disabled="disabled"  attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype"  field_desc ="支付类型"  field_type="select"></html:orderattr>
                            	</td>
                              	<th><span>*</span>支付方式：</th>
                              	<td>
                                   <html:orderattr disabled="disabled"   attr_code="DIC_PAY_WAY" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select"></html:orderattr>
                            	</td>
                            </tr>
                            <tr>
                              	<th><span>*</span>支付状态：</th>
                              	<td>
                                     <html:orderattr disabled="disabled" attr_code="DIC_PAY_STATUS" order_id="${order_id}" field_name="pay_status"  field_desc ="支付状态" field_type="select"></html:orderattr>
                            	</td>
                              	<th>支付流水号：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="payplatformorderid"  field_desc ="支付流水号" field_type="input"></html:orderattr></td>
                              	<th>支付机构编码：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="payproviderid"  field_desc ="支付机构编码" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>支付机构名称：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="payprovidername"  field_desc ="支付机构名称" field_type="input"></html:orderattr></td>
                              	<th>支付渠道编码：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="paychannelid"  field_desc ="支付渠道编码" field_type="input"></html:orderattr></td>
                              	<th>支付渠道名称：</th>
                              	<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="paychannelname"  field_desc ="支付渠道名称" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                               <th>基金类型：</th>
                               <td><html:orderattr disabled="disabled"  attr_code="DC_ISFUND_TYPE" order_id="${order_id}" field_name="fund_type"  field_desc ="基金类型" field_type="select"></html:orderattr></td>
                               <th>兑换积分：</th>
                               <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="order_points"  field_desc ="兑换积分" field_type="input"></html:orderattr></td>
                               <th>&nbsp;</th>
                               <td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                    <!-- 支付信息结束 -->
        	        <!-- 优惠信息开始 -->
        	  		<c:if test="${orderTree.discountInfoBusiRequests!=null}">
                    <div class="grid_n_cont_sub">
                    <h3>优惠信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                           <c:forEach var="discount" items="${orderTree.discountInfoBusiRequests}">
	                            <tr>
	                              <th>优惠活动类型：</th>
	                              <td>
	                              <html:selectdict disabled="true"  curr_val="${discount.activity_type }" attr_code="DC_DISCOUNT_TYPE"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
	                              </td>
	                              <th>代金活动编码：</th>
	                              <td>${discount.activity_code}</td>
	                              <th>代金活动名称：</th>
	                              <td>${discount.activity_name}</td>
	                            </tr>
                           </c:forEach>
                          </table>
                    </div>
             		</c:if>
        	        <!-- 优惠信息结束 -->
                    <!-- 物流信息开始 -->
                   <%-- <%if((EcsOrderConsts.IS_DEFAULT_VALUE).equals(AttrUtils.isShowShipVisiable(order_id))){ %> --%>
                    <div class="grid_n_cont_sub">
                      	<h3>物流信息：</h3> 
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>应收运费（元）：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="post_fee"  field_desc ="应收运费（元）" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>实收运费（元）：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="n_shipping_amount"  field_desc ="实收运费（元）" field_type="input"></html:orderattr></td>
                              <!-- <th>物流公司编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="shipping_company"  field_desc ="物流公司编码" field_type="input"></html:orderattr></td>
                          	    -->	
                            	<th>物流公司名称：</th>
                              	<td>
                                <select name="shipping_company" field_name="shipping_company"  field_desc="物流公司" 
											id="logi_company" class="ipt_new" style="width:200px;">
												<option value="NULL_VAL">---请选择---</option>
												<c:forEach var="logicompany" items="${logiCompanyList }">
													<option key_str="${logicompany.key_str }"
													<c:if test="${order_model=='01' && logicompany.id=='SF0001'}">selected="selected"</c:if>
														value="${logicompany.id }">${logicompany.name }</option>
												</c:forEach>
										</select> 
										<input type="hidden"  name="shipping_company_name"  id="shipping_company_name" value="${shipping_company_name}" >
									<script type="text/javascript">
										var sc = '${shipping_company}';
										if(sc){
											$("[name='shipping_company'] option[value='${shipping_company}']").attr("selected","selected");
										}
									    $("[name='shipping_company']").change(function(){
												var shipping_name =  $(this).find("option:selected").text(); 
												$("[name='shipping_company_name']").val(shipping_name);
										 });
										
		                            </script>
							    </td>
                          	</tr>
                            <tr>
                                <%-- <th><span>*</span>是否闪电送：</th>
                              	<td>
                                    <html:orderattr  attr_code="DIC_SHIPPING_QUICK" order_id="${order_id}" field_name="shipping_quick"  field_desc ="是否闪电送" field_type="select"  ></html:orderattr>
                            	</td> --%>
                            	
                              	<th><span>*</span>配送方式：</th>
                              	<td>
                                    <html:orderattr  attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select" ></html:orderattr>
                            	</td>
                            	<th>配送时间：</th>
                              	<td><html:orderattr attr_code ='DIC_SHIPPING_TIME'   order_id="${order_id}" field_name="shipping_time"  field_desc ="配送时间" field_type="select"></html:orderattr></td>
                            	<th><span>*</span>收货人姓名：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th><span>*</span>收货省份：</th>
                              	<td>
                              	   <select name="provinc_code" field_desc="收货省份" field_name="provinc_code" id="provinc_code" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
                              	     <c:forEach var="pro" items="${provList}">
                              	        <c:if test="${provinc_code==pro.code}">
                              	          <option value="${pro.code}" selected="selected">${pro.name}</option>
                              	        </c:if>
                              	         <c:if test="${provinc_code!=pro.code}">
                              	          <option value="${pro.code}">${pro.name}</option>
                              	        </c:if>
                              	     </c:forEach>
                              	   </select>
                              	</td>
                              	<th><span>*</span>收货地市：</th>
                              	<td>
                                     <select name="city_code" field_desc="收货地市" field_name="city_code" id="city_code" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
                              	     <c:forEach var="cityList" items="${cityList}">
                              	       <%--  <c:if test="${city_code==cityList.code}"> --%>
                              	          <option value="${cityList.code}" <c:if test="${city_code==cityList.code}">selected="selected"</c:if>>${cityList.name}</option>
                              	        <%-- </c:if>
                              	         <c:if test="${provinc_code!=cityList.code}">
                              	          <option value="${cityList.code}">${cityList.name}</option> 
                              	        </c:if> --%>
                              	     </c:forEach>
                              	   </select>
                              	</td>
                              	<th><span>*</span>收货区县：</th>
                              	<td>
                                    <select name="district_id" field_desc="收货区县" field_name="district_id" id="district_id" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
                              	     <c:forEach var="districtList" items="${districtList}">
                              	        <%-- <c:if test="${district_id==districtList.code}"> --%>
                              	          <option value="${districtList.code}" <c:if test="${district_id==districtList.code}">selected="selected" </c:if>>${districtList.name}</option>
                              	        <%-- </c:if>
                              	         <c:if test="${district_id!=districtList.code}">
                              	          <option value="${districtList.code}">${districtList.name}</option>
                              	        </c:if> --%>
                              	     </c:forEach>
                              	   </select>
                                </td>
                            </tr>
                            <tr>
                              	<th>收货商圈：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_area"  field_desc ="收货商圈" field_type="input"></html:orderattr></td>
                          		<th>是否保价：</th>
                              	<td>
                                    <html:orderattr attr_code="DC_IS_OR_NO"  order_id="${order_id}"  field_name="is_protect" field_value="${delivery.is_protect }" field_desc ="是否保价"  field_type="select"></html:orderattr>
                            	</td>
                            	<th>保价金额：</th>
                              	<td>
                                    <html:orderattr order_id="${order_id}" field_name="protect_price" field_desc ="保价金额" field_type="input"></html:orderattr>
                            	</td>
                          	</tr>
                            <tr>
                              	<th><span>*</span>详细地址：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_addr"  field_desc ="详细地址" field_type="input"></html:orderattr></td>
                              	<th>邮政编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_zip"  field_desc ="邮政编码" field_type="input"></html:orderattr></td>
                              	<th>固定电话：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="reference_phone"  field_desc ="固定电话" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th><span>*</span>收货电话：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input"></html:orderattr></td>
                              	<th>电子邮件：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_email"  field_desc ="电子邮件" field_type="input"></html:orderattr></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                      	</table>
                  	</div>
<script>
	$(function(){
		$("select[field_name='order_model']").change(function(){
			var order_model = $(this).val();
			if(order_model=='01'){
				$("select[field_name='is_send_wms']").val("1");
			}
			else{
				$("select[field_name='is_send_wms']").val("0");
			}
		});
	})
	$("select[field_name='order_city_code']").bind("change", function(){
	var order_city_code = $(this).val();
	$.ajax({
		type : "post",
		async : false,
		url : ctx+"/shop/admin/orderFlowAction!getLogiCompanyByCityCode.do?ajax=yes&order_city_code="+order_city_code,
		data : {},
		dataType : "json",
		success : function(data) {
			var result = data.result;
			if(result=="0"){
				var companyList = JSON.parse(data.msg);
				var selCompany = $("[name='shipping_company']");
				var order_model = $("select[field_name='order_model']").val();
				selCompany.empty();
				selCompany.append("<option value='NULL_VAL' key_str=''>--请选择--</option>")
				for(var i=0;i<companyList.length;i++){
					var company = companyList[i];
					if(order_model == "01"&&company.id=="SF0001"){
						selCompany.append("<option value='"+company.id+"' key_str='"+company.key_str+"' selected='selected'>"+company.name+"</option>");
					}else{
						selCompany.append("<option value='"+company.id+"' key_str='"+company.key_str+"'>"+company.name+"</option>");					
					}
					
				}
			}else{
				alert("error");
			}
		}
	});
	
});
</script>
                    <!-- 物流信息结束 -->