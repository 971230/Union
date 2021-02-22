<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/brandModel.js"></script>

<div class="grid">
	<form method="POST" ajax="yes">
	<div class="input">
	<table class="form-table">
		<tr>
			<th>
			品牌：
			</th>
			<td>
			<input type="text" class="ipttxt" id="brand_name" name="brand_name"  value="${brand_name }"/>
			</td>
			<th>
			机型名称：
			</th>
			<td>
			<input type="text" class="ipttxt" id="machine_name" name="machine_name"  value="${machine_name }"/>
			</td>
			<th>
			型号：
			</th>
			<td>
			<input type="text" class="ipttxt" id="model_name" name="model_name"  value="${model_name }"/>
			</td>
			<td width="100px"><a href="javascript:void(0)" id="model_search_btn" style="margin-right:10px;" class="graybtn1"><span>搜索</span></a></td>
		</tr>
	</table>		
	</div>
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell ></grid:cell>
				<grid:cell >品牌名称</grid:cell>
				<grid:cell >品牌编码</grid:cell>
				<grid:cell >机型名称</grid:cell>
				<grid:cell >机型编码</grid:cell>
				<grid:cell >型号名称</grid:cell>
				<grid:cell >型号编码</grid:cell>
				<grid:cell >创建时间</grid:cell>
			</grid:header>

			<grid:body item="brandModel">
				<grid:cell> <input type="checkbox" style="display:none;" name="id" code="${brandModel.machine_name }" value="${brandModel.brand_model_id }" /> </grid:cell>
				<grid:cell> ${brandModel.brand_name } </grid:cell>
				<grid:cell>${brandModel.brand_code } </grid:cell>
				<grid:cell>${brandModel.machine_name } </grid:cell>
				<grid:cell>${brandModel.machine_code}</grid:cell>
				<grid:cell>${brandModel.model_name } </grid:cell>
				<grid:cell>${brandModel.model_code}</grid:cell>
				<grid:cell><html:dateformat pattern="yyyy-MM-dd" d_time="${brandModel.create_date}"></html:dateformat></grid:cell>
			</grid:body>

		</grid:grid>
		<a href="javascript:void(0)" id="model_confirm_btn" style="margin-left:300px;display:none;" class="graybtn1"><span>确定</span></a></td>
	</form>
	<div style="clear:both;padding-top:5px;"></div>
</div>