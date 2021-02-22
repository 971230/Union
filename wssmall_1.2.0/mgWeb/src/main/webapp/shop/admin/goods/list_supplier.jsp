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
<form id="serchform">



	<div class="searchformDiv" style="height:auto">
	<div id="searchcbox" style="margin-left:0px">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<tr>
	  <th>供应商名称：</th><td><input type="text" class="ipttxt"  style="width:90px" name="supplier_name" value="${supplier_name}"/></td>
	  <th>公司名称：</th><td><input type="text" class="ipttxt"  style="width:90px" name="supplier_companyName" value="${supplier_companyName}"/></td>
	  <th></th><td> <input type="button" id="searchBtn" class="comBtn" name="searchBtn" value="搜索"></td>
	   </tr>
	</div>	
     <div style="clear:both"></div>
	</div>
<div class="grid">
<grid:grid  from="webpage" ajax="yes" formId="serchform">
 <grid:header>
		<grid:cell width="50px"> <a href='javascript:void(0);'>选择</a></grid:cell> 
	    <grid:cell sort="username" width="120px"><a href='javascript:void(0);'>供应商名称</a></grid:cell> 
		<grid:cell sort="realname" width="180px"><a href='javascript:void(0);'>公司名称</a></grid:cell>
		<grid:cell sort="email" width="120px"><a href='javascript:void(0);'>邮箱</a></grid:cell> 
		<grid:cell sort="phone" width="120px"><a href='javascript:void(0);'>联系电话</a></grid:cell> 
  </grid:header>
  <grid:body item="supplier" > 
  		<grid:cell><input type="radio" name="supplier_id" value="${supplier.supplier_id},${supplier.user_name }"/></grid:cell>
        <grid:cell>&nbsp;${supplier.user_name } </grid:cell>
        <grid:cell>&nbsp;${supplier.company_name } </grid:cell>
        <grid:cell>&nbsp;${supplier.email } </grid:cell>
        <grid:cell>&nbsp;${supplier.phone } </grid:cell>
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