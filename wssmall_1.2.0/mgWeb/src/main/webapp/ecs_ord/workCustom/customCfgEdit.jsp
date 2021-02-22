<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义工单配置</title>


<style>
.red_mark {
    color: red;
}

.page-container {
    display: flex;
    width: 100vw;
    justify-content: center;
    flex: 1;
}

.left-container {
    width: 20%;
    border: 1px solid #CCC;
}

.right-container {
    width: 80%;
}

.canvas-main {
    position: relative;
    margin-top: 98px;
}

.canvas {
    height: 550px;
    max-height: 700px;
    border: 1px solid #CCC;
    background-color: white;
    display: flex;
}

.lib_title{
	width: 170px;
	height: 50px;
	border: 1px solid #CCC;
	text-align: center;
	background-color: #bfc1ba;
	font-size: 140%;
	line-height: 50px;
}

.diamond{   
	width: 50px;   
	height: 50px;   
	transform:rotate(45deg);   
	-ms-transform:rotate(45deg); /* Internet Explorer */   
	-moz-transform:rotate(45deg); /* Firefox */   
	-webkit-transform:rotate(45deg); /* Safari 和 Chrome */   
	-o-transform:rotate(45deg); /* Opera */   
	margin:50px auto;/*让菱形浏览器上居中*/  
	text-align: center;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
}

.circle {
	width: 100px;
	height: 40px;
	text-align: center;
	border-radius: 50%;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
}

.rectangle {
	width:100px; 
	height:40px; 
	text-align: center;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
}

.rectangle_radius {
	width:100px; 
	height:40px; 
	text-align: center;
	cursor: pointer;
	position: absolute;
	border: 1px solid black;
	border-radius: 20px;
}

.aLabel {
    background-color: white;
    padding: 0.4em;
    font: 12px sans-serif;
    color: #444;
    z-index: 21;
    border: 1px dotted gray;
    opacity: 0.8;
    cursor: pointer;
}

.color_green	{
	background-color: #70e77f;
}

.color_yellow	{
	background-color: yellow;
}

.color_cyan	{
	background-color: cyan;
}

.color_orange{
	background-color: orange;
}

.color_gray{
	background-color: #CCC;
}

.node_curr{
	border: 3px solid orchid;
}

.node_error{
	border: 3px solid red;
}

</style>


</head>

