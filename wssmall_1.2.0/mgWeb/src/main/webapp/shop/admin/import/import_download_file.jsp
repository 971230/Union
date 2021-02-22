<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>
<link rel="stylesheet" href="css/import.css" type="text/css" />
<script type="text/javascript" src="import/js/import_download_file.js"></script>
<div class="searchWarp clearfix">
	<div class="newQuick">
    	<ul class="clearfix">
    		<li><a href="import!searchImportTemplates.do" class="template">模板管理</a></li>
        	<li class="curr"><a href="import!importDownloadFile.do" class="file">文件导入</a></li>
        	<li><a href="import!importLogList.do" class="note">导入日志</a></li>
        	<li><a href="import!importAlreadyUploadData.do" class="upload">数据中心</a></li>
        </ul>
    </div>
    <div class="newSearch" style="display:none;">
        <input type="text" name="textfield" id="textfield" class="newIpt" />
        <a href="#" class="searchBtn2" style="margin-left:5px;">搜索</a>
        <a href="#" class="seniorBtn">高级搜索</a>
    </div>
    <div class="clear"></div>
</div>
<div class="gridWarp">
	<div class="uploadLeft">
    	<div class="uploadCont">
        	<h2>模板下载</h2>
         	<div class="stepDiv">
            	<div class="tit"><span class="stepbg">第一步</span>选择模板文件</div>
            	<div class="info gray">从右侧模板列表中选择要下载的模板文件。</div>
            	<div class="tit"><span class="stepbg">第二步</span>选择模板文件类型</div>
            	<div class="info">请选择模板文件类型：
                    <select name="template_type" id="template_type" style="width:120px;">
                    	<option value="">--请选择--</option>
                      	<option value="EXCEL">EXCEL</option>
                      	<option value="CSV">CSV</option>
                    </select>
                    <a href="#" class="blue_b">下载</a>
              	</div>
            	<div class="tit"><span class="stepbg">第三步</span>填写文件内容</div>
                <div class="info gray">打开模板文件，按照下面规则描述写入上传商品、货品的内容。</div>
                <div class="stepEx">
                </div>
            </div>
        	<h2>上传文件<span>支持(.CSV、.xls、.xlsx)格式</span></h2>
         	<div class="stepDiv">
         		<form action="import!importFile.do" id="importForm" method="post"
						enctype="multipart/form-data">
	                <div class="info">
	                	导入文件：
	                    <input type="file" name="file" id="import_file" value="文件地址" />
	              	</div>
              	</form>
                <div class="fileBtn">
                	<a href="#" class="orgbtn"><span>导入</span></a>
                </div>
              <div class="modelEx">
                	<div class="tit"><img src="images/orgico.png" width="16" height="16" />模板说明：</div>
               		<p>1、提供上传模板维护功能（标题、编码、上传说明、模板文件类型(txt、excel)、模板URL地址、查看模板数据、模板格式（字段名、中文名、占用列数【+】列可以设置列名、设置基础数据）es_swfupload_spec</p>
                    <p>2、上传数据按批次号 写入es_swfupload_inst/es_swfupload_inst_detail（模板id、批次号id、处理状态（待处理、处理成功、处理失败）、处理意见、处理时间、处理人、实例业务id、）【提供一个界面查询详情，只是展示此批次号待处理、已处理数量、文件数据【重新上传】——弹出开始、截止号段重新上传</p>
                    <p>3、上传的数据执行规则处理</p>
                    <p>4、查看上传日志：上传记录总数、待处理、处理成功、处理失败记录总数、上传文件名、上传时间、【导出失败记录】</p>
                    <p>已上传数据中心包括：上传记录总数、待处理、处理成功、处理失败记录总数、上传文件名、上传时间、【导出失败记录】</p>
                </div>
            </div>
        </div>
    </div>
	<div class="uploadRight" style="display:;">
        <div class="proInfoWarp">
        	<div class="tabBg">
                <div class="stat_graph">
                    <h3>
                        <div class="graph_tab">
                            <ul>
                                <li id="show_click_1" class="selected"><span class="word">可用模板</span><span class="bg"></span></li>
                                <div class="clear"></div>
                            </ul>
                        </div>
                    </h3>
                </div>
            </div>
        	<div class="uploadList">
            	<ul>
            		<c:forEach items="${templateList }" var="template" varStatus="status">
                		<li tpid="${template.template_id }" rule_desc="${template.rule_desc }" <c:if test="${status.count==1 }">class="curr"</c:if>>${template.template_name }</li>
                	</c:forEach>
                </ul>
            </div>
            <div class="uploadBtn"><a href="import!templateConfig.do" class="bluebtn">配置新的模板</a></div>
        </div>
    </div>
</div>
<div id="templateExportDiv"></div>