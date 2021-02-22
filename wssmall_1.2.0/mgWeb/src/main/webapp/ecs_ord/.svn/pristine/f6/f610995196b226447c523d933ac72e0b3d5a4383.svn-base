<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
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

<body>
	<div id="dealer_edit_div" class="searchformDiv" style="background: white;border: 0px;" >
		<table>
			<tr id="tr_deal_id" style="display: none;">
				<th class="table_td_width">配置编号：</th>
				<td class="table_td_width">
					<input id="dealer_edit_deal_id" type="text" class="ipttxt" disabled="disabled" />
				</td>
				
				<th class="table_td_width">版本号：</th>
				<td class="table_td_width">
					<input id="dealer_edit_version_id" type="text" class="ipttxt" disabled="disabled" />
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
			</tr>
			<tr>
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>处理单位级别：</th>
				<td class="table_td_width">
					<select id="dealer_edit_deal_level" class="table_select">
						<option value="region">地市中台</option>
						<option value="county">县分中台</option>
	            	</select>
				</td>
				
				<th class="table_td_width deal_region"><span class="red_mark" style="display: inline;">*</span>地市：</th>
				<td class="table_td_width deal_region">
					<select id="dealer_edit_region_id" class="table_select">
	            	</select>
				</td>
				
				<th class="table_td_width deal_county"><span class="red_mark" style="display: inline;">*</span>县分：</th>
				<td class="table_td_width deal_county">
					<select id="dealer_edit_county_id" class="table_select">
	            	</select>
				</td>
				
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>流程环节：</th>
				<td class="table_td_width">
					<select id="dealer_edit_node" class="table_select">
	            	</select>
				</td>
			</tr>
			
			<tr>
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>处理单位类型：</th>
				<td class="table_td_width">
					<select id="dealer_edit_dealer_type" class="table_select">
						<option value="org">组织</option>
						<option value="person">人员</option>
						<option value="team">团队</option>
	            	</select>
				</td>
				
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>处理单位编码：</th>
				<td class="table_td_width">
			    	<input id="dealer_edit_dealer_code" type="text" class="ipttxt" disabled="disabled" />
				</td>
				
				<th class="table_td_width"><span class="red_mark" style="display: inline;">*</span>处理单位名称：</th>
				<td class="table_td_width">
			    	<input id="dealer_edit_dealer_name" type="text" class="ipttxt" disabled="disabled" />
				</td>
					
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
			</tr>
			
			<tr class="search_org">
				<td style="text-align: center;font-size: 120%;width: 1164px;" colspan="8">
					<br>
					<br>
		   			<h1>选择处理组织</h1>
		   		</td>
			</tr>
			
			<tr class="search_org">
				<th class="table_td_width">组织编号：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_org_party_id" name="party_id" />
				</td>					
			
				<th class="table_td_width">组织名称：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_org_org_name" name="org_name"  />
				</td>
				
				<th class="table_td_width">总部编码：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_org_hq_dept_id" name="hq_dept_id"  />
				</td>
			
				<th class="table_td_width">省内编码：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_org_dept_id" name="dept_id"  />
				</td>
			</tr>
			
			<tr class="search_org">
				<th class="table_td_width"></th>
				<td class="table_td_width">
					<input class="comBtn" type="submit" name="searchBtn" onclick="WorkFlowTool.doOrgSearch();" value="查询" style="margin-right:10px;"/>
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
			
			<tr class="search_user">
				<td style="text-align: center;font-size: 120%;width: 1164px;" colspan="8">
					<br>
					<br>
		   			<h1>选择处理人员</h1>
		   		</td>
			</tr>
			
			<tr class="search_user">
				<th class="table_td_width">人员工号：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_user_id" />
				</td>					
			
				<th class="table_td_width">人员名称：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_user_name" />
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					<input class="comBtn" type="submit" name="searchBtn" onclick="WorkFlowTool.doUserSearch();" value="查询" style="margin-right:10px;"/>
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					
				</td>
			</tr>
			
			<tr class="search_team">
				<td style="text-align: center;font-size: 120%;width: 1164px;" colspan="8">
					<br>
					<br>
		   			<h1>选择处理团队</h1>
		   		</td>
			</tr>
			
			<tr class="search_team">
				<th class="table_td_width">团队层级：</th>
				<td class="table_td_width">
					<select id="search_team_level" class="table_select">
						<option value=""></option>
						<option value="province">省公司</option>
						<option value="region">地市</option>
						<option value="county">县分</option>
	            	</select>
				</td>
				
				<th class="table_td_width">团队编号：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_team_id" />
				</td>					
			
				<th class="table_td_width">团队名称：</th>
				<td class="table_td_width">
					<input type="text" class="ipttxt" id="search_team_name" />
				</td>
				
				<th class="table_td_width"></th>
				<td class="table_td_width">
					<input class="comBtn" type="submit" name="searchBtn" onclick="WorkFlowTool.doTeamSearch();" value="查询" style="margin-right:10px;"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div class="searchformDiv" style="background: white;border: 0px;" >
		<div>
			<iframe id="org_info_frame" class="search_org" style="width: 100%;height: 430px;"></iframe>
		</div>
		<div>
			<iframe id="user_info_frame" class="search_user" style="width: 100%;height: 430px;"></iframe>
		</div>
		<div>
			<iframe id="team_info_frame" class="search_team" style="width: 100%;height: 430px;"></iframe>
		</div>
	</div>

	<div class="searchformDiv" style="background: white;border: 0px;margin-top: 30px;">
		<table>
			<tr>
				<th></th>
				<td colspan="7">
			    	<input class="comBtn" type="button" onclick="WorkFlowTool.saveDealer();" value="保存" style="margin-left: 450px;"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="SearchDealerDiag"></div>
</body>

<script type="text/javascript">
$(function(){
	WorkFlowTool.initDealerEdit();
});	

</script>

