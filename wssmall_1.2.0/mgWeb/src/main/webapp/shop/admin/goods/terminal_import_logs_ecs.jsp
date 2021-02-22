<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript">
loadScript("goods/js/terminal_import_logs_ecs.js");
</script>

<div >
	<form  action="${listFormActionVal}"  id="searchTerminalImportLogsForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							批次号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" name="params.batch_id" value="${params.batch_id }" />
						</td>
		    	      
		  	      		<th>开始时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.start_date"
								value="${params.start_date }" id="start_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>结束时间：</th>
						<td style="width:180px;">
							<input type="text" name="params.end_date"
								value="${params.end_date }" id="end_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>处理状态：</th>
						<td style="width:180px;">
							<html:selectdict name="params.deal_flag" curr_val="${params.deal_flag }"
					             attr_code="GOODS_IMPORT_STATUS" style="width:155px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
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
			       	<form action="goods!importTerminals.do" id="terminalUploadForm" method="post"
						enctype="multipart/form-data">
							<span>手机分类：</span>
							<select name="params.cat_id" id="cat_id">
								<option value="">--请选择--</option>
								<option value="690001000">社会机</option>
								<option value="690002000">定制机</option>
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<span>手机导入：</span>
						   	<input type="file" class="ipttxt" id="terminalImportFile" name="file" /> 
						   	<a href="javascript:void(0);" id="importActBtn" style="margin-right:10px;" class="graybtn1">导入手机</a>
						   	<a href="javascript:void(0);" id="downloadBtn" style="margin-right:10px;" class="graybtn1">下载模板</a>
						   	<a href="javascript:void(0);" style="margin-right:10px;" onclick="checkDate();" class="graybtn1"><span>搜索</span></a>
					</form>
				</div>	
			</li>
		</ul>
		<div style="clear:both"></div>
	</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchTerminalImportLogsForm" action="${listFormActionVal}" >
			<grid:header>
				<grid:cell sort="sn" width="80px">批次号</grid:cell>
				<grid:cell>本批次导入数</grid:cell>
				<grid:cell>品牌</grid:cell>
				<grid:cell>手机终端品牌</grid:cell>
				<grid:cell>型号</grid:cell>
				<grid:cell>手机终端型号</grid:cell>
				<grid:cell>颜色</grid:cell>
				<grid:cell>手机终端颜色</grid:cell>
				<grid:cell>手机终端编码</grid:cell>
				<grid:cell>导入时间</grid:cell>
				<grid:cell>状态更新时间</grid:cell>
				<grid:cell>处理状态</grid:cell>
				<grid:cell>处理次数</grid:cell>
				<grid:cell>结果描述</grid:cell>
			</grid:header>
			<grid:body item="log">
				<grid:cell>${log.batch_id } </grid:cell>
				<grid:cell>${log.batch_amount } </grid:cell>
				<grid:cell>${log.brand_name } </grid:cell>
				<grid:cell>
					${log.brand_code}
				</grid:cell>
				<grid:cell>
					${log.model_name }
				</grid:cell>
				<grid:cell>
				    ${log.model_code }
				</grid:cell>
				<grid:cell>
				    ${log.color_name }
				</grid:cell>
				<grid:cell>
				    ${log.color_code }
				</grid:cell>
				<grid:cell>
				    ${log.sn }
				</grid:cell>
				<grid:cell>
				    ${fn:substring(log.created_date , 0 , 19)}
				</grid:cell>
				<grid:cell>
				    ${fn:substring(log.status_date , 0 , 19)}
				</grid:cell>
				<grid:cell>
				    <c:if test="${log.deal_flag == 0 }">未处理</c:if>
				    <c:if test="${log.deal_flag == 1 }">成功</c:if>
				    <c:if test="${log.deal_flag == 2}">失败</c:if>
				</grid:cell>
				<grid:cell>
				    ${log.deal_num }
				</grid:cell>
				<grid:cell>
				<span title="${log.deal_desc}">${fn:substring(log.deal_desc , 0 , 4)}...</span>
				    
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="terminalImportDiv"></div>

