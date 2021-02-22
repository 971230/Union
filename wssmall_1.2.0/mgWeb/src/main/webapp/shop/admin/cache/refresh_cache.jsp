<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="cache/js/refresh_cache.js"></script>

<div class="searchformDiv">	
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr>
				<!-- <td style="text-align: center;">
					<select id="test_type">
						<option value="-1">请选择测试类型</option>
						<option value="0">规则缓存(actionId,ruleId)</option>
						<option value="1">方案缓存(planId)</option>
						<option value="2">方案下一级规则缓存(planId,auto_exe)</option>
						<option value="3">根据规则ID缓存方案(planId,ruleId,auto_exe)</option>
						<option value="4">缓存子规则(planId,ruleId,auto_exe)</option>
						<option value="5">缓存规则依赖关系(planId,ruleId,auto_exe)</option>
					</select>
					<input type="text" name="test_auto_exe" id="test_auto_exe" size="10"/>
					<textarea name="test_val" id="test_val" rows="3" cols="20"></textarea>
					<input type="submit" style="margin-right: 10px;" class="comBtn"
						value="测试缓存规则" id="testCache" name="testCache">
				</td> -->
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="缓存方案规则" id="rfsOrderRule" name="rfsOrderRule">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M缓存订单属性" id="rfsOrderTreeAttr" name="rfsOrderTreeAttr">
				</td>
				<!-- <td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="清空规则脚本缓存" id="rfsClearRuleScript" name="rfsClearRuleScript">
				</td> -->
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M刷新权限数据" id="rfsLimitData" name="rfsLimitData">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M商品配置数据" id="rfsGoodsConfig" name="rfsGoodsConfig" title="刷新类型，分类，品牌，型号，默认串号；全量刷新">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M刷新接口配置数据" id="refreshInfCommClientInfo" name="refreshInfCommClientInfo" title="相关表inf_comm_client_request、inf_comm_client_endpoint、inf_comm_client_response、inf_comm_client_operation">
				</td>
				<!-- <td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="E配置数据(仅本机)" id="rfsConfig" name="rfsConfig" title="可以刷新es_config_info表配置数据">
				</td> -->
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M刷新灰度配置" id="refreshGrayConfig" name="refreshGrayConfig" title="刷新es_abgray_hostenv，es_data_source_config">
				</td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="定时任务(java静态变量)" id="rfsTaskJob" name="rfsTaskJob">
				</td>
				
				<!-- <td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn" value="开启缓存" id="cache_btn" name="rfsGoods">
				</td> -->
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M刷新MALL_CONFIG" id="rfsMallConfig" name="rfsMallConfig">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M刷新DSTORE_CONFIG" id="rfsDStoreConfig" name="rfsDStoreConfig">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="M刷新对端系统参数" id="refreshRemoteService" name="refreshRemoteService">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="刷新业务类型配置数据" id="rfsBusinessProperties" name="rfsBusinessProperties" title="刷新es_business_type,es_business_type_attr_rela,es_business_type_rule_rela,es_business_cat">
				</td>
				
			</tr>
			<tr>
			    <td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="E配置数据" id="rfsAllConfig" name="rfsAllConfig" title="可以批量刷新es_config_info表配置数据">
				</td>
				<!-- <td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="E单点登录" id="rfsSingle" name="rfsSingle">
				</td> -->
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="E静态数据" id="rfsPublic" name="rfsPublic" title="可以刷新静态数据，映射数据，地市信息，物流公司信息，发展人信息，总部交互配置">
				</td>
				<!-- <td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="商品数据【全量】" id="rfsGoods" name="rfsGoods" title="刷新商品，货品；全量刷新">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="商品数据【增量】" id="rfsNewGoods" name="rfsNewGoods" title="刷新商品，货品，礼品；刷新近5天">
				</td> -->
				<!-- <td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="缓存方案规则和条件" id="cachePlanRuleCond" name="cachePlanRuleCond">
				</td> -->
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="E缓存自由组合依赖元素" id="cacheFreedomGruopDependElement" name="cacheFreedomGruopDependElement">
				</td>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="E刷新规格定义关键字" id="refreshSpecDefValues" name="refreshSpecDefValues">
				</td>
				
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="刷新爬虫配置文件" id="rfsCrawlerProperties" name="rfsCrawlerProperties">
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;">
					<input type="submit" style="margin-right: 10px;width:200px;" class="comBtn"
						value="刷新收单本地校验缓存" id="rfsStdValiProperties" name="rfsStdValiProperties" title="刷新收单本地校验优惠活动信息，商品信息缓存">
				</td>
			</tr>
		</tbody>
	</table>
</div>
<script>
$(function(){
	
	Eop.Cache.init();
	
	$("#rfsTaskJob").bind("click",function(){
		$.get("task!refreshCache.do?ajax=yes",{},function(data){
			alert(data.msg);
		},"json");
	});
	
	$("#rfsBusinessProperties").bind("click",function(){
		$.get("businessRefreshAction!refreshBusinessCache.do?ajax=yes",{},function(data){
			alert(data.msg);
		},"json");
	});
	
	$("#rfsStdValiProperties").bind("click",function(){
		$.get("businessRefreshAction!refreshValidateCache.do?ajax=yes",{},function(data){
			alert(data.msg);
		},"json");
	});
});
</script>
<div id="rfsResult">
</div>
