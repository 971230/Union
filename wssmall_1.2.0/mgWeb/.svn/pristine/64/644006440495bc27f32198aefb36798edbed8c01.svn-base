<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/activity_import_logs_ecs.js"></script>
<div >
<form method="post" 
	action='activity!listActivityImportLogsECS.do' id="queryActivityLogsForm">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
			    <input type="hidden" name="isSearch" value="true" />
				<tr>
					<th>批次号：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="ipttxt" style="width: 120px;"
							name="batch_id"
							value="${batch_id}" class="searchipt" />
					</td>
					
					<th>开始时间：&nbsp;&nbsp;</th>
					<td>
					<input type="text" class="dateinput ipttxt" dataType="date"
							style="width: 150px" name="begin_time" readOnly="true"
							value="${begin_time}" class="searchipt" /></td>
					<th>结束时间：&nbsp;&nbsp;</th>
					<td>
						<input type="text" class="dateinput ipttxt" dataType="date"
							style="width: 150px" name="end_time" readOnly="true"
							value="${end_time}" class="searchipt" />
						
						</td>
					<th>活动状态：</th>
					<td>
						<select  class="ipttxt"  style="width:100px"  name = "deal_flag" value="${deal_flag }">
							<option  value="">--请选择--</option>
							<option  value="0" <c:if test="${deal_flag == 0 }">selected</c:if> >未处理</option>
							<option  value="1" <c:if test="${deal_flag == 1 }">selected</c:if> >成功</option>
							<option  value="2" <c:if test="${deal_flag == 2 }">selected</c:if> >失败</option>
						    
						</select>
				    </td>
					<td>
						<div style="float:right;"></div>
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
			       <form action="activity!importActivity.do" id="importForm" method="post"
						enctype="multipart/form-data">
						   <input type="file" class="ipttxt" id="uploadFile" name="file" /> 
							<a href="javascript:void(0);" id="checkBtn" style="margin-right:10px;" class="graybtn1">校验活动</a>
						   <a href="javascript:void(0);" id="importActBtn" style="margin-right:10px;" class="graybtn1">导入活动</a>
						   <a href="javascript:void(0);" id="downloadBtn" style="margin-right:10px;" class="graybtn1">下载模板</a>
							<a href="javascript:void(0);" id="searchFormSubmit" style="margin-right:10px;" class="graybtn1">搜索</a>
					</form>
				</div>	
			</li>
		</ul>
		<div style="clear:both"></div>
</div>
	
<div class="grid">
<form method="POST" >
<grid:grid  from="webpage" ajax="yes" formId ="queryActivityLogsForm">

	<grid:header>
	<grid:cell  width="70">批次号</grid:cell>
	<grid:cell  >活动名称 </grid:cell>
	<grid:cell  >活动类型</grid:cell>
	<grid:cell  >金额</grid:cell> 
	<grid:cell  >货品包名称</grid:cell> 
	<grid:cell  >套餐分类</grid:cell>
	<grid:cell  >最低套餐档次</grid:cell> 
	<grid:cell  >最高套餐档次</grid:cell> 
	<grid:cell  >活动地市</grid:cell>
	<grid:cell  >活动商城 </grid:cell> 
	<grid:cell   >活动有效期</grid:cell> 
	<grid:cell  >处理状态</grid:cell>
	<grid:cell   >状态更新时间</grid:cell> 
	<grid:cell   >处理次数</grid:cell> 
	<grid:cell   >处理结果</grid:cell> 
	</grid:header>

  <grid:body item="act">
  		<grid:cell>${act.batch_id}</grid:cell> 
  		<grid:cell>${act.name}</grid:cell> 
  		<grid:cell>${act.pmt_type }</grid:cell> 
  		<grid:cell> ${act.pmt_price} </grid:cell>
  		<grid:cell>${act.relation_name}</grid:cell>
  		<grid:cell>${act.package_class}</grid:cell>
  		<grid:cell>${act.min_price }</grid:cell>
  		<grid:cell>${act.max_price }</grid:cell> 
  		<grid:cell>${act.region}</grid:cell>  
  		<grid:cell> ${act.org_id_str} </grid:cell> 		
        <grid:cell> ${act.available_period} </grid:cell>  
        <grid:cell>
			<c:if test="${act.deal_flag==0}">未处理</c:if>
	  		<c:if test="${act.deal_flag==1}">成功</c:if>
	  		<c:if test="${act.deal_flag==2}">失败</c:if>
		</grid:cell>
		<grid:cell>${fn:substring(act.status_date , 0 , 19)}</grid:cell>
		<grid:cell> ${act.deal_num} </grid:cell>
		<grid:cell> ${act.deal_desc} </grid:cell>
        
  </grid:body>
  
</grid:grid>  
</form>	
</div>
<div id="activityTemplateDownload"></div>
<div style="clear:both;padding-top:5px;"></div>
</div>
</div>
