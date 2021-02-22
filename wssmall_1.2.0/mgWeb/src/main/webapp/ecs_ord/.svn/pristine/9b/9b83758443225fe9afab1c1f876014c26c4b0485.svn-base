<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>

<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderExtBusiRequest"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.ztesoft.net.mall.core.utils.ICacheUtil"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>

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

String is_work_custom = request.getParameter("is_work_custom");
String workflow_id = request.getParameter("workflow_id");
String instance_id = request.getParameter("instance_id");
String node_index = request.getParameter("node_index");
String order_id =  request.getParameter("order_id");
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

<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/Base.js" ></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/control/js/splitScreen.js" ></script>
<script src="<%=request.getContextPath() %>/ecs_ord/order/onlinecustomwork/js/btn_menu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/ecs_ord/order/onlinecustomwork/js/open_account.js"></script>

<script type="text/javascript">
var theme_source_from = '<%=theme_source_from%>';
var app_path="<%=path%>";
var basePath="<%=basePath%>";
var mainpage=false;
</script>

<style>
<!--
.noborder {
	border-style: none;
}
-->
.icoFontlist{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    color:#5f5f5f;  
    overflow: hidden;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
} 

.red_mark {
    color: red;
}

.color_gray {
    background-color: #CCC;
}

.icoFontlist:hover  
{  
    width: 100px;  
    font-size: 12px;  
    border: 0px solid #ddd;  
    overflow: scroll;  
    text-overflow: ellipsis;  
    white-space: nowrap;  
    cursor:pointer;   
}  

.second select {  
    width: 352px;  
    height: 106px;  
    margin: 0px;  
    outline: none;  
    border: 1px solid #999;  
    margin-top: 33px; 
    background-color: white; 
}  
.second select option{
	background-color: inherit; 
}
.op{
	background-color: transparent; 
    bacground:tansparent;
    -webkit-appearance: none;
}
.second input {  
    width: 350px;  
    top: 9px;  
    outline: none;  
    border: 0pt;  
    position: absolute;  
    line-height: 30px;  
    /* left: 8px; */  
    height: 30px;  
    border: 1px solid #999;  
}  
.second ul {  
    position: absolute;  
    top: 27px;  
    border: 1px solid #999;  
    left: 8px;  
    width: 125px;  
    line-height: 16px;  
}  
.ul li{  
    list-style: none;  
    width: 161px;  
    /* left: 15px; */  
    margin-left: -40px;  
    font-family: 微软雅黑;  
    padding-left: 4px;  
}  
.blue {   
    background:#1e91ff;   
}  
</style>

<body style="background: white;">

<div class="input">
	<form class="validate" method="post" id="workform" validate="true" >
		<div>
			<div>
				<span style="color: red;font-size: 16px;margin-left: 20px;">流转信息：</span>
				<span id="remark" style="font-size: 16px;"></span>
			</div>
		
			<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>缴费款</h2>
            	<div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
						<table cellspacing="0" cellpadding="0" border="0" width="100%">
							<tbody>
								<tr>                          	
									<th style="width: 138px;"><span>*</span>BSS/ESS缴费款：</th>
			                        <td>
			                        	<input type="text" id="busi_money" class="ipt_new" style="width:120px;"/>
			                        	<a id="update_busi_money_btn" class="blue_b" style="margin-right:5px;margin-left: 50px;cursor: pointer;" onclick="updateBusiMoney();"><span>保存缴费款</span></a>
			                        </td>
		                        </tr>
							</tbody>
						</table>
					</div>
				</div>
			</div> 
		
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>卡信息</h2>
                <div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
                    	<h3>开户对象：套卡<span id="custNum"><a id="phone_num"></a></span>
                    	  	<a id="refresh_read_card_btn" class="blue_b" onclick="refreshReadCard();" style="margin-left:5px;cursor: pointer;">刷新读卡器列表</a>
                    	  	<a id="get_iccid_btn" onclick="getIccid();" class="blue_b" style="margin-left:5px;cursor: pointer;">读卡</a>
                    	  	<a id="open_acct_btn" onclick="openAcct();" class="blue_b" style="margin-left:5px;cursor: pointer;">开户</a>
	                    </h3>
	                    
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          <tr>
                            <th><span>*</span>请选择写卡器：</th>
                            <td class="title_desc">
		                    <select id="cardList" class="ipt_new" style="width:120px;"></select>
		                    </td>
		                    <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>卡ICCID：</th>
                            <td id="ICCID"></td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                    </div>
                </div>
            </div>
			
			<input id="order_id" type="hidden" value="${order_id}" />
			<input id="operatorList" name="operatorList" type="hidden" value="${operatorList}" />
		</div>
	</form>
</div>


            
<div id="queryUserListDlg"></div>

</body>

<!-- 写卡ActiveX控件和相关的JS -->
<OBJECT id="Ocxtest" classid=clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93 height="0"><PARAM NAME="_Version" VALUE="65536"><PARAM NAME="_ExtentX" VALUE="2646"><PARAM NAME="_ExtentY" VALUE="1323"><PARAM NAME="_StockProps" VALUE="0"></OBJECT>
<script src="<%=request.getContextPath() %>/ecs_ord/js/RwCardExtract.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/writeCard.js"></script> 


<script>
var TempArr=[];//存储option 
var NumArr=[];//存储号码 
var is_work_custom = "<%=is_work_custom%>";
var workflow_id = "<%=workflow_id%>";
var instance_id = "<%=instance_id%>";
var node_index = "<%=node_index%>";
var order_id = "<%=order_id%>";

$(function() {
	initInfo(order_id);
});


</script>
