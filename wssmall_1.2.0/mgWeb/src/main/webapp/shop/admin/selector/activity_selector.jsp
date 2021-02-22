<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<script type="text/javascript">
	
</script>
<form id="serchActform">
	<div class="input">
		<table class="form-table">
			<tr>
				<th>活动名称 ：</th>
				<td><input type="text" class="ipttxt" name="name" id="name" />
					<input type="hidden" class="ipttxt" name="activity.id" id="name" />
					<a href="javascript:void(0)" id="searchAct"
					style="margin-left:5px;margin-right:10px;" class="graybtn1"><span>搜索</span></a>
				</td>
			</tr>
		</table>
	</div>

	<div style="clear:both;padding-top:5px;"></div>

	<div class="grid" id="Activitylist">

		<grid:grid from="webpage" ajax="yes">

			<grid:header>
				<grid:cell width="50px">
					<input type="checkbox" id="toggleChk" onChange="selectChange()" />
				</grid:cell>
				<grid:cell width="80px">活动名称</grid:cell>
				<grid:cell width="250px">活动状态</grid:cell>
				<grid:cell>起始时间</grid:cell>
				<grid:cell>截止时间</grid:cell>

			</grid:header>


			<grid:body item="activity">

				<grid:cell>
					<input type="checkbox" name="id" value="${activity.id }" />
					<%--${goods.goods_id } --%>
				</grid:cell>
				<grid:cell>&nbsp;${activity.name } </grid:cell>
				<grid:cell>&nbsp;${activity.enable } 
        <c:if test="${pmt.enable=='1'}">有效</c:if>
					<c:if test="${pmt.enable=='0'}">停用</c:if>
				</grid:cell>
				<grid:cell>${fn:substring(activity.begin_time , 0 , 10)} </grid:cell>
				<grid:cell>${fn:substring(activity.end_time , 0 , 10)}</grid:cell>

			</grid:body>

		</grid:grid>
	</div>
	<div style="clear:both;margin-top:5px;"></div>
	<div align="center" style="margin-top:5px;margin-bottom:5px;">
		<input name="activity_btn" type="button" value=" 确    定   "
			class="submitBtn" />
	</div>


</form>
