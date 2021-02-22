<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<div id="orderExpMarkProcessedDiv">
	<table width="100%" cellspacing="2" cellpadding="2" border="0">
		<tbody>
			<tr style="height: 40px;display:none;">
		    	<th style="text-align: right;">处理状态：</th>
		    	<td>
					<select id="eopInner_record_status">
						<option value="1">已处理</option>
					</select>
				</td>
		   	</tr>
		    <tr>
		    	<th style="text-align: right;">处理备注：</th>
		    	<td>
					<textarea class="ipttxt" id="eopInner_deal_result" style="width:300px;height:120px"></textarea> 
				</td>
		   	</tr>
	  	</tbody>
	</table>
	  	<div style="margin-left: auto;margin-right: auto;" align="center">
        	<input name="btn" type="button" id="markBtn" value="标记已处理"  class="graybtn1" />
      	</div>
</div>
<script type="text/javascript">
var orderExpMarkProcessed= {
    init:function(){
        var me=this;
        $("#markBtn").unbind("click").bind("click",function() {
    		me.expMarkProcessed();
     	});
    },
    expMarkProcessed:function(){
    	OrderExp.batchExpMarkProcessed();
    }
}
orderExpMarkProcessed.init();
</script>