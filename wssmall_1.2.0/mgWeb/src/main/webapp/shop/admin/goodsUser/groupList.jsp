<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>



<form id="groupSerchform" >

 <div class="searchformDiv">
   <table>
	 <tr>
	    <th>组名：</th><td><input type="text" class="ipttxt"  style="width:90px" id="group_name" name="group_name" value="${group_name}"/></td>
	   
	    <th></th><td> <input class="comBtn" type="button" name="groupSearchBtn"  id="groupSearchBtn" value="搜索" style="margin-right:10px;"/></td>
	 </tr>
	</table>
     <div style="clear:both"></div>
</div>
</form>
<form >
<div class="grid">
<grid:grid  from="webpage"  ajax="yes">
 <grid:header>
		<grid:cell width="50px"> 选择</grid:cell> 
		<grid:cell sort="group_name" width="180px">分组名</grid:cell> 
		<grid:cell sort="group_type" width="180px">分组类型</grid:cell>
	
  </grid:header>
  <grid:body item="group" > 
  		<grid:cell> <input type="radio" name="groupRadio" group_id ="${group.group_id}" group_name="${group.group_name}" /></grid:cell>
        <grid:cell>&nbsp;${group.group_name } </grid:cell>
        <grid:cell>&nbsp;
         <c:if test="${group.group_type=='confirm'}">确认组</c:if>
         <c:if test="${group.group_type=='delvery'}">发货组</c:if>
         <c:if test="${group.group_type=='pay'}">支付组</c:if>
         <c:if test="${group.group_type=='accept'}">受理组</c:if>
         <c:if test="${group.group_type=='query'}">查询组</c:if>
        </grid:cell>
    
  </grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center">
 <table>
 <tr> <th> &nbsp;</th><td >
  <input name="btn" type="button" id="qrGroupBtn" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>
