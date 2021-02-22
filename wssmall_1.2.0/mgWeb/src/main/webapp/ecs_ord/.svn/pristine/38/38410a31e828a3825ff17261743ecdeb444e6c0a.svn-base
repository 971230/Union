<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单归档</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/ord_receipt.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>

</head>
<%
	String order_id = (String) request.getAttribute("order_id");
%>
<body>
	<form id="ordReceiptForm" method="post">
		<input type="hidden" value="<%=order_id %>" id="order_id"/>
		<input type="hidden" value="${county_name}" id="county_name"/>
		<div class="gridWarp">
			<div class="new_right">
				<div class="right_warp">
					<div id="auto_flows_div">
			            <c:choose>
			            	<c:when test="${orderTree.orderExtBusiRequest.is_work_custom=='1'}">
			            		<jsp:include page="custom_flow.jsp?order_id=${order_id }"/>
			            	</c:when>
			            	<c:otherwise>
			            		<jsp:include page="auto_flows.jsp?order_id=${order_id }"/>
			            	</c:otherwise>
			            </c:choose>
		     		</div>
					
					<div class="grid_n_div">
            	 <h2><a href="javascript:void(0);" class="closeArrow"></a>订单基本信息<!--<a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont" id="orderInfo">
            		<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16"><span>缺少信息或者信息格式不正确！</span></div> --%>
					<div id="order_base">
						 <jsp:include page="include/view_order_base.jsp?order_id=${order_id }&order_amount=${order_amount }&order_is_his=${order_is_his }"/> 
					</div>
					<div id="order_ext" style="height: 80px"></div>
        	    </div>
        	</div>
             
             <c:choose>
              <c:when test="${(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')||orderTree.orderExtBusiRequest.order_from=='10061'}">
              <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont" id="hp1">
					<div id="goods_info_more" style="height: 80px"></div>
                </div>
              </div>
              </c:when>
              <c:when test="${orderTree.orderExtBusiRequest.order_from=='10062' }">
              <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont" id="hp2">
              		<div id="hsB2C_goods_info" style="height: 80px"></div>
                </div>
              </div>
              </c:when>
              <c:otherwise>
              <c:if test="${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}">
              <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>货品信息<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont" >
            		<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span>缺少信息或者信息格式不正确！</span></div> --%>
					<div id="goods_info" style="height: 80px"></div>
					<div id="goods_open" style="height: 80px"></div>
					<div id="sp_product" style="height: 80px"></div>
	               	<div id="sub_product">
               	  	     <jsp:include page="include/sub_product.jsp"/>
					</div>               	  	
					<div id="goods_gift" style="height: 80px"></div>
                </div>
              </div>
              </c:if>
              <c:if test="${orderTree.orderExtBusiRequest.order_from=='10067'}">
              <div class="grid_n_div">
            	<h2><a href="javascript:void(0);" class="openArrow"></a>货品信息(智慧沃企)<!-- <a href="#" class="editBtn">编辑</a>--></h2> 
              	<div class="grid_n_cont">
            		<%-- <div class="remind_div"><img src="${context}/images/ic_remind.png" width="16" height="16" /><span>缺少信息或者信息格式不正确！</span></div> --%>
					<div id="goods_dtl" style="height: 80px"></div>
                </div>
              </div>
              </c:if>
              </c:otherwise>
			  </c:choose>
			   
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="closeArrow"></a>物流信息
						</h2>
						<div class="grid_n_cont">
							<%-- <div class="remind_div">
								<span></span><img src="${context}/images/ic_remind.png" width="16"
									height="16"> 
									</span>
							</div> --%>
							<div class="grid_n_cont_sub">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="tab_form">
									<tr>
										<th>用户收货时间：</th>
										<td><input name="ordReceipt.user_recieve_time" type="text"
											dataType="date" class="dateinput ipttxt" id="user_recieve_time"
											value="${orderTree.orderDeliveryBusiRequests[0].user_recieve_time }"/></td>
										<th>&nbsp;</th>
										<td>&nbsp;</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="closeArrow"></a>物流号对应的订单
						</h2>
						<div class="grid_n_cont">
							<div class="grid_n_cont_sub">
								<div class="form_g">
									<table width="60%" border="0" cellspacing="0" cellpadding="0"
										class="grid_s">
										<tr>
											<th>内部单号</th>
											<th>外部单号</th>
											<th>物流单号</th>
										</tr>
										<tr>
											<td>${orderTree.orderExtBusiRequest.order_id }</td>
											<td>${orderTree.orderExtBusiRequest.out_tid }</td>
											<td>${orderTree.orderDeliveryBusiRequests[0].logi_no }</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<%-- <div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="closeArrow"></a>订单需要回收的凭证
						</h2>
						<div class="grid_n_cont">
							<div class="grid_n_cont_sub">
								<div class="checkLabel">
									<div class="clearfix">
										<c:if test="${dic_material_retrieve != null }">
											<c:forEach var="retrieve" items="${dic_material_retrieve }">
												<label for="checkbox_${retrieve.pkey }">
													<input type="checkbox" name="CheckboxGroup" 
														id="checkbox_${retrieve.pkey }" 
														attr_code="${retrieve.pkey }"
														attr_name="${retrieve.pname }"
														value="${retrieve.pkey }" />
														${retrieve.pname }
												</label>
											</c:forEach>
										</c:if>
									<input type="hidden" id="material_retrieve" name="ordReceipt.material_retrieve"
										value="${orderTree.orderExtBusiRequest.material_retrieve }"/>
									</div>
									<div class="checkEx">
										<span>是否已上传：<html:orderattr attr_code="DIC_IS_UPLOAD"
												order_id="${order_id}" field_name="is_upload"
												field_desc="资料是否上传" field_type="select"></html:orderattr></span>
										<input type="hidden" name="ordReceipt.is_upload" id="is_upload" />
										<span>资料回收方式：<html:orderattr attr_code="DIC_RECOVERTY_TYPE"
												order_id="${order_id}" field_name="file_return_type"
												field_desc="资料回收方式" field_type="select"></html:orderattr></span>
										<input type="hidden" name="ordReceipt.file_return_type" id="file_return_type" />
									</div>
								</div>
							</div>
						</div>
					</div> --%>
					<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>客户历史订单</h2> 
              	<div class="grid_n_cont">
              		              	 <div id="order_his_before" style="height: 80px;" ></div>
              		<div id="order_his" style="height: 80px;display:none" ></div>
                </div>
              </div>
              <div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单修改记录</h2> 
              	<div class="grid_n_cont">
              		<div id="order_change" style="height: 80px"></div>
                </div>
              </div>
					
						<div class="grid_n_div">
						<h2>
							<a href="javascript:void(0);" class="closeArrow"></a>资料归档
						</h2>
						<div class="grid_n_cont">
							<div class="grid_n_cont_sub">
								<table width="60%" border="0" cellspacing="0" cellpadding="0"
									class="tab_form">
									<tr>
										<td width="20%">入网证件：</td>
										<td width="30%"><input type="radio" name="ordReceipt.net_certi" value="1" <c:if test="${ordReceipt.net_certi=='1'}">checked</c:if>/>一致</td>
										<td width="25%"><input type="radio" name="ordReceipt.net_certi" value="-1" <c:if test="${ordReceipt.net_certi=='-1' }">checked</c:if>/>不一致</td>
										<td width="25%"> <input type="radio" name="ordReceipt.net_certi" value="0" <c:if test="${ordReceipt.net_certi=='0' }">checked</c:if>/>无</td>									
									</tr>
									<tr>
										<td>受理协议：</td>
										<td><input type="radio" name="ordReceipt.accept_agree" value="1" <c:if test="${ordReceipt.accept_agree=='1' }">checked</c:if>/>一致</td>
										<td><input type="radio" name="ordReceipt.accept_agree" value="-1" <c:if test="${ordReceipt.accept_agree=='-1' }">checked</c:if>/>不一致</td>
										<td><input type="radio" name="ordReceipt.accept_agree" value="0" <c:if test="${ordReceipt.accept_agree=='0' }">checked</c:if>/>无</td>
									</tr>
									<tr>
										<td>靓号协议：</td>
										<td><input type="radio" name="ordReceipt.liang_agree" value="1" <c:if test="${ordReceipt.liang_agree=='1' }">checked</c:if>/>一致</td>
										<td><input type="radio" name="ordReceipt.liang_agree" value="-1" <c:if test="${ordReceipt.liang_agree=='-1' }">checked</c:if>/>不一致</td>
										<td><input type="radio" name="ordReceipt.liang_agree" value="0" <c:if test="${ordReceipt.liang_agree=='0' }">checked</c:if>/>无</td>
									</tr>
									<tr>
										<td>存档编号：</td>
										<td><input type="text" name="ordReceipt.archive_num" value="${ordReceipt.archive_num }" onkeyup="this.value=this.value.replace(/\D/g,'')" 
 onafterpaste="this.value=this.value.replace(/\D/g,'')"/></td>
										<td></td>
										<%-- <td>		<a href="javascript:void(0);" orderbtns="btn" name="o_save" ac_url="shop/admin/orderFlowAction!flowSave.do?btnType=save" show_type="ajaxSubmit" hide_page="list,unvisabled" order_id="${order_id }" form_id="preDealOrderForm" check_fn="checkData" callcack_fn="dealCallback2" class="dobtn" style="margin-right:5px;">保存</a>
										</td> --%>
									</tr>
									
								</table>
								
							</div>
						</div>
					</div>
					<input type="hidden" value="no" name="q_check"/> 
					<jsp:include page="order_handler.jsp?order_id=${order_id }" />
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
<script type="text/javascript">


