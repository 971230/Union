<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/shop/admin/goods/batchManage/js/prod_config_list.js"></script>
<style>
.prodPersonal table {
	border-top: #cccccc 1px solid;
	border-left: #cccccc 1px solid;
}

.batchGoodsConfigBtn {
	padding-top: 20px;
}

.submitBth {
	padding-top: 30px;
	padding-bottom: 30px;
}

#dlg_addProdPerConfig_dialog {
	position: absolute;
	left: 300px;
}
</style>
<div>
	<div>
		<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1"  id="addProdPerBtn"><span>添加多个货品</span></a>
	</div>

	<div class="grid" id="prodPersonal" style="width: 100%;">
		<table cellspacing="0" cellpadding="0" border="0"
			id="prodPersonalTable">
			<thead id="productNodeTitle">
				<tr>
				    <th ></th>
					<th style="width: auto;">货品名称</th>
					<th style="width: auto;">条形码</th>
					<th style="width: auto;">颜色</th>
					<th style="width: auto;">操作</th>
				</tr>
			</thead>
			<tbody id="prodPersonalBody" style="opacity: 1;"></tbody>
			<tbody >
				<tr style="height: 20px; display: none" id="product_info_tr">
				    <td><input value=""  id="tpl_product_id"  name="selectOrg" type="radio"/></td>
					<td id="tpl_goods_name"></td>
					<td id="tpl_goods_sn"></td>
					<td id="tpl_goods_color"></td>
					<td>
					<a  href="javascript:void(0);"  name="updateProdPer" id="updateProdPerId">修改</a>&nbsp;&nbsp;&nbsp;
					<a  href="javascript:void(0);"  name="delProdPer" id="delProdPer">删除</a>
					</a></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class='batchGoodsConfigBtn'>
		<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1"  id="batchGoodsConfigBtn"><span>批量生成商品</span></a>
		<a href="javascript:void(0)" style="margin-right:10px;display: none" class="graybtn1"  id="cancleBatchGoodsConfigBtn"><span>取消批量生成商品</span></a>
	</div>
	<div class="grid" id="goodsMsg" style="width: 100%;display: none">
			<table cellspacing="0" cellpadding="0" border="0"
				id="goodsMsgTable">
				<thead id="goodsMsgTitle">
					<tr>
					    <th ></th>
						<th style="width: auto;">商品名称</th>
						<th style="width: auto;">货品名称</th>
						<th style="width: auto;">销售价</th>
						<th style="width: auto;">操作</th>
					</tr>
				</thead>
				<tbody id="goodsMsgBody" style="opacity: 1;"></tbody>
			    <tbody >
				<tr style="height: 20px; display: none" id="goods_info_tr">
				    <td><input value=""  id="tpl_goods_id"  name="selectOrg" type="radio"/></td>
					<td id="g_goods_name"></td>
					<td id="tpl_product_name" product_id=""></td>
					<td id="tpl_goods_price"></td>
					<td>
					<a  href="javascript:void(0);"  name="updateGoodsPer" id="updateGoodsPer">修改</a>&nbsp;&nbsp;&nbsp;
					<a  href="javascript:void(0);"  name="delGoodsPer" id="delGoodsPer">删除</a>
					</a></td>
				</tr>
			   </tbody>
			</table>
	</div>
	<div class="submitlist  submitBth">
		<table width="100%">
			<tr>
				<td style="text-align: center; width: 100%"><input
					type="button" id="perReturnBtn" value="返回" class="submitBtn" /> <input
					type="button" id="perInsureBtn" value="提交配置" class="submitBtn" /></td>
			</tr>
		</table>
	</div>
</div>
<div id="addProdPerConfig_dialog"></div>
<div id="batchGoodsCofig_dialog"></div>
<div id="tagsDialog1"></div>
