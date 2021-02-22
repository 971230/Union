<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import ="com.ztesoft.common.util.BeanUtils" %>
<%@page import="params.adminuser.req.AdminCurrUserReq"%>
<%@page import="params.adminuser.resp.AdminUserResp"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="params.adminuser.req.AdminUserReq"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>
<%@page import="services.AdminUserInf" %>
<%
AdminUserInf adminUserServ =  SpringContextHolder.getBean("adminUserServ");
AdminCurrUserReq adminCurrUserReq = new AdminCurrUserReq();
AdminUserResp adminUserResp = adminUserServ.getCurrentUser(adminCurrUserReq);
AdminUser user = new AdminUser();
if(adminUserResp != null){
	user = adminUserResp.getAdminUser();
}
AdminUserReq adminUserReq = new AdminUserReq();
adminUserReq.setUser_id(user.getUserid());
if(user ==null)
{
	AdminUserResp adminUserResp1 = adminUserServ.getAdminUserById(adminUserReq);
	if(adminUserResp1 != null){
		user = adminUserResp1.getAdminUser();
	}
}
String orderExpUrl = BeanUtils.urlAddToken("#ORDEREXP#/shop/admin/orderExp!list.do", user.getUsername());
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>异常信息按目录统计</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body style="min-width:800px;">

<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<div class="searchformDiv">
		<form action="" method="post" id="order_exp_count_f">
				<table id="params_tb" width="100%" border="0" cellspacing="0"
					cellpadding="0" class="tab_form">
					<tr>
						<th>生产模式：</th>
						<td>
						<input type="hidden" value="${catalog_id }" id="catalog_id" name="catalog_id"/>
						<input type="hidden" value="${start_time }" id="start_time" name="start_time"/>
						<input type="hidden" value="${end_time }" id="end_time" name="end_time"/>
						<select id="order_model" name = "order_model">
						<c:forEach items="${order_model_list }" var="ds">
	                		<option value="${ds.pkey }" ${ds.pkey==order_model?'selected':'' }>${ds.pname }</option>
	                	</c:forEach>
						</select>
						<a href="javascript:void(0);" id="count_query" class="dobtn" style="margin-left:5px;">查询</a>
						</td>
						<td></td>
					</tr>
				</table>
			</form>
			</div>
        <div class="grid">
        <form action="" id="orderexp_spec_list_fm" class="grid_w" method="post">
        	<grid:grid from="webpage" formId="order_exp_count_f">
				<grid:header>
					<grid:cell style="width:50%;">异常信息</grid:cell>
					<grid:cell style="width:30%;">数量</grid:cell>
					<grid:cell style="width:20%;">异常百分比</grid:cell>
				</grid:header>
				<grid:body item="expSpecCount">
			  	    <grid:cell><div  style="width:100%;word-break:keep-all;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                            			<xmp>${expSpecCount.match_content}</xmp>
									</div></grid:cell>		
			  	    <grid:cell><a name="showExpInst" text="异常单管理" target="iframe" href="<%=orderExpUrl %>&start_time=${start_time }&end_time=${end_time }&eiqInner.key_id=${expSpecCount.key_id}" app_menu_id="2015021501" iframeall=yes>${expSpecCount.catalog_count }</a></grid:cell>
			  	    <!--<grid:cell><a name="showExpInst" key_id="${expSpecCount.key_id}" catalog_id="${expSpecCount.catalog_id}">${expSpecCount.catalog_count }</a></grid:cell>-->
			  	    <grid:cell>${expSpecCount.percent }</grid:cell>
			  </grid:body>
	      	</grid:grid>
        </form>
     </div>
  </div>
</div>
<div id="countview" style="display:none"></div>
<script type="text/javascript">
$(function(){
	$("#count_query").bind("click",function(){
		$.Loading.show("正在加载所需内容，请稍侯...");
		$("#order_exp_count_f").attr("action",ctx+"/shop/admin/orderExp!specCatalogList.do").submit();
	});
	$("a[name=showExpInst]").bind("click",function(){
		var cut = $(this);
		var app_menu_id = cut.attr("app_menu_id");
		var name = cut.attr("name");
		var target = cut.attr("target");
		var botton_falg = true;
		if(top.$("#bottom_tab_ul").find("li").length > 8){
			var move_app_id = top.$("#bottom_tab_ul").find("li").eq(0).attr("app_menu_id");
			top.$("#bottom_tab_ul").find("li").eq(0).remove();
			top.$(".main_all_iframe[app_menu_id='"+move_app_id+"']").remove();
		}			
		top.$("#bottom_tab_ul").find("li").each(function(){
			if($(this).attr("app_menu_id") == app_menu_id){
				top.$("#bottom_tab_ul").find("li").removeClass("curr")
				$(this).attr("class","curr");
				botton_falg = false;
			}
		});
		if(botton_falg){
			top.$("#bottom_tab_ul").find("li").removeClass("curr");
			var bottom_li = "<li target='"+target+"' class='curr' app_menu_id='"+app_menu_id+"'><a href='javascript:void(0);'><span>订单处理</span></a><a href='javascript:void(0);' class='tabClose'>关闭</a></li>";
			top.$("#bottom_tab_ul").append(bottom_li);
		} 

		//window.location.href=url;
		//parent.Ztp.AUI.loadUrlInFrm(url);
		
		top.BackendUi.bottonTabInit();			
		top.Ztp.AUI.load(cut);
		return false;
		//window.parent.location.href=url;
	});
});
</script>
</body>