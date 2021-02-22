<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/red_package_add.js"></script>
<div class="input" id="main" style="height: 280px; width: 1129px; visibility: visible; opacity: 1;">
<form action="" method="post" id="rededitform" class="validate">
<div class="tableform">
<div class="division">
<table cellspacing="0" cellpadding="0" border="0" width="100%" class="form-table">
	<tbody>
	  	<tr>
		  	<th>红包名:</th>
		    <td>
		    	<span id=""></span>
		    	<input type="text" class="ipttxt"  required="true" name="promotionRedPkg.name" value="${promotionRedPkg.name }" />
		    </td>
	  	</tr>
	  	<tr>
	  		<th>红包类型:</th>
	  		<td>
	  			<span id=""></span>
		        <select required="true" name="promotionRedPkg.type" class="ipttxt" value="${promotionRedPkg.type }"  disabled/>
		        	<option value="">请选择红包类型</option>
		        	<c:if test="${!empty redTypeList }">
			        	<c:forEach items="${redTypeList }" var="redType">
			        		<option value="${redType.pkey }" <c:if test="${redType.pkey == promotionRedPkg.type}">selected</c:if>>${redType.pname }</option>
			        	</c:forEach>
		        	</c:if>
		        </select>
	       	</td>
	 	</tr>
	 	<tr>
		 	<th>红包个数:</th>
		    <td id="">
		    	<span id=""></span>
	    		<input type="text" class="ipttxt"  required="true" name="promotionRedPkg.num" value="${promotionRedPkg.num }" disabled />
	    	</td>
	  	</tr>
	  	<tr>
		 	<th>红包金额:</th>
		    <td id="">
		    	<span id=""></span>
	    		<input type="text" class="ipttxt"  required="true" name="promotionRedPkg.amount" value="${promotionRedPkg.amount }" disabled />
	    	</td>
	  	</tr>
	  	<tr>
		 	<th>描述:</th>
		    <td id="">
		    	<span id=""></span>
	    		<input type="text" class="ipttxt"  name="promotionRedPkg.memo" value="${promotionRedPkg.memo }" />
	    	</td>
	  	</tr>
	  	<tr>
		 	<th>测试人员（member_id）:</th>
		    <td id="">
		    	<span id=""></span>
	    		<input type="text" class="ipttxt" id="member_id" value="" />
	    	</td>
	  	</tr>
	  	<input type="hidden" name="red_id" id="red_id" value="${promotionRedPkg.id }" />
	</tbody>
</table>
</div>
	<div class="submitlist" align="center">
	<table>
	<tr>
		<td>
			<input id="xg_btn" type="button" value=" 修  改   " class="submitBtn"/>
			<input id="test_btn" type="button" value=" 测试抢红包   " class="submitBtn"/>
		</td>
	</tr>
	</table>
	</div>
</div>
</form>
</div>