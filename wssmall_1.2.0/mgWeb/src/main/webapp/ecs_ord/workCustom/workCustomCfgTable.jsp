<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<%
String path= request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

AdminUser adminUser=ManagerUtils.getAdminUser();

String theme_source_from = "";

if(null != adminUser){
	theme_source_from =  adminUser.getTheme_source_from();
}
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/ztp-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/admin/api.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/publics/js/common/SelectTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/ckeditor/adapters/jquery.js"></script>

<link href="${context}/css/global.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/input.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/grid.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/validate.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/dialog.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/reset_n.css" rel="stylesheet" type="text/css" />
<link href="${context}/css/style_n.css" rel="stylesheet" type="text/css" />

<link href="${context}/css/form.css" rel="stylesheet" type="text/css" />

<link href="<%=request.getContextPath()%>/mgWeb/css/alert.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
var theme_source_from = '<%=theme_source_from%>';
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;
</script>

<body style="background: white;">
	<form method="post" id="cfgSearchform" action='<%=request.getContextPath()%>/shop/admin/WorkCustom!getWorkCustomCfgTable.do?' style="display: none;">
		<div class="searchformDiv">
			<input type="hidden" class="ipttxt" id="cfg.ext_1" name="cfg.ext_1"  value="${cfg.ext_1}"/>
			<input type="hidden" class="ipttxt" id="cfg.cfg_id" name="cfg.cfg_id"  value="${cfg.cfg_id}"/>
			<input type="hidden" class="ipttxt" id="cfg.cfg_name" name="cfg.cfg_name"  value="${cfg.cfg_name}"/>
			<input type="hidden" class="ipttxt" id="cfg.version_id" name="cfg.version_id"  value="${cfg.version_id}"/>
			<input type="hidden" class="ipttxt" id="cfg.cfg_level" name="cfg.cfg_level"  value="${cfg.cfg_level}"/>
			<input type="hidden" class="ipttxt" id="cfg.goods_type_id" name="cfg.goods_type_id"  value="${cfg.goods_type_id}"/>
			<input type="hidden" class="ipttxt" id="cfg.goods_cat_id" name="cfg.goods_cat_id"  value="${cfg.goods_cat_id}"/>
			<input type="hidden" class="ipttxt" id="cfg.goods_id" name="cfg.goods_id"  value="${cfg.goods_id}"/>
			<input type="hidden" class="ipttxt" id="cfg.order_from" name="cfg.order_from"  value="${cfg.order_from}"/>
		</div>
	</form>
	
	<div class="grid" >
		<form method="POST"  id="cfgInfoForm" >
			<grid:grid from="webpage"  formId="cfgSearchform" ajax="yes">
				<grid:header>
					<grid:cell width="5%">流程编号</grid:cell>
					<grid:cell width="5%">版本编号</grid:cell>
					<grid:cell width="8%">匹配方式</grid:cell>
					<grid:cell width="8%">流程编码</grid:cell>
					<grid:cell width="9%">流程名称</grid:cell> 
					<grid:cell width="9%">配置级别</grid:cell>
					<grid:cell width="9%">商品大类</grid:cell>
					<grid:cell width="9%">商品小类</grid:cell>
					<grid:cell width="9%">商品</grid:cell>
					<grid:cell width="6%">订单来源</grid:cell>
					<grid:cell width="5%">流程类型</grid:cell>
					<grid:cell width="5%">状态</grid:cell>
					<grid:cell width="12%">操作</grid:cell>
				</grid:header>
				
				<grid:body item="obj">
					<grid:cell>${obj.cfg_id}</grid:cell>
					<grid:cell>${obj.version_id}</grid:cell>
					<grid:cell>
						<c:choose>
							<c:when test="${obj.match_type == '1'}">
								按商品匹配
							</c:when>
							<c:otherwise>
								按流程编码匹配
							</c:otherwise>
						</c:choose>
					</grid:cell>
					<grid:cell>${obj.flow_code}</grid:cell>
					<grid:cell>${obj.cfg_name}</grid:cell>
					<grid:cell>
						<c:choose>
							<c:when test="${obj.cfg_level == 'id'}">
								商品
							</c:when>
							<c:when test="${obj.cfg_level == 'cat'}">
								商品小类
							</c:when>
							<c:when test="${obj.cfg_level == 'type'}">
								商品大类
							</c:when>
							<c:otherwise>
								${obj.cfg_level}
							</c:otherwise>
						</c:choose>
					</grid:cell>
					<grid:cell>${obj.goods_type_name}</grid:cell>
					<grid:cell>${obj.goods_cat_name}</grid:cell>
					<grid:cell>${obj.goods_name}</grid:cell>
					<grid:cell>
						<label name="order_from">${obj.order_from}</label>
					</grid:cell>
					<script>
						var order_from = '${obj.order_from}';
						
						if(window.parent.orderFrom){
							for(var i=0;i<window.parent.orderFrom.length;i++){
								if(order_from == window.parent.orderFrom[i].value){
									$("[name='order_from']").html(window.parent.orderFrom[i].value_desc);
									break;
								}
							}
						}
               		</script>
               		<grid:cell>
						<c:choose>
							<c:when test="${obj.cfg_type == 'order'}">
								正式订单
							</c:when>
							<c:when test="${obj.cfg_type == 'intent'}">
								意向单
							</c:when>
							<c:otherwise>
								退单流程
							</c:otherwise>
						</c:choose>
					</grid:cell>
               		<grid:cell>
						<c:choose>
							<c:when test="${obj.state == '0'}">
								禁用
							</c:when>
							<c:when test="${obj.state == '1'}">
								正常
							</c:when>
							<c:otherwise>
								历史
							</c:otherwise>
						</c:choose>
					</grid:cell>
					<grid:cell>
						<c:choose>
							<c:when test="${obj.state == 1 || obj.state == 0}">
								<a class="dealer_edit_item" onclick="editCfg('${obj.version_id}','${obj.state}');">
		                       		 修改
		      	            	</a>
		      	            	<span class="dealer_edit_item tdsper" ></span>
								<a class="dealer_edit_item" onclick="deleteCfg('${obj.cfg_id}');">
		                       		 删除
		      	            	</a>
		      	            	<span class="dealer_edit_item tdsper" ></span>
								<a onclick="dealerCfg('${obj.version_id}','3');">
		                       		 处理人配置
		      	            	</a>	
							</c:when>
							<c:otherwise>
								<a onclick="editCfg('${obj.version_id}','${obj.state}');">
		                       		 查看
		      	            	</a>
							</c:otherwise>
						</c:choose>
					</grid:cell>
				</grid:body>  
			</grid:grid>
		</form>
	</div>
</body>

<script type="text/javascript">

$(function(){
	if(typeof(window.parent.WorkFlowTool.permission)!="undfined" 
			&& null!=window.parent.WorkFlowTool.permission 
			&& "1" != window.parent.WorkFlowTool.permission.level){
		$(".dealer_edit_item").remove();
	}
});

function editCfg(version_id,state){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.showEditDiag(version_id,state);
	}else{
		alert("修改失败，修改流程的JS方法为空");
	}
}

function deleteCfg(cfgId){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.deleteCustomCfg(cfgId);
	}else{
		alert("删除失败，删除流程的JS方法为空");
	}
}

function dealerCfg(version_id,state){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.showEditDiag(version_id,state);
	}else{
		alert("修改失败，处理人配置的JS方法为空");
	}
}
</script>

