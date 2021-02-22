<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/red_package_add.js"></script>
<div class="input" id="main" style="height: 280px; width: 1129px; visibility: visible; opacity: 1;">
<form action="promotionred!redaddsave.do" method="post" id="redpackageform" class="validate">
	<div class="tableform">
	<div class="division">
	<table cellspacing="0" cellpadding="0" border="0" width="100%" >
  	<tr>
  		<th>红包名:</th>
		<td>
	    	${promotionRedPkg.name }
	    	<input type="hidden" name="promotionRedPkg.name" value="${promotionRedPkg.name }" />
	    </td>
  		<th>红包类型:</th>
  		<td>
	        <select name="promotionRedPkg.type" value="${promotionRedPkg.type }" disabled="disabled"/>
	        	<option value="">请选择红包类型</option>
	        	<c:if test="${!empty redTypeList }">
		        	<c:forEach items="${redTypeList }" var="redType">
		        		<option value="${redType.pkey }" <c:if test="${redType.pkey==promotionRedPkg.type}">selected</c:if>>${redType.pname }</option>
		        	</c:forEach>
	        	</c:if>
	        </select>
       	</td>
	 	<th>红包个数:</th>
	 	<td>
	    	${promotionRedPkg.num }
    		<input type="hidden" name="promotionRedPkg.num" value="${promotionRedPkg.num }" />
    	</td>
	 	<th>红包金额:</th>
	 	<td>
	    	${promotionRedPkg.amount }
    		<input type="hidden" name="promotionRedPkg.amount" value="${promotionRedPkg.amount }" />
    	</td>
	 	<th>描述:</th>
	 	<td>
	    	<span id="">${promotionRedPkg.memo }</span>
    		<input type="hidden" name="promotionRedPkg.memo" value="${promotionRedPkg.memo }" />
    	</td>
 	</tr>
  	<input type="hidden" name="red_id" id="red_id" value="${promotionRedPkg.id }" />
	</table>
	</div>
	</div>
	<div class="netWarp">
	<div class="goodCon">
	<table width="100%" class="grid_s">
	<tr>
	<th style="text-align: center;">红包优惠券ID</th>
	<th style="text-align: center;">红包创建时间</th>
	<th style="text-align: center;">红包修改时间</th>
	<th style="text-align: center;">红包使用状态</th>
	<th style="text-align: center;">优惠券编码</th>
	<th style="text-align: center;">红包ID</th>
	<th style="text-align: center;">会员名称</th>
	</tr>
	<c:forEach var="cpns" items="${cpnsList }">
	<tr>
		<td>${cpns.cpns_id }</td>
		<td>${cpns.create_time }</td>
		<td>${cpns.update_time }</td>
		<td>
		<c:choose>
			<c:when test="${cpns.state == '0' }">已生成</c:when>
			<c:when test="${cpns.state == '1' }">已发放</c:when>
			<c:when test="${cpns.state == '2' }">已使用</c:when>
		</c:choose>
		</td>
		<td>${cpns.cpns_code }</td>
		<td>${cpns.redpkgid }</td>
		<td>${cpns.uname }</td>
	</tr>
	</c:forEach>
	</table>
	</div>
	</div>
</form>
</div>