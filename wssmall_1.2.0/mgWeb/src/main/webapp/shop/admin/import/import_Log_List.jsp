<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" href="css/import.css" type="text/css" />
<div class="searchWarp clearfix">
	<div class="newQuick">
    	<ul class="clearfix">
    		<li><a href="import!searchImportTemplates.do" class="template">模板管理</a></li>
        	<li><a href="import!importDownloadFile.do" class="file">文件导入</a></li>
        	<li class="curr"><a href="import!importLogList.do" class="note">导入日志</a></li>
        	<li><a href="import!importAlreadyUploadData.do" class="upload">数据中心</a></li>
        </ul>
    </div>
    <form  action="${actionName }" id="importLogForm" method="post">
	    <div class="newSearch">
	    	<input type="hidden" id="batch_id_input" value="${params.batch_id }" name="params.batch_id" />
	        <input type="text" value="${params.batch_id}" id="search_batch_id" class="newIpt" />
	        <a href="javascript:void(0);" class="searchBtn2" style="margin-left:5px;">搜索</a>
	    </div>
    </form>
    <div class="clear"></div>
    
</div>
<div class="grid" id="typeList">
	<form validate="true" class="validate">
		<grid:grid from="webpage" formId="importLogForm">
			<grid:header>
				<grid:cell ><input style="display:none;" type="checkbox" id="toggleChk" /></grid:cell>
				<grid:cell>批次号</grid:cell> 
				<grid:cell >上传文件名</grid:cell> 
				<grid:cell >上传总数</grid:cell> 
				<grid:cell >处理成功</grid:cell> 
				<grid:cell >处理失败</grid:cell> 
				<grid:cell >未处理</grid:cell> 
				<grid:cell >创建时间</grid:cell> 
			</grid:header>
		
		  <grid:body item="log">
		  		<grid:cell><input type="checkbox" name="id" value="${log.log_id }" style="display:none;" /></grid:cell>
		        <grid:cell>${log.batch_id } </grid:cell>
		        <grid:cell>${log.file_name }</grid:cell>
		        <grid:cell>${log.total_num }</grid:cell>
		        <grid:cell>${log.succ_num}</grid:cell> 
		        <grid:cell>${log.fail_num }</grid:cell> 
		        <grid:cell>${log.wait_num }</grid:cell> 
		        <grid:cell><html:dateformat pattern="yyyy-MM-dd hh:mm:ss" d_time="${log.created_time }"></html:dateformat></grid:cell> 
		  </grid:body>  
		  
		</grid:grid>  
	</form>	
	<div style="clear:both;padding-top:5px;"></div>
</div>
<script>
var ImportLog  = {
	init : function(){
		var me = this;
		me.initClk();
	},
	initClk : function(){
		$(".searchBtn2").bind("click",function(){
			$("#importLogForm").submit();
		});
		$("#search_batch_id").focus(function(){
			if($.trim($(this).val())==""||$.trim($(this).val())=="请输入批次号"){
				$(this).val("");
				$("#batch_id_input").val("");
			}
		}).blur(function(){
			if($.trim($(this).val())==""){
				$(this).val("请输入批次号");
				$("#batch_id_input").val("");
			}else{
				$("#batch_id_input").val($.trim($(this).val()));
			}
		});
	}
};
$(function(){
	ImportLog.init();
});
</script>