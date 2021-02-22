<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
      <table cellspacing="0" cellpadding="0" border="0" width="100%"  >
        <thead>
        <tr>
          <th>订单商品 (购买量)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${itemList}" var="item">
         <tr>
          <td style="white-space: normal; text-align: left;">
          <a >${item.name }</a><!-- 李改：去了两点 -->
          <span class="fontcolorOrange fontbold">×<span style='font-weight:bold;font-size:1.2em'>(${item.num })</span></span></td>
        </tr>
		</c:forEach>
         </tbody>
      </table>
</div>
    
<div class="tableform">
<table cellspacing="0" cellpadding="0" border="0" style="width: auto;" class="orderdetails_basic">
  <tbody>
  <tr>
    <td style="vertical-align: top;">
    
		<h5>商品价格</h5>
		<div class="division">
		    <table cellspacing="0" cellpadding="0" border="0">
		      <tbody>
		       <%-- <tr>
		        <th style="width: 80px;">商品单价：</th>
		        <td>￥${ord.goods_amount/ord.goods_num} </td>
		      </tr> --%>
		       <tr>
		        <th style="width: 80px;">订购数量：</th>
		        <td>${ord.goods_num}</td>
		      </tr>
		     
		      <tr>
		        <th style="width: 80px;">商品总额：</th>
		        <td><fmt:formatNumber value="${ord.goods_amount }" type="currency" pattern="￥.00"/></td>
		      </tr>
		     
		      <tr>
		        <th style="width: 80px;">配送费用：<input type="hidden" id="orderid" value="${orderId }" /></th>
		        <td><div id="order_shipping_price_div"><span id="order_shipping_price">￥${ord.shipping_amount }</span>
		        		<c:if test="${ord.status!=7 && ord.status!=6}">
		        		<a href="javascript:void(0);" id="editShippingPriceBtn" style="font-size:12px">修改</a>
		        		</c:if>
		        	</div>
		        	<div id="order_shipping_price_edit_div" style="display: none;">
		        		<span style="display: none;" id="od_amount_val">${ord.order_amount }</span>
		        		<span style="display: none;" id="od_shipint_val">${ord.shipping_amount }</span>
		        		<input type="text" id="shipping_edit_ip" value="${ord.shipping_amount }" size="3" />
		        		<input type="button" value="确定" id="shipping_edit_btn" />
		        	</div>
		        </td>
		      </tr>
			           
		       <tr>
		        <th style="width: 80px;">订单总额：</th>
		        <td style="font-size: 16px; color: rgb(255, 153, 0); font-weight: 700;">
		        <span id="order_price_span">￥${ord.order_amount }</span>
		        <c:if test="${ord.status!=7 && ord.status!=6}">
		        <a href="javascript:;" id="editPriceBtn" style="font-size:12px">修改</a>
		        </c:if>
		        </td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">已支付金额：</th>
		        <td>￥${ord.paymoney }</td>
		      </tr>
		      </tbody>
		      </table>
		</div>
    </td>
    
    <td style="vertical-align: top;">
<h5>订单其他信息</h5>
<div class="division">
    <table cellspacing="0" cellpadding="0" border="0">
      
      <tbody><tr>
        <th style="width: 80px;">配送方式：</th>
        <td>${ord.shipping_type }</td>
      </tr>
      <tr>
        <th style="width: 80px;">配送保价：</th>
        <td>
        <c:if test="${ord.is_protect==1}">是</c:if>
		<c:if test="${ord.is_protect==0}">否</c:if>
		</td>
      </tr>
      <tr>
        <th style="width: 80px;">商品重量：</th>
        <td>${ord.weight } g</td>
      </tr>
      <tr>
        <th style="width: 80px;">支付方式：</th>
        <td>${ord.payment_name }</td>
      </tr>
    	<c:if test="${bank.img_url!=null}">
		     <tr>
		        <th style="width: 80px;">支付银行：</th>
		        <td><img src="${ctx}${bank.img_url}" /></td>
		      </tr>
     	 </c:if>
     <!-- 
     <tr>
        <th style="width: 80px;">可得积分：</th>
        <td>${ord.gainedpoint }</td>
      </tr>
       -->
    </tbody></table>
</div>
    </td>
    <td style="vertical-align: top;">
    <h5>会员信息</h5>
    <div class="division">
    <table cellspacing="0" cellpadding="0" border="0">
      
            <tbody><tr>
        <th style="width: 80px;">用户工号：</th>
        <td>${member.uname }
        </td>
      </tr>
      <tr>
        <th style="width: 80px;">姓名：</th>
        <td>${member.name }</td>
      </tr>
      <tr>
        <th style="width: 80px;">电话：</th>
        <td>${member.tel }</td>
      </tr>
       
      <tr>
        <th style="width: 80px;">地区：</th>
        <td>${member.province }-${member.city}-${member.region }</td>
      </tr>
        <tr>
         <td>Email：</td><td>demo@demo.com
	     </td></tr>
	     <tr>
	       <td>省份：</td><td>
	     </td></tr>
	     <tr>
	       <td>邮编：</td><td>
	     </td></tr>
      <tr>
        <th style="width:80px;" >地址：</th>
        <td></td>
      </tr>
          </tbody></table>
		</div>
    </td>
    <td style="vertical-align: top;">
            <h5 colspan="2">收货人信息 
			</h5>
        <div class="division">
        <table cellspacing="0" cellpadding="0" border="0">
          
          <tbody><tr>
          	 
           <th style="width: 80px;">发货日期：</th>
           <td>${ord.ship_day }${ord.ship_time }</td>
            
          </tr>
          <tr>
            <th style="width: 80px;">姓名：</th>
            <td>${ord.ship_name }</td>
          </tr>
          <tr>
            <th style="width: 80px;">电话：</th>
            <td>${ord.ship_tel}</td>
          </tr>
          <tr>
            <th style="width: 80px;">手机：</th>
            <td>${ord.ship_mobile}</td>
          </tr>
          
          <tr>
            <th style="width: 80px;">地区：</th>
            <td>${ord.shipping_area}</td>
          </tr>
         
          <tr>
            <th style="width: 80px;">地址：</th>
            <td style="white-space: normal; line-height: 18px;">${ord.ship_addr}</td>
          </tr>
          <tr>
            <th style="width: 80px;">邮编：</th>
            <td>${ord.ship_zip}</td>
          </tr>
           <tr>
         <th style="width: 80px;">Email：</th>
        <td><a href="mailto:demo@demo.com" target="_self">${member.email }</a></td>
      </tr>
           </tbody>
           </table>
 </div>
          </td>
  </tr>
</tbody></table></div>