<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<div class="input">
	 <form method="post" action="promotion!select_condition.do" id="pmtform">
	   <input type="hidden" name="activityid" value="${activityid }"/>
	   <input type="hidden" name="pmtid" value="${pmtid }" />
	   <input type="hidden" name="cps" value="${cps }" />
	   <table cellspacing="1" cellpadding="3" width="100%" class="form-table">
	   	<c:forEach items="${pluginList}" var="plugin">
      	<tr>
	       <th width="50px"><input type="radio" name="pluginid" value="${plugin.id }" <c:if test="${pluginid==plugin.id }" >checked="checked"</c:if> /></td>
	       <td>${plugin.name }</td>
	      </tr>
	     </c:forEach> 
	     <tr>
	     	<th width="50px"><input type="radio" name="pluginid" value="goodsDiscountPlugin" id="a"/></th>
	     	<td>直降————直降在原价上减多少</td>
	     </tr>
	     <tr >
	     	<th width="50px"><input type="radio" name="pluginid" value="goodsDiscountPlugin" id="b"/></th>
	     	<td>预售————对还没公开销售的物品，提交押金预约</td>
           	
	     </tr>
	   </table>
	   
	   
		<div class="submitlist" align="center">
		 <table>
		 <tr><td >
		  <input  type="submit" value=" 下一步   " class="submitBtn" />
		   </td>
		   </tr>
		 </table>
		</div>

	   
	 </form>
	 
</div>
<script>
$(function(){
	$("#pmtform").submit(function(){
		var result =false;
		$("#pmtform input[type=radio]").each(function(){
			if(this.checked) 
				result=true;
			if($("#a").attr("checked")){
				
				var promotion_type ="006";
				$("#pmtform").attr("action","promotion!select_condition.do?promotion_type="+promotion_type);
			}
			
				
			if($("#b").attr("checked")){
				var promotion_type ="007";
				$("#pmtform").attr("action","promotion!select_condition.do?promotion_type="+promotion_type);
			}
				
		});
		if(!result)
		alert("请选择一种优惠方案");
		return result;
	});
});
</script>