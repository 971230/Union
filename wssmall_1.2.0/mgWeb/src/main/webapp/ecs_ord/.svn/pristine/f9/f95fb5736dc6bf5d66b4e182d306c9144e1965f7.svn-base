<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="zte.net.ecsord.common.StypeConsts"%>
<%@page import="zte.net.ecsord.utils.AttrUtils"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderFileBusiRequest"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ztesoft.soc.fastdfs.IDfsManager"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/artZoom.min.js"></script>
<link href="<%=request.getContextPath() %>/public/common/control/css/lrtk.css" rel="stylesheet" type="text/css" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单预处理</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

<%
  String order_id = (String)request.getAttribute("order_id");
  String order_from  = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
  String customerType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
  boolean custFlag = EcsOrderConsts.CUSTOMER_CUST_TYPE_JTKH.equals(customerType);
  String d_type = (String)request.getAttribute("d_type");
  String sale_mode = (String)request.getAttribute("sale_mode");
  String devic_gear = (String)request.getAttribute("devic_gear");
  String is_done_active = (String)request.getAttribute("is_done_active");
  String yclbtn = "";
  if("ycl".equals(d_type)){
	  yclbtn = "ycl";
	  request.setAttribute("yclbtn", yclbtn);
  }
%>

</head>

<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<body>
<form action="javascript:void(0);" id="preDealOrderForm" method="post">

