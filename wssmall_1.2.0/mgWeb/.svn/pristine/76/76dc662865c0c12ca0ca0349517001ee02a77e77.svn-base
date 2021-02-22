<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<link rel="stylesheet" href="css/import.css" type="text/css" />
<script type="text/javascript" src="import/js/import_template_list.js"></script>
<div class="searchWarp clearfix">
	<div class="newQuick">
    	<ul class="clearfix">
    		<li class="curr"><a href="import!searchImportTemplates.do" class="template">模板管理</a></li>
        	<li><a href="import!importDownloadFile.do" class="file">文件导入</a></li>
        	<li><a href="import!importLogList.do" class="note">导入日志</a></li>
        	<li><a href="import!importAlreadyUploadData.do" class="upload">数据中心</a></li>
        </ul>
    </div>
    <form  action="${actionName }" id="searchTemplateForm" method="post">
	    <div class="newSearch">
	        <input type="text" name="params.template_name" id="template_name" class="newIpt" value="${params.template_name }"/>
	        <a href="#" class="searchBtn2" style="margin-left:5px;">搜索</a>
	        <a href="#" class="seniorBtn" style="display:none;">高级搜索</a>
	    </div>
    </form>
    <div class="clear"></div>
</div>


<div class="grid" id="typeList">
	<div class="comBtnDiv">
		<a href="import!templateConfig.do" style="margin-right:10px;" id="addTemplate" class="graybtn1" ><span>添加</span></a>
		<a href="javascript:;" style="margin-right:10px;" id="deleteTemplate" class="graybtn1" ><span>删除</span></a>
		<div style="clear:both"></div>
	</div>
	<form validate="true" class="validate">
		<grid:grid from="webpage" formId="searchTemplateForm">
		
			<grid:header>
				<grid:cell ><input type="checkbox" id="toggleChk" /></grid:cell>
				<grid:cell width="250px">&nbsp;模板名称</grid:cell> 
				<grid:cell >类型</grid:cell> 
				<grid:cell >子类型</grid:cell> 
				<grid:cell >JAVA规则类</grid:cell> 
				<grid:cell >线程数</grid:cell> 
				<grid:cell >数据处理量</grid:cell> 
				<grid:cell >状态</grid:cell> 
				<grid:cell  width="100px">操作</grid:cell> 
			</grid:header>
		
		  <grid:body item="template">
		  		<grid:cell><input type="checkbox" name="id" value="${template.template_id }" /></grid:cell>
		        <grid:cell>${template.template_name } </grid:cell>
		        <grid:cell>${template.type_name }</grid:cell>
		        <grid:cell>${template.sub_type_name }</grid:cell>
		        <grid:cell>
		        ${template.rule_java }
		        </grid:cell> 
		         
		        <grid:cell>        
		        ${template.thread_num }
		        </grid:cell> 
		        <grid:cell>        
		        ${template.max_num }
		        </grid:cell> 
		        <grid:cell>        
		        <c:if test="${template.status=='00A' }">有效</c:if>
		        <c:if test="${template.status=='00X' }">无效</c:if>
		        </grid:cell> 
		        <grid:cell> 
		        	<a href="import!editTemplate.do?template_id=${template.template_id }" >修改</a>
		        	<span class='tdsper'></span> 
		        	<a href="import!viewTemplate.do?template_id=${template.template_id }" >查看</a> 
		        </grid:cell>
		  </grid:body>  
		  
		</grid:grid>  
	</form>	
	<div style="clear:both;padding-top:5px;"></div>
</div>


