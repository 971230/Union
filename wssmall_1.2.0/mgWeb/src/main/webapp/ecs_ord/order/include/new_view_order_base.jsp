
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
  	  String sale_mode = (String)request.getAttribute("sale_mode");
  	  String devic_gear = (String)request.getAttribute("devic_gear");
  	  String is_done_active = (String)request.getAttribute("is_done_active");
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
                                <th><span>*</span>业务系统订单号：</th>
                                <td>
                                
                                </td>
                          	</tr>
                          	<tr>
                          		<th><span>*</span>地市：</th>
                                <td>
                                    <html:orderattr attr_code="DC_MODE_REGION" order_id="${order_id}" field_name="order_city_code"  field_desc ="归属地市" field_type="select" disabled="disabled"></html:orderattr>
                            	</td>
                          		<th><span>*</span>营业县分：</th>
                                 			<td>
                                 			<input type="text" id="county_name" disabled="disabled" value=${ county_name}>
                            	</td>
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
                          		<th><span>*</span>订单来源：</th>
                                <td>
                                    <html:orderattr disabled="disabled"  attr_code="ORDER_FROM"  order_id="${order_id}" field_name="order_from"  field_desc ="订单来源" field_type="select" ></html:orderattr>
                                </td>
                                <th>用户号码：</th>
                              <td><html:orderattr  disabled="disabled"   order_id="${order_id}" field_name="phone_num"  field_desc ="用户号码" field_type="text"></html:orderattr></td>
                                <th><span>*</span>主卡号码：</th>
                                <td>${mainnumber}</td>
                                <%-- <th>发展人编码：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="develop_code_new"  field_desc ="发展人编码" field_type="input"></html:orderattr></td>
                                 <th>发展人名称：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="develop_name_new"  field_desc ="发展人名称" field_type="input"></html:orderattr></td> --%>
                          	</tr>
                          	<tr>
                               <th><span>*</span>应收金额（元）：</th>
                                <td>  
                                <input disabled="disabled" class='ipt_new' style="width:200px;" onkeypress="if ((event.keyCode <48 && event.keyCode!=46) || event.keyCode > 57) event.returnValue = false;"
                                 name="order_amount" value="${order_amount}"/></td>
                                <th><span>*</span>实收金额（元）：</th>
                                <td><html:orderattr disabled="disabled" order_id="${order_id}" field_name="order_realfee"  field_desc ="实收金额（元）" field_type="input"></html:orderattr></td>
                                <th><span>*</span>订单状态：</th>
                                <td>
                                 <html:orderattr disabled="disabled" attr_code="DC_ORDER_NEW_TYPE" order_id="${order_id}" field_name="order_type"  field_desc ="订单类型" field_type="select" ></html:orderattr>
                            	</td>
                          	</tr>
                          	<tr>
                          	<th>激活状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${orderTree.orderRealNameInfoBusiRequest.active_flag }" attr_code="DC_ACTIVE_FLAG"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                          	<th>退单状态：</th>
                                <td><%=AttrUtils.getInstance().getRefund_status(order_id)%></td>	
                          	 <th>订单签收状态：</th>
                                <td><html:selectdict disabled="true" curr_val="${delivery.sign_status }" attr_code="DC_SIGN_STATUS"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                            </tr>
                            
                            <tr>
                             <th>稽核信息:</th>
                            <td>
                             <c:if  test="${orderTree.orderExtBusiRequest.audit_type==0}">稽核退单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==1}">稽核退款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==2}">稽核补单</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==3}">稽核补款</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==4}">稽核退现金</c:if>
                             <c:if test="${orderTree.orderExtBusiRequest.audit_type==null || orderTree.orderExtBusiRequest.audit_type==''}">暂无稽核信息</c:if>
                            </td>
                            <th><span>*</span>对应王卡状态：</th>
                                <td>${kingcard_status}</td>
                                <th>证件照上传状态：</th>
                                <td><html:orderattr  disabled="disabled" order_id="${order_id}" field_name="if_Send_Photos"  field_desc ="照片上传状态" field_type="select"></html:orderattr></td>
                            
                            </tr>
                            
                            <tr>
                            	<th><span>*</span>操作类型：</th>
                                <td>
                                  	<html:orderattr disabled="disabled" attr_code="DC_OPT_TYPE" order_id="${order_id}" field_name="optType"  field_desc ="操作类型" field_type="select" ></html:orderattr>
                            	</td>
                            </tr>
                            
                           <c:if test="${hide_div!='handle_input_area' }"> 
                  <div class="grid_n_div">
			    <div class="grid_n_div">
					<div class="grid_n_cont">
			  		<div class="grid_n_cont_sub">
			              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blue_grid">
			                <tr>
			                     <th style="text-align: right"><span style="color:red;">*</span> 备注：</th>
			                     <td><textarea id="node_deal_message" name="dealDesc" cols="45" rows="5" class='ipt_new' style="height:20px;width:600px;"></textarea></td>
			                   </tr>
			              </tbody></table>
			      	</div>
			   		</div>
				</div>
			</div>
            </c:if>
                        </table>
                    </div>      
        	        <!-- 订单基本信息结束 -->
							<h3>&nbsp;&nbsp;商品信息：</h3>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
							<tr>
							<th><span>*</span>商品编号：</th>
                                <td><%-- <html:orderattr  disabled="disabled"  order_id="${order_id}" field_name="out_package_id"  field_desc ="商品编号" field_type="input"></html:orderattr> --%>
                                ${goods_id}
                                </td>
                              <th>商品名称：</th>
                               <td >
                                  <%-- <html:orderattr  disabled="disabled" order_id="${order_id}" field_name="GoodsName"  field_desc ="商品名称" field_type="input"></html:orderattr> --%>
                               	  ${goods_name}
                               </td>
                               
                               <%-- <th>原商品名称：</th>
                               <td>
                                  <html:orderattr  disabled="disabled" order_id="${order_id}" field_name="oldGoodsName"  field_desc ="旧商品名称" field_type="input"></html:orderattr></td>
                               </td> --%>
							</tr>
							<tr>
							 <th><span>*</span>商品大类：</th>
                                <td><%-- <html:orderattr  disabled="disabled"  order_id="${order_id}" attr_code="DC_MODE_GOODS_TYPE" field_name="goods_type"  field_desc ="商品类型" field_type="select"></html:orderattr> --%>
                                ${goods_type_name}</td>
                                <th><span>*</span>商品小类：</th>
                                <td><html:orderattr  disabled="disabled"  order_id="${order_id}" attr_code="DC_GOODS_CAT_ID" field_name="goods_cat_id"  field_desc ="商品小类" field_type="select"></html:orderattr></td>
                                <th><span>*</span>商品价格（元）：</th>
                                <td><html:orderattr disabled="disabled"   order_id="${order_id}" field_name="sell_price"  field_desc ="商品价格" field_type="input"></html:orderattr></td>
                            
							</tr>
							
													<tr>
							<th>元素ID：</th>
							<td>
							<%-- <html:orderattr  disabled="disabled"  order_id="${order_id}" attr_code="ELEMENT_ID" field_name="element_id"  field_desc ="元素ID" ></html:orderattr>--%>
							${element_id}
							</td>
							<th>终端类型：</th>
							<td>
							<%--<html:orderattr  disabled="disabled"  order_id="${order_id}" attr_code="MOBILE_TYPE" field_name="mobile_type"  field_desc ="终端类型" ></html:orderattr>--%>
							${mobile_type}
							</td>
							<th>终端串号：</th>
							<td>
							<%--<html:orderattr  disabled="disabled"  order_id="${order_id}" attr_code="" field_name="object_esn"  field_desc ="终端串号" ></html:orderattr>--%>
							${object_esn}
							</td>
							</tr>
							<tr>
							<th>终端名称：</th>
							<td>
							<%--<html:orderattr  disabled="disabled"  order_id="${order_id}" attr_code="" field_name="object_esn"  field_desc ="终端串号" ></html:orderattr>--%>
							${terminal_name}
							</td>
							</tr>
						</table>	
        	        <c:if test="${goods_type =='20021' || goods_type == '170502112412000711' || goods_type == '180441456282001431' || goods_type == '180101547042001934'}">
						<!-- FQJ add 2017-03-03 宽带信息 start -->
						<div id="kd_detail"> 
							<h3>&nbsp;&nbsp;宽带信息：</h3>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                               <tr>
	                            <th>宽带账号：</th>
	                            			<td>${adsl_account}</td>
                                <th>宽带号码：</th>
                                 			<td>${adsl_number}</td>
                                 			<th>预约装机时间：</th>
                                	<td>${appt_date }</td>
                                <%-- <th>用户种类：</th>
                                	<td><html:selectdict name="user_kind"  disabled="true"  curr_val="${user_kind}"    attr_code="DIC_USER_KIND"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                                --%>
                               </tr>
                               <tr>
	                            <th>销售模式：</th>
	                            <td>
                                 			<select class="ipt_new" false?style="null" style="width:200px;" name="sale_mode" value=${ sale_mode}>
                                 			<option value="01" <%="01".equals(sale_mode)?"selected":"" %>>免费租用</option>
                                 			<option value="06" <%="06".equals(sale_mode)?"selected":"" %>>用户自购</option>
                                 			<option value="07" <%="07".equals(sale_mode)?"selected":"" %>>用户自备用户自备</option>
                                 			</select>
                                 			</td>
                                <th>新设备档位：</th>
                                 			<td>
                                 			<select class="ipt_new" false?style="null" style="width:200px;" name="devic_gear" value=${ devic_gear}>
                                 			<option value="00" <%="00".equals(devic_gear)?"selected":"" %>>标准版(光猫)</option>
                                 			<option value="01" <%="01".equals(devic_gear)?"selected":"" %>>加强版(光猫)</option>
                                 			</select>
                                 			</td>
                                <th>是否竣工生效：</th>
                                 			<td>
                                 			<select class="ipt_new" false?style="null" style="width:200px;" name="is_done_active" value=${ is_done_active}>
                                 			<option value="1" <%="1".equals(is_done_active)?"selected":"" %>>是</option>
                                 			<option value="0" <%="0".equals(is_done_active)?"selected":"" %>>否</option>
                                 			</select>
                                 			</td>
                                
                               </tr>
                               <tr>
                               <th><span>*</span>标准地址：</th>
                                 			<td>
                                 			<html:orderattr disabled="disabled" order_id="${order_id}" field_name="adsl_addr_desc"  field_desc ="标准地址描述" field_type="input" ></html:orderattr>
                               			    <html:orderattr style="display:none;" order_id="${order_id}"  field_name="adsl_addr"  field_desc ="标准地址" field_type="input" ></html:orderattr>
                                 			<input  disabled="disabled" type="hidden" name="extraInfo" id="extraInfo" value="${extraInfo}">
                                 			</td>
                                 			<script>
		                          				$("[field_name='adsl_addr_desc']").attr("readonly","true");
		                          				
		                          				var adsl_addr = $("[field_name='adsl_addr']").val();
		                          				
		                          				if("-1"==adsl_addr || ""==adsl_addr || "0"==adsl_addr){
		                          					$("[field_name='adsl_addr_desc']").val("");
		                          				}
		                         		    </script>
		                        <c:choose>
		                         		<c:when test="${'-1' == adsl_addr || '0'==adsl_addr || ''==adsl_addr}">
		                         			<th>临时地址：</th>
	                               			<td>
	                               				<input disabled="disabled" type="text" id="temp_adsl_addr_desc" disabled="disabled" value="${ temp_adsl_addr_desc}" style="width: 200px;" />
	                               			</td>
		                         		</c:when>
		                         		<c:otherwise>
		                         			
		                         		</c:otherwise>
		                         	</c:choose> 		
		                         	<th>用户种类：</th>
                                	<td><html:selectdict  name="user_kind"  disabled="true"  curr_val="${user_kind}"    attr_code="DIC_USER_KIND"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>    
                               </tr>
                               <tr>
	                            <%-- <th>网格：</th>
                                 			<td>${adsl_grid }</td> --%>
                                <th>局向编码：</th>
                                 			<td><html:orderattr disabled="disabled" order_id="${order_id}"  field_name="exch_code"  field_desc ="局向编码" field_type="input" ></html:orderattr></td>
                                <%-- <th>预约装机时间：</th>
                                	<td>${appt_date }</td> --%>
                                	 <th>接入方式：</th>
                                	<td>
                                		<input disabled="disabled" name="access_type" id="access_type" value="${access_type}">
                                	</td>
                                	<th>速率：</th>
                                 			<td>${net_speed }</td>
                                 			</tr>
                                 			<tr>
                                 			<th><span>*</span>营业县分：</th>
                                 			<td>
                                 			<input type="text" id="county_name" disabled="disabled" value=${ county_name}>
                                			<html:orderattr style="display:none;"  order_id="${order_id}" field_name="county_id"   field_desc ="营业县分" field_type="input"></html:orderattr>
                                			
                               				 <html:orderattr style="display:none;" order_id="${order_id}"  field_name="line_type"  field_desc ="预判返回类型" field_type="input"></html:orderattr>
                                 			</td>
                                 			<c:choose>
	                                	<c:when test="${'-1' == adsl_addr || '0'==adsl_addr || ''==adsl_addr}">
		                         			<c:if test="${service_type =='6115'}">
		                               			<th><span>*</span>光猫：</th>
		                                		<td>
													<html:orderattr disabled="disabled"  order_id="${order_id}"  field_name="moderm_name"  field_desc ="光猫ID中文名称" field_type="input" ></html:orderattr>
		                              				<html:orderattr style="display:none;" order_id="${order_id}"  field_name="moderm_id"  field_desc ="光猫ID" field_type="input" ></html:orderattr>
		                              				
												</td>
											    <script>
			                          				$("[field_name='moderm_name']").attr("readonly","true");
			                          			</script> 
				                          	</c:if>
		                         		</c:when>
	                         		</c:choose>
                               </tr>
                              
							</table>
						</div>
						</c:if>
        	        
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
<script type="text/javascript">         		
   $(function(){
	   Eop.Dialog.init({id:"selGoodsDlg",modal:true,title:"查询商品",width:'800px'});
	   //查询商品selNewGoodsBtn
	     $("#selNewGoodsBtn").bind("click",function(){
	       var stdOrderId = $("#stdOrderId").val();
	       var url = app_path+"/shop/admin/orderWarningAction!queryGoodsList.do?ajax=yes&stdOrderId="+stdOrderId;
	 			$("#selGoodsDlg").load(url,{},function(){});
	 			 Eop.Dialog.open("selGoodsDlg");
	 		});
   });
   </script>
                    <!-- 物流信息结束 -->