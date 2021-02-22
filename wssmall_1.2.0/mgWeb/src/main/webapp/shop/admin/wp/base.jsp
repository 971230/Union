<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="tableform">
<table cellspacing="0" cellpadding="0" border="0" style="width: auto;" class="orderdetails_basic">
  <tbody>
  <tr>
    <%-- <td style="vertical-align: top;">
		<h5>物流信息</h5>
		<div class="division">
		    <table cellspacing="0" cellpadding="0" border="0">
		      <tbody>
		       <tr>
			  		<th style="width: 80px;">物流编号：</th>
			        <td>${delivery.logi_no }</td>
			        <th style="width: 80px;">物流公司：</th>
			        <td>${delivery.logi_name }</td>
			  	</tr>
			  	<tr>
			        <th style="width: 80px;">数量：</th>
			        <td colspan="3">${delivery.ship_num }</td>
			  	</tr>
		      </tbody>
		      </table>
		</div>
    </td> --%>
    
    <td style="vertical-align: top;">
		<h5>订单信息</h5>
		<div class="division">
		    <table cellspacing="0" cellpadding="0" border="0">
		      
		      <tbody>
		      <tr>
		        <th style="width: 80px;">订单ID：</th>
		        <td>${order.order_id }</td>
		      </tr>
		       <tr>
		        <th style="width: 80px;">订购数量：</th>
		        <td>${order.goods_num}</td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">订单总金额：</th>
		        <td>￥${order.order_amount }</td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">商品总金额：</th>
		        <td>
		        ￥${order.goods_amount }
				</td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">物流费用：</th>
		        <td>￥${order.shipping_amount }</td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">已支付金额：</th>
		        <td>￥${ord.paymoney }</td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">支付方式：</th>
		        <td>${order.payment_name }</td>
		      </tr>
		    </tbody>
		    </table>
		</div>
    </td>
    <td style="vertical-align: top;">
    <h5>${order.create_type==6||order.create_type==7?'退货':'采购' }单信息</h5>
    <div class="division">
    <table cellspacing="0" cellpadding="0" border="0">
        <tbody>
        	<tr>
		        <th style="width: 80px;">${order.create_type==6||order.create_type==7?'退货':'采购' }单号：</th>
		        <td>${delivery.delivery_id }
		        </td>
		      </tr>
		     <tr>
		        <th style="width: 80px;">预存款：</th>
		        <td>${warehousePurorder.deposit }
		        </td>
		      </tr>
		    <tr>
		        <th style="width: 80px;">供货商：</th>
		        <td>${supplier.company_name }
		        </td>
		      </tr>
		     <tr>
		        <th style="width: 80px;">仓库：</th>
		        <td>${warehouse.house_name }
		        </td>
		      </tr>  
        	<tr>
		        <th style="width: 80px;">预计到达时间：</th>
		        <td>${warehousePurorder.pru_delvery_finish_time }
		        </td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">审核状态：</th>
		        <td><c:if test="${warehousePurorder.audit_status==0 }">未审核</c:if>
        			<c:if test="${warehousePurorder.audit_status==1 }">审核通过</c:if>
        			<c:if test="${warehousePurorder.audit_status==2 }">审核不通过</c:if>
        	    </td>
		      </tr>
		      <tr>
		        <th style="width: 80px;">${order.create_type==6||order.create_type==7?'退货':'采购' }类型：</th>
		        <td>
		        	<c:if test="${warehousePurorder.pru_type==0 }">现金</c:if>
        			<c:if test="${warehousePurorder.pru_type==1 }">赊购</c:if>
		        </td>
		      </tr>
		       
		      <%-- <tr>
		        <th style="width: 80px;">入库状态：</th>
		        <td>
		        	<c:if test="${warehousePurorder.pru_stock_status==0 }">待入库</c:if>
        			<c:if test="${warehousePurorder.pru_stock_status==1 }">部分入库</c:if>
        			<c:if test="${warehousePurorder.pru_stock_status==2 }">已入库</c:if>
		        </td>
		      </tr> --%>
		      
          </tbody>
          </table>
		</div>
    </td>
    <td style="vertical-align: top;">
            <h5 colspan="2">收货人信息 
			</h5>
        <div class="division">
        <table cellspacing="0" cellpadding="0" border="0">
          
          <tbody><tr>
          	 
           <th style="width: 80px;">发货日期：</th>
           <td>${order.ship_day }${order.ship_time }</td>
            
          </tr>
          <tr>
            <th style="width: 80px;">姓名：</th>
            <td>${order.ship_name }</td>
          </tr>
          <tr>
            <th style="width: 80px;">电话：</th>
            <td>${order.ship_tel}</td>
          </tr>
          <tr>
            <th style="width: 80px;">手机：</th>
            <td>${order.ship_mobile}</td>
          </tr>
          
          <tr>
            <th style="width: 80px;">地区：</th>
            <td>${order.shipping_area}</td>
          </tr>
         
          <tr>
            <th style="width: 80px;">地址：</th>
            <td style="white-space: normal; line-height: 18px;">${order.ship_addr}</td>
          </tr>
          <tr>
            <th style="width: 80px;">邮编：</th>
            <td>${order.ship_zip}</td>
          </tr>
           <tr>
      </tr>
     </tbody>
     </table>
 </div>
  </td>
  </tr>
</tbody>
</table>
</div>