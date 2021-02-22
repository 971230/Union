<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%
  String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
%>
<%@ include file="/commons/taglibs.jsp"%>
                    <!-- 买家信息开始 -->
                    <div class="grid_n_cont_sub">
                      	<h3>买家信息：</h3>   
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>客户电话：</th>
                              	<td>
                              	  <input name="tel" value="${buyInfoMap.tel}" class="ipt_new"  style="width:200px;"/>
                              	</td>
                              	<th>客户手机：</th>
                              	<td><input name="mobile" value="${buyInfoMap.mobile}" class="ipt_new"  style="width:200px;"/></td>
                              	<th>客户名称：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="buyer_name"  field_desc ="客户名称" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th>客户联系地址：</th>
                              	<td><input name="address" value="${buyInfoMap.address}" class="ipt_new"  style="width:200px;"/></td>
                                <th><span>*</span>买家昵称：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="buyer_id"  field_desc ="买家昵称" field_type="input"></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                              	<th>买家留言：</th>
                              	<td ><html:orderattr cols="45"  style="height:50px;width:200px;" order_id="${order_id}" field_name="buyer_message"  rows="5" field_desc ="买家留言" field_type="textarea" ></html:orderattr></td>
                                <th>卖家留言：</th>
                              	<td><html:orderattr style="height:50px;width:200px;"   order_id="${order_id}" field_name="seller_message" cols="45"  rows="5" field_desc ="卖家留言" field_type="textarea" ></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                          <!--  <tr>
                              	<th>订单备注：</th>
                              	<td colspan="5"><html:orderattr  order_id="${order_id}" field_name="service_remarks" cols="45"   field_desc ="订单备注" field_type="input" style="width:200px;"></html:orderattr></td>
                            
                               <th>&nbsp;</th>
                               <td>&nbsp;</td>
                             </tr>
                               -->
                    	</table>
                  	</div>
                  	<!-- 买家信息结束 -->
                  	<!-- 集团客户信息开始 -->
                  	<div class="grid_n_cont_sub"  id="union_cust">
                    	<h3>集团客户信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>集团编码：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="group_code"  field_desc ="集团编码" field_type="input"></html:orderattr></td>
                              	<th>集团名称：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="group_name"  field_desc ="集团名称" field_type="input"></html:orderattr></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                           <tr>
                              	<th>行业应用类别：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;" /></td>
                              	<th>应用子类别：</th>
                              	<td><input name="textfield" type="text" class="ipt_new" id="textfield" style="width:200px;" /></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                            
                        </table>
               	  	</div>
               	  	<!-- 集团信息结束 -->
               	  	<!-- 沃百富信息开始 -->
               	  	<% if(EcsOrderConsts.ORDER_FROM_10039.equals(order_from)){ %>
               	  	<div class="grid_n_cont_sub">
                    	<h3>沃百富信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>百度账号：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="baidu_id"  field_desc ="百度账号" field_type="input"></html:orderattr></td>
                              	<th>百度冻结流水号：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="freeze_tran_no"  field_desc ="百度冻结流水号" field_type="input"></html:orderattr></td>
                              	<th>百度冻结款：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="freeze_free"  field_desc ="百度冻结款" field_type="input"></html:orderattr></td>
                            </tr>
                            <tr>
                              	<th>百度冻结开始时间：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="reserve5"  field_desc ="百度冻结开始时间" field_type="date"></html:orderattr></td>
                              	<th>百度冻结结束时间：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="reserve6"  field_desc ="百度冻结结束时间" field_type="date"></html:orderattr></td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                        </table>
                    </div>
                    
                     <% } %>
                    <!-- 沃百富信息结束 -->
                    <!-- 资料回收方式 -->
                    <div class="grid_n_cont_sub">
                    	<h3>资料回收方式：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>回收方式：</th>
                              	<td><html:orderattr  attr_code="DIC_RECOVERTY_TYPE" order_id="${order_id}" field_name="file_return_type"  field_desc ="回收方式" field_type="select"></html:orderattr></td>
                              	<th>回收内容：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="recyle_desc"  field_desc ="回收内容" field_type="input"></html:orderattr></td>
                              	<th>是否已上传：</th>
                              	<td>
                                    <html:orderattr attr_code="DIC_IS_UPLOAD"  order_id="${order_id}" field_name="is_upload"  field_desc ="是否已上传" field_type="select" ></html:orderattr>
                              	</td>
                         	 </tr>
                      	</table>
                  	</div>
                  	<!-- 资料回收方式结束 -->
                  	<!-- 发票信息开始 -->
                  	<div class="grid_n_cont_sub">
                    	<h3>发票信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>发票类型：</th>
                              	<td>
                                    <html:orderattr attr_code="DIC_ORDER_INVOICE_TYPE"  order_id="${order_id}" field_name="invoice_type"  field_desc ="发票类型" field_type="select"  ></html:orderattr>
                              	</td>
                              	<th>发票打印方式：</th>
                              	<td>
                                    <html:orderattr attr_code="DIC_ORDER_INVOICE_PRINT_TYPE"  order_id="${order_id}" field_name="invoice_print_type"  field_desc ="发票打印方式" field_type="select" ></html:orderattr>
                              	</td>
                              	<th>发票抬头：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="reserve8"  field_desc ="发票抬头" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th>发票内容：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_group_content"  field_desc ="发票内容" attr_code="DIC_INVOICE_GROUP_CONTENT" field_type="select"></html:orderattr></td>
                              <!-- 内容跟明细差不多 先屏蔽 <th>发票明细：</th>
                              	<td><html:orderattr  order_id="${order_id}" field_name="invoice_group_content"  field_desc ="发票内容" field_type="input"></html:orderattr></td>
                              	-->	
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                              	<th>&nbsp;</th>
                              	<td>&nbsp;</td>
                            </tr>
                    	</table>
                  	</div>
                  	<!-- 发票信息结束 -->
<script type="text/javascript">
$(function(){
	   //集团客户和集客订单展示 集团信息
	  var cust_flag = $("#custFlag").val();
	  <%
	    boolean  flag_JK = EcsOrderConsts.ORDER_ORIGIN_NH.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_ORIGIN));
	  %>
	  var flag_JK = <%=flag_JK%>;
	  if(cust_flag=='false'&&!flag_JK){
	    $("#union_cust").hide();
      }
});

</script>