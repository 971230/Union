<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/red_package_add.js"></script>
<div class="input" id="main" style="height: 280px; width: 1129px; visibility: visible; opacity: 1;">
<form action="promotionred!redaddsave.do" method="post" id="redpackageform" class="validate">
<div class="tableform">
<div class="division">
<table cellspacing="0" cellpadding="0" border="0" width="100%" class="form-table">
	<tbody>
	  	<tr>
		  	<th>红包名:</th>
		    <td>
		    	<span id=""></span>
		    	<input type="text" class="ipttxt"  required="true" name="promotionRedPkg.name" >
		    </td>
	  	</tr>
	  	<tr>
	  		<th>红包类型:</th>
	  		<td>
	  			<span id=""></span>
		        <select required="true" name="promotionRedPkg.type" class="ipttxt" >
		        	<option value="">请选择红包类型</option>
		        	<c:if test="${!empty redTypeList }">
			        	<c:forEach items="${redTypeList }" var="redType">
			        		<option value="${redType.pkey }">${redType.pname }</option>
			        	</c:forEach>
		        	</c:if>
		        </select>
	       	</td>
	 	</tr>
	 	<tr>
		 	<th>红包个数:</th>
		    <td id="">
		    	<span id=""></span>
	    		<input type="text" class="ipttxt"  required="true" name="promotionRedPkg.num" >
	    	</td>
	  	</tr>
	  	<tr>
		 	<th>红包金额:</th>
		    <td id="">
		    	<span id=""></span>
	    		<input type="text" class="ipttxt"  required="true" name="promotionRedPkg.amount" >
	    	</td>
	  	</tr>
	  	<tr>
		 	<th>描述:</th>
		    <td id="">
		    	<span id=""></span>
	    		<input type="text" class="ipttxt"  name="promotionRedPkg.memo" >
	    	</td>
	  	</tr>
	  	<input type="hidden" id="red_id" value="" />
	</tbody>
</table>
</div>
	<div class="submitlist" align="center">
	<table>
	<tr>
		<td>
			<input name="lr_btn" type="submit" value=" 录    入   " class="submitBtn" />
			<input name="sc_btn" type="submit" value="生成红包" class="submitBtn" />
		</td>
	</tr>
	</table>
	</div>
</div>
</form>
</div>