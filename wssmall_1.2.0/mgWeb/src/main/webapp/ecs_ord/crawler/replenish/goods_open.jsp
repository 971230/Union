<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
  String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
  String customerType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
  boolean custFlag = EcsOrderConsts.CUSTOMER_CUST_TYPE_JTKH.equals(customerType);
  String d_type = (String)request.getAttribute("d_type");
  String yclbtn = "";
  if("ycl".equals(d_type)){
	  yclbtn = "ycl";
	  request.setAttribute("yclbtn", yclbtn);
  }
%>
               	       	<%
						  //  不是老用户并且存在成品卡和白卡类型  和号码类型
						  String  is_old = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD);
						  String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
						  String str1 = SpecConsts.TYPE_ID_20000;//号卡
						  String str2 = SpecConsts.TYPE_ID_20001;//上网卡
						  String str3 = SpecConsts.TYPE_ID_20002;//合约机
						  if((goods_type.equals(str1)||goods_type.equals(str2)||goods_type.equals(str3))&&!EcsOrderConsts.IS_OLD_1.equals(is_old)){
						%>
						<!-- 号码开户信息开始 -->
               	  	<div class="grid_n_cont_sub">
                    	<h3>号码开户信息：</h3>
                        <table width="100%" border="0" cellspacing="0" ceqllpadding="0" class="grid_form">
                            <tr>
                              	<th>用户号码：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="input"></html:orderattr></td>
                              	<th>入网地区：</th>
                              	<td><html:orderattr  attr_code="DC_MODE_REGION"   order_id="${order_id}" field_name="net_region"  field_desc ="入网地区" field_type="select"></html:orderattr></td>
                              	<th>副卡号码：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="vicecard_no"  field_desc ="副卡号码" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th>亲情号码：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="famliy_num"  field_desc ="亲情号码" field_type="input"></html:orderattr></td>
                              	<th>是否情侣号：</th>
                              	<td><html:orderattr attr_code="DIC_IS_LOVER"    order_id="${order_id}" field_name="is_loves_phone"  field_desc ="是否情侣号" field_type="select"></html:orderattr></td>
                              	<th>情侣号：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="loves_phone_num"  field_desc ="情侣号" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>号码网别：</th>
                              	<td><input name="net_type" type="text" class="ipt_new" id="net_type" readonly="readonly" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE) %>" style="width:200px;" /></td>
                              	<th>是否靓号：</th>
                              	<td>
                              	   <html:orderattr   attr_code="DIC_IS_LIANG" order_id="${order_id}" field_name="is_liang"  field_desc ="是否靓号" field_type="select"></html:orderattr>
                                </td>
                              	<th>靓号预存：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="liang_price"  field_desc ="靓号预存" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>靓号低消：</th>
                              	<td><input name="lowest" type="text"  disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getNumberSpec(CommonDataFactory.getInstance().getAttrFieldValue(order_id, "phone_num"), SpecConsts.NUMERO_LOWEST) %>" style="width:200px;" /></td>
                              	<th>性别：</th>
                              	<td>
                                	<html:orderattr  attr_code="DIC_CUSTOMER_SEX"  order_id="${order_id}" field_name="sex"  field_desc ="性别" field_type="select"></html:orderattr>
                               	</td>
                              	<th>出生日期：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="birthday"  field_desc ="出生日期" field_type="date"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<!-- <th>通讯地址：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="cert_address"  field_desc ="通讯地址" field_type="input"></html:orderattr></td>
                              	 -->
                              	<th>账户付费方式：</th>
                              	<td>
                                	<html:orderattr  attr_code="ACC_CON_PAY_MODE"  order_id="${order_id}" field_name="bill_type"  field_desc ="账户付费方式" field_type="select"></html:orderattr>
                               	</td>
                              	<th>上级银行编码：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="superiors_bankcode"  field_desc ="上级银行编码" field_type="input"></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                              	<th>托收银行：</th>
                              	<td><html:orderattr  attr_code="DIC_BANK_NAME"  order_id="${order_id}" field_name="bank_code"  field_desc ="托收银行" field_type="select"></html:orderattr></td>
                              	
                              	<th>托收银行账号：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="bank_account"  field_desc ="托收银行账号" field_type="input"></html:orderattr></td>
                                <th>银行账户名：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="bank_user"  field_desc ="银行账户名" field_type="input"></html:orderattr></td>
                              	
                             </tr>
                            <tr> 
                              	<th>卡类型：</th>
                              	<td>
                                	<html:orderattr  attr_code="DC_PRODUCT_CARD_TYPE"  order_id="${order_id}" field_name="white_cart_type"  field_desc ="卡类型" field_type="select"></html:orderattr>
                               	</td>
                              	<th>成卡/白卡：</th><!-- 卡种类 -->
                              	<td>
                                	<html:orderattr  attr_code="DC_MODE_CARD_TYPE"  order_id="${order_id}" field_name="sim_type"  field_desc ="成卡/白卡" field_type="select"></html:orderattr>
                                </td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                              	<th>信用等级：</th>
                              	<td>
                                	<html:orderattr  attr_code="DIC_CREDIT_CLASS"  order_id="${order_id}" field_name="credit_class"  field_desc ="信用等级" field_type="select"></html:orderattr>
                                </td>
                              	<th>信用度调整：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="adjustment_credit"  field_desc ="信用度调整" field_type="input"></html:orderattr></td>
                              	<th>担保人：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="guarantor"  field_desc ="担保人" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>担保信息参数：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="guarantor_info"  field_desc ="担保信息参数" field_type="input"></html:orderattr></td>
                              	<th>被担保人证件类型：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="guarantor_certi_type"  field_desc ="被担保人证件类型" field_type="input"></html:orderattr></td>
                              	<th>被担保人证件号码：</th>
                              	<td><html:orderattr    order_id="${order_id}" field_name="guarantor_certi_no"  field_desc ="被担保人证件号码" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>账单寄送方式：</th>
                              	<td>
                                	<html:orderattr  attr_code="DIC_BILL_MAIL_TYPE"  order_id="${order_id}" field_name="bill_mail_type"  field_desc ="账单寄送方式" field_type="select"></html:orderattr>
                                </td>
                              	<th>账单寄送内容：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="bill_mail_content"  field_desc ="账单寄送内容" field_type="input"></html:orderattr></td>
                              	<th>账单收件人：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="bill_mail_rec"  field_desc ="账单收件人" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>账单寄送地址：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="bill_mail_addr"  field_desc ="账单寄送地址" field_type="input"></html:orderattr></td>
                              	<th>账单寄送邮编：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="bill_mail_post_code"  field_desc ="账单寄送邮编" field_type="input"></html:orderattr></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                    	</table>
               	  	</div>
               	  	<%} %>
               	  	<!-- 号码开户信息结束 -->
               	  	<!-- 上网硬件开始 -->
               	  	 <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10003))){%>
               	  	 <div class="grid_n_cont_sub">
                    	<h3>上网卡硬件：</h3>	  
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>颜色：</th>
                              	<td>
                                	<input name="netCard.color_name"  readonly="readonly" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.COLOR_NAME) %>"   class="ipt_new" style="width:200px;" />
                                </td>
                              	<th>品牌：</th>
                              	<td>
                                    <input type="text" name="netCard.brand_name" class='ipt_new' disabled="disabled" style="width:200px;" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.BRAND_NAME) %>" />
                                </td>
                              	<th>型号：</th>
                              	<td>
                                	<input type="text" name="netCard.model_name" class='ipt_new' disabled="disabled" style="width:200px;" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, AttrConsts.MODEL_NAME) %>" />
                               </td>
                          	</tr>
                          	<tr>
                          	    <th>机型编码：</th>
                              	<td><input  name="netCard.machine_code" style="width:200px;" field_desc="机型编码" class='ipt_new' disabled="disabled"  class="ipt_new" type="text" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.MACHINE_CODE)%>" /></td>
                                <th><span>*</span>货品类型：</th>
                              	<td>
                              	    <select name="net.type"  field_desc ="货品类型" disabled="disabled"  style="width:200px;">
                              	     <option value="">--请选择--</option>
                              	      <c:forEach var="proType" items="${proTypeList}">
                              	              <option value="${proType.type_id}" >${proType.name}</option>
                              	       </c:forEach>
                              	    </select>
                               	</td>
                               	<th><span>*</span>货品小类：</th>
                               	<td> 
                               	  <select name="net.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                                    <option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	  </select>
                               	</td>
                            </tr>
                            <script type="text/javascript">
                              var net_type_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.TYPE_ID)%>';
                              var net_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.CAT_ID)%>';
                             $("[name='net.type'] option[value='"+net_type_id+"']").attr("selected","selected");
                             TypeFun.getCatList("net.type","net.cat");
                            </script>
                      	</table>
                  	</div>
                  	<%} %>
               	  	<!-- 上网硬件结束 -->
               	  	<!-- 配件开始 -->
               	  	<%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10006))){%>
               	  	 <div class="grid_n_cont_sub">
                    	<h3>配件：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>颜色：</th>
                              	<td>
                                	<input name="accessories.color_name" readonly="readonly"  value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.COLOR_NAME) %>"    class="ipt_new" style="width:200px;" />
                                </td>
                              	<th>品牌：</th>
                              	<td>
                              	<input type="text" name="accessories.brand_name" class='ipt_new' disabled="disabled" style="width:200px;" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, AttrConsts.BRAND_NAME) %>" />
                                	
                                </td>
                              	<th>型号：</th>
                              	<td>
                              	<input type="text" name="accessories.model_name" class='ipt_new' disabled="disabled" style="width:200px;" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, AttrConsts.MODEL_NAME) %>" />
                                </td>
                          	</tr>
                          		<tr>
                          		 <th>机型编码：</th>
                              	<td><input  name="accessories.machine_code" field_desc="机型编码"  class='ipt_new' disabled="disabled" style="width:200px;" class="ipt_new" type="text" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.MACHINE_CODE)%>" /></td>
                                <th><span>*</span>货品类型：</th>
                              	<td>
                              	    <select name="accessories.type"  field_desc ="货品类型" disabled="disabled"  style="width:200px;">
                              	     <option value="">--请选择--</option>
                              	      <c:forEach var="proType" items="${proTypeList}">
                              	              <option value="${proType.type_id}" >${proType.name}</option>
                              	       </c:forEach>
                              	    </select>
                               	</td>
                               	<th><span>*</span>货品小类：</th>
                               	<td>
                               	  <select name="accessories.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	    <option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	  </select>
                               	</td>
                            </tr>
                            <script type="text/javascript">
                              var accessories_type_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.TYPE_ID)%>';
                              var accessories_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.CAT_ID)%>';
                             $("[name='accessories.type'] option[value='"+accessories_type_id+"']").attr("selected","selected");
                             TypeFun.getCatList("accessories.type","accessories.cat");
                            </script>
                      	</table>
                  	</div>
                  	<%} %>
               	  	<!-- 配件结束 -->
