<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form id="serchform">
<div class="input">
<table class="form-table">
	<tr>
		<th>
		用户账号：
		</th>
		<td>
		<input type="text" class="ipttxt" id="uname" name="uname"  value="${uname }"/>
		<a href="javascript:void(0)" id="member_search_btn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a>
		</td>
	</tr>
</table>		
</div>

<div style="clear:both;padding-top:5px;"></div>

<div class="grid" id="goodslist">

<grid:grid  from="memberPage" ajax="yes" >

	<grid:header>
		<grid:cell width="50px"></grid:cell> 
		<grid:cell width="80px">账号</grid:cell> 
		<grid:cell width="250px">用户名</grid:cell>
	</grid:header>


  <grid:body item="member" >
  		<grid:cell><input type="radio" name="memberid" value="${member.member_id }|${member.uname }" /></grid:cell>
        <grid:cell>&nbsp;${member.uname } </grid:cell>
        <grid:cell>&nbsp;<span name="goodsname">${member.name }</span></grid:cell> 
  </grid:body>
  
</grid:grid>
</div>

<div class="submitlist" align="center">
 <table>
 <tr><td >
  <input id="member_confirm_btn" type="button" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>
