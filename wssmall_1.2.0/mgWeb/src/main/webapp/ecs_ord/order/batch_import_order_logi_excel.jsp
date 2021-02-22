<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath() %>/publics/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/js/batch_import_order_logi_excel.js" ></script>
<div >
	<form  action=""  id="searchTerminalImportLogsForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							批次号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" name="orderBatchLogiImport.batch_id" value="${orderBatchLogiImport.batch_id }" />
						</td>
						
						<th>
							订单号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" name="orderBatchLogiImport.order_id" value="${orderBatchLogiImport.order_id }" />
						</td>
		    	      
		  	      		<th>开始时间：</th>
						<td style="width:180px;">
							<input type="text" name="orderBatchLogiImport.start_time"
								value="${orderBatchLogiImport.start_time}" id="start_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>结束时间：</th>
						<td style="width:180px;">
							<input type="text" name="orderBatchLogiImport.end_time"
								value="${orderBatchLogiImport.end_time }" id="end_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>处理状态：</th> 
						<td style="width:180px;">
						<select name="orderBatchLogiImport.state" style="width:155px">
						<option value="">--请选择</option>
						<option value="0" <c:if test="${orderBatchLogiImport.state eq '0'}">selected="selected"</c:if>>成功</option>
						<option value="1" <c:if test="${orderBatchLogiImport.state eq '1'}">selected="selected"</c:if>>失败</option>
						</td>
				 	</tr>
	  	    	</tbody>
	  	    </table>
    	</div>
	</form>
	<div class="grid comBtnDiv" >
		<ul>
			<li>
				<div>
			       	<form action="" id="terminalUploadForm" method="post"
						enctype="multipart/form-data">
						   	<input type="file" class="ipttxt" id="terminalImportFileLogi" name="file" /> 
						   	<a href="javascript:void(0);" id="importActBtnLogi" style="margin-right:10px;" class="graybtn1">导入物流信息</a>
						   	<a href="javascript:void(0);" id="downloadBtnLogi" style="margin-right:10px;" class="graybtn1">下载导入模板</a>
						   	<a href="javascript:void(0);" style="margin-right:10px;" onclick="checkDate();" class="graybtn1"><span>搜索</span></a>
					</form>
				</div>	
			</li>
		</ul>
		<div style="clear:both"></div>
	</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchTerminalImportLogsForm" >
			<grid:header>
				<grid:cell sort="sn" width="80px">批次号</grid:cell>
				<grid:cell width="80px">订单编号</grid:cell>
				<grid:cell width="45px">操作人员</grid:cell>
				<grid:cell width="45px">操作组织</grid:cell>
				<grid:cell width="60px">物流公司</grid:cell>
				<grid:cell width="60px">物流编号</grid:cell>
				<grid:cell width="80px">创建时间</grid:cell>
				<grid:cell width="40px">是否成功</grid:cell>
				<grid:cell width="150px">失败原因</grid:cell>
				<grid:cell width="150px">备注</grid:cell>
			</grid:header>
			<grid:body item="logi">
				<grid:cell>${logi.batch_id} </grid:cell>
				<grid:cell>${logi.order_id} </grid:cell>
				<grid:cell>${logi.op_id} </grid:cell>
				<grid:cell>${logi.org_id}</grid:cell>
				<grid:cell>${logi.logi_company}</grid:cell>
				<grid:cell>${logi.logi_no}</grid:cell>
				<grid:cell><fmt:formatDate value="${logi.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></grid:cell>
				<grid:cell ><c:if test="${logi.state  eq '0'}">成功</c:if><c:if test="${logi.state  eq '1'}">失败</c:if></grid:cell>
				<grid:cell>${logi.cause_failure }</grid:cell>
				<grid:cell>${logi.remark}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
	
	<form id="hidden_export_excel_form" action="${ctx}/servlet/batchAcceptExcelServlet?lx=mb&service=logi_info" method="POST"></form>
</div>
<div id="orderLogiUpdate"></div>

