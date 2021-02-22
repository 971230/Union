<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>


<div class="tabBg">
    <div class="stat_graph">
        <h3>
            <div class="graph_tab">
                <ul>
                    <li id="show_click_order_base" order_id="${order.order_id }" class="selected"><span class="word">基本信息</span><span class="bg"></span></li>
                    <li id="show_click_return_visit" order_id="${order.order_id }" content_position="right" class=""><span class="word">回访日志</span><span class="bg"></span></li>
                    <li id="show_click_flow_log" order_id="${order.order_id }" content_position="right" class=""><span class="word">流程日志</span><span class="bg"></span></li>
                    <div class="clear"></div>
                </ul>
            </div>
        </h3>
    </div>
</div>
<div id="right_control">
<div class="orderInfo">
   	<div class="orderIco"><img src="<%=request.getContextPath() %>/publics/images/rightico1.png" width="20" height="20"></div>
      <div class="orderMsg">
      	<h2>订单信息</h2>
          <div class="orderTab">
          	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="noBorTable">
		     <tbody>
		     <tr>
		       <th>收货姓名：</th>
		       <td>${order.ship_name }</td>
		     </tr>
		     <tr>
		       <th>订单状仿：</th>
		       <td>${order.orderStatus}</td>
		     </tr>
		     <tr>
		       <th>订单总额：</th>
		       <td>${order.order_amount}</td>
		     </tr>
		     <tr>
		     	<th>购买商品：</th>
		     	<td>
		     		<c:forEach items="${itemList }" var="oi">
		     			<p>${oi.name }<span style="color: red;">[${oi.num }]</span></p>
		     		</c:forEach>
		     	</td>
		     </tr>
		     <c:if test="${query_type=='order' }">
			     <%-- <c:if test="${order.status!=99 }">
			     	<tr>
				       <th>收货地址：</th>
				       <td id="d_ship_addr">${order.shipping_area }${order.ship_addr }</td>
				     </tr>
				     <tr>
				       <th>支付状态：</th>
				       <td id="d_pay_status">${order.payStatus}</td>
				     </tr>
				     <tr>
				       <th>发货状态：</th>
				       <td id="d_ship_status">${order.shipStatus}</td>
				     </tr>
			     </c:if> --%>
			     
			     <c:if test="${orderExceptionList!=null }">
			     	<tr>
			     		<th>异常信息：</th>
				       <td>
				       	<c:forEach items="${orderExceptionList }" var="oe">
				       		<p>[${oe.exception_name }]${oe.comments }</p>
				       	</c:forEach>
				       </td>
			     	</tr>
			     </c:if>
		     </c:if>
		     
		     <c:if test="${query_type=='apply' }">
		     	<tr>
		     	   <th>申请信息：</th>
			       <td>
			       	 ${orderApply.question_desc }
			       </td>
		     	</tr>
		     	<tr>
		     		<th>
		     		<c:if test="${orderApply.service_type=='2' }">
		     			退费金额：
		     		</c:if>
		     		<c:if test="${orderApply.service_type=='4' }">
		     			退货商品：
		     		</c:if>
		     		<c:if test="${orderApply.service_type=='3' }">
		     			换货商品：
		     		</c:if>
		     		</th>
		     		<td>
		     			<c:if test="${orderApply.service_type=='2' }">
		     				${orderApply.refund_value }
			     		</c:if>
			     		<c:if test="${orderApply.service_type=='4' }">
			     			<c:forEach items="${applyItemList }" var="ai">
			     				<p>${ai.name }<span style="color: red;">[${ai.num }]</span></p>
			     			</c:forEach>
			     		</c:if>
			     		<c:if test="${orderApply.service_type=='3' }">
			     			<c:forEach items="${applyItemList }" var="ai">
			     				<p>${ai.name }<span style="color: red;">[${ai.num }][${ai.item_type=='3'?'换':'退' }]</span></p>
			     			</c:forEach>
			     		</c:if>
		     		</td>
		     	</tr>
		     </c:if>
		   </tbody>
		</table>
          </div>
      </div>
  </div>
  <div class="orderInfo">
  	<div class="orderIco"><img src="<%=request.getContextPath() %>/publics/images/rightico2.png" width="20" height="20"></div>
      <div class="orderMsg">
      	<h2>快捷操作</h2>
      	<c:if test="${order.order_record_status==99 }">
      		<!-- 异常单 -->
      		<div class="orderTab" id="order_btn_div">
              <!-- <p style="height: 37px;">
              	  <a name="un_exception_a" href="javascript:void(0);" class="blue_big" ><span>恢复订单</span></a>
              </p> -->
              <div class="btns"><a href="javascript:void(0);" name="deal_order_evn" class="orange_btn">查看订单</a></div>
             </div>  
      	</c:if>
      	<c:if test="${query_type=='order' && monitor==0 && order.order_record_status!=99}">
          <div class="orderTab" id="order_btn_div">
              <p style="height: 37px;">
              	  <a name="op_order_0" href="javascript:void(0);" class="blue_big op_order" ><span>订单补录</span></a>
                  <a name="op_order_2" href="javascript:void(0);" class="blue_big op_order" style="margin-left:20px;"><span>订单备注</span></a>
              </p>
              <p style="height: 37px;">
              	  <a name="op_order_3" href="javascript:void(0);" class="blue_big op_order" ><span>订单异常</span></a>
                  <a name="op_order_1" href="javascript:void(0);" class="blue_big " style="margin-left:20px;" ><span>待客下单</span></a>
              </p>
             
              <c:if test="${hasOrderApply!=true && hasOrderItems && order.status==7}">
              <p style="height: 37px;">
                  	<a id="returned_apply_btn" href="javascript:void(0);return false;" service_type="4" action="new" class="blue_big " ><span>退货申请</span></a>
              	  	<a id="refund_apply_btn" href="javascript:void(0);return false;" service_type="3" action="new" class="blue_big " style="margin-left:20px;"><span>换货申请</span></a>
              </p>
              </c:if>
              <c:if test="${order.status==5||order.status==6||order.status==7 }">
              <p style="height: 37px;">
              	  <a href="javascript:void(0);" class="blue_big" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!order_prnt.do?orderId=${order.order_id }'); return false;"><span style="width: 48px;text-align: center;">购买单</span></a>
				  <a href="javascript:void(0);" class="blue_big" style="margin-left:20px;" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!delivery_prnt.do?orderId=${order.order_id }');return false;"><span style="width: 48px;text-align: center;">配送单</span></a>
              </p>
              <%-- <p style="height: 37px;">
				  <a href="javascript:void(0);" class="blue_big" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!global_prnt.do?orderId=${order.order_id }');return false;"><span style="width: 48px;text-align: center;">合单</span></a>
              </p> --%>
              </c:if>
              <c:if test="${order.status!=-10 }">
              <p style="height: 37px;">
              	  <c:if test="${toDo!=null && toDo.currUserToDo}">
              	  <a id="return_visit_btn" href="javascript:void(0);return false;" class="blue_big"  order_id="${order.order_id }" todo_id="${toDo.list_id }" ><span>订单回访</span></a>
              	  </c:if>
              	  <c:if test="${hasOrderApply!=true && hasOrderItems }">
              	  	<a href="javascript:void(0);" class="blue_big" style="margin-left:${(toDo!=null && toDo.currUserToDo)?20:0}px;" onclick="javascript:window.open('${ctx}/shop/admin/orderPrint!global_prnt.do?orderId=${order.order_id }');return false;"><span style="width: 48px;text-align: center;">合单</span></a>
              	  </c:if>
              	  <%-- <c:if test="${toDo.flow_type=='rob' }">
					<a href="javascript:void(0);" id="BUTTON_ORDER_ROB_BTN" order_id="${order.order_id }" class="blue_big"  style="margin-left:20px;"><span style="width: 48px;text-align: center;">抢单</span></a>
				  </c:if>
				  <c:if test="${orderFlow.service_type=='buy' && toDo.flow_type!='rob' }">
					<a href="javascript:void(0);" id="BUTTON_ORDER_RIB_BTN" order_id="${order.order_id }" class="blue_big" style="margin-left:20px;"><span style="width: 48px;text-align: center;">撤单</span></a>
				  </c:if> --%>
              </p>
              </c:if>
              <c:if test="${toDo!=null &&  toDo.currUserToDo}">
              	<div class="btns"><a href="javascript:void(0);" name="deal_order_evn" class="orange_btn">处理订单</a></div>
              </c:if>
          </div>
          </c:if>
          <c:if test="${query_type=='apply' && monitor==0}">
          <div class="orderTab" id="order_apply_btn_div">
          	<c:if test="${orderApply.service_type=='3'||orderApply.service_type=='4' }">
              <p>
                  <a id="returned_edit_btn" action="edit" href="javascript:void(0);" class="blue_big " ><span>修改/查看</span></a>
                  <c:if test="${orderApply.apply_state==0}">
                  <a id="returned_audit_btn" href="javascript:void(0);" class="blue_big " style="margin-left:20px;"><span>申请审核</span></a>
              	  </c:if>	
              </p>
              </c:if>
              <!-- <div class="btns" ><a href="javascript:void(0);" name="deal_order_evn" class="orange_btn">处理订单</a></div> -->
          </div>
          </c:if>
          
          <c:if test="${monitor==1}">
          <div class="orderTab" id="order_apply_btn_div">
          	  <c:if test="${hasOrderApply!=true && hasOrderItems && order.status==7}">
              <p style="height: 37px;">
                  	<a id="returned_apply_btn" href="javascript:void(0);" service_type="4" action="new" class="blue_big " ><span>退货申请</span></a>
              	  	<a id="refund_apply_btn" href="javascript:void(0);" service_type="3" action="new" class="blue_big " style="margin-left:20px;"><span>换货申请</span></a>
              </p>
              </c:if>
              <c:if test="${toDo!=null && order.status!=-10}">	 
              <p>
                  <a id="order_dispatch_n_btn" right_bth="yes" action="edit" href="javascript:void(0);" order_id="${order.order_id }" class="blue_big " ><span>订单分派</span></a>
                  <c:if test="${toDo.currUserToDo }"> 
                  	 <a id="un_lock_n_btn" href="javascript:void(0);" order_id="${order.order_id }" right_bth="yes" class="blue_big " style="margin-left:20px;"><span>解除锁定</span></a>
              	  </c:if>	
              </p>
              </c:if>
              <div class="btns"><a href="javascript:void(0);" name="deal_order_evn" class="orange_btn">查看订单</a></div>
          </div>
          </c:if>
      </div>
  </div>
</div>            