<input type="hidden"  value="${query_type}" name="query_type"/>
<input type="hidden"  value="${d_type}" name="d_type"/>
<input type="hidden" id="custFlag" value="<%=custFlag%>"/>
<input type="hidden" name="oldCertNum" value="<%=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM)%>" />
<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
            <!-- 顶部公共 -->
            
            <c:choose>
            	<c:when test="${orderTree.orderExtBusiRequest.is_work_custom=='1'}">
            		<jsp:include page="custom_flow.jsp?order_id=${order_id }&btn=${yclbtn }"/>
            	</c:when>
            	<c:otherwise>
            		<jsp:include page="auto_flows.jsp?order_id=${order_id }&btn=${yclbtn }"/>
            	</c:otherwise>
            </c:choose>
         
        	<!-- 关联订单显示开始-->
            <!-- <div class="grid_n_div">
	            <h2><a href="javascript:void(0);" class="openArrow"></a>关联订单信息</h2> 
	           	<div class="grid_n_cont">
	    		  	<div id="relations_info" style="height: 40px"></div>
	            </div>
            </div> -->
            <!-- 关联订单显示结束 -->

          	<!-- 订单全部信息开始 -->  
        	<div class="grid_n_div">
            	 <h2><a href="javascript:void(0);" class="closeArrow"></a>订单基本信息<!--<a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span></span></div>
					<div id="order_base">
						<jsp:include page="include/order_base.jsp?order_id=${order_id }&order_amount=${order_amount }"/>
					</div>
					<div class="grid_n_cont_sub"  id="certPhotoDiv">
						<h3>用户证件照片：</h3>
						<%
							List<String> img_list = new ArrayList<String>();
							List<OrderFileBusiRequest> orderFileBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderFileBusiRequests();
							if (orderFileBusiRequests != null && orderFileBusiRequests.size() > 0) {
								for (OrderFileBusiRequest obj : orderFileBusiRequests) {
									String status = obj.getStatus();
									String file_type = obj.getFile_type();
									if("1".equals(status)&&"jpg".equals(file_type)){
										String imgPath = request.getContextPath() + "/servlet/OrderShowPhotoServlet?file_path=" + obj.getFile_path();
										img_list.add(imgPath);
									}
								}
							}
							request.setAttribute("img_list", img_list);
						%>
						<div class="picListDiv" id="show_id_cart">
							<ul class="imgList">
								<c:forEach items="${img_list}" var="img">
									<td>
										<a class="artZoom" href="${img}" rel="${img}">
											<img src="${img}" style="width: 180px;height: 180px" attr="${img}"/>
										</a>
									</td>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div id="order_ext" style="height: 80px"></div>
        	    </div>
        	</div>

              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>客户历史订单</h2> 
              	  
              	<div class="grid_n_cont">
              		<div id="order_his_before" style="height: 80px;" ></div>
              		<div id="order_his" style="height: 80px;display:none" ></div>
                </div>
              </div> 
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>电话外呼记录</h2> 
              	  
              	<div class="grid_n_cont">
              		<div id="calllog_info_before" style="height: 80px;" ></div>
              		<div id="calllog_info" style="height: 80px;display:none" ></div>
                </div>
              </div> 
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>订单修改记录</h2> 
              	<div class="grid_n_cont">
              		<div id="order_change" style="height: 80px"></div>
                </div>
              </div>
        	<!-- 商品信息开始 -->
        	<div class="grid_n_div">
             <h2><a href="javascript:void(0);" class="openArrow"></a>商品信息<!-- <a href="#" class="editBtn">编辑</a> --></h2> 
              	<div class="grid_n_cont">
       		  	  	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span id="specValidateMsg_Span"></span></div>
					<div id="prod_info" style="height: 80px"></div>
                </div>
            </div>
              <!-- 商品信息结束 -->
              
              <!-- 货品信息开始 -->
              <c:if test="${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}">
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span></span></div>
					<div id="goods_info" style="height: 80px"></div>
					
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
                                <th>用户种类：</th>
                                	<td><html:selectdict name="user_kind"  disabled="true"  curr_val="${user_kind}"    attr_code="DIC_USER_KIND"  appen_options="<option>---请选择---</option>" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict></td>
                               </tr>
                               <tr>
	                            <th>网格：</th>
                                 			<td>${adsl_grid }</td>
                                <th>局向编码：</th>
                                 			<td><html:orderattr  order_id="${order_id}"  field_name="exch_code"  field_desc ="局向编码" field_type="input" ></html:orderattr></td>
                                <th>预约装机时间：</th>
                                	<td>${appt_date }</td>
                               </tr>
                               <tr>
	                            <th>合约编码：</th>
                                 			<td>${p_code }</td>
                                <th>合约名称：</th>
                                 			<td>${p_name }</td>
                                <th>合约期：</th>
                                	<td>
                                	<html:selectdict disabled="true" name="packge_limit" curr_val="${packge_limit}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_CONTRACT_MONTH" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                                	</td>
                               </tr>
                                
                               <tr>
                                <th>接入方式：</th>
                                	<td>
                                		<input  name="access_type" id="access_type" value="${access_type}">
                                	</td>
                                <th>地市：</th>
                                	<td>
                                		<html:selectdict disabled="true" name="city" curr_val="${city}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_CITY_ZJ" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                                	</td>
                                <th>终端类型：</th>
                                	<td>
										<html:selectdict disabled="true" name="terminal_type" curr_val="${terminal_type}" appen_options="<option value=''>---请选择---</option>"   attr_code="DIC_TERMINAL_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                                	</td>			                               
                                </tr>
                               
                               <tr>
	                            <th>套餐编码：</th>
                                 			<td>${ess_code }</td>
                                <th>套餐名称：</th>
                                 			<td>${plan_title }</td>
                                <th>速率：</th>
                                 			<td>${net_speed }</td>
                                
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
                                 <th><span>*</span>营业县分：</th>
                                 			<td>
                                 			<input type="text" id="county_name" disabled="disabled" value=${ county_name}>
                                			<html:orderattr style="display:none;"  order_id="${order_id}" field_name="county_id"   field_desc ="营业县分" field_type="input"></html:orderattr>
                                			<input type="button" value="一键获取" id="selAreaCountyBtn" class="graybtn1"/>
                               				 <input type="button" value="查询" id="selXcountyBtn" class="graybtn1"/>
                               				 <html:orderattr style="display:none;" order_id="${order_id}"  field_name="line_type"  field_desc ="预判返回类型" field_type="input"></html:orderattr>
                                 			</td>
	                           <th><span>*</span>标准地址：</th>
                                 			<td>
                                 			<html:orderattr  order_id="${order_id}" field_name="adsl_addr_desc"  field_desc ="标准地址描述" field_type="input" ></html:orderattr><input type="button" value="查询" id="selStdAddrBtn" class="graybtn1"/>
                               			    <html:orderattr style="display:none;" order_id="${order_id}"  field_name="adsl_addr"  field_desc ="标准地址" field_type="input" ></html:orderattr>
                                 			<input type="hidden" name="extraInfo" id="extraInfo" value="${extraInfo}">
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
	                               				<input type="text" id="temp_adsl_addr_desc" disabled="disabled" value="${ temp_adsl_addr_desc}" style="width: 200px;" />
	                               			</td>
		                         		</c:when>
		                         		<c:otherwise>
		                         			<c:if test="${service_type =='6115'}">
		                               			<th><span>*</span>光猫：</th>
		                                		<td>
													<html:orderattr   order_id="${order_id}"  field_name="moderm_name"  field_desc ="光猫ID中文名称" field_type="input" ></html:orderattr>
		                              				<html:orderattr style="display:none;" order_id="${order_id}"  field_name="moderm_id"  field_desc ="光猫ID" field_type="input" ></html:orderattr>
		                              				<input type="button" value="查询" id="selModermIDBtn" class="graybtn1"/>
												</td>
											    <script>
			                          				$("[field_name='moderm_name']").attr("readonly","true");
			                          			</script> 
				                          	</c:if>
		                         		</c:otherwise>
		                         	</c:choose>
                                </tr>
                                
                                <tr>
                                	<c:choose>
	                                	<c:when test="${'-1' == adsl_addr || '0'==adsl_addr || ''==adsl_addr}">
		                         			<c:if test="${service_type =='6115'}">
		                               			<th><span>*</span>光猫：</th>
		                                		<td>
													<html:orderattr   order_id="${order_id}"  field_name="moderm_name"  field_desc ="光猫ID中文名称" field_type="input" ></html:orderattr>
		                              				<html:orderattr style="display:none;" order_id="${order_id}"  field_name="moderm_id"  field_desc ="光猫ID" field_type="input" ></html:orderattr>
		                              				<input type="button" value="查询" id="selModermIDBtn" class="graybtn1"/>
												</td>
											    <script>
			                          				$("[field_name='moderm_name']").attr("readonly","true");
			                          			</script> 
				                          	</c:if>
		                         		</c:when>
	                         		</c:choose>
                                </tr>
                                
                                <tr>
                                 <th>用户地址：</th>
                                 			<td>${user_address }</td>
                                </tr>
							</table>
						</div>
						<!-- FQJ add 2017-03-03 宽带信息 end -->
              		</c:if>
					
					<div id="goods_open" style="height: 80px"></div>
					<div id="sp_product" style="height: 80px"></div>
	               	<div id="sub_product">
               	  	     <jsp:include page="include/sub_product.jsp"/>
					</div>               	  	
               	  	<!-- ZX add 2015-12-30 start 副卡信息开始 -->
               	  	<div id="phoneInfoFukaDiv">
               	  	 	<jsp:include page="include/phone_info_fuka.jsp?order_id=${order_id }&isChangePhone=${isChangePhone}"/>
               	  	</div>
               	  	<!-- ZX add 2015-12-30 end 副卡信息结束 -->
					<div id="goods_gift" style="height: 80px"></div>
					
                </div>
              </div>
              </c:if>
              <!-- 货品信息结束 --> 
              <!-- 多商品货品信息开始 -->
              <c:if test="${orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053'}">
              <div class="grid_n_div">
            	<h2><a href="#" class="openArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
					<div id="goods_info_more" style="height: 80px"></div>
                </div>
              </div>
              </c:if>
              <!-- 多商品货品信息结束 --> 
              
              <!-- 总部反馈费用项减免开始 <div id="fee_info" style="height: 80px"></div>-->
              <div class="grid_n_div">
              <h2><a href="#" class="closeArrow"></a>费用项减免</h2> 
              <div class="grid_n_cont">
              	<div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span></span></div>
				<div id="fee_info" style="height: 80px"></div>
              </div>
              </div>
              <!-- 总部反馈费用项减免结束 -->
              <jsp:include page="order_handler.jsp?order_id=${order_id }"/>
        </div>
        
    </div>
