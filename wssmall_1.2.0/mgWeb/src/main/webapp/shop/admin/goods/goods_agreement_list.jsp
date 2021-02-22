<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<script type="text/javascript">
loadScript("js/goods_agreement.js");
</script>
<form method="post" id="serchform" action='goodsAgreementAction!goodsAgreementList.do'>

<div class="searchformDiv">
<table class="form-table">
	<tr>

	    <th>协议名称:</th>
		<td><input type="text" class="ipttxt" id = "agreement_name" name="agreement_name"  value="${agreement_name}"/></td>
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
	    </td>
	   <!-- <td>  <input class="comBtn" type="reset" name="resetbtn" id="searchBtn" value="重置" style="margin-right:10px;"/></td> -->
	</tr>
	 <div style="clear:both"></div>
</table>		
</div>
</form>
<form class="grid" id ="btn_form">

<div class="comBtnDiv" id="agree_del_form">
    <!--   <input type="hidden" name = "messId" id="messId" value="${messId}">-->

	<a href="javascript:;" style="margin-right:10px;" id="agreement_deleteBtn" class="graybtn1" ><span>删除</span></a>
	<!--  <a href="writemsg.jsp" style="margin-right:10px;"  class="graybtn1" ><span>写信</span></a>-->
     <div style="clear:both"></div>
     
</div>

</form>
<form id="gridform" class="grid" ajax="yes">
<div class="grid" id="goodslist">
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
	    <grid:cell width="50px" >
			选择
		</grid:cell>
		<grid:cell width="110px">协议名称</grid:cell>
		<grid:cell width="180px">协议编码</grid:cell>
		<grid:cell width="180px">付款方式</grid:cell>
		<grid:cell width="200px">份额控制</grid:cell>	
		<grid:cell width="180px"> 操作</grid:cell>
	  
		
	</grid:header>
	<grid:body item="agreement">
	 <grid:cell>
	 <input type="checkbox"  id = "agreement_checked" name ="agreement_checked" value="${agreement.agreement_id }" /></grid:cell>
	 <grid:cell>${agreement.agreement_name }</grid:cell>
	 <grid:cell>${agreement.agreement_code }</grid:cell>
	 <grid:cell>
	<c:if test="${agreement.pay_type=='OL'}"></c:if>
	 在线支付</grid:cell>
	 <grid:cell>
	 <c:if test="${agreement.portion=='NO'}">无份额控制</c:if>
	 <c:if test="${agreement.portion=='MO'}">按金额控制</c:if>
	 <c:if test="${agreement.portion=='PN'}">按产品数量控制</c:if> 
	 <c:if test="${agreement.portion=='CO'}">按地市控制</c:if>
	</grid:cell>
	 <grid:cell>
	   <a title="修改框架协议" href="goodsAgreementAction!editGoodsAgreement.do?agreement_id=${agreement.agreement_id}" style="margin-right:10px;" id="readBtn" class="p_prted" >修改</a>   
	 </grid:cell>
		
	</grid:body>
</grid:grid></div>
</form>

