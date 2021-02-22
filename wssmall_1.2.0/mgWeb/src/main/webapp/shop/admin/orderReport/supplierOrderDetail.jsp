<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>


<div class="grid">
	
<form method="POST">
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell sort="name" width="250px">商品名称</grid:cell> 
	<grid:cell sort="goods_id" width="250px">商品ID</grid:cell>
	<grid:cell sort="payment_name">支付类型</grid:cell>
	<grid:cell sort="goods_num">购买数量</grid:cell>
	<grid:cell sort="create_time">交易时间</grid:cell>
	<grid:cell sort="order_amount">商品价格</grid:cell>
	</grid:header>

  <grid:body item="orderReport">
        <grid:cell>${orderReport.name } </grid:cell>
        <grid:cell>${orderReport.goods_id} </grid:cell>
        <grid:cell>${orderReport.payment_name} </grid:cell>
        <grid:cell>${orderReport.goods_num} </grid:cell>
        <grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:SS" d_time="${orderReport.create_time}"></html:dateformat></grid:cell>
        <grid:cell>￥${orderReport.order_amount}</grid:cell>
  </grid:body>  
  <div class='comBtnDiv'><a href='orderReport!orderReportList.do' style="margin-right:20px;" class="graybtn1"><span>返回</span></a>
</grid:grid>  
</form>	
</div>
