<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/orderouter.js"></script>

<form  method="post" id='query_form' action='orderouter!list.do'>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
    	      <th>订单编号:</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="ordParam.order_id" value="${ordParam.order_id}" class="searchipt" /></td>
    	      <th>批次号:</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="ordParam.batch_id" value="${ordParam.batch_id}" class="searchipt" /></td>
    	      
    	      <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
   	<div class="comBtnDiv">
		<a  style="margin-right:10px;" class="graybtn1" id='order_sys_btn'><span>订单同步</span></a>
		<a  style="margin-right:10px;" class="graybtn1" id='order_import_btn'><span>订单导入</span></a>
		<a  style="margin-right:10px;" class="graybtn1" id='order_delete_btn'><span>删除</span></a>
	</div>
	<div id="importDialog">
		
	</div>
</form>
<div id="orderList">
	<jsp:include page="import_list.jsp" flush="true"></jsp:include>
</div>

