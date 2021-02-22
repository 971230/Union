<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script>
	var outLanId = '${order.lan_id}';
	var goods_id = '${order.goods_id}';
</script>
<script type="text/javascript" src="js/cloud_random_list.js"></script>

<c:if test="${from_page=='order'}">
	<div class="warm_reminder">
        	<p class="title"><i class="warmico"></i>温馨提示</p>
            <p>1、云卡金额为：${order.goods_amount}（元），已支付总金额为<span class='red'>${order.order_amount}</span>（元）</p>
<%--             <p>2、用户申请调拨号码段为：${begin_nbr}-${end_nbr}</p> --%>
            <p>2、用户购买云卡总数为：<span class='red'>${order.goods_num}</span>（个），已调拨云卡数量为：<span class='red'>${succ_count}</span>（个）,剩余调拨数量为：<span class='red'>${order.goods_num-succ_count}</span>（个）</p>
            <p>3、正在处理中的云卡数量为：<span class='red'>${dealing_count}</span>（个）</p>  
   </div>
</c:if>
<form  method="post" id='cloud_query_form' action='cloud!list.do'>
<input type="hidden" id="hidden_lan_id" value=""/>
<input type="hidden" id="hidden_b_acc_type" value=""/>
<input type="hidden" id="hidden_e_acc_type" value=""/>
<input type="hidden" id="hidden_acc_type" value=""/>
<input type="hidden" id="hidden_sel_type" value=""/>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
 <input type="hidden" id="from_page" name="from_page" value="${from_page}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody>
    	     
    	     <tr>
    	     <c:if test="${show_acc_interval=='yes'}">
    	      <th>新起始号码:&nbsp;&nbsp;</th>
    	      <td>
    	      	<input type="text" class="ipttxt" attr="p_begin_nbr" autocomplete="off" dataType="mobile"   style="width: 150px" name="begin_nbr" value="" class="searchipt" />
    	      	<a href="javascript:void(0)" class="sgreenbtn" > 
			    		<span name="cloudStockView" sel_type='begin'  style='color:#fff;'>查看可用库存</span>
			 	</a>
    	      </td>
    	      
    	      <th>新结束号码:&nbsp;&nbsp;</th>
    	      <td>
    	     	<input type="text" class="ipttxt"  attr="p_end_nbr" autocomplete="off" dataType="mobile"    style="width: 150px" name="end_nbr" value="" class="searchipt"/>
    	      	<a href="javascript:void(0)" class="sgreenbtn" > 
			    		<span name="cloudStockView" sel_type='end'  style='color:#fff;'>查看可用库存</span>
			  	</a>
    	      </td>
    	      <span class='red' id='order_goods_count_span'></span>
    	      </c:if>
    	      <th>
<%--     	      	 <c:if test="${from_page=='order' && adminUser.founder == 3}"> --%>
<!-- 				   	<div class="comBtnDiv"> -->
<!-- 						<a  style="margin-right:10px;" class="graybtn1" id='cloud_tansfer_a'><span>调拨</span></a> -->
<!-- 					</div> -->
<%-- 				</c:if> --%>
<%--     	      	 <c:if test="${adminUser.founder == 0 || adminUser.founder == 1}"> --%>
<!-- 				   	<div class="comBtnDiv" style="text-align: center"> -->
<!-- 				   	  <p>请根据订单号上CRM系统进行调拨</p> -->
<!--                     </div>    	      	       -->
<%-- 				 </c:if> --%>
                 <c:if test="${from_page=='order'}">
                    <div class="comBtnDiv">
                        <a  style="margin-right:10px;" class="graybtn1" id='cloud_tansfer_a'><span>调拨</span></a>
                    </div>
                </c:if>
    	      </th>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
</form>
<div id="cloud_dialog">
</div>
<form  class="validate" method="post" action="" id='acc_number_form' validate="true">
</form>


