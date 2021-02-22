<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
loadScript("js/goods_areaLog.js");
</script>
<div style="margin-left:10px">
	<form id="gridform" class="grid" >
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell width="120px">发布区域</grid:cell>
				<grid:cell width="180px">发布状态</grid:cell>
				<grid:cell >审核原因</grid:cell>
			</grid:header>
			<grid:body item="goodsArea">
				<grid:cell>&nbsp;
					<c:if test="${goodsArea.lan_id==730}">岳阳</c:if>
					<c:if test="${goodsArea.lan_id==731}">长沙</c:if>
					<c:if test="${goodsArea.lan_id==732}">湘潭</c:if>
					<c:if test="${goodsArea.lan_id==733}">株洲</c:if>
					<c:if test="${goodsArea.lan_id==734}">衡阳</c:if>
					<c:if test="${goodsArea.lan_id==735}">郴州</c:if>
					<c:if test="${goodsArea.lan_id==736}">常德</c:if>
					<c:if test="${goodsArea.lan_id==737}">益阳</c:if>
					<c:if test="${goodsArea.lan_id==738}">娄底</c:if>
					<c:if test="${goodsArea.lan_id==739}">邵阳</c:if>
					<c:if test="${goodsArea.lan_id==743}">吉首</c:if>
					<c:if test="${goodsArea.lan_id==744}">张家界</c:if>
					<c:if test="${goodsArea.lan_id==745}">怀化</c:if>
					<c:if test="${goodsArea.lan_id==746}">永州</c:if>
				</grid:cell>
				<grid:cell>&nbsp;
					<c:if test="${goodsArea.state==0}">未发布</c:if>
					<c:if test="${goodsArea.state==1}">已发布</c:if>
					<c:if test="${goodsArea.state==2}">不发布</c:if>
				</grid:cell>
				<grid:cell>&nbsp;${goodsArea.comments}</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
<div class="submitlist" align="center">
	<table>
		<tr>
			<td>
				<input type="button" value="关闭" class="submitBtn" name='submitBtn' />
			</td>
		</tr>
	</table>
</div>
</div>
