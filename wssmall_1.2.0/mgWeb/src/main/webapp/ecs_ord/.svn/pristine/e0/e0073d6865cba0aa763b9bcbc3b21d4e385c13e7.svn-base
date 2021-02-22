<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<%@ include file="/commons/taglibs.jsp"%>
                    <!-- 买家信息开始 -->
                    <div class="grid_n_cont_sub">
                      	<h3>买家信息：</h3>   
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>客户电话：</th>
                              	<td>${buyInfoMap.tel}
                              	</td>
                              	<th>客户手机：</th>
                              	<td>${buyInfoMap.mobile}
                              	    </td>
                              	<th>客户名称：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="buyer_name"  field_desc ="客户名称" field_type="input"></html:orderattr></td>
                          	</tr>
                            <tr>
                              	<th>客户联系地址：</th>
                              	<td>${buyInfoMap.address}
                              	</td>
                                <th>买家昵称：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="buyer_id"  field_desc ="买家昵称" field_type="input"></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                              	<th>买家留言：</th>
                              	<td ><html:orderattr disabled="disabled" cols="45"  style="height:50px;width:200px;" order_id="${order_id}" field_name="buyer_message"  rows="5" field_desc ="买家留言" field_type="textarea" ></html:orderattr></td>
                                <th>卖家留言：</th>
                              	<td><html:orderattr disabled="disabled" style="height:50px;width:200px;"   order_id="${order_id}" field_name="seller_message" cols="45"  rows="5" field_desc ="卖家留言" field_type="textarea" ></html:orderattr></td>
                                <th>&nbsp;</th>
                                <td>&nbsp;</td>
                            </tr>
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
               	  	<c:if test="${orderTree.orderExtBusiRequest.order_from=='10039' }">
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
                    </c:if>
                    <!-- 沃百富信息结束 -->
                    <!-- 资料回收方式 -->
                    <div class="grid_n_cont_sub">
                    	<h3>资料回收方式：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                            <tr>
                              	<th>回收方式：</th>
                              	<td><html:orderattr attr_code="DIC_RECOVERTY_TYPE"  order_id="${order_id}" field_name="file_return_type"  field_desc ="回收方式" field_type="select"></html:orderattr></td>
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
                              	<th>发票号码：</th>
                              	<td><html:orderattr disabled="disabled"  order_id="${order_id}" field_name="invoice_no"  field_desc ="发票号码" field_type="input"></html:orderattr></td>
                             	<th>发票代码：</th>
                              	<td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="invoice_code"  field_desc ="发票代码" field_type="input"></html:orderattr></td>
                            </tr>
                    	</table>
                  	</div>
                  	<!-- 发票信息结束 -->
                  	<!-- 联盟信息开始 -->
                  	 <div class="grid_n_cont_sub">
                    	<h3>联盟信息：</h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                        <c:choose>
						   <c:when test="${orderTree.leagueInfoBusiRequests == null || fn:length(orderTree.leagueInfoBusiRequests) == 0}">
						   	<tr>
                              	<th>联盟名称：</th>
                              	<td></td>
                              	<th>上级联盟名称：</th>
                              	<td></td>
                              	<th></th>
                              	<td>
                              	</td> 
                         	 </tr>
						   </c:when>
						   <c:otherwise>
						   	<tr>
                              	<th>联盟名称：</th>
                              	<td>${league_info.league_name}</td>
                              	<th>上级联盟名称：</th>
                              	<td>${league_info.higher_league_name}</td>
                              	<th></th>
                              	<td>
                              	</td>
                         	 </tr>
						   </c:otherwise>
						</c:choose>
                      	</table>
                  	</div>
                  	<!-- 联盟信息 结束-->
<script type="text/javascript">
$(function(){
	  //集团客户和集客订单展示 集团信息
	  var cust_flag = $("#custFlag").val();
	  var order_origin = '${orderTree.orderExtBusiRequest.order_origin}';
	  var flag_JK;
	  
	  if('NH'==order_origin) {
		  flag_JK = 'true';
	  } else {
		  flag_JK = 'false';
	  }
	 
	  if(cust_flag=='false'&&!flag_JK){
	    $("#union_cust").hide();
      }
});

</script>