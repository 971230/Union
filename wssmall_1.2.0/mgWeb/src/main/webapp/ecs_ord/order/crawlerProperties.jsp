<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<script type="text/javascript" src="js/jquery.checktree.js"></script>
<script type="text/javascript" src="../../mgWeb/menu.do"></script>
<script type="text/javascript" src="js/Auth.js"></script>

<div class="grid">
	
	<div class="searchformDiv">
	<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" onclick="saveProperties()"><span>保存</span></a>
</div>
<form method="POST" action="ordAuto!saveCrawlerProperties.do" id="theForm">
	<table>
	<h3>代理配置</h3>
		<tr>
		    <th><label class="text"><span class="red"></span>是否开启代理</label></th>
			<td>
				<select  name="crawlerProperties.IS_PROXY"  id ="IS_PROXY" style="margin-top:6px;" required="true">
					<option value="Y" <c:if test="${crawlerProperties.IS_PROXY=='Y'}"> selected</c:if>>开启</option>
					<option value="Y" <c:if test="${crawlerProperties.IS_PROXY=='N'}"> selected</c:if>>不开启</option>
				</select>
				
			</td>
			
		
		    <th><label class="text"><span class="red"></span>代理地址</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.PROXY_HOST" id="PROXY_HOST" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.PROXY_HOST }" required="true"/>
			</td>
			
			 <th><label class="text"><span class="red"></span>代理端口</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.PROXY_PORT" id="PROXY_PORT" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.PROXY_PORT }"  required="true"/>
			</td>
			
		</tr>
	</table>
	<h3>订单审核条件</h3>
	<table>
		<tr>
		    <th><label class="text"><span class="red"></span>是否开启订单号匹配</label></th>
			<td>
			<select  name="crawlerProperties.IS_OUT_ID_CHECK"  id ="IS_OUT_ID_CHECK" style="margin-top:6px;"  required="true" >
					<option value="Y" <c:if test="${crawlerProperties.IS_OUT_ID_CHECK=='Y'}"> selected</c:if>>开启</option>
					<option value="Y" <c:if test="${crawlerProperties.IS_OUT_ID_CHECK=='N'}"> selected</c:if>>不开启</option>
				</select>
			</td>
			
		    <th><label class="text"><span class="red"></span>卡类型</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_businessType" id="order_review_businessType" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_businessType }" required="true" />
			</td>
			
			 <th><label class="text"><span class="red"></span>客户姓名</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_custName" id="order_review_custName" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_custName }" required="true"/>
			</td>
		
			<th><label class="text"><span class="red"></span>证件号码</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_psptNo" id="order_review_psptNo" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_psptNo }"  required="true"/>
			</td>
			
		</tr>
		<tr>
		 	<th><label class="text"><span class="red"></span>联系电话</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_mobilePhone" id="order_review_mobilePhone" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_mobilePhone }"  required="true"/>
			</td>
			 <th><label class="text"><span class="red"></span>订单类型</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_orderType" id="order_review_orderType" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_orderType }"  required="true"/>
			</td>
			
			<th><label class="text"><span class="red"></span>查询条数</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_pageSize" id="order_review_pageSize" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_pageSize }"  required="true"/>
			</td>
			
			<th><label class="text"><span class="red"></span>处理条数</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_count" id="order_review_count" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_pageSize }"  required="true"/>
			</td>
			
			
		</tr>
		<tr>
		 	<th><label class="text"><span class="red"></span>用户类型</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_review_userTag" id="order_review_userTag" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_review_userTag }"  required="true"/>
			</td>
			
		</tr>
	</table>
	<h3>订单分配条件</h3>
	<table>
		<tr><!-- 分配条件 -->
		    <th><label class="text"><span class="red"></span>订单类型</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_ctgrCode"  id ="order_allocation_ctgrCode" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_ctgrCode }" required="true" />
			</td>
			
		
		    <th><label class="text"><span class="red"></span>订购号码</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_preNum" id="order_allocation_preNum" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_preNum }" required="true" />
			</td>
			
			 <th><label class="text"><span class="red"></span>客户姓名</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_custName" id="order_review_custName" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_custName }" required="true" />
			</td>
			
			 <th><label class="text"><span class="red"></span>证件号码</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_psptNo" id="order_allocation_psptNo" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_psptNo }" required="true" />
			</td>
		</tr>
		<tr>
			 <th><label class="text"><span class="red"></span>联系电话</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_mobilePhone" id="order_allocation_mobilePhone" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_mobilePhone }" required="true" />
			</td>
			
			<th><label class="text"><span class="red"></span>用户类型</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_userTag" id="order_allocation_userTag" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_userTag }" required="true" />
			</td>
			
			<th><label class="text"><span class="red"></span>业务类型</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_businessType" id="order_allocation_businessType" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_businessType }" required="true" />
			</td>
			
			<th><label class="text"><span class="red"></span>查询条数</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_pageSize" id="order_allocation_pageSize" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_pageSize }" required="true" />
			</td>
			
		</tr>
		<tr>
			<th><label class="text"><span class="red"></span>查询条数</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_page_webPager_pageInfo_pageSize" id="order_allocation_page_webPager_pageInfo_pageSize" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_page_webPager_pageInfo_pageSize }" required="true" />
			</td>
			
			<th><label class="text"><span class="red"></span>处理条数</label></th>
			<td>
				<input type="text" class="ipttxt"  name="crawlerProperties.order_allocation_count" id="order_allocation_count" style="margin-top:6px;"   class="ipttxt fail" value="${crawlerProperties.order_allocation_count }" required="true" />
			</td>
			
		</tr>
	
 </table>
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>

<script type="text/javascript">
function saveProperties(){
	$("#theForm").submit();
}
</script>