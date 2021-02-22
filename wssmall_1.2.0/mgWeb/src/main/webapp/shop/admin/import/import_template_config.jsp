<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<link rel="stylesheet" href="css/import.css" type="text/css" />
<script type="text/javascript" src="import/js/import_template_config.js"></script>
<div class="searchWarp clearfix">
	<div class="newQuick">
    	<ul class="clearfix">
    		<li class="curr"><a href="import!searchImportTemplates.do" class="template">模板管理</a></li>
        	<li><a href="import!importDownloadFile.do" class="file">文件上传</a></li>
        	<li><a href="import!importLogList.do" class="note">查看上传日志</a></li>
        	<li><a href="import!importAlreadyUploadData.do" class="upload">已上传数据中心</a></li>
        </ul>
    </div>
    <div class="newSearch" style="display:none;">
        <input type="text" name="textfield" id="textfield" class="newIpt" />
        <a href="#" class="searchBtn2" style="margin-left:5px;">搜索</a>
        <a href="#" class="seniorBtn">高级搜索</a>
    </div>
    <div class="clear"></div>
</div>
<div class="input">
	<form method="post" name="templateForm" action="${actionName }" id="templateForm"
		class="validate">
		
		<div style="display: block;">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active">
						基本信息
					</li>
					<li tabid="1">
						列信息
					</li>
				</ul>
			</div>
			<div class="tab-page">
				<div class='clear'></div>
				<div tabid="tab_0" class="tab-panel"
					style='border: 0px solid red; position : absolute; z-index: 8;'>
					<table class="form-table" style="width: 100%; float: left"
						id='base_table'>
						<tr style="display:none;">
							<th>
								<span class="red">*</span>模板类型：
							</th>
							<td>
								<html:selectdict name="template.template_type"
					             	attr_code="DC_IMPORT_TEMPLATE_TYPE" style="width:155px" 
					        		appen_options='<option value="">--请选择--</option>'></html:selectdict>
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>类型：
							</th>
							<td>
								 <html:selectdict id="type_id" name="template.type_id" curr_val="${template.type_id }"
					             	attr_code="DC_IMPORT_GOODS_TYPE" style="width:155px" 
					        		appen_options='<option value="">--请选择--</option>'></html:selectdict>
							</td>
						</tr>
						
						<tr>
							<th>
								<span class="red">*</span>子类型：
							</th>
							<td>
								<select id="sub_type_id" name="template.sub_type_id">
									<option value="">--请选择--</option>
								</select>
								<input type="hidden" id="hidden_sub_type" value="${template.sub_type_id }">
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>模板名称：
							</th>
							<td>
								<input type="text" name="template.template_name" id="template_name"
									style="width:200px" class="top_up_ipt" dataType="string" required="true"
									value="${template.template_name}" />
								<input type="hidden" name="template.template_id" id="template_id" value="${template.template_id }">
								&nbsp;&nbsp;
								<span class="help_icon" helpid="goods_price"></span>
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>JAVA规则类：
							</th>
							<td>
								<input type="text" name="template.rule_java" id="java_rule" required="true"
									style="width:200px" class="top_up_ipt" dataType="string" value="${template.rule_java}" />
								<span class="red">填写规则类BEAN</span>
							</td>
						</tr>

						<tr>
							<th>
								最大线程数：
							</th>
							<td>
								<input type="text" name="template.thread_num" id="thread_num"
									style="width:200px" class="top_up_ipt" dataType="string" value="${template.thread_num}" onkeydown="onlyNum(this)"/>
								<input type="hidden" id="max_thread_num" name="max_thread_num" value=${max_thread_num }>
								<span class="red">(&gt=10,&lt=${max_thread_num })</span>
							</td>
						</tr>
						<tr>
							<th>
								数据处理量：
							</th>
							<td>
								<input type="text" name="template.max_num" id="max_num"
									style="width:200px" class="top_up_ipt" dataType="string" value="${template.max_num}" onkeydown="onlyNum(this)"/>
								<span class="red">(&lt=1000)</span>
							</td>
						</tr>
						<tr>
							<th>
								模板填写规则：
							</th>
							<td>
								<div>
									<textarea id="rule_desc" name="template.rule_desc" class="ipttxt" style="width:500px;height:200px;"><c:out value="${template.rule_desc}" escapeXml="false"></c:out></textarea>
								</div>
							</td>
						</tr>
						
					</table>
					<table>
					
					</table>
					
					<div class="append_tab_div">
					</div>
					<div class="inputBox">
						
						<table> 
							<tr>
								<td rowspan="2" style="margin-right: 240px; text-align: center;">

									<a id="nextStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">下一步</a>
								</td>
							</tr>
						</table>
					</div>
					
				</div>
				<div tabid="tab_1" class="tab-panel"
					style="border: 0px solid red; position : absolute; z-index: 8;display:none;width:800px;">
					
					<table class="form-table" style="width: 100%; float: left"
						id='column_table'>
						<tr tridx="0">
							<div class="comBtnDiv" style="padding-left:40px;">
								<span class="red">*</span>文件名称：&nbsp;
						    	<input type="text" name="name" id="exp_file_name" required="true"
											style="width:200px" class="top_up_ipt" dataType="string" value="${column.name }" />
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:;" id="addColumn">添加列</a>
								<div style="clear:both"></div>
							</div>
						</tr>
						<c:forEach items="${column.cells}" var="cell" varStatus="status">
							<tr tridx="${status.count }">
								<th style="width:70px;">
									<span class="red">*</span>中文名称：
								</th>
								<td>
									<input type="text" name="cname" id="cname" required="true"
										style="width:150px" class="top_up_ipt" dataType="string" value="${cell.cname}" />
								</td>
								<th style="width:60px;">
									英文名称：
								</th>
								<td>
									<input type="text" name="ename" id="ename"
										style="width:150px" class="top_up_ipt" dataType="string" value="${cell.ename}" />
								</td>
								<td style="width:60px;">
									<a href="javascript:;"><img class="delete" src="${ctx}/shop/admin/images/transparent.gif"></a>
								</td>
							</tr>
						</c:forEach>
						
					</table>
					
					<table id='publish_table_0' style='display: none;'>
					<tr>
						<td rowspan="2" style="margin-right: 240px; text-align: center;">
							<a id="preStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">上一步</a>
							<a id="finishStep" class="greenbtnbig" href="javascript:void(0)">确定</a>
							<a id="backupBtn" class="greenbtnbig" href="javascript:void(0)" style="display:none;">返回</a>
						</td>
					</tr>
				</table>
				</div>
			</div>
	</form>
</div>

<c:if test="${is_view==true}">
	<script type="text/javascript">
		$(".red").hide();
		$("#backupBtn").show();
		$("#type_id").attr("disabled", "disabled");
		$("#sub_type_id").attr("disabled", "disabled");
		$("#rule_desc").attr("disabled", "disabled");
		$("#templateForm input").each(function(){
			$(this).attr("disabled", "disabled");
		})
		$("#finishStep").hide();
		$("#addColumn").hide();
		$("#column_table a").each(function(){
			$(this).hide();
		})
	</script>
</c:if>