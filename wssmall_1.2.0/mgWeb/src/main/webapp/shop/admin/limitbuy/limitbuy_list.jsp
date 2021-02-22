<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/LimitBuy_list.js"></script>
<script type="text/javascript" src="js/Selector.js"></script>
<form method="post" atcion="limitBuy!list.do">
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th>限时购名称：</th>
					<td><input type="text" class="ipttxt" name="limitBuy.name" value="${limitBuy.name }" /></td>
					<th>限时购状态：</th>
					<td>
						<select class="ipttxt" style="width: 155px" name="limitBuy.disabled" id="limitBuy_disabled">
							<option value="-1">--请选择--</option>
							<option value="0">正常</option>
							<option value="1">停用</option>
						</select>
					</td>
					<th>限时购商品：</th>
					<td>
						<input type="text" id="goodsname" name="goodsname" class="ipttxt" value="${goodsname }" readonly/>
						<input type=hidden name="goodsid" value="${goodsid[0] }" id="goodsid"/>
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
		<a href="limitBuy!add.do" class="graybtn1"><span>添加秒杀</span></a>
	</div>
</form>
<div class="grid">
<form method="POST" >
<grid:grid from="webpage">
	<grid:header>
	<grid:cell width="15%">id</grid:cell>
	<grid:cell width="15%">限时购名称</grid:cell> 
	<grid:cell>开始时间</grid:cell>
	<grid:cell>结束时间</grid:cell>
	<grid:cell>限时购状态</grid:cell>
	<grid:cell>限时购商品信息</grid:cell> 
	<grid:cell>操作</grid:cell> 
	</grid:header>

  <grid:body item="limit">
  		<grid:cell>${limit.id} </grid:cell>
  		<grid:cell>${limit.name } </grid:cell>
  		<grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${limit.start_time}"/></grid:cell>
  		<grid:cell><html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${limit.end_time}"/></grid:cell>
  		<grid:cell><c:if test="${limit.disabled==0 }">正常</c:if><c:if test="${limit.disabled==1 }">停用</c:if></grid:cell>
  		<grid:cell><a href="###" onclick="javascript:openDialog('${limit.id}')">详情</a></grid:cell>
        <grid:cell><a href="limitBuy!edit.do?id=${limit.id }" ><img class="modify" src="images/transparent.gif" ></a>  
        &nbsp;&nbsp; 
        	<c:if test="${limit.disabled==0 }">
        		<a onclick="javascript:return confirm('确认停用此限时抢购吗?');" href="limitBuy!delete.do?id=${limit.id }&limitBuy.disabled=1" ><span>停用</span></a>
        	</c:if>
        	<c:if test="${limit.disabled==1 }">
        		<a onclick="javascript:return confirm('确认启用此限时抢购吗?');" href="limitBuy!delete.do?id=${limit.id }&limitBuy.disabled=0" ><span>启用</span></a>
        	</c:if>
        </grid:cell>
  </grid:body>  
</grid:grid>  
</form>	
</div>
<div id="goods_dlg"></div>
<div id="goodsInfo_dlg"></div>
<script>
$(function() {
	$("#limitBuy_disabled option[value='${limitBuy.disabled}']").attr("selected", true);
});
</script>