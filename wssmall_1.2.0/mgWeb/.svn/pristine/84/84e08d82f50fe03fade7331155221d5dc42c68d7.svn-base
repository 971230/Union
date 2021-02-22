<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("activities/js/activity_syn_logs.js");
</script>

<div >
	<form action="activity!searchActivitySynLogs.do"  id="searchSynchLogsForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							批次号：
						</th>
						<td style="width:160px;">
							<input type="text" class="ipttxt" style="width: 150px" id="batch_id" name="params.batch_id" value="${params.batch_id }" />
						</td>
		    	      	<th>活动编码：</th>
						<td style="width:160px;">
							<input type="text" class="ipttxt" style="width: 150px" id="pmt_code" name="params.pmt_code" value="${params.pmt_code }" />
						</td>
						<th>活动名称：</th>
						<td style="width:160px;">
							<input type="text" class="ipttxt" style="width: 150px" id="pmt_name" name="params.pmt_name" value="${params.pmt_name }" />
						</td>
						<th>状态：</th>
						<td style="width:160px;">
							<select name="params.status" id="status" style="width:100px;">
								<option value="">--请选择--</option>
								<option value="WFS" <c:if test="${params.status=='WFS' }">selected</c:if>>未发送</ption>
								<option value="FSZ" <c:if test="${params.status=='FSZ' }">selected</c:if>>发送中</option>
								<option value="XYCG" <c:if test="${params.status=='XYCG' }">selected</c:if>>成功</option>
								<option value="XYSB" <c:if test="${params.status=='XYSB' }">selected</c:if>>失败</option>
							</select>
						</td>
						<td>
							<a href="javascript:void(0);" style="margin-right:10px;" onclick="checkDate();" class="graybtn1"><span>搜索</span></a>
							<a href="javascript:void(0);" style="margin-right:10px;" onclick="publishAgain();" class="graybtn1"><span>重发</span></a>
						</td>
						<tr>
		  	      		<th>同步开始时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.start_date"
								value="${params.start_date }" id="start_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>同步结束时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.end_date"
								value="${params.end_date }" id="end_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						</tr>
						<%--<th>状态：</th>
						<td style="width:180px;">
							<select name="params.status" id="status" style="width:100px;">
								<option value="">--请选择--</option>
								<option value="WFS" <c:if test="${params.status=='WFS' }">selected</c:if>>未发送</ption>
								<option value="FSZ" <c:if test="${params.status=='FSZ' }">selected</c:if>>发送中</option>
								<option value="XYCG" <c:if test="${params.status=='XYCG' }">selected</c:if>>成功</option>
								<option value="XYSB" <c:if test="${params.status=='XYSB' }">selected</c:if>>失败</option>
							</select>
						</td>
						--%>
						
				 	</tr>
	  	    	</tbody>
	  	    </table>
    	</div>
	</form>

	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchSynchLogsForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell width="160px;">批次号</grid:cell>
				<grid:cell width="160px;">活动编码</grid:cell>
				<grid:cell>活动名称</grid:cell>
				<grid:cell>动作</grid:cell>
				<grid:cell>同步时间</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell width="260px;">同步结果</grid:cell>
			</grid:header>
			<grid:body item="log">
				<grid:cell>${log.batch_id } </grid:cell>
				<grid:cell>${log.pmt_code } </grid:cell>
				<grid:cell>${log.name } </grid:cell>
				<grid:cell>
					<c:if test="${log.action_code == 'A' }">新增</c:if>
				    <c:if test="${log.action_code == 'M' }">修改</c:if>
				    <c:if test="${log.action_code == 'D' }">删除</c:if>
				</grid:cell>
				<grid:cell>${fn:substring(log.created_date , 0 , 19)}</grid:cell>
				<grid:cell>
					<div class="relaDiv">
					    <c:if test="${log.status == 'FSZ' }">发送中</c:if>
					    <c:if test="${log.status == 'WFS' }">未发送</c:if>
					    <c:if test="${log.status == 'XYSB'}"><span style='color:red'>失败</span></c:if>
					    <c:if test="${log.status == 'XYCG'}"><span style='color:green'>成功</span></c:if>
					    <div class="absoDiv">
						   <div class="absoDivHead">发布组织</div>
						   <div class="absoDivContent">
						      ${log.org_name_str}<c:if test="${empty log.org_name_str}">无发布组织</c:if>
						   </div>
						</div>
				    </div>
				</grid:cell>
				<grid:cell>
					${log.failure_desc }
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="goodsImportDiv"></div>

