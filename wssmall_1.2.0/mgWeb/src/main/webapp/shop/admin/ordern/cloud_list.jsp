<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/cloud_list.js"></script>
<c:if test="${from_page=='order'}">
	<div class="warm_reminder">
        	<p class="title"><i class="warmico"></i>温馨提示</p>
            <p>1、用户已支付金额为${order.order_amount}（元）</p>
            <p>1、选择充值卡总面额为<span id='all_card_money'>0</span>（元）</p>
   </div>
</c:if>

<form  method="post" id='cloud_query_form' action='cloud!list.do'>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
 <input type="hidden" id="from_page" name="from_page" value="${from_page}" />
 
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
	    	    <th>本地网：</th>
					<td>
						<select  class="ipttxt"  style="width: 100px" name="lan_id" id="lan_id">
							<c:forEach var="lan" items="${lanList}">
								<option  value="${lan.lan_id }" ${lan_id == lan.lan_id ? ' selected="selected" ' : ''}>${lan.lan_name }</option>
							</c:forEach>
					    </select>
					<td>
	    	      <th>号码状态:&nbsp;&nbsp;</th>
	    	      <td>
	    	      		<c:if test="${from_page!='order'}">
			    	      	<html:selectdict name="cloud.state"  appen_options="<option value=''>--全部--</option>" curr_val="${cloud.state}" attr_code="DC_NUM_STATE">
							</html:selectdict>
						</c:if>
						<c:if test="${from_page=='order'}">
			    	      	<select  class="ipttxt"  name="cloud.state" ><option value='0'>可用</option></select>
						</c:if>
	    	      </td>
    	      <th>业务号码:&nbsp;&nbsp;</th>
    	      <td><input type="text" class="ipttxt"  style="width: 95px;" name="cloud.phone_num" value="${cloud.phone_num}" class="searchipt" /></td>
    	      <th>批次号:</th>
    	      <td><input type="text" class="ipttxt"  style="width: 95px;" name="cloud.batch_id" value="${cloud.batch_id}" class="searchipt" /></td>
    	      <th>销售品名称:</th>
    	      <td><input type="text" class="ipttxt"  style="width: 95px;" name="cloud.offer_name" value="${cloud.offer_name}" class="searchipt" /></td>
   	     	  <th>批开金额:</th>
    	      <td><input type="text" class="ipttxt" dataType="int" style="width: 120px" name="cloud.pay_money" value="${cloud.pay_money}" class="searchipt" /></td>
    	      <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="submitButton" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
   	<c:if test="${adminUser.founder==0 or adminUser.founder == 1}">
	   	<div class="comBtnDiv">
			<a  style="margin-right:10px;" class="graybtn1" id='cloudCard_import_btn'><span>云卡导入</span></a>
		</div>
	</c:if>
	<div id="importDialog">
		
	</div>
   	
    <c:if test="${from_page=='order'}">
	   	<div class="comBtnDiv">
			<a  style="margin-right:10px;" class="graybtn1" id='cloud_tansfer_a'><span>云卡调拨</span></a>
		</div>
	</c:if>
</form>

<div id="cloudList">
	<jsp:include page="import_cloud_all_list.jsp" flush="true"></jsp:include>
</div>