<body>
	<div id="base_info" class="searchformDiv" style="background: white;border: 0px;display: block;" >
		<table>
			
			<tr>
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>流程名称：</th>
				<td class="table_td_width">
					<input id="edit_cfg_name" type="text" class="ipttxt" />
				</td>
				
				<th class="table_td_width"><span class="id_info" style="display: none;">流程编号：</span></th>
				<td class="table_td_width" >
					<input id="edit_cfg_id" type="text" class="ipttxt id_info" disabled="disabled" style="display: none;" />
				</td>
				
				<th class="table_td_width" ><span class="id_info" style="display: none;">版本编号：</span></th>
				<td class="table_td_width" >
					<input id="edit_version_id" type="text" class="ipttxt id_info" disabled="disabled" style="display: none;" />
				</td>
				
				<th class="table_td_width"><span class="id_info" style="display: none;">状态：</span></th>
				<td class="table_td_width">
					<select id="edit_state" class="table_select id_info" style="display: none;">
						<option value="0">禁用</option>
						<option value="1">正常</option>
						<option value="2">历史</option>
	            	</select>
				</td>
			</tr>
			
			<tr height="50px;">
		   		<td style="text-align: center;font-size: 140%;" colspan="8">
		   			<h1>匹配信息</h1>
		   		</td>
			</tr>
			
			<tr>
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>匹配方式：</th>
				<td class="table_td_width">
					<select id="edit_match_type" class="table_select">
						<option value="1">按商品匹配</option>
						<option value="2">按流程编码匹配</option>
	            	</select>
				</td>
				
				<th class="table_td_width">
					<span class="red_mark flow_code_info" style="display: none;">*</span>
					<span class="flow_code_info" style="display: none;">流程编码：</span>
				</th>
				<td class="table_td_width" colspan="5">
					<input id="edit_flow_code" type="text" class="ipttxt flow_code_info" style="display: none;"/>
				</td>
			</tr>
			
			<tr>
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>配置级别：</th>
				<td class="table_td_width">
					<select id="edit_cfg_level" class="table_select">
						<option value="type">商品大类</option>
						<option value="cat">商品小类</option>
						<option value="id">商品</option>
	            	</select>
				</td>	
				
				<th class="table_td_width condition_type_id">商品大类：</th>
				<td class="table_td_width condition_type_id">
					<select id="edit_goods_type_id" class="table_select">
	            	</select>
				</td>		
				
				<th class="table_td_width condition_cat_id">商品小类：</th>
				<td class="table_td_width condition_cat_id">
					<select id="edit_goods_cat_id" class="table_select">
	            	</select>
				</td>		
				
				<th class="table_td_width condition_goods_id">商品：</th>
				<td class="table_td_width condition_goods_id">
					<select id="edit_goods_id" class="table_select">
	            	</select>
				</td>	
			</tr>
			
			<tr>
				<th class="table_td_width">订单来源：</th>
				<td class="table_td_width">
					<select id="edit_order_from" class="table_select">
	            	</select>
				</td>
				
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>流程类型：</th>
				<td class="table_td_width">
					<select id="edit_cfg_type" class="table_select">
						
	            	</select>
				</td>
				
				<th class="table_td_width td_order_deal_method"><span class="red_mark" style="display: inline;">*</span>受理方式：</th>
				<td class="table_td_width td_order_deal_method">
					<select id="edit_order_deal_method" class="table_select">
						<option value="">不限制</option>
						<option value="1">线上</option>
						<option value="2">线下</option>
	            	</select>
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					<input id="btn_workflow_copy" class="comBtn" type="button" onclick="WorkFlowTool.showCopyWorkFlowDiag();" value="复制流程" style="display: none;"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="searchformDiv" style="background: white;border: 0px;display: block;">
		<table>
			<tr height="50px;">
		   		<td style="text-align: center;font-size: 140%;width: 1164px;">
		   			<h1>流程图</h1>
		   		</td>
			</tr>
		</table>
	</div>
	
	<div id="comments_div" style="display: block;">
		<span style="color: red;margin-left: 80px;font-size: 120%;">1、拖放左侧组件库中的组件来新增组件</span><br />
		<span style="color: red;margin-left: 80px;font-size: 120%;">2、右键点击流程图中的组件来编辑组件属性</span><br />
		<span style="color: red;margin-left: 80px;font-size: 120%;">3、双击连接线来编辑连接条件</span><br />
		<span style="color: red;margin-left: 80px;font-size: 120%;">4、只有需要人员介入的前台环节才能配置处理人</span><br />
		<span style="color: red;margin-left: 80px;font-size: 120%;">5、修改时请谨慎删除流程环节，删除环节后该环节配置的处理人也会失效</span><br />
	</div>
	
	<div id ="work_flow_div"></div>
	
	<div id="div_deal_cfg" class="searchformDiv" style="background: white;border: 0px;margin-top: 30px;display: none;">
		<div class="searchformDiv" style="background: white;border: 0px;">
			<table>
				<tr height="50px;">
			   		<td style="text-align: center;font-size: 140%;width: 1164px;">
			   			<h1>处理人配置</h1>
			   		</td>
				</tr>
			</table>
		</div>
		
		<table>
			<tr>
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
			</tr>
			<tr>
				<th class="table_td_width">查询方式：</th>
				<td class="table_td_width">
					<select id="deal_ext_1" class="table_select">
	            		<option value="1">当前版本</option>
	            		<option value="2">历史版本</option>
	            	</select>
				</td>		
					
				<th class="table_td_width">处理单位级别：</th>
				<td class="table_td_width">
					<select id="deal_cfg_level" class="table_select">
						<option value=""></option>
						<option value="region">地市中台</option>
						<option value="county">县分中台</option>
	            	</select>
				</td>
				
				<th class="table_td_width deal_region">地市：</th>
				<td class="table_td_width deal_region">
					<select id="deal_region_id" class="table_select">
	            	</select>
				</td>
				
				<th class="table_td_width deal_county">县分：</th>
				<td class="table_td_width deal_county">
					<select id="deal_county_id" class="table_select">
	            	</select>
				</td>
			</tr>
			
			<tr>
				<th class="table_td_width">流程环节：</th>
				<td class="table_td_width">
					<select id="deal_node" class="table_select">
	            	</select>
				</td>
				
				<th class="table_td_width">配置编号：</th>
				<td class="table_td_width">
			    	<input id="deal_deal_id" type="text" class="ipttxt" />
				</td>
				
				<th class="table_td_width">版本号：</th>
				<td class="table_td_width">
			    	<input id="deal_version_id" type="text" class="ipttxt" />
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
			    	<input class="comBtn" type="button" name="searchBtn" onclick="WorkFlowTool.doDealQuery();" value="查询" style="margin-right:10px;"/>
				</td>
			</tr>
			
			<tr>
				<th></th>
				<td>
			    	<input class="comBtn" type="button" name="addBtn" onclick="WorkFlowTool.showDealerEditDiag('add');" value="新增" style="margin-right:10px;"/>
				</td>
			</tr>
		</table>
		
		<div>
			<iframe id="deal_info_frame" style="width: 100%;height: 600px;"></iframe>
		</div>
	</div>
	
	<div class="searchformDiv" style="background: white;border: 0px;margin-top: 30px;display: block;">
		<table>
			<tr>
				<th></th>
				<td>
					<br />
					<br />
			    	<input class="comBtn" type="button" id="saveCfgBtn" onclick="WorkFlowTool.saveWorkFlowCfg();" value="保存" style="margin-left:450px;"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="nodeEditDiag"></div>
	<div id="labelEditDiag"></div>
	<div id="dealerEditDiag"></div>
	<div id="CopyWorkFlowComfirmDiag"></div>
</body>

</html>

<script type="text/javascript">

$(function(){
	//初始化流程绘制JS框架
	WorkFlowTool.canvas.init();
	
	//初始化订单来源
	WorkFlowTool.initOrderFrom("edit_order_from");
	
	//初始化配置级别
	WorkFlowTool.initCfgLevel("edit_cfg_level","base_info");
	
	//初始化商品大类、商品小类、商品联动
	WorkFlowTool.addGoodsChanger = new GoodsChanger("edit_goods_type_id","edit_goods_cat_id","edit_goods_id");
	
	//初始化配置类型事件
	WorkFlowTool.initCfgType("edit_cfg_type");
	
	//初始化匹配类型
	WorkFlowTool.initMatchType("edit_match_type");
	
	//装载流程配置
	WorkFlowTool.canvas.loadWorkFlowCfg();
	
	//初始化人员配置界面
	WorkFlowTool.showDealerCfg();
	
	Eop.Dialog.init({
		id:"CopyWorkFlowComfirmDiag",
		modal:false,
		title:"复制流程",
		width:"900px"
	});
});	

</script>