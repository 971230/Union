<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
  String order_is_his = (String)request.getAttribute("order_is_his");
  pageContext.setAttribute("order_is_his_page_flag", order_is_his);
  String order_id = (String)request.getAttribute("order_id");
  String county_name = (String)request.getAttribute("county_name");
  
  String order_from  = "";
  String customerType = "";
  String sku = "";
  String hasTypeOfProduct = "";
  String color_name="";
  String size = "";//容量
  String termType="";//货品类型
  String termCat="";//货品小类
  String termianlMachine_code="";//机型编码
  String termBrand_name="";//品牌
  String termModel_name="";//型号
  String term_cat_id=""; 
  String DEFAULT_STR_ONE="";
  String monthFee="";//套餐档次
  String payType="";//付费类型
  String plan_title="";//套餐名称
  String offerType="";//货品类型
  String offerCat="";//货品小类
  String offer_cat_id="";
  String DEFAULT_STR_ONE_10001="";
  String OUT_PACKAGE_ID="";
  String heyueName="";//合约名称
  String package_limit="";//合约期限
  String contractType="";//货品类型
  String contractCat="";//货品小类
  String contract_type_id="";
  String contract_cat_id="";
  String adsl_addr_desc="";
  String moderm_name="";
  
  if(order_is_his!=null&&EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){
	 order_from  = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.ORDER_FROM);
	 customerType = CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.CUSTOMER_TYPE);
	 sku=CommonDataFactory.getInstance().getGoodSpecHis(order_id,  null, SpecConsts.SKU);
	 hasTypeOfProduct=CommonDataFactory.getInstance().hasTypeOfProductHis(order_id, SpecConsts.TYPE_ID_10000);
	 color_name=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME);
	 size=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE);
	 termType=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.TYPE_ID);
	 termCat= CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.CAT_ID);
	 termianlMachine_code= CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MACHINE_CODE);
	 termBrand_name=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME);
	 termModel_name=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MODEL_NAME) ;
	 term_cat_id=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.CAT_ID);
	 DEFAULT_STR_ONE=CommonDataFactory.getInstance().hasTypeOfProductHis(order_id, SpecConsts.TYPE_ID_10002);
	 monthFee=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.MONTH_FEE) ;
	 payType=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
	 plan_title=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE);
	 offerType=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.TYPE_ID);
	 offerCat=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
	 offer_cat_id=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
	 DEFAULT_STR_ONE_10001=CommonDataFactory.getInstance().hasTypeOfProductHis(order_id, SpecConsts.TYPE_ID_10001);
	 OUT_PACKAGE_ID=CommonDataFactory.getInstance().getAttrFieldValueHis(order_id,AttrConsts.OUT_PACKAGE_ID);
	 heyueName=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME) ;
	 package_limit=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT);
	 contractType=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID);
	 contractCat=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID);
	 contract_type_id=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID);
	 contract_cat_id=CommonDataFactory.getInstance().getProductSpecHis(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID);
	 if(order_from.equals("10078")) {
		 adsl_addr_desc= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest().get(0).getAdsl_addr_desc();
		 moderm_name=CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest().get(0).getModerm_name();
	 }
  }else{
	  order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
	  customerType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
	  sku=CommonDataFactory.getInstance().getGoodSpec(order_id,  null, SpecConsts.SKU);
	  hasTypeOfProduct=CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10000);
	  color_name=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME);
	  size=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE);
	  termType=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.TYPE_ID);
	  termCat= CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.CAT_ID);
	  termianlMachine_code= CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MACHINE_CODE);
	  termBrand_name=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME);
	  termModel_name=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MODEL_NAME) ;
	  term_cat_id=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.CAT_ID);
	  DEFAULT_STR_ONE=CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10002);
	  monthFee=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.MONTH_FEE) ;
	  payType=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
	  plan_title=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PLAN_TITLE);
	  offerType=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.TYPE_ID);
	  offerCat=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
	  offer_cat_id=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.CAT_ID);
	  DEFAULT_STR_ONE_10001=CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10001);
	  OUT_PACKAGE_ID=CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.OUT_PACKAGE_ID);
	  heyueName=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.GOODS_NAME) ;
	  package_limit=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.PACKAGE_LIMIT);
	  contractType=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID);
	  contractCat=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID);
	  contract_type_id=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.TYPE_ID);
	  contract_cat_id=CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, null, SpecConsts.CAT_ID);
	  if(order_from.equals("10078")) {
			 adsl_addr_desc= CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest().get(0).getAdsl_addr_desc();
			 moderm_name=CommonDataFactory.getInstance().getOrderTree(order_id).getOrderAdslBusiRequest().get(0).getModerm_name();
		 }
  }
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
                                <td><%= sku %></td>
                               
                                
                              <!-- 	<th><span>*</span>货品说明：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;" /></td>
                              -->
                              <!--  
                             	<th><span>*</span>是否虚拟货品：</th>
                              	<td>
                                    <html:orderattr  attr_code="DIC_IS_VIRTUAL" order_id="${order_id}" field_name="specification_code"  field_desc ="是否虚拟货品" field_type="select"></html:orderattr>
                              	</td>
                              	-->
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                          
                    	</table>
                    	 <%if(order_from.equals("10078")){%>
                    	<h3>宽带基本信息：</h3>
                    		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                    		 <tr>
                           		 <th><span>*</span>标准地址：</th>
                                <td><%= adsl_addr_desc %></td>
                                
                                 
                                <th><span>*</span>光猫名称：</th>
                                <td><%= moderm_name %></td>
                                
                                <th><span>*</span>营业县分：</th>
                                <td><%= county_name %></td>
                            </tr>
                    		</table>
                    	<%} %>
                    </div> 
                    <!-- 货品基本信息结束 -->
                    <!-- 终端信息开始 -->
                   
                   <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(hasTypeOfProduct)){
                	%>
                   <div class="grid_n_cont_sub">
                      	<h3>终端信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>颜色：</th>
                              	<td>
                              	<input name="term.color_name"  value="<%=color_name %>" disabled="disabled"  class="ipt_new" style="width:200px;" />
                              	</td>
                              	<th><span>*</span>容量：</th>
                              	<td><input name="size" type="text" disabled="disabled" class="ipt_new" style="width:200px;"  value="<%=size %>" readonly="readonly"/></td>
                              	<th>是否定制机：</th>
                              	<td>
                                	<html:orderattr  attr_code="DC_IS_OR_NO" disabled="disabled" order_id="${order_id}" field_name="is_customized"  field_desc ="是否虚拟货品" field_type="select"></html:orderattr>
                                </td>
                          	</tr>
                          	<tr>
                          	    <th>终端串号：</th>
                          	    <td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="terminal_num"  field_desc ="串号" field_type="input" ></html:orderattr></td>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                              	    <html:selectdict name="term.type"  disabled="true" curr_val="<%=termType%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                             	</td>
                               	<th><span>*</span>货品小类：</th>
                               	<td>
                               	   <select name="term.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	      <option value='<%=termCat%>' selected="selected"></option>
                               	   </select>
                               	</td>
                            </tr>
                            <tr> 
                              	<th><span>*</span>机型编码：</th>
                              	<td>
                              
                              	<input  name="termianl.machine_code" field_desc="机型编码"   disabled="disabled"  style="width:200px;" class="ipt_new" type="text" value="<%=termianlMachine_code%>"  />
                              	
                              	<th><span>*</span>品牌：</th>
                              	<td>
                                  <input type="text" name="term.brand_name" class='ipt_new'  disabled="disabled" value="<%=termBrand_name %>" style="width:200px;"/>
                              	</td>
                              	<th><span>*</span>型号：</th>
                              	<td>
                                  <input type="text" name="term.model_name" class='ipt_new'  disabled="disabled" value="<%=termModel_name%>" style="width:200px;"/>
                              	</td>
                              	
                            </tr>
                            <script type="text/javascript">
                               var term_cat_id = '<%=term_cat_id%>';
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
                     <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(DEFAULT_STR_ONE)){%>
                    <div class="grid_n_cont_sub">
                      	<h3>套餐信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>ESS套餐编码：</th>
                              	<td><html:orderattr   order_id="${order_id}" field_name="out_plan_id_ess"  field_desc ="ESS套餐编码" field_type="input"></html:orderattr></td>
                              	<th><span>*</span>套餐档次：</th>
                              	<td><input name="month_fee" type="text" readonly="readonly" class="ipt_new" value="<%=monthFee%>" id="textfield" style="width:200px;" /></td>
                              	<th>付费类型：</th>
                              	<td>
                                  <html:selectdict  curr_val="<%=payType %>"    attr_code="DIC_ORDER_PAY_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                               	</td>
                          	</tr>
                            <tr>
                              	<th>套餐名称：</th>
                              	<td><input type="text"  name="plan_title" disabled="disabled" style="width:200px;"  value="<%=plan_title %>" /></td>
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
                               	  <html:selectdict name="offer.type"  disabled="true" curr_val="<%=offerType%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                               	</td>
                               	<th><span>*</span>货品小类：</th>
                               	<td>
                               	  <select name="offer.cat"  disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	      <option value='<%=offerCat%>' selected="selected"></option>
                               	  </select>
                                 </td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                                
                            </tr>
                            <script type="text/javascript">
                               var offer_cat_id = '<%=offer_cat_id%>';
                               TypeFun.getCatList("offer.type","offer.cat");
                            </script>
                            <tr>
		                          <th>可选包：</th>
		                          <td colspan="5">
		                                  <% 
		                                   int  packageListSize = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests().size();
		                                   for(int i=0;i<packageListSize;i++){
		                                	    String  packageName = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests().get(i).getPackageName();
		                                     	String  elementName = CommonDataFactory.getInstance().getOrderTree(order_id).getPackageBusiRequests().get(i).getElementName();
		                                     	out.print(packageName+"："+elementName);
		                                     	if(i!=packageListSize-1){
		                                     		out.print("，");
		                                     	}
		                                     		
		                                    }
		                                   %>
		                          </td>
		                 </tr>
                      	</table>
                      	
               	  	</div>
               	  	<%} %>
               	  	<!-- 套餐信息结束 -->
               	  	<!-- 合约计划开始 -->
               	  	  <%if(EcsOrderConsts.DEFAULT_STR_ONE.equals(DEFAULT_STR_ONE_10001)){%>
               	  	<div class="grid_n_cont_sub">
                      	<h3>合约计划：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th><span>*</span>合约编码：</th>
                              	<td>
                              	<input  type="text" disabled="disabled" class="ipt_new" value="<%=OUT_PACKAGE_ID %>" id="textfield" style="width:200px;" />
                              	</td>
                              	<th><span>*</span>合约名称：</th>
                              	<td><input  type="text" disabled="disabled" class="ipt_new" value="<%=heyueName%>" id="textfield" style="width:200px;" /></td>
                                <th><span>*</span>合约期限：</th>
                              	<td><input name="package_limit" type="text" disabled="disabled" class="ipt_new" value="<%=package_limit %>" id="textfield" style="width:200px;" /></td>
                           
                             
                          	</tr>
                            <tr>
                              	<th><span>*</span>货品类型：</th>
                              	<td>
                              	   <html:selectdict name="contract.type"  disabled="true" curr_val="<%=contractType%>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                              	</td>
                               	<th><span>*</span>货品小类：</th><!-- 就是合约类型 -->
                               	<td>
                               	  <select name="contract.cat" disabled="disabled"  style="width:200px;" class="ipt_new" >
                               	     <option value='<%=contractCat%>' selected="selected"></option>
                               	  </select>
                               	</td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                            <script type="text/javascript">
                              var contract_type_id = '<%=contract_type_id%>';
                              var contract_cat_id = '<%=contract_cat_id%>';
                             $("[name='contract.type'] option[value='"+contract_type_id+"']").attr("selected","selected");
                             TypeFun.getCatList("contract.type","contract.cat");
                            </script>
                    	</table>
                    	<h3>活动下自选包：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
						<c:forEach var="orderActivityBusiRequest" items="${orderTree.orderActivityBusiRequest}">
						<c:if test="${orderActivityBusiRequest.activity_type_zhufu=='1' }">
							<%{ int activity_package_count = 0;%>
							<c:forEach var="attrPackageActivityBusiRequest" items="${orderTree.attrPackageActivityBusiRequest }">
							<c:if test="${orderActivityBusiRequest.inst_id==attrPackageActivityBusiRequest.activity_inst_id }">
							<tr>
							<th>&nbsp;</th>
							<td>第<%=++activity_package_count%>包</td>
							<th>包编号：</th>
							<td>${attrPackageActivityBusiRequest.package_code }</td>
							<th>包名称：</th>
							<td>${attrPackageActivityBusiRequest.package_name }</td>
							</tr>
							<tr>
							<th>元素编码：</th>
							<td>${attrPackageActivityBusiRequest.element_code }</td>
							<th>元素类型：</th>
							<td>
							<html:selectdict curr_val="${attrPackageActivityBusiRequest.element_type }" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_ELEMENT_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
							</td>
							<th>元素名称：</th>
							<td>${attrPackageActivityBusiRequest.element_name }</td>
							</tr>
							</c:if>
							</c:forEach>
							<%} %>
						</c:if>
						</c:forEach>
                        </table>
                  	</div>
                    <%}%>
               	  	<!-- 合约计划结束 -->