<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="zte.net.ecsord.common.StypeConsts"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String order_is_his = (String)request.getAttribute("order_is_his");
    pageContext.setAttribute("order_is_his_page_flag", order_is_his);
   	String order_id = (String) request.getAttribute("order_id");
	String order_from = "";
	String bss_refund_satus = "";
	String refund_name = "";
	String pay_type = "";
	if (order_is_his != null && EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)) {
		order_from = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.ORDER_FROM);
		bss_refund_satus = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id,AttrConsts.BSS_REFUND_STATUS);
		pay_type = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.PAY_TYPE);
	} else {
		order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
		bss_refund_satus = CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.BSS_REFUND_STATUS);
		pay_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE);
	}
	bss_refund_satus = StringUtils.isEmpty(bss_refund_satus) ? "0": bss_refund_satus;
	refund_name = AttrUtils.getInstance().getDcPublicDataByPkey(StypeConsts.DIC_BSS_REFUND_STATUS, bss_refund_satus);
	if (StringUtils.equals(pay_type, EcsOrderConsts.PAY_TYPE_HDFK)) {
		refund_name = "";
	}
%>
        	        <!--订单基本信息开始-->
        	        <div class="grid_n_cont_sub">
                        <h3>订单基本信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          	<tr>
                                <th><span>*</span>订单编号：</th>
                                <td>${order_id}</td>
                                 <th><span>*</span>外部订单编号：</th>
                                <%-- <td>${ext_order_id}</td> --%>
                                 <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="out_tid"  field_desc ="外部订单编号" field_type="text"></html:orderattr></td>
                                
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
                                <th><span>*</span>订单类别：</th>
                                <td>
                                    <html:orderattr disabled="disabled"  attr_code="DIC_SUB_ORDER_TYPE" order_id="${order_id}" field_name="tid_category"  field_desc ="订单类别" field_type="select" ></html:orderattr>
                            	</td>
                                <th><span>*</span>归属地市：</th>
                                <td>
                                    <html:orderattr disabled="disabled"  attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select"></html:orderattr>
                            	</td>
                                
                          	</tr>
                          	<tr>
                          	    <th><span>*</span>渠道标记：</th>
                                <td>
                                   <html:orderattr disabled="disabled"  attr_code="DIC_CHANNEL_MARK" order_id="${order_id}" field_name="channel_mark"  field_desc ="渠道标识" field_type="select"></html:orderattr>
                            	 </td>
                                
                                <%--
                                <td><html:orderattr  order_id="${order_id}" attr_code='DEVELOPMENT_CODE' field_name="development_code"  field_desc ="发展人编码" field_type="select"></html:orderattr></td>
                                --%>
                                <th>发展人编码：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}"  field_name="develop_code_new"  field_desc ="发展人编码" field_type="input"></html:orderattr></td>
                                <th>发展人名称：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="develop_name_new"  field_desc ="发展人名称" field_type="input"></html:orderattr></td>
                                  <script>
		                          	<%-- $("[field_name='development_code']").blur(function(){
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
		                     	       }); --%>
		                          	     /*  $("[field_name='development_code']").change(function(){
		                          	    	   var selText  = $("[field_name='development_code'] option:selected").html();  
		                          	    	   $("[field_name='development_name']").val(selText);
		                          	       });*/
                          	    </script>
                          	</tr>
                          	<!-- 新增编码   -->
                          	<tr>
                          		<th>发展点编码：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="develop_point_code_new"  field_desc ="发展点编码" field_type="input"></html:orderattr></td>
                          	    <th>发展点名称：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="development_point_name"  field_desc ="发展点名称" field_type="input"></html:orderattr></td>
                          	</tr>
                       <%--    	<tr>
                          		<th>发展点编码：</th>
                                <td>${development_point_code }</td>
                          	    <th>发展点名称：</th>
                                <td>${development_point_name }</td>
                          	</tr>  --%>
                          	<tr>
                          	    <th>推荐人手机：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="recommended_phone"  field_desc ="推荐人手机" field_type="input"></html:orderattr></td>
                          	    <th>推荐人名称：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="recommended_name"  field_desc ="推荐人名称" field_type="input"></html:orderattr></td>
                                <th><span>*</span>下单时间：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="tid_time"   field_desc ="下单时间" field_type="date"></html:orderattr></td>
                               
                            </tr>
                            <tr>
                             <th>订单来源系统：</th>
	                         <td><html:orderattr attr_code="DIC_PLAT_TYPE" order_id="${order_id}" disabled="disabled" field_name="plat_type"  field_desc ="订单来源系统" field_type="select"></html:orderattr></td>
                             <th>外部状态：</th>
	                         <td><html:orderattr disabled="disabled"  attr_code="DIC_ORDER_EXT_STATUS"  order_id="${order_id}"  field_name="platform_status"  field_desc ="外部状态" field_type="select"></html:orderattr></td>
                             <th>订单生产模式：</th>
                             <td><html:orderattr disabled="disabled"  attr_code="DC_MODE_OPER_MODE"  order_id="${order_id}"  field_name="order_model"  field_desc ="订单生产模式" field_type="select"></html:orderattr></td>
                            <%-- <th>订单处理类型：</th>
                             <td><html:orderattr  order_id="${order_id}" attr_code="DIC_ORDER_DEAL_TYPE"  field_name="order_deal_type"  field_desc ="订单处理类型" field_type="select"></html:orderattr></td>
                            --%>
                            </tr>
                          	<tr>
                                <th><span>*</span>订单总价（元）：</th>
                                <td>  
                                <input class='ipt_new' style="width:200px;" name="order_amount" value="${order_amount}"/></td>
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
                               </tr>
                          	 <tr>
                          	   <th>第三方处理状态：</th>
                                <td>
                                  ${orderTree.orderExtBusiRequest.visible_status=='1'?'处理中':'已处理'}
                                </td>
                                 <th>总部订单号：</th>
                                <td>
                                	<html:orderattr  disabled="disabled" order_id="${order_id}" field_name="zb_inf_id"  field_desc ="总部交互订单号" field_type="input"></html:orderattr>
                                </td>
                               <th>是否预约单：</th>
                               <td><html:orderattr disabled="disabled" order_id="${order_id}"  field_name="wm_isreservation_from"  field_desc ="是否预约单" field_type="select"></html:orderattr></td>                                  
                          	 </tr>
                          	 <tr>
                                <th>关联单号：</th>
                                <td>
                                	<html:orderattr  disabled="disabled" order_id="${order_id}" field_name="related_order_id"  field_desc ="关联订单号" field_type="input"></html:orderattr>
                                </td>
	                          <th style="color:red;">退款状态：</th>
	                          <td><%=refund_name%></td>
	                          <th>特殊业务类型：</th>
	                           <td><html:orderattr disabled="disabled" attr_code="DC_BUSINESS_TYPE" order_id="${order_id }" field_name="special_busi_type" field_desc="业务类型" field_type="select" ></html:orderattr></td>
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
                                <!-- 
                                <th>证件审核状态：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="account_vali"  field_desc ="开户人身份验证" field_type="select"></html:orderattr></td>
                                -->
                                  <th>稽核信息:</th>
                            <td>
                             <c:if  test="${orderTree.orderExtBusiRequest.audit_type==0}">稽核退单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==1}">稽核退款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==2}">稽核补单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==3}">稽核补款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==4}">稽核退现金</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==null || orderTree.orderExtBusiRequest.audit_type==''}">暂无稽核信息</c:if>
                            </td>
                                <th>撤单状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.cancel_flag }" attr_code="DC_CANCEL_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                                <th>订单签收状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${delivery.sign_status }" attr_code="DC_SIGN_STATUS"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                          		
                          	</tr>
                          	<tr>
                          		<th><span>*</span>主卡号码：</th>
                                <td>${mainnumber}</td>
                          	</tr>
                          	
                        </table>
                    </div>      
        	        <!-- 订单基本信息结束 -->
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
        	        <!-- 支付信息开始 -->
        	        <div class="grid_n_cont_sub">
                      	<h3>支付信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>支付时间：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="pay_time"  field_desc ="支付时间" field_type="date"></html:orderattr></td>
                              	<th><span>*</span>支付类型：</th>
                              	<td>
                                    <html:orderattr disabled="disabled"  attr_code="DIC_PAY_TYPE" order_id="${order_id}" field_name="paytype"  field_desc ="支付类型"  field_type="select"></html:orderattr>
                            	</td>
                              	<th><span>*</span>支付方式：</th>
                              	<td>
                                   <html:orderattr disabled="disabled"  attr_code="DIC_PAY_WAY" order_id="${order_id}" field_name="pay_method"   field_desc ="支付方式" field_type="select"></html:orderattr>
                            	</td>
                            </tr>
                            <tr>
                              	<th><span>*</span>支付状态：</th>
                              	<td>
                                     <html:orderattr attr_code="DIC_PAY_STATUS" disabled="disabled" order_id="${order_id}" field_name="pay_status"  field_desc ="支付状态" field_type="select"></html:orderattr>
                            	</td>
                              	<th>支付流水号：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="payplatformorderid"  field_desc ="支付流水号" field_type="input"></html:orderattr></td>
                              	<th>支付机构编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="payproviderid"  field_desc ="支付机构编码" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>支付机构名称：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="payprovidername"  field_desc ="支付机构名称" field_type="input"></html:orderattr></td>
                              	<th>支付渠道编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="paychannelid"  field_desc ="支付渠道编码" field_type="input"></html:orderattr></td>
                              	<th>支付渠道名称：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="paychannelname"  field_desc ="支付渠道名称" field_type="input"></html:orderattr></td>
                            </tr>
                             <tr>
                               <th>基金类型：</th>
                               <td><html:orderattr disabled="disabled" attr_code="DC_ISFUND_TYPE" order_id="${order_id}" field_name="fund_type"  field_desc ="基金类型" field_type="select"></html:orderattr></td>
                               <th>兑换积分：</th>
                               <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="order_points"  field_desc ="兑换积分" field_type="input"></html:orderattr></td>
                               <th>&nbsp;</th>
                               <td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                    <!-- 支付信息结束 -->
                    <!-- 物流信息开始 -->
                    <div class="grid_n_cont_sub">
                      	<h3>物流信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                            	<th><span>*</span>物流单号：</th>
                            	<td><html:orderattr  order_id="${order_id}" field_name="logi_no"  field_desc ="物流单号" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>应收运费（元）：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="post_fee"  field_desc ="应收运费（元）" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>实收运费（元）：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="n_shipping_amount"  field_desc ="实收运费（元）" field_type="input"></html:orderattr></td>
                              <!-- <th>物流公司编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="shipping_company"  field_desc ="物流公司编码" field_type="input"></html:orderattr></td>
                          	    -->	
                          	</tr>
                            <tr>
                              	<th>物流公司名称：</th>
                              	<td>
                                <select name="shipping_company" field_desc="物流公司" 
											id="logi_company" class="ipt_new" style="width:200px;">
												<option value="">---请选择---</option>
												<c:forEach var="logicompany" items="${logiCompanyList }">
													<option key_str="${logicompany.key_str }"
														<c:if test="${order_model=='01' && logicompany.id=='SF0001'}">selected="selected"</c:if>
														value="${logicompany.id }">${logicompany.name }</option>
												</c:forEach>
										</select> 
										<script type="text/javascript">
										var sc = '${shipping_company}';
										if(sc){
											$("[name='shipping_company'] option[value='${shipping_company}']").attr("selected","selected");
										}
		                            </script>
							    </td>
                               <th><span>*</span>是否闪电送：</th>
                              	<td>
                                    <html:orderattr  attr_code="DIC_SHIPPING_QUICK" order_id="${order_id}" field_name="shipping_quick"  field_desc ="是否闪电送" field_type="select"  ></html:orderattr>
                            	</td>
                              	<th><span>*</span>配送方式：</th>
                              	<td>
                                    <html:orderattr  attr_code="DC_MODE_SHIP_TYPE" order_id="${order_id}" field_name="sending_type"  field_desc ="配送方式" field_type="select" ></html:orderattr>
                            	</td>
                            </tr>
                            <tr>
                              	<th>配送时间：</th>
                              	<td><html:orderattr attr_code ='DIC_SHIPPING_TIME'   order_id="${order_id}" field_name="shipping_time"  field_desc ="配送时间" field_type="select"></html:orderattr></td>
                              	<th><span>*</span>收货人姓名：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="ship_name"  field_desc ="收货人姓名" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>收货省份：</th>
                              	<td>
                              	   <select name="provinc_code" field_desc="收货省份" id="provinc_code" class="ipt_new" style="width:200px;" >
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
                            </tr>
                            <tr>
                              	<th>收货地市：</th>
                              	<td>
                                     <select name="city_code" field_desc="收货地市" id="city_code" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
                              	     <c:forEach var="cityList" items="${cityList}">
                              	        <c:if test="${city_code==cityList.code}">
                              	          <option value="${cityList.code}" selected="selected">${cityList.name}</option>
                              	        </c:if>
                              	         <c:if test="${provinc_code!=cityList.code}">
                              	          <option value="${cityList.code}">${cityList.name}</option>
                              	        </c:if>
                              	     </c:forEach>
                              	   </select>
                              	</td>
                              	<th><span>*</span>收货区县：</th>
                              	<td>
                                    <select name="district_id" field_desc="收货区县" id="district_id" class="ipt_new" style="width:200px;" >
                              	     <option value="">--请选择--</option>
                              	     <c:forEach var="districtList" items="${districtList}">
                              	        <c:if test="${district_id==districtList.code}">
                              	          <option value="${districtList.code}" selected="selected">${districtList.name}</option>
                              	        </c:if>
                              	         <c:if test="${provinc_code!=districtList.code}">
                              	          <option value="${districtList.code}">${districtList.name}</option>
                              	        </c:if>
                              	     </c:forEach>
                              	   </select>
                                </td>
                              	<th>收货商圈：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_area"  field_desc ="收货商圈" field_type="input"></html:orderattr></td>
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
                              	<th><span>*</span>手机号码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="receiver_mobile"  field_desc ="手机号码" field_type="input"></html:orderattr></td>
                              	<th>电子邮件：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="ship_email"  field_desc ="电子邮件" field_type="input"></html:orderattr></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                      	</table>
                  	</div>
                    <!-- 物流信息结束 -->