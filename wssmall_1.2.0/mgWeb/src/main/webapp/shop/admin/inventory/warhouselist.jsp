<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<style>
.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}

.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}

h4  {
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

.branch_list {
padding: 6px;
overflow: hidden;
margin: 0;
line-height: 30px;
color: #444;
}

ul{
display: block;
list-style-type: disc;
-webkit-margin-before: 1em;
-webkit-margin-after: 1em;
-webkit-margin-start: 0px;
-webkit-margin-end: 0px;
-webkit-padding-start: 40px;
}

ul li{
list-style: none;
}

</style>
<div class="input" >
<form id="test_submit_aaaa_form"  method="post"  class="validate">
<div class="tableform">
<h4>选择仓库</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody>
		<tr>
			<td>
			<ul class="branch_list">
			 	<c:forEach items="${wareHouseList }" var="wh">
			 		<li>
			 			<input type="radio" name="house_id" value="${wh.house_id }" />
			 			${wh.house_name }
			 		</li>
			 	</c:forEach>
			 </ul>	
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>
<div class="submitlist" style="width: 100%;">
 <table style="width: 100%;">
 <tr>
 <td style="text-align: right;">
   <input  type="button" id="inventory_next_btn" value=" 下一步   " class="submitBtn" />
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   </td>
   </tr>
 </table>
</div>
</form>
</div>

