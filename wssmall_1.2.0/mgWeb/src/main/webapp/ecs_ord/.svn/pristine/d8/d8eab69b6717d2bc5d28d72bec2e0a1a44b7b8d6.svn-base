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
	<div class="searchBx" style="display: none;">
		<form action="/shop/admin/orderIntentAction!intentQueryTable.do" method="post" id="intentHandleQuery" >
			<input type="hidden" class="ipttxt" id="params.order_id" name="params.order_id"  value="${params.order_id}"/>
			<input type="hidden" class="ipttxt" id="params.intent_order_id" name="params.intent_order_id"  value="${params.intent_order_id}"/>
			<input type="hidden" class="ipttxt" id="params.create_start" name="params.create_start"  value="${params.create_start}"/>
			<input type="hidden" class="ipttxt" id="params.create_end" name="params.create_end"  value="${params.create_end}"/>
			<input type="hidden" class="ipttxt" id="params.status" name="params.status"  value="${params.status}"/>
			<input type="hidden" class="ipttxt" id="params.order_city_code" name="params.order_city_code"  value="${params.order_city_code}"/>
			<input type="hidden" class="ipttxt" id="params.order_county_code" name="params.order_county_code"  value="${params.order_county_code}"/>
			<input type="hidden" class="ipttxt" id="params.phone_num" name="params.phone_num"  value="${params.phone_num}"/>
			<input type="hidden" class="ipttxt" id="params.ship_tel" name="params.ship_tel"  value="${params.ship_tel}"/>
			<input type="hidden" class="ipttxt" id="params.order_from" name="params.order_from"  value="${params.order_from}"/>
		</form>
	</div>
	
	<div>
		<form id="gridform" class="grid">
			<grid:grid from="webpage" formId="intentHandleQuery" action="/shop/admin/orderIntentAction!intentQueryTable.do">
				<grid:header>
					<grid:cell width="3%">地市</grid:cell>
					<grid:cell width="4%"> 营业县分 </grid:cell>
					<grid:cell width="11%">订单号</grid:cell>
					<%-- <grid:cell width="12%">意向单号</grid:cell> --%>
					<grid:cell width="8%">商品名称</grid:cell>
					<grid:cell width="5%">客户姓名</grid:cell>
					<grid:cell width="8%">客户号码</grid:cell>
					<grid:cell width="10%">客户地址</grid:cell>
					<grid:cell width="11%">创建时间</grid:cell>
					<grid:cell width="5%">备注</grid:cell>
					<grid:cell width="10%">状态 </grid:cell>
					<grid:cell width="5%">锁定信息</grid:cell>
					<grid:cell width="15%">处理</grid:cell>
				</grid:header>
				<grid:body item="intent">
					<grid:cell>${intent.order_city_code}</grid:cell>
					<grid:cell>${intent.order_county_code}</grid:cell>
					<grid:cell>${intent.order_id} </grid:cell>
					<%-- <grid:cell>${intent.intent_order_id} </grid:cell> --%>
					<grid:cell>${intent.goods_name}</grid:cell>
					<grid:cell>${intent.ship_name}</grid:cell>
					<grid:cell>${intent.ship_tel}</grid:cell>
					<grid:cell>${intent.ship_addr}</grid:cell>
					<grid:cell>${intent.create_time}</grid:cell>
					<grid:cell>
						<div class="icoFontlist" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;" title="${intent.remark}" >${intent.remark}</div>
					</grid:cell>
					<grid:cell>
						<div class="list_t">
                        	<ul>
                            	<li><span style="width:90px">订单：</span><div>${intent.status}</div></li>
                            	<li><span style="width:90px">工单：</span><div>${intent.work_status}</div></li>
                            </ul>
                        </div>
                    </grid:cell>
                    
					<grid:cell>${intent.lock_id}</grid:cell>
					<grid:cell> 
						<a onclick="showIntentInfo('${intent.order_id}','${intent.is_work_custom}');" name="intentDetails" class="dobtn" style="margin-left: 1px;">详情</a>
						<a onclick="addIntentRemark('${intent.order_id}');" name="addRemarks" class="dobtn" style="margin-left: 1px;">添加备注</a>
						<c:if test="${isSZT==1}">
							<a onclick="intentBack('${intent.order_id}');" value="${intent.order_id}" name="intentBack" class="dobtn" style="margin-left: 1px;">订单回收</a>
						</c:if>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</body>

<script type="text/javascript">
function showIntentInfo(order_id,is_work_custom){
	if(window.parent.showIntentInfo){
		window.parent.showIntentInfo(order_id,is_work_custom);
	}
}

function addIntentRemark(order_id){
	if(window.parent.addIntentRemark){
		window.parent.addIntentRemark(order_id);
	}
}

function intentBack(order_id){
	if(window.parent.intentBack){
		window.parent.intentBack(order_id);
	}
}
</script>