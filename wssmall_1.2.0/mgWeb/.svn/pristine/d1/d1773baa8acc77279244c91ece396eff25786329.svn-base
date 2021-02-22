<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/GroupBuy.js"></script>
<script type="text/javascript" src="js/Selector.js"></script>

<form  method="post" action="groupBuy!list.do">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>团购名称：</th>
					<td><input type="text" class="ipttxt" name="groupBuy.title" value="${groupBuy.title }" /></td>
					<th>团购状态：</th>
					<td>
						<select class="ipttxt" style="width: 155px" name="groupBuy.disabled" id="groupBuy_disabled">
							<option value="-1">--请选择--</option>
							<option value="0">正常</option>
							<option value="1">停用</option>
						</select>
					</td>
					<th>团购商品：</th>
					<td>
						<input type="text" id="goodsname" name="groupBuy.goods.name" class="ipttxt" value="${groupBuy.goods.name }" readonly/>
						<input type="hidden" name="groupBuy.goodsid" value="${groupBuy.goodsid }" id="goodsid"/>
						<img src="images/zoom_btn.gif" id="selgoodsBtn" app="desktop" title="查看商品列表" class="pointer btn_supplier" style="cursor: pointer;vertical-align: middle;">
					</td>
					<th width="10%">&nbsp;</th>
					<td width="10%">&nbsp;</td>
				</tr>
				<tr>
					<th>开始时间：</th>
					<td>
						<input type="text" class="dateinput ipttxt" name="start_time" dataType="date" required="true" readonly value="${start_time}">
					</td>
					<th>结束时间：</th>
					<td>
						<input type="text" class="dateinput ipttxt" name="end_time" dataType="date" required="true" readonly value="${end_time}">
					</td>
					<th>&nbsp;</th>
					<td style="text-align:center"><input type="submit" class="comBtn" value="查 询"/></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a href="groupBuy!add.do" class="graybtn1"><span> 添加团购 </span></a>
	</div>
</form>
<div class="grid">
<form method="POST" >
<grid:grid  from="webpage">

	<grid:header>
	<grid:cell width="15%">id</grid:cell>
	<grid:cell>团购名称</grid:cell>
	<grid:cell>商品名称</grid:cell> 
	<grid:cell>限购数量</grid:cell>
	<grid:cell>已购数量</grid:cell>
	<grid:cell>开始时间</grid:cell>
	<grid:cell>结束时间</grid:cell>
	<grid:cell>团购状态</grid:cell>
	<grid:cell>操作</grid:cell> 
	</grid:header>

  <grid:body item="group">
  		<grid:cell>${group.groupid } </grid:cell>
        <grid:cell>${group.title } </grid:cell>
        <grid:cell>${group.name } </grid:cell>
        <grid:cell>${group.buy_count}</grid:cell>
        <grid:cell>${group.buyed_count}</grid:cell>  
  		<grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${group.start_time}"/></grid:cell>
  		<grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${group.end_time}"/></grid:cell>
  		<grid:cell><c:if test="${group.disabled==0 }">正常</c:if><c:if test="${group.disabled==1 }">停用</c:if></grid:cell>
        <grid:cell><a href="groupBuy!edit.do?groupid=${group.groupid }" title="编辑团购"><img class="modify" src="images/transparent.gif" style="vertical-align:bottom;"></a>  
        &nbsp;&nbsp; 
        	<c:if test="${group.disabled==0 }">
        		<a onclick="javascript:return confirm('确认停用此团购吗?');" href="groupBuy!delete.do?groupid=${group.groupid }&groupBuy.disabled=1" ><span>停用</span></a>
        	</c:if>
        	<c:if test="${group.disabled==1 }">
        		<a onclick="javascript:return confirm('确认启用此团购吗?');" href="groupBuy!delete.do?groupid=${group.groupid }&groupBuy.disabled=0" ><span>启用</span></a>
        	</c:if>
        </grid:cell>
  </grid:body>  
  
</grid:grid>  
</form>	
</div>
<div id="goods_dlg"></div>
<script>
$(function() {
	$("#groupBuy_disabled option[value='${groupBuy.disabled}']").attr("selected", true);
});
</script>