</div>

</form>
<br />
<br /><br /><br />
</body>
</html>

<script type="text/javascript">
jQuery('a.artZoom').artZoom();
function load_ord_his(){
	//$("#table_show").show();
     // $("#table_show").toggle();
  	 $("#table_show").toggle();
	//$("#order_his").show();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};
function load_calllog(){
	//$("#table_show").show();
     // $("#table_show").toggle();
  	 $("#log_table_show").toggle();
	//$("#order_his").show();
};
</script>
 

<script type="text/javascript">

  $(function(){
	  //CommonLoad.loadJSP('order_base','< %=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"order_base"},false,null,true);
	  //CommonLoad.loadJSP('relations_info','< %=request.getContextPath() %>/shop/admin/orderFlowAction!getRealtionOrders.do',{order_id:"${order_id }",ajax:"yes",includePage:"relations_info"},false,function(){AutoFlow.checkMsg();},true);
  	  
	  CommonLoad.loadJSP('order_ext','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"order_ext"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('prod_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"prod_info"},false,function(){AutoFlow.checkMsg();},true);
	  CommonLoad.loadJSP('calllog_info_before','<%=request.getContextPath() %>/shop/admin/ordCall!queryCalllog.do',{order_id:"${order_id }",first_load:"yes",call_order_type:"order",ajax:"yes",includePage:"calllog_info_before"},false,null,true);
      //先加载总数和按钮页
	  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	  if(${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}){//非多商品
		  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_info"},false,function(){AutoFlow.checkMsg();},true);
		  if(${orderTree.orderExtBusiRequest.order_model=='10'}||${orderTree.orderExtBusiRequest.order_from=='10012'}){
			  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"view_goods_open"},false,function(){AutoFlow.checkMsg();},true);
		  }else{
			  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_open"},false,function(){AutoFlow.checkMsg();},true);
		  }
	  	  
		  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"sp_product"},false,function(){AutoFlow.checkMsg();},true);
		  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"goods_gift"},false,function(){AutoFlow.checkMsg();},true);
	  }
	  if(${orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053'}){//多商品
		  CommonLoad.loadJSP('goods_info_more','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"view_goods_info_more"},false,function(){AutoFlow.checkMsg();},true);
	  }
	  CommonLoad.loadJSP('fee_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includePage.do',{order_id:"${order_id }",ajax:"yes",includePage:"fee_info"},false,function(){AutoFlow.checkMsg();},true);
	   //校验信息
	  AutoFlow.checkMsg();
	  
  });
</script>

