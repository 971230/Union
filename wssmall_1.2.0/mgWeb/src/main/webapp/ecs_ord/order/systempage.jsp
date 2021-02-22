<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ztesoft.common.util.BeanUtils"%>
<%@page import="services.*"%>
<%@page import="params.adminuser.req.AdminCurrUserReq"%>
<%@page import="params.adminuser.resp.AdminUserResp"%>
<%@page import="com.ztesoft.net.eop.resource.model.AdminUser"%>
<%@page import="params.adminuser.req.AdminUserReq"%>
<%@page
	import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>
<%@page import="java.util.*"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	AdminUserInf adminUserServ = SpringContextHolder
			.getBean("adminUserServ");
	AdminCurrUserReq adminCurrUserReq = new AdminCurrUserReq();
	AdminUserResp adminUserResp = adminUserServ
			.getCurrentUser(adminCurrUserReq);
	AdminUser user = new AdminUser();
	if (adminUserResp != null) {
		user = adminUserResp.getAdminUser();
	}
	AdminUserReq adminUserReq = new AdminUserReq();
	adminUserReq.setUser_id(user.getUserid());
	if (user == null) {
		AdminUserResp adminUserResp1 = adminUserServ
				.getAdminUserById(adminUserReq);
		if (adminUserResp1 != null) {
			user = adminUserResp1.getAdminUser();
		}
	}
	String orderExpUrl = BeanUtils.urlAddToken(
			"#ORDEREXP#/shop/admin/orderExp!list.do",
			user.getUsername());
	String orderCatalogUrl = BeanUtils.urlAddToken(
			"#ORDEREXP#/shop/admin/esearchCatalog!specCatalogMain.do",
			user.getUsername());
%>
<script src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/esl.js"></script>
<head>
<title>ECharts</title>
<script data-require-id="echarts/chart/bar" src="<%=request.getContextPath()%>/ecs_ord/js/echarts.js" async=""></script>
<script src="<%=request.getContextPath()%>/commons/js/jquery.js"></script>
<style type="text/css">
.tit-item {
	background: #f9f9fa;
	border-bottom: 1px solid #e6e6e6;
	padding: 10px;
	color: #666;
}

.tips-con {
	text-align: left;
	color: #000;
	font-size: 14px;
	padding: 10px 10px;
}

.red {
	color: #de2200;
}

.ml10 {
	margin-left: 10px;
}

.mlr5 {
	margin-left: 5px;
	margin-right: 5px;
}

.text-center {
	text-align: center;
}

.linePicDiv, .pieDivMain {
	float: left;
	font-size: 14px;
	color: #f00;;
}

#tooltipdiv {
	position: absolute;
	border: 1px solid #333;
	background: #f7f5d1;
	padding: 3px 3px 3px 3px;
	color: #333;
	display: none;
}

.divmatnrdesc {
	width: 420px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.ellipsis {center;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.padd {
	padding-top: 10px;
	padding-right: 10px;
	padding-bottom: 10px;
	padding-left: 10px;
}

<
script data-require-id ="echarts " src ="<%=request.getContextPath()%>
	/ecs_ord /js/echarts.js " async =""> </script>.tit-item {
	background: #f9f9fa;
	border-bottom: 1px solid #e6e6e6;
	padding: 10px;
	color: #666;
}

.tips-con {
	text-align: left;
	color: #000;
	font-size: 14px;
	padding: 10px 10px;
}

.red {
	color: #de2200;
}

.ml10 {
	margin-left: 10px;
}

.mlr5 {
	margin-left: 5px;
	margin-right: 5px;
}

.text-center {
	text-align: center;
}

.linePicDiv, .pieDivMain {
	float: left;
	font-size: 14px;
	color: #f00;;
}

#tooltipdiv {
	position: absolute;
	border: 1px solid #333;
	background: #f7f5d1;
	padding: 3px 3px 3px 3px;
	color: #333;
	display: none;
}

