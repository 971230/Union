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
                    <!-- 货品基本信息开始 -->
                    <div class="grid_n_cont_sub">
                    	<h3>货品基本信息：</h3>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th><span>*</span>sku：</th>
                                <td><%=CommonDataFactory.getInstance().getGoodSpec(order_id,  null, SpecConsts.SKU) %></td>
                              <%-- 	<th><span>*</span>货品说明：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;" /></td>
                              --%>
                               <%-- 
                             	<th><span>*</span>是否虚拟货品：</th>
                              	<td>
                                    <html:orderattr  attr_code="DIC_IS_VIRTUAL" order_id="${order_id}" field_name="specification_code"  field_desc ="是否虚拟货品" field_type="select"></html:orderattr>
                              	</td>
                              	 --%>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                    	</table>
                    </div>
                    <!-- 货品基本信息结束 -->
                    <!-- 终端信息开始 -->
                   
                   <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10000))){
                	%>
                   <div class="grid_n_cont_sub">
                      	<h3>终端信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>颜色：</th>
                              	<td>
                              	<input name="term.color_name"  value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME) %>" disabled="disabled"  class="ipt_new" style="width:200px;" />
                              	</td>
                              	<th><span>*</span>容量：</th>
                              	<td><input name="size" type="text" disabled="disabled" class="ipt_new" style="width:200px;"  value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE) %>" readonly="readonly"/></td>
                              	<th>是否定制机：</th>
                              	<td>
                                	<html:orderattr  attr_code="DC_IS_OR_NO" disabled="disabled" order_id="${order_id}" field_name="is_customized"  field_desc ="是否虚拟货品" field_type="select"></html:orderattr>
                                </td>
                          	</tr>
                          	<tr>
                          	    <th>终端串号：</th>
                          	    <td><html:orderattr  order_id="${order_id}" field_name="terminal_num"  field_desc ="串号" field_type="input" ></html:orderattr></td>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                              	    <html:selectdict name="term.type"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                             	</td>
                               	<th><span>*</span>货品小类：</th>
                               	<td>
                               	   <select name="term.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	      <option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	   </select>
                               	</td>
                            </tr>
                            <tr> 
                              	<th><span>*</span>机型编码：</th>
                              	<td>
                              
                              	<input  name="termianl.machine_code" field_desc="机型编码"   disabled="disabled"  style="width:200px;" class="ipt_new" type="text" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MACHINE_CODE)%>"  />
                              	
                              	<th><span>*</span>品牌：</th>
                              	<td>
                                  <input type="text" name="term.brand_name" class='ipt_new'  disabled="disabled" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME) %>" style="width:200px;"/>
                              	</td>
                              	<th><span>*</span>型号：</th>
                              	<td>
                                  <input type="text" name="term.model_name" class='ipt_new'  disabled="disabled" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MODEL_NAME) %>" style="width:200px;"/>
                              	</td>
                              	
                            </tr>
                            <script type="text/javascript">
                               var term_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.CAT_ID)%>';
                               TypeFun.getCatList("term.type","term.cat");
                            </script>
                        <!--   <tr>
                              	<th><span>*</span>配件：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" readonly="readonly" style="width:200px;" /></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>-->
                      	</table>
                  	</div>
                  	<%} %>
                    <!-- 终端信息结束 -->
                    <!-- 套餐信息开始 -->
                     <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10002))){%>
                    <div class="grid_n_cont_sub">
                      	<h3>套餐信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>ESS套餐编码：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="out_plan_id_ess"  field_desc ="ESS套餐编码" field_type="input"></html:orderattr></td>
                              	<th>套餐档次：</th>
                              	<td><input name="month_fee" type="text" readonly="readonly" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.MONTH_FEE) %>" id="textfield" style="width:200px;" /></td>
                              	<th>付费类型：</th>
                              	<td>
                                  <html:selectdict  curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE) %>"    attr_code="DIC_ORDER_PAY_TYPE"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                               	</td>
                          	</tr>
                            <tr>
                              	<th>套餐名称：</th>
                              	<td><input type="text"  class='ipt_new' name="plan_title" disabled="disabled" style="width:200px;"  value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE) %>" /></td>
                              	<th><span>*</span>首月资费方式：</th>
                              	<td>
                                	 <html:orderattr attr_code="DIC_OFFER_EFF_TYPE"  order_id="${order_id}" field_name="first_payment"  field_desc ="首月资费方式" field_type="select"></html:orderattr>
                                </td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                              <!-- 	<th>微信沃包生效方式：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;"  readonly="readonly"/></td>
                               -->
                            </tr>
                            <tr>
                              	<th><span>*</span>套餐是否变更：</th>
                              	<td><html:orderattr attr_code="is_change"   order_id="${order_id}" field_name="is_change"  field_desc ="套餐是否变更" field_type="select" ></html:orderattr></td>
                              	<th><span>*</span>是否iPhone套餐：</th>
                              	<td><html:orderattr attr_code="DIC_IS_IPHONE_PLAN"   order_id="${order_id}" field_name="is_iphone_plan"  field_desc ="套餐是否变更" field_type="select"  ></html:orderattr></td>
                              	<th>4G套餐类型：</th>
                              	<td>
                                	<html:orderattr attr_code="DIC_PRODUCT_TYPE"   order_id="${order_id}" field_name="bss_order_type"  field_desc ="4G套餐类型" field_type="select"  ></html:orderattr>
                                </td>
                            </tr>
                            <tr>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                               	  <html:selectdict name="offer.type"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                               	</td>
                               	<th><span>*</span>货品小类：</th>
                               	<td>
                               	  <select name="offer.cat"  disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	      <option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	  </select>
                                 </td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                                
                            </tr>
                            <script type="text/javascript">
                               var offer_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID)%>';
                               TypeFun.getCatList("offer.type","offer.cat");
                            </script>
                      	</table>
                      	
               	  	</div>
               	  	<%} %>
               	  	<!-- 套餐信息结束 -->
               	  	<!-- 合约计划开始 -->
               	  	  <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10001))){%>
               	  	<div class="grid_n_cont_sub">
                      	<h3>合约计划：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>合约编码：</th>
                              	<td>
                              	<input  type="text" disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.OUT_PACKAGE_ID) %>" id="textfield" style="width:200px;" />
                              	</td>
                              	<th><span>*</span>合约名称：</th>
                              	<td><input  type="text" disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME) %>" id="textfield" style="width:200px;" /></td>
                                <th><span>*</span>合约期限：</th>
                              	<td><input name="package_limit" type="text" disabled="disabled" class="ipt_new" value="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT) %>" id="textfield" style="width:200px;" /></td>
                           
                             <!-- 	<th><span>*</span>合约类型：</th>
                              	<td>
                                	<html:selectdict  name="package_type" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PACKAGE_TYPE) %>"  appen_options="<option value=''>---请选择---</option>"   attr_code="DIC_ORDER_PAY_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                                </td>
                                 -->
                          	</tr>
                            <tr>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                              	   <html:selectdict name="contract.type"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                              	</td>
                               	<th><span>*</span>货品小类：</th><!-- 就是合约类型 -->
                               	<td>
                               	  <select name="contract.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	     <option value='<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID)%>' selected="selected"></option>
                               	  </select>
                               	</td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                            <script type="text/javascript">
                              var contract_type_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID)%>';
                              var contract_cat_id = '<%=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID)%>';
                             $("[name='contract.type'] option[value='"+contract_type_id+"']").attr("selected","selected");
                             TypeFun.getCatList("contract.type","contract.cat");
                            </script>
                    	</table>
                  	</div>
                    <%}%>
               	  	<!-- 合约计划结束 -->