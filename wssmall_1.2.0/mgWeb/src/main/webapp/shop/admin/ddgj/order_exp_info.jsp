<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!-- 订单异常tab页面  show_click_8-->

<div class="btnWarp">
 </div>
 
<div class="formWarp" style="border-bottom: none;">
<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="500" class="closeArrow"></a>订单异常<div class="dobtnDiv"></div></div>
<div id="order_tag_500" class="formGridDiv">
    <table id="flow_items_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="blueTable">
				<tr>
					<th style="width: 80px;">订单号</th>
					<th style="width: 80px;">异常描述</th>
					<th style="width: 80px;">异常名称</th>
					<th style="width: 80px;">创建时间</th>
				</tr>
			<tbody>
				<c:forEach items="${orderExpList}" var="expItem">
					<tr>
						<td>${expItem.order_id}</td>
						<td>${expItem.comments}</td>
						<td>${expItem.exception_name}</td>
						<td><html:dateformat pattern="yyyy-MM-dd HH:mm:ss"
								d_time="${expItem.create_date}"></html:dateformat></td>
					</tr>
				</c:forEach>
				<tr>
				</tr>
			</tbody>
    </table>
</div>    
</div>
