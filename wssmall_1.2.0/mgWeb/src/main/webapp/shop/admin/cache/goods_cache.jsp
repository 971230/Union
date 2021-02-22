<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="cache/js/goods_cache.js"></script>
<div class="searchformDiv">	
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr>
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="只缓存商品基本信息、商品分类、商品品牌、商品类型、商品服务类型、商品会员售卖价格、商品规格产品列表，不包括详情页属性信息，详情信息" 
						value="商品数据" id="loadAllGoods" name="loadAllGoods">
				</td>
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="终端缓存" 
						value="终端缓存" id="loadAllGoodsTerminal" name="loadAllGoodsTerminal">
				</td>
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="套餐缓存" 
						value="套餐缓存" id="loadAllGoodsTC" name="loadAllGoodsTC">
				</td>
			</tr>
			<tr>		
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="合约机缓存" 
						value="合约机缓存" id="loadAllGoodsContract" name="loadAllGoodsContract">
				</td>	
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="商品促销" 
						value="商品促销" id="loadAllGoodsPromotion" name="loadAllGoodsPromotion">
				</td>	
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存商品标签列表，精品推荐、热卖商品等" 
						value="商品标签" id="loadAllGoodsTags" name="loadAllGoodsTags">
				</td>	
			</tr>
			<tr>					
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存标签关联商品信息" 
						value="标签关联商品" id="loadAllGoodsRelTags" name="loadAllGoodsRelTags">
				</td>	
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存商品绑定关联商品" 
						value="绑定商品缓存" id="loadGoodsComplex" name="loadGoodsComplex">
				</td>		
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存商品的配件信息" 
						value="商品配件缓存" id="loadGoodsAdjunct" name="loadGoodsAdjunct">
				</td>
			</tr>
			<tr>					
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存每一个分类的推荐商品信息" 
						value="分类推荐商品缓存" id="loadCatComplex" name="loadCatComplex">
				</td>	
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存商品类型信息" 
						value="商品类型" id="loadGoodsType" name="loadGoodsType">
				</td>		
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存商品品牌信息" 
						value="商品品牌缓存" id="loadBrand" name="loadBrand">
				</td>	
			</tr>
			<tr>				
				<td style="text-align: center;">
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="缓存商品分类信息，提供按会员缓存商品分类" 
						value="商品分类缓存" id="loadGoodsCatByLvId" name="loadGoodsCatByLvId">
				</td>	
				<td style="text-align: center;" >
					<input jqType="goodsCache" type="submit" style="margin-right: 10px;" class="comBtn" title="查询分类商品（门户展示一级分类下所有商品）" 
						value="查询分类商品" id="loadGoodsByCatLvI" name="loadGoodsByCatLvI">
				</td>	
				<td style="text-align: center;" >
				</td>											
			</tr>
		</tbody>
	</table>
</div>
<script>
$(function(){
	Eop.Cache.init();
})
</script>
<div id="rfsResult">
</div>
