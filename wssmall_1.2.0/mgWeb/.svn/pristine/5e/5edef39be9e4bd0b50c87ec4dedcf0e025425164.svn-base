<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript">
loadScript("numero/js/numero_import_logs_ecs.js");
</script>

<div >
	<form  action="numero!numeroImportLogsECS.do"  id="searchNumeroImportLogsForm" method="post" >
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							批次号：
						</th>
						<td style="width:120px;">
							<input type="text" class="ipttxt" style="width: 150px" name="params.batch_id" value="${params.batch_id }" />
						</td>
		    	      
		  	      		<th style="width:120px;">导入开始时间：</th>
						<td style="width:120px;">
							<input type="text" name="params.start_date"
								value="${params.start_date }" id="start_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th style="width:120px;">导入结束时间：</th>
						<td style="width:120px;">
							<input type="text" name="params.end_date"
								value="${params.end_date }" id="end_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>处理状态：</th>
						<td style="width:120px;">
							<html:selectdict name="params.deal_flag" curr_val="${params.deal_flag }"
					             attr_code="GOODS_IMPORT_STATUS" style="width:155px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
						</td>
						<th>操作类型：</th>
						<td style="width:120px;">
							<select name="params.action_code">
								<option value="">--请选择--</option>
								<option value="A" <c:if test="${params.action_code=='A' }">selected</c:if>>批量导入</option>
								<option value="M" <c:if test="${params.action_code=='M' }">selected</c:if>>批量修改</option>
								<option value="B" <c:if test="${params.action_code=='B' }">selected</c:if>>批量发布</option>
								<option value="D" <c:if test="${params.action_code=='D' }">selected</c:if>>批量回收</option>
							</select>
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
			       	<form action="numero!importacion.do" id="batchForm" method="post" enctype="multipart/form-data">
			       	   	<input type="hidden" class="ipttxt" name="params.action_code" id="action_code" value="A"/>
			       	   	<input type="hidden" id="orgids_input" name="params.orgIds"> 
			       	   	<input type="hidden" id="fileFileName" name="fileFileName">
			       	   	<input type="file" class="ipttxt" name="file" id="uploadFile" /> 
					   	<input class="comBtn" type="button" name="searchBtn" id="searchBtn"
					   		value="导入号码" onclick="importacion();" style="margin-right:10px;" />
					   	<input class="comBtn" type="button" value="批量修改" onclick="batchModificar();" style="margin-right:10px;" />
						<input class="comBtn" type="button" value="批量发布" onclick="batchPublish();" style="margin-right:10px;" /> 
						<input class="comBtn" type="button" value="批量回收" onclick="batchRecycle();" style="margin-right:10px;" /> 
					   	<input class="comBtn" type="button" name="downloadBtn" id="downloadBtn"
							value="下载模板" onclick="download();" style="margin-right:10px;" />
							<input class="comBtn" type="button" value="搜索" onclick="checkDate();" style="margin-right:10px;" />
					</form>
				</div>	
			</li>
		</ul>
		<div style="clear:both"></div>
	</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchNumeroImportLogsForm" action="${listFormActionVal}" >
			<grid:header>
				
				<grid:cell sort="sn" width="80px">批次号</grid:cell>
				<grid:cell>手机号码</grid:cell>
				<grid:cell>网别</grid:cell>
				<grid:cell>预存款</grid:cell>
				<grid:cell>合约期</grid:cell>
				<grid:cell>最低消费额</grid:cell>
				<grid:cell>付费方式</grid:cell>
				<grid:cell>减免金额</grid:cell>
				<grid:cell>导入时间</grid:cell>
				<grid:cell>操作类型</grid:cell>
				<grid:cell>状态更新时间</grid:cell>
				<grid:cell>处理状态</grid:cell>
				<grid:cell>处理次数</grid:cell>
				<grid:cell>结果描述</grid:cell>
			</grid:header>
			<grid:body item="log">
				<grid:cell>${log.batch_id } </grid:cell>
				<grid:cell>${log.dn_no } </grid:cell>
				<grid:cell>${log.no_gen } </grid:cell>
				<grid:cell>${log.deposit } </grid:cell>
				<grid:cell>
					${log.period}
				</grid:cell>
				<grid:cell>
					${log.lowest }
				</grid:cell>
				<grid:cell>
				    ${log.charge_type }
				</grid:cell>
				<grid:cell>
				    ${log.fee_adjust }
				</grid:cell>
				<grid:cell>
				    ${fn:substring(log.created_date , 0 , 19)}
				</grid:cell>
				<grid:cell>
				    <c:if test="${log.action_code == 'A' }">批量导入</c:if>
				    <c:if test="${log.action_code == 'B' }">批量发布</c:if>
				    <c:if test="${log.action_code == 'M' }">批量修改</c:if>
				    <c:if test="${log.action_code == 'D' }">批量回收</c:if>
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
				    ${log.deal_desc }
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="goodsImportDiv"></div>
<!-- 号码发布 -->
<div id="numbers_pub_dialog"></div>

