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
		     
		   </tbody>
		</table>
          </div>
      </div>
  </div>
  <div class="orderInfo">
  	<div class="orderIco"><img src="<%=request.getContextPath() %>/publics/images/rightico2.png" width="20" height="20"></div>
      <div class="orderMsg">
      	<h2>快捷操作</h2>
          <div class="orderTab" id="order_btn_div">
              <div class="btns"><a href="javascript:void(0);" name="deal_order_evn" class="orange_btn">订单详细</a></div>
          </div>
      </div>
  </div>
</div>            
