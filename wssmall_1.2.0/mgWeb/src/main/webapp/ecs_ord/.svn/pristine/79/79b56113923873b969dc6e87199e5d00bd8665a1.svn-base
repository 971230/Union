<%@page import="com.ztesoft.net.framework.util.StringUtil"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
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
<%@ include file="/commons/taglibs.jsp"%>
                    <!-- 商品基本信息开始 -->
                    <div class="grid_n_cont_sub">
                        <h3>基本信息：</h3>
                        <input type="hidden"  id="specValidateMsg"  value="${specValidateMsg}"/>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th><span>*</span>商品编号：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="out_package_id"  field_desc ="商品编号" field_type="input"></html:orderattr></td>
                                <th><span>*</span>商品名称：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="input"></html:orderattr></td>
                                <th><span>*</span>商品类型：</th>
                                <td><html:orderattr  order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th>产品品牌：</th>
                                <td>
                                    <html:orderattr disabled="disabled" attr_code="DIC_BRAND_TYPE"  order_id="${order_id}" field_name="pro_brand"  field_desc ="产品品牌" field_type="select"  ></html:orderattr>
                                </td>
                                <th><span>*</span>是否总部合约：</th>
                                <td><html:orderattr  attr_code="DIC_IS_GROUP_CONTRACT" order_id="${order_id}" field_name="is_group_contract"  field_desc ="是否总部合约" field_type="select"  ></html:orderattr></td>
                                <th><span>*</span>商品价格（元）：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="sell_price"  field_desc ="商品价格" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th><span>*</span>优惠价格（元）：</th>
                                <td>
                                <html:orderattr  order_id="${order_id}" field_name="discountrange"  field_desc ="优惠价格" field_type="input"></html:orderattr></td>
                                <th><span>*</span>实收价格（元）：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="pro_realfee"  field_desc ="实收价格" field_type="input"></html:orderattr></td>
                                <th><span>*</span>商品数量：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="pro_num"  field_desc ="商品数量" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th>仓库名称：</th>
                                <td>
                                    <html:orderattr attr_code="DIC_MT_STOREHOUSE" order_id="${order_id}" field_name="house_id"  field_desc ="仓库名称" field_type="select"  ></html:orderattr>
                                </td>
                               <!--  <th>多交预存款：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;" /></td>
                                -->
                                <th>减免预存标记：</th>
                                <td><html:orderattr  order_id="${order_id}" attr_code="DIC_RELIEFPRES_FLAG" field_name="reliefpres_flag"  field_desc ="减免预存标记" field_type="select"></html:orderattr></td>
                                <th>上网时长：</th>
                                <td><input name="card_time" type="text" class="ipt_new" readonly="readonly" value="<%=CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CARD_TIME)%>" style="width:200px;" /></td>
                             
                            </tr>
                             <!--  <tr> 
                                <th>商品备注：</th>
                                <td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;" /></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>-->
                        </table>
                    </div>
                    <!-- 商品基本信息结束 -->
                    <!-- 商品参数开始 -->
                    <div class="grid_n_cont_sub">
                        <h3>商品参数：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th><span>*</span>分月返还：</th>
                                <td><input name="mon_return" type="text" class="ipt_new" readonly="readonly" value="<%=(StringUtil.isEmpty(CommonDataFactory.getInstance().getGoodSpec(order_id, null,SpecConsts.MON_RETURN))?"0":CommonDataFactory.getInstance().getGoodSpec(order_id,null,SpecConsts.MON_RETURN)) %>" style="width:200px;" /></td>
                                <th><span>*</span>手机款：</th>
                                <td><input name="mobile_price" type="text" class="ipt_new" readonly="readonly" value="<%=(StringUtil.isEmpty(CommonDataFactory.getInstance().getGoodSpec(order_id, null,SpecConsts.MOBILE_PRICE))?"0":CommonDataFactory.getInstance().getGoodSpec(order_id,  null,SpecConsts.MOBILE_PRICE)) %>" style="width:200px;" /></td>
                                <th><span>*</span>首月返还：</th>
                                <td><input name="order_return" type="text" class="ipt_new" readonly="readonly" value="<%=(StringUtil.isEmpty(CommonDataFactory.getInstance().getGoodSpec(order_id, null,SpecConsts.ORDER_RETURN))?"0":CommonDataFactory.getInstance().getGoodSpec(order_id,  null,SpecConsts.ORDER_RETURN)) %>" style="width:200px;" /></td>
                            </tr>
                            <tr>
                                <th><span>*</span>预存款：</th>
                                <td><input name="deposit_fee" type="text" class="ipt_new" readonly="readonly" value="<%=(StringUtil.isEmpty(CommonDataFactory.getInstance().getGoodSpec(order_id,  null,SpecConsts.DEPOSIT_FEE))?"0":CommonDataFactory.getInstance().getGoodSpec(order_id,  null,SpecConsts.DEPOSIT_FEE)) %>" style="width:200px;" /></td>
                                <th><span>*</span>月送费金额：</th>
                                <td><input name="mon_give" type="text" class="ipt_new" readonly="readonly" value="<%=(StringUtil.isEmpty(CommonDataFactory.getInstance().getGoodSpec(order_id,  null,SpecConsts.MON_GIVE))?"0":CommonDataFactory.getInstance().getGoodSpec(order_id, null,SpecConsts.MON_GIVE)) %>" style="width:200px;" /></td>
                                <th><span>*</span>协议期总送费金额：</th>
                                <td><input name="all_give" type="text" class="ipt_new" readonly="readonly" value="<%=(StringUtil.isEmpty(CommonDataFactory.getInstance().getGoodSpec(order_id,  null,SpecConsts.ALL_GIVE))?"0":CommonDataFactory.getInstance().getGoodSpec(order_id, null,SpecConsts.ALL_GIVE)) %>" style="width:200px;" /></td>
                            </tr>
                            <tr>
                                <th>是否全国卡：</th>
                                <td>
                                <html:selectdict name="is_union"  disabled="true" curr_val="<%=CommonDataFactory.getInstance().getGoodSpec(order_id, null,SpecConsts.IS_GROUP) %>"  appen_options="<option vlaue=''>---请选择---</option>"   attr_code="DC_IS_GROUP" style="border:1px solid; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                                </td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                    <!-- 商品参数结束 -->
                    <!-- 受理单信息开始 -->
                    <div class="grid_n_cont_sub">
                        <h3>受理单信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th>受理单模板编码：</th>
                                <td>
                                  <input type="text" class="ipt_new" disabled="disabled"  style="width:200px;" field_desc ='受理单模板编码' name="acceptanceTp" value="${acceptanceTp}"/>
                                </td>
                                <th>受理单打印模式：</th>
                                <td>
                                 <html:selectdict  curr_val="${accept_print_type}"  disabled="true"  appen_options="<option value=''>---请选择---</option>"  name="accept_print_type"   attr_code="DIC_RECEIPT_MODE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;" ></html:selectdict>
                                </td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                                <!--  <th><span>*</span>受理单打印内容：</th>
                                <td>
                                <input type="text" class="ipt_new"   style="width:200px;" field_desc ='受理单打印内容' name="acceptanceForm" value="${acceptanceForm}"/>
                                </td>
                                 -->
                            </tr>
                        </table>
                    </div>
                  <!-- 受理单信息结束 -->
		            <!-- 用户证件信息开始 -->
		            <div class="grid_n_cont_sub">
                        <h3>用户证件信息：<span id="certValidateMsg" style="color:red;">
                           <%-- <%
                             String valiMsg = "身份证检验信息：";
                             String valiCode = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACCOUNT_VALI);
                             if(EcsOrderConsts.ACCOUNT_VALI_1.equals(valiCode)){
                            	 valiMsg +="校验成功"; 
                             }else if(StringUtil.isEmpty(valiCode)){
                            	 valiMsg += "未校验";
                             }else{
                            	 valiMsg += "校验失败";
                             }
                           %>
                        <%=valiMsg%></span> | <span style="color:red;">预校验信息：
                         <%
                             String validateMsg = "";
                             String validateCode = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderItemBusiRequests().
             						get(0).getOrderItemExtBusiRequest().getEss_pre_status();
                             if(EcsOrderConsts.ESS_PRE_STATUS_1.equals(validateCode)){
                            	 validateMsg +="校验成功"; 
                             }else if(StringUtil.isEmpty(validateCode)){
                            	 validateMsg += "未校验";
                             }else{
                            	 validateMsg += "校验失败";
                             }
                           %>
                            <%=validateMsg%> --%>
                        </span></h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                                <th>证件类型：</th>
                                <td>
                                    <html:orderattr attr_code="DIC_CARD_TYPE"  order_id="${order_id}" field_name="certi_type"  field_desc ="证件类型" field_type="select" ></html:orderattr>
                                </td>
                                <th>证件号码：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="cert_card_num"  field_desc ="证件号码" field_type="input"></html:orderattr></td>
                                <th>证件地址：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="cert_address"  field_desc ="证件地址" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                                <th>客户名称：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="phone_owner_name"  field_desc ="客户名称" field_type="input"></html:orderattr></td>
                                <th>客户类型：</th>
                                <td>
                                   <html:orderattr attr_code="DIC_CUSTOMER_TYPE"   order_id="${order_id}" field_name="CustomerType"  field_desc ="客户类型" field_type="select"  ></html:orderattr>
                                </td>
                                <th>证件有效期：</th>
                                <td><html:orderattr  order_id="${order_id}" field_name="cert_failure_time"  field_desc ="证件有效期" field_type="date"></html:orderattr></td>
                            </tr>
                        </table>
                    </div>
            		<!-- 用户证件信息结束 -->