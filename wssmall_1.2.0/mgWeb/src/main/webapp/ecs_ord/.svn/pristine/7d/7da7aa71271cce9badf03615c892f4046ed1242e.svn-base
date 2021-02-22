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
	<form method="post" id="dealSearchform" action='<%=request.getContextPath()%>/shop/admin/WorkCustom!getWorkCustomDealTable.do?' style="display: none;">
		<div class="searchformDiv">
			<input type="hidden" class="ipttxt" id="dealer.ext_1" name="dealer.ext_1"  value="${dealer.ext_1}"/>
			<input type="hidden" class="ipttxt" id="dealer.cfg_id" name="dealer.cfg_id"  value="${dealer.cfg_id}"/>
			<input type="hidden" class="ipttxt" id="dealer.deal_level" name="dealer.deal_level"  value="${dealer.deal_level}"/>
			<input type="hidden" class="ipttxt" id="dealer.region_id" name="dealer.region_id"  value="${dealer.region_id}"/>
			<input type="hidden" class="ipttxt" id="dealer.county_id" name="dealer.county_id"  value="${dealer.county_id}"/>
			<input type="hidden" class="ipttxt" id="dealer.node_id" name="dealer.node_id"  value="${dealer.node_id}"/>
			<input type="hidden" class="ipttxt" id="dealer.d_version_id" name="dealer.d_version_id"  value="${dealer.d_version_id}"/>
		</div>
	</form>
	
	<div class="grid" >
		<form method="POST"  id="dealInfoForm" >
			<grid:grid from="webpage"  formId="dealSearchform" ajax="yes">
				<grid:header>
					<grid:cell width="11%">配置编号</grid:cell>
					<grid:cell width="11%">版本号</grid:cell>
					<grid:cell width="11%">处理单位级别</grid:cell> 
					<grid:cell width="11%">地市</grid:cell> 
					<grid:cell width="11%">县分</grid:cell>
					<grid:cell width="11%">环节名称</grid:cell>
					<grid:cell width="11%">处理单位类型</grid:cell>
					<grid:cell width="11%">处理单位</grid:cell>
					<grid:cell width="11%">操作</grid:cell>
				</grid:header>
				
				<grid:body item="obj">
					<grid:cell>${obj.deal_id}</grid:cell>
					<grid:cell>${obj.d_version_id}</grid:cell>
					<grid:cell>
						<c:choose>
							<c:when test="${obj.deal_level == 'region'}">
								地市
							</c:when>
							<c:otherwise>
								县分
							</c:otherwise>
						</c:choose>
					</grid:cell>
					<grid:cell>${obj.region_name}</grid:cell>
					<grid:cell>${obj.county_name}</grid:cell>
					<grid:cell>${obj.node_name}</grid:cell>
					<grid:cell>
						<c:choose>
							<c:when test="${obj.dealer_type == 'org'}">
								组织
							</c:when>
							<c:when test="${obj.dealer_type == 'person'}">
								人员
							</c:when>
							<c:otherwise>
								团队
							</c:otherwise>
						</c:choose>
					</grid:cell>
					<grid:cell>${obj.dealer_name}</grid:cell>
					<grid:cell>
						<c:if test="${obj.state == 1}">
							<a class="dealer_edit_item" onclick="updateDealer('${obj.deal_id}');">
	                       		 修改
	      	            	</a>
	      	            	<span class="dealer_edit_item tdsper"></span>
							<a class="dealer_edit_item" onclick="deleteDealer('${obj.deal_id}');">
	                       		 删除
	      	            	</a>
						</c:if>
					</grid:cell>
				</grid:body>  
			</grid:grid>
		</form>
	</div>
</body>

<script type="text/javascript">
function updateDealer(deal_id){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.showDealerEditDiag("edit",deal_id);
	}else{
		alert("修改失败，修改处理单位的JS方法为空");
	}
}

function deleteDealer(deal_id){
	if(window.parent.WorkFlowTool){
		window.parent.WorkFlowTool.deleteDealer(deal_id);
	}else{
		alert("删除失败，删除处理人单位的JS方法为空");
	}
}
</script>

