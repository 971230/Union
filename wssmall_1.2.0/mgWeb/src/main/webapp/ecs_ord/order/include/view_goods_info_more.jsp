<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<% String order_is_his = (String)request.getAttribute("order_is_his");
   pageContext.setAttribute("order_is_his_page_flag", order_is_his);
   int seriaNO = 1;
%>
               	  	<!-- 多商品货品信息开始 -->
               	  <c:if test="${(orderTree.orderBusiRequest.order_type=='09'&&orderTree.orderExtBusiRequest.order_from=='10053')||orderTree.orderExtBusiRequest.order_from=='10061'}">
               	  	<div class="grid_n_cont_sub">
                    	<h3>货品（多个记录）：</h3>
                    	<div class="netWarp">
                        	<a href="#" class="icon_close">展开</a>
                    		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_g">
                     		<tbody>
                        	<tr>
                        	<th></th>
                            <th>商品名称</th>
                            <th>商品价格</th>
                            <th>货品类型</th>
                            <th>机型编码</th>
                            <th>品牌名称</th>
                            <th>型号名称</th>
                            <th>颜色</th>
                            <th>终端串号</th><!-- 判断是终端类型才有终端串号 -->
                            <th>操作状态</th>
                            <th>操作描述</th>
                         	</tr>
                        	<c:forEach var = "itemExtvl" items="${orderTree.orderItemExtvtlBusiRequests}" varStatus="i_gift">
                        	
                        	
	                         <tr>
	                             <td><%=seriaNO++ %></td>
	                             <td>${itemExtvl.goods_name}</td>
	                             <td>${itemExtvl.goods_price}</td>
	                             <td>${itemExtvl.goods_type_name}</td>
	                             <td>${itemExtvl.machine_type_code}</td>
	                             <td>${itemExtvl.resources_brand_name}</td>
	                             <td>${itemExtvl.resources_model_name}</td>
	                             <td>${itemExtvl.resources_color}</td>
	                             <td>${itemExtvl.resources_code}</td>
	                             <td>
	           		                <c:if test="${ itemExtvl.operation_status=='1'}">预占成功</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='0'}">待预占</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='5'}">预占失败</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='4'}">释放成功</c:if>
	           		                <c:if test="${ itemExtvl.operation_status=='7'}">释放失败</c:if>
	           		             </td>
	                             <td style="width:250px;">
	           		                <p>${itemExtvl.operation_desc}</p>
	           		             </td>
	                         </tr>
                        	
                        	</c:forEach>
                        	
                     	 	</tbody>
                   			</table>
                      	   </div>
                      	 </div>
                      	</c:if>
               	  	<!-- 多商品货品信息结束 -->