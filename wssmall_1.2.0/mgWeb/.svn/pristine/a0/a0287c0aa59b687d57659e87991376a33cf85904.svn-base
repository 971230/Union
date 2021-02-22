<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<div id="orderExpMarkProcessedDiv">
	<table width="100%" cellspacing="2" cellpadding="2" border="0">
		<input type="hidden" id="single_exp_inst_id" value="<%=request.getParameter("single_exp_inst_id") == null ? "":request.getParameter("single_exp_inst_id")%>"/>
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
					<textarea class="ipttxt" id="eopInner_deal_result" style="width:500px;height:60px"></textarea> 
				</td>
		   	</tr>
	  	</tbody>
	</table>
	  	<div style="margin-left: auto;margin-right: auto;" align="center">
        	<input name="btn" type="button" id="orderExpProcessBtn" value="处理"  class="graybtn1" />
      	</div>
      	
    <div class="right_warp">
        <form action="" id="process_result_fm" class="grid">
        	<div class="gridbody"  gridid='process_result_f' >
        	<table>
        		<thead>
					<tr>
						<th colspan="6">处理结果</th>
	    			</tr>
	    		</thead>
				<thead>
					<tr>
    					<th style="width:100px;">异常单ID</th>
						<th style="width:100px;">实例ID</th>
						<th style="width:60px;">处理状态</th>
						<th style="width:60px;">方案类型</th>
						<th style="width:180px;">方案内容</th>
						<th>处理结果</th>
	    			</tr>
	    		</thead>
	    		<tbody id="order_exp_process_result_tby">
	    			
	    		</tbody>
	    	</table>
	    </div>
	  </form>
	</div>
</div>
<script type="text/javascript">
var orderExpProcessFlag = false;//异常单是否在处理标识

var orderExpProcess= {
    init:function(){
        var me=this;
        $("#orderExpProcessBtn").unbind("click").bind("click",function() {
        	if(orderExpProcessFlag){//防止重复处理
        		return false;
        	}
        	orderExpProcessFlag = true;
        	$(this).val("处理中...");
    		me.orderExpProcess();
     	});
    },
    orderExpProcess:function(){
    	OrderExp.batchOrderExpProcess();
    }
}
orderExpProcess.init();
</script>