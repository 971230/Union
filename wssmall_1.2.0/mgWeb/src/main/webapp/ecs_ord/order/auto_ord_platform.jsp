<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import ="com.ztesoft.common.util.BeanUtils" %>
<%@page import="services.*"%>
<%@page import="params.adminuser.req.AdminCurrUserReq"%>
<%@page import="params.adminuser.resp.AdminUserResp"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="params.adminuser.req.AdminUserReq"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>
<%@page import="java.util.*" %>
<%@ include file="/commons/taglibs.jsp"%>
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
String orderCatalogUrl = BeanUtils.urlAddToken("#ORDEREXP#/shop/admin/esearchCatalog!specCatalogMain.do", user.getUsername());
%>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
<head>
    <title>ECharts</title>
     <script src="<%=request.getContextPath() %>/ecs_ord/js/esl.js"></script>
     <style type="text/css">
     	.tit-item{background:#f9f9fa; border-bottom:1px solid #e6e6e6; padding:10px; color:#666;}
     	.tips-con{text-align:left; color:#000; font-size:14px; padding:10px 10px;}
     	.red{color:#de2200;}
		.ml10{margin-left:10px;}
		.mlr5{margin-left:5px; margin-right:5px;}
		.text-center{text-align:center;}
		.linePicDiv,.pieDivMain{
			float:left;
			font-size:14px;
			color:#f00;;
		}
		#tooltipdiv {
			 position: absolute;
			 border: 1px solid #333;
			 background: #f7f5d1;
			 padding: 3px 3px 3px 3px;
			 color: #333;
			 display: none;
			}
			.divmatnrdesc{
			   width:420px;
			   overflow:hidden;
			   white-space:nowrap;
			   text-overflow:ellipsis;
			}
     </style>
</head>
<body>
	<div style="width: 100%;" >
	<form action="<%=request.getContextPath() %>/shop/admin/workPlatformActoin!showOrderWorkPlatform.do" method="post" id="qryFrm">
		<div class="searchBx">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <td>
						创建时间：<input type="text" name="params.start_time" value="${params.start_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    			   <input type="text" name="params.end_time" value="${params.end_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	    	      		<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	    	          	<input type="hidden" name="params.query_type" value="1"/>
	    	          	<input type="hidden" name="params.show_type" value="${params.show_type }"/>
	    	      </td>
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>		
	</form>
	
	<table width="100%" cellspacing="0" cellpadding="0" border="0" class="tab_form">
			<tbody id="tbody_A">
				<tr>			
					<td colspan="2">		
					    <!--线性图-->
						<div id="linePicDiv" class="linePicDiv" style="width: 75%;height: 300px;"></div>
					    <!--饼图-->
					    <div id="pieDivMain" class="pieDivMain" style="width: 25%;height: 300px;"></div>				    
					</td>					
				</tr>
				<tr>
					<td colspan="2" class="tit-item tips-con">
					<div style="margin-left: 4%">
					您好！<strong style="color: blue;">${params.username }</strong>， ${params.start_time } 至 ${params.end_time }，
					待处理正常订单数：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
										<em class="red mlr5"><span id="totalorders">正在加载...</span></em>
									</a>，
					锁定正常订单数：<c:if test="${params.show_type!='all' }">
							<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?platform_locked_status=2&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
										<em class="red mlr5"><span id="lockedorders">正在加载...</span></em>
									</a>
							</c:if>
							<c:if test="${params.show_type=='all' }">
							<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?platform_locked_status=1&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
										<em class="red mlr5"><span id="lockedorders">正在加载...</span></em>
									</a>
							</c:if>。
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="tit-item tips-con">
					<div style="margin-left: 4%">订单处理视图(处理中)>><br>
						<table width="100%">
							<tr>
								<td width="12%"><em style="color: #9900FF;">预处理 > </em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=B&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="delayorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="bfloworders">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="bflowavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="bflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></em></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">预拣货 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=C&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="cflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="cfloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="cflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="cflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">开户 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=D&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="dflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="dfloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="dflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="dflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							
							<tr>
								<td width="12%"><em style="color: #9900FF;">写卡 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=X&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="xflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="xfloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="xflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="xflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">业务办理 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=Y&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="yflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="yfloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="yflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="yflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							
							<tr>
								<td width="12%"><em style="color: #9900FF;">质检稽核 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=F&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="fflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="ffloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="fflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="fflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">发货 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=H&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="hflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="hfloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="hflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="hflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">回单 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=J&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="jflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="jfloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="jflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="jflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">归档 ></em></td>
								<td width="14%">待处理订单：<a  name="normal_order"  href="<%=request.getContextPath() %>/shop/admin/ordAuto!showOrderList.do?trace_id=L&start_time=${params.start_time }&end_time=${params.end_time }" text="处理正常单" target="noneMenu" class="shortCut"  app_menu_id="1"  iframeall=yes >
																<em style="color: blue;"><span id="lflowundoorders">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="lfloworders">正在加载...</span></em></td>
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="lflowavgtimes">正在加载...</span></em></td>
								<td width="14%">超时订单：<em style="color: red;"><span id="lflowtimeoutorders">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
						</table>
					</div>
					</td>
				</tr>
				
				<script type="text/javascript">
					
				</script>
				
				<tr>
					<td colspan="2" class="tit-item tips-con">
					<div style="margin-left: 4%">
					异常数(未归档)：<a name="abnormal_order" href="<%=orderExpUrl%>&start_time=${params.start_time }&end_time=${params.end_time }"   
											text="处理异常单"  target="noneMenu" class="shortCut"  app_menu_id="2015021501" iframeall=yes >
									<em style="color: blue;">
										<span id="totalexpinsts">正在加载...</span>
									</em>
									(<em style="color: blue;">
										<span id="totalexporders">正在加载...</span>
									</em>单)
								</a>，
					待处理异常数：<a name="abnormal_order"   href="<%=orderExpUrl%>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0"   
											text="处理异常单"  target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
									<em style="color: blue;">
										<span id="totalexpinsts_0">正在加载...</span>
									</em>
									(<em style="color: blue;">
										<span id="totalexporders_0">正在加载...</span>
									</em>单)
								</a>，
					已处理异常数：<a name="abnormal_order" href="<%=orderExpUrl%>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=1"   
											text="处理异常单"  target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
									<em style="color: blue;">
										<span id="totalexpinsts_1">正在加载...</span>
									</em>
									(<em style="color: blue;">
										<span id="totalexporders_1">正在加载...</span>
									</em>单)
								</a>，
					已处理平均时长：<em style="color: blue;">
								   		<span id="totalexpinsts_dealavgtimes">正在加载...</span>
								   </em>，
					超时异常数：<em style="color: red;">
									<span id="totalexpinsts_timeout">正在加载...</span>
							   </em>
							  (<em style="color: blue;">
									<span id="totalexporders_timeout">正在加载...</span>
								</em>单)。
					</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="tit-item tips-con">
					<div style="margin-left: 4%">异常订单视图>><br>
						<table width="100%">
							<tr>
								<td width="12%"><em style="color: #9900FF;">订单归集 > </em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=GJ" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;">
																	<span id="gj_0">正在加载...</span>
																</em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="gj_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="gj_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="gj_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">预处理 > </em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=B" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="b_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="b_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="b_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="b_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">预拣货 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=C" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="c_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="c_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="c_dealavgtimes">正在加载...</span>${expOrderPlatFormView.c_dealavgtimes }</em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="c_timeout">正在加载...</span>${expOrderPlatFormView.c_timeout }</em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">开户 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=D" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="d_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="d_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="d_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="d_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">写卡 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=X" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="x_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="x_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="x_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="x_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">业务办理 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=Y" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="y_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="y_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="y_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="y_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">质检稽核 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=F" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="f_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="f_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="f_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="f_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">发货 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=H" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="h_0">正在加载...</span>${expOrderPlatFormView.h_0 }</em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="h_1">正在加载...</span>${expOrderPlatFormView.h_1 }</em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="h_dealavgtimes">正在加载...</span>${expOrderPlatFormView.h_dealavgtimes }</em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="h_timeout">正在加载...</span>${expOrderPlatFormView.h_timeout }</em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">回单 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=J" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="j_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="j_1">正在加载...</span></em></td>	
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="j_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="j_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>
							<tr>
								<td width="12%"><em style="color: #9900FF;">归档 ></em></td>
								<td width="14%">待处理订单：<a  name="abnormal_order"  href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }&record_status=0&eiqInner.flow_id=L" text="处理异常单" target="noneMenu" class="shortCut"  app_menu_id="2015021501"  iframeall=yes >
																<em style="color: blue;"><span id="l_0">正在加载...</span></em>
															</a>
								</td>
								<td width="14%">已处理订单：<em style="color: blue;"><span id="l_1">正在加载...</span></em></td>		
								<td width="26%">已处理平均时长：<em style="color: blue;"><span id="l_dealavgtimes">正在加载...</span></em></td>														
								<td width="14%">超时订单：<em style="color: red;"><span id="l_timeout">正在加载...</span></em></td>
								<td width="16%" style="display: none"></td>
							</tr>					
						</table>
					</div>
					</td>
				</tr>
				
				<script type="text/javascript">
					
				</script>
				
				<tr>
					<td colspan="2" class="tit-item tips-con">
					<div style="width:100%;margin-left: 4%">异常信息统计>></div>
					<iframe id="catalogMFrame" name="catalogMFrame" src="<%=orderCatalogUrl %>&start_time=${params.start_time }&end_time=${params.end_time }" style="width:100%;min-height:800px;"></iframe>
						<iframe id="showOrderExp" name="showOrderExp" src="" style="display:none;"></iframe>
						</div>
<a name="showOrderExpInst" id="showOrderExpInst" style="display:none;" text="异常单管理" target="noneMenu" href="<%=orderExpUrl %>&start_time=${params.start_time }&end_time=${params.end_time }" app_menu_id="2015021501" iframeall=yes>aaaa</a>
					</td>
				</tr>
				<!-- 
				<tr>
					<td colspan="2" class="tit-item tips-con">
					<div style="margin-left: 4%">
				     	<em style="color: #de2200;">温馨提示：订单处理视图中的订单统计的数据为可见订单数；已处理平均时长为(已处理订单的总处理时长/已处理订单数)</em>		
					</div>
					</td>
				</tr>
				 -->
			</tbody>
		</table>
	</div>
	<form id ="formAdd" name ="formAdd" method="post"  enctype="multipart/form-data">
		<input type="hidden" name="params.start_time"  value="${params.start_time }" />
		<input type="hidden" name="params.end_time"  value="${params.end_time }" />
		<input type="hidden" id="pageNo" name="params.pageNo"  value="${params.pageNo }" />
		<input type="hidden" id="pageSize" name="params.pageSize"  value="10" />
	</form>
     <script type="text/javascript">
        // 路径配置  饼图
        require.config({
            paths:{ 
                'echarts' : '/ecs_ord/js/echarts',
                'echarts/chart/pie' : '/ecs_ord/js/echarts'
            }
        });
        
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('pieDivMain')); 
                
                option = {
                		backgroundColor:'#f9f9fa',
                	    title : {
                	        text: '',
                	        subtext: '',
                	        x:'left'
                	    },
                	    legend: {
                	        orient : 'vertical',
                	        x : 'center',
                	        data:['已处理','未处理','退单']
                	    },
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    toolbox: {
                	        show : false,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    series : [
                	        {
                	            name:'全部订单处理占比情况',
                	            type:'pie',
                	            radius : '45%',
                	            data:[
                	                {value:${pieView.dealorders}, name:'已处理'},
                	                {value:${pieView.delayorders}, name:'未处理'},
                	                {value:${pieView.refundorders}, name:'退单'}
                	            ]
                	        }
                	    ]
                	};
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
            }
        );
        
        //线性图
        // 路径配置
        require.config({
            paths:{ 
                'echarts' : 'js/echarts',
                'echarts/chart/line' : 'js/echarts'
            }
        });
        
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                var myChart = ec.init(document.getElementById('linePicDiv')); 
                
                option = {
                		backgroundColor:'#f9f9fa',
                	    title : {
       						text: '',
        					subtext: ''
    					},
    					tooltip : {
        					trigger: 'axis'
    					},
    					legend: {
                	        orient : 'vertical',
                	        x : 'center',
        					data:['您好！${params.username }，${params.start_time}-${params.end_time} ，全部受理订单量(在途和历史)']
    					},
    					toolbox: {
        					show : false,
        					feature : {
            					mark : {show: true},
            					dataView : {show: true, readOnly: false},
            					magicType : {show: true, type: ['line', 'bar']},
            					restore : {show: true},
            					saveAsImage : {show: true}
        					}
    					},
    					calculable : true,
    					xAxis : [
        					{
           						type : 'category',
            					boundaryGap : false,
            					data : []
        					}
    					],
    					yAxis : [
        					{
            					type : 'value',
            					splitArea : {show:true},
            					min:0,
            				//	max:20000,
            					scale:true
        					}
    					],
    					series : [
        					{
            					name:'订单量',
            					type:'line',
            					data:[]
        					}
    					]
                	};
        
                // 为echarts对象加载数据 
                option.xAxis[0].data = '${params.xaxis}'.split(",");
                option.series[0].data = '${params.series}'.split(",");
                myChart.setOption(option);
            }
        );
        
        
        
        $("a[name=abnormal_order]").bind("click",function(){				

		//	var url = "/shop/admin/orderExp!list.do";
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
				var bottom_li = "<li target='"+target+"' class='curr' app_menu_id='"+app_menu_id+"'><a href='javascript:void(0);'><span>异常单管理</span></a><a href='javascript:void(0);' class='tabClose'>关闭</a></li>";
				top.$("#bottom_tab_ul").append(bottom_li);
			}  
			//window.location.href=url;
			//top.Ztp.AUI.loadUrlInFrm(url);
			
			top.BackendUi.bottonTabInit();			
			top.Ztp.AUI.load($(this));
			return false;
		});
		
		$("a[name=normal_order]").bind("click",function(){			

			//var url = "/shop/admin/ordAuto!showOrderList.do";
			
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
			top.Ztp.AUI.load($(this));
			return false;
		});

		var start_time ="${params.start_time }";
		var end_time = "${params.end_time }";
		
		 $('div.divmatnrdesc').live('mouseover', function(e) {
			   _text=$(this).text(); 
			   _tooltip = "<div id='tooltipdiv'><font style='font-size:18px'> "+_text+"</font></div>";  
			      $("body").append(_tooltip);  
			      $("#tooltipdiv").show();
			      $("#tooltipdiv")
				   .css({
				   "top": (e.pageY+10) + "px",
				   "left":  (e.pageX +10) + "px"
				  }).show("fast");    
			 });
			 
			 $('div.divmatnrdesc').live('mouseout', function(e) {
			  $("#tooltipdiv").remove();
			 });
			 
			 
			 $('div.divmatnrdesc').live('mousemove', function(e) {
			  $("#tooltipdiv")
			  .css({
			   "top": (e.pageY+10 ) + "px",
			   "left":  (e.pageX+10)  + "px"
			  }).show();    
			 });
			 
			 // 查看更多
			 $("#viewAll").bind("click",function(){
				var pageNo = $("#pageNo").val();
				pageNo++;
				$("#pageNo").val(pageNo)
				var url = app_path + "shop/admin/workPlatformActoin!getTopExpKey.do?ajax=yes";
			    Cmp.ajaxSubmit('formAdd','',url,'',function(data){
			    	if (data.result =='0') {
			    		var return_data = data.return_data;
				    	var maxPageNo = data.maxPageNo;
				    	if (pageNo==maxPageNo) {
				    		$("#viewAll").hide();
				    	}
				    	if (null != return_data && return_data.length>0) {
				    		var table = $("#topExpKey_table");
				    		for (var i=0;i<return_data.length;i++) {
					    		//alert(data[i].match_content);
					    		var match_content=return_data[i].match_content;
					    		var counts = return_data[i].counts;
					    		var search_name = return_data[i].search_name;
					    		var key_id = return_data[i].key_id;
					    		var href = "<%=orderExpUrl%>" +"&start_time=" + start_time + "&end_time=" +end_time + "&key_id="+key_id;
					    		var tr_html = "";
					    		tr_html +="<tr>";
					    		tr_html +="<td width='50px'></td>"
					    		tr_html +="<td width='250px'><div>接口："+search_name+"</div></td>";
					    		tr_html +="<td><div>关键字："+match_content+"</div></td>";
					    		tr_html +="<td width='200px'>";
					    		tr_html +="<a name='abnormal_order' onclick='doClick(this)' href='"+href+"'text='处理异常单'  target='iframe' class='shortCut'  app_menu_id='2015021501'  iframeall='yes' >";
					    		tr_html +="<em style='color: blue;'>"+counts+"</em>";
					    		tr_html +="</a></td>";
					    		tr_html +="<td width='50px'></td>";
					    		tr_html +="</tr>";
					    		table.append(tr_html);
					    	}
				    	}
			    	}
			    	$.Loading.hide();
				},'json');
					
			 });
			 
			 function doClick(obj) {
//					var url = "/shop/admin/orderExp!list.do";
					var cut = $(obj);
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
						var bottom_li = "<li target='"+target+"' class='curr' app_menu_id='"+app_menu_id+"'><a href='javascript:void(0);'><span>异常单管理</span></a><a href='javascript:void(0);' class='tabClose'>关闭</a></li>";
						top.$("#bottom_tab_ul").append(bottom_li);
					}  
					//window.location.href=url;
					//top.Ztp.AUI.loadUrlInFrm(url);
					top.BackendUi.bottonTabInit();	
					top.Ztp.AUI.load(cut);
					cut.removeAttr("href")
					return false;
			 }
		
			 var show_type = '${params.show_type }';
			 $.ajax({
					type : "post",
					async : true,
					url : "/shop/admin/workPlatformActoin!orderStatistics.do?ajax=yes&params.query_type=1&params.show_type="+show_type+"&params.start_time="+start_time+"&params.end_time="+end_time,
					data : {},
					dataType : "json",
					success : function(data) {
						if(data != null) {
							$("#totalorders").text(data.totalorders);
							$("#lockedorders").text(data.lockedorders);
							$("#delayorders").text(data.delayorders);
							$("#bfloworders").text(data.bfloworders);
							$("#bflowavgtimes").text(data.bflowavgtimes);
							$("#bflowtimeoutorders").text(data.bflowtimeoutorders);
							$("#cflowundoorders").text(data.cflowundoorders);
							$("#cfloworders").text(data.cfloworders);
							$("#cflowavgtimes").text(data.cflowavgtimes);
							$("#cflowtimeoutorders").text(data.cflowtimeoutorders);
							$("#dflowundoorders").text(data.dflowundoorders);
							$("#dfloworders").text(data.dfloworders);
							$("#dflowavgtimes").text(data.dflowavgtimes);
							$("#dflowtimeoutorders").text(data.dflowtimeoutorders);
							$("#xflowundoorders").text(data.xflowundoorders);
							$("#xfloworders").text(data.xfloworders);
							$("#xflowavgtimes").text(data.xflowavgtimes);
							$("#xflowtimeoutorders").text(data.xflowtimeoutorders);
							$("#yflowundoorders").text(data.yflowundoorders);
							$("#yfloworders").text(data.yfloworders);
							$("#yflowavgtimes").text(data.yflowavgtimes);
							$("#yflowtimeoutorders").text(data.yflowtimeoutorders);
							$("#fflowundoorders").text(data.fflowundoorders);
							$("#ffloworders").text(data.ffloworders);
							$("#fflowavgtimes").text(data.fflowavgtimes);
							$("#fflowtimeoutorders").text(data.fflowtimeoutorders);
							$("#hflowundoorders").text(data.hflowundoorders);
							$("#hfloworders").text(data.hfloworders);
							$("#hflowavgtimes").text(data.hflowavgtimes);
							$("#hflowtimeoutorders").text(data.hflowtimeoutorders);
							$("#jflowundoorders").text(data.jflowundoorders);
							$("#jfloworders").text(data.jfloworders);
							$("#jflowavgtimes").text(data.jflowavgtimes);
							$("#jflowtimeoutorders").text(data.jflowtimeoutorders);
							$("#lflowundoorders").text(data.lflowundoorders);
							$("#lfloworders").text(data.lfloworders);
							$("#lflowavgtimes").text(data.lflowavgtimes);
							$("#lflowtimeoutorders").text(data.lflowtimeoutorders);
						}
					}
				});
			 
			 
			 $.ajax({
					type : "post",
					async : true,
					url : "/shop/admin/workPlatformActoin!expOrderStatistics.do?ajax=yes&params.query_type=1&params.show_type="+show_type+"&params.start_time="+start_time+"&params.end_time="+end_time,
					data : {},
					dataType : "json",
					success : function(data) {
						if(data != null) {
							$("#f_dealavgtimes").text(data.f_dealavgtimes);
							$("#h_timeout").text(data.h_timeout);
							$("#totalexporders_timeout").text(data.totalexporders_timeout);
							$("#h_0").text(data.h_0);
							$("#h_1").text(data.h_1);
							$("#gj_1").text(data.gj_1);
							$("#gj_0").text(data.gj_0);
							$("#totalexporders").text(data.totalexporders);
							$("#h_dealtimes").text(data.h_dealtimes);
							$("#b_dealavgtimes").text(data.b_dealavgtimes);
							$("#l_dealavgtimes").text(data.l_dealavgtimes);
							$("#d_dealavgtimes").text(data.d_dealavgtimes);
							$("#f_dealtimes").text(data.f_dealtimes);
							$("#c_dealtimes").text(data.c_dealtimes);
							$("#j_dealtimes").text(data.j_dealtimes);
							$("#d_dealtimes").text(data.d_dealtimes);
							$("#b_timeout").text(data.b_timeout);
							$("#j_0").text(data.j_0);
							$("#j_1").text(data.j_1);
							$("#l_dealtimes").text(data.l_dealtimes);
							$("#f_0").text(data.f_0);
							$("#totalexpinsts_0").text(data.totalexpinsts_0);
							$("#totalexpinsts_dealtimes").text(data.totalexpinsts_dealtimes);
							$("#totalexpinsts_1").text(data.totalexpinsts_1);
							$("#y_timeout").text(data.y_timeout);
							$("#d_1").text(data.d_1);
							$("#gj_timeout").text(data.gj_timeout);
							$("#totalexpinsts_dealavgtimes").text(data.totalexpinsts_dealavgtimes);
							$("#d_0").text(data.d_0);
							$("#f_1").text(data.f_1);
							$("#y_dealavgtimes").text(data.y_dealavgtimes);
							$("#c_1").text(data.c_1);
							$("#x_1").text(data.x_1);
							$("#c_0").text(data.c_0);
							$("#totalexpinsts").text(data.totalexpinsts);
							$("#x_0").text(data.x_0);
							$("#y_1").text(data.y_1);
							$("#c_dealavgtimes").text(data.c_dealavgtimes);
							$("#totalexpinsts_timeout").text(data.totalexpinsts_timeout);
							$("#b_0").text(data.b_0);
							$("#y_0").text(data.y_0);
							$("#b_1").text(data.b_1);
							$("#d_timeout").text(data.d_timeout);
							$("#x_timeout").text(data.x_timeout);
							$("#totalCount").text(data.totalCount);
							$("#l_0").text(data.l_0);
							$("#l_1").text(data.l_1);
							$("#l_timeout").text(data.l_timeout);
							$("#totalexporders_0").text(data.totalexporders_0);
							$("#j_dealavgtimes").text(data.j_dealavgtimes);
							$("#j_timeout").text(data.j_timeout);
							$("#y_dealtimes").text(data.y_dealtimes);
							$("#x_dealavgtimes").text(data.x_dealavgtimes);
							$("#c_timeout").text(data.c_timeout);
							$("#b_dealtimes").text(data.b_dealtimes);
							$("#gj_dealtimes").text(data.gj_dealtimes);
							$("#h_dealavgtimes").text(data.h_dealavgtimes);
							$("#gj_dealavgtimes").text(data.gj_dealavgtimes);
							$("#x_dealtimes").text(data.x_dealtimes);
							$("#totalexporders_1").text(data.totalexporders_1);
							$("#f_timeout").text(data.f_timeout);
						}
					}
				}); 
			 window.addEventListener('message', function(e) {
				 showExp(e.data);
				}, false);
			 function showExp(key_id){
				 var cut = $("#showOrderExpInst");
				 var href = cut.attr("href") + "&key_id="+key_id;
				 cut.attr("href",href);
				 doClick(cut);
			 }
    </script>
</body