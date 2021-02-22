<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid" >
<form method="POST" id="accnbr_list_form">
<grid:grid  from="webpage" excel="yes">
	<grid:header>
		<grid:cell style="width: 30px;">选择</grid:cell>
		<grid:cell sort="sn" style="width: 100px;">号码</grid:cell>
		<grid:cell style="width: 80px;">状态</grid:cell>
		<grid:cell style="width: 80px;">号码等级</grid:cell>
		<grid:cell style="width: 80px;">预存金</grid:cell>
		<grid:cell style="width: 80px;">最低消费</grid:cell>
		<grid:cell style="width: 80px;">归属本地网</grid:cell>
		<grid:cell style="width: 80px;">用途</grid:cell>
		<grid:cell style="width: 80px;">调拨时间</grid:cell>
		<grid:cell style="width: 80px;">号码号段</grid:cell>
		<grid:cell style="width: 80px;">选号时间</grid:cell>
		<grid:cell style="width: 80px;">修改时间</grid:cell>
		<grid:cell style="width: 80px;">占用时间</grid:cell>
	</grid:header>
  <grid:body item="accNbr">
  		<grid:cell><input type="checkbox" name="accnbr_checkbox" value="${accNbr.num_id}" <c:if test="${accNbr.use_type == '网厅'}">disabled</c:if> /></grid:cell>
        <grid:cell>${accNbr.num_code}</grid:cell>
        <grid:cell>${accNbr.state_name}</grid:cell>
        <grid:cell>
        <c:choose>
        	<c:when test="${accNbr.acc_level == 1}">
        		一类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 2}">
        		二类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 3}">
        		三类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 4}">
        		四类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 5}">
        		五类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 6}">
        		六类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 7}">
        		七类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 8}">
        		八类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 9}">
        		九类号码
        	</c:when>
        	<c:when test="${accNbr.acc_level == 10}">
        		普通号码
        	</c:when>
        	<c:otherwise>
        		${accNbr.acc_level}
        	</c:otherwise>
        </c:choose>
        </grid:cell>
        <grid:cell>${accNbr.pre_cash}</grid:cell>
        <grid:cell>${accNbr.min_consume}</grid:cell>
        <grid:cell>${accNbr.area_name}</grid:cell>
        <grid:cell>${accNbr.use_name}</grid:cell>
        <grid:cell>${accNbr.deal_time}</grid:cell>
        <grid:cell>${accNbr.code_head_name}</grid:cell>
        <grid:cell>${accNbr.choose_date}</grid:cell>
        <grid:cell>${accNbr.modify_date}</grid:cell>
        <grid:cell>${accNbr.take_date}</grid:cell>
  </grid:body>
</grid:grid>  
</form>

</div>

