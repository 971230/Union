<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
//loadScript("js/GoodsList.js");
</script>

<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>

<div >
	<form  action="${listFormActionVal}"  id="searchGoodsListForm" method="post">
    <div class="searchformDiv">
	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
	  <tbody>
	    <tr>
	      <th>商品名称:</th>
	      <td><input type="text" class="ipttxt"  style="width: 250px" name="name" class="searchipt" value="${name}" /></td>
		<th>商品类型:</th>
		<td><select class="ipttxt" style="width: 150px"name="taobao_search_goods_cat" >
				<div style="boder-top-style: solid; border-top-width: 2px">
					<option value="69030101"   <% if(request.getAttribute("taobao_search_goods_cat").equals("69030101")){ %>selected<%} %>> 定制机全国存费送机</option>					
					<option value="69030102"  <% if(request.getAttribute("taobao_search_goods_cat").equals("69030102")){ %>selected<%} %> >定制机全国购机送费</option>
					<option value="69030103" <% if(request.getAttribute("taobao_search_goods_cat").equals("69030103")){ %>selected<%} %>>定制机本地购机送费</option>
					<option value="69030104" <% if(request.getAttribute("taobao_search_goods_cat").equals("69030104")){ %>selected<%} %>>定制机全国合约惠机</option>
					<option value="690301041" <% if(request.getAttribute("taobao_search_goods_cat").equals("690301041")){ %>selected<%} %>>定制机全国合约惠机A</option>
					<option value="690301042" <% if(request.getAttribute("taobao_search_goods_cat").equals("690301042")){ %>selected<%} %>>定制机全国合约惠机B</option>
				</div>
				<!-- 
				<div style="boder-top-style: solid; border-top-width: 2px">
					<option value="69030201" >社会机全国存费送机</option>
					<option value="69030202">社会机全国购机送费</option>
					<option value="69030203">社会机本地购机送费</option>
					<option value="69030204">社会机全国合约惠机</option>
				</div> -->
				<div style="boder-top-style: solid; border-top-width: 2px">
					<option value="69050100" <% if(request.getAttribute("taobao_search_goods_cat").equals("69050100")){ %>selected<%} %>>裸机定制机</option>
					<option value="69050200" <% if(request.getAttribute("taobao_search_goods_cat").equals("69050200")){ %>selected<%} %>>裸机社会机</option>
				</div>
				<div style="boder-top-style: solid; border-top-width: 2px">
					<option value="69010101" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010101")){ %>selected<%} %>>预付套餐3G号卡</option>
					<option value="69010102" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010102")){ %>selected<%} %>>预付套餐2G号卡</option>
					<option value="69010103" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010103")){ %>selected<%} %>>预付套餐3G号卡套装</option>
					<option value="69010104" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010104")){ %>selected<%} %>>预付套餐2G号卡套装</option>
				</div>
				<div style="boder-top-style: solid; border-top-width: 2px">
					<option value="69010201" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010201")){ %>selected<%} %>>后付费套餐3G号卡</option>
					<option value="69010202" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010202")){ %>selected<%} %>>后付费套餐2G号卡</option>
					<option value="69010203" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010203")){ %>selected<%} %>>后付费套餐3G号卡套装</option>
					<option value="69010204" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010204")){ %>selected<%} %>>后付费套餐2G号卡套装</option>
					<option value="69010205" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010205")){ %>selected<%} %>>后付费套餐4G号卡合约</option>
					<option value="69010207" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010207")){ %>selected<%} %>>后付费套餐4G号卡</option>
				</div>
				<div style="boder-top-style: solid; border-top-width: 2px">
					<option value="69010206" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010206")){ %>selected<%} %>>4G全国版自由组合套餐</option>
					<option value="69010208" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010208")){ %>selected<%} %>>4G本地版自由组合套餐</option>
					<option value="69010209" <% if(request.getAttribute("taobao_search_goods_cat").equals("69010209")){ %>selected<%} %>>4G共享版自由组合套餐</option>
				</div>
				</select></td>
						<!-- 
	      <th>产品包编码:</th>
	      <td><input type="text" class="ipttxt"  class="searchipt" style="width: 170px" name="p_code" value="${p_code}"/> </td>
	       -->
	      <th>是否有效:<input type="hidden" id="back_market_enable" value="${market_enable}" /></th>
	      <td>
		    <select class="ipttxt" style="width:100px" id="market_enable_select" name="market_enable">
			  <option value="2">--请选择--</option>
			  <option value="1">是</option>
			  <option value="0">否</option>
		    </select>
		  </td>
	      <td>
	        <input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索"  id="button" name="button">
	      </td>
        </tr>
      </tbody>
      </table>
    </div>
  </form>
	
	<c:if test="${not taobao_search_contract_type}">
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchGoodsListForm" action="${listFormActionVal}" >
			<grid:header>
					<grid:cell width="250px">商品名称</grid:cell>
					<grid:cell>goods_id</grid:cell>
					<grid:cell>销售价格(元)</grid:cell>
					<grid:cell sort="create_time">创建时间</grid:cell>
					<grid:cell>是否有效</grid:cell>		
			</grid:header>
			<grid:body item="goods">		
					<grid:cell>&nbsp;${goods.name}</grid:cell>
					<grid:cell>&nbsp;${goods.goods_id}</grid:cell>
					<grid:cell>&nbsp;${goods.price}</grid:cell>
					<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat></grid:cell>
					<grid:cell><c:if test="${goods.market_enable == 1}">有效</c:if><c:if test="${goods.market_enable != 1}">无效</c:if></grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	</c:if>
	
	<c:if test="${taobao_search_contract_type }">
	<form id="gridform" class="grid">
		<grid:grid from="webpage" formId="searchGoodsListForm" action="${listFormActionVal}" >
			<grid:header>
					<grid:cell width="250px">商品名称</grid:cell>
					<grid:cell>合约编码</grid:cell>
					<grid:cell>商家编码</grid:cell>
					<grid:cell>销售价格(元)</grid:cell>
					<grid:cell sort="create_time">创建时间</grid:cell>
					<grid:cell>是否有效</grid:cell>
			</grid:header>
			<grid:body item="goods">
					<grid:cell>&nbsp;${goods.name}</grid:cell>
					<grid:cell>&nbsp;${goods.p_code }</grid:cell>
					<grid:cell>&nbsp;${goods.barcode }</grid:cell>
					<grid:cell>&nbsp;${goods.price}</grid:cell>
					<grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${goods.create_time}"></html:dateformat></grid:cell>
					<grid:cell><c:if test="${goods.market_enable == 1}">有效</c:if><c:if test="${goods.market_enable != 1}">无效</c:if></grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	</c:if>
	<!-- 审核日志页面 -->
	<div id="auditDialog_log" >
		
	</div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<!-- 商品分类选择 -->
<div id="goodsTypeQuickBtn_dialog"></div>

<div id="modPriceDlg"></div>
<!-- 提价申请对话框 -->
<div id="increase_dialog" ></div>
<!-- 高级搜索对话框 -->
<div id="search_dialog" ></div>
<!-- 商家列表  -->
<div id="showPurchaseOrderDlg"></div>
<!-- 流程发起申请理由 -->
<div id="apply_reson_info"></div>
<!-- 商品发布 -->
<div id="goods_publish_dialog"></div>
<!-- 价格列表框 -->
<div id="price_list" align="center"></div>
<input type="hidden" id="up_goods_state">

<script type="text/javascript">

  $(function(){
	
  });

</script>
