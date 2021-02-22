<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script>
	var begin_nbr ='${accNbr.begin_nbr}';
	var end_nbr ='${accNbr.end_nbr}';
</script>
<script type="text/javascript" src="js/accnbr_avialable_list.js"></script>
<form  method="post" id='accnbr_query_form' action='ContractAction!list_avaible.do'>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
 <input type="hidden" id="from_page" name="from_page" value="${from_page}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
	    	  <th>本地网：</th>
				<td>
						<select  class="ipttxt"  style="width: 100px" id="lan_id" name="lan_id">
							<c:forEach var="lan" items="${lanList}">
							 	<option  value="${lan.lan_id }" ${lan_id == lan.lan_id ? ' selected="selected" ' : ''}>${lan.lan_name }</option>
							 </c:forEach>
					    </select>
				<td>	
    	      <th>号码类型:&nbsp;&nbsp;</th>
			      <td>
			      	<html:selectdict curr_val="${accNbr.acc_type}" name="${accNbr.acc_type}" attr_code="DC_ACC_TYPE"></html:selectdict>
			      </td>
    	      <th>起始号码:&nbsp;&nbsp;</th>
    	      <td>
    	      	<input type="text" class="ipttxt" attr='begin_nbr' name="accNbr.begin_nbr"  class="searchipt" />
    	      </td>
    	      <th>结止号码:&nbsp;&nbsp;</th>
    	      <td>
    	      	<input type="text" class="ipttxt" attr='end_nbr'  name="accNbr.end_nbr"  class="searchipt" />
    	      </td>
    	      <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
   	<c:if test="${from_page=='order'}">
	   	<div class="comBtnDiv">
			<a  style="margin-right:10px;" class="graybtn1" id='accnbr_tansfer_a'><span>号码调拨</span></a>
		</div>
	</c:if>
</form>

<div class="grid">
<form method="POST" >
<grid:grid  from="webpage" ajax="yes">
	<grid:header>
		<grid:cell style="width: 30px;">选择</grid:cell>
		<grid:cell sort="sn" style="width: 100px;">号码</grid:cell>
		<grid:cell style="width: 60px;">状态</grid:cell>
		<grid:cell style="width: 80px;">号码等级</grid:cell>
		<grid:cell style="width: 80px;">预存金</grid:cell>
		<grid:cell style="width: 80px;">最低消费</grid:cell>
		<grid:cell style="width: 80px;">归属本地网</grid:cell>
<%-- 		<grid:cell width="40px">用途</grid:cell> --%>
<%-- 		<grid:cell width="40px">调拨时间</grid:cell> --%>
	</grid:header>
  <grid:body item="accNbr">
  		<grid:cell><input type="radio" name="accnbr_checkbox" value="${accNbr.num_id}"  num_code="${accNbr.num_code}" <c:if test="${accNbr.use_type == '网厅'}">disabled</c:if> /></grid:cell>
        <grid:cell>${accNbr.num_code}</grid:cell>
        <grid:cell>${accNbr.state_name}</grid:cell>
        <grid:cell>${accNbr.acc_level}</grid:cell>
        <grid:cell>${accNbr.pre_cash}</grid:cell>
        <grid:cell>${accNbr.min_consume}</grid:cell>
        <grid:cell>${accNbr.area_name}</grid:cell>
<%--         <grid:cell>${accNbr.use_name}</grid:cell> --%>
<%--         <grid:cell>${accNbr.deal_time}</grid:cell> --%>
  </grid:body>
</grid:grid>  