.divmatnrdesc {
	width: 420px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
</style>
<body>
	<div style="width: 100%;">
<div class="notice_content" style="display: none"></div>
		<table width="100%" cellspacing="0" cellpadding="0" border="0"
			class="tab_form">
			<tbody id="tbody_A">
				<tr>
					<td colspan="10" class="tit-item tips-con"
						style="font-weight: bold;">系统公告</td>
				</tr>
				<tr>
					<td colspan="2" class="tit-item tips-con">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="mytable" style="table-layout: fixed;">
							<tbody id="authBody">
								<tr align="center">
									<th style='text-align: center' width="10%">公告标题</th>
									<th style='text-align: center' width="20%">发布者</th>
									<th style='text-align: center' width="10%">公告内容</th>
									<th style='text-align: center' width="20%">发布时间</th>
									<th style='text-align: center' width="20%">生效时间</th>
									<th style='text-align: center' width="20%">失效时间</th>
								</tr>
								
								<tr name="noticeList" style="display: none">
										<td style='text-align: center' class="ellipsis" width="10%"
											title="" name="title"></td>
										<td style='text-align: center' width="20%" name="realname"></td>
										<td style='text-align: center' class="ellipsis" width="10%"
											title="" name="content"></td>
										<td style='text-align: center' width="20%" name="create_time"></td>
										<td style='text-align: center' width="20%" name="begin_time"></td>
										<td style='text-align: center' width="20%" name="end_time"></td>
									</tr>
								<c:forEach items="${noticeList}" var="notice">
									<script type="text/javascript">
										var authBody = $("#authBody");
										var $tr=authBody.find("[name='noticeList']").clone().eq(0);
										$tr.css("display", "");
										$("td[name='title']",$tr).html("${notice.title}");
										$("td[name='realname']",$tr).html("${notice.realname}");
										$(".notice_content").html("${notice.content}");
										$("td[name='content']",$tr).html($(".notice_content").text());
										$("td[name='create_time']",$tr).html("${notice.create_time}");
										$("td[name='begin_time']",$tr).html("${notice.begin_time}");
										$("td[name='end_time']",$tr).html("${notice.end_time}");
										authBody.append($tr);
									</script>
								</c:forEach>
								<tr>
									<td colspan='6' style="text-align: center;"><a
										href="/core/admin/noticeAction!list.do" title="" id="descMore"
										style="font-weight: bold; color: blue;">+更多</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="tit-item tips-con">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							style="table-layout: fixed;">
							<tbody id="authBody">
								<tr>
									<td colspan="10" class="tit-item tips-con"
										style="font-weight: bold;">我的待处理订单</td>
								</tr>
								<tr align="center">
									<th style='text-align: center; width: 15%'>订单编号</th>

									<th style='text-align: center; width: 8%'>订单来源</th>
									<th style='text-align: center; width: 13%'>生效时间</th>
									<th style='text-align: center; width: 10%'>商城号码</th>
									<th style='text-align: center; width: 7%'>入网姓名</th>
									<th style='text-align: center; width: 12%'>产品名称</th>
									<th style='text-align: center; width: 7%'>联系人</th>
									<th style='text-align: center; width: 10%'>联系电话</th>
									<th style='text-align: center; width: 6%'>订单状态</th>
									<th style='text-align: center; width: 10%'>一键处理</th>
								</tr>
								<c:forEach items="${pendingOrderList}" var="ord">
									<tr>
										<td style='text-align: center; width: 13%' class="ellipsis"
											title="${ord.order_id}">${ord.order_id}</td>

										<td style='text-align: center; width: 8%'>${ord.order_from}</td>
										<td style='text-align: center; width: 13%' class="ellipsis"
											title="${ord.create_time}">${ord.create_time}</td>
										<td style='text-align: center; width: 10%'>${ord.phone_num}</td>
										<td style='text-align: center; width: 8%'>${ord.phone_owner_name}</td>
										<td style='text-align: center; width: 12%' class="ellipsis"
											title="${ord.goodsname}">${ord.goodsname}</td>
										<td style='text-align: center; width: 8%'>${ord.ship_name}</td>
										<td style='text-align: center; width: 10%'>${ord.ship_mobile}</td>
										<td style='text-align: center; width: 6%'>${ord.status}</td>
										<td style='text-align: center; width: 10%'><a
											href="shop/admin/orderFlowAction!preDealOrd.do?order_id=${ord.order_id}"
											title="" id="descMore"
											style="font-weight: bold; bold; color: #ff8000">快速处理</a></td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan='10' style="text-align: center;"><a
										href="/shop/admin/ordAuto!showOrderList.do" title=""
										id="descMore" style="font-weight: bold; bold; color: blue;">+更多</a></td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan="2" class="tit-item tips-con">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="mytable">
							<tbody id="authBody">
								<tr align="center">
									<c:forEach items="${orderListCount}" var="orderCount">
										<td valign="top" class="padd">
											<div align="top" style="width:270px;height: 20px;">
											<table width="100%" cellspacing="0" style="border: 1px solid #f6f6f6;" cellpadding="0">
												<th style='text-align: center'>${orderCount.tache_name}（${orderCount.count}）</th>
											</table>
											</div>
										</td>
									</c:forEach>
								</tr>
								<tr onmouseover="this.style.background='transparent	'"
									onmouseout="this.style.background=''">
									
									<c:forEach items="${orderListCount}" var="orderCount">
										<td valign="top" class="padd">
											<div align="top" style="width:270px;height: 219px;">
											<table width="100%" cellspacing="0"
												style="border: 1px solid #f6f6f6;" cellpadding="0">
												<tr>
													<th style='text-align: center'>订单编号</th>
													<th style='text-align: center'>停留时间(天)</th>
												</tr>
												<c:forEach items="${orderList}" var="order">
													<c:if test="${orderCount.flow_trace_id == order.flow_trace_id}">
													<tr>
														<td style='text-align: center; width: 70%'>${order.order_id}</td>
														<td style='text-align: center; width: 30%'>${order.timeout}</td>
													</tr>
													</c:if>
												</c:forEach>
												<tr>
													<td colspan='2' style="text-align: center;"><a
														href="/shop/admin/ordAuto!showOrderList.do"
														style="font-weight: bold; bold; color: blue;">+更多</a></td>
												</tr>
											</table>
											</div>
										</td>
									</c:forEach>
									
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="tit-item tips-con">
						<div id="orderGet" style="float: left; width: 50%; height: 300px;"></div>
						<div id="orderStatus"
							style="float: right; width: 50%; height: 300px;"></div>
					</td>
				</tr>
				
				<!-- 新增 -->
				<tr>
					<td colspan="1" class="tit-item tips-con">
						<div id="accountNotOpen" style="float: left; width: 100%; height: 300px;"></div>
					</td>
				</tr>
				
				<tr>
					<td colspan="1" class="tit-item tips-con">
						<div id="accountAudit" style="float: left; width: 100%; height: 300px;"></div>
					</td>
				</tr>
				
				
				
		</table>
	</div>
	<script>
		//线性图
		// 路径配置
		require.config({
			paths : {
				'echarts' : 'js/echarts',
				'echarts/chart/bar' : 'js/echarts'
			}
		});
		// 使用
		require([ 'echarts', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
		], function(ec) {
			var myChartOrderGet = ec.init(document.getElementById('orderGet'));
			var myChartOrderStatus = ec.init(document
					.getElementById('orderStatus'));
			var statusXAxisData = [];statusXAxisData[0]="";
			var statusSeriesData = [];statusSeriesData[0]="";
			var getXAxisData = [];getXAxisData[0]="";
			var getSeriesData = [];getSeriesData[0]="";
			// 指定图表的配置项和数据
			var optionOrderGet = {
				title : {
					textAlign : 'left',
					text : '订单数量（条）',
				},
				legend : {
					orient : 'vertical',
					x : 'center',
					data : [ '订单领取分布' ]
				},
				tooltip : {},
				xAxis : {
					data : getXAxisData
				},
				yAxis : {},
				series : [ {
					name : '订单数量',
					type : 'bar',
					data : getSeriesData
				} ]
			};

			var optionOrderStatus = {
				title : {
					textAlign : 'left',
					text : '订单数量（条）'
				},
				legend : {
					orient : 'vertical',
					x : 'center',
					data : [ '订单状态分布' ]
				},
				tooltip : {},
				xAxis : {
					data : statusXAxisData
				},
				yAxis : {},
				series : [ {
					name : '订单数量',
					type : 'bar',
					data : statusSeriesData
				} ]
			};

			$.ajax({
				type : "post",
				async : true,
				url : ctx + "/shop/admin/ordAuto!showSysHomeList.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					if (data.result == 0) {
						var return_data = data.return_data;
						if(return_data.status_group) {
							statusXAxisData.length=0;
							statusSeriesData.length=0;
						}
						if(return_data.get_group){
							getXAxisData.length=0;
							getSeriesData.length=0;
						}
						//处理状态分布
						$.each(return_data.status_group, function(name,
								value) {
							statusXAxisData.push(value.status);
							statusSeriesData.push(value.status_count);
						});
						//处理领取分布
						$.each(return_data.get_group,
								function(name, value) {
									getXAxisData.push(value.realname);
									getSeriesData.push(value.count_order);
						});
					
						// 为echarts对象加载数据 
						myChartOrderStatus.setOption(optionOrderStatus);
						myChartOrderGet.setOption(optionOrderGet);

					} else {
						alert("请求数据出错");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

					alert(XMLHttpRequest.readyState);
					alert(textStatus);

				}
			});
		});
		
		
		
		
	</script>
	
	<!-- 新增图表  未开户分布，待审核未分布-->
	<script>
		//线性图
		// 路径配置
		require.config({
			paths : {
				'echarts' : 'js/echarts',
				'echarts/chart/bar' : 'js/echarts'
			}
		});
		// 使用
		require([ 'echarts', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
		], function(ec2) {
			var myChartOrderAcNotOpen = ec2.init(document.getElementById('accountNotOpen'));
			var myChartOrderAcAudit= ec2.init(document.getElementById('accountAudit'));
			var noOpXAxisData = [];noOpXAxisData[0]="";
			var noOpSeriesData = [];noOpSeriesData[0]="";
			var auditXAxisData = [];auditXAxisData[0]="";
			var auditSeriesData = [];auditSeriesData[0]="";
			// 指定图表的配置项和数据
			
			var optionOrderNoOp = {
				title : { textAlign : 'left', text : '订单数量（条）' },
				legend : { orient : 'vertical', x : 'center', data : [ '未开户分布' ] },
				tooltip : {},
				xAxis : { data : noOpXAxisData },
				yAxis : {},
				series : [ { name : '订单数量', type : 'bar', data : noOpSeriesData } ]
			};


			var optionOrderAudit = {
				title : { textAlign : 'left', text : '订单数量（条）', },
				legend : { orient : 'vertical', x : 'center', data : [ '未审核分布' ] },
				tooltip : {},
				xAxis : { data : auditXAxisData },
				yAxis : {},
				series : [ { name : '订单数量', type : 'bar', data : auditSeriesData } ] };


			$.ajax({
				type : "post",
				async : true,
				url : ctx + "/shop/admin/ordAuto!showSysHomeListWithAccountOpen.do?ajax=yes",
				data : {},
				dataType : "json",
				success : function(data) {
					if (data.result == 0) {
						var return_data2 = data.return_data2;
						if(return_data2.noop_group) {
							noOpXAxisData.length=0;
							noOpSeriesData.length=0;
						}
						if(return_data2.audit_group){
							auditXAxisData.length=0;
							auditSeriesData.length=0;
						}
						//未开户分布
						
						$.each(return_data2.noop_group, function(name, value) {
							noOpXAxisData.push(value.typename);
							noOpSeriesData.push(value.ordercount);
						});
						//领取未开户分布
						$.each(return_data2.audit_group, function(name, value) {
							 auditXAxisData.push(value.audittypename);
							 auditSeriesData.push(value.auditordercount);
						});
					
						// 为echarts对象加载数据 
						myChartOrderAcNotOpen.setOption(optionOrderNoOp);
						myChartOrderAcAudit.setOption(optionOrderAudit);
						

					} else {
						alert("请求数据出错");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

					alert(XMLHttpRequest.readyState);
					alert(textStatus);

				}
			});
		});
	</script>
	
</body>
