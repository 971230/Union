<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<script type="text/javascript">
loadScript("goods/js/zdb_import_logs_ecs.js");
</script>

<div >
	<form  id="searchZdbsImportLogsForm" action="goods!zdbImportLogsEcs.do" method="post">		
		<div class="searchformDiv">
	    	<table width="100%" cellspacing="0" cellpadding="0" border="0">
	    		<tbody>
		    	    <tr>
		    	    	<th>
							转兑包名称：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" name="zdb_name" value="${zdb_name }" />
						</td>
		    	    
		    	    	<th>
							批次号：
						</th>
						<td style="width:180px;">
							<input type="text" class="ipttxt" style="width: 150px" name="batch_id" value="${batch_id }" />
						</td>
		    	      
		  	      		<th>开始时间：</th>
						<td style="width:180px;">
							<input type="text" name="start_date"
								value="${start_date }" id="start_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						<th>结束时间：</th>
						<td style="width:180px;">
							<input type="text" name="end_date"
								value="${end_date }" id="end_date" readonly="readonly"
								class="dateinput ipttxt" dataType="date" />
						</td>
						
				 	</tr>
				 	<tr>
				 	<th>处理状态：</th>
						<td style="width:180px;">
							<html:selectdict name="deal_flag" curr_val="${deal_flag }"
					             attr_code="GOODS_IMPORT_STATUS" style="width:155px" 
					        appen_options='<option value="">--请选择--</option>'></html:selectdict>
						</td>
						<th>操作类型：</th>
						<td style="width:180px;">
					       <html:selectdict name="deal_type" curr_val="${deal_type }"
					             attr_code="GOODS_DEAL_TYPE" style="width:155px" 
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
			       <form id="zdbsUploadForm" method="post" enctype="multipart/form-data">
						   <input type="file" class="ipttxt" id="zdbsUploadFile" name="file" /> 
						   <a href="javascript:void(0);" id="importActBtn" style="margin-right:10px;" class="graybtn1">导入转兑包</a>
						   <a href="javascript:void(0);" id="editActBtn" style="margin-right:10px;" class="graybtn1">批量修改</a>
						   <a href="javascript:void(0);" id="deleteActBtn" style="margin-right:10px;" class="graybtn1">批量回收</a>
						   <a href="javascript:void(0);" id="downloadBtn" style="margin-right:10px;" class="graybtn1">下载模板</a>
						   <a href="javascript:void(0);" style="margin-right:10px;" onclick="checkDate();" class="graybtn1"><span>搜索</span></a>
						   <a href="javascript:void(0);" id="exportBtn" style="margin-right:10px;" class="graybtn1"><span>导出</span></a>
					</form>
				</div>	
			</li>
		</ul>
		<div style="clear:both"></div>
	</div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell sort="sn" width="80px">批次号</grid:cell>
				<grid:cell>本次导入数</grid:cell>
				<grid:cell>名称</grid:cell>
				<grid:cell>转兑包类型</grid:cell>
				<grid:cell>BSS编码</grid:cell>
				<grid:cell>转兑包网别</grid:cell>
				<grid:cell>调价额度（元）</grid:cell>
				<grid:cell>更新时间</grid:cell>
				<grid:cell>操作类型</grid:cell>
				<grid:cell>处理状态</grid:cell>
				<grid:cell>处理次数</grid:cell>
				<grid:cell>结果</grid:cell>
			</grid:header>
			<grid:body item="log">
				<grid:cell>${log.batch_id } </grid:cell>

				<grid:cell>${log.batch_count } </grid:cell>
				<grid:cell>${log.zdb_name } </grid:cell>
				<grid:cell>${log.zdb_type }</grid:cell>
				<grid:cell>${log.bss_code }</grid:cell>
				<grid:cell>${log.zdb_gen }</grid:cell>
				<grid:cell>${log.zdb_price }</grid:cell>
				<grid:cell>
					${fn:substring(log.status_date , 0 , 19)}
				</grid:cell>
				<grid:cell>
				    <c:if test="${log.deal_type == 'PLDR' }">批量导入</c:if>
				    <c:if test="${log.deal_type == 'PLXG' }">批量修改</c:if>
				    <c:if test="${log.deal_type == 'PLHS' }">批量回收</c:if>
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
					<span title="${log.deal_desc}">${fn:substring(log.deal_desc , 0 , 15)}...</span>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<div id="zdbsImportDiv"></div>

