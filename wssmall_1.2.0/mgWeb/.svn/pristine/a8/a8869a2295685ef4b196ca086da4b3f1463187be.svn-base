<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/contract_machine_config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/easyui/locale/easyui-lang-zh_CN.js"></script>

<style>
.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}
.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}
h4{
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

thead tr td {
font-weight:bold;
}

.month-fee-selected{
	background:none repeat scroll 0 0 #F1F7FD;
}

</style>
<form method="post"  id="contractMachineForm" class="validate" validate="true">
<div class="rightDiv" style="overflow: auto;">
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_1" class="selected">
						<span class="word">合约机配置</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
<div class="input">
  <div class="tableform">
     <div class="division" id ="">
	    <table width="100%" >
		    <thead>
		      <tr>
				  <td style="width:25%;"><label for="isAccNbr">号卡配置：&nbsp;&nbsp;</label><input type="checkbox" id="isAccNbr"  /></td>
				  <td style="width:35%;"><label for="goods_price">商品销售价格（元）：</label>
				          <input type='text' id='goods_price'  name='price' class='top_up_ipt'  req size='10' onkeydown="onlyNum(this)"/>
				  </td style="width:35%;">
				  <td>商品分类：
			  			<select style="width:235px;" id="goods_cat_tree">
			  				<option value="">--请选择商品分类--</option>
			  			</select>
				  </td>
		      </tr>
		    </thead>
	    </table>
	  </div>
  </div>
</div>	
<div class="input" id="terminalTablesInput">
  <div class="tableform">
	   <h4>终端列表:
		    <a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" name="addTerminalBtn" id="addTerminalBtn"><span>选择终端</span></a>
	   </h4>
		<div class="division" id ="contractDiv">
		  <table width="100%" id="terminalTables">
		    <thead>
				<tr>
				 	<td><input type="hidden" onclick="selectedChange()"  id="togggleChked" /></td> 
					<td style="width:25%;">终端编码</td>
					<td style="width:25%;">终端名称</td>
					<td style="width:25%;">操作</td>
					<td style="width:25%;"></td>
				</tr>
		    </thead>
	     </table>
	    </div>
   </div>	
</div>
<div class="input" >
  <div class="tableform">
	   <h4>合约列表:
		    <a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" name="addContractBtn" id="addContractBtn"><span>选择合约</span></a>
	   </h4>
		<div class="division" id ="contractDiv">
		  <table width="100%" id="contractTables">
		    <thead>
				<tr>
				 	<td><input type="hidden" onclick="selectedChange()"  id="togggleChked"  /></td> 
					<td style="width:25%;">合约编码</td>
					<td style="width:25%;">合约名称</td>
					<td style="width:25%;">操作</td>
					<td style="width:25%;"></td>
				</tr>
		    </thead>
	     </table>
	    </div>
   </div>	
</div>
<div class="input" >
  <div class="tableform">
	   <h4>套餐列表:
		    <a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" name="addOfferBtn" id="addOfferBtn"><span>选择套餐</span></a>
	   </h4>
		<div class="division" id ="contractDiv">
		  <table width="100%" id="offerTables">
		    <thead>
				<tr>
				 	<td><input type="hidden" onclick="selectedChange()"  id="togggleChked" /></td> 
					<td style="width:25%;">套餐编码</td>
					<td style="width:25%;">套餐名称</td>
					<td style="width:25%;">档次</td>
					<td style="width:25%;">操作</td>
				</tr>
		    </thead>
	     </table>
	    </div>
   </div>	
</div>
<div class="input"  >
  <div >
	   <h4 style="margin-left:10px;">合约机参数配置:(请注意填写以下蓝色背景的参数)
	   </h4>
		<div class="division" id ="contractDiv">
		  <table width="100%" id="contractMachineTables">
			<thead>
				<tr style="">
					<td>档次(元)</td>
				</tr>
			</thead>
            <tbody>
				<tr id="mobile_price">
					<td>购机价(元)</td>
				</tr>
				<tr id="deposit_fee">
					<td>预存款(元)</td>
				</tr>
				<tr id="order_return">
					<td>首月返还金额(元)</td>
				</tr>
				<tr id="mon_return">
					<td>每月返还金额(元)</td>
				</tr>
				<tr id="all_give">
					<td>协议期总送费金额(元)</td>
				</tr>
				<tr id="bss_code" style="display:none;">
					<td>BSS编码</td>
					<td colspan="19" style="padding-left:8px;">
						<input type="text" id="bss_code_ipt" name="paramvalues" class="top_up_ipt" ime_mode_enable="1">
						<input type="hidden" value="0" name="attrvaltype">
						<input type="hidden" value="bss_code" name="ename">
						<input type="hidden" value="" name="attrcode">
						<input type="hidden" value="goodsparam" name="attrtype">
						<input type="hidden" value="" name="required">
					</td>
				</tr>
				<tr id="is_set" style="display:none;">
					<td>是否成品卡</td>
					<td colspan="19" style="padding-left:8px;">
						<select name="paramvalues" style="width:155px;">
							<option value="">--请选择--</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
						<input type="hidden" value="1" name="attrvaltype">
						<input type="hidden" value="is_set" name="ename">
						<input type="hidden" value="DC_IS_SET" name="attrcode">
						<input type="hidden" value="goodsparam" name="attrtype">
						<input type="hidden" value="" name="required">
					</td>
				</tr>
				<tr id="is_attached" style="display:none;">
					<td>是否副卡</td>
					<td colspan="19" style="padding-left:8px;">
						<select name="paramvalues" style="width:155px;">
							<option value="">--请选择--</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
						<input type="hidden" value="1" name="attrvaltype">
						<input type="hidden" value="is_attached" name="ename">
						<input type="hidden" value="DC_IS_ATTACHED" name="attrcode">
						<input type="hidden" value="goodsparam" name="attrtype">
						<input type="hidden" value="" name="required">
					</td>
				</tr>
            </tbody>
	     </table>
	    </div>

   </div>	
</div>
<div class="input2" style="height:100px ">
<div class="inputBox" align="center"  >
	 <table>
	 <tr>
	 <th></th>
	 <td style="text-align:center;">
           <input  type="button"  value=" 新增保存 " class="greenbtnbig" style="cursor:pointer;" id="selectOkBottf"/>
           <input  type="button"  value=" 修改保存 " class="greenbtnbig" style="cursor:pointer;" id="editContractBtn"/>
	 </td>
	 </tr>
	 </table>
 </div>
</div>
</div>
</form>
<div id="terminalList"></div>
<div id="contractList"></div>
<div id="offerList"></div>





