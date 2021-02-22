<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style>
	<!-- .noborder {
		border-style: none;
	}
	
	-->.icoFontlist {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		color: #5f5f5f;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.icoFontlist:hover {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		overflow: scroll;
		text-overflow: ellipsis;
		white-space: nowrap;
		cursor: pointer;
	}
	
	.second select {
		width: 352px;
		height: 106px;
		margin: 0px;
		outline: none;
		border: 1px solid #999;
		margin-top: 33px;
		background-color: white;
	}
	
	.second select option {
		background-color: inherit;
	}
	
	.op {
		background-color: transparent;
		bacground: tansparent;
		-webkit-appearance: none;
	}
	
	.second input {
		width: 350px;
		top: 9px;
		outline: none;
		border: 0pt;
		position: absolute;
		line-height: 30px;
		/* left: 8px; */
		height: 30px;
		border: 1px solid #999;
	}
	
	.blue {
		background: #1e91ff;
	}
</style>

<body>

<div class="input">
	<div id="copy_cfg_info">
		<div style="margin-top: 5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th style="width: 150px;text-align: right;">
							<span class="red_mark" style="display: inline;">*</span>流程名称：
						</th>
						<td>
							<input id="copy_cfg_name" type="text" class="ipt_new grid_form_input" style="width: 200px;"/>
						</td>
						
						<th style="width: 150px;text-align: right;" >匹配方式：</th>
						<td>
							<select id="copy_match_type" class="ipt_new grid_form_select" style="width: 200px;">
	                        	<option value="1">按商品匹配</option>
								<option value="2">按流程编码匹配</option>
                            </select>
						</td>
						
						<th style="width: 150px;text-align: right;">
							<span class="red_mark flow_code_info" style="display: none;">*</span>
							<span class="flow_code_info" style="display: none;">流程编码：</span>
						</th>
						<td>
							<input id="copy_flow_code" type="text" class="ipt_new grid_form_input flow_code_info" style="width: 200px;"/>
						</td>
					</tr>
					
					<tr>
						<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>配置级别：</th>
						<td class="table_td_width">
							<select id="copy_cfg_level" class="ipt_new grid_form_select" style="width: 200px;">
								<option value="type">商品大类</option>
								<option value="cat">商品小类</option>
								<option value="id">商品</option>
			            	</select>
						</td>
					</tr>
						
					<tr>
						<th class="condition_type_id" style="width: 150px;text-align: right;" >商品大类：</th>
						<td class="condition_type_id">
							<select id="copy_goods_type_id" class="ipt_new grid_form_select" style="width: 200px;">
	                        		
                            </select>
						</td>
						
						<th class="condition_cat_id" style="width: 150px;text-align: right;" >商品小类：</th>
						<td class="condition_cat_id">
							<select id="copy_goods_cat_id" class="ipt_new grid_form_select" style="width: 200px;">
	                        		
                            </select>
						</td>
						
						<th class="condition_goods_id" style="width: 150px;text-align: right;" >商品：</th>
						<td class="condition_goods_id">
							<select id="copy_goods_id" class="ipt_new grid_form_select" style="width: 200px;">
	                        		
                            </select>
						</td>
					</tr>
					
					<tr>
						<th style="width: 150px;text-align: right;" >订单来源：</th>
						<td>
							<select id="copy_order_from" class="ipt_new grid_form_select" style="width: 200px;">
	                        		
                            </select>
						</td>
						
						<th style="width: 150px;text-align: right;" >流程类型：</th>
						<td>
							<select id="copy_cfg_type" class="ipt_new grid_form_select" style="width: 200px;">
	                        	
                            </select>
						</td>
						
						<th style="width: 150px;text-align: right;" class="td_order_deal_method">受理方式：</th>
						<td class="td_order_deal_method">
							<select id="copy_order_deal_method" class="ipt_new grid_form_select" style="width: 200px;">
								<option value="">不限制</option>
								<option value="1">线上</option>
								<option value="2">线下</option>
			            	</select>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="pop_btn" align="center">
			<a id="copy_comfirm_btn" class="blueBtns" style="cursor: pointer;" onclick="WorkFlowTool.copyWorkFlow();"><span>确定</span></a>
		</div>
	</div>
</div>

</body>

<script type="text/javascript">

var copyGoodsChanger = new GoodsChanger("copy_goods_type_id","copy_goods_cat_id","copy_goods_id");

$(function(){
	//初始化订单来源
	WorkFlowTool.initOrderFrom("copy_order_from");
	
	//初始化配置级别
	WorkFlowTool.initCfgLevel("copy_cfg_level","copy_cfg_info");
	
	//初始化配置类型事件
	WorkFlowTool.initCfgType("copy_cfg_type");
	
	//初始化匹配类型
	WorkFlowTool.initMatchType("copy_match_type");
});	

</script>