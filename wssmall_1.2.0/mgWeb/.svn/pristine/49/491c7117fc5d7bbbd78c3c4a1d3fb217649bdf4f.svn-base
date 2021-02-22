<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/orderReportList.js");
</script>

<div ><form action="orderReport!orderReportList.do" id="" method="post">
<div class="searchformDiv">
	<table class="form-table">
		<tbody><tr>
			<th>开始时间：</th>
			<td>
				<input size="15" type="text"  name="startDate" id="startDate"
								value='${startDate}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date"/>  
			</td>		
			<th>结束时间：</th>
			<td>		
				<input size="15" type="text"  name="endDate" id="endDate"
								value='${endDate}'
								readonly="readonly"
								maxlength="60" class="dateinput ipttxt" dataType="date" /> 
			</td>
			
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  <input class="comBtn" type="submit" name="searchBtn" id="" value="搜索" style="margin-right: 5px;">
			</td>
		</tr>
	</tbody></table>
	
</div>
</form>
</div>
<div class="grid">
	<form method="POST">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell sort="staff_no" width="250px">经销商工号</grid:cell>
				<grid:cell sort="realname" width="250px">经销商名称</grid:cell>
				<grid:cell sort="order_amount">订单总金额</grid:cell>
			</grid:header>
			<grid:body item="orderReport">
				<grid:cell>${orderReport.staff_no } </grid:cell>
				<grid:cell>${orderReport.realname } </grid:cell>
				<grid:cell><a href="javascript:void(0);" staff_no="${orderReport.staff_no}" class="showSupplierOrder">￥${orderReport.order_amount}</a></grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>
<!-- 报表详情div -->
<div id="reportDetail_dialog"></div>