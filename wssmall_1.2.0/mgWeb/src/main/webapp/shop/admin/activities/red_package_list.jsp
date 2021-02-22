<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="activities/js/red_package_list.js"></script>
<form method="post" id='coupon_redlist_form' action='promotionred!redlist.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>红包名称:&nbsp;&nbsp;</th>
					<td><input type="text" class="ipttxt" style="width: 95px;"
						name="redSearch.redName" value="${redSearch.redName }"
						class="searchipt" /></td>
					<th>红包创建时间:</th>
					<td><input type="text" class="ipttxt" dataType="date"
						style="width: 120px" name="redSearch.redCreateTime"
						value="${redSearch.redCreateTime }" class="searchipt" /></td>
					<td><input type="submit" style="margin-right:10px;"
						class="comBtn" value="搜&nbsp;索" type="submit" id="submitButton"
						name="button"></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a href="promotionred!redadd.do" style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
		<a href="javascript:;" style="margin-right:10px;" class="graybtn1" id="del_btn"><span>删除</span></a>
		<div style="clear:both"></div>
	</div>
</form>
<div class="grid">
	<form method="POST" id="coupon_redlist_form">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell >红包名称</grid:cell>
				<grid:cell >红包类型</grid:cell>
				<grid:cell >红包个数</grid:cell>
				<grid:cell >红包描述</grid:cell>
				<grid:cell >红包已使用个数</grid:cell>
				<grid:cell >红包创建时间</grid:cell>
				<grid:cell >红包总金额</grid:cell>
				<grid:cell width="100px">操作</grid:cell>
			</grid:header>
			<grid:body item="promotion">
				<grid:cell>
					<input red_checkbox="red_checkbox" type="checkbox" value="${promotion.id }" />
					<input type="hidden" name="" value="" disabled="" />
				</grid:cell>
				<grid:cell>${promotion.name } </grid:cell>
				<grid:cell>
					<c:choose>
						<c:when test="${promotion.type=='001'}">普通红包</c:when>
						<c:when test="${promotion.type=='002'}">随机红包</c:when>
						<c:when test="${promotion.type=='003'}">话费红包</c:when>
					</c:choose>
				</grid:cell>
				<grid:cell>${promotion.num }</grid:cell>
				<grid:cell>${promotion.memo } </grid:cell>
				<grid:cell>${promotion.usednum } </grid:cell>
				<grid:cell>${promotion.create_time }</grid:cell>
				<grid:cell>${promotion.amount } </grid:cell>
				<grid:cell>
					<a href="promotionred!rededit.do?red_id=${promotion.id }" id="edit_red_pkg"><img class="modify" src="images/transparent.gif"></a>
					<span class='tdsper'></span>
					<a href="promotionred!redview.do?red_id=${promotion.id }">查看详细</a>
				</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
</div>