function load_ord_his(){
	//$("#table_show").show();
	//$("#order_his").show();
	 $("#table_show").toggle();
   // CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!getOrderHis.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);	
};
function OpenDiv(){
	//$("#table_show").show();
	//$("#order_his").show();
	 $("#wo_table").toggle();
};

$(function() {
	//先加载总数和按钮页
	  CommonLoad.loadJSP('order_his_before','<%=request.getContextPath() %>/shop/admin/orderFlowAction!loadOrderHisPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his_before"},false,null,true);
	//CommonLoad.loadJSP('order_his','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_his"},false,null,true);
	  CommonLoad.loadJSP('order_change','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"order_change"},false,null,true);
	  if(${!(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')}){}
	  if(${(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')||orderTree.orderExtBusiRequest.order_from=='10061'}){//多商品(含B2B商城&华盛B2C)
		  CommonLoad.loadJSP('goods_info_more','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_info_more"},false,null,true);
	  }else if(${orderTree.orderExtBusiRequest.order_from=='10062'}){//华盛B2B
		  CommonLoad.loadJSP('hsB2C_goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!hsB2Cgoods.do',{order_id:"${order_id }",ajax:"yes",includePage:"hsB2C_goods_info",order_is_his:"${order_is_his }",query_type:"view"},false,null,true);
	  }else {//非多商品
		  CommonLoad.loadJSP('goods_info','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",county_name:"${county_name}",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_info"},false,null,true);
		  CommonLoad.loadJSP('goods_open','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_open"},false,null,true);
		  CommonLoad.loadJSP('sp_product','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"sp_product"},false,null,true);
		  CommonLoad.loadJSP('goods_gift','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"view_goods_gift"},false,null,true);
	  }
	  if(${orderTree.orderExtBusiRequest.order_from=='10067'}){
		  CommonLoad.loadJSP('goods_dtl','<%=request.getContextPath() %>/shop/admin/orderFlowAction!includeViewPage.do',{order_id:"${order_id }",order_is_his:"${order_is_his }",ajax:"yes",includePage:"goods_dtl"},false,null,true);
	  }
	  /* var data = ${material_retrieve};
	data = eval(data);	
	if (data.length>0) {
		for (var aa =0; aa<data.length; aa++) {
			datas = data[aa];
			var pkey = datas.code;
			$("input:checkbox[name=CheckboxGroup]").each(function(j, ck){
				if (pkey == $(ck).attr("attr_code")) {
					$(ck).attr("checked", "true");
				}
			});
		}
	} */
});
</script>

