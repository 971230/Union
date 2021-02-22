<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan{
	position: absolute;
	display:none;
}
#searchcbox{float:left}
#searchcbox div{float:left;margin-left:10px}
</style>
<form id="agreementserchform">



	<div class="searchformDiv" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<tr>
	  <th>框架名称：</th><td><input type="text" class="ipttxt"  style="width:90px" name="agreement_name" value="${agreement_name}"/></td>
	  <th></th><td> <input type="button" id="searchBtn" class="comBtn" name="searchBtn" value="搜索"></td>
	   </tr>
	</div>	
     <div style="clear:both"></div>
	</div>
<div class="grid">
<grid:grid  from="webpage" ajax="yes" formId="serchform">
 <grid:header>
	    <grid:cell width="50px" >
			选择
		</grid:cell>
		<grid:cell width="110px">协议名称</grid:cell>
		
		<grid:cell width="200px">份额控制</grid:cell>	
	  
		
	</grid:header>
	<grid:body item="agreement">
	<grid:cell><input type="radio" name="agreement_id" value="${agreement.agreement_id},${agreement.agreement_name },${agreement.portion },${agreement.sub_portion }"/></grid:cell>
    <grid:cell>${agreement.agreement_name }</grid:cell>
	  <grid:cell>
	 <c:if test="${agreement.portion=='NO'}">无份额控制</c:if>
	 <c:if test="${agreement.portion=='MO'}">按金额控制</c:if>
	 <c:if test="${agreement.portion=='PN'}">按产品数量控制</c:if>
	 <c:if test="${agreement.portion=='CO'}">按地市控制</c:if>
	</grid:cell>
		
	</grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input name="btn" type="button" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>