
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>



<form id="serchHisForm">

</form>

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_n_cont_sub">
	<tbody>
	
        	<tr>
				&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;${orderhisPageRowCount}&nbsp;条历史订单记录 &nbsp;&nbsp;<input type="button" id="open_order_his_list" value="点击展开" onclick="load_ord_his()" class="graybtn1"></th>
			</tr>
	</tbody>
	
</table>

	<div class="grid_n_div" id="table_show" style="display:none">
              	<div class="grid_n_cont">
					<div class="grid" >
						
                                 <grid:grid from="orderhisPage"  formId="serchHisForm" action="/shop/admin/orderFlowAction!getOrderHis.do?ajax=yes&order_id=${order_id }" ajax="yes">
									<grid:header>
	    								<grid:cell width="5%" >收货人</grid:cell>
	    								<grid:cell width="15%">收货地址</grid:cell>
										<grid:cell width="10%">收货电话</grid:cell>
										<grid:cell width="20%">订单编号</grid:cell>
										<grid:cell width="20%">下单时间</grid:cell>
										<grid:cell width="10%">订单类型</grid:cell>
										<grid:cell width="10%">订单数量</grid:cell>
										<grid:cell width="10%">订单状态</grid:cell>
									</grid:header>
									<grid:body item="orderhis">
										<grid:cell>${orderhis.ship_name}</grid:cell>
										<grid:cell>${orderhis.ship_addr}</grid:cell>
										<grid:cell>${orderhis.ship_tel}</grid:cell>
										<grid:cell>${orderhis.order_id}</grid:cell>
										<grid:cell>${orderhis.tid_time}</grid:cell>
										<grid:cell><html:orderattr disabled="disabled"  attr_code="DC_ORDER_NEW_TYPE" order_id="${orderhis.order_id}" field_name="order_type"  field_desc ="订单类型" field_type="select" ></html:orderattr></grid:cell>
										<grid:cell>${orderhis.goods_num}</grid:cell>
										<grid:cell>${orderhis.new_status}</grid:cell>
										
									</grid:body>
								</grid:grid>
							
					</div>
                </div>
            </div>
