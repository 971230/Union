<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义流程配置</title>

<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/jsplumb.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/workCustom/js/workFlow.js"></script>

<style>

.clickClass{
   background:#f7f7f7
}

.table_td_width{
	width: 12.5%;
}

.table_select{
	width: 162px;
	height: 24px;
}
  
</style>

</head>

<body style="min-width: 150px;">
	<form method="post" id="cfgSearchform">
		<div class="searchformDiv" style="background: white;" >
			<table>
				<tr>
					<th class="table_td_width">查询方式：</th>
					<td class="table_td_width">
						<select id="ext_1" name="cfg.ext_1" class="table_select">
		            		<option value="1">当前版本</option>
		            		<option value="2">历史版本</option>
		            	</select>
					</td>		
					
					<th class="table_td_width">流程编号：</th>
					<td class="table_td_width">
						<input id="cfg_id" type="text" class="ipttxt"  name="cfg.cfg_id" />
					</td>	
					
					<th class="table_td_width">流程名称：</th>
					<td class="table_td_width">
						<input id="cfg_name" type="text" class="ipttxt"  name="cfg.cfg_name" />
					</td>		
					
					<th class="table_td_width">版本编号：</th>
					<td class="table_td_width">
						<input id="version_id" type="text" class="ipttxt"  name="cfg.version_id"  />
					</td>	
				</tr>
				
				<tr>
					<th class="table_td_width">配置级别：</th>
					<td class="table_td_width">
						<select id="cfg_level" name="cfg.cfg_level" class="table_select">
							<option value="">请选择</option>
							<option value="type">商品大类</option>
							<option value="cat">商品小类</option>
							<option value="id">商品</option>
		            	</select>
					</td>	
					
					<th class="table_td_width condition_type_id">商品大类：</th>
					<td class="table_td_width condition_type_id">
						<select id="goods_type_id" name="cfg.goods_type_id" class="table_select">
		            	</select>
					</td>		
					
					<th class="table_td_width condition_cat_id">商品小类：</th>
					<td class="table_td_width condition_cat_id">
						<select id="goods_cat_id" name="cfg.goods_cat_id" class="table_select">
		            	</select>
					</td>		
					
					<th class="table_td_width condition_goods_id">商品：</th>
					<td class="table_td_width condition_goods_id">
						<select id="goods_id" name="cfg.goods_id" class="table_select">
		            	</select>
					</td>	
				</tr>
				
				<tr>
					<th class="table_td_width">订单来源：</th>
					<td class="table_td_width">
						<select id="order_from" name="cfg.order_from" class="table_select">
		            	</select>
					</td>	
					
					<th class="table_td_width"></th>
					<td class="table_td_width">
				    	<input class="comBtn" type="button" name="searchBtn" id="searchBtn" onclick="WorkFlowTool.doQuery();" value="查询" style="margin-right:10px;"/>
					</td>
				</tr>
				
				<tr>
					<th></th>
					<td>
				    	<input class="comBtn" type="button" name="addBtn" id="addBtn" onclick="WorkFlowTool.showAddDiag();" value="新增" style="margin-right:10px;"/>
					</td>
				</tr>
			</table>
		</div>
	</form>

	<div>
		<iframe id="cfgFrame" style="width: 100%;height: 600px;"></iframe>
	</div>
	
	<div id="EditDiag"></div>
</body>

<script type="text/javascript">

$(function(){
	WorkFlowTool.initPermission();
	
	WorkFlowTool.initOrderFrom("order_from");
	
	WorkFlowTool.initCfgLevel("cfg_level","cfgSearchform");
	
	WorkFlowTool.goodsChanger = new GoodsChanger("goods_type_id","goods_cat_id","goods_id");
	
	$("#ext_1").change(function(){
		WorkFlowTool.doQuery();
	});
	
	$("#ext_1").change();
	
	if("1" != WorkFlowTool.permission.level){
		$("#addBtn").remove();
	}
});


</script>

